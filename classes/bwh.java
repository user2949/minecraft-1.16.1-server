import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class bwh extends buq<cdp> implements cax {
	public static final cgd b = byp.aq;
	public static final cgg<cgb> c = cfz.aF;
	public static final cga d = cfz.C;
	protected static final dfg e = bvr.a(1.0, 0.0, 0.0, 15.0, 14.0, 15.0);
	protected static final dfg f = bvr.a(1.0, 0.0, 1.0, 15.0, 14.0, 16.0);
	protected static final dfg g = bvr.a(0.0, 0.0, 1.0, 15.0, 14.0, 15.0);
	protected static final dfg h = bvr.a(1.0, 0.0, 1.0, 16.0, 14.0, 15.0);
	protected static final dfg i = bvr.a(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
	private static final bxf.b<cdp, Optional<amz>> j = new bxf.b<cdp, Optional<amz>>() {
		public Optional<amz> a(cdp cdp1, cdp cdp2) {
			return Optional.of(new amy(cdp1, cdp2));
		}

		public Optional<amz> a(cdp cdp) {
			return Optional.of(cdp);
		}

		public Optional<amz> b() {
			return Optional.empty();
		}
	};
	private static final bxf.b<cdp, Optional<anj>> k = new bxf.b<cdp, Optional<anj>>() {
		public Optional<anj> a(cdp cdp1, cdp cdp2) {
			final amz amz4 = new amy(cdp1, cdp2);
			return Optional.of(new anj() {
				@Nullable
				@Override
				public bgi createMenu(int integer, beb beb, bec bec) {
					if (cdp1.e(bec) && cdp2.e(bec)) {
						cdp1.d(beb.e);
						cdp2.d(beb.e);
						return bgp.b(integer, beb, amz4);
					} else {
						return null;
					}
				}

				@Override
				public mr d() {
					if (cdp1.Q()) {
						return cdp1.d();
					} else {
						return (mr)(cdp2.Q() ? cdp2.d() : new ne("container.chestDouble"));
					}
				}
			});
		}

		public Optional<anj> a(cdp cdp) {
			return Optional.of(cdp);
		}

		public Optional<anj> b() {
			return Optional.empty();
		}
	};

	protected bwh(cfi.c c, Supplier<cdm<? extends cdp>> supplier) {
		super(c, supplier);
		this.j(this.n.b().a(b, fz.NORTH).a(bwh.c, cgb.SINGLE).a(d, Boolean.valueOf(false)));
	}

	public static bxf.a g(cfj cfj) {
		cgb cgb2 = cfj.c(c);
		if (cgb2 == cgb.SINGLE) {
			return bxf.a.SINGLE;
		} else {
			return cgb2 == cgb.RIGHT ? bxf.a.FIRST : bxf.a.SECOND;
		}
	}

	@Override
	public cak b(cfj cfj) {
		return cak.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(d)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		if (cfj3.a(this) && fz.n().d()) {
			cgb cgb8 = cfj3.c(c);
			if (cfj1.c(c) == cgb.SINGLE && cgb8 != cgb.SINGLE && cfj1.c(b) == cfj3.c(b) && h(cfj3) == fz.f()) {
				return cfj1.a(c, cgb8.b());
			}
		} else if (h(cfj1) == fz) {
			return cfj1.a(c, cgb.SINGLE);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		if (cfj.c(c) == cgb.SINGLE) {
			return i;
		} else {
			switch (h(cfj)) {
				case NORTH:
				default:
					return e;
				case SOUTH:
					return f;
				case WEST:
					return g;
				case EAST:
					return h;
			}
		}
	}

	public static fz h(cfj cfj) {
		fz fz2 = cfj.c(b);
		return cfj.c(c) == cgb.LEFT ? fz2.g() : fz2.h();
	}

	@Override
	public cfj a(bin bin) {
		cgb cgb3 = cgb.SINGLE;
		fz fz4 = bin.f().f();
		cxa cxa5 = bin.o().b(bin.a());
		boolean boolean6 = bin.g();
		fz fz7 = bin.i();
		if (fz7.n().d() && boolean6) {
			fz fz8 = this.a(bin, fz7.f());
			if (fz8 != null && fz8.n() != fz7.n()) {
				fz4 = fz8;
				cgb3 = fz8.h() == fz7.f() ? cgb.RIGHT : cgb.LEFT;
			}
		}

		if (cgb3 == cgb.SINGLE && !boolean6) {
			if (fz4 == this.a(bin, fz4.g())) {
				cgb3 = cgb.LEFT;
			} else if (fz4 == this.a(bin, fz4.h())) {
				cgb3 = cgb.RIGHT;
			}
		}

		return this.n().a(b, fz4).a(c, cgb3).a(d, Boolean.valueOf(cxa5.a() == cxb.c));
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(d) ? cxb.c.a(false) : super.d(cfj);
	}

	@Nullable
	private fz a(bin bin, fz fz) {
		cfj cfj4 = bin.o().d_(bin.a().a(fz));
		return cfj4.a(this) && cfj4.c(c) == cgb.SINGLE ? cfj4.c(b) : null;
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdp) {
				((cdp)cdl7).a(bki.r());
			}
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof amz) {
				anc.a(bqb, fu, (amz)cdl7);
				bqb.c(fu, this);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			anj anj8 = this.b(cfj, bqb, fu);
			if (anj8 != null) {
				bec.a(anj8);
				bec.b(this.c());
				bdd.a(bec, true);
			}

			return ang.CONSUME;
		}
	}

	protected acr<uh> c() {
		return acu.i.b(acu.an);
	}

	@Nullable
	public static amz a(bwh bwh, cfj cfj, bqb bqb, fu fu, boolean boolean5) {
		return (amz)bwh.a(cfj, bqb, fu, boolean5).apply(j).orElse(null);
	}

	public bxf.c<? extends cdp> a(cfj cfj, bqb bqb, fu fu, boolean boolean4) {
		BiPredicate<bqc, fu> biPredicate6;
		if (boolean4) {
			biPredicate6 = (bqc, fux) -> false;
		} else {
			biPredicate6 = bwh::a;
		}

		return bxf.a((cdm<? extends cdp>)this.a.get(), bwh::g, bwh::h, b, cfj, bqb, fu, biPredicate6);
	}

	@Nullable
	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		return (anj)this.a(cfj, bqb, fu, false).apply(k).orElse(null);
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdp();
	}

	public static boolean a(bqc bqc, fu fu) {
		return a((bpg)bqc, fu) || b(bqc, fu);
	}

	private static boolean a(bpg bpg, fu fu) {
		fu fu3 = fu.b();
		return bpg.d_(fu3).g(bpg, fu3);
	}

	private static boolean b(bqc bqc, fu fu) {
		List<aym> list3 = bqc.a(
			aym.class, new deg((double)fu.u(), (double)(fu.v() + 1), (double)fu.w(), (double)(fu.u() + 1), (double)(fu.v() + 2), (double)(fu.w() + 1))
		);
		if (!list3.isEmpty()) {
			for (aym aym5 : list3) {
				if (aym5.eN()) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return bgi.b(a(this, cfj, bqb, fu, false));
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(b, cap.a(cfj.c(b)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(b)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(b, c, d);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
