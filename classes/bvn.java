import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class bvn extends bvg {
	private static final fz[] c = new fz[]{fz.WEST, fz.EAST, fz.SOUTH};
	public static final cgd a = byp.aq;
	public static final cgi b = cfz.au;

	public bvn(cfi.c c) {
		super(c);
		this.j(this.n.b().a(b, Integer.valueOf(0)).a(a, fz.NORTH));
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return (Integer)cfj.c(b);
	}

	@Override
	public void a(bqb bqb, bec bec, fu fu, cfj cfj, @Nullable cdl cdl, bki bki) {
		super.a(bqb, bec, fu, cfj, cdl, bki);
		if (!bqb.v && cdl instanceof cdi) {
			cdi cdi8 = (cdi)cdl;
			if (bny.a(boa.u, bki) == 0) {
				cdi8.a(bec, cfj, cdi.b.EMERGENCY);
				bqb.c(fu, this);
				this.b(bqb, fu);
			}

			aa.K.a((ze)bec, cfj.b(), bki, cdi8.j());
		}
	}

	private void b(bqb bqb, fu fu) {
		List<ayl> list4 = bqb.a(ayl.class, new deg(fu).c(8.0, 6.0, 8.0));
		if (!list4.isEmpty()) {
			List<bec> list5 = bqb.a(bec.class, new deg(fu).c(8.0, 6.0, 8.0));
			int integer6 = list5.size();

			for (ayl ayl8 : list4) {
				if (ayl8.A() == null) {
					ayl8.i((aoy)list5.get(bqb.t.nextInt(integer6)));
				}
			}
		}
	}

	public static void a(bqb bqb, fu fu) {
		a(bqb, fu, new bki(bkk.rp, 3));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		bki bki8 = bec.b(anf);
		int integer9 = (Integer)cfj.c(b);
		boolean boolean10 = false;
		if (integer9 >= 5) {
			if (bki8.b() == bkk.ng) {
				bqb.a(bec, bec.cC(), bec.cD(), bec.cG(), acl.aH, acm.NEUTRAL, 1.0F, 1.0F);
				a(bqb, fu);
				bki8.a(1, bec, becx -> becx.d(anf));
				boolean10 = true;
			} else if (bki8.b() == bkk.nw) {
				bki8.g(1);
				bqb.a(bec, bec.cC(), bec.cD(), bec.cG(), acl.bb, acm.NEUTRAL, 1.0F, 1.0F);
				if (bki8.a()) {
					bec.a(anf, new bki(bkk.rs));
				} else if (!bec.bt.e(new bki(bkk.rs))) {
					bec.a(new bki(bkk.rs), false);
				}

				boolean10 = true;
			}
		}

		if (boolean10) {
			if (!bwb.a(bqb, fu)) {
				if (this.d(bqb, fu)) {
					this.b(bqb, fu);
				}

				this.a(bqb, cfj, fu, bec, cdi.b.EMERGENCY);
			} else {
				this.a(bqb, cfj, fu);
			}

			return ang.a(bqb.v);
		} else {
			return super.a(cfj, bqb, fu, bec, anf, deh);
		}
	}

	private boolean d(bqb bqb, fu fu) {
		cdl cdl4 = bqb.c(fu);
		if (cdl4 instanceof cdi) {
			cdi cdi5 = (cdi)cdl4;
			return !cdi5.f();
		} else {
			return false;
		}
	}

	public void a(bqb bqb, cfj cfj, fu fu, @Nullable bec bec, cdi.b b) {
		this.a(bqb, cfj, fu);
		cdl cdl7 = bqb.c(fu);
		if (cdl7 instanceof cdi) {
			cdi cdi8 = (cdi)cdl7;
			cdi8.a(bec, cfj, b);
		}
	}

	public void a(bqb bqb, cfj cfj, fu fu) {
		bqb.a(fu, cfj.a(b, Integer.valueOf(0)), 3);
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.f().f());
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(b, bvn.a);
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Nullable
	@Override
	public cdl a(bpg bpg) {
		return new cdi();
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		if (!bqb.v && bec.b_() && bqb.S().b(bpx.f)) {
			cdl cdl6 = bqb.c(fu);
			if (cdl6 instanceof cdi) {
				cdi cdi7 = (cdi)cdl6;
				bki bki8 = new bki(this);
				int integer9 = (Integer)cfj.c(b);
				boolean boolean10 = !cdi7.f();
				if (!boolean10 && integer9 == 0) {
					return;
				}

				if (boolean10) {
					le le11 = new le();
					le11.a("Bees", cdi7.m());
					bki8.a("BlockEntityTag", le11);
				}

				le le11 = new le();
				le11.b("honey_level", integer9);
				bki8.a("BlockStateTag", le11);
				bbg bbg12 = new bbg(bqb, (double)fu.u(), (double)fu.v(), (double)fu.w(), bki8);
				bbg12.m();
				bqb.c(bbg12);
			}
		}

		super.a(bqb, fu, cfj, bec);
	}

	@Override
	public List<bki> a(cfj cfj, dat.a a) {
		aom aom4 = a.b(dda.a);
		if (aom4 instanceof bbh || aom4 instanceof bbn || aom4 instanceof bff || aom4 instanceof baw || aom4 instanceof bgb) {
			cdl cdl5 = a.b(dda.i);
			if (cdl5 instanceof cdi) {
				cdi cdi6 = (cdi)cdl5;
				cdi6.a(null, cfj, cdi.b.EMERGENCY);
			}
		}

		return super.a(cfj, a);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (bqc.d_(fu6).b() instanceof bxv) {
			cdl cdl8 = bqc.c(fu5);
			if (cdl8 instanceof cdi) {
				cdi cdi9 = (cdi)cdl8;
				cdi9.a(null, cfj1, cdi.b.EMERGENCY);
			}
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	public static fz a(Random random) {
		return v.a(c, random);
	}
}
