import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;

public class atz extends aug {
	protected final apg a;
	private double b;
	private double c;
	private double d;
	private final double e;
	private final bqb f;

	public atz(apg apg, double double2) {
		this.a = apg;
		this.e = double2;
		this.f = apg.l;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		if (this.a.A() != null) {
			return false;
		} else if (!this.f.J()) {
			return false;
		} else if (!this.a.bm()) {
			return false;
		} else if (!this.f.f(this.a.cA())) {
			return false;
		} else {
			return !this.a.b(aor.HEAD).a() ? false : this.g();
		}
	}

	protected boolean g() {
		dem dem2 = this.h();
		if (dem2 == null) {
			return false;
		} else {
			this.b = dem2.b;
			this.c = dem2.c;
			this.d = dem2.d;
			return true;
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

	@Nullable
	protected dem h() {
		Random random2 = this.a.cX();
		fu fu3 = this.a.cA();

		for (int integer4 = 0; integer4 < 10; integer4++) {
			fu fu5 = fu3.b(random2.nextInt(20) - 10, random2.nextInt(6) - 3, random2.nextInt(20) - 10);
			if (!this.f.f(fu5) && this.a.f(fu5) < 0.0F) {
				return dem.c(fu5);
			}
		}

		return null;
	}
}
