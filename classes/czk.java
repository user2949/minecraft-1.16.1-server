import javax.annotation.Nullable;

public class czk extends czl {
	private float k;
	private float l;

	@Override
	public void a(bql bql, aoz aoz) {
		super.a(bql, aoz);
		aoz.a(czb.WATER, 0.0F);
		this.k = aoz.a(czb.WALKABLE);
		aoz.a(czb.WALKABLE, 6.0F);
		this.l = aoz.a(czb.WATER_BORDER);
		aoz.a(czb.WATER_BORDER, 4.0F);
	}

	@Override
	public void a() {
		this.b.a(czb.WALKABLE, this.k);
		this.b.a(czb.WATER_BORDER, this.l);
		super.a();
	}

	@Override
	public czd b() {
		return this.a(aec.c(this.b.cb().a), aec.c(this.b.cb().b + 0.5), aec.c(this.b.cb().c));
	}

	@Override
	public czj a(double double1, double double2, double double3) {
		return new czj(this.a(aec.c(double1), aec.c(double2 + 0.5), aec.c(double3)));
	}

	@Override
	public int a(czd[] arr, czd czd) {
		int integer4 = 0;
		int integer5 = 1;
		fu fu6 = new fu(czd.a, czd.b, czd.c);
		double double7 = this.b(fu6);
		czd czd9 = this.a(czd.a, czd.b, czd.c + 1, 1, double7);
		czd czd10 = this.a(czd.a - 1, czd.b, czd.c, 1, double7);
		czd czd11 = this.a(czd.a + 1, czd.b, czd.c, 1, double7);
		czd czd12 = this.a(czd.a, czd.b, czd.c - 1, 1, double7);
		czd czd13 = this.a(czd.a, czd.b + 1, czd.c, 0, double7);
		czd czd14 = this.a(czd.a, czd.b - 1, czd.c, 1, double7);
		if (czd9 != null && !czd9.i) {
			arr[integer4++] = czd9;
		}

		if (czd10 != null && !czd10.i) {
			arr[integer4++] = czd10;
		}

		if (czd11 != null && !czd11.i) {
			arr[integer4++] = czd11;
		}

		if (czd12 != null && !czd12.i) {
			arr[integer4++] = czd12;
		}

		if (czd13 != null && !czd13.i) {
			arr[integer4++] = czd13;
		}

		if (czd14 != null && !czd14.i) {
			arr[integer4++] = czd14;
		}

		boolean boolean15 = czd12 == null || czd12.l == czb.OPEN || czd12.k != 0.0F;
		boolean boolean16 = czd9 == null || czd9.l == czb.OPEN || czd9.k != 0.0F;
		boolean boolean17 = czd11 == null || czd11.l == czb.OPEN || czd11.k != 0.0F;
		boolean boolean18 = czd10 == null || czd10.l == czb.OPEN || czd10.k != 0.0F;
		if (boolean15 && boolean18) {
			czd czd19 = this.a(czd.a - 1, czd.b, czd.c - 1, 1, double7);
			if (czd19 != null && !czd19.i) {
				arr[integer4++] = czd19;
			}
		}

		if (boolean15 && boolean17) {
			czd czd19 = this.a(czd.a + 1, czd.b, czd.c - 1, 1, double7);
			if (czd19 != null && !czd19.i) {
				arr[integer4++] = czd19;
			}
		}

		if (boolean16 && boolean18) {
			czd czd19 = this.a(czd.a - 1, czd.b, czd.c + 1, 1, double7);
			if (czd19 != null && !czd19.i) {
				arr[integer4++] = czd19;
			}
		}

		if (boolean16 && boolean17) {
			czd czd19 = this.a(czd.a + 1, czd.b, czd.c + 1, 1, double7);
			if (czd19 != null && !czd19.i) {
				arr[integer4++] = czd19;
			}
		}

		return integer4;
	}

	private double b(fu fu) {
		if (!this.b.aA()) {
			fu fu3 = fu.c();
			dfg dfg4 = this.a.d_(fu3).k(this.a, fu3);
			return (double)fu3.v() + (dfg4.b() ? 0.0 : dfg4.c(fz.a.Y));
		} else {
			return (double)fu.v() + 0.5;
		}
	}

	@Nullable
	private czd a(int integer1, int integer2, int integer3, int integer4, double double5) {
		czd czd8 = null;
		fu fu9 = new fu(integer1, integer2, integer3);
		double double10 = this.b(fu9);
		if (double10 - double5 > 1.125) {
			return null;
		} else {
			czb czb12 = this.a(this.a, integer1, integer2, integer3, this.b, this.d, this.e, this.f, false, false);
			float float13 = this.b.a(czb12);
			double double14 = (double)this.b.cx() / 2.0;
			if (float13 >= 0.0F) {
				czd8 = this.a(integer1, integer2, integer3);
				czd8.l = czb12;
				czd8.k = Math.max(czd8.k, float13);
			}

			if (czb12 != czb.WATER && czb12 != czb.WALKABLE) {
				if (czd8 == null && integer4 > 0 && czb12 != czb.FENCE && czb12 != czb.UNPASSABLE_RAIL && czb12 != czb.TRAPDOOR) {
					czd8 = this.a(integer1, integer2 + 1, integer3, integer4 - 1, double5);
				}

				if (czb12 == czb.OPEN) {
					deg deg16 = new deg(
						(double)integer1 - double14 + 0.5,
						(double)integer2 + 0.001,
						(double)integer3 - double14 + 0.5,
						(double)integer1 + double14 + 0.5,
						(double)((float)integer2 + this.b.cy()),
						(double)integer3 + double14 + 0.5
					);
					if (!this.b.l.a_(this.b, deg16)) {
						return null;
					}

					czb czb17 = this.a(this.a, integer1, integer2 - 1, integer3, this.b, this.d, this.e, this.f, false, false);
					if (czb17 == czb.BLOCKED) {
						czd8 = this.a(integer1, integer2, integer3);
						czd8.l = czb.WALKABLE;
						czd8.k = Math.max(czd8.k, float13);
						return czd8;
					}

					if (czb17 == czb.WATER) {
						czd8 = this.a(integer1, integer2, integer3);
						czd8.l = czb.WATER;
						czd8.k = Math.max(czd8.k, float13);
						return czd8;
					}

					int integer18 = 0;

					while (integer2 > 0 && czb12 == czb.OPEN) {
						integer2--;
						if (integer18++ >= this.b.bL()) {
							return null;
						}

						czb12 = this.a(this.a, integer1, integer2, integer3, this.b, this.d, this.e, this.f, false, false);
						float13 = this.b.a(czb12);
						if (czb12 != czb.OPEN && float13 >= 0.0F) {
							czd8 = this.a(integer1, integer2, integer3);
							czd8.l = czb12;
							czd8.k = Math.max(czd8.k, float13);
							break;
						}

						if (float13 < 0.0F) {
							return null;
						}
					}
				}

				return czd8;
			} else {
				if (integer2 < this.b.l.t_() - 10 && czd8 != null) {
					czd8.k++;
				}

				return czd8;
			}
		}
	}

	@Override
	protected czb a(bpg bpg, boolean boolean2, boolean boolean3, fu fu, czb czb) {
		if (czb == czb.RAIL && !(bpg.d_(fu).b() instanceof bvj) && !(bpg.d_(fu.c()).b() instanceof bvj)) {
			czb = czb.UNPASSABLE_RAIL;
		}

		if (czb == czb.DOOR_OPEN || czb == czb.DOOR_WOOD_CLOSED || czb == czb.DOOR_IRON_CLOSED) {
			czb = czb.BLOCKED;
		}

		if (czb == czb.LEAVES) {
			czb = czb.BLOCKED;
		}

		return czb;
	}

	@Override
	public czb a(bpg bpg, int integer2, int integer3, int integer4) {
		fu.a a6 = new fu.a();
		czb czb7 = b(bpg, a6.d(integer2, integer3, integer4));
		if (czb7 == czb.WATER) {
			for (fz fz11 : fz.values()) {
				czb czb12 = b(bpg, a6.d(integer2, integer3, integer4).c(fz11));
				if (czb12 == czb.BLOCKED) {
					return czb.WATER_BORDER;
				}
			}

			return czb.WATER;
		} else {
			if (czb7 == czb.OPEN && integer3 >= 1) {
				cfj cfj8 = bpg.d_(new fu(integer2, integer3 - 1, integer4));
				czb czb9 = b(bpg, a6.d(integer2, integer3 - 1, integer4));
				if (czb9 != czb.WALKABLE && czb9 != czb.OPEN && czb9 != czb.LAVA) {
					czb7 = czb.WALKABLE;
				} else {
					czb7 = czb.OPEN;
				}

				if (czb9 == czb.DAMAGE_FIRE || cfj8.a(bvs.iJ) || cfj8.a(acx.ax)) {
					czb7 = czb.DAMAGE_FIRE;
				}

				if (czb9 == czb.DAMAGE_CACTUS) {
					czb7 = czb.DAMAGE_CACTUS;
				}

				if (czb9 == czb.DAMAGE_OTHER) {
					czb7 = czb.DAMAGE_OTHER;
				}
			}

			if (czb7 == czb.WALKABLE) {
				czb7 = a(bpg, a6.d(integer2, integer3, integer4), czb7);
			}

			return czb7;
		}
	}
}
