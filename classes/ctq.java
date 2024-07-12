import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class ctq {
	private static final uh[] a = new uh[]{
		new uh("underwater_ruin/warm_1"),
		new uh("underwater_ruin/warm_2"),
		new uh("underwater_ruin/warm_3"),
		new uh("underwater_ruin/warm_4"),
		new uh("underwater_ruin/warm_5"),
		new uh("underwater_ruin/warm_6"),
		new uh("underwater_ruin/warm_7"),
		new uh("underwater_ruin/warm_8")
	};
	private static final uh[] b = new uh[]{
		new uh("underwater_ruin/brick_1"),
		new uh("underwater_ruin/brick_2"),
		new uh("underwater_ruin/brick_3"),
		new uh("underwater_ruin/brick_4"),
		new uh("underwater_ruin/brick_5"),
		new uh("underwater_ruin/brick_6"),
		new uh("underwater_ruin/brick_7"),
		new uh("underwater_ruin/brick_8")
	};
	private static final uh[] c = new uh[]{
		new uh("underwater_ruin/cracked_1"),
		new uh("underwater_ruin/cracked_2"),
		new uh("underwater_ruin/cracked_3"),
		new uh("underwater_ruin/cracked_4"),
		new uh("underwater_ruin/cracked_5"),
		new uh("underwater_ruin/cracked_6"),
		new uh("underwater_ruin/cracked_7"),
		new uh("underwater_ruin/cracked_8")
	};
	private static final uh[] d = new uh[]{
		new uh("underwater_ruin/mossy_1"),
		new uh("underwater_ruin/mossy_2"),
		new uh("underwater_ruin/mossy_3"),
		new uh("underwater_ruin/mossy_4"),
		new uh("underwater_ruin/mossy_5"),
		new uh("underwater_ruin/mossy_6"),
		new uh("underwater_ruin/mossy_7"),
		new uh("underwater_ruin/mossy_8")
	};
	private static final uh[] e = new uh[]{
		new uh("underwater_ruin/big_brick_1"), new uh("underwater_ruin/big_brick_2"), new uh("underwater_ruin/big_brick_3"), new uh("underwater_ruin/big_brick_8")
	};
	private static final uh[] f = new uh[]{
		new uh("underwater_ruin/big_mossy_1"), new uh("underwater_ruin/big_mossy_2"), new uh("underwater_ruin/big_mossy_3"), new uh("underwater_ruin/big_mossy_8")
	};
	private static final uh[] g = new uh[]{
		new uh("underwater_ruin/big_cracked_1"),
		new uh("underwater_ruin/big_cracked_2"),
		new uh("underwater_ruin/big_cracked_3"),
		new uh("underwater_ruin/big_cracked_8")
	};
	private static final uh[] h = new uh[]{
		new uh("underwater_ruin/big_warm_4"), new uh("underwater_ruin/big_warm_5"), new uh("underwater_ruin/big_warm_6"), new uh("underwater_ruin/big_warm_7")
	};

	private static uh a(Random random) {
		return v.a(a, random);
	}

	private static uh b(Random random) {
		return v.a(h, random);
	}

	public static void a(cva cva, fu fu, cap cap, List<cty> list, Random random, cob cob) {
		boolean boolean7 = random.nextFloat() <= cob.c;
		float float8 = boolean7 ? 0.9F : 0.8F;
		a(cva, fu, cap, list, random, cob, boolean7, float8);
		if (boolean7 && random.nextFloat() <= cob.d) {
			a(cva, random, cap, fu, cob, list);
		}
	}

	private static void a(cva cva, Random random, cap cap, fu fu, cob cob, List<cty> list) {
		int integer7 = fu.u();
		int integer8 = fu.w();
		fu fu9 = cve.a(new fu(15, 0, 15), bzj.NONE, cap, fu.b).b(integer7, 0, integer8);
		ctd ctd10 = ctd.a(integer7, 0, integer8, fu9.u(), 0, fu9.w());
		fu fu11 = new fu(Math.min(integer7, fu9.u()), 0, Math.min(integer8, fu9.w()));
		List<fu> list12 = a(random, fu11.u(), fu11.w());
		int integer13 = aec.a(random, 4, 8);

		for (int integer14 = 0; integer14 < integer13; integer14++) {
			if (!list12.isEmpty()) {
				int integer15 = random.nextInt(list12.size());
				fu fu16 = (fu)list12.remove(integer15);
				int integer17 = fu16.u();
				int integer18 = fu16.w();
				cap cap19 = cap.a(random);
				fu fu20 = cve.a(new fu(5, 0, 6), bzj.NONE, cap19, fu.b).b(integer17, 0, integer18);
				ctd ctd21 = ctd.a(integer17, 0, integer18, fu20.u(), 0, fu20.w());
				if (!ctd21.b(ctd10)) {
					a(cva, fu16, cap19, list, random, cob, false, 0.8F);
				}
			}
		}
	}

	private static List<fu> a(Random random, int integer2, int integer3) {
		List<fu> list4 = Lists.<fu>newArrayList();
		list4.add(new fu(integer2 - 16 + aec.a(random, 1, 8), 90, integer3 + 16 + aec.a(random, 1, 7)));
		list4.add(new fu(integer2 - 16 + aec.a(random, 1, 8), 90, integer3 + aec.a(random, 1, 7)));
		list4.add(new fu(integer2 - 16 + aec.a(random, 1, 8), 90, integer3 - 16 + aec.a(random, 4, 8)));
		list4.add(new fu(integer2 + aec.a(random, 1, 7), 90, integer3 + 16 + aec.a(random, 1, 7)));
		list4.add(new fu(integer2 + aec.a(random, 1, 7), 90, integer3 - 16 + aec.a(random, 4, 6)));
		list4.add(new fu(integer2 + 16 + aec.a(random, 1, 7), 90, integer3 + 16 + aec.a(random, 3, 8)));
		list4.add(new fu(integer2 + 16 + aec.a(random, 1, 7), 90, integer3 + aec.a(random, 1, 7)));
		list4.add(new fu(integer2 + 16 + aec.a(random, 1, 7), 90, integer3 - 16 + aec.a(random, 4, 8)));
		return list4;
	}

	private static void a(cva cva, fu fu, cap cap, List<cty> list, Random random, cob cob, boolean boolean7, float float8) {
		if (cob.b == ctp.b.WARM) {
			uh uh9 = boolean7 ? b(random) : a(random);
			list.add(new ctq.a(cva, uh9, fu, cap, float8, cob.b, boolean7));
		} else if (cob.b == ctp.b.COLD) {
			uh[] arr9 = boolean7 ? e : b;
			uh[] arr10 = boolean7 ? g : c;
			uh[] arr11 = boolean7 ? f : d;
			int integer12 = random.nextInt(arr9.length);
			list.add(new ctq.a(cva, arr9[integer12], fu, cap, float8, cob.b, boolean7));
			list.add(new ctq.a(cva, arr10[integer12], fu, cap, 0.7F, cob.b, boolean7));
			list.add(new ctq.a(cva, arr11[integer12], fu, cap, 0.5F, cob.b, boolean7));
		}
	}

	public static class a extends cub {
		private final ctp.b d;
		private final float e;
		private final uh f;
		private final cap g;
		private final boolean h;

		public a(cva cva, uh uh, fu fu, cap cap, float float5, ctp.b b, boolean boolean7) {
			super(cmm.J, 0);
			this.f = uh;
			this.c = fu;
			this.g = cap;
			this.e = float5;
			this.d = b;
			this.h = boolean7;
			this.a(cva);
		}

		public a(cva cva, le le) {
			super(cmm.J, le);
			this.f = new uh(le.l("Template"));
			this.g = cap.valueOf(le.l("Rot"));
			this.e = le.j("Integrity");
			this.d = ctp.b.valueOf(le.l("BiomeType"));
			this.h = le.q("IsLarge");
			this.a(cva);
		}

		private void a(cva cva) {
			cve cve3 = cva.a(this.f);
			cvb cvb4 = new cvb().a(this.g).a(bzj.NONE).a(cui.d);
			this.a(cve3, this.c, cvb4);
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Template", this.f.toString());
			le.a("Rot", this.g.name());
			le.a("Integrity", this.e);
			le.a("BiomeType", this.d.toString());
			le.a("IsLarge", this.h);
		}

		@Override
		protected void a(String string, fu fu, bqc bqc, Random random, ctd ctd) {
			if ("chest".equals(string)) {
				bqc.a(fu, bvs.bR.n().a(bwh.d, Boolean.valueOf(bqc.b(fu).a(acz.a))), 2);
				cdl cdl7 = bqc.c(fu);
				if (cdl7 instanceof cdp) {
					((cdp)cdl7).a(this.h ? dao.F : dao.E, random.nextLong());
				}
			} else if ("drowned".equals(string)) {
				bbp bbp7 = aoq.q.a(bqc.n());
				bbp7.et();
				bbp7.a(fu, 0.0F, 0.0F);
				bbp7.a(bqc, bqc.d(fu), apb.STRUCTURE, null, null);
				bqc.c(bbp7);
				if (fu.v() > bqc.t_()) {
					bqc.a(fu, bvs.a.n(), 2);
				} else {
					bqc.a(fu, bvs.A.n(), 2);
				}
			}
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			this.b.b().a(new cuk(this.e)).a(cui.d);
			int integer9 = bqu.a(cio.a.OCEAN_FLOOR_WG, this.c.u(), this.c.w());
			this.c = new fu(this.c.u(), integer9, this.c.w());
			fu fu10 = cve.a(new fu(this.a.a().u() - 1, 0, this.a.a().w() - 1), bzj.NONE, this.g, fu.b).a(this.c);
			this.c = new fu(this.c.u(), this.a(this.c, bqu, fu10), this.c.w());
			return super.a(bqu, bqq, cha, random, ctd, bph, fu);
		}

		private int a(fu fu1, bpg bpg, fu fu3) {
			int integer5 = fu1.v();
			int integer6 = 512;
			int integer7 = integer5 - 1;
			int integer8 = 0;

			for (fu fu10 : fu.a(fu1, fu3)) {
				int integer11 = fu10.u();
				int integer12 = fu10.w();
				int integer13 = fu1.v() - 1;
				fu.a a14 = new fu.a(integer11, integer13, integer12);
				cfj cfj15 = bpg.d_(a14);

				for (cxa cxa16 = bpg.b(a14); (cfj15.g() || cxa16.a(acz.a) || cfj15.b().a(acx.T)) && integer13 > 1; cxa16 = bpg.b(a14)) {
					a14.d(integer11, --integer13, integer12);
					cfj15 = bpg.d_(a14);
				}

				integer6 = Math.min(integer6, integer13);
				if (integer13 < integer7 - 2) {
					integer8++;
				}
			}

			int integer9 = Math.abs(fu1.u() - fu3.u());
			if (integer7 - integer6 > 2 && integer8 > integer9 - 2) {
				integer5 = integer6 + 1;
			}

			return integer5;
		}
	}
}
