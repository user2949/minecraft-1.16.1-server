import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aah implements aae {
	public static Path a;
	private static final Logger d = LogManager.getLogger();
	public static Class<?> b;
	private static final Map<aaf, FileSystem> e = v.a(Maps.<aaf, FileSystem>newHashMap(), hashMap -> {
		synchronized (aah.class) {
			for (aaf aaf6 : aaf.values()) {
				URL uRL7 = aah.class.getResource("/" + aaf6.a() + "/.mcassetsroot");

				try {
					URI uRI8 = uRL7.toURI();
					if ("jar".equals(uRI8.getScheme())) {
						FileSystem fileSystem9;
						try {
							fileSystem9 = FileSystems.getFileSystem(uRI8);
						} catch (FileSystemNotFoundException var11) {
							fileSystem9 = FileSystems.newFileSystem(uRI8, Collections.emptyMap());
						}

						hashMap.put(aaf6, fileSystem9);
					}
				} catch (IOException | URISyntaxException var12) {
					d.error("Couldn't get a list of all vanilla resources", (Throwable)var12);
				}
			}
		}
	});
	public final Set<String> c;

	public aah(String... arr) {
		this.c = ImmutableSet.copyOf(arr);
	}

	public InputStream b(String string) throws IOException {
		if (!string.contains("/") && !string.contains("\\")) {
			if (a != null) {
				Path path3 = a.resolve(string);
				if (Files.exists(path3, new LinkOption[0])) {
					return Files.newInputStream(path3);
				}
			}

			return this.a(string);
		} else {
			throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
		}
	}

	@Override
	public InputStream a(aaf aaf, uh uh) throws IOException {
		InputStream inputStream4 = this.c(aaf, uh);
		if (inputStream4 != null) {
			return inputStream4;
		} else {
			throw new FileNotFoundException(uh.a());
		}
	}

	@Override
	public Collection<uh> a(aaf aaf, String string2, String string3, int integer, Predicate<String> predicate) {
		Set<uh> set7 = Sets.<uh>newHashSet();
		if (a != null) {
			try {
				a(set7, integer, string2, a.resolve(aaf.a()), string3, predicate);
			} catch (IOException var15) {
			}

			if (aaf == aaf.CLIENT_RESOURCES) {
				Enumeration<URL> enumeration8 = null;

				try {
					enumeration8 = b.getClassLoader().getResources(aaf.a() + "/");
				} catch (IOException var14) {
				}

				while (enumeration8 != null && enumeration8.hasMoreElements()) {
					try {
						URI uRI9 = ((URL)enumeration8.nextElement()).toURI();
						if ("file".equals(uRI9.getScheme())) {
							a(set7, integer, string2, Paths.get(uRI9), string3, predicate);
						}
					} catch (IOException | URISyntaxException var13) {
					}
				}
			}
		}

		try {
			URL uRL8 = aah.class.getResource("/" + aaf.a() + "/.mcassetsroot");
			if (uRL8 == null) {
				d.error("Couldn't find .mcassetsroot, cannot load vanilla resources");
				return set7;
			}

			URI uRI9 = uRL8.toURI();
			if ("file".equals(uRI9.getScheme())) {
				URL uRL10 = new URL(uRL8.toString().substring(0, uRL8.toString().length() - ".mcassetsroot".length()));
				Path path11 = Paths.get(uRL10.toURI());
				a(set7, integer, string2, path11, string3, predicate);
			} else if ("jar".equals(uRI9.getScheme())) {
				Path path10 = ((FileSystem)e.get(aaf)).getPath("/" + aaf.a());
				a(set7, integer, "minecraft", path10, string3, predicate);
			} else {
				d.error("Unsupported scheme {} trying to list vanilla resources (NYI?)", uRI9);
			}
		} catch (NoSuchFileException | FileNotFoundException var11) {
		} catch (IOException | URISyntaxException var12) {
			d.error("Couldn't get a list of all vanilla resources", (Throwable)var12);
		}

		return set7;
	}

	private static void a(Collection<uh> collection, int integer, String string3, Path path, String string5, Predicate<String> predicate) throws IOException {
		Path path7 = path.resolve(string3);
		Stream<Path> stream8 = Files.walk(path7.resolve(string5), integer, new FileVisitOption[0]);
		Throwable var8 = null;

		try {
			stream8.filter(pathx -> !pathx.endsWith(".mcmeta") && Files.isRegularFile(pathx, new LinkOption[0]) && predicate.test(pathx.getFileName().toString()))
				.map(path3 -> new uh(string3, path7.relativize(path3).toString().replaceAll("\\\\", "/")))
				.forEach(collection::add);
		} catch (Throwable var17) {
			var8 = var17;
			throw var17;
		} finally {
			if (stream8 != null) {
				if (var8 != null) {
					try {
						stream8.close();
					} catch (Throwable var16) {
						var8.addSuppressed(var16);
					}
				} else {
					stream8.close();
				}
			}
		}
	}

	@Nullable
	protected InputStream c(aaf aaf, uh uh) {
		String string4 = d(aaf, uh);
		if (a != null) {
			Path path5 = a.resolve(aaf.a() + "/" + uh.b() + "/" + uh.a());
			if (Files.exists(path5, new LinkOption[0])) {
				try {
					return Files.newInputStream(path5);
				} catch (IOException var7) {
				}
			}
		}

		try {
			URL uRL5 = aah.class.getResource(string4);
			return a(string4, uRL5) ? uRL5.openStream() : null;
		} catch (IOException var6) {
			return aah.class.getResourceAsStream(string4);
		}
	}

	private static String d(aaf aaf, uh uh) {
		return "/" + aaf.a() + "/" + uh.b() + "/" + uh.a();
	}

	private static boolean a(String string, @Nullable URL uRL) throws IOException {
		return uRL != null && (uRL.getProtocol().equals("jar") || aad.a(new File(uRL.getFile()), string));
	}

	@Nullable
	protected InputStream a(String string) {
		return aah.class.getResourceAsStream("/" + string);
	}

	@Override
	public boolean b(aaf aaf, uh uh) {
		String string4 = d(aaf, uh);
		if (a != null) {
			Path path5 = a.resolve(aaf.a() + "/" + uh.b() + "/" + uh.a());
			if (Files.exists(path5, new LinkOption[0])) {
				return true;
			}
		}

		try {
			URL uRL5 = aah.class.getResource(string4);
			return a(string4, uRL5);
		} catch (IOException var5) {
			return false;
		}
	}

	@Override
	public Set<String> a(aaf aaf) {
		return this.c;
	}

	@Nullable
	@Override
	public <T> T a(aai<T> aai) throws IOException {
		try {
			InputStream inputStream3 = this.b("pack.mcmeta");
			Throwable var3 = null;

			Object var4;
			try {
				var4 = aab.a(aai, inputStream3);
			} catch (Throwable var14) {
				var3 = var14;
				throw var14;
			} finally {
				if (inputStream3 != null) {
					if (var3 != null) {
						try {
							inputStream3.close();
						} catch (Throwable var13) {
							var3.addSuppressed(var13);
						}
					} else {
						inputStream3.close();
					}
				}
			}

			return (T)var4;
		} catch (FileNotFoundException | RuntimeException var16) {
			return null;
		}
	}

	@Override
	public String a() {
		return "Default";
	}

	@Override
	public void close() {
	}
}
