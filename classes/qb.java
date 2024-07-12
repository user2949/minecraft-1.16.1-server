import java.io.IOException;

public class qb implements ni<nl> {
	private int a;
	private int b;
	private int c;
	private int d;

	public qb() {
	}

	public qb(aom aom) {
		this(aom.V(), aom.cB());
	}

	public qb(int integer, dem dem) {
		this.a = integer;
		double double4 = 3.9;
		double double6 = aec.a(dem.b, -3.9, 3.9);
		double double8 = aec.a(dem.c, -3.9, 3.9);
		double double10 = aec.a(dem.d, -3.9, 3.9);
		this.b = (int)(double6 * 8000.0);
		this.c = (int)(double8 * 8000.0);
		this.d = (int)(double10 * 8000.0);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.readShort();
		this.c = mg.readShort();
		this.d = mg.readShort();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.writeShort(this.b);
		mg.writeShort(this.c);
		mg.writeShort(this.d);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
