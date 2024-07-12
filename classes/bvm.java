import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class bvm extends byp implements bxp {
	public static final cgg<cfx> a = cfz.aE;
	public static final cga b = cfz.t;
	protected static final dfg c = bvr.a(0.0, 3.0, 0.0, 16.0, 9.0, 16.0);
	protected static final dfg d = bvr.a(0.0, 0.0, 0.0, 3.0, 3.0, 3.0);
	protected static final dfg e = bvr.a(0.0, 0.0, 13.0, 3.0, 3.0, 16.0);
	protected static final dfg f = bvr.a(13.0, 0.0, 0.0, 16.0, 3.0, 3.0);
	protected static final dfg g = bvr.a(13.0, 0.0, 13.0, 16.0, 3.0, 16.0);
	protected static final dfg h = dfd.a(c, d, f);
	protected static final dfg i = dfd.a(c, e, g);
	protected static final dfg j = dfd.a(c, d, e);
	protected static final dfg k = dfd.a(c, f, g);
	private final bje o;

	public bvm(bje bje, cfi.c c) {
		super(c);
		this.o = bje;
		this.j(this.n.b().a(a, cfx.FOOT).a(b, Boolean.valueOf(false)));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.CONSUME;
		} else {
			if (cfj.c(a) != cfx.HEAD) {
				fu = fu.a(cfj.c(aq));
				cfj = bqb.d_(fu);
				if (!cfj.a(this)) {
					return ang.CONSUME;
				}
			}

			if (!a(bqb)) {
				bqb.a(fu, false);
				fu fu8 = fu.a(((fz)cfj.c(aq)).f());
				if (bqb.d_(fu8).a(this)) {
					bqb.a(fu8, false);
				}

				bqb.a(null, anw.a(), null, (double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5, 5.0F, true, bpt.a.DESTROY);
				return ang.SUCCESS;
			} else if ((Boolean)cfj.c(b)) {
				if (!this.a(bqb, fu)) {
					bec.a(new ne("block.minecraft.bed.occupied"), true);
				}

				return ang.SUCCESS;
			} else {
				bec.a(fu).ifLeft(a -> {
					if (a != null) {
						bec.a(a.a(), true);
					}
				});
				return ang.SUCCESS;
			}
		}
	}

	public static boolean a(bqb bqb) {
		return bqb.m().j();
	}

	private boolean a(bqb bqb, fu fu) {
		List<bdp> list4 = bqb.a(bdp.class, new deg(fu), aoy::el);
		if (list4.isEmpty()) {
			return false;
		} else {
			((bdp)list4.get(0)).em();
			return true;
		}
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom, float float4) {
		super.a(bqb, fu, aom, float4 * 0.5F);
	}

	@Override
	public void a(bpg bpg, aom aom) {
		if (aom.bs()) {
			super.a(bpg, aom);
		} else {
			this.a(aom);
		}
	}

	private void a(aom aom) {
		dem dem3 = aom.cB();
		if (dem3.c < 0.0) {
			double double4 = aom instanceof aoy ? 1.0 : 0.8;
			aom.m(dem3.b, -dem3.c * 0.66F * double4, dem3.d);
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == a(cfj1.c(a), cfj1.c(aq))) {
			return cfj3.a(this) && cfj3.c(a) != cfj1.c(a) ? cfj1.a(b, cfj3.c(b)) : bvs.a.n();
		} else {
			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		}
	}

	private static fz a(cfx cfx, fz fz) {
		return cfx == cfx.FOOT ? fz : fz.f();
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		if (!bqb.v && bec.b_()) {
			cfx cfx6 = cfj.c(a);
			if (cfx6 == cfx.FOOT) {
				fu fu7 = fu.a(a(cfx6, cfj.c(aq)));
				cfj cfj8 = bqb.d_(fu7);
				if (cfj8.b() == this && cfj8.c(a) == cfx.HEAD) {
					bqb.a(fu7, bvs.a.n(), 35);
					bqb.a(bec, 2001, fu7, bvr.i(cfj8));
				}
			}
		}

		super.a(bqb, fu, cfj, bec);
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		fz fz3 = bin.f();
		fu fu4 = bin.a();
		fu fu5 = fu4.a(fz3);
		return bin.o().d_(fu5).a(bin) ? this.n().a(aq, fz3) : null;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		fz fz6 = g(cfj).f();
		switch (fz6) {
			case NORTH:
				return h;
			case SOUTH:
				return i;
			case WEST:
				return j;
			default:
				return k;
		}
	}

	public static fz g(cfj cfj) {
		fz fz2 = cfj.c(aq);
		return cfj.c(a) == cfx.HEAD ? fz2.f() : fz2;
	}

	public static Optional<dem> a(aoq<?> aoq, bqd bqd, fu fu, int integer) {
		fz fz5 = bqd.d_(fu).c(aq);
		int integer6 = fu.u();
		int integer7 = fu.v();
		int integer8 = fu.w();

		for (int integer9 = 0; integer9 <= 1; integer9++) {
			int integer10 = integer6 - fz5.i() * integer9 - 1;
			int integer11 = integer8 - fz5.k() * integer9 - 1;
			int integer12 = integer10 + 2;
			int integer13 = integer11 + 2;

			for (int integer14 = integer10; integer14 <= integer12; integer14++) {
				for (int integer15 = integer11; integer15 <= integer13; integer15++) {
					fu fu16 = new fu(integer14, integer7, integer15);
					Optional<dem> optional17 = a(aoq, bqd, fu16);
					if (optional17.isPresent()) {
						if (integer <= 0) {
							return optional17;
						}

						integer--;
					}
				}
			}
		}

		return Optional.empty();
	}

	public static Optional<dem> a(aoq<?> aoq, bqd bqd, fu fu) {
		dfg dfg4 = bqd.d_(fu).k(bqd, fu);
		if (dfg4.c(fz.a.Y) > 0.4375) {
			return Optional.empty();
		} else {
			fu.a a5 = fu.i();

			while (a5.v() >= 0 && fu.v() - a5.v() <= 2 && bqd.d_(a5).k(bqd, a5).b()) {
				a5.c(fz.DOWN);
			}

			dfg dfg6 = bqd.d_(a5).k(bqd, a5);
			if (dfg6.b()) {
				return Optional.empty();
			} else {
				double double7 = (double)a5.v() + dfg6.c(fz.a.Y) + 2.0E-7;
				if ((double)fu.v() - double7 > 2.0) {
					return Optional.empty();
				} else {
					dem dem9 = new dem((double)a5.u() + 0.5, double7, (double)a5.w() + 0.5);
					deg deg10 = aoq.a(dem9.b, dem9.c, dem9.d);
					return bqd.b(deg10) && bqd.a(deg10.b(0.0, -0.2F, 0.0)).noneMatch(aoq::a) ? Optional.of(dem9) : Optional.empty();
				}
			}
		}
	}

	@Override
	public cxf f(cfj cfj) {
		return cxf.DESTROY;
	}

	@Override
	public cak b(cfj cfj) {
		return cak.ENTITYBLOCK_ANIMATED;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(aq, bvm.a, b);
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdh(this.o);
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, @Nullable aoy aoy, bki bki) {
		super.a(bqb, fu, cfj, aoy, bki);
		if (!bqb.v) {
			fu fu7 = fu.a(cfj.c(aq));
			bqb.a(fu7, cfj.a(a, cfx.HEAD), 3);
			bqb.a(fu, bvs.a);
			cfj.a(bqb, fu, 3);
		}
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
