public enum cgk implements aeh {
	DEFAULT("normal"),
	STICKY("sticky");

	private final String c;

	private cgk(String string3) {
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
