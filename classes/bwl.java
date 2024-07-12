import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bwl extends bvg {
	private static final Logger c = LogManager.getLogger();
	public static final cgd a = bxc.a;
	public static final cga b = cfz.c;

	public bwl(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)));
	}

	@Override
	public cdl a(bpg bpg) {
		cdq cdq3 = new cdq();
		cdq3.b(this == bvs.iH);
		return cdq3;
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (!bqb.v) {
			cdl cdl8 = bqb.c(fu3);
			if (cdl8 instanceof cdq) {
				cdq cdq9 = (cdq)cdl8;
				boolean boolean10 = bqb.r(fu3);
				boolean boolean11 = cdq9.f();
				cdq9.a(boolean10);
				if (!boolean11 && !cdq9.g() && cdq9.m() != cdq.a.SEQUENCE) {
					if (boolean10) {
						cdq9.k();
						bqb.G().a(fu3, this, 1);
					}
				}
			}
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		cdl cdl6 = zd.c(fu);
		if (cdl6 instanceof cdq) {
			cdq cdq7 = (cdq)cdl6;
			bpc bpc8 = cdq7.d();
			boolean boolean9 = !aei.b(bpc8.k());
			cdq.a a10 = cdq7.m();
			boolean boolean11 = cdq7.j();
			if (a10 == cdq.a.AUTO) {
				cdq7.k();
				if (boolean11) {
					this.a(cfj, zd, fu, bpc8, boolean9);
				} else if (cdq7.x()) {
					bpc8.a(0);
				}

				if (cdq7.f() || cdq7.g()) {
					zd.j().a(fu, this, 1);
				}
			} else if (a10 == cdq.a.REDSTONE) {
				if (boolean11) {
					this.a(cfj, zd, fu, bpc8, boolean9);
				} else if (cdq7.x()) {
					bpc8.a(0);
				}
			}

			zd.c(fu, this);
		}
	}

	private void a(cfj cfj, bqb bqb, fu fu, bpc bpc, boolean boolean5) {
		if (boolean5) {
			bpc.a(bqb);
		} else {
			bpc.a(0);
		}

		a(bqb, fu, cfj.c(a));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		cdl cdl8 = bqb.c(fu);
		if (cdl8 instanceof cdq && bec.eV()) {
			bec.a((cdq)cdl8);
			return ang.a(bqb.v);
		} else {
			return ang.PASS;
		}
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		cdl cdl5 = bqb.c(fu);
		return cdl5 instanceof cdq ? ((cdq)cdl5).d().i() : 0;
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		cdl cdl7 = bqb.c(fu);
		if (cdl7 instanceof cdq) {
			cdq cdq8 = (cdq)cdl7;
			bpc bpc9 = cdq8.d();
			if (bki.t()) {
				bpc9.a(bki.r());
			}

			if (!bqb.v) {
				if (bki.b("BlockEntityTag") == null) {
					bpc9.a(bqb.S().b(bpx.n));
					cdq8.b(this == bvs.iH);
				}

				if (cdq8.m() == cdq.a.SEQUENCE) {
					boolean boolean10 = bqb.r(fu);
					cdq8.a(boolean10);
				}
			}
		}
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(a)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bwl.a, b);
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.d().f());
	}

	private static void a(bqb bqb, fu fu, fz fz) {
		fu.a a4 = fu.i();
		bpx bpx5 = bqb.S();
		int integer6 = bpx5.c(bpx.v);

		while (integer6-- > 0) {
			a4.c(fz);
			cfj cfj7 = bqb.d_(a4);
			bvr bvr8 = cfj7.b();
			if (!cfj7.a(bvs.iH)) {
				break;
			}

			cdl cdl9 = bqb.c(a4);
			if (!(cdl9 instanceof cdq)) {
				break;
			}

			cdq cdq10 = (cdq)cdl9;
			if (cdq10.m() != cdq.a.SEQUENCE) {
				break;
			}

			if (cdq10.f() || cdq10.g()) {
				bpc bpc11 = cdq10.d();
				if (cdq10.k()) {
					if (!bpc11.a(bqb)) {
						break;
					}

					bqb.c(a4, bvr8);
				} else if (cdq10.x()) {
					bpc11.a(0);
				}
			}

			fz = cfj7.c(a);
		}

		if (integer6 <= 0) {
			int integer7 = Math.max(bpx5.c(bpx.v), 0);
			c.warn("Command Block chain tried to execute more than {} steps!", integer7);
		}
	}
}
