public enum cfx implements aeh {
	HEAD("head"),
	FOOT("foot");

	private final String c;

	private cfx(String string3) {
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
