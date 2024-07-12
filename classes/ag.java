public enum ag {
	TASK("task", 0, i.GREEN),
	CHALLENGE("challenge", 26, i.DARK_PURPLE),
	GOAL("goal", 52, i.GREEN);

	private final String d;
	private final int e;
	private final i f;

	private ag(String string3, int integer4, i i) {
		this.d = string3;
		this.e = integer4;
		this.f = i;
	}

	public String a() {
		return this.d;
	}

	public static ag a(String string) {
		for (ag ag5 : values()) {
			if (ag5.d.equals(string)) {
				return ag5;
			}
		}

		throw new IllegalArgumentException("Unknown frame type '" + string + "'");
	}

	public i c() {
		return this.f;
	}
}
