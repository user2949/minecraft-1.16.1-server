import java.util.Random;

public class bbs extends bcb {
	private int b;
	private boolean c;

	public bbs(aoq<? extends bbs> aoq, bqb bqb) {
		super(aoq, bqb);
		this.f = 3;
	}

	@Override
	protected void o() {
		this.br.a(1, new aua(this));
		this.br.a(2, new auq(this, 1.0, false));
		this.br.a(3, new avw(this, 1.0));
		this.br.a(7, new auo(this, bec.class, 8.0F));
		this.br.a(8, new ave(this));
		this.bs.a(1, new awb(this).a());
		this.bs.a(2, new awc(this, bec.class, true));
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 0.13F;
	}

	public static apw.a m() {
		return bcb.eS().a(apx.a, 8.0).a(apx.d, 0.25).a(apx.f, 2.0);
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	protected ack I() {
		return acl.dE;
	}

	@Override
	protected ack e(anw anw) {
		return acl.dG;
	}

	@Override
	protected ack dp() {
		return acl.dF;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.dH, 0.15F, 1.0F);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.b = le.h("Lifetime");
		this.c = le.q("PlayerSpawned");
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Lifetime", this.b);
		le.a("PlayerSpawned", this.c);
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
	public double aX() {
		return 0.1;
	}

	public boolean eL() {
		return this.c;
	}

	public void t(boolean boolean1) {
		this.c = boolean1;
	}

	@Override
	public void k() {
		super.k();
		if (this.l.v) {
			for (int integer2 = 0; integer2 < 2; integer2++) {
				this.l.a(hh.Q, this.d(0.5), this.cE(), this.g(0.5), (this.J.nextDouble() - 0.5) * 2.0, -this.J.nextDouble(), (this.J.nextDouble() - 0.5) * 2.0);
			}
		} else {
			if (!this.ev()) {
				this.b++;
			}

			if (this.b >= 2400) {
				this.aa();
			}
		}
	}

	public static boolean b(aoq<bbs> aoq, bqc bqc, apb apb, fu fu, Random random) {
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
}
