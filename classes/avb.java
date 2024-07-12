import java.util.EnumSet;
import javax.annotation.Nullable;

public class avb extends aug {
	protected final apg a;
	protected final double b;
	protected double c;
	protected double d;
	protected double e;
	protected boolean f;

	public avb(apg apg, double double2) {
		this.a = apg;
		this.b = double2;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		if (this.a.cY() == null && !this.a.bm()) {
			return false;
		} else {
			if (this.a.bm()) {
				fu fu2 = this.a(this.a.l, this.a, 5, 4);
				if (fu2 != null) {
					this.c = (double)fu2.u();
					this.d = (double)fu2.v();
					this.e = (double)fu2.w();
					return true;
				}
			}

			return this.g();
		}
	}

	protected boolean g() {
		dem dem2 = axu.a(this.a, 5, 4);
		if (dem2 == null) {
			return false;
		} else {
			this.c = dem2.b;
			this.d = dem2.c;
			this.e = dem2.d;
			return true;
		}
	}

	public boolean h() {
		return this.f;
	}

	@Override
	public void c() {
		this.a.x().a(this.c, this.d, this.e, this.b);
		this.f = true;
	}

	@Override
	public void d() {
		this.f = false;
	}

	@Override
	public boolean b() {
		return !this.a.x().m();
	}

	@Nullable
	protected fu a(bpg bpg, aom aom, int integer3, int integer4) {
		fu fu6 = aom.cA();
		int integer7 = fu6.u();
		int integer8 = fu6.v();
		int integer9 = fu6.w();
		float float10 = (float)(integer3 * integer3 * integer4 * 2);
		fu fu11 = null;
		fu.a a12 = new fu.a();

		for (int integer13 = integer7 - integer3; integer13 <= integer7 + integer3; integer13++) {
			for (int integer14 = integer8 - integer4; integer14 <= integer8 + integer4; integer14++) {
				for (int integer15 = integer9 - integer3; integer15 <= integer9 + integer3; integer15++) {
					a12.d(integer13, integer14, integer15);
					if (bpg.b(a12).a(acz.a)) {
						float float16 = (float)(
							(integer13 - integer7) * (integer13 - integer7) + (integer14 - integer8) * (integer14 - integer8) + (integer15 - integer9) * (integer15 - integer9)
						);
						if (float16 < float10) {
							float10 = float16;
							fu11 = new fu(a12);
						}
					}
				}
			}
		}

		return fu11;
	}
}
