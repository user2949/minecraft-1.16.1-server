import java.util.EnumSet;

public class avr extends aug {
	private static final axs c = new axs().a(10.0).a().b().d().c();
	protected final apg a;
	private final double d;
	private double e;
	private double f;
	private double g;
	private double h;
	private double i;
	protected bec b;
	private int j;
	private boolean k;
	private final bmr l;
	private final boolean m;

	public avr(apg apg, double double2, bmr bmr, boolean boolean4) {
		this(apg, double2, boolean4, bmr);
	}

	public avr(apg apg, double double2, boolean boolean3, bmr bmr) {
		this.a = apg;
		this.d = double2;
		this.l = bmr;
		this.m = boolean3;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		if (!(apg.x() instanceof awu) && !(apg.x() instanceof awt)) {
			throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
		}
	}

	@Override
	public boolean a() {
		if (this.j > 0) {
			this.j--;
			return false;
		} else {
			this.b = this.a.l.a(c, this.a);
			return this.b == null ? false : this.a(this.b.dC()) || this.a(this.b.dD());
		}
	}

	protected boolean a(bki bki) {
		return this.l.a(bki);
	}

	@Override
	public boolean b() {
		if (this.g()) {
			if (this.a.h(this.b) < 36.0) {
				if (this.b.g(this.e, this.f, this.g) > 0.010000000000000002) {
					return false;
				}

				if (Math.abs((double)this.b.q - this.h) > 5.0 || Math.abs((double)this.b.p - this.i) > 5.0) {
					return false;
				}
			} else {
				this.e = this.b.cC();
				this.f = this.b.cD();
				this.g = this.b.cG();
			}

			this.h = (double)this.b.q;
			this.i = (double)this.b.p;
		}

		return this.a();
	}

	protected boolean g() {
		return this.m;
	}

	@Override
	public void c() {
		this.e = this.b.cC();
		this.f = this.b.cD();
		this.g = this.b.cG();
		this.k = true;
	}

	@Override
	public void d() {
		this.b = null;
		this.a.x().o();
		this.j = 100;
		this.k = false;
	}

	@Override
	public void e() {
		this.a.t().a(this.b, (float)(this.a.ep() + 20), (float)this.a.eo());
		if (this.a.h(this.b) < 6.25) {
			this.a.x().o();
		} else {
			this.a.x().a(this.b, this.d);
		}
	}

	public boolean h() {
		return this.k;
	}
}
