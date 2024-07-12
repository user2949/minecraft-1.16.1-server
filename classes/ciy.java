import java.util.Random;

public class ciy extends Random {
	private int a;

	public ciy() {
	}

	public ciy(long long1) {
		super(long1);
	}

	public void a(int integer) {
		for (int integer3 = 0; integer3 < integer; integer3++) {
			this.next(1);
		}
	}

	protected int next(int integer) {
		this.a++;
		return super.next(integer);
	}

	public long a(int integer1, int integer2) {
		long long4 = (long)integer1 * 341873128712L + (long)integer2 * 132897987541L;
		this.setSeed(long4);
		return long4;
	}

	public long a(long long1, int integer2, int integer3) {
		this.setSeed(long1);
		long long6 = this.nextLong() | 1L;
		long long8 = this.nextLong() | 1L;
		long long10 = (long)integer2 * long6 + (long)integer3 * long8 ^ long1;
		this.setSeed(long10);
		return long10;
	}

	public long b(long long1, int integer2, int integer3) {
		long long6 = long1 + (long)integer2 + (long)(10000 * integer3);
		this.setSeed(long6);
		return long6;
	}

	public long c(long long1, int integer2, int integer3) {
		this.setSeed(long1);
		long long6 = this.nextLong();
		long long8 = this.nextLong();
		long long10 = (long)integer2 * long6 ^ (long)integer3 * long8 ^ long1;
		this.setSeed(long10);
		return long10;
	}

	public long a(long long1, int integer2, int integer3, int integer4) {
		long long7 = (long)integer2 * 341873128712L + (long)integer3 * 132897987541L + long1 + (long)integer4;
		this.setSeed(long7);
		return long7;
	}

	public static Random a(int integer1, int integer2, long long3, long long4) {
		return new Random(
			long3 + (long)(integer1 * integer1 * 4987142) + (long)(integer1 * 5947611) + (long)(integer2 * integer2) * 4392871L + (long)(integer2 * 389711) ^ long4
		);
	}
}
