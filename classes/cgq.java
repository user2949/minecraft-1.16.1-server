public enum cgq implements aeh {
	SAVE("save"),
	LOAD("load"),
	CORNER("corner"),
	DATA("data");

	private final String e;

	private cgq(String string3) {
		this.e = string3;
	}

	@Override
	public String a() {
		return this.e;
	}
}
