import java.io.File;

public interface amh {
	boolean a(File file);

	long a();

	int b();

	long c();

	int d();

	default long g() {
		return this.c() - this.a();
	}

	default int f() {
		return this.d() - this.b();
	}

	static String b(String string) {
		return string.replace('\u001e', '.');
	}
}
