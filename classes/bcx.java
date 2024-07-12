import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.Random;
import javax.annotation.Nullable;

public class bcx extends ayk implements bbt, bcz {
	private static final tq<Boolean> bx = tt.a(bcx.class, ts.i);
	private int by;
	private int bz = 0;
	private boolean bA = false;
	protected static final ImmutableList<? extends axo<? extends axn<? super bcx>>> bv = ImmutableList.of(axo.c, axo.d, axo.n, axo.m);
	protected static final ImmutableList<? extends awp<?>> bw = ImmutableList.of(
		awp.r, awp.g, awp.h, awp.k, awp.l, awp.n, awp.m, awp.D, awp.t, awp.o, awp.p, awp.Z, awp.z, awp.ab, awp.ac, awp.Y, awp.I, awp.af, awp.ag
	);

	public bcx(aoq<? extends bcx> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 5;
	}

	@Override
	public boolean a(bec bec) {
		return !this.eC();
	}

	public static apw.a eL() {
		return bcb.eS().a(apx.a, 40.0).a(apx.d, 0.3F).a(apx.c, 0.6F).a(apx.g, 1.0).a(apx.f, 6.0);
	}

	@Override
	public boolean B(aom aom) {
		if (!(aom instanceof aoy)) {
			return false;
		} else {
			this.by = 10;
			this.l.a(this, (byte)4);
			this.a(acl.fD, 1.0F, this.dG());
			bcy.a(this, (aoy)aom);
			return bcz.a(this, (aoy)aom);
		}
	}

	@Override
	protected void f(aoy aoy) {
		if (this.eM()) {
			bcz.b(this, aoy);
		}
	}

	@Override
	public boolean a(anw anw, float float2) {
		boolean boolean4 = super.a(anw, float2);
		if (this.l.v) {
			return false;
		} else {
			if (boolean4 && anw.k() instanceof aoy) {
				bcy.b(this, (aoy)anw.k());
			}

			return boolean4;
		}
	}

	@Override
	protected apr.b<bcx> cJ() {
		return apr.a(bw, bv);
	}

	@Override
	protected apr<?> a(Dynamic<?> dynamic) {
		return bcy.a(this.cJ().a(dynamic));
	}

	@Override
	public apr<bcx> cI() {
		return (apr<bcx>)super.cI();
	}

	@Override
	protected void N() {
		this.l.X().a("hoglinBrain");
		this.cI().a((zd)this.l, this);
		this.l.X().c();
		bcy.a(this);
		if (this.eO()) {
			this.bz++;
			if (this.bz > 300) {
				this.a(acl.fE);
				this.b((zd)this.l);
			}
		} else {
			this.bz = 0;
		}
	}

	@Override
	public void k() {
		if (this.by > 0) {
			this.by--;
		}

		super.k();
	}

	@Override
	protected void m() {
		if (this.x_()) {
			this.f = 3;
			this.a(apx.f).a(0.5);
		} else {
			this.f = 5;
			this.a(apx.f).a(6.0);
		}
	}

	public static boolean c(aoq<bcx> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return !bqc.d_(fu.c()).a(bvs.iK);
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (bqc.v_().nextFloat() < 0.2F) {
			this.a(true);
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	@Override
	public boolean h(double double1) {
		return !this.ev();
	}

	@Override
	public float a(fu fu, bqd bqd) {
		if (bcy.a(this, fu)) {
			return -1.0F;
		} else {
			return bqd.d_(fu.c()).a(bvs.mu) ? 10.0F : 0.0F;
		}
	}

	@Override
	public double aY() {
		return (double)this.cy() - (this.x_() ? 0.2 : 0.15);
	}

	@Override
	public ang b(bec bec, anf anf) {
		ang ang4 = super.b(bec, anf);
		if (ang4.a()) {
			this.et();
		}

		return ang4;
	}

	@Override
	protected boolean cU() {
		return true;
	}

	@Override
	protected int d(bec bec) {
		return this.f;
	}

	private void b(zd zd) {
		bct bct3 = this.b(aoq.aW);
		bct3.c(new aog(aoi.i, 200, 0));
	}

	@Override
	public boolean k(bki bki) {
		return bki.b() == bkk.bw;
	}

	public boolean eM() {
		return !this.x_();
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bx, false);
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.eW()) {
			le.a("IsImmuneToZombification", true);
		}

		le.b("TimeInOverworld", this.bz);
		if (this.bA) {
			le.a("CannotBeHunted", true);
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.t(le.q("IsImmuneToZombification"));
		this.bz = le.h("TimeInOverworld");
		this.u(le.q("CannotBeHunted"));
	}

	public void t(boolean boolean1) {
		this.Y().b(bx, boolean1);
	}

	private boolean eW() {
		return this.Y().a(bx);
	}

	public boolean eO() {
		return !this.l.m().i() && !this.eW() && !this.eE();
	}

	private void u(boolean boolean1) {
		this.bA = boolean1;
	}

	public boolean eP() {
		return this.eM() && !this.bA;
	}

	@Nullable
	@Override
	public aok a(aok aok) {
		bcx bcx3 = aoq.G.a(this.l);
		if (bcx3 != null) {
			bcx3.et();
		}

		return bcx3;
	}

	@Override
	public boolean eQ() {
		return !bcy.c(this) && super.eQ();
	}

	@Override
	public acm ct() {
		return acm.HOSTILE;
	}

	@Override
	protected ack I() {
		return this.l.v ? null : (ack)bcy.b(this).orElse(null);
	}

	@Override
	protected ack e(anw anw) {
		return acl.fG;
	}

	@Override
	protected ack dp() {
		return acl.fF;
	}

	@Override
	protected ack aq() {
		return acl.gi;
	}

	@Override
	protected ack ar() {
		return acl.gh;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.fI, 0.15F, 1.0F);
	}

	protected void a(ack ack) {
		this.a(ack, this.dF(), this.dG());
	}

	@Override
	protected void M() {
		super.M();
		qy.a(this);
	}
}
