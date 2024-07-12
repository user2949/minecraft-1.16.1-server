public class atg {
	private final aoz a;
	private int b;
	private float c;

	public atg(aoz aoz) {
		this.a = aoz;
	}

	public void a() {
		if (this.f()) {
			this.a.aH = this.a.p;
			this.c();
			this.c = this.a.aJ;
			this.b = 0;
		} else {
			if (this.e()) {
				if (Math.abs(this.a.aJ - this.c) > 15.0F) {
					this.b = 0;
					this.c = this.a.aJ;
					this.b();
				} else {
					this.b++;
					if (this.b > 10) {
						this.d();
					}
				}
			}
		}
	}

	private void b() {
		this.a.aH = aec.b(this.a.aH, this.a.aJ, (float)this.a.ep());
	}

	private void c() {
		this.a.aJ = aec.b(this.a.aJ, this.a.aH, (float)this.a.ep());
	}

	private void d() {
		int integer2 = this.b - 10;
		float float3 = aec.a((float)integer2 / 10.0F, 0.0F, 1.0F);
		float float4 = (float)this.a.ep() * (1.0F - float3);
		this.a.aH = aec.b(this.a.aH, this.a.aJ, float4);
	}

	private boolean e() {
		return this.a.cm().isEmpty() || !(this.a.cm().get(0) instanceof aoz);
	}

	private boolean f() {
		double double2 = this.a.cC() - this.a.m;
		double double4 = this.a.cG() - this.a.o;
		return double2 * double2 + double4 * double4 > 2.5000003E-7F;
	}
}
