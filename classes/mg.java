import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DataResult.PartialResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;

public class mg extends ByteBuf {
	private final ByteBuf a;

	public mg(ByteBuf byteBuf) {
		this.a = byteBuf;
	}

	public static int a(int integer) {
		for (int integer2 = 1; integer2 < 5; integer2++) {
			if ((integer & -1 << integer2 * 7) == 0) {
				return integer2;
			}
		}

		return 5;
	}

	public <T> T a(Codec<T> codec) throws IOException {
		le le3 = this.l();
		DataResult<T> dataResult4 = codec.parse(lp.a, le3);
		if (dataResult4.error().isPresent()) {
			throw new IOException("Failed to decode: " + ((PartialResult)dataResult4.error().get()).message() + " " + le3);
		} else {
			return (T)dataResult4.result().get();
		}
	}

	public <T> void a(Codec<T> codec, T object) throws IOException {
		DataResult<lu> dataResult4 = codec.encodeStart(lp.a, object);
		if (dataResult4.error().isPresent()) {
			throw new IOException("Failed to encode: " + ((PartialResult)dataResult4.error().get()).message() + " " + object);
		} else {
			this.a((le)dataResult4.result().get());
		}
	}

	public mg a(byte[] arr) {
		this.d(arr.length);
		this.writeBytes(arr);
		return this;
	}

	public byte[] a() {
		return this.b(this.readableBytes());
	}

	public byte[] b(int integer) {
		int integer3 = this.i();
		if (integer3 > integer) {
			throw new DecoderException("ByteArray with size " + integer3 + " is bigger than allowed " + integer);
		} else {
			byte[] arr4 = new byte[integer3];
			this.readBytes(arr4);
			return arr4;
		}
	}

	public mg a(int[] arr) {
		this.d(arr.length);

		for (int integer6 : arr) {
			this.d(integer6);
		}

		return this;
	}

	public int[] b() {
		return this.c(this.readableBytes());
	}

	public int[] c(int integer) {
		int integer3 = this.i();
		if (integer3 > integer) {
			throw new DecoderException("VarIntArray with size " + integer3 + " is bigger than allowed " + integer);
		} else {
			int[] arr4 = new int[integer3];

			for (int integer5 = 0; integer5 < arr4.length; integer5++) {
				arr4[integer5] = this.i();
			}

			return arr4;
		}
	}

	public mg a(long[] arr) {
		this.d(arr.length);

		for (long long6 : arr) {
			this.writeLong(long6);
		}

		return this;
	}

	public fu e() {
		return fu.e(this.readLong());
	}

	public mg a(fu fu) {
		this.writeLong(fu.a());
		return this;
	}

	public mr h() {
		return mr.a.a(this.e(262144));
	}

	public mg a(mr mr) {
		return this.a(mr.a.a(mr), 262144);
	}

	public <T extends Enum<T>> T a(Class<T> class1) {
		return (T)class1.getEnumConstants()[this.i()];
	}

	public mg a(Enum<?> enum1) {
		return this.d(enum1.ordinal());
	}

	public int i() {
		int integer2 = 0;
		int integer3 = 0;

		byte byte4;
		do {
			byte4 = this.readByte();
			integer2 |= (byte4 & 127) << integer3++ * 7;
			if (integer3 > 5) {
				throw new RuntimeException("VarInt too big");
			}
		} while ((byte4 & 128) == 128);

		return integer2;
	}

	public long j() {
		long long2 = 0L;
		int integer4 = 0;

		byte byte5;
		do {
			byte5 = this.readByte();
			long2 |= (long)(byte5 & 127) << integer4++ * 7;
			if (integer4 > 10) {
				throw new RuntimeException("VarLong too big");
			}
		} while ((byte5 & 128) == 128);

		return long2;
	}

	public mg a(UUID uUID) {
		this.writeLong(uUID.getMostSignificantBits());
		this.writeLong(uUID.getLeastSignificantBits());
		return this;
	}

	public UUID k() {
		return new UUID(this.readLong(), this.readLong());
	}

	public mg d(int integer) {
		while ((integer & -128) != 0) {
			this.writeByte(integer & 127 | 128);
			integer >>>= 7;
		}

		this.writeByte(integer);
		return this;
	}

	public mg b(long long1) {
		while ((long1 & -128L) != 0L) {
			this.writeByte((int)(long1 & 127L) | 128);
			long1 >>>= 7;
		}

		this.writeByte((int)long1);
		return this;
	}

	public mg a(@Nullable le le) {
		if (le == null) {
			this.writeByte(0);
		} else {
			try {
				lo.a(le, (DataOutput)(new ByteBufOutputStream(this)));
			} catch (IOException var3) {
				throw new EncoderException(var3);
			}
		}

		return this;
	}

	@Nullable
	public le l() {
		int integer2 = this.readerIndex();
		byte byte3 = this.readByte();
		if (byte3 == 0) {
			return null;
		} else {
			this.readerIndex(integer2);

			try {
				return lo.a(new ByteBufInputStream(this), new ln(2097152L));
			} catch (IOException var4) {
				throw new EncoderException(var4);
			}
		}
	}

	public mg a(bki bki) {
		if (bki.a()) {
			this.writeBoolean(false);
		} else {
			this.writeBoolean(true);
			bke bke3 = bki.b();
			this.d(bke.a(bke3));
			this.writeByte(bki.E());
			le le4 = null;
			if (bke3.k() || bke3.n()) {
				le4 = bki.o();
			}

			this.a(le4);
		}

		return this;
	}

	public bki m() {
		if (!this.readBoolean()) {
			return bki.b;
		} else {
			int integer2 = this.i();
			int integer3 = this.readByte();
			bki bki4 = new bki(bke.b(integer2), integer3);
			bki4.c(this.l());
			return bki4;
		}
	}

	public String e(int integer) {
		int integer3 = this.i();
		if (integer3 > integer * 4) {
			throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + integer3 + " > " + integer * 4 + ")");
		} else if (integer3 < 0) {
			throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
		} else {
			String string4 = this.toString(this.readerIndex(), integer3, StandardCharsets.UTF_8);
			this.readerIndex(this.readerIndex() + integer3);
			if (string4.length() > integer) {
				throw new DecoderException("The received string length is longer than maximum allowed (" + integer3 + " > " + integer + ")");
			} else {
				return string4;
			}
		}
	}

	public mg a(String string) {
		return this.a(string, 32767);
	}

	public mg a(String string, int integer) {
		byte[] arr4 = string.getBytes(StandardCharsets.UTF_8);
		if (arr4.length > integer) {
			throw new EncoderException("String too big (was " + arr4.length + " bytes encoded, max " + integer + ")");
		} else {
			this.d(arr4.length);
			this.writeBytes(arr4);
			return this;
		}
	}

	public uh o() {
		return new uh(this.e(32767));
	}

	public mg a(uh uh) {
		this.a(uh.toString());
		return this;
	}

	public Date p() {
		return new Date(this.readLong());
	}

	public mg a(Date date) {
		this.writeLong(date.getTime());
		return this;
	}

	public deh q() {
		fu fu2 = this.e();
		fz fz3 = this.a(fz.class);
		float float4 = this.readFloat();
		float float5 = this.readFloat();
		float float6 = this.readFloat();
		boolean boolean7 = this.readBoolean();
		return new deh(new dem((double)fu2.u() + (double)float4, (double)fu2.v() + (double)float5, (double)fu2.w() + (double)float6), fz3, fu2, boolean7);
	}

	public void a(deh deh) {
		fu fu3 = deh.a();
		this.a(fu3);
		this.a(deh.b());
		dem dem4 = deh.e();
		this.writeFloat((float)(dem4.b - (double)fu3.u()));
		this.writeFloat((float)(dem4.c - (double)fu3.v()));
		this.writeFloat((float)(dem4.d - (double)fu3.w()));
		this.writeBoolean(deh.d());
	}

	@Override
	public int capacity() {
		return this.a.capacity();
	}

	@Override
	public ByteBuf capacity(int integer) {
		return this.a.capacity(integer);
	}

	@Override
	public int maxCapacity() {
		return this.a.maxCapacity();
	}

	@Override
	public ByteBufAllocator alloc() {
		return this.a.alloc();
	}

	@Override
	public ByteOrder order() {
		return this.a.order();
	}

	@Override
	public ByteBuf order(ByteOrder byteOrder) {
		return this.a.order(byteOrder);
	}

	@Override
	public ByteBuf unwrap() {
		return this.a.unwrap();
	}

	@Override
	public boolean isDirect() {
		return this.a.isDirect();
	}

	@Override
	public boolean isReadOnly() {
		return this.a.isReadOnly();
	}

	@Override
	public ByteBuf asReadOnly() {
		return this.a.asReadOnly();
	}

	@Override
	public int readerIndex() {
		return this.a.readerIndex();
	}

	@Override
	public ByteBuf readerIndex(int integer) {
		return this.a.readerIndex(integer);
	}

	@Override
	public int writerIndex() {
		return this.a.writerIndex();
	}

	@Override
	public ByteBuf writerIndex(int integer) {
		return this.a.writerIndex(integer);
	}

	@Override
	public ByteBuf setIndex(int integer1, int integer2) {
		return this.a.setIndex(integer1, integer2);
	}

	@Override
	public int readableBytes() {
		return this.a.readableBytes();
	}

	@Override
	public int writableBytes() {
		return this.a.writableBytes();
	}

	@Override
	public int maxWritableBytes() {
		return this.a.maxWritableBytes();
	}

	@Override
	public boolean isReadable() {
		return this.a.isReadable();
	}

	@Override
	public boolean isReadable(int integer) {
		return this.a.isReadable(integer);
	}

	@Override
	public boolean isWritable() {
		return this.a.isWritable();
	}

	@Override
	public boolean isWritable(int integer) {
		return this.a.isWritable(integer);
	}

	@Override
	public ByteBuf clear() {
		return this.a.clear();
	}

	@Override
	public ByteBuf markReaderIndex() {
		return this.a.markReaderIndex();
	}

	@Override
	public ByteBuf resetReaderIndex() {
		return this.a.resetReaderIndex();
	}

	@Override
	public ByteBuf markWriterIndex() {
		return this.a.markWriterIndex();
	}

	@Override
	public ByteBuf resetWriterIndex() {
		return this.a.resetWriterIndex();
	}

	@Override
	public ByteBuf discardReadBytes() {
		return this.a.discardReadBytes();
	}

	@Override
	public ByteBuf discardSomeReadBytes() {
		return this.a.discardSomeReadBytes();
	}

	@Override
	public ByteBuf ensureWritable(int integer) {
		return this.a.ensureWritable(integer);
	}

	@Override
	public int ensureWritable(int integer, boolean boolean2) {
		return this.a.ensureWritable(integer, boolean2);
	}

	@Override
	public boolean getBoolean(int integer) {
		return this.a.getBoolean(integer);
	}

	@Override
	public byte getByte(int integer) {
		return this.a.getByte(integer);
	}

	@Override
	public short getUnsignedByte(int integer) {
		return this.a.getUnsignedByte(integer);
	}

	@Override
	public short getShort(int integer) {
		return this.a.getShort(integer);
	}

	@Override
	public short getShortLE(int integer) {
		return this.a.getShortLE(integer);
	}

	@Override
	public int getUnsignedShort(int integer) {
		return this.a.getUnsignedShort(integer);
	}

	@Override
	public int getUnsignedShortLE(int integer) {
		return this.a.getUnsignedShortLE(integer);
	}

	@Override
	public int getMedium(int integer) {
		return this.a.getMedium(integer);
	}

	@Override
	public int getMediumLE(int integer) {
		return this.a.getMediumLE(integer);
	}

	@Override
	public int getUnsignedMedium(int integer) {
		return this.a.getUnsignedMedium(integer);
	}

	@Override
	public int getUnsignedMediumLE(int integer) {
		return this.a.getUnsignedMediumLE(integer);
	}

	@Override
	public int getInt(int integer) {
		return this.a.getInt(integer);
	}

	@Override
	public int getIntLE(int integer) {
		return this.a.getIntLE(integer);
	}

	@Override
	public long getUnsignedInt(int integer) {
		return this.a.getUnsignedInt(integer);
	}

	@Override
	public long getUnsignedIntLE(int integer) {
		return this.a.getUnsignedIntLE(integer);
	}

	@Override
	public long getLong(int integer) {
		return this.a.getLong(integer);
	}

	@Override
	public long getLongLE(int integer) {
		return this.a.getLongLE(integer);
	}

	@Override
	public char getChar(int integer) {
		return this.a.getChar(integer);
	}

	@Override
	public float getFloat(int integer) {
		return this.a.getFloat(integer);
	}

	@Override
	public double getDouble(int integer) {
		return this.a.getDouble(integer);
	}

	@Override
	public ByteBuf getBytes(int integer, ByteBuf byteBuf) {
		return this.a.getBytes(integer, byteBuf);
	}

	@Override
	public ByteBuf getBytes(int integer1, ByteBuf byteBuf, int integer3) {
		return this.a.getBytes(integer1, byteBuf, integer3);
	}

	@Override
	public ByteBuf getBytes(int integer1, ByteBuf byteBuf, int integer3, int integer4) {
		return this.a.getBytes(integer1, byteBuf, integer3, integer4);
	}

	@Override
	public ByteBuf getBytes(int integer, byte[] arr) {
		return this.a.getBytes(integer, arr);
	}

	@Override
	public ByteBuf getBytes(int integer1, byte[] arr, int integer3, int integer4) {
		return this.a.getBytes(integer1, arr, integer3, integer4);
	}

	@Override
	public ByteBuf getBytes(int integer, ByteBuffer byteBuffer) {
		return this.a.getBytes(integer, byteBuffer);
	}

	@Override
	public ByteBuf getBytes(int integer1, OutputStream outputStream, int integer3) throws IOException {
		return this.a.getBytes(integer1, outputStream, integer3);
	}

	@Override
	public int getBytes(int integer1, GatheringByteChannel gatheringByteChannel, int integer3) throws IOException {
		return this.a.getBytes(integer1, gatheringByteChannel, integer3);
	}

	@Override
	public int getBytes(int integer1, FileChannel fileChannel, long long3, int integer4) throws IOException {
		return this.a.getBytes(integer1, fileChannel, long3, integer4);
	}

	@Override
	public CharSequence getCharSequence(int integer1, int integer2, Charset charset) {
		return this.a.getCharSequence(integer1, integer2, charset);
	}

	@Override
	public ByteBuf setBoolean(int integer, boolean boolean2) {
		return this.a.setBoolean(integer, boolean2);
	}

	@Override
	public ByteBuf setByte(int integer1, int integer2) {
		return this.a.setByte(integer1, integer2);
	}

	@Override
	public ByteBuf setShort(int integer1, int integer2) {
		return this.a.setShort(integer1, integer2);
	}

	@Override
	public ByteBuf setShortLE(int integer1, int integer2) {
		return this.a.setShortLE(integer1, integer2);
	}

	@Override
	public ByteBuf setMedium(int integer1, int integer2) {
		return this.a.setMedium(integer1, integer2);
	}

	@Override
	public ByteBuf setMediumLE(int integer1, int integer2) {
		return this.a.setMediumLE(integer1, integer2);
	}

	@Override
	public ByteBuf setInt(int integer1, int integer2) {
		return this.a.setInt(integer1, integer2);
	}

	@Override
	public ByteBuf setIntLE(int integer1, int integer2) {
		return this.a.setIntLE(integer1, integer2);
	}

	@Override
	public ByteBuf setLong(int integer, long long2) {
		return this.a.setLong(integer, long2);
	}

	@Override
	public ByteBuf setLongLE(int integer, long long2) {
		return this.a.setLongLE(integer, long2);
	}

	@Override
	public ByteBuf setChar(int integer1, int integer2) {
		return this.a.setChar(integer1, integer2);
	}

	@Override
	public ByteBuf setFloat(int integer, float float2) {
		return this.a.setFloat(integer, float2);
	}

	@Override
	public ByteBuf setDouble(int integer, double double2) {
		return this.a.setDouble(integer, double2);
	}

	@Override
	public ByteBuf setBytes(int integer, ByteBuf byteBuf) {
		return this.a.setBytes(integer, byteBuf);
	}

	@Override
	public ByteBuf setBytes(int integer1, ByteBuf byteBuf, int integer3) {
		return this.a.setBytes(integer1, byteBuf, integer3);
	}

	@Override
	public ByteBuf setBytes(int integer1, ByteBuf byteBuf, int integer3, int integer4) {
		return this.a.setBytes(integer1, byteBuf, integer3, integer4);
	}

	@Override
	public ByteBuf setBytes(int integer, byte[] arr) {
		return this.a.setBytes(integer, arr);
	}

	@Override
	public ByteBuf setBytes(int integer1, byte[] arr, int integer3, int integer4) {
		return this.a.setBytes(integer1, arr, integer3, integer4);
	}

	@Override
	public ByteBuf setBytes(int integer, ByteBuffer byteBuffer) {
		return this.a.setBytes(integer, byteBuffer);
	}

	@Override
	public int setBytes(int integer1, InputStream inputStream, int integer3) throws IOException {
		return this.a.setBytes(integer1, inputStream, integer3);
	}

	@Override
	public int setBytes(int integer1, ScatteringByteChannel scatteringByteChannel, int integer3) throws IOException {
		return this.a.setBytes(integer1, scatteringByteChannel, integer3);
	}

	@Override
	public int setBytes(int integer1, FileChannel fileChannel, long long3, int integer4) throws IOException {
		return this.a.setBytes(integer1, fileChannel, long3, integer4);
	}

	@Override
	public ByteBuf setZero(int integer1, int integer2) {
		return this.a.setZero(integer1, integer2);
	}

	@Override
	public int setCharSequence(int integer, CharSequence charSequence, Charset charset) {
		return this.a.setCharSequence(integer, charSequence, charset);
	}

	@Override
	public boolean readBoolean() {
		return this.a.readBoolean();
	}

	@Override
	public byte readByte() {
		return this.a.readByte();
	}

	@Override
	public short readUnsignedByte() {
		return this.a.readUnsignedByte();
	}

	@Override
	public short readShort() {
		return this.a.readShort();
	}

	@Override
	public short readShortLE() {
		return this.a.readShortLE();
	}

	@Override
	public int readUnsignedShort() {
		return this.a.readUnsignedShort();
	}

	@Override
	public int readUnsignedShortLE() {
		return this.a.readUnsignedShortLE();
	}

	@Override
	public int readMedium() {
		return this.a.readMedium();
	}

	@Override
	public int readMediumLE() {
		return this.a.readMediumLE();
	}

	@Override
	public int readUnsignedMedium() {
		return this.a.readUnsignedMedium();
	}

	@Override
	public int readUnsignedMediumLE() {
		return this.a.readUnsignedMediumLE();
	}

	@Override
	public int readInt() {
		return this.a.readInt();
	}

	@Override
	public int readIntLE() {
		return this.a.readIntLE();
	}

	@Override
	public long readUnsignedInt() {
		return this.a.readUnsignedInt();
	}

	@Override
	public long readUnsignedIntLE() {
		return this.a.readUnsignedIntLE();
	}

	@Override
	public long readLong() {
		return this.a.readLong();
	}

	@Override
	public long readLongLE() {
		return this.a.readLongLE();
	}

	@Override
	public char readChar() {
		return this.a.readChar();
	}

	@Override
	public float readFloat() {
		return this.a.readFloat();
	}

	@Override
	public double readDouble() {
		return this.a.readDouble();
	}

	@Override
	public ByteBuf readBytes(int integer) {
		return this.a.readBytes(integer);
	}

	@Override
	public ByteBuf readSlice(int integer) {
		return this.a.readSlice(integer);
	}

	@Override
	public ByteBuf readRetainedSlice(int integer) {
		return this.a.readRetainedSlice(integer);
	}

	@Override
	public ByteBuf readBytes(ByteBuf byteBuf) {
		return this.a.readBytes(byteBuf);
	}

	@Override
	public ByteBuf readBytes(ByteBuf byteBuf, int integer) {
		return this.a.readBytes(byteBuf, integer);
	}

	@Override
	public ByteBuf readBytes(ByteBuf byteBuf, int integer2, int integer3) {
		return this.a.readBytes(byteBuf, integer2, integer3);
	}

	@Override
	public ByteBuf readBytes(byte[] arr) {
		return this.a.readBytes(arr);
	}

	@Override
	public ByteBuf readBytes(byte[] arr, int integer2, int integer3) {
		return this.a.readBytes(arr, integer2, integer3);
	}

	@Override
	public ByteBuf readBytes(ByteBuffer byteBuffer) {
		return this.a.readBytes(byteBuffer);
	}

	@Override
	public ByteBuf readBytes(OutputStream outputStream, int integer) throws IOException {
		return this.a.readBytes(outputStream, integer);
	}

	@Override
	public int readBytes(GatheringByteChannel gatheringByteChannel, int integer) throws IOException {
		return this.a.readBytes(gatheringByteChannel, integer);
	}

	@Override
	public CharSequence readCharSequence(int integer, Charset charset) {
		return this.a.readCharSequence(integer, charset);
	}

	@Override
	public int readBytes(FileChannel fileChannel, long long2, int integer) throws IOException {
		return this.a.readBytes(fileChannel, long2, integer);
	}

	@Override
	public ByteBuf skipBytes(int integer) {
		return this.a.skipBytes(integer);
	}

	@Override
	public ByteBuf writeBoolean(boolean boolean1) {
		return this.a.writeBoolean(boolean1);
	}

	@Override
	public ByteBuf writeByte(int integer) {
		return this.a.writeByte(integer);
	}

	@Override
	public ByteBuf writeShort(int integer) {
		return this.a.writeShort(integer);
	}

	@Override
	public ByteBuf writeShortLE(int integer) {
		return this.a.writeShortLE(integer);
	}

	@Override
	public ByteBuf writeMedium(int integer) {
		return this.a.writeMedium(integer);
	}

	@Override
	public ByteBuf writeMediumLE(int integer) {
		return this.a.writeMediumLE(integer);
	}

	@Override
	public ByteBuf writeInt(int integer) {
		return this.a.writeInt(integer);
	}

	@Override
	public ByteBuf writeIntLE(int integer) {
		return this.a.writeIntLE(integer);
	}

	@Override
	public ByteBuf writeLong(long long1) {
		return this.a.writeLong(long1);
	}

	@Override
	public ByteBuf writeLongLE(long long1) {
		return this.a.writeLongLE(long1);
	}

	@Override
	public ByteBuf writeChar(int integer) {
		return this.a.writeChar(integer);
	}

	@Override
	public ByteBuf writeFloat(float float1) {
		return this.a.writeFloat(float1);
	}

	@Override
	public ByteBuf writeDouble(double double1) {
		return this.a.writeDouble(double1);
	}

	@Override
	public ByteBuf writeBytes(ByteBuf byteBuf) {
		return this.a.writeBytes(byteBuf);
	}

	@Override
	public ByteBuf writeBytes(ByteBuf byteBuf, int integer) {
		return this.a.writeBytes(byteBuf, integer);
	}

	@Override
	public ByteBuf writeBytes(ByteBuf byteBuf, int integer2, int integer3) {
		return this.a.writeBytes(byteBuf, integer2, integer3);
	}

	@Override
	public ByteBuf writeBytes(byte[] arr) {
		return this.a.writeBytes(arr);
	}

	@Override
	public ByteBuf writeBytes(byte[] arr, int integer2, int integer3) {
		return this.a.writeBytes(arr, integer2, integer3);
	}

	@Override
	public ByteBuf writeBytes(ByteBuffer byteBuffer) {
		return this.a.writeBytes(byteBuffer);
	}

	@Override
	public int writeBytes(InputStream inputStream, int integer) throws IOException {
		return this.a.writeBytes(inputStream, integer);
	}

	@Override
	public int writeBytes(ScatteringByteChannel scatteringByteChannel, int integer) throws IOException {
		return this.a.writeBytes(scatteringByteChannel, integer);
	}

	@Override
	public int writeBytes(FileChannel fileChannel, long long2, int integer) throws IOException {
		return this.a.writeBytes(fileChannel, long2, integer);
	}

	@Override
	public ByteBuf writeZero(int integer) {
		return this.a.writeZero(integer);
	}

	@Override
	public int writeCharSequence(CharSequence charSequence, Charset charset) {
		return this.a.writeCharSequence(charSequence, charset);
	}

	@Override
	public int indexOf(int integer1, int integer2, byte byte3) {
		return this.a.indexOf(integer1, integer2, byte3);
	}

	@Override
	public int bytesBefore(byte byte1) {
		return this.a.bytesBefore(byte1);
	}

	@Override
	public int bytesBefore(int integer, byte byte2) {
		return this.a.bytesBefore(integer, byte2);
	}

	@Override
	public int bytesBefore(int integer1, int integer2, byte byte3) {
		return this.a.bytesBefore(integer1, integer2, byte3);
	}

	@Override
	public int forEachByte(ByteProcessor byteProcessor) {
		return this.a.forEachByte(byteProcessor);
	}

	@Override
	public int forEachByte(int integer1, int integer2, ByteProcessor byteProcessor) {
		return this.a.forEachByte(integer1, integer2, byteProcessor);
	}

	@Override
	public int forEachByteDesc(ByteProcessor byteProcessor) {
		return this.a.forEachByteDesc(byteProcessor);
	}

	@Override
	public int forEachByteDesc(int integer1, int integer2, ByteProcessor byteProcessor) {
		return this.a.forEachByteDesc(integer1, integer2, byteProcessor);
	}

	@Override
	public ByteBuf copy() {
		return this.a.copy();
	}

	@Override
	public ByteBuf copy(int integer1, int integer2) {
		return this.a.copy(integer1, integer2);
	}

	@Override
	public ByteBuf slice() {
		return this.a.slice();
	}

	@Override
	public ByteBuf retainedSlice() {
		return this.a.retainedSlice();
	}

	@Override
	public ByteBuf slice(int integer1, int integer2) {
		return this.a.slice(integer1, integer2);
	}

	@Override
	public ByteBuf retainedSlice(int integer1, int integer2) {
		return this.a.retainedSlice(integer1, integer2);
	}

	@Override
	public ByteBuf duplicate() {
		return this.a.duplicate();
	}

	@Override
	public ByteBuf retainedDuplicate() {
		return this.a.retainedDuplicate();
	}

	@Override
	public int nioBufferCount() {
		return this.a.nioBufferCount();
	}

	@Override
	public ByteBuffer nioBuffer() {
		return this.a.nioBuffer();
	}

	@Override
	public ByteBuffer nioBuffer(int integer1, int integer2) {
		return this.a.nioBuffer(integer1, integer2);
	}

	@Override
	public ByteBuffer internalNioBuffer(int integer1, int integer2) {
		return this.a.internalNioBuffer(integer1, integer2);
	}

	@Override
	public ByteBuffer[] nioBuffers() {
		return this.a.nioBuffers();
	}

	@Override
	public ByteBuffer[] nioBuffers(int integer1, int integer2) {
		return this.a.nioBuffers(integer1, integer2);
	}

	@Override
	public boolean hasArray() {
		return this.a.hasArray();
	}

	@Override
	public byte[] array() {
		return this.a.array();
	}

	@Override
	public int arrayOffset() {
		return this.a.arrayOffset();
	}

	@Override
	public boolean hasMemoryAddress() {
		return this.a.hasMemoryAddress();
	}

	@Override
	public long memoryAddress() {
		return this.a.memoryAddress();
	}

	@Override
	public String toString(Charset charset) {
		return this.a.toString(charset);
	}

	@Override
	public String toString(int integer1, int integer2, Charset charset) {
		return this.a.toString(integer1, integer2, charset);
	}

	@Override
	public int hashCode() {
		return this.a.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		return this.a.equals(object);
	}

	@Override
	public int compareTo(ByteBuf byteBuf) {
		return this.a.compareTo(byteBuf);
	}

	@Override
	public String toString() {
		return this.a.toString();
	}

	@Override
	public ByteBuf retain(int integer) {
		return this.a.retain(integer);
	}

	@Override
	public ByteBuf retain() {
		return this.a.retain();
	}

	@Override
	public ByteBuf touch() {
		return this.a.touch();
	}

	@Override
	public ByteBuf touch(Object object) {
		return this.a.touch(object);
	}

	@Override
	public int refCnt() {
		return this.a.refCnt();
	}

	@Override
	public boolean release() {
		return this.a.release();
	}

	@Override
	public boolean release(int integer) {
		return this.a.release(integer);
	}
}
