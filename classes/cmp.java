import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;

public class cmp extends ckt<cou> {
	public cmp(Codec<cou> codec) {
		super(codec);
	}

	public static boolean c(bqg bqg, fu fu) {
		return e(bqg, fu) || bqg.a(fu, cfj -> cfj.a(acx.r));
	}

	private static boolean f(bqg bqg, fu fu) {
		return bqg.a(fu, cfj -> cfj.a(bvs.dP));
	}

	private static boolean g(bqg bqg, fu fu) {
		return bqg.a(fu, cfj -> cfj.a(bvs.A));
	}

	public static boolean d(bqg bqg, fu fu) {
		return bqg.a(fu, cfj -> cfj.g() || cfj.a(acx.H));
	}

	private static boolean h(bqg bqg, fu fu) {
		return bqg.a(fu, cfj -> {
			bvr bvr2 = cfj.b();
			return b(bvr2) || bvr2 == bvs.bX;
		});
	}

	private static boolean i(bqg bqg, fu fu) {
		return bqg.a(fu, cfj -> {
			cxd cxd2 = cfj.c();
			return cxd2 == cxd.g;
		});
	}

	public static void b(bqh bqh, fu fu, cfj cfj) {
		bqh.a(fu, cfj, 19);
	}

	public static boolean e(bqg bqg, fu fu) {
		return d(bqg, fu) || i(bqg, fu) || g(bqg, fu);
	}

	private boolean a(bqf bqf, Random random, fu fu, Set<fu> set4, Set<fu> set5, ctd ctd, cou cou) {
		int integer9 = cou.g.a(random);
		int integer10 = cou.f.a(random, integer9, cou);
		int integer11 = integer9 - integer10;
		int integer12 = cou.f.a(random, integer11);
		fu fu13;
		if (!cou.e) {
			int integer14 = bqf.a(cio.a.OCEAN_FLOOR, fu).v();
			int integer15 = bqf.a(cio.a.WORLD_SURFACE, fu).v();
			if (integer15 - integer14 > cou.i) {
				return false;
			}

			int integer16;
			if (cou.l == cio.a.OCEAN_FLOOR) {
				integer16 = integer14;
			} else if (cou.l == cio.a.WORLD_SURFACE) {
				integer16 = integer15;
			} else {
				integer16 = bqf.a(cou.l, fu).v();
			}

			fu13 = new fu(fu.u(), integer16, fu.w());
		} else {
			fu13 = fu;
		}

		if (fu13.v() < 1 || fu13.v() + integer9 + 1 > 256) {
			return false;
		} else if (!h(bqf, fu13.c())) {
			return false;
		} else {
			OptionalInt optionalInt14 = cou.h.c();
			int integer15x = this.a(bqf, integer9, fu13, cou);
			if (integer15x >= integer9 || optionalInt14.isPresent() && integer15x >= optionalInt14.getAsInt()) {
				List<cpg.b> list16 = cou.g.a(bqf, random, integer15x, fu13, set4, ctd, cou);
				list16.forEach(b -> cou.f.a(bqf, random, cou, integer15, b, integer10, integer12, set5, ctd));
				return true;
			} else {
				return false;
			}
		}
	}

	private int a(bqg bqg, int integer, fu fu, cou cou) {
		fu.a a6 = new fu.a();

		for (int integer7 = 0; integer7 <= integer + 1; integer7++) {
			int integer8 = cou.h.a(integer, integer7);

			for (int integer9 = -integer8; integer9 <= integer8; integer9++) {
				for (int integer10 = -integer8; integer10 <= integer8; integer10++) {
					a6.a(fu, integer9, integer7, integer10);
					if (!c(bqg, a6) || !cou.j && f(bqg, a6)) {
						return integer7 - 2;
					}
				}
			}
		}

		return integer;
	}

	@Override
	protected void a(bqh bqh, fu fu, cfj cfj) {
		b(bqh, fu, cfj);
	}

	public final boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cou cou) {
		Set<fu> set8 = Sets.<fu>newHashSet();
		Set<fu> set9 = Sets.<fu>newHashSet();
		Set<fu> set10 = Sets.<fu>newHashSet();
		ctd ctd11 = ctd.a();
		boolean boolean12 = this.a(bqu, random, fu, set8, set9, ctd11, cou);
		if (ctd11.a <= ctd11.d && boolean12 && !set8.isEmpty()) {
			if (!cou.d.isEmpty()) {
				List<fu> list13 = Lists.<fu>newArrayList(set8);
				List<fu> list14 = Lists.<fu>newArrayList(set9);
				list13.sort(Comparator.comparingInt(gr::v));
				list14.sort(Comparator.comparingInt(gr::v));
				cou.d.forEach(cqm -> cqm.a(bqu, random, list13, list14, set10, ctd11));
			}

			dev dev13 = this.a(bqu, ctd11, set8, set10);
			cve.a(bqu, 3, dev13, ctd11.a, ctd11.b, ctd11.c);
			return true;
		} else {
			return false;
		}
	}

	private dev a(bqc bqc, ctd ctd, Set<fu> set3, Set<fu> set4) {
		List<Set<fu>> list6 = Lists.<Set<fu>>newArrayList();
		dev dev7 = new dep(ctd.d(), ctd.e(), ctd.f());
		int integer8 = 6;

		for (int integer9 = 0; integer9 < 6; integer9++) {
			list6.add(Sets.newHashSet());
		}

		fu.a a9 = new fu.a();

		for (fu fu11 : Lists.newArrayList(set4)) {
			if (ctd.b(fu11)) {
				dev7.a(fu11.u() - ctd.a, fu11.v() - ctd.b, fu11.w() - ctd.c, true, true);
			}
		}

		for (fu fu11x : Lists.newArrayList(set3)) {
			if (ctd.b(fu11x)) {
				dev7.a(fu11x.u() - ctd.a, fu11x.v() - ctd.b, fu11x.w() - ctd.c, true, true);
			}

			for (fz fz15 : fz.values()) {
				a9.a(fu11x, fz15);
				if (!set3.contains(a9)) {
					cfj cfj16 = bqc.d_(a9);
					if (cfj16.b(cfz.an)) {
						((Set)list6.get(0)).add(a9.h());
						b(bqc, a9, cfj16.a(cfz.an, Integer.valueOf(1)));
						if (ctd.b(a9)) {
							dev7.a(a9.u() - ctd.a, a9.v() - ctd.b, a9.w() - ctd.c, true, true);
						}
					}
				}
			}
		}

		for (int integer10 = 1; integer10 < 6; integer10++) {
			Set<fu> set11 = (Set<fu>)list6.get(integer10 - 1);
			Set<fu> set12 = (Set<fu>)list6.get(integer10);

			for (fu fu14 : set11) {
				if (ctd.b(fu14)) {
					dev7.a(fu14.u() - ctd.a, fu14.v() - ctd.b, fu14.w() - ctd.c, true, true);
				}

				for (fz fz18 : fz.values()) {
					a9.a(fu14, fz18);
					if (!set11.contains(a9) && !set12.contains(a9)) {
						cfj cfj19 = bqc.d_(a9);
						if (cfj19.b(cfz.an)) {
							int integer20 = (Integer)cfj19.c(cfz.an);
							if (integer20 > integer10 + 1) {
								cfj cfj21 = cfj19.a(cfz.an, Integer.valueOf(integer10 + 1));
								b(bqc, a9, cfj21);
								if (ctd.b(a9)) {
									dev7.a(a9.u() - ctd.a, a9.v() - ctd.b, a9.w() - ctd.c, true, true);
								}

								set12.add(a9.h());
							}
						}
					}
				}
			}
		}

		return dev7;
	}
}
