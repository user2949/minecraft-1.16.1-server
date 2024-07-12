import java.util.EnumSet;
import javax.annotation.Nullable;

public class avf extends aug {
	protected final apg a;
	protected double b;
	protected double c;
	protected double d;
	protected final double e;
	protected int f;
	protected boolean g;
	private boolean h;

	public avf(apg apg, double double2) {
		this(apg, double2, 120);
	}

	public avf(apg apg, double double2, int integer) {
		this(apg, double2, integer, true);
	}

	public avf(apg apg, double double2, int integer, boolean boolean4) {
		this.a = apg;
		this.e = double2;
		this.f = integer;
		this.h = boolean4;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		if (this.a.bo()) {
			return false;
		} else {
			if (!this.g) {
				if (this.h && this.a.dc() >= 100) {
					return false;
				}

				if (this.a.cX().nextInt(this.f) != 0) {
					return false;
				}
			}

			dem dem2 = this.g();
			if (dem2 == null) {
				return false;
			} else {
				this.b = dem2.b;
				this.c = dem2.c;
				this.d = dem2.d;
				this.g = false;
				return true;
			}
		}
	}

	@Nullable
	protected dem g() {
		return axu.a(this.a, 10, 7);
	}

	@Override
	public boolean b() {
		return !this.a.x().m() && !this.a.bo();
	}

	@Override
	public void c() {
		this.a.x().a(this.b, this.c, this.d, this.e);
	}

	@Override
	public void d() {
		this.a.x().o();
		super.d();
	}

	public void h() {
		this.g = true;
	}

	public void a(int integer) {
		this.f = integer;
	}
}
