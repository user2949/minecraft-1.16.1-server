public final class d {
	public static final d a = new d(0.0F, 0.0F, 0.0F, 1.0F);
	private float b;
	private float c;
	private float d;
	private float e;

	public d(float float1, float float2, float float3, float float4) {
		this.b = float1;
		this.c = float2;
		this.d = float3;
		this.e = float4;
	}

	public d(g g, float float2, boolean boolean3) {
		if (boolean3) {
			float2 *= (float) (Math.PI / 180.0);
		}

		float float5 = c(float2 / 2.0F);
		this.b = g.a() * float5;
		this.c = g.b() * float5;
		this.d = g.c() * float5;
		this.e = b(float2 / 2.0F);
	}

	public d(d d) {
		this.b = d.b;
		this.c = d.c;
		this.d = d.d;
		this.e = d.e;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			d d3 = (d)object;
			if (Float.compare(d3.b, this.b) != 0) {
				return false;
			} else if (Float.compare(d3.c, this.c) != 0) {
				return false;
			} else {
				return Float.compare(d3.d, this.d) != 0 ? false : Float.compare(d3.e, this.e) == 0;
			}
		} else {
			return false;
		}
	}

	public int hashCode() {
		int integer2 = Float.floatToIntBits(this.b);
		integer2 = 31 * integer2 + Float.floatToIntBits(this.c);
		integer2 = 31 * integer2 + Float.floatToIntBits(this.d);
		return 31 * integer2 + Float.floatToIntBits(this.e);
	}

	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append("Quaternion[").append(this.d()).append(" + ");
		stringBuilder2.append(this.a()).append("i + ");
		stringBuilder2.append(this.b()).append("j + ");
		stringBuilder2.append(this.c()).append("k]");
		return stringBuilder2.toString();
	}

	public float a() {
		return this.b;
	}

	public float b() {
		return this.c;
	}

	public float c() {
		return this.d;
	}

	public float d() {
		return this.e;
	}

	public void a(d d) {
		float float3 = this.a();
		float float4 = this.b();
		float float5 = this.c();
		float float6 = this.d();
		float float7 = d.a();
		float float8 = d.b();
		float float9 = d.c();
		float float10 = d.d();
		this.b = float6 * float7 + float3 * float10 + float4 * float9 - float5 * float8;
		this.c = float6 * float8 - float3 * float9 + float4 * float10 + float5 * float7;
		this.d = float6 * float9 + float3 * float8 - float4 * float7 + float5 * float10;
		this.e = float6 * float10 - float3 * float7 - float4 * float8 - float5 * float9;
	}

	public void e() {
		this.b = -this.b;
		this.c = -this.c;
		this.d = -this.d;
	}

	private static float b(float float1) {
		return (float)Math.cos((double)float1);
	}

	private static float c(float float1) {
		return (float)Math.sin((double)float1);
	}
}
