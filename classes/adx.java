import java.util.Random;

public class adx {
	private final int a;
	private final int b;

	public adx(int integer1, int integer2) {
		if (integer2 < integer1) {
			throw new IllegalArgumentException("max must be >= minInclusive! Given minInclusive: " + integer1 + ", Given max: " + integer2);
		} else {
			this.a = integer1;
			this.b = integer2;
		}
	}

	public static adx a(int integer1, int integer2) {
		return new adx(integer1, integer2);
	}

	public int a(Random random) {
		return this.a == this.b ? this.a : random.nextInt(this.b - this.a + 1) + this.a;
	}

	public int a() {
		return this.a;
	}

	public int b() {
		return this.b;
	}

	public String toString() {
		return "IntRange[" + this.a + "-" + this.b + "]";
	}
}
