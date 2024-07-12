import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cto {
	static class a implements cto.i {
		private a() {
		}

		@Override
		public boolean a(cto.v v) {
			return v.c[fz.EAST.c()] && !v.b[fz.EAST.c()].d;
		}

		@Override
		public cto.r a(fz fz, cto.v v, Random random) {
			v.d = true;
			v.b[fz.EAST.c()].d = true;
			return new cto.k(fz, v);
		}
	}

	static class b implements cto.i {
		private b() {
		}

		@Override
		public boolean a(cto.v v) {
			if (v.c[fz.EAST.c()] && !v.b[fz.EAST.c()].d && v.c[fz.UP.c()] && !v.b[fz.UP.c()].d) {
				cto.v v3 = v.b[fz.EAST.c()];
				return v3.c[fz.UP.c()] && !v3.b[fz.UP.c()].d;
			} else {
				return false;
			}
		}

		@Override
		public cto.r a(fz fz, cto.v v, Random random) {
			v.d = true;
			v.b[fz.EAST.c()].d = true;
			v.b[fz.UP.c()].d = true;
			v.b[fz.EAST.c()].b[fz.UP.c()].d = true;
			return new cto.l(fz, v);
		}
	}

	static class c implements cto.i {
		private c() {
		}

		@Override
		public boolean a(cto.v v) {
			return v.c[fz.UP.c()] && !v.b[fz.UP.c()].d;
		}

		@Override
		public cto.r a(fz fz, cto.v v, Random random) {
			v.d = true;
			v.b[fz.UP.c()].d = true;
			return new cto.m(fz, v);
		}
	}

	static class d implements cto.i {
		private d() {
		}

		@Override
		public boolean a(cto.v v) {
			if (v.c[fz.NORTH.c()] && !v.b[fz.NORTH.c()].d && v.c[fz.UP.c()] && !v.b[fz.UP.c()].d) {
				cto.v v3 = v.b[fz.NORTH.c()];
				return v3.c[fz.UP.c()] && !v3.b[fz.UP.c()].d;
			} else {
				return false;
			}
		}

		@Override
		public cto.r a(fz fz, cto.v v, Random random) {
			v.d = true;
			v.b[fz.NORTH.c()].d = true;
			v.b[fz.UP.c()].d = true;
			v.b[fz.NORTH.c()].b[fz.UP.c()].d = true;
			return new cto.n(fz, v);
		}
	}

	static class e implements cto.i {
		private e() {
		}

		@Override
		public boolean a(cto.v v) {
			return v.c[fz.NORTH.c()] && !v.b[fz.NORTH.c()].d;
		}

		@Override
		public cto.r a(fz fz, cto.v v, Random random) {
			cto.v v5 = v;
			if (!v.c[fz.NORTH.c()] || v.b[fz.NORTH.c()].d) {
				v5 = v.b[fz.SOUTH.c()];
			}

			v5.d = true;
			v5.b[fz.NORTH.c()].d = true;
			return new cto.o(fz, v5);
		}
	}

	static class f implements cto.i {
		private f() {
		}

		@Override
		public boolean a(cto.v v) {
			return true;
		}

		@Override
		public cto.r a(fz fz, cto.v v, Random random) {
			v.d = true;
			return new cto.s(fz, v, random);
		}
	}

	static class g implements cto.i {
		private g() {
		}

		@Override
		public boolean a(cto.v v) {
			return !v.c[fz.WEST.c()] && !v.c[fz.EAST.c()] && !v.c[fz.NORTH.c()] && !v.c[fz.SOUTH.c()] && !v.c[fz.UP.c()];
		}

		@Override
		public cto.r a(fz fz, cto.v v, Random random) {
			v.d = true;
			return new cto.t(fz, v);
		}
	}

	public static class h extends cto.r {
		private cto.v p;
		private cto.v q;
		private final List<cto.r> r = Lists.<cto.r>newArrayList();

		public h(Random random, int integer2, int integer3, fz fz) {
			super(cmm.O, 0);
			this.a(fz);
			fz fz6 = this.i();
			if (fz6.n() == fz.a.Z) {
				this.n = new ctd(integer2, 39, integer3, integer2 + 58 - 1, 61, integer3 + 58 - 1);
			} else {
				this.n = new ctd(integer2, 39, integer3, integer2 + 58 - 1, 61, integer3 + 58 - 1);
			}

			List<cto.v> list7 = this.a(random);
			this.p.d = true;
			this.r.add(new cto.p(fz6, this.p));
			this.r.add(new cto.j(fz6, this.q));
			List<cto.i> list8 = Lists.<cto.i>newArrayList();
			list8.add(new cto.b());
			list8.add(new cto.d());
			list8.add(new cto.e());
			list8.add(new cto.a());
			list8.add(new cto.c());
			list8.add(new cto.g());
			list8.add(new cto.f());

			for (cto.v v10 : list7) {
				if (!v10.d && !v10.b()) {
					for (cto.i i12 : list8) {
						if (i12.a(v10)) {
							this.r.add(i12.a(fz6, v10, random));
							break;
						}
					}
				}
			}

			int integer9 = this.n.b;
			int integer10 = this.a(9, 22);
			int integer11 = this.b(9, 22);

			for (cto.r r13 : this.r) {
				r13.g().a(integer10, integer9, integer11);
			}

			ctd ctd12 = ctd.a(this.a(1, 1), this.d(1), this.b(1, 1), this.a(23, 21), this.d(8), this.b(23, 21));
			ctd ctd13 = ctd.a(this.a(34, 1), this.d(1), this.b(34, 1), this.a(56, 21), this.d(8), this.b(56, 21));
			ctd ctd14 = ctd.a(this.a(22, 22), this.d(13), this.b(22, 22), this.a(35, 35), this.d(17), this.b(35, 35));
			int integer15 = random.nextInt();
			this.r.add(new cto.u(fz6, ctd12, integer15++));
			this.r.add(new cto.u(fz6, ctd13, integer15++));
			this.r.add(new cto.q(fz6, ctd14));
		}

		public h(cva cva, le le) {
			super(cmm.O, le);
		}

		private List<cto.v> a(Random random) {
			cto.v[] arr3 = new cto.v[75];

			for (int integer4 = 0; integer4 < 5; integer4++) {
				for (int integer5 = 0; integer5 < 4; integer5++) {
					int integer6 = 0;
					int integer7 = b(integer4, 0, integer5);
					arr3[integer7] = new cto.v(integer7);
				}
			}

			for (int integer4 = 0; integer4 < 5; integer4++) {
				for (int integer5 = 0; integer5 < 4; integer5++) {
					int integer6 = 1;
					int integer7 = b(integer4, 1, integer5);
					arr3[integer7] = new cto.v(integer7);
				}
			}

			for (int integer4 = 1; integer4 < 4; integer4++) {
				for (int integer5 = 0; integer5 < 2; integer5++) {
					int integer6 = 2;
					int integer7 = b(integer4, 2, integer5);
					arr3[integer7] = new cto.v(integer7);
				}
			}

			this.p = arr3[h];

			for (int integer4 = 0; integer4 < 5; integer4++) {
				for (int integer5 = 0; integer5 < 5; integer5++) {
					for (int integer6 = 0; integer6 < 3; integer6++) {
						int integer7 = b(integer4, integer6, integer5);
						if (arr3[integer7] != null) {
							for (fz fz11 : fz.values()) {
								int integer12 = integer4 + fz11.i();
								int integer13 = integer6 + fz11.j();
								int integer14 = integer5 + fz11.k();
								if (integer12 >= 0 && integer12 < 5 && integer14 >= 0 && integer14 < 5 && integer13 >= 0 && integer13 < 3) {
									int integer15 = b(integer12, integer13, integer14);
									if (arr3[integer15] != null) {
										if (integer14 == integer5) {
											arr3[integer7].a(fz11, arr3[integer15]);
										} else {
											arr3[integer7].a(fz11.f(), arr3[integer15]);
										}
									}
								}
							}
						}
					}
				}
			}

			cto.v v4 = new cto.v(1003);
			cto.v v5 = new cto.v(1001);
			cto.v v6 = new cto.v(1002);
			arr3[i].a(fz.UP, v4);
			arr3[j].a(fz.SOUTH, v5);
			arr3[k].a(fz.SOUTH, v6);
			v4.d = true;
			v5.d = true;
			v6.d = true;
			this.p.e = true;
			this.q = arr3[b(random.nextInt(4), 0, 2)];
			this.q.d = true;
			this.q.b[fz.EAST.c()].d = true;
			this.q.b[fz.NORTH.c()].d = true;
			this.q.b[fz.EAST.c()].b[fz.NORTH.c()].d = true;
			this.q.b[fz.UP.c()].d = true;
			this.q.b[fz.EAST.c()].b[fz.UP.c()].d = true;
			this.q.b[fz.NORTH.c()].b[fz.UP.c()].d = true;
			this.q.b[fz.EAST.c()].b[fz.NORTH.c()].b[fz.UP.c()].d = true;
			List<cto.v> list7 = Lists.<cto.v>newArrayList();

			for (cto.v v11 : arr3) {
				if (v11 != null) {
					v11.a();
					list7.add(v11);
				}
			}

			v4.a();
			Collections.shuffle(list7, random);
			int integer8 = 1;

			for (cto.v v10 : list7) {
				int integer11 = 0;
				int integer12 = 0;

				while (integer11 < 2 && integer12 < 5) {
					integer12++;
					int integer13 = random.nextInt(6);
					if (v10.c[integer13]) {
						int integer14 = fz.a(integer13).f().c();
						v10.c[integer13] = false;
						v10.b[integer13].c[integer14] = false;
						if (v10.a(integer8++) && v10.b[integer13].a(integer8++)) {
							integer11++;
						} else {
							v10.c[integer13] = true;
							v10.b[integer13].c[integer14] = true;
						}
					}
				}
			}

			list7.add(v4);
			list7.add(v5);
			list7.add(v6);
			return list7;
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			int integer9 = Math.max(bqu.t_(), 64) - this.n.b;
			this.a(bqu, ctd, 0, 0, 0, 58, integer9, 58);
			this.a(false, 0, bqu, random, ctd);
			this.a(true, 33, bqu, random, ctd);
			this.a(bqu, random, ctd);
			this.b(bqu, random, ctd);
			this.c(bqu, random, ctd);
			this.d(bqu, random, ctd);
			this.e(bqu, random, ctd);
			this.f(bqu, random, ctd);

			for (int integer10 = 0; integer10 < 7; integer10++) {
				int integer11 = 0;

				while (integer11 < 7) {
					if (integer11 == 0 && integer10 == 3) {
						integer11 = 6;
					}

					int integer12 = integer10 * 9;
					int integer13 = integer11 * 9;

					for (int integer14 = 0; integer14 < 4; integer14++) {
						for (int integer15 = 0; integer15 < 4; integer15++) {
							this.a(bqu, b, integer12 + integer14, 0, integer13 + integer15, ctd);
							this.b(bqu, b, integer12 + integer14, -1, integer13 + integer15, ctd);
						}
					}

					if (integer10 != 0 && integer10 != 6) {
						integer11 += 6;
					} else {
						integer11++;
					}
				}
			}

			for (int integer10 = 0; integer10 < 5; integer10++) {
				this.a(bqu, ctd, -1 - integer10, 0 + integer10 * 2, -1 - integer10, -1 - integer10, 23, 58 + integer10);
				this.a(bqu, ctd, 58 + integer10, 0 + integer10 * 2, -1 - integer10, 58 + integer10, 23, 58 + integer10);
				this.a(bqu, ctd, 0 - integer10, 0 + integer10 * 2, -1 - integer10, 57 + integer10, 23, -1 - integer10);
				this.a(bqu, ctd, 0 - integer10, 0 + integer10 * 2, 58 + integer10, 57 + integer10, 23, 58 + integer10);
			}

			for (cto.r r11 : this.r) {
				if (r11.g().b(ctd)) {
					r11.a(bqu, bqq, cha, random, ctd, bph, fu);
				}
			}

			return true;
		}

		private void a(boolean boolean1, int integer, bqc bqc, Random random, ctd ctd) {
			int integer7 = 24;
			if (this.a(ctd, integer, 0, integer + 23, 20)) {
				this.a(bqc, ctd, integer + 0, 0, 0, integer + 24, 0, 20, a, a, false);
				this.a(bqc, ctd, integer + 0, 1, 0, integer + 24, 10, 20);

				for (int integer8 = 0; integer8 < 4; integer8++) {
					this.a(bqc, ctd, integer + integer8, integer8 + 1, integer8, integer + integer8, integer8 + 1, 20, b, b, false);
					this.a(bqc, ctd, integer + integer8 + 7, integer8 + 5, integer8 + 7, integer + integer8 + 7, integer8 + 5, 20, b, b, false);
					this.a(bqc, ctd, integer + 17 - integer8, integer8 + 5, integer8 + 7, integer + 17 - integer8, integer8 + 5, 20, b, b, false);
					this.a(bqc, ctd, integer + 24 - integer8, integer8 + 1, integer8, integer + 24 - integer8, integer8 + 1, 20, b, b, false);
					this.a(bqc, ctd, integer + integer8 + 1, integer8 + 1, integer8, integer + 23 - integer8, integer8 + 1, integer8, b, b, false);
					this.a(bqc, ctd, integer + integer8 + 8, integer8 + 5, integer8 + 7, integer + 16 - integer8, integer8 + 5, integer8 + 7, b, b, false);
				}

				this.a(bqc, ctd, integer + 4, 4, 4, integer + 6, 4, 20, a, a, false);
				this.a(bqc, ctd, integer + 7, 4, 4, integer + 17, 4, 6, a, a, false);
				this.a(bqc, ctd, integer + 18, 4, 4, integer + 20, 4, 20, a, a, false);
				this.a(bqc, ctd, integer + 11, 8, 11, integer + 13, 8, 20, a, a, false);
				this.a(bqc, d, integer + 12, 9, 12, ctd);
				this.a(bqc, d, integer + 12, 9, 15, ctd);
				this.a(bqc, d, integer + 12, 9, 18, ctd);
				int integer8 = integer + (boolean1 ? 19 : 5);
				int integer9 = integer + (boolean1 ? 5 : 19);

				for (int integer10 = 20; integer10 >= 5; integer10 -= 3) {
					this.a(bqc, d, integer8, 5, integer10, ctd);
				}

				for (int integer10 = 19; integer10 >= 7; integer10 -= 3) {
					this.a(bqc, d, integer9, 5, integer10, ctd);
				}

				for (int integer10 = 0; integer10 < 4; integer10++) {
					int integer11 = boolean1 ? integer + 24 - (17 - integer10 * 3) : integer + 17 - integer10 * 3;
					this.a(bqc, d, integer11, 5, 5, ctd);
				}

				this.a(bqc, d, integer9, 5, 5, ctd);
				this.a(bqc, ctd, integer + 11, 1, 12, integer + 13, 7, 12, a, a, false);
				this.a(bqc, ctd, integer + 12, 1, 11, integer + 12, 7, 13, a, a, false);
			}
		}

		private void a(bqc bqc, Random random, ctd ctd) {
			if (this.a(ctd, 22, 5, 35, 17)) {
				this.a(bqc, ctd, 25, 0, 0, 32, 8, 20);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, 24, 2, 5 + integer5 * 4, 24, 4, 5 + integer5 * 4, b, b, false);
					this.a(bqc, ctd, 22, 4, 5 + integer5 * 4, 23, 4, 5 + integer5 * 4, b, b, false);
					this.a(bqc, b, 25, 5, 5 + integer5 * 4, ctd);
					this.a(bqc, b, 26, 6, 5 + integer5 * 4, ctd);
					this.a(bqc, e, 26, 5, 5 + integer5 * 4, ctd);
					this.a(bqc, ctd, 33, 2, 5 + integer5 * 4, 33, 4, 5 + integer5 * 4, b, b, false);
					this.a(bqc, ctd, 34, 4, 5 + integer5 * 4, 35, 4, 5 + integer5 * 4, b, b, false);
					this.a(bqc, b, 32, 5, 5 + integer5 * 4, ctd);
					this.a(bqc, b, 31, 6, 5 + integer5 * 4, ctd);
					this.a(bqc, e, 31, 5, 5 + integer5 * 4, ctd);
					this.a(bqc, ctd, 27, 6, 5 + integer5 * 4, 30, 6, 5 + integer5 * 4, a, a, false);
				}
			}
		}

		private void b(bqc bqc, Random random, ctd ctd) {
			if (this.a(ctd, 15, 20, 42, 21)) {
				this.a(bqc, ctd, 15, 0, 21, 42, 0, 21, a, a, false);
				this.a(bqc, ctd, 26, 1, 21, 31, 3, 21);
				this.a(bqc, ctd, 21, 12, 21, 36, 12, 21, a, a, false);
				this.a(bqc, ctd, 17, 11, 21, 40, 11, 21, a, a, false);
				this.a(bqc, ctd, 16, 10, 21, 41, 10, 21, a, a, false);
				this.a(bqc, ctd, 15, 7, 21, 42, 9, 21, a, a, false);
				this.a(bqc, ctd, 16, 6, 21, 41, 6, 21, a, a, false);
				this.a(bqc, ctd, 17, 5, 21, 40, 5, 21, a, a, false);
				this.a(bqc, ctd, 21, 4, 21, 36, 4, 21, a, a, false);
				this.a(bqc, ctd, 22, 3, 21, 26, 3, 21, a, a, false);
				this.a(bqc, ctd, 31, 3, 21, 35, 3, 21, a, a, false);
				this.a(bqc, ctd, 23, 2, 21, 25, 2, 21, a, a, false);
				this.a(bqc, ctd, 32, 2, 21, 34, 2, 21, a, a, false);
				this.a(bqc, ctd, 28, 4, 20, 29, 4, 21, b, b, false);
				this.a(bqc, b, 27, 3, 21, ctd);
				this.a(bqc, b, 30, 3, 21, ctd);
				this.a(bqc, b, 26, 2, 21, ctd);
				this.a(bqc, b, 31, 2, 21, ctd);
				this.a(bqc, b, 25, 1, 21, ctd);
				this.a(bqc, b, 32, 1, 21, ctd);

				for (int integer5 = 0; integer5 < 7; integer5++) {
					this.a(bqc, c, 28 - integer5, 6 + integer5, 21, ctd);
					this.a(bqc, c, 29 + integer5, 6 + integer5, 21, ctd);
				}

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, c, 28 - integer5, 9 + integer5, 21, ctd);
					this.a(bqc, c, 29 + integer5, 9 + integer5, 21, ctd);
				}

				this.a(bqc, c, 28, 12, 21, ctd);
				this.a(bqc, c, 29, 12, 21, ctd);

				for (int integer5 = 0; integer5 < 3; integer5++) {
					this.a(bqc, c, 22 - integer5 * 2, 8, 21, ctd);
					this.a(bqc, c, 22 - integer5 * 2, 9, 21, ctd);
					this.a(bqc, c, 35 + integer5 * 2, 8, 21, ctd);
					this.a(bqc, c, 35 + integer5 * 2, 9, 21, ctd);
				}

				this.a(bqc, ctd, 15, 13, 21, 42, 15, 21);
				this.a(bqc, ctd, 15, 1, 21, 15, 6, 21);
				this.a(bqc, ctd, 16, 1, 21, 16, 5, 21);
				this.a(bqc, ctd, 17, 1, 21, 20, 4, 21);
				this.a(bqc, ctd, 21, 1, 21, 21, 3, 21);
				this.a(bqc, ctd, 22, 1, 21, 22, 2, 21);
				this.a(bqc, ctd, 23, 1, 21, 24, 1, 21);
				this.a(bqc, ctd, 42, 1, 21, 42, 6, 21);
				this.a(bqc, ctd, 41, 1, 21, 41, 5, 21);
				this.a(bqc, ctd, 37, 1, 21, 40, 4, 21);
				this.a(bqc, ctd, 36, 1, 21, 36, 3, 21);
				this.a(bqc, ctd, 33, 1, 21, 34, 1, 21);
				this.a(bqc, ctd, 35, 1, 21, 35, 2, 21);
			}
		}

		private void c(bqc bqc, Random random, ctd ctd) {
			if (this.a(ctd, 21, 21, 36, 36)) {
				this.a(bqc, ctd, 21, 0, 22, 36, 0, 36, a, a, false);
				this.a(bqc, ctd, 21, 1, 22, 36, 23, 36);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, 21 + integer5, 13 + integer5, 21 + integer5, 36 - integer5, 13 + integer5, 21 + integer5, b, b, false);
					this.a(bqc, ctd, 21 + integer5, 13 + integer5, 36 - integer5, 36 - integer5, 13 + integer5, 36 - integer5, b, b, false);
					this.a(bqc, ctd, 21 + integer5, 13 + integer5, 22 + integer5, 21 + integer5, 13 + integer5, 35 - integer5, b, b, false);
					this.a(bqc, ctd, 36 - integer5, 13 + integer5, 22 + integer5, 36 - integer5, 13 + integer5, 35 - integer5, b, b, false);
				}

				this.a(bqc, ctd, 25, 16, 25, 32, 16, 32, a, a, false);
				this.a(bqc, ctd, 25, 17, 25, 25, 19, 25, b, b, false);
				this.a(bqc, ctd, 32, 17, 25, 32, 19, 25, b, b, false);
				this.a(bqc, ctd, 25, 17, 32, 25, 19, 32, b, b, false);
				this.a(bqc, ctd, 32, 17, 32, 32, 19, 32, b, b, false);
				this.a(bqc, b, 26, 20, 26, ctd);
				this.a(bqc, b, 27, 21, 27, ctd);
				this.a(bqc, e, 27, 20, 27, ctd);
				this.a(bqc, b, 26, 20, 31, ctd);
				this.a(bqc, b, 27, 21, 30, ctd);
				this.a(bqc, e, 27, 20, 30, ctd);
				this.a(bqc, b, 31, 20, 31, ctd);
				this.a(bqc, b, 30, 21, 30, ctd);
				this.a(bqc, e, 30, 20, 30, ctd);
				this.a(bqc, b, 31, 20, 26, ctd);
				this.a(bqc, b, 30, 21, 27, ctd);
				this.a(bqc, e, 30, 20, 27, ctd);
				this.a(bqc, ctd, 28, 21, 27, 29, 21, 27, a, a, false);
				this.a(bqc, ctd, 27, 21, 28, 27, 21, 29, a, a, false);
				this.a(bqc, ctd, 28, 21, 30, 29, 21, 30, a, a, false);
				this.a(bqc, ctd, 30, 21, 28, 30, 21, 29, a, a, false);
			}
		}

		private void d(bqc bqc, Random random, ctd ctd) {
			if (this.a(ctd, 0, 21, 6, 58)) {
				this.a(bqc, ctd, 0, 0, 21, 6, 0, 57, a, a, false);
				this.a(bqc, ctd, 0, 1, 21, 6, 7, 57);
				this.a(bqc, ctd, 4, 4, 21, 6, 4, 53, a, a, false);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, integer5, integer5 + 1, 21, integer5, integer5 + 1, 57 - integer5, b, b, false);
				}

				for (int integer5 = 23; integer5 < 53; integer5 += 3) {
					this.a(bqc, d, 5, 5, integer5, ctd);
				}

				this.a(bqc, d, 5, 5, 52, ctd);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, integer5, integer5 + 1, 21, integer5, integer5 + 1, 57 - integer5, b, b, false);
				}

				this.a(bqc, ctd, 4, 1, 52, 6, 3, 52, a, a, false);
				this.a(bqc, ctd, 5, 1, 51, 5, 3, 53, a, a, false);
			}

			if (this.a(ctd, 51, 21, 58, 58)) {
				this.a(bqc, ctd, 51, 0, 21, 57, 0, 57, a, a, false);
				this.a(bqc, ctd, 51, 1, 21, 57, 7, 57);
				this.a(bqc, ctd, 51, 4, 21, 53, 4, 53, a, a, false);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, 57 - integer5, integer5 + 1, 21, 57 - integer5, integer5 + 1, 57 - integer5, b, b, false);
				}

				for (int integer5 = 23; integer5 < 53; integer5 += 3) {
					this.a(bqc, d, 52, 5, integer5, ctd);
				}

				this.a(bqc, d, 52, 5, 52, ctd);
				this.a(bqc, ctd, 51, 1, 52, 53, 3, 52, a, a, false);
				this.a(bqc, ctd, 52, 1, 51, 52, 3, 53, a, a, false);
			}

			if (this.a(ctd, 0, 51, 57, 57)) {
				this.a(bqc, ctd, 7, 0, 51, 50, 0, 57, a, a, false);
				this.a(bqc, ctd, 7, 1, 51, 50, 10, 57);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, integer5 + 1, integer5 + 1, 57 - integer5, 56 - integer5, integer5 + 1, 57 - integer5, b, b, false);
				}
			}
		}

		private void e(bqc bqc, Random random, ctd ctd) {
			if (this.a(ctd, 7, 21, 13, 50)) {
				this.a(bqc, ctd, 7, 0, 21, 13, 0, 50, a, a, false);
				this.a(bqc, ctd, 7, 1, 21, 13, 10, 50);
				this.a(bqc, ctd, 11, 8, 21, 13, 8, 53, a, a, false);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, integer5 + 7, integer5 + 5, 21, integer5 + 7, integer5 + 5, 54, b, b, false);
				}

				for (int integer5 = 21; integer5 <= 45; integer5 += 3) {
					this.a(bqc, d, 12, 9, integer5, ctd);
				}
			}

			if (this.a(ctd, 44, 21, 50, 54)) {
				this.a(bqc, ctd, 44, 0, 21, 50, 0, 50, a, a, false);
				this.a(bqc, ctd, 44, 1, 21, 50, 10, 50);
				this.a(bqc, ctd, 44, 8, 21, 46, 8, 53, a, a, false);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, 50 - integer5, integer5 + 5, 21, 50 - integer5, integer5 + 5, 54, b, b, false);
				}

				for (int integer5 = 21; integer5 <= 45; integer5 += 3) {
					this.a(bqc, d, 45, 9, integer5, ctd);
				}
			}

			if (this.a(ctd, 8, 44, 49, 54)) {
				this.a(bqc, ctd, 14, 0, 44, 43, 0, 50, a, a, false);
				this.a(bqc, ctd, 14, 1, 44, 43, 10, 50);

				for (int integer5 = 12; integer5 <= 45; integer5 += 3) {
					this.a(bqc, d, integer5, 9, 45, ctd);
					this.a(bqc, d, integer5, 9, 52, ctd);
					if (integer5 == 12 || integer5 == 18 || integer5 == 24 || integer5 == 33 || integer5 == 39 || integer5 == 45) {
						this.a(bqc, d, integer5, 9, 47, ctd);
						this.a(bqc, d, integer5, 9, 50, ctd);
						this.a(bqc, d, integer5, 10, 45, ctd);
						this.a(bqc, d, integer5, 10, 46, ctd);
						this.a(bqc, d, integer5, 10, 51, ctd);
						this.a(bqc, d, integer5, 10, 52, ctd);
						this.a(bqc, d, integer5, 11, 47, ctd);
						this.a(bqc, d, integer5, 11, 50, ctd);
						this.a(bqc, d, integer5, 12, 48, ctd);
						this.a(bqc, d, integer5, 12, 49, ctd);
					}
				}

				for (int integer5x = 0; integer5x < 3; integer5x++) {
					this.a(bqc, ctd, 8 + integer5x, 5 + integer5x, 54, 49 - integer5x, 5 + integer5x, 54, a, a, false);
				}

				this.a(bqc, ctd, 11, 8, 54, 46, 8, 54, b, b, false);
				this.a(bqc, ctd, 14, 8, 44, 43, 8, 53, a, a, false);
			}
		}

		private void f(bqc bqc, Random random, ctd ctd) {
			if (this.a(ctd, 14, 21, 20, 43)) {
				this.a(bqc, ctd, 14, 0, 21, 20, 0, 43, a, a, false);
				this.a(bqc, ctd, 14, 1, 22, 20, 14, 43);
				this.a(bqc, ctd, 18, 12, 22, 20, 12, 39, a, a, false);
				this.a(bqc, ctd, 18, 12, 21, 20, 12, 21, b, b, false);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, integer5 + 14, integer5 + 9, 21, integer5 + 14, integer5 + 9, 43 - integer5, b, b, false);
				}

				for (int integer5 = 23; integer5 <= 39; integer5 += 3) {
					this.a(bqc, d, 19, 13, integer5, ctd);
				}
			}

			if (this.a(ctd, 37, 21, 43, 43)) {
				this.a(bqc, ctd, 37, 0, 21, 43, 0, 43, a, a, false);
				this.a(bqc, ctd, 37, 1, 22, 43, 14, 43);
				this.a(bqc, ctd, 37, 12, 22, 39, 12, 39, a, a, false);
				this.a(bqc, ctd, 37, 12, 21, 39, 12, 21, b, b, false);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, 43 - integer5, integer5 + 9, 21, 43 - integer5, integer5 + 9, 43 - integer5, b, b, false);
				}

				for (int integer5 = 23; integer5 <= 39; integer5 += 3) {
					this.a(bqc, d, 38, 13, integer5, ctd);
				}
			}

			if (this.a(ctd, 15, 37, 42, 43)) {
				this.a(bqc, ctd, 21, 0, 37, 36, 0, 43, a, a, false);
				this.a(bqc, ctd, 21, 1, 37, 36, 14, 43);
				this.a(bqc, ctd, 21, 12, 37, 36, 12, 39, a, a, false);

				for (int integer5 = 0; integer5 < 4; integer5++) {
					this.a(bqc, ctd, 15 + integer5, integer5 + 9, 43 - integer5, 42 - integer5, integer5 + 9, 43 - integer5, b, b, false);
				}

				for (int integer5 = 21; integer5 <= 36; integer5 += 3) {
					this.a(bqc, d, integer5, 13, 38, ctd);
				}
			}
		}
	}

	interface i {
		boolean a(cto.v v);

		cto.r a(fz fz, cto.v v, Random random);
	}

	public static class j extends cto.r {
		public j(fz fz, cto.v v) {
			super(cmm.P, 1, fz, v, 2, 2, 2);
		}

		public j(cva cva, le le) {
			super(cmm.P, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 1, 8, 0, 14, 8, 14, a);
			int integer9 = 7;
			cfj cfj10 = b;
			this.a(bqu, ctd, 0, 7, 0, 0, 7, 15, cfj10, cfj10, false);
			this.a(bqu, ctd, 15, 7, 0, 15, 7, 15, cfj10, cfj10, false);
			this.a(bqu, ctd, 1, 7, 0, 15, 7, 0, cfj10, cfj10, false);
			this.a(bqu, ctd, 1, 7, 15, 14, 7, 15, cfj10, cfj10, false);

			for (int integer9x = 1; integer9x <= 6; integer9x++) {
				cfj10 = b;
				if (integer9x == 2 || integer9x == 6) {
					cfj10 = a;
				}

				for (int integer11 = 0; integer11 <= 15; integer11 += 15) {
					this.a(bqu, ctd, integer11, integer9x, 0, integer11, integer9x, 1, cfj10, cfj10, false);
					this.a(bqu, ctd, integer11, integer9x, 6, integer11, integer9x, 9, cfj10, cfj10, false);
					this.a(bqu, ctd, integer11, integer9x, 14, integer11, integer9x, 15, cfj10, cfj10, false);
				}

				this.a(bqu, ctd, 1, integer9x, 0, 1, integer9x, 0, cfj10, cfj10, false);
				this.a(bqu, ctd, 6, integer9x, 0, 9, integer9x, 0, cfj10, cfj10, false);
				this.a(bqu, ctd, 14, integer9x, 0, 14, integer9x, 0, cfj10, cfj10, false);
				this.a(bqu, ctd, 1, integer9x, 15, 14, integer9x, 15, cfj10, cfj10, false);
			}

			this.a(bqu, ctd, 6, 3, 6, 9, 6, 9, c, c, false);
			this.a(bqu, ctd, 7, 4, 7, 8, 5, 8, bvs.bE.n(), bvs.bE.n(), false);

			for (int integer9x = 3; integer9x <= 6; integer9x += 3) {
				for (int integer10 = 6; integer10 <= 9; integer10 += 3) {
					this.a(bqu, e, integer10, integer9x, 6, ctd);
					this.a(bqu, e, integer10, integer9x, 9, ctd);
				}
			}

			this.a(bqu, ctd, 5, 1, 6, 5, 2, 6, b, b, false);
			this.a(bqu, ctd, 5, 1, 9, 5, 2, 9, b, b, false);
			this.a(bqu, ctd, 10, 1, 6, 10, 2, 6, b, b, false);
			this.a(bqu, ctd, 10, 1, 9, 10, 2, 9, b, b, false);
			this.a(bqu, ctd, 6, 1, 5, 6, 2, 5, b, b, false);
			this.a(bqu, ctd, 9, 1, 5, 9, 2, 5, b, b, false);
			this.a(bqu, ctd, 6, 1, 10, 6, 2, 10, b, b, false);
			this.a(bqu, ctd, 9, 1, 10, 9, 2, 10, b, b, false);
			this.a(bqu, ctd, 5, 2, 5, 5, 6, 5, b, b, false);
			this.a(bqu, ctd, 5, 2, 10, 5, 6, 10, b, b, false);
			this.a(bqu, ctd, 10, 2, 5, 10, 6, 5, b, b, false);
			this.a(bqu, ctd, 10, 2, 10, 10, 6, 10, b, b, false);
			this.a(bqu, ctd, 5, 7, 1, 5, 7, 6, b, b, false);
			this.a(bqu, ctd, 10, 7, 1, 10, 7, 6, b, b, false);
			this.a(bqu, ctd, 5, 7, 9, 5, 7, 14, b, b, false);
			this.a(bqu, ctd, 10, 7, 9, 10, 7, 14, b, b, false);
			this.a(bqu, ctd, 1, 7, 5, 6, 7, 5, b, b, false);
			this.a(bqu, ctd, 1, 7, 10, 6, 7, 10, b, b, false);
			this.a(bqu, ctd, 9, 7, 5, 14, 7, 5, b, b, false);
			this.a(bqu, ctd, 9, 7, 10, 14, 7, 10, b, b, false);
			this.a(bqu, ctd, 2, 1, 2, 2, 1, 3, b, b, false);
			this.a(bqu, ctd, 3, 1, 2, 3, 1, 2, b, b, false);
			this.a(bqu, ctd, 13, 1, 2, 13, 1, 3, b, b, false);
			this.a(bqu, ctd, 12, 1, 2, 12, 1, 2, b, b, false);
			this.a(bqu, ctd, 2, 1, 12, 2, 1, 13, b, b, false);
			this.a(bqu, ctd, 3, 1, 13, 3, 1, 13, b, b, false);
			this.a(bqu, ctd, 13, 1, 12, 13, 1, 13, b, b, false);
			this.a(bqu, ctd, 12, 1, 13, 12, 1, 13, b, b, false);
			return true;
		}
	}

	public static class k extends cto.r {
		public k(fz fz, cto.v v) {
			super(cmm.Q, 1, fz, v, 2, 1, 1);
		}

		public k(cva cva, le le) {
			super(cmm.Q, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			cto.v v9 = this.l.b[fz.EAST.c()];
			cto.v v10 = this.l;
			if (this.l.a / 25 > 0) {
				this.a(bqu, ctd, 8, 0, v9.c[fz.DOWN.c()]);
				this.a(bqu, ctd, 0, 0, v10.c[fz.DOWN.c()]);
			}

			if (v10.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 1, 4, 1, 7, 4, 6, a);
			}

			if (v9.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 8, 4, 1, 14, 4, 6, a);
			}

			this.a(bqu, ctd, 0, 3, 0, 0, 3, 7, b, b, false);
			this.a(bqu, ctd, 15, 3, 0, 15, 3, 7, b, b, false);
			this.a(bqu, ctd, 1, 3, 0, 15, 3, 0, b, b, false);
			this.a(bqu, ctd, 1, 3, 7, 14, 3, 7, b, b, false);
			this.a(bqu, ctd, 0, 2, 0, 0, 2, 7, a, a, false);
			this.a(bqu, ctd, 15, 2, 0, 15, 2, 7, a, a, false);
			this.a(bqu, ctd, 1, 2, 0, 15, 2, 0, a, a, false);
			this.a(bqu, ctd, 1, 2, 7, 14, 2, 7, a, a, false);
			this.a(bqu, ctd, 0, 1, 0, 0, 1, 7, b, b, false);
			this.a(bqu, ctd, 15, 1, 0, 15, 1, 7, b, b, false);
			this.a(bqu, ctd, 1, 1, 0, 15, 1, 0, b, b, false);
			this.a(bqu, ctd, 1, 1, 7, 14, 1, 7, b, b, false);
			this.a(bqu, ctd, 5, 1, 0, 10, 1, 4, b, b, false);
			this.a(bqu, ctd, 6, 2, 0, 9, 2, 3, a, a, false);
			this.a(bqu, ctd, 5, 3, 0, 10, 3, 4, b, b, false);
			this.a(bqu, e, 6, 2, 3, ctd);
			this.a(bqu, e, 9, 2, 3, ctd);
			if (v10.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 3, 1, 0, 4, 2, 0);
			}

			if (v10.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 3, 1, 7, 4, 2, 7);
			}

			if (v10.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 1, 3, 0, 2, 4);
			}

			if (v9.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 11, 1, 0, 12, 2, 0);
			}

			if (v9.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 11, 1, 7, 12, 2, 7);
			}

			if (v9.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 15, 1, 3, 15, 2, 4);
			}

			return true;
		}
	}

	public static class l extends cto.r {
		public l(fz fz, cto.v v) {
			super(cmm.R, 1, fz, v, 2, 2, 1);
		}

		public l(cva cva, le le) {
			super(cmm.R, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			cto.v v9 = this.l.b[fz.EAST.c()];
			cto.v v10 = this.l;
			cto.v v11 = v10.b[fz.UP.c()];
			cto.v v12 = v9.b[fz.UP.c()];
			if (this.l.a / 25 > 0) {
				this.a(bqu, ctd, 8, 0, v9.c[fz.DOWN.c()]);
				this.a(bqu, ctd, 0, 0, v10.c[fz.DOWN.c()]);
			}

			if (v11.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 1, 8, 1, 7, 8, 6, a);
			}

			if (v12.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 8, 8, 1, 14, 8, 6, a);
			}

			for (int integer13 = 1; integer13 <= 7; integer13++) {
				cfj cfj14 = b;
				if (integer13 == 2 || integer13 == 6) {
					cfj14 = a;
				}

				this.a(bqu, ctd, 0, integer13, 0, 0, integer13, 7, cfj14, cfj14, false);
				this.a(bqu, ctd, 15, integer13, 0, 15, integer13, 7, cfj14, cfj14, false);
				this.a(bqu, ctd, 1, integer13, 0, 15, integer13, 0, cfj14, cfj14, false);
				this.a(bqu, ctd, 1, integer13, 7, 14, integer13, 7, cfj14, cfj14, false);
			}

			this.a(bqu, ctd, 2, 1, 3, 2, 7, 4, b, b, false);
			this.a(bqu, ctd, 3, 1, 2, 4, 7, 2, b, b, false);
			this.a(bqu, ctd, 3, 1, 5, 4, 7, 5, b, b, false);
			this.a(bqu, ctd, 13, 1, 3, 13, 7, 4, b, b, false);
			this.a(bqu, ctd, 11, 1, 2, 12, 7, 2, b, b, false);
			this.a(bqu, ctd, 11, 1, 5, 12, 7, 5, b, b, false);
			this.a(bqu, ctd, 5, 1, 3, 5, 3, 4, b, b, false);
			this.a(bqu, ctd, 10, 1, 3, 10, 3, 4, b, b, false);
			this.a(bqu, ctd, 5, 7, 2, 10, 7, 5, b, b, false);
			this.a(bqu, ctd, 5, 5, 2, 5, 7, 2, b, b, false);
			this.a(bqu, ctd, 10, 5, 2, 10, 7, 2, b, b, false);
			this.a(bqu, ctd, 5, 5, 5, 5, 7, 5, b, b, false);
			this.a(bqu, ctd, 10, 5, 5, 10, 7, 5, b, b, false);
			this.a(bqu, b, 6, 6, 2, ctd);
			this.a(bqu, b, 9, 6, 2, ctd);
			this.a(bqu, b, 6, 6, 5, ctd);
			this.a(bqu, b, 9, 6, 5, ctd);
			this.a(bqu, ctd, 5, 4, 3, 6, 4, 4, b, b, false);
			this.a(bqu, ctd, 9, 4, 3, 10, 4, 4, b, b, false);
			this.a(bqu, e, 5, 4, 2, ctd);
			this.a(bqu, e, 5, 4, 5, ctd);
			this.a(bqu, e, 10, 4, 2, ctd);
			this.a(bqu, e, 10, 4, 5, ctd);
			if (v10.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 3, 1, 0, 4, 2, 0);
			}

			if (v10.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 3, 1, 7, 4, 2, 7);
			}

			if (v10.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 1, 3, 0, 2, 4);
			}

			if (v9.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 11, 1, 0, 12, 2, 0);
			}

			if (v9.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 11, 1, 7, 12, 2, 7);
			}

			if (v9.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 15, 1, 3, 15, 2, 4);
			}

			if (v11.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 3, 5, 0, 4, 6, 0);
			}

			if (v11.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 3, 5, 7, 4, 6, 7);
			}

			if (v11.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 5, 3, 0, 6, 4);
			}

			if (v12.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 11, 5, 0, 12, 6, 0);
			}

			if (v12.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 11, 5, 7, 12, 6, 7);
			}

			if (v12.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 15, 5, 3, 15, 6, 4);
			}

			return true;
		}
	}

	public static class m extends cto.r {
		public m(fz fz, cto.v v) {
			super(cmm.S, 1, fz, v, 1, 2, 1);
		}

		public m(cva cva, le le) {
			super(cmm.S, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			if (this.l.a / 25 > 0) {
				this.a(bqu, ctd, 0, 0, this.l.c[fz.DOWN.c()]);
			}

			cto.v v9 = this.l.b[fz.UP.c()];
			if (v9.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 1, 8, 1, 6, 8, 6, a);
			}

			this.a(bqu, ctd, 0, 4, 0, 0, 4, 7, b, b, false);
			this.a(bqu, ctd, 7, 4, 0, 7, 4, 7, b, b, false);
			this.a(bqu, ctd, 1, 4, 0, 6, 4, 0, b, b, false);
			this.a(bqu, ctd, 1, 4, 7, 6, 4, 7, b, b, false);
			this.a(bqu, ctd, 2, 4, 1, 2, 4, 2, b, b, false);
			this.a(bqu, ctd, 1, 4, 2, 1, 4, 2, b, b, false);
			this.a(bqu, ctd, 5, 4, 1, 5, 4, 2, b, b, false);
			this.a(bqu, ctd, 6, 4, 2, 6, 4, 2, b, b, false);
			this.a(bqu, ctd, 2, 4, 5, 2, 4, 6, b, b, false);
			this.a(bqu, ctd, 1, 4, 5, 1, 4, 5, b, b, false);
			this.a(bqu, ctd, 5, 4, 5, 5, 4, 6, b, b, false);
			this.a(bqu, ctd, 6, 4, 5, 6, 4, 5, b, b, false);
			cto.v v10 = this.l;

			for (int integer11 = 1; integer11 <= 5; integer11 += 4) {
				int integer12 = 0;
				if (v10.c[fz.SOUTH.c()]) {
					this.a(bqu, ctd, 2, integer11, integer12, 2, integer11 + 2, integer12, b, b, false);
					this.a(bqu, ctd, 5, integer11, integer12, 5, integer11 + 2, integer12, b, b, false);
					this.a(bqu, ctd, 3, integer11 + 2, integer12, 4, integer11 + 2, integer12, b, b, false);
				} else {
					this.a(bqu, ctd, 0, integer11, integer12, 7, integer11 + 2, integer12, b, b, false);
					this.a(bqu, ctd, 0, integer11 + 1, integer12, 7, integer11 + 1, integer12, a, a, false);
				}

				int var13 = 7;
				if (v10.c[fz.NORTH.c()]) {
					this.a(bqu, ctd, 2, integer11, var13, 2, integer11 + 2, var13, b, b, false);
					this.a(bqu, ctd, 5, integer11, var13, 5, integer11 + 2, var13, b, b, false);
					this.a(bqu, ctd, 3, integer11 + 2, var13, 4, integer11 + 2, var13, b, b, false);
				} else {
					this.a(bqu, ctd, 0, integer11, var13, 7, integer11 + 2, var13, b, b, false);
					this.a(bqu, ctd, 0, integer11 + 1, var13, 7, integer11 + 1, var13, a, a, false);
				}

				int integer13 = 0;
				if (v10.c[fz.WEST.c()]) {
					this.a(bqu, ctd, integer13, integer11, 2, integer13, integer11 + 2, 2, b, b, false);
					this.a(bqu, ctd, integer13, integer11, 5, integer13, integer11 + 2, 5, b, b, false);
					this.a(bqu, ctd, integer13, integer11 + 2, 3, integer13, integer11 + 2, 4, b, b, false);
				} else {
					this.a(bqu, ctd, integer13, integer11, 0, integer13, integer11 + 2, 7, b, b, false);
					this.a(bqu, ctd, integer13, integer11 + 1, 0, integer13, integer11 + 1, 7, a, a, false);
				}

				int var14 = 7;
				if (v10.c[fz.EAST.c()]) {
					this.a(bqu, ctd, var14, integer11, 2, var14, integer11 + 2, 2, b, b, false);
					this.a(bqu, ctd, var14, integer11, 5, var14, integer11 + 2, 5, b, b, false);
					this.a(bqu, ctd, var14, integer11 + 2, 3, var14, integer11 + 2, 4, b, b, false);
				} else {
					this.a(bqu, ctd, var14, integer11, 0, var14, integer11 + 2, 7, b, b, false);
					this.a(bqu, ctd, var14, integer11 + 1, 0, var14, integer11 + 1, 7, a, a, false);
				}

				v10 = v9;
			}

			return true;
		}
	}

	public static class n extends cto.r {
		public n(fz fz, cto.v v) {
			super(cmm.T, 1, fz, v, 1, 2, 2);
		}

		public n(cva cva, le le) {
			super(cmm.T, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			cto.v v9 = this.l.b[fz.NORTH.c()];
			cto.v v10 = this.l;
			cto.v v11 = v9.b[fz.UP.c()];
			cto.v v12 = v10.b[fz.UP.c()];
			if (this.l.a / 25 > 0) {
				this.a(bqu, ctd, 0, 8, v9.c[fz.DOWN.c()]);
				this.a(bqu, ctd, 0, 0, v10.c[fz.DOWN.c()]);
			}

			if (v12.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 1, 8, 1, 6, 8, 7, a);
			}

			if (v11.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 1, 8, 8, 6, 8, 14, a);
			}

			for (int integer13 = 1; integer13 <= 7; integer13++) {
				cfj cfj14 = b;
				if (integer13 == 2 || integer13 == 6) {
					cfj14 = a;
				}

				this.a(bqu, ctd, 0, integer13, 0, 0, integer13, 15, cfj14, cfj14, false);
				this.a(bqu, ctd, 7, integer13, 0, 7, integer13, 15, cfj14, cfj14, false);
				this.a(bqu, ctd, 1, integer13, 0, 6, integer13, 0, cfj14, cfj14, false);
				this.a(bqu, ctd, 1, integer13, 15, 6, integer13, 15, cfj14, cfj14, false);
			}

			for (int integer13 = 1; integer13 <= 7; integer13++) {
				cfj cfj14 = c;
				if (integer13 == 2 || integer13 == 6) {
					cfj14 = e;
				}

				this.a(bqu, ctd, 3, integer13, 7, 4, integer13, 8, cfj14, cfj14, false);
			}

			if (v10.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 3, 1, 0, 4, 2, 0);
			}

			if (v10.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 7, 1, 3, 7, 2, 4);
			}

			if (v10.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 1, 3, 0, 2, 4);
			}

			if (v9.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 3, 1, 15, 4, 2, 15);
			}

			if (v9.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 1, 11, 0, 2, 12);
			}

			if (v9.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 7, 1, 11, 7, 2, 12);
			}

			if (v12.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 3, 5, 0, 4, 6, 0);
			}

			if (v12.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 7, 5, 3, 7, 6, 4);
				this.a(bqu, ctd, 5, 4, 2, 6, 4, 5, b, b, false);
				this.a(bqu, ctd, 6, 1, 2, 6, 3, 2, b, b, false);
				this.a(bqu, ctd, 6, 1, 5, 6, 3, 5, b, b, false);
			}

			if (v12.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 5, 3, 0, 6, 4);
				this.a(bqu, ctd, 1, 4, 2, 2, 4, 5, b, b, false);
				this.a(bqu, ctd, 1, 1, 2, 1, 3, 2, b, b, false);
				this.a(bqu, ctd, 1, 1, 5, 1, 3, 5, b, b, false);
			}

			if (v11.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 3, 5, 15, 4, 6, 15);
			}

			if (v11.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 5, 11, 0, 6, 12);
				this.a(bqu, ctd, 1, 4, 10, 2, 4, 13, b, b, false);
				this.a(bqu, ctd, 1, 1, 10, 1, 3, 10, b, b, false);
				this.a(bqu, ctd, 1, 1, 13, 1, 3, 13, b, b, false);
			}

			if (v11.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 7, 5, 11, 7, 6, 12);
				this.a(bqu, ctd, 5, 4, 10, 6, 4, 13, b, b, false);
				this.a(bqu, ctd, 6, 1, 10, 6, 3, 10, b, b, false);
				this.a(bqu, ctd, 6, 1, 13, 6, 3, 13, b, b, false);
			}

			return true;
		}
	}

	public static class o extends cto.r {
		public o(fz fz, cto.v v) {
			super(cmm.U, 1, fz, v, 1, 1, 2);
		}

		public o(cva cva, le le) {
			super(cmm.U, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			cto.v v9 = this.l.b[fz.NORTH.c()];
			cto.v v10 = this.l;
			if (this.l.a / 25 > 0) {
				this.a(bqu, ctd, 0, 8, v9.c[fz.DOWN.c()]);
				this.a(bqu, ctd, 0, 0, v10.c[fz.DOWN.c()]);
			}

			if (v10.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 1, 4, 1, 6, 4, 7, a);
			}

			if (v9.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 1, 4, 8, 6, 4, 14, a);
			}

			this.a(bqu, ctd, 0, 3, 0, 0, 3, 15, b, b, false);
			this.a(bqu, ctd, 7, 3, 0, 7, 3, 15, b, b, false);
			this.a(bqu, ctd, 1, 3, 0, 7, 3, 0, b, b, false);
			this.a(bqu, ctd, 1, 3, 15, 6, 3, 15, b, b, false);
			this.a(bqu, ctd, 0, 2, 0, 0, 2, 15, a, a, false);
			this.a(bqu, ctd, 7, 2, 0, 7, 2, 15, a, a, false);
			this.a(bqu, ctd, 1, 2, 0, 7, 2, 0, a, a, false);
			this.a(bqu, ctd, 1, 2, 15, 6, 2, 15, a, a, false);
			this.a(bqu, ctd, 0, 1, 0, 0, 1, 15, b, b, false);
			this.a(bqu, ctd, 7, 1, 0, 7, 1, 15, b, b, false);
			this.a(bqu, ctd, 1, 1, 0, 7, 1, 0, b, b, false);
			this.a(bqu, ctd, 1, 1, 15, 6, 1, 15, b, b, false);
			this.a(bqu, ctd, 1, 1, 1, 1, 1, 2, b, b, false);
			this.a(bqu, ctd, 6, 1, 1, 6, 1, 2, b, b, false);
			this.a(bqu, ctd, 1, 3, 1, 1, 3, 2, b, b, false);
			this.a(bqu, ctd, 6, 3, 1, 6, 3, 2, b, b, false);
			this.a(bqu, ctd, 1, 1, 13, 1, 1, 14, b, b, false);
			this.a(bqu, ctd, 6, 1, 13, 6, 1, 14, b, b, false);
			this.a(bqu, ctd, 1, 3, 13, 1, 3, 14, b, b, false);
			this.a(bqu, ctd, 6, 3, 13, 6, 3, 14, b, b, false);
			this.a(bqu, ctd, 2, 1, 6, 2, 3, 6, b, b, false);
			this.a(bqu, ctd, 5, 1, 6, 5, 3, 6, b, b, false);
			this.a(bqu, ctd, 2, 1, 9, 2, 3, 9, b, b, false);
			this.a(bqu, ctd, 5, 1, 9, 5, 3, 9, b, b, false);
			this.a(bqu, ctd, 3, 2, 6, 4, 2, 6, b, b, false);
			this.a(bqu, ctd, 3, 2, 9, 4, 2, 9, b, b, false);
			this.a(bqu, ctd, 2, 2, 7, 2, 2, 8, b, b, false);
			this.a(bqu, ctd, 5, 2, 7, 5, 2, 8, b, b, false);
			this.a(bqu, e, 2, 2, 5, ctd);
			this.a(bqu, e, 5, 2, 5, ctd);
			this.a(bqu, e, 2, 2, 10, ctd);
			this.a(bqu, e, 5, 2, 10, ctd);
			this.a(bqu, b, 2, 3, 5, ctd);
			this.a(bqu, b, 5, 3, 5, ctd);
			this.a(bqu, b, 2, 3, 10, ctd);
			this.a(bqu, b, 5, 3, 10, ctd);
			if (v10.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 3, 1, 0, 4, 2, 0);
			}

			if (v10.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 7, 1, 3, 7, 2, 4);
			}

			if (v10.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 1, 3, 0, 2, 4);
			}

			if (v9.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 3, 1, 15, 4, 2, 15);
			}

			if (v9.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 1, 11, 0, 2, 12);
			}

			if (v9.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 7, 1, 11, 7, 2, 12);
			}

			return true;
		}
	}

	public static class p extends cto.r {
		public p(fz fz, cto.v v) {
			super(cmm.V, 1, fz, v, 1, 1, 1);
		}

		public p(cva cva, le le) {
			super(cmm.V, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 0, 3, 0, 2, 3, 7, b, b, false);
			this.a(bqu, ctd, 5, 3, 0, 7, 3, 7, b, b, false);
			this.a(bqu, ctd, 0, 2, 0, 1, 2, 7, b, b, false);
			this.a(bqu, ctd, 6, 2, 0, 7, 2, 7, b, b, false);
			this.a(bqu, ctd, 0, 1, 0, 0, 1, 7, b, b, false);
			this.a(bqu, ctd, 7, 1, 0, 7, 1, 7, b, b, false);
			this.a(bqu, ctd, 0, 1, 7, 7, 3, 7, b, b, false);
			this.a(bqu, ctd, 1, 1, 0, 2, 3, 0, b, b, false);
			this.a(bqu, ctd, 5, 1, 0, 6, 3, 0, b, b, false);
			if (this.l.c[fz.NORTH.c()]) {
				this.a(bqu, ctd, 3, 1, 7, 4, 2, 7);
			}

			if (this.l.c[fz.WEST.c()]) {
				this.a(bqu, ctd, 0, 1, 3, 1, 2, 4);
			}

			if (this.l.c[fz.EAST.c()]) {
				this.a(bqu, ctd, 6, 1, 3, 7, 2, 4);
			}

			return true;
		}
	}

	public static class q extends cto.r {
		public q(fz fz, ctd ctd) {
			super(cmm.W, fz, ctd);
		}

		public q(cva cva, le le) {
			super(cmm.W, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.a(bqu, ctd, 2, -1, 2, 11, -1, 11, b, b, false);
			this.a(bqu, ctd, 0, -1, 0, 1, -1, 11, a, a, false);
			this.a(bqu, ctd, 12, -1, 0, 13, -1, 11, a, a, false);
			this.a(bqu, ctd, 2, -1, 0, 11, -1, 1, a, a, false);
			this.a(bqu, ctd, 2, -1, 12, 11, -1, 13, a, a, false);
			this.a(bqu, ctd, 0, 0, 0, 0, 0, 13, b, b, false);
			this.a(bqu, ctd, 13, 0, 0, 13, 0, 13, b, b, false);
			this.a(bqu, ctd, 1, 0, 0, 12, 0, 0, b, b, false);
			this.a(bqu, ctd, 1, 0, 13, 12, 0, 13, b, b, false);

			for (int integer9 = 2; integer9 <= 11; integer9 += 3) {
				this.a(bqu, e, 0, 0, integer9, ctd);
				this.a(bqu, e, 13, 0, integer9, ctd);
				this.a(bqu, e, integer9, 0, 0, ctd);
			}

			this.a(bqu, ctd, 2, 0, 3, 4, 0, 9, b, b, false);
			this.a(bqu, ctd, 9, 0, 3, 11, 0, 9, b, b, false);
			this.a(bqu, ctd, 4, 0, 9, 9, 0, 11, b, b, false);
			this.a(bqu, b, 5, 0, 8, ctd);
			this.a(bqu, b, 8, 0, 8, ctd);
			this.a(bqu, b, 10, 0, 10, ctd);
			this.a(bqu, b, 3, 0, 10, ctd);
			this.a(bqu, ctd, 3, 0, 3, 3, 0, 7, c, c, false);
			this.a(bqu, ctd, 10, 0, 3, 10, 0, 7, c, c, false);
			this.a(bqu, ctd, 6, 0, 10, 7, 0, 10, c, c, false);
			int integer9 = 3;

			for (int integer10 = 0; integer10 < 2; integer10++) {
				for (int integer11 = 2; integer11 <= 8; integer11 += 3) {
					this.a(bqu, ctd, integer9, 0, integer11, integer9, 2, integer11, b, b, false);
				}

				integer9 = 10;
			}

			this.a(bqu, ctd, 5, 0, 10, 5, 2, 10, b, b, false);
			this.a(bqu, ctd, 8, 0, 10, 8, 2, 10, b, b, false);
			this.a(bqu, ctd, 6, -1, 7, 7, -1, 8, c, c, false);
			this.a(bqu, ctd, 6, -1, 3, 7, -1, 4);
			this.a(bqu, ctd, 6, 1, 6);
			return true;
		}
	}

	public abstract static class r extends cty {
		protected static final cfj a = bvs.gq.n();
		protected static final cfj b = bvs.gr.n();
		protected static final cfj c = bvs.gs.n();
		protected static final cfj d = b;
		protected static final cfj e = bvs.gz.n();
		protected static final cfj f = bvs.A.n();
		protected static final Set<bvr> g = ImmutableSet.<bvr>builder().add(bvs.cD).add(bvs.gT).add(bvs.kV).add(f.b()).build();
		protected static final int h = b(2, 0, 0);
		protected static final int i = b(2, 2, 0);
		protected static final int j = b(0, 1, 0);
		protected static final int k = b(4, 1, 0);
		protected cto.v l;

		protected static final int b(int integer1, int integer2, int integer3) {
			return integer2 * 25 + integer3 * 5 + integer1;
		}

		public r(cmm cmm, int integer) {
			super(cmm, integer);
		}

		public r(cmm cmm, fz fz, ctd ctd) {
			super(cmm, 1);
			this.a(fz);
			this.n = ctd;
		}

		protected r(cmm cmm, int integer2, fz fz, cto.v v, int integer5, int integer6, int integer7) {
			super(cmm, integer2);
			this.a(fz);
			this.l = v;
			int integer9 = v.a;
			int integer10 = integer9 % 5;
			int integer11 = integer9 / 5 % 5;
			int integer12 = integer9 / 25;
			if (fz != fz.NORTH && fz != fz.SOUTH) {
				this.n = new ctd(0, 0, 0, integer7 * 8 - 1, integer6 * 4 - 1, integer5 * 8 - 1);
			} else {
				this.n = new ctd(0, 0, 0, integer5 * 8 - 1, integer6 * 4 - 1, integer7 * 8 - 1);
			}

			switch (fz) {
				case NORTH:
					this.n.a(integer10 * 8, integer12 * 4, -(integer11 + integer7) * 8 + 1);
					break;
				case SOUTH:
					this.n.a(integer10 * 8, integer12 * 4, integer11 * 8);
					break;
				case WEST:
					this.n.a(-(integer11 + integer7) * 8 + 1, integer12 * 4, integer10 * 8);
					break;
				default:
					this.n.a(integer11 * 8, integer12 * 4, integer10 * 8);
			}
		}

		public r(cmm cmm, le le) {
			super(cmm, le);
		}

		@Override
		protected void a(le le) {
		}

		protected void a(bqc bqc, ctd ctd, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8) {
			for (int integer10 = integer4; integer10 <= integer7; integer10++) {
				for (int integer11 = integer3; integer11 <= integer6; integer11++) {
					for (int integer12 = integer5; integer12 <= integer8; integer12++) {
						cfj cfj13 = this.a(bqc, integer11, integer10, integer12, ctd);
						if (!g.contains(cfj13.b())) {
							if (this.d(integer10) >= bqc.t_() && cfj13 != f) {
								this.a(bqc, bvs.a.n(), integer11, integer10, integer12, ctd);
							} else {
								this.a(bqc, f, integer11, integer10, integer12, ctd);
							}
						}
					}
				}
			}
		}

		protected void a(bqc bqc, ctd ctd, int integer3, int integer4, boolean boolean5) {
			if (boolean5) {
				this.a(bqc, ctd, integer3 + 0, 0, integer4 + 0, integer3 + 2, 0, integer4 + 8 - 1, a, a, false);
				this.a(bqc, ctd, integer3 + 5, 0, integer4 + 0, integer3 + 8 - 1, 0, integer4 + 8 - 1, a, a, false);
				this.a(bqc, ctd, integer3 + 3, 0, integer4 + 0, integer3 + 4, 0, integer4 + 2, a, a, false);
				this.a(bqc, ctd, integer3 + 3, 0, integer4 + 5, integer3 + 4, 0, integer4 + 8 - 1, a, a, false);
				this.a(bqc, ctd, integer3 + 3, 0, integer4 + 2, integer3 + 4, 0, integer4 + 2, b, b, false);
				this.a(bqc, ctd, integer3 + 3, 0, integer4 + 5, integer3 + 4, 0, integer4 + 5, b, b, false);
				this.a(bqc, ctd, integer3 + 2, 0, integer4 + 3, integer3 + 2, 0, integer4 + 4, b, b, false);
				this.a(bqc, ctd, integer3 + 5, 0, integer4 + 3, integer3 + 5, 0, integer4 + 4, b, b, false);
			} else {
				this.a(bqc, ctd, integer3 + 0, 0, integer4 + 0, integer3 + 8 - 1, 0, integer4 + 8 - 1, a, a, false);
			}
		}

		protected void a(bqc bqc, ctd ctd, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, cfj cfj) {
			for (int integer11 = integer4; integer11 <= integer7; integer11++) {
				for (int integer12 = integer3; integer12 <= integer6; integer12++) {
					for (int integer13 = integer5; integer13 <= integer8; integer13++) {
						if (this.a(bqc, integer12, integer11, integer13, ctd) == f) {
							this.a(bqc, cfj, integer12, integer11, integer13, ctd);
						}
					}
				}
			}
		}

		protected boolean a(ctd ctd, int integer2, int integer3, int integer4, int integer5) {
			int integer7 = this.a(integer2, integer3);
			int integer8 = this.b(integer2, integer3);
			int integer9 = this.a(integer4, integer5);
			int integer10 = this.b(integer4, integer5);
			return ctd.a(Math.min(integer7, integer9), Math.min(integer8, integer10), Math.max(integer7, integer9), Math.max(integer8, integer10));
		}

		protected boolean a(bqc bqc, ctd ctd, int integer3, int integer4, int integer5) {
			int integer7 = this.a(integer3, integer5);
			int integer8 = this.d(integer4);
			int integer9 = this.b(integer3, integer5);
			if (ctd.b(new fu(integer7, integer8, integer9))) {
				bbq bbq10 = aoq.r.a(bqc.n());
				bbq10.b(bbq10.dw());
				bbq10.b((double)integer7 + 0.5, (double)integer8, (double)integer9 + 0.5, 0.0F, 0.0F);
				bbq10.a(bqc, bqc.d(bbq10.cA()), apb.STRUCTURE, null, null);
				bqc.c(bbq10);
				return true;
			} else {
				return false;
			}
		}
	}

	public static class s extends cto.r {
		private int p;

		public s(fz fz, cto.v v, Random random) {
			super(cmm.X, 1, fz, v, 1, 1, 1);
			this.p = random.nextInt(3);
		}

		public s(cva cva, le le) {
			super(cmm.X, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			if (this.l.a / 25 > 0) {
				this.a(bqu, ctd, 0, 0, this.l.c[fz.DOWN.c()]);
			}

			if (this.l.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 1, 4, 1, 6, 4, 6, a);
			}

			boolean boolean9 = this.p != 0 && random.nextBoolean() && !this.l.c[fz.DOWN.c()] && !this.l.c[fz.UP.c()] && this.l.c() > 1;
			if (this.p == 0) {
				this.a(bqu, ctd, 0, 1, 0, 2, 1, 2, b, b, false);
				this.a(bqu, ctd, 0, 3, 0, 2, 3, 2, b, b, false);
				this.a(bqu, ctd, 0, 2, 0, 0, 2, 2, a, a, false);
				this.a(bqu, ctd, 1, 2, 0, 2, 2, 0, a, a, false);
				this.a(bqu, e, 1, 2, 1, ctd);
				this.a(bqu, ctd, 5, 1, 0, 7, 1, 2, b, b, false);
				this.a(bqu, ctd, 5, 3, 0, 7, 3, 2, b, b, false);
				this.a(bqu, ctd, 7, 2, 0, 7, 2, 2, a, a, false);
				this.a(bqu, ctd, 5, 2, 0, 6, 2, 0, a, a, false);
				this.a(bqu, e, 6, 2, 1, ctd);
				this.a(bqu, ctd, 0, 1, 5, 2, 1, 7, b, b, false);
				this.a(bqu, ctd, 0, 3, 5, 2, 3, 7, b, b, false);
				this.a(bqu, ctd, 0, 2, 5, 0, 2, 7, a, a, false);
				this.a(bqu, ctd, 1, 2, 7, 2, 2, 7, a, a, false);
				this.a(bqu, e, 1, 2, 6, ctd);
				this.a(bqu, ctd, 5, 1, 5, 7, 1, 7, b, b, false);
				this.a(bqu, ctd, 5, 3, 5, 7, 3, 7, b, b, false);
				this.a(bqu, ctd, 7, 2, 5, 7, 2, 7, a, a, false);
				this.a(bqu, ctd, 5, 2, 7, 6, 2, 7, a, a, false);
				this.a(bqu, e, 6, 2, 6, ctd);
				if (this.l.c[fz.SOUTH.c()]) {
					this.a(bqu, ctd, 3, 3, 0, 4, 3, 0, b, b, false);
				} else {
					this.a(bqu, ctd, 3, 3, 0, 4, 3, 1, b, b, false);
					this.a(bqu, ctd, 3, 2, 0, 4, 2, 0, a, a, false);
					this.a(bqu, ctd, 3, 1, 0, 4, 1, 1, b, b, false);
				}

				if (this.l.c[fz.NORTH.c()]) {
					this.a(bqu, ctd, 3, 3, 7, 4, 3, 7, b, b, false);
				} else {
					this.a(bqu, ctd, 3, 3, 6, 4, 3, 7, b, b, false);
					this.a(bqu, ctd, 3, 2, 7, 4, 2, 7, a, a, false);
					this.a(bqu, ctd, 3, 1, 6, 4, 1, 7, b, b, false);
				}

				if (this.l.c[fz.WEST.c()]) {
					this.a(bqu, ctd, 0, 3, 3, 0, 3, 4, b, b, false);
				} else {
					this.a(bqu, ctd, 0, 3, 3, 1, 3, 4, b, b, false);
					this.a(bqu, ctd, 0, 2, 3, 0, 2, 4, a, a, false);
					this.a(bqu, ctd, 0, 1, 3, 1, 1, 4, b, b, false);
				}

				if (this.l.c[fz.EAST.c()]) {
					this.a(bqu, ctd, 7, 3, 3, 7, 3, 4, b, b, false);
				} else {
					this.a(bqu, ctd, 6, 3, 3, 7, 3, 4, b, b, false);
					this.a(bqu, ctd, 7, 2, 3, 7, 2, 4, a, a, false);
					this.a(bqu, ctd, 6, 1, 3, 7, 1, 4, b, b, false);
				}
			} else if (this.p == 1) {
				this.a(bqu, ctd, 2, 1, 2, 2, 3, 2, b, b, false);
				this.a(bqu, ctd, 2, 1, 5, 2, 3, 5, b, b, false);
				this.a(bqu, ctd, 5, 1, 5, 5, 3, 5, b, b, false);
				this.a(bqu, ctd, 5, 1, 2, 5, 3, 2, b, b, false);
				this.a(bqu, e, 2, 2, 2, ctd);
				this.a(bqu, e, 2, 2, 5, ctd);
				this.a(bqu, e, 5, 2, 5, ctd);
				this.a(bqu, e, 5, 2, 2, ctd);
				this.a(bqu, ctd, 0, 1, 0, 1, 3, 0, b, b, false);
				this.a(bqu, ctd, 0, 1, 1, 0, 3, 1, b, b, false);
				this.a(bqu, ctd, 0, 1, 7, 1, 3, 7, b, b, false);
				this.a(bqu, ctd, 0, 1, 6, 0, 3, 6, b, b, false);
				this.a(bqu, ctd, 6, 1, 7, 7, 3, 7, b, b, false);
				this.a(bqu, ctd, 7, 1, 6, 7, 3, 6, b, b, false);
				this.a(bqu, ctd, 6, 1, 0, 7, 3, 0, b, b, false);
				this.a(bqu, ctd, 7, 1, 1, 7, 3, 1, b, b, false);
				this.a(bqu, a, 1, 2, 0, ctd);
				this.a(bqu, a, 0, 2, 1, ctd);
				this.a(bqu, a, 1, 2, 7, ctd);
				this.a(bqu, a, 0, 2, 6, ctd);
				this.a(bqu, a, 6, 2, 7, ctd);
				this.a(bqu, a, 7, 2, 6, ctd);
				this.a(bqu, a, 6, 2, 0, ctd);
				this.a(bqu, a, 7, 2, 1, ctd);
				if (!this.l.c[fz.SOUTH.c()]) {
					this.a(bqu, ctd, 1, 3, 0, 6, 3, 0, b, b, false);
					this.a(bqu, ctd, 1, 2, 0, 6, 2, 0, a, a, false);
					this.a(bqu, ctd, 1, 1, 0, 6, 1, 0, b, b, false);
				}

				if (!this.l.c[fz.NORTH.c()]) {
					this.a(bqu, ctd, 1, 3, 7, 6, 3, 7, b, b, false);
					this.a(bqu, ctd, 1, 2, 7, 6, 2, 7, a, a, false);
					this.a(bqu, ctd, 1, 1, 7, 6, 1, 7, b, b, false);
				}

				if (!this.l.c[fz.WEST.c()]) {
					this.a(bqu, ctd, 0, 3, 1, 0, 3, 6, b, b, false);
					this.a(bqu, ctd, 0, 2, 1, 0, 2, 6, a, a, false);
					this.a(bqu, ctd, 0, 1, 1, 0, 1, 6, b, b, false);
				}

				if (!this.l.c[fz.EAST.c()]) {
					this.a(bqu, ctd, 7, 3, 1, 7, 3, 6, b, b, false);
					this.a(bqu, ctd, 7, 2, 1, 7, 2, 6, a, a, false);
					this.a(bqu, ctd, 7, 1, 1, 7, 1, 6, b, b, false);
				}
			} else if (this.p == 2) {
				this.a(bqu, ctd, 0, 1, 0, 0, 1, 7, b, b, false);
				this.a(bqu, ctd, 7, 1, 0, 7, 1, 7, b, b, false);
				this.a(bqu, ctd, 1, 1, 0, 6, 1, 0, b, b, false);
				this.a(bqu, ctd, 1, 1, 7, 6, 1, 7, b, b, false);
				this.a(bqu, ctd, 0, 2, 0, 0, 2, 7, c, c, false);
				this.a(bqu, ctd, 7, 2, 0, 7, 2, 7, c, c, false);
				this.a(bqu, ctd, 1, 2, 0, 6, 2, 0, c, c, false);
				this.a(bqu, ctd, 1, 2, 7, 6, 2, 7, c, c, false);
				this.a(bqu, ctd, 0, 3, 0, 0, 3, 7, b, b, false);
				this.a(bqu, ctd, 7, 3, 0, 7, 3, 7, b, b, false);
				this.a(bqu, ctd, 1, 3, 0, 6, 3, 0, b, b, false);
				this.a(bqu, ctd, 1, 3, 7, 6, 3, 7, b, b, false);
				this.a(bqu, ctd, 0, 1, 3, 0, 2, 4, c, c, false);
				this.a(bqu, ctd, 7, 1, 3, 7, 2, 4, c, c, false);
				this.a(bqu, ctd, 3, 1, 0, 4, 2, 0, c, c, false);
				this.a(bqu, ctd, 3, 1, 7, 4, 2, 7, c, c, false);
				if (this.l.c[fz.SOUTH.c()]) {
					this.a(bqu, ctd, 3, 1, 0, 4, 2, 0);
				}

				if (this.l.c[fz.NORTH.c()]) {
					this.a(bqu, ctd, 3, 1, 7, 4, 2, 7);
				}

				if (this.l.c[fz.WEST.c()]) {
					this.a(bqu, ctd, 0, 1, 3, 0, 2, 4);
				}

				if (this.l.c[fz.EAST.c()]) {
					this.a(bqu, ctd, 7, 1, 3, 7, 2, 4);
				}
			}

			if (boolean9) {
				this.a(bqu, ctd, 3, 1, 3, 4, 1, 4, b, b, false);
				this.a(bqu, ctd, 3, 2, 3, 4, 2, 4, a, a, false);
				this.a(bqu, ctd, 3, 3, 3, 4, 3, 4, b, b, false);
			}

			return true;
		}
	}

	public static class t extends cto.r {
		public t(fz fz, cto.v v) {
			super(cmm.Y, 1, fz, v, 1, 1, 1);
		}

		public t(cva cva, le le) {
			super(cmm.Y, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			if (this.l.a / 25 > 0) {
				this.a(bqu, ctd, 0, 0, this.l.c[fz.DOWN.c()]);
			}

			if (this.l.b[fz.UP.c()] == null) {
				this.a(bqu, ctd, 1, 4, 1, 6, 4, 6, a);
			}

			for (int integer9 = 1; integer9 <= 6; integer9++) {
				for (int integer10 = 1; integer10 <= 6; integer10++) {
					if (random.nextInt(3) != 0) {
						int integer11 = 2 + (random.nextInt(4) == 0 ? 0 : 1);
						cfj cfj12 = bvs.ao.n();
						this.a(bqu, ctd, integer9, integer11, integer10, integer9, 3, integer10, cfj12, cfj12, false);
					}
				}
			}

			this.a(bqu, ctd, 0, 1, 0, 0, 1, 7, b, b, false);
			this.a(bqu, ctd, 7, 1, 0, 7, 1, 7, b, b, false);
			this.a(bqu, ctd, 1, 1, 0, 6, 1, 0, b, b, false);
			this.a(bqu, ctd, 1, 1, 7, 6, 1, 7, b, b, false);
			this.a(bqu, ctd, 0, 2, 0, 0, 2, 7, c, c, false);
			this.a(bqu, ctd, 7, 2, 0, 7, 2, 7, c, c, false);
			this.a(bqu, ctd, 1, 2, 0, 6, 2, 0, c, c, false);
			this.a(bqu, ctd, 1, 2, 7, 6, 2, 7, c, c, false);
			this.a(bqu, ctd, 0, 3, 0, 0, 3, 7, b, b, false);
			this.a(bqu, ctd, 7, 3, 0, 7, 3, 7, b, b, false);
			this.a(bqu, ctd, 1, 3, 0, 6, 3, 0, b, b, false);
			this.a(bqu, ctd, 1, 3, 7, 6, 3, 7, b, b, false);
			this.a(bqu, ctd, 0, 1, 3, 0, 2, 4, c, c, false);
			this.a(bqu, ctd, 7, 1, 3, 7, 2, 4, c, c, false);
			this.a(bqu, ctd, 3, 1, 0, 4, 2, 0, c, c, false);
			this.a(bqu, ctd, 3, 1, 7, 4, 2, 7, c, c, false);
			if (this.l.c[fz.SOUTH.c()]) {
				this.a(bqu, ctd, 3, 1, 0, 4, 2, 0);
			}

			return true;
		}
	}

	public static class u extends cto.r {
		private int p;

		public u(fz fz, ctd ctd, int integer) {
			super(cmm.Z, fz, ctd);
			this.p = integer & 1;
		}

		public u(cva cva, le le) {
			super(cmm.Z, le);
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			if (this.p == 0) {
				for (int integer9 = 0; integer9 < 4; integer9++) {
					this.a(bqu, ctd, 10 - integer9, 3 - integer9, 20 - integer9, 12 + integer9, 3 - integer9, 20, b, b, false);
				}

				this.a(bqu, ctd, 7, 0, 6, 15, 0, 16, b, b, false);
				this.a(bqu, ctd, 6, 0, 6, 6, 3, 20, b, b, false);
				this.a(bqu, ctd, 16, 0, 6, 16, 3, 20, b, b, false);
				this.a(bqu, ctd, 7, 1, 7, 7, 1, 20, b, b, false);
				this.a(bqu, ctd, 15, 1, 7, 15, 1, 20, b, b, false);
				this.a(bqu, ctd, 7, 1, 6, 9, 3, 6, b, b, false);
				this.a(bqu, ctd, 13, 1, 6, 15, 3, 6, b, b, false);
				this.a(bqu, ctd, 8, 1, 7, 9, 1, 7, b, b, false);
				this.a(bqu, ctd, 13, 1, 7, 14, 1, 7, b, b, false);
				this.a(bqu, ctd, 9, 0, 5, 13, 0, 5, b, b, false);
				this.a(bqu, ctd, 10, 0, 7, 12, 0, 7, c, c, false);
				this.a(bqu, ctd, 8, 0, 10, 8, 0, 12, c, c, false);
				this.a(bqu, ctd, 14, 0, 10, 14, 0, 12, c, c, false);

				for (int integer9 = 18; integer9 >= 7; integer9 -= 3) {
					this.a(bqu, e, 6, 3, integer9, ctd);
					this.a(bqu, e, 16, 3, integer9, ctd);
				}

				this.a(bqu, e, 10, 0, 10, ctd);
				this.a(bqu, e, 12, 0, 10, ctd);
				this.a(bqu, e, 10, 0, 12, ctd);
				this.a(bqu, e, 12, 0, 12, ctd);
				this.a(bqu, e, 8, 3, 6, ctd);
				this.a(bqu, e, 14, 3, 6, ctd);
				this.a(bqu, b, 4, 2, 4, ctd);
				this.a(bqu, e, 4, 1, 4, ctd);
				this.a(bqu, b, 4, 0, 4, ctd);
				this.a(bqu, b, 18, 2, 4, ctd);
				this.a(bqu, e, 18, 1, 4, ctd);
				this.a(bqu, b, 18, 0, 4, ctd);
				this.a(bqu, b, 4, 2, 18, ctd);
				this.a(bqu, e, 4, 1, 18, ctd);
				this.a(bqu, b, 4, 0, 18, ctd);
				this.a(bqu, b, 18, 2, 18, ctd);
				this.a(bqu, e, 18, 1, 18, ctd);
				this.a(bqu, b, 18, 0, 18, ctd);
				this.a(bqu, b, 9, 7, 20, ctd);
				this.a(bqu, b, 13, 7, 20, ctd);
				this.a(bqu, ctd, 6, 0, 21, 7, 4, 21, b, b, false);
				this.a(bqu, ctd, 15, 0, 21, 16, 4, 21, b, b, false);
				this.a(bqu, ctd, 11, 2, 16);
			} else if (this.p == 1) {
				this.a(bqu, ctd, 9, 3, 18, 13, 3, 20, b, b, false);
				this.a(bqu, ctd, 9, 0, 18, 9, 2, 18, b, b, false);
				this.a(bqu, ctd, 13, 0, 18, 13, 2, 18, b, b, false);
				int integer9 = 9;
				int integer10 = 20;
				int integer11 = 5;

				for (int integer12 = 0; integer12 < 2; integer12++) {
					this.a(bqu, b, integer9, 6, 20, ctd);
					this.a(bqu, e, integer9, 5, 20, ctd);
					this.a(bqu, b, integer9, 4, 20, ctd);
					integer9 = 13;
				}

				this.a(bqu, ctd, 7, 3, 7, 15, 3, 14, b, b, false);
				int var14 = 10;

				for (int integer12 = 0; integer12 < 2; integer12++) {
					this.a(bqu, ctd, var14, 0, 10, var14, 6, 10, b, b, false);
					this.a(bqu, ctd, var14, 0, 12, var14, 6, 12, b, b, false);
					this.a(bqu, e, var14, 0, 10, ctd);
					this.a(bqu, e, var14, 0, 12, ctd);
					this.a(bqu, e, var14, 4, 10, ctd);
					this.a(bqu, e, var14, 4, 12, ctd);
					var14 = 12;
				}

				var14 = 8;

				for (int integer12 = 0; integer12 < 2; integer12++) {
					this.a(bqu, ctd, var14, 0, 7, var14, 2, 7, b, b, false);
					this.a(bqu, ctd, var14, 0, 14, var14, 2, 14, b, b, false);
					var14 = 14;
				}

				this.a(bqu, ctd, 8, 3, 8, 8, 3, 13, c, c, false);
				this.a(bqu, ctd, 14, 3, 8, 14, 3, 13, c, c, false);
				this.a(bqu, ctd, 11, 5, 13);
			}

			return true;
		}
	}

	static class v {
		private final int a;
		private final cto.v[] b = new cto.v[6];
		private final boolean[] c = new boolean[6];
		private boolean d;
		private boolean e;
		private int f;

		public v(int integer) {
			this.a = integer;
		}

		public void a(fz fz, cto.v v) {
			this.b[fz.c()] = v;
			v.b[fz.f().c()] = this;
		}

		public void a() {
			for (int integer2 = 0; integer2 < 6; integer2++) {
				this.c[integer2] = this.b[integer2] != null;
			}
		}

		public boolean a(int integer) {
			if (this.e) {
				return true;
			} else {
				this.f = integer;

				for (int integer3 = 0; integer3 < 6; integer3++) {
					if (this.b[integer3] != null && this.c[integer3] && this.b[integer3].f != integer && this.b[integer3].a(integer)) {
						return true;
					}
				}

				return false;
			}
		}

		public boolean b() {
			return this.a >= 75;
		}

		public int c() {
			int integer2 = 0;

			for (int integer3 = 0; integer3 < 6; integer3++) {
				if (this.c[integer3]) {
					integer2++;
				}
			}

			return integer2;
		}
	}
}
