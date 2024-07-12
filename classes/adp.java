import com.google.common.base.Charsets;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class adp implements AutoCloseable {
	private final FileChannel a;
	private final FileLock b;
	private static final ByteBuffer c;

	public static adp a(Path path) throws IOException {
		Path path2 = path.resolve("session.lock");
		if (!Files.isDirectory(path, new LinkOption[0])) {
			Files.createDirectories(path);
		}

		FileChannel fileChannel3 = FileChannel.open(path2, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

		try {
			fileChannel3.write(c.duplicate());
			fileChannel3.force(true);
			FileLock fileLock4 = fileChannel3.tryLock();
			if (fileLock4 == null) {
				throw adp.a.a(path2);
			} else {
				return new adp(fileChannel3, fileLock4);
			}
		} catch (IOException var6) {
			try {
				fileChannel3.close();
			} catch (IOException var5) {
				var6.addSuppressed(var5);
			}

			throw var6;
		}
	}

	private adp(FileChannel fileChannel, FileLock fileLock) {
		this.a = fileChannel;
		this.b = fileLock;
	}

	public void close() throws IOException {
		try {
			if (this.b.isValid()) {
				this.b.release();
			}
		} finally {
			if (this.a.isOpen()) {
				this.a.close();
			}
		}
	}

	public boolean a() {
		return this.b.isValid();
	}

	static {
		byte[] arr1 = "☃".getBytes(Charsets.UTF_8);
		c = ByteBuffer.allocateDirect(arr1.length);
		c.put(arr1);
		c.flip();
	}

	public static class a extends IOException {
		private a(Path path, String string) {
			super(path.toAbsolutePath() + ": " + string);
		}

		public static adp.a a(Path path) {
			return new adp.a(path, "already locked (possibly by other Minecraft instance?)");
		}
	}
}
