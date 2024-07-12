public enum cxv implements cyt {
	INSTANCE;

	private static final int b = gl.as.a(brk.d);
	private static final int c = gl.as.a(brk.e);
	private static final int d = gl.as.a(brk.J);
	private static final int e = gl.as.a(brk.n);
	private static final int f = gl.as.a(brk.w);
	private static final int g = gl.as.a(brk.aw);
	private static final int h = gl.as.a(brk.y);
	private static final int i = gl.as.a(brk.M);
	private static final int j = gl.as.a(brk.O);
	private static final int k = gl.as.a(brk.N);
	private static final int l = gl.as.a(brk.c);
	private static final int m = gl.as.a(brk.H);
	private static final int n = gl.as.a(brk.v);
	private static final int o = gl.as.a(brk.h);
	private static final int p = gl.as.a(brk.g);
	private static final int q = gl.as.a(brk.F);

	@Override
	public int a(cxn cxn, int integer2, int integer3, int integer4, int integer5, int integer6) {
		int[] arr8 = new int[1];
		if (!this.a(arr8, integer2, integer3, integer4, integer5, integer6, c, n)
			&& !this.b(arr8, integer2, integer3, integer4, integer5, integer6, k, i)
			&& !this.b(arr8, integer2, integer3, integer4, integer5, integer6, j, i)
			&& !this.b(arr8, integer2, integer3, integer4, integer5, integer6, m, p)) {
			if (integer6 != b || integer2 != e && integer3 != e && integer5 != e && integer4 != e) {
				if (integer6 == o) {
					if (integer2 == b
						|| integer3 == b
						|| integer5 == b
						|| integer4 == b
						|| integer2 == q
						|| integer3 == q
						|| integer5 == q
						|| integer4 == q
						|| integer2 == e
						|| integer3 == e
						|| integer5 == e
						|| integer4 == e) {
						return l;
					}

					if (integer2 == f || integer4 == f || integer3 == f || integer5 == f || integer2 == g || integer4 == g || integer3 == g || integer5 == g) {
						return h;
					}
				}

				return integer6;
			} else {
				return d;
			}
		} else {
			return arr8[0];
		}
	}

	private boolean a(int[] arr, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8) {
		if (!cxz.a(integer6, integer7)) {
			return false;
		} else {
			if (this.a(integer2, integer7) && this.a(integer3, integer7) && this.a(integer5, integer7) && this.a(integer4, integer7)) {
				arr[0] = integer6;
			} else {
				arr[0] = integer8;
			}

			return true;
		}
	}

	private boolean b(int[] arr, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8) {
		if (integer6 != integer7) {
			return false;
		} else {
			if (cxz.a(integer2, integer7) && cxz.a(integer3, integer7) && cxz.a(integer5, integer7) && cxz.a(integer4, integer7)) {
				arr[0] = integer6;
			} else {
				arr[0] = integer8;
			}

			return true;
		}
	}

	private boolean a(int integer1, int integer2) {
		if (cxz.a(integer1, integer2)) {
			return true;
		} else {
			bre bre4 = gl.as.a(integer1);
			bre bre5 = gl.as.a(integer2);
			if (bre4 != null && bre5 != null) {
				bre.c c6 = bre4.j();
				bre.c c7 = bre5.j();
				return c6 == c7 || c6 == bre.c.MEDIUM || c7 == bre.c.MEDIUM;
			} else {
				return false;
			}
		}
	}
}
