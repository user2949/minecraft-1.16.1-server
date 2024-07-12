import java.util.function.Supplier;

public interface ami {
	void a();

	void b();

	void a(String string);

	void a(Supplier<String> supplier);

	void c();

	void b(String string);

	void c(String string);

	void c(Supplier<String> supplier);

	static ami a(ami ami1, ami ami2) {
		if (ami1 == amf.a) {
			return ami2;
		} else {
			return ami2 == amf.a ? ami1 : new ami() {
				@Override
				public void a() {
					ami1.a();
					ami2.a();
				}

				@Override
				public void b() {
					ami1.b();
					ami2.b();
				}

				@Override
				public void a(String string) {
					ami1.a(string);
					ami2.a(string);
				}

				@Override
				public void a(Supplier<String> supplier) {
					ami1.a(supplier);
					ami2.a(supplier);
				}

				@Override
				public void c() {
					ami1.c();
					ami2.c();
				}

				@Override
				public void b(String string) {
					ami1.b(string);
					ami2.b(string);
				}

				@Override
				public void c(String string) {
					ami1.c(string);
					ami2.c(string);
				}

				@Override
				public void c(Supplier<String> supplier) {
					ami1.c(supplier);
					ami2.c(supplier);
				}
			};
		}
	}
}
