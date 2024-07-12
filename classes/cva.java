import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cva {
	private static final Logger a = LogManager.getLogger();
	private final Map<uh, cve> b = Maps.<uh, cve>newHashMap();
	private final DataFixer c;
	private abc d;
	private final Path e;

	public cva(abc abc, dae.a a, DataFixer dataFixer) {
		this.d = abc;
		this.c = dataFixer;
		this.e = a.a(dac.f).normalize();
	}

	public cve a(uh uh) {
		cve cve3 = this.b(uh);
		if (cve3 == null) {
			cve3 = new cve();
			this.b.put(uh, cve3);
		}

		return cve3;
	}

	@Nullable
	public cve b(uh uh) {
		return (cve)this.b.computeIfAbsent(uh, uhx -> {
			cve cve3 = this.f(uhx);
			return cve3 != null ? cve3 : this.e(uhx);
		});
	}

	public void a(abc abc) {
		this.d = abc;
		this.b.clear();
	}

	@Nullable
	private cve e(uh uh) {
		uh uh3 = new uh(uh.b(), "structures/" + uh.a() + ".nbt");

		try {
			abb abb4 = this.d.a(uh3);
			Throwable var4 = null;

			cve var5;
			try {
				var5 = this.a(abb4.b());
			} catch (Throwable var16) {
				var4 = var16;
				throw var16;
			} finally {
				if (abb4 != null) {
					if (var4 != null) {
						try {
							abb4.close();
						} catch (Throwable var15) {
							var4.addSuppressed(var15);
						}
					} else {
						abb4.close();
					}
				}
			}

			return var5;
		} catch (FileNotFoundException var18) {
			return null;
		} catch (Throwable var19) {
			a.error("Couldn't load structure {}: {}", uh, var19.toString());
			return null;
		}
	}

	@Nullable
	private cve f(uh uh) {
		if (!this.e.toFile().isDirectory()) {
			return null;
		} else {
			Path path3 = this.b(uh, ".nbt");

			try {
				InputStream inputStream4 = new FileInputStream(path3.toFile());
				Throwable var4 = null;

				cve var5;
				try {
					var5 = this.a(inputStream4);
				} catch (Throwable var16) {
					var4 = var16;
					throw var16;
				} finally {
					if (inputStream4 != null) {
						if (var4 != null) {
							try {
								inputStream4.close();
							} catch (Throwable var15) {
								var4.addSuppressed(var15);
							}
						} else {
							inputStream4.close();
						}
					}
				}

				return var5;
			} catch (FileNotFoundException var18) {
				return null;
			} catch (IOException var19) {
				a.error("Couldn't load structure from {}", path3, var19);
				return null;
			}
		}
	}

	private cve a(InputStream inputStream) throws IOException {
		le le3 = lo.a(inputStream);
		return this.a(le3);
	}

	public cve a(le le) {
		if (!le.c("DataVersion", 99)) {
			le.b("DataVersion", 500);
		}

		cve cve3 = new cve();
		cve3.b(lq.a(this.c, aeo.STRUCTURE, le, le.h("DataVersion")));
		return cve3;
	}

	public boolean c(uh uh) {
		cve cve3 = (cve)this.b.get(uh);
		if (cve3 == null) {
			return false;
		} else {
			Path path4 = this.b(uh, ".nbt");
			Path path5 = path4.getParent();
			if (path5 == null) {
				return false;
			} else {
				try {
					Files.createDirectories(Files.exists(path5, new LinkOption[0]) ? path5.toRealPath() : path5);
				} catch (IOException var19) {
					a.error("Failed to create parent directory: {}", path5);
					return false;
				}

				le le6 = cve3.a(new le());

				try {
					OutputStream outputStream7 = new FileOutputStream(path4.toFile());
					Throwable var7 = null;

					try {
						lo.a(le6, outputStream7);
					} catch (Throwable var18) {
						var7 = var18;
						throw var18;
					} finally {
						if (outputStream7 != null) {
							if (var7 != null) {
								try {
									outputStream7.close();
								} catch (Throwable var17) {
									var7.addSuppressed(var17);
								}
							} else {
								outputStream7.close();
							}
						}
					}

					return true;
				} catch (Throwable var21) {
					return false;
				}
			}
		}
	}

	public Path a(uh uh, String string) {
		try {
			Path path4 = this.e.resolve(uh.b());
			Path path5 = path4.resolve("structures");
			return q.b(path5, uh.a(), string);
		} catch (InvalidPathException var5) {
			throw new t("Invalid resource path: " + uh, var5);
		}
	}

	private Path b(uh uh, String string) {
		if (uh.a().contains("//")) {
			throw new t("Invalid resource path: " + uh);
		} else {
			Path path4 = this.a(uh, string);
			if (path4.startsWith(this.e) && q.a(path4) && q.b(path4)) {
				return path4;
			} else {
				throw new t("Invalid resource path: " + path4);
			}
		}
	}

	public void d(uh uh) {
		this.b.remove(uh);
	}
}
