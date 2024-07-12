import java.util.Arrays;
import java.util.Comparator;

public enum azv {
	WHITE(0),
	CREAMY(1),
	CHESTNUT(2),
	BROWN(3),
	BLACK(4),
	GRAY(5),
	DARKBROWN(6);

	private static final azv[] h = (azv[])Arrays.stream(values()).sorted(Comparator.comparingInt(azv::a)).toArray(azv[]::new);
	private final int i;

	private azv(int integer3) {
		this.i = integer3;
	}

	public int a() {
		return this.i;
	}

	public static azv a(int integer) {
		return h[integer % h.length];
	}
}
