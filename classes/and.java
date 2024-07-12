import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.Nullable;

public enum and {
	PEACEFUL(0, "peaceful"),
	EASY(1, "easy"),
	NORMAL(2, "normal"),
	HARD(3, "hard");

	private static final and[] e = (and[])Arrays.stream(values()).sorted(Comparator.comparingInt(and::a)).toArray(and[]::new);
	private final int f;
	private final String g;

	private and(int integer3, String string4) {
		this.f = integer3;
		this.g = string4;
	}

	public int a() {
		return this.f;
	}

	public mr b() {
		return new ne("options.difficulty." + this.g);
	}

	public static and a(int integer) {
		return e[integer % e.length];
	}

	@Nullable
	public static and a(String string) {
		for (and and5 : values()) {
			if (and5.g.equals(string)) {
				return and5;
			}
		}

		return null;
	}

	public String c() {
		return this.g;
	}
}
