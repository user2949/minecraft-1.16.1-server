public interface aow {
	boolean P_();

	void a_(dem dem);

	float O_();

	default boolean a(aoz aoz, aov aov, dem dem) {
		if (!aoz.aU()) {
			return false;
		} else {
			aom aom5 = aoz.cm().isEmpty() ? null : (aom)aoz.cm().get(0);
			if (aoz.bo() && aoz.es() && aom5 instanceof bec) {
				aoz.p = aom5.p;
				aoz.r = aoz.p;
				aoz.q = aom5.q * 0.5F;
				aoz.a(aoz.p, aoz.q);
				aoz.aH = aoz.p;
				aoz.aJ = aoz.p;
				aoz.G = 1.0F;
				aoz.aL = aoz.dM() * 0.1F;
				if (aov.a && aov.b++ > aov.c) {
					aov.a = false;
				}

				if (aoz.cr()) {
					float float6 = this.O_();
					if (aov.a) {
						float6 += float6 * 1.15F * aec.a((float)aov.b / (float)aov.c * (float) Math.PI);
					}

					aoz.n(float6);
					this.a_(new dem(0.0, 0.0, 1.0));
					aoz.bb = 0;
				} else {
					aoz.a(aoz, false);
					aoz.e(dem.a);
				}

				return true;
			} else {
				aoz.G = 0.5F;
				aoz.aL = 0.02F;
				this.a_(dem);
				return false;
			}
		}
	}
}
