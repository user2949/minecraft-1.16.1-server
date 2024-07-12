import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jo implements hl {
	@Nullable
	private static final Path b = null;
	private static final Logger c = LogManager.getLogger();
	private final hk d;
	private final List<jo.a> e = Lists.<jo.a>newArrayList();

	public jo(hk hk) {
		this.d = hk;
	}

	public jo a(jo.a a) {
		this.e.add(a);
		return this;
	}

	private le a(String string, le le) {
		le le4 = le;

		for (jo.a a6 : this.e) {
			le4 = a6.a(string, le4);
		}

		return le4;
	}

	@Override
	public void a(hm hm) throws IOException {
		Path path3 = this.d.b();
		List<CompletableFuture<jo.b>> list4 = Lists.<CompletableFuture<jo.b>>newArrayList();

		for (Path path6 : this.d.a()) {
			Files.walk(path6)
				.filter(path -> path.toString().endsWith(".snbt"))
				.forEach(path3x -> list4.add(CompletableFuture.supplyAsync(() -> this.a(path3x, this.a(path6, path3x)), v.f())));
		}

		((List)v.b(list4).join()).stream().filter(Objects::nonNull).forEach(b -> this.a(hm, b, path3));
	}

	@Override
	public String a() {
		return "SNBT -> NBT";
	}

	private String a(Path path1, Path path2) {
		String string4 = path1.relativize(path2).toString().replaceAll("\\\\", "/");
		return string4.substring(0, string4.length() - ".snbt".length());
	}

	@Nullable
	private jo.b a(Path path, String string) {
		try {
			BufferedReader bufferedReader4 = Files.newBufferedReader(path);
			Throwable var4 = null;

			jo.b var11;
			try {
				String string6 = IOUtils.toString(bufferedReader4);
				le le7 = this.a(string, lv.a(string6));
				ByteArrayOutputStream byteArrayOutputStream8 = new ByteArrayOutputStream();
				lo.a(le7, byteArrayOutputStream8);
				byte[] arr9 = byteArrayOutputStream8.toByteArray();
				String string10 = a.hashBytes(arr9).toString();
				String string11;
				if (b != null) {
					string11 = le7.a("    ", 0).getString() + "\n";
				} else {
					string11 = null;
				}

				var11 = new jo.b(string, arr9, string11, string10);
			} catch (Throwable var22) {
				var4 = var22;
				throw var22;
			} finally {
				if (bufferedReader4 != null) {
					if (var4 != null) {
						try {
							bufferedReader4.close();
						} catch (Throwable var21) {
							var4.addSuppressed(var21);
						}
					} else {
						bufferedReader4.close();
					}
				}
			}

			return var11;
		} catch (CommandSyntaxException var24) {
			c.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", string, path, var24);
		} catch (IOException var25) {
			c.error("Couldn't convert {} from SNBT to NBT at {}", string, path, var25);
		}

		return null;
	}

	private void a(hm hm, jo.b b, Path path) {
		if (b.c != null) {
			Path path5 = jo.b.resolve(b.a + ".snbt");

			try {
				FileUtils.write(path5.toFile(), b.c, StandardCharsets.UTF_8);
			} catch (IOException var18) {
				c.error("Couldn't write structure SNBT {} at {}", b.a, path5, var18);
			}
		}

		Path path5 = path.resolve(b.a + ".nbt");

		try {
			if (!Objects.equals(hm.a(path5), b.d) || !Files.exists(path5, new LinkOption[0])) {
				Files.createDirectories(path5.getParent());
				OutputStream outputStream6 = Files.newOutputStream(path5);
				Throwable var6 = null;

				try {
					outputStream6.write(b.b);
				} catch (Throwable var17) {
					var6 = var17;
					throw var17;
				} finally {
					if (outputStream6 != null) {
						if (var6 != null) {
							try {
								outputStream6.close();
							} catch (Throwable var16) {
								var6.addSuppressed(var16);
							}
						} else {
							outputStream6.close();
						}
					}
				}
			}

			hm.a(path5, b.d);
		} catch (IOException var20) {
			c.error("Couldn't write structure {} at {}", b.a, path5, var20);
		}
	}

	@FunctionalInterface
	public interface a {
		le a(String string, le le);
	}

	static class b {
		private final String a;
		private final byte[] b;
		@Nullable
		private final String c;
		private final String d;

		public b(String string1, byte[] arr, @Nullable String string3, String string4) {
			this.a = string1;
			this.b = arr;
			this.c = string3;
			this.d = string4;
		}
	}
}
