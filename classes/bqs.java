import java.util.Comparator;

public class bqs<T> {
	private static long d;
	private final T e;
	public final fu a;
	public final long b;
	public final bqt c;
	private final long f;

	public bqs(fu fu, T object) {
		this(fu, object, 0L, bqt.NORMAL);
	}

	public bqs(fu fu, T object, long long3, bqt bqt) {
		this.f = d++;
		this.a = fu.h();
		this.e = object;
		this.b = long3;
		this.c = bqt;
	}

	public boolean equals(Object object) {
		if (!(object instanceof bqs)) {
			return false;
		} else {
			bqs<?> bqs3 = (bqs<?>)object;
			return this.a.equals(bqs3.a) && this.e == bqs3.e;
		}
	}

	public int hashCode() {
		return this.a.hashCode();
	}

	public static <T> Comparator<bqs<T>> a() {
		return Comparator.comparingLong(bqs -> bqs.b).thenComparing(bqs -> bqs.c).thenComparingLong(bqs -> bqs.f);
	}

	public String toString() {
		return this.e + ": " + this.a + ", " + this.b + ", " + this.c + ", " + this.f;
	}

	public T b() {
		return this.e;
	}
}
