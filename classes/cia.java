import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cia implements AutoCloseable {
	private static final Logger a = LogManager.getLogger();
	private static final ByteBuffer b = ByteBuffer.allocateDirect(1);
	private final FileChannel c;
	private final Path d;
	private final cic e;
	private final ByteBuffer f = ByteBuffer.allocateDirect(8192);
	private final IntBuffer g;
	private final IntBuffer h;
	private final chz i = new chz();

	public cia(File file1, File file2, boolean boolean3) throws IOException {
		this(file1.toPath(), file2.toPath(), cic.b, boolean3);
	}

	public cia(Path path1, Path path2, cic cic, boolean boolean4) throws IOException {
		this.e = cic;
		if (!Files.isDirectory(path2, new LinkOption[0])) {
			throw new IllegalArgumentException("Expected directory, got " + path2.toAbsolutePath());
		} else {
			this.d = path2;
			this.g = this.f.asIntBuffer();
			this.g.limit(1024);
			this.f.position(4096);
			this.h = this.f.asIntBuffer();
			if (boolean4) {
				this.c = FileChannel.open(path1, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC);
			} else {
				this.c = FileChannel.open(path1, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
			}

			this.i.a(0, 2);
			this.f.position(0);
			int integer6 = this.c.read(this.f, 0L);
			if (integer6 != -1) {
				if (integer6 != 8192) {
					a.warn("Region file {} has truncated header: {}", path1, integer6);
				}

				for (int integer7 = 0; integer7 < 1024; integer7++) {
					int integer8 = this.g.get(integer7);
					if (integer8 != 0) {
						int integer9 = b(integer8);
						int integer10 = a(integer8);
						this.i.a(integer9, integer10);
					}
				}
			}
		}
	}

	private Path e(bph bph) {
		String string3 = "c." + bph.b + "." + bph.c + ".mcc";
		return this.d.resolve(string3);
	}

	@Nullable
	public synchronized DataInputStream a(bph bph) throws IOException {
		int integer3 = this.f(bph);
		if (integer3 == 0) {
			return null;
		} else {
			int integer4 = b(integer3);
			int integer5 = a(integer3);
			int integer6 = integer5 * 4096;
			ByteBuffer byteBuffer7 = ByteBuffer.allocate(integer6);
			this.c.read(byteBuffer7, (long)(integer4 * 4096));
			byteBuffer7.flip();
			if (byteBuffer7.remaining() < 5) {
				a.error("Chunk {} header is truncated: expected {} but read {}", bph, integer6, byteBuffer7.remaining());
				return null;
			} else {
				int integer8 = byteBuffer7.getInt();
				byte byte9 = byteBuffer7.get();
				if (integer8 == 0) {
					a.warn("Chunk {} is allocated, but stream is missing", bph);
					return null;
				} else {
					int integer10 = integer8 - 1;
					if (a(byte9)) {
						if (integer10 != 0) {
							a.warn("Chunk has both internal and external streams");
						}

						return this.a(bph, b(byte9));
					} else if (integer10 > byteBuffer7.remaining()) {
						a.error("Chunk {} stream is truncated: expected {} but read {}", bph, integer10, byteBuffer7.remaining());
						return null;
					} else if (integer10 < 0) {
						a.error("Declared size {} of chunk {} is negative", integer8, bph);
						return null;
					} else {
						return this.a(bph, byte9, a(byteBuffer7, integer10));
					}
				}
			}
		}
	}

	private static boolean a(byte byte1) {
		return (byte1 & 128) != 0;
	}

	private static byte b(byte byte1) {
		return (byte)(byte1 & -129);
	}

	@Nullable
	private DataInputStream a(bph bph, byte byte2, InputStream inputStream) throws IOException {
		cic cic5 = cic.a(byte2);
		if (cic5 == null) {
			a.error("Chunk {} has invalid chunk stream version {}", bph, byte2);
			return null;
		} else {
			return new DataInputStream(new BufferedInputStream(cic5.a(inputStream)));
		}
	}

	@Nullable
	private DataInputStream a(bph bph, byte byte2) throws IOException {
		Path path4 = this.e(bph);
		if (!Files.isRegularFile(path4, new LinkOption[0])) {
			a.error("External chunk path {} is not file", path4);
			return null;
		} else {
			return this.a(bph, byte2, Files.newInputStream(path4));
		}
	}

	private static ByteArrayInputStream a(ByteBuffer byteBuffer, int integer) {
		return new ByteArrayInputStream(byteBuffer.array(), byteBuffer.position(), integer);
	}

	private int a(int integer1, int integer2) {
		return integer1 << 8 | integer2;
	}

	private static int a(int integer) {
		return integer & 0xFF;
	}

	private static int b(int integer) {
		return integer >> 8;
	}

	private static int c(int integer) {
		return (integer + 4096 - 1) / 4096;
	}

	public boolean b(bph bph) {
		int integer3 = this.f(bph);
		if (integer3 == 0) {
			return false;
		} else {
			int integer4 = b(integer3);
			int integer5 = a(integer3);
			ByteBuffer byteBuffer6 = ByteBuffer.allocate(5);

			try {
				this.c.read(byteBuffer6, (long)(integer4 * 4096));
				byteBuffer6.flip();
				if (byteBuffer6.remaining() != 5) {
					return false;
				} else {
					int integer7 = byteBuffer6.getInt();
					byte byte8 = byteBuffer6.get();
					if (a(byte8)) {
						if (!cic.b(b(byte8))) {
							return false;
						}

						if (!Files.isRegularFile(this.e(bph), new LinkOption[0])) {
							return false;
						}
					} else {
						if (!cic.b(byte8)) {
							return false;
						}

						if (integer7 == 0) {
							return false;
						}

						int integer9 = integer7 - 1;
						if (integer9 < 0 || integer9 > 4096 * integer5) {
							return false;
						}
					}

					return true;
				}
			} catch (IOException var9) {
				return false;
			}
		}
	}

	public DataOutputStream c(bph bph) throws IOException {
		return new DataOutputStream(new BufferedOutputStream(this.e.a(new cia.a(bph))));
	}

	public void a() throws IOException {
		this.c.force(true);
	}

	protected synchronized void a(bph bph, ByteBuffer byteBuffer) throws IOException {
		int integer4 = g(bph);
		int integer5 = this.g.get(integer4);
		int integer6 = b(integer5);
		int integer7 = a(integer5);
		int integer8 = byteBuffer.remaining();
		int integer9 = c(integer8);
		int integer10;
		cia.b b11;
		if (integer9 >= 256) {
			Path path12 = this.e(bph);
			a.warn("Saving oversized chunk {} ({} bytes} to external file {}", bph, integer8, path12);
			integer9 = 1;
			integer10 = this.i.a(integer9);
			b11 = this.a(path12, byteBuffer);
			ByteBuffer byteBuffer13 = this.b();
			this.c.write(byteBuffer13, (long)(integer10 * 4096));
		} else {
			integer10 = this.i.a(integer9);
			b11 = () -> Files.deleteIfExists(this.e(bph));
			this.c.write(byteBuffer, (long)(integer10 * 4096));
		}

		int integer12 = (int)(v.d() / 1000L);
		this.g.put(integer4, this.a(integer10, integer9));
		this.h.put(integer4, integer12);
		this.c();
		b11.run();
		if (integer6 != 0) {
			this.i.b(integer6, integer7);
		}
	}

	private ByteBuffer b() {
		ByteBuffer byteBuffer2 = ByteBuffer.allocate(5);
		byteBuffer2.putInt(1);
		byteBuffer2.put((byte)(this.e.a() | 128));
		byteBuffer2.flip();
		return byteBuffer2;
	}

	private cia.b a(Path path, ByteBuffer byteBuffer) throws IOException {
		Path path4 = Files.createTempFile(this.d, "tmp", null);
		FileChannel fileChannel5 = FileChannel.open(path4, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		Throwable var5 = null;

		try {
			byteBuffer.position(5);
			fileChannel5.write(byteBuffer);
		} catch (Throwable var14) {
			var5 = var14;
			throw var14;
		} finally {
			if (fileChannel5 != null) {
				if (var5 != null) {
					try {
						fileChannel5.close();
					} catch (Throwable var13) {
						var5.addSuppressed(var13);
					}
				} else {
					fileChannel5.close();
				}
			}
		}

		return () -> Files.move(path4, path, StandardCopyOption.REPLACE_EXISTING);
	}

	private void c() throws IOException {
		this.f.position(0);
		this.c.write(this.f, 0L);
	}

	private int f(bph bph) {
		return this.g.get(g(bph));
	}

	public boolean d(bph bph) {
		return this.f(bph) != 0;
	}

	private static int g(bph bph) {
		return bph.j() + bph.k() * 32;
	}

	public void close() throws IOException {
		try {
			this.d();
		} finally {
			try {
				this.c.force(true);
			} finally {
				this.c.close();
			}
		}
	}

	private void d() throws IOException {
		int integer2 = (int)this.c.size();
		int integer3 = c(integer2) * 4096;
		if (integer2 != integer3) {
			ByteBuffer byteBuffer4 = b.duplicate();
			byteBuffer4.position(0);
			this.c.write(byteBuffer4, (long)(integer3 - 1));
		}
	}

	class a extends ByteArrayOutputStream {
		private final bph b;

		public a(bph bph) {
			super(8096);
			super.write(0);
			super.write(0);
			super.write(0);
			super.write(0);
			super.write(cia.this.e.a());
			this.b = bph;
		}

		public void close() throws IOException {
			ByteBuffer byteBuffer2 = ByteBuffer.wrap(this.buf, 0, this.count);
			byteBuffer2.putInt(0, this.count - 5 + 1);
			cia.this.a(this.b, byteBuffer2);
		}
	}

	interface b {
		void run() throws IOException;
	}
}
