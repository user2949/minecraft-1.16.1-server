import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.Random;

public class cxo implements cxm<cxk> {
	private final Long2IntLinkedOpenHashMap a;
	private final int b;
	private final cwc c;
	private final long d;
	private long e;

	public cxo(int integer, long long2, long long3) {
		this.d = b(long2, long3);
		this.c = new cwc(new Random(long2));
		this.a = new Long2IntLinkedOpenHashMap(16, 0.25F);
		this.a.defaultReturnValue(Integer.MIN_VALUE);
		this.b = integer;
	}

	public cxk a(cyx cyx) {
		return new cxk(this.a, this.b, cyx);
	}

	public cxk a(cyx cyx, cxk cxk) {
		return new cxk(this.a, Math.min(1024, cxk.a() * 4), cyx);
	}

	public cxk a(cyx cyx, cxk cxk2, cxk cxk3) {
		return new cxk(this.a, Math.min(1024, Math.max(cxk2.a(), cxk3.a()) * 4), cyx);
	}

	@Override
	public void a(long long1, long long2) {
		long long6 = this.d;
		long6 = aea.a(long6, long1);
		long6 = aea.a(long6, long2);
		long6 = aea.a(long6, long1);
		long6 = aea.a(long6, long2);
		this.e = long6;
	}

	@Override
	public int a(int integer) {
		int integer3 = (int)Math.floorMod(this.e >> 24, (long)integer);
		this.e = aea.a(this.e, this.d);
		return integer3;
	}

	@Override
	public cwc b() {
		return this.c;
	}

	private static long b(long long1, long long2) {
		long long5 = aea.a(long2, long2);
		long5 = aea.a(long5, long2);
		long5 = aea.a(long5, long2);
		long long7 = aea.a(long1, long5);
		long7 = aea.a(long7, long5);
		return aea.a(long7, long5);
	}
}
