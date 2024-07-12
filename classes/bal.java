import java.util.Random;
import javax.annotation.Nullable;

public class bal extends bae {
	private dem b;

	public bal(bac bac) {
		super(bac);
	}

	@Override
	public void b() {
		dem dem2 = this.a.t(1.0F).d();
		dem2.b((float) (-Math.PI / 4));
		double double3 = this.a.bv.cC();
		double double5 = this.a.bv.e(0.5);
		double double7 = this.a.bv.cG();

		for (int integer9 = 0; integer9 < 8; integer9++) {
			Random random10 = this.a.cX();
			double double11 = double3 + random10.nextGaussian() / 2.0;
			double double13 = double5 + random10.nextGaussian() / 2.0;
			double double15 = double7 + random10.nextGaussian() / 2.0;
			dem dem17 = this.a.cB();
			this.a.l.a(hh.i, double11, double13, double15, -dem2.b * 0.08F + dem17.b, -dem2.c * 0.3F + dem17.c, -dem2.d * 0.08F + dem17.d);
			dem2.b((float) (Math.PI / 16));
		}
	}

	@Override
	public void c() {
		if (this.b == null) {
			this.b = dem.c(this.a.l.a(cio.a.MOTION_BLOCKING_NO_LEAVES, cks.a));
		}

		if (this.b.c(this.a.cC(), this.a.cD(), this.a.cG()) < 1.0) {
			this.a.eL().b(bas.f).j();
			this.a.eL().a(bas.g);
		}
	}

	@Override
	public float f() {
		return 1.5F;
	}

	@Override
	public float h() {
		float float2 = aec.a(aom.b(this.a.cB())) + 1.0F;
		float float3 = Math.min(float2, 40.0F);
		return float3 / float2;
	}

	@Override
	public void d() {
		this.b = null;
	}

	@Nullable
	@Override
	public dem g() {
		return this.b;
	}

	@Override
	public bas<bal> i() {
		return bas.d;
	}
}
