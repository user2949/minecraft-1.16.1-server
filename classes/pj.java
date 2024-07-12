import java.io.IOException;

public class pj implements ni<nl> {
	private double a;
	private double b;
	private double c;
	private int d;
	private dg.a e;
	private dg.a f;
	private boolean g;

	public pj() {
	}

	public pj(dg.a a, double double2, double double3, double double4) {
		this.e = a;
		this.a = double2;
		this.b = double3;
		this.c = double4;
	}

	public pj(dg.a a1, aom aom, dg.a a3) {
		this.e = a1;
		this.d = aom.V();
		this.f = a3;
		dem dem5 = a3.a(aom);
		this.a = dem5.b;
		this.b = dem5.c;
		this.c = dem5.d;
		this.g = true;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.e = mg.a(dg.a.class);
		this.a = mg.readDouble();
		this.b = mg.readDouble();
		this.c = mg.readDouble();
		if (mg.readBoolean()) {
			this.g = true;
			this.d = mg.i();
			this.f = mg.a(dg.a.class);
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.e);
		mg.writeDouble(this.a);
		mg.writeDouble(this.b);
		mg.writeDouble(this.c);
		mg.writeBoolean(this.g);
		if (this.g) {
			mg.d(this.d);
			mg.a(this.f);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
