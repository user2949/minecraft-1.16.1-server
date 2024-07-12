import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class beo extends bes {
	private final Random b = new Random();
	private boolean c;
	private int d;
	private static final tq<Integer> e = tt.a(beo.class, ts.b);
	private static final tq<Boolean> f = tt.a(beo.class, ts.i);
	private int g;
	private int an;
	private int ao;
	private int ap;
	private float aq;
	private boolean ar = true;
	private aom as;
	private beo.a at = beo.a.FLYING;
	private final int au;
	private final int av;

	private beo(bqb bqb, bec bec, int integer3, int integer4) {
		super(aoq.bc, bqb);
		this.ac = true;
		this.b(bec);
		bec.bP = this;
		this.au = Math.max(0, integer3);
		this.av = Math.max(0, integer4);
	}

	public beo(bec bec, bqb bqb, int integer3, int integer4) {
		this(bqb, bec, integer3, integer4);
		float float6 = bec.q;
		float float7 = bec.p;
		float float8 = aec.b(-float7 * (float) (Math.PI / 180.0) - (float) Math.PI);
		float float9 = aec.a(-float7 * (float) (Math.PI / 180.0) - (float) Math.PI);
		float float10 = -aec.b(-float6 * (float) (Math.PI / 180.0));
		float float11 = aec.a(-float6 * (float) (Math.PI / 180.0));
		double double12 = bec.cC() - (double)float9 * 0.3;
		double double14 = bec.cF();
		double double16 = bec.cG() - (double)float8 * 0.3;
		this.b(double12, double14, double16, float7, float6);
		dem dem18 = new dem((double)(-float9), (double)aec.a(-(float11 / float10), -5.0F, 5.0F), (double)(-float8));
		double double19 = dem18.f();
		dem18 = dem18.d(
			0.6 / double19 + 0.5 + this.J.nextGaussian() * 0.0045,
			0.6 / double19 + 0.5 + this.J.nextGaussian() * 0.0045,
			0.6 / double19 + 0.5 + this.J.nextGaussian() * 0.0045
		);
		this.e(dem18);
		this.p = (float)(aec.d(dem18.b, dem18.d) * 180.0F / (float)Math.PI);
		this.q = (float)(aec.d(dem18.c, (double)aec.a(b(dem18))) * 180.0F / (float)Math.PI);
		this.r = this.p;
		this.s = this.q;
	}

	@Override
	protected void e() {
		this.Y().a(e, 0);
		this.Y().a(f, false);
	}

	@Override
	public void a(tq<?> tq) {
		if (e.equals(tq)) {
			int integer3 = this.Y().a(e);
			this.as = integer3 > 0 ? this.l.a(integer3 - 1) : null;
		}

		if (f.equals(tq)) {
			this.c = this.Y().a(f);
			if (this.c) {
				this.m(this.cB().b, (double)(-0.4F * aec.a(this.b, 0.6F, 1.0F)), this.cB().d);
			}
		}

		super.a(tq);
	}

	@Override
	public void j() {
		this.b.setSeed(this.bR().getLeastSignificantBits() ^ this.l.Q());
		super.j();
		bec bec2 = this.i();
		if (bec2 == null) {
			this.aa();
		} else if (this.l.v || !this.a(bec2)) {
			if (this.t) {
				this.g++;
				if (this.g >= 1200) {
					this.aa();
					return;
				}
			} else {
				this.g = 0;
			}

			float float3 = 0.0F;
			fu fu4 = this.cA();
			cxa cxa5 = this.l.b(fu4);
			if (cxa5.a(acz.a)) {
				float3 = cxa5.a((bpg)this.l, fu4);
			}

			boolean boolean6 = float3 > 0.0F;
			if (this.at == beo.a.FLYING) {
				if (this.as != null) {
					this.e(dem.a);
					this.at = beo.a.HOOKED_IN_ENTITY;
					return;
				}

				if (boolean6) {
					this.e(this.cB().d(0.3, 0.2, 0.3));
					this.at = beo.a.BOBBING;
					return;
				}

				this.m();
			} else {
				if (this.at == beo.a.HOOKED_IN_ENTITY) {
					if (this.as != null) {
						if (this.as.y) {
							this.as = null;
							this.at = beo.a.FLYING;
						} else {
							this.d(this.as.cC(), this.as.e(0.8), this.as.cG());
						}
					}

					return;
				}

				if (this.at == beo.a.BOBBING) {
					dem dem7 = this.cB();
					double double8 = this.cD() + dem7.c - (double)fu4.v() - (double)float3;
					if (Math.abs(double8) < 0.01) {
						double8 += Math.signum(double8) * 0.1;
					}

					this.m(dem7.b * 0.9, dem7.c - double8 * (double)this.J.nextFloat() * 0.2, dem7.d * 0.9);
					if (this.an <= 0 && this.ap <= 0) {
						this.ar = true;
					} else {
						this.ar = this.ar && this.d < 10 && this.b(fu4);
					}

					if (boolean6) {
						this.d = Math.max(0, this.d - 1);
						if (this.c) {
							this.e(this.cB().b(0.0, -0.1 * (double)this.b.nextFloat() * (double)this.b.nextFloat(), 0.0));
						}

						if (!this.l.v) {
							this.a(fu4);
						}
					} else {
						this.d = Math.min(10, this.d + 1);
					}
				}
			}

			if (!cxa5.a(acz.a)) {
				this.e(this.cB().b(0.0, -0.03, 0.0));
			}

			this.a(apd.SELF, this.cB());
			this.x();
			if (this.at == beo.a.FLYING && (this.t || this.u)) {
				this.e(dem.a);
			}

			double double7 = 0.92;
			this.e(this.cB().a(0.92));
			this.ac();
		}
	}

	private boolean a(bec bec) {
		bki bki3 = bec.dC();
		bki bki4 = bec.dD();
		boolean boolean5 = bki3.b() == bkk.mi;
		boolean boolean6 = bki4.b() == bkk.mi;
		if (!bec.y && bec.aU() && (boolean5 || boolean6) && !(this.h(bec) > 1024.0)) {
			return false;
		} else {
			this.aa();
			return true;
		}
	}

	private void m() {
		dej dej2 = bet.a(this, this::a, bpj.a.COLLIDER);
		this.a(dej2);
	}

	@Override
	protected boolean a(aom aom) {
		return super.a(aom) || aom.aU() && aom instanceof bbg;
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		if (!this.l.v) {
			this.as = dei.a();
			this.n();
		}
	}

	@Override
	protected void a(deh deh) {
		super.a(deh);
		this.e(this.cB().d().a(deh.a(this)));
	}

	private void n() {
		this.Y().b(e, this.as.V() + 1);
	}

	private void a(fu fu) {
		zd zd3 = (zd)this.l;
		int integer4 = 1;
		fu fu5 = fu.b();
		if (this.J.nextFloat() < 0.25F && this.l.t(fu5)) {
			integer4++;
		}

		if (this.J.nextFloat() < 0.5F && !this.l.f(fu5)) {
			integer4--;
		}

		if (this.an > 0) {
			this.an--;
			if (this.an <= 0) {
				this.ao = 0;
				this.ap = 0;
				this.Y().b(f, false);
			}
		} else if (this.ap > 0) {
			this.ap -= integer4;
			if (this.ap > 0) {
				this.aq = (float)((double)this.aq + this.J.nextGaussian() * 4.0);
				float float6 = this.aq * (float) (Math.PI / 180.0);
				float float7 = aec.a(float6);
				float float8 = aec.b(float6);
				double double9 = this.cC() + (double)(float7 * (float)this.ap * 0.1F);
				double double11 = (double)((float)aec.c(this.cD()) + 1.0F);
				double double13 = this.cG() + (double)(float8 * (float)this.ap * 0.1F);
				cfj cfj15 = zd3.d_(new fu(double9, double11 - 1.0, double13));
				if (cfj15.a(bvs.A)) {
					if (this.J.nextFloat() < 0.15F) {
						zd3.a(hh.e, double9, double11 - 0.1F, double13, 1, (double)float7, 0.1, (double)float8, 0.0);
					}

					float float16 = float7 * 0.04F;
					float float17 = float8 * 0.04F;
					zd3.a(hh.z, double9, double11, double13, 0, (double)float17, 0.01, (double)(-float16), 1.0);
					zd3.a(hh.z, double9, double11, double13, 0, (double)(-float17), 0.01, (double)float16, 1.0);
				}
			} else {
				this.a(acl.em, 0.25F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
				double double6 = this.cD() + 0.5;
				zd3.a(hh.e, this.cC(), double6, this.cG(), (int)(1.0F + this.cx() * 20.0F), (double)this.cx(), 0.0, (double)this.cx(), 0.2F);
				zd3.a(hh.z, this.cC(), double6, this.cG(), (int)(1.0F + this.cx() * 20.0F), (double)this.cx(), 0.0, (double)this.cx(), 0.2F);
				this.an = aec.a(this.J, 20, 40);
				this.Y().b(f, true);
			}
		} else if (this.ao > 0) {
			this.ao -= integer4;
			float float6 = 0.15F;
			if (this.ao < 20) {
				float6 = (float)((double)float6 + (double)(20 - this.ao) * 0.05);
			} else if (this.ao < 40) {
				float6 = (float)((double)float6 + (double)(40 - this.ao) * 0.02);
			} else if (this.ao < 60) {
				float6 = (float)((double)float6 + (double)(60 - this.ao) * 0.01);
			}

			if (this.J.nextFloat() < float6) {
				float float7 = aec.a(this.J, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
				float float8 = aec.a(this.J, 25.0F, 60.0F);
				double double9 = this.cC() + (double)(aec.a(float7) * float8 * 0.1F);
				double double11 = (double)((float)aec.c(this.cD()) + 1.0F);
				double double13 = this.cG() + (double)(aec.b(float7) * float8 * 0.1F);
				cfj cfj15 = zd3.d_(new fu(double9, double11 - 1.0, double13));
				if (cfj15.a(bvs.A)) {
					zd3.a(hh.Z, double9, double11, double13, 2 + this.J.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
				}
			}

			if (this.ao <= 0) {
				this.aq = aec.a(this.J, 0.0F, 360.0F);
				this.ap = aec.a(this.J, 20, 80);
			}
		} else {
			this.ao = aec.a(this.J, 100, 600);
			this.ao = this.ao - this.av * 20 * 5;
		}
	}

	private boolean b(fu fu) {
		beo.b b3 = beo.b.INVALID;

		for (int integer4 = -1; integer4 <= 2; integer4++) {
			beo.b b5 = this.a(fu.b(-2, integer4, -2), fu.b(2, integer4, 2));
			switch (b5) {
				case INVALID:
					return false;
				case ABOVE_WATER:
					if (b3 == beo.b.INVALID) {
						return false;
					}
					break;
				case INSIDE_WATER:
					if (b3 == beo.b.ABOVE_WATER) {
						return false;
					}
			}

			b3 = b5;
		}

		return true;
	}

	private beo.b a(fu fu1, fu fu2) {
		return (beo.b)fu.b(fu1, fu2).map(this::c).reduce((b1, b2) -> b1 == b2 ? b1 : beo.b.INVALID).orElse(beo.b.INVALID);
	}

	private beo.b c(fu fu) {
		cfj cfj3 = this.l.d_(fu);
		if (!cfj3.g() && !cfj3.a(bvs.dU)) {
			cxa cxa4 = cfj3.m();
			return cxa4.a(acz.a) && cxa4.b() && cfj3.k(this.l, fu).b() ? beo.b.INSIDE_WATER : beo.b.INVALID;
		} else {
			return beo.b.ABOVE_WATER;
		}
	}

	public boolean g() {
		return this.ar;
	}

	@Override
	public void b(le le) {
	}

	@Override
	public void a(le le) {
	}

	public int b(bki bki) {
		bec bec3 = this.i();
		if (!this.l.v && bec3 != null) {
			int integer4 = 0;
			if (this.as != null) {
				this.h();
				aa.D.a((ze)bec3, bki, this, Collections.emptyList());
				this.l.a(this, (byte)31);
				integer4 = this.as instanceof bbg ? 3 : 5;
			} else if (this.an > 0) {
				dat.a a5 = new dat.a((zd)this.l).a(dda.f, this.cA()).a(dda.j, bki).a(dda.a, this).a(this.J).a((float)this.au + bec3.eU());
				daw daw6 = this.l.l().aH().a(dao.ag);
				List<bki> list7 = daw6.a(a5.a(dcz.e));
				aa.D.a((ze)bec3, bki, this, list7);

				for (bki bki9 : list7) {
					bbg bbg10 = new bbg(this.l, this.cC(), this.cD(), this.cG(), bki9);
					double double11 = bec3.cC() - this.cC();
					double double13 = bec3.cD() - this.cD();
					double double15 = bec3.cG() - this.cG();
					double double17 = 0.1;
					bbg10.m(double11 * 0.1, double13 * 0.1 + Math.sqrt(Math.sqrt(double11 * double11 + double13 * double13 + double15 * double15)) * 0.08, double15 * 0.1);
					this.l.c(bbg10);
					bec3.l.c(new aos(bec3.l, bec3.cC(), bec3.cD() + 0.5, bec3.cG() + 0.5, this.J.nextInt(6) + 1));
					if (bki9.b().a(ada.S)) {
						bec3.a(acu.Q, 1);
					}
				}

				integer4 = 1;
			}

			if (this.t) {
				integer4 = 2;
			}

			this.aa();
			return integer4;
		} else {
			return 0;
		}
	}

	protected void h() {
		aom aom2 = this.v();
		if (aom2 != null) {
			dem dem3 = new dem(aom2.cC() - this.cC(), aom2.cD() - this.cD(), aom2.cG() - this.cG()).a(0.1);
			this.as.e(this.as.cB().e(dem3));
		}
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	public void aa() {
		super.aa();
		bec bec2 = this.i();
		if (bec2 != null) {
			bec2.bP = null;
		}
	}

	@Nullable
	public bec i() {
		aom aom2 = this.v();
		return aom2 instanceof bec ? (bec)aom2 : null;
	}

	@Nullable
	public aom k() {
		return this.as;
	}

	@Override
	public boolean bK() {
		return false;
	}

	@Override
	public ni<?> O() {
		aom aom2 = this.v();
		return new nm(this, aom2 == null ? this.V() : aom2.V());
	}

	static enum a {
		FLYING,
		HOOKED_IN_ENTITY,
		BOBBING;
	}

	static enum b {
		ABOVE_WATER,
		INSIDE_WATER,
		INVALID;
	}
}
