import java.util.EnumSet;

public class avm extends aug {
	private final azm a;
	private final double b;
	private double c;
	private double d;
	private double e;

	public avm(azm azm, double double2) {
		this.a = azm;
		this.b = double2;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		if (!this.a.eX() && this.a.bo()) {
			dem dem2 = axu.a(this.a, 5, 4);
			if (dem2 == null) {
				return false;
			} else {
				this.c = dem2.b;
				this.d = dem2.c;
				this.e = dem2.d;
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public void c() {
		this.a.x().a(this.c, this.d, this.e, this.b);
	}

	@Override
	public boolean b() {
		return !this.a.eX() && !this.a.x().m() && this.a.bo();
	}

	@Override
	public void e() {
		if (!this.a.eX() && this.a.cX().nextInt(50) == 0) {
			aom aom2 = (aom)this.a.cm().get(0);
			if (aom2 == null) {
				return;
			}

			if (aom2 instanceof bec) {
				int integer3 = this.a.fd();
				int integer4 = this.a.fk();
				if (integer4 > 0 && this.a.cX().nextInt(integer4) < integer3) {
					this.a.i((bec)aom2);
					return;
				}

				this.a.v(5);
			}

			this.a.ba();
			this.a.fn();
			this.a.l.a(this.a, (byte)6);
		}
	}
}
