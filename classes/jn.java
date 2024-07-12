import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jn implements hl {
	private static final Logger b = LogManager.getLogger();
	private final hk c;

	public jn(hk hk) {
		this.c = hk;
	}

	@Override
	public void a(hm hm) throws IOException {
		Path path3 = this.c.b();

		for (Path path5 : this.c.a()) {
			Files.walk(path5).filter(path -> path.toString().endsWith(".nbt")).forEach(path3x -> a(path3x, this.a(path5, path3x), path3));
		}
	}

	@Override
	public String a() {
		return "NBT to SNBT";
	}

	private String a(Path path1, Path path2) {
		String string4 = path1.relativize(path2).toString().replaceAll("\\\\", "/");
		return string4.substring(0, string4.length() - ".nbt".length());
	}

	@Nullable
	public static Path a(Path path1, String string, Path path3) {
		try {
			le le4 = lo.a(Files.newInputStream(path1));
			mr mr5 = le4.a("    ", 0);
			String string6 = mr5.getString() + "\n";
			Path path7 = path3.resolve(string + ".snbt");
			Files.createDirectories(path7.getParent());
			BufferedWriter bufferedWriter8 = Files.newBufferedWriter(path7);
			Throwable var8 = null;

			try {
				bufferedWriter8.write(string6);
			} catch (Throwable var18) {
				var8 = var18;
				throw var18;
			} finally {
				if (bufferedWriter8 != null) {
					if (var8 != null) {
						try {
							bufferedWriter8.close();
						} catch (Throwable var17) {
							var8.addSuppressed(var17);
						}
					} else {
						bufferedWriter8.close();
					}
				}
			}

			b.info("Converted {} from NBT to SNBT", string);
			return path7;
		} catch (IOException var20) {
			b.error("Couldn't convert {} from NBT to SNBT at {}", string, path1, var20);
			return null;
		}
	}
}
