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
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;

public class TextIO {
	public static final int BUFFER_SIZE = 8192;

	private TextIO() {
	}

	public static int loadInts(BufferedReader reader, int[] array, int offset, int length) throws IOException {
		IntArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		String s;
		try {
			for (i = 0; i < length && (s = reader.readLine()) != null; i++) {
				array[i + offset] = Integer.parseInt(s.trim());
			}
		} catch (EOFException var7) {
		}

		return i;
	}

	public static int loadInts(BufferedReader reader, int[] array) throws IOException {
		return loadInts(reader, array, 0, array.length);
	}

	public static int loadInts(File file, int[] array, int offset, int length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int result = loadInts(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static int loadInts(CharSequence filename, int[] array, int offset, int length) throws IOException {
		return loadInts(new File(filename.toString()), array, offset, length);
	}

	public static int loadInts(File file, int[] array) throws IOException {
		return loadInts(file, array, 0, array.length);
	}

	public static int loadInts(CharSequence filename, int[] array) throws IOException {
		return loadInts(filename, array, 0, array.length);
	}

	public static void storeInts(int[] array, int offset, int length, PrintStream stream) {
		IntArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			stream.println(array[offset + i]);
		}
	}

	public static void storeInts(int[] array, PrintStream stream) {
		storeInts(array, 0, array.length, stream);
	}

	public static void storeInts(int[] array, int offset, int length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeInts(array, offset, length, stream);
		stream.close();
	}

	public static void storeInts(int[] array, int offset, int length, CharSequence filename) throws IOException {
		storeInts(array, offset, length, new File(filename.toString()));
	}

	public static void storeInts(int[] array, File file) throws IOException {
		storeInts(array, 0, array.length, file);
	}

	public static void storeInts(int[] array, CharSequence filename) throws IOException {
		storeInts(array, 0, array.length, filename);
	}

	public static void storeInts(IntIterator i, PrintStream stream) {
		while (i.hasNext()) {
			stream.println(i.nextInt());
		}
	}

	public static void storeInts(IntIterator i, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeInts(i, stream);
		stream.close();
	}

	public static void storeInts(IntIterator i, CharSequence filename) throws IOException {
		storeInts(i, new File(filename.toString()));
	}

	public static long loadInts(BufferedReader reader, int[][] array, long offset, long length) throws IOException {
		IntBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				int[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					String s;
					if ((s = reader.readLine()) == null) {
						return c;
					}

					t[d] = Integer.parseInt(s.trim());
					c++;
				}
			}
		} catch (EOFException var13) {
		}

		return c;
	}

	public static long loadInts(BufferedReader reader, int[][] array) throws IOException {
		return loadInts(reader, array, 0L, IntBigArrays.length(array));
	}

	public static long loadInts(File file, int[][] array, long offset, long length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		long result = loadInts(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static long loadInts(CharSequence filename, int[][] array, long offset, long length) throws IOException {
		return loadInts(new File(filename.toString()), array, offset, length);
	}

	public static long loadInts(File file, int[][] array) throws IOException {
		return loadInts(file, array, 0L, IntBigArrays.length(array));
	}

	public static long loadInts(CharSequence filename, int[][] array) throws IOException {
		return loadInts(filename, array, 0L, IntBigArrays.length(array));
	}

	public static void storeInts(int[][] array, long offset, long length, PrintStream stream) {
		IntBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			int[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				stream.println(t[d]);
			}
		}
	}

	public static void storeInts(int[][] array, PrintStream stream) {
		storeInts(array, 0L, IntBigArrays.length(array), stream);
	}

	public static void storeInts(int[][] array, long offset, long length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeInts(array, offset, length, stream);
		stream.close();
	}

	public static void storeInts(int[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeInts(array, offset, length, new File(filename.toString()));
	}

	public static void storeInts(int[][] array, File file) throws IOException {
		storeInts(array, 0L, IntBigArrays.length(array), file);
	}

	public static void storeInts(int[][] array, CharSequence filename) throws IOException {
		storeInts(array, 0L, IntBigArrays.length(array), filename);
	}

	public static IntIterator asIntIterator(BufferedReader reader) {
		return new TextIO.IntReaderWrapper(reader);
	}

	public static IntIterator asIntIterator(File file) throws IOException {
		return new TextIO.IntReaderWrapper(new BufferedReader(new FileReader(file)));
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

	public static int loadLongs(BufferedReader reader, long[] array, int offset, int length) throws IOException {
		LongArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		String s;
		try {
			for (i = 0; i < length && (s = reader.readLine()) != null; i++) {
				array[i + offset] = Long.parseLong(s.trim());
			}
		} catch (EOFException var7) {
		}

		return i;
	}

	public static int loadLongs(BufferedReader reader, long[] array) throws IOException {
		return loadLongs(reader, array, 0, array.length);
	}

	public static int loadLongs(File file, long[] array, int offset, int length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int result = loadLongs(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static int loadLongs(CharSequence filename, long[] array, int offset, int length) throws IOException {
		return loadLongs(new File(filename.toString()), array, offset, length);
	}

	public static int loadLongs(File file, long[] array) throws IOException {
		return loadLongs(file, array, 0, array.length);
	}

	public static int loadLongs(CharSequence filename, long[] array) throws IOException {
		return loadLongs(filename, array, 0, array.length);
	}

	public static void storeLongs(long[] array, int offset, int length, PrintStream stream) {
		LongArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			stream.println(array[offset + i]);
		}
	}

	public static void storeLongs(long[] array, PrintStream stream) {
		storeLongs(array, 0, array.length, stream);
	}

	public static void storeLongs(long[] array, int offset, int length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeLongs(array, offset, length, stream);
		stream.close();
	}

	public static void storeLongs(long[] array, int offset, int length, CharSequence filename) throws IOException {
		storeLongs(array, offset, length, new File(filename.toString()));
	}

	public static void storeLongs(long[] array, File file) throws IOException {
		storeLongs(array, 0, array.length, file);
	}

	public static void storeLongs(long[] array, CharSequence filename) throws IOException {
		storeLongs(array, 0, array.length, filename);
	}

	public static void storeLongs(LongIterator i, PrintStream stream) {
		while (i.hasNext()) {
			stream.println(i.nextLong());
		}
	}

	public static void storeLongs(LongIterator i, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeLongs(i, stream);
		stream.close();
	}

	public static void storeLongs(LongIterator i, CharSequence filename) throws IOException {
		storeLongs(i, new File(filename.toString()));
	}

	public static long loadLongs(BufferedReader reader, long[][] array, long offset, long length) throws IOException {
		LongBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				long[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					String s;
					if ((s = reader.readLine()) == null) {
						return c;
					}

					t[d] = Long.parseLong(s.trim());
					c++;
				}
			}
		} catch (EOFException var13) {
		}

		return c;
	}

	public static long loadLongs(BufferedReader reader, long[][] array) throws IOException {
		return loadLongs(reader, array, 0L, LongBigArrays.length(array));
	}

	public static long loadLongs(File file, long[][] array, long offset, long length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		long result = loadLongs(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static long loadLongs(CharSequence filename, long[][] array, long offset, long length) throws IOException {
		return loadLongs(new File(filename.toString()), array, offset, length);
	}

	public static long loadLongs(File file, long[][] array) throws IOException {
		return loadLongs(file, array, 0L, LongBigArrays.length(array));
	}

	public static long loadLongs(CharSequence filename, long[][] array) throws IOException {
		return loadLongs(filename, array, 0L, LongBigArrays.length(array));
	}

	public static void storeLongs(long[][] array, long offset, long length, PrintStream stream) {
		LongBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			long[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				stream.println(t[d]);
			}
		}
	}

	public static void storeLongs(long[][] array, PrintStream stream) {
		storeLongs(array, 0L, LongBigArrays.length(array), stream);
	}

	public static void storeLongs(long[][] array, long offset, long length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeLongs(array, offset, length, stream);
		stream.close();
	}

	public static void storeLongs(long[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeLongs(array, offset, length, new File(filename.toString()));
	}

	public static void storeLongs(long[][] array, File file) throws IOException {
		storeLongs(array, 0L, LongBigArrays.length(array), file);
	}

	public static void storeLongs(long[][] array, CharSequence filename) throws IOException {
		storeLongs(array, 0L, LongBigArrays.length(array), filename);
	}

	public static LongIterator asLongIterator(BufferedReader reader) {
		return new TextIO.LongReaderWrapper(reader);
	}

	public static LongIterator asLongIterator(File file) throws IOException {
		return new TextIO.LongReaderWrapper(new BufferedReader(new FileReader(file)));
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

	public static int loadDoubles(BufferedReader reader, double[] array, int offset, int length) throws IOException {
		DoubleArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		String s;
		try {
			for (i = 0; i < length && (s = reader.readLine()) != null; i++) {
				array[i + offset] = Double.parseDouble(s.trim());
			}
		} catch (EOFException var7) {
		}

		return i;
	}

	public static int loadDoubles(BufferedReader reader, double[] array) throws IOException {
		return loadDoubles(reader, array, 0, array.length);
	}

	public static int loadDoubles(File file, double[] array, int offset, int length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int result = loadDoubles(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static int loadDoubles(CharSequence filename, double[] array, int offset, int length) throws IOException {
		return loadDoubles(new File(filename.toString()), array, offset, length);
	}

	public static int loadDoubles(File file, double[] array) throws IOException {
		return loadDoubles(file, array, 0, array.length);
	}

	public static int loadDoubles(CharSequence filename, double[] array) throws IOException {
		return loadDoubles(filename, array, 0, array.length);
	}

	public static void storeDoubles(double[] array, int offset, int length, PrintStream stream) {
		DoubleArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			stream.println(array[offset + i]);
		}
	}

	public static void storeDoubles(double[] array, PrintStream stream) {
		storeDoubles(array, 0, array.length, stream);
	}

	public static void storeDoubles(double[] array, int offset, int length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeDoubles(array, offset, length, stream);
		stream.close();
	}

	public static void storeDoubles(double[] array, int offset, int length, CharSequence filename) throws IOException {
		storeDoubles(array, offset, length, new File(filename.toString()));
	}

	public static void storeDoubles(double[] array, File file) throws IOException {
		storeDoubles(array, 0, array.length, file);
	}

	public static void storeDoubles(double[] array, CharSequence filename) throws IOException {
		storeDoubles(array, 0, array.length, filename);
	}

	public static void storeDoubles(DoubleIterator i, PrintStream stream) {
		while (i.hasNext()) {
			stream.println(i.nextDouble());
		}
	}

	public static void storeDoubles(DoubleIterator i, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeDoubles(i, stream);
		stream.close();
	}

	public static void storeDoubles(DoubleIterator i, CharSequence filename) throws IOException {
		storeDoubles(i, new File(filename.toString()));
	}

	public static long loadDoubles(BufferedReader reader, double[][] array, long offset, long length) throws IOException {
		DoubleBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				double[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					String s;
					if ((s = reader.readLine()) == null) {
						return c;
					}

					t[d] = Double.parseDouble(s.trim());
					c++;
				}
			}
		} catch (EOFException var13) {
		}

		return c;
	}

	public static long loadDoubles(BufferedReader reader, double[][] array) throws IOException {
		return loadDoubles(reader, array, 0L, DoubleBigArrays.length(array));
	}

	public static long loadDoubles(File file, double[][] array, long offset, long length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		long result = loadDoubles(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static long loadDoubles(CharSequence filename, double[][] array, long offset, long length) throws IOException {
		return loadDoubles(new File(filename.toString()), array, offset, length);
	}

	public static long loadDoubles(File file, double[][] array) throws IOException {
		return loadDoubles(file, array, 0L, DoubleBigArrays.length(array));
	}

	public static long loadDoubles(CharSequence filename, double[][] array) throws IOException {
		return loadDoubles(filename, array, 0L, DoubleBigArrays.length(array));
	}

	public static void storeDoubles(double[][] array, long offset, long length, PrintStream stream) {
		DoubleBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			double[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				stream.println(t[d]);
			}
		}
	}

	public static void storeDoubles(double[][] array, PrintStream stream) {
		storeDoubles(array, 0L, DoubleBigArrays.length(array), stream);
	}

	public static void storeDoubles(double[][] array, long offset, long length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeDoubles(array, offset, length, stream);
		stream.close();
	}

	public static void storeDoubles(double[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeDoubles(array, offset, length, new File(filename.toString()));
	}

	public static void storeDoubles(double[][] array, File file) throws IOException {
		storeDoubles(array, 0L, DoubleBigArrays.length(array), file);
	}

	public static void storeDoubles(double[][] array, CharSequence filename) throws IOException {
		storeDoubles(array, 0L, DoubleBigArrays.length(array), filename);
	}

	public static DoubleIterator asDoubleIterator(BufferedReader reader) {
		return new TextIO.DoubleReaderWrapper(reader);
	}

	public static DoubleIterator asDoubleIterator(File file) throws IOException {
		return new TextIO.DoubleReaderWrapper(new BufferedReader(new FileReader(file)));
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

	public static int loadBooleans(BufferedReader reader, boolean[] array, int offset, int length) throws IOException {
		BooleanArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		String s;
		try {
			for (i = 0; i < length && (s = reader.readLine()) != null; i++) {
				array[i + offset] = Boolean.parseBoolean(s.trim());
			}
		} catch (EOFException var7) {
		}

		return i;
	}

	public static int loadBooleans(BufferedReader reader, boolean[] array) throws IOException {
		return loadBooleans(reader, array, 0, array.length);
	}

	public static int loadBooleans(File file, boolean[] array, int offset, int length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int result = loadBooleans(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static int loadBooleans(CharSequence filename, boolean[] array, int offset, int length) throws IOException {
		return loadBooleans(new File(filename.toString()), array, offset, length);
	}

	public static int loadBooleans(File file, boolean[] array) throws IOException {
		return loadBooleans(file, array, 0, array.length);
	}

	public static int loadBooleans(CharSequence filename, boolean[] array) throws IOException {
		return loadBooleans(filename, array, 0, array.length);
	}

	public static void storeBooleans(boolean[] array, int offset, int length, PrintStream stream) {
		BooleanArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			stream.println(array[offset + i]);
		}
	}

	public static void storeBooleans(boolean[] array, PrintStream stream) {
		storeBooleans(array, 0, array.length, stream);
	}

	public static void storeBooleans(boolean[] array, int offset, int length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeBooleans(array, offset, length, stream);
		stream.close();
	}

	public static void storeBooleans(boolean[] array, int offset, int length, CharSequence filename) throws IOException {
		storeBooleans(array, offset, length, new File(filename.toString()));
	}

	public static void storeBooleans(boolean[] array, File file) throws IOException {
		storeBooleans(array, 0, array.length, file);
	}

	public static void storeBooleans(boolean[] array, CharSequence filename) throws IOException {
		storeBooleans(array, 0, array.length, filename);
	}

	public static void storeBooleans(BooleanIterator i, PrintStream stream) {
		while (i.hasNext()) {
			stream.println(i.nextBoolean());
		}
	}

	public static void storeBooleans(BooleanIterator i, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeBooleans(i, stream);
		stream.close();
	}

	public static void storeBooleans(BooleanIterator i, CharSequence filename) throws IOException {
		storeBooleans(i, new File(filename.toString()));
	}

	public static long loadBooleans(BufferedReader reader, boolean[][] array, long offset, long length) throws IOException {
		BooleanBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				boolean[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					String s;
					if ((s = reader.readLine()) == null) {
						return c;
					}

					t[d] = Boolean.parseBoolean(s.trim());
					c++;
				}
			}
		} catch (EOFException var13) {
		}

		return c;
	}

	public static long loadBooleans(BufferedReader reader, boolean[][] array) throws IOException {
		return loadBooleans(reader, array, 0L, BooleanBigArrays.length(array));
	}

	public static long loadBooleans(File file, boolean[][] array, long offset, long length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		long result = loadBooleans(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static long loadBooleans(CharSequence filename, boolean[][] array, long offset, long length) throws IOException {
		return loadBooleans(new File(filename.toString()), array, offset, length);
	}

	public static long loadBooleans(File file, boolean[][] array) throws IOException {
		return loadBooleans(file, array, 0L, BooleanBigArrays.length(array));
	}

	public static long loadBooleans(CharSequence filename, boolean[][] array) throws IOException {
		return loadBooleans(filename, array, 0L, BooleanBigArrays.length(array));
	}

	public static void storeBooleans(boolean[][] array, long offset, long length, PrintStream stream) {
		BooleanBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			boolean[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				stream.println(t[d]);
			}
		}
	}

	public static void storeBooleans(boolean[][] array, PrintStream stream) {
		storeBooleans(array, 0L, BooleanBigArrays.length(array), stream);
	}

	public static void storeBooleans(boolean[][] array, long offset, long length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeBooleans(array, offset, length, stream);
		stream.close();
	}

	public static void storeBooleans(boolean[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeBooleans(array, offset, length, new File(filename.toString()));
	}

	public static void storeBooleans(boolean[][] array, File file) throws IOException {
		storeBooleans(array, 0L, BooleanBigArrays.length(array), file);
	}

	public static void storeBooleans(boolean[][] array, CharSequence filename) throws IOException {
		storeBooleans(array, 0L, BooleanBigArrays.length(array), filename);
	}

	public static BooleanIterator asBooleanIterator(BufferedReader reader) {
		return new TextIO.BooleanReaderWrapper(reader);
	}

	public static BooleanIterator asBooleanIterator(File file) throws IOException {
		return new TextIO.BooleanReaderWrapper(new BufferedReader(new FileReader(file)));
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

	public static int loadBytes(BufferedReader reader, byte[] array, int offset, int length) throws IOException {
		ByteArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		String s;
		try {
			for (i = 0; i < length && (s = reader.readLine()) != null; i++) {
				array[i + offset] = Byte.parseByte(s.trim());
			}
		} catch (EOFException var7) {
		}

		return i;
	}

	public static int loadBytes(BufferedReader reader, byte[] array) throws IOException {
		return loadBytes(reader, array, 0, array.length);
	}

	public static int loadBytes(File file, byte[] array, int offset, int length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int result = loadBytes(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static int loadBytes(CharSequence filename, byte[] array, int offset, int length) throws IOException {
		return loadBytes(new File(filename.toString()), array, offset, length);
	}

	public static int loadBytes(File file, byte[] array) throws IOException {
		return loadBytes(file, array, 0, array.length);
	}

	public static int loadBytes(CharSequence filename, byte[] array) throws IOException {
		return loadBytes(filename, array, 0, array.length);
	}

	public static void storeBytes(byte[] array, int offset, int length, PrintStream stream) {
		ByteArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			stream.println(array[offset + i]);
		}
	}

	public static void storeBytes(byte[] array, PrintStream stream) {
		storeBytes(array, 0, array.length, stream);
	}

	public static void storeBytes(byte[] array, int offset, int length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeBytes(array, offset, length, stream);
		stream.close();
	}

	public static void storeBytes(byte[] array, int offset, int length, CharSequence filename) throws IOException {
		storeBytes(array, offset, length, new File(filename.toString()));
	}

	public static void storeBytes(byte[] array, File file) throws IOException {
		storeBytes(array, 0, array.length, file);
	}

	public static void storeBytes(byte[] array, CharSequence filename) throws IOException {
		storeBytes(array, 0, array.length, filename);
	}

	public static void storeBytes(ByteIterator i, PrintStream stream) {
		while (i.hasNext()) {
			stream.println(i.nextByte());
		}
	}

	public static void storeBytes(ByteIterator i, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeBytes(i, stream);
		stream.close();
	}

	public static void storeBytes(ByteIterator i, CharSequence filename) throws IOException {
		storeBytes(i, new File(filename.toString()));
	}

	public static long loadBytes(BufferedReader reader, byte[][] array, long offset, long length) throws IOException {
		ByteBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				byte[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					String s;
					if ((s = reader.readLine()) == null) {
						return c;
					}

					t[d] = Byte.parseByte(s.trim());
					c++;
				}
			}
		} catch (EOFException var13) {
		}

		return c;
	}

	public static long loadBytes(BufferedReader reader, byte[][] array) throws IOException {
		return loadBytes(reader, array, 0L, ByteBigArrays.length(array));
	}

	public static long loadBytes(File file, byte[][] array, long offset, long length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		long result = loadBytes(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static long loadBytes(CharSequence filename, byte[][] array, long offset, long length) throws IOException {
		return loadBytes(new File(filename.toString()), array, offset, length);
	}

	public static long loadBytes(File file, byte[][] array) throws IOException {
		return loadBytes(file, array, 0L, ByteBigArrays.length(array));
	}

	public static long loadBytes(CharSequence filename, byte[][] array) throws IOException {
		return loadBytes(filename, array, 0L, ByteBigArrays.length(array));
	}

	public static void storeBytes(byte[][] array, long offset, long length, PrintStream stream) {
		ByteBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			byte[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				stream.println(t[d]);
			}
		}
	}

	public static void storeBytes(byte[][] array, PrintStream stream) {
		storeBytes(array, 0L, ByteBigArrays.length(array), stream);
	}

	public static void storeBytes(byte[][] array, long offset, long length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeBytes(array, offset, length, stream);
		stream.close();
	}

	public static void storeBytes(byte[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeBytes(array, offset, length, new File(filename.toString()));
	}

	public static void storeBytes(byte[][] array, File file) throws IOException {
		storeBytes(array, 0L, ByteBigArrays.length(array), file);
	}

	public static void storeBytes(byte[][] array, CharSequence filename) throws IOException {
		storeBytes(array, 0L, ByteBigArrays.length(array), filename);
	}

	public static ByteIterator asByteIterator(BufferedReader reader) {
		return new TextIO.ByteReaderWrapper(reader);
	}

	public static ByteIterator asByteIterator(File file) throws IOException {
		return new TextIO.ByteReaderWrapper(new BufferedReader(new FileReader(file)));
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

	public static int loadShorts(BufferedReader reader, short[] array, int offset, int length) throws IOException {
		ShortArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		String s;
		try {
			for (i = 0; i < length && (s = reader.readLine()) != null; i++) {
				array[i + offset] = Short.parseShort(s.trim());
			}
		} catch (EOFException var7) {
		}

		return i;
	}

	public static int loadShorts(BufferedReader reader, short[] array) throws IOException {
		return loadShorts(reader, array, 0, array.length);
	}

	public static int loadShorts(File file, short[] array, int offset, int length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int result = loadShorts(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static int loadShorts(CharSequence filename, short[] array, int offset, int length) throws IOException {
		return loadShorts(new File(filename.toString()), array, offset, length);
	}

	public static int loadShorts(File file, short[] array) throws IOException {
		return loadShorts(file, array, 0, array.length);
	}

	public static int loadShorts(CharSequence filename, short[] array) throws IOException {
		return loadShorts(filename, array, 0, array.length);
	}

	public static void storeShorts(short[] array, int offset, int length, PrintStream stream) {
		ShortArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			stream.println(array[offset + i]);
		}
	}

	public static void storeShorts(short[] array, PrintStream stream) {
		storeShorts(array, 0, array.length, stream);
	}

	public static void storeShorts(short[] array, int offset, int length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeShorts(array, offset, length, stream);
		stream.close();
	}

	public static void storeShorts(short[] array, int offset, int length, CharSequence filename) throws IOException {
		storeShorts(array, offset, length, new File(filename.toString()));
	}

	public static void storeShorts(short[] array, File file) throws IOException {
		storeShorts(array, 0, array.length, file);
	}

	public static void storeShorts(short[] array, CharSequence filename) throws IOException {
		storeShorts(array, 0, array.length, filename);
	}

	public static void storeShorts(ShortIterator i, PrintStream stream) {
		while (i.hasNext()) {
			stream.println(i.nextShort());
		}
	}

	public static void storeShorts(ShortIterator i, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeShorts(i, stream);
		stream.close();
	}

	public static void storeShorts(ShortIterator i, CharSequence filename) throws IOException {
		storeShorts(i, new File(filename.toString()));
	}

	public static long loadShorts(BufferedReader reader, short[][] array, long offset, long length) throws IOException {
		ShortBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				short[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					String s;
					if ((s = reader.readLine()) == null) {
						return c;
					}

					t[d] = Short.parseShort(s.trim());
					c++;
				}
			}
		} catch (EOFException var13) {
		}

		return c;
	}

	public static long loadShorts(BufferedReader reader, short[][] array) throws IOException {
		return loadShorts(reader, array, 0L, ShortBigArrays.length(array));
	}

	public static long loadShorts(File file, short[][] array, long offset, long length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		long result = loadShorts(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static long loadShorts(CharSequence filename, short[][] array, long offset, long length) throws IOException {
		return loadShorts(new File(filename.toString()), array, offset, length);
	}

	public static long loadShorts(File file, short[][] array) throws IOException {
		return loadShorts(file, array, 0L, ShortBigArrays.length(array));
	}

	public static long loadShorts(CharSequence filename, short[][] array) throws IOException {
		return loadShorts(filename, array, 0L, ShortBigArrays.length(array));
	}

	public static void storeShorts(short[][] array, long offset, long length, PrintStream stream) {
		ShortBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			short[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				stream.println(t[d]);
			}
		}
	}

	public static void storeShorts(short[][] array, PrintStream stream) {
		storeShorts(array, 0L, ShortBigArrays.length(array), stream);
	}

	public static void storeShorts(short[][] array, long offset, long length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeShorts(array, offset, length, stream);
		stream.close();
	}

	public static void storeShorts(short[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeShorts(array, offset, length, new File(filename.toString()));
	}

	public static void storeShorts(short[][] array, File file) throws IOException {
		storeShorts(array, 0L, ShortBigArrays.length(array), file);
	}

	public static void storeShorts(short[][] array, CharSequence filename) throws IOException {
		storeShorts(array, 0L, ShortBigArrays.length(array), filename);
	}

	public static ShortIterator asShortIterator(BufferedReader reader) {
		return new TextIO.ShortReaderWrapper(reader);
	}

	public static ShortIterator asShortIterator(File file) throws IOException {
		return new TextIO.ShortReaderWrapper(new BufferedReader(new FileReader(file)));
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

	public static int loadFloats(BufferedReader reader, float[] array, int offset, int length) throws IOException {
		FloatArrays.ensureOffsetLength(array, offset, length);
		int i = 0;

		String s;
		try {
			for (i = 0; i < length && (s = reader.readLine()) != null; i++) {
				array[i + offset] = Float.parseFloat(s.trim());
			}
		} catch (EOFException var7) {
		}

		return i;
	}

	public static int loadFloats(BufferedReader reader, float[] array) throws IOException {
		return loadFloats(reader, array, 0, array.length);
	}

	public static int loadFloats(File file, float[] array, int offset, int length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int result = loadFloats(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static int loadFloats(CharSequence filename, float[] array, int offset, int length) throws IOException {
		return loadFloats(new File(filename.toString()), array, offset, length);
	}

	public static int loadFloats(File file, float[] array) throws IOException {
		return loadFloats(file, array, 0, array.length);
	}

	public static int loadFloats(CharSequence filename, float[] array) throws IOException {
		return loadFloats(filename, array, 0, array.length);
	}

	public static void storeFloats(float[] array, int offset, int length, PrintStream stream) {
		FloatArrays.ensureOffsetLength(array, offset, length);

		for (int i = 0; i < length; i++) {
			stream.println(array[offset + i]);
		}
	}

	public static void storeFloats(float[] array, PrintStream stream) {
		storeFloats(array, 0, array.length, stream);
	}

	public static void storeFloats(float[] array, int offset, int length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeFloats(array, offset, length, stream);
		stream.close();
	}

	public static void storeFloats(float[] array, int offset, int length, CharSequence filename) throws IOException {
		storeFloats(array, offset, length, new File(filename.toString()));
	}

	public static void storeFloats(float[] array, File file) throws IOException {
		storeFloats(array, 0, array.length, file);
	}

	public static void storeFloats(float[] array, CharSequence filename) throws IOException {
		storeFloats(array, 0, array.length, filename);
	}

	public static void storeFloats(FloatIterator i, PrintStream stream) {
		while (i.hasNext()) {
			stream.println(i.nextFloat());
		}
	}

	public static void storeFloats(FloatIterator i, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeFloats(i, stream);
		stream.close();
	}

	public static void storeFloats(FloatIterator i, CharSequence filename) throws IOException {
		storeFloats(i, new File(filename.toString()));
	}

	public static long loadFloats(BufferedReader reader, float[][] array, long offset, long length) throws IOException {
		FloatBigArrays.ensureOffsetLength(array, offset, length);
		long c = 0L;

		try {
			for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
				float[] t = array[i];
				int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

				for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
					String s;
					if ((s = reader.readLine()) == null) {
						return c;
					}

					t[d] = Float.parseFloat(s.trim());
					c++;
				}
			}
		} catch (EOFException var13) {
		}

		return c;
	}

	public static long loadFloats(BufferedReader reader, float[][] array) throws IOException {
		return loadFloats(reader, array, 0L, FloatBigArrays.length(array));
	}

	public static long loadFloats(File file, float[][] array, long offset, long length) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		long result = loadFloats(reader, array, offset, length);
		reader.close();
		return result;
	}

	public static long loadFloats(CharSequence filename, float[][] array, long offset, long length) throws IOException {
		return loadFloats(new File(filename.toString()), array, offset, length);
	}

	public static long loadFloats(File file, float[][] array) throws IOException {
		return loadFloats(file, array, 0L, FloatBigArrays.length(array));
	}

	public static long loadFloats(CharSequence filename, float[][] array) throws IOException {
		return loadFloats(filename, array, 0L, FloatBigArrays.length(array));
	}

	public static void storeFloats(float[][] array, long offset, long length, PrintStream stream) {
		FloatBigArrays.ensureOffsetLength(array, offset, length);

		for (int i = BigArrays.segment(offset); i < BigArrays.segment(offset + length + 134217727L); i++) {
			float[] t = array[i];
			int l = (int)Math.min((long)t.length, offset + length - BigArrays.start(i));

			for (int d = (int)Math.max(0L, offset - BigArrays.start(i)); d < l; d++) {
				stream.println(t[d]);
			}
		}
	}

	public static void storeFloats(float[][] array, PrintStream stream) {
		storeFloats(array, 0L, FloatBigArrays.length(array), stream);
	}

	public static void storeFloats(float[][] array, long offset, long length, File file) throws IOException {
		PrintStream stream = new PrintStream(new FastBufferedOutputStream(new FileOutputStream(file)));
		storeFloats(array, offset, length, stream);
		stream.close();
	}

	public static void storeFloats(float[][] array, long offset, long length, CharSequence filename) throws IOException {
		storeFloats(array, offset, length, new File(filename.toString()));
	}

	public static void storeFloats(float[][] array, File file) throws IOException {
		storeFloats(array, 0L, FloatBigArrays.length(array), file);
	}

	public static void storeFloats(float[][] array, CharSequence filename) throws IOException {
		storeFloats(array, 0L, FloatBigArrays.length(array), filename);
	}

	public static FloatIterator asFloatIterator(BufferedReader reader) {
		return new TextIO.FloatReaderWrapper(reader);
	}

	public static FloatIterator asFloatIterator(File file) throws IOException {
		return new TextIO.FloatReaderWrapper(new BufferedReader(new FileReader(file)));
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

	private static final class BooleanReaderWrapper implements BooleanIterator {
		private final BufferedReader reader;
		private boolean toAdvance = true;
		private String s;
		private boolean next;

		public BooleanReaderWrapper(BufferedReader reader) {
			this.reader = reader;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return this.s != null;
			} else {
				this.toAdvance = false;

				try {
					this.s = this.reader.readLine();
				} catch (EOFException var2) {
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				if (this.s == null) {
					return false;
				} else {
					this.next = Boolean.parseBoolean(this.s.trim());
					return true;
				}
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

	private static final class ByteReaderWrapper implements ByteIterator {
		private final BufferedReader reader;
		private boolean toAdvance = true;
		private String s;
		private byte next;

		public ByteReaderWrapper(BufferedReader reader) {
			this.reader = reader;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return this.s != null;
			} else {
				this.toAdvance = false;

				try {
					this.s = this.reader.readLine();
				} catch (EOFException var2) {
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				if (this.s == null) {
					return false;
				} else {
					this.next = Byte.parseByte(this.s.trim());
					return true;
				}
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

	private static final class DoubleReaderWrapper implements DoubleIterator {
		private final BufferedReader reader;
		private boolean toAdvance = true;
		private String s;
		private double next;

		public DoubleReaderWrapper(BufferedReader reader) {
			this.reader = reader;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return this.s != null;
			} else {
				this.toAdvance = false;

				try {
					this.s = this.reader.readLine();
				} catch (EOFException var2) {
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				if (this.s == null) {
					return false;
				} else {
					this.next = Double.parseDouble(this.s.trim());
					return true;
				}
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

	private static final class FloatReaderWrapper implements FloatIterator {
		private final BufferedReader reader;
		private boolean toAdvance = true;
		private String s;
		private float next;

		public FloatReaderWrapper(BufferedReader reader) {
			this.reader = reader;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return this.s != null;
			} else {
				this.toAdvance = false;

				try {
					this.s = this.reader.readLine();
				} catch (EOFException var2) {
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				if (this.s == null) {
					return false;
				} else {
					this.next = Float.parseFloat(this.s.trim());
					return true;
				}
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

	private static final class IntReaderWrapper implements IntIterator {
		private final BufferedReader reader;
		private boolean toAdvance = true;
		private String s;
		private int next;

		public IntReaderWrapper(BufferedReader reader) {
			this.reader = reader;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return this.s != null;
			} else {
				this.toAdvance = false;

				try {
					this.s = this.reader.readLine();
				} catch (EOFException var2) {
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				if (this.s == null) {
					return false;
				} else {
					this.next = Integer.parseInt(this.s.trim());
					return true;
				}
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

	private static final class LongReaderWrapper implements LongIterator {
		private final BufferedReader reader;
		private boolean toAdvance = true;
		private String s;
		private long next;

		public LongReaderWrapper(BufferedReader reader) {
			this.reader = reader;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return this.s != null;
			} else {
				this.toAdvance = false;

				try {
					this.s = this.reader.readLine();
				} catch (EOFException var2) {
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				if (this.s == null) {
					return false;
				} else {
					this.next = Long.parseLong(this.s.trim());
					return true;
				}
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

	private static final class ShortReaderWrapper implements ShortIterator {
		private final BufferedReader reader;
		private boolean toAdvance = true;
		private String s;
		private short next;

		public ShortReaderWrapper(BufferedReader reader) {
			this.reader = reader;
		}

		public boolean hasNext() {
			if (!this.toAdvance) {
				return this.s != null;
			} else {
				this.toAdvance = false;

				try {
					this.s = this.reader.readLine();
				} catch (EOFException var2) {
				} catch (IOException var3) {
					throw new RuntimeException(var3);
				}

				if (this.s == null) {
					return false;
				} else {
					this.next = Short.parseShort(this.s.trim());
					return true;
				}
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
