import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public final class bet {
	public static dej a(aom aom, Predicate<aom> predicate, bpj.a a) {
		dem dem4 = aom.cB();
		bqb bqb5 = aom.l;
		dem dem6 = aom.cz();
		dem dem7 = dem6.e(dem4);
		dej dej8 = bqb5.a(new bpj(dem6, dem7, a, bpj.b.NONE, aom));
		if (dej8.c() != dej.a.MISS) {
			dem7 = dej8.e();
		}

		dej dej9 = a(bqb5, aom, dem6, dem7, aom.cb().b(aom.cB()).g(1.0), predicate);
		if (dej9 != null) {
			dej8 = dej9;
		}

		return dej8;
	}

	@Nullable
	public static dei a(bqb bqb, aom aom, dem dem3, dem dem4, deg deg, Predicate<aom> predicate) {
		double double7 = Double.MAX_VALUE;
		aom aom9 = null;

		for (aom aom11 : bqb.a(aom, deg, predicate)) {
			deg deg12 = aom11.cb().g(0.3F);
			Optional<dem> optional13 = deg12.b(dem3, dem4);
			if (optional13.isPresent()) {
				double double14 = dem3.g((dem)optional13.get());
				if (double14 < double7) {
					aom9 = aom11;
					double7 = double14;
				}
			}
		}

		return aom9 == null ? null : new dei(aom9);
	}

	public static final void a(aom aom, float float2) {
		dem dem3 = aom.cB();
		if (dem3.g() != 0.0) {
			float float4 = aec.a(aom.b(dem3));
			aom.p = (float)(aec.d(dem3.d, dem3.b) * 180.0F / (float)Math.PI) + 90.0F;
			aom.q = (float)(aec.d((double)float4, dem3.c) * 180.0F / (float)Math.PI) - 90.0F;

			while (aom.q - aom.s < -180.0F) {
				aom.s -= 360.0F;
			}

			while (aom.q - aom.s >= 180.0F) {
				aom.s += 360.0F;
			}

			while (aom.p - aom.r < -180.0F) {
				aom.r -= 360.0F;
			}

			while (aom.p - aom.r >= 180.0F) {
				aom.r += 360.0F;
			}

			aom.q = aec.g(float2, aom.s, aom.q);
			aom.p = aec.g(float2, aom.r, aom.p);
		}
	}

	public static anf a(aoy aoy, bke bke) {
		return aoy.dC().b() == bke ? anf.MAIN_HAND : anf.OFF_HAND;
	}

	public static beg a(aoy aoy, bki bki, float float3) {
		bih bih4 = (bih)(bki.b() instanceof bih ? bki.b() : bkk.kg);
		beg beg5 = bih4.a(aoy.l, bki, aoy);
		beg5.a(aoy, float3);
		if (bki.b() == bkk.qk && beg5 instanceof bei) {
			((bei)beg5).b(bki);
		}

		return beg5;
	}
}
