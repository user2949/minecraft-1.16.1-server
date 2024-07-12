public class caa extends bvj {
	public static final cgg<cgm> c = cfz.ad;
	public static final cga d = cfz.w;

	protected caa(cfi.c c) {
		super(true, c);
		this.j(this.n.b().a(caa.c, cgm.NORTH_SOUTH).a(d, Boolean.valueOf(false)));
	}

	protected boolean a(bqb bqb, fu fu, cfj cfj, boolean boolean4, int integer) {
		if (integer >= 8) {
			return false;
		} else {
			int integer7 = fu.u();
			int integer8 = fu.v();
			int integer9 = fu.w();
			boolean boolean10 = true;
			cgm cgm11 = cfj.c(c);
			switch (cgm11) {
				case NORTH_SOUTH:
					if (boolean4) {
						integer9++;
					} else {
						integer9--;
					}
					break;
				case EAST_WEST:
					if (boolean4) {
						integer7--;
					} else {
						integer7++;
					}
					break;
				case ASCENDING_EAST:
					if (boolean4) {
						integer7--;
					} else {
						integer7++;
						integer8++;
						boolean10 = false;
					}

					cgm11 = cgm.EAST_WEST;
					break;
				case ASCENDING_WEST:
					if (boolean4) {
						integer7--;
						integer8++;
						boolean10 = false;
					} else {
						integer7++;
					}

					cgm11 = cgm.EAST_WEST;
					break;
				case ASCENDING_NORTH:
					if (boolean4) {
						integer9++;
					} else {
						integer9--;
						integer8++;
						boolean10 = false;
					}

					cgm11 = cgm.NORTH_SOUTH;
					break;
				case ASCENDING_SOUTH:
					if (boolean4) {
						integer9++;
						integer8++;
						boolean10 = false;
					} else {
						integer9--;
					}

					cgm11 = cgm.NORTH_SOUTH;
			}

			return this.a(bqb, new fu(integer7, integer8, integer9), boolean4, integer, cgm11)
				? true
				: boolean10 && this.a(bqb, new fu(integer7, integer8 - 1, integer9), boolean4, integer, cgm11);
		}
	}

	protected boolean a(bqb bqb, fu fu, boolean boolean3, int integer, cgm cgm) {
		cfj cfj7 = bqb.d_(fu);
		if (!cfj7.a(this)) {
			return false;
		} else {
			cgm cgm8 = cfj7.c(c);
			if (cgm != cgm.EAST_WEST || cgm8 != cgm.NORTH_SOUTH && cgm8 != cgm.ASCENDING_NORTH && cgm8 != cgm.ASCENDING_SOUTH) {
				if (cgm != cgm.NORTH_SOUTH || cgm8 != cgm.EAST_WEST && cgm8 != cgm.ASCENDING_EAST && cgm8 != cgm.ASCENDING_WEST) {
					if (!(Boolean)cfj7.c(d)) {
						return false;
					} else {
						return bqb.r(fu) ? true : this.a(bqb, fu, cfj7, boolean3, integer + 1);
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	@Override
	protected void a(cfj cfj, bqb bqb, fu fu, bvr bvr) {
		boolean boolean6 = (Boolean)cfj.c(d);
		boolean boolean7 = bqb.r(fu) || this.a(bqb, fu, cfj, true, 0) || this.a(bqb, fu, cfj, false, 0);
		if (boolean7 != boolean6) {
			bqb.a(fu, cfj.a(d, Boolean.valueOf(boolean7)), 3);
			bqb.b(fu.c(), this);
			if (((cgm)cfj.c(c)).c()) {
				bqb.b(fu.b(), this);
			}
		}
	}

	@Override
	public cgl<cgm> d() {
		return c;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		switch (cap) {
			case CLOCKWISE_180:
				switch ((cgm)cfj.c(c)) {
					case ASCENDING_EAST:
						return cfj.a(c, cgm.ASCENDING_WEST);
					case ASCENDING_WEST:
						return cfj.a(c, cgm.ASCENDING_EAST);
					case ASCENDING_NORTH:
						return cfj.a(c, cgm.ASCENDING_SOUTH);
					case ASCENDING_SOUTH:
						return cfj.a(c, cgm.ASCENDING_NORTH);
					case SOUTH_EAST:
						return cfj.a(c, cgm.NORTH_WEST);
					case SOUTH_WEST:
						return cfj.a(c, cgm.NORTH_EAST);
					case NORTH_WEST:
						return cfj.a(c, cgm.SOUTH_EAST);
					case NORTH_EAST:
						return cfj.a(c, cgm.SOUTH_WEST);
				}
			case COUNTERCLOCKWISE_90:
				switch ((cgm)cfj.c(c)) {
					case NORTH_SOUTH:
						return cfj.a(c, cgm.EAST_WEST);
					case EAST_WEST:
						return cfj.a(c, cgm.NORTH_SOUTH);
					case ASCENDING_EAST:
						return cfj.a(c, cgm.ASCENDING_NORTH);
					case ASCENDING_WEST:
						return cfj.a(c, cgm.ASCENDING_SOUTH);
					case ASCENDING_NORTH:
						return cfj.a(c, cgm.ASCENDING_WEST);
					case ASCENDING_SOUTH:
						return cfj.a(c, cgm.ASCENDING_EAST);
					case SOUTH_EAST:
						return cfj.a(c, cgm.NORTH_EAST);
					case SOUTH_WEST:
						return cfj.a(c, cgm.SOUTH_EAST);
					case NORTH_WEST:
						return cfj.a(c, cgm.SOUTH_WEST);
					case NORTH_EAST:
						return cfj.a(c, cgm.NORTH_WEST);
				}
			case CLOCKWISE_90:
				switch ((cgm)cfj.c(c)) {
					case NORTH_SOUTH:
						return cfj.a(c, cgm.EAST_WEST);
					case EAST_WEST:
						return cfj.a(c, cgm.NORTH_SOUTH);
					case ASCENDING_EAST:
						return cfj.a(c, cgm.ASCENDING_SOUTH);
					case ASCENDING_WEST:
						return cfj.a(c, cgm.ASCENDING_NORTH);
					case ASCENDING_NORTH:
						return cfj.a(c, cgm.ASCENDING_EAST);
					case ASCENDING_SOUTH:
						return cfj.a(c, cgm.ASCENDING_WEST);
					case SOUTH_EAST:
						return cfj.a(c, cgm.SOUTH_WEST);
					case SOUTH_WEST:
						return cfj.a(c, cgm.NORTH_WEST);
					case NORTH_WEST:
						return cfj.a(c, cgm.NORTH_EAST);
					case NORTH_EAST:
						return cfj.a(c, cgm.SOUTH_EAST);
				}
			default:
				return cfj;
		}
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		cgm cgm4 = cfj.c(c);
		switch (bzj) {
			case LEFT_RIGHT:
				switch (cgm4) {
					case ASCENDING_NORTH:
						return cfj.a(c, cgm.ASCENDING_SOUTH);
					case ASCENDING_SOUTH:
						return cfj.a(c, cgm.ASCENDING_NORTH);
					case SOUTH_EAST:
						return cfj.a(c, cgm.NORTH_EAST);
					case SOUTH_WEST:
						return cfj.a(c, cgm.NORTH_WEST);
					case NORTH_WEST:
						return cfj.a(c, cgm.SOUTH_WEST);
					case NORTH_EAST:
						return cfj.a(c, cgm.SOUTH_EAST);
					default:
						return super.a(cfj, bzj);
				}
			case FRONT_BACK:
				switch (cgm4) {
					case ASCENDING_EAST:
						return cfj.a(c, cgm.ASCENDING_WEST);
					case ASCENDING_WEST:
						return cfj.a(c, cgm.ASCENDING_EAST);
					case ASCENDING_NORTH:
					case ASCENDING_SOUTH:
					default:
						break;
					case SOUTH_EAST:
						return cfj.a(c, cgm.SOUTH_WEST);
					case SOUTH_WEST:
						return cfj.a(c, cgm.SOUTH_EAST);
					case NORTH_WEST:
						return cfj.a(c, cgm.NORTH_EAST);
					case NORTH_EAST:
						return cfj.a(c, cgm.NORTH_WEST);
				}
		}

		return super.a(cfj, bzj);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(c, d);
	}
}
