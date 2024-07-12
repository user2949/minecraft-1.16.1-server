public enum bqt {
	EXTREMELY_HIGH(-3),
	VERY_HIGH(-2),
	HIGH(-1),
	NORMAL(0),
	LOW(1),
	VERY_LOW(2),
	EXTREMELY_LOW(3);

	private final int h;

	private bqt(int integer3) {
		this.h = integer3;
	}

	public static bqt a(int integer) {
		for (bqt bqt5 : values()) {
			if (bqt5.h == integer) {
				return bqt5;
			}
		}

		return integer < EXTREMELY_HIGH.h ? EXTREMELY_HIGH : EXTREMELY_LOW;
	}

	public int a() {
		return this.h;
	}
}
