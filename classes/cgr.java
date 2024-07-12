public enum cgr implements aeh {
	NONE("none"),
	LOW("low"),
	TALL("tall");

	private final String d;

	private cgr(String string3) {
		this.d = string3;
	}

	public String toString() {
		return this.a();
	}

	@Override
	public String a() {
		return this.d;
	}
}
