public enum cyc implements cys {
	INSTANCE;

	private static final int b = gl.as.a(brk.w);
	private static final int c = gl.as.a(brk.aw);

	@Override
	public int a(cxn cxn, int integer) {
		return cxn.a(10) == 0 && integer == b ? c : integer;
	}
}
