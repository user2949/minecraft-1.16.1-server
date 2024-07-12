import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public abstract class bcc extends bcb {
	private fu b;
	private boolean c;
	private boolean d;

	protected bcc(aoq<? extends bcc> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(4, new bcc.a<>(this, 0.7, 0.595));
	}

	@Override
	public void b(le le) {
		super.b(le);
		if (this.b != null) {
			le.a("PatrolTarget", lq.a(this.b));
		}

		le.a("PatrolLeader", this.c);
		le.a("Patrolling", this.d);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.e("PatrolTarget")) {
			this.b = lq.b(le.p("PatrolTarget"));
		}

		this.c = le.q("PatrolLeader");
		this.d = le.q("Patrolling");
	}

	@Override
	public double aX() {
		return -0.45;
	}

	public boolean eO() {
		return true;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (apb != apb.PATROL && apb != apb.EVENT && apb != apb.STRUCTURE && this.J.nextFloat() < 0.06F && this.eO()) {
			this.c = true;
		}

		if (this.eT()) {
			this.a(aor.HEAD, bfh.s());
			this.a(aor.HEAD, 2.0F);
		}

		if (apb == apb.PATROL) {
			this.d = true;
		}

		return super.a(bqc, ane, apb, apo, le);
	}

	public static boolean b(aoq<? extends bcc> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return bqc.a(bqi.BLOCK, fu) > 8 ? false : d(aoq, bqc, apb, fu, random);
	}

	@Override
	public boolean h(double double1) {
		return !this.d || double1 > 16384.0;
	}

	public void g(fu fu) {
		this.b = fu;
		this.d = true;
	}

	public fu eP() {
		return this.b;
	}

	public boolean eQ() {
		return this.b != null;
	}

	public void t(boolean boolean1) {
		this.c = boolean1;
		this.d = true;
	}

	public boolean eT() {
		return this.c;
	}

	public boolean eU() {
		return true;
	}

	public void eV() {
		this.b = this.cA().b(-500 + this.J.nextInt(1000), 0, -500 + this.J.nextInt(1000));
		this.d = true;
	}

	protected boolean eW() {
		return this.d;
	}

	protected void u(boolean boolean1) {
		this.d = boolean1;
	}

	public static class a<T extends bcc> extends aug {
		private final T a;
		private final double b;
		private final double c;
		private long d;

		public a(T bcc, double double2, double double3) {
			this.a = bcc;
			this.b = double2;
			this.c = double3;
			this.d = -1L;
			this.a(EnumSet.of(aug.a.MOVE));
		}

		@Override
		public boolean a() {
			boolean boolean2 = this.a.l.Q() < this.d;
			return this.a.eW() && this.a.A() == null && !this.a.bo() && this.a.eQ() && !boolean2;
		}

		@Override
		public void c() {
		}

		@Override
		public void d() {
		}

		@Override
		public void e() {
			boolean boolean2 = this.a.eT();
			awv awv3 = this.a.x();
			if (awv3.m()) {
				List<bcc> list4 = this.g();
				if (this.a.eW() && list4.isEmpty()) {
					this.a.u(false);
				} else if (boolean2 && this.a.eP().a(this.a.cz(), 10.0)) {
					this.a.eV();
				} else {
					dem dem5 = dem.c(this.a.eP());
					dem dem6 = this.a.cz();
					dem dem7 = dem6.d(dem5);
					dem5 = dem7.b(90.0F).a(0.4).e(dem5);
					dem dem8 = dem5.d(dem6).d().a(10.0).e(dem6);
					fu fu9 = new fu(dem8);
					fu9 = this.a.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, fu9);
					if (!awv3.a((double)fu9.u(), (double)fu9.v(), (double)fu9.w(), boolean2 ? this.c : this.b)) {
						this.h();
						this.d = this.a.l.Q() + 200L;
					} else if (boolean2) {
						for (bcc bcc11 : list4) {
							bcc11.g(fu9);
						}
					}
				}
			}
		}

		private List<bcc> g() {
			return this.a.l.a(bcc.class, this.a.cb().g(16.0), bcc -> bcc.eU() && !bcc.s(this.a));
		}

		private boolean h() {
			Random random2 = this.a.cX();
			fu fu3 = this.a.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, this.a.cA().b(-8 + random2.nextInt(16), 0, -8 + random2.nextInt(16)));
			return this.a.x().a((double)fu3.u(), (double)fu3.v(), (double)fu3.w(), this.b);
		}
	}
}
