package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.internal.tcnative.CertificateVerifier;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateRevokedException;
import java.security.cert.X509Certificate;
import java.security.cert.CertPathValidatorException.BasicReason;
import java.security.cert.CertPathValidatorException.Reason;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

public abstract class ReferenceCountedOpenSslContext extends SslContext implements ReferenceCounted {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslContext.class);
	private static final int DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE = (Integer)AccessController.doPrivileged(new PrivilegedAction<Integer>() {
		public Integer run() {
			return Math.max(1, SystemPropertyUtil.getInt("io.netty.handler.ssl.openssl.bioNonApplicationBufferSize", 2048));
		}
	});
	private static final Integer DH_KEY_LENGTH;
	private static final ResourceLeakDetector<ReferenceCountedOpenSslContext> leakDetector = ResourceLeakDetectorFactory.instance()
		.newResourceLeakDetector(ReferenceCountedOpenSslContext.class);
	protected static final int VERIFY_DEPTH = 10;
	protected long ctx;
	private final List<String> unmodifiableCiphers;
	private final long sessionCacheSize;
	private final long sessionTimeout;
	private final OpenSslApplicationProtocolNegotiator apn;
	private final int mode;
	private final ResourceLeakTracker<ReferenceCountedOpenSslContext> leak;
	private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted() {
		@Override
		public ReferenceCounted touch(Object hint) {
			if (ReferenceCountedOpenSslContext.this.leak != null) {
				ReferenceCountedOpenSslContext.this.leak.record(hint);
			}

			return ReferenceCountedOpenSslContext.this;
		}

		@Override
		protected void deallocate() {
			ReferenceCountedOpenSslContext.this.destroy();
			if (ReferenceCountedOpenSslContext.this.leak != null) {
				boolean closed = ReferenceCountedOpenSslContext.this.leak.close(ReferenceCountedOpenSslContext.this);

				assert closed;
			}
		}
	};
	final Certificate[] keyCertChain;
	final ClientAuth clientAuth;
	final String[] protocols;
	final boolean enableOcsp;
	final OpenSslEngineMap engineMap = new ReferenceCountedOpenSslContext.DefaultOpenSslEngineMap();
	final ReadWriteLock ctxLock = new ReentrantReadWriteLock();
	private volatile int bioNonApplicationBufferSize = DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE;
	static final OpenSslApplicationProtocolNegotiator NONE_PROTOCOL_NEGOTIATOR = new OpenSslApplicationProtocolNegotiator() {
		@Override
		public Protocol protocol() {
			return Protocol.NONE;
		}

		@Override
		public List<String> protocols() {
			return Collections.emptyList();
		}

		@Override
		public SelectorFailureBehavior selectorFailureBehavior() {
			return SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
		}

		@Override
		public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
			return SelectedListenerFailureBehavior.ACCEPT;
		}
	};

	ReferenceCountedOpenSslContext(
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		ApplicationProtocolConfig apnCfg,
		long sessionCacheSize,
		long sessionTimeout,
		int mode,
		Certificate[] keyCertChain,
		ClientAuth clientAuth,
		String[] protocols,
		boolean startTls,
		boolean enableOcsp,
		boolean leakDetection
	) throws SSLException {
		this(
			ciphers,
			cipherFilter,
			toNegotiator(apnCfg),
			sessionCacheSize,
			sessionTimeout,
			mode,
			keyCertChain,
			clientAuth,
			protocols,
			startTls,
			enableOcsp,
			leakDetection
		);
	}

	ReferenceCountedOpenSslContext(
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		OpenSslApplicationProtocolNegotiator apn,
		long sessionCacheSize,
		long sessionTimeout,
		int mode,
		Certificate[] keyCertChain,
		ClientAuth clientAuth,
		String[] protocols,
		boolean startTls,
		boolean enableOcsp,
		boolean leakDetection
	) throws SSLException {
		super(startTls);
		OpenSsl.ensureAvailability();
		if (enableOcsp && !OpenSsl.isOcspSupported()) {
			throw new IllegalStateException("OCSP is not supported.");
		} else if (mode != 1 && mode != 0) {
			throw new IllegalArgumentException("mode most be either SSL.SSL_MODE_SERVER or SSL.SSL_MODE_CLIENT");
		} else {
			this.leak = leakDetection ? leakDetector.track(this) : null;
			this.mode = mode;
			this.clientAuth = this.isServer() ? ObjectUtil.checkNotNull(clientAuth, "clientAuth") : ClientAuth.NONE;
			this.protocols = protocols;
			this.enableOcsp = enableOcsp;
			this.keyCertChain = keyCertChain == null ? null : (Certificate[])keyCertChain.clone();
			this.unmodifiableCiphers = Arrays.asList(
				ObjectUtil.checkNotNull(cipherFilter, "cipherFilter").filterCipherSuites(ciphers, OpenSsl.DEFAULT_CIPHERS, OpenSsl.availableJavaCipherSuites())
			);
			this.apn = ObjectUtil.checkNotNull(apn, "apn");
			boolean success = false;

			try {
				try {
					this.ctx = SSLContext.make(31, mode);
				} catch (Exception var26) {
					throw new SSLException("failed to create an SSL_CTX", var26);
				}

				SSLContext.setOptions(
					this.ctx,
					SSLContext.getOptions(this.ctx)
						| SSL.SSL_OP_NO_SSLv2
						| SSL.SSL_OP_NO_SSLv3
						| SSL.SSL_OP_CIPHER_SERVER_PREFERENCE
						| SSL.SSL_OP_NO_COMPRESSION
						| SSL.SSL_OP_NO_TICKET
				);
				SSLContext.setMode(this.ctx, SSLContext.getMode(this.ctx) | SSL.SSL_MODE_ACCEPT_MOVING_WRITE_BUFFER);
				if (DH_KEY_LENGTH != null) {
					SSLContext.setTmpDHLength(this.ctx, DH_KEY_LENGTH);
				}

				try {
					SSLContext.setCipherSuite(this.ctx, CipherSuiteConverter.toOpenSsl(this.unmodifiableCiphers));
				} catch (SSLException var24) {
					throw var24;
				} catch (Exception var25) {
					throw new SSLException("failed to set cipher suite: " + this.unmodifiableCiphers, var25);
				}

				List<String> nextProtoList = apn.protocols();
				if (!nextProtoList.isEmpty()) {
					String[] appProtocols = (String[])nextProtoList.toArray(new String[nextProtoList.size()]);
					int selectorBehavior = opensslSelectorFailureBehavior(apn.selectorFailureBehavior());
					switch (apn.protocol()) {
						case NPN:
							SSLContext.setNpnProtos(this.ctx, appProtocols, selectorBehavior);
							break;
						case ALPN:
							SSLContext.setAlpnProtos(this.ctx, appProtocols, selectorBehavior);
							break;
						case NPN_AND_ALPN:
							SSLContext.setNpnProtos(this.ctx, appProtocols, selectorBehavior);
							SSLContext.setAlpnProtos(this.ctx, appProtocols, selectorBehavior);
							break;
						default:
							throw new Error();
					}
				}

				if (sessionCacheSize <= 0L) {
					sessionCacheSize = SSLContext.setSessionCacheSize(this.ctx, 20480L);
				}

				this.sessionCacheSize = sessionCacheSize;
				SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
				if (sessionTimeout <= 0L) {
					sessionTimeout = SSLContext.setSessionCacheTimeout(this.ctx, 300L);
				}

				this.sessionTimeout = sessionTimeout;
				SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
				if (enableOcsp) {
					SSLContext.enableOcsp(this.ctx, this.isClient());
				}

				success = true;
			} finally {
				if (!success) {
					this.release();
				}
			}
		}
	}

	private static int opensslSelectorFailureBehavior(SelectorFailureBehavior behavior) {
		switch (behavior) {
			case NO_ADVERTISE:
				return 0;
			case CHOOSE_MY_LAST_PROTOCOL:
				return 1;
			default:
				throw new Error();
		}
	}

	@Override
	public final List<String> cipherSuites() {
		return this.unmodifiableCiphers;
	}

	@Override
	public final long sessionCacheSize() {
		return this.sessionCacheSize;
	}

	@Override
	public final long sessionTimeout() {
		return this.sessionTimeout;
	}

	@Override
	public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
		return this.apn;
	}

	@Override
	public final boolean isClient() {
		return this.mode == 0;
	}

	@Override
	public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
		return this.newEngine0(alloc, peerHost, peerPort, true);
	}

	@Override
	protected final SslHandler newHandler(ByteBufAllocator alloc, boolean startTls) {
		return new SslHandler(this.newEngine0(alloc, null, -1, false), startTls);
	}

	@Override
	protected final SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, boolean startTls) {
		return new SslHandler(this.newEngine0(alloc, peerHost, peerPort, false), startTls);
	}

	SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort, boolean jdkCompatibilityMode) {
		return new ReferenceCountedOpenSslEngine(this, alloc, peerHost, peerPort, jdkCompatibilityMode, true);
	}

	abstract OpenSslKeyMaterialManager keyMaterialManager();

	@Override
	public final SSLEngine newEngine(ByteBufAllocator alloc) {
		return this.newEngine(alloc, null, -1);
	}

	@Deprecated
	public final long context() {
		Lock readerLock = this.ctxLock.readLock();
		readerLock.lock();

		long var2;
		try {
			var2 = this.ctx;
		} finally {
			readerLock.unlock();
		}

		return var2;
	}

	@Deprecated
	public final OpenSslSessionStats stats() {
		return this.sessionContext().stats();
	}

	@Deprecated
	public void setRejectRemoteInitiatedRenegotiation(boolean rejectRemoteInitiatedRenegotiation) {
		if (!rejectRemoteInitiatedRenegotiation) {
			throw new UnsupportedOperationException("Renegotiation is not supported");
		}
	}

	@Deprecated
	public boolean getRejectRemoteInitiatedRenegotiation() {
		return true;
	}

	public void setBioNonApplicationBufferSize(int bioNonApplicationBufferSize) {
		this.bioNonApplicationBufferSize = ObjectUtil.checkPositiveOrZero(bioNonApplicationBufferSize, "bioNonApplicationBufferSize");
	}

	public int getBioNonApplicationBufferSize() {
		return this.bioNonApplicationBufferSize;
	}

	@Deprecated
	public final void setTicketKeys(byte[] keys) {
		this.sessionContext().setTicketKeys(keys);
	}

	public abstract OpenSslSessionContext sessionContext();

	@Deprecated
	public final long sslCtxPointer() {
		Lock readerLock = this.ctxLock.readLock();
		readerLock.lock();

		long var2;
		try {
			var2 = this.ctx;
		} finally {
			readerLock.unlock();
		}

		return var2;
	}

	private void destroy() {
		Lock writerLock = this.ctxLock.writeLock();
		writerLock.lock();

		try {
			if (this.ctx != 0L) {
				if (this.enableOcsp) {
					SSLContext.disableOcsp(this.ctx);
				}

				SSLContext.free(this.ctx);
				this.ctx = 0L;
			}
		} finally {
			writerLock.unlock();
		}
	}

	protected static X509Certificate[] certificates(byte[][] chain) {
		X509Certificate[] peerCerts = new X509Certificate[chain.length];

		for (int i = 0; i < peerCerts.length; i++) {
			peerCerts[i] = new OpenSslX509Certificate(chain[i]);
		}

		return peerCerts;
	}

	protected static X509TrustManager chooseTrustManager(TrustManager[] managers) {
		for (TrustManager m : managers) {
			if (m instanceof X509TrustManager) {
				return (X509TrustManager)m;
			}
		}

		throw new IllegalStateException("no X509TrustManager found");
	}

	protected static X509KeyManager chooseX509KeyManager(KeyManager[] kms) {
		for (KeyManager km : kms) {
			if (km instanceof X509KeyManager) {
				return (X509KeyManager)km;
			}
		}

		throw new IllegalStateException("no X509KeyManager found");
	}

	static OpenSslApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config) {
		if (config == null) {
			return NONE_PROTOCOL_NEGOTIATOR;
		} else {
			switch (config.protocol()) {
				case NPN:
				case ALPN:
				case NPN_AND_ALPN:
					switch (config.selectedListenerFailureBehavior()) {
						case CHOOSE_MY_LAST_PROTOCOL:
						case ACCEPT:
							switch (config.selectorFailureBehavior()) {
								case NO_ADVERTISE:
								case CHOOSE_MY_LAST_PROTOCOL:
									return new OpenSslDefaultApplicationProtocolNegotiator(config);
								default:
									throw new UnsupportedOperationException("OpenSSL provider does not support " + config.selectorFailureBehavior() + " behavior");
							}
						default:
							throw new UnsupportedOperationException("OpenSSL provider does not support " + config.selectedListenerFailureBehavior() + " behavior");
					}
				case NONE:
					return NONE_PROTOCOL_NEGOTIATOR;
				default:
					throw new Error();
			}
		}
	}

	static boolean useExtendedTrustManager(X509TrustManager trustManager) {
		return PlatformDependent.javaVersion() >= 7 && trustManager instanceof X509ExtendedTrustManager;
	}

	static boolean useExtendedKeyManager(X509KeyManager keyManager) {
		return PlatformDependent.javaVersion() >= 7 && keyManager instanceof X509ExtendedKeyManager;
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

	static void setKeyMaterial(long ctx, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword) throws SSLException {
		long keyBio = 0L;
		long keyCertChainBio = 0L;
		long keyCertChainBio2 = 0L;
		PemEncoded encoded = null;

		try {
			encoded = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, keyCertChain);
			keyCertChainBio = toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
			keyCertChainBio2 = toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
			if (key != null) {
				keyBio = toBIO(key);
			}

			SSLContext.setCertificateBio(ctx, keyCertChainBio, keyBio, keyPassword == null ? "" : keyPassword);
			SSLContext.setCertificateChainBio(ctx, keyCertChainBio2, true);
		} catch (SSLException var17) {
			throw var17;
		} catch (Exception var18) {
			throw new SSLException("failed to set certificate and key", var18);
		} finally {
			freeBio(keyBio);
			freeBio(keyCertChainBio);
			freeBio(keyCertChainBio2);
			if (encoded != null) {
				encoded.release();
			}
		}
	}

	static void freeBio(long bio) {
		if (bio != 0L) {
			SSL.freeBIO(bio);
		}
	}

	static long toBIO(PrivateKey key) throws Exception {
		if (key == null) {
			return 0L;
		} else {
			ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
			PemEncoded pem = PemPrivateKey.toPEM(allocator, true, key);

			long var3;
			try {
				var3 = toBIO(allocator, pem.retain());
			} finally {
				pem.release();
			}

			return var3;
		}
	}

	static long toBIO(X509Certificate... certChain) throws Exception {
		if (certChain == null) {
			return 0L;
		} else if (certChain.length == 0) {
			throw new IllegalArgumentException("certChain can't be empty");
		} else {
			ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
			PemEncoded pem = PemX509Certificate.toPEM(allocator, true, certChain);

			long var3;
			try {
				var3 = toBIO(allocator, pem.retain());
			} finally {
				pem.release();
			}

			return var3;
		}
	}

	static long toBIO(ByteBufAllocator allocator, PemEncoded pem) throws Exception {
		long var4;
		try {
			ByteBuf content = pem.content();
			if (content.isDirect()) {
				return newBIO(content.retainedSlice());
			}

			ByteBuf buffer = allocator.directBuffer(content.readableBytes());

			try {
				buffer.writeBytes(content, content.readerIndex(), content.readableBytes());
				var4 = newBIO(buffer.retainedSlice());
			} finally {
				try {
					if (pem.isSensitive()) {
						SslUtils.zeroout(buffer);
					}
				} finally {
					buffer.release();
				}
			}
		} finally {
			pem.release();
		}

		return var4;
	}

	private static long newBIO(ByteBuf buffer) throws Exception {
		long var4;
		try {
			long bio = SSL.newMemBIO();
			int readable = buffer.readableBytes();
			if (SSL.bioWrite(bio, OpenSsl.memoryAddress(buffer) + (long)buffer.readerIndex(), readable) != readable) {
				SSL.freeBIO(bio);
				throw new IllegalStateException("Could not write data to memory BIO");
			}

			var4 = bio;
		} finally {
			buffer.release();
		}

		return var4;
	}

	static {
		Integer dhLen = null;

		try {
			String dhKeySize = (String)AccessController.doPrivileged(new PrivilegedAction<String>() {
				public String run() {
					return SystemPropertyUtil.get("jdk.tls.ephemeralDHKeySize");
				}
			});
			if (dhKeySize != null) {
				try {
					dhLen = Integer.valueOf(dhKeySize);
				} catch (NumberFormatException var3) {
					logger.debug("ReferenceCountedOpenSslContext supports -Djdk.tls.ephemeralDHKeySize={int}, but got: " + dhKeySize);
				}
			}
		} catch (Throwable var4) {
		}

		DH_KEY_LENGTH = dhLen;
	}

	abstract static class AbstractCertificateVerifier extends CertificateVerifier {
		private final OpenSslEngineMap engineMap;

		AbstractCertificateVerifier(OpenSslEngineMap engineMap) {
			this.engineMap = engineMap;
		}

		public final int verify(long ssl, byte[][] chain, String auth) {
			X509Certificate[] peerCerts = ReferenceCountedOpenSslContext.certificates(chain);
			ReferenceCountedOpenSslEngine engine = this.engineMap.get(ssl);

			try {
				this.verify(engine, peerCerts, auth);
				return CertificateVerifier.X509_V_OK;
			} catch (Throwable var12) {
				ReferenceCountedOpenSslContext.logger.debug("verification of certificate failed", var12);
				SSLHandshakeException e = new SSLHandshakeException("General OpenSslEngine problem");
				e.initCause(var12);
				engine.handshakeException = e;
				if (var12 instanceof OpenSslCertificateException) {
					return ((OpenSslCertificateException)var12).errorCode();
				} else if (var12 instanceof CertificateExpiredException) {
					return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
				} else if (var12 instanceof CertificateNotYetValidException) {
					return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
				} else {
					if (PlatformDependent.javaVersion() >= 7) {
						if (var12 instanceof CertificateRevokedException) {
							return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
						}

						for (Throwable wrapped = var12.getCause(); wrapped != null; wrapped = wrapped.getCause()) {
							if (wrapped instanceof CertPathValidatorException) {
								CertPathValidatorException ex = (CertPathValidatorException)wrapped;
								Reason reason = ex.getReason();
								if (reason == BasicReason.EXPIRED) {
									return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
								}

								if (reason == BasicReason.NOT_YET_VALID) {
									return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
								}

								if (reason == BasicReason.REVOKED) {
									return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
								}
							}
						}
					}

					return CertificateVerifier.X509_V_ERR_UNSPECIFIED;
				}
			}
		}

		abstract void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] arr, String string) throws Exception;
	}

	private static final class DefaultOpenSslEngineMap implements OpenSslEngineMap {
		private final Map<Long, ReferenceCountedOpenSslEngine> engines = PlatformDependent.<Long, ReferenceCountedOpenSslEngine>newConcurrentHashMap();

		private DefaultOpenSslEngineMap() {
		}

		@Override
		public ReferenceCountedOpenSslEngine remove(long ssl) {
			return (ReferenceCountedOpenSslEngine)this.engines.remove(ssl);
		}

		@Override
		public void add(ReferenceCountedOpenSslEngine engine) {
			this.engines.put(engine.sslPointer(), engine);
		}

		@Override
		public ReferenceCountedOpenSslEngine get(long ssl) {
			return (ReferenceCountedOpenSslEngine)this.engines.get(ssl);
		}
	}
}
