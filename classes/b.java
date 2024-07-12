public final class b {
	protected float a;
	protected float b;
	protected float c;
	protected float d;
	protected float e;
	protected float f;
	protected float g;
	protected float h;
	protected float i;
	protected float j;
	protected float k;
	protected float l;
	protected float m;
	protected float n;
	protected float o;
	protected float p;

	public b() {
	}

	public b(b b) {
		this.a = b.a;
		this.b = b.b;
		this.c = b.c;
		this.d = b.d;
		this.e = b.e;
		this.f = b.f;
		this.g = b.g;
		this.h = b.h;
		this.i = b.i;
		this.j = b.j;
		this.k = b.k;
		this.l = b.l;
		this.m = b.m;
		this.n = b.n;
		this.o = b.o;
		this.p = b.p;
	}

	public b(d d) {
		float float3 = d.a();
		float float4 = d.b();
		float float5 = d.c();
		float float6 = d.d();
		float float7 = 2.0F * float3 * float3;
		float float8 = 2.0F * float4 * float4;
		float float9 = 2.0F * float5 * float5;
		this.a = 1.0F - float8 - float9;
		this.f = 1.0F - float9 - float7;
		this.k = 1.0F - float7 - float8;
		this.p = 1.0F;
		float float10 = float3 * float4;
		float float11 = float4 * float5;
		float float12 = float5 * float3;
		float float13 = float3 * float6;
		float float14 = float4 * float6;
		float float15 = float5 * float6;
		this.e = 2.0F * (float10 + float15);
		this.b = 2.0F * (float10 - float15);
		this.i = 2.0F * (float12 - float14);
		this.c = 2.0F * (float12 + float14);
		this.j = 2.0F * (float11 + float13);
		this.g = 2.0F * (float11 - float13);
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			b b3 = (b)object;
			return Float.compare(b3.a, this.a) == 0
				&& Float.compare(b3.b, this.b) == 0
				&& Float.compare(b3.c, this.c) == 0
				&& Float.compare(b3.d, this.d) == 0
				&& Float.compare(b3.e, this.e) == 0
				&& Float.compare(b3.f, this.f) == 0
				&& Float.compare(b3.g, this.g) == 0
				&& Float.compare(b3.h, this.h) == 0
				&& Float.compare(b3.i, this.i) == 0
				&& Float.compare(b3.j, this.j) == 0
				&& Float.compare(b3.k, this.k) == 0
				&& Float.compare(b3.l, this.l) == 0
				&& Float.compare(b3.m, this.m) == 0
				&& Float.compare(b3.n, this.n) == 0
				&& Float.compare(b3.o, this.o) == 0
				&& Float.compare(b3.p, this.p) == 0;
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
		integer2 = 31 * integer2 + (this.i != 0.0F ? Float.floatToIntBits(this.i) : 0);
		integer2 = 31 * integer2 + (this.j != 0.0F ? Float.floatToIntBits(this.j) : 0);
		integer2 = 31 * integer2 + (this.k != 0.0F ? Float.floatToIntBits(this.k) : 0);
		integer2 = 31 * integer2 + (this.l != 0.0F ? Float.floatToIntBits(this.l) : 0);
		integer2 = 31 * integer2 + (this.m != 0.0F ? Float.floatToIntBits(this.m) : 0);
		integer2 = 31 * integer2 + (this.n != 0.0F ? Float.floatToIntBits(this.n) : 0);
		integer2 = 31 * integer2 + (this.o != 0.0F ? Float.floatToIntBits(this.o) : 0);
		return 31 * integer2 + (this.p != 0.0F ? Float.floatToIntBits(this.p) : 0);
	}

	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append("Matrix4f:\n");
		stringBuilder2.append(this.a);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.b);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.c);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.d);
		stringBuilder2.append("\n");
		stringBuilder2.append(this.e);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.f);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.g);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.h);
		stringBuilder2.append("\n");
		stringBuilder2.append(this.i);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.j);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.k);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.l);
		stringBuilder2.append("\n");
		stringBuilder2.append(this.m);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.n);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.o);
		stringBuilder2.append(" ");
		stringBuilder2.append(this.p);
		stringBuilder2.append("\n");
		return stringBuilder2.toString();
	}
}
