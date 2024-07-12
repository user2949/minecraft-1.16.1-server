package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

class PooledHeapByteBuf extends PooledByteBuf<byte[]> {
	private static final Recycler<PooledHeapByteBuf> RECYCLER = new Recycler<PooledHeapByteBuf>() {
		protected PooledHeapByteBuf newObject(Handle<PooledHeapByteBuf> handle) {
			return new PooledHeapByteBuf(handle, 0);
		}
	};

	static PooledHeapByteBuf newInstance(int maxCapacity) {
		PooledHeapByteBuf buf = RECYCLER.get();
		buf.reuse(maxCapacity);
		return buf;
	}

	PooledHeapByteBuf(Handle<? extends PooledHeapByteBuf> recyclerHandle, int maxCapacity) {
		super(recyclerHandle, maxCapacity);
	}

	@Override
	public final boolean isDirect() {
		return false;
	}

	@Override
	protected byte _getByte(int index) {
		return HeapByteBufUtil.getByte(this.memory, this.idx(index));
	}

	@Override
	protected short _getShort(int index) {
		return HeapByteBufUtil.getShort(this.memory, this.idx(index));
	}

	@Override
	protected short _getShortLE(int index) {
		return HeapByteBufUtil.getShortLE(this.memory, this.idx(index));
	}

	@Override
	protected int _getUnsignedMedium(int index) {
		return HeapByteBufUtil.getUnsignedMedium(this.memory, this.idx(index));
	}

	@Override
	protected int _getUnsignedMediumLE(int index) {
		return HeapByteBufUtil.getUnsignedMediumLE(this.memory, this.idx(index));
	}

	@Override
	protected int _getInt(int index) {
		return HeapByteBufUtil.getInt(this.memory, this.idx(index));
	}

	@Override
	protected int _getIntLE(int index) {
		return HeapByteBufUtil.getIntLE(this.memory, this.idx(index));
	}

	@Override
	protected long _getLong(int index) {
		return HeapByteBufUtil.getLong(this.memory, this.idx(index));
	}

	@Override
	protected long _getLongLE(int index) {
		return HeapByteBufUtil.getLongLE(this.memory, this.idx(index));
	}

	@Override
	public final ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
		this.checkDstIndex(index, length, dstIndex, dst.capacity());
		if (dst.hasMemoryAddress()) {
			PlatformDependent.copyMemory(this.memory, this.idx(index), dst.memoryAddress() + (long)dstIndex, (long)length);
		} else if (dst.hasArray()) {
			this.getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
		} else {
			dst.setBytes(dstIndex, this.memory, this.idx(index), length);
		}

		return this;
	}

	@Override
	public final ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
		this.checkDstIndex(index, length, dstIndex, dst.length);
		System.arraycopy(this.memory, this.idx(index), dst, dstIndex, length);
		return this;
	}

	@Override
	public final ByteBuf getBytes(int index, ByteBuffer dst) {
		this.checkIndex(index, dst.remaining());
		dst.put(this.memory, this.idx(index), dst.remaining());
		return this;
	}

	@Override
	public final ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
		this.checkIndex(index, length);
		out.write(this.memory, this.idx(index), length);
		return this;
	}

	@Override
	public final int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
		return this.getBytes(index, out, length, false);
	}

	private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
		this.checkIndex(index, length);
		index = this.idx(index);
		ByteBuffer tmpBuf;
		if (internal) {
			tmpBuf = this.internalNioBuffer();
		} else {
			tmpBuf = ByteBuffer.wrap(this.memory);
		}

		return out.write((ByteBuffer)tmpBuf.clear().position(index).limit(index + length));
	}

	@Override
	public final int getBytes(int index, FileChannel out, long position, int length) throws IOException {
		return this.getBytes(index, out, position, length, false);
	}

	private int getBytes(int index, FileChannel out, long position, int length, boolean internal) throws IOException {
		this.checkIndex(index, length);
		index = this.idx(index);
		ByteBuffer tmpBuf = internal ? this.internalNioBuffer() : ByteBuffer.wrap(this.memory);
		return out.write((ByteBuffer)tmpBuf.clear().position(index).limit(index + length), position);
	}

	@Override
	public final int readBytes(GatheringByteChannel out, int length) throws IOException {
		this.checkReadableBytes(length);
		int readBytes = this.getBytes(this.readerIndex, out, length, true);
		this.readerIndex += readBytes;
		return readBytes;
	}

	@Override
	public final int readBytes(FileChannel out, long position, int length) throws IOException {
		this.checkReadableBytes(length);
		int readBytes = this.getBytes(this.readerIndex, out, position, length, true);
		this.readerIndex += readBytes;
		return readBytes;
	}

	@Override
	protected void _setByte(int index, int value) {
		HeapByteBufUtil.setByte(this.memory, this.idx(index), value);
	}

	@Override
	protected void _setShort(int index, int value) {
		HeapByteBufUtil.setShort(this.memory, this.idx(index), value);
	}

	@Override
	protected void _setShortLE(int index, int value) {
		HeapByteBufUtil.setShortLE(this.memory, this.idx(index), value);
	}

	@Override
	protected void _setMedium(int index, int value) {
		HeapByteBufUtil.setMedium(this.memory, this.idx(index), value);
	}

	@Override
	protected void _setMediumLE(int index, int value) {
		HeapByteBufUtil.setMediumLE(this.memory, this.idx(index), value);
	}

	@Override
	protected void _setInt(int index, int value) {
		HeapByteBufUtil.setInt(this.memory, this.idx(index), value);
	}

	@Override
	protected void _setIntLE(int index, int value) {
		HeapByteBufUtil.setIntLE(this.memory, this.idx(index), value);
	}

	@Override
	protected void _setLong(int index, long value) {
		HeapByteBufUtil.setLong(this.memory, this.idx(index), value);
	}

	@Override
	protected void _setLongLE(int index, long value) {
		HeapByteBufUtil.setLongLE(this.memory, this.idx(index), value);
	}

	@Override
	public final ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
		this.checkSrcIndex(index, length, srcIndex, src.capacity());
		if (src.hasMemoryAddress()) {
			PlatformDependent.copyMemory(src.memoryAddress() + (long)srcIndex, this.memory, this.idx(index), (long)length);
		} else if (src.hasArray()) {
			this.setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
		} else {
			src.getBytes(srcIndex, this.memory, this.idx(index), length);
		}

		return this;
	}

	@Override
	public final ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
		this.checkSrcIndex(index, length, srcIndex, src.length);
		System.arraycopy(src, srcIndex, this.memory, this.idx(index), length);
		return this;
	}

	@Override
	public final ByteBuf setBytes(int index, ByteBuffer src) {
		int length = src.remaining();
		this.checkIndex(index, length);
		src.get(this.memory, this.idx(index), length);
		return this;
	}

	@Override
	public final int setBytes(int index, InputStream in, int length) throws IOException {
		this.checkIndex(index, length);
		return in.read(this.memory, this.idx(index), length);
	}

	@Override
	public final int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
		this.checkIndex(index, length);
		index = this.idx(index);

		try {
			return in.read((ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length));
		} catch (ClosedChannelException var5) {
			return -1;
		}
	}

	@Override
	public final int setBytes(int index, FileChannel in, long position, int length) throws IOException {
		this.checkIndex(index, length);
		index = this.idx(index);

		try {
			return in.read((ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length), position);
		} catch (ClosedChannelException var7) {
			return -1;
		}
	}

	@Override
	public final ByteBuf copy(int index, int length) {
		this.checkIndex(index, length);
		ByteBuf copy = this.alloc().heapBuffer(length, this.maxCapacity());
		copy.writeBytes(this.memory, this.idx(index), length);
		return copy;
	}

	@Override
	public final int nioBufferCount() {
		return 1;
	}

	@Override
	public final ByteBuffer[] nioBuffers(int index, int length) {
		return new ByteBuffer[]{this.nioBuffer(index, length)};
	}

	@Override
	public final ByteBuffer nioBuffer(int index, int length) {
		this.checkIndex(index, length);
		index = this.idx(index);
		ByteBuffer buf = ByteBuffer.wrap(this.memory, index, length);
		return buf.slice();
	}

	@Override
	public final ByteBuffer internalNioBuffer(int index, int length) {
		this.checkIndex(index, length);
		index = this.idx(index);
		return (ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length);
	}

	@Override
	public final boolean hasArray() {
		return true;
	}

	@Override
	public final byte[] array() {
		this.ensureAccessible();
		return this.memory;
	}

	@Override
	public final int arrayOffset() {
		return this.offset;
	}

	@Override
	public final boolean hasMemoryAddress() {
		return false;
	}

	@Override
	public final long memoryAddress() {
		throw new UnsupportedOperationException();
	}

	protected final ByteBuffer newInternalNioBuffer(byte[] memory) {
		return ByteBuffer.wrap(memory);
	}
}
