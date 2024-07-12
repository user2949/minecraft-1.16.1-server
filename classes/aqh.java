import java.util.Map;
import java.util.Map.Entry;

public abstract class aqh<E extends aoy> {
	protected final Map<awp<?>, awq> a;
	private aqh.a b = aqh.a.STOPPED;
	private long c;
	private final int d;
	private final int e;

	public aqh(Map<awp<?>, awq> map) {
		this(map, 60);
	}

	public aqh(Map<awp<?>, awq> map, int integer) {
		this(map, integer, integer);
	}

	public aqh(Map<awp<?>, awq> map, int integer2, int integer3) {
		this.d = integer2;
		this.e = integer3;
		this.a = map;
	}

	public aqh.a a() {
		return this.b;
	}

	public final boolean e(zd zd, E aoy, long long3) {
		if (this.a(aoy) && this.a(zd, aoy)) {
			this.b = aqh.a.RUNNING;
			int integer6 = this.d + zd.v_().nextInt(this.e + 1 - this.d);
			this.c = long3 + (long)integer6;
			this.a(zd, aoy, long3);
			return true;
		} else {
			return false;
		}
	}

	protected void a(zd zd, E aoy, long long3) {
	}

	public final void f(zd zd, E aoy, long long3) {
		if (!this.a(long3) && this.b(zd, aoy, long3)) {
			this.d(zd, aoy, long3);
		} else {
			this.g(zd, aoy, long3);
		}
	}

	protected void d(zd zd, E aoy, long long3) {
	}

	public final void g(zd zd, E aoy, long long3) {
		this.b = aqh.a.STOPPED;
		this.c(zd, aoy, long3);
	}

	protected void c(zd zd, E aoy, long long3) {
	}

	protected boolean b(zd zd, E aoy, long long3) {
		return false;
	}

	protected boolean a(long long1) {
		return long1 > this.c;
	}

	protected boolean a(zd zd, E aoy) {
		return true;
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}

	private boolean a(E aoy) {
		for (Entry<awp<?>, awq> entry4 : this.a.entrySet()) {
			awp<?> awp5 = (awp<?>)entry4.getKey();
			awq awq6 = (awq)entry4.getValue();
			if (!aoy.cI().a(awp5, awq6)) {
				return false;
			}
		}

		return true;
	}

	public static enum a {
		STOPPED,
		RUNNING;
	}
}
