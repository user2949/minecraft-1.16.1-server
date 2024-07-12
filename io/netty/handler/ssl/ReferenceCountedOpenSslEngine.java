package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.SSL;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.locks.Lock;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.security.cert.X509Certificate;

public class ReferenceCountedOpenSslEngine extends SSLEngine implements ReferenceCounted, ApplicationProtocolAccessor {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslEngine.class);
	private static final SSLException BEGIN_HANDSHAKE_ENGINE_CLOSED = ThrowableUtil.unknownStackTrace(
		new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "beginHandshake()"
	);
	private static final SSLException HANDSHAKE_ENGINE_CLOSED = ThrowableUtil.unknownStackTrace(
		new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "handshake()"
	);
	private static final SSLException RENEGOTIATION_UNSUPPORTED = ThrowableUtil.unknownStackTrace(
		new SSLException("renegotiation unsupported"), ReferenceCountedOpenSslEngine.class, "beginHandshake()"
	);
	private static final ResourceLeakDetector<ReferenceCountedOpenSslEngine> leakDetector = ResourceLeakDetectorFactory.instance()
		.newResourceLeakDetector(ReferenceCountedOpenSslEngine.class);
	private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV2 = 0;
	private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV3 = 1;
	private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1 = 2;
	private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_1 = 3;
	private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_2 = 4;
	private static final int[] OPENSSL_OP_NO_PROTOCOLS = new int[]{
		SSL.SSL_OP_NO_SSLv2, SSL.SSL_OP_NO_SSLv3, SSL.SSL_OP_NO_TLSv1, SSL.SSL_OP_NO_TLSv1_1, SSL.SSL_OP_NO_TLSv1_2
	};
	private static final int DEFAULT_HOSTNAME_VALIDATION_FLAGS = 0;
	static final int MAX_PLAINTEXT_LENGTH = SSL.SSL_MAX_PLAINTEXT_LENGTH;
	private static final int MAX_RECORD_SIZE = SSL.SSL_MAX_RECORD_LENGTH;
	private static final AtomicIntegerFieldUpdater<ReferenceCountedOpenSslEngine> DESTROYED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(
		ReferenceCountedOpenSslEngine.class, "destroyed"
	);
	private static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
	private static final SSLEngineResult NEED_UNWRAP_OK = new SSLEngineResult(Status.OK, HandshakeStatus.NEED_UNWRAP, 0, 0);
	private static final SSLEngineResult NEED_UNWRAP_CLOSED = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NEED_UNWRAP, 0, 0);
	private static final SSLEngineResult NEED_WRAP_OK = new SSLEngineResult(Status.OK, HandshakeStatus.NEED_WRAP, 0, 0);
	private static final SSLEngineResult NEED_WRAP_CLOSED = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NEED_WRAP, 0, 0);
	private static final SSLEngineResult CLOSED_NOT_HANDSHAKING = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NOT_HANDSHAKING, 0, 0);
	private long ssl;
	private long networkBIO;
	private boolean certificateSet;
	private ReferenceCountedOpenSslEngine.HandshakeState handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED;
	private boolean receivedShutdown;
	private volatile int destroyed;
	private volatile String applicationProtocol;
	private final ResourceLeakTracker<ReferenceCountedOpenSslEngine> leak;
	private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted() {
		@Override
		public ReferenceCounted touch(Object hint) {
			if (ReferenceCountedOpenSslEngine.this.leak != null) {
				ReferenceCountedOpenSslEngine.this.leak.record(hint);
			}

			return ReferenceCountedOpenSslEngine.this;
		}

		@Override
		protected void deallocate() {
			ReferenceCountedOpenSslEngine.this.shutdown();
			if (ReferenceCountedOpenSslEngine.this.leak != null) {
				boolean closed = ReferenceCountedOpenSslEngine.this.leak.close(ReferenceCountedOpenSslEngine.this);

				assert closed;
			}
		}
	};
	private volatile ClientAuth clientAuth = ClientAuth.NONE;
	private volatile long lastAccessed = -1L;
	private String endPointIdentificationAlgorithm;
	private Object algorithmConstraints;
	private List<String> sniHostNames;
	private volatile Collection<?> matchers;
	private boolean isInboundDone;
	private boolean outboundClosed;
	final boolean jdkCompatibilityMode;
	private final boolean clientMode;
	private final ByteBufAllocator alloc;
	private final OpenSslEngineMap engineMap;
	private final OpenSslApplicationProtocolNegotiator apn;
	private final ReferenceCountedOpenSslEngine.OpenSslSession session;
	private final Certificate[] localCerts;
	private final ByteBuffer[] singleSrcBuffer = new ByteBuffer[1];
	private final ByteBuffer[] singleDstBuffer = new ByteBuffer[1];
	private final OpenSslKeyMaterialManager keyMaterialManager;
	private final boolean enableOcsp;
	private int maxWrapOverhead;
	private int maxWrapBufferSize;
	SSLHandshakeException handshakeException;

	ReferenceCountedOpenSslEngine(
		ReferenceCountedOpenSslContext context, ByteBufAllocator alloc, String peerHost, int peerPort, boolean jdkCompatibilityMode, boolean leakDetection
	) {
		super(peerHost, peerPort);
		OpenSsl.ensureAvailability();
		this.alloc = ObjectUtil.checkNotNull(alloc, "alloc");
		this.apn = (OpenSslApplicationProtocolNegotiator)context.applicationProtocolNegotiator();
		this.session = new ReferenceCountedOpenSslEngine.OpenSslSession(context.sessionContext());
		this.clientMode = context.isClient();
		this.engineMap = context.engineMap;
		this.localCerts = context.keyCertChain;
		this.keyMaterialManager = context.keyMaterialManager();
		this.enableOcsp = context.enableOcsp;
		this.jdkCompatibilityMode = jdkCompatibilityMode;
		Lock readerLock = context.ctxLock.readLock();
		readerLock.lock();

		long finalSsl;
		try {
			finalSsl = SSL.newSSL(context.ctx, !context.isClient());
		} finally {
			readerLock.unlock();
		}

		synchronized (this) {
			this.ssl = finalSsl;

			try {
				this.networkBIO = SSL.bioNewByteBuffer(this.ssl, context.getBioNonApplicationBufferSize());
				this.setClientAuth(this.clientMode ? ClientAuth.NONE : context.clientAuth);
				if (context.protocols != null) {
					this.setEnabledProtocols(context.protocols);
				}

				if (this.clientMode && peerHost != null) {
					SSL.setTlsExtHostName(this.ssl, peerHost);
				}

				if (this.enableOcsp) {
					SSL.enableOcsp(this.ssl);
				}

				if (!jdkCompatibilityMode) {
					SSL.setMode(this.ssl, SSL.getMode(this.ssl) | SSL.SSL_MODE_ENABLE_PARTIAL_WRITE);
				}

				this.calculateMaxWrapOverhead();
			} catch (Throwable var16) {
				SSL.freeSSL(this.ssl);
				PlatformDependent.throwException(var16);
			}
		}

		this.leak = leakDetection ? leakDetector.track(this) : null;
	}

	public void setOcspResponse(byte[] response) {
		if (!this.enableOcsp) {
			throw new IllegalStateException("OCSP stapling is not enabled");
		} else if (this.clientMode) {
			throw new IllegalStateException("Not a server SSLEngine");
		} else {
			synchronized (this) {
				SSL.setOcspResponse(this.ssl, response);
			}
		}
	}

	public byte[] getOcspResponse() {
		if (!this.enableOcsp) {
			throw new IllegalStateException("OCSP stapling is not enabled");
		} else if (!this.clientMode) {
			throw new IllegalStateException("Not a client SSLEngine");
		} else {
			synchronized (this) {
				return SSL.getOcspResponse(this.ssl);
			}
		}
	}

	@Override
	public final int refCnt() {
		return this.refCnt.refCnt();
	}

	@Override
	public final ReferenceCounted retain() {
		this.refCnt.retain();
		return this;
	}

	@Override
	public final ReferenceCounted retain(int increment) {
		this.refCnt.retain(increment);
		return this;
	}

	@Override
	public final ReferenceCounted touch() {
		this.refCnt.touch();
		return this;
	}

	@Override
	public final ReferenceCounted touch(Object hint) {
		this.refCnt.touch(hint);
		return this;
	}

	@Override
	public final boolean release() {
		return this.refCnt.release();
	}

	@Override
	public final boolean release(int decrement) {
		return this.refCnt.release(decrement);
	}

	public final synchronized SSLSession getHandshakeSession() {
		switch (this.handshakeState) {
			case NOT_STARTED:
			case FINISHED:
				return null;
			default:
				return this.session;
		}
	}

	public final synchronized long sslPointer() {
		return this.ssl;
	}

	public final synchronized void shutdown() {
		if (DESTROYED_UPDATER.compareAndSet(this, 0, 1)) {
			this.engineMap.remove(this.ssl);
			SSL.freeSSL(this.ssl);
			this.ssl = this.networkBIO = 0L;
			this.isInboundDone = this.outboundClosed = true;
		}

		SSL.clearError();
	}

	private int writePlaintextData(ByteBuffer src, int len) {
		int pos = src.position();
		int limit = src.limit();
		int sslWrote;
		if (src.isDirect()) {
			sslWrote = SSL.writeToSSL(this.ssl, bufferAddress(src) + (long)pos, len);
			if (sslWrote > 0) {
				src.position(pos + sslWrote);
			}
		} else {
			ByteBuf buf = this.alloc.directBuffer(len);

			try {
				src.limit(pos + len);
				buf.setBytes(0, src);
				src.limit(limit);
				sslWrote = SSL.writeToSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
				if (sslWrote > 0) {
					src.position(pos + sslWrote);
				} else {
					src.position(pos);
				}
			} finally {
				buf.release();
			}
		}

		return sslWrote;
	}

	private ByteBuf writeEncryptedData(ByteBuffer src, int len) {
		int pos = src.position();
		if (src.isDirect()) {
			SSL.bioSetByteBuffer(this.networkBIO, bufferAddress(src) + (long)pos, len, false);
		} else {
			ByteBuf buf = this.alloc.directBuffer(len);

			try {
				int limit = src.limit();
				src.limit(pos + len);
				buf.writeBytes(src);
				src.position(pos);
				src.limit(limit);
				SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(buf), len, false);
				return buf;
			} catch (Throwable var6) {
				buf.release();
				PlatformDependent.throwException(var6);
			}
		}

		return null;
	}

	private int readPlaintextData(ByteBuffer dst) {
		int pos = dst.position();
		int sslRead;
		if (dst.isDirect()) {
			sslRead = SSL.readFromSSL(this.ssl, bufferAddress(dst) + (long)pos, dst.limit() - pos);
			if (sslRead > 0) {
				dst.position(pos + sslRead);
			}
		} else {
			int limit = dst.limit();
			int len = Math.min(this.maxEncryptedPacketLength0(), limit - pos);
			ByteBuf buf = this.alloc.directBuffer(len);

			try {
				sslRead = SSL.readFromSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
				if (sslRead > 0) {
					dst.limit(pos + sslRead);
					buf.getBytes(buf.readerIndex(), dst);
					dst.limit(limit);
				}
			} finally {
				buf.release();
			}
		}

		return sslRead;
	}

	final synchronized int maxWrapOverhead() {
		return this.maxWrapOverhead;
	}

	final synchronized int maxEncryptedPacketLength() {
		return this.maxEncryptedPacketLength0();
	}

	final int maxEncryptedPacketLength0() {
		return this.maxWrapOverhead + MAX_PLAINTEXT_LENGTH;
	}

	final int calculateMaxLengthForWrap(int plaintextLength, int numComponents) {
		return (int)Math.min((long)this.maxWrapBufferSize, (long)plaintextLength + (long)this.maxWrapOverhead * (long)numComponents);
	}

	final synchronized int sslPending() {
		return this.sslPending0();
	}

	private void calculateMaxWrapOverhead() {
		this.maxWrapOverhead = SSL.getMaxWrapOverhead(this.ssl);
		this.maxWrapBufferSize = this.jdkCompatibilityMode ? this.maxEncryptedPacketLength0() : this.maxEncryptedPacketLength0() << 4;
	}

	private int sslPending0() {
		return this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED ? 0 : SSL.sslPending(this.ssl);
	}

	private boolean isBytesAvailableEnoughForWrap(int bytesAvailable, int plaintextLength, int numComponents) {
		return (long)bytesAvailable - (long)this.maxWrapOverhead * (long)numComponents >= (long)plaintextLength;
	}

	public final SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int length, ByteBuffer dst) throws SSLException {
		if (srcs == null) {
			throw new IllegalArgumentException("srcs is null");
		} else if (dst == null) {
			throw new IllegalArgumentException("dst is null");
		} else if (offset >= srcs.length || offset + length > srcs.length) {
			throw new IndexOutOfBoundsException(
				"offset: " + offset + ", length: " + length + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))"
			);
		} else if (dst.isReadOnly()) {
			throw new ReadOnlyBufferException();
		} else {
			synchronized (this) {
				if (this.isOutboundDone()) {
					return !this.isInboundDone() && !this.isDestroyed() ? NEED_UNWRAP_CLOSED : CLOSED_NOT_HANDSHAKING;
				} else {
					int bytesProduced = 0;
					ByteBuf bioReadCopyBuf = null;

					try {
						if (dst.isDirect()) {
							SSL.bioSetByteBuffer(this.networkBIO, bufferAddress(dst) + (long)dst.position(), dst.remaining(), true);
						} else {
							bioReadCopyBuf = this.alloc.directBuffer(dst.remaining());
							SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(bioReadCopyBuf), bioReadCopyBuf.writableBytes(), true);
						}

						int bioLengthBefore = SSL.bioLengthByteBuffer(this.networkBIO);
						if (this.outboundClosed) {
							bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
							if (bytesProduced <= 0) {
								return this.newResultMayFinishHandshake(HandshakeStatus.NOT_HANDSHAKING, 0, 0);
							} else if (this.doSSLShutdown()) {
								bytesProduced = bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
								return this.newResultMayFinishHandshake(HandshakeStatus.NEED_WRAP, 0, bytesProduced);
							} else {
								return this.newResultMayFinishHandshake(HandshakeStatus.NOT_HANDSHAKING, 0, bytesProduced);
							}
						} else {
							HandshakeStatus status = HandshakeStatus.NOT_HANDSHAKING;
							if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED) {
								if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY) {
									this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_IMPLICITLY;
								}

								bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
								if (bytesProduced > 0 && this.handshakeException != null) {
									return this.newResult(HandshakeStatus.NEED_WRAP, 0, bytesProduced);
								}

								status = this.handshake();
								bytesProduced = bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
								if (bytesProduced > 0) {
									return this.newResult(
										this.mayFinishHandshake(
											status != HandshakeStatus.FINISHED
												? (bytesProduced == bioLengthBefore ? HandshakeStatus.NEED_WRAP : this.getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO)))
												: HandshakeStatus.FINISHED
										),
										0,
										bytesProduced
									);
								}

								if (status == HandshakeStatus.NEED_UNWRAP) {
									return this.isOutboundDone() ? NEED_UNWRAP_CLOSED : NEED_UNWRAP_OK;
								}

								if (this.outboundClosed) {
									bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
									return this.newResultMayFinishHandshake(status, 0, bytesProduced);
								}
							}

							int endOffset = offset + length;
							if (this.jdkCompatibilityMode) {
								int srcsLen = 0;

								for (int i = offset; i < endOffset; i++) {
									ByteBuffer src = srcs[i];
									if (src == null) {
										throw new IllegalArgumentException("srcs[" + i + "] is null");
									}

									if (srcsLen != MAX_PLAINTEXT_LENGTH) {
										srcsLen += src.remaining();
										if (srcsLen > MAX_PLAINTEXT_LENGTH || srcsLen < 0) {
											srcsLen = MAX_PLAINTEXT_LENGTH;
										}
									}
								}

								if (!this.isBytesAvailableEnoughForWrap(dst.remaining(), srcsLen, 1)) {
									return new SSLEngineResult(Status.BUFFER_OVERFLOW, this.getHandshakeStatus(), 0, 0);
								}
							}

							int bytesConsumed = 0;

							for (bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO); offset < endOffset; offset++) {
								ByteBuffer srcx = srcs[offset];
								int remaining = srcx.remaining();
								if (remaining != 0) {
									int bytesWritten;
									if (this.jdkCompatibilityMode) {
										bytesWritten = this.writePlaintextData(srcx, Math.min(remaining, MAX_PLAINTEXT_LENGTH - bytesConsumed));
									} else {
										int availableCapacityForWrap = dst.remaining() - bytesProduced - this.maxWrapOverhead;
										if (availableCapacityForWrap <= 0) {
											return new SSLEngineResult(Status.BUFFER_OVERFLOW, this.getHandshakeStatus(), bytesConsumed, bytesProduced);
										}

										bytesWritten = this.writePlaintextData(srcx, Math.min(remaining, availableCapacityForWrap));
									}

									if (bytesWritten <= 0) {
										int sslError = SSL.getError(this.ssl, bytesWritten);
										if (sslError != SSL.SSL_ERROR_ZERO_RETURN) {
											if (sslError != SSL.SSL_ERROR_WANT_READ) {
												if (sslError != SSL.SSL_ERROR_WANT_WRITE) {
													throw this.shutdownWithError("SSL_write");
												}

												return this.newResult(Status.BUFFER_OVERFLOW, status, bytesConsumed, bytesProduced);
											}

											return this.newResult(HandshakeStatus.NEED_UNWRAP, bytesConsumed, bytesProduced);
										}

										if (this.receivedShutdown) {
											return this.newResult(HandshakeStatus.NOT_HANDSHAKING, bytesConsumed, bytesProduced);
										}

										this.closeAll();
										bytesProduced += bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
										HandshakeStatus hs = this.mayFinishHandshake(
											status != HandshakeStatus.FINISHED
												? (bytesProduced == dst.remaining() ? HandshakeStatus.NEED_WRAP : this.getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO)))
												: HandshakeStatus.FINISHED
										);
										return this.newResult(hs, bytesConsumed, bytesProduced);
									}

									bytesConsumed += bytesWritten;
									int pendingNow = SSL.bioLengthByteBuffer(this.networkBIO);
									bytesProduced += bioLengthBefore - pendingNow;
									bioLengthBefore = pendingNow;
									if (this.jdkCompatibilityMode || bytesProduced == dst.remaining()) {
										return this.newResultMayFinishHandshake(status, bytesConsumed, bytesProduced);
									}
								}
							}

							return this.newResultMayFinishHandshake(status, bytesConsumed, bytesProduced);
						}
					} finally {
						SSL.bioClearByteBuffer(this.networkBIO);
						if (bioReadCopyBuf == null) {
							dst.position(dst.position() + bytesProduced);
						} else {
							assert bioReadCopyBuf.readableBytes() <= dst.remaining() : "The destination buffer "
								+ dst
								+ " didn't have enough remaining space to hold the encrypted content in "
								+ bioReadCopyBuf;

							dst.put(bioReadCopyBuf.internalNioBuffer(bioReadCopyBuf.readerIndex(), bytesProduced));
							bioReadCopyBuf.release();
						}
					}
				}
			}
		}
	}

	private SSLEngineResult newResult(HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
		return this.newResult(Status.OK, hs, bytesConsumed, bytesProduced);
	}

	private SSLEngineResult newResult(Status status, HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
		if (this.isOutboundDone()) {
			if (this.isInboundDone()) {
				hs = HandshakeStatus.NOT_HANDSHAKING;
				this.shutdown();
			}

			return new SSLEngineResult(Status.CLOSED, hs, bytesConsumed, bytesProduced);
		} else {
			return new SSLEngineResult(status, hs, bytesConsumed, bytesProduced);
		}
	}

	private SSLEngineResult newResultMayFinishHandshake(HandshakeStatus hs, int bytesConsumed, int bytesProduced) throws SSLException {
		return this.newResult(
			this.mayFinishHandshake(hs != HandshakeStatus.FINISHED ? this.getHandshakeStatus() : HandshakeStatus.FINISHED), bytesConsumed, bytesProduced
		);
	}

	private SSLEngineResult newResultMayFinishHandshake(Status status, HandshakeStatus hs, int bytesConsumed, int bytesProduced) throws SSLException {
		return this.newResult(
			status, this.mayFinishHandshake(hs != HandshakeStatus.FINISHED ? this.getHandshakeStatus() : HandshakeStatus.FINISHED), bytesConsumed, bytesProduced
		);
	}

	private SSLException shutdownWithError(String operations) {
		String err = SSL.getLastError();
		return this.shutdownWithError(operations, err);
	}

	private SSLException shutdownWithError(String operation, String err) {
		if (logger.isDebugEnabled()) {
			logger.debug("{} failed: OpenSSL error: {}", operation, err);
		}

		this.shutdown();
		return (SSLException)(this.handshakeState == ReferenceCountedOpenSslEngine.HandshakeState.FINISHED ? new SSLException(err) : new SSLHandshakeException(err));
	}

	public final SSLEngineResult unwrap(ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer[] dsts, int dstsOffset, int dstsLength) throws SSLException {
		if (srcs == null) {
			throw new NullPointerException("srcs");
		} else if (srcsOffset >= srcs.length || srcsOffset + srcsLength > srcs.length) {
			throw new IndexOutOfBoundsException(
				"offset: " + srcsOffset + ", length: " + srcsLength + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))"
			);
		} else if (dsts == null) {
			throw new IllegalArgumentException("dsts is null");
		} else if (dstsOffset < dsts.length && dstsOffset + dstsLength <= dsts.length) {
			long capacity = 0L;
			int dstsEndOffset = dstsOffset + dstsLength;

			for (int i = dstsOffset; i < dstsEndOffset; i++) {
				ByteBuffer dst = dsts[i];
				if (dst == null) {
					throw new IllegalArgumentException("dsts[" + i + "] is null");
				}

				if (dst.isReadOnly()) {
					throw new ReadOnlyBufferException();
				}

				capacity += (long)dst.remaining();
			}

			int srcsEndOffset = srcsOffset + srcsLength;
			long len = 0L;

			for (int i = srcsOffset; i < srcsEndOffset; i++) {
				ByteBuffer src = srcs[i];
				if (src == null) {
					throw new IllegalArgumentException("srcs[" + i + "] is null");
				}

				len += (long)src.remaining();
			}

			synchronized (this) {
				if (this.isInboundDone()) {
					return !this.isOutboundDone() && !this.isDestroyed() ? NEED_WRAP_CLOSED : CLOSED_NOT_HANDSHAKING;
				} else {
					HandshakeStatus status = HandshakeStatus.NOT_HANDSHAKING;
					if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED) {
						if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY) {
							this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_IMPLICITLY;
						}

						status = this.handshake();
						if (status == HandshakeStatus.NEED_WRAP) {
							return NEED_WRAP_OK;
						}

						if (this.isInboundDone) {
							return NEED_WRAP_CLOSED;
						}
					}

					int sslPending = this.sslPending0();
					int packetLength;
					if (this.jdkCompatibilityMode) {
						if (len < 5L) {
							return this.newResultMayFinishHandshake(Status.BUFFER_UNDERFLOW, status, 0, 0);
						}

						packetLength = SslUtils.getEncryptedPacketLength(srcs, srcsOffset);
						if (packetLength == -2) {
							throw new NotSslRecordException("not an SSL/TLS record");
						}

						int packetLengthDataOnly = packetLength - 5;
						if ((long)packetLengthDataOnly > capacity) {
							if (packetLengthDataOnly > MAX_RECORD_SIZE) {
								throw new SSLException("Illegal packet length: " + packetLengthDataOnly + " > " + this.session.getApplicationBufferSize());
							}

							this.session.tryExpandApplicationBufferSize(packetLengthDataOnly);
							return this.newResultMayFinishHandshake(Status.BUFFER_OVERFLOW, status, 0, 0);
						}

						if (len < (long)packetLength) {
							return this.newResultMayFinishHandshake(Status.BUFFER_UNDERFLOW, status, 0, 0);
						}
					} else {
						if (len == 0L && sslPending <= 0) {
							return this.newResultMayFinishHandshake(Status.BUFFER_UNDERFLOW, status, 0, 0);
						}

						if (capacity == 0L) {
							return this.newResultMayFinishHandshake(Status.BUFFER_OVERFLOW, status, 0, 0);
						}

						packetLength = (int)Math.min(2147483647L, len);
					}

					assert srcsOffset < srcsEndOffset;

					assert capacity > 0L;

					int bytesProduced = 0;
					int bytesConsumed = 0;

					try {
						label620:
						while (true) {
							ByteBuffer src = srcs[srcsOffset];
							int remaining = src.remaining();
							ByteBuf bioWriteCopyBuf;
							int pendingEncryptedBytes;
							if (remaining == 0) {
								if (sslPending <= 0) {
									if (++srcsOffset < srcsEndOffset) {
										continue;
									}
									break;
								}

								bioWriteCopyBuf = null;
								pendingEncryptedBytes = SSL.bioLengthByteBuffer(this.networkBIO);
							} else {
								pendingEncryptedBytes = Math.min(packetLength, remaining);
								bioWriteCopyBuf = this.writeEncryptedData(src, pendingEncryptedBytes);
							}

							try {
								while (true) {
									ByteBuffer dstx = dsts[dstsOffset];
									if (dstx.hasRemaining()) {
										int bytesRead = this.readPlaintextData(dstx);
										int localBytesConsumed = pendingEncryptedBytes - SSL.bioLengthByteBuffer(this.networkBIO);
										bytesConsumed += localBytesConsumed;
										packetLength -= localBytesConsumed;
										pendingEncryptedBytes -= localBytesConsumed;
										src.position(src.position() + localBytesConsumed);
										if (bytesRead <= 0) {
											int sslError = SSL.getError(this.ssl, bytesRead);
											if (sslError != SSL.SSL_ERROR_WANT_READ && sslError != SSL.SSL_ERROR_WANT_WRITE) {
												if (sslError != SSL.SSL_ERROR_ZERO_RETURN) {
													return this.sslReadErrorResult(SSL.getLastErrorNumber(), bytesConsumed, bytesProduced);
												}

												if (!this.receivedShutdown) {
													this.closeAll();
												}

												return this.newResultMayFinishHandshake(this.isInboundDone() ? Status.CLOSED : Status.OK, status, bytesConsumed, bytesProduced);
											}

											if (++srcsOffset < srcsEndOffset) {
												break;
											}
											break label620;
										}

										bytesProduced += bytesRead;
										if (!dstx.hasRemaining()) {
											sslPending = this.sslPending0();
											if (++dstsOffset >= dstsEndOffset) {
												return sslPending > 0
													? this.newResult(Status.BUFFER_OVERFLOW, status, bytesConsumed, bytesProduced)
													: this.newResultMayFinishHandshake(this.isInboundDone() ? Status.CLOSED : Status.OK, status, bytesConsumed, bytesProduced);
											}
										} else if (packetLength == 0 || this.jdkCompatibilityMode) {
											break label620;
										}
									} else if (++dstsOffset >= dstsEndOffset) {
										break label620;
									}
								}
							} finally {
								if (bioWriteCopyBuf != null) {
									bioWriteCopyBuf.release();
								}
							}
						}
					} finally {
						SSL.bioClearByteBuffer(this.networkBIO);
						this.rejectRemoteInitiatedRenegotiation();
					}

					if (!this.receivedShutdown && (SSL.getShutdown(this.ssl) & SSL.SSL_RECEIVED_SHUTDOWN) == SSL.SSL_RECEIVED_SHUTDOWN) {
						this.closeAll();
					}

					return this.newResultMayFinishHandshake(this.isInboundDone() ? Status.CLOSED : Status.OK, status, bytesConsumed, bytesProduced);
				}
			}
		} else {
			throw new IndexOutOfBoundsException(
				"offset: " + dstsOffset + ", length: " + dstsLength + " (expected: offset <= offset + length <= dsts.length (" + dsts.length + "))"
			);
		}
	}

	private SSLEngineResult sslReadErrorResult(int err, int bytesConsumed, int bytesProduced) throws SSLException {
		String errStr = SSL.getErrorString((long)err);
		if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
			if (this.handshakeException == null && this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED) {
				this.handshakeException = new SSLHandshakeException(errStr);
			}

			return new SSLEngineResult(Status.OK, HandshakeStatus.NEED_WRAP, bytesConsumed, bytesProduced);
		} else {
			throw this.shutdownWithError("SSL_read", errStr);
		}
	}

	private void closeAll() throws SSLException {
		this.receivedShutdown = true;
		this.closeOutbound();
		this.closeInbound();
	}

	private void rejectRemoteInitiatedRenegotiation() throws SSLHandshakeException {
		if (!this.isDestroyed() && SSL.getHandshakeCount(this.ssl) > 1) {
			this.shutdown();
			throw new SSLHandshakeException("remote-initiated renegotiation not allowed");
		}
	}

	public final SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dsts) throws SSLException {
		return this.unwrap(srcs, 0, srcs.length, dsts, 0, dsts.length);
	}

	private ByteBuffer[] singleSrcBuffer(ByteBuffer src) {
		this.singleSrcBuffer[0] = src;
		return this.singleSrcBuffer;
	}

	private void resetSingleSrcBuffer() {
		this.singleSrcBuffer[0] = null;
	}

	private ByteBuffer[] singleDstBuffer(ByteBuffer src) {
		this.singleDstBuffer[0] = src;
		return this.singleDstBuffer;
	}

	private void resetSingleDstBuffer() {
		this.singleDstBuffer[0] = null;
	}

	public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) throws SSLException {
		SSLEngineResult var5;
		try {
			var5 = this.unwrap(this.singleSrcBuffer(src), 0, 1, dsts, offset, length);
		} finally {
			this.resetSingleSrcBuffer();
		}

		return var5;
	}

	public final synchronized SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
		SSLEngineResult var3;
		try {
			var3 = this.wrap(this.singleSrcBuffer(src), dst);
		} finally {
			this.resetSingleSrcBuffer();
		}

		return var3;
	}

	public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
		SSLEngineResult var3;
		try {
			var3 = this.unwrap(this.singleSrcBuffer(src), this.singleDstBuffer(dst));
		} finally {
			this.resetSingleSrcBuffer();
			this.resetSingleDstBuffer();
		}

		return var3;
	}

	public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
		SSLEngineResult var3;
		try {
			var3 = this.unwrap(this.singleSrcBuffer(src), dsts);
		} finally {
			this.resetSingleSrcBuffer();
		}

		return var3;
	}

	public final Runnable getDelegatedTask() {
		return null;
	}

	public final synchronized void closeInbound() throws SSLException {
		if (!this.isInboundDone) {
			this.isInboundDone = true;
			if (this.isOutboundDone()) {
				this.shutdown();
			}

			if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED && !this.receivedShutdown) {
				throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
			}
		}
	}

	public final synchronized boolean isInboundDone() {
		return this.isInboundDone;
	}

	public final synchronized void closeOutbound() {
		if (!this.outboundClosed) {
			this.outboundClosed = true;
			if (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED && !this.isDestroyed()) {
				int mode = SSL.getShutdown(this.ssl);
				if ((mode & SSL.SSL_SENT_SHUTDOWN) != SSL.SSL_SENT_SHUTDOWN) {
					this.doSSLShutdown();
				}
			} else {
				this.shutdown();
			}
		}
	}

	private boolean doSSLShutdown() {
		if (SSL.isInInit(this.ssl) != 0) {
			return false;
		} else {
			int err = SSL.shutdownSSL(this.ssl);
			if (err < 0) {
				int sslErr = SSL.getError(this.ssl, err);
				if (sslErr == SSL.SSL_ERROR_SYSCALL || sslErr == SSL.SSL_ERROR_SSL) {
					if (logger.isDebugEnabled()) {
						logger.debug("SSL_shutdown failed: OpenSSL error: {}", SSL.getLastError());
					}

					this.shutdown();
					return false;
				}

				SSL.clearError();
			}

			return true;
		}
	}

	public final synchronized boolean isOutboundDone() {
		return this.outboundClosed && (this.networkBIO == 0L || SSL.bioLengthNonApplication(this.networkBIO) == 0);
	}

	public final String[] getSupportedCipherSuites() {
		return (String[])OpenSsl.AVAILABLE_CIPHER_SUITES.toArray(new String[OpenSsl.AVAILABLE_CIPHER_SUITES.size()]);
	}

	public final String[] getEnabledCipherSuites() {
		String[] enabled;
		synchronized (this) {
			if (this.isDestroyed()) {
				return EmptyArrays.EMPTY_STRINGS;
			}

			enabled = SSL.getCiphers(this.ssl);
		}

		if (enabled == null) {
			return EmptyArrays.EMPTY_STRINGS;
		} else {
			synchronized (this) {
				for (int i = 0; i < enabled.length; i++) {
					String mapped = this.toJavaCipherSuite(enabled[i]);
					if (mapped != null) {
						enabled[i] = mapped;
					}
				}

				return enabled;
			}
		}
	}

	public final void setEnabledCipherSuites(String[] cipherSuites) {
		ObjectUtil.checkNotNull(cipherSuites, "cipherSuites");
		StringBuilder buf = new StringBuilder();

		for (String c : cipherSuites) {
			if (c == null) {
				break;
			}

			String converted = CipherSuiteConverter.toOpenSsl(c);
			if (converted == null) {
				converted = c;
			}

			if (!OpenSsl.isCipherSuiteAvailable(converted)) {
				throw new IllegalArgumentException("unsupported cipher suite: " + c + '(' + converted + ')');
			}

			buf.append(converted);
			buf.append(':');
		}

		if (buf.length() == 0) {
			throw new IllegalArgumentException("empty cipher suites");
		} else {
			buf.setLength(buf.length() - 1);
			String cipherSuiteSpec = buf.toString();
			synchronized (this) {
				if (!this.isDestroyed()) {
					try {
						SSL.setCipherSuites(this.ssl, cipherSuiteSpec);
					} catch (Exception var9) {
						throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec, var9);
					}
				} else {
					throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec);
				}
			}
		}
	}

	public final String[] getSupportedProtocols() {
		return (String[])OpenSsl.SUPPORTED_PROTOCOLS_SET.toArray(new String[OpenSsl.SUPPORTED_PROTOCOLS_SET.size()]);
	}

	public final String[] getEnabledProtocols() {
		List<String> enabled = new ArrayList(6);
		enabled.add("SSLv2Hello");
		int opts;
		synchronized (this) {
			if (this.isDestroyed()) {
				return (String[])enabled.toArray(new String[1]);
			}

			opts = SSL.getOptions(this.ssl);
		}

		if (isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1, "TLSv1")) {
			enabled.add("TLSv1");
		}

		if (isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1_1, "TLSv1.1")) {
			enabled.add("TLSv1.1");
		}

		if (isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1_2, "TLSv1.2")) {
			enabled.add("TLSv1.2");
		}

		if (isProtocolEnabled(opts, SSL.SSL_OP_NO_SSLv2, "SSLv2")) {
			enabled.add("SSLv2");
		}

		if (isProtocolEnabled(opts, SSL.SSL_OP_NO_SSLv3, "SSLv3")) {
			enabled.add("SSLv3");
		}

		return (String[])enabled.toArray(new String[enabled.size()]);
	}

	private static boolean isProtocolEnabled(int opts, int disableMask, String protocolString) {
		return (opts & disableMask) == 0 && OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(protocolString);
	}

	public final void setEnabledProtocols(String[] protocols) {
		if (protocols == null) {
			throw new IllegalArgumentException();
		} else {
			int minProtocolIndex = OPENSSL_OP_NO_PROTOCOLS.length;
			int maxProtocolIndex = 0;

			for (String p : protocols) {
				if (!OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(p)) {
					throw new IllegalArgumentException("Protocol " + p + " is not supported.");
				}

				if (p.equals("SSLv2")) {
					if (minProtocolIndex > 0) {
						minProtocolIndex = 0;
					}

					if (maxProtocolIndex < 0) {
						maxProtocolIndex = 0;
					}
				} else if (p.equals("SSLv3")) {
					if (minProtocolIndex > 1) {
						minProtocolIndex = 1;
					}

					if (maxProtocolIndex < 1) {
						maxProtocolIndex = 1;
					}
				} else if (p.equals("TLSv1")) {
					if (minProtocolIndex > 2) {
						minProtocolIndex = 2;
					}

					if (maxProtocolIndex < 2) {
						maxProtocolIndex = 2;
					}
				} else if (p.equals("TLSv1.1")) {
					if (minProtocolIndex > 3) {
						minProtocolIndex = 3;
					}

					if (maxProtocolIndex < 3) {
						maxProtocolIndex = 3;
					}
				} else if (p.equals("TLSv1.2")) {
					if (minProtocolIndex > 4) {
						minProtocolIndex = 4;
					}

					if (maxProtocolIndex < 4) {
						maxProtocolIndex = 4;
					}
				}
			}

			synchronized (this) {
				if (this.isDestroyed()) {
					throw new IllegalStateException("failed to enable protocols: " + Arrays.asList(protocols));
				} else {
					SSL.clearOptions(this.ssl, SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_NO_TLSv1 | SSL.SSL_OP_NO_TLSv1_1 | SSL.SSL_OP_NO_TLSv1_2);
					int opts = 0;

					for (int i = 0; i < minProtocolIndex; i++) {
						opts |= OPENSSL_OP_NO_PROTOCOLS[i];
					}

					assert maxProtocolIndex != Integer.MAX_VALUE;

					for (int i = maxProtocolIndex + 1; i < OPENSSL_OP_NO_PROTOCOLS.length; i++) {
						opts |= OPENSSL_OP_NO_PROTOCOLS[i];
					}

					SSL.setOptions(this.ssl, opts);
				}
			}
		}
	}

	public final SSLSession getSession() {
		return this.session;
	}

	public final synchronized void beginHandshake() throws SSLException {
		switch (this.handshakeState) {
			case NOT_STARTED:
				this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY;
				this.handshake();
				this.calculateMaxWrapOverhead();
				break;
			case FINISHED:
				throw RENEGOTIATION_UNSUPPORTED;
			case STARTED_IMPLICITLY:
				this.checkEngineClosed(BEGIN_HANDSHAKE_ENGINE_CLOSED);
				this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY;
				this.calculateMaxWrapOverhead();
			case STARTED_EXPLICITLY:
				break;
			default:
				throw new Error();
		}
	}

	private void checkEngineClosed(SSLException cause) throws SSLException {
		if (this.isDestroyed()) {
			throw cause;
		}
	}

	private static HandshakeStatus pendingStatus(int pendingStatus) {
		return pendingStatus > 0 ? HandshakeStatus.NEED_WRAP : HandshakeStatus.NEED_UNWRAP;
	}

	private static boolean isEmpty(Object[] arr) {
		return arr == null || arr.length == 0;
	}

	private static boolean isEmpty(byte[] cert) {
		return cert == null || cert.length == 0;
	}

	private HandshakeStatus handshake() throws SSLException {
		if (this.handshakeState == ReferenceCountedOpenSslEngine.HandshakeState.FINISHED) {
			return HandshakeStatus.FINISHED;
		} else {
			this.checkEngineClosed(HANDSHAKE_ENGINE_CLOSED);
			SSLHandshakeException exception = this.handshakeException;
			if (exception != null) {
				if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
					return HandshakeStatus.NEED_WRAP;
				} else {
					this.handshakeException = null;
					this.shutdown();
					throw exception;
				}
			} else {
				this.engineMap.add(this);
				if (this.lastAccessed == -1L) {
					this.lastAccessed = System.currentTimeMillis();
				}

				if (!this.certificateSet && this.keyMaterialManager != null) {
					this.certificateSet = true;
					this.keyMaterialManager.setKeyMaterial(this);
				}

				int code = SSL.doHandshake(this.ssl);
				if (code <= 0) {
					if (this.handshakeException != null) {
						exception = this.handshakeException;
						this.handshakeException = null;
						this.shutdown();
						throw exception;
					} else {
						int sslError = SSL.getError(this.ssl, code);
						if (sslError != SSL.SSL_ERROR_WANT_READ && sslError != SSL.SSL_ERROR_WANT_WRITE) {
							throw this.shutdownWithError("SSL_do_handshake");
						} else {
							return pendingStatus(SSL.bioLengthNonApplication(this.networkBIO));
						}
					}
				} else {
					this.session.handshakeFinished();
					this.engineMap.remove(this.ssl);
					return HandshakeStatus.FINISHED;
				}
			}
		}
	}

	private HandshakeStatus mayFinishHandshake(HandshakeStatus status) throws SSLException {
		return status == HandshakeStatus.NOT_HANDSHAKING && this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED ? this.handshake() : status;
	}

	public final synchronized HandshakeStatus getHandshakeStatus() {
		return this.needPendingStatus() ? pendingStatus(SSL.bioLengthNonApplication(this.networkBIO)) : HandshakeStatus.NOT_HANDSHAKING;
	}

	private HandshakeStatus getHandshakeStatus(int pending) {
		return this.needPendingStatus() ? pendingStatus(pending) : HandshakeStatus.NOT_HANDSHAKING;
	}

	private boolean needPendingStatus() {
		return this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED
			&& !this.isDestroyed()
			&& (this.handshakeState != ReferenceCountedOpenSslEngine.HandshakeState.FINISHED || this.isInboundDone() || this.isOutboundDone());
	}

	private String toJavaCipherSuite(String openSslCipherSuite) {
		if (openSslCipherSuite == null) {
			return null;
		} else {
			String prefix = toJavaCipherSuitePrefix(SSL.getVersion(this.ssl));
			return CipherSuiteConverter.toJava(openSslCipherSuite, prefix);
		}
	}

	private static String toJavaCipherSuitePrefix(String protocolVersion) {
		char c;
		if (protocolVersion != null && !protocolVersion.isEmpty()) {
			c = protocolVersion.charAt(0);
		} else {
			c = 0;
		}

		switch (c) {
			case 'S':
				return "SSL";
			case 'T':
				return "TLS";
			default:
				return "UNKNOWN";
		}
	}

	public final void setUseClientMode(boolean clientMode) {
		if (clientMode != this.clientMode) {
			throw new UnsupportedOperationException();
		}
	}

	public final boolean getUseClientMode() {
		return this.clientMode;
	}

	public final void setNeedClientAuth(boolean b) {
		this.setClientAuth(b ? ClientAuth.REQUIRE : ClientAuth.NONE);
	}

	public final boolean getNeedClientAuth() {
		return this.clientAuth == ClientAuth.REQUIRE;
	}

	public final void setWantClientAuth(boolean b) {
		this.setClientAuth(b ? ClientAuth.OPTIONAL : ClientAuth.NONE);
	}

	public final boolean getWantClientAuth() {
		return this.clientAuth == ClientAuth.OPTIONAL;
	}

	public final synchronized void setVerify(int verifyMode, int depth) {
		SSL.setVerify(this.ssl, verifyMode, depth);
	}

	private void setClientAuth(ClientAuth mode) {
		if (!this.clientMode) {
			synchronized (this) {
				if (this.clientAuth != mode) {
					switch (mode) {
						case NONE:
							SSL.setVerify(this.ssl, 0, 10);
							break;
						case REQUIRE:
							SSL.setVerify(this.ssl, 2, 10);
							break;
						case OPTIONAL:
							SSL.setVerify(this.ssl, 1, 10);
							break;
						default:
							throw new Error(mode.toString());
					}

					this.clientAuth = mode;
				}
			}
		}
	}

	public final void setEnableSessionCreation(boolean b) {
		if (b) {
			throw new UnsupportedOperationException();
		}
	}

	public final boolean getEnableSessionCreation() {
		return false;
	}

	public final synchronized SSLParameters getSSLParameters() {
		SSLParameters sslParameters = super.getSSLParameters();
		int version = PlatformDependent.javaVersion();
		if (version >= 7) {
			sslParameters.setEndpointIdentificationAlgorithm(this.endPointIdentificationAlgorithm);
			Java7SslParametersUtils.setAlgorithmConstraints(sslParameters, this.algorithmConstraints);
			if (version >= 8) {
				if (this.sniHostNames != null) {
					Java8SslUtils.setSniHostNames(sslParameters, this.sniHostNames);
				}

				if (!this.isDestroyed()) {
					Java8SslUtils.setUseCipherSuitesOrder(sslParameters, (SSL.getOptions(this.ssl) & SSL.SSL_OP_CIPHER_SERVER_PREFERENCE) != 0);
				}

				Java8SslUtils.setSNIMatchers(sslParameters, this.matchers);
			}
		}

		return sslParameters;
	}

	public final synchronized void setSSLParameters(SSLParameters sslParameters) {
		int version = PlatformDependent.javaVersion();
		if (version >= 7) {
			if (sslParameters.getAlgorithmConstraints() != null) {
				throw new IllegalArgumentException("AlgorithmConstraints are not supported.");
			}

			if (version >= 8) {
				if (!this.isDestroyed()) {
					if (this.clientMode) {
						List<String> sniHostNames = Java8SslUtils.getSniHostNames(sslParameters);

						for (String name : sniHostNames) {
							SSL.setTlsExtHostName(this.ssl, name);
						}

						this.sniHostNames = sniHostNames;
					}

					if (Java8SslUtils.getUseCipherSuitesOrder(sslParameters)) {
						SSL.setOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
					} else {
						SSL.clearOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
					}
				}

				this.matchers = sslParameters.getSNIMatchers();
			}

			String endPointIdentificationAlgorithm = sslParameters.getEndpointIdentificationAlgorithm();
			boolean endPointVerificationEnabled = endPointIdentificationAlgorithm != null && !endPointIdentificationAlgorithm.isEmpty();
			SSL.setHostNameValidation(this.ssl, 0, endPointVerificationEnabled ? this.getPeerHost() : null);
			if (this.clientMode && endPointVerificationEnabled) {
				SSL.setVerify(this.ssl, 2, -1);
			}

			this.endPointIdentificationAlgorithm = endPointIdentificationAlgorithm;
			this.algorithmConstraints = sslParameters.getAlgorithmConstraints();
		}

		super.setSSLParameters(sslParameters);
	}

	private boolean isDestroyed() {
		return this.destroyed != 0;
	}

	final boolean checkSniHostnameMatch(String hostname) {
		return Java8SslUtils.checkSniHostnameMatch(this.matchers, hostname);
	}

	@Override
	public String getNegotiatedApplicationProtocol() {
		return this.applicationProtocol;
	}

	private static long bufferAddress(ByteBuffer b) {
		assert b.isDirect();

		return PlatformDependent.hasUnsafe() ? PlatformDependent.directBufferAddress(b) : Buffer.address(b);
	}

	private static enum HandshakeState {
		NOT_STARTED,
		STARTED_IMPLICITLY,
		STARTED_EXPLICITLY,
		FINISHED;
	}

	private final class OpenSslSession implements SSLSession {
		private final OpenSslSessionContext sessionContext;
		private X509Certificate[] x509PeerCerts;
		private Certificate[] peerCerts;
		private String protocol;
		private String cipher;
		private byte[] id;
		private long creationTime;
		private volatile int applicationBufferSize = ReferenceCountedOpenSslEngine.MAX_PLAINTEXT_LENGTH;
		private Map<String, Object> values;

		OpenSslSession(OpenSslSessionContext sessionContext) {
			this.sessionContext = sessionContext;
		}

		public byte[] getId() {
			synchronized (ReferenceCountedOpenSslEngine.this) {
				return this.id == null ? EmptyArrays.EMPTY_BYTES : (byte[])this.id.clone();
			}
		}

		public SSLSessionContext getSessionContext() {
			return this.sessionContext;
		}

		public long getCreationTime() {
			synchronized (ReferenceCountedOpenSslEngine.this) {
				if (this.creationTime == 0L && !ReferenceCountedOpenSslEngine.this.isDestroyed()) {
					this.creationTime = SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000L;
				}
			}

			return this.creationTime;
		}

		public long getLastAccessedTime() {
			long lastAccessed = ReferenceCountedOpenSslEngine.this.lastAccessed;
			return lastAccessed == -1L ? this.getCreationTime() : lastAccessed;
		}

		public void invalidate() {
			synchronized (ReferenceCountedOpenSslEngine.this) {
				if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
					SSL.setTimeout(ReferenceCountedOpenSslEngine.this.ssl, 0L);
				}
			}
		}

		public boolean isValid() {
			synchronized (ReferenceCountedOpenSslEngine.this) {
				return !ReferenceCountedOpenSslEngine.this.isDestroyed()
					? System.currentTimeMillis() - SSL.getTimeout(ReferenceCountedOpenSslEngine.this.ssl) * 1000L
						< SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000L
					: false;
			}
		}

		public void putValue(String name, Object value) {
			if (name == null) {
				throw new NullPointerException("name");
			} else if (value == null) {
				throw new NullPointerException("value");
			} else {
				Map<String, Object> values = this.values;
				if (values == null) {
					values = this.values = new HashMap(2);
				}

				Object old = values.put(name, value);
				if (value instanceof SSLSessionBindingListener) {
					((SSLSessionBindingListener)value).valueBound(new SSLSessionBindingEvent(this, name));
				}

				this.notifyUnbound(old, name);
			}
		}

		public Object getValue(String name) {
			if (name == null) {
				throw new NullPointerException("name");
			} else {
				return this.values == null ? null : this.values.get(name);
			}
		}

		public void removeValue(String name) {
			if (name == null) {
				throw new NullPointerException("name");
			} else {
				Map<String, Object> values = this.values;
				if (values != null) {
					Object old = values.remove(name);
					this.notifyUnbound(old, name);
				}
			}
		}

		public String[] getValueNames() {
			Map<String, Object> values = this.values;
			return values != null && !values.isEmpty() ? (String[])values.keySet().toArray(new String[values.size()]) : EmptyArrays.EMPTY_STRINGS;
		}

		private void notifyUnbound(Object value, String name) {
			if (value instanceof SSLSessionBindingListener) {
				((SSLSessionBindingListener)value).valueUnbound(new SSLSessionBindingEvent(this, name));
			}
		}

		void handshakeFinished() throws SSLException {
			synchronized (ReferenceCountedOpenSslEngine.this) {
				if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
					this.id = SSL.getSessionId(ReferenceCountedOpenSslEngine.this.ssl);
					this.cipher = ReferenceCountedOpenSslEngine.this.toJavaCipherSuite(SSL.getCipherForSSL(ReferenceCountedOpenSslEngine.this.ssl));
					this.protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
					this.initPeerCerts();
					this.selectApplicationProtocol();
					ReferenceCountedOpenSslEngine.this.calculateMaxWrapOverhead();
					ReferenceCountedOpenSslEngine.this.handshakeState = ReferenceCountedOpenSslEngine.HandshakeState.FINISHED;
				} else {
					throw new SSLException("Already closed");
				}
			}
		}

		private void initPeerCerts() {
			byte[][] chain = SSL.getPeerCertChain(ReferenceCountedOpenSslEngine.this.ssl);
			if (ReferenceCountedOpenSslEngine.this.clientMode) {
				if (ReferenceCountedOpenSslEngine.isEmpty(chain)) {
					this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
					this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
				} else {
					this.peerCerts = new Certificate[chain.length];
					this.x509PeerCerts = new X509Certificate[chain.length];
					this.initCerts(chain, 0);
				}
			} else {
				byte[] clientCert = SSL.getPeerCertificate(ReferenceCountedOpenSslEngine.this.ssl);
				if (ReferenceCountedOpenSslEngine.isEmpty(clientCert)) {
					this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
					this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
				} else if (ReferenceCountedOpenSslEngine.isEmpty(chain)) {
					this.peerCerts = new Certificate[]{new OpenSslX509Certificate(clientCert)};
					this.x509PeerCerts = new X509Certificate[]{new OpenSslJavaxX509Certificate(clientCert)};
				} else {
					this.peerCerts = new Certificate[chain.length + 1];
					this.x509PeerCerts = new X509Certificate[chain.length + 1];
					this.peerCerts[0] = new OpenSslX509Certificate(clientCert);
					this.x509PeerCerts[0] = new OpenSslJavaxX509Certificate(clientCert);
					this.initCerts(chain, 1);
				}
			}
		}

		private void initCerts(byte[][] chain, int startPos) {
			for (int i = 0; i < chain.length; i++) {
				int certPos = startPos + i;
				this.peerCerts[certPos] = new OpenSslX509Certificate(chain[i]);
				this.x509PeerCerts[certPos] = new OpenSslJavaxX509Certificate(chain[i]);
			}
		}

		private void selectApplicationProtocol() throws SSLException {
			SelectedListenerFailureBehavior behavior = ReferenceCountedOpenSslEngine.this.apn.selectedListenerFailureBehavior();
			List<String> protocols = ReferenceCountedOpenSslEngine.this.apn.protocols();
			switch (ReferenceCountedOpenSslEngine.this.apn.protocol()) {
				case NONE:
					break;
				case ALPN:
					String applicationProtocolxx = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
					if (applicationProtocolxx != null) {
						ReferenceCountedOpenSslEngine.this.applicationProtocol = this.selectApplicationProtocol(protocols, behavior, applicationProtocolxx);
					}
					break;
				case NPN:
					String applicationProtocolx = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
					if (applicationProtocolx != null) {
						ReferenceCountedOpenSslEngine.this.applicationProtocol = this.selectApplicationProtocol(protocols, behavior, applicationProtocolx);
					}
					break;
				case NPN_AND_ALPN:
					String applicationProtocol = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
					if (applicationProtocol == null) {
						applicationProtocol = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
					}

					if (applicationProtocol != null) {
						ReferenceCountedOpenSslEngine.this.applicationProtocol = this.selectApplicationProtocol(protocols, behavior, applicationProtocol);
					}
					break;
				default:
					throw new Error();
			}
		}

		private String selectApplicationProtocol(List<String> protocols, SelectedListenerFailureBehavior behavior, String applicationProtocol) throws SSLException {
			if (behavior == SelectedListenerFailureBehavior.ACCEPT) {
				return applicationProtocol;
			} else {
				int size = protocols.size();

				assert size > 0;

				if (protocols.contains(applicationProtocol)) {
					return applicationProtocol;
				} else if (behavior == SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
					return (String)protocols.get(size - 1);
				} else {
					throw new SSLException("unknown protocol " + applicationProtocol);
				}
			}
		}

		public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
			synchronized (ReferenceCountedOpenSslEngine.this) {
				if (ReferenceCountedOpenSslEngine.isEmpty(this.peerCerts)) {
					throw new SSLPeerUnverifiedException("peer not verified");
				} else {
					return (Certificate[])this.peerCerts.clone();
				}
			}
		}

		public Certificate[] getLocalCertificates() {
			return ReferenceCountedOpenSslEngine.this.localCerts == null ? null : (Certificate[])ReferenceCountedOpenSslEngine.this.localCerts.clone();
		}

		public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
			synchronized (ReferenceCountedOpenSslEngine.this) {
				if (ReferenceCountedOpenSslEngine.isEmpty(this.x509PeerCerts)) {
					throw new SSLPeerUnverifiedException("peer not verified");
				} else {
					return (X509Certificate[])this.x509PeerCerts.clone();
				}
			}
		}

		public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
			Certificate[] peer = this.getPeerCertificates();
			return ((java.security.cert.X509Certificate)peer[0]).getSubjectX500Principal();
		}

		public Principal getLocalPrincipal() {
			Certificate[] local = ReferenceCountedOpenSslEngine.this.localCerts;
			return local != null && local.length != 0 ? ((java.security.cert.X509Certificate)local[0]).getIssuerX500Principal() : null;
		}

		public String getCipherSuite() {
			synchronized (ReferenceCountedOpenSslEngine.this) {
				return this.cipher == null ? "SSL_NULL_WITH_NULL_NULL" : this.cipher;
			}
		}

		public String getProtocol() {
			String protocol = this.protocol;
			if (protocol == null) {
				synchronized (ReferenceCountedOpenSslEngine.this) {
					if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
						protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
					} else {
						protocol = "";
					}
				}
			}

			return protocol;
		}

		public String getPeerHost() {
			return ReferenceCountedOpenSslEngine.this.getPeerHost();
		}

		public int getPeerPort() {
			return ReferenceCountedOpenSslEngine.this.getPeerPort();
		}

		public int getPacketBufferSize() {
			return ReferenceCountedOpenSslEngine.this.maxEncryptedPacketLength();
		}

		public int getApplicationBufferSize() {
			return this.applicationBufferSize;
		}

		void tryExpandApplicationBufferSize(int packetLengthDataOnly) {
			if (packetLengthDataOnly > ReferenceCountedOpenSslEngine.MAX_PLAINTEXT_LENGTH && this.applicationBufferSize != ReferenceCountedOpenSslEngine.MAX_RECORD_SIZE
				)
			 {
				this.applicationBufferSize = ReferenceCountedOpenSslEngine.MAX_RECORD_SIZE;
			}
		}
	}
}
