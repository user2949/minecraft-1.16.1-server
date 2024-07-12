public enum cgb implements aeh {
	SINGLE("single", 0),
	LEFT("left", 2),
	RIGHT("right", 1);

	public static final cgb[] d = values();
	private final String e;
	private final int f;

	private cgb(String string3, int integer4) {
		this.e = string3;
		this.f = integer4;
	}

	@Override
	public String a() {
		return this.e;
	}

	public cgb b() {
		return d[this.f];
	}
}
