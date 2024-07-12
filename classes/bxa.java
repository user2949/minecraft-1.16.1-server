import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bxa extends bvj {
	public static final cgg<cgm> c = cfz.ad;
	public static final cga d = cfz.w;

	public bxa(cfi.c c) {
		super(true, c);
		this.j(this.n.b().a(d, Boolean.valueOf(false)).a(bxa.c, cgm.NORTH_SOUTH));
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (!bqb.v) {
			if (!(Boolean)cfj.c(d)) {
				this.a(bqb, fu, cfj);
			}
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if ((Boolean)cfj.c(d)) {
			this.a(zd, fu, cfj);
		}
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(d) ? 15 : 0;
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		if (!(Boolean)cfj.c(d)) {
			return 0;
		} else {
			return fz == fz.UP ? 15 : 0;
		}
	}

	private void a(bqb bqb, fu fu, cfj cfj) {
		boolean boolean5 = (Boolean)cfj.c(d);
		boolean boolean6 = false;
		List<bfr> list7 = this.a(bqb, fu, bfr.class, null);
		if (!list7.isEmpty()) {
			boolean6 = true;
		}

		if (boolean6 && !boolean5) {
			cfj cfj8 = cfj.a(d, Boolean.valueOf(true));
			bqb.a(fu, cfj8, 3);
			this.b(bqb, fu, cfj8, true);
			bqb.b(fu, this);
			bqb.b(fu.c(), this);
			bqb.b(fu, cfj, cfj8);
		}

		if (!boolean6 && boolean5) {
			cfj cfj8 = cfj.a(d, Boolean.valueOf(false));
			bqb.a(fu, cfj8, 3);
			this.b(bqb, fu, cfj8, false);
			bqb.b(fu, this);
			bqb.b(fu.c(), this);
			bqb.b(fu, cfj, cfj8);
		}

		if (boolean6) {
			bqb.G().a(fu, this, 20);
		}

		bqb.c(fu, this);
	}

	protected void b(bqb bqb, fu fu, cfj cfj, boolean boolean4) {
		cae cae6 = new cae(bqb, fu, cfj);

		for (fu fu9 : cae6.a()) {
			cfj cfj10 = bqb.d_(fu9);
			cfj10.a(bqb, fu9, cfj10.b(), fu, false);
		}
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b())) {
			this.a(bqb, fu, this.a(cfj1, bqb, fu, boolean5));
		}
	}

	@Override
	public cgl<cgm> d() {
		return c;
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		if ((Boolean)cfj.c(d)) {
			List<bfx> list5 = this.a(bqb, fu, bfx.class, null);
			if (!list5.isEmpty()) {
				return ((bfx)list5.get(0)).u().i();
			}

			List<bfr> list6 = this.a(bqb, fu, bfr.class, aop.d);
			if (!list6.isEmpty()) {
				return bgi.b((amz)list6.get(0));
			}
		}

		return 0;
	}

	protected <T extends bfr> List<T> a(bqb bqb, fu fu, Class<T> class3, @Nullable Predicate<aom> predicate) {
		return bqb.a(class3, this.a(fu), predicate);
	}

	private deg a(fu fu) {
		double double3 = 0.2;
		return new deg((double)fu.u() + 0.2, (double)fu.v(), (double)fu.w() + 0.2, (double)(fu.u() + 1) - 0.2, (double)(fu.v() + 1) - 0.2, (double)(fu.w() + 1) - 0.2);
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
		a.a(c, d);
	}
}
