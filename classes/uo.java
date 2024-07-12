import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class uo {
	private static final Logger a = LogManager.getLogger();
	private final Path b;
	private final boolean c;

	public uo(Path path) {
		this.b = path;
		this.c = u.d || this.b();
	}

	private boolean b() {
		try {
			InputStream inputStream2 = Files.newInputStream(this.b);
			Throwable var2 = null;

			boolean var4;
			try {
				Properties properties4 = new Properties();
				properties4.load(inputStream2);
				var4 = Boolean.parseBoolean(properties4.getProperty("eula", "false"));
			} catch (Throwable var14) {
				var2 = var14;
				throw var14;
			} finally {
				if (inputStream2 != null) {
					if (var2 != null) {
						try {
							inputStream2.close();
						} catch (Throwable var13) {
							var2.addSuppressed(var13);
						}
					} else {
						inputStream2.close();
					}
				}
			}

			return var4;
		} catch (Exception var16) {
			a.warn("Failed to load {}", this.b);
			this.c();
			return false;
		}
	}

	public boolean a() {
		return this.c;
	}

	private void c() {
		if (!u.d) {
			try {
				OutputStream outputStream2 = Files.newOutputStream(this.b);
				Throwable var2 = null;

				try {
					Properties properties4 = new Properties();
					properties4.setProperty("eula", "false");
					properties4.store(
						outputStream2,
						"By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula)."
					);
				} catch (Throwable var12) {
					var2 = var12;
					throw var12;
				} finally {
					if (outputStream2 != null) {
						if (var2 != null) {
							try {
								outputStream2.close();
							} catch (Throwable var11) {
								var2.addSuppressed(var11);
							}
						} else {
							outputStream2.close();
						}
					}
				}
			} catch (Exception var14) {
				a.warn("Failed to save {}", this.b, var14);
			}
		}
	}
}
