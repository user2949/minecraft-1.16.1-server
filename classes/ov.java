import java.io.IOException;

public class ov implements ni<nl> {
	private double a;
	private double b;
	private double c;
	private float d;
	private float e;
	private float f;
	private float g;
	private int h;
	private boolean i;
	private hf j;

	public ov() {
	}

	public <T extends hf> ov(
		T hf, boolean boolean2, double double3, double double4, double double5, float float6, float float7, float float8, float float9, int integer
	) {
		this.j = hf;
		this.i = boolean2;
		this.a = double3;
		this.b = double4;
		this.c = double5;
		this.d = float6;
		this.e = float7;
		this.f = float8;
		this.g = float9;
		this.h = integer;
	}

	@Override
	public void a(mg mg) throws IOException {
		hg<?> hg3 = gl.az.a(mg.readInt());
		if (hg3 == null) {
			hg3 = hh.c;
		}

		this.i = mg.readBoolean();
		this.a = mg.readDouble();
		this.b = mg.readDouble();
		this.c = mg.readDouble();
		this.d = mg.readFloat();
		this.e = mg.readFloat();
		this.f = mg.readFloat();
		this.g = mg.readFloat();
		this.h = mg.readInt();
		this.j = this.a(mg, (hg<hf>)hg3);
	}

	private <T extends hf> T a(mg mg, hg<T> hg) {
		return hg.d().b(hg, mg);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeInt(gl.az.a(this.j.b()));
		mg.writeBoolean(this.i);
		mg.writeDouble(this.a);
		mg.writeDouble(this.b);
		mg.writeDouble(this.c);
		mg.writeFloat(this.d);
		mg.writeFloat(this.e);
		mg.writeFloat(this.f);
		mg.writeFloat(this.g);
		mg.writeInt(this.h);
		this.j.a(mg);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
