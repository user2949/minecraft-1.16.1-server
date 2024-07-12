public enum cgf implements aeh {
	UPPER,
	LOWER;

	public String toString() {
		return this.a();
	}

	@Override
	public String a() {
		return this == UPPER ? "upper" : "lower";
	}
}
