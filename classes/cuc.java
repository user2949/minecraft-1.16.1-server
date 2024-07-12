import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class cuc {
	public static void a(cva cva, fu fu, cap cap, List<cuc.i> list, Random random) {
		cuc.c c6 = new cuc.c(random);
		cuc.d d7 = new cuc.d(cva, random);
		d7.a(fu, cap, list, c6);
	}

	static class a extends cuc.b {
		private a() {
		}

		@Override
		public String a(Random random) {
			return "1x1_a" + (random.nextInt(5) + 1);
		}

		@Override
		public String b(Random random) {
			return "1x1_as" + (random.nextInt(4) + 1);
		}

		@Override
		public String a(Random random, boolean boolean2) {
			return "1x2_a" + (random.nextInt(9) + 1);
		}

		@Override
		public String b(Random random, boolean boolean2) {
			return "1x2_b" + (random.nextInt(5) + 1);
		}

		@Override
		public String c(Random random) {
			return "1x2_s" + (random.nextInt(2) + 1);
		}

		@Override
		public String d(Random random) {
			return "2x2_a" + (random.nextInt(4) + 1);
		}

		@Override
		public String e(Random random) {
			return "2x2_s1";
		}
	}

	abstract static class b {
		private b() {
		}

		public abstract String a(Random random);

		public abstract String b(Random random);

		public abstract String a(Random random, boolean boolean2);

		public abstract String b(Random random, boolean boolean2);

		public abstract String c(Random random);

		public abstract String d(Random random);

		public abstract String e(Random random);
	}

	static class c {
		private final Random a;
		private final cuc.g b;
		private final cuc.g c;
		private final cuc.g[] d;
		private final int e;
		private final int f;

		public c(Random random) {
			this.a = random;
			int integer3 = 11;
			this.e = 7;
			this.f = 4;
			this.b = new cuc.g(11, 11, 5);
			this.b.a(this.e, this.f, this.e + 1, this.f + 1, 3);
			this.b.a(this.e - 1, this.f, this.e - 1, this.f + 1, 2);
			this.b.a(this.e + 2, this.f - 2, this.e + 3, this.f + 3, 5);
			this.b.a(this.e + 1, this.f - 2, this.e + 1, this.f - 1, 1);
			this.b.a(this.e + 1, this.f + 2, this.e + 1, this.f + 3, 1);
			this.b.a(this.e - 1, this.f - 1, 1);
			this.b.a(this.e - 1, this.f + 2, 1);
			this.b.a(0, 0, 11, 1, 5);
			this.b.a(0, 9, 11, 11, 5);
			this.a(this.b, this.e, this.f - 2, fz.WEST, 6);
			this.a(this.b, this.e, this.f + 3, fz.WEST, 6);
			this.a(this.b, this.e - 2, this.f - 1, fz.WEST, 3);
			this.a(this.b, this.e - 2, this.f + 2, fz.WEST, 3);

			while (this.a(this.b)) {
			}

			this.d = new cuc.g[3];
			this.d[0] = new cuc.g(11, 11, 5);
			this.d[1] = new cuc.g(11, 11, 5);
			this.d[2] = new cuc.g(11, 11, 5);
			this.a(this.b, this.d[0]);
			this.a(this.b, this.d[1]);
			this.d[0].a(this.e + 1, this.f, this.e + 1, this.f + 1, 8388608);
			this.d[1].a(this.e + 1, this.f, this.e + 1, this.f + 1, 8388608);
			this.c = new cuc.g(this.b.b, this.b.c, 5);
			this.b();
			this.a(this.c, this.d[2]);
		}

		public static boolean a(cuc.g g, int integer2, int integer3) {
			int integer4 = g.a(integer2, integer3);
			return integer4 == 1 || integer4 == 2 || integer4 == 3 || integer4 == 4;
		}

		public boolean a(cuc.g g, int integer2, int integer3, int integer4, int integer5) {
			return (this.d[integer4].a(integer2, integer3) & 65535) == integer5;
		}

		@Nullable
		public fz b(cuc.g g, int integer2, int integer3, int integer4, int integer5) {
			for (fz fz8 : fz.c.HORIZONTAL) {
				if (this.a(g, integer2 + fz8.i(), integer3 + fz8.k(), integer4, integer5)) {
					return fz8;
				}
			}

			return null;
		}

		private void a(cuc.g g, int integer2, int integer3, fz fz, int integer5) {
			if (integer5 > 0) {
				g.a(integer2, integer3, 1);
				g.a(integer2 + fz.i(), integer3 + fz.k(), 0, 1);

				for (int integer7 = 0; integer7 < 8; integer7++) {
					fz fz8 = fz.b(this.a.nextInt(4));
					if (fz8 != fz.f() && (fz8 != fz.EAST || !this.a.nextBoolean())) {
						int integer9 = integer2 + fz.i();
						int integer10 = integer3 + fz.k();
						if (g.a(integer9 + fz8.i(), integer10 + fz8.k()) == 0 && g.a(integer9 + fz8.i() * 2, integer10 + fz8.k() * 2) == 0) {
							this.a(g, integer2 + fz.i() + fz8.i(), integer3 + fz.k() + fz8.k(), fz8, integer5 - 1);
							break;
						}
					}
				}

				fz fz7 = fz.g();
				fz fz8 = fz.h();
				g.a(integer2 + fz7.i(), integer3 + fz7.k(), 0, 2);
				g.a(integer2 + fz8.i(), integer3 + fz8.k(), 0, 2);
				g.a(integer2 + fz.i() + fz7.i(), integer3 + fz.k() + fz7.k(), 0, 2);
				g.a(integer2 + fz.i() + fz8.i(), integer3 + fz.k() + fz8.k(), 0, 2);
				g.a(integer2 + fz.i() * 2, integer3 + fz.k() * 2, 0, 2);
				g.a(integer2 + fz7.i() * 2, integer3 + fz7.k() * 2, 0, 2);
				g.a(integer2 + fz8.i() * 2, integer3 + fz8.k() * 2, 0, 2);
			}
		}

		private boolean a(cuc.g g) {
			boolean boolean3 = false;

			for (int integer4 = 0; integer4 < g.c; integer4++) {
				for (int integer5 = 0; integer5 < g.b; integer5++) {
					if (g.a(integer5, integer4) == 0) {
						int integer6 = 0;
						integer6 += a(g, integer5 + 1, integer4) ? 1 : 0;
						integer6 += a(g, integer5 - 1, integer4) ? 1 : 0;
						integer6 += a(g, integer5, integer4 + 1) ? 1 : 0;
						integer6 += a(g, integer5, integer4 - 1) ? 1 : 0;
						if (integer6 >= 3) {
							g.a(integer5, integer4, 2);
							boolean3 = true;
						} else if (integer6 == 2) {
							int integer7 = 0;
							integer7 += a(g, integer5 + 1, integer4 + 1) ? 1 : 0;
							integer7 += a(g, integer5 - 1, integer4 + 1) ? 1 : 0;
							integer7 += a(g, integer5 + 1, integer4 - 1) ? 1 : 0;
							integer7 += a(g, integer5 - 1, integer4 - 1) ? 1 : 0;
							if (integer7 <= 1) {
								g.a(integer5, integer4, 2);
								boolean3 = true;
							}
						}
					}
				}
			}

			return boolean3;
		}

		private void b() {
			List<aek<Integer, Integer>> list2 = Lists.<aek<Integer, Integer>>newArrayList();
			cuc.g g3 = this.d[1];

			for (int integer4 = 0; integer4 < this.c.c; integer4++) {
				for (int integer5 = 0; integer5 < this.c.b; integer5++) {
					int integer6 = g3.a(integer5, integer4);
					int integer7 = integer6 & 983040;
					if (integer7 == 131072 && (integer6 & 2097152) == 2097152) {
						list2.add(new aek<>(integer5, integer4));
					}
				}
			}

			if (list2.isEmpty()) {
				this.c.a(0, 0, this.c.b, this.c.c, 5);
			} else {
				aek<Integer, Integer> aek4 = (aek<Integer, Integer>)list2.get(this.a.nextInt(list2.size()));
				int integer5x = g3.a(aek4.a(), aek4.b());
				g3.a(aek4.a(), aek4.b(), integer5x | 4194304);
				fz fz6 = this.b(this.b, aek4.a(), aek4.b(), 1, integer5x & 65535);
				int integer7 = aek4.a() + fz6.i();
				int integer8 = aek4.b() + fz6.k();

				for (int integer9 = 0; integer9 < this.c.c; integer9++) {
					for (int integer10 = 0; integer10 < this.c.b; integer10++) {
						if (!a(this.b, integer10, integer9)) {
							this.c.a(integer10, integer9, 5);
						} else if (integer10 == aek4.a() && integer9 == aek4.b()) {
							this.c.a(integer10, integer9, 3);
						} else if (integer10 == integer7 && integer9 == integer8) {
							this.c.a(integer10, integer9, 3);
							this.d[2].a(integer10, integer9, 8388608);
						}
					}
				}

				List<fz> list9 = Lists.<fz>newArrayList();

				for (fz fz11 : fz.c.HORIZONTAL) {
					if (this.c.a(integer7 + fz11.i(), integer8 + fz11.k()) == 0) {
						list9.add(fz11);
					}
				}

				if (list9.isEmpty()) {
					this.c.a(0, 0, this.c.b, this.c.c, 5);
					g3.a(aek4.a(), aek4.b(), integer5x);
				} else {
					fz fz10 = (fz)list9.get(this.a.nextInt(list9.size()));
					this.a(this.c, integer7 + fz10.i(), integer8 + fz10.k(), fz10, 4);

					while (this.a(this.c)) {
					}
				}
			}
		}

		private void a(cuc.g g1, cuc.g g2) {
			List<aek<Integer, Integer>> list4 = Lists.<aek<Integer, Integer>>newArrayList();

			for (int integer5 = 0; integer5 < g1.c; integer5++) {
				for (int integer6 = 0; integer6 < g1.b; integer6++) {
					if (g1.a(integer6, integer5) == 2) {
						list4.add(new aek<>(integer6, integer5));
					}
				}
			}

			Collections.shuffle(list4, this.a);
			int integer5 = 10;

			for (aek<Integer, Integer> aek7 : list4) {
				int integer8 = aek7.a();
				int integer9 = aek7.b();
				if (g2.a(integer8, integer9) == 0) {
					int integer10 = integer8;
					int integer11 = integer8;
					int integer12 = integer9;
					int integer13 = integer9;
					int integer14 = 65536;
					if (g2.a(integer8 + 1, integer9) == 0
						&& g2.a(integer8, integer9 + 1) == 0
						&& g2.a(integer8 + 1, integer9 + 1) == 0
						&& g1.a(integer8 + 1, integer9) == 2
						&& g1.a(integer8, integer9 + 1) == 2
						&& g1.a(integer8 + 1, integer9 + 1) == 2) {
						integer11 = integer8 + 1;
						integer13 = integer9 + 1;
						integer14 = 262144;
					} else if (g2.a(integer8 - 1, integer9) == 0
						&& g2.a(integer8, integer9 + 1) == 0
						&& g2.a(integer8 - 1, integer9 + 1) == 0
						&& g1.a(integer8 - 1, integer9) == 2
						&& g1.a(integer8, integer9 + 1) == 2
						&& g1.a(integer8 - 1, integer9 + 1) == 2) {
						integer10 = integer8 - 1;
						integer13 = integer9 + 1;
						integer14 = 262144;
					} else if (g2.a(integer8 - 1, integer9) == 0
						&& g2.a(integer8, integer9 - 1) == 0
						&& g2.a(integer8 - 1, integer9 - 1) == 0
						&& g1.a(integer8 - 1, integer9) == 2
						&& g1.a(integer8, integer9 - 1) == 2
						&& g1.a(integer8 - 1, integer9 - 1) == 2) {
						integer10 = integer8 - 1;
						integer12 = integer9 - 1;
						integer14 = 262144;
					} else if (g2.a(integer8 + 1, integer9) == 0 && g1.a(integer8 + 1, integer9) == 2) {
						integer11 = integer8 + 1;
						integer14 = 131072;
					} else if (g2.a(integer8, integer9 + 1) == 0 && g1.a(integer8, integer9 + 1) == 2) {
						integer13 = integer9 + 1;
						integer14 = 131072;
					} else if (g2.a(integer8 - 1, integer9) == 0 && g1.a(integer8 - 1, integer9) == 2) {
						integer10 = integer8 - 1;
						integer14 = 131072;
					} else if (g2.a(integer8, integer9 - 1) == 0 && g1.a(integer8, integer9 - 1) == 2) {
						integer12 = integer9 - 1;
						integer14 = 131072;
					}

					int integer15 = this.a.nextBoolean() ? integer10 : integer11;
					int integer16 = this.a.nextBoolean() ? integer12 : integer13;
					int integer17 = 2097152;
					if (!g1.b(integer15, integer16, 1)) {
						integer15 = integer15 == integer10 ? integer11 : integer10;
						integer16 = integer16 == integer12 ? integer13 : integer12;
						if (!g1.b(integer15, integer16, 1)) {
							integer16 = integer16 == integer12 ? integer13 : integer12;
							if (!g1.b(integer15, integer16, 1)) {
								integer15 = integer15 == integer10 ? integer11 : integer10;
								integer16 = integer16 == integer12 ? integer13 : integer12;
								if (!g1.b(integer15, integer16, 1)) {
									integer17 = 0;
									integer15 = integer10;
									integer16 = integer12;
								}
							}
						}
					}

					for (int integer18 = integer12; integer18 <= integer13; integer18++) {
						for (int integer19 = integer10; integer19 <= integer11; integer19++) {
							if (integer19 == integer15 && integer18 == integer16) {
								g2.a(integer19, integer18, 1048576 | integer17 | integer14 | integer5);
							} else {
								g2.a(integer19, integer18, integer14 | integer5);
							}
						}
					}

					integer5++;
				}
			}
		}
	}

	static class d {
		private final cva a;
		private final Random b;
		private int c;
		private int d;

		public d(cva cva, Random random) {
			this.a = cva;
			this.b = random;
		}

		public void a(fu fu, cap cap, List<cuc.i> list, cuc.c c) {
			cuc.e e6 = new cuc.e();
			e6.b = fu;
			e6.a = cap;
			e6.c = "wall_flat";
			cuc.e e7 = new cuc.e();
			this.a(list, e6);
			e7.b = e6.b.b(8);
			e7.a = e6.a;
			e7.c = "wall_window";
			if (!list.isEmpty()) {
			}

			cuc.g g8 = c.b;
			cuc.g g9 = c.c;
			this.c = c.e + 1;
			this.d = c.f + 1;
			int integer10 = c.e + 1;
			int integer11 = c.f;
			this.a(list, e6, g8, fz.SOUTH, this.c, this.d, integer10, integer11);
			this.a(list, e7, g8, fz.SOUTH, this.c, this.d, integer10, integer11);
			cuc.e e12 = new cuc.e();
			e12.b = e6.b.b(19);
			e12.a = e6.a;
			e12.c = "wall_window";
			boolean boolean13 = false;

			for (int integer14 = 0; integer14 < g9.c && !boolean13; integer14++) {
				for (int integer15 = g9.b - 1; integer15 >= 0 && !boolean13; integer15--) {
					if (cuc.c.a(g9, integer15, integer14)) {
						e12.b = e12.b.a(cap.a(fz.SOUTH), 8 + (integer14 - this.d) * 8);
						e12.b = e12.b.a(cap.a(fz.EAST), (integer15 - this.c) * 8);
						this.b(list, e12);
						this.a(list, e12, g9, fz.SOUTH, integer15, integer14, integer15, integer14);
						boolean13 = true;
					}
				}
			}

			this.a(list, fu.b(16), cap, g8, g9);
			this.a(list, fu.b(27), cap, g9, null);
			if (!list.isEmpty()) {
			}

			cuc.b[] arr14 = new cuc.b[]{new cuc.a(), new cuc.f(), new cuc.h()};

			for (int integer15x = 0; integer15x < 3; integer15x++) {
				fu fu16 = fu.b(8 * integer15x + (integer15x == 2 ? 3 : 0));
				cuc.g g17 = c.d[integer15x];
				cuc.g g18 = integer15x == 2 ? g9 : g8;
				String string19 = integer15x == 0 ? "carpet_south_1" : "carpet_south_2";
				String string20 = integer15x == 0 ? "carpet_west_1" : "carpet_west_2";

				for (int integer21 = 0; integer21 < g18.c; integer21++) {
					for (int integer22 = 0; integer22 < g18.b; integer22++) {
						if (g18.a(integer22, integer21) == 1) {
							fu fu23 = fu16.a(cap.a(fz.SOUTH), 8 + (integer21 - this.d) * 8);
							fu23 = fu23.a(cap.a(fz.EAST), (integer22 - this.c) * 8);
							list.add(new cuc.i(this.a, "corridor_floor", fu23, cap));
							if (g18.a(integer22, integer21 - 1) == 1 || (g17.a(integer22, integer21 - 1) & 8388608) == 8388608) {
								list.add(new cuc.i(this.a, "carpet_north", fu23.a(cap.a(fz.EAST), 1).b(), cap));
							}

							if (g18.a(integer22 + 1, integer21) == 1 || (g17.a(integer22 + 1, integer21) & 8388608) == 8388608) {
								list.add(new cuc.i(this.a, "carpet_east", fu23.a(cap.a(fz.SOUTH), 1).a(cap.a(fz.EAST), 5).b(), cap));
							}

							if (g18.a(integer22, integer21 + 1) == 1 || (g17.a(integer22, integer21 + 1) & 8388608) == 8388608) {
								list.add(new cuc.i(this.a, string19, fu23.a(cap.a(fz.SOUTH), 5).a(cap.a(fz.WEST), 1), cap));
							}

							if (g18.a(integer22 - 1, integer21) == 1 || (g17.a(integer22 - 1, integer21) & 8388608) == 8388608) {
								list.add(new cuc.i(this.a, string20, fu23.a(cap.a(fz.WEST), 1).a(cap.a(fz.NORTH), 1), cap));
							}
						}
					}
				}

				String string21 = integer15x == 0 ? "indoors_wall_1" : "indoors_wall_2";
				String string22 = integer15x == 0 ? "indoors_door_1" : "indoors_door_2";
				List<fz> list23 = Lists.<fz>newArrayList();

				for (int integer24 = 0; integer24 < g18.c; integer24++) {
					for (int integer25 = 0; integer25 < g18.b; integer25++) {
						boolean boolean26 = integer15x == 2 && g18.a(integer25, integer24) == 3;
						if (g18.a(integer25, integer24) == 2 || boolean26) {
							int integer27 = g17.a(integer25, integer24);
							int integer28 = integer27 & 983040;
							int integer29 = integer27 & 65535;
							boolean26 = boolean26 && (integer27 & 8388608) == 8388608;
							list23.clear();
							if ((integer27 & 2097152) == 2097152) {
								for (fz fz31 : fz.c.HORIZONTAL) {
									if (g18.a(integer25 + fz31.i(), integer24 + fz31.k()) == 1) {
										list23.add(fz31);
									}
								}
							}

							fz fz30 = null;
							if (!list23.isEmpty()) {
								fz30 = (fz)list23.get(this.b.nextInt(list23.size()));
							} else if ((integer27 & 1048576) == 1048576) {
								fz30 = fz.UP;
							}

							fu fu31 = fu16.a(cap.a(fz.SOUTH), 8 + (integer24 - this.d) * 8);
							fu31 = fu31.a(cap.a(fz.EAST), -1 + (integer25 - this.c) * 8);
							if (cuc.c.a(g18, integer25 - 1, integer24) && !c.a(g18, integer25 - 1, integer24, integer15x, integer29)) {
								list.add(new cuc.i(this.a, fz30 == fz.WEST ? string22 : string21, fu31, cap));
							}

							if (g18.a(integer25 + 1, integer24) == 1 && !boolean26) {
								fu fu32 = fu31.a(cap.a(fz.EAST), 8);
								list.add(new cuc.i(this.a, fz30 == fz.EAST ? string22 : string21, fu32, cap));
							}

							if (cuc.c.a(g18, integer25, integer24 + 1) && !c.a(g18, integer25, integer24 + 1, integer15x, integer29)) {
								fu fu32 = fu31.a(cap.a(fz.SOUTH), 7);
								fu32 = fu32.a(cap.a(fz.EAST), 7);
								list.add(new cuc.i(this.a, fz30 == fz.SOUTH ? string22 : string21, fu32, cap.a(cap.CLOCKWISE_90)));
							}

							if (g18.a(integer25, integer24 - 1) == 1 && !boolean26) {
								fu fu32 = fu31.a(cap.a(fz.NORTH), 1);
								fu32 = fu32.a(cap.a(fz.EAST), 7);
								list.add(new cuc.i(this.a, fz30 == fz.NORTH ? string22 : string21, fu32, cap.a(cap.CLOCKWISE_90)));
							}

							if (integer28 == 65536) {
								this.a(list, fu31, cap, fz30, arr14[integer15x]);
							} else if (integer28 == 131072 && fz30 != null) {
								fz fz32 = c.b(g18, integer25, integer24, integer15x, integer29);
								boolean boolean33 = (integer27 & 4194304) == 4194304;
								this.a(list, fu31, cap, fz32, fz30, arr14[integer15x], boolean33);
							} else if (integer28 == 262144 && fz30 != null && fz30 != fz.UP) {
								fz fz32 = fz30.g();
								if (!c.a(g18, integer25 + fz32.i(), integer24 + fz32.k(), integer15x, integer29)) {
									fz32 = fz32.f();
								}

								this.a(list, fu31, cap, fz32, fz30, arr14[integer15x]);
							} else if (integer28 == 262144 && fz30 == fz.UP) {
								this.a(list, fu31, cap, arr14[integer15x]);
							}
						}
					}
				}
			}
		}

		private void a(List<cuc.i> list, cuc.e e, cuc.g g, fz fz, int integer5, int integer6, int integer7, int integer8) {
			int integer10 = integer5;
			int integer11 = integer6;
			fz fz12 = fz;

			do {
				if (!cuc.c.a(g, integer10 + fz.i(), integer11 + fz.k())) {
					this.c(list, e);
					fz = fz.g();
					if (integer10 != integer7 || integer11 != integer8 || fz12 != fz) {
						this.b(list, e);
					}
				} else if (cuc.c.a(g, integer10 + fz.i(), integer11 + fz.k()) && cuc.c.a(g, integer10 + fz.i() + fz.h().i(), integer11 + fz.k() + fz.h().k())) {
					this.d(list, e);
					integer10 += fz.i();
					integer11 += fz.k();
					fz = fz.h();
				} else {
					integer10 += fz.i();
					integer11 += fz.k();
					if (integer10 != integer7 || integer11 != integer8 || fz12 != fz) {
						this.b(list, e);
					}
				}
			} while (integer10 != integer7 || integer11 != integer8 || fz12 != fz);
		}

		private void a(List<cuc.i> list, fu fu, cap cap, cuc.g g4, @Nullable cuc.g g5) {
			for (int integer7 = 0; integer7 < g4.c; integer7++) {
				for (int integer8 = 0; integer8 < g4.b; integer8++) {
					fu fu9 = fu.a(cap.a(fz.SOUTH), 8 + (integer7 - this.d) * 8);
					fu9 = fu9.a(cap.a(fz.EAST), (integer8 - this.c) * 8);
					boolean boolean10 = g5 != null && cuc.c.a(g5, integer8, integer7);
					if (cuc.c.a(g4, integer8, integer7) && !boolean10) {
						list.add(new cuc.i(this.a, "roof", fu9.b(3), cap));
						if (!cuc.c.a(g4, integer8 + 1, integer7)) {
							fu fu11 = fu9.a(cap.a(fz.EAST), 6);
							list.add(new cuc.i(this.a, "roof_front", fu11, cap));
						}

						if (!cuc.c.a(g4, integer8 - 1, integer7)) {
							fu fu11 = fu9.a(cap.a(fz.EAST), 0);
							fu11 = fu11.a(cap.a(fz.SOUTH), 7);
							list.add(new cuc.i(this.a, "roof_front", fu11, cap.a(cap.CLOCKWISE_180)));
						}

						if (!cuc.c.a(g4, integer8, integer7 - 1)) {
							fu fu11 = fu9.a(cap.a(fz.WEST), 1);
							list.add(new cuc.i(this.a, "roof_front", fu11, cap.a(cap.COUNTERCLOCKWISE_90)));
						}

						if (!cuc.c.a(g4, integer8, integer7 + 1)) {
							fu fu11 = fu9.a(cap.a(fz.EAST), 6);
							fu11 = fu11.a(cap.a(fz.SOUTH), 6);
							list.add(new cuc.i(this.a, "roof_front", fu11, cap.a(cap.CLOCKWISE_90)));
						}
					}
				}
			}

			if (g5 != null) {
				for (int integer7 = 0; integer7 < g4.c; integer7++) {
					for (int integer8x = 0; integer8x < g4.b; integer8x++) {
						fu var17 = fu.a(cap.a(fz.SOUTH), 8 + (integer7 - this.d) * 8);
						var17 = var17.a(cap.a(fz.EAST), (integer8x - this.c) * 8);
						boolean boolean10 = cuc.c.a(g5, integer8x, integer7);
						if (cuc.c.a(g4, integer8x, integer7) && boolean10) {
							if (!cuc.c.a(g4, integer8x + 1, integer7)) {
								fu fu11 = var17.a(cap.a(fz.EAST), 7);
								list.add(new cuc.i(this.a, "small_wall", fu11, cap));
							}

							if (!cuc.c.a(g4, integer8x - 1, integer7)) {
								fu fu11 = var17.a(cap.a(fz.WEST), 1);
								fu11 = fu11.a(cap.a(fz.SOUTH), 6);
								list.add(new cuc.i(this.a, "small_wall", fu11, cap.a(cap.CLOCKWISE_180)));
							}

							if (!cuc.c.a(g4, integer8x, integer7 - 1)) {
								fu fu11 = var17.a(cap.a(fz.WEST), 0);
								fu11 = fu11.a(cap.a(fz.NORTH), 1);
								list.add(new cuc.i(this.a, "small_wall", fu11, cap.a(cap.COUNTERCLOCKWISE_90)));
							}

							if (!cuc.c.a(g4, integer8x, integer7 + 1)) {
								fu fu11 = var17.a(cap.a(fz.EAST), 6);
								fu11 = fu11.a(cap.a(fz.SOUTH), 7);
								list.add(new cuc.i(this.a, "small_wall", fu11, cap.a(cap.CLOCKWISE_90)));
							}

							if (!cuc.c.a(g4, integer8x + 1, integer7)) {
								if (!cuc.c.a(g4, integer8x, integer7 - 1)) {
									fu fu11 = var17.a(cap.a(fz.EAST), 7);
									fu11 = fu11.a(cap.a(fz.NORTH), 2);
									list.add(new cuc.i(this.a, "small_wall_corner", fu11, cap));
								}

								if (!cuc.c.a(g4, integer8x, integer7 + 1)) {
									fu fu11 = var17.a(cap.a(fz.EAST), 8);
									fu11 = fu11.a(cap.a(fz.SOUTH), 7);
									list.add(new cuc.i(this.a, "small_wall_corner", fu11, cap.a(cap.CLOCKWISE_90)));
								}
							}

							if (!cuc.c.a(g4, integer8x - 1, integer7)) {
								if (!cuc.c.a(g4, integer8x, integer7 - 1)) {
									fu fu11 = var17.a(cap.a(fz.WEST), 2);
									fu11 = fu11.a(cap.a(fz.NORTH), 1);
									list.add(new cuc.i(this.a, "small_wall_corner", fu11, cap.a(cap.COUNTERCLOCKWISE_90)));
								}

								if (!cuc.c.a(g4, integer8x, integer7 + 1)) {
									fu fu11 = var17.a(cap.a(fz.WEST), 1);
									fu11 = fu11.a(cap.a(fz.SOUTH), 8);
									list.add(new cuc.i(this.a, "small_wall_corner", fu11, cap.a(cap.CLOCKWISE_180)));
								}
							}
						}
					}
				}
			}

			for (int integer7 = 0; integer7 < g4.c; integer7++) {
				for (int integer8xx = 0; integer8xx < g4.b; integer8xx++) {
					fu var19 = fu.a(cap.a(fz.SOUTH), 8 + (integer7 - this.d) * 8);
					var19 = var19.a(cap.a(fz.EAST), (integer8xx - this.c) * 8);
					boolean boolean10 = g5 != null && cuc.c.a(g5, integer8xx, integer7);
					if (cuc.c.a(g4, integer8xx, integer7) && !boolean10) {
						if (!cuc.c.a(g4, integer8xx + 1, integer7)) {
							fu fu11 = var19.a(cap.a(fz.EAST), 6);
							if (!cuc.c.a(g4, integer8xx, integer7 + 1)) {
								fu fu12 = fu11.a(cap.a(fz.SOUTH), 6);
								list.add(new cuc.i(this.a, "roof_corner", fu12, cap));
							} else if (cuc.c.a(g4, integer8xx + 1, integer7 + 1)) {
								fu fu12 = fu11.a(cap.a(fz.SOUTH), 5);
								list.add(new cuc.i(this.a, "roof_inner_corner", fu12, cap));
							}

							if (!cuc.c.a(g4, integer8xx, integer7 - 1)) {
								list.add(new cuc.i(this.a, "roof_corner", fu11, cap.a(cap.COUNTERCLOCKWISE_90)));
							} else if (cuc.c.a(g4, integer8xx + 1, integer7 - 1)) {
								fu fu12 = var19.a(cap.a(fz.EAST), 9);
								fu12 = fu12.a(cap.a(fz.NORTH), 2);
								list.add(new cuc.i(this.a, "roof_inner_corner", fu12, cap.a(cap.CLOCKWISE_90)));
							}
						}

						if (!cuc.c.a(g4, integer8xx - 1, integer7)) {
							fu fu11x = var19.a(cap.a(fz.EAST), 0);
							fu11x = fu11x.a(cap.a(fz.SOUTH), 0);
							if (!cuc.c.a(g4, integer8xx, integer7 + 1)) {
								fu fu12 = fu11x.a(cap.a(fz.SOUTH), 6);
								list.add(new cuc.i(this.a, "roof_corner", fu12, cap.a(cap.CLOCKWISE_90)));
							} else if (cuc.c.a(g4, integer8xx - 1, integer7 + 1)) {
								fu fu12 = fu11x.a(cap.a(fz.SOUTH), 8);
								fu12 = fu12.a(cap.a(fz.WEST), 3);
								list.add(new cuc.i(this.a, "roof_inner_corner", fu12, cap.a(cap.COUNTERCLOCKWISE_90)));
							}

							if (!cuc.c.a(g4, integer8xx, integer7 - 1)) {
								list.add(new cuc.i(this.a, "roof_corner", fu11x, cap.a(cap.CLOCKWISE_180)));
							} else if (cuc.c.a(g4, integer8xx - 1, integer7 - 1)) {
								fu fu12 = fu11x.a(cap.a(fz.SOUTH), 1);
								list.add(new cuc.i(this.a, "roof_inner_corner", fu12, cap.a(cap.CLOCKWISE_180)));
							}
						}
					}
				}
			}
		}

		private void a(List<cuc.i> list, cuc.e e) {
			fz fz4 = e.a.a(fz.WEST);
			list.add(new cuc.i(this.a, "entrance", e.b.a(fz4, 9), e.a));
			e.b = e.b.a(e.a.a(fz.SOUTH), 16);
		}

		private void b(List<cuc.i> list, cuc.e e) {
			list.add(new cuc.i(this.a, e.c, e.b.a(e.a.a(fz.EAST), 7), e.a));
			e.b = e.b.a(e.a.a(fz.SOUTH), 8);
		}

		private void c(List<cuc.i> list, cuc.e e) {
			e.b = e.b.a(e.a.a(fz.SOUTH), -1);
			list.add(new cuc.i(this.a, "wall_corner", e.b, e.a));
			e.b = e.b.a(e.a.a(fz.SOUTH), -7);
			e.b = e.b.a(e.a.a(fz.WEST), -6);
			e.a = e.a.a(cap.CLOCKWISE_90);
		}

		private void d(List<cuc.i> list, cuc.e e) {
			e.b = e.b.a(e.a.a(fz.SOUTH), 6);
			e.b = e.b.a(e.a.a(fz.EAST), 8);
			e.a = e.a.a(cap.COUNTERCLOCKWISE_90);
		}

		private void a(List<cuc.i> list, fu fu, cap cap, fz fz, cuc.b b) {
			cap cap7 = cap.NONE;
			String string8 = b.a(this.b);
			if (fz != fz.EAST) {
				if (fz == fz.NORTH) {
					cap7 = cap7.a(cap.COUNTERCLOCKWISE_90);
				} else if (fz == fz.WEST) {
					cap7 = cap7.a(cap.CLOCKWISE_180);
				} else if (fz == fz.SOUTH) {
					cap7 = cap7.a(cap.CLOCKWISE_90);
				} else {
					string8 = b.b(this.b);
				}
			}

			fu fu9 = cve.a(new fu(1, 0, 0), bzj.NONE, cap7, 7, 7);
			cap7 = cap7.a(cap);
			fu9 = fu9.a(cap);
			fu fu10 = fu.b(fu9.u(), 0, fu9.w());
			list.add(new cuc.i(this.a, string8, fu10, cap7));
		}

		private void a(List<cuc.i> list, fu fu, cap cap, fz fz4, fz fz5, cuc.b b, boolean boolean7) {
			if (fz5 == fz.EAST && fz4 == fz.SOUTH) {
				fu fu9 = fu.a(cap.a(fz.EAST), 1);
				list.add(new cuc.i(this.a, b.a(this.b, boolean7), fu9, cap));
			} else if (fz5 == fz.EAST && fz4 == fz.NORTH) {
				fu fu9 = fu.a(cap.a(fz.EAST), 1);
				fu9 = fu9.a(cap.a(fz.SOUTH), 6);
				list.add(new cuc.i(this.a, b.a(this.b, boolean7), fu9, cap, bzj.LEFT_RIGHT));
			} else if (fz5 == fz.WEST && fz4 == fz.NORTH) {
				fu fu9 = fu.a(cap.a(fz.EAST), 7);
				fu9 = fu9.a(cap.a(fz.SOUTH), 6);
				list.add(new cuc.i(this.a, b.a(this.b, boolean7), fu9, cap.a(cap.CLOCKWISE_180)));
			} else if (fz5 == fz.WEST && fz4 == fz.SOUTH) {
				fu fu9 = fu.a(cap.a(fz.EAST), 7);
				list.add(new cuc.i(this.a, b.a(this.b, boolean7), fu9, cap, bzj.FRONT_BACK));
			} else if (fz5 == fz.SOUTH && fz4 == fz.EAST) {
				fu fu9 = fu.a(cap.a(fz.EAST), 1);
				list.add(new cuc.i(this.a, b.a(this.b, boolean7), fu9, cap.a(cap.CLOCKWISE_90), bzj.LEFT_RIGHT));
			} else if (fz5 == fz.SOUTH && fz4 == fz.WEST) {
				fu fu9 = fu.a(cap.a(fz.EAST), 7);
				list.add(new cuc.i(this.a, b.a(this.b, boolean7), fu9, cap.a(cap.CLOCKWISE_90)));
			} else if (fz5 == fz.NORTH && fz4 == fz.WEST) {
				fu fu9 = fu.a(cap.a(fz.EAST), 7);
				fu9 = fu9.a(cap.a(fz.SOUTH), 6);
				list.add(new cuc.i(this.a, b.a(this.b, boolean7), fu9, cap.a(cap.CLOCKWISE_90), bzj.FRONT_BACK));
			} else if (fz5 == fz.NORTH && fz4 == fz.EAST) {
				fu fu9 = fu.a(cap.a(fz.EAST), 1);
				fu9 = fu9.a(cap.a(fz.SOUTH), 6);
				list.add(new cuc.i(this.a, b.a(this.b, boolean7), fu9, cap.a(cap.COUNTERCLOCKWISE_90)));
			} else if (fz5 == fz.SOUTH && fz4 == fz.NORTH) {
				fu fu9 = fu.a(cap.a(fz.EAST), 1);
				fu9 = fu9.a(cap.a(fz.NORTH), 8);
				list.add(new cuc.i(this.a, b.b(this.b, boolean7), fu9, cap));
			} else if (fz5 == fz.NORTH && fz4 == fz.SOUTH) {
				fu fu9 = fu.a(cap.a(fz.EAST), 7);
				fu9 = fu9.a(cap.a(fz.SOUTH), 14);
				list.add(new cuc.i(this.a, b.b(this.b, boolean7), fu9, cap.a(cap.CLOCKWISE_180)));
			} else if (fz5 == fz.WEST && fz4 == fz.EAST) {
				fu fu9 = fu.a(cap.a(fz.EAST), 15);
				list.add(new cuc.i(this.a, b.b(this.b, boolean7), fu9, cap.a(cap.CLOCKWISE_90)));
			} else if (fz5 == fz.EAST && fz4 == fz.WEST) {
				fu fu9 = fu.a(cap.a(fz.WEST), 7);
				fu9 = fu9.a(cap.a(fz.SOUTH), 6);
				list.add(new cuc.i(this.a, b.b(this.b, boolean7), fu9, cap.a(cap.COUNTERCLOCKWISE_90)));
			} else if (fz5 == fz.UP && fz4 == fz.EAST) {
				fu fu9 = fu.a(cap.a(fz.EAST), 15);
				list.add(new cuc.i(this.a, b.c(this.b), fu9, cap.a(cap.CLOCKWISE_90)));
			} else if (fz5 == fz.UP && fz4 == fz.SOUTH) {
				fu fu9 = fu.a(cap.a(fz.EAST), 1);
				fu9 = fu9.a(cap.a(fz.NORTH), 0);
				list.add(new cuc.i(this.a, b.c(this.b), fu9, cap));
			}
		}

		private void a(List<cuc.i> list, fu fu, cap cap, fz fz4, fz fz5, cuc.b b) {
			int integer8 = 0;
			int integer9 = 0;
			cap cap10 = cap;
			bzj bzj11 = bzj.NONE;
			if (fz5 == fz.EAST && fz4 == fz.SOUTH) {
				integer8 = -7;
			} else if (fz5 == fz.EAST && fz4 == fz.NORTH) {
				integer8 = -7;
				integer9 = 6;
				bzj11 = bzj.LEFT_RIGHT;
			} else if (fz5 == fz.NORTH && fz4 == fz.EAST) {
				integer8 = 1;
				integer9 = 14;
				cap10 = cap.a(cap.COUNTERCLOCKWISE_90);
			} else if (fz5 == fz.NORTH && fz4 == fz.WEST) {
				integer8 = 7;
				integer9 = 14;
				cap10 = cap.a(cap.COUNTERCLOCKWISE_90);
				bzj11 = bzj.LEFT_RIGHT;
			} else if (fz5 == fz.SOUTH && fz4 == fz.WEST) {
				integer8 = 7;
				integer9 = -8;
				cap10 = cap.a(cap.CLOCKWISE_90);
			} else if (fz5 == fz.SOUTH && fz4 == fz.EAST) {
				integer8 = 1;
				integer9 = -8;
				cap10 = cap.a(cap.CLOCKWISE_90);
				bzj11 = bzj.LEFT_RIGHT;
			} else if (fz5 == fz.WEST && fz4 == fz.NORTH) {
				integer8 = 15;
				integer9 = 6;
				cap10 = cap.a(cap.CLOCKWISE_180);
			} else if (fz5 == fz.WEST && fz4 == fz.SOUTH) {
				integer8 = 15;
				bzj11 = bzj.FRONT_BACK;
			}

			fu fu12 = fu.a(cap.a(fz.EAST), integer8);
			fu12 = fu12.a(cap.a(fz.SOUTH), integer9);
			list.add(new cuc.i(this.a, b.d(this.b), fu12, cap10, bzj11));
		}

		private void a(List<cuc.i> list, fu fu, cap cap, cuc.b b) {
			fu fu6 = fu.a(cap.a(fz.EAST), 1);
			list.add(new cuc.i(this.a, b.e(this.b), fu6, cap, bzj.NONE));
		}
	}

	static class e {
		public cap a;
		public fu b;
		public String c;

		private e() {
		}
	}

	static class f extends cuc.b {
		private f() {
		}

		@Override
		public String a(Random random) {
			return "1x1_b" + (random.nextInt(4) + 1);
		}

		@Override
		public String b(Random random) {
			return "1x1_as" + (random.nextInt(4) + 1);
		}

		@Override
		public String a(Random random, boolean boolean2) {
			return boolean2 ? "1x2_c_stairs" : "1x2_c" + (random.nextInt(4) + 1);
		}

		@Override
		public String b(Random random, boolean boolean2) {
			return boolean2 ? "1x2_d_stairs" : "1x2_d" + (random.nextInt(5) + 1);
		}

		@Override
		public String c(Random random) {
			return "1x2_se" + (random.nextInt(1) + 1);
		}

		@Override
		public String d(Random random) {
			return "2x2_b" + (random.nextInt(5) + 1);
		}

		@Override
		public String e(Random random) {
			return "2x2_s1";
		}
	}

	static class g {
		private final int[][] a;
		private final int b;
		private final int c;
		private final int d;

		public g(int integer1, int integer2, int integer3) {
			this.b = integer1;
			this.c = integer2;
			this.d = integer3;
			this.a = new int[integer1][integer2];
		}

		public void a(int integer1, int integer2, int integer3) {
			if (integer1 >= 0 && integer1 < this.b && integer2 >= 0 && integer2 < this.c) {
				this.a[integer1][integer2] = integer3;
			}
		}

		public void a(int integer1, int integer2, int integer3, int integer4, int integer5) {
			for (int integer7 = integer2; integer7 <= integer4; integer7++) {
				for (int integer8 = integer1; integer8 <= integer3; integer8++) {
					this.a(integer8, integer7, integer5);
				}
			}
		}

		public int a(int integer1, int integer2) {
			return integer1 >= 0 && integer1 < this.b && integer2 >= 0 && integer2 < this.c ? this.a[integer1][integer2] : this.d;
		}

		public void a(int integer1, int integer2, int integer3, int integer4) {
			if (this.a(integer1, integer2) == integer3) {
				this.a(integer1, integer2, integer4);
			}
		}

		public boolean b(int integer1, int integer2, int integer3) {
			return this.a(integer1 - 1, integer2) == integer3
				|| this.a(integer1 + 1, integer2) == integer3
				|| this.a(integer1, integer2 + 1) == integer3
				|| this.a(integer1, integer2 - 1) == integer3;
		}
	}

	static class h extends cuc.f {
		private h() {
		}
	}

	public static class i extends cub {
		private final String d;
		private final cap e;
		private final bzj f;

		public i(cva cva, String string, fu fu, cap cap) {
			this(cva, string, fu, cap, bzj.NONE);
		}

		public i(cva cva, String string, fu fu, cap cap, bzj bzj) {
			super(cmm.ab, 0);
			this.d = string;
			this.c = fu;
			this.e = cap;
			this.f = bzj;
			this.a(cva);
		}

		public i(cva cva, le le) {
			super(cmm.ab, le);
			this.d = le.l("Template");
			this.e = cap.valueOf(le.l("Rot"));
			this.f = bzj.valueOf(le.l("Mi"));
			this.a(cva);
		}

		private void a(cva cva) {
			cve cve3 = cva.a(new uh("woodland_mansion/" + this.d));
			cvb cvb4 = new cvb().a(true).a(this.e).a(this.f).a(cui.b);
			this.a(cve3, this.c, cvb4);
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Template", this.d);
			le.a("Rot", this.b.d().name());
			le.a("Mi", this.b.c().name());
		}

		@Override
		protected void a(String string, fu fu, bqc bqc, Random random, ctd ctd) {
			if (string.startsWith("Chest")) {
				cap cap7 = this.b.d();
				cfj cfj8 = bvs.bR.n();
				if ("ChestWest".equals(string)) {
					cfj8 = cfj8.a(bwh.b, cap7.a(fz.WEST));
				} else if ("ChestEast".equals(string)) {
					cfj8 = cfj8.a(bwh.b, cap7.a(fz.EAST));
				} else if ("ChestSouth".equals(string)) {
					cfj8 = cfj8.a(bwh.b, cap7.a(fz.SOUTH));
				} else if ("ChestNorth".equals(string)) {
					cfj8 = cfj8.a(bwh.b, cap7.a(fz.NORTH));
				}

				this.a(bqc, ctd, random, fu, dao.D, cfj8);
			} else {
				bbj bbj7;
				switch (string) {
					case "Mage":
						bbj7 = aoq.w.a(bqc.n());
						break;
					case "Warrior":
						bbj7 = aoq.aP.a(bqc.n());
						break;
					default:
						return;
				}

				bbj7.et();
				bbj7.a(fu, 0.0F, 0.0F);
				bbj7.a(bqc, bqc.d(bbj7.cA()), apb.STRUCTURE, null, null);
				bqc.c(bbj7);
				bqc.a(fu, bvs.a.n(), 2);
			}
		}
	}
}
