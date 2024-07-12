public class cad extends bvj {
	public static final cgg<cgm> c = cfz.ac;

	protected cad(cfi.c c) {
		super(false, c);
		this.j(this.n.b().a(cad.c, cgm.NORTH_SOUTH));
	}

	@Override
	protected void a(cfj cfj, bqb bqb, fu fu, bvr bvr) {
		if (bvr.n().i() && new cae(bqb, fu, cfj).b() == 3) {
			this.a(bqb, fu, cfj, false);
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
					case NORTH_SOUTH:
						return cfj.a(c, cgm.EAST_WEST);
					case EAST_WEST:
						return cfj.a(c, cgm.NORTH_SOUTH);
				}
			case CLOCKWISE_90:
				switch ((cgm)cfj.c(c)) {
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
					case NORTH_SOUTH:
						return cfj.a(c, cgm.EAST_WEST);
					case EAST_WEST:
						return cfj.a(c, cgm.NORTH_SOUTH);
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
		a.a(c);
	}
}
