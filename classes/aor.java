public enum aor {
	MAINHAND(aor.a.HAND, 0, 0, "mainhand"),
	OFFHAND(aor.a.HAND, 1, 5, "offhand"),
	FEET(aor.a.ARMOR, 0, 1, "feet"),
	LEGS(aor.a.ARMOR, 1, 2, "legs"),
	CHEST(aor.a.ARMOR, 2, 3, "chest"),
	HEAD(aor.a.ARMOR, 3, 4, "head");

	private final aor.a g;
	private final int h;
	private final int i;
	private final String j;

	private aor(aor.a a, int integer4, int integer5, String string6) {
		this.g = a;
		this.h = integer4;
		this.i = integer5;
		this.j = string6;
	}

	public aor.a a() {
		return this.g;
	}

	public int b() {
		return this.h;
	}

	public int c() {
		return this.i;
	}

	public String d() {
		return this.j;
	}

	public static aor a(String string) {
		for (aor aor5 : values()) {
			if (aor5.d().equals(string)) {
				return aor5;
			}
		}

		throw new IllegalArgumentException("Invalid slot '" + string + "'");
	}

	public static aor a(aor.a a, int integer) {
		for (aor aor6 : values()) {
			if (aor6.a() == a && aor6.b() == integer) {
				return aor6;
			}
		}

		throw new IllegalArgumentException("Invalid slot '" + a + "': " + integer);
	}

	public static enum a {
		HAND,
		ARMOR;
	}
}
