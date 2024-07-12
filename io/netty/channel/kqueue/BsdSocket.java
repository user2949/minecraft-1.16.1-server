package io.netty.channel.kqueue;

import io.netty.channel.DefaultFileRegion;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.PeerCredentials;
import io.netty.channel.unix.Socket;
import io.netty.channel.unix.Errors.NativeIoException;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;

final class BsdSocket extends Socket {
	private static final NativeIoException SENDFILE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException(
		"syscall:sendfile", Errors.ERRNO_EPIPE_NEGATIVE
	);
	private static final ClosedChannelException SENDFILE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), Native.class, "sendfile(..)"
	);
	private static final int APPLE_SND_LOW_AT_MAX = 131072;
	private static final int FREEBSD_SND_LOW_AT_MAX = 32768;
	static final int BSD_SND_LOW_AT_MAX = Math.min(131072, 32768);

	BsdSocket(int fd) {
		super(fd);
	}

	void setAcceptFilter(AcceptFilter acceptFilter) throws IOException {
		setAcceptFilter(this.intValue(), acceptFilter.filterName(), acceptFilter.filterArgs());
	}

	void setTcpNoPush(boolean tcpNoPush) throws IOException {
		setTcpNoPush(this.intValue(), tcpNoPush ? 1 : 0);
	}

	void setSndLowAt(int lowAt) throws IOException {
		setSndLowAt(this.intValue(), lowAt);
	}

	boolean isTcpNoPush() throws IOException {
		return getTcpNoPush(this.intValue()) != 0;
	}

	int getSndLowAt() throws IOException {
		return getSndLowAt(this.intValue());
	}

	AcceptFilter getAcceptFilter() throws IOException {
		String[] result = getAcceptFilter(this.intValue());
		return result == null ? AcceptFilter.PLATFORM_UNSUPPORTED : new AcceptFilter(result[0], result[1]);
	}

	PeerCredentials getPeerCredentials() throws IOException {
		return getPeerCredentials(this.intValue());
	}

	long sendFile(DefaultFileRegion src, long baseOffset, long offset, long length) throws IOException {
		src.open();
		long res = sendFile(this.intValue(), src, baseOffset, offset, length);
		return res >= 0L ? res : (long)Errors.ioResult("sendfile", (int)res, SENDFILE_CONNECTION_RESET_EXCEPTION, SENDFILE_CLOSED_CHANNEL_EXCEPTION);
	}

	public static BsdSocket newSocketStream() {
		return new BsdSocket(newSocketStream0());
	}

	public static BsdSocket newSocketDgram() {
		return new BsdSocket(newSocketDgram0());
	}

	public static BsdSocket newSocketDomain() {
		return new BsdSocket(newSocketDomain0());
	}

	private static native long sendFile(int integer, DefaultFileRegion defaultFileRegion, long long3, long long4, long long5) throws IOException;

	private static native String[] getAcceptFilter(int integer) throws IOException;

	private static native int getTcpNoPush(int integer) throws IOException;

	private static native int getSndLowAt(int integer) throws IOException;

	private static native PeerCredentials getPeerCredentials(int integer) throws IOException;

	private static native void setAcceptFilter(int integer, String string2, String string3) throws IOException;

	private static native void setTcpNoPush(int integer1, int integer2) throws IOException;

	private static native void setSndLowAt(int integer1, int integer2) throws IOException;
}
