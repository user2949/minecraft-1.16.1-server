import java.io.IOException;

public class qd implements ni<nl> {
	private float a;
	private int b;
	private int c;

	public qd() {
	}

	public qd(float float1, int integer2, int integer3) {
		this.a = float1;
		this.b = integer2;
		this.c = integer3;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readFloat();
		this.c = mg.i();
		this.b = mg.i();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeFloat(this.a);
		mg.d(this.c);
		mg.d(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
