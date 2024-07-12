package it.unimi.dsi.fastutil.io;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
import it.unimi.dsi.fastutil.booleans.BooleanIterable;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterable;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharBigArrays;
import it.unimi.dsi.fastutil.chars.CharIterable;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
import it.unimi.dsi.fastutil.doubles.DoubleIterable;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatBigArrays;
import it.unimi.dsi.fastutil.floats.FloatIterable;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntBigArrays;
import it.unimi.dsi.fastutil.ints.IntIterable;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongBigArrays;
import it.unimi.dsi.fastutil.longs.LongIterable;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
import it.unimi.dsi.fastutil.shorts.ShortIterable;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;

public class BinIO {
	private static final int MAX_IO_LENGTH = 1048576;

	private BinIO() {
	}

	public static void storeObject(Object o, File file) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		oos.writeObject(o);
		oos.close();
	}

	public static void storeObject(Object o, CharSequence filename) throws IOException {
		storeObject(o, new File(filename.toString()));
	}

	public static Object loadObject(File file) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FastBufferedInputStream(new FileInputStream(file)));
		Object result = ois.readObject();
		ois.close();
		return result;
	}

	public static Object loadObject(CharSequence filename) throws IOException, ClassNotFoundException {
		return loadObject(new File(filename.toString()));
	}

	public static void storeObject(Object o, OutputStream s) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FastBufferedOutputStream(s));
		oos.writeObject(o);
		oos.flush();
	}

	public static Object loadObject(InputStream s) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FastBufferedInputStream(s));
		return ois.readObject();
	}

	private static int read(InputStream is, byte[] a, int offset, int length) throws IOException {
		if (length == 0) {
			return 0;
		} else {
			int read = 0;

			do {
				int result = is.read(a, offset + read, Math.min(length - read, 1048576));
				if (result < 0) {
					return read;
				}

				read += result;
			} while (read < length);

			return read;
		}
	}

	private static void write(OutputStream outputStream, byte[] a, int offset, int length) throws IOException {
		int written = 0;

		while (written < length) {
			outputStream.write(a, offset + written, Math.min(length - written, 1048576));
			written += Math.min(length - written, 1048576);
		}
	}

	private static void write(DataOutput dataOutput, byte[] a, int offset, int length) throws IOException {
		int written = 0;

		while (written < length) {
			dataOutput.write(a, offset + written, Math.min(length - written, 1048576));
			written += Math.min(length - written, 1048576);
		}
	}

	public static int loadBytes(InputStream inputStream, byte[] array, int offset, int length) throws IOException {
		return read(inputStream, array, offset, length);
	}

	public static int loadBytes(InputStream inputStream, byte[] array) throws IOException {
		return read(inputStream, array, 0, array.length);
	}

	public static void storeBytes(byte[] array, int offset, int length, OutputStream outputStream) throws IOException {
		write(outputStream, array, offset, length);
	}

	public static void storeBytes(byte[] array, OutputStream outputStream) throws IOException {
		write(outputStream, array, 0, array.length);
	}

	private static long read(InputStream is, byte[][] a, long offset, long length) throws IOException {
		if (length == 0L) {
			return 0L;
		} else {
			long read = 0L;
			int segment = BigArrays.segment(offset);
			int displacement = BigArrays.displacement(offset);

			do {
				int result = is.read(a[segment], displacement, (int)Math.min((long)(a[segment].length - displacement), Math.min(length - read, 1048576L)));
				if (result < 0) {
					return read;
				}

				read += (long)result;
				displacement += result;
				if (displacement == a[segment].length) {
					segment++;
					displacement = 0;
				}
			} while (read < length);

			return read;
		}
	}

	private static void write(OutputStream outputStream, byte[][] a, long offset, long length) throws IOException {
		if (length != 0L) {
			long written = 0L;
			int segment = BigArrays.segment(offset);
			int displacement = BigArrays.displacement(offset);

			do {
				int toWrite = (int)Math.min((long)(a[segment].length - displacement), Math.min(length - written, 1048576L));
				outputStream.write(a[segment], displacement, toWrite);
				written += (long)toWrite;
				displacement += toWrite;
				if (displacement == a[segment].length) {
					segment++;
					displacement = 0;
				}
			} while (written < length);
		}
	}

	private static void write(DataOutput dataOutput, byte[][] a, long offset, long length) throws IOException {
		if (length != 0L) {
			long written = 0L;
			int segment = BigArrays.segment(offset);
			int displacement = BigArrays.displacement(offset);

			do {
				int toWrite = (int)Math.min((long)(a[segment].length - displacement), Math.min(length - written, 1048576L));
				dataOutput.write(a[segment], displacement, toWrite);
				written += (long)toWrite;
				displacement += toWrite;
				if (displacement == a[segment].length) {
					segment++;
					displacement = 0;
				}
			} while (written < length);
		}
	}

	public static long loadBytes(InputStream inputStream, byte[][] array, long offset, long length) throws IOException {
		return read(inputStream, array, offset, length);
	}

	public static long loadBytes(InputStream inputStream, byte[][] array) throws IOException {
		return read(inputStream, array, 0L, ByteBigArrays.length(array));
	}

	public static void storeBytes(byte[][] array, long offset, long length, OutputStream outputStream) throws IOException {
		write(outputStream, array, offset, length);
	}

	public static void storeBytes(byte[][] array, OutputStream outputStream) throws IOException {
		write(outputStream, array, 0L, ByteBigArrays.length(array));
	}

	public static int loadBytes(DataInput dataInput, byte[] array, int offset, int length) throws IOException {
		ByteArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dataInput.readByte();
			}
		} catch (EOFException var6) {
		}

		return i;
	}

	public static int loadBytes(DataInput dataInput, byte[] array) throws IOException {
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dataInput.readByte();
			}
		} catch (EOFException var4) {
		}

		return i;
	}

	public static int loadBytes(File file, byte[] array, int offset, int length) throws IOException {
		ByteArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		int result = read(fis, array, offset, length);
		fis.close();
		return result;
	}

	public static int loadBytes(CharSequence filename, byte[] array, int offset, int length) throws IOException {
		return loadBytes(new File(filename.toString()), array, offset, length);
	}

	public static int loadBytes(File file, byte[] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		int result = read(fis, array, 0, array.length);
		fis.close();
		return result;
	}

	public static int loadBytes(CharSequence filename, byte[] array) throws IOException {
		return loadBytes(new File(filename.toString()), array);
	}

	public static byte[] loadBytes(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 1L;
		if (length > 2147483647L) {
			fis.close();
			throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
		} else {
			byte[] array = new byte[(int)length];
			if ((long)read(fis, array, 0, (int)length) < length) {
				throw new EOFException();
			} else {
				fis.close();
				return array;
			}
		}
	}

	public static byte[] loadBytes(CharSequence filename) throws IOException {
		return loadBytes(new File(filename.toString()));
	}

	public static void storeBytes(byte[] array, int offset, int length, DataOutput dataOutput) throws IOException {
		ByteArrays.ensureOffsetLength(array, offset, length);
		write(dataOutput, array, offset, length);
	}

	public static void storeBytes(byte[] array, DataOutput dataOutput) throws IOException {
		write(dataOutput, array, 0, array.length);
	}

	public static void storeBytes(byte[] array, int offset, int length, File file) throws IOException {
		ByteArrays.ensureOffsetLength(array, offset, length);
		OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
		write(os, array, offset, length);
		os.close();
	}

	public static void storeBytes(byte[] array, int offset, int length, CharSequence filename) throws IOException {
		storeBytes(array, offset, length, new File(filename.toString()));
	}

	public static void storeBytes(byte[] array, File file) throws IOException {
		OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
		write(os, array, 0, array.length);
		os.close();
	}

	public static void storeBytes(byte[] array, CharSequence filename) throws IOException {
		storeBytes(array, new File(filename.toString()));
	}

	public static long loadBytes(DataInput dataInput, byte[][] array, long offset, long length) throws IOException {
		ByteBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				byte[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dataInput.readByte();
					c++;
				}
			}
		} catch (EOFException var12) {
		}

		return c;
	}

	public static long loadBytes(DataInput dataInput, byte[][] array) throws IOException {
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				byte[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dataInput.readByte();
					c++;
				}
			}
		} catch (EOFException var8) {
		}

		return c;
	}

	public static long loadBytes(File file, byte[][] array, long offset, long length) throws IOException {
		ByteBigArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		long result = read(fis, array, offset, length);
		fis.close();
		return result;
	}

	public static long loadBytes(CharSequence filename, byte[][] array, long offset, long length) throws IOException {
		return loadBytes(new File(filename.toString()), array, offset, length);
	}

	public static long loadBytes(File file, byte[][] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long result = read(fis, array, 0L, ByteBigArrays.length(array));
		fis.close();
		return result;
	}

	public static long loadBytes(CharSequence filename, byte[][] array) throws IOException {
		return loadBytes(new File(filename.toString()), array);
	}

	public static byte[][] loadBytesBig(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 1L;
		byte[][] array = ByteBigArrays.newBigArray(length);
		if (read(fis, array, 0L, length) < length) {
			throw new EOFException();
		} else {
			fis.close();
			return array;
		}
	}

	public static byte[][] loadBytesBig(CharSequence filename) throws IOException {
		return loadBytesBig(new File(filename.toString()));
	}

	public static void storeBytes(byte[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
		ByteBigArrays.ensureOffsetLength(array, offset, length);
		write(dataOutput, array, offset, length);
	}

	public static void storeBytes(byte[][] array, DataOutput dataOutput) throws IOException {
		write(dataOutput, array, 0L, ByteBigArrays.length(array));
	}

	public static void storeBytes(byte[][] array, long offset, long length, File file) throws IOException {
		ByteBigArrays.ensureOffsetLength(array, offset, length);
		OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
		write(os, array, offset, length);
		os.close();
	}

	public static void storeBytes(byte[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeBytes(array, offset, length, new File(filename.toString()));
	}

	public static void storeBytes(byte[][] array, File file) throws IOException {
		OutputStream os = new FastBufferedOutputStream(new FileOutputStream(file));
		write(os, array, 0L, ByteBigArrays.length(array));
		os.close();
	}

	public static void storeBytes(byte[][] array, CharSequence filename) throws IOException {
		storeBytes(array, new File(filename.toString()));
	}

	public static void storeBytes(ByteIterator i, DataOutput dataOutput) throws IOException {
		while (i.hasNext()) {
			dataOutput.writeByte(i.nextByte());
		}
	}

	public static void storeBytes(ByteIterator i, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		while (i.hasNext()) {
			dos.writeByte(i.nextByte());
		}

		dos.close();
	}

	public static void storeBytes(ByteIterator i, CharSequence filename) throws IOException {
		storeBytes(i, new File(filename.toString()));
	}

	public static ByteIterator asByteIterator(DataInput dataInput) {
		return new BinIO.ByteDataInputWrapper(dataInput);
	}

	public static ByteIterator asByteIterator(File file) throws IOException {
		return new BinIO.ByteDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
	}

	public static ByteIterator asByteIterator(CharSequence filename) throws IOException {
		return asByteIterator(new File(filename.toString()));
	}

	public static ByteIterable asByteIterable(File file) {
		return () -> {
			try {
				return asByteIterator(file);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static ByteIterable asByteIterable(CharSequence filename) {
		return () -> {
			try {
				return asByteIterator(filename);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static int loadInts(DataInput dataInput, int[] array, int offset, int length) throws IOException {
		IntArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dataInput.readInt();
			}
		} catch (EOFException var6) {
		}

		return i;
	}

	public static int loadInts(DataInput dataInput, int[] array) throws IOException {
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dataInput.readInt();
			}
		} catch (EOFException var4) {
		}

		return i;
	}

	public static int loadInts(File file, int[] array, int offset, int length) throws IOException {
		IntArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dis.readInt();
			}
		} catch (EOFException var8) {
		}

		dis.close();
		return i;
	}

	public static int loadInts(CharSequence filename, int[] array, int offset, int length) throws IOException {
		return loadInts(new File(filename.toString()), array, offset, length);
	}

	public static int loadInts(File file, int[] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dis.readInt();
			}
		} catch (EOFException var6) {
		}

		dis.close();
		return i;
	}

	public static int loadInts(CharSequence filename, int[] array) throws IOException {
		return loadInts(new File(filename.toString()), array);
	}

	public static int[] loadInts(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 4L;
		if (length > 2147483647L) {
			fis.close();
			throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
		} else {
			int[] array = new int[(int)length];
			DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

			for (int i = 0; (long)i < length; i++) {
				array[i] = dis.readInt();
			}

			dis.close();
			return array;
		}
	}

	public static int[] loadInts(CharSequence filename) throws IOException {
		return loadInts(new File(filename.toString()));
	}

	public static void storeInts(int[] array, int offset, int length, DataOutput dataOutput) throws IOException {
		IntArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			dataOutput.writeInt(array[offset + i]);
		}
	}

	public static void storeInts(int[] array, DataOutput dataOutput) throws IOException {
		int length = array.length;

		for (int i = 0; i < length; i++) {
			dataOutput.writeInt(array[i]);
		}
	}

	public static void storeInts(int[] array, int offset, int length, File file) throws IOException {
		IntArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeInt(array[offset + i]);
		}

		dos.close();
	}

	public static void storeInts(int[] array, int offset, int length, CharSequence filename) throws IOException {
		storeInts(array, offset, length, new File(filename.toString()));
	}

	public static void storeInts(int[] array, File file) throws IOException {
		int length = array.length;
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeInt(array[i]);
		}

		dos.close();
	}

	public static void storeInts(int[] array, CharSequence filename) throws IOException {
		storeInts(array, new File(filename.toString()));
	}

	public static long loadInts(DataInput dataInput, int[][] array, long offset, long length) throws IOException {
		IntBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				int[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dataInput.readInt();
					c++;
				}
			}
		} catch (EOFException var12) {
		}

		return c;
	}

	public static long loadInts(DataInput dataInput, int[][] array) throws IOException {
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				int[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dataInput.readInt();
					c++;
				}
			}
		} catch (EOFException var8) {
		}

		return c;
	}

	public static long loadInts(File file, int[][] array, long offset, long length) throws IOException {
		IntBigArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				int[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dis.readInt();
					c++;
				}
			}
		} catch (EOFException var14) {
		}

		dis.close();
		return c;
	}

	public static long loadInts(CharSequence filename, int[][] array, long offset, long length) throws IOException {
		return loadInts(new File(filename.toString()), array, offset, length);
	}

	public static long loadInts(File file, int[][] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				int[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dis.readInt();
					c++;
				}
			}
		} catch (EOFException var10) {
		}

		dis.close();
		return c;
	}

	public static long loadInts(CharSequence filename, int[][] array) throws IOException {
		return loadInts(new File(filename.toString()), array);
	}

	public static int[][] loadIntsBig(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 4L;
		int[][] array = IntBigArrays.newBigArray(length);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

		for (int i = 0; i < array.length; i++) {
			int[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				t[d] = dis.readInt();
			}
		}

		dis.close();
		return array;
	}

	public static int[][] loadIntsBig(CharSequence filename) throws IOException {
		return loadIntsBig(new File(filename.toString()));
	}

	public static void storeInts(int[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
		IntBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			int[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dataOutput.writeInt(t[d]);
			}
		}
	}

	public static void storeInts(int[][] array, DataOutput dataOutput) throws IOException {
		for (int i = 0; i < array.length; i++) {
			int[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dataOutput.writeInt(t[d]);
			}
		}
	}

	public static void storeInts(int[][] array, long offset, long length, File file) throws IOException {
		IntBigArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			int[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dos.writeInt(t[d]);
			}
		}

		dos.close();
	}

	public static void storeInts(int[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeInts(array, offset, length, new File(filename.toString()));
	}

	public static void storeInts(int[][] array, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < array.length; i++) {
			int[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dos.writeInt(t[d]);
			}
		}

		dos.close();
	}

	public static void storeInts(int[][] array, CharSequence filename) throws IOException {
		storeInts(array, new File(filename.toString()));
	}

	public static void storeInts(IntIterator i, DataOutput dataOutput) throws IOException {
		while (i.hasNext()) {
			dataOutput.writeInt(i.nextInt());
		}
	}

	public static void storeInts(IntIterator i, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		while (i.hasNext()) {
			dos.writeInt(i.nextInt());
		}

		dos.close();
	}

	public static void storeInts(IntIterator i, CharSequence filename) throws IOException {
		storeInts(i, new File(filename.toString()));
	}

	public static IntIterator asIntIterator(DataInput dataInput) {
		return new BinIO.IntDataInputWrapper(dataInput);
	}

	public static IntIterator asIntIterator(File file) throws IOException {
		return new BinIO.IntDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
	}

	public static IntIterator asIntIterator(CharSequence filename) throws IOException {
		return asIntIterator(new File(filename.toString()));
	}

	public static IntIterable asIntIterable(File file) {
		return () -> {
			try {
				return asIntIterator(file);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static IntIterable asIntIterable(CharSequence filename) {
		return () -> {
			try {
				return asIntIterator(filename);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static int loadLongs(DataInput dataInput, long[] array, int offset, int length) throws IOException {
		LongArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dataInput.readLong();
			}
		} catch (EOFException var6) {
		}

		return i;
	}

	public static int loadLongs(DataInput dataInput, long[] array) throws IOException {
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dataInput.readLong();
			}
		} catch (EOFException var4) {
		}

		return i;
	}

	public static int loadLongs(File file, long[] array, int offset, int length) throws IOException {
		LongArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dis.readLong();
			}
		} catch (EOFException var8) {
		}

		dis.close();
		return i;
	}

	public static int loadLongs(CharSequence filename, long[] array, int offset, int length) throws IOException {
		return loadLongs(new File(filename.toString()), array, offset, length);
	}

	public static int loadLongs(File file, long[] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dis.readLong();
			}
		} catch (EOFException var6) {
		}

		dis.close();
		return i;
	}

	public static int loadLongs(CharSequence filename, long[] array) throws IOException {
		return loadLongs(new File(filename.toString()), array);
	}

	public static long[] loadLongs(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 8L;
		if (length > 2147483647L) {
			fis.close();
			throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
		} else {
			long[] array = new long[(int)length];
			DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

			for (int i = 0; (long)i < length; i++) {
				array[i] = dis.readLong();
			}

			dis.close();
			return array;
		}
	}

	public static long[] loadLongs(CharSequence filename) throws IOException {
		return loadLongs(new File(filename.toString()));
	}

	public static void storeLongs(long[] array, int offset, int length, DataOutput dataOutput) throws IOException {
		LongArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			dataOutput.writeLong(array[offset + i]);
		}
	}

	public static void storeLongs(long[] array, DataOutput dataOutput) throws IOException {
		int length = array.length;

		for (int i = 0; i < length; i++) {
			dataOutput.writeLong(array[i]);
		}
	}

	public static void storeLongs(long[] array, int offset, int length, File file) throws IOException {
		LongArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeLong(array[offset + i]);
		}

		dos.close();
	}

	public static void storeLongs(long[] array, int offset, int length, CharSequence filename) throws IOException {
		storeLongs(array, offset, length, new File(filename.toString()));
	}

	public static void storeLongs(long[] array, File file) throws IOException {
		int length = array.length;
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeLong(array[i]);
		}

		dos.close();
	}

	public static void storeLongs(long[] array, CharSequence filename) throws IOException {
		storeLongs(array, new File(filename.toString()));
	}

	public static long loadLongs(DataInput dataInput, long[][] array, long offset, long length) throws IOException {
		LongBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				long[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dataInput.readLong();
					c++;
				}
			}
		} catch (EOFException var12) {
		}

		return c;
	}

	public static long loadLongs(DataInput dataInput, long[][] array) throws IOException {
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				long[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dataInput.readLong();
					c++;
				}
			}
		} catch (EOFException var8) {
		}

		return c;
	}

	public static long loadLongs(File file, long[][] array, long offset, long length) throws IOException {
		LongBigArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				long[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dis.readLong();
					c++;
				}
			}
		} catch (EOFException var14) {
		}

		dis.close();
		return c;
	}

	public static long loadLongs(CharSequence filename, long[][] array, long offset, long length) throws IOException {
		return loadLongs(new File(filename.toString()), array, offset, length);
	}

	public static long loadLongs(File file, long[][] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				long[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dis.readLong();
					c++;
				}
			}
		} catch (EOFException var10) {
		}

		dis.close();
		return c;
	}

	public static long loadLongs(CharSequence filename, long[][] array) throws IOException {
		return loadLongs(new File(filename.toString()), array);
	}

	public static long[][] loadLongsBig(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 8L;
		long[][] array = LongBigArrays.newBigArray(length);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

		for (int i = 0; i < array.length; i++) {
			long[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				t[d] = dis.readLong();
			}
		}

		dis.close();
		return array;
	}

	public static long[][] loadLongsBig(CharSequence filename) throws IOException {
		return loadLongsBig(new File(filename.toString()));
	}

	public static void storeLongs(long[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
		LongBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			long[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dataOutput.writeLong(t[d]);
			}
		}
	}

	public static void storeLongs(long[][] array, DataOutput dataOutput) throws IOException {
		for (int i = 0; i < array.length; i++) {
			long[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dataOutput.writeLong(t[d]);
			}
		}
	}

	public static void storeLongs(long[][] array, long offset, long length, File file) throws IOException {
		LongBigArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			long[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dos.writeLong(t[d]);
			}
		}

		dos.close();
	}

	public static void storeLongs(long[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeLongs(array, offset, length, new File(filename.toString()));
	}

	public static void storeLongs(long[][] array, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < array.length; i++) {
			long[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dos.writeLong(t[d]);
			}
		}

		dos.close();
	}

	public static void storeLongs(long[][] array, CharSequence filename) throws IOException {
		storeLongs(array, new File(filename.toString()));
	}

	public static void storeLongs(LongIterator i, DataOutput dataOutput) throws IOException {
		while (i.hasNext()) {
			dataOutput.writeLong(i.nextLong());
		}
	}

	public static void storeLongs(LongIterator i, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		while (i.hasNext()) {
			dos.writeLong(i.nextLong());
		}

		dos.close();
	}

	public static void storeLongs(LongIterator i, CharSequence filename) throws IOException {
		storeLongs(i, new File(filename.toString()));
	}

	public static LongIterator asLongIterator(DataInput dataInput) {
		return new BinIO.LongDataInputWrapper(dataInput);
	}

	public static LongIterator asLongIterator(File file) throws IOException {
		return new BinIO.LongDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
	}

	public static LongIterator asLongIterator(CharSequence filename) throws IOException {
		return asLongIterator(new File(filename.toString()));
	}

	public static LongIterable asLongIterable(File file) {
		return () -> {
			try {
				return asLongIterator(file);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static LongIterable asLongIterable(CharSequence filename) {
		return () -> {
			try {
				return asLongIterator(filename);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static int loadDoubles(DataInput dataInput, double[] array, int offset, int length) throws IOException {
		DoubleArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dataInput.readDouble();
			}
		} catch (EOFException var6) {
		}

		return i;
	}

	public static int loadDoubles(DataInput dataInput, double[] array) throws IOException {
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dataInput.readDouble();
			}
		} catch (EOFException var4) {
		}

		return i;
	}

	public static int loadDoubles(File file, double[] array, int offset, int length) throws IOException {
		DoubleArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dis.readDouble();
			}
		} catch (EOFException var8) {
		}

		dis.close();
		return i;
	}

	public static int loadDoubles(CharSequence filename, double[] array, int offset, int length) throws IOException {
		return loadDoubles(new File(filename.toString()), array, offset, length);
	}

	public static int loadDoubles(File file, double[] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dis.readDouble();
			}
		} catch (EOFException var6) {
		}

		dis.close();
		return i;
	}

	public static int loadDoubles(CharSequence filename, double[] array) throws IOException {
		return loadDoubles(new File(filename.toString()), array);
	}

	public static double[] loadDoubles(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 8L;
		if (length > 2147483647L) {
			fis.close();
			throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
		} else {
			double[] array = new double[(int)length];
			DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

			for (int i = 0; (long)i < length; i++) {
				array[i] = dis.readDouble();
			}

			dis.close();
			return array;
		}
	}

	public static double[] loadDoubles(CharSequence filename) throws IOException {
		return loadDoubles(new File(filename.toString()));
	}

	public static void storeDoubles(double[] array, int offset, int length, DataOutput dataOutput) throws IOException {
		DoubleArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			dataOutput.writeDouble(array[offset + i]);
		}
	}

	public static void storeDoubles(double[] array, DataOutput dataOutput) throws IOException {
		int length = array.length;

		for (int i = 0; i < length; i++) {
			dataOutput.writeDouble(array[i]);
		}
	}

	public static void storeDoubles(double[] array, int offset, int length, File file) throws IOException {
		DoubleArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeDouble(array[offset + i]);
		}

		dos.close();
	}

	public static void storeDoubles(double[] array, int offset, int length, CharSequence filename) throws IOException {
		storeDoubles(array, offset, length, new File(filename.toString()));
	}

	public static void storeDoubles(double[] array, File file) throws IOException {
		int length = array.length;
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeDouble(array[i]);
		}

		dos.close();
	}

	public static void storeDoubles(double[] array, CharSequence filename) throws IOException {
		storeDoubles(array, new File(filename.toString()));
	}

	public static long loadDoubles(DataInput dataInput, double[][] array, long offset, long length) throws IOException {
		DoubleBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				double[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dataInput.readDouble();
					c++;
				}
			}
		} catch (EOFException var12) {
		}

		return c;
	}

	public static long loadDoubles(DataInput dataInput, double[][] array) throws IOException {
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				double[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dataInput.readDouble();
					c++;
				}
			}
		} catch (EOFException var8) {
		}

		return c;
	}

	public static long loadDoubles(File file, double[][] array, long offset, long length) throws IOException {
		DoubleBigArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				double[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dis.readDouble();
					c++;
				}
			}
		} catch (EOFException var14) {
		}

		dis.close();
		return c;
	}

	public static long loadDoubles(CharSequence filename, double[][] array, long offset, long length) throws IOException {
		return loadDoubles(new File(filename.toString()), array, offset, length);
	}

	public static long loadDoubles(File file, double[][] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				double[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dis.readDouble();
					c++;
				}
			}
		} catch (EOFException var10) {
		}

		dis.close();
		return c;
	}

	public static long loadDoubles(CharSequence filename, double[][] array) throws IOException {
		return loadDoubles(new File(filename.toString()), array);
	}

	public static double[][] loadDoublesBig(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 8L;
		double[][] array = DoubleBigArrays.newBigArray(length);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

		for (int i = 0; i < array.length; i++) {
			double[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				t[d] = dis.readDouble();
			}
		}

		dis.close();
		return array;
	}

	public static double[][] loadDoublesBig(CharSequence filename) throws IOException {
		return loadDoublesBig(new File(filename.toString()));
	}

	public static void storeDoubles(double[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
		DoubleBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			double[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dataOutput.writeDouble(t[d]);
			}
		}
	}

	public static void storeDoubles(double[][] array, DataOutput dataOutput) throws IOException {
		for (int i = 0; i < array.length; i++) {
			double[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dataOutput.writeDouble(t[d]);
			}
		}
	}

	public static void storeDoubles(double[][] array, long offset, long length, File file) throws IOException {
		DoubleBigArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			double[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dos.writeDouble(t[d]);
			}
		}

		dos.close();
	}

	public static void storeDoubles(double[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeDoubles(array, offset, length, new File(filename.toString()));
	}

	public static void storeDoubles(double[][] array, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < array.length; i++) {
			double[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dos.writeDouble(t[d]);
			}
		}

		dos.close();
	}

	public static void storeDoubles(double[][] array, CharSequence filename) throws IOException {
		storeDoubles(array, new File(filename.toString()));
	}

	public static void storeDoubles(DoubleIterator i, DataOutput dataOutput) throws IOException {
		while (i.hasNext()) {
			dataOutput.writeDouble(i.nextDouble());
		}
	}

	public static void storeDoubles(DoubleIterator i, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		while (i.hasNext()) {
			dos.writeDouble(i.nextDouble());
		}

		dos.close();
	}

	public static void storeDoubles(DoubleIterator i, CharSequence filename) throws IOException {
		storeDoubles(i, new File(filename.toString()));
	}

	public static DoubleIterator asDoubleIterator(DataInput dataInput) {
		return new BinIO.DoubleDataInputWrapper(dataInput);
	}

	public static DoubleIterator asDoubleIterator(File file) throws IOException {
		return new BinIO.DoubleDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
	}

	public static DoubleIterator asDoubleIterator(CharSequence filename) throws IOException {
		return asDoubleIterator(new File(filename.toString()));
	}

	public static DoubleIterable asDoubleIterable(File file) {
		return () -> {
			try {
				return asDoubleIterator(file);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static DoubleIterable asDoubleIterable(CharSequence filename) {
		return () -> {
			try {
				return asDoubleIterator(filename);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static int loadBooleans(DataInput dataInput, boolean[] array, int offset, int length) throws IOException {
		BooleanArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dataInput.readBoolean();
			}
		} catch (EOFException var6) {
		}

		return i;
	}

	public static int loadBooleans(DataInput dataInput, boolean[] array) throws IOException {
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dataInput.readBoolean();
			}
		} catch (EOFException var4) {
		}

		return i;
	}

	public static int loadBooleans(File file, boolean[] array, int offset, int length) throws IOException {
		BooleanArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dis.readBoolean();
			}
		} catch (EOFException var8) {
		}

		dis.close();
		return i;
	}

	public static int loadBooleans(CharSequence filename, boolean[] array, int offset, int length) throws IOException {
		return loadBooleans(new File(filename.toString()), array, offset, length);
	}

	public static int loadBooleans(File file, boolean[] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dis.readBoolean();
			}
		} catch (EOFException var6) {
		}

		dis.close();
		return i;
	}

	public static int loadBooleans(CharSequence filename, boolean[] array) throws IOException {
		return loadBooleans(new File(filename.toString()), array);
	}

	public static boolean[] loadBooleans(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size();
		if (length > 2147483647L) {
			fis.close();
			throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
		} else {
			boolean[] array = new boolean[(int)length];
			DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

			for (int i = 0; (long)i < length; i++) {
				array[i] = dis.readBoolean();
			}

			dis.close();
			return array;
		}
	}

	public static boolean[] loadBooleans(CharSequence filename) throws IOException {
		return loadBooleans(new File(filename.toString()));
	}

	public static void storeBooleans(boolean[] array, int offset, int length, DataOutput dataOutput) throws IOException {
		BooleanArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			dataOutput.writeBoolean(array[offset + i]);
		}
	}

	public static void storeBooleans(boolean[] array, DataOutput dataOutput) throws IOException {
		int length = array.length;

		for (int i = 0; i < length; i++) {
			dataOutput.writeBoolean(array[i]);
		}
	}

	public static void storeBooleans(boolean[] array, int offset, int length, File file) throws IOException {
		BooleanArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeBoolean(array[offset + i]);
		}

		dos.close();
	}

	public static void storeBooleans(boolean[] array, int offset, int length, CharSequence filename) throws IOException {
		storeBooleans(array, offset, length, new File(filename.toString()));
	}

	public static void storeBooleans(boolean[] array, File file) throws IOException {
		int length = array.length;
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeBoolean(array[i]);
		}

		dos.close();
	}

	public static void storeBooleans(boolean[] array, CharSequence filename) throws IOException {
		storeBooleans(array, new File(filename.toString()));
	}

	public static long loadBooleans(DataInput dataInput, boolean[][] array, long offset, long length) throws IOException {
		BooleanBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				boolean[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dataInput.readBoolean();
					c++;
				}
			}
		} catch (EOFException var12) {
		}

		return c;
	}

	public static long loadBooleans(DataInput dataInput, boolean[][] array) throws IOException {
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				boolean[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dataInput.readBoolean();
					c++;
				}
			}
		} catch (EOFException var8) {
		}

		return c;
	}

	public static long loadBooleans(File file, boolean[][] array, long offset, long length) throws IOException {
		BooleanBigArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				boolean[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dis.readBoolean();
					c++;
				}
			}
		} catch (EOFException var14) {
		}

		dis.close();
		return c;
	}

	public static long loadBooleans(CharSequence filename, boolean[][] array, long offset, long length) throws IOException {
		return loadBooleans(new File(filename.toString()), array, offset, length);
	}

	public static long loadBooleans(File file, boolean[][] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				boolean[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dis.readBoolean();
					c++;
				}
			}
		} catch (EOFException var10) {
		}

		dis.close();
		return c;
	}

	public static long loadBooleans(CharSequence filename, boolean[][] array) throws IOException {
		return loadBooleans(new File(filename.toString()), array);
	}

	public static boolean[][] loadBooleansBig(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size();
		boolean[][] array = BooleanBigArrays.newBigArray(length);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

		for (int i = 0; i < array.length; i++) {
			boolean[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				t[d] = dis.readBoolean();
			}
		}

		dis.close();
		return array;
	}

	public static boolean[][] loadBooleansBig(CharSequence filename) throws IOException {
		return loadBooleansBig(new File(filename.toString()));
	}

	public static void storeBooleans(boolean[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
		BooleanBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			boolean[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dataOutput.writeBoolean(t[d]);
			}
		}
	}

	public static void storeBooleans(boolean[][] array, DataOutput dataOutput) throws IOException {
		for (int i = 0; i < array.length; i++) {
			boolean[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dataOutput.writeBoolean(t[d]);
			}
		}
	}

	public static void storeBooleans(boolean[][] array, long offset, long length, File file) throws IOException {
		BooleanBigArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			boolean[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dos.writeBoolean(t[d]);
			}
		}

		dos.close();
	}

	public static void storeBooleans(boolean[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeBooleans(array, offset, length, new File(filename.toString()));
	}

	public static void storeBooleans(boolean[][] array, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < array.length; i++) {
			boolean[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dos.writeBoolean(t[d]);
			}
		}

		dos.close();
	}

	public static void storeBooleans(boolean[][] array, CharSequence filename) throws IOException {
		storeBooleans(array, new File(filename.toString()));
	}

	public static void storeBooleans(BooleanIterator i, DataOutput dataOutput) throws IOException {
		while (i.hasNext()) {
			dataOutput.writeBoolean(i.nextBoolean());
		}
	}

	public static void storeBooleans(BooleanIterator i, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		while (i.hasNext()) {
			dos.writeBoolean(i.nextBoolean());
		}

		dos.close();
	}

	public static void storeBooleans(BooleanIterator i, CharSequence filename) throws IOException {
		storeBooleans(i, new File(filename.toString()));
	}

	public static BooleanIterator asBooleanIterator(DataInput dataInput) {
		return new BinIO.BooleanDataInputWrapper(dataInput);
	}

	public static BooleanIterator asBooleanIterator(File file) throws IOException {
		return new BinIO.BooleanDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
	}

	public static BooleanIterator asBooleanIterator(CharSequence filename) throws IOException {
		return asBooleanIterator(new File(filename.toString()));
	}

	public static BooleanIterable asBooleanIterable(File file) {
		return () -> {
			try {
				return asBooleanIterator(file);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static BooleanIterable asBooleanIterable(CharSequence filename) {
		return () -> {
			try {
				return asBooleanIterator(filename);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static int loadShorts(DataInput dataInput, short[] array, int offset, int length) throws IOException {
		ShortArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dataInput.readShort();
			}
		} catch (EOFException var6) {
		}

		return i;
	}

	public static int loadShorts(DataInput dataInput, short[] array) throws IOException {
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dataInput.readShort();
			}
		} catch (EOFException var4) {
		}

		return i;
	}

	public static int loadShorts(File file, short[] array, int offset, int length) throws IOException {
		ShortArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dis.readShort();
			}
		} catch (EOFException var8) {
		}

		dis.close();
		return i;
	}

	public static int loadShorts(CharSequence filename, short[] array, int offset, int length) throws IOException {
		return loadShorts(new File(filename.toString()), array, offset, length);
	}

	public static int loadShorts(File file, short[] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dis.readShort();
			}
		} catch (EOFException var6) {
		}

		dis.close();
		return i;
	}

	public static int loadShorts(CharSequence filename, short[] array) throws IOException {
		return loadShorts(new File(filename.toString()), array);
	}

	public static short[] loadShorts(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 2L;
		if (length > 2147483647L) {
			fis.close();
			throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
		} else {
			short[] array = new short[(int)length];
			DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

			for (int i = 0; (long)i < length; i++) {
				array[i] = dis.readShort();
			}

			dis.close();
			return array;
		}
	}

	public static short[] loadShorts(CharSequence filename) throws IOException {
		return loadShorts(new File(filename.toString()));
	}

	public static void storeShorts(short[] array, int offset, int length, DataOutput dataOutput) throws IOException {
		ShortArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			dataOutput.writeShort(array[offset + i]);
		}
	}

	public static void storeShorts(short[] array, DataOutput dataOutput) throws IOException {
		int length = array.length;

		for (int i = 0; i < length; i++) {
			dataOutput.writeShort(array[i]);
		}
	}

	public static void storeShorts(short[] array, int offset, int length, File file) throws IOException {
		ShortArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeShort(array[offset + i]);
		}

		dos.close();
	}

	public static void storeShorts(short[] array, int offset, int length, CharSequence filename) throws IOException {
		storeShorts(array, offset, length, new File(filename.toString()));
	}

	public static void storeShorts(short[] array, File file) throws IOException {
		int length = array.length;
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeShort(array[i]);
		}

		dos.close();
	}

	public static void storeShorts(short[] array, CharSequence filename) throws IOException {
		storeShorts(array, new File(filename.toString()));
	}

	public static long loadShorts(DataInput dataInput, short[][] array, long offset, long length) throws IOException {
		ShortBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				short[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dataInput.readShort();
					c++;
				}
			}
		} catch (EOFException var12) {
		}

		return c;
	}

	public static long loadShorts(DataInput dataInput, short[][] array) throws IOException {
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				short[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dataInput.readShort();
					c++;
				}
			}
		} catch (EOFException var8) {
		}

		return c;
	}

	public static long loadShorts(File file, short[][] array, long offset, long length) throws IOException {
		ShortBigArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				short[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dis.readShort();
					c++;
				}
			}
		} catch (EOFException var14) {
		}

		dis.close();
		return c;
	}

	public static long loadShorts(CharSequence filename, short[][] array, long offset, long length) throws IOException {
		return loadShorts(new File(filename.toString()), array, offset, length);
	}

	public static long loadShorts(File file, short[][] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				short[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dis.readShort();
					c++;
				}
			}
		} catch (EOFException var10) {
		}

		dis.close();
		return c;
	}

	public static long loadShorts(CharSequence filename, short[][] array) throws IOException {
		return loadShorts(new File(filename.toString()), array);
	}

	public static short[][] loadShortsBig(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 2L;
		short[][] array = ShortBigArrays.newBigArray(length);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

		for (int i = 0; i < array.length; i++) {
			short[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				t[d] = dis.readShort();
			}
		}

		dis.close();
		return array;
	}

	public static short[][] loadShortsBig(CharSequence filename) throws IOException {
		return loadShortsBig(new File(filename.toString()));
	}

	public static void storeShorts(short[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
		ShortBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			short[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dataOutput.writeShort(t[d]);
			}
		}
	}

	public static void storeShorts(short[][] array, DataOutput dataOutput) throws IOException {
		for (int i = 0; i < array.length; i++) {
			short[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dataOutput.writeShort(t[d]);
			}
		}
	}

	public static void storeShorts(short[][] array, long offset, long length, File file) throws IOException {
		ShortBigArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			short[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dos.writeShort(t[d]);
			}
		}

		dos.close();
	}

	public static void storeShorts(short[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeShorts(array, offset, length, new File(filename.toString()));
	}

	public static void storeShorts(short[][] array, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < array.length; i++) {
			short[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dos.writeShort(t[d]);
			}
		}

		dos.close();
	}

	public static void storeShorts(short[][] array, CharSequence filename) throws IOException {
		storeShorts(array, new File(filename.toString()));
	}

	public static void storeShorts(ShortIterator i, DataOutput dataOutput) throws IOException {
		while (i.hasNext()) {
			dataOutput.writeShort(i.nextShort());
		}
	}

	public static void storeShorts(ShortIterator i, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		while (i.hasNext()) {
			dos.writeShort(i.nextShort());
		}

		dos.close();
	}

	public static void storeShorts(ShortIterator i, CharSequence filename) throws IOException {
		storeShorts(i, new File(filename.toString()));
	}

	public static ShortIterator asShortIterator(DataInput dataInput) {
		return new BinIO.ShortDataInputWrapper(dataInput);
	}

	public static ShortIterator asShortIterator(File file) throws IOException {
		return new BinIO.ShortDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
	}

	public static ShortIterator asShortIterator(CharSequence filename) throws IOException {
		return asShortIterator(new File(filename.toString()));
	}

	public static ShortIterable asShortIterable(File file) {
		return () -> {
			try {
				return asShortIterator(file);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static ShortIterable asShortIterable(CharSequence filename) {
		return () -> {
			try {
				return asShortIterator(filename);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static int loadChars(DataInput dataInput, char[] array, int offset, int length) throws IOException {
		CharArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dataInput.readChar();
			}
		} catch (EOFException var6) {
		}

		return i;
	}

	public static int loadChars(DataInput dataInput, char[] array) throws IOException {
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dataInput.readChar();
			}
		} catch (EOFException var4) {
		}

		return i;
	}

	public static int loadChars(File file, char[] array, int offset, int length) throws IOException {
		CharArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dis.readChar();
			}
		} catch (EOFException var8) {
		}

		dis.close();
		return i;
	}

	public static int loadChars(CharSequence filename, char[] array, int offset, int length) throws IOException {
		return loadChars(new File(filename.toString()), array, offset, length);
	}

	public static int loadChars(File file, char[] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dis.readChar();
			}
		} catch (EOFException var6) {
		}

		dis.close();
		return i;
	}

	public static int loadChars(CharSequence filename, char[] array) throws IOException {
		return loadChars(new File(filename.toString()), array);
	}

	public static char[] loadChars(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 2L;
		if (length > 2147483647L) {
			fis.close();
			throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
		} else {
			char[] array = new char[(int)length];
			DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

			for (int i = 0; (long)i < length; i++) {
				array[i] = dis.readChar();
			}

			dis.close();
			return array;
		}
	}

	public static char[] loadChars(CharSequence filename) throws IOException {
		return loadChars(new File(filename.toString()));
	}

	public static void storeChars(char[] array, int offset, int length, DataOutput dataOutput) throws IOException {
		CharArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			dataOutput.writeChar(array[offset + i]);
		}
	}

	public static void storeChars(char[] array, DataOutput dataOutput) throws IOException {
		int length = array.length;

		for (int i = 0; i < length; i++) {
			dataOutput.writeChar(array[i]);
		}
	}

	public static void storeChars(char[] array, int offset, int length, File file) throws IOException {
		CharArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeChar(array[offset + i]);
		}

		dos.close();
	}

	public static void storeChars(char[] array, int offset, int length, CharSequence filename) throws IOException {
		storeChars(array, offset, length, new File(filename.toString()));
	}

	public static void storeChars(char[] array, File file) throws IOException {
		int length = array.length;
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeChar(array[i]);
		}

		dos.close();
	}

	public static void storeChars(char[] array, CharSequence filename) throws IOException {
		storeChars(array, new File(filename.toString()));
	}

	public static long loadChars(DataInput dataInput, char[][] array, long offset, long length) throws IOException {
		CharBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				char[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dataInput.readChar();
					c++;
				}
			}
		} catch (EOFException var12) {
		}

		return c;
	}

	public static long loadChars(DataInput dataInput, char[][] array) throws IOException {
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				char[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dataInput.readChar();
					c++;
				}
			}
		} catch (EOFException var8) {
		}

		return c;
	}

	public static long loadChars(File file, char[][] array, long offset, long length) throws IOException {
		CharBigArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				char[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dis.readChar();
					c++;
				}
			}
		} catch (EOFException var14) {
		}

		dis.close();
		return c;
	}

	public static long loadChars(CharSequence filename, char[][] array, long offset, long length) throws IOException {
		return loadChars(new File(filename.toString()), array, offset, length);
	}

	public static long loadChars(File file, char[][] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				char[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dis.readChar();
					c++;
				}
			}
		} catch (EOFException var10) {
		}

		dis.close();
		return c;
	}

	public static long loadChars(CharSequence filename, char[][] array) throws IOException {
		return loadChars(new File(filename.toString()), array);
	}

	public static char[][] loadCharsBig(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 2L;
		char[][] array = CharBigArrays.newBigArray(length);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

		for (int i = 0; i < array.length; i++) {
			char[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				t[d] = dis.readChar();
			}
		}

		dis.close();
		return array;
	}

	public static char[][] loadCharsBig(CharSequence filename) throws IOException {
		return loadCharsBig(new File(filename.toString()));
	}

	public static void storeChars(char[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
		CharBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			char[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dataOutput.writeChar(t[d]);
			}
		}
	}

	public static void storeChars(char[][] array, DataOutput dataOutput) throws IOException {
		for (int i = 0; i < array.length; i++) {
			char[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dataOutput.writeChar(t[d]);
			}
		}
	}

	public static void storeChars(char[][] array, long offset, long length, File file) throws IOException {
		CharBigArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			char[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dos.writeChar(t[d]);
			}
		}

		dos.close();
	}

	public static void storeChars(char[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeChars(array, offset, length, new File(filename.toString()));
	}

	public static void storeChars(char[][] array, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < array.length; i++) {
			char[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dos.writeChar(t[d]);
			}
		}

		dos.close();
	}

	public static void storeChars(char[][] array, CharSequence filename) throws IOException {
		storeChars(array, new File(filename.toString()));
	}

	public static void storeChars(CharIterator i, DataOutput dataOutput) throws IOException {
		while (i.hasNext()) {
			dataOutput.writeChar(i.nextChar());
		}
	}

	public static void storeChars(CharIterator i, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		while (i.hasNext()) {
			dos.writeChar(i.nextChar());
		}

		dos.close();
	}

	public static void storeChars(CharIterator i, CharSequence filename) throws IOException {
		storeChars(i, new File(filename.toString()));
	}

	public static CharIterator asCharIterator(DataInput dataInput) {
		return new BinIO.CharDataInputWrapper(dataInput);
	}

	public static CharIterator asCharIterator(File file) throws IOException {
		return new BinIO.CharDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
	}

	public static CharIterator asCharIterator(CharSequence filename) throws IOException {
		return asCharIterator(new File(filename.toString()));
	}

	public static CharIterable asCharIterable(File file) {
		return () -> {
			try {
				return asCharIterator(file);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static CharIterable asCharIterable(CharSequence filename) {
		return () -> {
			try {
				return asCharIterator(filename);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static int loadFloats(DataInput dataInput, float[] array, int offset, int length) throws IOException {
		FloatArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dataInput.readFloat();
			}
		} catch (EOFException var6) {
		}

		return i;
	}

	public static int loadFloats(DataInput dataInput, float[] array) throws IOException {
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dataInput.readFloat();
			}
		} catch (EOFException var4) {
		}

		return i;
	}

	public static int loadFloats(File file, float[] array, int offset, int length) throws IOException {
		FloatArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			for (i = 0; i < length; i++) {
				array[i + offset] = dis.readFloat();
			}
		} catch (EOFException var8) {
		}

		dis.close();
		return i;
	}

	public static int loadFloats(CharSequence filename, float[] array, int offset, int length) throws IOException {
		return loadFloats(new File(filename.toString()), array, offset, length);
	}

	public static int loadFloats(File file, float[] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		int i = 0;

		try {
			int length = array.length;

			for (i = 0; i < length; i++) {
				array[i] = dis.readFloat();
			}
		} catch (EOFException var6) {
		}

		dis.close();
		return i;
	}

	public static int loadFloats(CharSequence filename, float[] array) throws IOException {
		return loadFloats(new File(filename.toString()), array);
	}

	public static float[] loadFloats(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 4L;
		if (length > 2147483647L) {
			fis.close();
			throw new IllegalArgumentException("File too long: " + fis.getChannel().size() + " bytes (" + length + " elements)");
		} else {
			float[] array = new float[(int)length];
			DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

			for (int i = 0; (long)i < length; i++) {
				array[i] = dis.readFloat();
			}

			dis.close();
			return array;
		}
	}

	public static float[] loadFloats(CharSequence filename) throws IOException {
		return loadFloats(new File(filename.toString()));
	}

	public static void storeFloats(float[] array, int offset, int length, DataOutput dataOutput) throws IOException {
		FloatArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			dataOutput.writeFloat(array[offset + i]);
		}
	}

	public static void storeFloats(float[] array, DataOutput dataOutput) throws IOException {
		int length = array.length;

		for (int i = 0; i < length; i++) {
			dataOutput.writeFloat(array[i]);
		}
	}

	public static void storeFloats(float[] array, int offset, int length, File file) throws IOException {
		FloatArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeFloat(array[offset + i]);
		}

		dos.close();
	}

	public static void storeFloats(float[] array, int offset, int length, CharSequence filename) throws IOException {
		storeFloats(array, offset, length, new File(filename.toString()));
	}

	public static void storeFloats(float[] array, File file) throws IOException {
		int length = array.length;
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < length; i++) {
			dos.writeFloat(array[i]);
		}

		dos.close();
	}

	public static void storeFloats(float[] array, CharSequence filename) throws IOException {
		storeFloats(array, new File(filename.toString()));
	}

	public static long loadFloats(DataInput dataInput, float[][] array, long offset, long length) throws IOException {
		FloatBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				float[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dataInput.readFloat();
					c++;
				}
			}
		} catch (EOFException var12) {
		}

		return c;
	}

	public static long loadFloats(DataInput dataInput, float[][] array) throws IOException {
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				float[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dataInput.readFloat();
					c++;
				}
			}
		} catch (EOFException var8) {
		}

		return c;
	}

	public static long loadFloats(File file, float[][] array, long offset, long length) throws IOException {
		FloatBigArrays.ensureOffsetLength(array, offset, length);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				float[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					t[d] = dis.readFloat();
					c++;
				}
			}
		} catch (EOFException var14) {
		}

		dis.close();
		return c;
	}

	public static long loadFloats(CharSequence filename, float[][] array, long offset, long length) throws IOException {
		return loadFloats(new File(filename.toString()), array, offset, length);
	}

	public static long loadFloats(File file, float[][] array) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));
		long c = 0L;

		try {
			for (int i = 0; i < array.length; i++) {
				float[] t = array[i];
				int l = t.length;

				for (int d = 0; d < l; d++) {
					t[d] = dis.readFloat();
					c++;
				}
			}
		} catch (EOFException var10) {
		}

		dis.close();
		return c;
	}

	public static long loadFloats(CharSequence filename, float[][] array) throws IOException {
		return loadFloats(new File(filename.toString()), array);
	}

	public static float[][] loadFloatsBig(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		long length = fis.getChannel().size() / 4L;
		float[][] array = FloatBigArrays.newBigArray(length);
		DataInputStream dis = new DataInputStream(new FastBufferedInputStream(fis));

		for (int i = 0; i < array.length; i++) {
			float[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				t[d] = dis.readFloat();
			}
		}

		dis.close();
		return array;
	}

	public static float[][] loadFloatsBig(CharSequence filename) throws IOException {
		return loadFloatsBig(new File(filename.toString()));
	}

	public static void storeFloats(float[][] array, long offset, long length, DataOutput dataOutput) throws IOException {
		FloatBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			float[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dataOutput.writeFloat(t[d]);
			}
		}
	}

	public static void storeFloats(float[][] array, DataOutput dataOutput) throws IOException {
		for (int i = 0; i < array.length; i++) {
			float[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dataOutput.writeFloat(t[d]);
			}
		}
	}

	public static void storeFloats(float[][] array, long offset, long length, File file) throws IOException {
		FloatBigArrays.ensureOffsetLength(array, offset, length);
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			float[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				dos.writeFloat(t[d]);
			}
		}

		dos.close();
	}

	public static void storeFloats(float[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeFloats(array, offset, length, new File(filename.toString()));
	}

	public static void storeFloats(float[][] array, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		for (int i = 0; i < array.length; i++) {
			float[] t = array[i];
			int l = t.length;

			for (int d = 0; d < l; d++) {
				dos.writeFloat(t[d]);
			}
		}

		dos.close();
	}

	public static void storeFloats(float[][] array, CharSequence filename) throws IOException {
		storeFloats(array, new File(filename.toString()));
	}

	public static void storeFloats(FloatIterator i, DataOutput dataOutput) throws IOException {
		while (i.hasNext()) {
			dataOutput.writeFloat(i.nextFloat());
		}
	}

	public static void storeFloats(FloatIterator i, File file) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FastBufferedOutputStream(new FileOutputStream(file)));

		while (i.hasNext()) {
			dos.writeFloat(i.nextFloat());
		}

		dos.close();
	}

	public static void storeFloats(FloatIterator i, CharSequence filename) throws IOException {
		storeFloats(i, new File(filename.toString()));
	}

	public static FloatIterator asFloatIterator(DataInput dataInput) {
		return new BinIO.FloatDataInputWrapper(dataInput);
	}

	public static FloatIterator asFloatIterator(File file) throws IOException {
		return new BinIO.FloatDataInputWrapper(new DataInputStream(new FastBufferedInputStream(new FileInputStream(file))));
	}

	public static FloatIterator asFloatIterator(CharSequence filename) throws IOException {
		return asFloatIterator(new File(filename.toString()));
	}

	public static FloatIterable asFloatIterable(File file) {
		return () -> {
			try {
				return asFloatIterator(file);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	public static FloatIterable asFloatIterable(CharSequence filename) {
		return () -> {
			try {
				return asFloatIterator(filename);
			} catch (IOException var2) {
				throw new RuntimeException(var2);
			}
		};
	}

	private static final class BooleanDataInputWrapper implements BooleanIterator {
		private final DataInput dataInput;
		private boolean toAdvance = true;
		private boolean endOfProcess = false;
		private boolean next;

		public BooleanDataInputWrapper(DataInput dataInput) {
			this.dataInput = dataInput;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return !this.endOfProcess;
			} else {
				this.toAdvance = false;

				try {
					this.next = this.dataInput.readBoolean();
				} catch (EOFException var2) {
					this.endOfProcess = true;
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				return !this.endOfProcess;
			}
		}

		@Override
		public boolean nextBoolean() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.toAdvance = true;
				return this.next;
			}
		}
	}

	private static final class ByteDataInputWrapper implements ByteIterator {
		private final DataInput dataInput;
		private boolean toAdvance = true;
		private boolean endOfProcess = false;
		private byte next;

		public ByteDataInputWrapper(DataInput dataInput) {
			this.dataInput = dataInput;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return !this.endOfProcess;
			} else {
				this.toAdvance = false;

				try {
					this.next = this.dataInput.readByte();
				} catch (EOFException var2) {
					this.endOfProcess = true;
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				return !this.endOfProcess;
			}
		}

		@Override
		public byte nextByte() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.toAdvance = true;
				return this.next;
			}
		}
	}

	private static final class CharDataInputWrapper implements CharIterator {
		private final DataInput dataInput;
		private boolean toAdvance = true;
		private boolean endOfProcess = false;
		private char next;

		public CharDataInputWrapper(DataInput dataInput) {
			this.dataInput = dataInput;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return !this.endOfProcess;
			} else {
				this.toAdvance = false;

				try {
					this.next = this.dataInput.readChar();
				} catch (EOFException var2) {
					this.endOfProcess = true;
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				return !this.endOfProcess;
			}
		}

		@Override
		public char nextChar() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.toAdvance = true;
				return this.next;
			}
		}
	}

	private static final class DoubleDataInputWrapper implements DoubleIterator {
		private final DataInput dataInput;
		private boolean toAdvance = true;
		private boolean endOfProcess = false;
		private double next;

		public DoubleDataInputWrapper(DataInput dataInput) {
			this.dataInput = dataInput;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return !this.endOfProcess;
			} else {
				this.toAdvance = false;

				try {
					this.next = this.dataInput.readDouble();
				} catch (EOFException var2) {
					this.endOfProcess = true;
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				return !this.endOfProcess;
			}
		}

		@Override
		public double nextDouble() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.toAdvance = true;
				return this.next;
			}
		}
	}

	private static final class FloatDataInputWrapper implements FloatIterator {
		private final DataInput dataInput;
		private boolean toAdvance = true;
		private boolean endOfProcess = false;
		private float next;

		public FloatDataInputWrapper(DataInput dataInput) {
			this.dataInput = dataInput;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return !this.endOfProcess;
			} else {
				this.toAdvance = false;

				try {
					this.next = this.dataInput.readFloat();
				} catch (EOFException var2) {
					this.endOfProcess = true;
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				return !this.endOfProcess;
			}
		}

		@Override
		public float nextFloat() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.toAdvance = true;
				return this.next;
			}
		}
	}

	private static final class IntDataInputWrapper implements IntIterator {
		private final DataInput dataInput;
		private boolean toAdvance = true;
		private boolean endOfProcess = false;
		private int next;

		public IntDataInputWrapper(DataInput dataInput) {
			this.dataInput = dataInput;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return !this.endOfProcess;
			} else {
				this.toAdvance = false;

				try {
					this.next = this.dataInput.readInt();
				} catch (EOFException var2) {
					this.endOfProcess = true;
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				return !this.endOfProcess;
			}
		}

		@Override
		public int nextInt() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.toAdvance = true;
				return this.next;
			}
		}
	}

	private static final class LongDataInputWrapper implements LongIterator {
		private final DataInput dataInput;
		private boolean toAdvance = true;
		private boolean endOfProcess = false;
		private long next;

		public LongDataInputWrapper(DataInput dataInput) {
			this.dataInput = dataInput;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return !this.endOfProcess;
			} else {
				this.toAdvance = false;

				try {
					this.next = this.dataInput.readLong();
				} catch (EOFException var2) {
					this.endOfProcess = true;
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				return !this.endOfProcess;
			}
		}

		@Override
		public long nextLong() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.toAdvance = true;
				return this.next;
			}
		}
	}

	private static final class ShortDataInputWrapper implements ShortIterator {
		private final DataInput dataInput;
		private boolean toAdvance = true;
		private boolean endOfProcess = false;
		private short next;

		public ShortDataInputWrapper(DataInput dataInput) {
			this.dataInput = dataInput;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return !this.endOfProcess;
			} else {
				this.toAdvance = false;

				try {
					this.next = this.dataInput.readShort();
				} catch (EOFException var2) {
					this.endOfProcess = true;
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				return !this.endOfProcess;
			}
		}

		@Override
		public short nextShort() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.toAdvance = true;
				return this.next;
			}
		}
	}
}
