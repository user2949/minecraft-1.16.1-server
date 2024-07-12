import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class ctw {
	private static final ctw.f[] a = new ctw.f[]{
		new ctw.f(ctw.n.class, 40, 0),
		new ctw.f(ctw.h.class, 5, 5),
		new ctw.f(ctw.d.class, 20, 0),
		new ctw.f(ctw.i.class, 20, 0),
		new ctw.f(ctw.j.class, 10, 6),
		new ctw.f(ctw.o.class, 5, 5),
		new ctw.f(ctw.l.class, 5, 5),
		new ctw.f(ctw.c.class, 5, 4),
		new ctw.f(ctw.a.class, 5, 4),
		new ctw.f(ctw.e.class, 10, 2) {
			@Override
			public boolean a(int integer) {
				return super.a(integer) && integer > 4;
			}
		},
		new ctw.f(ctw.g.class, 20, 1) {
			@Override
			public boolean a(int integer) {
				return super.a(integer) && integer > 5;
			}
		}
	};
	private static List<ctw.f> b;
	private static Class<? extends ctw.p> c;
	private static int d;
	private static final ctw.k e = new ctw.k();

	public static void a() {
		b = Lists.<ctw.f>newArrayList();

		for (ctw.f f4 : a) {
			f4.c = 0;
			b.add(f4);
		}

		c = null;
	}

	private static boolean c() {
		boolean boolean1 = false;
		d = 0;

		for (ctw.f f3 : b) {
			if (f3.d > 0 && f3.c < f3.d) {
				boolean1 = true;
			}

			d = d + f3.b;
		}

		return boolean1;
	}

	private static ctw.p a(Class<? extends ctw.p> class1, List<cty> list, Random random, int integer4, int integer5, int integer6, @Nullable fz fz, int integer8) {
		ctw.p p9 = null;
		if (class1 == ctw.n.class) {
			p9 = ctw.n.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.h.class) {
			p9 = ctw.h.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.d.class) {
			p9 = ctw.d.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.i.class) {
			p9 = ctw.i.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.j.class) {
			p9 = ctw.j.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.o.class) {
			p9 = ctw.o.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.l.class) {
			p9 = ctw.l.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.c.class) {
			p9 = ctw.c.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.a.class) {
			p9 = ctw.a.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.e.class) {
			p9 = ctw.e.a(list, random, integer4, integer5, integer6, fz, integer8);
		} else if (class1 == ctw.g.class) {
			p9 = ctw.g.a(list, integer4, integer5, integer6, fz, integer8);
		}

		return p9;
	}

	private static ctw.p b(ctw.m m, List<cty> list, Random random, int integer4, int integer5, int integer6, fz fz, int integer8) {
		if (!c()) {
			return null;
		} else {
			if (c != null) {
				ctw.p p9 = a(c, list, random, integer4, integer5, integer6, fz, integer8);
				c = null;
				if (p9 != null) {
					return p9;
				}
			}

			int integer9 = 0;

			while (integer9 < 5) {
				integer9++;
				int integer10 = random.nextInt(d);

				for (ctw.f f12 : b) {
					integer10 -= f12.b;
					if (integer10 < 0) {
						if (!f12.a(integer8) || f12 == m.a) {
							break;
						}

						ctw.p p13 = a(f12.a, list, random, integer4, integer5, integer6, fz, integer8);
						if (p13 != null) {
							f12.c++;
							m.a = f12;
							if (!f12.a()) {
								b.remove(f12);
							}

							return p13;
						}
					}
				}
			}

			ctd ctd10 = ctw.b.a(list, random, integer4, integer5, integer6, fz);
			return ctd10 != null && ctd10.b > 1 ? new ctw.b(integer8, ctd10, fz) : null;
		}
	}

	private static cty c(ctw.m m, List<cty> list, Random random, int integer4, int integer5, int integer6, @Nullable fz fz, int integer8) {
		if (integer8 > 50) {
			return null;
		} else if (Math.abs(integer4 - m.g().a) <= 112 && Math.abs(integer6 - m.g().c) <= 112) {
			cty cty9 = b(m, list, random, integer4, integer5, integer6, fz, integer8 + 1);
			if (cty9 != null) {
				list.add(cty9);
				m.c.add(cty9);
			}

			return cty9;
		} else {
			return null;
		}
	}

	public static class a extends ctw.p {
		private boolean a;

		public a(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.v, integer);
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
		}

		public a(cva cva, le le) {
			super(cmm.v, le);
			this.a = le.q("Chest");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Chest", this.a);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctw.m)cty, list, random, 1, 1);
		}

		public static ctw.a a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -1, 0, 5, 5, 7, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctw.a(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 4, 6, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 1, 1, 0);
			this.a(bqu, random, ctd, ctw.p.a.OPENING, 1, 1, 6);
			this.a(bqu, ctd, 3, 1, 2, 3, 1, 4, bvs.du.n(), bvs.du.n(), false);
			this.a(bqu, bvs.hX.n(), 3, 1, 1, ctd);
			this.a(bqu, bvs.hX.n(), 3, 1, 5, ctd);
			this.a(bqu, bvs.hX.n(), 3, 2, 2, ctd);
			this.a(bqu, bvs.hX.n(), 3, 2, 4, ctd);

			for (int integer9 = 2; integer9 <= 4; integer9++) {
				this.a(bqu, bvs.hX.n(), 2, 1, integer9, ctd);
			}

			if (!this.a && ctd.b(new fu(this.a(3, 3), this.d(2), this.b(3, 3)))) {
				this.a = true;
				this.a(bqu, ctd, random, 3, 2, 3, dao.y);
			}

			return true;
		}
	}

	public static class b extends ctw.p {
		private final int a;

		public b(int integer, ctd ctd, fz fz) {
			super(cmm.w, integer);
			this.a(fz);
			this.n = ctd;
			this.a = fz != fz.NORTH && fz != fz.SOUTH ? ctd.d() : ctd.f();
		}

		public b(cva cva, le le) {
			super(cmm.w, le);
			this.a = le.h("Steps");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.b("Steps", this.a);
		}

		public static ctd a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz) {
			int integer7 = 3;
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -1, 0, 5, 5, 4, fz);
			cty cty9 = cty.a(list, ctd8);
			if (cty9 == null) {
				return null;
			} else {
				if (cty9.g().b == ctd8.b) {
					for (int integer10 = 3; integer10 >= 1; integer10--) {
						ctd8 = ctd.a(integer3, integer4, integer5, -1, -1, 0, 5, 5, integer10 - 1, fz);
						if (!cty9.g().b(ctd8)) {
							return ctd.a(integer3, integer4, integer5, -1, -1, 0, 5, 5, integer10, fz);
						}
					}
				}

				return null;
			}
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			for (int integer9 = 0; integer9 < this.a; integer9++) {
				this.a(bqu, bvs.du.n(), 0, 0, integer9, ctd);
				this.a(bqu, bvs.du.n(), 1, 0, integer9, ctd);
				this.a(bqu, bvs.du.n(), 2, 0, integer9, ctd);
				this.a(bqu, bvs.du.n(), 3, 0, integer9, ctd);
				this.a(bqu, bvs.du.n(), 4, 0, integer9, ctd);

				for (int integer10 = 1; integer10 <= 3; integer10++) {
					this.a(bqu, bvs.du.n(), 0, integer10, integer9, ctd);
					this.a(bqu, bvs.lb.n(), 1, integer10, integer9, ctd);
					this.a(bqu, bvs.lb.n(), 2, integer10, integer9, ctd);
					this.a(bqu, bvs.lb.n(), 3, integer10, integer9, ctd);
					this.a(bqu, bvs.du.n(), 4, integer10, integer9, ctd);
				}

				this.a(bqu, bvs.du.n(), 0, 4, integer9, ctd);
				this.a(bqu, bvs.du.n(), 1, 4, integer9, ctd);
				this.a(bqu, bvs.du.n(), 2, 4, integer9, ctd);
				this.a(bqu, bvs.du.n(), 3, 4, integer9, ctd);
				this.a(bqu, bvs.du.n(), 4, 4, integer9, ctd);
			}

			return true;
		}
	}

	public static class c extends ctw.p {
		private final boolean a;
		private final boolean b;
		private final boolean c;
		private final boolean e;

		public c(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.x, integer);
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
			this.a = random.nextBoolean();
			this.b = random.nextBoolean();
			this.c = random.nextBoolean();
			this.e = random.nextInt(3) > 0;
		}

		public c(cva cva, le le) {
			super(cmm.x, le);
			this.a = le.q("leftLow");
			this.b = le.q("leftHigh");
			this.c = le.q("rightLow");
			this.e = le.q("rightHigh");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("leftLow", this.a);
			le.a("leftHigh", this.b);
			le.a("rightLow", this.c);
			le.a("rightHigh", this.e);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			int integer5 = 3;
			int integer6 = 5;
			fz fz7 = this.i();
			if (fz7 == fz.WEST || fz7 == fz.NORTH) {
				integer5 = 8 - integer5;
				integer6 = 8 - integer6;
			}

			this.a((ctw.m)cty, list, random, 5, 1);
			if (this.a) {
				this.b((ctw.m)cty, list, random, integer5, 1);
			}

			if (this.b) {
				this.b((ctw.m)cty, list, random, integer6, 7);
			}

			if (this.c) {
				this.c((ctw.m)cty, list, random, integer5, 1);
			}

			if (this.e) {
				this.c((ctw.m)cty, list, random, integer6, 7);
			}
		}

		public static ctw.c a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -4, -3, 0, 10, 9, 11, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctw.c(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 9, 8, 10, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 4, 3, 0);
			if (this.a) {
				this.a(bqu, ctd, 0, 3, 1, 0, 5, 3, m, m, false);
			}

			if (this.c) {
				this.a(bqu, ctd, 9, 3, 1, 9, 5, 3, m, m, false);
			}

			if (this.b) {
				this.a(bqu, ctd, 0, 5, 7, 0, 7, 9, m, m, false);
			}

			if (this.e) {
				this.a(bqu, ctd, 9, 5, 7, 9, 7, 9, m, m, false);
			}

			this.a(bqu, ctd, 5, 1, 10, 7, 3, 10, m, m, false);
			this.a(bqu, ctd, 1, 2, 1, 8, 2, 6, false, random, ctw.e);
			this.a(bqu, ctd, 4, 1, 5, 4, 4, 9, false, random, ctw.e);
			this.a(bqu, ctd, 8, 1, 5, 8, 4, 9, false, random, ctw.e);
			this.a(bqu, ctd, 1, 4, 7, 3, 4, 9, false, random, ctw.e);
			this.a(bqu, ctd, 1, 3, 5, 3, 3, 6, false, random, ctw.e);
			this.a(bqu, ctd, 1, 3, 4, 3, 3, 4, bvs.hR.n(), bvs.hR.n(), false);
			this.a(bqu, ctd, 1, 4, 6, 3, 4, 6, bvs.hR.n(), bvs.hR.n(), false);
			this.a(bqu, ctd, 5, 1, 7, 7, 1, 8, false, random, ctw.e);
			this.a(bqu, ctd, 5, 1, 9, 7, 1, 9, bvs.hR.n(), bvs.hR.n(), false);
			this.a(bqu, ctd, 5, 2, 7, 7, 2, 7, bvs.hR.n(), bvs.hR.n(), false);
			this.a(bqu, ctd, 4, 5, 7, 4, 5, 9, bvs.hR.n(), bvs.hR.n(), false);
			this.a(bqu, ctd, 8, 5, 7, 8, 5, 9, bvs.hR.n(), bvs.hR.n(), false);
			this.a(bqu, ctd, 5, 5, 7, 7, 5, 9, bvs.hR.n().a(caz.a, cgo.DOUBLE), bvs.hR.n().a(caz.a, cgo.DOUBLE), false);
			this.a(bqu, bvs.bM.n().a(ccp.a, fz.SOUTH), 6, 5, 6, ctd);
			return true;
		}
	}

	public static class d extends ctw.q {
		public d(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.y, integer);
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
		}

		public d(cva cva, le le) {
			super(cmm.y, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			fz fz5 = this.i();
			if (fz5 != fz.NORTH && fz5 != fz.EAST) {
				this.c((ctw.m)cty, list, random, 1, 1);
			} else {
				this.b((ctw.m)cty, list, random, 1, 1);
			}
		}

		public static ctw.d a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -1, 0, 5, 5, 5, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctw.d(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 4, 4, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 1, 1, 0);
			fz fz9 = this.i();
			if (fz9 != fz.NORTH && fz9 != fz.EAST) {
				this.a(bqu, ctd, 4, 1, 1, 4, 3, 3, m, m, false);
			} else {
				this.a(bqu, ctd, 0, 1, 1, 0, 3, 3, m, m, false);
			}

			return true;
		}
	}

	public static class e extends ctw.p {
		private final boolean a;

		public e(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.z, integer);
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
			this.a = ctd.e() > 6;
		}

		public e(cva cva, le le) {
			super(cmm.z, le);
			this.a = le.q("Tall");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Tall", this.a);
		}

		public static ctw.e a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -4, -1, 0, 14, 11, 15, fz);
			if (!a(ctd8) || cty.a(list, ctd8) != null) {
				ctd8 = ctd.a(integer3, integer4, integer5, -4, -1, 0, 14, 6, 15, fz);
				if (!a(ctd8) || cty.a(list, ctd8) != null) {
					return null;
				}
			}

			return new ctw.e(integer7, random, ctd8, fz);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			int integer9 = 11;
			if (!this.a) {
				integer9 = 6;
			}

			this.a(bqu, ctd, 0, 0, 0, 13, integer9 - 1, 14, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 4, 1, 0);
			this.a(bqu, ctd, random, 0.07F, 2, 1, 1, 11, 4, 13, bvs.aQ.n(), bvs.aQ.n(), false, false);
			int integer10 = 1;
			int integer11 = 12;

			for (int integer12 = 1; integer12 <= 13; integer12++) {
				if ((integer12 - 1) % 4 == 0) {
					this.a(bqu, ctd, 1, 1, integer12, 1, 4, integer12, bvs.n.n(), bvs.n.n(), false);
					this.a(bqu, ctd, 12, 1, integer12, 12, 4, integer12, bvs.n.n(), bvs.n.n(), false);
					this.a(bqu, bvs.bM.n().a(ccp.a, fz.EAST), 2, 3, integer12, ctd);
					this.a(bqu, bvs.bM.n().a(ccp.a, fz.WEST), 11, 3, integer12, ctd);
					if (this.a) {
						this.a(bqu, ctd, 1, 6, integer12, 1, 9, integer12, bvs.n.n(), bvs.n.n(), false);
						this.a(bqu, ctd, 12, 6, integer12, 12, 9, integer12, bvs.n.n(), bvs.n.n(), false);
					}
				} else {
					this.a(bqu, ctd, 1, 1, integer12, 1, 4, integer12, bvs.bI.n(), bvs.bI.n(), false);
					this.a(bqu, ctd, 12, 1, integer12, 12, 4, integer12, bvs.bI.n(), bvs.bI.n(), false);
					if (this.a) {
						this.a(bqu, ctd, 1, 6, integer12, 1, 9, integer12, bvs.bI.n(), bvs.bI.n(), false);
						this.a(bqu, ctd, 12, 6, integer12, 12, 9, integer12, bvs.bI.n(), bvs.bI.n(), false);
					}
				}
			}

			for (int integer12x = 3; integer12x < 12; integer12x += 2) {
				this.a(bqu, ctd, 3, 1, integer12x, 4, 3, integer12x, bvs.bI.n(), bvs.bI.n(), false);
				this.a(bqu, ctd, 6, 1, integer12x, 7, 3, integer12x, bvs.bI.n(), bvs.bI.n(), false);
				this.a(bqu, ctd, 9, 1, integer12x, 10, 3, integer12x, bvs.bI.n(), bvs.bI.n(), false);
			}

			if (this.a) {
				this.a(bqu, ctd, 1, 5, 1, 3, 5, 13, bvs.n.n(), bvs.n.n(), false);
				this.a(bqu, ctd, 10, 5, 1, 12, 5, 13, bvs.n.n(), bvs.n.n(), false);
				this.a(bqu, ctd, 4, 5, 1, 9, 5, 2, bvs.n.n(), bvs.n.n(), false);
				this.a(bqu, ctd, 4, 5, 12, 9, 5, 13, bvs.n.n(), bvs.n.n(), false);
				this.a(bqu, bvs.n.n(), 9, 5, 11, ctd);
				this.a(bqu, bvs.n.n(), 8, 5, 11, ctd);
				this.a(bqu, bvs.n.n(), 9, 5, 10, ctd);
				cfj cfj12 = bvs.cJ.n().a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
				cfj cfj13 = bvs.cJ.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.c, Boolean.valueOf(true));
				this.a(bqu, ctd, 3, 6, 3, 3, 6, 11, cfj13, cfj13, false);
				this.a(bqu, ctd, 10, 6, 3, 10, 6, 9, cfj13, cfj13, false);
				this.a(bqu, ctd, 4, 6, 2, 9, 6, 2, cfj12, cfj12, false);
				this.a(bqu, ctd, 4, 6, 12, 7, 6, 12, cfj12, cfj12, false);
				this.a(bqu, bvs.cJ.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true)), 3, 6, 2, ctd);
				this.a(bqu, bvs.cJ.n().a(bxt.c, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true)), 3, 6, 12, ctd);
				this.a(bqu, bvs.cJ.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.d, Boolean.valueOf(true)), 10, 6, 2, ctd);

				for (int integer14 = 0; integer14 <= 2; integer14++) {
					this.a(bqu, bvs.cJ.n().a(bxt.c, Boolean.valueOf(true)).a(bxt.d, Boolean.valueOf(true)), 8 + integer14, 6, 12 - integer14, ctd);
					if (integer14 != 2) {
						this.a(bqu, bvs.cJ.n().a(bxt.a, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true)), 8 + integer14, 6, 11 - integer14, ctd);
					}
				}

				cfj cfj14 = bvs.cg.n().a(byy.a, fz.SOUTH);
				this.a(bqu, cfj14, 10, 1, 13, ctd);
				this.a(bqu, cfj14, 10, 2, 13, ctd);
				this.a(bqu, cfj14, 10, 3, 13, ctd);
				this.a(bqu, cfj14, 10, 4, 13, ctd);
				this.a(bqu, cfj14, 10, 5, 13, ctd);
				this.a(bqu, cfj14, 10, 6, 13, ctd);
				this.a(bqu, cfj14, 10, 7, 13, ctd);
				int integer15 = 7;
				int integer16 = 7;
				cfj cfj17 = bvs.cJ.n().a(bxt.b, Boolean.valueOf(true));
				this.a(bqu, cfj17, 6, 9, 7, ctd);
				cfj cfj18 = bvs.cJ.n().a(bxt.d, Boolean.valueOf(true));
				this.a(bqu, cfj18, 7, 9, 7, ctd);
				this.a(bqu, cfj17, 6, 8, 7, ctd);
				this.a(bqu, cfj18, 7, 8, 7, ctd);
				cfj cfj19 = cfj13.a(bxt.d, Boolean.valueOf(true)).a(bxt.b, Boolean.valueOf(true));
				this.a(bqu, cfj19, 6, 7, 7, ctd);
				this.a(bqu, cfj19, 7, 7, 7, ctd);
				this.a(bqu, cfj17, 5, 7, 7, ctd);
				this.a(bqu, cfj18, 8, 7, 7, ctd);
				this.a(bqu, cfj17.a(bxt.a, Boolean.valueOf(true)), 6, 7, 6, ctd);
				this.a(bqu, cfj17.a(bxt.c, Boolean.valueOf(true)), 6, 7, 8, ctd);
				this.a(bqu, cfj18.a(bxt.a, Boolean.valueOf(true)), 7, 7, 6, ctd);
				this.a(bqu, cfj18.a(bxt.c, Boolean.valueOf(true)), 7, 7, 8, ctd);
				cfj cfj20 = bvs.bL.n();
				this.a(bqu, cfj20, 5, 8, 7, ctd);
				this.a(bqu, cfj20, 8, 8, 7, ctd);
				this.a(bqu, cfj20, 6, 8, 6, ctd);
				this.a(bqu, cfj20, 6, 8, 8, ctd);
				this.a(bqu, cfj20, 7, 8, 6, ctd);
				this.a(bqu, cfj20, 7, 8, 8, ctd);
			}

			this.a(bqu, ctd, random, 3, 3, 5, dao.w);
			if (this.a) {
				this.a(bqu, m, 12, 9, 1, ctd);
				this.a(bqu, ctd, random, 12, 8, 1, dao.w);
			}

			return true;
		}
	}

	static class f {
		public final Class<? extends ctw.p> a;
		public final int b;
		public int c;
		public final int d;

		public f(Class<? extends ctw.p> class1, int integer2, int integer3) {
			this.a = class1;
			this.b = integer2;
			this.d = integer3;
		}

		public boolean a(int integer) {
			return this.d == 0 || this.c < this.d;
		}

		public boolean a() {
			return this.d == 0 || this.c < this.d;
		}
	}

	public static class g extends ctw.p {
		private boolean a;

		public g(int integer, ctd ctd, fz fz) {
			super(cmm.A, integer);
			this.a(fz);
			this.n = ctd;
		}

		public g(cva cva, le le) {
			super(cmm.A, le);
			this.a = le.q("Mob");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Mob", this.a);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			if (cty != null) {
				((ctw.m)cty).b = this;
			}
		}

		public static ctw.g a(List<cty> list, int integer2, int integer3, int integer4, fz fz, int integer6) {
			ctd ctd7 = ctd.a(integer2, integer3, integer4, -4, -1, 0, 11, 8, 16, fz);
			return a(ctd7) && cty.a(list, ctd7) == null ? new ctw.g(integer6, ctd7, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 10, 7, 15, false, random, ctw.e);
			this.a(bqu, random, ctd, ctw.p.a.GRATES, 4, 1, 0);
			int integer9 = 6;
			this.a(bqu, ctd, 1, integer9, 1, 1, integer9, 14, false, random, ctw.e);
			this.a(bqu, ctd, 9, integer9, 1, 9, integer9, 14, false, random, ctw.e);
			this.a(bqu, ctd, 2, integer9, 1, 8, integer9, 2, false, random, ctw.e);
			this.a(bqu, ctd, 2, integer9, 14, 8, integer9, 14, false, random, ctw.e);
			this.a(bqu, ctd, 1, 1, 1, 2, 1, 4, false, random, ctw.e);
			this.a(bqu, ctd, 8, 1, 1, 9, 1, 4, false, random, ctw.e);
			this.a(bqu, ctd, 1, 1, 1, 1, 1, 3, bvs.B.n(), bvs.B.n(), false);
			this.a(bqu, ctd, 9, 1, 1, 9, 1, 3, bvs.B.n(), bvs.B.n(), false);
			this.a(bqu, ctd, 3, 1, 8, 7, 1, 12, false, random, ctw.e);
			this.a(bqu, ctd, 4, 1, 9, 6, 1, 11, bvs.B.n(), bvs.B.n(), false);
			cfj cfj10 = bvs.dH.n().a(byt.a, Boolean.valueOf(true)).a(byt.c, Boolean.valueOf(true));
			cfj cfj11 = bvs.dH.n().a(byt.d, Boolean.valueOf(true)).a(byt.b, Boolean.valueOf(true));

			for (int integer12 = 3; integer12 < 14; integer12 += 2) {
				this.a(bqu, ctd, 0, 3, integer12, 0, 4, integer12, cfj10, cfj10, false);
				this.a(bqu, ctd, 10, 3, integer12, 10, 4, integer12, cfj10, cfj10, false);
			}

			for (int integer12 = 2; integer12 < 9; integer12 += 2) {
				this.a(bqu, ctd, integer12, 3, 15, integer12, 4, 15, cfj11, cfj11, false);
			}

			cfj cfj12 = bvs.dS.n().a(cbn.a, fz.NORTH);
			this.a(bqu, ctd, 4, 1, 5, 6, 1, 7, false, random, ctw.e);
			this.a(bqu, ctd, 4, 2, 6, 6, 2, 7, false, random, ctw.e);
			this.a(bqu, ctd, 4, 3, 7, 6, 3, 7, false, random, ctw.e);

			for (int integer13 = 4; integer13 <= 6; integer13++) {
				this.a(bqu, cfj12, integer13, 1, 4, ctd);
				this.a(bqu, cfj12, integer13, 2, 5, ctd);
				this.a(bqu, cfj12, integer13, 3, 6, ctd);
			}

			cfj cfj13 = bvs.ed.n().a(bxm.a, fz.NORTH);
			cfj cfj14 = bvs.ed.n().a(bxm.a, fz.SOUTH);
			cfj cfj15 = bvs.ed.n().a(bxm.a, fz.EAST);
			cfj cfj16 = bvs.ed.n().a(bxm.a, fz.WEST);
			boolean boolean17 = true;
			boolean[] arr18 = new boolean[12];

			for (int integer19 = 0; integer19 < arr18.length; integer19++) {
				arr18[integer19] = random.nextFloat() > 0.9F;
				boolean17 &= arr18[integer19];
			}

			this.a(bqu, cfj13.a(bxm.b, Boolean.valueOf(arr18[0])), 4, 3, 8, ctd);
			this.a(bqu, cfj13.a(bxm.b, Boolean.valueOf(arr18[1])), 5, 3, 8, ctd);
			this.a(bqu, cfj13.a(bxm.b, Boolean.valueOf(arr18[2])), 6, 3, 8, ctd);
			this.a(bqu, cfj14.a(bxm.b, Boolean.valueOf(arr18[3])), 4, 3, 12, ctd);
			this.a(bqu, cfj14.a(bxm.b, Boolean.valueOf(arr18[4])), 5, 3, 12, ctd);
			this.a(bqu, cfj14.a(bxm.b, Boolean.valueOf(arr18[5])), 6, 3, 12, ctd);
			this.a(bqu, cfj15.a(bxm.b, Boolean.valueOf(arr18[6])), 3, 3, 9, ctd);
			this.a(bqu, cfj15.a(bxm.b, Boolean.valueOf(arr18[7])), 3, 3, 10, ctd);
			this.a(bqu, cfj15.a(bxm.b, Boolean.valueOf(arr18[8])), 3, 3, 11, ctd);
			this.a(bqu, cfj16.a(bxm.b, Boolean.valueOf(arr18[9])), 7, 3, 9, ctd);
			this.a(bqu, cfj16.a(bxm.b, Boolean.valueOf(arr18[10])), 7, 3, 10, ctd);
			this.a(bqu, cfj16.a(bxm.b, Boolean.valueOf(arr18[11])), 7, 3, 11, ctd);
			if (boolean17) {
				cfj cfj19 = bvs.ec.n();
				this.a(bqu, cfj19, 4, 3, 9, ctd);
				this.a(bqu, cfj19, 5, 3, 9, ctd);
				this.a(bqu, cfj19, 6, 3, 9, ctd);
				this.a(bqu, cfj19, 4, 3, 10, ctd);
				this.a(bqu, cfj19, 5, 3, 10, ctd);
				this.a(bqu, cfj19, 6, 3, 10, ctd);
				this.a(bqu, cfj19, 4, 3, 11, ctd);
				this.a(bqu, cfj19, 5, 3, 11, ctd);
				this.a(bqu, cfj19, 6, 3, 11, ctd);
			}

			if (!this.a) {
				integer9 = this.d(3);
				fu fu19 = new fu(this.a(5, 6), integer9, this.b(5, 6));
				if (ctd.b(fu19)) {
					this.a = true;
					bqu.a(fu19, bvs.bP.n(), 2);
					cdl cdl20 = bqu.c(fu19);
					if (cdl20 instanceof cek) {
						((cek)cdl20).d().a(aoq.at);
					}
				}
			}

			return true;
		}
	}

	public static class h extends ctw.p {
		public h(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.B, integer);
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
		}

		public h(cva cva, le le) {
			super(cmm.B, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctw.m)cty, list, random, 1, 1);
		}

		public static ctw.h a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -1, 0, 9, 5, 11, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctw.h(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 8, 4, 10, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 1, 1, 0);
			this.a(bqu, ctd, 1, 1, 10, 3, 3, 10, m, m, false);
			this.a(bqu, ctd, 4, 1, 1, 4, 3, 1, false, random, ctw.e);
			this.a(bqu, ctd, 4, 1, 3, 4, 3, 3, false, random, ctw.e);
			this.a(bqu, ctd, 4, 1, 7, 4, 3, 7, false, random, ctw.e);
			this.a(bqu, ctd, 4, 1, 9, 4, 3, 9, false, random, ctw.e);

			for (int integer9 = 1; integer9 <= 3; integer9++) {
				this.a(bqu, bvs.dH.n().a(byt.a, Boolean.valueOf(true)).a(byt.c, Boolean.valueOf(true)), 4, integer9, 4, ctd);
				this.a(bqu, bvs.dH.n().a(byt.a, Boolean.valueOf(true)).a(byt.c, Boolean.valueOf(true)).a(byt.b, Boolean.valueOf(true)), 4, integer9, 5, ctd);
				this.a(bqu, bvs.dH.n().a(byt.a, Boolean.valueOf(true)).a(byt.c, Boolean.valueOf(true)), 4, integer9, 6, ctd);
				this.a(bqu, bvs.dH.n().a(byt.d, Boolean.valueOf(true)).a(byt.b, Boolean.valueOf(true)), 5, integer9, 5, ctd);
				this.a(bqu, bvs.dH.n().a(byt.d, Boolean.valueOf(true)).a(byt.b, Boolean.valueOf(true)), 6, integer9, 5, ctd);
				this.a(bqu, bvs.dH.n().a(byt.d, Boolean.valueOf(true)).a(byt.b, Boolean.valueOf(true)), 7, integer9, 5, ctd);
			}

			this.a(bqu, bvs.dH.n().a(byt.a, Boolean.valueOf(true)).a(byt.c, Boolean.valueOf(true)), 4, 3, 2, ctd);
			this.a(bqu, bvs.dH.n().a(byt.a, Boolean.valueOf(true)).a(byt.c, Boolean.valueOf(true)), 4, 3, 8, ctd);
			cfj cfj9 = bvs.cr.n().a(bxe.a, fz.WEST);
			cfj cfj10 = bvs.cr.n().a(bxe.a, fz.WEST).a(bxe.e, cgf.UPPER);
			this.a(bqu, cfj9, 4, 1, 2, ctd);
			this.a(bqu, cfj10, 4, 2, 2, ctd);
			this.a(bqu, cfj9, 4, 1, 8, ctd);
			this.a(bqu, cfj10, 4, 2, 8, ctd);
			return true;
		}
	}

	public static class i extends ctw.q {
		public i(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.C, integer);
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
		}

		public i(cva cva, le le) {
			super(cmm.C, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			fz fz5 = this.i();
			if (fz5 != fz.NORTH && fz5 != fz.EAST) {
				this.b((ctw.m)cty, list, random, 1, 1);
			} else {
				this.c((ctw.m)cty, list, random, 1, 1);
			}
		}

		public static ctw.i a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -1, 0, 5, 5, 5, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctw.i(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 4, 4, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 1, 1, 0);
			fz fz9 = this.i();
			if (fz9 != fz.NORTH && fz9 != fz.EAST) {
				this.a(bqu, ctd, 0, 1, 1, 0, 3, 3, m, m, false);
			} else {
				this.a(bqu, ctd, 4, 1, 1, 4, 3, 3, m, m, false);
			}

			return true;
		}
	}

	public static class j extends ctw.p {
		protected final int a;

		public j(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.D, integer);
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
			this.a = random.nextInt(5);
		}

		public j(cva cva, le le) {
			super(cmm.D, le);
			this.a = le.h("Type");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.b("Type", this.a);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctw.m)cty, list, random, 4, 1);
			this.b((ctw.m)cty, list, random, 1, 4);
			this.c((ctw.m)cty, list, random, 1, 4);
		}

		public static ctw.j a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -4, -1, 0, 11, 7, 11, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctw.j(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 10, 6, 10, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 4, 1, 0);
			this.a(bqu, ctd, 4, 1, 10, 6, 3, 10, m, m, false);
			this.a(bqu, ctd, 0, 1, 4, 0, 3, 6, m, m, false);
			this.a(bqu, ctd, 10, 1, 4, 10, 3, 6, m, m, false);
			switch (this.a) {
				case 0:
					this.a(bqu, bvs.du.n(), 5, 1, 5, ctd);
					this.a(bqu, bvs.du.n(), 5, 2, 5, ctd);
					this.a(bqu, bvs.du.n(), 5, 3, 5, ctd);
					this.a(bqu, bvs.bM.n().a(ccp.a, fz.WEST), 4, 3, 5, ctd);
					this.a(bqu, bvs.bM.n().a(ccp.a, fz.EAST), 6, 3, 5, ctd);
					this.a(bqu, bvs.bM.n().a(ccp.a, fz.SOUTH), 5, 3, 4, ctd);
					this.a(bqu, bvs.bM.n().a(ccp.a, fz.NORTH), 5, 3, 6, ctd);
					this.a(bqu, bvs.hR.n(), 4, 1, 4, ctd);
					this.a(bqu, bvs.hR.n(), 4, 1, 5, ctd);
					this.a(bqu, bvs.hR.n(), 4, 1, 6, ctd);
					this.a(bqu, bvs.hR.n(), 6, 1, 4, ctd);
					this.a(bqu, bvs.hR.n(), 6, 1, 5, ctd);
					this.a(bqu, bvs.hR.n(), 6, 1, 6, ctd);
					this.a(bqu, bvs.hR.n(), 5, 1, 4, ctd);
					this.a(bqu, bvs.hR.n(), 5, 1, 6, ctd);
					break;
				case 1:
					for (int integer9 = 0; integer9 < 5; integer9++) {
						this.a(bqu, bvs.du.n(), 3, 1, 3 + integer9, ctd);
						this.a(bqu, bvs.du.n(), 7, 1, 3 + integer9, ctd);
						this.a(bqu, bvs.du.n(), 3 + integer9, 1, 3, ctd);
						this.a(bqu, bvs.du.n(), 3 + integer9, 1, 7, ctd);
					}

					this.a(bqu, bvs.du.n(), 5, 1, 5, ctd);
					this.a(bqu, bvs.du.n(), 5, 2, 5, ctd);
					this.a(bqu, bvs.du.n(), 5, 3, 5, ctd);
					this.a(bqu, bvs.A.n(), 5, 4, 5, ctd);
					break;
				case 2:
					for (int integer9 = 1; integer9 <= 9; integer9++) {
						this.a(bqu, bvs.m.n(), 1, 3, integer9, ctd);
						this.a(bqu, bvs.m.n(), 9, 3, integer9, ctd);
					}

					for (int integer9 = 1; integer9 <= 9; integer9++) {
						this.a(bqu, bvs.m.n(), integer9, 3, 1, ctd);
						this.a(bqu, bvs.m.n(), integer9, 3, 9, ctd);
					}

					this.a(bqu, bvs.m.n(), 5, 1, 4, ctd);
					this.a(bqu, bvs.m.n(), 5, 1, 6, ctd);
					this.a(bqu, bvs.m.n(), 5, 3, 4, ctd);
					this.a(bqu, bvs.m.n(), 5, 3, 6, ctd);
					this.a(bqu, bvs.m.n(), 4, 1, 5, ctd);
					this.a(bqu, bvs.m.n(), 6, 1, 5, ctd);
					this.a(bqu, bvs.m.n(), 4, 3, 5, ctd);
					this.a(bqu, bvs.m.n(), 6, 3, 5, ctd);

					for (int integer9 = 1; integer9 <= 3; integer9++) {
						this.a(bqu, bvs.m.n(), 4, integer9, 4, ctd);
						this.a(bqu, bvs.m.n(), 6, integer9, 4, ctd);
						this.a(bqu, bvs.m.n(), 4, integer9, 6, ctd);
						this.a(bqu, bvs.m.n(), 6, integer9, 6, ctd);
					}

					this.a(bqu, bvs.bL.n(), 5, 3, 5, ctd);

					for (int integer9 = 2; integer9 <= 8; integer9++) {
						this.a(bqu, bvs.n.n(), 2, 3, integer9, ctd);
						this.a(bqu, bvs.n.n(), 3, 3, integer9, ctd);
						if (integer9 <= 3 || integer9 >= 7) {
							this.a(bqu, bvs.n.n(), 4, 3, integer9, ctd);
							this.a(bqu, bvs.n.n(), 5, 3, integer9, ctd);
							this.a(bqu, bvs.n.n(), 6, 3, integer9, ctd);
						}

						this.a(bqu, bvs.n.n(), 7, 3, integer9, ctd);
						this.a(bqu, bvs.n.n(), 8, 3, integer9, ctd);
					}

					cfj cfj9 = bvs.cg.n().a(byy.a, fz.WEST);
					this.a(bqu, cfj9, 9, 1, 3, ctd);
					this.a(bqu, cfj9, 9, 2, 3, ctd);
					this.a(bqu, cfj9, 9, 3, 3, ctd);
					this.a(bqu, ctd, random, 3, 4, 8, dao.x);
			}

			return true;
		}
	}

	static class k extends cty.a {
		private k() {
		}

		@Override
		public void a(Random random, int integer2, int integer3, int integer4, boolean boolean5) {
			if (boolean5) {
				float float7 = random.nextFloat();
				if (float7 < 0.2F) {
					this.a = bvs.dw.n();
				} else if (float7 < 0.5F) {
					this.a = bvs.dv.n();
				} else if (float7 < 0.55F) {
					this.a = bvs.dA.n();
				} else {
					this.a = bvs.du.n();
				}
			} else {
				this.a = bvs.lb.n();
			}
		}
	}

	public static class l extends ctw.p {
		private final boolean a;

		public l(cmm cmm, int integer2, Random random, int integer4, int integer5) {
			super(cmm, integer2);
			this.a = true;
			this.a(fz.c.HORIZONTAL.a(random));
			this.d = ctw.p.a.OPENING;
			if (this.i().n() == fz.a.Z) {
				this.n = new ctd(integer4, 64, integer5, integer4 + 5 - 1, 74, integer5 + 5 - 1);
			} else {
				this.n = new ctd(integer4, 64, integer5, integer4 + 5 - 1, 74, integer5 + 5 - 1);
			}
		}

		public l(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.E, integer);
			this.a = false;
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
		}

		public l(cmm cmm, le le) {
			super(cmm, le);
			this.a = le.q("Source");
		}

		public l(cva cva, le le) {
			this(cmm.E, le);
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Source", this.a);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			if (this.a) {
				ctw.c = ctw.c.class;
			}

			this.a((ctw.m)cty, list, random, 1, 1);
		}

		public static ctw.l a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -7, 0, 5, 11, 5, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctw.l(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 10, 4, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 1, 7, 0);
			this.a(bqu, random, ctd, ctw.p.a.OPENING, 1, 1, 4);
			this.a(bqu, bvs.du.n(), 2, 6, 1, ctd);
			this.a(bqu, bvs.du.n(), 1, 5, 1, ctd);
			this.a(bqu, bvs.hR.n(), 1, 6, 1, ctd);
			this.a(bqu, bvs.du.n(), 1, 5, 2, ctd);
			this.a(bqu, bvs.du.n(), 1, 4, 3, ctd);
			this.a(bqu, bvs.hR.n(), 1, 5, 3, ctd);
			this.a(bqu, bvs.du.n(), 2, 4, 3, ctd);
			this.a(bqu, bvs.du.n(), 3, 3, 3, ctd);
			this.a(bqu, bvs.hR.n(), 3, 4, 3, ctd);
			this.a(bqu, bvs.du.n(), 3, 3, 2, ctd);
			this.a(bqu, bvs.du.n(), 3, 2, 1, ctd);
			this.a(bqu, bvs.hR.n(), 3, 3, 1, ctd);
			this.a(bqu, bvs.du.n(), 2, 2, 1, ctd);
			this.a(bqu, bvs.du.n(), 1, 1, 1, ctd);
			this.a(bqu, bvs.hR.n(), 1, 2, 1, ctd);
			this.a(bqu, bvs.du.n(), 1, 1, 2, ctd);
			this.a(bqu, bvs.hR.n(), 1, 1, 3, ctd);
			return true;
		}
	}

	public static class m extends ctw.l {
		public ctw.f a;
		@Nullable
		public ctw.g b;
		public final List<cty> c = Lists.<cty>newArrayList();

		public m(Random random, int integer2, int integer3) {
			super(cmm.F, 0, random, integer2, integer3);
		}

		public m(cva cva, le le) {
			super(cmm.F, le);
		}
	}

	public static class n extends ctw.p {
		private final boolean a;
		private final boolean b;

		public n(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.G, integer);
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
			this.a = random.nextInt(2) == 0;
			this.b = random.nextInt(2) == 0;
		}

		public n(cva cva, le le) {
			super(cmm.G, le);
			this.a = le.q("Left");
			this.b = le.q("Right");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Left", this.a);
			le.a("Right", this.b);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctw.m)cty, list, random, 1, 1);
			if (this.a) {
				this.b((ctw.m)cty, list, random, 1, 2);
			}

			if (this.b) {
				this.c((ctw.m)cty, list, random, 1, 2);
			}
		}

		public static ctw.n a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -1, 0, 5, 5, 7, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctw.n(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 4, 6, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 1, 1, 0);
			this.a(bqu, random, ctd, ctw.p.a.OPENING, 1, 1, 6);
			cfj cfj9 = bvs.bM.n().a(ccp.a, fz.EAST);
			cfj cfj10 = bvs.bM.n().a(ccp.a, fz.WEST);
			this.a(bqu, ctd, random, 0.1F, 1, 2, 1, cfj9);
			this.a(bqu, ctd, random, 0.1F, 3, 2, 1, cfj10);
			this.a(bqu, ctd, random, 0.1F, 1, 2, 5, cfj9);
			this.a(bqu, ctd, random, 0.1F, 3, 2, 5, cfj10);
			if (this.a) {
				this.a(bqu, ctd, 0, 1, 2, 0, 3, 4, m, m, false);
			}

			if (this.b) {
				this.a(bqu, ctd, 4, 1, 2, 4, 3, 4, m, m, false);
			}

			return true;
		}
	}

	public static class o extends ctw.p {
		public o(int integer, Random random, ctd ctd, fz fz) {
			super(cmm.H, integer);
			this.a(fz);
			this.d = this.a(random);
			this.n = ctd;
		}

		public o(cva cva, le le) {
			super(cmm.H, le);
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			this.a((ctw.m)cty, list, random, 1, 1);
		}

		public static ctw.o a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz, int integer7) {
			ctd ctd8 = ctd.a(integer3, integer4, integer5, -1, -7, 0, 5, 11, 8, fz);
			return a(ctd8) && cty.a(list, ctd8) == null ? new ctw.o(integer7, random, ctd8, fz) : null;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 0, 0, 4, 10, 7, true, random, ctw.e);
			this.a(bqu, random, ctd, this.d, 1, 7, 0);
			this.a(bqu, random, ctd, ctw.p.a.OPENING, 1, 1, 7);
			cfj cfj9 = bvs.ci.n().a(cbn.a, fz.SOUTH);

			for (int integer10 = 0; integer10 < 6; integer10++) {
				this.a(bqu, cfj9, 1, 6 - integer10, 1 + integer10, ctd);
				this.a(bqu, cfj9, 2, 6 - integer10, 1 + integer10, ctd);
				this.a(bqu, cfj9, 3, 6 - integer10, 1 + integer10, ctd);
				if (integer10 < 5) {
					this.a(bqu, bvs.du.n(), 1, 5 - integer10, 1 + integer10, ctd);
					this.a(bqu, bvs.du.n(), 2, 5 - integer10, 1 + integer10, ctd);
					this.a(bqu, bvs.du.n(), 3, 5 - integer10, 1 + integer10, ctd);
				}
			}

			return true;
		}
	}

	abstract static class p extends cty {
		protected ctw.p.a d = ctw.p.a.OPENING;

		protected p(cmm cmm, int integer) {
			super(cmm, integer);
		}

		public p(cmm cmm, le le) {
			super(cmm, le);
			this.d = ctw.p.a.valueOf(le.l("EntryDoor"));
		}

		@Override
		protected void a(le le) {
			le.a("EntryDoor", this.d.name());
		}

		protected void a(bqc bqc, Random random, ctd ctd, ctw.p.a a, int integer5, int integer6, int integer7) {
			switch (a) {
				case OPENING:
					this.a(bqc, ctd, integer5, integer6, integer7, integer5 + 3 - 1, integer6 + 3 - 1, integer7, m, m, false);
					break;
				case WOOD_DOOR:
					this.a(bqc, bvs.du.n(), integer5, integer6, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5, integer6 + 1, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5, integer6 + 2, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5 + 1, integer6 + 2, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5 + 2, integer6 + 2, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5 + 2, integer6 + 1, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5 + 2, integer6, integer7, ctd);
					this.a(bqc, bvs.cf.n(), integer5 + 1, integer6, integer7, ctd);
					this.a(bqc, bvs.cf.n().a(bxe.e, cgf.UPPER), integer5 + 1, integer6 + 1, integer7, ctd);
					break;
				case GRATES:
					this.a(bqc, bvs.lb.n(), integer5 + 1, integer6, integer7, ctd);
					this.a(bqc, bvs.lb.n(), integer5 + 1, integer6 + 1, integer7, ctd);
					this.a(bqc, bvs.dH.n().a(byt.d, Boolean.valueOf(true)), integer5, integer6, integer7, ctd);
					this.a(bqc, bvs.dH.n().a(byt.d, Boolean.valueOf(true)), integer5, integer6 + 1, integer7, ctd);
					this.a(bqc, bvs.dH.n().a(byt.b, Boolean.valueOf(true)).a(byt.d, Boolean.valueOf(true)), integer5, integer6 + 2, integer7, ctd);
					this.a(bqc, bvs.dH.n().a(byt.b, Boolean.valueOf(true)).a(byt.d, Boolean.valueOf(true)), integer5 + 1, integer6 + 2, integer7, ctd);
					this.a(bqc, bvs.dH.n().a(byt.b, Boolean.valueOf(true)).a(byt.d, Boolean.valueOf(true)), integer5 + 2, integer6 + 2, integer7, ctd);
					this.a(bqc, bvs.dH.n().a(byt.b, Boolean.valueOf(true)), integer5 + 2, integer6 + 1, integer7, ctd);
					this.a(bqc, bvs.dH.n().a(byt.b, Boolean.valueOf(true)), integer5 + 2, integer6, integer7, ctd);
					break;
				case IRON_DOOR:
					this.a(bqc, bvs.du.n(), integer5, integer6, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5, integer6 + 1, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5, integer6 + 2, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5 + 1, integer6 + 2, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5 + 2, integer6 + 2, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5 + 2, integer6 + 1, integer7, ctd);
					this.a(bqc, bvs.du.n(), integer5 + 2, integer6, integer7, ctd);
					this.a(bqc, bvs.cr.n(), integer5 + 1, integer6, integer7, ctd);
					this.a(bqc, bvs.cr.n().a(bxe.e, cgf.UPPER), integer5 + 1, integer6 + 1, integer7, ctd);
					this.a(bqc, bvs.cB.n().a(bvy.aq, fz.NORTH), integer5 + 2, integer6 + 1, integer7 + 1, ctd);
					this.a(bqc, bvs.cB.n().a(bvy.aq, fz.SOUTH), integer5 + 2, integer6 + 1, integer7 - 1, ctd);
			}
		}

		protected ctw.p.a a(Random random) {
			int integer3 = random.nextInt(5);
			switch (integer3) {
				case 0:
				case 1:
				default:
					return ctw.p.a.OPENING;
				case 2:
					return ctw.p.a.WOOD_DOOR;
				case 3:
					return ctw.p.a.GRATES;
				case 4:
					return ctw.p.a.IRON_DOOR;
			}
		}

		@Nullable
		protected cty a(ctw.m m, List<cty> list, Random random, int integer4, int integer5) {
			fz fz7 = this.i();
			if (fz7 != null) {
				switch (fz7) {
					case NORTH:
						return ctw.c(m, list, random, this.n.a + integer4, this.n.b + integer5, this.n.c - 1, fz7, this.h());
					case SOUTH:
						return ctw.c(m, list, random, this.n.a + integer4, this.n.b + integer5, this.n.f + 1, fz7, this.h());
					case WEST:
						return ctw.c(m, list, random, this.n.a - 1, this.n.b + integer5, this.n.c + integer4, fz7, this.h());
					case EAST:
						return ctw.c(m, list, random, this.n.d + 1, this.n.b + integer5, this.n.c + integer4, fz7, this.h());
				}
			}

			return null;
		}

		@Nullable
		protected cty b(ctw.m m, List<cty> list, Random random, int integer4, int integer5) {
			fz fz7 = this.i();
			if (fz7 != null) {
				switch (fz7) {
					case NORTH:
						return ctw.c(m, list, random, this.n.a - 1, this.n.b + integer4, this.n.c + integer5, fz.WEST, this.h());
					case SOUTH:
						return ctw.c(m, list, random, this.n.a - 1, this.n.b + integer4, this.n.c + integer5, fz.WEST, this.h());
					case WEST:
						return ctw.c(m, list, random, this.n.a + integer5, this.n.b + integer4, this.n.c - 1, fz.NORTH, this.h());
					case EAST:
						return ctw.c(m, list, random, this.n.a + integer5, this.n.b + integer4, this.n.c - 1, fz.NORTH, this.h());
				}
			}

			return null;
		}

		@Nullable
		protected cty c(ctw.m m, List<cty> list, Random random, int integer4, int integer5) {
			fz fz7 = this.i();
			if (fz7 != null) {
				switch (fz7) {
					case NORTH:
						return ctw.c(m, list, random, this.n.d + 1, this.n.b + integer4, this.n.c + integer5, fz.EAST, this.h());
					case SOUTH:
						return ctw.c(m, list, random, this.n.d + 1, this.n.b + integer4, this.n.c + integer5, fz.EAST, this.h());
					case WEST:
						return ctw.c(m, list, random, this.n.a + integer5, this.n.b + integer4, this.n.f + 1, fz.SOUTH, this.h());
					case EAST:
						return ctw.c(m, list, random, this.n.a + integer5, this.n.b + integer4, this.n.f + 1, fz.SOUTH, this.h());
				}
			}

			return null;
		}

		protected static boolean a(ctd ctd) {
			return ctd != null && ctd.b > 10;
		}

		public static enum a {
			OPENING,
			WOOD_DOOR,
			GRATES,
			IRON_DOOR;
		}
	}

	public abstract static class q extends ctw.p {
		protected q(cmm cmm, int integer) {
			super(cmm, integer);
		}

		public q(cmm cmm, le le) {
			super(cmm, le);
		}
	}
}
