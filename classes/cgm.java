public enum cgm implements aeh {
	NORTH_SOUTH("north_south"),
	EAST_WEST("east_west"),
	ASCENDING_EAST("ascending_east"),
	ASCENDING_WEST("ascending_west"),
	ASCENDING_NORTH("ascending_north"),
	ASCENDING_SOUTH("ascending_south"),
	SOUTH_EAST("south_east"),
	SOUTH_WEST("south_west"),
	NORTH_WEST("north_west"),
	NORTH_EAST("north_east");

	private final String k;

	private cgm(String string3) {
		this.k = string3;
	}

	public String toString() {
		return this.k;
	}

	public boolean c() {
		return this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST;
	}

	@Override
	public String a() {
		return this.k;
	}
}
