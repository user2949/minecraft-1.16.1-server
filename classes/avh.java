import java.util.EnumSet;

public class avh extends aug {
	private final aoz a;
	private final bcf b;
	private aoy c;
	private int d = -1;
	private final double e;
	private int f;
	private final int g;
	private final int h;
	private final float i;
	private final float j;

	public avh(bcf bcf, double double2, int integer, float float4) {
		this(bcf, double2, integer, integer, float4);
	}

	public avh(bcf bcf, double double2, int integer3, int integer4, float float5) {
		if (!(bcf instanceof aoy)) {
			throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
		} else {
			this.b = bcf;
			this.a = (aoz)bcf;
			this.e = double2;
			this.g = integer3;
			this.h = integer4;
			this.i = float5;
			this.j = float5 * float5;
			this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		}
	}

	@Override
	public boolean a() {
		aoy aoy2 = this.a.A();
		if (aoy2 != null && aoy2.aU()) {
			this.c = aoy2;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean b() {
		return this.a() || !this.a.x().m();
	}

	@Override
	public void d() {
		this.c = null;
		this.f = 0;
		this.d = -1;
	}

	@Override
	public void e() {
		double double2 = this.a.g(this.c.cC(), this.c.cD(), this.c.cG());
		boolean boolean4 = this.a.z().a(this.c);
		if (boolean4) {
			this.f++;
		} else {
			this.f = 0;
		}

		if (!(double2 > (double)this.j) && this.f >= 5) {
			this.a.x().o();
		} else {
			this.a.x().a(this.c, this.e);
		}

		this.a.t().a(this.c, 30.0F, 30.0F);
		if (--this.d == 0) {
			if (!boolean4) {
				return;
			}

			float float5 = aec.a(double2) / this.i;
			float float6 = aec.a(float5, 0.1F, 1.0F);
			this.b.a(this.c, float6);
			this.d = aec.d(float5 * (float)(this.h - this.g) + (float)this.g);
		} else if (this.d < 0) {
			float float5 = aec.a(double2) / this.i;
			this.d = aec.d(float5 * (float)(this.h - this.g) + (float)this.g);
		}
	}
}
