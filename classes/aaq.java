public enum aaq {
	TOO_OLD("old"),
	TOO_NEW("new"),
	COMPATIBLE("compatible");

	private final mr d;
	private final mr e;

	private aaq(String string3) {
		this.d = new ne("pack.incompatible." + string3);
		this.e = new ne("pack.incompatible.confirm." + string3);
	}

	public boolean a() {
		return this == COMPATIBLE;
	}

	public static aaq a(int integer) {
		if (integer < u.a().getPackVersion()) {
			return TOO_OLD;
		} else {
			return integer > u.a().getPackVersion() ? TOO_NEW : COMPATIBLE;
		}
	}
}
