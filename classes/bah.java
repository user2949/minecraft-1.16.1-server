import javax.annotation.Nullable;

public class bah extends bae {
	private dem b;
	private int c;

	public bah(bac bac) {
		super(bac);
	}

	@Override
	public void b() {
		if (this.c++ % 10 == 0) {
			float float2 = (this.a.cX().nextFloat() - 0.5F) * 8.0F;
			float float3 = (this.a.cX().nextFloat() - 0.5F) * 4.0F;
			float float4 = (this.a.cX().nextFloat() - 0.5F) * 8.0F;
			this.a.l.a(hh.v, this.a.cC() + (double)float2, this.a.cD() + 2.0 + (double)float3, this.a.cG() + (double)float4, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void c() {
		this.c++;
		if (this.b == null) {
			fu fu2 = this.a.l.a(cio.a.MOTION_BLOCKING, cks.a);
			this.b = dem.c(fu2);
		}

		double double2 = this.b.c(this.a.cC(), this.a.cD(), this.a.cG());
		if (!(double2 < 100.0) && !(double2 > 22500.0) && !this.a.u && !this.a.v) {
			this.a.c(1.0F);
		} else {
			this.a.c(0.0F);
		}
	}

	@Override
	public void d() {
		this.b = null;
		this.c = 0;
	}

	@Override
	public float f() {
		return 3.0F;
	}

	@Nullable
	@Override
	public dem g() {
		return this.b;
	}

	@Override
	public bas<bah> i() {
		return bas.j;
	}
}
