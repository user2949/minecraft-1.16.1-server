package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class OioSocketChannel extends OioByteStreamChannel implements SocketChannel {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSocketChannel.class);
	private final Socket socket;
	private final OioSocketChannelConfig config;

	public OioSocketChannel() {
		this(new Socket());
	}

	public OioSocketChannel(Socket socket) {
		this(null, socket);
	}

	public OioSocketChannel(Channel parent, Socket socket) {
		super(parent);
		this.socket = socket;
		this.config = new DefaultOioSocketChannelConfig(this, socket);
		boolean success = false;

		try {
			if (socket.isConnected()) {
				this.activate(socket.getInputStream(), socket.getOutputStream());
			}

			socket.setSoTimeout(1000);
			success = true;
		} catch (Exception var12) {
			throw new ChannelException("failed to initialize a socket", var12);
		} finally {
			if (!success) {
				try {
					socket.close();
				} catch (IOException var11) {
					logger.warn("Failed to close a socket.", (Throwable)var11);
				}
			}
		}
	}

	@Override
	public ServerSocketChannel parent() {
		return (ServerSocketChannel)super.parent();
	}

	public OioSocketChannelConfig config() {
		return this.config;
	}

	@Override
	public boolean isOpen() {
		return !this.socket.isClosed();
	}

	@Override
	public boolean isActive() {
		return !this.socket.isClosed() && this.socket.isConnected();
	}

	@Override
	public boolean isOutputShutdown() {
		return this.socket.isOutputShutdown() || !this.isActive();
	}

	@Override
	public boolean isInputShutdown() {
		return this.socket.isInputShutdown() || !this.isActive();
	}

	@Override
	public boolean isShutdown() {
		return this.socket.isInputShutdown() && this.socket.isOutputShutdown() || !this.isActive();
	}

	@Override
	protected final void doShutdownOutput() throws Exception {
		this.shutdownOutput0();
	}

	@Override
	public ChannelFuture shutdownOutput() {
		return this.shutdownOutput(this.newPromise());
	}

	@Override
	public ChannelFuture shutdownInput() {
		return this.shutdownInput(this.newPromise());
	}

	@Override
	public ChannelFuture shutdown() {
		return this.shutdown(this.newPromise());
	}

	@Override
	protected int doReadBytes(ByteBuf buf) throws Exception {
		if (this.socket.isClosed()) {
			return -1;
		} else {
			try {
				return super.doReadBytes(buf);
			} catch (SocketTimeoutException var3) {
				return 0;
			}
		}
	}

	@Override
	public ChannelFuture shutdownOutput(ChannelPromise promise) {
		EventLoop loop = this.eventLoop();
		if (loop.inEventLoop()) {
			this.shutdownOutput0(promise);
		} else {
			loop.execute(new Runnable() {
				public void run() {
					OioSocketChannel.this.shutdownOutput0(promise);
				}
			});
		}

		return promise;
	}

	private void shutdownOutput0(ChannelPromise promise) {
		try {
			this.shutdownOutput0();
			promise.setSuccess();
		} catch (Throwable var3) {
			promise.setFailure(var3);
		}
	}

	private void shutdownOutput0() throws IOException {
		this.socket.shutdownOutput();
	}

	@Override
	public ChannelFuture shutdownInput(ChannelPromise promise) {
		EventLoop loop = this.eventLoop();
		if (loop.inEventLoop()) {
			this.shutdownInput0(promise);
		} else {
			loop.execute(new Runnable() {
				public void run() {
					OioSocketChannel.this.shutdownInput0(promise);
				}
			});
		}

		return promise;
	}

	private void shutdownInput0(ChannelPromise promise) {
		try {
			this.socket.shutdownInput();
			promise.setSuccess();
		} catch (Throwable var3) {
			promise.setFailure(var3);
		}
	}

	@Override
	public ChannelFuture shutdown(ChannelPromise promise) {
		ChannelFuture shutdownOutputFuture = this.shutdownOutput();
		if (shutdownOutputFuture.isDone()) {
			this.shutdownOutputDone(shutdownOutputFuture, promise);
		} else {
			shutdownOutputFuture.addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture shutdownOutputFuture) throws Exception {
					OioSocketChannel.this.shutdownOutputDone(shutdownOutputFuture, promise);
				}
			});
		}

		return promise;
	}

	private void shutdownOutputDone(ChannelFuture shutdownOutputFuture, ChannelPromise promise) {
		ChannelFuture shutdownInputFuture = this.shutdownInput();
		if (shutdownInputFuture.isDone()) {
			shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
		} else {
			shutdownInputFuture.addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture shutdownInputFuture) throws Exception {
					OioSocketChannel.shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
				}
			});
		}
	}

	private static void shutdownDone(ChannelFuture shutdownOutputFuture, ChannelFuture shutdownInputFuture, ChannelPromise promise) {
		Throwable shutdownOutputCause = shutdownOutputFuture.cause();
		Throwable shutdownInputCause = shutdownInputFuture.cause();
		if (shutdownOutputCause != null) {
			if (shutdownInputCause != null) {
				logger.debug("Exception suppressed because a previous exception occurred.", shutdownInputCause);
			}

			promise.setFailure(shutdownOutputCause);
		} else if (shutdownInputCause != null) {
			promise.setFailure(shutdownInputCause);
		} else {
			promise.setSuccess();
		}
	}

	@Override
	public InetSocketAddress localAddress() {
		return (InetSocketAddress)super.localAddress();
	}

	@Override
	public InetSocketAddress remoteAddress() {
		return (InetSocketAddress)super.remoteAddress();
	}

	@Override
	protected SocketAddress localAddress0() {
		return this.socket.getLocalSocketAddress();
	}

	@Override
	protected SocketAddress remoteAddress0() {
		return this.socket.getRemoteSocketAddress();
	}

	@Override
	protected void doBind(SocketAddress localAddress) throws Exception {
		SocketUtils.bind(this.socket, localAddress);
	}

	@Override
	protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
		if (localAddress != null) {
			SocketUtils.bind(this.socket, localAddress);
		}

		boolean success = false;

		try {
			SocketUtils.connect(this.socket, remoteAddress, this.config().getConnectTimeoutMillis());
			this.activate(this.socket.getInputStream(), this.socket.getOutputStream());
			success = true;
		} catch (SocketTimeoutException var9) {
			ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
			cause.setStackTrace(var9.getStackTrace());
			throw cause;
		} finally {
			if (!success) {
				this.doClose();
			}
		}
	}

	@Override
	protected void doDisconnect() throws Exception {
		this.doClose();
	}

	@Override
	protected void doClose() throws Exception {
		this.socket.close();
	}

	protected boolean checkInputShutdown() {
		if (this.isInputShutdown()) {
			try {
				Thread.sleep((long)this.config().getSoTimeout());
			} catch (Throwable var2) {
			}

			return true;
		} else {
			return false;
		}
	}

	@Deprecated
	@Override
	protected void setReadPending(boolean readPending) {
		super.setReadPending(readPending);
	}

	final void clearReadPending0() {
		this.clearReadPending();
	}
}
