import java.util.Comparator;

public class zi<T> {
	private final String i;
	private final Comparator<T> j;
	private final long k;
	public static final zi<ael> a = a("start", (ael1, ael2) -> 0);
	public static final zi<ael> b = a("dragon", (ael1, ael2) -> 0);
	public static final zi<bph> c = a("player", Comparator.comparingLong(bph::a));
	public static final zi<bph> d = a("forced", Comparator.comparingLong(bph::a));
	public static final zi<bph> e = a("light", Comparator.comparingLong(bph::a));
	public static final zi<fu> f = a("portal", gr::i, 300);
	public static final zi<Integer> g = a("post_teleport", Integer::compareTo, 5);
	public static final zi<bph> h = a("unknown", Comparator.comparingLong(bph::a), 1);

	public static <T> zi<T> a(String string, Comparator<T> comparator) {
		return new zi<>(string, comparator, 0L);
	}

	public static <T> zi<T> a(String string, Comparator<T> comparator, int integer) {
		return new zi<>(string, comparator, (long)integer);
	}

	protected zi(String string, Comparator<T> comparator, long long3) {
		this.i = string;
		this.j = comparator;
		this.k = long3;
	}

	public String toString() {
		return this.i;
	}

	public Comparator<T> a() {
		return this.j;
	}

	public long b() {
		return this.k;
	}
}
