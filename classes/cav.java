import java.util.List;
import javax.annotation.Nullable;

public class cav extends bvg {
	public static final cgg<fz> a = bxc.a;
	public static final uh b = new uh("contents");
	@Nullable
	private final bje c;

	public cav(@Nullable bje bje, cfi.c c) {
		super(c);
		this.c = bje;
		this.j(this.n.b().a(a, fz.UP));
	}

	@Override
	public cdl a(bpg bpg) {
		return new ceg(this.c);
	}

	@Override
	public cak b(cfj cfj) {
		return cak.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else if (bec.a_()) {
			return ang.CONSUME;
		} else {
			cdl cdl8 = bqb.c(fu);
			if (cdl8 instanceof ceg) {
				ceg ceg9 = (ceg)cdl8;
				boolean boolean10;
				if (ceg9.j() == ceg.a.CLOSED) {
					fz fz11 = cfj.c(a);
					boolean10 = bqb.b(anl.a(fu, fz11));
				} else {
					boolean10 = true;
				}

				if (boolean10) {
					bec.a(ceg9);
					bec.a(acu.ap);
					bdd.a(bec, true);
				}

				return ang.CONSUME;
			} else {
				return ang.PASS;
			}
		}
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.i());
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cav.a);
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		cdl cdl6 = bqb.c(fu);
		if (cdl6 instanceof ceg) {
			ceg ceg7 = (ceg)cdl6;
			if (!bqb.v && bec.b_() && !ceg7.c()) {
				bki bki8 = b(this.c());
				le le9 = ceg7.e(new le());
				if (!le9.isEmpty()) {
					bki8.a("BlockEntityTag", le9);
				}

				if (ceg7.Q()) {
					bki8.a(ceg7.R());
				}

				bbg bbg10 = new bbg(bqb, (double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5, bki8);
				bbg10.m();
				bqb.c(bbg10);
			} else {
				ceg7.d(bec);
			}
		}

		super.a(bqb, fu, cfj, bec);
	}

	@Override
	public List<bki> a(cfj cfj, dat.a a) {
		cdl cdl4 = a.b(dda.i);
		if (cdl4 instanceof ceg) {
			ceg ceg5 = (ceg)cdl4;
			a = a.a(b, (dat, consumer) -> {
				for (int integer4 = 0; integer4 < ceg5.ab_(); integer4++) {
					consumer.accept(ceg5.a(integer4));
				}
			});
		}

		return super.a(cfj, a);
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof ceg) {
				((ceg)cdl7).a(bki.r());
			}
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof ceg) {
				bqb.c(fu, cfj1.b());
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public cxf f(cfj cfj) {
		return cxf.DESTROY;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		cdl cdl6 = bpg.c(fu);
		return cdl6 instanceof ceg ? dfd.a(((ceg)cdl6).a(cfj)) : dfd.b();
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return bgi.b((amz)bqb.c(fu));
	}

	public static bvr a(@Nullable bje bje) {
		if (bje == null) {
			return bvs.iP;
		} else {
			switch (bje) {
				case WHITE:
					return bvs.iQ;
				case ORANGE:
					return bvs.iR;
				case MAGENTA:
					return bvs.iS;
				case LIGHT_BLUE:
					return bvs.iT;
				case YELLOW:
					return bvs.iU;
				case LIME:
					return bvs.iV;
				case PINK:
					return bvs.iW;
				case GRAY:
					return bvs.iX;
				case LIGHT_GRAY:
					return bvs.iY;
				case CYAN:
					return bvs.iZ;
				case PURPLE:
				default:
					return bvs.ja;
				case BLUE:
					return bvs.jb;
				case BROWN:
					return bvs.jc;
				case GREEN:
					return bvs.jd;
				case RED:
					return bvs.je;
				case BLACK:
					return bvs.jf;
			}
		}
	}

	@Nullable
	public bje c() {
		return this.c;
	}

	public static bki b(@Nullable bje bje) {
		return new bki(a(bje));
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(a)));
	}
}
