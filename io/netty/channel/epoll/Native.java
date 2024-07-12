package io.netty.channel.epoll;

import io.netty.channel.epoll.NativeDatagramPacketArray.NativeDatagramPacket;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.channel.unix.Errors.NativeIoException;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.Locale;

public final class Native {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(Native.class);
	public static final int EPOLLIN;
	public static final int EPOLLOUT;
	public static final int EPOLLRDHUP;
	public static final int EPOLLET;
	public static final int EPOLLERR;
	public static final boolean IS_SUPPORTING_SENDMMSG;
	public static final boolean IS_SUPPORTING_TCP_FASTOPEN;
	public static final int TCP_MD5SIG_MAXKEYLEN;
	public static final String KERNEL_VERSION;
	private static final NativeIoException SENDMMSG_CONNECTION_RESET_EXCEPTION;
	private static final NativeIoException SPLICE_CONNECTION_RESET_EXCEPTION;
	private static final ClosedChannelException SENDMMSG_CLOSED_CHANNEL_EXCEPTION;
	private static final ClosedChannelException SPLICE_CLOSED_CHANNEL_EXCEPTION;

	public static FileDescriptor newEventFd() {
		return new FileDescriptor(eventFd());
	}

	public static FileDescriptor newTimerFd() {
		return new FileDescriptor(timerFd());
	}

	private static native int eventFd();

	private static native int timerFd();

	public static native void eventFdWrite(int integer, long long2);

	public static native void eventFdRead(int integer);

	static native void timerFdRead(int integer);

	public static FileDescriptor newEpollCreate() {
		return new FileDescriptor(epollCreate());
	}

	private static native int epollCreate();

	public static int epollWait(FileDescriptor epollFd, EpollEventArray events, FileDescriptor timerFd, int timeoutSec, int timeoutNs) throws IOException {
		int ready = epollWait0(epollFd.intValue(), events.memoryAddress(), events.length(), timerFd.intValue(), timeoutSec, timeoutNs);
		if (ready < 0) {
			throw Errors.newIOException("epoll_wait", ready);
		} else {
			return ready;
		}
	}

	private static native int epollWait0(int integer1, long long2, int integer3, int integer4, int integer5, int integer6);

	public static void epollCtlAdd(int efd, int fd, int flags) throws IOException {
		int res = epollCtlAdd0(efd, fd, flags);
		if (res < 0) {
			throw Errors.newIOException("epoll_ctl", res);
		}
	}

	private static native int epollCtlAdd0(int integer1, int integer2, int integer3);

	public static void epollCtlMod(int efd, int fd, int flags) throws IOException {
		int res = epollCtlMod0(efd, fd, flags);
		if (res < 0) {
			throw Errors.newIOException("epoll_ctl", res);
		}
	}

	private static native int epollCtlMod0(int integer1, int integer2, int integer3);

	public static void epollCtlDel(int efd, int fd) throws IOException {
		int res = epollCtlDel0(efd, fd);
		if (res < 0) {
			throw Errors.newIOException("epoll_ctl", res);
		}
	}

	private static native int epollCtlDel0(int integer1, int integer2);

	public static int splice(int fd, long offIn, int fdOut, long offOut, long len) throws IOException {
		int res = splice0(fd, offIn, fdOut, offOut, len);
		return res >= 0 ? res : Errors.ioResult("splice", res, SPLICE_CONNECTION_RESET_EXCEPTION, SPLICE_CLOSED_CHANNEL_EXCEPTION);
	}

	private static native int splice0(int integer1, long long2, int integer3, long long4, long long5);

	public static int sendmmsg(int fd, NativeDatagramPacket[] msgs, int offset, int len) throws IOException {
		int res = sendmmsg0(fd, msgs, offset, len);
		return res >= 0 ? res : Errors.ioResult("sendmmsg", res, SENDMMSG_CONNECTION_RESET_EXCEPTION, SENDMMSG_CLOSED_CHANNEL_EXCEPTION);
	}

	private static native int sendmmsg0(int integer1, NativeDatagramPacket[] arr, int integer3, int integer4);

	public static native int sizeofEpollEvent();

	public static native int offsetofEpollData();

	private static void loadNativeLibrary() {
		String name = SystemPropertyUtil.get("os.name").toLowerCase(Locale.UK).trim();
		if (!name.startsWith("linux")) {
			throw new IllegalStateException("Only supported on Linux");
		} else {
			String staticLibName = "netty_transport_native_epoll";
			String sharedLibName = staticLibName + '_' + PlatformDependent.normalizedArch();
			ClassLoader cl = PlatformDependent.getClassLoader(Native.class);

			try {
				NativeLibraryLoader.load(sharedLibName, cl);
			} catch (UnsatisfiedLinkError var7) {
				UnsatisfiedLinkError e1 = var7;

				try {
					NativeLibraryLoader.load(staticLibName, cl);
					logger.debug("Failed to load {}", sharedLibName, e1);
				} catch (UnsatisfiedLinkError var6) {
					ThrowableUtil.addSuppressed(var7, var6);
					throw var7;
				}
			}
		}
	}

	private Native() {
	}

	static {
		try {
			offsetofEpollData();
		} catch (UnsatisfiedLinkError var1) {
			loadNativeLibrary();
		}

		Socket.initialize();
		EPOLLIN = NativeStaticallyReferencedJniMethods.epollin();
		EPOLLOUT = NativeStaticallyReferencedJniMethods.epollout();
		EPOLLRDHUP = NativeStaticallyReferencedJniMethods.epollrdhup();
		EPOLLET = NativeStaticallyReferencedJniMethods.epollet();
		EPOLLERR = NativeStaticallyReferencedJniMethods.epollerr();
		IS_SUPPORTING_SENDMMSG = NativeStaticallyReferencedJniMethods.isSupportingSendmmsg();
		IS_SUPPORTING_TCP_FASTOPEN = NativeStaticallyReferencedJniMethods.isSupportingTcpFastopen();
		TCP_MD5SIG_MAXKEYLEN = NativeStaticallyReferencedJniMethods.tcpMd5SigMaxKeyLen();
		KERNEL_VERSION = NativeStaticallyReferencedJniMethods.kernelVersion();
		SENDMMSG_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "sendmmsg(...)");
		SPLICE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "splice(...)");
		SENDMMSG_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:sendmmsg(...)", Errors.ERRNO_EPIPE_NEGATIVE);
		SPLICE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:splice(...)", Errors.ERRNO_EPIPE_NEGATIVE);
	}
}
