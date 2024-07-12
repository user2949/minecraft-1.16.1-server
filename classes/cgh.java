public enum cgh implements aeh {
	TOP("top"),
	BOTTOM("bottom");

	private final String c;

	private cgh(String string3) {
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
