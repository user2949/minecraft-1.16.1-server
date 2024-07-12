import java.util.EnumSet;

public class auz extends aug {
	private static final axs a = new axs().a(6.0).b().a();
	private final ayt b;
	private bdp c;
	private int d;

	public auz(ayt ayt) {
		this.b = ayt;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
	}

	@Override
	public boolean a() {
		if (!this.b.l.J()) {
			return false;
		} else if (this.b.cX().nextInt(8000) != 0) {
			return false;
		} else {
			this.c = this.b.l.a(bdp.class, a, this.b, this.b.cC(), this.b.cD(), this.b.cG(), this.b.cb().c(6.0, 2.0, 6.0));
			return this.c != null;
		}
	}

	@Override
	public boolean b() {
		return this.d > 0;
	}

	@Override
	public void c() {
		this.d = 400;
		this.b.t(true);
	}

	@Override
	public void d() {
		this.b.t(false);
		this.c = null;
	}

	@Override
	public void e() {
		this.b.t().a(this.c, 30.0F, 30.0F);
		this.d--;
	}
}
