public enum ang {
	SUCCESS,
	CONSUME,
	PASS,
	FAIL;

	public boolean a() {
		return this == SUCCESS || this == CONSUME;
	}

	public boolean b() {
		return this == SUCCESS;
	}

	public static ang a(boolean boolean1) {
		return boolean1 ? SUCCESS : CONSUME;
	}
}
