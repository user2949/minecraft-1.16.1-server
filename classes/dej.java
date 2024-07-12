public abstract class dej {
	protected final dem a;

	protected dej(dem dem) {
		this.a = dem;
	}

	public double a(aom aom) {
		double double3 = this.a.b - aom.cC();
		double double5 = this.a.c - aom.cD();
		double double7 = this.a.d - aom.cG();
		return double3 * double3 + double5 * double5 + double7 * double7;
	}

	public abstract dej.a c();

	public dem e() {
		return this.a;
	}

	public static enum a {
		MISS,
		BLOCK,
		ENTITY;
	}
}
