public enum cyh implements cyt {
	INSTANCE;

	public static final int b = gl.as.a(brk.i);

	@Override
	public int a(cxn cxn, int integer2, int integer3, int integer4, int integer5, int integer6) {
		int integer8 = c(integer6);
		return integer8 == c(integer5) && integer8 == c(integer2) && integer8 == c(integer3) && integer8 == c(integer4) ? -1 : b;
	}

	private static int c(int integer) {
		return integer >= 2 ? 2 + (integer & 1) : integer;
	}
}
