import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class bdc extends bcb implements bbo {
	private static final tq<Boolean> d = tt.a(bdc.class, ts.i);
	private static final tq<Boolean> bv = tt.a(bdc.class, ts.i);
	private static final tq<Boolean> bw = tt.a(bdc.class, ts.i);
	private static final tq<Boolean> bx = tt.a(bdc.class, ts.i);
	private static final UUID by = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
	private static final apv bz = new apv(by, "Baby speed boost", 0.2F, apv.a.MULTIPLY_BASE);
	private int bA = 0;
	private final anm bB = new anm(8);
	private boolean bC = false;
	protected static final ImmutableList<axo<? extends axn<? super bdc>>> b = ImmutableList.of(axo.c, axo.d, axo.b, axo.g, axo.e, axo.l);
	protected static final ImmutableList<awp<?>> c = ImmutableList.of(
		awp.n,
		awp.u,
		awp.v,
		awp.g,
		awp.h,
		awp.k,
		awp.l,
		awp.X,
		awp.W,
		awp.J,
		awp.x,
		awp.y,
		awp.m,
		awp.D,
		awp.o,
		awp.p,
		awp.q,
		awp.t,
		awp.L,
		awp.M,
		awp.z,
		awp.N,
		awp.O,
		awp.Q,
		awp.R,
		awp.P,
		awp.T,
		awp.U,
		awp.K,
		awp.aa,
		awp.s,
		awp.ab,
		awp.ac,
		awp.S,
		awp.V,
		awp.ad,
		awp.ae,
		awp.af
	);

	public bdc(aoq<? extends bcb> aoq, bqb bqb) {
		super(aoq, bqb);
		this.p(true);
		((awu)this.x()).a(true);
		this.f = 5;
		this.a(czb.DANGER_FIRE, 16.0F);
		this.a(czb.DAMAGE_FIRE, -1.0F);
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.x_()) {
			le.a("IsBaby", true);
		}

		if (this.eT()) {
			le.a("IsImmuneToZombification", true);
		}

		if (this.bC) {
			le.a("CannotHunt", true);
		}

		le.b("TimeInOverworld", this.bA);
		le.a("Inventory", this.bB.g());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.a(le.q("IsBaby"));
		this.t(le.q("IsImmuneToZombification"));
		this.v(le.q("CannotHunt"));
		this.bA = le.h("TimeInOverworld");
		this.bB.a(le.d("Inventory", 10));
	}

	@Override
	protected void a(anw anw, int integer, boolean boolean3) {
		super.a(anw, integer, boolean3);
		this.bB.f().forEach(this::a);
	}

	protected bki k(bki bki) {
		return this.bB.a(bki);
	}

	protected boolean l(bki bki) {
		return this.bB.b(bki);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(d, false);
		this.S.a(bw, false);
		this.S.a(bv, false);
		this.S.a(bx, false);
	}

	@Override
	public void a(tq<?> tq) {
		super.a(tq);
		if (d.equals(tq)) {
			this.y_();
		}
	}

	public static apw.a eL() {
		return bcb.eS().a(apx.a, 16.0).a(apx.d, 0.35F).a(apx.f, 5.0);
	}

	public static boolean b(aoq<bdc> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return !bqc.d_(fu.c()).a(bvs.iK);
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (apb != apb.STRUCTURE) {
			if (bqc.v_().nextFloat() < 0.2F) {
				this.a(true);
			} else if (this.eM()) {
				this.a(aor.MAINHAND, this.eU());
			}
		}

		bdd.a(this);
		this.a(ane);
		this.b(ane);
		return super.a(bqc, ane, apb, apo, le);
	}

	@Override
	protected boolean L() {
		return false;
	}

	@Override
	public boolean h(double double1) {
		return !this.ev();
	}

	@Override
	protected void a(ane ane) {
		if (this.eM()) {
			this.d(aor.HEAD, new bki(bkk.lo));
			this.d(aor.CHEST, new bki(bkk.lp));
			this.d(aor.LEGS, new bki(bkk.lq));
			this.d(aor.FEET, new bki(bkk.lr));
		}
	}

	private void d(aor aor, bki bki) {
		if (this.l.t.nextFloat() < 0.1F) {
			this.a(aor, bki);
		}
	}

	@Override
	protected apr.b<bdc> cJ() {
		return apr.a(c, b);
	}

	@Override
	protected apr<?> a(Dynamic<?> dynamic) {
		return bdd.a(this, this.cJ().a(dynamic));
	}

	@Override
	public apr<bdc> cI() {
		return (apr<bdc>)super.cI();
	}

	@Override
	public ang b(bec bec, anf anf) {
		ang ang4 = super.b(bec, anf);
		if (ang4.a()) {
			return ang4;
		} else if (!this.l.v) {
			return bdd.a(this, bec, anf);
		} else {
			boolean boolean5 = bdd.b(this, bec.b(anf)) && this.eP() != bdc.a.ADMIRING_ITEM;
			return boolean5 ? ang.SUCCESS : ang.PASS;
		}
	}

	@Override
	protected float b(apj apj, aon aon) {
		return this.x_() ? 0.93F : 1.74F;
	}

	@Override
	public double aX() {
		return this.x_() ? -0.1 : -0.45;
	}

	@Override
	public double aY() {
		return (double)this.cy() * 0.92;
	}

	@Override
	public void a(boolean boolean1) {
		this.Y().b(d, boolean1);
		if (!this.l.v) {
			apt apt3 = this.a(apx.d);
			apt3.d(bz);
			if (boolean1) {
				apt3.b(bz);
			}
		}
	}

	@Override
	public boolean x_() {
		return this.Y().a(d);
	}

	public boolean eM() {
		return !this.x_();
	}

	public void t(boolean boolean1) {
		this.Y().b(bv, boolean1);
	}

	private boolean eT() {
		return this.Y().a(bv);
	}

	private void v(boolean boolean1) {
		this.bC = boolean1;
	}

	protected boolean eN() {
		return !this.bC;
	}

	public boolean eO() {
		return !this.l.m().i() && !this.eT() && !this.eE();
	}

	@Override
	protected void N() {
		this.l.X().a("piglinBrain");
		this.cI().a((zd)this.l, this);
		this.l.X().c();
		bdd.b(this);
		if (this.eO()) {
			this.bA++;
		} else {
			this.bA = 0;
		}

		if (this.bA > 300) {
			this.a(acl.la);
			this.b((zd)this.l);
		}
	}

	@Override
	protected int d(bec bec) {
		return this.f;
	}

	private void b(zd zd) {
		bdd.c(this);
		this.bB.f().forEach(this::a);
		bcw bcw3 = this.b(aoq.ba);
		bcw3.c(new aog(aoi.i, 200, 0));
	}

	@Nullable
	@Override
	public aoy A() {
		return (aoy)this.bn.c(awp.o).orElse(null);
	}

	private bki eU() {
		return (double)this.J.nextFloat() < 0.5 ? new bki(bkk.qP) : new bki(bkk.kE);
	}

	private boolean eV() {
		return this.S.a(bw);
	}

	@Override
	public void b(boolean boolean1) {
		this.S.b(bw, boolean1);
	}

	@Override
	public void V_() {
		this.aP = 0;
	}

	public bdc.a eP() {
		if (this.eQ()) {
			return bdc.a.DANCING;
		} else if (bdd.a(this.dD().b())) {
			return bdc.a.ADMIRING_ITEM;
		} else if (this.eG() && this.eW()) {
			return bdc.a.ATTACKING_WITH_MELEE_WEAPON;
		} else if (this.eV()) {
			return bdc.a.CROSSBOW_CHARGE;
		} else {
			return this.eG() && this.a(bkk.qP) ? bdc.a.CROSSBOW_HOLD : bdc.a.DEFAULT;
		}
	}

	public boolean eQ() {
		return this.S.a(bx);
	}

	public void u(boolean boolean1) {
		this.S.b(bx, boolean1);
	}

	private boolean eW() {
		return this.dC().b() instanceof blp;
	}

	@Override
	public boolean a(anw anw, float float2) {
		boolean boolean4 = super.a(anw, float2);
		if (this.l.v) {
			return false;
		} else {
			if (boolean4 && anw.k() instanceof aoy) {
				bdd.a(this, (aoy)anw.k());
			}

			return boolean4;
		}
	}

	@Override
	public void a(aoy aoy, float float2) {
		this.b(this, 1.6F);
	}

	@Override
	public void a(aoy aoy, bki bki, bes bes, float float4) {
		this.a(this, aoy, bes, float4, 1.6F);
	}

	@Override
	public boolean a(bkv bkv) {
		return bkv == bkk.qP;
	}

	protected void m(bki bki) {
		this.b(aor.MAINHAND, bki);
	}

	protected void n(bki bki) {
		if (bki.b() == bdd.a) {
			this.a(aor.OFFHAND, bki);
			this.d(aor.OFFHAND);
		} else {
			this.b(aor.OFFHAND, bki);
		}
	}

	@Override
	public boolean i(bki bki) {
		return this.l.S().b(bpx.b) && bdd.a(this, bki);
	}

	protected boolean o(bki bki) {
		aor aor3 = aoz.j(bki);
		bki bki4 = this.b(aor3);
		return this.a(bki, bki4);
	}

	@Override
	protected boolean a(bki bki1, bki bki2) {
		if (bny.d(bki2)) {
			return false;
		} else {
			boolean boolean4 = bdd.a(bki1.b()) || bki1.b() == bkk.qP;
			boolean boolean5 = bdd.a(bki2.b()) || bki2.b() == bkk.qP;
			if (boolean4 && !boolean5) {
				return true;
			} else if (!boolean4 && boolean5) {
				return false;
			} else {
				return this.eM() && bki1.b() != bkk.qP && bki2.b() == bkk.qP ? false : super.a(bki1, bki2);
			}
		}
	}

	@Override
	protected void b(bbg bbg) {
		this.a(bbg);
		bdd.a(this, bbg);
	}

	@Override
	public boolean a(aom aom, boolean boolean2) {
		if (this.x_() && aom.U() == aoq.G) {
			aom = this.b(aom, 3);
		}

		return super.a(aom, boolean2);
	}

	private aom b(aom aom, int integer) {
		List<aom> list4 = aom.cm();
		return integer != 1 && !list4.isEmpty() ? this.b((aom)list4.get(0), integer - 1) : aom;
	}

	@Override
	protected ack I() {
		return this.l.v ? null : (ack)bdd.d(this).orElse(null);
	}

	@Override
	protected ack e(anw anw) {
		return acl.kX;
	}

	@Override
	protected ack dp() {
		return acl.kV;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.kZ, 0.15F, 1.0F);
	}

	protected void a(ack ack) {
		this.a(ack, this.dF(), this.dG());
	}

	@Override
	protected void M() {
		super.M();
		qy.a(this);
	}

	public static enum a {
		ATTACKING_WITH_MELEE_WEAPON,
		CROSSBOW_HOLD,
		CROSSBOW_CHARGE,
		ADMIRING_ITEM,
		DANCING,
		DEFAULT;
	}
}
