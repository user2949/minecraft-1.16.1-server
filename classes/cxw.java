public class cxw implements cyr {
	private static final int a = gl.as.a(brk.C);
	private static final int b = gl.as.a(brk.d);
	private static final int c = gl.as.a(brk.e);
	private static final int d = gl.as.a(brk.f);
	private static final int e = gl.as.a(brk.n);
	private static final int f = gl.as.a(brk.w);
	private static final int g = gl.as.a(brk.O);
	private static final int h = gl.as.a(brk.N);
	private static final int i = gl.as.a(brk.p);
	private static final int j = gl.as.a(brk.c);
	private static final int k = gl.as.a(brk.H);
	private static final int l = gl.as.a(brk.E);
	private static final int m = gl.as.a(brk.K);
	private static final int n = gl.as.a(brk.h);
	private static final int o = gl.as.a(brk.g);
	private static final int p = gl.as.a(brk.F);
	private static final int[] q = new int[]{b, d, c, n, j, o};
	private static final int[] r = new int[]{b, b, b, m, m, j};
	private static final int[] s = new int[]{d, l, c, j, a, n};
	private static final int[] t = new int[]{d, c, o, j};
	private static final int[] u = new int[]{e, e, e, p};
	private int[] v = r;

	public cxw(boolean boolean1) {
		if (boolean1) {
			this.v = q;
		}
	}

	@Override
	public int a(cxn cxn, int integer) {
		int integer4 = (integer & 3840) >> 8;
		integer &= -3841;
		if (!cxz.a(integer) && integer != i) {
			switch (integer) {
				case 1:
					if (integer4 > 0) {
						return cxn.a(3) == 0 ? g : h;
					}

					return this.v[cxn.a(this.v.length)];
				case 2:
					if (integer4 > 0) {
						return f;
					}

					return s[cxn.a(s.length)];
				case 3:
					if (integer4 > 0) {
						return k;
					}

					return t[cxn.a(t.length)];
				case 4:
					return u[cxn.a(u.length)];
				default:
					return i;
			}
		} else {
			return integer;
		}
	}
}
