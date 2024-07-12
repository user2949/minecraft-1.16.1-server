package io.netty.channel.epoll;

import io.netty.channel.DefaultFileRegion;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.channel.unix.PeerCredentials;
import io.netty.channel.unix.Socket;
import io.netty.channel.unix.Errors.NativeIoException;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.ClosedChannelException;

final class LinuxSocket extends Socket {
	private static final long MAX_UINT32_T = 4294967295L;
	private static final NativeIoException SENDFILE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException(
		"syscall:sendfile(...)", Errors.ERRNO_EPIPE_NEGATIVE
	);
	private static final ClosedChannelException SENDFILE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), Native.class, "sendfile(...)"
	);

	public LinuxSocket(int fd) {
		super(fd);
	}

	void setTcpDeferAccept(int deferAccept) throws IOException {
		setTcpDeferAccept(this.intValue(), deferAccept);
	}

	void setTcpQuickAck(boolean quickAck) throws IOException {
		setTcpQuickAck(this.intValue(), quickAck ? 1 : 0);
	}

	void setTcpCork(boolean tcpCork) throws IOException {
		setTcpCork(this.intValue(), tcpCork ? 1 : 0);
	}

	void setTcpNotSentLowAt(long tcpNotSentLowAt) throws IOException {
		if (tcpNotSentLowAt >= 0L && tcpNotSentLowAt <= 4294967295L) {
			setTcpNotSentLowAt(this.intValue(), (int)tcpNotSentLowAt);
		} else {
			throw new IllegalArgumentException("tcpNotSentLowAt must be a uint32_t");
		}
	}

	void setTcpFastOpen(int tcpFastopenBacklog) throws IOException {
		setTcpFastOpen(this.intValue(), tcpFastopenBacklog);
	}

	void setTcpFastOpenConnect(boolean tcpFastOpenConnect) throws IOException {
		setTcpFastOpenConnect(this.intValue(), tcpFastOpenConnect ? 1 : 0);
	}

	boolean isTcpFastOpenConnect() throws IOException {
		return isTcpFastOpenConnect(this.intValue()) != 0;
	}

	void setTcpKeepIdle(int seconds) throws IOException {
		setTcpKeepIdle(this.intValue(), seconds);
	}

	void setTcpKeepIntvl(int seconds) throws IOException {
		setTcpKeepIntvl(this.intValue(), seconds);
	}

	void setTcpKeepCnt(int probes) throws IOException {
		setTcpKeepCnt(this.intValue(), probes);
	}

	void setTcpUserTimeout(int milliseconds) throws IOException {
		setTcpUserTimeout(this.intValue(), milliseconds);
	}

	void setIpFreeBind(boolean enabled) throws IOException {
		setIpFreeBind(this.intValue(), enabled ? 1 : 0);
	}

	void setIpTransparent(boolean enabled) throws IOException {
		setIpTransparent(this.intValue(), enabled ? 1 : 0);
	}

	void setIpRecvOrigDestAddr(boolean enabled) throws IOException {
		setIpRecvOrigDestAddr(this.intValue(), enabled ? 1 : 0);
	}

	void getTcpInfo(EpollTcpInfo info) throws IOException {
		getTcpInfo(this.intValue(), info.info);
	}

	void setTcpMd5Sig(InetAddress address, byte[] key) throws IOException {
		NativeInetAddress a = NativeInetAddress.newInstance(address);
		setTcpMd5Sig(this.intValue(), a.address(), a.scopeId(), key);
	}

	boolean isTcpCork() throws IOException {
		return isTcpCork(this.intValue()) != 0;
	}

	int getTcpDeferAccept() throws IOException {
		return getTcpDeferAccept(this.intValue());
	}

	boolean isTcpQuickAck() throws IOException {
		return isTcpQuickAck(this.intValue()) != 0;
	}

	long getTcpNotSentLowAt() throws IOException {
		return (long)getTcpNotSentLowAt(this.intValue()) & 4294967295L;
	}

	int getTcpKeepIdle() throws IOException {
		return getTcpKeepIdle(this.intValue());
	}

	int getTcpKeepIntvl() throws IOException {
		return getTcpKeepIntvl(this.intValue());
	}

	int getTcpKeepCnt() throws IOException {
		return getTcpKeepCnt(this.intValue());
	}

	int getTcpUserTimeout() throws IOException {
		return getTcpUserTimeout(this.intValue());
	}

	boolean isIpFreeBind() throws IOException {
		return isIpFreeBind(this.intValue()) != 0;
	}

	boolean isIpTransparent() throws IOException {
		return isIpTransparent(this.intValue()) != 0;
	}

	boolean isIpRecvOrigDestAddr() throws IOException {
		return isIpRecvOrigDestAddr(this.intValue()) != 0;
	}

	PeerCredentials getPeerCredentials() throws IOException {
		return getPeerCredentials(this.intValue());
	}

	long sendFile(DefaultFileRegion src, long baseOffset, long offset, long length) throws IOException {
		src.open();
		long res = sendFile(this.intValue(), src, baseOffset, offset, length);
		return res >= 0L ? res : (long)Errors.ioResult("sendfile", (int)res, SENDFILE_CONNECTION_RESET_EXCEPTION, SENDFILE_CLOSED_CHANNEL_EXCEPTION);
	}

	public static LinuxSocket newSocketStream() {
		return new LinuxSocket(newSocketStream0());
	}

	public static LinuxSocket newSocketDgram() {
		return new LinuxSocket(newSocketDgram0());
	}

	public static LinuxSocket newSocketDomain() {
		return new LinuxSocket(newSocketDomain0());
	}

	private static native long sendFile(int integer, DefaultFileRegion defaultFileRegion, long long3, long long4, long long5) throws IOException;

	private static native int getTcpDeferAccept(int integer) throws IOException;

	private static native int isTcpQuickAck(int integer) throws IOException;

	private static native int isTcpCork(int integer) throws IOException;

	private static native int getTcpNotSentLowAt(int integer) throws IOException;

	private static native int getTcpKeepIdle(int integer) throws IOException;

	private static native int getTcpKeepIntvl(int integer) throws IOException;

	private static native int getTcpKeepCnt(int integer) throws IOException;

	private static native int getTcpUserTimeout(int integer) throws IOException;

	private static native int isIpFreeBind(int integer) throws IOException;

	private static native int isIpTransparent(int integer) throws IOException;

	private static native int isIpRecvOrigDestAddr(int integer) throws IOException;

	private static native void getTcpInfo(int integer, long[] arr) throws IOException;

	private static native PeerCredentials getPeerCredentials(int integer) throws IOException;

	private static native int isTcpFastOpenConnect(int integer) throws IOException;

	private static native void setTcpDeferAccept(int integer1, int integer2) throws IOException;

	private static native void setTcpQuickAck(int integer1, int integer2) throws IOException;

	private static native void setTcpCork(int integer1, int integer2) throws IOException;

	private static native void setTcpNotSentLowAt(int integer1, int integer2) throws IOException;

	private static native void setTcpFastOpen(int integer1, int integer2) throws IOException;

	private static native void setTcpFastOpenConnect(int integer1, int integer2) throws IOException;

	private static native void setTcpKeepIdle(int integer1, int integer2) throws IOException;

	private static native void setTcpKeepIntvl(int integer1, int integer2) throws IOException;

	private static native void setTcpKeepCnt(int integer1, int integer2) throws IOException;

	private static native void setTcpUserTimeout(int integer1, int integer2) throws IOException;

	private static native void setIpFreeBind(int integer1, int integer2) throws IOException;

	private static native void setIpTransparent(int integer1, int integer2) throws IOException;

	private static native void setIpRecvOrigDestAddr(int integer1, int integer2) throws IOException;

	private static native void setTcpMd5Sig(int integer1, byte[] arr2, int integer3, byte[] arr4) throws IOException;
}
