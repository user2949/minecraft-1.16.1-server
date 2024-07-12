public class czd {
	public final int a;
	public final int b;
	public final int c;
	private final int m;
	public int d = -1;
	public float e;
	public float f;
	public float g;
	public czd h;
	public boolean i;
	public float j;
	public float k;
	public czb l = czb.BLOCKED;

	public czd(int integer1, int integer2, int integer3) {
		this.a = integer1;
		this.b = integer2;
		this.c = integer3;
		this.m = b(integer1, integer2, integer3);
	}

	public czd a(int integer1, int integer2, int integer3) {
		czd czd5 = new czd(integer1, integer2, integer3);
		czd5.d = this.d;
		czd5.e = this.e;
		czd5.f = this.f;
		czd5.g = this.g;
		czd5.h = this.h;
		czd5.i = this.i;
		czd5.j = this.j;
		czd5.k = this.k;
		czd5.l = this.l;
		return czd5;
	}

	public static int b(int integer1, int integer2, int integer3) {
		return integer2 & 0xFF | (integer1 & 32767) << 8 | (integer3 & 32767) << 24 | (integer1 < 0 ? Integer.MIN_VALUE : 0) | (integer3 < 0 ? 32768 : 0);
	}

	public float a(czd czd) {
		float float3 = (float)(czd.a - this.a);
		float float4 = (float)(czd.b - this.b);
		float float5 = (float)(czd.c - this.c);
		return aec.c(float3 * float3 + float4 * float4 + float5 * float5);
	}

	public float b(czd czd) {
		float float3 = (float)(czd.a - this.a);
		float float4 = (float)(czd.b - this.b);
		float float5 = (float)(czd.c - this.c);
		return float3 * float3 + float4 * float4 + float5 * float5;
	}

	public float c(czd czd) {
		float float3 = (float)Math.abs(czd.a - this.a);
		float float4 = (float)Math.abs(czd.b - this.b);
		float float5 = (float)Math.abs(czd.c - this.c);
		return float3 + float4 + float5;
	}

	public float c(fu fu) {
		float float3 = (float)Math.abs(fu.u() - this.a);
		float float4 = (float)Math.abs(fu.v() - this.b);
		float float5 = (float)Math.abs(fu.w() - this.c);
		return float3 + float4 + float5;
	}

	public fu a() {
		return new fu(this.a, this.b, this.c);
	}

	public boolean equals(Object object) {
		if (!(object instanceof czd)) {
			return false;
		} else {
			czd czd3 = (czd)object;
			return this.m == czd3.m && this.a == czd3.a && this.b == czd3.b && this.c == czd3.c;
		}
	}

	public int hashCode() {
		return this.m;
	}

	public boolean c() {
		return this.d >= 0;
	}

	public String toString() {
		return "Node{x=" + this.a + ", y=" + this.b + ", z=" + this.c + '}';
	}
}
