public class atl {
	protected final aoz a;
	protected float b;
	protected float c;
	protected boolean d;
	protected double e;
	protected double f;
	protected double g;

	public atl(aoz aoz) {
		this.a = aoz;
	}

	public void a(dem dem) {
		this.a(dem.b, dem.c, dem.d);
	}

	public void a(aom aom, float float2, float float3) {
		this.a(aom.cC(), b(aom), aom.cG(), float2, float3);
	}

	public void a(double double1, double double2, double double3) {
		this.a(double1, double2, double3, (float)this.a.eq(), (float)this.a.eo());
	}

	public void a(double double1, double double2, double double3, float float4, float float5) {
		this.e = double1;
		this.f = double2;
		this.g = double3;
		this.b = float4;
		this.c = float5;
		this.d = true;
	}

	public void a() {
		if (this.b()) {
			this.a.q = 0.0F;
		}

		if (this.d) {
			this.d = false;
			this.a.aJ = this.a(this.a.aJ, this.h(), this.b);
			this.a.q = this.a(this.a.q, this.g(), this.c);
		} else {
			this.a.aJ = this.a(this.a.aJ, this.a.aH, 10.0F);
		}

		if (!this.a.x().m()) {
			this.a.aJ = aec.b(this.a.aJ, this.a.aH, (float)this.a.ep());
		}
	}

	protected boolean b() {
		return true;
	}

	public boolean c() {
		return this.d;
	}

	public double d() {
		return this.e;
	}

	public double e() {
		return this.f;
	}

	public double f() {
		return this.g;
	}

	protected float g() {
		double double2 = this.e - this.a.cC();
		double double4 = this.f - this.a.cF();
		double double6 = this.g - this.a.cG();
		double double8 = (double)aec.a(double2 * double2 + double6 * double6);
		return (float)(-(aec.d(double4, double8) * 180.0F / (float)Math.PI));
	}

	protected float h() {
		double double2 = this.e - this.a.cC();
		double double4 = this.g - this.a.cG();
		return (float)(aec.d(double4, double2) * 180.0F / (float)Math.PI) - 90.0F;
	}

	protected float a(float float1, float float2, float float3) {
		float float5 = aec.c(float1, float2);
		float float6 = aec.a(float5, -float3, float3);
		return float1 + float6;
	}

	private static double b(aom aom) {
		return aom instanceof aoy ? aom.cF() : (aom.cb().b + aom.cb().e) / 2.0;
	}
}
