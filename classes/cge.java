public enum cge implements aeh {
	LEFT,
	RIGHT;

	public String toString() {
		return this.a();
	}

	@Override
	public String a() {
		return this == LEFT ? "left" : "right";
	}
}
