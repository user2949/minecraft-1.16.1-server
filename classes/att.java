import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;

public class att extends aug {
	private static final axs d = new axs().a(8.0).a().b().c();
	protected final ayk a;
	private final Class<? extends ayk> e;
	protected final bqb b;
	protected ayk c;
	private int f;
	private final double g;

	public att(ayk ayk, double double2) {
		this(ayk, double2, ayk.getClass());
	}

	public att(ayk ayk, double double2, Class<? extends ayk> class3) {
		this.a = ayk;
		this.b = ayk.l;
		this.e = class3;
		this.g = double2;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
	}

	@Override
	public boolean a() {
		if (!this.a.eT()) {
			return false;
		} else {
			this.c = this.h();
			return this.c != null;
		}
	}

	@Override
	public boolean b() {
		return this.c.aU() && this.c.eT() && this.f < 60;
	}

	@Override
	public void d() {
		this.c = null;
		this.f = 0;
	}

	@Override
	public void e() {
		this.a.t().a(this.c, 10.0F, (float)this.a.eo());
		this.a.x().a(this.c, this.g);
		this.f++;
		if (this.f >= 60 && this.a.h(this.c) < 9.0) {
			this.g();
		}
	}

	@Nullable
	private ayk h() {
		List<ayk> list2 = this.b.a(this.e, d, this.a, this.a.cb().g(8.0));
		double double3 = Double.MAX_VALUE;
		ayk ayk5 = null;

		for (ayk ayk7 : list2) {
			if (this.a.a(ayk7) && this.a.h(ayk7) < double3) {
				ayk5 = ayk7;
				double3 = this.a.h(ayk7);
			}
		}

		return ayk5;
	}

	protected void g() {
		this.a.a(this.b, this.c);
	}
}
