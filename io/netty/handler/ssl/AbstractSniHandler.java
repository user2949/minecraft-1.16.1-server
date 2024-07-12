package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.util.List;
import java.util.Locale;

public abstract class AbstractSniHandler<T> extends ByteToMessageDecoder implements ChannelOutboundHandler {
	private static final int MAX_SSL_RECORDS = 4;
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractSniHandler.class);
	private boolean handshakeFailed;
	private boolean suppressRead;
	private boolean readPending;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (!this.suppressRead && !this.handshakeFailed) {
			int writerIndex = in.writerIndex();

			try {
				int i = 0;

				label96:
				while (i < 4) {
					int readerIndex = in.readerIndex();
					int readableBytes = writerIndex - readerIndex;
					if (readableBytes < 5) {
						return;
					}

					int command = in.getUnsignedByte(readerIndex);
					switch (command) {
						case 20:
						case 21:
							int len = SslUtils.getEncryptedPacketLength(in, readerIndex);
							if (len == -2) {
								this.handshakeFailed = true;
								NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
								in.skipBytes(in.readableBytes());
								ctx.fireUserEventTriggered(new SniCompletionEvent(e));
								SslUtils.handleHandshakeFailure(ctx, e, true);
								throw e;
							}

							if (len == -1 || writerIndex - readerIndex - 5 < len) {
								return;
							}

							in.skipBytes(len);
							i++;
							break;
						case 22:
							int majorVersion = in.getUnsignedByte(readerIndex + 1);
							if (majorVersion == 3) {
								int packetLength = in.getUnsignedShort(readerIndex + 3) + 5;
								if (readableBytes < packetLength) {
									return;
								}

								int endOffset = readerIndex + packetLength;
								int offset = readerIndex + 43;
								if (endOffset - offset >= 6) {
									int sessionIdLength = in.getUnsignedByte(offset);
									offset += sessionIdLength + 1;
									int cipherSuitesLength = in.getUnsignedShort(offset);
									offset += cipherSuitesLength + 2;
									int compressionMethodLength = in.getUnsignedByte(offset);
									offset += compressionMethodLength + 1;
									int extensionsLength = in.getUnsignedShort(offset);
									offset += 2;
									int extensionsLimit = offset + extensionsLength;
									if (extensionsLimit <= endOffset) {
										while (extensionsLimit - offset >= 4) {
											int extensionType = in.getUnsignedShort(offset);
											offset += 2;
											int extensionLength = in.getUnsignedShort(offset);
											offset += 2;
											if (extensionsLimit - offset < extensionLength) {
												break label96;
											}

											if (extensionType == 0) {
												offset += 2;
												if (extensionsLimit - offset >= 3) {
													int serverNameType = in.getUnsignedByte(offset);
													offset++;
													if (serverNameType == 0) {
														int serverNameLength = in.getUnsignedShort(offset);
														offset += 2;
														if (extensionsLimit - offset >= serverNameLength) {
															String hostname = in.toString(offset, serverNameLength, CharsetUtil.US_ASCII);

															try {
																this.select(ctx, hostname.toLowerCase(Locale.US));
															} catch (Throwable var25) {
																PlatformDependent.throwException(var25);
															}

															return;
														}
													}
												}
												break label96;
											}

											offset += extensionLength;
										}
									}
								}
							}
						default:
							break label96;
					}
				}
			} catch (NotSslRecordException var26) {
				throw var26;
			} catch (Exception var27) {
				if (logger.isDebugEnabled()) {
					logger.debug("Unexpected client hello packet: " + ByteBufUtil.hexDump(in), (Throwable)var27);
				}
			}

			this.select(ctx, null);
		}
	}

	private void select(ChannelHandlerContext ctx, String hostname) throws Exception {
		Future<T> future = this.lookup(ctx, hostname);
		if (future.isDone()) {
			this.fireSniCompletionEvent(ctx, hostname, future);
			this.onLookupComplete(ctx, hostname, future);
		} else {
			this.suppressRead = true;
			future.addListener(new FutureListener<T>() {
				@Override
				public void operationComplete(Future<T> future) throws Exception {
					try {
						AbstractSniHandler.this.suppressRead = false;

						try {
							AbstractSniHandler.this.fireSniCompletionEvent(ctx, hostname, future);
							AbstractSniHandler.this.onLookupComplete(ctx, hostname, future);
						} catch (DecoderException var8) {
							ctx.fireExceptionCaught(var8);
						} catch (Exception var9) {
							ctx.fireExceptionCaught(new DecoderException(var9));
						} catch (Throwable var10) {
							ctx.fireExceptionCaught(var10);
						}
					} finally {
						if (AbstractSniHandler.this.readPending) {
							AbstractSniHandler.this.readPending = false;
							ctx.read();
						}
					}
				}
			});
		}
	}

	private void fireSniCompletionEvent(ChannelHandlerContext ctx, String hostname, Future<T> future) {
		Throwable cause = future.cause();
		if (cause == null) {
			ctx.fireUserEventTriggered(new SniCompletionEvent(hostname));
		} else {
			ctx.fireUserEventTriggered(new SniCompletionEvent(hostname, cause));
		}
	}

	protected abstract Future<T> lookup(ChannelHandlerContext channelHandlerContext, String string) throws Exception;

	protected abstract void onLookupComplete(ChannelHandlerContext channelHandlerContext, String string, Future<T> future) throws Exception;

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		if (this.suppressRead) {
			this.readPending = true;
		} else {
			ctx.read();
		}
	}

	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		ctx.bind(localAddress, promise);
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		ctx.connect(remoteAddress, localAddress, promise);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.disconnect(promise);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.close(promise);
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		ctx.deregister(promise);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ctx.write(msg, promise);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
