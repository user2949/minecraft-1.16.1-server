import java.lang.reflect.Constructor;
import java.util.Arrays;

public class bas<T extends bam> {
	private static bas<?>[] l = new bas[0];
	public static final bas<bai> a = a(bai.class, "HoldingPattern");
	public static final bas<baq> b = a(baq.class, "StrafePlayer");
	public static final bas<bak> c = a(bak.class, "LandingApproach");
	public static final bas<bal> d = a(bal.class, "Landing");
	public static final bas<bar> e = a(bar.class, "Takeoff");
	public static final bas<bao> f = a(bao.class, "SittingFlaming");
	public static final bas<bap> g = a(bap.class, "SittingScanning");
	public static final bas<ban> h = a(ban.class, "SittingAttacking");
	public static final bas<bag> i = a(bag.class, "ChargingPlayer");
	public static final bas<bah> j = a(bah.class, "Dying");
	public static final bas<baj> k = a(baj.class, "Hover");
	private final Class<? extends bam> m;
	private final int n;
	private final String o;

	private bas(int integer, Class<? extends bam> class2, String string) {
		this.n = integer;
		this.m = class2;
		this.o = string;
	}

	public bam a(bac bac) {
		try {
			Constructor<? extends bam> constructor3 = this.a();
			return (bam)constructor3.newInstance(bac);
		} catch (Exception var3) {
			throw new Error(var3);
		}
	}

	protected Constructor<? extends bam> a() throws NoSuchMethodException {
		return this.m.getConstructor(bac.class);
	}

	public int b() {
		return this.n;
	}

	public String toString() {
		return this.o + " (#" + this.n + ")";
	}

	public static bas<?> a(int integer) {
		return integer >= 0 && integer < l.length ? l[integer] : a;
	}

	public static int c() {
		return l.length;
	}

	private static <T extends bam> bas<T> a(Class<T> class1, String string) {
		bas<T> bas3 = new bas<>(l.length, class1, string);
		l = (bas<?>[])Arrays.copyOf(l, l.length + 1);
		l[bas3.b()] = bas3;
		return bas3;
	}
}
