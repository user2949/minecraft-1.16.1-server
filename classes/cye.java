import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum cye implements cyp, cyv {
	INSTANCE;

	private static final Logger b = LogManager.getLogger();
	private static final int c = gl.as.a(brk.C);
	private static final int d = gl.as.a(brk.D);
	private static final int e = gl.as.a(brk.d);
	private static final int f = gl.as.a(brk.s);
	private static final int g = gl.as.a(brk.e);
	private static final int h = gl.as.a(brk.J);
	private static final int i = gl.as.a(brk.f);
	private static final int j = gl.as.a(brk.t);
	private static final int k = gl.as.a(brk.n);
	private static final int l = gl.as.a(brk.o);
	private static final int m = gl.as.a(brk.w);
	private static final int n = gl.as.a(brk.x);
	private static final int o = gl.as.a(brk.aw);
	private static final int p = gl.as.a(brk.ax);
	private static final int q = gl.as.a(brk.M);
	private static final int r = gl.as.a(brk.N);
	private static final int s = gl.as.a(brk.c);
	private static final int t = gl.as.a(brk.H);
	private static final int u = gl.as.a(brk.I);
	private static final int v = gl.as.a(brk.E);
	private static final int w = gl.as.a(brk.K);
	private static final int x = gl.as.a(brk.L);
	private static final int y = gl.as.a(brk.g);
	private static final int z = gl.as.a(brk.F);
	private static final int A = gl.as.a(brk.G);
	private static final int B = gl.as.a(brk.u);

	@Override
	public int a(cxn cxn, cxi cxi2, cxi cxi3, int integer4, int integer5) {
		int integer7 = cxi2.a(this.a(integer4 + 1), this.b(integer5 + 1));
		int integer8 = cxi3.a(this.a(integer4 + 1), this.b(integer5 + 1));
		if (integer7 > 255) {
			b.debug("old! {}", integer7);
		}

		int integer9 = (integer8 - 2) % 29;
		if (!cxz.b(integer7) && integer8 >= 2 && integer9 == 1) {
			bre bre10 = gl.as.a(integer7);
			if (bre10 == null || !bre10.b()) {
				bre bre11 = bre.a(bre10);
				return bre11 == null ? integer7 : gl.as.a(bre11);
			}
		}

		if (cxn.a(3) == 0 || integer9 == 0) {
			int integer10 = integer7;
			if (integer7 == e) {
				integer10 = f;
			} else if (integer7 == i) {
				integer10 = j;
			} else if (integer7 == c) {
				integer10 = d;
			} else if (integer7 == v) {
				integer10 = s;
			} else if (integer7 == y) {
				integer10 = B;
			} else if (integer7 == t) {
				integer10 = u;
			} else if (integer7 == z) {
				integer10 = A;
			} else if (integer7 == s) {
				integer10 = cxn.a(3) == 0 ? j : i;
			} else if (integer7 == k) {
				integer10 = l;
			} else if (integer7 == m) {
				integer10 = n;
			} else if (integer7 == o) {
				integer10 = p;
			} else if (integer7 == cxz.c) {
				integer10 = cxz.h;
			} else if (integer7 == cxz.b) {
				integer10 = cxz.g;
			} else if (integer7 == cxz.d) {
				integer10 = cxz.i;
			} else if (integer7 == cxz.e) {
				integer10 = cxz.j;
			} else if (integer7 == g) {
				integer10 = h;
			} else if (integer7 == w) {
				integer10 = x;
			} else if (cxz.a(integer7, r)) {
				integer10 = q;
			} else if ((integer7 == cxz.h || integer7 == cxz.g || integer7 == cxz.i || integer7 == cxz.j) && cxn.a(3) == 0) {
				integer10 = cxn.a(2) == 0 ? s : i;
			}

			if (integer9 == 0 && integer10 != integer7) {
				bre bre11 = bre.a(gl.as.a(integer10));
				integer10 = bre11 == null ? integer7 : gl.as.a(bre11);
			}

			if (integer10 != integer7) {
				int integer11 = 0;
				if (cxz.a(cxi2.a(this.a(integer4 + 1), this.b(integer5 + 0)), integer7)) {
					integer11++;
				}

				if (cxz.a(cxi2.a(this.a(integer4 + 2), this.b(integer5 + 1)), integer7)) {
					integer11++;
				}

				if (cxz.a(cxi2.a(this.a(integer4 + 0), this.b(integer5 + 1)), integer7)) {
					integer11++;
				}

				if (cxz.a(cxi2.a(this.a(integer4 + 1), this.b(integer5 + 2)), integer7)) {
					integer11++;
				}

				if (integer11 >= 3) {
					return integer10;
				}
			}
		}

		return integer7;
	}
}
