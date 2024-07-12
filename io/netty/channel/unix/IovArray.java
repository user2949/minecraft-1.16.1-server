package io.netty.channel.unix;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOutboundBuffer.MessageProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

public final class IovArray implements MessageProcessor {
	private static final int ADDRESS_SIZE = PlatformDependent.addressSize();
	private static final int IOV_SIZE = 2 * ADDRESS_SIZE;
	private static final int CAPACITY = Limits.IOV_MAX * IOV_SIZE;
	private final long memoryAddress;
	private int count;
	private long size;
	private long maxBytes = Limits.SSIZE_MAX;

	public IovArray() {
		this.memoryAddress = PlatformDependent.allocateMemory((long)CAPACITY);
	}

	public void clear() {
		this.count = 0;
		this.size = 0L;
	}

	public boolean add(ByteBuf buf) {
		if (this.count == Limits.IOV_MAX) {
			return false;
		} else if (buf.hasMemoryAddress() && buf.nioBufferCount() == 1) {
			int len = buf.readableBytes();
			return len == 0 || this.add(buf.memoryAddress(), buf.readerIndex(), len);
		} else {
			ByteBuffer[] buffers = buf.nioBuffers();

			for (ByteBuffer nioBuffer : buffers) {
				int len = nioBuffer.remaining();
				if (len != 0 && (!this.add(PlatformDependent.directBufferAddress(nioBuffer), nioBuffer.position(), len) || this.count == Limits.IOV_MAX)) {
					return false;
				}
			}

			return true;
		}
	}

	private boolean add(long addr, int offset, int len) {
		long baseOffset = this.memoryAddress(this.count);
		long lengthOffset = baseOffset + (long)ADDRESS_SIZE;
		if (this.maxBytes - (long)len < this.size && this.count > 0) {
			return false;
		} else {
			this.size += (long)len;
			this.count++;
			if (ADDRESS_SIZE == 8) {
				PlatformDependent.putLong(baseOffset, addr + (long)offset);
				PlatformDependent.putLong(lengthOffset, (long)len);
			} else {
				assert ADDRESS_SIZE == 4;

				PlatformDependent.putInt(baseOffset, (int)addr + offset);
				PlatformDependent.putInt(lengthOffset, len);
			}

			return true;
		}
	}

	public int count() {
		return this.count;
	}

	public long size() {
		return this.size;
	}

	public void maxBytes(long maxBytes) {
		this.maxBytes = Math.min(Limits.SSIZE_MAX, ObjectUtil.checkPositive(maxBytes, "maxBytes"));
	}

	public long maxBytes() {
		return this.maxBytes;
	}

	public long memoryAddress(int offset) {
		return this.memoryAddress + (long)(IOV_SIZE * offset);
	}

	public void release() {
		PlatformDependent.freeMemory(this.memoryAddress);
	}

	@Override
	public boolean processMessage(Object msg) throws Exception {
		return msg instanceof ByteBuf && this.add((ByteBuf)msg);
	}
}
