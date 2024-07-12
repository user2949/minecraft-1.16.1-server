import java.io.IOException;

public class rs implements ni<qz> {
	protected double a;
	protected double b;
	protected double c;
	protected float d;
	protected float e;
	protected boolean f;
	protected boolean g;
	protected boolean h;

	public void a(qz qz) {
		qz.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.f = mg.readUnsignedByte() != 0;
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.f ? 1 : 0);
	}

	public double a(double double1) {
		return this.g ? this.a : double1;
	}

	public double b(double double1) {
		return this.g ? this.b : double1;
	}

	public double c(double double1) {
		return this.g ? this.c : double1;
	}

	public float a(float float1) {
		return this.h ? this.d : float1;
	}

	public float b(float float1) {
		return this.h ? this.e : float1;
	}

	public boolean b() {
		return this.f;
	}

	public static class a extends rs {
		public a() {
			this.g = true;
		}

		@Override
		public void a(mg mg) throws IOException {
			this.a = mg.readDouble();
			this.b = mg.readDouble();
			this.c = mg.readDouble();
			super.a(mg);
		}

		@Override
		public void b(mg mg) throws IOException {
			mg.writeDouble(this.a);
			mg.writeDouble(this.b);
			mg.writeDouble(this.c);
			super.b(mg);
		}
	}

	public static class b extends rs {
		public b() {
			this.g = true;
			this.h = true;
		}

		@Override
		public void a(mg mg) throws IOException {
			this.a = mg.readDouble();
			this.b = mg.readDouble();
			this.c = mg.readDouble();
			this.d = mg.readFloat();
			this.e = mg.readFloat();
			super.a(mg);
		}

		@Override
		public void b(mg mg) throws IOException {
			mg.writeDouble(this.a);
			mg.writeDouble(this.b);
			mg.writeDouble(this.c);
			mg.writeFloat(this.d);
			mg.writeFloat(this.e);
			super.b(mg);
		}
	}

	public static class c extends rs {
		public c() {
			this.h = true;
		}

		@Override
		public void a(mg mg) throws IOException {
			this.d = mg.readFloat();
			this.e = mg.readFloat();
			super.a(mg);
		}

		@Override
		public void b(mg mg) throws IOException {
			mg.writeFloat(this.d);
			mg.writeFloat(this.e);
			super.b(mg);
		}
	}
}
