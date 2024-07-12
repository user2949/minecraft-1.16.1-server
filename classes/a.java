public final class a {
	private static final float j = 3.0F + 2.0F * (float)Math.sqrt(2.0);
	private static final float k = (float)Math.cos(Math.PI / 8);
	private static final float l = (float)Math.sin(Math.PI / 8);
	private static final float m = 1.0F / (float)Math.sqrt(2.0);
	protected float a;
	protected float b;
	protected float c;
	protected float d;
	protected float e;
	protected float f;
	protected float g;
	protected float h;
	protected float i;

	public a() {
	}

	public a(d d) {
		float float3 = d.a();
		float float4 = d.b();
		float float5 = d.c();
		float float6 = d.d();
		float float7 = 2.0F * float3 * float3;
		float float8 = 2.0F * float4 * float4;
		float float9 = 2.0F * float5 * float5;
		this.a = 1.0F - float8 - float9;
		this.e = 1.0F - float9 - float7;
		this.i = 1.0F - float7 - float8;
		float float10 = float3 * float4;
		float float11 = float4 * float5;
		float float12 = float5 * float3;
		float float13 = float3 * float6;
		float float14 = float4 * float6;
		float float15 = float5 * float6;
		this.d = 2.0F * (float10 + float15);
		this.b = 2.0F * (float10 - float15);
		this.g = 2.0F * (float12 - float14);
		this.c = 2.0F * (float12 + float14);
		this.h = 2.0F * (float11 + float13);
		this.f = 2.0F * (float11 - float13);
	}

	public a(b b) {
		this.a = b.a;
		this.b = b.b;
		this.c = b.c;
		this.d = b.e;
		this.e = b.f;
		this.f = b.g;
		this.g = b.i;
		this.h = b.j;
		this.i = b.k;
	}

	public a(a a) {
		this.a = a.a;
		this.b = a.b;
		this.c = a.c;
		this.d = a.d;
		this.e = a.e;
		this.f = a.f;
		this.g = a.g;
		this.h = a.h;
		this.i = a.i;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			a a3 = (a)object;
			return Float.compare(a3.a, this.a) == 0
				&& Float.compare(a3.b, this.b) == 0
				&& Float.compare(a3.c, this.c) == 0
				&& Float.compare(a3.d, this.d) == 0
				&& Float.compare(a3.e, this.e) == 0
				&& Float.compare(a3.f, this.f) == 0
				&& Float.compare(a3.g, this.g) == 0
				&& Float.compare(a3.h, this.h) == 0
				&& Float.compare(a3.i, this.i) == 0;
		} else {
			return false;
		}
	}

	public int hashCode() {
		int integer2 = this.a != 0.0F ? Float.floatToIntBits(this.a) : 0;
		integer2 = 31 * integer2 + (this.b != 0.0F ? Float.floatToIntBits(this.b) : 0);
		integer2 = 31 * integer2 + (this.c != 0.0F ? Float.floatToIntBits(this.c) : 0);
		integer2 = 31 * integer2 + (this.d != 0.0F ? Float.floatToIntBits(this.d) : 0);
		integer2 = 31 * integer2 + (this.e != 0.0F ? Float.floatToIntBits(this.e) : 0);
		integer2 = 31 * integer2 + (this.f != 0.0F ? Float.floatToIntBits(this.f) : 0);
		integer2 = 31 * integer2 + (this.g != 0.0F ? Float.floatToIntBits(this.g) : 0);
		integer2 = 31 * integer2 + (this.h != 0.0F ? Float.floatToIntBits(this.h) : 0);
		return 31 * integer2 + (this.i != 0.0F ? Float.floatToIntBits(this.i) : 0);
	}

	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append("Matrix3f:\n");
		stringBuilder2.append(this.a);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.b);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.c);
		stringBuilder2.append("\n");
		stringBuilder2.append(this.d);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.e);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.f);
		stringBuilder2.append("\n");
		stringBuilder2.append(this.g);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.h);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.i);
		stringBuilder2.append("\n");
		return stringBuilder2.toString();
	}

	public void a(int integer1, int integer2, float float3) {
		if (integer1 == 0) {
			if (integer2 == 0) {
				this.a = float3;
			} else if (integer2 == 1) {
				this.b = float3;
			} else {
				this.c = float3;
			}
		} else if (integer1 == 1) {
			if (integer2 == 0) {
				this.d = float3;
			} else if (integer2 == 1) {
				this.e = float3;
			} else {
				this.f = float3;
			}
		} else if (integer2 == 0) {
			this.g = float3;
		} else if (integer2 == 1) {
			this.h = float3;
		} else {
			this.i = float3;
		}
	}

	public void b(a a) {
		float float3 = this.a * a.a + this.b * a.d + this.c * a.g;
		float float4 = this.a * a.b + this.b * a.e + this.c * a.h;
		float float5 = this.a * a.c + this.b * a.f + this.c * a.i;
		float float6 = this.d * a.a + this.e * a.d + this.f * a.g;
		float float7 = this.d * a.b + this.e * a.e + this.f * a.h;
		float float8 = this.d * a.c + this.e * a.f + this.f * a.i;
		float float9 = this.g * a.a + this.h * a.d + this.i * a.g;
		float float10 = this.g * a.b + this.h * a.e + this.i * a.h;
		float float11 = this.g * a.c + this.h * a.f + this.i * a.i;
		this.a = float3;
		this.b = float4;
		this.c = float5;
		this.d = float6;
		this.e = float7;
		this.f = float8;
		this.g = float9;
		this.h = float10;
		this.i = float11;
	}
}
