package io.netty.channel.unix;

import io.netty.channel.ChannelException;
import io.netty.channel.unix.Errors.NativeConnectException;
import io.netty.channel.unix.Errors.NativeIoException;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Socket extends FileDescriptor {
	private static final ClosedChannelException SHUTDOWN_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), Socket.class, "shutdown(..)"
	);
	private static final ClosedChannelException SEND_TO_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), Socket.class, "sendTo(..)"
	);
	private static final ClosedChannelException SEND_TO_ADDRESS_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), Socket.class, "sendToAddress(..)"
	);
	private static final ClosedChannelException SEND_TO_ADDRESSES_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), Socket.class, "sendToAddresses(..)"
	);
	private static final NativeIoException SEND_TO_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:sendto", Errors.ERRNO_EPIPE_NEGATIVE), Socket.class, "sendTo(..)"
	);
	private static final NativeIoException SEND_TO_ADDRESS_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:sendto", Errors.ERRNO_EPIPE_NEGATIVE), Socket.class, "sendToAddress"
	);
	private static final NativeIoException CONNECTION_RESET_EXCEPTION_SENDMSG = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:sendmsg", Errors.ERRNO_EPIPE_NEGATIVE), Socket.class, "sendToAddresses(..)"
	);
	private static final NativeIoException CONNECTION_RESET_SHUTDOWN_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:shutdown", Errors.ERRNO_ECONNRESET_NEGATIVE), Socket.class, "shutdown"
	);
	private static final NativeConnectException FINISH_CONNECT_REFUSED_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new NativeConnectException("syscall:getsockopt", Errors.ERROR_ECONNREFUSED_NEGATIVE), Socket.class, "finishConnect(..)"
	);
	private static final NativeConnectException CONNECT_REFUSED_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new NativeConnectException("syscall:connect", Errors.ERROR_ECONNREFUSED_NEGATIVE), Socket.class, "connect(..)"
	);
	public static final int UDS_SUN_PATH_SIZE = LimitsStaticallyReferencedJniMethods.udsSunPathSize();
	private static final AtomicBoolean INITIALIZED = new AtomicBoolean();

	public Socket(int fd) {
		super(fd);
	}

	public final void shutdown() throws IOException {
		this.shutdown(true, true);
	}

	public final void shutdown(boolean read, boolean write) throws IOException {
		int oldState;
		int newState;
		do {
			oldState = this.state;
			if (isClosed(oldState)) {
				throw new ClosedChannelException();
			}

			newState = oldState;
			if (read && !isInputShutdown(oldState)) {
				newState = inputShutdown(oldState);
			}

			if (write && !isOutputShutdown(newState)) {
				newState = outputShutdown(newState);
			}

			if (newState == oldState) {
				return;
			}
		} while (!this.casState(oldState, newState));

		oldState = shutdown(this.fd, read, write);
		if (oldState < 0) {
			Errors.ioResult("shutdown", oldState, CONNECTION_RESET_SHUTDOWN_EXCEPTION, SHUTDOWN_CLOSED_CHANNEL_EXCEPTION);
		}
	}

	public final boolean isShutdown() {
		int state = this.state;
		return isInputShutdown(state) && isOutputShutdown(state);
	}

	public final boolean isInputShutdown() {
		return isInputShutdown(this.state);
	}

	public final boolean isOutputShutdown() {
		return isOutputShutdown(this.state);
	}

	public final int sendTo(ByteBuffer buf, int pos, int limit, InetAddress addr, int port) throws IOException {
		byte[] address;
		int scopeId;
		if (addr instanceof Inet6Address) {
			address = addr.getAddress();
			scopeId = ((Inet6Address)addr).getScopeId();
		} else {
			scopeId = 0;
			address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
		}

		int res = sendTo(this.fd, buf, pos, limit, address, scopeId, port);
		if (res >= 0) {
			return res;
		} else if (res == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
			throw new PortUnreachableException("sendTo failed");
		} else {
			return Errors.ioResult("sendTo", res, SEND_TO_CONNECTION_RESET_EXCEPTION, SEND_TO_CLOSED_CHANNEL_EXCEPTION);
		}
	}

	public final int sendToAddress(long memoryAddress, int pos, int limit, InetAddress addr, int port) throws IOException {
		byte[] address;
		int scopeId;
		if (addr instanceof Inet6Address) {
			address = addr.getAddress();
			scopeId = ((Inet6Address)addr).getScopeId();
		} else {
			scopeId = 0;
			address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
		}

		int res = sendToAddress(this.fd, memoryAddress, pos, limit, address, scopeId, port);
		if (res >= 0) {
			return res;
		} else if (res == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
			throw new PortUnreachableException("sendToAddress failed");
		} else {
			return Errors.ioResult("sendToAddress", res, SEND_TO_ADDRESS_CONNECTION_RESET_EXCEPTION, SEND_TO_ADDRESS_CLOSED_CHANNEL_EXCEPTION);
		}
	}

	public final int sendToAddresses(long memoryAddress, int length, InetAddress addr, int port) throws IOException {
		byte[] address;
		int scopeId;
		if (addr instanceof Inet6Address) {
			address = addr.getAddress();
			scopeId = ((Inet6Address)addr).getScopeId();
		} else {
			scopeId = 0;
			address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
		}

		int res = sendToAddresses(this.fd, memoryAddress, length, address, scopeId, port);
		if (res >= 0) {
			return res;
		} else if (res == Errors.ERROR_ECONNREFUSED_NEGATIVE) {
			throw new PortUnreachableException("sendToAddresses failed");
		} else {
			return Errors.ioResult("sendToAddresses", res, CONNECTION_RESET_EXCEPTION_SENDMSG, SEND_TO_ADDRESSES_CLOSED_CHANNEL_EXCEPTION);
		}
	}

	public final DatagramSocketAddress recvFrom(ByteBuffer buf, int pos, int limit) throws IOException {
		return recvFrom(this.fd, buf, pos, limit);
	}

	public final DatagramSocketAddress recvFromAddress(long memoryAddress, int pos, int limit) throws IOException {
		return recvFromAddress(this.fd, memoryAddress, pos, limit);
	}

	public final int recvFd() throws IOException {
		int res = recvFd(this.fd);
		if (res > 0) {
			return res;
		} else if (res == 0) {
			return -1;
		} else if (res != Errors.ERRNO_EAGAIN_NEGATIVE && res != Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
			throw Errors.newIOException("recvFd", res);
		} else {
			return 0;
		}
	}

	public final int sendFd(int fdToSend) throws IOException {
		int res = sendFd(this.fd, fdToSend);
		if (res >= 0) {
			return res;
		} else if (res != Errors.ERRNO_EAGAIN_NEGATIVE && res != Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
			throw Errors.newIOException("sendFd", res);
		} else {
			return -1;
		}
	}

	public final boolean connect(SocketAddress socketAddress) throws IOException {
		int res;
		if (socketAddress instanceof InetSocketAddress) {
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
			NativeInetAddress address = NativeInetAddress.newInstance(inetSocketAddress.getAddress());
			res = connect(this.fd, address.address, address.scopeId, inetSocketAddress.getPort());
		} else {
			if (!(socketAddress instanceof DomainSocketAddress)) {
				throw new Error("Unexpected SocketAddress implementation " + socketAddress);
			}

			DomainSocketAddress unixDomainSocketAddress = (DomainSocketAddress)socketAddress;
			res = connectDomainSocket(this.fd, unixDomainSocketAddress.path().getBytes(CharsetUtil.UTF_8));
		}

		if (res < 0) {
			if (res == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
				return false;
			}

			Errors.throwConnectException("connect", CONNECT_REFUSED_EXCEPTION, res);
		}

		return true;
	}

	public final boolean finishConnect() throws IOException {
		int res = finishConnect(this.fd);
		if (res < 0) {
			if (res == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
				return false;
			}

			Errors.throwConnectException("finishConnect", FINISH_CONNECT_REFUSED_EXCEPTION, res);
		}

		return true;
	}

	public final void disconnect() throws IOException {
		int res = disconnect(this.fd);
		if (res < 0) {
			Errors.throwConnectException("disconnect", FINISH_CONNECT_REFUSED_EXCEPTION, res);
		}
	}

	public final void bind(SocketAddress socketAddress) throws IOException {
		if (socketAddress instanceof InetSocketAddress) {
			InetSocketAddress addr = (InetSocketAddress)socketAddress;
			NativeInetAddress address = NativeInetAddress.newInstance(addr.getAddress());
			int res = bind(this.fd, address.address, address.scopeId, addr.getPort());
			if (res < 0) {
				throw Errors.newIOException("bind", res);
			}
		} else {
			if (!(socketAddress instanceof DomainSocketAddress)) {
				throw new Error("Unexpected SocketAddress implementation " + socketAddress);
			}

			DomainSocketAddress addr = (DomainSocketAddress)socketAddress;
			int res = bindDomainSocket(this.fd, addr.path().getBytes(CharsetUtil.UTF_8));
			if (res < 0) {
				throw Errors.newIOException("bind", res);
			}
		}
	}

	public final void listen(int backlog) throws IOException {
		int res = listen(this.fd, backlog);
		if (res < 0) {
			throw Errors.newIOException("listen", res);
		}
	}

	public final int accept(byte[] addr) throws IOException {
		int res = accept(this.fd, addr);
		if (res >= 0) {
			return res;
		} else if (res != Errors.ERRNO_EAGAIN_NEGATIVE && res != Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
			throw Errors.newIOException("accept", res);
		} else {
			return -1;
		}
	}

	public final InetSocketAddress remoteAddress() {
		byte[] addr = remoteAddress(this.fd);
		return addr == null ? null : NativeInetAddress.address(addr, 0, addr.length);
	}

	public final InetSocketAddress localAddress() {
		byte[] addr = localAddress(this.fd);
		return addr == null ? null : NativeInetAddress.address(addr, 0, addr.length);
	}

	public final int getReceiveBufferSize() throws IOException {
		return getReceiveBufferSize(this.fd);
	}

	public final int getSendBufferSize() throws IOException {
		return getSendBufferSize(this.fd);
	}

	public final boolean isKeepAlive() throws IOException {
		return isKeepAlive(this.fd) != 0;
	}

	public final boolean isTcpNoDelay() throws IOException {
		return isTcpNoDelay(this.fd) != 0;
	}

	public final boolean isReuseAddress() throws IOException {
		return isReuseAddress(this.fd) != 0;
	}

	public final boolean isReusePort() throws IOException {
		return isReusePort(this.fd) != 0;
	}

	public final boolean isBroadcast() throws IOException {
		return isBroadcast(this.fd) != 0;
	}

	public final int getSoLinger() throws IOException {
		return getSoLinger(this.fd);
	}

	public final int getSoError() throws IOException {
		return getSoError(this.fd);
	}

	public final int getTrafficClass() throws IOException {
		return getTrafficClass(this.fd);
	}

	public final void setKeepAlive(boolean keepAlive) throws IOException {
		setKeepAlive(this.fd, keepAlive ? 1 : 0);
	}

	public final void setReceiveBufferSize(int receiveBufferSize) throws IOException {
		setReceiveBufferSize(this.fd, receiveBufferSize);
	}

	public final void setSendBufferSize(int sendBufferSize) throws IOException {
		setSendBufferSize(this.fd, sendBufferSize);
	}

	public final void setTcpNoDelay(boolean tcpNoDelay) throws IOException {
		setTcpNoDelay(this.fd, tcpNoDelay ? 1 : 0);
	}

	public final void setSoLinger(int soLinger) throws IOException {
		setSoLinger(this.fd, soLinger);
	}

	public final void setReuseAddress(boolean reuseAddress) throws IOException {
		setReuseAddress(this.fd, reuseAddress ? 1 : 0);
	}

	public final void setReusePort(boolean reusePort) throws IOException {
		setReusePort(this.fd, reusePort ? 1 : 0);
	}

	public final void setBroadcast(boolean broadcast) throws IOException {
		setBroadcast(this.fd, broadcast ? 1 : 0);
	}

	public final void setTrafficClass(int trafficClass) throws IOException {
		setTrafficClass(this.fd, trafficClass);
	}

	@Override
	public String toString() {
		return "Socket{fd=" + this.fd + '}';
	}

	public static Socket newSocketStream() {
		return new Socket(newSocketStream0());
	}

	public static Socket newSocketDgram() {
		return new Socket(newSocketDgram0());
	}

	public static Socket newSocketDomain() {
		return new Socket(newSocketDomain0());
	}

	public static void initialize() {
		if (INITIALIZED.compareAndSet(false, true)) {
			initialize(NetUtil.isIpV4StackPreferred());
		}
	}

	protected static int newSocketStream0() {
		int res = newSocketStreamFd();
		if (res < 0) {
			throw new ChannelException(Errors.newIOException("newSocketStream", res));
		} else {
			return res;
		}
	}

	protected static int newSocketDgram0() {
		int res = newSocketDgramFd();
		if (res < 0) {
			throw new ChannelException(Errors.newIOException("newSocketDgram", res));
		} else {
			return res;
		}
	}

	protected static int newSocketDomain0() {
		int res = newSocketDomainFd();
		if (res < 0) {
			throw new ChannelException(Errors.newIOException("newSocketDomain", res));
		} else {
			return res;
		}
	}

	private static native int shutdown(int integer, boolean boolean2, boolean boolean3);

	private static native int connect(int integer1, byte[] arr, int integer3, int integer4);

	private static native int connectDomainSocket(int integer, byte[] arr);

	private static native int finishConnect(int integer);

	private static native int disconnect(int integer);

	private static native int bind(int integer1, byte[] arr, int integer3, int integer4);

	private static native int bindDomainSocket(int integer, byte[] arr);

	private static native int listen(int integer1, int integer2);

	private static native int accept(int integer, byte[] arr);

	private static native byte[] remoteAddress(int integer);

	private static native byte[] localAddress(int integer);

	private static native int sendTo(int integer1, ByteBuffer byteBuffer, int integer3, int integer4, byte[] arr, int integer6, int integer7);

	private static native int sendToAddress(int integer1, long long2, int integer3, int integer4, byte[] arr, int integer6, int integer7);

	private static native int sendToAddresses(int integer1, long long2, int integer3, byte[] arr, int integer5, int integer6);

	private static native DatagramSocketAddress recvFrom(int integer1, ByteBuffer byteBuffer, int integer3, int integer4) throws IOException;

	private static native DatagramSocketAddress recvFromAddress(int integer1, long long2, int integer3, int integer4) throws IOException;

	private static native int recvFd(int integer);

	private static native int sendFd(int integer1, int integer2);

	private static native int newSocketStreamFd();

	private static native int newSocketDgramFd();

	private static native int newSocketDomainFd();

	private static native int isReuseAddress(int integer) throws IOException;

	private static native int isReusePort(int integer) throws IOException;

	private static native int getReceiveBufferSize(int integer) throws IOException;

	private static native int getSendBufferSize(int integer) throws IOException;

	private static native int isKeepAlive(int integer) throws IOException;

	private static native int isTcpNoDelay(int integer) throws IOException;

	private static native int isBroadcast(int integer) throws IOException;

	private static native int getSoLinger(int integer) throws IOException;

	private static native int getSoError(int integer) throws IOException;

	private static native int getTrafficClass(int integer) throws IOException;

	private static native void setReuseAddress(int integer1, int integer2) throws IOException;

	private static native void setReusePort(int integer1, int integer2) throws IOException;

	private static native void setKeepAlive(int integer1, int integer2) throws IOException;

	private static native void setReceiveBufferSize(int integer1, int integer2) throws IOException;

	private static native void setSendBufferSize(int integer1, int integer2) throws IOException;

	private static native void setTcpNoDelay(int integer1, int integer2) throws IOException;

	private static native void setSoLinger(int integer1, int integer2) throws IOException;

	private static native void setBroadcast(int integer1, int integer2) throws IOException;

	private static native void setTrafficClass(int integer1, int integer2) throws IOException;

	private static native void initialize(boolean boolean1);
}
