import java.util.EnumSet;

public class avj<T extends bcb & bcf & bbo> extends aug {
	public static final adx a = new adx(20, 40);
	private final T b;
	private avj.a c = avj.a.UNCHARGED;
	private final double d;
	private final float e;
	private int f;
	private int g;
	private int h;

	public avj(T bcb, double double2, float float3) {
		this.b = bcb;
		this.d = double2;
		this.e = float3 * float3;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
	}

	@Override
	public boolean a() {
		return this.h() && this.g();
	}

	private boolean g() {
		return this.b.a(bkk.qP);
	}

	@Override
	public boolean b() {
		return this.h() && (this.a() || !this.b.x().m()) && this.g();
	}

	private boolean h() {
		return this.b.A() != null && this.b.A().aU();
	}

	@Override
	public void d() {
		super.d();
		this.b.s(false);
		this.b.i(null);
		this.f = 0;
		if (this.b.dV()) {
			this.b.eb();
			this.b.b(false);
			biz.a(this.b.dX(), false);
		}
	}

	@Override
	public void e() {
		aoy aoy2 = this.b.A();
		if (aoy2 != null) {
			boolean boolean3 = this.b.z().a(aoy2);
			boolean boolean4 = this.f > 0;
			if (boolean3 != boolean4) {
				this.f = 0;
			}

			if (boolean3) {
				this.f++;
			} else {
				this.f--;
			}

			double double5 = this.b.h((aom)aoy2);
			boolean boolean7 = (double5 > (double)this.e || this.f < 5) && this.g == 0;
			if (boolean7) {
				this.h--;
				if (this.h <= 0) {
					this.b.x().a(aoy2, this.j() ? this.d : this.d * 0.5);
					this.h = a.a(this.b.cX());
				}
			} else {
				this.h = 0;
				this.b.x().o();
			}

			this.b.t().a(aoy2, 30.0F, 30.0F);
			if (this.c == avj.a.UNCHARGED) {
				if (!boolean7) {
					this.b.c(bet.a(this.b, bkk.qP));
					this.c = avj.a.CHARGING;
					this.b.b(true);
				}
			} else if (this.c == avj.a.CHARGING) {
				if (!this.b.dV()) {
					this.c = avj.a.UNCHARGED;
				}

				int integer8 = this.b.dZ();
				bki bki9 = this.b.dX();
				if (integer8 >= biz.g(bki9)) {
					this.b.ea();
					this.c = avj.a.CHARGED;
					this.g = 20 + this.b.cX().nextInt(20);
					this.b.b(false);
				}
			} else if (this.c == avj.a.CHARGED) {
				this.g--;
				if (this.g == 0) {
					this.c = avj.a.READY_TO_ATTACK;
				}
			} else if (this.c == avj.a.READY_TO_ATTACK && boolean3) {
				this.b.a(aoy2, 1.0F);
				bki bki8 = this.b.b(bet.a(this.b, bkk.qP));
				biz.a(bki8, false);
				this.c = avj.a.UNCHARGED;
			}
		}
	}

	private boolean j() {
		return this.c == avj.a.UNCHARGED;
	}

	static enum a {
		UNCHARGED,
		CHARGING,
		CHARGED,
		READY_TO_ATTACK;
	}
}
