package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel.Unsafe;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.socket.ChannelOutputShutdownEvent;
import io.netty.channel.socket.ChannelOutputShutdownException;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public abstract class AbstractChannel extends DefaultAttributeMap implements Channel {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
	private static final ClosedChannelException FLUSH0_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), AbstractChannel.AbstractUnsafe.class, "flush0()"
	);
	private static final ClosedChannelException ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), AbstractChannel.AbstractUnsafe.class, "ensureOpen(...)"
	);
	private static final ClosedChannelException CLOSE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), AbstractChannel.AbstractUnsafe.class, "close(...)"
	);
	private static final ClosedChannelException WRITE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), AbstractChannel.AbstractUnsafe.class, "write(...)"
	);
	private static final NotYetConnectedException FLUSH0_NOT_YET_CONNECTED_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new NotYetConnectedException(), AbstractChannel.AbstractUnsafe.class, "flush0()"
	);
	private final Channel parent;
	private final ChannelId id;
	private final Unsafe unsafe;
	private final DefaultChannelPipeline pipeline;
	private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);
	private final AbstractChannel.CloseFuture closeFuture = new AbstractChannel.CloseFuture(this);
	private volatile SocketAddress localAddress;
	private volatile SocketAddress remoteAddress;
	private volatile EventLoop eventLoop;
	private volatile boolean registered;
	private boolean closeInitiated;
	private boolean strValActive;
	private String strVal;

	protected AbstractChannel(Channel parent) {
		this.parent = parent;
		this.id = this.newId();
		this.unsafe = this.newUnsafe();
		this.pipeline = this.newChannelPipeline();
	}

	protected AbstractChannel(Channel parent, ChannelId id) {
		this.parent = parent;
		this.id = id;
		this.unsafe = this.newUnsafe();
		this.pipeline = this.newChannelPipeline();
	}

	@Override
	public final ChannelId id() {
		return this.id;
	}

	protected ChannelId newId() {
		return DefaultChannelId.newInstance();
	}

	protected DefaultChannelPipeline newChannelPipeline() {
		return new DefaultChannelPipeline(this);
	}

	@Override
	public boolean isWritable() {
		ChannelOutboundBuffer buf = this.unsafe.outboundBuffer();
		return buf != null && buf.isWritable();
	}

	@Override
	public long bytesBeforeUnwritable() {
		ChannelOutboundBuffer buf = this.unsafe.outboundBuffer();
		return buf != null ? buf.bytesBeforeUnwritable() : 0L;
	}

	@Override
	public long bytesBeforeWritable() {
		ChannelOutboundBuffer buf = this.unsafe.outboundBuffer();
		return buf != null ? buf.bytesBeforeWritable() : Long.MAX_VALUE;
	}

	@Override
	public Channel parent() {
		return this.parent;
	}

	@Override
	public ChannelPipeline pipeline() {
		return this.pipeline;
	}

	@Override
	public ByteBufAllocator alloc() {
		return this.config().getAllocator();
	}

	@Override
	public EventLoop eventLoop() {
		EventLoop eventLoop = this.eventLoop;
		if (eventLoop == null) {
			throw new IllegalStateException("channel not registered to an event loop");
		} else {
			return eventLoop;
		}
	}

	@Override
	public SocketAddress localAddress() {
		SocketAddress localAddress = this.localAddress;
		if (localAddress == null) {
			try {
				this.localAddress = localAddress = this.unsafe().localAddress();
			} catch (Throwable var3) {
				return null;
			}
		}

		return localAddress;
	}

	@Deprecated
	protected void invalidateLocalAddress() {
		this.localAddress = null;
	}

	@Override
	public SocketAddress remoteAddress() {
		SocketAddress remoteAddress = this.remoteAddress;
		if (remoteAddress == null) {
			try {
				this.remoteAddress = remoteAddress = this.unsafe().remoteAddress();
			} catch (Throwable var3) {
				return null;
			}
		}

		return remoteAddress;
	}

	@Deprecated
	protected void invalidateRemoteAddress() {
		this.remoteAddress = null;
	}

	@Override
	public boolean isRegistered() {
		return this.registered;
	}

	@Override
	public ChannelFuture bind(SocketAddress localAddress) {
		return this.pipeline.bind(localAddress);
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress) {
		return this.pipeline.connect(remoteAddress);
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
		return this.pipeline.connect(remoteAddress, localAddress);
	}

	@Override
	public ChannelFuture disconnect() {
		return this.pipeline.disconnect();
	}

	@Override
	public ChannelFuture close() {
		return this.pipeline.close();
	}

	@Override
	public ChannelFuture deregister() {
		return this.pipeline.deregister();
	}

	@Override
	public Channel flush() {
		this.pipeline.flush();
		return this;
	}

	@Override
	public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
		return this.pipeline.bind(localAddress, promise);
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
		return this.pipeline.connect(remoteAddress, promise);
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
		return this.pipeline.connect(remoteAddress, localAddress, promise);
	}

	@Override
	public ChannelFuture disconnect(ChannelPromise promise) {
		return this.pipeline.disconnect(promise);
	}

	@Override
	public ChannelFuture close(ChannelPromise promise) {
		return this.pipeline.close(promise);
	}

	@Override
	public ChannelFuture deregister(ChannelPromise promise) {
		return this.pipeline.deregister(promise);
	}

	@Override
	public Channel read() {
		this.pipeline.read();
		return this;
	}

	@Override
	public ChannelFuture write(Object msg) {
		return this.pipeline.write(msg);
	}

	@Override
	public ChannelFuture write(Object msg, ChannelPromise promise) {
		return this.pipeline.write(msg, promise);
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg) {
		return this.pipeline.writeAndFlush(msg);
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
		return this.pipeline.writeAndFlush(msg, promise);
	}

	@Override
	public ChannelPromise newPromise() {
		return this.pipeline.newPromise();
	}

	@Override
	public ChannelProgressivePromise newProgressivePromise() {
		return this.pipeline.newProgressivePromise();
	}

	@Override
	public ChannelFuture newSucceededFuture() {
		return this.pipeline.newSucceededFuture();
	}

	@Override
	public ChannelFuture newFailedFuture(Throwable cause) {
		return this.pipeline.newFailedFuture(cause);
	}

	@Override
	public ChannelFuture closeFuture() {
		return this.closeFuture;
	}

	@Override
	public Unsafe unsafe() {
		return this.unsafe;
	}

	protected abstract AbstractChannel.AbstractUnsafe newUnsafe();

	public final int hashCode() {
		return this.id.hashCode();
	}

	public final boolean equals(Object o) {
		return this == o;
	}

	public final int compareTo(Channel o) {
		return this == o ? 0 : this.id().compareTo(o.id());
	}

	public String toString() {
		boolean active = this.isActive();
		if (this.strValActive == active && this.strVal != null) {
			return this.strVal;
		} else {
			SocketAddress remoteAddr = this.remoteAddress();
			SocketAddress localAddr = this.localAddress();
			if (remoteAddr != null) {
				StringBuilder buf = new StringBuilder(96)
					.append("[id: 0x")
					.append(this.id.asShortText())
					.append(", L:")
					.append(localAddr)
					.append(active ? " - " : " ! ")
					.append("R:")
					.append(remoteAddr)
					.append(']');
				this.strVal = buf.toString();
			} else if (localAddr != null) {
				StringBuilder buf = new StringBuilder(64).append("[id: 0x").append(this.id.asShortText()).append(", L:").append(localAddr).append(']');
				this.strVal = buf.toString();
			} else {
				StringBuilder buf = new StringBuilder(16).append("[id: 0x").append(this.id.asShortText()).append(']');
				this.strVal = buf.toString();
			}

			this.strValActive = active;
			return this.strVal;
		}
	}

	@Override
	public final ChannelPromise voidPromise() {
		return this.pipeline.voidPromise();
	}

	protected abstract boolean isCompatible(EventLoop eventLoop);

	protected abstract SocketAddress localAddress0();

	protected abstract SocketAddress remoteAddress0();

	protected void doRegister() throws Exception {
	}

	protected abstract void doBind(SocketAddress socketAddress) throws Exception;

	protected abstract void doDisconnect() throws Exception;

	protected abstract void doClose() throws Exception;

	protected void doShutdownOutput() throws Exception {
		this.doClose();
	}

	protected void doDeregister() throws Exception {
	}

	protected abstract void doBeginRead() throws Exception;

	protected abstract void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception;

	protected Object filterOutboundMessage(Object msg) throws Exception {
		return msg;
	}

	protected abstract class AbstractUnsafe implements Unsafe {
		private volatile ChannelOutboundBuffer outboundBuffer = new ChannelOutboundBuffer(AbstractChannel.this);
		private Handle recvHandle;
		private boolean inFlush0;
		private boolean neverRegistered = true;

		private void assertEventLoop() {
			assert !AbstractChannel.this.registered || AbstractChannel.this.eventLoop.inEventLoop();
		}

		@Override
		public Handle recvBufAllocHandle() {
			if (this.recvHandle == null) {
				this.recvHandle = AbstractChannel.this.config().<RecvByteBufAllocator>getRecvByteBufAllocator().newHandle();
			}

			return this.recvHandle;
		}

		@Override
		public final ChannelOutboundBuffer outboundBuffer() {
			return this.outboundBuffer;
		}

		@Override
		public final SocketAddress localAddress() {
			return AbstractChannel.this.localAddress0();
		}

		@Override
		public final SocketAddress remoteAddress() {
			return AbstractChannel.this.remoteAddress0();
		}

		@Override
		public final void register(EventLoop eventLoop, ChannelPromise promise) {
			if (eventLoop == null) {
				throw new NullPointerException("eventLoop");
			} else if (AbstractChannel.this.isRegistered()) {
				promise.setFailure(new IllegalStateException("registered to an event loop already"));
			} else if (!AbstractChannel.this.isCompatible(eventLoop)) {
				promise.setFailure(new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
			} else {
				AbstractChannel.this.eventLoop = eventLoop;
				if (eventLoop.inEventLoop()) {
					this.register0(promise);
				} else {
					try {
						eventLoop.execute(new Runnable() {
							public void run() {
								AbstractUnsafe.this.register0(promise);
							}
						});
					} catch (Throwable var4) {
						AbstractChannel.logger.warn("Force-closing a channel whose registration task was not accepted by an event loop: {}", AbstractChannel.this, var4);
						this.closeForcibly();
						AbstractChannel.this.closeFuture.setClosed();
						this.safeSetFailure(promise, var4);
					}
				}
			}
		}

		private void register0(ChannelPromise promise) {
			try {
				if (!promise.setUncancellable() || !this.ensureOpen(promise)) {
					return;
				}

				boolean firstRegistration = this.neverRegistered;
				AbstractChannel.this.doRegister();
				this.neverRegistered = false;
				AbstractChannel.this.registered = true;
				AbstractChannel.this.pipeline.invokeHandlerAddedIfNeeded();
				this.safeSetSuccess(promise);
				AbstractChannel.this.pipeline.fireChannelRegistered();
				if (AbstractChannel.this.isActive()) {
					if (firstRegistration) {
						AbstractChannel.this.pipeline.fireChannelActive();
					} else if (AbstractChannel.this.config().isAutoRead()) {
						this.beginRead();
					}
				}
			} catch (Throwable var3) {
				this.closeForcibly();
				AbstractChannel.this.closeFuture.setClosed();
				this.safeSetFailure(promise, var3);
			}
		}

		@Override
		public final void bind(SocketAddress localAddress, ChannelPromise promise) {
			this.assertEventLoop();
			if (promise.setUncancellable() && this.ensureOpen(promise)) {
				if (Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST))
					&& localAddress instanceof InetSocketAddress
					&& !((InetSocketAddress)localAddress).getAddress().isAnyLocalAddress()
					&& !PlatformDependent.isWindows()
					&& !PlatformDependent.maybeSuperUser()) {
					AbstractChannel.logger
						.warn(
							"A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address ("
								+ localAddress
								+ ") anyway as requested."
						);
				}

				boolean wasActive = AbstractChannel.this.isActive();

				try {
					AbstractChannel.this.doBind(localAddress);
				} catch (Throwable var5) {
					this.safeSetFailure(promise, var5);
					this.closeIfClosed();
					return;
				}

				if (!wasActive && AbstractChannel.this.isActive()) {
					this.invokeLater(new Runnable() {
						public void run() {
							AbstractChannel.this.pipeline.fireChannelActive();
						}
					});
				}

				this.safeSetSuccess(promise);
			}
		}

		@Override
		public final void disconnect(ChannelPromise promise) {
			this.assertEventLoop();
			if (promise.setUncancellable()) {
				boolean wasActive = AbstractChannel.this.isActive();

				try {
					AbstractChannel.this.doDisconnect();
				} catch (Throwable var4) {
					this.safeSetFailure(promise, var4);
					this.closeIfClosed();
					return;
				}

				if (wasActive && !AbstractChannel.this.isActive()) {
					this.invokeLater(new Runnable() {
						public void run() {
							AbstractChannel.this.pipeline.fireChannelInactive();
						}
					});
				}

				this.safeSetSuccess(promise);
				this.closeIfClosed();
			}
		}

		@Override
		public final void close(ChannelPromise promise) {
			this.assertEventLoop();
			this.close(promise, AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION, AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION, false);
		}

		public final void shutdownOutput(ChannelPromise promise) {
			this.assertEventLoop();
			this.shutdownOutput(promise, null);
		}

		private void shutdownOutput(ChannelPromise promise, Throwable cause) {
			if (promise.setUncancellable()) {
				final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
				if (outboundBuffer == null) {
					promise.setFailure(AbstractChannel.CLOSE_CLOSED_CHANNEL_EXCEPTION);
				} else {
					this.outboundBuffer = null;
					final Throwable shutdownCause = cause == null
						? new ChannelOutputShutdownException("Channel output shutdown")
						: new ChannelOutputShutdownException("Channel output shutdown", cause);
					Executor closeExecutor = this.prepareToClose();
					if (closeExecutor != null) {
						closeExecutor.execute(new Runnable() {
							public void run() {
								try {
									AbstractChannel.this.doShutdownOutput();
									promise.setSuccess();
								} catch (Throwable var5) {
									promise.setFailure(var5);
								} finally {
									AbstractChannel.this.eventLoop().execute(new Runnable() {
										public void run() {
											AbstractUnsafe.this.closeOutboundBufferForShutdown(AbstractChannel.this.pipeline, outboundBuffer, shutdownCause);
										}
									});
								}
							}
						});
					} else {
						try {
							AbstractChannel.this.doShutdownOutput();
							promise.setSuccess();
						} catch (Throwable var10) {
							promise.setFailure(var10);
						} finally {
							this.closeOutboundBufferForShutdown(AbstractChannel.this.pipeline, outboundBuffer, shutdownCause);
						}
					}
				}
			}
		}

		private void closeOutboundBufferForShutdown(ChannelPipeline pipeline, ChannelOutboundBuffer buffer, Throwable cause) {
			buffer.failFlushed(cause, false);
			buffer.close(cause, true);
			pipeline.fireUserEventTriggered(ChannelOutputShutdownEvent.INSTANCE);
		}

		private void close(ChannelPromise promise, Throwable cause, ClosedChannelException closeCause, boolean notify) {
			if (promise.setUncancellable()) {
				if (AbstractChannel.this.closeInitiated) {
					if (AbstractChannel.this.closeFuture.isDone()) {
						this.safeSetSuccess(promise);
					} else if (!(promise instanceof VoidChannelPromise)) {
						AbstractChannel.this.closeFuture.addListener(new ChannelFutureListener() {
							public void operationComplete(ChannelFuture future) throws Exception {
								promise.setSuccess();
							}
						});
					}
				} else {
					AbstractChannel.this.closeInitiated = true;
					final boolean wasActive = AbstractChannel.this.isActive();
					final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
					this.outboundBuffer = null;
					Executor closeExecutor = this.prepareToClose();
					if (closeExecutor != null) {
						closeExecutor.execute(new Runnable() {
							public void run() {
								try {
									AbstractUnsafe.this.doClose0(promise);
								} finally {
									AbstractUnsafe.this.invokeLater(new Runnable() {
										public void run() {
											if (outboundBuffer != null) {
												outboundBuffer.failFlushed(cause, notify);
												outboundBuffer.close(closeCause);
											}

											AbstractUnsafe.this.fireChannelInactiveAndDeregister(wasActive);
										}
									});
								}
							}
						});
					} else {
						try {
							this.doClose0(promise);
						} finally {
							if (outboundBuffer != null) {
								outboundBuffer.failFlushed(cause, notify);
								outboundBuffer.close(closeCause);
							}
						}

						if (this.inFlush0) {
							this.invokeLater(new Runnable() {
								public void run() {
									AbstractUnsafe.this.fireChannelInactiveAndDeregister(wasActive);
								}
							});
						} else {
							this.fireChannelInactiveAndDeregister(wasActive);
						}
					}
				}
			}
		}

		private void doClose0(ChannelPromise promise) {
			try {
				AbstractChannel.this.doClose();
				AbstractChannel.this.closeFuture.setClosed();
				this.safeSetSuccess(promise);
			} catch (Throwable var3) {
				AbstractChannel.this.closeFuture.setClosed();
				this.safeSetFailure(promise, var3);
			}
		}

		private void fireChannelInactiveAndDeregister(boolean wasActive) {
			this.deregister(this.voidPromise(), wasActive && !AbstractChannel.this.isActive());
		}

		@Override
		public final void closeForcibly() {
			this.assertEventLoop();

			try {
				AbstractChannel.this.doClose();
			} catch (Exception var2) {
				AbstractChannel.logger.warn("Failed to close a channel.", (Throwable)var2);
			}
		}

		@Override
		public final void deregister(ChannelPromise promise) {
			this.assertEventLoop();
			this.deregister(promise, false);
		}

		private void deregister(ChannelPromise promise, boolean fireChannelInactive) {
			if (promise.setUncancellable()) {
				if (!AbstractChannel.this.registered) {
					this.safeSetSuccess(promise);
				} else {
					this.invokeLater(new Runnable() {
						public void run() {
							try {
								AbstractChannel.this.doDeregister();
							} catch (Throwable var5) {
								AbstractChannel.logger.warn("Unexpected exception occurred while deregistering a channel.", var5);
							} finally {
								if (fireChannelInactive) {
									AbstractChannel.this.pipeline.fireChannelInactive();
								}

								if (AbstractChannel.this.registered) {
									AbstractChannel.this.registered = false;
									AbstractChannel.this.pipeline.fireChannelUnregistered();
								}

								AbstractUnsafe.this.safeSetSuccess(promise);
							}
						}
					});
				}
			}
		}

		@Override
		public final void beginRead() {
			this.assertEventLoop();
			if (AbstractChannel.this.isActive()) {
				try {
					AbstractChannel.this.doBeginRead();
				} catch (Exception var2) {
					final Exception e = var2;
					this.invokeLater(new Runnable() {
						public void run() {
							AbstractChannel.this.pipeline.fireExceptionCaught(e);
						}
					});
					this.close(this.voidPromise());
				}
			}
		}

		@Override
		public final void write(Object msg, ChannelPromise promise) {
			this.assertEventLoop();
			ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
			if (outboundBuffer == null) {
				this.safeSetFailure(promise, AbstractChannel.WRITE_CLOSED_CHANNEL_EXCEPTION);
				ReferenceCountUtil.release(msg);
			} else {
				int size;
				try {
					msg = AbstractChannel.this.filterOutboundMessage(msg);
					size = AbstractChannel.this.pipeline.estimatorHandle().size(msg);
					if (size < 0) {
						size = 0;
					}
				} catch (Throwable var6) {
					this.safeSetFailure(promise, var6);
					ReferenceCountUtil.release(msg);
					return;
				}

				outboundBuffer.addMessage(msg, size, promise);
			}
		}

		@Override
		public final void flush() {
			this.assertEventLoop();
			ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
			if (outboundBuffer != null) {
				outboundBuffer.addFlush();
				this.flush0();
			}
		}

		protected void flush0() {
			if (!this.inFlush0) {
				ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
				if (outboundBuffer != null && !outboundBuffer.isEmpty()) {
					this.inFlush0 = true;
					if (!AbstractChannel.this.isActive()) {
						try {
							if (AbstractChannel.this.isOpen()) {
								outboundBuffer.failFlushed(AbstractChannel.FLUSH0_NOT_YET_CONNECTED_EXCEPTION, true);
							} else {
								outboundBuffer.failFlushed(AbstractChannel.FLUSH0_CLOSED_CHANNEL_EXCEPTION, false);
							}
						} finally {
							this.inFlush0 = false;
						}
					} else {
						try {
							AbstractChannel.this.doWrite(outboundBuffer);
						} catch (Throwable var15) {
							Throwable t = var15;
							if (var15 instanceof IOException && AbstractChannel.this.config().isAutoClose()) {
								this.close(this.voidPromise(), var15, AbstractChannel.FLUSH0_CLOSED_CHANNEL_EXCEPTION, false);
							} else {
								try {
									this.shutdownOutput(this.voidPromise(), t);
								} catch (Throwable var14) {
									this.close(this.voidPromise(), var14, AbstractChannel.FLUSH0_CLOSED_CHANNEL_EXCEPTION, false);
								}
							}
						} finally {
							this.inFlush0 = false;
						}
					}
				}
			}
		}

		@Override
		public final ChannelPromise voidPromise() {
			this.assertEventLoop();
			return AbstractChannel.this.unsafeVoidPromise;
		}

		protected final boolean ensureOpen(ChannelPromise promise) {
			if (AbstractChannel.this.isOpen()) {
				return true;
			} else {
				this.safeSetFailure(promise, AbstractChannel.ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION);
				return false;
			}
		}

		protected final void safeSetSuccess(ChannelPromise promise) {
			if (!(promise instanceof VoidChannelPromise) && !promise.trySuccess()) {
				AbstractChannel.logger.warn("Failed to mark a promise as success because it is done already: {}", promise);
			}
		}

		protected final void safeSetFailure(ChannelPromise promise, Throwable cause) {
			if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
				AbstractChannel.logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
			}
		}

		protected final void closeIfClosed() {
			if (!AbstractChannel.this.isOpen()) {
				this.close(this.voidPromise());
			}
		}

		private void invokeLater(Runnable task) {
			try {
				AbstractChannel.this.eventLoop().execute(task);
			} catch (RejectedExecutionException var3) {
				AbstractChannel.logger.warn("Can't invoke task later as EventLoop rejected it", (Throwable)var3);
			}
		}

		protected final Throwable annotateConnectException(Throwable cause, SocketAddress remoteAddress) {
			if (cause instanceof ConnectException) {
				return new AbstractChannel.AnnotatedConnectException((ConnectException)cause, remoteAddress);
			} else if (cause instanceof NoRouteToHostException) {
				return new AbstractChannel.AnnotatedNoRouteToHostException((NoRouteToHostException)cause, remoteAddress);
			} else {
				return (Throwable)(cause instanceof SocketException ? new AbstractChannel.AnnotatedSocketException((SocketException)cause, remoteAddress) : cause);
			}
		}

		protected Executor prepareToClose() {
			return null;
		}
	}

	private static final class AnnotatedConnectException extends ConnectException {
		private static final long serialVersionUID = 3901958112696433556L;

		AnnotatedConnectException(ConnectException exception, SocketAddress remoteAddress) {
			super(exception.getMessage() + ": " + remoteAddress);
			this.initCause(exception);
			this.setStackTrace(exception.getStackTrace());
		}

		public Throwable fillInStackTrace() {
			return this;
		}
	}

	private static final class AnnotatedNoRouteToHostException extends NoRouteToHostException {
		private static final long serialVersionUID = -6801433937592080623L;

		AnnotatedNoRouteToHostException(NoRouteToHostException exception, SocketAddress remoteAddress) {
			super(exception.getMessage() + ": " + remoteAddress);
			this.initCause(exception);
			this.setStackTrace(exception.getStackTrace());
		}

		public Throwable fillInStackTrace() {
			return this;
		}
	}

	private static final class AnnotatedSocketException extends SocketException {
		private static final long serialVersionUID = 3896743275010454039L;

		AnnotatedSocketException(SocketException exception, SocketAddress remoteAddress) {
			super(exception.getMessage() + ": " + remoteAddress);
			this.initCause(exception);
			this.setStackTrace(exception.getStackTrace());
		}

		public Throwable fillInStackTrace() {
			return this;
		}
	}

	static final class CloseFuture extends DefaultChannelPromise {
		CloseFuture(AbstractChannel ch) {
			super(ch);
		}

		@Override
		public ChannelPromise setSuccess() {
			throw new IllegalStateException();
		}

		@Override
		public ChannelPromise setFailure(Throwable cause) {
			throw new IllegalStateException();
		}

		@Override
		public boolean trySuccess() {
			throw new IllegalStateException();
		}

		@Override
		public boolean tryFailure(Throwable cause) {
			throw new IllegalStateException();
		}

		boolean setClosed() {
			return super.trySuccess();
		}
	}
}
