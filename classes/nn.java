import java.io.IOException;

public class nn implements ni<nl> {
	private int a;
	private double b;
	private double c;
	private double d;
	private int e;

	public nn() {
	}

	public nn(aos aos) {
		this.a = aos.V();
		this.b = aos.cC();
		this.c = aos.cD();
		this.d = aos.cG();
		this.e = aos.g();
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.readDouble();
		this.c = mg.readDouble();
		this.d = mg.readDouble();
		this.e = mg.readShort();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.writeDouble(this.b);
		mg.writeDouble(this.c);
		mg.writeDouble(this.d);
		mg.writeShort(this.e);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
