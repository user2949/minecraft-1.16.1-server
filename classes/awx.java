public class awx extends awv {
	private boolean p;

	public awx(aoz aoz, bqb bqb) {
		super(aoz, bqb);
	}

	@Override
	protected czh a(int integer) {
		this.p = this.a instanceof ayq;
		this.o = new czi(this.p);
		return new czh(this.o, integer);
	}

	@Override
	protected boolean a() {
		return this.p || this.p();
	}

	@Override
	protected dem b() {
		return new dem(this.a.cC(), this.a.e(0.5), this.a.cG());
	}

	@Override
	public void c() {
		this.e++;
		if (this.m) {
			this.j();
		}

		if (!this.m()) {
			if (this.a()) {
				this.l();
			} else if (this.c != null && this.c.f() < this.c.e()) {
				dem dem2 = this.c.a(this.a, this.c.f());
				if (aec.c(this.a.cC()) == aec.c(dem2.b) && aec.c(this.a.cD()) == aec.c(dem2.c) && aec.c(this.a.cG()) == aec.c(dem2.d)) {
					this.c.c(this.c.f() + 1);
				}
			}

			qy.a(this.b, this.a, this.c, this.l);
			if (!this.m()) {
				dem dem2 = this.c.a(this.a);
				this.a.u().a(dem2.b, dem2.c, dem2.d, this.d);
			}
		}
	}

	@Override
	protected void l() {
		if (this.c != null) {
			dem dem2 = this.b();
			float float3 = this.a.cx();
			float float4 = float3 > 0.75F ? float3 / 2.0F : 0.75F - float3 / 2.0F;
			dem dem5 = this.a.cB();
			if (Math.abs(dem5.b) > 0.2 || Math.abs(dem5.d) > 0.2) {
				float4 = (float)((double)float4 * dem5.f() * 6.0);
			}

			int integer6 = 6;
			dem dem7 = dem.c(this.c.g());
			if (Math.abs(this.a.cC() - dem7.b) < (double)float4
				&& Math.abs(this.a.cG() - dem7.d) < (double)float4
				&& Math.abs(this.a.cD() - dem7.c) < (double)(float4 * 2.0F)) {
				this.c.a();
			}

			for (int integer8 = Math.min(this.c.f() + 6, this.c.e() - 1); integer8 > this.c.f(); integer8--) {
				dem7 = this.c.a(this.a, integer8);
				if (!(dem7.g(dem2) > 36.0) && this.a(dem2, dem7, 0, 0, 0)) {
					this.c.c(integer8);
					break;
				}
			}

			this.a(dem2);
		}
	}

	@Override
	protected void a(dem dem) {
		if (this.e - this.f > 100) {
			if (dem.g(this.g) < 2.25) {
				this.o();
			}

			this.f = this.e;
			this.g = dem;
		}

		if (this.c != null && !this.c.b()) {
			gr gr3 = this.c.g();
			if (gr3.equals(this.h)) {
				this.i = this.i + (v.b() - this.j);
			} else {
				this.h = gr3;
				double double4 = dem.f(dem.a(this.h));
				this.k = this.a.dM() > 0.0F ? double4 / (double)this.a.dM() * 100.0 : 0.0;
			}

			if (this.k > 0.0 && (double)this.i > this.k * 2.0) {
				this.h = gr.d;
				this.i = 0L;
				this.k = 0.0;
				this.o();
			}

			this.j = v.b();
		}
	}

	@Override
	protected boolean a(dem dem1, dem dem2, int integer3, int integer4, int integer5) {
		dem dem7 = new dem(dem2.b, dem2.c + (double)this.a.cy() * 0.5, dem2.d);
		return this.b.a(new bpj(dem1, dem7, bpj.a.COLLIDER, bpj.b.NONE, this.a)).c() == dej.a.MISS;
	}

	@Override
	public boolean a(fu fu) {
		return !this.b.d_(fu).i(this.b, fu);
	}

	@Override
	public void d(boolean boolean1) {
	}
}
