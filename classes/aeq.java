import org.apache.commons.lang3.Validate;

public class aeq {
	private final long[] a;
	private final int b;
	private final long c;
	private final int d;

	public aeq(int integer1, int integer2) {
		this(integer1, integer2, new long[aec.c(integer2 * integer1, 64) / 64]);
	}

	public aeq(int integer1, int integer2, long[] arr) {
		Validate.inclusiveBetween(1L, 32L, (long)integer1);
		this.d = integer2;
		this.b = integer1;
		this.a = arr;
		this.c = (1L << integer1) - 1L;
		int integer5 = aec.c(integer2 * integer1, 64) / 64;
		if (arr.length != integer5) {
			throw new IllegalArgumentException("Invalid length given for storage, got: " + arr.length + " but expected: " + integer5);
		}
	}

	public void a(int integer1, int integer2) {
		Validate.inclusiveBetween(0L, (long)(this.d - 1), (long)integer1);
		Validate.inclusiveBetween(0L, this.c, (long)integer2);
		int integer4 = integer1 * this.b;
		int integer5 = integer4 >> 6;
		int integer6 = (integer1 + 1) * this.b - 1 >> 6;
		int integer7 = integer4 ^ integer5 << 6;
		this.a[integer5] = this.a[integer5] & ~(this.c << integer7) | ((long)integer2 & this.c) << integer7;
		if (integer5 != integer6) {
			int integer8 = 64 - integer7;
			int integer9 = this.b - integer8;
			this.a[integer6] = this.a[integer6] >>> integer9 << integer9 | ((long)integer2 & this.c) >> integer8;
		}
	}

	public int a(int integer) {
		Validate.inclusiveBetween(0L, (long)(this.d - 1), (long)integer);
		int integer3 = integer * this.b;
		int integer4 = integer3 >> 6;
		int integer5 = (integer + 1) * this.b - 1 >> 6;
		int integer6 = integer3 ^ integer4 << 6;
		if (integer4 == integer5) {
			return (int)(this.a[integer4] >>> integer6 & this.c);
		} else {
			int integer7 = 64 - integer6;
			return (int)((this.a[integer4] >>> integer6 | this.a[integer5] << integer7) & this.c);
		}
	}

	public long[] a() {
		return this.a;
	}

	public int b() {
		return this.b;
	}
}
