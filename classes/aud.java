import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class aud extends aug {
	private final aoz a;
	private final Predicate<aoz> b;
	private aoz c;
	private final double d;
	private final awv e;
	private int f;
	private final float g;
	private float h;
	private final float i;

	public aud(aoz aoz, double double2, float float3, float float4) {
		this.a = aoz;
		this.b = aoz2 -> aoz2 != null && aoz.getClass() != aoz2.getClass();
		this.d = double2;
		this.e = aoz.x();
		this.g = float3;
		this.i = float4;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		if (!(aoz.x() instanceof awu) && !(aoz.x() instanceof awt)) {
			throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
		}
	}

	@Override
	public boolean a() {
		List<aoz> list2 = this.a.l.a(aoz.class, this.a.cb().g((double)this.i), this.b);
		if (!list2.isEmpty()) {
			for (aoz aoz4 : list2) {
				if (!aoz4.bB()) {
					this.c = aoz4;
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean b() {
		return this.c != null && !this.e.m() && this.a.h(this.c) > (double)(this.g * this.g);
	}

	@Override
	public void c() {
		this.f = 0;
		this.h = this.a.a(czb.WATER);
		this.a.a(czb.WATER, 0.0F);
	}

	@Override
	public void d() {
		this.c = null;
		this.e.o();
		this.a.a(czb.WATER, this.h);
	}

	@Override
	public void e() {
		if (this.c != null && !this.a.eC()) {
			this.a.t().a(this.c, 10.0F, (float)this.a.eo());
			if (--this.f <= 0) {
				this.f = 10;
				double double2 = this.a.cC() - this.c.cC();
				double double4 = this.a.cD() - this.c.cD();
				double double6 = this.a.cG() - this.c.cG();
				double double8 = double2 * double2 + double4 * double4 + double6 * double6;
				if (!(double8 <= (double)(this.g * this.g))) {
					this.e.a(this.c, this.d);
				} else {
					this.e.o();
					atl atl10 = this.c.t();
					if (double8 <= (double)this.g || atl10.d() == this.a.cC() && atl10.e() == this.a.cD() && atl10.f() == this.a.cG()) {
						double double11 = this.c.cC() - this.a.cC();
						double double13 = this.c.cG() - this.a.cG();
						this.e.a(this.a.cC() - double11, this.a.cD(), this.a.cG() - double13, this.d);
					}
				}
			}
		}
	}
}
