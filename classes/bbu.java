import java.util.List;
import javax.annotation.Nullable;

public class bbu extends bcl {
	private azd bv;

	public bbu(aoq<? extends bbu> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 10;
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(0, new aua(this));
		this.br.a(1, new bbu.b());
		this.br.a(2, new ato(this, bec.class, 8.0F, 0.6, 1.0));
		this.br.a(4, new bbu.c());
		this.br.a(5, new bbu.a());
		this.br.a(6, new bbu.d());
		this.br.a(8, new avf(this, 0.6));
		this.br.a(9, new auo(this, bec.class, 3.0F, 1.0F));
		this.br.a(10, new auo(this, aoz.class, 8.0F));
		this.bs.a(1, new awb(this, bfi.class).a());
		this.bs.a(2, new awc(this, bec.class, true).a(300));
		this.bs.a(3, new awc(this, bdk.class, false).a(300));
		this.bs.a(3, new awc(this, ayt.class, false));
	}

	public static apw.a eL() {
		return bcb.eS().a(apx.d, 0.5).a(apx.b, 12.0).a(apx.a, 24.0);
	}

	@Override
	protected void e() {
		super.e();
	}

	@Override
	public void a(le le) {
		super.a(le);
	}

	@Override
	public ack eM() {
		return acl.dO;
	}

	@Override
	public void b(le le) {
		super.b(le);
	}

	@Override
	protected void N() {
		super.N();
	}

	@Override
	public boolean r(aom aom) {
		if (aom == null) {
			return false;
		} else if (aom == this) {
			return true;
		} else if (super.r(aom)) {
			return true;
		} else if (aom instanceof bcp) {
			return this.r(((bcp)aom).eL());
		} else {
			return aom instanceof aoy && ((aoy)aom).dB() == apc.d ? this.bC() == null && aom.bC() == null : false;
		}
	}

	@Override
	protected ack I() {
		return acl.dM;
	}

	@Override
	protected ack dp() {
		return acl.dP;
	}

	@Override
	protected ack e(anw anw) {
		return acl.dR;
	}

	private void a(@Nullable azd azd) {
		this.bv = azd;
	}

	@Nullable
	private azd fh() {
		return this.bv;
	}

	@Override
	protected ack eN() {
		return acl.dN;
	}

	@Override
	public void a(int integer, boolean boolean2) {
	}

	class a extends bcl.c {
		private a() {
			super(bbu.this);
		}

		@Override
		protected int g() {
			return 40;
		}

		@Override
		protected int h() {
			return 100;
		}

		@Override
		protected void j() {
			aoy aoy2 = bbu.this.A();
			double double3 = Math.min(aoy2.cD(), bbu.this.cD());
			double double5 = Math.max(aoy2.cD(), bbu.this.cD()) + 1.0;
			float float7 = (float)aec.d(aoy2.cG() - bbu.this.cG(), aoy2.cC() - bbu.this.cC());
			if (bbu.this.h((aom)aoy2) < 9.0) {
				for (int integer8 = 0; integer8 < 5; integer8++) {
					float float9 = float7 + (float)integer8 * (float) Math.PI * 0.4F;
					this.a(bbu.this.cC() + (double)aec.b(float9) * 1.5, bbu.this.cG() + (double)aec.a(float9) * 1.5, double3, double5, float9, 0);
				}

				for (int integer8 = 0; integer8 < 8; integer8++) {
					float float9 = float7 + (float)integer8 * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
					this.a(bbu.this.cC() + (double)aec.b(float9) * 2.5, bbu.this.cG() + (double)aec.a(float9) * 2.5, double3, double5, float9, 3);
				}
			} else {
				for (int integer8 = 0; integer8 < 16; integer8++) {
					double double9 = 1.25 * (double)(integer8 + 1);
					int integer11 = 1 * integer8;
					this.a(bbu.this.cC() + (double)aec.b(float7) * double9, bbu.this.cG() + (double)aec.a(float7) * double9, double3, double5, float7, integer11);
				}
			}
		}

		private void a(double double1, double double2, double double3, double double4, float float5, int integer) {
			fu fu12 = new fu(double1, double4, double2);
			boolean boolean13 = false;
			double double14 = 0.0;

			do {
				fu fu16 = fu12.c();
				cfj cfj17 = bbu.this.l.d_(fu16);
				if (cfj17.d(bbu.this.l, fu16, fz.UP)) {
					if (!bbu.this.l.w(fu12)) {
						cfj cfj18 = bbu.this.l.d_(fu12);
						dfg dfg19 = cfj18.k(bbu.this.l, fu12);
						if (!dfg19.b()) {
							double14 = dfg19.c(fz.a.Y);
						}
					}

					boolean13 = true;
					break;
				}

				fu12 = fu12.c();
			} while (fu12.v() >= aec.c(double3) - 1);

			if (boolean13) {
				bbu.this.l.c(new bek(bbu.this.l, double1, (double)fu12.v() + double14, double2, float5, integer, bbu.this));
			}
		}

		@Override
		protected ack k() {
			return acl.dS;
		}

		@Override
		protected bcl.a l() {
			return bcl.a.FANGS;
		}
	}

	class b extends bcl.b {
		private b() {
			super(bbu.this);
		}

		@Override
		public void e() {
			if (bbu.this.A() != null) {
				bbu.this.t().a(bbu.this.A(), (float)bbu.this.ep(), (float)bbu.this.eo());
			} else if (bbu.this.fh() != null) {
				bbu.this.t().a(bbu.this.fh(), (float)bbu.this.ep(), (float)bbu.this.eo());
			}
		}
	}

	class c extends bcl.c {
		private final axs e = new axs().a(16.0).c().e().a().b();

		private c() {
			super(bbu.this);
		}

		@Override
		public boolean a() {
			if (!super.a()) {
				return false;
			} else {
				int integer2 = bbu.this.l.a(bcp.class, this.e, bbu.this, bbu.this.cb().g(16.0)).size();
				return bbu.this.J.nextInt(8) + 1 > integer2;
			}
		}

		@Override
		protected int g() {
			return 100;
		}

		@Override
		protected int h() {
			return 340;
		}

		@Override
		protected void j() {
			for (int integer2 = 0; integer2 < 3; integer2++) {
				fu fu3 = bbu.this.cA().b(-2 + bbu.this.J.nextInt(5), 1, -2 + bbu.this.J.nextInt(5));
				bcp bcp4 = aoq.aN.a(bbu.this.l);
				bcp4.a(fu3, 0.0F, 0.0F);
				bcp4.a(bbu.this.l, bbu.this.l.d(fu3), apb.MOB_SUMMONED, null, null);
				bcp4.a(bbu.this);
				bcp4.g(fu3);
				bcp4.a(20 * (30 + bbu.this.J.nextInt(90)));
				bbu.this.l.c(bcp4);
			}
		}

		@Override
		protected ack k() {
			return acl.dT;
		}

		@Override
		protected bcl.a l() {
			return bcl.a.SUMMON_VEX;
		}
	}

	public class d extends bcl.c {
		private final axs e = new axs().a(16.0).a().a(aoy -> ((azd)aoy).eM() == bje.BLUE);

		public d() {
			super(bbu.this);
		}

		@Override
		public boolean a() {
			if (bbu.this.A() != null) {
				return false;
			} else if (bbu.this.eX()) {
				return false;
			} else if (bbu.this.K < this.c) {
				return false;
			} else if (!bbu.this.l.S().b(bpx.b)) {
				return false;
			} else {
				List<azd> list2 = bbu.this.l.a(azd.class, this.e, bbu.this, bbu.this.cb().c(16.0, 4.0, 16.0));
				if (list2.isEmpty()) {
					return false;
				} else {
					bbu.this.a((azd)list2.get(bbu.this.J.nextInt(list2.size())));
					return true;
				}
			}
		}

		@Override
		public boolean b() {
			return bbu.this.fh() != null && this.b > 0;
		}

		@Override
		public void d() {
			super.d();
			bbu.this.a(null);
		}

		@Override
		protected void j() {
			azd azd2 = bbu.this.fh();
			if (azd2 != null && azd2.aU()) {
				azd2.b(bje.RED);
			}
		}

		@Override
		protected int m() {
			return 40;
		}

		@Override
		protected int g() {
			return 60;
		}

		@Override
		protected int h() {
			return 140;
		}

		@Override
		protected ack k() {
			return acl.dU;
		}

		@Override
		protected bcl.a l() {
			return bcl.a.WOLOLO;
		}
	}
}
