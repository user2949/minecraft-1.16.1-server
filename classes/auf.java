import java.util.List;

public class auf extends aug {
	private final ayk a;
	private ayk b;
	private final double c;
	private int d;

	public auf(ayk ayk, double double2) {
		this.a = ayk;
		this.c = double2;
	}

	@Override
	public boolean a() {
		if (this.a.i() >= 0) {
			return false;
		} else {
			List<ayk> list2 = this.a.l.a(this.a.getClass(), this.a.cb().c(8.0, 4.0, 8.0));
			ayk ayk3 = null;
			double double4 = Double.MAX_VALUE;

			for (ayk ayk7 : list2) {
				if (ayk7.i() >= 0) {
					double double8 = this.a.h(ayk7);
					if (!(double8 > double4)) {
						double4 = double8;
						ayk3 = ayk7;
					}
				}
			}

			if (ayk3 == null) {
				return false;
			} else if (double4 < 9.0) {
				return false;
			} else {
				this.b = ayk3;
				return true;
			}
		}
	}

	@Override
	public boolean b() {
		if (this.a.i() >= 0) {
			return false;
		} else if (!this.b.aU()) {
			return false;
		} else {
			double double2 = this.a.h(this.b);
			return !(double2 < 9.0) && !(double2 > 256.0);
		}
	}

	@Override
	public void c() {
		this.d = 0;
	}

	@Override
	public void d() {
		this.b = null;
	}

	@Override
	public void e() {
		if (--this.d <= 0) {
			this.d = 10;
			this.a.x().a(this.b, this.c);
		}
	}
}
