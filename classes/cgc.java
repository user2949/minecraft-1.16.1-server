public enum cgc implements aeh {
	COMPARE("compare"),
	SUBTRACT("subtract");

	private final String c;

	private cgc(String string3) {
		this.c = string3;
	}

	public String toString() {
		return this.c;
	}

	@Override
	public String a() {
		return this.c;
	}
}
