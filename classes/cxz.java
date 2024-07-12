import java.util.function.LongFunction;

public class cxz {
	protected static final int a = gl.as.a(brk.T);
	protected static final int b = gl.as.a(brk.U);
	protected static final int c = gl.as.a(brk.a);
	protected static final int d = gl.as.a(brk.V);
	protected static final int e = gl.as.a(brk.l);
	protected static final int f = gl.as.a(brk.W);
	protected static final int g = gl.as.a(brk.X);
	protected static final int h = gl.as.a(brk.z);
	protected static final int i = gl.as.a(brk.Y);
	protected static final int j = gl.as.a(brk.Z);

	private static <T extends cxi, C extends cxm<T>> cxj<T> a(long long1, cyo cyo, cxj<T> cxj, int integer, LongFunction<C> longFunction) {
		cxj<T> cxj7 = cxj;

		for (int integer8 = 0; integer8 < integer; integer8++) {
			cxj7 = cyo.a((cxm<T>)longFunction.apply(long1 + (long)integer8), cxj7);
		}

		return cxj7;
	}

	private static <T extends cxi, C extends cxm<T>> cxj<T> a(boolean boolean1, int integer2, int integer3, LongFunction<C> longFunction) {
		cxj<T> cxj5 = cxx.INSTANCE.a((cxm<T>)longFunction.apply(1L));
		cxj5 = cyl.FUZZY.a((cxm<T>)longFunction.apply(2000L), cxj5);
		cxj5 = cxs.INSTANCE.a((cxm<T>)longFunction.apply(1L), cxj5);
		cxj5 = cyl.NORMAL.a((cxm<T>)longFunction.apply(2001L), cxj5);
		cxj5 = cxs.INSTANCE.a((cxm<T>)longFunction.apply(2L), cxj5);
		cxj5 = cxs.INSTANCE.a((cxm<T>)longFunction.apply(50L), cxj5);
		cxj5 = cxs.INSTANCE.a((cxm<T>)longFunction.apply(70L), cxj5);
		cxj5 = cyf.INSTANCE.a((cxm<T>)longFunction.apply(2L), cxj5);
		cxj<T> cxj6 = cya.INSTANCE.a((cxm<T>)longFunction.apply(2L));
		cxj6 = a(2001L, cyl.NORMAL, cxj6, 6, longFunction);
		cxj5 = cxu.INSTANCE.a((cxm<T>)longFunction.apply(2L), cxj5);
		cxj5 = cxs.INSTANCE.a((cxm<T>)longFunction.apply(3L), cxj5);
		cxj5 = cxr.a.INSTANCE.a((cxm<T>)longFunction.apply(2L), cxj5);
		cxj5 = cxr.b.INSTANCE.a((cxm<T>)longFunction.apply(2L), cxj5);
		cxj5 = cxr.c.INSTANCE.a((cxm<T>)longFunction.apply(3L), cxj5);
		cxj5 = cyl.NORMAL.a((cxm<T>)longFunction.apply(2002L), cxj5);
		cxj5 = cyl.NORMAL.a((cxm<T>)longFunction.apply(2003L), cxj5);
		cxj5 = cxs.INSTANCE.a((cxm<T>)longFunction.apply(4L), cxj5);
		cxj5 = cxt.INSTANCE.a((cxm<T>)longFunction.apply(5L), cxj5);
		cxj5 = cxq.INSTANCE.a((cxm<T>)longFunction.apply(4L), cxj5);
		cxj5 = a(1000L, cyl.NORMAL, cxj5, 0, longFunction);
		cxj<T> cxj7 = a(1000L, cyl.NORMAL, cxj5, 0, longFunction);
		cxj7 = cyg.INSTANCE.a((cxm)longFunction.apply(100L), cxj7);
		cxj<T> cxj8 = new cxw(boolean1).a((cxm<T>)longFunction.apply(200L), cxj5);
		cxj8 = cyc.INSTANCE.a((cxm)longFunction.apply(1001L), cxj8);
		cxj8 = a(1000L, cyl.NORMAL, cxj8, 2, longFunction);
		cxj8 = cxv.INSTANCE.a((cxm)longFunction.apply(1000L), cxj8);
		cxj<T> cxj9 = a(1000L, cyl.NORMAL, cxj7, 2, longFunction);
		cxj8 = cye.INSTANCE.a((cxm)longFunction.apply(1000L), cxj8, cxj9);
		cxj7 = a(1000L, cyl.NORMAL, cxj7, 2, longFunction);
		cxj7 = a(1000L, cyl.NORMAL, cxj7, integer3, longFunction);
		cxj7 = cyh.INSTANCE.a((cxm)longFunction.apply(1L), cxj7);
		cxj7 = cyk.INSTANCE.a((cxm)longFunction.apply(1000L), cxj7);
		cxj8 = cyd.INSTANCE.a((cxm)longFunction.apply(1001L), cxj8);

		for (int integer10 = 0; integer10 < integer2; integer10++) {
			cxj8 = cyl.NORMAL.a((cxm)longFunction.apply((long)(1000 + integer10)), cxj8);
			if (integer10 == 0) {
				cxj8 = cxs.INSTANCE.a((cxm)longFunction.apply(3L), cxj8);
			}

			if (integer10 == 1 || integer2 == 1) {
				cxj8 = cyj.INSTANCE.a((cxm)longFunction.apply(1000L), cxj8);
			}
		}

		cxj8 = cyk.INSTANCE.a((cxm)longFunction.apply(1000L), cxj8);
		cxj8 = cyi.INSTANCE.a((cxm)longFunction.apply(100L), cxj8, cxj7);
		return cyb.INSTANCE.a((cxm<T>)longFunction.apply(100L), cxj8, cxj6);
	}

	public static cxy a(long long1, boolean boolean2, int integer3, int integer4) {
		int integer6 = 25;
		cxj<cxk> cxj7 = a(boolean2, integer3, integer4, long2 -> new cxo(25, long1, long2));
		return new cxy(cxj7);
	}

	public static boolean a(int integer1, int integer2) {
		if (integer1 == integer2) {
			return true;
		} else {
			bre bre3 = gl.as.a(integer1);
			bre bre4 = gl.as.a(integer2);
			if (bre3 == null || bre4 == null) {
				return false;
			} else if (bre3 != brk.N && bre3 != brk.O) {
				return bre3.y() != bre.b.NONE && bre4.y() != bre.b.NONE && bre3.y() == bre4.y() ? true : bre3 == bre4;
			} else {
				return bre4 == brk.N || bre4 == brk.O;
			}
		}
	}

	protected static boolean a(int integer) {
		return integer == a
			|| integer == b
			|| integer == c
			|| integer == d
			|| integer == e
			|| integer == f
			|| integer == g
			|| integer == h
			|| integer == i
			|| integer == j;
	}

	protected static boolean b(int integer) {
		return integer == a || integer == b || integer == c || integer == d || integer == e;
	}
}
