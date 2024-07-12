public enum cgn implements aeh {
	UP("up"),
	SIDE("side"),
	NONE("none");

	private final String d;

	private cgn(String string3) {
		this.d = string3;
	}

	public String toString() {
		return this.a();
	}

	@Override
	public String a() {
		return this.d;
	}

	public boolean b() {
		return this != NONE;
	}
}
