import java.util.EnumSet;

public class auo extends aug {
	protected final aoz a;
	protected aom b;
	protected final float c;
	private int g;
	protected final float d;
	protected final Class<? extends aoy> e;
	protected final axs f;

	public auo(aoz aoz, Class<? extends aoy> class2, float float3) {
		this(aoz, class2, float3, 0.02F);
	}

	public auo(aoz aoz, Class<? extends aoy> class2, float float3, float float4) {
		this.a = aoz;
		this.e = class2;
		this.c = float3;
		this.d = float4;
		this.a(EnumSet.of(aug.a.LOOK));
		if (class2 == bec.class) {
			this.f = new axs().a((double)float3).b().a().d().a(aoy -> aop.b(aoz).test(aoy));
		} else {
			this.f = new axs().a((double)float3).b().a().d();
		}
	}

	@Override
	public boolean a() {
		if (this.a.cX().nextFloat() >= this.d) {
			return false;
		} else {
			if (this.a.A() != null) {
				this.b = this.a.A();
			}

			if (this.e == bec.class) {
				this.b = this.a.l.a(this.f, this.a, this.a.cC(), this.a.cF(), this.a.cG());
			} else {
				this.b = this.a.l.b(this.e, this.f, this.a, this.a.cC(), this.a.cF(), this.a.cG(), this.a.cb().c((double)this.c, 3.0, (double)this.c));
			}

			return this.b != null;
		}
	}

	@Override
	public boolean b() {
		if (!this.b.aU()) {
			return false;
		} else {
			return this.a.h(this.b) > (double)(this.c * this.c) ? false : this.g > 0;
		}
	}

	@Override
	public void c() {
		this.g = 40 + this.a.cX().nextInt(40);
	}

	@Override
	public void d() {
		this.b = null;
	}

	@Override
	public void e() {
		this.a.t().a(this.b.cC(), this.b.cF(), this.b.cG());
		this.g--;
	}
}
