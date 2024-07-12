import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class ctk {
	private static ctk.c a(List<cty> list, Random random, int integer3, int integer4, int integer5, @Nullable fz fz, int integer7, cli.b b) {
		int integer9 = random.nextInt(100);
		if (integer9 >= 80) {
			ctd ctd10 = ctk.b.a(list, random, integer3, integer4, integer5, fz);
			if (ctd10 != null) {
				return new ctk.b(integer7, ctd10, fz, b);
			}
		} else if (integer9 >= 70) {
			ctd ctd10 = ctk.e.a(list, random, integer3, integer4, integer5, fz);
			if (ctd10 != null) {
				return new ctk.e(integer7, ctd10, fz, b);
			}
		} else {
			ctd ctd10 = ctk.a.a(list, random, integer3, integer4, integer5, fz);
			if (ctd10 != null) {
				return new ctk.a(integer7, random, ctd10, fz, b);
			}
		}

		return null;
	}

	private static ctk.c b(cty cty, List<cty> list, Random random, int integer4, int integer5, int integer6, fz fz, int integer8) {
		if (integer8 > 8) {
			return null;
		} else if (Math.abs(integer4 - cty.g().a) <= 80 && Math.abs(integer6 - cty.g().c) <= 80) {
			cli.b b9 = ((ctk.c)cty).a;
			ctk.c c10 = a(list, random, integer4, integer5, integer6, fz, integer8 + 1, b9);
			if (c10 != null) {
				list.add(c10);
				c10.a(cty, list, random);
			}

			return c10;
		} else {
			return null;
		}
	}

	public static class a extends ctk.c {
		private final boolean b;
		private final boolean c;
		private boolean d;
		private final int e;

		public a(cva cva, le le) {
			super(cmm.a, le);
			this.b = le.q("hr");
			this.c = le.q("sc");
			this.d = le.q("hps");
			this.e = le.h("Num");
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("hr", this.b);
			le.a("sc", this.c);
			le.a("hps", this.d);
			le.b("Num", this.e);
		}

		public a(int integer, Random random, ctd ctd, fz fz, cli.b b) {
			super(cmm.a, integer, b);
			this.a(fz);
			this.n = ctd;
			this.b = random.nextInt(3) == 0;
			this.c = !this.b && random.nextInt(23) == 0;
			if (this.i().n() == fz.a.Z) {
				this.e = ctd.f() / 5;
			} else {
				this.e = ctd.d() / 5;
			}
		}

		public static ctd a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz) {
			ctd ctd7 = new ctd(integer3, integer4, integer5, integer3, integer4 + 3 - 1, integer5);

			int integer8;
			for (integer8 = random.nextInt(3) + 2; integer8 > 0; integer8--) {
				int integer9 = integer8 * 5;
				switch (fz) {
					case NORTH:
					default:
						ctd7.d = integer3 + 3 - 1;
						ctd7.c = integer5 - (integer9 - 1);
						break;
					case SOUTH:
						ctd7.d = integer3 + 3 - 1;
						ctd7.f = integer5 + integer9 - 1;
						break;
					case WEST:
						ctd7.a = integer3 - (integer9 - 1);
						ctd7.f = integer5 + 3 - 1;
						break;
					case EAST:
						ctd7.d = integer3 + integer9 - 1;
						ctd7.f = integer5 + 3 - 1;
				}

				if (cty.a(list, ctd7) == null) {
					break;
				}
			}

			return integer8 > 0 ? ctd7 : null;
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			int integer5 = this.h();
			int integer6 = random.nextInt(4);
			fz fz7 = this.i();
			if (fz7 != null) {
				switch (fz7) {
					case NORTH:
					default:
						if (integer6 <= 1) {
							ctk.b(cty, list, random, this.n.a, this.n.b - 1 + random.nextInt(3), this.n.c - 1, fz7, integer5);
						} else if (integer6 == 2) {
							ctk.b(cty, list, random, this.n.a - 1, this.n.b - 1 + random.nextInt(3), this.n.c, fz.WEST, integer5);
						} else {
							ctk.b(cty, list, random, this.n.d + 1, this.n.b - 1 + random.nextInt(3), this.n.c, fz.EAST, integer5);
						}
						break;
					case SOUTH:
						if (integer6 <= 1) {
							ctk.b(cty, list, random, this.n.a, this.n.b - 1 + random.nextInt(3), this.n.f + 1, fz7, integer5);
						} else if (integer6 == 2) {
							ctk.b(cty, list, random, this.n.a - 1, this.n.b - 1 + random.nextInt(3), this.n.f - 3, fz.WEST, integer5);
						} else {
							ctk.b(cty, list, random, this.n.d + 1, this.n.b - 1 + random.nextInt(3), this.n.f - 3, fz.EAST, integer5);
						}
						break;
					case WEST:
						if (integer6 <= 1) {
							ctk.b(cty, list, random, this.n.a - 1, this.n.b - 1 + random.nextInt(3), this.n.c, fz7, integer5);
						} else if (integer6 == 2) {
							ctk.b(cty, list, random, this.n.a, this.n.b - 1 + random.nextInt(3), this.n.c - 1, fz.NORTH, integer5);
						} else {
							ctk.b(cty, list, random, this.n.a, this.n.b - 1 + random.nextInt(3), this.n.f + 1, fz.SOUTH, integer5);
						}
						break;
					case EAST:
						if (integer6 <= 1) {
							ctk.b(cty, list, random, this.n.d + 1, this.n.b - 1 + random.nextInt(3), this.n.c, fz7, integer5);
						} else if (integer6 == 2) {
							ctk.b(cty, list, random, this.n.d - 3, this.n.b - 1 + random.nextInt(3), this.n.c - 1, fz.NORTH, integer5);
						} else {
							ctk.b(cty, list, random, this.n.d - 3, this.n.b - 1 + random.nextInt(3), this.n.f + 1, fz.SOUTH, integer5);
						}
				}
			}

			if (integer5 < 8) {
				if (fz7 != fz.NORTH && fz7 != fz.SOUTH) {
					for (int integer8 = this.n.a + 3; integer8 + 3 <= this.n.d; integer8 += 5) {
						int integer9 = random.nextInt(5);
						if (integer9 == 0) {
							ctk.b(cty, list, random, integer8, this.n.b, this.n.c - 1, fz.NORTH, integer5 + 1);
						} else if (integer9 == 1) {
							ctk.b(cty, list, random, integer8, this.n.b, this.n.f + 1, fz.SOUTH, integer5 + 1);
						}
					}
				} else {
					for (int integer8x = this.n.c + 3; integer8x + 3 <= this.n.f; integer8x += 5) {
						int integer9 = random.nextInt(5);
						if (integer9 == 0) {
							ctk.b(cty, list, random, this.n.a - 1, this.n.b, integer8x, fz.WEST, integer5 + 1);
						} else if (integer9 == 1) {
							ctk.b(cty, list, random, this.n.d + 1, this.n.b, integer8x, fz.EAST, integer5 + 1);
						}
					}
				}
			}
		}

		@Override
		protected boolean a(bqc bqc, ctd ctd, Random random, int integer4, int integer5, int integer6, uh uh) {
			fu fu9 = new fu(this.a(integer4, integer6), this.d(integer5), this.b(integer4, integer6));
			if (ctd.b(fu9) && bqc.d_(fu9).g() && !bqc.d_(fu9.c()).g()) {
				cfj cfj10 = bvs.ch.n().a(cad.c, random.nextBoolean() ? cgm.NORTH_SOUTH : cgm.EAST_WEST);
				this.a(bqc, cfj10, integer4, integer5, integer6, ctd);
				bfw bfw11 = new bfw(bqc.n(), (double)fu9.u() + 0.5, (double)fu9.v() + 0.5, (double)fu9.w() + 0.5);
				bfw11.a(uh, random.nextLong());
				bqc.c(bfw11);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			if (this.a(bqu, ctd)) {
				return false;
			} else {
				int integer9 = 0;
				int integer10 = 2;
				int integer11 = 0;
				int integer12 = 2;
				int integer13 = this.e * 5 - 1;
				cfj cfj14 = this.a();
				this.a(bqu, ctd, 0, 0, 0, 2, 1, integer13, m, m, false);
				this.a(bqu, ctd, random, 0.8F, 0, 2, 0, 2, 2, integer13, m, m, false, false);
				if (this.c) {
					this.a(bqu, ctd, random, 0.6F, 0, 0, 0, 2, 1, integer13, bvs.aQ.n(), m, false, true);
				}

				for (int integer15 = 0; integer15 < this.e; integer15++) {
					int integer16 = 2 + integer15 * 5;
					this.a(bqu, ctd, 0, 0, integer16, 2, 2, random);
					this.a(bqu, ctd, random, 0.1F, 0, 2, integer16 - 1);
					this.a(bqu, ctd, random, 0.1F, 2, 2, integer16 - 1);
					this.a(bqu, ctd, random, 0.1F, 0, 2, integer16 + 1);
					this.a(bqu, ctd, random, 0.1F, 2, 2, integer16 + 1);
					this.a(bqu, ctd, random, 0.05F, 0, 2, integer16 - 2);
					this.a(bqu, ctd, random, 0.05F, 2, 2, integer16 - 2);
					this.a(bqu, ctd, random, 0.05F, 0, 2, integer16 + 2);
					this.a(bqu, ctd, random, 0.05F, 2, 2, integer16 + 2);
					if (random.nextInt(100) == 0) {
						this.a(bqu, ctd, random, 2, 0, integer16 - 1, dao.u);
					}

					if (random.nextInt(100) == 0) {
						this.a(bqu, ctd, random, 0, 0, integer16 + 1, dao.u);
					}

					if (this.c && !this.d) {
						int integer17 = this.d(0);
						int integer18 = integer16 - 1 + random.nextInt(3);
						int integer19 = this.a(1, integer18);
						int integer20 = this.b(1, integer18);
						fu fu21 = new fu(integer19, integer17, integer20);
						if (ctd.b(fu21) && this.a(bqu, 1, 0, integer18, ctd)) {
							this.d = true;
							bqu.a(fu21, bvs.bP.n(), 2);
							cdl cdl22 = bqu.c(fu21);
							if (cdl22 instanceof cek) {
								((cek)cdl22).d().a(aoq.i);
							}
						}
					}
				}

				for (int integer15 = 0; integer15 <= 2; integer15++) {
					for (int integer16x = 0; integer16x <= integer13; integer16x++) {
						int integer17 = -1;
						cfj cfj18 = this.a(bqu, integer15, -1, integer16x, ctd);
						if (cfj18.g() && this.a(bqu, integer15, -1, integer16x, ctd)) {
							int integer19 = -1;
							this.a(bqu, cfj14, integer15, -1, integer16x, ctd);
						}
					}
				}

				if (this.b) {
					cfj cfj15 = bvs.ch.n().a(cad.c, cgm.NORTH_SOUTH);

					for (int integer16xx = 0; integer16xx <= integer13; integer16xx++) {
						cfj cfj17 = this.a(bqu, 1, -1, integer16xx, ctd);
						if (!cfj17.g() && cfj17.i(bqu, new fu(this.a(1, integer16xx), this.d(-1), this.b(1, integer16xx)))) {
							float float18 = this.a(bqu, 1, 0, integer16xx, ctd) ? 0.7F : 0.9F;
							this.a(bqu, ctd, random, float18, 1, 0, integer16xx, cfj15);
						}
					}
				}

				return true;
			}
		}

		private void a(bqc bqc, ctd ctd, int integer3, int integer4, int integer5, int integer6, int integer7, Random random) {
			if (this.a(bqc, ctd, integer3, integer7, integer6, integer5)) {
				cfj cfj10 = this.a();
				cfj cfj11 = this.b();
				this.a(bqc, ctd, integer3, integer4, integer5, integer3, integer6 - 1, integer5, cfj11.a(bxt.d, Boolean.valueOf(true)), m, false);
				this.a(bqc, ctd, integer7, integer4, integer5, integer7, integer6 - 1, integer5, cfj11.a(bxt.b, Boolean.valueOf(true)), m, false);
				if (random.nextInt(4) == 0) {
					this.a(bqc, ctd, integer3, integer6, integer5, integer3, integer6, integer5, cfj10, m, false);
					this.a(bqc, ctd, integer7, integer6, integer5, integer7, integer6, integer5, cfj10, m, false);
				} else {
					this.a(bqc, ctd, integer3, integer6, integer5, integer7, integer6, integer5, cfj10, m, false);
					this.a(bqc, ctd, random, 0.05F, integer3 + 1, integer6, integer5 - 1, bvs.bM.n().a(ccp.a, fz.NORTH));
					this.a(bqc, ctd, random, 0.05F, integer3 + 1, integer6, integer5 + 1, bvs.bM.n().a(ccp.a, fz.SOUTH));
				}
			}
		}

		private void a(bqc bqc, ctd ctd, Random random, float float4, int integer5, int integer6, int integer7) {
			if (this.a(bqc, integer5, integer6, integer7, ctd)) {
				this.a(bqc, ctd, random, float4, integer5, integer6, integer7, bvs.aQ.n());
			}
		}
	}

	public static class b extends ctk.c {
		private final fz b;
		private final boolean c;

		public b(cva cva, le le) {
			super(cmm.b, le);
			this.c = le.q("tf");
			this.b = fz.b(le.h("D"));
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("tf", this.c);
			le.b("D", this.b.d());
		}

		public b(int integer, ctd ctd, @Nullable fz fz, cli.b b) {
			super(cmm.b, integer, b);
			this.b = fz;
			this.n = ctd;
			this.c = ctd.e() > 3;
		}

		public static ctd a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz) {
			ctd ctd7 = new ctd(integer3, integer4, integer5, integer3, integer4 + 3 - 1, integer5);
			if (random.nextInt(4) == 0) {
				ctd7.e += 4;
			}

			switch (fz) {
				case NORTH:
				default:
					ctd7.a = integer3 - 1;
					ctd7.d = integer3 + 3;
					ctd7.c = integer5 - 4;
					break;
				case SOUTH:
					ctd7.a = integer3 - 1;
					ctd7.d = integer3 + 3;
					ctd7.f = integer5 + 3 + 1;
					break;
				case WEST:
					ctd7.a = integer3 - 4;
					ctd7.c = integer5 - 1;
					ctd7.f = integer5 + 3;
					break;
				case EAST:
					ctd7.d = integer3 + 3 + 1;
					ctd7.c = integer5 - 1;
					ctd7.f = integer5 + 3;
			}

			return cty.a(list, ctd7) != null ? null : ctd7;
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			int integer5 = this.h();
			switch (this.b) {
				case NORTH:
				default:
					ctk.b(cty, list, random, this.n.a + 1, this.n.b, this.n.c - 1, fz.NORTH, integer5);
					ctk.b(cty, list, random, this.n.a - 1, this.n.b, this.n.c + 1, fz.WEST, integer5);
					ctk.b(cty, list, random, this.n.d + 1, this.n.b, this.n.c + 1, fz.EAST, integer5);
					break;
				case SOUTH:
					ctk.b(cty, list, random, this.n.a + 1, this.n.b, this.n.f + 1, fz.SOUTH, integer5);
					ctk.b(cty, list, random, this.n.a - 1, this.n.b, this.n.c + 1, fz.WEST, integer5);
					ctk.b(cty, list, random, this.n.d + 1, this.n.b, this.n.c + 1, fz.EAST, integer5);
					break;
				case WEST:
					ctk.b(cty, list, random, this.n.a + 1, this.n.b, this.n.c - 1, fz.NORTH, integer5);
					ctk.b(cty, list, random, this.n.a + 1, this.n.b, this.n.f + 1, fz.SOUTH, integer5);
					ctk.b(cty, list, random, this.n.a - 1, this.n.b, this.n.c + 1, fz.WEST, integer5);
					break;
				case EAST:
					ctk.b(cty, list, random, this.n.a + 1, this.n.b, this.n.c - 1, fz.NORTH, integer5);
					ctk.b(cty, list, random, this.n.a + 1, this.n.b, this.n.f + 1, fz.SOUTH, integer5);
					ctk.b(cty, list, random, this.n.d + 1, this.n.b, this.n.c + 1, fz.EAST, integer5);
			}

			if (this.c) {
				if (random.nextBoolean()) {
					ctk.b(cty, list, random, this.n.a + 1, this.n.b + 3 + 1, this.n.c - 1, fz.NORTH, integer5);
				}

				if (random.nextBoolean()) {
					ctk.b(cty, list, random, this.n.a - 1, this.n.b + 3 + 1, this.n.c + 1, fz.WEST, integer5);
				}

				if (random.nextBoolean()) {
					ctk.b(cty, list, random, this.n.d + 1, this.n.b + 3 + 1, this.n.c + 1, fz.EAST, integer5);
				}

				if (random.nextBoolean()) {
					ctk.b(cty, list, random, this.n.a + 1, this.n.b + 3 + 1, this.n.f + 1, fz.SOUTH, integer5);
				}
			}
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			if (this.a(bqu, ctd)) {
				return false;
			} else {
				cfj cfj9 = this.a();
				if (this.c) {
					this.a(bqu, ctd, this.n.a + 1, this.n.b, this.n.c, this.n.d - 1, this.n.b + 3 - 1, this.n.f, m, m, false);
					this.a(bqu, ctd, this.n.a, this.n.b, this.n.c + 1, this.n.d, this.n.b + 3 - 1, this.n.f - 1, m, m, false);
					this.a(bqu, ctd, this.n.a + 1, this.n.e - 2, this.n.c, this.n.d - 1, this.n.e, this.n.f, m, m, false);
					this.a(bqu, ctd, this.n.a, this.n.e - 2, this.n.c + 1, this.n.d, this.n.e, this.n.f - 1, m, m, false);
					this.a(bqu, ctd, this.n.a + 1, this.n.b + 3, this.n.c + 1, this.n.d - 1, this.n.b + 3, this.n.f - 1, m, m, false);
				} else {
					this.a(bqu, ctd, this.n.a + 1, this.n.b, this.n.c, this.n.d - 1, this.n.e, this.n.f, m, m, false);
					this.a(bqu, ctd, this.n.a, this.n.b, this.n.c + 1, this.n.d, this.n.e, this.n.f - 1, m, m, false);
				}

				this.a(bqu, ctd, this.n.a + 1, this.n.b, this.n.c + 1, this.n.e);
				this.a(bqu, ctd, this.n.a + 1, this.n.b, this.n.f - 1, this.n.e);
				this.a(bqu, ctd, this.n.d - 1, this.n.b, this.n.c + 1, this.n.e);
				this.a(bqu, ctd, this.n.d - 1, this.n.b, this.n.f - 1, this.n.e);

				for (int integer10 = this.n.a; integer10 <= this.n.d; integer10++) {
					for (int integer11 = this.n.c; integer11 <= this.n.f; integer11++) {
						if (this.a(bqu, integer10, this.n.b - 1, integer11, ctd).g() && this.a(bqu, integer10, this.n.b - 1, integer11, ctd)) {
							this.a(bqu, cfj9, integer10, this.n.b - 1, integer11, ctd);
						}
					}
				}

				return true;
			}
		}

		private void a(bqc bqc, ctd ctd, int integer3, int integer4, int integer5, int integer6) {
			if (!this.a(bqc, integer3, integer6 + 1, integer5, ctd).g()) {
				this.a(bqc, ctd, integer3, integer4, integer5, integer3, integer6, integer5, this.a(), m, false);
			}
		}
	}

	abstract static class c extends cty {
		protected cli.b a;

		public c(cmm cmm, int integer, cli.b b) {
			super(cmm, integer);
			this.a = b;
		}

		public c(cmm cmm, le le) {
			super(cmm, le);
			this.a = cli.b.a(le.h("MST"));
		}

		@Override
		protected void a(le le) {
			le.b("MST", this.a.ordinal());
		}

		protected cfj a() {
			switch (this.a) {
				case NORMAL:
				default:
					return bvs.n.n();
				case MESA:
					return bvs.s.n();
			}
		}

		protected cfj b() {
			switch (this.a) {
				case NORMAL:
				default:
					return bvs.cJ.n();
				case MESA:
					return bvs.iq.n();
			}
		}

		protected boolean a(bpg bpg, ctd ctd, int integer3, int integer4, int integer5, int integer6) {
			for (int integer8 = integer3; integer8 <= integer4; integer8++) {
				if (this.a(bpg, integer8, integer5 + 1, integer6, ctd).g()) {
					return false;
				}
			}

			return true;
		}
	}

	public static class d extends ctk.c {
		private final List<ctd> b = Lists.<ctd>newLinkedList();

		public d(int integer1, Random random, int integer3, int integer4, cli.b b) {
			super(cmm.c, integer1, b);
			this.a = b;
			this.n = new ctd(integer3, 50, integer4, integer3 + 7 + random.nextInt(6), 54 + random.nextInt(6), integer4 + 7 + random.nextInt(6));
		}

		public d(cva cva, le le) {
			super(cmm.c, le);
			lk lk4 = le.d("Entrances", 11);

			for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
				this.b.add(new ctd(lk4.f(integer5)));
			}
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			int integer5 = this.h();
			int integer7 = this.n.e() - 3 - 1;
			if (integer7 <= 0) {
				integer7 = 1;
			}

			int integer6 = 0;

			while (integer6 < this.n.d()) {
				integer6 += random.nextInt(this.n.d());
				if (integer6 + 3 > this.n.d()) {
					break;
				}

				ctk.c c8 = ctk.b(cty, list, random, this.n.a + integer6, this.n.b + random.nextInt(integer7) + 1, this.n.c - 1, fz.NORTH, integer5);
				if (c8 != null) {
					ctd ctd9 = c8.g();
					this.b.add(new ctd(ctd9.a, ctd9.b, this.n.c, ctd9.d, ctd9.e, this.n.c + 1));
				}

				integer6 += 4;
			}

			integer6 = 0;

			while (integer6 < this.n.d()) {
				integer6 += random.nextInt(this.n.d());
				if (integer6 + 3 > this.n.d()) {
					break;
				}

				ctk.c c8 = ctk.b(cty, list, random, this.n.a + integer6, this.n.b + random.nextInt(integer7) + 1, this.n.f + 1, fz.SOUTH, integer5);
				if (c8 != null) {
					ctd ctd9 = c8.g();
					this.b.add(new ctd(ctd9.a, ctd9.b, this.n.f - 1, ctd9.d, ctd9.e, this.n.f));
				}

				integer6 += 4;
			}

			integer6 = 0;

			while (integer6 < this.n.f()) {
				integer6 += random.nextInt(this.n.f());
				if (integer6 + 3 > this.n.f()) {
					break;
				}

				ctk.c c8 = ctk.b(cty, list, random, this.n.a - 1, this.n.b + random.nextInt(integer7) + 1, this.n.c + integer6, fz.WEST, integer5);
				if (c8 != null) {
					ctd ctd9 = c8.g();
					this.b.add(new ctd(this.n.a, ctd9.b, ctd9.c, this.n.a + 1, ctd9.e, ctd9.f));
				}

				integer6 += 4;
			}

			integer6 = 0;

			while (integer6 < this.n.f()) {
				integer6 += random.nextInt(this.n.f());
				if (integer6 + 3 > this.n.f()) {
					break;
				}

				cty cty8 = ctk.b(cty, list, random, this.n.d + 1, this.n.b + random.nextInt(integer7) + 1, this.n.c + integer6, fz.EAST, integer5);
				if (cty8 != null) {
					ctd ctd9 = cty8.g();
					this.b.add(new ctd(this.n.d - 1, ctd9.b, ctd9.c, this.n.d, ctd9.e, ctd9.f));
				}

				integer6 += 4;
			}
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			if (this.a(bqu, ctd)) {
				return false;
			} else {
				this.a(bqu, ctd, this.n.a, this.n.b, this.n.c, this.n.d, this.n.b, this.n.f, bvs.j.n(), m, true);
				this.a(bqu, ctd, this.n.a, this.n.b + 1, this.n.c, this.n.d, Math.min(this.n.b + 3, this.n.e), this.n.f, m, m, false);

				for (ctd ctd10 : this.b) {
					this.a(bqu, ctd, ctd10.a, ctd10.e - 2, ctd10.c, ctd10.d, ctd10.e, ctd10.f, m, m, false);
				}

				this.a(bqu, ctd, this.n.a, this.n.b + 4, this.n.c, this.n.d, this.n.e, this.n.f, m, false);
				return true;
			}
		}

		@Override
		public void a(int integer1, int integer2, int integer3) {
			super.a(integer1, integer2, integer3);

			for (ctd ctd6 : this.b) {
				ctd6.a(integer1, integer2, integer3);
			}
		}

		@Override
		protected void a(le le) {
			super.a(le);
			lk lk3 = new lk();

			for (ctd ctd5 : this.b) {
				lk3.add(ctd5.h());
			}

			le.a("Entrances", lk3);
		}
	}

	public static class e extends ctk.c {
		public e(int integer, ctd ctd, fz fz, cli.b b) {
			super(cmm.d, integer, b);
			this.a(fz);
			this.n = ctd;
		}

		public e(cva cva, le le) {
			super(cmm.d, le);
		}

		public static ctd a(List<cty> list, Random random, int integer3, int integer4, int integer5, fz fz) {
			ctd ctd7 = new ctd(integer3, integer4 - 5, integer5, integer3, integer4 + 3 - 1, integer5);
			switch (fz) {
				case NORTH:
				default:
					ctd7.d = integer3 + 3 - 1;
					ctd7.c = integer5 - 8;
					break;
				case SOUTH:
					ctd7.d = integer3 + 3 - 1;
					ctd7.f = integer5 + 8;
					break;
				case WEST:
					ctd7.a = integer3 - 8;
					ctd7.f = integer5 + 3 - 1;
					break;
				case EAST:
					ctd7.d = integer3 + 8;
					ctd7.f = integer5 + 3 - 1;
			}

			return cty.a(list, ctd7) != null ? null : ctd7;
		}

		@Override
		public void a(cty cty, List<cty> list, Random random) {
			int integer5 = this.h();
			fz fz6 = this.i();
			if (fz6 != null) {
				switch (fz6) {
					case NORTH:
					default:
						ctk.b(cty, list, random, this.n.a, this.n.b, this.n.c - 1, fz.NORTH, integer5);
						break;
					case SOUTH:
						ctk.b(cty, list, random, this.n.a, this.n.b, this.n.f + 1, fz.SOUTH, integer5);
						break;
					case WEST:
						ctk.b(cty, list, random, this.n.a - 1, this.n.b, this.n.c, fz.WEST, integer5);
						break;
					case EAST:
						ctk.b(cty, list, random, this.n.d + 1, this.n.b, this.n.c, fz.EAST, integer5);
				}
			}
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			if (this.a(bqu, ctd)) {
				return false;
			} else {
				this.a(bqu, ctd, 0, 5, 0, 2, 7, 1, m, m, false);
				this.a(bqu, ctd, 0, 0, 7, 2, 2, 8, m, m, false);

				for (int integer9 = 0; integer9 < 5; integer9++) {
					this.a(bqu, ctd, 0, 5 - integer9 - (integer9 < 4 ? 1 : 0), 2 + integer9, 2, 7 - integer9, 2 + integer9, m, m, false);
				}

				return true;
			}
		}
	}
}
