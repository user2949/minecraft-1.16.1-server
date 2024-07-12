public final class g {
	public static g a = new g(-1.0F, 0.0F, 0.0F);
	public static g b = new g(1.0F, 0.0F, 0.0F);
	public static g c = new g(0.0F, -1.0F, 0.0F);
	public static g d = new g(0.0F, 1.0F, 0.0F);
	public static g e = new g(0.0F, 0.0F, -1.0F);
	public static g f = new g(0.0F, 0.0F, 1.0F);
	private float g;
	private float h;
	private float i;

	public g() {
	}

	public g(float float1, float float2, float float3) {
		this.g = float1;
		this.h = float2;
		this.i = float3;
	}

	public g(dem dem) {
		this((float)dem.b, (float)dem.c, (float)dem.d);
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			g g3 = (g)object;
			if (Float.compare(g3.g, this.g) != 0) {
				return false;
			} else {
				return Float.compare(g3.h, this.h) != 0 ? false : Float.compare(g3.i, this.i) == 0;
			}
		} else {
			return false;
		}
	}

	public int hashCode() {
		int integer2 = Float.floatToIntBits(this.g);
		integer2 = 31 * integer2 + Float.floatToIntBits(this.h);
		return 31 * integer2 + Float.floatToIntBits(this.i);
	}

	public float a() {
		return this.g;
	}

	public float b() {
		return this.h;
	}

	public float c() {
		return this.i;
	}

	public void a(float float1, float float2, float float3) {
		this.g = float1;
		this.h = float2;
		this.i = float3;
	}

	public void a(d d) {
		d d3 = new d(d);
		d3.a(new d(this.a(), this.b(), this.c(), 0.0F));
		d d4 = new d(d);
		d4.e();
		d3.a(d4);
		this.a(d3.a(), d3.b(), d3.c());
	}

	public String toString() {
		return "[" + this.g + ", " + this.h + ", " + this.i + "]";
	}
}
