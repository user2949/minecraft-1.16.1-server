import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class czq {
	private static final Logger a = LogManager.getLogger();
	private final String b;
	private boolean c;

	public czq(String string) {
		this.b = string;
	}

	public abstract void a(le le);

	public abstract le b(le le);

	public void b() {
		this.a(true);
	}

	public void a(boolean boolean1) {
		this.c = boolean1;
	}

	public boolean c() {
		return this.c;
	}

	public String d() {
		return this.b;
	}

	public void a(File file) {
		if (this.c()) {
			le le3 = new le();
			le3.a("data", this.b(new le()));
			le3.b("DataVersion", u.a().getWorldVersion());

			try {
				FileOutputStream fileOutputStream4 = new FileOutputStream(file);
				Throwable var4 = null;

				try {
					lo.a(le3, fileOutputStream4);
				} catch (Throwable var14) {
					var4 = var14;
					throw var14;
				} finally {
					if (fileOutputStream4 != null) {
						if (var4 != null) {
							try {
								fileOutputStream4.close();
							} catch (Throwable var13) {
								var4.addSuppressed(var13);
							}
						} else {
							fileOutputStream4.close();
						}
					}
				}
			} catch (IOException var16) {
				a.error("Could not save data {}", this, var16);
			}

			this.a(false);
		}
	}
}
