import bre.f;

public enum cyj implements cyt {
	INSTANCE;

	private static final int b = gl.as.a(brk.r);
	private static final int c = gl.as.a(brk.B);
	private static final int d = gl.as.a(brk.d);
	private static final int e = gl.as.a(brk.e);
	private static final int f = gl.as.a(brk.J);
	private static final int g = gl.as.a(brk.f);
	private static final int h = gl.as.a(brk.w);
	private static final int i = gl.as.a(brk.y);
	private static final int j = gl.as.a(brk.x);
	private static final int k = gl.as.a(brk.M);
	private static final int l = gl.as.a(brk.N);
	private static final int m = gl.as.a(brk.O);
	private static final int n = gl.as.a(brk.at);
	private static final int o = gl.as.a(brk.au);
	private static final int p = gl.as.a(brk.av);
	private static final int q = gl.as.a(brk.p);
	private static final int r = gl.as.a(brk.q);
	private static final int s = gl.as.a(brk.i);
	private static final int t = gl.as.a(brk.v);
	private static final int u = gl.as.a(brk.A);
	private static final int v = gl.as.a(brk.h);
	private static final int w = gl.as.a(brk.g);

	@Override
	public int a(cxn cxn, int integer2, int integer3, int integer4, int integer5, int integer6) {
		bre bre8 = gl.as.a(integer6);
		if (integer6 == q) {
			if (cxz.b(integer2) || cxz.b(integer3) || cxz.b(integer4) || cxz.b(integer5)) {
				return r;
			}
		} else if (bre8 != null && bre8.y() == bre.b.JUNGLE) {
			if (!c(integer2) || !c(integer3) || !c(integer4) || !c(integer5)) {
				return i;
			}

			if (cxz.a(integer2) || cxz.a(integer3) || cxz.a(integer4) || cxz.a(integer5)) {
				return b;
			}
		} else if (integer6 != e && integer6 != f && integer6 != t) {
			if (bre8 != null && bre8.d() == bre.f.SNOW) {
				if (!cxz.a(integer6) && (cxz.a(integer2) || cxz.a(integer3) || cxz.a(integer4) || cxz.a(integer5))) {
					return c;
				}
			} else if (integer6 != k && integer6 != l) {
				if (!cxz.a(integer6) && integer6 != s && integer6 != v && (cxz.a(integer2) || cxz.a(integer3) || cxz.a(integer4) || cxz.a(integer5))) {
					return b;
				}
			} else if (!cxz.a(integer2)
				&& !cxz.a(integer3)
				&& !cxz.a(integer4)
				&& !cxz.a(integer5)
				&& (!this.d(integer2) || !this.d(integer3) || !this.d(integer4) || !this.d(integer5))) {
				return d;
			}
		} else if (!cxz.a(integer6) && (cxz.a(integer2) || cxz.a(integer3) || cxz.a(integer4) || cxz.a(integer5))) {
			return u;
		}

		return integer6;
	}

	private static boolean c(int integer) {
		return gl.as.a(integer) != null && gl.as.a(integer).y() == bre.b.JUNGLE
			? true
			: integer == i || integer == h || integer == j || integer == g || integer == w || cxz.a(integer);
	}

	private boolean d(int integer) {
		return integer == k || integer == l || integer == m || integer == n || integer == o || integer == p;
	}
}
