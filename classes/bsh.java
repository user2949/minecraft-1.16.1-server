import bre.f;
import com.google.common.collect.ImmutableList;

public final class bsh extends bre {
	protected static final cwf t = new cwf(new ciy(3456L), ImmutableList.of(-2, -1, 0));

	public bsh() {
		super(
			new bre.a()
				.a(cvw.ab, cvw.D)
				.a(bre.f.SNOW)
				.a(bre.b.OCEAN)
				.a(-1.0F)
				.b(0.1F)
				.c(0.0F)
				.d(0.5F)
				.a(new bri.a().b(3750089).c(329011).a(12638463).a(bqw.b).a())
				.a(null)
		);
		this.a(brf.m);
		brf.c(this);
		this.a(brf.D);
		brf.e(this);
		brf.f(this);
		brf.ap(this);
		brf.h(this);
		brf.aq(this);
		brf.i(this);
		brf.j(this);
		brf.n(this);
		brf.w(this);
		brf.W(this);
		brf.Y(this);
		brf.ab(this);
		brf.ac(this);
		brf.ao(this);
		brf.ar(this);
		this.a(apa.WATER_CREATURE, new bre.g(aoq.aC, 1, 1, 4));
		this.a(apa.WATER_AMBIENT, new bre.g(aoq.ap, 15, 1, 5));
		this.a(apa.CREATURE, new bre.g(aoq.ak, 1, 1, 2));
		this.a(apa.AMBIENT, new bre.g(aoq.d, 10, 8, 8));
		this.a(apa.MONSTER, new bre.g(aoq.aB, 100, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.aX, 95, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.q, 5, 1, 1));
		this.a(apa.MONSTER, new bre.g(aoq.aZ, 5, 1, 1));
		this.a(apa.MONSTER, new bre.g(aoq.au, 100, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.m, 100, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.aw, 100, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.u, 10, 1, 4));
		this.a(apa.MONSTER, new bre.g(aoq.aR, 5, 1, 1));
	}

	@Override
	protected float a(fu fu) {
		float float3 = this.p();
		double double4 = t.a((double)fu.u() * 0.05, (double)fu.w() * 0.05, false) * 7.0;
		double double6 = f.a((double)fu.u() * 0.2, (double)fu.w() * 0.2, false);
		double double8 = double4 + double6;
		if (double8 < 0.3) {
			double double10 = f.a((double)fu.u() * 0.09, (double)fu.w() * 0.09, false);
			if (double10 < 0.8) {
				float3 = 0.2F;
			}
		}

		if (fu.v() > 64) {
			float float10 = (float)(e.a((double)((float)fu.u() / 8.0F), (double)((float)fu.w() / 8.0F), false) * 4.0);
			return float3 - (float10 + (float)fu.v() - 64.0F) * 0.05F / 30.0F;
		} else {
			return float3;
		}
	}
}
