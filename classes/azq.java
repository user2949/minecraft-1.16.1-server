import java.util.Arrays;
import java.util.Comparator;

public enum azq {
	NONE(0),
	WHITE(1),
	WHITE_FIELD(2),
	WHITE_DOTS(3),
	BLACK_DOTS(4);

	private static final azq[] f = (azq[])Arrays.stream(values()).sorted(Comparator.comparingInt(azq::a)).toArray(azq[]::new);
	private final int g;

	private azq(int integer3) {
		this.g = integer3;
	}

	public int a() {
		return this.g;
	}

	public static azq a(int integer) {
		return f[integer % f.length];
	}
}
