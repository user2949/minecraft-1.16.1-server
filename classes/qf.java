import java.io.IOException;

public class qf implements ni<nl> {
	private String a;
	private mr b;
	private dfp.a c;
	private int d;

	public qf() {
	}

	public qf(dfj dfj, int integer) {
		this.a = dfj.b();
		this.b = dfj.d();
		this.c = dfj.f();
		this.d = integer;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e(16);
		this.d = mg.readByte();
		if (this.d == 0 || this.d == 2) {
			this.b = mg.h();
			this.c = mg.a(dfp.a.class);
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.writeByte(this.d);
		if (this.d == 0 || this.d == 2) {
			mg.a(this.b);
			mg.a(this.c);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
