import javax.annotation.Nullable;

public interface bbo extends bcf {
	void b(boolean boolean1);

	void a(aoy aoy, bki bki, bes bes, float float4);

	@Nullable
	aoy A();

	void V_();

	default void b(aoy aoy, float float2) {
		anf anf4 = bet.a(aoy, bkk.qP);
		bki bki5 = aoy.b(anf4);
		if (aoy.a(bkk.qP)) {
			biz.a(aoy.l, aoy, anf4, bki5, float2, (float)(14 - aoy.l.ac().a() * 4));
		}

		this.V_();
	}

	default void a(aoy aoy1, aoy aoy2, bes bes, float float4, float float5) {
		double double8 = aoy2.cC() - aoy1.cC();
		double double10 = aoy2.cG() - aoy1.cG();
		double double12 = (double)aec.a(double8 * double8 + double10 * double10);
		double double14 = aoy2.e(0.3333333333333333) - bes.cD() + double12 * 0.2F;
		g g16 = this.a(aoy1, new dem(double8, double14, double10), float4);
		bes.c((double)g16.a(), (double)g16.b(), (double)g16.c(), float5, (float)(14 - aoy1.l.ac().a() * 4));
		aoy1.a(acl.cz, 1.0F, 1.0F / (aoy1.cX().nextFloat() * 0.4F + 0.8F));
	}

	default g a(aoy aoy, dem dem, float float3) {
		dem dem5 = dem.d();
		dem dem6 = dem5.c(new dem(0.0, 1.0, 0.0));
		if (dem6.g() <= 1.0E-7) {
			dem6 = dem5.c(aoy.i(1.0F));
		}

		d d7 = new d(new g(dem6), 90.0F, true);
		g g8 = new g(dem5);
		g8.a(d7);
		d d9 = new d(g8, float3, true);
		g g10 = new g(dem5);
		g10.a(d9);
		return g10;
	}
}
