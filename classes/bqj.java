import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class bqj {
	private static final Logger a = LogManager.getLogger();
	private static final int b = (int)Math.pow(17.0, 2.0);
	private static final apa[] c = (apa[])Stream.of(apa.values()).filter(apa -> apa != apa.MISC).toArray(apa[]::new);

	public static bqj.d a(int integer, Iterable<aom> iterable, bqj.b b) {
		bqn bqn4 = new bqn();
		Object2IntOpenHashMap<apa> object2IntOpenHashMap5 = new Object2IntOpenHashMap<>();

		for (aom aom7 : iterable) {
			if (aom7 instanceof aoz) {
				aoz aoz8 = (aoz)aom7;
				if (aoz8.ev() || aoz8.K()) {
					continue;
				}
			}

			apa apa8 = aom7.U().e();
			if (apa8 != apa.MISC) {
				fu fu9 = aom7.cA();
				long long10 = bph.a(fu9.u() >> 4, fu9.w() >> 4);
				b.query(long10, chj -> {
					bre bre7 = b(fu9, chj);
					bre.e e8 = bre7.a(aom7.U());
					if (e8 != null) {
						bqn4.a(aom7.cA(), e8.b());
					}

					object2IntOpenHashMap5.addTo(apa8, 1);
				});
			}
		}

		return new bqj.d(integer, object2IntOpenHashMap5, bqn4);
	}

	private static bre b(fu fu, cgy cgy) {
		return btf.INSTANCE.a(0L, fu.u(), fu.v(), fu.w(), cgy.i());
	}

	public static void a(zd zd, chj chj, bqj.d d, boolean boolean4, boolean boolean5, boolean boolean6) {
		zd.X().a("spawner");

		for (apa apa10 : c) {
			if ((boolean4 || !apa10.d()) && (boolean5 || apa10.d()) && (boolean6 || !apa10.e()) && d.a(apa10)) {
				a(apa10, zd, chj, (aoq, fu, cgy) -> d.a(aoq, fu, cgy), (aoz, cgy) -> d.a(aoz, cgy));
			}
		}

		zd.X().c();
	}

	public static void a(apa apa, zd zd, chj chj, bqj.c c, bqj.a a) {
		fu fu6 = a(zd, chj);
		if (fu6.v() >= 1) {
			a(apa, zd, chj, fu6, c, a);
		}
	}

	public static void a(apa apa, zd zd, cgy cgy, fu fu, bqj.c c, bqj.a a) {
		bqq bqq7 = zd.a();
		cha cha8 = zd.i().g();
		int integer9 = fu.v();
		cfj cfj10 = cgy.d_(fu);
		if (!cfj10.g(cgy, fu)) {
			fu.a a11 = new fu.a();
			int integer12 = 0;

			for (int integer13 = 0; integer13 < 3; integer13++) {
				int integer14 = fu.u();
				int integer15 = fu.w();
				int integer16 = 6;
				bre.g g17 = null;
				apo apo18 = null;
				int integer19 = aec.f(zd.t.nextFloat() * 4.0F);
				int integer20 = 0;

				for (int integer21 = 0; integer21 < integer19; integer21++) {
					integer14 += zd.t.nextInt(6) - zd.t.nextInt(6);
					integer15 += zd.t.nextInt(6) - zd.t.nextInt(6);
					a11.d(integer14, integer9, integer15);
					double double22 = (double)integer14 + 0.5;
					double double24 = (double)integer15 + 0.5;
					bec bec26 = zd.a(double22, (double)integer9, double24, -1.0, false);
					if (bec26 != null) {
						double double27 = bec26.g(double22, (double)integer9, double24);
						if (a(zd, cgy, a11, double27)) {
							if (g17 == null) {
								g17 = a(zd, bqq7, cha8, apa, zd.t, a11);
								if (g17 == null) {
									break;
								}

								integer19 = g17.d + zd.t.nextInt(1 + g17.e - g17.d);
							}

							if (a(zd, apa, bqq7, cha8, g17, a11, double27) && c.test(g17.c, a11, cgy)) {
								aoz aoz29 = a(zd, g17.c);
								if (aoz29 == null) {
									return;
								}

								aoz29.b(double22, (double)integer9, double24, zd.t.nextFloat() * 360.0F, 0.0F);
								if (a(zd, aoz29, double27)) {
									apo18 = aoz29.a(zd, zd.d(aoz29.cA()), apb.NATURAL, apo18, null);
									integer12++;
									integer20++;
									zd.c(aoz29);
									a.run(aoz29, cgy);
									if (integer12 >= aoz29.er()) {
										return;
									}

									if (aoz29.c(integer20)) {
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static boolean a(zd zd, cgy cgy, fu.a a, double double4) {
		if (double4 <= 576.0) {
			return false;
		} else if (zd.u().a(new dem((double)a.u() + 0.5, (double)a.v(), (double)a.w() + 0.5), 24.0)) {
			return false;
		} else {
			bph bph6 = new bph(a);
			return Objects.equals(bph6, cgy.g()) || zd.i().a(bph6);
		}
	}

	private static boolean a(zd zd, apa apa, bqq bqq, cha cha, bre.g g, fu.a a, double double7) {
		aoq<?> aoq9 = g.c;
		if (aoq9.e() == apa.MISC) {
			return false;
		} else if (!aoq9.d() && double7 > (double)(aoq9.e().f() * aoq9.e().f())) {
			return false;
		} else if (aoq9.b() && a(zd, bqq, cha, apa, g, a)) {
			app.c c10 = app.a(aoq9);
			if (!a(c10, zd, a, aoq9)) {
				return false;
			} else {
				return !app.a(aoq9, zd, apb.NATURAL, a, zd.t) ? false : zd.b(aoq9.a((double)a.u() + 0.5, (double)a.v(), (double)a.w() + 0.5));
			}
		} else {
			return false;
		}
	}

	@Nullable
	private static aoz a(zd zd, aoq<?> aoq) {
		try {
			aom aom4 = aoq.a(zd);
			if (!(aom4 instanceof aoz)) {
				throw new IllegalStateException("Trying to spawn a non-mob: " + gl.al.b(aoq));
			} else {
				return (aoz)aom4;
			}
		} catch (Exception var4) {
			a.warn("Failed to create mob", (Throwable)var4);
			return null;
		}
	}

	private static boolean a(zd zd, aoz aoz, double double3) {
		return double3 > (double)(aoz.U().e().f() * aoz.U().e().f()) && aoz.h(double3) ? false : aoz.a(zd, apb.NATURAL) && aoz.a(zd);
	}

	@Nullable
	private static bre.g a(zd zd, bqq bqq, cha cha, apa apa, Random random, fu fu) {
		bre bre7 = zd.v(fu);
		if (apa == apa.WATER_AMBIENT && bre7.y() == bre.b.RIVER && random.nextFloat() < 0.98F) {
			return null;
		} else {
			List<bre.g> list8 = a(zd, bqq, cha, apa, fu, bre7);
			return list8.isEmpty() ? null : aen.a(random, list8);
		}
	}

	private static boolean a(zd zd, bqq bqq, cha cha, apa apa, bre.g g, fu fu) {
		return a(zd, bqq, cha, apa, fu, null).contains(g);
	}

	private static List<bre.g> a(zd zd, bqq bqq, cha cha, apa apa, fu fu, @Nullable bre bre) {
		return apa == apa.MONSTER && zd.d_(fu.c()).b() == bvs.dV && bqq.a(fu, false, cml.n).e() ? cml.n.c() : cha.a(bre != null ? bre : zd.v(fu), bqq, apa, fu);
	}

	private static fu a(bqb bqb, chj chj) {
		bph bph3 = chj.g();
		int integer4 = bph3.d() + bqb.t.nextInt(16);
		int integer5 = bph3.e() + bqb.t.nextInt(16);
		int integer6 = chj.a(cio.a.WORLD_SURFACE, integer4, integer5) + 1;
		int integer7 = bqb.t.nextInt(integer6 + 1);
		return new fu(integer4, integer7, integer5);
	}

	public static boolean a(bpg bpg, fu fu, cfj cfj, cxa cxa, aoq aoq) {
		if (cfj.r(bpg, fu)) {
			return false;
		} else if (cfj.i()) {
			return false;
		} else if (!cxa.c()) {
			return false;
		} else {
			return cfj.a(acx.az) ? false : !aoq.a(cfj);
		}
	}

	public static boolean a(app.c c, bqd bqd, fu fu, @Nullable aoq<?> aoq) {
		if (c == app.c.NO_RESTRICTIONS) {
			return true;
		} else if (aoq != null && bqd.f().a(fu)) {
			cfj cfj5 = bqd.d_(fu);
			cxa cxa6 = bqd.b(fu);
			fu fu7 = fu.b();
			fu fu8 = fu.c();
			switch (c) {
				case IN_WATER:
					return cxa6.a(acz.a) && bqd.b(fu8).a(acz.a) && !bqd.d_(fu7).g(bqd, fu7);
				case IN_LAVA:
					return cxa6.a(acz.b);
				case ON_GROUND:
				default:
					cfj cfj9 = bqd.d_(fu8);
					return !cfj9.a(bqd, fu8, aoq) ? false : a(bqd, fu, cfj5, cxa6, aoq) && a(bqd, fu7, bqd.d_(fu7), bqd.b(fu7), aoq);
			}
		} else {
			return false;
		}
	}

	public static void a(bqc bqc, bre bre, int integer3, int integer4, Random random) {
		List<bre.g> list6 = bre.a(apa.CREATURE);
		if (!list6.isEmpty()) {
			int integer7 = integer3 << 4;
			int integer8 = integer4 << 4;

			while (random.nextFloat() < bre.f()) {
				bre.g g9 = aen.a(random, list6);
				int integer10 = g9.d + random.nextInt(1 + g9.e - g9.d);
				apo apo11 = null;
				int integer12 = integer7 + random.nextInt(16);
				int integer13 = integer8 + random.nextInt(16);
				int integer14 = integer12;
				int integer15 = integer13;

				for (int integer16 = 0; integer16 < integer10; integer16++) {
					boolean boolean17 = false;

					for (int integer18 = 0; !boolean17 && integer18 < 4; integer18++) {
						fu fu19 = a(bqc, g9.c, integer12, integer13);
						if (g9.c.b() && a(app.a(g9.c), bqc, fu19, g9.c)) {
							float float20 = g9.c.j();
							double double21 = aec.a((double)integer12, (double)integer7 + (double)float20, (double)integer7 + 16.0 - (double)float20);
							double double23 = aec.a((double)integer13, (double)integer8 + (double)float20, (double)integer8 + 16.0 - (double)float20);
							if (!bqc.b(g9.c.a(double21, (double)fu19.v(), double23))
								|| !app.a(g9.c, bqc, apb.CHUNK_GENERATION, new fu(double21, (double)fu19.v(), double23), bqc.v_())) {
								continue;
							}

							aom aom25;
							try {
								aom25 = g9.c.a(bqc.n());
							} catch (Exception var26) {
								a.warn("Failed to create mob", (Throwable)var26);
								continue;
							}

							aom25.b(double21, (double)fu19.v(), double23, random.nextFloat() * 360.0F, 0.0F);
							if (aom25 instanceof aoz) {
								aoz aoz26 = (aoz)aom25;
								if (aoz26.a(bqc, apb.CHUNK_GENERATION) && aoz26.a(bqc)) {
									apo11 = aoz26.a(bqc, bqc.d(aoz26.cA()), apb.CHUNK_GENERATION, apo11, null);
									bqc.c(aoz26);
									boolean17 = true;
								}
							}
						}

						integer12 += random.nextInt(5) - random.nextInt(5);

						for (integer13 += random.nextInt(5) - random.nextInt(5);
							integer12 < integer7 || integer12 >= integer7 + 16 || integer13 < integer8 || integer13 >= integer8 + 16;
							integer13 = integer15 + random.nextInt(5) - random.nextInt(5)
						) {
							integer12 = integer14 + random.nextInt(5) - random.nextInt(5);
						}
					}
				}
			}
		}
	}

	private static fu a(bqd bqd, aoq<?> aoq, int integer3, int integer4) {
		int integer5 = bqd.a(app.b(aoq), integer3, integer4);
		fu.a a6 = new fu.a(integer3, integer5, integer4);
		if (bqd.m().e()) {
			do {
				a6.c(fz.DOWN);
			} while (!bqd.d_(a6).g());

			do {
				a6.c(fz.DOWN);
			} while (bqd.d_(a6).g() && a6.v() > 0);
		}

		if (app.a(aoq) == app.c.ON_GROUND) {
			fu fu7 = a6.c();
			if (bqd.d_(fu7).a(bqd, fu7, czg.LAND)) {
				return fu7;
			}
		}

		return a6.h();
	}

	@FunctionalInterface
	public interface a {
		void run(aoz aoz, cgy cgy);
	}

	@FunctionalInterface
	public interface b {
		void query(long long1, Consumer<chj> consumer);
	}

	@FunctionalInterface
	public interface c {
		boolean test(aoq<?> aoq, fu fu, cgy cgy);
	}

	public static class d {
		private final int a;
		private final Object2IntOpenHashMap<apa> b;
		private final bqn c;
		private final Object2IntMap<apa> d;
		@Nullable
		private fu e;
		@Nullable
		private aoq<?> f;
		private double g;

		private d(int integer, Object2IntOpenHashMap<apa> object2IntOpenHashMap, bqn bqn) {
			this.a = integer;
			this.b = object2IntOpenHashMap;
			this.c = bqn;
			this.d = Object2IntMaps.unmodifiable(object2IntOpenHashMap);
		}

		private boolean a(aoq<?> aoq, fu fu, cgy cgy) {
			this.e = fu;
			this.f = aoq;
			bre bre5 = bqj.b(fu, cgy);
			bre.e e6 = bre5.a(aoq);
			if (e6 == null) {
				this.g = 0.0;
				return true;
			} else {
				double double7 = e6.b();
				this.g = double7;
				double double9 = this.c.b(fu, double7);
				return double9 <= e6.a();
			}
		}

		private void a(aoz aoz, cgy cgy) {
			aoq<?> aoq4 = aoz.U();
			fu fu7 = aoz.cA();
			double double5;
			if (fu7.equals(this.e) && aoq4 == this.f) {
				double5 = this.g;
			} else {
				bre bre8 = bqj.b(fu7, cgy);
				bre.e e9 = bre8.a(aoq4);
				if (e9 != null) {
					double5 = e9.b();
				} else {
					double5 = 0.0;
				}
			}

			this.c.a(fu7, double5);
			this.b.addTo(aoq4.e(), 1);
		}

		public Object2IntMap<apa> b() {
			return this.d;
		}

		private boolean a(apa apa) {
			int integer3 = apa.c() * this.a / bqj.b;
			return this.b.getInt(apa) < integer3;
		}
	}
}
