import java.io.IOException;

public class rt implements ni<qz> {
	private double a;
	private double b;
	private double c;
	private float d;
	private float e;

	public rt() {
	}

	public rt(aom aom) {
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

	public void a(qz qz) {
		qz.a(this);
	}

	public double b() {
		return this.a;
	}

	public double c() {
		return this.b;
	}

	public double d() {
		return this.c;
	}

	public float e() {
		return this.d;
	}

	public float f() {
		return this.e;
	}
}
