public enum cfy implements aeh {
	FLOOR("floor"),
	CEILING("ceiling"),
	SINGLE_WALL("single_wall"),
	DOUBLE_WALL("double_wall");

	private final String e;

	private cfy(String string3) {
		this.e = string3;
	}

	@Override
	public String a() {
		return this.e;
	}
}
