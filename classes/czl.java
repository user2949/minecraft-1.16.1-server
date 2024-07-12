import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;

public class czl extends cze {
	protected float j;
	private final Long2ObjectMap<czb> k = new Long2ObjectOpenHashMap<>();
	private final Object2BooleanMap<deg> l = new Object2BooleanOpenHashMap<>();

	@Override
	public void a(bql bql, aoz aoz) {
		super.a(bql, aoz);
		this.j = aoz.a(czb.WATER);
	}

	@Override
	public void a() {
		this.b.a(czb.WATER, this.j);
		this.k.clear();
		this.l.clear();
		super.a();
	}

	@Override
	public czd b() {
		fu.a a3 = new fu.a();
		int integer2 = aec.c(this.b.cD());
		cfj cfj4 = this.a.d_(a3.c(this.b.cC(), (double)integer2, this.b.cG()));
		if (!this.b.a(cfj4.m().a())) {
			if (this.e() && this.b.aA()) {
				while (true) {
					if (cfj4.b() != bvs.A && cfj4.m() != cxb.c.a(false)) {
						integer2--;
						break;
					}

					cfj4 = this.a.d_(a3.c(this.b.cC(), (double)(++integer2), this.b.cG()));
				}
			} else if (this.b.aj()) {
				integer2 = aec.c(this.b.cD() + 0.5);
			} else {
				fu fu5 = this.b.cA();

				while ((this.a.d_(fu5).g() || this.a.d_(fu5).a(this.a, fu5, czg.LAND)) && fu5.v() > 0) {
					fu5 = fu5.c();
				}

				integer2 = fu5.b().v();
			}
		} else {
			while (this.b.a(cfj4.m().a())) {
				cfj4 = this.a.d_(a3.c(this.b.cC(), (double)(++integer2), this.b.cG()));
			}

			integer2--;
		}

		fu fu5 = this.b.cA();
		czb czb6 = this.a(this.b, fu5.u(), integer2, fu5.w());
		if (this.b.a(czb6) < 0.0F) {
			deg deg7 = this.b.cb();
			if (this.b(a3.c(deg7.a, (double)integer2, deg7.c))
				|| this.b(a3.c(deg7.a, (double)integer2, deg7.f))
				|| this.b(a3.c(deg7.d, (double)integer2, deg7.c))
				|| this.b(a3.c(deg7.d, (double)integer2, deg7.f))) {
				czd czd8 = this.a(a3);
				czd8.l = this.a(this.b, czd8.a());
				czd8.k = this.b.a(czd8.l);
				return czd8;
			}
		}

		czd czd7 = this.a(fu5.u(), integer2, fu5.w());
		czd7.l = this.a(this.b, czd7.a());
		czd7.k = this.b.a(czd7.l);
		return czd7;
	}

	private boolean b(fu fu) {
		czb czb3 = this.a(this.b, fu);
		return this.b.a(czb3) >= 0.0F;
	}

	@Override
	public czj a(double double1, double double2, double double3) {
		return new czj(this.a(aec.c(double1), aec.c(double2), aec.c(double3)));
	}

	@Override
	public int a(czd[] arr, czd czd) {
		int integer4 = 0;
		int integer5 = 0;
		czb czb6 = this.a(this.b, czd.a, czd.b + 1, czd.c);
		czb czb7 = this.a(this.b, czd.a, czd.b, czd.c);
		if (this.b.a(czb6) >= 0.0F && czb7 != czb.STICKY_HONEY) {
			integer5 = aec.d(Math.max(1.0F, this.b.G));
		}

		double double8 = a(this.a, new fu(czd.a, czd.b, czd.c));
		czd czd10 = this.a(czd.a, czd.b, czd.c + 1, integer5, double8, fz.SOUTH, czb7);
		if (this.a(czd10, czd)) {
			arr[integer4++] = czd10;
		}

		czd czd11 = this.a(czd.a - 1, czd.b, czd.c, integer5, double8, fz.WEST, czb7);
		if (this.a(czd11, czd)) {
			arr[integer4++] = czd11;
		}

		czd czd12 = this.a(czd.a + 1, czd.b, czd.c, integer5, double8, fz.EAST, czb7);
		if (this.a(czd12, czd)) {
			arr[integer4++] = czd12;
		}

		czd czd13 = this.a(czd.a, czd.b, czd.c - 1, integer5, double8, fz.NORTH, czb7);
		if (this.a(czd13, czd)) {
			arr[integer4++] = czd13;
		}

		czd czd14 = this.a(czd.a - 1, czd.b, czd.c - 1, integer5, double8, fz.NORTH, czb7);
		if (this.a(czd, czd11, czd13, czd14)) {
			arr[integer4++] = czd14;
		}

		czd czd15 = this.a(czd.a + 1, czd.b, czd.c - 1, integer5, double8, fz.NORTH, czb7);
		if (this.a(czd, czd12, czd13, czd15)) {
			arr[integer4++] = czd15;
		}

		czd czd16 = this.a(czd.a - 1, czd.b, czd.c + 1, integer5, double8, fz.SOUTH, czb7);
		if (this.a(czd, czd11, czd10, czd16)) {
			arr[integer4++] = czd16;
		}

		czd czd17 = this.a(czd.a + 1, czd.b, czd.c + 1, integer5, double8, fz.SOUTH, czb7);
		if (this.a(czd, czd12, czd10, czd17)) {
			arr[integer4++] = czd17;
		}

		return integer4;
	}

	private boolean a(czd czd1, czd czd2) {
		return czd1 != null && !czd1.i && (czd1.k >= 0.0F || czd2.k < 0.0F);
	}

	private boolean a(czd czd1, @Nullable czd czd2, @Nullable czd czd3, @Nullable czd czd4) {
		if (czd4 == null || czd3 == null || czd2 == null) {
			return false;
		} else if (czd4.i) {
			return false;
		} else if (czd3.b <= czd1.b && czd2.b <= czd1.b) {
			boolean boolean6 = czd3.l == czb.FENCE && czd2.l == czb.FENCE && (double)this.b.cx() < 0.5;
			return czd4.k >= 0.0F && (czd3.b < czd1.b || czd3.k >= 0.0F || boolean6) && (czd2.b < czd1.b || czd2.k >= 0.0F || boolean6);
		} else {
			return false;
		}
	}

	private boolean a(czd czd) {
		dem dem3 = new dem((double)czd.a - this.b.cC(), (double)czd.b - this.b.cD(), (double)czd.c - this.b.cG());
		deg deg4 = this.b.cb();
		int integer5 = aec.f(dem3.f() / deg4.a());
		dem3 = dem3.a((double)(1.0F / (float)integer5));

		for (int integer6 = 1; integer6 <= integer5; integer6++) {
			deg4 = deg4.c(dem3);
			if (this.a(deg4)) {
				return false;
			}
		}

		return true;
	}

	public static double a(bpg bpg, fu fu) {
		fu fu3 = fu.c();
		dfg dfg4 = bpg.d_(fu3).k(bpg, fu3);
		return (double)fu3.v() + (dfg4.b() ? 0.0 : dfg4.c(fz.a.Y));
	}

	@Nullable
	private czd a(int integer1, int integer2, int integer3, int integer4, double double5, fz fz, czb czb) {
		czd czd10 = null;
		fu.a a11 = new fu.a();
		double double12 = a(this.a, (fu)a11.d(integer1, integer2, integer3));
		if (double12 - double5 > 1.125) {
			return null;
		} else {
			czb czb14 = this.a(this.b, integer1, integer2, integer3);
			float float15 = this.b.a(czb14);
			double double16 = (double)this.b.cx() / 2.0;
			if (float15 >= 0.0F) {
				czd10 = this.a(integer1, integer2, integer3);
				czd10.l = czb14;
				czd10.k = Math.max(czd10.k, float15);
			}

			if (czb == czb.FENCE && czd10 != null && czd10.k >= 0.0F && !this.a(czd10)) {
				czd10 = null;
			}

			if (czb14 == czb.WALKABLE) {
				return czd10;
			} else {
				if ((czd10 == null || czd10.k < 0.0F) && integer4 > 0 && czb14 != czb.FENCE && czb14 != czb.UNPASSABLE_RAIL && czb14 != czb.TRAPDOOR) {
					czd10 = this.a(integer1, integer2 + 1, integer3, integer4 - 1, double5, fz, czb);
					if (czd10 != null && (czd10.l == czb.OPEN || czd10.l == czb.WALKABLE) && this.b.cx() < 1.0F) {
						double double18 = (double)(integer1 - fz.i()) + 0.5;
						double double20 = (double)(integer3 - fz.k()) + 0.5;
						deg deg22 = new deg(
							double18 - double16,
							a(this.a, (fu)a11.c(double18, (double)(integer2 + 1), double20)) + 0.001,
							double20 - double16,
							double18 + double16,
							(double)this.b.cy() + a(this.a, (fu)a11.c((double)czd10.a, (double)czd10.b, (double)czd10.c)) - 0.002,
							double20 + double16
						);
						if (this.a(deg22)) {
							czd10 = null;
						}
					}
				}

				if (czb14 == czb.WATER && !this.e()) {
					if (this.a(this.b, integer1, integer2 - 1, integer3) != czb.WATER) {
						return czd10;
					}

					while (integer2 > 0) {
						czb14 = this.a(this.b, integer1, --integer2, integer3);
						if (czb14 != czb.WATER) {
							return czd10;
						}

						czd10 = this.a(integer1, integer2, integer3);
						czd10.l = czb14;
						czd10.k = Math.max(czd10.k, this.b.a(czb14));
					}
				}

				if (czb14 == czb.OPEN) {
					deg deg18 = new deg(
						(double)integer1 - double16 + 0.5,
						(double)integer2 + 0.001,
						(double)integer3 - double16 + 0.5,
						(double)integer1 + double16 + 0.5,
						(double)((float)integer2 + this.b.cy()),
						(double)integer3 + double16 + 0.5
					);
					if (this.a(deg18)) {
						return null;
					}

					if (this.b.cx() >= 1.0F) {
						czb czb19 = this.a(this.b, integer1, integer2 - 1, integer3);
						if (czb19 == czb.BLOCKED) {
							czd10 = this.a(integer1, integer2, integer3);
							czd10.l = czb.WALKABLE;
							czd10.k = Math.max(czd10.k, float15);
							return czd10;
						}
					}

					int integer19 = 0;
					int integer20 = integer2;

					while (czb14 == czb.OPEN) {
						if (--integer2 < 0) {
							czd czd21 = this.a(integer1, integer20, integer3);
							czd21.l = czb.BLOCKED;
							czd21.k = -1.0F;
							return czd21;
						}

						czd czd21 = this.a(integer1, integer2, integer3);
						if (integer19++ >= this.b.bL()) {
							czd21.l = czb.BLOCKED;
							czd21.k = -1.0F;
							return czd21;
						}

						czb14 = this.a(this.b, integer1, integer2, integer3);
						float15 = this.b.a(czb14);
						if (czb14 != czb.OPEN && float15 >= 0.0F) {
							czd10 = czd21;
							czd21.l = czb14;
							czd21.k = Math.max(czd21.k, float15);
							break;
						}

						if (float15 < 0.0F) {
							czd21.l = czb.BLOCKED;
							czd21.k = -1.0F;
							return czd21;
						}
					}
				}

				if (czb14 == czb.FENCE) {
					czd10 = this.a(integer1, integer2, integer3);
					czd10.i = true;
					czd10.l = czb14;
					czd10.k = czb14.a();
				}

				return czd10;
			}
		}
	}

	private boolean a(deg deg) {
		return (Boolean)this.l.computeIfAbsent(deg, deg2 -> !this.a.a_(this.b, deg));
	}

	@Override
	public czb a(bpg bpg, int integer2, int integer3, int integer4, aoz aoz, int integer6, int integer7, int integer8, boolean boolean9, boolean boolean10) {
		EnumSet<czb> enumSet12 = EnumSet.noneOf(czb.class);
		czb czb13 = czb.BLOCKED;
		fu fu14 = aoz.cA();
		czb13 = this.a(bpg, integer2, integer3, integer4, integer6, integer7, integer8, boolean9, boolean10, enumSet12, czb13, fu14);
		if (enumSet12.contains(czb.FENCE)) {
			return czb.FENCE;
		} else if (enumSet12.contains(czb.UNPASSABLE_RAIL)) {
			return czb.UNPASSABLE_RAIL;
		} else {
			czb czb15 = czb.BLOCKED;

			for (czb czb17 : enumSet12) {
				if (aoz.a(czb17) < 0.0F) {
					return czb17;
				}

				if (aoz.a(czb17) >= aoz.a(czb15)) {
					czb15 = czb17;
				}
			}

			return czb13 == czb.OPEN && aoz.a(czb15) == 0.0F ? czb.OPEN : czb15;
		}
	}

	public czb a(
		bpg bpg,
		int integer2,
		int integer3,
		int integer4,
		int integer5,
		int integer6,
		int integer7,
		boolean boolean8,
		boolean boolean9,
		EnumSet<czb> enumSet,
		czb czb,
		fu fu
	) {
		for (int integer14 = 0; integer14 < integer5; integer14++) {
			for (int integer15 = 0; integer15 < integer6; integer15++) {
				for (int integer16 = 0; integer16 < integer7; integer16++) {
					int integer17 = integer14 + integer2;
					int integer18 = integer15 + integer3;
					int integer19 = integer16 + integer4;
					czb czb20 = this.a(bpg, integer17, integer18, integer19);
					czb20 = this.a(bpg, boolean8, boolean9, fu, czb20);
					if (integer14 == 0 && integer15 == 0 && integer16 == 0) {
						czb = czb20;
					}

					enumSet.add(czb20);
				}
			}
		}

		return czb;
	}

	protected czb a(bpg bpg, boolean boolean2, boolean boolean3, fu fu, czb czb) {
		if (czb == czb.DOOR_WOOD_CLOSED && boolean2 && boolean3) {
			czb = czb.WALKABLE;
		}

		if (czb == czb.DOOR_OPEN && !boolean3) {
			czb = czb.BLOCKED;
		}

		if (czb == czb.RAIL && !(bpg.d_(fu).b() instanceof bvj) && !(bpg.d_(fu.c()).b() instanceof bvj)) {
			czb = czb.UNPASSABLE_RAIL;
		}

		if (czb == czb.LEAVES) {
			czb = czb.BLOCKED;
		}

		return czb;
	}

	private czb a(aoz aoz, fu fu) {
		return this.a(aoz, fu.u(), fu.v(), fu.w());
	}

	private czb a(aoz aoz, int integer2, int integer3, int integer4) {
		return this.k
			.computeIfAbsent(fu.a(integer2, integer3, integer4), long5 -> this.a(this.a, integer2, integer3, integer4, aoz, this.d, this.e, this.f, this.d(), this.c()));
	}

	@Override
	public czb a(bpg bpg, int integer2, int integer3, int integer4) {
		return a(bpg, new fu.a(integer2, integer3, integer4));
	}

	public static czb a(bpg bpg, fu.a a) {
		int integer3 = a.u();
		int integer4 = a.v();
		int integer5 = a.w();
		czb czb6 = b(bpg, a);
		if (czb6 == czb.OPEN && integer4 >= 1) {
			czb czb7 = b(bpg, a.d(integer3, integer4 - 1, integer5));
			czb6 = czb7 != czb.WALKABLE && czb7 != czb.OPEN && czb7 != czb.WATER && czb7 != czb.LAVA ? czb.WALKABLE : czb.OPEN;
			if (czb7 == czb.DAMAGE_FIRE) {
				czb6 = czb.DAMAGE_FIRE;
			}

			if (czb7 == czb.DAMAGE_CACTUS) {
				czb6 = czb.DAMAGE_CACTUS;
			}

			if (czb7 == czb.DAMAGE_OTHER) {
				czb6 = czb.DAMAGE_OTHER;
			}

			if (czb7 == czb.STICKY_HONEY) {
				czb6 = czb.STICKY_HONEY;
			}
		}

		if (czb6 == czb.WALKABLE) {
			czb6 = a(bpg, a.d(integer3, integer4, integer5), czb6);
		}

		return czb6;
	}

	public static czb a(bpg bpg, fu.a a, czb czb) {
		int integer4 = a.u();
		int integer5 = a.v();
		int integer6 = a.w();

		for (int integer7 = -1; integer7 <= 1; integer7++) {
			for (int integer8 = -1; integer8 <= 1; integer8++) {
				for (int integer9 = -1; integer9 <= 1; integer9++) {
					if (integer7 != 0 || integer9 != 0) {
						a.d(integer4 + integer7, integer5 + integer8, integer6 + integer9);
						cfj cfj10 = bpg.d_(a);
						if (cfj10.a(bvs.cF)) {
							return czb.DANGER_CACTUS;
						}

						if (cfj10.a(bvs.mg)) {
							return czb.DANGER_OTHER;
						}

						if (a(cfj10)) {
							return czb.DANGER_FIRE;
						}

						cxa cxa11 = bpg.b(a);
						if (cxa11.a(acz.a)) {
							return czb.WATER_BORDER;
						}

						if (cxa11.a(acz.b)) {
							return czb.LAVA;
						}
					}
				}
			}
		}

		return czb;
	}

	protected static czb b(bpg bpg, fu fu) {
		cfj cfj3 = bpg.d_(fu);
		bvr bvr4 = cfj3.b();
		cxd cxd5 = cfj3.c();
		if (cfj3.g()) {
			return czb.OPEN;
		} else if (cfj3.a(acx.I) || cfj3.a(bvs.dU)) {
			return czb.TRAPDOOR;
		} else if (cfj3.a(bvs.cF)) {
			return czb.DAMAGE_CACTUS;
		} else if (cfj3.a(bvs.mg)) {
			return czb.DAMAGE_OTHER;
		} else if (cfj3.a(bvs.ne)) {
			return czb.STICKY_HONEY;
		} else if (cfj3.a(bvs.eh)) {
			return czb.COCOA;
		} else if (a(cfj3)) {
			return czb.DAMAGE_FIRE;
		} else if (bxe.h(cfj3) && !(Boolean)cfj3.c(bxe.b)) {
			return czb.DOOR_WOOD_CLOSED;
		} else if (bvr4 instanceof bxe && cxd5 == cxd.I && !(Boolean)cfj3.c(bxe.b)) {
			return czb.DOOR_IRON_CLOSED;
		} else if (bvr4 instanceof bxe && (Boolean)cfj3.c(bxe.b)) {
			return czb.DOOR_OPEN;
		} else if (bvr4 instanceof bvj) {
			return czb.RAIL;
		} else if (bvr4 instanceof bza) {
			return czb.LEAVES;
		} else if (!bvr4.a(acx.L) && !bvr4.a(acx.E) && (!(bvr4 instanceof bxu) || (Boolean)cfj3.c(bxu.a))) {
			if (!cfj3.a(bpg, fu, czg.LAND)) {
				return czb.BLOCKED;
			} else {
				cxa cxa6 = bpg.b(fu);
				if (cxa6.a(acz.a)) {
					return czb.WATER;
				} else {
					return cxa6.a(acz.b) ? czb.LAVA : czb.OPEN;
				}
			}
		} else {
			return czb.FENCE;
		}
	}

	private static boolean a(cfj cfj) {
		return cfj.a(acx.am) || cfj.a(bvs.iJ) || bwb.g(cfj);
	}
}
