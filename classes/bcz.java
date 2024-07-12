public interface bcz {
	static boolean a(aoy aoy1, aoy aoy2) {
		float float4 = (float)aoy1.b(apx.f);
		float float3;
		if (!aoy1.x_() && (int)float4 > 0) {
			float3 = float4 / 2.0F + (float)aoy1.l.t.nextInt((int)float4);
		} else {
			float3 = float4;
		}

		boolean boolean5 = aoy2.a(anw.c(aoy1), float3);
		if (boolean5) {
			aoy1.a(aoy1, aoy2);
			if (!aoy1.x_()) {
				b(aoy1, aoy2);
			}
		}

		return boolean5;
	}

	static void b(aoy aoy1, aoy aoy2) {
		double double3 = aoy1.b(apx.g);
		double double5 = aoy2.b(apx.c);
		double double7 = double3 - double5;
		if (!(double7 <= 0.0)) {
			double double9 = aoy2.cC() - aoy1.cC();
			double double11 = aoy2.cG() - aoy1.cG();
			float float13 = (float)(aoy1.l.t.nextInt(21) - 10);
			double double14 = double7 * (double)(aoy1.l.t.nextFloat() * 0.5F + 0.2F);
			dem dem16 = new dem(double9, 0.0, double11).d().a(double14).b(float13);
			double double17 = double7 * (double)aoy1.l.t.nextFloat() * 0.5;
			aoy2.h(dem16.b, double17, dem16.d);
			aoy2.w = true;
		}
	}
}
