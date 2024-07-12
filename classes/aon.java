public class aon {
	public final float a;
	public final float b;
	public final boolean c;

	public aon(float float1, float float2, boolean boolean3) {
		this.a = float1;
		this.b = float2;
		this.c = boolean3;
	}

	public aon a(float float1) {
		return this.a(float1, float1);
	}

	public aon a(float float1, float float2) {
		return !this.c && (float1 != 1.0F || float2 != 1.0F) ? b(this.a * float1, this.b * float2) : this;
	}

	public static aon b(float float1, float float2) {
		return new aon(float1, float2, false);
	}

	public static aon c(float float1, float float2) {
		return new aon(float1, float2, true);
	}

	public String toString() {
		return "EntityDimensions w=" + this.a + ", h=" + this.b + ", fixed=" + this.c;
	}
}
