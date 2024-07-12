public enum cyi implements cyp, cyu {
	INSTANCE;

	private static final int b = gl.as.a(brk.m);
	private static final int c = gl.as.a(brk.n);
	private static final int d = gl.as.a(brk.p);
	private static final int e = gl.as.a(brk.q);
	private static final int f = gl.as.a(brk.i);

	@Override
	public int a(cxn cxn, cxi cxi2, cxi cxi3, int integer4, int integer5) {
		int integer7 = cxi2.a(this.a(integer4), this.b(integer5));
		int integer8 = cxi3.a(this.a(integer4), this.b(integer5));
		if (cxz.a(integer7)) {
			return integer7;
		} else if (integer8 == f) {
			if (integer7 == c) {
				return b;
			} else {
				return integer7 != d && integer7 != e ? integer8 & 0xFF : e;
			}
		} else {
			return integer7;
		}
	}
}
