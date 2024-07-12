package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

final class PooledDirectByteBuf extends PooledByteBuf<ByteBuffer> {
	private static final Recycler<PooledDirectByteBuf> RECYCLER = new Recycler<PooledDirectByteBuf>() {
		protected PooledDirectByteBuf newObject(Handle<PooledDirectByteBuf> handle) {
			return new PooledDirectByteBuf(handle, 0);
		}
	};

	static PooledDirectByteBuf newInstance(int maxCapacity) {
		PooledDirectByteBuf buf = RECYCLER.get();
		buf.reuse(maxCapacity);
		return buf;
	}

	private PooledDirectByteBuf(Handle<PooledDirectByteBuf> recyclerHandle, int maxCapacity) {
		super(recyclerHandle, maxCapacity);
	}

	protected ByteBuffer newInternalNioBuffer(ByteBuffer memory) {
		return memory.duplicate();
	}

	@Override
	public boolean isDirect() {
		return true;
	}

	@Override
	protected byte _getByte(int index) {
		return this.memory.get(this.idx(index));
	}

	@Override
	protected short _getShort(int index) {
		return this.memory.getShort(this.idx(index));
	}

	@Override
	protected short _getShortLE(int index) {
		return ByteBufUtil.swapShort(this._getShort(index));
	}

	@Override
	protected int _getUnsignedMedium(int index) {
		index = this.idx(index);
		return (this.memory.get(index) & 0xFF) << 16 | (this.memory.get(index + 1) & 0xFF) << 8 | this.memory.get(index + 2) & 0xFF;
	}

	@Override
	protected int _getUnsignedMediumLE(int index) {
		index = this.idx(index);
		return this.memory.get(index) & 0xFF | (this.memory.get(index + 1) & 0xFF) << 8 | (this.memory.get(index + 2) & 0xFF) << 16;
	}

	@Override
	protected int _getInt(int index) {
		return this.memory.getInt(this.idx(index));
	}

	@Override
	protected int _getIntLE(int index) {
		return ByteBufUtil.swapInt(this._getInt(index));
	}

	@Override
	protected long _getLong(int index) {
		return this.memory.getLong(this.idx(index));
	}

	@Override
	protected long _getLongLE(int index) {
		return ByteBufUtil.swapLong(this._getLong(index));
	}

	@Override
	public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
		this.checkDstIndex(index, length, dstIndex, dst.capacity());
		if (dst.hasArray()) {
			this.getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
		} else if (dst.nioBufferCount() > 0) {
			for (ByteBuffer bb : dst.nioBuffers(dstIndex, length)) {
				int bbLen = bb.remaining();
				this.getBytes(index, bb);
				index += bbLen;
			}
		} else {
			dst.setBytes(dstIndex, this, index, length);
		}

		return this;
	}

	@Override
	public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
		this.getBytes(index, dst, dstIndex, length, false);
		return this;
	}

	private void getBytes(int index, byte[] dst, int dstIndex, int length, boolean internal) {
		this.checkDstIndex(index, length, dstIndex, dst.length);
		ByteBuffer tmpBuf;
		if (internal) {
			tmpBuf = this.internalNioBuffer();
		} else {
			tmpBuf = this.memory.duplicate();
		}

		index = this.idx(index);
		tmpBuf.clear().position(index).limit(index + length);
		tmpBuf.get(dst, dstIndex, length);
	}

	@Override
	public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
		this.checkReadableBytes(length);
		this.getBytes(this.readerIndex, dst, dstIndex, length, true);
		this.readerIndex += length;
		return this;
	}

	@Override
	public ByteBuf getBytes(int index, ByteBuffer dst) {
		this.getBytes(index, dst, false);
		return this;
	}

	private void getBytes(int index, ByteBuffer dst, boolean internal) {
		this.checkIndex(index, dst.remaining());
		ByteBuffer tmpBuf;
		if (internal) {
			tmpBuf = this.internalNioBuffer();
		} else {
			tmpBuf = this.memory.duplicate();
		}

		index = this.idx(index);
		tmpBuf.clear().position(index).limit(index + dst.remaining());
		dst.put(tmpBuf);
	}

	@Override
	public ByteBuf readBytes(ByteBuffer dst) {
		int length = dst.remaining();
		this.checkReadableBytes(length);
		this.getBytes(this.readerIndex, dst, true);
		this.readerIndex += length;
		return this;
	}

	@Override
	public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
		this.getBytes(index, out, length, false);
		return this;
	}

	private void getBytes(int index, OutputStream out, int length, boolean internal) throws IOException {
		this.checkIndex(index, length);
		if (length != 0) {
			ByteBufUtil.readBytes(this.alloc(), internal ? this.internalNioBuffer() : this.memory.duplicate(), this.idx(index), length, out);
		}
	}

	@Override
	public ByteBuf readBytes(OutputStream out, int length) throws IOException {
		this.checkReadableBytes(length);
		this.getBytes(this.readerIndex, out, length, true);
		this.readerIndex += length;
		return this;
	}

	@Override
	public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
		return this.getBytes(index, out, length, false);
	}

	private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
		this.checkIndex(index, length);
		if (length == 0) {
			return 0;
		} else {
			ByteBuffer tmpBuf;
			if (internal) {
				tmpBuf = this.internalNioBuffer();
			} else {
				tmpBuf = this.memory.duplicate();
			}

			index = this.idx(index);
			tmpBuf.clear().position(index).limit(index + length);
			return out.write(tmpBuf);
		}
	}

	@Override
	public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
		return this.getBytes(index, out, position, length, false);
	}

	private int getBytes(int index, FileChannel out, long position, int length, boolean internal) throws IOException {
		this.checkIndex(index, length);
		if (length == 0) {
			return 0;
		} else {
			ByteBuffer tmpBuf = internal ? this.internalNioBuffer() : this.memory.duplicate();
			index = this.idx(index);
			tmpBuf.clear().position(index).limit(index + length);
			return out.write(tmpBuf, position);
		}
	}

	@Override
	public int readBytes(GatheringByteChannel out, int length) throws IOException {
		this.checkReadableBytes(length);
		int readBytes = this.getBytes(this.readerIndex, out, length, true);
		this.readerIndex += readBytes;
		return readBytes;
	}

	@Override
	public int readBytes(FileChannel out, long position, int length) throws IOException {
		this.checkReadableBytes(length);
		int readBytes = this.getBytes(this.readerIndex, out, position, length, true);
		this.readerIndex += readBytes;
		return readBytes;
	}

	@Override
	protected void _setByte(int index, int value) {
		this.memory.put(this.idx(index), (byte)value);
	}

	@Override
	protected void _setShort(int index, int value) {
		this.memory.putShort(this.idx(index), (short)value);
	}

	@Override
	protected void _setShortLE(int index, int value) {
		this._setShort(index, ByteBufUtil.swapShort((short)value));
	}

	@Override
	protected void _setMedium(int index, int value) {
		index = this.idx(index);
		this.memory.put(index, (byte)(value >>> 16));
		this.memory.put(index + 1, (byte)(value >>> 8));
		this.memory.put(index + 2, (byte)value);
	}

	@Override
	protected void _setMediumLE(int index, int value) {
		index = this.idx(index);
		this.memory.put(index, (byte)value);
		this.memory.put(index + 1, (byte)(value >>> 8));
		this.memory.put(index + 2, (byte)(value >>> 16));
	}

	@Override
	protected void _setInt(int index, int value) {
		this.memory.putInt(this.idx(index), value);
	}

	@Override
	protected void _setIntLE(int index, int value) {
		this._setInt(index, ByteBufUtil.swapInt(value));
	}

	@Override
	protected void _setLong(int index, long value) {
		this.memory.putLong(this.idx(index), value);
	}

	@Override
	protected void _setLongLE(int index, long value) {
		this._setLong(index, ByteBufUtil.swapLong(value));
	}

	@Override
	public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
		this.checkSrcIndex(index, length, srcIndex, src.capacity());
		if (src.hasArray()) {
			this.setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
		} else if (src.nioBufferCount() > 0) {
			for (ByteBuffer bb : src.nioBuffers(srcIndex, length)) {
				int bbLen = bb.remaining();
				this.setBytes(index, bb);
				index += bbLen;
			}
		} else {
			src.getBytes(srcIndex, this, index, length);
		}

		return this;
	}

	@Override
	public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
		this.checkSrcIndex(index, length, srcIndex, src.length);
		ByteBuffer tmpBuf = this.internalNioBuffer();
		index = this.idx(index);
		tmpBuf.clear().position(index).limit(index + length);
		tmpBuf.put(src, srcIndex, length);
		return this;
	}

	@Override
	public ByteBuf setBytes(int index, ByteBuffer src) {
		this.checkIndex(index, src.remaining());
		ByteBuffer tmpBuf = this.internalNioBuffer();
		if (src == tmpBuf) {
			src = src.duplicate();
		}

		index = this.idx(index);
		tmpBuf.clear().position(index).limit(index + src.remaining());
		tmpBuf.put(src);
		return this;
	}

	@Override
	public int setBytes(int index, InputStream in, int length) throws IOException {
		this.checkIndex(index, length);
		byte[] tmp = new byte[length];
		int readBytes = in.read(tmp);
		if (readBytes <= 0) {
			return readBytes;
		} else {
			ByteBuffer tmpBuf = this.internalNioBuffer();
			tmpBuf.clear().position(this.idx(index));
			tmpBuf.put(tmp, 0, readBytes);
			return readBytes;
		}
	}

	@Override
	public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
		this.checkIndex(index, length);
		ByteBuffer tmpBuf = this.internalNioBuffer();
		index = this.idx(index);
		tmpBuf.clear().position(index).limit(index + length);

		try {
			return in.read(tmpBuf);
		} catch (ClosedChannelException var6) {
			return -1;
		}
	}

	@Override
	public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
		this.checkIndex(index, length);
		ByteBuffer tmpBuf = this.internalNioBuffer();
		index = this.idx(index);
		tmpBuf.clear().position(index).limit(index + length);

		try {
			return in.read(tmpBuf, position);
		} catch (ClosedChannelException var8) {
			return -1;
		}
	}

	@Override
	public ByteBuf copy(int index, int length) {
		this.checkIndex(index, length);
		ByteBuf copy = this.alloc().directBuffer(length, this.maxCapacity());
		copy.writeBytes(this, index, length);
		return copy;
	}

	@Override
	public int nioBufferCount() {
		return 1;
	}

	@Override
	public ByteBuffer nioBuffer(int index, int length) {
		this.checkIndex(index, length);
		index = this.idx(index);
		return ((ByteBuffer)this.memory.duplicate().position(index).limit(index + length)).slice();
	}

	@Override
	public ByteBuffer[] nioBuffers(int index, int length) {
		return new ByteBuffer[]{this.nioBuffer(index, length)};
	}

	@Override
	public ByteBuffer internalNioBuffer(int index, int length) {
		this.checkIndex(index, length);
		index = this.idx(index);
		return (ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length);
	}

	@Override
	public boolean hasArray() {
		return false;
	}

	@Override
	public byte[] array() {
		throw new UnsupportedOperationException("direct buffer");
	}

	@Override
	public int arrayOffset() {
		throw new UnsupportedOperationException("direct buffer");
	}

	@Override
	public boolean hasMemoryAddress() {
		return false;
	}

	@Override
	public long memoryAddress() {
		throw new UnsupportedOperationException();
	}
}
