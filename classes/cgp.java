public enum cgp implements aeh {
	STRAIGHT("straight"),
	INNER_LEFT("inner_left"),
	INNER_RIGHT("inner_right"),
	OUTER_LEFT("outer_left"),
	OUTER_RIGHT("outer_right");

	private final String f;

	private cgp(String string3) {
		this.f = string3;
	}

	public String toString() {
		return this.f;
	}

	@Override
	public String a() {
		return this.f;
	}
}
