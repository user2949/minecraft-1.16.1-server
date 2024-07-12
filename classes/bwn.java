import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.Random;
import javax.annotation.Nullable;

public class bwn extends bvr implements anr {
	public static final cgi a = cfz.as;
	public static final Object2FloatMap<bqa> b = new Object2FloatOpenHashMap<>();
	private static final dfg c = dfd.b();
	private static final dfg[] d = v.a(new dfg[9], arr -> {
		for (int integer2 = 0; integer2 < 8; integer2++) {
			arr[integer2] = dfd.a(c, bvr.a(2.0, (double)Math.max(2, 1 + integer2 * 2), 2.0, 14.0, 16.0, 14.0), deq.e);
		}

		arr[8] = arr[7];
	});

	public static void c() {
		b.defaultReturnValue(-1.0F);
		float float1 = 0.3F;
		float float2 = 0.5F;
		float float3 = 0.65F;
		float float4 = 0.85F;
		float float5 = 1.0F;
		a(0.3F, bkk.au);
		a(0.3F, bkk.ar);
		a(0.3F, bkk.as);
		a(0.3F, bkk.aw);
		a(0.3F, bkk.av);
		a(0.3F, bkk.at);
		a(0.3F, bkk.x);
		a(0.3F, bkk.y);
		a(0.3F, bkk.z);
		a(0.3F, bkk.A);
		a(0.3F, bkk.B);
		a(0.3F, bkk.C);
		a(0.3F, bkk.qf);
		a(0.3F, bkk.ni);
		a(0.3F, bkk.aL);
		a(0.3F, bkk.bE);
		a(0.3F, bkk.nk);
		a(0.3F, bkk.nj);
		a(0.3F, bkk.aO);
		a(0.3F, bkk.rl);
		a(0.3F, bkk.kV);
		a(0.5F, bkk.ma);
		a(0.5F, bkk.gn);
		a(0.5F, bkk.cX);
		a(0.5F, bkk.bD);
		a(0.5F, bkk.dR);
		a(0.5F, bkk.bA);
		a(0.5F, bkk.bB);
		a(0.5F, bkk.bC);
		a(0.5F, bkk.nh);
		a(0.65F, bkk.aP);
		a(0.65F, bkk.ed);
		a(0.65F, bkk.di);
		a(0.65F, bkk.dj);
		a(0.65F, bkk.dQ);
		a(0.65F, bkk.ke);
		a(0.65F, bkk.qe);
		a(0.65F, bkk.oX);
		a(0.65F, bkk.mu);
		a(0.65F, bkk.oY);
		a(0.65F, bkk.kW);
		a(0.65F, bkk.bu);
		a(0.65F, bkk.bv);
		a(0.65F, bkk.dM);
		a(0.65F, bkk.bw);
		a(0.65F, bkk.bx);
		a(0.65F, bkk.nu);
		a(0.65F, bkk.by);
		a(0.65F, bkk.bz);
		a(0.65F, bkk.ro);
		a(0.65F, bkk.bh);
		a(0.65F, bkk.bi);
		a(0.65F, bkk.bj);
		a(0.65F, bkk.bk);
		a(0.65F, bkk.bl);
		a(0.65F, bkk.bm);
		a(0.65F, bkk.bn);
		a(0.65F, bkk.bo);
		a(0.65F, bkk.bp);
		a(0.65F, bkk.bq);
		a(0.65F, bkk.br);
		a(0.65F, bkk.bs);
		a(0.65F, bkk.bt);
		a(0.65F, bkk.aM);
		a(0.65F, bkk.gj);
		a(0.65F, bkk.gk);
		a(0.65F, bkk.gl);
		a(0.65F, bkk.gm);
		a(0.65F, bkk.go);
		a(0.85F, bkk.fL);
		a(0.85F, bkk.dK);
		a(0.85F, bkk.dL);
		a(0.85F, bkk.hj);
		a(0.85F, bkk.hk);
		a(0.85F, bkk.kX);
		a(0.85F, bkk.oZ);
		a(0.85F, bkk.ne);
		a(1.0F, bkk.mN);
		a(1.0F, bkk.pm);
	}

	private static void a(float float1, bqa bqa) {
		b.put(bqa.h(), float1);
	}

	public bwn(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return d[cfj.c(a)];
	}

	@Override
	public dfg a_(cfj cfj, bpg bpg, fu fu) {
		return c;
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return d[0];
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if ((Integer)cfj1.c(a) == 7) {
			bqb.G().a(fu, cfj1.b(), 20);
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		int integer8 = (Integer)cfj.c(a);
		bki bki9 = bec.b(anf);
		if (integer8 < 8 && b.containsKey(bki9.b())) {
			if (integer8 < 7 && !bqb.v) {
				cfj cfj10 = b(cfj, bqb, fu, bki9);
				bqb.c(1500, fu, cfj != cfj10 ? 1 : 0);
				if (!bec.bJ.d) {
					bki9.g(1);
				}
			}

			return ang.a(bqb.v);
		} else if (integer8 == 8) {
			d(cfj, bqb, fu);
			return ang.a(bqb.v);
		} else {
			return ang.PASS;
		}
	}

	public static cfj a(cfj cfj, zd zd, bki bki, fu fu) {
		int integer5 = (Integer)cfj.c(a);
		if (integer5 < 7 && b.containsKey(bki.b())) {
			cfj cfj6 = b(cfj, zd, fu, bki);
			bki.g(1);
			zd.c(1500, fu, cfj != cfj6 ? 1 : 0);
			return cfj6;
		} else {
			return cfj;
		}
	}

	public static cfj d(cfj cfj, bqb bqb, fu fu) {
		if (!bqb.v) {
			float float4 = 0.7F;
			double double5 = (double)(bqb.t.nextFloat() * 0.7F) + 0.15F;
			double double7 = (double)(bqb.t.nextFloat() * 0.7F) + 0.060000002F + 0.6;
			double double9 = (double)(bqb.t.nextFloat() * 0.7F) + 0.15F;
			bbg bbg11 = new bbg(bqb, (double)fu.u() + double5, (double)fu.v() + double7, (double)fu.w() + double9, new bki(bkk.mG));
			bbg11.m();
			bqb.c(bbg11);
		}

		cfj cfj4 = d(cfj, (bqc)bqb, fu);
		bqb.a(null, fu, acl.bU, acm.BLOCKS, 1.0F, 1.0F);
		return cfj4;
	}

	private static cfj d(cfj cfj, bqc bqc, fu fu) {
		cfj cfj4 = cfj.a(a, Integer.valueOf(0));
		bqc.a(fu, cfj4, 3);
		return cfj4;
	}

	private static cfj b(cfj cfj, bqc bqc, fu fu, bki bki) {
		int integer5 = (Integer)cfj.c(a);
		float float6 = b.getFloat(bki.b());
		if ((integer5 != 0 || !(float6 > 0.0F)) && !(bqc.v_().nextDouble() < (double)float6)) {
			return cfj;
		} else {
			int integer7 = integer5 + 1;
			cfj cfj8 = cfj.a(a, Integer.valueOf(integer7));
			bqc.a(fu, cfj8, 3);
			if (integer7 == 7) {
				bqc.G().a(fu, cfj.b(), 20);
			}

			return cfj8;
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if ((Integer)cfj.c(a) == 7) {
			zd.a(fu, cfj.a(a), 3);
			zd.a(null, fu, acl.bX, acm.BLOCKS, 1.0F, 1.0F);
		}
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return (Integer)cfj.c(a);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bwn.a);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}

	@Override
	public anq a(cfj cfj, bqc bqc, fu fu) {
		int integer5 = (Integer)cfj.c(a);
		if (integer5 == 8) {
			return new bwn.c(cfj, bqc, fu, new bki(bkk.mG));
		} else {
			return (anq)(integer5 < 7 ? new bwn.b(cfj, bqc, fu) : new bwn.a());
		}
	}

	static class a extends anm implements anq {
		public a() {
			super(0);
		}

		@Override
		public int[] a(fz fz) {
			return new int[0];
		}

		@Override
		public boolean a(int integer, bki bki, @Nullable fz fz) {
			return false;
		}

		@Override
		public boolean b(int integer, bki bki, fz fz) {
			return false;
		}
	}

	static class b extends anm implements anq {
		private final cfj a;
		private final bqc b;
		private final fu c;
		private boolean d;

		public b(cfj cfj, bqc bqc, fu fu) {
			super(1);
			this.a = cfj;
			this.b = bqc;
			this.c = fu;
		}

		@Override
		public int X_() {
			return 1;
		}

		@Override
		public int[] a(fz fz) {
			return fz == fz.UP ? new int[]{0} : new int[0];
		}

		@Override
		public boolean a(int integer, bki bki, @Nullable fz fz) {
			return !this.d && fz == fz.UP && bwn.b.containsKey(bki.b());
		}

		@Override
		public boolean b(int integer, bki bki, fz fz) {
			return false;
		}

		@Override
		public void Z_() {
			bki bki2 = this.a(0);
			if (!bki2.a()) {
				this.d = true;
				cfj cfj3 = bwn.b(this.a, this.b, this.c, bki2);
				this.b.c(1500, this.c, cfj3 != this.a ? 1 : 0);
				this.b(0);
			}
		}
	}

	static class c extends anm implements anq {
		private final cfj a;
		private final bqc b;
		private final fu c;
		private boolean d;

		public c(cfj cfj, bqc bqc, fu fu, bki bki) {
			super(bki);
			this.a = cfj;
			this.b = bqc;
			this.c = fu;
		}

		@Override
		public int X_() {
			return 1;
		}

		@Override
		public int[] a(fz fz) {
			return fz == fz.DOWN ? new int[]{0} : new int[0];
		}

		@Override
		public boolean a(int integer, bki bki, @Nullable fz fz) {
			return false;
		}

		@Override
		public boolean b(int integer, bki bki, fz fz) {
			return !this.d && fz == fz.DOWN && bki.b() == bkk.mG;
		}

		@Override
		public void Z_() {
			bwn.d(this.a, this.b, this.c);
			this.d = true;
		}
	}
}
