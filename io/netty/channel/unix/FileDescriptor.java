package io.netty.channel.unix;

import io.netty.channel.unix.Errors.NativeIoException;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class FileDescriptor {
	private static final ClosedChannelException WRITE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), FileDescriptor.class, "write(..)"
	);
	private static final ClosedChannelException WRITE_ADDRESS_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), FileDescriptor.class, "writeAddress(..)"
	);
	private static final ClosedChannelException WRITEV_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), FileDescriptor.class, "writev(..)"
	);
	private static final ClosedChannelException WRITEV_ADDRESSES_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), FileDescriptor.class, "writevAddresses(..)"
	);
	private static final ClosedChannelException READ_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), FileDescriptor.class, "read(..)"
	);
	private static final ClosedChannelException READ_ADDRESS_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(
		new ClosedChannelException(), FileDescriptor.class, "readAddress(..)"
	);
	private static final NativeIoException WRITE_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:write", Errors.ERRNO_EPIPE_NEGATIVE), FileDescriptor.class, "write(..)"
	);
	private static final NativeIoException WRITE_ADDRESS_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:write", Errors.ERRNO_EPIPE_NEGATIVE), FileDescriptor.class, "writeAddress(..)"
	);
	private static final NativeIoException WRITEV_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:writev", Errors.ERRNO_EPIPE_NEGATIVE), FileDescriptor.class, "writev(..)"
	);
	private static final NativeIoException WRITEV_ADDRESSES_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:writev", Errors.ERRNO_EPIPE_NEGATIVE), FileDescriptor.class, "writeAddresses(..)"
	);
	private static final NativeIoException READ_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:read", Errors.ERRNO_ECONNRESET_NEGATIVE), FileDescriptor.class, "read(..)"
	);
	private static final NativeIoException READ_ADDRESS_CONNECTION_RESET_EXCEPTION = ThrowableUtil.unknownStackTrace(
		Errors.newConnectionResetException("syscall:read", Errors.ERRNO_ECONNRESET_NEGATIVE), FileDescriptor.class, "readAddress(..)"
	);
	private static final AtomicIntegerFieldUpdater<FileDescriptor> stateUpdater = AtomicIntegerFieldUpdater.newUpdater(FileDescriptor.class, "state");
	private static final int STATE_CLOSED_MASK = 1;
	private static final int STATE_INPUT_SHUTDOWN_MASK = 2;
	private static final int STATE_OUTPUT_SHUTDOWN_MASK = 4;
	private static final int STATE_ALL_MASK = 7;
	volatile int state;
	final int fd;

	public FileDescriptor(int fd) {
		if (fd < 0) {
			throw new IllegalArgumentException("fd must be >= 0");
		} else {
			this.fd = fd;
		}
	}

	public final int intValue() {
		return this.fd;
	}

	public void close() throws IOException {
		int state;
		do {
			state = this.state;
			if (isClosed(state)) {
				return;
			}
		} while (!this.casState(state, state | 7));

		state = close(this.fd);
		if (state < 0) {
			throw Errors.newIOException("close", state);
		}
	}

	public boolean isOpen() {
		return !isClosed(this.state);
	}

	public final int write(ByteBuffer buf, int pos, int limit) throws IOException {
		int res = write(this.fd, buf, pos, limit);
		return res >= 0 ? res : Errors.ioResult("write", res, WRITE_CONNECTION_RESET_EXCEPTION, WRITE_CLOSED_CHANNEL_EXCEPTION);
	}

	public final int writeAddress(long address, int pos, int limit) throws IOException {
		int res = writeAddress(this.fd, address, pos, limit);
		return res >= 0 ? res : Errors.ioResult("writeAddress", res, WRITE_ADDRESS_CONNECTION_RESET_EXCEPTION, WRITE_ADDRESS_CLOSED_CHANNEL_EXCEPTION);
	}

	public final long writev(ByteBuffer[] buffers, int offset, int length, long maxBytesToWrite) throws IOException {
		long res = writev(this.fd, buffers, offset, Math.min(Limits.IOV_MAX, length), maxBytesToWrite);
		return res >= 0L ? res : (long)Errors.ioResult("writev", (int)res, WRITEV_CONNECTION_RESET_EXCEPTION, WRITEV_CLOSED_CHANNEL_EXCEPTION);
	}

	public final long writevAddresses(long memoryAddress, int length) throws IOException {
		long res = writevAddresses(this.fd, memoryAddress, length);
		return res >= 0L
			? res
			: (long)Errors.ioResult("writevAddresses", (int)res, WRITEV_ADDRESSES_CONNECTION_RESET_EXCEPTION, WRITEV_ADDRESSES_CLOSED_CHANNEL_EXCEPTION);
	}

	public final int read(ByteBuffer buf, int pos, int limit) throws IOException {
		int res = read(this.fd, buf, pos, limit);
		if (res > 0) {
			return res;
		} else {
			return res == 0 ? -1 : Errors.ioResult("read", res, READ_CONNECTION_RESET_EXCEPTION, READ_CLOSED_CHANNEL_EXCEPTION);
		}
	}

	public final int readAddress(long address, int pos, int limit) throws IOException {
		int res = readAddress(this.fd, address, pos, limit);
		if (res > 0) {
			return res;
		} else {
			return res == 0 ? -1 : Errors.ioResult("readAddress", res, READ_ADDRESS_CONNECTION_RESET_EXCEPTION, READ_ADDRESS_CLOSED_CHANNEL_EXCEPTION);
		}
	}

	public String toString() {
		return "FileDescriptor{fd=" + this.fd + '}';
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return !(o instanceof FileDescriptor) ? false : this.fd == ((FileDescriptor)o).fd;
		}
	}

	public int hashCode() {
		return this.fd;
	}

	public static FileDescriptor from(String path) throws IOException {
		ObjectUtil.checkNotNull(path, "path");
		int res = open(path);
		if (res < 0) {
			throw Errors.newIOException("open", res);
		} else {
			return new FileDescriptor(res);
		}
	}

	public static FileDescriptor from(File file) throws IOException {
		return from(ObjectUtil.checkNotNull(file, "file").getPath());
	}

	public static FileDescriptor[] pipe() throws IOException {
		long res = newPipe();
		if (res < 0L) {
			throw Errors.newIOException("newPipe", (int)res);
		} else {
			return new FileDescriptor[]{new FileDescriptor((int)(res >>> 32)), new FileDescriptor((int)res)};
		}
	}

	final boolean casState(int expected, int update) {
		return stateUpdater.compareAndSet(this, expected, update);
	}

	static boolean isClosed(int state) {
		return (state & 1) != 0;
	}

	static boolean isInputShutdown(int state) {
		return (state & 2) != 0;
	}

	static boolean isOutputShutdown(int state) {
		return (state & 4) != 0;
	}

	static int inputShutdown(int state) {
		return state | 2;
	}

	static int outputShutdown(int state) {
		return state | 4;
	}

	private static native int open(String string);

	private static native int close(int integer);

	private static native int write(int integer1, ByteBuffer byteBuffer, int integer3, int integer4);

	private static native int writeAddress(int integer1, long long2, int integer3, int integer4);

	private static native long writev(int integer1, ByteBuffer[] arr, int integer3, int integer4, long long5);

	private static native long writevAddresses(int integer1, long long2, int integer3);

	private static native int read(int integer1, ByteBuffer byteBuffer, int integer3, int integer4);

	private static native int readAddress(int integer1, long long2, int integer3, int integer4);

	private static native long newPipe();
}
