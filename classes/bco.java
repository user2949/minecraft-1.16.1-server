import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class bco extends ayk implements aow, apm {
	private static final bmr bv = bmr.a(bkk.bx);
	private static final bmr bw = bmr.a(bkk.bx, bkk.pk);
	private static final tq<Integer> bx = tt.a(bco.class, ts.b);
	private static final tq<Boolean> by = tt.a(bco.class, ts.i);
	private static final tq<Boolean> bz = tt.a(bco.class, ts.i);
	private final aov bA = new aov(this.S, bx, bz);
	private avr bB;
	private avb bC;

	public bco(aoq<? extends bco> aoq, bqb bqb) {
		super(aoq, bqb);
		this.i = true;
		this.a(czb.WATER, -1.0F);
		this.a(czb.LAVA, 0.0F);
		this.a(czb.DANGER_FIRE, 0.0F);
		this.a(czb.DAMAGE_FIRE, 0.0F);
	}

	public static boolean c(aoq<bco> aoq, bqc bqc, apb apb, fu fu, Random random) {
		fu.a a6 = fu.i();

		do {
			a6.c(fz.UP);
		} while (bqc.b(a6).a(acz.b));

		return bqc.d_(a6).g();
	}

	@Override
	public void a(tq<?> tq) {
		if (bx.equals(tq) && this.l.v) {
			this.bA.a();
		}

		super.a(tq);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bx, 0);
		this.S.a(by, false);
		this.S.a(bz, false);
	}

	@Override
	public void b(le le) {
		super.b(le);
		this.bA.a(le);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.bA.b(le);
	}

	@Override
	public boolean N_() {
		return this.bA.b();
	}

	@Override
	public boolean M_() {
		return this.aU() && !this.x_();
	}

	@Override
	public void a(@Nullable acm acm) {
		this.bA.a(true);
		if (acm != null) {
			this.l.a(null, this, acl.ob, acm, 0.5F, 1.0F);
		}
	}

	@Override
	protected void o() {
		this.bC = new avb(this, 1.65);
		this.br.a(1, this.bC);
		this.br.a(3, new att(this, 1.0));
		this.bB = new avr(this, 1.4, false, bw);
		this.br.a(4, this.bB);
		this.br.a(5, new auf(this, 1.1));
		this.br.a(7, new avf(this, 1.0, 60));
		this.br.a(8, new auo(this, bec.class, 8.0F));
		this.br.a(8, new ave(this));
		this.br.a(9, new auo(this, bco.class, 8.0F));
	}

	public void t(boolean boolean1) {
		this.S.b(by, boolean1);
	}

	public boolean eL() {
		return this.cs() instanceof bco ? ((bco)this.cs()).eL() : this.S.a(by);
	}

	@Override
	public boolean a(cwz cwz) {
		return cwz.a(acz.b);
	}

	@Nullable
	@Override
	public deg j(aom aom) {
		return aom.aR() ? aom.cb() : null;
	}

	@Override
	public boolean aR() {
		return true;
	}

	@Override
	public double aY() {
		float float2 = Math.min(0.25F, this.aC);
		float float3 = this.aD;
		return (double)this.cy() - 0.2 + (double)(0.12F * aec.b(float3 * 1.5F) * 2.0F * float2);
	}

	@Override
	public boolean es() {
		aom aom2 = this.cl();
		if (!(aom2 instanceof bec)) {
			return false;
		} else {
			bec bec3 = (bec)aom2;
			return bec3.dC().b() == bkk.pk || bec3.dD().b() == bkk.pk;
		}
	}

	@Override
	public boolean a(bqd bqd) {
		return bqd.i(this);
	}

	@Nullable
	@Override
	public aom cl() {
		return this.cm().isEmpty() ? null : (aom)this.cm().get(0);
	}

	@Override
	public dem c(aoy aoy) {
		dem[] arr3 = new dem[]{
			a((double)this.cx(), (double)aoy.cx(), aoy.p),
			a((double)this.cx(), (double)aoy.cx(), aoy.p - 22.5F),
			a((double)this.cx(), (double)aoy.cx(), aoy.p + 22.5F),
			a((double)this.cx(), (double)aoy.cx(), aoy.p - 45.0F),
			a((double)this.cx(), (double)aoy.cx(), aoy.p + 45.0F)
		};
		Set<fu> set4 = Sets.<fu>newLinkedHashSet();
		double double5 = this.cb().e;
		double double7 = this.cb().b - 0.5;
		fu.a a9 = new fu.a();

		for (dem dem13 : arr3) {
			a9.c(this.cC() + dem13.b, double5, this.cG() + dem13.d);

			for (double double14 = double5; double14 > double7; double14--) {
				set4.add(a9.h());
				a9.c(fz.DOWN);
			}
		}

		for (fu fu11 : set4) {
			if (!this.l.b(fu11).a(acz.b)) {
				for (apj apj13 : aoy.ei()) {
					double double14 = this.l.m(fu11);
					if (bfu.a(double14)) {
						deg deg16 = aoy.f(apj13);
						dem dem17 = dem.a(fu11, double14);
						if (bfu.a(this.l, aoy, deg16.c(dem17))) {
							aoy.b(apj13);
							return dem17;
						}
					}
				}
			}
		}

		return new dem(this.cC(), this.cb().e, this.cG());
	}

	@Override
	public void f(dem dem) {
		this.n(this.eM());
		this.a(this, this.bA, dem);
	}

	public float eM() {
		return (float)this.b(apx.d) * (this.eL() ? 0.66F : 1.0F);
	}

	@Override
	public float O_() {
		return (float)this.b(apx.d) * (this.eL() ? 0.23F : 0.55F);
	}

	@Override
	public void a_(dem dem) {
		super.f(dem);
	}

	@Override
	protected float ao() {
		return this.B + 0.6F;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(this.aN() ? acl.nZ : acl.nY, 1.0F, 1.0F);
	}

	@Override
	public boolean P_() {
		return this.bA.a(this.cX());
	}

	@Override
	protected void a(double double1, boolean boolean2, cfj cfj, fu fu) {
		this.at();
		if (this.aN()) {
			this.C = 0.0F;
		} else {
			super.a(double1, boolean2, cfj, fu);
		}
	}

	@Override
	public void j() {
		if (this.eP() && this.J.nextInt(140) == 0) {
			this.a(acl.nU, 1.0F, this.dG());
		} else if (this.eO() && this.J.nextInt(60) == 0) {
			this.a(acl.nV, 1.0F, this.dG());
		}

		cfj cfj2 = this.l.d_(this.cA());
		cfj cfj3 = this.aJ();
		boolean boolean4 = cfj2.a(acx.aw) || cfj3.a(acx.aw) || this.b(acz.b) > 0.0;
		this.t(!boolean4);
		super.j();
		this.eV();
		this.at();
	}

	private boolean eO() {
		return this.bC != null && this.bC.h();
	}

	private boolean eP() {
		return this.bB != null && this.bB.h();
	}

	@Override
	protected boolean q() {
		return true;
	}

	private void eV() {
		if (this.aN()) {
			der der2 = der.a(this);
			if (der2.a(bze.c, this.cA(), true) && !this.l.b(this.cA().b()).a(acz.b)) {
				this.t = true;
			} else {
				this.e(this.cB().a(0.5).b(0.0, 0.05, 0.0));
			}
		}
	}

	public static apw.a eN() {
		return aoz.p().a(apx.d, 0.175F).a(apx.b, 16.0);
	}

	@Override
	protected ack I() {
		return !this.eO() && !this.eP() ? acl.nT : null;
	}

	@Override
	protected ack e(anw anw) {
		return acl.nX;
	}

	@Override
	protected ack dp() {
		return acl.nW;
	}

	@Override
	protected boolean q(aom aom) {
		return this.cm().isEmpty() && !this.a(acz.b);
	}

	@Override
	public boolean dN() {
		return true;
	}

	@Override
	public boolean bm() {
		return false;
	}

	@Override
	protected awv b(bqb bqb) {
		return new bco.b(this, bqb);
	}

	@Override
	public float a(fu fu, bqd bqd) {
		return bqd.d_(fu).m().a(acz.b) ? 10.0F : 0.0F;
	}

	public bco a(aok aok) {
		return aoq.aE.a(this.l);
	}

	@Override
	public boolean k(bki bki) {
		return bv.a(bki);
	}

	@Override
	protected void dm() {
		super.dm();
		if (this.N_()) {
			this.a(bkk.lO);
		}
	}

	@Override
	public ang b(bec bec, anf anf) {
		boolean boolean4 = this.k(bec.b(anf));
		if (!boolean4 && this.N_() && !this.bo()) {
			if (!this.l.v) {
				bec.m(this);
			}

			return ang.a(this.l.v);
		} else {
			ang ang5 = super.b(bec, anf);
			if (!ang5.a()) {
				bki bki6 = bec.b(anf);
				return bki6.b() == bkk.lO ? bki6.a(bec, this, anf) : ang.PASS;
			} else {
				if (boolean4 && !this.av()) {
					this.l.a(null, this.cC(), this.cD(), this.cG(), acl.oa, this.ct(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
				}

				return ang5;
			}
		}
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		apo apo8 = null;
		bco.a.a a7;
		if (apo instanceof bco.a) {
			a7 = ((bco.a)apo).a;
		} else if (!this.x_()) {
			if (this.J.nextInt(30) == 0) {
				a7 = bco.a.a.PIGLIN_RIDER;
				apo8 = new bcu.b(bcu.a(this.J), false);
			} else if (this.J.nextInt(10) == 0) {
				a7 = bco.a.a.BABY_RIDER;
			} else {
				a7 = bco.a.a.NO_RIDER;
			}

			apo = new bco.a(a7);
			((aok.a)apo).a(a7 == bco.a.a.NO_RIDER ? 0.5F : 0.0F);
		} else {
			a7 = bco.a.a.NO_RIDER;
		}

		aoz aoz9 = null;
		if (a7 == bco.a.a.BABY_RIDER) {
			bco bco10 = aoq.aE.a(bqc.n());
			if (bco10 != null) {
				aoz9 = bco10;
				bco10.c_(-24000);
			}
		} else if (a7 == bco.a.a.PIGLIN_RIDER) {
			bcw bcw10 = aoq.ba.a(bqc.n());
			if (bcw10 != null) {
				aoz9 = bcw10;
				this.a(null);
			}
		}

		if (aoz9 != null) {
			aoz9.b(this.cC(), this.cD(), this.cG(), this.p, 0.0F);
			aoz9.a(bqc, ane, apb.JOCKEY, apo8, null);
			aoz9.a(this, true);
			bqc.c(aoz9);
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	public static class a extends aok.a {
		public final bco.a.a a;

		public a(bco.a.a a) {
			this.a = a;
		}

		public static enum a {
			NO_RIDER,
			BABY_RIDER,
			PIGLIN_RIDER;
		}
	}

	static class b extends awu {
		b(bco bco, bqb bqb) {
			super(bco, bqb);
		}

		@Override
		protected czh a(int integer) {
			this.o = new czl();
			return new czh(this.o, integer);
		}

		@Override
		protected boolean a(czb czb) {
			return czb != czb.LAVA && czb != czb.DAMAGE_FIRE && czb != czb.DANGER_FIRE ? super.a(czb) : true;
		}

		@Override
		public boolean a(fu fu) {
			return this.b.d_(fu).a(bvs.B) || super.a(fu);
		}
	}
}
