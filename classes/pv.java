import java.io.IOException;

public class pv implements ni<nl> {
	private int a;
	private int b;

	public pv() {
	}

	public pv(int integer1, int integer2) {
		this.a = integer1;
		this.b = integer2;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.i();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.d(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
