public class cdx extends cdl implements ceo {
	public float a;
	public float b;
	public int c;
	private int g;

	public cdx() {
		super(cdm.d);
	}

	@Override
	public void al_() {
		if (++this.g % 20 * 4 == 0) {
			this.d.a(this.e, bvs.ek, 1, this.c);
		}

		this.b = this.a;
		int integer2 = this.e.u();
		int integer3 = this.e.v();
		int integer4 = this.e.w();
		float float5 = 0.1F;
		if (this.c > 0 && this.a == 0.0F) {
			double double6 = (double)integer2 + 0.5;
			double double8 = (double)integer4 + 0.5;
			this.d.a(null, double6, (double)integer3 + 0.5, double8, acl.do, acm.BLOCKS, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
		}

		if (this.c == 0 && this.a > 0.0F || this.c > 0 && this.a < 1.0F) {
			float float6 = this.a;
			if (this.c > 0) {
				this.a += 0.1F;
			} else {
				this.a -= 0.1F;
			}

			if (this.a > 1.0F) {
				this.a = 1.0F;
			}

			float float7 = 0.5F;
			if (this.a < 0.5F && float6 >= 0.5F) {
				double double8 = (double)integer2 + 0.5;
				double double10 = (double)integer4 + 0.5;
				this.d.a(null, double8, (double)integer3 + 0.5, double10, acl.dn, acm.BLOCKS, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
			}

			if (this.a < 0.0F) {
				this.a = 0.0F;
			}
		}
	}

	@Override
	public boolean a_(int integer1, int integer2) {
		if (integer1 == 1) {
			this.c = integer2;
			return true;
		} else {
			return super.a_(integer1, integer2);
		}
	}

	@Override
	public void an_() {
		this.s();
		super.an_();
	}

	public void d() {
		this.c++;
		this.d.a(this.e, bvs.ek, 1, this.c);
	}

	public void f() {
		this.c--;
		this.d.a(this.e, bvs.ek, 1, this.c);
	}

	public boolean a(bec bec) {
		return this.d.c(this.e) != this ? false : !(bec.g((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5) > 64.0);
	}
}
