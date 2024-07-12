import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Random;

public class bxv extends bvh {
	public static final cgi g = cfz.aj;
	public static final cga h = bzv.a;
	public static final cga i = bzv.b;
	public static final cga j = bzv.c;
	public static final cga k = bzv.d;
	public static final cga o = bzv.e;
	private static final Map<fz, cga> p = (Map<fz, cga>)bzv.g.entrySet().stream().filter(entry -> entry.getKey() != fz.DOWN).collect(v.a());
	private final Object2IntMap<bvr> q = new Object2IntOpenHashMap<>();
	private final Object2IntMap<bvr> r = new Object2IntOpenHashMap<>();

	public bxv(cfi.c c) {
		super(c, 1.0F);
		this.j(
			this.n
				.b()
				.a(g, Integer.valueOf(0))
				.a(h, Boolean.valueOf(false))
				.a(i, Boolean.valueOf(false))
				.a(j, Boolean.valueOf(false))
				.a(k, Boolean.valueOf(false))
				.a(o, Boolean.valueOf(false))
		);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return this.a(cfj1, bqc, fu5) ? this.a(bqc, fu5, (Integer)cfj1.c(g)) : bvs.a.n();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		dfg dfg6 = dfd.a();
		if ((Boolean)cfj.c(o)) {
			dfg6 = a;
		}

		if ((Boolean)cfj.c(k)) {
			dfg6 = dfd.a(dfg6, c);
		}

		if ((Boolean)cfj.c(i)) {
			dfg6 = dfd.a(dfg6, d);
		}

		if ((Boolean)cfj.c(h)) {
			dfg6 = dfd.a(dfg6, e);
		}

		if ((Boolean)cfj.c(j)) {
			dfg6 = dfd.a(dfg6, f);
		}

		return dfg6 == dfd.a() ? b : dfg6;
	}

	@Override
	public cfj a(bin bin) {
		return this.b(bin.o(), bin.a());
	}

	protected cfj b(bpg bpg, fu fu) {
		fu fu4 = fu.c();
		cfj cfj5 = bpg.d_(fu4);
		if (!this.e(cfj5) && !cfj5.d(bpg, fu4, fz.UP)) {
			cfj cfj6 = this.n();

			for (fz fz10 : fz.values()) {
				cga cga11 = (cga)p.get(fz10);
				if (cga11 != null) {
					cfj6 = cfj6.a(cga11, Boolean.valueOf(this.e(bpg.d_(fu.a(fz10)))));
				}
			}

			return cfj6;
		} else {
			return this.n();
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fu fu5 = fu.c();
		return bqd.d_(fu5).d(bqd, fu5, fz.UP) || this.e(bqd, fu);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		zd.j().a(fu, this, a(zd.t));
		if (zd.S().b(bpx.a)) {
			if (!cfj.a(zd, fu)) {
				zd.a(fu, false);
			}

			cfj cfj6 = zd.d_(fu.c());
			boolean boolean7 = cfj6.a(zd.m().q());
			int integer8 = (Integer)cfj.c(g);
			if (!boolean7 && zd.U() && this.a((bqb)zd, fu) && random.nextFloat() < 0.2F + (float)integer8 * 0.03F) {
				zd.a(fu, false);
			} else {
				int integer9 = Math.min(15, integer8 + random.nextInt(3) / 2);
				if (integer8 != integer9) {
					cfj = cfj.a(g, Integer.valueOf(integer9));
					zd.a(fu, cfj, 4);
				}

				if (!boolean7) {
					if (!this.e(zd, fu)) {
						fu fu10 = fu.c();
						if (!zd.d_(fu10).d(zd, fu10, fz.UP) || integer8 > 3) {
							zd.a(fu, false);
						}

						return;
					}

					if (integer8 == 15 && random.nextInt(4) == 0 && !this.e(zd.d_(fu.c()))) {
						zd.a(fu, false);
						return;
					}
				}

				boolean boolean10 = zd.u(fu);
				int integer11 = boolean10 ? -50 : 0;
				this.a(zd, fu.g(), 300 + integer11, random, integer8);
				this.a(zd, fu.f(), 300 + integer11, random, integer8);
				this.a(zd, fu.c(), 250 + integer11, random, integer8);
				this.a(zd, fu.b(), 250 + integer11, random, integer8);
				this.a(zd, fu.d(), 300 + integer11, random, integer8);
				this.a(zd, fu.e(), 300 + integer11, random, integer8);
				fu.a a12 = new fu.a();

				for (int integer13 = -1; integer13 <= 1; integer13++) {
					for (int integer14 = -1; integer14 <= 1; integer14++) {
						for (int integer15 = -1; integer15 <= 4; integer15++) {
							if (integer13 != 0 || integer15 != 0 || integer14 != 0) {
								int integer16 = 100;
								if (integer15 > 1) {
									integer16 += (integer15 - 1) * 100;
								}

								a12.a(fu, integer13, integer15, integer14);
								int integer17 = this.a((bqd)zd, a12);
								if (integer17 > 0) {
									int integer18 = (integer17 + 40 + zd.ac().a() * 7) / (integer8 + 30);
									if (boolean10) {
										integer18 /= 2;
									}

									if (integer18 > 0 && random.nextInt(integer16) <= integer18 && (!zd.U() || !this.a((bqb)zd, a12))) {
										int integer19 = Math.min(15, integer8 + random.nextInt(5) / 4);
										zd.a(a12, this.a(zd, a12, integer19), 3);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	protected boolean a(bqb bqb, fu fu) {
		return bqb.t(fu) || bqb.t(fu.f()) || bqb.t(fu.g()) || bqb.t(fu.d()) || bqb.t(fu.e());
	}

	private int h(cfj cfj) {
		return cfj.b(cfz.C) && cfj.c(cfz.C) ? 0 : this.r.getInt(cfj.b());
	}

	private int l(cfj cfj) {
		return cfj.b(cfz.C) && cfj.c(cfz.C) ? 0 : this.q.getInt(cfj.b());
	}

	private void a(bqb bqb, fu fu, int integer3, Random random, int integer5) {
		int integer7 = this.h(bqb.d_(fu));
		if (random.nextInt(integer3) < integer7) {
			cfj cfj8 = bqb.d_(fu);
			if (random.nextInt(integer5 + 10) < 5 && !bqb.t(fu)) {
				int integer9 = Math.min(integer5 + random.nextInt(5) / 4, 15);
				bqb.a(fu, this.a(bqb, fu, integer9), 3);
			} else {
				bqb.a(fu, false);
			}

			bvr bvr9 = cfj8.b();
			if (bvr9 instanceof ccb) {
				ccb.a(bqb, fu);
			}
		}
	}

	private cfj a(bqc bqc, fu fu, int integer) {
		cfj cfj5 = a(bqc, fu);
		return cfj5.a(bvs.bN) ? cfj5.a(g, Integer.valueOf(integer)) : cfj5;
	}

	private boolean e(bpg bpg, fu fu) {
		for (fz fz7 : fz.values()) {
			if (this.e(bpg.d_(fu.a(fz7)))) {
				return true;
			}
		}

		return false;
	}

	private int a(bqd bqd, fu fu) {
		if (!bqd.w(fu)) {
			return 0;
		} else {
			int integer4 = 0;

			for (fz fz8 : fz.values()) {
				cfj cfj9 = bqd.d_(fu.a(fz8));
				integer4 = Math.max(this.l(cfj9), integer4);
			}

			return integer4;
		}
	}

	@Override
	protected boolean e(cfj cfj) {
		return this.l(cfj) > 0;
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		super.b(cfj1, bqb, fu, cfj4, boolean5);
		bqb.G().a(fu, this, a(bqb.t));
	}

	private static int a(Random random) {
		return 30 + random.nextInt(10);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(g, h, i, j, k, o);
	}

	private void a(bvr bvr, int integer2, int integer3) {
		this.q.put(bvr, integer2);
		this.r.put(bvr, integer3);
	}

	public static void c() {
		bxv bxv1 = (bxv)bvs.bN;
		bxv1.a(bvs.n, 5, 20);
		bxv1.a(bvs.o, 5, 20);
		bxv1.a(bvs.p, 5, 20);
		bxv1.a(bvs.q, 5, 20);
		bxv1.a(bvs.r, 5, 20);
		bxv1.a(bvs.s, 5, 20);
		bxv1.a(bvs.hK, 5, 20);
		bxv1.a(bvs.hL, 5, 20);
		bxv1.a(bvs.hM, 5, 20);
		bxv1.a(bvs.hN, 5, 20);
		bxv1.a(bvs.hO, 5, 20);
		bxv1.a(bvs.hP, 5, 20);
		bxv1.a(bvs.dQ, 5, 20);
		bxv1.a(bvs.ih, 5, 20);
		bxv1.a(bvs.ii, 5, 20);
		bxv1.a(bvs.ij, 5, 20);
		bxv1.a(bvs.il, 5, 20);
		bxv1.a(bvs.ik, 5, 20);
		bxv1.a(bvs.cJ, 5, 20);
		bxv1.a(bvs.im, 5, 20);
		bxv1.a(bvs.in, 5, 20);
		bxv1.a(bvs.io, 5, 20);
		bxv1.a(bvs.iq, 5, 20);
		bxv1.a(bvs.ip, 5, 20);
		bxv1.a(bvs.bQ, 5, 20);
		bxv1.a(bvs.ep, 5, 20);
		bxv1.a(bvs.eo, 5, 20);
		bxv1.a(bvs.eq, 5, 20);
		bxv1.a(bvs.gl, 5, 20);
		bxv1.a(bvs.gm, 5, 20);
		bxv1.a(bvs.J, 5, 5);
		bxv1.a(bvs.K, 5, 5);
		bxv1.a(bvs.L, 5, 5);
		bxv1.a(bvs.M, 5, 5);
		bxv1.a(bvs.N, 5, 5);
		bxv1.a(bvs.O, 5, 5);
		bxv1.a(bvs.U, 5, 5);
		bxv1.a(bvs.P, 5, 5);
		bxv1.a(bvs.Q, 5, 5);
		bxv1.a(bvs.R, 5, 5);
		bxv1.a(bvs.S, 5, 5);
		bxv1.a(bvs.T, 5, 5);
		bxv1.a(bvs.ab, 5, 5);
		bxv1.a(bvs.ac, 5, 5);
		bxv1.a(bvs.ad, 5, 5);
		bxv1.a(bvs.ae, 5, 5);
		bxv1.a(bvs.af, 5, 5);
		bxv1.a(bvs.ag, 5, 5);
		bxv1.a(bvs.V, 5, 5);
		bxv1.a(bvs.W, 5, 5);
		bxv1.a(bvs.X, 5, 5);
		bxv1.a(bvs.Y, 5, 5);
		bxv1.a(bvs.Z, 5, 5);
		bxv1.a(bvs.aa, 5, 5);
		bxv1.a(bvs.ah, 30, 60);
		bxv1.a(bvs.ai, 30, 60);
		bxv1.a(bvs.aj, 30, 60);
		bxv1.a(bvs.ak, 30, 60);
		bxv1.a(bvs.al, 30, 60);
		bxv1.a(bvs.am, 30, 60);
		bxv1.a(bvs.bI, 30, 20);
		bxv1.a(bvs.bH, 15, 100);
		bxv1.a(bvs.aR, 60, 100);
		bxv1.a(bvs.aS, 60, 100);
		bxv1.a(bvs.aT, 60, 100);
		bxv1.a(bvs.gU, 60, 100);
		bxv1.a(bvs.gV, 60, 100);
		bxv1.a(bvs.gW, 60, 100);
		bxv1.a(bvs.gX, 60, 100);
		bxv1.a(bvs.gY, 60, 100);
		bxv1.a(bvs.gZ, 60, 100);
		bxv1.a(bvs.bp, 60, 100);
		bxv1.a(bvs.bq, 60, 100);
		bxv1.a(bvs.br, 60, 100);
		bxv1.a(bvs.bs, 60, 100);
		bxv1.a(bvs.bt, 60, 100);
		bxv1.a(bvs.bu, 60, 100);
		bxv1.a(bvs.bv, 60, 100);
		bxv1.a(bvs.bw, 60, 100);
		bxv1.a(bvs.bx, 60, 100);
		bxv1.a(bvs.by, 60, 100);
		bxv1.a(bvs.bz, 60, 100);
		bxv1.a(bvs.bB, 60, 100);
		bxv1.a(bvs.bA, 60, 100);
		bxv1.a(bvs.aY, 30, 60);
		bxv1.a(bvs.aZ, 30, 60);
		bxv1.a(bvs.ba, 30, 60);
		bxv1.a(bvs.bb, 30, 60);
		bxv1.a(bvs.bc, 30, 60);
		bxv1.a(bvs.bd, 30, 60);
		bxv1.a(bvs.be, 30, 60);
		bxv1.a(bvs.bf, 30, 60);
		bxv1.a(bvs.bg, 30, 60);
		bxv1.a(bvs.bh, 30, 60);
		bxv1.a(bvs.bi, 30, 60);
		bxv1.a(bvs.bj, 30, 60);
		bxv1.a(bvs.bk, 30, 60);
		bxv1.a(bvs.bl, 30, 60);
		bxv1.a(bvs.bm, 30, 60);
		bxv1.a(bvs.bn, 30, 60);
		bxv1.a(bvs.dP, 15, 100);
		bxv1.a(bvs.gS, 5, 5);
		bxv1.a(bvs.gA, 60, 20);
		bxv1.a(bvs.nb, 15, 20);
		bxv1.a(bvs.gB, 60, 20);
		bxv1.a(bvs.gC, 60, 20);
		bxv1.a(bvs.gD, 60, 20);
		bxv1.a(bvs.gE, 60, 20);
		bxv1.a(bvs.gF, 60, 20);
		bxv1.a(bvs.gG, 60, 20);
		bxv1.a(bvs.gH, 60, 20);
		bxv1.a(bvs.gI, 60, 20);
		bxv1.a(bvs.gJ, 60, 20);
		bxv1.a(bvs.gK, 60, 20);
		bxv1.a(bvs.gL, 60, 20);
		bxv1.a(bvs.gM, 60, 20);
		bxv1.a(bvs.gN, 60, 20);
		bxv1.a(bvs.gO, 60, 20);
		bxv1.a(bvs.gP, 60, 20);
		bxv1.a(bvs.gQ, 60, 20);
		bxv1.a(bvs.ke, 30, 60);
		bxv1.a(bvs.kY, 60, 60);
		bxv1.a(bvs.lQ, 60, 60);
		bxv1.a(bvs.lY, 30, 20);
		bxv1.a(bvs.na, 5, 20);
		bxv1.a(bvs.mg, 60, 100);
		bxv1.a(bvs.nd, 5, 20);
		bxv1.a(bvs.nc, 30, 20);
	}
}
