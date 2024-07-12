import java.util.EnumSet;

public class avi<T extends bcb & bcf> extends aug {
	private final T a;
	private final double b;
	private int c;
	private final float d;
	private int e = -1;
	private int f;
	private boolean g;
	private boolean h;
	private int i = -1;

	public avi(T bcb, double double2, int integer, float float4) {
		this.a = bcb;
		this.b = double2;
		this.c = integer;
		this.d = float4 * float4;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
	}

	public void a(int integer) {
		this.c = integer;
	}

	@Override
	public boolean a() {
		return this.a.A() == null ? false : this.g();
	}

	protected boolean g() {
		return this.a.a(bkk.kf);
	}

	@Override
	public boolean b() {
		return (this.a() || !this.a.x().m()) && this.g();
	}

	@Override
	public void c() {
		super.c();
		this.a.s(true);
	}

	@Override
	public void d() {
		super.d();
		this.a.s(false);
		this.f = 0;
		this.e = -1;
		this.a.eb();
	}

	@Override
	public void e() {
		aoy aoy2 = this.a.A();
		if (aoy2 != null) {
			double double3 = this.a.g(aoy2.cC(), aoy2.cD(), aoy2.cG());
			boolean boolean5 = this.a.z().a(aoy2);
			boolean boolean6 = this.f > 0;
			if (boolean5 != boolean6) {
				this.f = 0;
			}

			if (boolean5) {
				this.f++;
			} else {
				this.f--;
			}

			if (!(double3 > (double)this.d) && this.f >= 20) {
				this.a.x().o();
				this.i++;
			} else {
				this.a.x().a(aoy2, this.b);
				this.i = -1;
			}

			if (this.i >= 20) {
				if ((double)this.a.cX().nextFloat() < 0.3) {
					this.g = !this.g;
				}

				if ((double)this.a.cX().nextFloat() < 0.3) {
					this.h = !this.h;
				}

				this.i = 0;
			}

			if (this.i > -1) {
				if (double3 > (double)(this.d * 0.75F)) {
					this.h = false;
				} else if (double3 < (double)(this.d * 0.25F)) {
					this.h = true;
				}

				this.a.u().a(this.h ? -0.5F : 0.5F, this.g ? 0.5F : -0.5F);
				this.a.a(aoy2, 30.0F, 30.0F);
			} else {
				this.a.t().a(aoy2, 30.0F, 30.0F);
			}

			if (this.a.dV()) {
				if (!boolean5 && this.f < -60) {
					this.a.eb();
				} else if (boolean5) {
					int integer7 = this.a.dZ();
					if (integer7 >= 20) {
						this.a.eb();
						this.a.a(aoy2, bis.a(integer7));
						this.e = this.c;
					}
				}
			} else if (--this.e <= 0 && this.f >= -60) {
				this.a.c(bet.a(this.a, bkk.kf));
			}
		}
	}
}
