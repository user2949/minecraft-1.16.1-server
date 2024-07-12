import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class ctl {
	private static final ctl.n[] a = new ctl.n[]{
		new ctl.n(ctl.c.class, 30, 0, true),
		new ctl.n(ctl.a.class, 10, 4),
		new ctl.n(ctl.o.class, 10, 4),
		new ctl.n(ctl.p.class, 10, 3),
		new ctl.n(ctl.l.class, 5, 2),
		new ctl.n(ctl.f.class, 5, 1)
	};
	private static final ctl.n[] b = new ctl.n[]{
		new ctl.n(ctl.i.class, 25, 0, true),
		new ctl.n(ctl.g.class, 15, 5),
		new ctl.n(ctl.j.class, 5, 10),
		new ctl.n(ctl.h.class, 5, 10),
		new ctl.n(ctl.d.class, 10, 3, true),
		new ctl.n(ctl.e.class, 7, 2),
		new ctl.n(ctl.k.class, 5, 2)
	};

	private static ctl.m b(ctl.n n, List<cty> list, Random random, int integer4, int integer5, int integer6, fz fz, int integer8) {
		Class<? extends ctl.m> class9 = n.a;
		ctl.m m10 = null;
		if (class9 == ctl.c.class) {
			m10 = ctl.c.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.a.class) {
			m10 = ctl.a.a(list, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.o.class) {
			m10 = ctl.o.a(list, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.p.class) {
			m10 = ctl.p.a(list, integer4, integer5, integer6, integer8, fz);
		} else if (class9 == ctl.l.class) {
			m10 = ctl.l.a(list, integer4, integer5, integer6, integer8, fz);
		} else if (class9 == ctl.f.class) {
			m10 = ctl.f.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.i.class) {
			m10 = ctl.i.a(list, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.j.class) {
			m10 = ctl.j.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.h.class) {
			m10 = ctl.h.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.d.class) {
			m10 = ctl.d.a(list, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.e.class) {
			m10 = ctl.e.a(list, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.g.class) {
			m10 = ctl.g.a(list, integer4, integer5, integer6, fz, integer8);
		} else if (class9 == ctl.k.class) {
			m10 = ctl.k.a(list, integer4, integer5, integer6, fz, integer8);
		}

		return m10;
	}

	public static class a extends ctl.m {
		public a(int integer, ctd ctd, fz fz) {
			super(cmm.g, integer);
			this.a(fz);
			this.n = ctd;
		}

		protected a(Random random, int integer2, int integer3) {
			super(cmm.g, 0);
			this.a(fz.c.HORIZONTAL.a(random));
			if (this.i().n() == fz.a.Z) {
				this.n = new ctd(integer2, 64, integer3, integer2 + 19 - 1, 73, integer3 + 19 - 1);
			} else {
				this.n = new ctd(integer2, 64, integer3, integer2 + 19 - 1, 73, integer3 + 19 - 1);
			}
		}

		protected a(cmm cmm, le le) {
			super(cmm, le);
		}

		public a(cva cva, le le) {
			this(cmm.g, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctl.q)cty, list, random, 8, 3, false);
			this.b((ctl.q)cty, list, random, 3, 8, false);
			this.c((ctl.q)cty, list, random, 3, 8, false);
		}

		public static ctl.a a(List<cty> list, int integer2, int integer3, int integer4, fz fz, int integer6) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -8, -3, 0, 19, 10, 19, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctl.a(integer6, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 7, 3, 0, 11, 4, 18, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 3, 7, 18, 4, 11, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 8, 5, 0, 10, 7, 18, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 0, 5, 8, 18, 7, 10, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 7, 5, 0, 7, 5, 7, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 7, 5, 11, 7, 5, 18, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 11, 5, 0, 11, 5, 7, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 11, 5, 11, 11, 5, 18, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 5, 7, 7, 5, 7, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 11, 5, 7, 18, 5, 7, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 5, 11, 7, 5, 11, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 11, 5, 11, 18, 5, 11, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 7, 2, 0, 11, 2, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 7, 2, 13, 11, 2, 18, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 7, 0, 0, 11, 1, 3, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 7, 0, 15, 11, 1, 18, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer9 = 7; integer9 <= 11; integer9++) {
				for (int integer10 = 0; integer10 <= 2; integer10++) {
					this.b(bqu, bvs.dV.n(), integer9, -1, integer10, ctd);
					this.b(bqu, bvs.dV.n(), integer9, -1, 18 - integer10, ctd);
				}
			}

			this.a(bqu, ctd, 0, 2, 7, 5, 2, 11, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 13, 2, 7, 18, 2, 11, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 0, 7, 3, 1, 11, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 15, 0, 7, 18, 1, 11, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer9 = 0; integer9 <= 2; integer9++) {
				for (int integer10 = 7; integer10 <= 11; integer10++) {
					this.b(bqu, bvs.dV.n(), integer9, -1, integer10, ctd);
					this.b(bqu, bvs.dV.n(), 18 - integer9, -1, integer10, ctd);
				}
			}

			return true;
		}
	}

	public static class b extends ctl.m {
		private final int a;

		public b(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.h, integer);
			this.a(fz);
			this.n = ctd;
			this.a = random.nextInt();
		}

		public b(cva cva, le le) {
			super(cmm.h, le);
			this.a = le.h("Seed");
		}

		public static ctl.b a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -3, 0, 5, 10, 8, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctl.b(integer7, random, ctd8, fz) : null;
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.b("Seed", this.a);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			Random random9 = new Random((long)this.a);

			for (int integer10 = 0; integer10 <= 4; integer10++) {
				for (int integer11 = 3; integer11 <= 4; integer11++) {
					int integer12 = random9.nextInt(8);
					this.a(bqu, ctd, integer10, integer11, 0, integer10, integer11, integer12, bvs.dV.n(), bvs.dV.n(), false);
				}
			}

			int integer10 = random9.nextInt(8);
			this.a(bqu, ctd, 0, 5, 0, 0, 5, integer10, bvs.dV.n(), bvs.dV.n(), false);
			integer10 = random9.nextInt(8);
			this.a(bqu, ctd, 4, 5, 0, 4, 5, integer10, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer10x = 0; integer10x <= 4; integer10x++) {
				int integer11 = random9.nextInt(5);
				this.a(bqu, ctd, integer10x, 2, 0, integer10x, 2, integer11, bvs.dV.n(), bvs.dV.n(), false);
			}

			for (int integer10x = 0; integer10x <= 4; integer10x++) {
				for (int integer11 = 0; integer11 <= 1; integer11++) {
					int integer12 = random9.nextInt(3);
					this.a(bqu, ctd, integer10x, integer11, 0, integer10x, integer11, integer12, bvs.dV.n(), bvs.dV.n(), false);
				}
			}

			return true;
		}
	}

	public static class c extends ctl.m {
		public c(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.i, integer);
			this.a(fz);
			this.n = ctd;
		}

		public c(cva cva, le le) {
			super(cmm.i, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctl.q)cty, list, random, 1, 3, false);
		}

		public static ctl.c a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -3, 0, 5, 10, 19, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctl.c(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 3, 0, 4, 4, 18, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 5, 0, 3, 7, 18, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 0, 5, 0, 0, 5, 18, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 4, 5, 0, 4, 5, 18, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 4, 2, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 13, 4, 2, 18, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 0, 0, 4, 1, 3, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 0, 15, 4, 1, 18, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer9 = 0; integer9 <= 4; integer9++) {
				for (int integer10 = 0; integer10 <= 2; integer10++) {
					this.b(bqu, bvs.dV.n(), integer9, -1, integer10, ctd);
					this.b(bqu, bvs.dV.n(), integer9, -1, 18 - integer10, ctd);
				}
			}

			cfj cfj9 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
			cfj cfj10 = cfj9.a(bxt.b, Boolean.valueOf(true));
			cfj cfj11 = cfj9.a(bxt.d, Boolean.valueOf(true));
			this.a(bqu, ctd, 0, 1, 1, 0, 4, 1, cfj10, cfj10, false);
			this.a(bqu, ctd, 0, 3, 4, 0, 4, 4, cfj10, cfj10, false);
			this.a(bqu, ctd, 0, 3, 14, 0, 4, 14, cfj10, cfj10, false);
			this.a(bqu, ctd, 0, 1, 17, 0, 4, 17, cfj10, cfj10, false);
			this.a(bqu, ctd, 4, 1, 1, 4, 4, 1, cfj11, cfj11, false);
			this.a(bqu, ctd, 4, 3, 4, 4, 4, 4, cfj11, cfj11, false);
			this.a(bqu, ctd, 4, 3, 14, 4, 4, 14, cfj11, cfj11, false);
			this.a(bqu, ctd, 4, 1, 17, 4, 4, 17, cfj11, cfj11, false);
			return true;
		}
	}

	public static class d extends ctl.m {
		public d(int integer, ctd ctd, fz fz) {
			super(cmm.j, integer);
			this.a(fz);
			this.n = ctd;
		}

		public d(cva cva, le le) {
			super(cmm.j, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctl.q)cty, list, random, 1, 0, true);
		}

		public static ctl.d a(List<cty> list, int integer2, int integer3, int integer4, fz fz, int integer6) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -1, -7, 0, 5, 14, 10, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctl.d(integer6, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			cfj cfj9 = bvs.dX.n().a(cbn.a, fz.SOUTH);
			cfj cfj10 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));

			for (int integer11 = 0; integer11 <= 9; integer11++) {
				int integer12 = Math.max(1, 7 - integer11);
				int integer13 = Math.min(Math.max(integer12 + 5, 14 - integer11), 13);
				int integer14 = integer11;
				this.a(bqu, ctd, 0, 0, integer11, 4, integer12, integer11, bvs.dV.n(), bvs.dV.n(), false);
				this.a(bqu, ctd, 1, integer12 + 1, integer11, 3, integer13 - 1, integer11, bvs.a.n(), bvs.a.n(), false);
				if (integer11 <= 6) {
					this.a(bqu, cfj9, 1, integer12 + 1, integer11, ctd);
					this.a(bqu, cfj9, 2, integer12 + 1, integer11, ctd);
					this.a(bqu, cfj9, 3, integer12 + 1, integer11, ctd);
				}

				this.a(bqu, ctd, 0, integer13, integer11, 4, integer13, integer11, bvs.dV.n(), bvs.dV.n(), false);
				this.a(bqu, ctd, 0, integer12 + 1, integer11, 0, integer13 - 1, integer11, bvs.dV.n(), bvs.dV.n(), false);
				this.a(bqu, ctd, 4, integer12 + 1, integer11, 4, integer13 - 1, integer11, bvs.dV.n(), bvs.dV.n(), false);
				if ((integer11 & 1) == 0) {
					this.a(bqu, ctd, 0, integer12 + 2, integer11, 0, integer12 + 3, integer11, cfj10, cfj10, false);
					this.a(bqu, ctd, 4, integer12 + 2, integer11, 4, integer12 + 3, integer11, cfj10, cfj10, false);
				}

				for (int integer15 = 0; integer15 <= 4; integer15++) {
					this.b(bqu, bvs.dV.n(), integer15, -1, integer14, ctd);
				}
			}

			return true;
		}
	}

	public static class e extends ctl.m {
		public e(int integer, ctd ctd, fz fz) {
			super(cmm.k, integer);
			this.a(fz);
			this.n = ctd;
		}

		public e(cva cva, le le) {
			super(cmm.k, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			int integer5 = 1;
			fz fz6 = this.i();
			if (fz6 == fz.WEST || fz6 == fz.NORTH) {
				integer5 = 5;
			}

			this.b((ctl.q)cty, list, random, 0, integer5, random.nextInt(8) > 0);
			this.c((ctl.q)cty, list, random, 0, integer5, random.nextInt(8) > 0);
		}

		public static ctl.e a(List<cty> list, int integer2, int integer3, int integer4, fz fz, int integer6) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -3, 0, 0, 9, 7, 9, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctl.e(integer6, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			cfj cfj9 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
			cfj cfj10 = bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
			this.a(bqu, ctd, 0, 0, 0, 8, 1, 8, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 8, 5, 8, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 0, 6, 0, 8, 6, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 2, 5, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 6, 2, 0, 8, 5, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 3, 0, 1, 4, 0, cfj10, cfj10, false);
			this.a(bqu, ctd, 7, 3, 0, 7, 4, 0, cfj10, cfj10, false);
			this.a(bqu, ctd, 0, 2, 4, 8, 2, 8, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 1, 4, 2, 2, 4, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 6, 1, 4, 7, 2, 4, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 1, 3, 8, 7, 3, 8, cfj10, cfj10, false);
			this.a(bqu, bvs.dW.n().a(bxt.b, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true)), 0, 3, 8, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true)), 8, 3, 8, ctd);
			this.a(bqu, ctd, 0, 3, 6, 0, 3, 7, cfj9, cfj9, false);
			this.a(bqu, ctd, 8, 3, 6, 8, 3, 7, cfj9, cfj9, false);
			this.a(bqu, ctd, 0, 3, 4, 0, 5, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 8, 3, 4, 8, 5, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 3, 5, 2, 5, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 6, 3, 5, 7, 5, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 4, 5, 1, 5, 5, cfj10, cfj10, false);
			this.a(bqu, ctd, 7, 4, 5, 7, 5, 5, cfj10, cfj10, false);

			for (int integer11 = 0; integer11 <= 5; integer11++) {
				for (int integer12 = 0; integer12 <= 8; integer12++) {
					this.b(bqu, bvs.dV.n(), integer12, -1, integer11, ctd);
				}
			}

			return true;
		}
	}

	public static class f extends ctl.m {
		public f(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.l, integer);
			this.a(fz);
			this.n = ctd;
		}

		public f(cva cva, le le) {
			super(cmm.l, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctl.q)cty, list, random, 5, 3, true);
		}

		public static ctl.f a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -5, -3, 0, 13, 14, 13, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctl.f(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 3, 0, 12, 4, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 5, 0, 12, 13, 12, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 0, 5, 0, 1, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 11, 5, 0, 12, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 5, 11, 4, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 8, 5, 11, 10, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 9, 11, 7, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 5, 0, 4, 12, 1, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 8, 5, 0, 10, 12, 1, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 9, 0, 7, 12, 1, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 11, 2, 10, 12, 10, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 8, 0, 7, 8, 0, bvs.dW.n(), bvs.dW.n(), false);
			cfj cfj9 = bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
			cfj cfj10 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));

			for (int integer11 = 1; integer11 <= 11; integer11 += 2) {
				this.a(bqu, ctd, integer11, 10, 0, integer11, 11, 0, cfj9, cfj9, false);
				this.a(bqu, ctd, integer11, 10, 12, integer11, 11, 12, cfj9, cfj9, false);
				this.a(bqu, ctd, 0, 10, integer11, 0, 11, integer11, cfj10, cfj10, false);
				this.a(bqu, ctd, 12, 10, integer11, 12, 11, integer11, cfj10, cfj10, false);
				this.a(bqu, bvs.dV.n(), integer11, 13, 0, ctd);
				this.a(bqu, bvs.dV.n(), integer11, 13, 12, ctd);
				this.a(bqu, bvs.dV.n(), 0, 13, integer11, ctd);
				this.a(bqu, bvs.dV.n(), 12, 13, integer11, ctd);
				if (integer11 != 11) {
					this.a(bqu, cfj9, integer11 + 1, 13, 0, ctd);
					this.a(bqu, cfj9, integer11 + 1, 13, 12, ctd);
					this.a(bqu, cfj10, 0, 13, integer11 + 1, ctd);
					this.a(bqu, cfj10, 12, 13, integer11 + 1, ctd);
				}
			}

			this.a(bqu, bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true)), 0, 13, 0, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.c, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true)), 0, 13, 12, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.c, Boolean.valueOf(true)).a(bxt.d, Boolean.valueOf(true)), 12, 13, 12, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.d, Boolean.valueOf(true)), 12, 13, 0, ctd);

			for (int integer11x = 3; integer11x <= 9; integer11x += 2) {
				this.a(bqu, ctd, 1, 7, integer11x, 1, 8, integer11x, cfj10.a(bxt.d, Boolean.valueOf(true)), cfj10.a(bxt.d, Boolean.valueOf(true)), false);
				this.a(bqu, ctd, 11, 7, integer11x, 11, 8, integer11x, cfj10.a(bxt.b, Boolean.valueOf(true)), cfj10.a(bxt.b, Boolean.valueOf(true)), false);
			}

			this.a(bqu, ctd, 4, 2, 0, 8, 2, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 4, 12, 2, 8, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 4, 0, 0, 8, 1, 3, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 4, 0, 9, 8, 1, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 0, 4, 3, 1, 8, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 9, 0, 4, 12, 1, 8, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer11x = 4; integer11x <= 8; integer11x++) {
				for (int integer12 = 0; integer12 <= 2; integer12++) {
					this.b(bqu, bvs.dV.n(), integer11x, -1, integer12, ctd);
					this.b(bqu, bvs.dV.n(), integer11x, -1, 12 - integer12, ctd);
				}
			}

			for (int integer11x = 0; integer11x <= 2; integer11x++) {
				for (int integer12 = 4; integer12 <= 8; integer12++) {
					this.b(bqu, bvs.dV.n(), integer11x, -1, integer12, ctd);
					this.b(bqu, bvs.dV.n(), 12 - integer11x, -1, integer12, ctd);
				}
			}

			this.a(bqu, ctd, 5, 5, 5, 7, 5, 7, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 6, 1, 6, 6, 4, 6, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, bvs.dV.n(), 6, 0, 6, ctd);
			this.a(bqu, bvs.B.n(), 6, 5, 6, ctd);
			fu fu11 = new fu(this.a(6, 6), this.d(5), this.b(6, 6));
			if (ctd.b(fu11)) {
				bqu.F().a(fu11, cxb.e, 0);
			}

			return true;
		}
	}

	public static class g extends ctl.m {
		public g(int integer, ctd ctd, fz fz) {
			super(cmm.m, integer);
			this.a(fz);
			this.n = ctd;
		}

		public g(cva cva, le le) {
			super(cmm.m, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctl.q)cty, list, random, 1, 0, true);
			this.b((ctl.q)cty, list, random, 0, 1, true);
			this.c((ctl.q)cty, list, random, 0, 1, true);
		}

		public static ctl.g a(List<cty> list, int integer2, int integer3, int integer4, fz fz, int integer6) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -1, 0, 0, 5, 7, 5, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctl.g(integer6, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 1, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 4, 5, 4, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 0, 5, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 4, 2, 0, 4, 5, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 4, 0, 5, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 4, 2, 4, 4, 5, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 6, 0, 4, 6, 4, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer9 = 0; integer9 <= 4; integer9++) {
				for (int integer10 = 0; integer10 <= 4; integer10++) {
					this.b(bqu, bvs.dV.n(), integer9, -1, integer10, ctd);
				}
			}

			return true;
		}
	}

	public static class h extends ctl.m {
		private boolean a;

		public h(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.n, integer);
			this.a(fz);
			this.n = ctd;
			this.a = random.nextInt(3) == 0;
		}

		public h(cva cva, le le) {
			super(cmm.n, le);
			this.a = le.q("Chest");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Chest", this.a);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.b((ctl.q)cty, list, random, 0, 1, true);
		}

		public static ctl.h a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, 0, 0, 5, 7, 5, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctl.h(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 1, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 4, 5, 4, bvs.a.n(), bvs.a.n(), false);
			cfj cfj9 = bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
			cfj cfj10 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
			this.a(bqu, ctd, 4, 2, 0, 4, 5, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 4, 3, 1, 4, 4, 1, cfj10, cfj10, false);
			this.a(bqu, ctd, 4, 3, 3, 4, 4, 3, cfj10, cfj10, false);
			this.a(bqu, ctd, 0, 2, 0, 0, 5, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 4, 3, 5, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 3, 4, 1, 4, 4, cfj9, cfj9, false);
			this.a(bqu, ctd, 3, 3, 4, 3, 4, 4, cfj9, cfj9, false);
			if (this.a && ctd.b(new fu(this.a(3, 3), this.d(2), this.b(3, 3)))) {
				this.a = false;
				this.a(bqu, ctd, random, 3, 2, 3, dao.v);
			}

			this.a(bqu, ctd, 0, 6, 0, 4, 6, 4, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer11 = 0; integer11 <= 4; integer11++) {
				for (int integer12 = 0; integer12 <= 4; integer12++) {
					this.b(bqu, bvs.dV.n(), integer11, -1, integer12, ctd);
				}
			}

			return true;
		}
	}

	public static class i extends ctl.m {
		public i(int integer, ctd ctd, fz fz) {
			super(cmm.o, integer);
			this.a(fz);
			this.n = ctd;
		}

		public i(cva cva, le le) {
			super(cmm.o, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctl.q)cty, list, random, 1, 0, true);
		}

		public static ctl.i a(List<cty> list, int integer2, int integer3, int integer4, fz fz, int integer6) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -1, 0, 0, 5, 7, 5, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctl.i(integer6, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 1, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 4, 5, 4, bvs.a.n(), bvs.a.n(), false);
			cfj cfj9 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
			this.a(bqu, ctd, 0, 2, 0, 0, 5, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 4, 2, 0, 4, 5, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 3, 1, 0, 4, 1, cfj9, cfj9, false);
			this.a(bqu, ctd, 0, 3, 3, 0, 4, 3, cfj9, cfj9, false);
			this.a(bqu, ctd, 4, 3, 1, 4, 4, 1, cfj9, cfj9, false);
			this.a(bqu, ctd, 4, 3, 3, 4, 4, 3, cfj9, cfj9, false);
			this.a(bqu, ctd, 0, 6, 0, 4, 6, 4, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer10 = 0; integer10 <= 4; integer10++) {
				for (int integer11 = 0; integer11 <= 4; integer11++) {
					this.b(bqu, bvs.dV.n(), integer10, -1, integer11, ctd);
				}
			}

			return true;
		}
	}

	public static class j extends ctl.m {
		private boolean a;

		public j(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.p, integer);
			this.a(fz);
			this.n = ctd;
			this.a = random.nextInt(3) == 0;
		}

		public j(cva cva, le le) {
			super(cmm.p, le);
			this.a = le.q("Chest");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Chest", this.a);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.c((ctl.q)cty, list, random, 0, 1, true);
		}

		public static ctl.j a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, 0, 0, 5, 7, 5, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctl.j(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 1, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 4, 5, 4, bvs.a.n(), bvs.a.n(), false);
			cfj cfj9 = bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
			cfj cfj10 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
			this.a(bqu, ctd, 0, 2, 0, 0, 5, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 3, 1, 0, 4, 1, cfj10, cfj10, false);
			this.a(bqu, ctd, 0, 3, 3, 0, 4, 3, cfj10, cfj10, false);
			this.a(bqu, ctd, 4, 2, 0, 4, 5, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 2, 4, 4, 5, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 3, 4, 1, 4, 4, cfj9, cfj9, false);
			this.a(bqu, ctd, 3, 3, 4, 3, 4, 4, cfj9, cfj9, false);
			if (this.a && ctd.b(new fu(this.a(1, 3), this.d(2), this.b(1, 3)))) {
				this.a = false;
				this.a(bqu, ctd, random, 1, 2, 3, dao.v);
			}

			this.a(bqu, ctd, 0, 6, 0, 4, 6, 4, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer11 = 0; integer11 <= 4; integer11++) {
				for (int integer12 = 0; integer12 <= 4; integer12++) {
					this.b(bqu, bvs.dV.n(), integer11, -1, integer12, ctd);
				}
			}

			return true;
		}
	}

	public static class k extends ctl.m {
		public k(int integer, ctd ctd, fz fz) {
			super(cmm.q, integer);
			this.a(fz);
			this.n = ctd;
		}

		public k(cva cva, le le) {
			super(cmm.q, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctl.q)cty, list, random, 5, 3, true);
			this.a((ctl.q)cty, list, random, 5, 11, true);
		}

		public static ctl.k a(List<cty> list, int integer2, int integer3, int integer4, fz fz, int integer6) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -5, -3, 0, 13, 14, 13, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctl.k(integer6, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 3, 0, 12, 4, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 5, 0, 12, 13, 12, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 0, 5, 0, 1, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 11, 5, 0, 12, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 5, 11, 4, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 8, 5, 11, 10, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 9, 11, 7, 12, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 5, 0, 4, 12, 1, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 8, 5, 0, 10, 12, 1, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 9, 0, 7, 12, 1, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 11, 2, 10, 12, 10, bvs.dV.n(), bvs.dV.n(), false);
			cfj cfj9 = bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
			cfj cfj10 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
			cfj cfj11 = cfj10.a(bxt.d, Boolean.valueOf(true));
			cfj cfj12 = cfj10.a(bxt.b, Boolean.valueOf(true));

			for (int integer13 = 1; integer13 <= 11; integer13 += 2) {
				this.a(bqu, ctd, integer13, 10, 0, integer13, 11, 0, cfj9, cfj9, false);
				this.a(bqu, ctd, integer13, 10, 12, integer13, 11, 12, cfj9, cfj9, false);
				this.a(bqu, ctd, 0, 10, integer13, 0, 11, integer13, cfj10, cfj10, false);
				this.a(bqu, ctd, 12, 10, integer13, 12, 11, integer13, cfj10, cfj10, false);
				this.a(bqu, bvs.dV.n(), integer13, 13, 0, ctd);
				this.a(bqu, bvs.dV.n(), integer13, 13, 12, ctd);
				this.a(bqu, bvs.dV.n(), 0, 13, integer13, ctd);
				this.a(bqu, bvs.dV.n(), 12, 13, integer13, ctd);
				if (integer13 != 11) {
					this.a(bqu, cfj9, integer13 + 1, 13, 0, ctd);
					this.a(bqu, cfj9, integer13 + 1, 13, 12, ctd);
					this.a(bqu, cfj10, 0, 13, integer13 + 1, ctd);
					this.a(bqu, cfj10, 12, 13, integer13 + 1, ctd);
				}
			}

			this.a(bqu, bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true)), 0, 13, 0, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.c, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true)), 0, 13, 12, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.c, Boolean.valueOf(true)).a(bxt.d, Boolean.valueOf(true)), 12, 13, 12, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.d, Boolean.valueOf(true)), 12, 13, 0, ctd);

			for (int integer13x = 3; integer13x <= 9; integer13x += 2) {
				this.a(bqu, ctd, 1, 7, integer13x, 1, 8, integer13x, cfj11, cfj11, false);
				this.a(bqu, ctd, 11, 7, integer13x, 11, 8, integer13x, cfj12, cfj12, false);
			}

			cfj cfj13 = bvs.dX.n().a(cbn.a, fz.NORTH);

			for (int integer14 = 0; integer14 <= 6; integer14++) {
				int integer15 = integer14 + 4;

				for (int integer16 = 5; integer16 <= 7; integer16++) {
					this.a(bqu, cfj13, integer16, 5 + integer14, integer15, ctd);
				}

				if (integer15 >= 5 && integer15 <= 8) {
					this.a(bqu, ctd, 5, 5, integer15, 7, integer14 + 4, integer15, bvs.dV.n(), bvs.dV.n(), false);
				} else if (integer15 >= 9 && integer15 <= 10) {
					this.a(bqu, ctd, 5, 8, integer15, 7, integer14 + 4, integer15, bvs.dV.n(), bvs.dV.n(), false);
				}

				if (integer14 >= 1) {
					this.a(bqu, ctd, 5, 6 + integer14, integer15, 7, 9 + integer14, integer15, bvs.a.n(), bvs.a.n(), false);
				}
			}

			for (int integer14 = 5; integer14 <= 7; integer14++) {
				this.a(bqu, cfj13, integer14, 12, 11, ctd);
			}

			this.a(bqu, ctd, 5, 6, 7, 5, 7, 7, cfj12, cfj12, false);
			this.a(bqu, ctd, 7, 6, 7, 7, 7, 7, cfj11, cfj11, false);
			this.a(bqu, ctd, 5, 13, 12, 7, 13, 12, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 2, 5, 2, 3, 5, 3, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 5, 9, 3, 5, 10, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 5, 4, 2, 5, 8, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 9, 5, 2, 10, 5, 3, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 9, 5, 9, 10, 5, 10, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 10, 5, 4, 10, 5, 8, bvs.dV.n(), bvs.dV.n(), false);
			cfj cfj14 = cfj13.a(cbn.a, fz.EAST);
			cfj cfj15 = cfj13.a(cbn.a, fz.WEST);
			this.a(bqu, cfj15, 4, 5, 2, ctd);
			this.a(bqu, cfj15, 4, 5, 3, ctd);
			this.a(bqu, cfj15, 4, 5, 9, ctd);
			this.a(bqu, cfj15, 4, 5, 10, ctd);
			this.a(bqu, cfj14, 8, 5, 2, ctd);
			this.a(bqu, cfj14, 8, 5, 3, ctd);
			this.a(bqu, cfj14, 8, 5, 9, ctd);
			this.a(bqu, cfj14, 8, 5, 10, ctd);
			this.a(bqu, ctd, 3, 4, 4, 4, 4, 8, bvs.cM.n(), bvs.cM.n(), false);
			this.a(bqu, ctd, 8, 4, 4, 9, 4, 8, bvs.cM.n(), bvs.cM.n(), false);
			this.a(bqu, ctd, 3, 5, 4, 4, 5, 8, bvs.dY.n(), bvs.dY.n(), false);
			this.a(bqu, ctd, 8, 5, 4, 9, 5, 8, bvs.dY.n(), bvs.dY.n(), false);
			this.a(bqu, ctd, 4, 2, 0, 8, 2, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 4, 12, 2, 8, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 4, 0, 0, 8, 1, 3, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 4, 0, 9, 8, 1, 12, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 0, 4, 3, 1, 8, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 9, 0, 4, 12, 1, 8, bvs.dV.n(), bvs.dV.n(), false);

			for (int integer16 = 4; integer16 <= 8; integer16++) {
				for (int integer17 = 0; integer17 <= 2; integer17++) {
					this.b(bqu, bvs.dV.n(), integer16, -1, integer17, ctd);
					this.b(bqu, bvs.dV.n(), integer16, -1, 12 - integer17, ctd);
				}
			}

			for (int integer16 = 0; integer16 <= 2; integer16++) {
				for (int integer17 = 4; integer17 <= 8; integer17++) {
					this.b(bqu, bvs.dV.n(), integer16, -1, integer17, ctd);
					this.b(bqu, bvs.dV.n(), 12 - integer16, -1, integer17, ctd);
				}
			}

			return true;
		}
	}

	public static class l extends ctl.m {
		private boolean a;

		public l(int integer, ctd ctd, fz fz) {
			super(cmm.r, integer);
			this.a(fz);
			this.n = ctd;
		}

		public l(cva cva, le le) {
			super(cmm.r, le);
			this.a = le.q("Mob");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Mob", this.a);
		}

		public static ctl.l a(List<cty> list, int integer2, int integer3, int integer4, int integer5, fz fz) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -2, 0, 0, 7, 8, 9, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctl.l(integer5, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 2, 0, 6, 7, 7, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 1, 0, 0, 5, 1, 7, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 2, 1, 5, 2, 7, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 3, 2, 5, 3, 7, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 4, 3, 5, 4, 7, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 2, 0, 1, 4, 2, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 2, 0, 5, 4, 2, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 5, 2, 1, 5, 3, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 5, 2, 5, 5, 3, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 5, 3, 0, 5, 8, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 6, 5, 3, 6, 5, 8, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 5, 8, 5, 5, 8, bvs.dV.n(), bvs.dV.n(), false);
			cfj cfj9 = bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
			cfj cfj10 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
			this.a(bqu, bvs.dW.n().a(bxt.d, Boolean.valueOf(true)), 1, 6, 3, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.b, Boolean.valueOf(true)), 5, 6, 3, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.b, Boolean.valueOf(true)).a(bxt.a, Boolean.valueOf(true)), 0, 6, 3, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.a, Boolean.valueOf(true)), 6, 6, 3, ctd);
			this.a(bqu, ctd, 0, 6, 4, 0, 6, 7, cfj10, cfj10, false);
			this.a(bqu, ctd, 6, 6, 4, 6, 6, 7, cfj10, cfj10, false);
			this.a(bqu, bvs.dW.n().a(bxt.b, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true)), 0, 6, 8, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true)), 6, 6, 8, ctd);
			this.a(bqu, ctd, 1, 6, 8, 5, 6, 8, cfj9, cfj9, false);
			this.a(bqu, bvs.dW.n().a(bxt.b, Boolean.valueOf(true)), 1, 7, 8, ctd);
			this.a(bqu, ctd, 2, 7, 8, 4, 7, 8, cfj9, cfj9, false);
			this.a(bqu, bvs.dW.n().a(bxt.d, Boolean.valueOf(true)), 5, 7, 8, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.b, Boolean.valueOf(true)), 2, 8, 8, ctd);
			this.a(bqu, cfj9, 3, 8, 8, ctd);
			this.a(bqu, bvs.dW.n().a(bxt.d, Boolean.valueOf(true)), 4, 8, 8, ctd);
			if (!this.a) {
				fu fu11 = new fu(this.a(3, 5), this.d(5), this.b(3, 5));
				if (ctd.b(fu11)) {
					this.a = true;
					bqu.a(fu11, bvs.bP.n(), 2);
					cdl cdl12 = bqu.c(fu11);
					if (cdl12 instanceof cek) {
						((cek)cdl12).d().a(aoq.f);
					}
				}
			}

			for (int integer11 = 0; integer11 <= 6; integer11++) {
				for (int integer12 = 0; integer12 <= 6; integer12++) {
					this.b(bqu, bvs.dV.n(), integer11, -1, integer12, ctd);
				}
			}

			return true;
		}
	}

	abstract static class m extends cty {
		protected m(cmm cmm, int integer) {
			super(cmm, integer);
		}

		public m(cmm cmm, le le) {
			super(cmm, le);
		}

		@Override
		protected void a(le le) {
		}

		private int a(List<ctl.n> list) {
			boolean boolean3 = false;
			int integer4 = 0;

			for (ctl.n n6 : list) {
				if (n6.d > 0 && n6.c < n6.d) {
					boolean3 = true;
				}

				integer4 += n6.b;
			}

			return boolean3 ? integer4 : -1;
		}

		private ctl.m a(ctl.q q, List<ctl.n> list2, List<cty> list3, Random random, int integer5, int integer6, int integer7, fz fz, int integer9) {
			int integer11 = this.a(list2);
			boolean boolean12 = integer11 > 0 && integer9 <= 30;
			int integer13 = 0;

			while (integer13 < 5 && boolean12) {
				integer13++;
				int integer14 = random.nextInt(integer11);

				for (ctl.n n16 : list2) {
					integer14 -= n16.b;
					if (integer14 < 0) {
						if (!n16.a(integer9) || n16 == q.a && !n16.e) {
							break;
						}

						ctl.m m17 = ctl.b(n16, list3, random, integer5, integer6, integer7, fz, integer9);
						if (m17 != null) {
							n16.c++;
							q.a = n16;
							if (!n16.a()) {
								list2.remove(n16);
							}

							return m17;
						}
					}
				}
			}

			return ctl.b.a(list3, random, integer5, integer6, integer7, fz, integer9);
		}

		private cty a(ctl.q q, List<cty> list, Random random, int integer4, int integer5, int integer6, @Nullable fz fz, int integer8, boolean boolean9) {
			if (Math.abs(integer4 - q.g().a) <= 112 && Math.abs(integer6 - q.g().c) <= 112) {
				List<ctl.n> list11 = q.b;
				if (boolean9) {
					list11 = q.c;
				}

				cty cty12 = this.a(q, list11, list, random, integer4, integer5, integer6, fz, integer8 + 1);
				if (cty12 != null) {
					list.add(cty12);
					q.d.add(cty12);
				}

				return cty12;
			} else {
				return ctl.b.a(list, random, integer4, integer5, integer6, fz, integer8);
			}
		}

		@Nullable
		protected cty a(ctl.q q, List<cty> list, Random random, int integer4, int integer5, boolean boolean6) {
			fz fz8 = this.i();
			if (fz8 != null) {
				switch (fz8) {
					case NORTH:
						return this.a(q, list, random, this.n.a + integer4, this.n.b + integer5, this.n.c - 1, fz8, this.h(), boolean6);
					case SOUTH:
						return this.a(q, list, random, this.n.a + integer4, this.n.b + integer5, this.n.f + 1, fz8, this.h(), boolean6);
					case WEST:
						return this.a(q, list, random, this.n.a - 1, this.n.b + integer5, this.n.c + integer4, fz8, this.h(), boolean6);
					case EAST:
						return this.a(q, list, random, this.n.d + 1, this.n.b + integer5, this.n.c + integer4, fz8, this.h(), boolean6);
				}
			}

			return null;
		}

		@Nullable
		protected cty b(ctl.q q, List<cty> list, Random random, int integer4, int integer5, boolean boolean6) {
			fz fz8 = this.i();
			if (fz8 != null) {
				switch (fz8) {
					case NORTH:
						return this.a(q, list, random, this.n.a - 1, this.n.b + integer4, this.n.c + integer5, fz.WEST, this.h(), boolean6);
					case SOUTH:
						return this.a(q, list, random, this.n.a - 1, this.n.b + integer4, this.n.c + integer5, fz.WEST, this.h(), boolean6);
					case WEST:
						return this.a(q, list, random, this.n.a + integer5, this.n.b + integer4, this.n.c - 1, fz.NORTH, this.h(), boolean6);
					case EAST:
						return this.a(q, list, random, this.n.a + integer5, this.n.b + integer4, this.n.c - 1, fz.NORTH, this.h(), boolean6);
				}
			}

			return null;
		}

		@Nullable
		protected cty c(ctl.q q, List<cty> list, Random random, int integer4, int integer5, boolean boolean6) {
			fz fz8 = this.i();
			if (fz8 != null) {
				switch (fz8) {
					case NORTH:
						return this.a(q, list, random, this.n.d + 1, this.n.b + integer4, this.n.c + integer5, fz.EAST, this.h(), boolean6);
					case SOUTH:
						return this.a(q, list, random, this.n.d + 1, this.n.b + integer4, this.n.c + integer5, fz.EAST, this.h(), boolean6);
					case WEST:
						return this.a(q, list, random, this.n.a + integer5, this.n.b + integer4, this.n.f + 1, fz.SOUTH, this.h(), boolean6);
					case EAST:
						return this.a(q, list, random, this.n.a + integer5, this.n.b + integer4, this.n.f + 1, fz.SOUTH, this.h(), boolean6);
				}
			}

			return null;
		}

		protected static boolean a(ctd ctd) {
			return ctd != null && ctd.b > 10;
		}
	}

	static class n {
		public final Class<? extends ctl.m> a;
		public final int b;
		public int c;
		public final int d;
		public final boolean e;

		public n(Class<? extends ctl.m> class1, int integer2, int integer3, boolean boolean4) {
			this.a = class1;
			this.b = integer2;
			this.d = integer3;
			this.e = boolean4;
		}

		public n(Class<? extends ctl.m> class1, int integer2, int integer3) {
			this(class1, integer2, integer3, false);
		}

		public boolean a(int integer) {
			return this.d == 0 || this.c < this.d;
		}

		public boolean a() {
			return this.d == 0 || this.c < this.d;
		}
	}

	public static class o extends ctl.m {
		public o(int integer, ctd ctd, fz fz) {
			super(cmm.s, integer);
			this.a(fz);
			this.n = ctd;
		}

		public o(cva cva, le le) {
			super(cmm.s, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctl.q)cty, list, random, 2, 0, false);
			this.b((ctl.q)cty, list, random, 0, 2, false);
			this.c((ctl.q)cty, list, random, 0, 2, false);
		}

		public static ctl.o a(List<cty> list, int integer2, int integer3, int integer4, fz fz, int integer6) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -2, 0, 0, 7, 9, 7, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctl.o(integer6, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 6, 1, 6, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 6, 7, 6, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 1, 6, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 6, 1, 6, 6, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 2, 0, 6, 6, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 2, 6, 6, 6, 6, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 0, 6, 1, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 5, 0, 6, 6, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 6, 2, 0, 6, 6, 1, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 6, 2, 5, 6, 6, 6, bvs.dV.n(), bvs.dV.n(), false);
			cfj cfj9 = bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
			cfj cfj10 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
			this.a(bqu, ctd, 2, 6, 0, 4, 6, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 5, 0, 4, 5, 0, cfj9, cfj9, false);
			this.a(bqu, ctd, 2, 6, 6, 4, 6, 6, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 5, 6, 4, 5, 6, cfj9, cfj9, false);
			this.a(bqu, ctd, 0, 6, 2, 0, 6, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 5, 2, 0, 5, 4, cfj10, cfj10, false);
			this.a(bqu, ctd, 6, 6, 2, 6, 6, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 6, 5, 2, 6, 5, 4, cfj10, cfj10, false);

			for (int integer11 = 0; integer11 <= 6; integer11++) {
				for (int integer12 = 0; integer12 <= 6; integer12++) {
					this.b(bqu, bvs.dV.n(), integer11, -1, integer12, ctd);
				}
			}

			return true;
		}
	}

	public static class p extends ctl.m {
		public p(int integer, ctd ctd, fz fz) {
			super(cmm.t, integer);
			this.a(fz);
			this.n = ctd;
		}

		public p(cva cva, le le) {
			super(cmm.t, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.c((ctl.q)cty, list, random, 6, 2, false);
		}

		public static ctl.p a(List<cty> list, int integer2, int integer3, int integer4, int integer5, fz fz) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -2, 0, 0, 7, 11, 7, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctl.p(integer5, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 6, 1, 6, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 6, 10, 6, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 0, 2, 0, 1, 8, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 5, 2, 0, 6, 8, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 0, 2, 1, 0, 8, 6, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 6, 2, 1, 6, 8, 6, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 2, 6, 5, 8, 6, bvs.dV.n(), bvs.dV.n(), false);
			cfj cfj9 = bvs.dW.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
			cfj cfj10 = bvs.dW.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
			this.a(bqu, ctd, 0, 3, 2, 0, 5, 4, cfj10, cfj10, false);
			this.a(bqu, ctd, 6, 3, 2, 6, 5, 2, cfj10, cfj10, false);
			this.a(bqu, ctd, 6, 3, 4, 6, 5, 4, cfj10, cfj10, false);
			this.a(bqu, bvs.dV.n(), 5, 2, 5, ctd);
			this.a(bqu, ctd, 4, 2, 5, 4, 3, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 3, 2, 5, 3, 4, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 2, 5, 2, 5, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 2, 5, 1, 6, 5, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 1, 7, 1, 5, 7, 4, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 6, 8, 2, 6, 8, 4, bvs.a.n(), bvs.a.n(), false);
			this.a(bqu, ctd, 2, 6, 0, 4, 8, 0, bvs.dV.n(), bvs.dV.n(), false);
			this.a(bqu, ctd, 2, 5, 0, 4, 5, 0, cfj9, cfj9, false);

			for (int integer11 = 0; integer11 <= 6; integer11++) {
				for (int integer12 = 0; integer12 <= 6; integer12++) {
					this.b(bqu, bvs.dV.n(), integer11, -1, integer12, ctd);
				}
			}

			return true;
		}
	}

	public static class q extends ctl.a {
		public ctl.n a;
		public List<ctl.n> b;
		public List<ctl.n> c;
		public final List<cty> d = Lists.<cty>newArrayList();

		public q(Random random, int integer2, int integer3) {
			super(random, integer2, integer3);
			this.b = Lists.<ctl.n>newArrayList();

			for (ctl.n n8 : ctl.a) {
				n8.c = 0;
				this.b.add(n8);
			}

			this.c = Lists.<ctl.n>newArrayList();

			for (ctl.n n8 : ctl.b) {
				n8.c = 0;
				this.c.add(n8);
			}
		}

		public q(cva cva, le le) {
			super(cmm.u, le);
		}
	}
}
