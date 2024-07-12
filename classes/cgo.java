public enum cgo implements aeh {
	TOP("top"),
	BOTTOM("bottom"),
	DOUBLE("double");

	private final String d;

	private cgo(String string3) {
		this.d = string3;
	}

	public String toString() {
		return this.d;
	}

	@Override
	public String a() {
		return this.d;
	}
}
