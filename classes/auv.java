import java.util.EnumSet;

public class auv extends aug {
	private final apg a;
	private double b;
	private double c;
	private double d;
	private final double e;

	public auv(apg apg, double double2) {
		this.a = apg;
		this.e = double2;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		if (this.a.ew()) {
			return false;
		} else {
			dem dem2 = axu.b(this.a, 16, 7, dem.c(this.a.ex()));
			if (dem2 == null) {
				return false;
			} else {
				this.b = dem2.b;
				this.c = dem2.c;
				this.d = dem2.d;
				return true;
			}
		}
	}

	@Override
	public boolean b() {
		return !this.a.x().m();
	}

	@Override
	public void c() {
		this.a.x().a(this.b, this.c, this.d, this.e);
	}
}
