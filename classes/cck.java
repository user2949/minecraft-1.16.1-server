import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class cck extends bvr {
	public static final cga a = bzv.e;
	public static final cga b = bzv.a;
	public static final cga c = bzv.b;
	public static final cga d = bzv.c;
	public static final cga e = bzv.d;
	public static final Map<fz, cga> f = (Map<fz, cga>)bzv.g.entrySet().stream().filter(entry -> entry.getKey() != fz.DOWN).collect(v.a());
	protected static final dfg g = bvr.a(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg h = bvr.a(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
	protected static final dfg i = bvr.a(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg j = bvr.a(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
	protected static final dfg k = bvr.a(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

	public cck(cfi.c c) {
		super(c);
		this.j(
			this.n
				.b()
				.a(a, Boolean.valueOf(false))
				.a(b, Boolean.valueOf(false))
				.a(cck.c, Boolean.valueOf(false))
				.a(d, Boolean.valueOf(false))
				.a(e, Boolean.valueOf(false))
		);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		dfg dfg6 = dfd.a();
		if ((Boolean)cfj.c(a)) {
			dfg6 = dfd.a(dfg6, g);
		}

		if ((Boolean)cfj.c(b)) {
			dfg6 = dfd.a(dfg6, j);
		}

		if ((Boolean)cfj.c(c)) {
			dfg6 = dfd.a(dfg6, i);
		}

		if ((Boolean)cfj.c(d)) {
			dfg6 = dfd.a(dfg6, k);
		}

		if ((Boolean)cfj.c(e)) {
			dfg6 = dfd.a(dfg6, h);
		}

		return dfg6;
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return this.h(this.g(cfj, bqd, fu));
	}

	private boolean h(cfj cfj) {
		return this.l(cfj) > 0;
	}

	private int l(cfj cfj) {
		int integer3 = 0;

		for (cga cga5 : f.values()) {
			if ((Boolean)cfj.c(cga5)) {
				integer3++;
			}
		}

		return integer3;
	}

	private boolean b(bpg bpg, fu fu, fz fz) {
		if (fz == fz.DOWN) {
			return false;
		} else {
			fu fu5 = fu.a(fz);
			if (a(bpg, fu5, fz)) {
				return true;
			} else if (fz.n() == fz.a.Y) {
				return false;
			} else {
				cga cga6 = (cga)f.get(fz);
				cfj cfj7 = bpg.d_(fu.b());
				return cfj7.a(this) && (Boolean)cfj7.c(cga6);
			}
		}
	}

	public static boolean a(bpg bpg, fu fu, fz fz) {
		cfj cfj4 = bpg.d_(fu);
		return bvr.a(cfj4.k(bpg, fu), fz.f());
	}

	private cfj g(cfj cfj, bpg bpg, fu fu) {
		fu fu5 = fu.b();
		if ((Boolean)cfj.c(a)) {
			cfj = cfj.a(a, Boolean.valueOf(a(bpg, fu5, fz.DOWN)));
		}

		cfj cfj6 = null;

		for (fz fz8 : fz.c.HORIZONTAL) {
			cga cga9 = a(fz8);
			if ((Boolean)cfj.c(cga9)) {
				boolean boolean10 = this.b(bpg, fu, fz8);
				if (!boolean10) {
					if (cfj6 == null) {
						cfj6 = bpg.d_(fu5);
					}

					boolean10 = cfj6.a(this) && (Boolean)cfj6.c(cga9);
				}

				cfj = cfj.a(cga9, Boolean.valueOf(boolean10));
			}
		}

		return cfj;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == fz.DOWN) {
			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		} else {
			cfj cfj8 = this.g(cfj1, bqc, fu5);
			return !this.h(cfj8) ? bvs.a.n() : cfj8;
		}
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (zd.t.nextInt(4) == 0) {
			fz fz6 = fz.a(random);
			fu fu7 = fu.b();
			if (fz6.n().d() && !(Boolean)cfj.c(a(fz6))) {
				if (this.a(zd, fu)) {
					fu fu8 = fu.a(fz6);
					cfj cfj9 = zd.d_(fu8);
					if (cfj9.g()) {
						fz fz10 = fz6.g();
						fz fz11 = fz6.h();
						boolean boolean12 = (Boolean)cfj.c(a(fz10));
						boolean boolean13 = (Boolean)cfj.c(a(fz11));
						fu fu14 = fu8.a(fz10);
						fu fu15 = fu8.a(fz11);
						if (boolean12 && a(zd, fu14, fz10)) {
							zd.a(fu8, this.n().a(a(fz10), Boolean.valueOf(true)), 2);
						} else if (boolean13 && a(zd, fu15, fz11)) {
							zd.a(fu8, this.n().a(a(fz11), Boolean.valueOf(true)), 2);
						} else {
							fz fz16 = fz6.f();
							if (boolean12 && zd.w(fu14) && a(zd, fu.a(fz10), fz16)) {
								zd.a(fu14, this.n().a(a(fz16), Boolean.valueOf(true)), 2);
							} else if (boolean13 && zd.w(fu15) && a(zd, fu.a(fz11), fz16)) {
								zd.a(fu15, this.n().a(a(fz16), Boolean.valueOf(true)), 2);
							} else if ((double)zd.t.nextFloat() < 0.05 && a(zd, fu8.b(), fz.UP)) {
								zd.a(fu8, this.n().a(a, Boolean.valueOf(true)), 2);
							}
						}
					} else if (a(zd, fu8, fz6)) {
						zd.a(fu, cfj.a(a(fz6), Boolean.valueOf(true)), 2);
					}
				}
			} else {
				if (fz6 == fz.UP && fu.v() < 255) {
					if (this.b(zd, fu, fz6)) {
						zd.a(fu, cfj.a(a, Boolean.valueOf(true)), 2);
						return;
					}

					if (zd.w(fu7)) {
						if (!this.a(zd, fu)) {
							return;
						}

						cfj cfj8 = cfj;

						for (fz fz10 : fz.c.HORIZONTAL) {
							if (random.nextBoolean() || !a(zd, fu7.a(fz10), fz.UP)) {
								cfj8 = cfj8.a(a(fz10), Boolean.valueOf(false));
							}
						}

						if (this.m(cfj8)) {
							zd.a(fu7, cfj8, 2);
						}

						return;
					}
				}

				if (fu.v() > 0) {
					fu fu8 = fu.c();
					cfj cfj9 = zd.d_(fu8);
					if (cfj9.g() || cfj9.a(this)) {
						cfj cfj10 = cfj9.g() ? this.n() : cfj9;
						cfj cfj11 = this.a(cfj, cfj10, random);
						if (cfj10 != cfj11 && this.m(cfj11)) {
							zd.a(fu8, cfj11, 2);
						}
					}
				}
			}
		}
	}

	private cfj a(cfj cfj1, cfj cfj2, Random random) {
		for (fz fz6 : fz.c.HORIZONTAL) {
			if (random.nextBoolean()) {
				cga cga7 = a(fz6);
				if ((Boolean)cfj1.c(cga7)) {
					cfj2 = cfj2.a(cga7, Boolean.valueOf(true));
				}
			}
		}

		return cfj2;
	}

	private boolean m(cfj cfj) {
		return (Boolean)cfj.c(b) || (Boolean)cfj.c(c) || (Boolean)cfj.c(d) || (Boolean)cfj.c(e);
	}

	private boolean a(bpg bpg, fu fu) {
		int integer4 = 4;
		Iterable<fu> iterable5 = fu.b(fu.u() - 4, fu.v() - 1, fu.w() - 4, fu.u() + 4, fu.v() + 1, fu.w() + 4);
		int integer6 = 5;

		for (fu fu8 : iterable5) {
			if (bpg.d_(fu8).a(this)) {
				if (--integer6 <= 0) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean a(cfj cfj, bin bin) {
		cfj cfj4 = bin.o().d_(bin.a());
		return cfj4.a(this) ? this.l(cfj4) < f.size() : super.a(cfj, bin);
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cfj cfj3 = bin.o().d_(bin.a());
		boolean boolean4 = cfj3.a(this);
		cfj cfj5 = boolean4 ? cfj3 : this.n();

		for (fz fz9 : bin.e()) {
			if (fz9 != fz.DOWN) {
				cga cga10 = a(fz9);
				boolean boolean11 = boolean4 && (Boolean)cfj3.c(cga10);
				if (!boolean11 && this.b(bin.o(), bin.a(), fz9)) {
					return cfj5.a(cga10, Boolean.valueOf(true));
				}
			}
		}

		return boolean4 ? cfj5 : null;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cck.a, b, c, d, e);
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		switch (cap) {
			case CLOCKWISE_180:
				return cfj.a(b, cfj.c(d)).a(c, cfj.c(e)).a(d, cfj.c(b)).a(e, cfj.c(c));
			case COUNTERCLOCKWISE_90:
				return cfj.a(b, cfj.c(c)).a(c, cfj.c(d)).a(d, cfj.c(e)).a(e, cfj.c(b));
			case CLOCKWISE_90:
				return cfj.a(b, cfj.c(e)).a(c, cfj.c(b)).a(d, cfj.c(c)).a(e, cfj.c(d));
			default:
				return cfj;
		}
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		switch (bzj) {
			case LEFT_RIGHT:
				return cfj.a(b, cfj.c(d)).a(d, cfj.c(b));
			case FRONT_BACK:
				return cfj.a(c, cfj.c(e)).a(e, cfj.c(c));
			default:
				return super.a(cfj, bzj);
		}
	}

	public static cga a(fz fz) {
		return (cga)f.get(fz);
	}
}
