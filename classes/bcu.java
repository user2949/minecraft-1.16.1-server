import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bcu extends bcb {
	private static final UUID b = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final apv c = new apv(b, "Baby speed boost", 0.5, apv.a.MULTIPLY_BASE);
	private static final tq<Boolean> d = tt.a(bcu.class, ts.i);
	private static final tq<Integer> bv = tt.a(bcu.class, ts.b);
	private static final tq<Boolean> bw = tt.a(bcu.class, ts.i);
	private static final Predicate<and> bx = and -> and == and.HARD;
	private final atr by = new atr(this, bx);
	private boolean bz;
	private int bA;
	private int bB;

	public bcu(aoq<? extends bcu> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bcu(bqb bqb) {
		this(aoq.aX, bqb);
	}

	@Override
	protected void o() {
		this.br.a(4, new bcu.a(this, 1.0, 3));
		this.br.a(8, new auo(this, bec.class, 8.0F));
		this.br.a(8, new ave(this));
		this.m();
	}

	protected void m() {
		this.br.a(2, new avy(this, 1.0, false));
		this.br.a(6, new aut(this, 1.0, true, 4, this::eV));
		this.br.a(7, new avw(this, 1.0));
		this.bs.a(1, new awb(this).a(bcw.class));
		this.bs.a(2, new awc(this, bec.class, true));
		this.bs.a(3, new awc(this, bdk.class, false));
		this.bs.a(3, new awc(this, ayt.class, true));
		this.bs.a(5, new awc(this, azi.class, 10, true, false, azi.bv));
	}

	public static apw.a eT() {
		return bcb.eS().a(apx.b, 35.0).a(apx.d, 0.23F).a(apx.f, 3.0).a(apx.i, 2.0).a(apx.l);
	}

	@Override
	protected void e() {
		super.e();
		this.Y().a(d, false);
		this.Y().a(bv, 0);
		this.Y().a(bw, false);
	}

	public boolean eU() {
		return this.Y().a(bw);
	}

	public boolean eV() {
		return this.bz;
	}

	public void u(boolean boolean1) {
		if (this.eL()) {
			if (this.bz != boolean1) {
				this.bz = boolean1;
				((awu)this.x()).a(boolean1);
				if (boolean1) {
					this.br.a(1, this.by);
				} else {
					this.br.a(this.by);
				}
			}
		} else if (this.bz) {
			this.br.a(this.by);
			this.bz = false;
		}
	}

	protected boolean eL() {
		return true;
	}

	@Override
	public boolean x_() {
		return this.Y().a(d);
	}

	@Override
	protected int d(bec bec) {
		if (this.x_()) {
			this.f = (int)((float)this.f * 2.5F);
		}

		return super.d(bec);
	}

	@Override
	public void a(boolean boolean1) {
		this.Y().b(d, boolean1);
		if (this.l != null && !this.l.v) {
			apt apt3 = this.a(apx.d);
			apt3.d(c);
			if (boolean1) {
				apt3.b(c);
			}
		}
	}

	@Override
	public void a(tq<?> tq) {
		if (d.equals(tq)) {
			this.y_();
		}

		super.a(tq);
	}

	protected boolean eO() {
		return true;
	}

	@Override
	public void j() {
		if (!this.l.v && this.aU() && !this.eE()) {
			if (this.eU()) {
				this.bB--;
				if (this.bB < 0) {
					this.eQ();
				}
			} else if (this.eO()) {
				if (this.a(acz.a)) {
					this.bA++;
					if (this.bA >= 600) {
						this.a(300);
					}
				} else {
					this.bA = -1;
				}
			}
		}

		super.j();
	}

	@Override
	public void k() {
		if (this.aU()) {
			boolean boolean2 = this.U_() && this.eH();
			if (boolean2) {
				bki bki3 = this.b(aor.HEAD);
				if (!bki3.a()) {
					if (bki3.e()) {
						bki3.b(bki3.g() + this.J.nextInt(2));
						if (bki3.g() >= bki3.h()) {
							this.c(aor.HEAD);
							this.a(aor.HEAD, bki.b);
						}
					}

					boolean2 = false;
				}

				if (boolean2) {
					this.f(8);
				}
			}
		}

		super.k();
	}

	private void a(int integer) {
		this.bB = integer;
		this.Y().b(bw, true);
	}

	protected void eQ() {
		this.c(aoq.q);
		if (!this.av()) {
			this.l.a(null, 1040, this.cA(), 0);
		}
	}

	protected void c(aoq<? extends bcu> aoq) {
		bcu bcu3 = this.b(aoq);
		if (bcu3 != null) {
			bcu3.u(bcu3.l.d(bcu3.cA()).d());
			bcu3.u(bcu3.eL() && this.eV());
		}
	}

	protected boolean U_() {
		return true;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (super.a(anw, float2)) {
			aoy aoy4 = this.A();
			if (aoy4 == null && anw.k() instanceof aoy) {
				aoy4 = (aoy)anw.k();
			}

			if (aoy4 != null && this.l.ac() == and.HARD && (double)this.J.nextFloat() < this.b(apx.l) && this.l.S().b(bpx.d)) {
				int integer5 = aec.c(this.cC());
				int integer6 = aec.c(this.cD());
				int integer7 = aec.c(this.cG());
				bcu bcu8 = new bcu(this.l);

				for (int integer9 = 0; integer9 < 50; integer9++) {
					int integer10 = integer5 + aec.a(this.J, 7, 40) * aec.a(this.J, -1, 1);
					int integer11 = integer6 + aec.a(this.J, 7, 40) * aec.a(this.J, -1, 1);
					int integer12 = integer7 + aec.a(this.J, 7, 40) * aec.a(this.J, -1, 1);
					fu fu13 = new fu(integer10, integer11, integer12);
					aoq<?> aoq14 = bcu8.U();
					app.c c15 = app.a(aoq14);
					if (bqj.a(c15, this.l, fu13, aoq14) && app.a(aoq14, this.l, apb.REINFORCEMENT, fu13, this.l.t)) {
						bcu8.d((double)integer10, (double)integer11, (double)integer12);
						if (!this.l.a((double)integer10, (double)integer11, (double)integer12, 7.0) && this.l.i(bcu8) && this.l.j(bcu8) && !this.l.d(bcu8.cb())) {
							this.l.c(bcu8);
							bcu8.i(aoy4);
							bcu8.a(this.l, this.l.d(bcu8.cA()), apb.REINFORCEMENT, null, null);
							this.a(apx.l).c(new apv("Zombie reinforcement caller charge", -0.05F, apv.a.ADDITION));
							bcu8.a(apx.l).c(new apv("Zombie reinforcement callee charge", -0.05F, apv.a.ADDITION));
							break;
						}
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean B(aom aom) {
		boolean boolean3 = super.B(aom);
		if (boolean3) {
			float float4 = this.l.d(this.cA()).b();
			if (this.dC().a() && this.bm() && this.J.nextFloat() < float4 * 0.3F) {
				aom.f(2 * (int)float4);
			}
		}

		return boolean3;
	}

	@Override
	protected ack I() {
		return acl.rA;
	}

	@Override
	protected ack e(anw anw) {
		return acl.rK;
	}

	@Override
	protected ack dp() {
		return acl.rF;
	}

	protected ack eM() {
		return acl.rQ;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(this.eM(), 0.15F, 1.0F);
	}

	@Override
	public apc dB() {
		return apc.b;
	}

	@Override
	protected void a(ane ane) {
		super.a(ane);
		if (this.J.nextFloat() < (this.l.ac() == and.HARD ? 0.05F : 0.01F)) {
			int integer3 = this.J.nextInt(3);
			if (integer3 == 0) {
				this.a(aor.MAINHAND, new bki(bkk.ko));
			} else {
				this.a(aor.MAINHAND, new bki(bkk.ka));
			}
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.x_()) {
			le.a("IsBaby", true);
		}

		le.a("CanBreakDoors", this.eV());
		le.b("InWaterTime", this.aA() ? this.bA : -1);
		le.b("DrownedConversionTime", this.eU() ? this.bB : -1);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.q("IsBaby")) {
			this.a(true);
		}

		this.u(le.q("CanBreakDoors"));
		this.bA = le.h("InWaterTime");
		if (le.c("DrownedConversionTime", 99) && le.h("DrownedConversionTime") > -1) {
			this.a(le.h("DrownedConversionTime"));
		}
	}

	@Override
	public void a_(aoy aoy) {
		super.a_(aoy);
		if ((this.l.ac() == and.NORMAL || this.l.ac() == and.HARD) && aoy instanceof bdp) {
			if (this.l.ac() != and.HARD && this.J.nextBoolean()) {
				return;
			}

			bdp bdp3 = (bdp)aoy;
			bcv bcv4 = aoq.aZ.a(this.l);
			bcv4.u(bdp3);
			bdp3.aa();
			bcv4.a(this.l, this.l.d(bcv4.cA()), apb.CONVERSION, new bcu.b(false, true), null);
			bcv4.a(bdp3.eY());
			bcv4.a(bdp3.fj().a(lp.a).getValue());
			bcv4.g(bdp3.eP().a());
			bcv4.a(bdp3.eM());
			bcv4.a(bdp3.x_());
			bcv4.q(bdp3.eE());
			if (bdp3.Q()) {
				bcv4.a(bdp3.R());
				bcv4.n(bdp3.bW());
			}

			if (bdp3.ev()) {
				bcv4.et();
			}

			bcv4.m(this.bI());
			this.l.c(bcv4);
			if (!this.av()) {
				this.l.a(null, 1026, this.cA(), 0);
			}
		}
	}

	@Override
	protected float b(apj apj, aon aon) {
		return this.x_() ? 0.93F : 1.74F;
	}

	@Override
	public boolean h(bki bki) {
		return bki.b() == bkk.mg && this.x_() && this.bn() ? false : super.h(bki);
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		apo = super.a(bqc, ane, apb, apo, le);
		float float7 = ane.d();
		this.p(this.J.nextFloat() < 0.55F * float7);
		if (apo == null) {
			apo = new bcu.b(a(bqc.v_()), true);
		}

		if (apo instanceof bcu.b) {
			bcu.b b8 = (bcu.b)apo;
			if (b8.a) {
				this.a(true);
				if (b8.b) {
					if ((double)bqc.v_().nextFloat() < 0.05) {
						List<ayn> list9 = bqc.a(ayn.class, this.cb().c(5.0, 3.0, 5.0), aop.c);
						if (!list9.isEmpty()) {
							ayn ayn10 = (ayn)list9.get(0);
							ayn10.t(true);
							this.m(ayn10);
						}
					} else if ((double)bqc.v_().nextFloat() < 0.05) {
						ayn ayn9 = aoq.j.a(this.l);
						ayn9.b(this.cC(), this.cD(), this.cG(), this.p, 0.0F);
						ayn9.a(bqc, ane, apb.JOCKEY, null, null);
						ayn9.t(true);
						this.m(ayn9);
						bqc.c(ayn9);
					}
				}
			}

			this.u(this.eL() && this.J.nextFloat() < float7 * 0.1F);
			this.a(ane);
			this.b(ane);
		}

		if (this.b(aor.HEAD).a()) {
			LocalDate localDate8 = LocalDate.now();
			int integer9 = localDate8.get(ChronoField.DAY_OF_MONTH);
			int integer10 = localDate8.get(ChronoField.MONTH_OF_YEAR);
			if (integer10 == 10 && integer9 == 31 && this.J.nextFloat() < 0.25F) {
				this.a(aor.HEAD, new bki(this.J.nextFloat() < 0.1F ? bvs.cV : bvs.cU));
				this.bu[aor.HEAD.b()] = 0.0F;
			}
		}

		this.u(float7);
		return apo;
	}

	public static boolean a(Random random) {
		return random.nextFloat() < 0.05F;
	}

	protected void u(float float1) {
		this.eW();
		this.a(apx.c).c(new apv("Random spawn bonus", this.J.nextDouble() * 0.05F, apv.a.ADDITION));
		double double3 = this.J.nextDouble() * 1.5 * (double)float1;
		if (double3 > 1.0) {
			this.a(apx.b).c(new apv("Random zombie-spawn bonus", double3, apv.a.MULTIPLY_TOTAL));
		}

		if (this.J.nextFloat() < float1 * 0.05F) {
			this.a(apx.l).c(new apv("Leader zombie bonus", this.J.nextDouble() * 0.25 + 0.5, apv.a.ADDITION));
			this.a(apx.a).c(new apv("Leader zombie bonus", this.J.nextDouble() * 3.0 + 1.0, apv.a.MULTIPLY_TOTAL));
			this.u(this.eL());
		}
	}

	protected void eW() {
		this.a(apx.l).a(this.J.nextDouble() * 0.1F);
	}

	@Override
	public double aX() {
		return this.x_() ? 0.0 : -0.45;
	}

	@Override
	protected void a(anw anw, int integer, boolean boolean3) {
		super.a(anw, integer, boolean3);
		aom aom5 = anw.k();
		if (aom5 instanceof bbn) {
			bbn bbn6 = (bbn)aom5;
			if (bbn6.eO()) {
				bbn6.eP();
				bki bki7 = this.eN();
				if (!bki7.a()) {
					this.a(bki7);
				}
			}
		}
	}

	protected bki eN() {
		return new bki(bkk.pg);
	}

	class a extends avk {
		a(apg apg, double double3, int integer) {
			super(bvs.kf, apg, double3, integer);
		}

		@Override
		public void a(bqc bqc, fu fu) {
			bqc.a(null, fu, acl.rG, acm.HOSTILE, 0.5F, 0.9F + bcu.this.J.nextFloat() * 0.2F);
		}

		@Override
		public void a(bqb bqb, fu fu) {
			bqb.a(null, fu, acl.pp, acm.BLOCKS, 0.7F, 0.9F + bqb.t.nextFloat() * 0.2F);
		}

		@Override
		public double h() {
			return 1.14;
		}
	}

	public static class b implements apo {
		public final boolean a;
		public final boolean b;

		public b(boolean boolean1, boolean boolean2) {
			this.a = boolean1;
			this.b = boolean2;
		}
	}
}
