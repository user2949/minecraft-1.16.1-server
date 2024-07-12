public class gn {
	protected final float a;
	protected final float b;
	protected final float c;

	public gn(float float1, float float2, float float3) {
		this.a = !Float.isInfinite(float1) && !Float.isNaN(float1) ? float1 % 360.0F : 0.0F;
		this.b = !Float.isInfinite(float2) && !Float.isNaN(float2) ? float2 % 360.0F : 0.0F;
		this.c = !Float.isInfinite(float3) && !Float.isNaN(float3) ? float3 % 360.0F : 0.0F;
	}

	public gn(lk lk) {
		this(lk.i(0), lk.i(1), lk.i(2));
	}

	public lk a() {
		lk lk2 = new lk();
		lk2.add(lh.a(this.a));
		lk2.add(lh.a(this.b));
		lk2.add(lh.a(this.c));
		return lk2;
	}

	public boolean equals(Object object) {
		if (!(object instanceof gn)) {
			return false;
		} else {
			gn gn3 = (gn)object;
			return this.a == gn3.a && this.b == gn3.b && this.c == gn3.c;
		}
	}

	public float b() {
		return this.a;
	}

	public float c() {
		return this.b;
	}

	public float d() {
		return this.c;
	}
}
