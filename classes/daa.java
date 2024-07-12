import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class daa {
	private static final Logger a = LogManager.getLogger();
	private final Map<String, czq> b = Maps.<String, czq>newHashMap();
	private final DataFixer c;
	private final File d;

	public daa(File file, DataFixer dataFixer) {
		this.c = dataFixer;
		this.d = file;
	}

	private File a(String string) {
		return new File(this.d, string + ".dat");
	}

	public <T extends czq> T a(Supplier<T> supplier, String string) {
		T czq4 = this.b(supplier, string);
		if (czq4 != null) {
			return czq4;
		} else {
			T czq5 = (T)supplier.get();
			this.a(czq5);
			return czq5;
		}
	}

	@Nullable
	public <T extends czq> T b(Supplier<T> supplier, String string) {
		czq czq4 = (czq)this.b.get(string);
		if (czq4 == null && !this.b.containsKey(string)) {
			czq4 = this.c(supplier, string);
			this.b.put(string, czq4);
		}

		return (T)czq4;
	}

	@Nullable
	private <T extends czq> T c(Supplier<T> supplier, String string) {
		try {
			File file4 = this.a(string);
			if (file4.exists()) {
				T czq5 = (T)supplier.get();
				le le6 = this.a(string, u.a().getWorldVersion());
				czq5.a(le6.p("data"));
				return czq5;
			}
		} catch (Exception var6) {
			a.error("Error loading saved data: {}", string, var6);
		}

		return null;
	}

	public void a(czq czq) {
		this.b.put(czq.d(), czq);
	}

	public le a(String string, int integer) throws IOException {
		File file4 = this.a(string);
		PushbackInputStream pushbackInputStream5 = new PushbackInputStream(new FileInputStream(file4), 2);
		Throwable var5 = null;

		le var36;
		try {
			le le7;
			if (this.a(pushbackInputStream5)) {
				le7 = lo.a(pushbackInputStream5);
			} else {
				DataInputStream dataInputStream8 = new DataInputStream(pushbackInputStream5);
				Throwable var8 = null;

				try {
					le7 = lo.a(dataInputStream8);
				} catch (Throwable var31) {
					var8 = var31;
					throw var31;
				} finally {
					if (dataInputStream8 != null) {
						if (var8 != null) {
							try {
								dataInputStream8.close();
							} catch (Throwable var30) {
								var8.addSuppressed(var30);
							}
						} else {
							dataInputStream8.close();
						}
					}
				}
			}

			int integer8 = le7.c("DataVersion", 99) ? le7.h("DataVersion") : 1343;
			var36 = lq.a(this.c, aeo.SAVED_DATA, le7, integer8, integer);
		} catch (Throwable var33) {
			var5 = var33;
			throw var33;
		} finally {
			if (pushbackInputStream5 != null) {
				if (var5 != null) {
					try {
						pushbackInputStream5.close();
					} catch (Throwable var29) {
						var5.addSuppressed(var29);
					}
				} else {
					pushbackInputStream5.close();
				}
			}
		}

		return var36;
	}

	private boolean a(PushbackInputStream pushbackInputStream) throws IOException {
		byte[] arr3 = new byte[2];
		boolean boolean4 = false;
		int integer5 = pushbackInputStream.read(arr3, 0, 2);
		if (integer5 == 2) {
			int integer6 = (arr3[1] & 255) << 8 | arr3[0] & 255;
			if (integer6 == 35615) {
				boolean4 = true;
			}
		}

		if (integer5 != 0) {
			pushbackInputStream.unread(arr3, 0, integer5);
		}

		return boolean4;
	}

	public void a() {
		for (czq czq3 : this.b.values()) {
			if (czq3 != null) {
				czq3.a(this.a(czq3.d()));
			}
		}
	}
}
