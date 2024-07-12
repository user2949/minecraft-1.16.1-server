import java.util.EnumSet;

public abstract class auu extends aug {
	protected final apg a;
	public final double b;
	protected int c;
	protected int d;
	private int g;
	protected fu e = fu.b;
	private boolean h;
	private final int i;
	private final int j;
	protected int f;

	public auu(apg apg, double double2, int integer) {
		this(apg, double2, integer, 1);
	}

	public auu(apg apg, double double2, int integer3, int integer4) {
		this.a = apg;
		this.b = double2;
		this.i = integer3;
		this.f = 0;
		this.j = integer4;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.JUMP));
	}

	@Override
	public boolean a() {
		if (this.c > 0) {
			this.c--;
			return false;
		} else {
			this.c = this.a(this.a);
			return this.l();
		}
	}

	protected int a(apg apg) {
		return 200 + apg.cX().nextInt(200);
	}

	@Override
	public boolean b() {
		return this.d >= -this.g && this.d <= 1200 && this.a(this.a.l, this.e);
	}

	@Override
	public void c() {
		this.g();
		this.d = 0;
		this.g = this.a.cX().nextInt(this.a.cX().nextInt(1200) + 1200) + 1200;
	}

	protected void g() {
		this.a.x().a((double)((float)this.e.u()) + 0.5, (double)(this.e.v() + 1), (double)((float)this.e.w()) + 0.5, this.b);
	}

	public double h() {
		return 1.0;
	}

	@Override
	public void e() {
		if (!this.e.b().a(this.a.cz(), this.h())) {
			this.h = false;
			this.d++;
			if (this.j()) {
				this.a.x().a((double)((float)this.e.u()) + 0.5, (double)(this.e.v() + 1), (double)((float)this.e.w()) + 0.5, this.b);
			}
		} else {
			this.h = true;
			this.d--;
		}
	}

	public boolean j() {
		return this.d % 40 == 0;
	}

	protected boolean k() {
		return this.h;
	}

	protected boolean l() {
		int integer2 = this.i;
		int integer3 = this.j;
		fu fu4 = this.a.cA();
		fu.a a5 = new fu.a();

		for (int integer6 = this.f; integer6 <= integer3; integer6 = integer6 > 0 ? -integer6 : 1 - integer6) {
			for (int integer7 = 0; integer7 < integer2; integer7++) {
				for (int integer8 = 0; integer8 <= integer7; integer8 = integer8 > 0 ? -integer8 : 1 - integer8) {
					for (int integer9 = integer8 < integer7 && integer8 > -integer7 ? integer7 : 0; integer9 <= integer7; integer9 = integer9 > 0 ? -integer9 : 1 - integer9) {
						a5.a(fu4, integer8, integer6 - 1, integer9);
						if (this.a.a(a5) && this.a(this.a.l, a5)) {
							this.e = a5;
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	protected abstract boolean a(bqd bqd, fu fu);
}
