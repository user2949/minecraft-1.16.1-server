public enum cfw implements aeh {
	NONE("none"),
	SMALL("small"),
	LARGE("large");

	private final String d;

	private cfw(String string3) {
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
