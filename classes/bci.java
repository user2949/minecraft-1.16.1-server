import java.util.EnumSet;
import java.util.Random;

public class bci extends bcb {
	private bci.b b;

	public bci(aoq<? extends bci> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		this.b = new bci.b(this);
		this.br.a(1, new aua(this));
		this.br.a(3, this.b);
		this.br.a(4, new auq(this, 1.0, false));
		this.br.a(5, new bci.a(this));
		this.bs.a(1, new awb(this).a());
		this.bs.a(2, new awc(this, bec.class, true));
	}

	@Override
	public double aX() {
		return 0.1;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 0.13F;
	}

	public static apw.a m() {
		return bcb.eS().a(apx.a, 8.0).a(apx.d, 0.25).a(apx.f, 1.0);
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	protected ack I() {
		return acl.nh;
	}

	@Override
	protected ack e(anw anw) {
		return acl.nj;
	}

	@Override
	protected ack dp() {
		return acl.ni;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.nk, 0.15F, 1.0F);
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			if ((anw instanceof anx || anw == anw.o) && this.b != null) {
				this.b.g();
			}

			return super.a(anw, float2);
		}
	}

	@Override
	public void j() {
		this.aH = this.p;
		super.j();
	}

	@Override
	public void l(float float1) {
		this.p = float1;
		super.l(float1);
	}

	@Override
	public float a(fu fu, bqd bqd) {
		return bys.h(bqd.d_(fu.c())) ? 10.0F : super.a(fu, bqd);
	}

	public static boolean b(aoq<bci> aoq, bqc bqc, apb apb, fu fu, Random random) {
		if (d(aoq, bqc, apb, fu, random)) {
			bec bec6 = bqc.a((double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5, 5.0, true);
			return bec6 == null;
		} else {
			return false;
		}
	}

	@Override
	public apc dB() {
		return apc.c;
	}

	static class a extends avf {
		private fz h;
		private boolean i;

		public a(bci bci) {
			super(bci, 1.0, 10);
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			if (this.a.A() != null) {
				return false;
			} else if (!this.a.x().m()) {
				return false;
			} else {
				Random random2 = this.a.cX();
				if (this.a.l.S().b(bpx.b) && random2.nextInt(10) == 0) {
					this.h = fz.a(random2);
					fu fu3 = new fu(this.a.cC(), this.a.cD() + 0.5, this.a.cG()).a(this.h);
					cfj cfj4 = this.a.l.d_(fu3);
					if (bys.h(cfj4)) {
						this.i = true;
						return true;
					}
				}

				this.i = false;
				return super.a();
			}
		}

		@Override
		public boolean b() {
			return this.i ? false : super.b();
		}

		@Override
		public void c() {
			if (!this.i) {
				super.c();
			} else {
				bqc bqc2 = this.a.l;
				fu fu3 = new fu(this.a.cC(), this.a.cD() + 0.5, this.a.cG()).a(this.h);
				cfj cfj4 = bqc2.d_(fu3);
				if (bys.h(cfj4)) {
					bqc2.a(fu3, bys.c(cfj4.b()), 3);
					this.a.G();
					this.a.aa();
				}
			}
		}
	}

	static class b extends aug {
		private final bci a;
		private int b;

		public b(bci bci) {
			this.a = bci;
		}

		public void g() {
			if (this.b == 0) {
				this.b = 20;
			}
		}

		@Override
		public boolean a() {
			return this.b > 0;
		}

		@Override
		public void e() {
			this.b--;
			if (this.b <= 0) {
				bqb bqb2 = this.a.l;
				Random random3 = this.a.cX();
				fu fu4 = this.a.cA();

				for (int integer5 = 0; integer5 <= 5 && integer5 >= -5; integer5 = (integer5 <= 0 ? 1 : 0) - integer5) {
					for (int integer6 = 0; integer6 <= 10 && integer6 >= -10; integer6 = (integer6 <= 0 ? 1 : 0) - integer6) {
						for (int integer7 = 0; integer7 <= 10 && integer7 >= -10; integer7 = (integer7 <= 0 ? 1 : 0) - integer7) {
							fu fu8 = fu4.b(integer6, integer5, integer7);
							cfj cfj9 = bqb2.d_(fu8);
							bvr bvr10 = cfj9.b();
							if (bvr10 instanceof bys) {
								if (bqb2.S().b(bpx.b)) {
									bqb2.a(fu8, true, this.a);
								} else {
									bqb2.a(fu8, ((bys)bvr10).c().n(), 3);
								}

								if (random3.nextBoolean()) {
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}
