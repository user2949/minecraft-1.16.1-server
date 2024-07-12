import java.util.EnumSet;

public class aue extends aug {
	private final apq a;
	private aoy b;
	private final bqd c;
	private final double d;
	private final awv e;
	private int f;
	private final float g;
	private final float h;
	private float i;
	private final boolean j;

	public aue(apq apq, double double2, float float3, float float4, boolean boolean5) {
		this.a = apq;
		this.c = apq.l;
		this.d = double2;
		this.e = apq.x();
		this.h = float3;
		this.g = float4;
		this.j = boolean5;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
		if (!(apq.x() instanceof awu) && !(apq.x() instanceof awt)) {
			throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
		}
	}

	@Override
	public boolean a() {
		aoy aoy2 = this.a.eO();
		if (aoy2 == null) {
			return false;
		} else if (aoy2.a_()) {
			return false;
		} else if (this.a.eP()) {
			return false;
		} else if (this.a.h((aom)aoy2) < (double)(this.h * this.h)) {
			return false;
		} else {
			this.b = aoy2;
			return true;
		}
	}

	@Override
	public boolean b() {
		if (this.e.m()) {
			return false;
		} else {
			return this.a.eP() ? false : !(this.a.h((aom)this.b) <= (double)(this.g * this.g));
		}
	}

	@Override
	public void c() {
		this.f = 0;
		this.i = this.a.a(czb.WATER);
		this.a.a(czb.WATER, 0.0F);
	}

	@Override
	public void d() {
		this.b = null;
		this.e.o();
		this.a.a(czb.WATER, this.i);
	}

	@Override
	public void e() {
		this.a.t().a(this.b, 10.0F, (float)this.a.eo());
		if (--this.f <= 0) {
			this.f = 10;
			if (!this.a.eC() && !this.a.bn()) {
				if (this.a.h((aom)this.b) >= 144.0) {
					this.g();
				} else {
					this.e.a(this.b, this.d);
				}
			}
		}
	}

	private void g() {
		fu fu2 = this.b.cA();

		for (int integer3 = 0; integer3 < 10; integer3++) {
			int integer4 = this.a(-3, 3);
			int integer5 = this.a(-1, 1);
			int integer6 = this.a(-3, 3);
			boolean boolean7 = this.a(fu2.u() + integer4, fu2.v() + integer5, fu2.w() + integer6);
			if (boolean7) {
				return;
			}
		}
	}

	private boolean a(int integer1, int integer2, int integer3) {
		if (Math.abs((double)integer1 - this.b.cC()) < 2.0 && Math.abs((double)integer3 - this.b.cG()) < 2.0) {
			return false;
		} else if (!this.a(new fu(integer1, integer2, integer3))) {
			return false;
		} else {
			this.a.b((double)integer1 + 0.5, (double)integer2, (double)integer3 + 0.5, this.a.p, this.a.q);
			this.e.o();
			return true;
		}
	}

	private boolean a(fu fu) {
		czb czb3 = czl.a(this.c, fu.i());
		if (czb3 != czb.WALKABLE) {
			return false;
		} else {
			cfj cfj4 = this.c.d_(fu.c());
			if (!this.j && cfj4.b() instanceof bza) {
				return false;
			} else {
				fu fu5 = fu.b(this.a.cA());
				return this.c.a_(this.a, this.a.cb().a(fu5));
			}
		}
	}

	private int a(int integer1, int integer2) {
		return this.a.cX().nextInt(integer2 - integer1 + 1) + integer1;
	}
}
