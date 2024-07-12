import java.util.function.Predicate;

public class atr extends atx {
	private final Predicate<and> g;
	protected int a;
	protected int b = -1;
	protected int c = -1;

	public atr(aoz aoz, Predicate<and> predicate) {
		super(aoz);
		this.g = predicate;
	}

	public atr(aoz aoz, int integer, Predicate<and> predicate) {
		this(aoz, predicate);
		this.c = integer;
	}

	protected int f() {
		return Math.max(240, this.c);
	}

	@Override
	public boolean a() {
		if (!super.a()) {
			return false;
		} else {
			return !this.d.l.S().b(bpx.b) ? false : this.a(this.d.l.ac()) && !this.g();
		}
	}

	@Override
	public void c() {
		super.c();
		this.a = 0;
	}

	@Override
	public boolean b() {
		return this.a <= this.f() && !this.g() && this.e.a(this.d.cz(), 2.0) && this.a(this.d.l.ac());
	}

	@Override
	public void d() {
		super.d();
		this.d.l.a(this.d.V(), this.e, -1);
	}

	@Override
	public void e() {
		super.e();
		if (this.d.cX().nextInt(20) == 0) {
			this.d.l.c(1019, this.e, 0);
			if (!this.d.ap) {
				this.d.a(this.d.dW());
			}
		}

		this.a++;
		int integer2 = (int)((float)this.a / (float)this.f() * 10.0F);
		if (integer2 != this.b) {
			this.d.l.a(this.d.V(), this.e, integer2);
			this.b = integer2;
		}

		if (this.a == this.f() && this.a(this.d.l.ac())) {
			this.d.l.a(this.e, false);
			this.d.l.c(1021, this.e, 0);
			this.d.l.c(2001, this.e, bvr.i(this.d.l.d_(this.e)));
		}
	}

	private boolean a(and and) {
		return this.g.test(and);
	}
}
