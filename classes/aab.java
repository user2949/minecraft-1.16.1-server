import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class aab implements aae {
	private static final Logger b = LogManager.getLogger();
	protected final File a;

	public aab(File file) {
		this.a = file;
	}

	private static String c(aaf aaf, uh uh) {
		return String.format("%s/%s/%s", aaf.a(), uh.b(), uh.a());
	}

	protected static String a(File file1, File file2) {
		return file1.toURI().relativize(file2.toURI()).getPath();
	}

	@Override
	public InputStream a(aaf aaf, uh uh) throws IOException {
		return this.a(c(aaf, uh));
	}

	@Override
	public boolean b(aaf aaf, uh uh) {
		return this.c(c(aaf, uh));
	}

	protected abstract InputStream a(String string) throws IOException;

	protected abstract boolean c(String string);

	protected void d(String string) {
		b.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", string, this.a);
	}

	@Nullable
	@Override
	public <T> T a(aai<T> aai) throws IOException {
		InputStream inputStream3 = this.a("pack.mcmeta");
		Throwable var3 = null;

		Object var4;
		try {
			var4 = a(aai, inputStream3);
		} catch (Throwable var13) {
			var3 = var13;
			throw var13;
		} finally {
			if (inputStream3 != null) {
				if (var3 != null) {
					try {
						inputStream3.close();
					} catch (Throwable var12) {
						var3.addSuppressed(var12);
					}
				} else {
					inputStream3.close();
				}
			}
		}

		return (T)var4;
	}

	@Nullable
	public static <T> T a(aai<T> aai, InputStream inputStream) {
		JsonObject jsonObject3;
		try {
			BufferedReader bufferedReader4 = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			Throwable var4 = null;

			try {
				jsonObject3 = adt.a(bufferedReader4);
			} catch (Throwable var16) {
				var4 = var16;
				throw var16;
			} finally {
				if (bufferedReader4 != null) {
					if (var4 != null) {
						try {
							bufferedReader4.close();
						} catch (Throwable var14) {
							var4.addSuppressed(var14);
						}
					} else {
						bufferedReader4.close();
					}
				}
			}
		} catch (JsonParseException | IOException var18) {
			b.error("Couldn't load {} metadata", aai.a(), var18);
			return null;
		}

		if (!jsonObject3.has(aai.a())) {
			return null;
		} else {
			try {
				return aai.a(adt.t(jsonObject3, aai.a()));
			} catch (JsonParseException var15) {
				b.error("Couldn't load {} metadata", aai.a(), var15);
				return null;
			}
		}
	}

	@Override
	public String a() {
		return this.a.getName();
	}
}
