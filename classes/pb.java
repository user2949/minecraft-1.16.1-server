import java.io.IOException;

public class pb implements ni<nl> {
	private double a;
	private double b;
	private double c;
	private float d;
	private float e;

	public pb() {
	}

	public pb(aom aom) {
		this.a = aom.cC();
		this.b = aom.cD();
		this.c = aom.cG();
		this.d = aom.p;
		this.e = aom.q;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readDouble();
		this.b = mg.readDouble();
		this.c = mg.readDouble();
		this.d = mg.readFloat();
		this.e = mg.readFloat();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeDouble(this.a);
		mg.writeDouble(this.b);
		mg.writeDouble(this.c);
		mg.writeFloat(this.d);
		mg.writeFloat(this.e);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
