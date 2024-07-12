public class cdp extends cef implements ceo {
	private gi<bki> i = gi.a(27, bki.b);
	protected float a;
	protected float b;
	protected int c;
	private int j;

	protected cdp(cdm<?> cdm) {
		super(cdm);
	}

	public cdp() {
		this(cdm.b);
	}

	@Override
	public int ab_() {
		return 27;
	}

	@Override
	protected mr g() {
		return new ne("container.chest");
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.i = gi.a(this.ab_(), bki.b);
		if (!this.b(le)) {
			ana.b(le, this.i);
		}
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (!this.c(le)) {
			ana.a(le, this.i);
		}

		return le;
	}

	@Override
	public void al_() {
		int integer2 = this.e.u();
		int integer3 = this.e.v();
		int integer4 = this.e.w();
		this.j++;
		this.c = a(this.d, this, this.j, integer2, integer3, integer4, this.c);
		this.b = this.a;
		float float5 = 0.1F;
		if (this.c > 0 && this.a == 0.0F) {
			this.a(acl.bG);
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
				this.a(acl.bE);
			}

			if (this.a < 0.0F) {
				this.a = 0.0F;
			}
		}
	}

	public static int a(bqb bqb, cdf cdf, int integer3, int integer4, int integer5, int integer6, int integer7) {
		if (!bqb.v && integer7 != 0 && (integer3 + integer4 + integer5 + integer6) % 200 == 0) {
			integer7 = a(bqb, cdf, integer4, integer5, integer6);
		}

		return integer7;
	}

	public static int a(bqb bqb, cdf cdf, int integer3, int integer4, int integer5) {
		int integer6 = 0;
		float float7 = 5.0F;

		for (bec bec10 : bqb.a(
			bec.class,
			new deg(
				(double)((float)integer3 - 5.0F),
				(double)((float)integer4 - 5.0F),
				(double)((float)integer5 - 5.0F),
				(double)((float)(integer3 + 1) + 5.0F),
				(double)((float)(integer4 + 1) + 5.0F),
				(double)((float)(integer5 + 1) + 5.0F)
			)
		)) {
			if (bec10.bw instanceof bgp) {
				amz amz11 = ((bgp)bec10.bw).e();
				if (amz11 == cdf || amz11 instanceof amy && ((amy)amz11).a(cdf)) {
					integer6++;
				}
			}
		}

		return integer6;
	}

	private void a(ack ack) {
		cgb cgb3 = this.p().c(bwh.c);
		if (cgb3 != cgb.LEFT) {
			double double4 = (double)this.e.u() + 0.5;
			double double6 = (double)this.e.v() + 0.5;
			double double8 = (double)this.e.w() + 0.5;
			if (cgb3 == cgb.RIGHT) {
				fz fz10 = bwh.h(this.p());
				double4 += (double)fz10.i() * 0.5;
				double8 += (double)fz10.k() * 0.5;
			}

			this.d.a(null, double4, double6, double8, ack, acm.BLOCKS, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
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
	public void c_(bec bec) {
		if (!bec.a_()) {
			if (this.c < 0) {
				this.c = 0;
			}

			this.c++;
			this.h();
		}
	}

	@Override
	public void b_(bec bec) {
		if (!bec.a_()) {
			this.c--;
			this.h();
		}
	}

	protected void h() {
		bvr bvr2 = this.p().b();
		if (bvr2 instanceof bwh) {
			this.d.a(this.e, bvr2, 1, this.c);
			this.d.b(this.e, bvr2);
		}
	}

	@Override
	protected gi<bki> f() {
		return this.i;
	}

	@Override
	protected void a(gi<bki> gi) {
		this.i = gi;
	}

	public static int a(bpg bpg, fu fu) {
		cfj cfj3 = bpg.d_(fu);
		if (cfj3.b().q()) {
			cdl cdl4 = bpg.c(fu);
			if (cdl4 instanceof cdp) {
				return ((cdp)cdl4).c;
			}
		}

		return 0;
	}

	public static void a(cdp cdp1, cdp cdp2) {
		gi<bki> gi3 = cdp1.f();
		cdp1.a(cdp2.f());
		cdp2.a(gi3);
	}

	@Override
	protected bgi a(int integer, beb beb) {
		return bgp.a(integer, beb, this);
	}
}
