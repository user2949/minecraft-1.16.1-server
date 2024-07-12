public class bgb extends bfr {
	private int b = -1;

	public bgb(aoq<? extends bgb> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bgb(bqb bqb, double double2, double double3, double double4) {
		super(aoq.Z, bqb, double2, double3, double4);
	}

	@Override
	public bfr.a o() {
		return bfr.a.TNT;
	}

	@Override
	public cfj q() {
		return bvs.bH.n();
	}

	@Override
	public void j() {
		super.j();
		if (this.b > 0) {
			this.b--;
			this.l.a(hh.S, this.cC(), this.cD() + 0.5, this.cG(), 0.0, 0.0, 0.0);
		} else if (this.b == 0) {
			this.h(b(this.cB()));
		}

		if (this.u) {
			double double2 = b(this.cB());
			if (double2 >= 0.01F) {
				this.h(double2);
			}
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		aom aom4 = anw.j();
		if (aom4 instanceof beg) {
			beg beg5 = (beg)aom4;
			if (beg5.bm()) {
				this.h(beg5.cB().g());
			}
		}

		return super.a(anw, float2);
	}

	@Override
	public void a(anw anw) {
		double double3 = b(this.cB());
		if (!anw.p() && !anw.d() && !(double3 >= 0.01F)) {
			super.a(anw);
			if (!anw.d() && this.l.S().b(bpx.g)) {
				this.a(bvs.bH);
			}
		} else {
			if (this.b < 0) {
				this.u();
				this.b = this.J.nextInt(20) + this.J.nextInt(20);
			}
		}
	}

	protected void h(double double1) {
		if (!this.l.v) {
			double double4 = Math.sqrt(double1);
			if (double4 > 5.0) {
				double4 = 5.0;
			}

			this.l.a(this, this.cC(), this.cD(), this.cG(), (float)(4.0 + this.J.nextDouble() * 1.5 * double4), bpt.a.BREAK);
			this.aa();
		}
	}

	@Override
	public boolean b(float float1, float float2) {
		if (float1 >= 3.0F) {
			float float4 = float1 / 10.0F;
			this.h((double)(float4 * float4));
		}

		return super.b(float1, float2);
	}

	@Override
	public void a(int integer1, int integer2, int integer3, boolean boolean4) {
		if (boolean4 && this.b < 0) {
			this.u();
		}
	}

	public void u() {
		this.b = 80;
		if (!this.l.v) {
			this.l.a(this, (byte)10);
			if (!this.av()) {
				this.l.a(null, this.cC(), this.cD(), this.cG(), acl.oU, acm.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	public boolean x() {
		return this.b > -1;
	}

	@Override
	public float a(bpt bpt, bpg bpg, fu fu, cfj cfj, cxa cxa, float float6) {
		return !this.x() || !cfj.a(acx.G) && !bpg.d_(fu.b()).a(acx.G) ? super.a(bpt, bpg, fu, cfj, cxa, float6) : 0.0F;
	}

	@Override
	public boolean a(bpt bpt, bpg bpg, fu fu, cfj cfj, float float5) {
		return !this.x() || !cfj.a(acx.G) && !bpg.d_(fu.b()).a(acx.G) ? super.a(bpt, bpg, fu, cfj, float5) : false;
	}

	@Override
	protected void a(le le) {
		super.a(le);
		if (le.c("TNTFuse", 99)) {
			this.b = le.h("TNTFuse");
		}
	}

	@Override
	protected void b(le le) {
		super.b(le);
		le.b("TNTFuse", this.b);
	}
}
