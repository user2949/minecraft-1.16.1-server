import java.util.EnumSet;
import java.util.function.Predicate;

public class ato<T extends aoy> extends aug {
	protected final apg a;
	private final double i;
	private final double j;
	protected T b;
	protected final float c;
	protected czf d;
	protected final awv e;
	protected final Class<T> f;
	protected final Predicate<aoy> g;
	protected final Predicate<aoy> h;
	private final axs k;

	public ato(apg apg, Class<T> class2, float float3, double double4, double double5) {
		this(apg, class2, aoy -> true, float3, double4, double5, aop.e::test);
	}

	public ato(apg apg, Class<T> class2, Predicate<aoy> predicate3, float float4, double double5, double double6, Predicate<aoy> predicate7) {
		this.a = apg;
		this.f = class2;
		this.g = predicate3;
		this.c = float4;
		this.i = double5;
		this.j = double6;
		this.h = predicate7;
		this.e = apg.x();
		this.a(EnumSet.of(aug.a.MOVE));
		this.k = new axs().a((double)float4).a(predicate7.and(predicate3));
	}

	public ato(apg apg, Class<T> class2, float float3, double double4, double double5, Predicate<aoy> predicate) {
		this(apg, class2, aoy -> true, float3, double4, double5, predicate);
	}

	@Override
	public boolean a() {
		this.b = this.a.l.b(this.f, this.k, this.a, this.a.cC(), this.a.cD(), this.a.cG(), this.a.cb().c((double)this.c, 3.0, (double)this.c));
		if (this.b == null) {
			return false;
		} else {
			dem dem2 = axu.c(this.a, 16, 7, this.b.cz());
			if (dem2 == null) {
				return false;
			} else if (this.b.g(dem2.b, dem2.c, dem2.d) < this.b.h(this.a)) {
				return false;
			} else {
				this.d = this.e.a(dem2.b, dem2.c, dem2.d, 0);
				return this.d != null;
			}
		}
	}

	@Override
	public boolean b() {
		return !this.e.m();
	}

	@Override
	public void c() {
		this.e.a(this.d, this.i);
	}

	@Override
	public void d() {
		this.b = null;
	}

	@Override
	public void e() {
		if (this.a.h((aom)this.b) < 49.0) {
			this.a.x().a(this.j);
		} else {
			this.a.x().a(this.i);
		}
	}
}
