import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class bcw extends bcu implements ape {
	private static final UUID b = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final apv c = new apv(b, "Attacking speed boost", 0.05, apv.a.ADDITION);
	private static final adx d = aej.a(0, 1);
	private int bv;
	private static final adx bw = aej.a(20, 39);
	private int bx;
	private UUID by;
	private static final adx bz = aej.a(4, 6);
	private int bA;

	public bcw(aoq<? extends bcw> aoq, bqb bqb) {
		super(aoq, bqb);
		this.a(czb.LAVA, 8.0F);
	}

	@Override
	public void a(@Nullable UUID uUID) {
		this.by = uUID;
	}

	@Override
	public double aX() {
		return this.x_() ? -0.16 : -0.45;
	}

	@Override
	protected void m() {
		this.br.a(2, new avy(this, 1.0, false));
		this.br.a(7, new avw(this, 1.0));
		this.bs.a(1, new awb(this).a());
		this.bs.a(2, new awc(this, bec.class, 10, true, false, this::b));
		this.bs.a(3, new awi<>(this, true));
	}

	public static apw.a eX() {
		return bcu.eT().a(apx.l, 0.0).a(apx.d, 0.23F).a(apx.f, 5.0);
	}

	@Override
	protected boolean eO() {
		return false;
	}

	@Override
	protected void N() {
		apt apt2 = this.a(apx.d);
		if (this.I_()) {
			if (!this.x_() && !apt2.a(c)) {
				apt2.b(c);
			}

			this.eY();
		} else if (apt2.a(c)) {
			apt2.d(c);
		}

		this.a((zd)this.l, true);
		if (this.A() != null) {
			this.eZ();
		}

		if (this.I_()) {
			this.aN = this.K;
		}

		super.N();
	}

	private void eY() {
		if (this.bv > 0) {
			this.bv--;
			if (this.bv == 0) {
				this.fb();
			}
		}
	}

	private void eZ() {
		if (this.bA > 0) {
			this.bA--;
		} else {
			if (this.z().a(this.A())) {
				this.fa();
			}

			this.bA = bz.a(this.J);
		}
	}

	private void fa() {
		double double2 = this.b(apx.b);
		deg deg4 = deg.a(this.cz()).c(double2, 10.0, double2);
		this.l.b(bcw.class, deg4).stream().filter(bcw -> bcw != this).filter(bcw -> bcw.A() == null).filter(bcw -> !bcw.r(this.A())).forEach(bcw -> bcw.i(this.A()));
	}

	private void fb() {
		this.a(acl.rN, this.dF() * 2.0F, this.dG() * 1.8F);
	}

	@Override
	public void i(@Nullable aoy aoy) {
		if (this.A() == null && aoy != null) {
			this.bv = d.a(this.J);
			this.bA = bz.a(this.J);
		}

		if (aoy instanceof bec) {
			this.e((bec)aoy);
		}

		super.i(aoy);
	}

	@Override
	public void H_() {
		this.a_(bw.a(this.J));
	}

	public static boolean b(aoq<bcw> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return bqc.ac() != and.PEACEFUL && bqc.d_(fu.c()).b() != bvs.iK;
	}

	@Override
	public boolean a(bqd bqd) {
		return bqd.i(this) && !bqd.d(this.cb());
	}

	@Override
	public void b(le le) {
		super.b(le);
		this.c(le);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.a((zd)this.l, le);
	}

	@Override
	public void a_(int integer) {
		this.bx = integer;
	}

	@Override
	public int F_() {
		return this.bx;
	}

	@Override
	public boolean a(anw anw, float float2) {
		return this.b(anw) ? false : super.a(anw, float2);
	}

	@Override
	protected ack I() {
		return this.I_() ? acl.rN : acl.rM;
	}

	@Override
	protected ack e(anw anw) {
		return acl.rP;
	}

	@Override
	protected ack dp() {
		return acl.rO;
	}

	@Override
	protected void a(ane ane) {
		this.a(aor.MAINHAND, new bki(bkk.kE));
	}

	@Override
	protected bki eN() {
		return bki.b;
	}

	@Override
	protected void eW() {
		this.a(apx.l).a(0.0);
	}

	@Override
	public UUID G_() {
		return this.by;
	}

	@Override
	public boolean f(bec bec) {
		return this.b(bec);
	}
}
