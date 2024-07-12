package io.netty.buffer;

import io.netty.util.ByteProcessor;
import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public abstract class ByteBuf implements ReferenceCounted, Comparable<ByteBuf> {
	public abstract int capacity();

	public abstract ByteBuf capacity(int integer);

	public abstract int maxCapacity();

	public abstract ByteBufAllocator alloc();

	@Deprecated
	public abstract ByteOrder order();

	@Deprecated
	public abstract ByteBuf order(ByteOrder byteOrder);

	public abstract ByteBuf unwrap();

	public abstract boolean isDirect();

	public abstract boolean isReadOnly();

	public abstract ByteBuf asReadOnly();

	public abstract int readerIndex();

	public abstract ByteBuf readerIndex(int integer);

	public abstract int writerIndex();

	public abstract ByteBuf writerIndex(int integer);

	public abstract ByteBuf setIndex(int integer1, int integer2);

	public abstract int readableBytes();

	public abstract int writableBytes();

	public abstract int maxWritableBytes();

	public abstract boolean isReadable();

	public abstract boolean isReadable(int integer);

	public abstract boolean isWritable();

	public abstract boolean isWritable(int integer);

	public abstract ByteBuf clear();

	public abstract ByteBuf markReaderIndex();

	public abstract ByteBuf resetReaderIndex();

	public abstract ByteBuf markWriterIndex();

	public abstract ByteBuf resetWriterIndex();

	public abstract ByteBuf discardReadBytes();

	public abstract ByteBuf discardSomeReadBytes();

	public abstract ByteBuf ensureWritable(int integer);

	public abstract int ensureWritable(int integer, boolean boolean2);

	public abstract boolean getBoolean(int integer);

	public abstract byte getByte(int integer);

	public abstract short getUnsignedByte(int integer);

	public abstract short getShort(int integer);

	public abstract short getShortLE(int integer);

	public abstract int getUnsignedShort(int integer);

	public abstract int getUnsignedShortLE(int integer);

	public abstract int getMedium(int integer);

	public abstract int getMediumLE(int integer);

	public abstract int getUnsignedMedium(int integer);

	public abstract int getUnsignedMediumLE(int integer);

	public abstract int getInt(int integer);

	public abstract int getIntLE(int integer);

	public abstract long getUnsignedInt(int integer);

	public abstract long getUnsignedIntLE(int integer);

	public abstract long getLong(int integer);

	public abstract long getLongLE(int integer);

	public abstract char getChar(int integer);

	public abstract float getFloat(int integer);

	public float getFloatLE(int index) {
		return Float.intBitsToFloat(this.getIntLE(index));
	}

	public abstract double getDouble(int integer);

	public double getDoubleLE(int index) {
		return Double.longBitsToDouble(this.getLongLE(index));
	}

	public abstract ByteBuf getBytes(int integer, ByteBuf byteBuf);

	public abstract ByteBuf getBytes(int integer1, ByteBuf byteBuf, int integer3);

	public abstract ByteBuf getBytes(int integer1, ByteBuf byteBuf, int integer3, int integer4);

	public abstract ByteBuf getBytes(int integer, byte[] arr);

	public abstract ByteBuf getBytes(int integer1, byte[] arr, int integer3, int integer4);

	public abstract ByteBuf getBytes(int integer, ByteBuffer byteBuffer);

	public abstract ByteBuf getBytes(int integer1, OutputStream outputStream, int integer3) throws IOException;

	public abstract int getBytes(int integer1, GatheringByteChannel gatheringByteChannel, int integer3) throws IOException;

	public abstract int getBytes(int integer1, FileChannel fileChannel, long long3, int integer4) throws IOException;

	public abstract CharSequence getCharSequence(int integer1, int integer2, Charset charset);

	public abstract ByteBuf setBoolean(int integer, boolean boolean2);

	public abstract ByteBuf setByte(int integer1, int integer2);

	public abstract ByteBuf setShort(int integer1, int integer2);

	public abstract ByteBuf setShortLE(int integer1, int integer2);

	public abstract ByteBuf setMedium(int integer1, int integer2);

	public abstract ByteBuf setMediumLE(int integer1, int integer2);

	public abstract ByteBuf setInt(int integer1, int integer2);

	public abstract ByteBuf setIntLE(int integer1, int integer2);

	public abstract ByteBuf setLong(int integer, long long2);

	public abstract ByteBuf setLongLE(int integer, long long2);

	public abstract ByteBuf setChar(int integer1, int integer2);

	public abstract ByteBuf setFloat(int integer, float float2);

	public ByteBuf setFloatLE(int index, float value) {
		return this.setIntLE(index, Float.floatToRawIntBits(value));
	}

	public abstract ByteBuf setDouble(int integer, double double2);

	public ByteBuf setDoubleLE(int index, double value) {
		return this.setLongLE(index, Double.doubleToRawLongBits(value));
	}

	public abstract ByteBuf setBytes(int integer, ByteBuf byteBuf);

	public abstract ByteBuf setBytes(int integer1, ByteBuf byteBuf, int integer3);

	public abstract ByteBuf setBytes(int integer1, ByteBuf byteBuf, int integer3, int integer4);

	public abstract ByteBuf setBytes(int integer, byte[] arr);

	public abstract ByteBuf setBytes(int integer1, byte[] arr, int integer3, int integer4);

	public abstract ByteBuf setBytes(int integer, ByteBuffer byteBuffer);

	public abstract int setBytes(int integer1, InputStream inputStream, int integer3) throws IOException;

	public abstract int setBytes(int integer1, ScatteringByteChannel scatteringByteChannel, int integer3) throws IOException;

	public abstract int setBytes(int integer1, FileChannel fileChannel, long long3, int integer4) throws IOException;

	public abstract ByteBuf setZero(int integer1, int integer2);

	public abstract int setCharSequence(int integer, CharSequence charSequence, Charset charset);

	public abstract boolean readBoolean();

	public abstract byte readByte();

	public abstract short readUnsignedByte();

	public abstract short readShort();

	public abstract short readShortLE();

	public abstract int readUnsignedShort();

	public abstract int readUnsignedShortLE();

	public abstract int readMedium();

	public abstract int readMediumLE();

	public abstract int readUnsignedMedium();

	public abstract int readUnsignedMediumLE();

	public abstract int readInt();

	public abstract int readIntLE();

	public abstract long readUnsignedInt();

	public abstract long readUnsignedIntLE();

	public abstract long readLong();

	public abstract long readLongLE();

	public abstract char readChar();

	public abstract float readFloat();

	public float readFloatLE() {
		return Float.intBitsToFloat(this.readIntLE());
	}

	public abstract double readDouble();

	public double readDoubleLE() {
		return Double.longBitsToDouble(this.readLongLE());
	}

	public abstract ByteBuf readBytes(int integer);

	public abstract ByteBuf readSlice(int integer);

	public abstract ByteBuf readRetainedSlice(int integer);

	public abstract ByteBuf readBytes(ByteBuf byteBuf);

	public abstract ByteBuf readBytes(ByteBuf byteBuf, int integer);

	public abstract ByteBuf readBytes(ByteBuf byteBuf, int integer2, int integer3);

	public abstract ByteBuf readBytes(byte[] arr);

	public abstract ByteBuf readBytes(byte[] arr, int integer2, int integer3);

	public abstract ByteBuf readBytes(ByteBuffer byteBuffer);

	public abstract ByteBuf readBytes(OutputStream outputStream, int integer) throws IOException;

	public abstract int readBytes(GatheringByteChannel gatheringByteChannel, int integer) throws IOException;

	public abstract CharSequence readCharSequence(int integer, Charset charset);

	public abstract int readBytes(FileChannel fileChannel, long long2, int integer) throws IOException;

	public abstract ByteBuf skipBytes(int integer);

	public abstract ByteBuf writeBoolean(boolean boolean1);

	public abstract ByteBuf writeByte(int integer);

	public abstract ByteBuf writeShort(int integer);

	public abstract ByteBuf writeShortLE(int integer);

	public abstract ByteBuf writeMedium(int integer);

	public abstract ByteBuf writeMediumLE(int integer);

	public abstract ByteBuf writeInt(int integer);

	public abstract ByteBuf writeIntLE(int integer);

	public abstract ByteBuf writeLong(long long1);

	public abstract ByteBuf writeLongLE(long long1);

	public abstract ByteBuf writeChar(int integer);

	public abstract ByteBuf writeFloat(float float1);

	public ByteBuf writeFloatLE(float value) {
		return this.writeIntLE(Float.floatToRawIntBits(value));
	}

	public abstract ByteBuf writeDouble(double double1);

	public ByteBuf writeDoubleLE(double value) {
		return this.writeLongLE(Double.doubleToRawLongBits(value));
	}

	public abstract ByteBuf writeBytes(ByteBuf byteBuf);

	public abstract ByteBuf writeBytes(ByteBuf byteBuf, int integer);

	public abstract ByteBuf writeBytes(ByteBuf byteBuf, int integer2, int integer3);

	public abstract ByteBuf writeBytes(byte[] arr);

	public abstract ByteBuf writeBytes(byte[] arr, int integer2, int integer3);

	public abstract ByteBuf writeBytes(ByteBuffer byteBuffer);

	public abstract int writeBytes(InputStream inputStream, int integer) throws IOException;

	public abstract int writeBytes(ScatteringByteChannel scatteringByteChannel, int integer) throws IOException;

	public abstract int writeBytes(FileChannel fileChannel, long long2, int integer) throws IOException;

	public abstract ByteBuf writeZero(int integer);

	public abstract int writeCharSequence(CharSequence charSequence, Charset charset);

	public abstract int indexOf(int integer1, int integer2, byte byte3);

	public abstract int bytesBefore(byte byte1);

	public abstract int bytesBefore(int integer, byte byte2);

	public abstract int bytesBefore(int integer1, int integer2, byte byte3);

	public abstract int forEachByte(ByteProcessor byteProcessor);

	public abstract int forEachByte(int integer1, int integer2, ByteProcessor byteProcessor);

	public abstract int forEachByteDesc(ByteProcessor byteProcessor);

	public abstract int forEachByteDesc(int integer1, int integer2, ByteProcessor byteProcessor);

	public abstract ByteBuf copy();

	public abstract ByteBuf copy(int integer1, int integer2);

	public abstract ByteBuf slice();

	public abstract ByteBuf retainedSlice();

	public abstract ByteBuf slice(int integer1, int integer2);

	public abstract ByteBuf retainedSlice(int integer1, int integer2);

	public abstract ByteBuf duplicate();

	public abstract ByteBuf retainedDuplicate();

	public abstract int nioBufferCount();

	public abstract ByteBuffer nioBuffer();

	public abstract ByteBuffer nioBuffer(int integer1, int integer2);

	public abstract ByteBuffer internalNioBuffer(int integer1, int integer2);

	public abstract ByteBuffer[] nioBuffers();

	public abstract ByteBuffer[] nioBuffers(int integer1, int integer2);

	public abstract boolean hasArray();

	public abstract byte[] array();

	public abstract int arrayOffset();

	public abstract boolean hasMemoryAddress();

	public abstract long memoryAddress();

	public abstract String toString(Charset charset);

	public abstract String toString(int integer1, int integer2, Charset charset);

	public abstract int hashCode();

	public abstract boolean equals(Object object);

	public abstract int compareTo(ByteBuf byteBuf);

	public abstract String toString();

	public abstract ByteBuf retain(int integer);

	public abstract ByteBuf retain();

	public abstract ByteBuf touch();

	public abstract ByteBuf touch(Object object);
}
