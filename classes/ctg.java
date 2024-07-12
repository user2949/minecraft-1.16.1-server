import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class ctg {
	private static final cvb a = new cvb().a(true).a(cui.b);
	private static final cvb b = new cvb().a(true).a(cui.d);
	private static final ctg.b c = new ctg.b() {
		@Override
		public void a() {
		}

		@Override
		public boolean a(cva cva, int integer, ctg.a a, fu fu, List<cty> list, Random random) {
			if (integer > 8) {
				return false;
			} else {
				cap cap8 = a.b.d();
				ctg.a a9 = ctg.b(list, ctg.b(cva, a, fu, "base_floor", cap8, true));
				int integer10 = random.nextInt(3);
				if (integer10 == 0) {
					a9 = ctg.b(list, ctg.b(cva, a9, new fu(-1, 4, -1), "base_roof", cap8, true));
				} else if (integer10 == 1) {
					a9 = ctg.b(list, ctg.b(cva, a9, new fu(-1, 0, -1), "second_floor_2", cap8, false));
					a9 = ctg.b(list, ctg.b(cva, a9, new fu(-1, 8, -1), "second_roof", cap8, false));
					ctg.b(cva, ctg.e, integer + 1, a9, null, list, random);
				} else if (integer10 == 2) {
					a9 = ctg.b(list, ctg.b(cva, a9, new fu(-1, 0, -1), "second_floor_2", cap8, false));
					a9 = ctg.b(list, ctg.b(cva, a9, new fu(-1, 4, -1), "third_floor_2", cap8, false));
					a9 = ctg.b(list, ctg.b(cva, a9, new fu(-1, 8, -1), "third_roof", cap8, true));
					ctg.b(cva, ctg.e, integer + 1, a9, null, list, random);
				}

				return true;
			}
		}
	};
	private static final List<aek<cap, fu>> d = Lists.<aek<cap, fu>>newArrayList(
		new aek<>(cap.NONE, new fu(1, -1, 0)),
		new aek<>(cap.CLOCKWISE_90, new fu(6, -1, 1)),
		new aek<>(cap.COUNTERCLOCKWISE_90, new fu(0, -1, 5)),
		new aek<>(cap.CLOCKWISE_180, new fu(5, -1, 6))
	);
	private static final ctg.b e = new ctg.b() {
		@Override
		public void a() {
		}

		@Override
		public boolean a(cva cva, int integer, ctg.a a, fu fu, List<cty> list, Random random) {
			cap cap8 = a.b.d();
			ctg.a a9 = ctg.b(list, ctg.b(cva, a, new fu(3 + random.nextInt(2), -3, 3 + random.nextInt(2)), "tower_base", cap8, true));
			a9 = ctg.b(list, ctg.b(cva, a9, new fu(0, 7, 0), "tower_piece", cap8, true));
			ctg.a a10 = random.nextInt(3) == 0 ? a9 : null;
			int integer11 = 1 + random.nextInt(3);

			for (int integer12 = 0; integer12 < integer11; integer12++) {
				a9 = ctg.b(list, ctg.b(cva, a9, new fu(0, 4, 0), "tower_piece", cap8, true));
				if (integer12 < integer11 - 1 && random.nextBoolean()) {
					a10 = a9;
				}
			}

			if (a10 != null) {
				for (aek<cap, fu> aek13 : ctg.d) {
					if (random.nextBoolean()) {
						ctg.a a14 = ctg.b(list, ctg.b(cva, a10, aek13.b(), "bridge_end", cap8.a(aek13.a()), true));
						ctg.b(cva, ctg.f, integer + 1, a14, null, list, random);
					}
				}

				a9 = ctg.b(list, ctg.b(cva, a9, new fu(-1, 4, -1), "tower_top", cap8, true));
			} else {
				if (integer != 7) {
					return ctg.b(cva, ctg.h, integer + 1, a9, null, list, random);
				}

				a9 = ctg.b(list, ctg.b(cva, a9, new fu(-1, 4, -1), "tower_top", cap8, true));
			}

			return true;
		}
	};
	private static final ctg.b f = new ctg.b() {
		public boolean a;

		@Override
		public void a() {
			this.a = false;
		}

		@Override
		public boolean a(cva cva, int integer, ctg.a a, fu fu, List<cty> list, Random random) {
			cap cap8 = a.b.d();
			int integer9 = random.nextInt(4) + 1;
			ctg.a a10 = ctg.b(list, ctg.b(cva, a, new fu(0, 0, -4), "bridge_piece", cap8, true));
			a10.o = -1;
			int integer11 = 0;

			for (int integer12 = 0; integer12 < integer9; integer12++) {
				if (random.nextBoolean()) {
					a10 = ctg.b(list, ctg.b(cva, a10, new fu(0, integer11, -4), "bridge_piece", cap8, true));
					integer11 = 0;
				} else {
					if (random.nextBoolean()) {
						a10 = ctg.b(list, ctg.b(cva, a10, new fu(0, integer11, -4), "bridge_steep_stairs", cap8, true));
					} else {
						a10 = ctg.b(list, ctg.b(cva, a10, new fu(0, integer11, -8), "bridge_gentle_stairs", cap8, true));
					}

					integer11 = 4;
				}
			}

			if (!this.a && random.nextInt(10 - integer) == 0) {
				ctg.b(list, ctg.b(cva, a10, new fu(-8 + random.nextInt(8), integer11, -70 + random.nextInt(10)), "ship", cap8, true));
				this.a = true;
			} else if (!ctg.b(cva, ctg.c, integer + 1, a10, new fu(-3, integer11 + 1, -11), list, random)) {
				return false;
			}

			a10 = ctg.b(list, ctg.b(cva, a10, new fu(4, integer11, 0), "bridge_end", cap8.a(cap.CLOCKWISE_180), true));
			a10.o = -1;
			return true;
		}
	};
	private static final List<aek<cap, fu>> g = Lists.<aek<cap, fu>>newArrayList(
		new aek<>(cap.NONE, new fu(4, -1, 0)),
		new aek<>(cap.CLOCKWISE_90, new fu(12, -1, 4)),
		new aek<>(cap.COUNTERCLOCKWISE_90, new fu(0, -1, 8)),
		new aek<>(cap.CLOCKWISE_180, new fu(8, -1, 12))
	);
	private static final ctg.b h = new ctg.b() {
		@Override
		public void a() {
		}

		@Override
		public boolean a(cva cva, int integer, ctg.a a, fu fu, List<cty> list, Random random) {
			cap cap9 = a.b.d();
			ctg.a a8 = ctg.b(list, ctg.b(cva, a, new fu(-3, 4, -3), "fat_tower_base", cap9, true));
			a8 = ctg.b(list, ctg.b(cva, a8, new fu(0, 4, 0), "fat_tower_middle", cap9, true));

			for (int integer10 = 0; integer10 < 2 && random.nextInt(3) != 0; integer10++) {
				a8 = ctg.b(list, ctg.b(cva, a8, new fu(0, 8, 0), "fat_tower_middle", cap9, true));

				for (aek<cap, fu> aek12 : ctg.g) {
					if (random.nextBoolean()) {
						ctg.a a13 = ctg.b(list, ctg.b(cva, a8, aek12.b(), "bridge_end", cap9.a(aek12.a()), true));
						ctg.b(cva, ctg.f, integer + 1, a13, null, list, random);
					}
				}
			}

			a8 = ctg.b(list, ctg.b(cva, a8, new fu(-2, 8, -2), "fat_tower_top", cap9, true));
			return true;
		}
	};

	private static ctg.a b(cva cva, ctg.a a, fu fu, String string, cap cap, boolean boolean6) {
		ctg.a a7 = new ctg.a(cva, string, a.c, cap, boolean6);
		fu fu8 = a.a.a(a.b, fu, a7.b, fu.b);
		a7.a(fu8.u(), fu8.v(), fu8.w());
		return a7;
	}

	public static void a(cva cva, fu fu, cap cap, List<cty> list, Random random) {
		h.a();
		c.a();
		f.a();
		e.a();
		ctg.a a6 = b(list, new ctg.a(cva, "base_floor", fu, cap, true));
		a6 = b(list, b(cva, a6, new fu(-1, 0, -1), "second_floor_1", cap, false));
		a6 = b(list, b(cva, a6, new fu(-1, 4, -1), "third_floor_1", cap, false));
		a6 = b(list, b(cva, a6, new fu(-1, 8, -1), "third_roof", cap, true));
		b(cva, e, 1, a6, null, list, random);
	}

	private static ctg.a b(List<cty> list, ctg.a a) {
		list.add(a);
		return a;
	}

	private static boolean b(cva cva, ctg.b b, int integer, ctg.a a, fu fu, List<cty> list, Random random) {
		if (integer > 8) {
			return false;
		} else {
			List<cty> list8 = Lists.<cty>newArrayList();
			if (b.a(cva, integer, a, fu, list8, random)) {
				boolean boolean9 = false;
				int integer10 = random.nextInt();

				for (cty cty12 : list8) {
					cty12.o = integer10;
					cty cty13 = cty.a(list, cty12.g());
					if (cty13 != null && cty13.o != a.o) {
						boolean9 = true;
						break;
					}
				}

				if (!boolean9) {
					list.addAll(list8);
					return true;
				}
			}

			return false;
		}
	}

	public static class a extends cub {
		private final String d;
		private final cap e;
		private final boolean f;

		public a(cva cva, String string, fu fu, cap cap, boolean boolean5) {
			super(cmm.aa, 0);
			this.d = string;
			this.c = fu;
			this.e = cap;
			this.f = boolean5;
			this.a(cva);
		}

		public a(cva cva, le le) {
			super(cmm.aa, le);
			this.d = le.l("Template");
			this.e = cap.valueOf(le.l("Rot"));
			this.f = le.q("OW");
			this.a(cva);
		}

		private void a(cva cva) {
			cve cve3 = cva.a(new uh("end_city/" + this.d));
			cvb cvb4 = (this.f ? ctg.a : ctg.b).a().a(this.e);
			this.a(cve3, this.c, cvb4);
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Template", this.d);
			le.a("Rot", this.e.name());
			le.a("OW", this.f);
		}

		@Override
		protected void a(String string, fu fu, bqc bqc, Random random, ctd ctd) {
			if (string.startsWith("Chest")) {
				fu fu7 = fu.c();
				if (ctd.b(fu7)) {
					cef.a(bqc, random, fu7, dao.c);
				}
			} else if (string.startsWith("Sentry")) {
				bch bch7 = aoq.ar.a(bqc.n());
				bch7.d((double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5);
				bch7.h(fu);
				bqc.c(bch7);
			} else if (string.startsWith("Elytra")) {
				bba bba7 = new bba(bqc.n(), fu, this.e.a(fz.SOUTH));
				bba7.a(new bki(bkk.qn), false);
				bqc.c(bba7);
			}
		}
	}

	interface b {
		void a();

		boolean a(cva cva, int integer, ctg.a a, fu fu, List<cty> list, Random random);
	}
}
