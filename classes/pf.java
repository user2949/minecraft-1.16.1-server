import java.io.IOException;

public class pf implements ni<nl> {
	private int a;
	private uh b;

	public pf() {
	}

	public pf(int integer, bmu<?> bmu) {
		this.a = integer;
		this.b = bmu.f();
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readByte();
		this.b = mg.o();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.a(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
