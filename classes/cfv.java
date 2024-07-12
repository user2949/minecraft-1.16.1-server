public enum cfv implements aeh {
	FLOOR("floor"),
	WALL("wall"),
	CEILING("ceiling");

	private final String d;

	private cfv(String string3) {
		this.d = string3;
	}

	@Override
	public String a() {
		return this.d;
	}
}
