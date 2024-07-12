import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;

public class bmc {
	private static final List<bmc.a<bmb>> a = Lists.<bmc.a<bmb>>newArrayList();
	private static final List<bmc.a<bke>> b = Lists.<bmc.a<bke>>newArrayList();
	private static final List<bmr> c = Lists.<bmr>newArrayList();
	private static final Predicate<bki> d = bki -> {
		for (bmr bmr3 : c) {
			if (bmr3.a(bki)) {
				return true;
			}
		}

		return false;
	};

	public static boolean a(bki bki) {
		return b(bki) || c(bki);
	}

	protected static boolean b(bki bki) {
		int integer2 = 0;

		for (int integer3 = b.size(); integer2 < integer3; integer2++) {
			if (((bmc.a)b.get(integer2)).b.a(bki)) {
				return true;
			}
		}

		return false;
	}

	protected static boolean c(bki bki) {
		int integer2 = 0;

		for (int integer3 = a.size(); integer2 < integer3; integer2++) {
			if (((bmc.a)a.get(integer2)).b.a(bki)) {
				return true;
			}
		}

		return false;
	}

	public static boolean a(bmb bmb) {
		int integer2 = 0;

		for (int integer3 = a.size(); integer2 < integer3; integer2++) {
			if (((bmc.a)a.get(integer2)).c == bmb) {
				return true;
			}
		}

		return false;
	}

	public static boolean a(bki bki1, bki bki2) {
		return !d.test(bki1) ? false : b(bki1, bki2) || c(bki1, bki2);
	}

	protected static boolean b(bki bki1, bki bki2) {
		bke bke3 = bki1.b();
		int integer4 = 0;

		for (int integer5 = b.size(); integer4 < integer5; integer4++) {
			bmc.a<bke> a6 = (bmc.a<bke>)b.get(integer4);
			if (a6.a == bke3 && a6.b.a(bki2)) {
				return true;
			}
		}

		return false;
	}

	protected static boolean c(bki bki1, bki bki2) {
		bmb bmb3 = bmd.d(bki1);
		int integer4 = 0;

		for (int integer5 = a.size(); integer4 < integer5; integer4++) {
			bmc.a<bmb> a6 = (bmc.a<bmb>)a.get(integer4);
			if (a6.a == bmb3 && a6.b.a(bki2)) {
				return true;
			}
		}

		return false;
	}

	public static bki d(bki bki1, bki bki2) {
		if (!bki2.a()) {
			bmb bmb3 = bmd.d(bki2);
			bke bke4 = bki2.b();
			int integer5 = 0;

			for (int integer6 = b.size(); integer5 < integer6; integer5++) {
				bmc.a<bke> a7 = (bmc.a<bke>)b.get(integer5);
				if (a7.a == bke4 && a7.b.a(bki1)) {
					return bmd.a(new bki(a7.c), bmb3);
				}
			}

			integer5 = 0;

			for (int integer6x = a.size(); integer5 < integer6x; integer5++) {
				bmc.a<bmb> a7 = (bmc.a<bmb>)a.get(integer5);
				if (a7.a == bmb3 && a7.b.a(bki1)) {
					return bmd.a(new bki(bke4), a7.c);
				}
			}
		}

		return bki2;
	}

	public static void a() {
		a(bkk.nv);
		a(bkk.qi);
		a(bkk.ql);
		a(bkk.nv, bkk.kO, bkk.qi);
		a(bkk.qi, bkk.qh, bkk.ql);
		a(bme.b, bkk.nE, bme.c);
		a(bme.b, bkk.ns, bme.c);
		a(bme.b, bkk.pz, bme.c);
		a(bme.b, bkk.nz, bme.c);
		a(bme.b, bkk.nx, bme.c);
		a(bme.b, bkk.mM, bme.c);
		a(bme.b, bkk.nA, bme.c);
		a(bme.b, bkk.mk, bme.d);
		a(bme.b, bkk.lP, bme.c);
		a(bme.b, bkk.nu, bme.e);
		a(bme.e, bkk.pc, bme.f);
		a(bme.f, bkk.lP, bme.g);
		a(bme.f, bkk.ny, bme.h);
		a(bme.g, bkk.ny, bme.i);
		a(bme.h, bkk.lP, bme.i);
		a(bme.e, bkk.nA, bme.m);
		a(bme.m, bkk.lP, bme.n);
		a(bme.e, bkk.pz, bme.j);
		a(bme.j, bkk.lP, bme.k);
		a(bme.j, bkk.mk, bme.l);
		a(bme.j, bkk.ny, bme.r);
		a(bme.k, bkk.ny, bme.s);
		a(bme.r, bkk.lP, bme.s);
		a(bme.r, bkk.mk, bme.t);
		a(bme.e, bkk.jY, bme.u);
		a(bme.u, bkk.lP, bme.v);
		a(bme.u, bkk.mk, bme.w);
		a(bme.o, bkk.ny, bme.r);
		a(bme.p, bkk.ny, bme.s);
		a(bme.e, bkk.mM, bme.o);
		a(bme.o, bkk.lP, bme.p);
		a(bme.o, bkk.mk, bme.q);
		a(bme.e, bkk.mo, bme.x);
		a(bme.x, bkk.lP, bme.y);
		a(bme.e, bkk.nE, bme.z);
		a(bme.z, bkk.mk, bme.A);
		a(bme.z, bkk.ny, bme.B);
		a(bme.A, bkk.ny, bme.C);
		a(bme.B, bkk.mk, bme.C);
		a(bme.D, bkk.ny, bme.B);
		a(bme.E, bkk.ny, bme.B);
		a(bme.F, bkk.ny, bme.C);
		a(bme.e, bkk.nx, bme.D);
		a(bme.D, bkk.lP, bme.E);
		a(bme.D, bkk.mk, bme.F);
		a(bme.e, bkk.ns, bme.G);
		a(bme.G, bkk.lP, bme.H);
		a(bme.G, bkk.mk, bme.I);
		a(bme.e, bkk.nz, bme.J);
		a(bme.J, bkk.lP, bme.K);
		a(bme.J, bkk.mk, bme.L);
		a(bme.b, bkk.ny, bme.M);
		a(bme.M, bkk.lP, bme.N);
		a(bme.e, bkk.qM, bme.P);
		a(bme.P, bkk.lP, bme.Q);
	}

	private static void a(bke bke1, bke bke2, bke bke3) {
		if (!(bke1 instanceof bku)) {
			throw new IllegalArgumentException("Expected a potion, got: " + gl.am.b(bke1));
		} else if (!(bke3 instanceof bku)) {
			throw new IllegalArgumentException("Expected a potion, got: " + gl.am.b(bke3));
		} else {
			b.add(new bmc.a<>(bke1, bmr.a(bke2), bke3));
		}
	}

	private static void a(bke bke) {
		if (!(bke instanceof bku)) {
			throw new IllegalArgumentException("Expected a potion, got: " + gl.am.b(bke));
		} else {
			c.add(bmr.a(bke));
		}
	}

	private static void a(bmb bmb1, bke bke, bmb bmb3) {
		a.add(new bmc.a<>(bmb1, bmr.a(bke), bmb3));
	}

	static class a<T> {
		private final T a;
		private final bmr b;
		private final T c;

		public a(T object1, bmr bmr, T object3) {
			this.a = object1;
			this.b = bmr;
			this.c = object3;
		}
	}
}
