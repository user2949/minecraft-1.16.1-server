import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cht {
	private static final Logger b = LogManager.getLogger();
	public static final cht a = new cht();
	private static final ga[] c = ga.values();
	private final EnumSet<ga> d = EnumSet.noneOf(ga.class);
	private final int[][] e = new int[16][];
	private static final Map<bvr, cht.a> f = new IdentityHashMap();
	private static final Set<cht.a> g = Sets.<cht.a>newHashSet();

	private cht() {
	}

	public cht(le le) {
		this();
		if (le.c("Indices", 10)) {
			le le3 = le.p("Indices");

			for (int integer4 = 0; integer4 < this.e.length; integer4++) {
				String string5 = String.valueOf(integer4);
				if (le3.c(string5, 11)) {
					this.e[integer4] = le3.n(string5);
				}
			}
		}

		int integer3 = le.h("Sides");

		for (ga ga7 : ga.values()) {
			if ((integer3 & 1 << ga7.ordinal()) != 0) {
				this.d.add(ga7);
			}
		}
	}

	public void a(chj chj) {
		this.b(chj);

		for (ga ga6 : c) {
			a(chj, ga6);
		}

		bqb bqb3 = chj.x();
		g.forEach(a -> a.a(bqb3));
	}

	private static void a(chj chj, ga ga) {
		bqb bqb3 = chj.x();
		if (chj.p().d.remove(ga)) {
			Set<fz> set4 = ga.a();
			int integer5 = 0;
			int integer6 = 15;
			boolean boolean7 = set4.contains(fz.EAST);
			boolean boolean8 = set4.contains(fz.WEST);
			boolean boolean9 = set4.contains(fz.SOUTH);
			boolean boolean10 = set4.contains(fz.NORTH);
			boolean boolean11 = set4.size() == 1;
			bph bph12 = chj.g();
			int integer13 = bph12.d() + (!boolean11 || !boolean10 && !boolean9 ? (boolean8 ? 0 : 15) : 1);
			int integer14 = bph12.d() + (!boolean11 || !boolean10 && !boolean9 ? (boolean8 ? 0 : 15) : 14);
			int integer15 = bph12.e() + (!boolean11 || !boolean7 && !boolean8 ? (boolean10 ? 0 : 15) : 1);
			int integer16 = bph12.e() + (!boolean11 || !boolean7 && !boolean8 ? (boolean10 ? 0 : 15) : 14);
			fz[] arr17 = fz.values();
			fu.a a18 = new fu.a();

			for (fu fu20 : fu.b(integer13, 0, integer15, integer14, bqb3.I() - 1, integer16)) {
				cfj cfj21 = bqb3.d_(fu20);
				cfj cfj22 = cfj21;

				for (fz fz26 : arr17) {
					a18.a(fu20, fz26);
					cfj22 = a(cfj22, fz26, bqb3, fu20, a18);
				}

				bvr.a(cfj21, cfj22, bqb3, fu20, 18);
			}
		}
	}

	private static cfj a(cfj cfj, fz fz, bqc bqc, fu fu4, fu fu5) {
		return ((cht.a)f.getOrDefault(cfj.b(), cht.b.DEFAULT)).a(cfj, fz, bqc.d_(fu5), bqc, fu4, fu5);
	}

	private void b(chj chj) {
		fu.a a3 = new fu.a();
		fu.a a4 = new fu.a();
		bph bph5 = chj.g();
		bqc bqc6 = chj.x();

		for (int integer7 = 0; integer7 < 16; integer7++) {
			chk chk8 = chj.d()[integer7];
			int[] arr9 = this.e[integer7];
			this.e[integer7] = null;
			if (chk8 != null && arr9 != null && arr9.length > 0) {
				fz[] arr10 = fz.values();
				chq<cfj> chq11 = chk8.i();

				for (int integer15 : arr9) {
					int integer16 = integer15 & 15;
					int integer17 = integer15 >> 8 & 15;
					int integer18 = integer15 >> 4 & 15;
					a3.d(bph5.d() + integer16, (integer7 << 4) + integer17, bph5.e() + integer18);
					cfj cfj19 = chq11.a(integer15);
					cfj cfj20 = cfj19;

					for (fz fz24 : arr10) {
						a4.a(a3, fz24);
						if (a3.u() >> 4 == bph5.b && a3.w() >> 4 == bph5.c) {
							cfj20 = a(cfj20, fz24, bqc6, a3, a4);
						}
					}

					bvr.a(cfj19, cfj20, bqc6, a3, 18);
				}
			}
		}

		for (int integer7x = 0; integer7x < this.e.length; integer7x++) {
			if (this.e[integer7x] != null) {
				b.warn("Discarding update data for section {} for chunk ({} {})", integer7x, bph5.b, bph5.c);
			}

			this.e[integer7x] = null;
		}
	}

	public boolean a() {
		for (int[] arr5 : this.e) {
			if (arr5 != null) {
				return false;
			}
		}

		return this.d.isEmpty();
	}

	public le b() {
		le le2 = new le();
		le le3 = new le();

		for (int integer4 = 0; integer4 < this.e.length; integer4++) {
			String string5 = String.valueOf(integer4);
			if (this.e[integer4] != null && this.e[integer4].length != 0) {
				le3.a(string5, this.e[integer4]);
			}
		}

		if (!le3.isEmpty()) {
			le2.a("Indices", le3);
		}

		int integer4x = 0;

		for (ga ga6 : this.d) {
			integer4x |= 1 << ga6.ordinal();
		}

		le2.a("Sides", (byte)integer4x);
		return le2;
	}

	public interface a {
		cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6);

		default void a(bqc bqc) {
		}
	}

	static enum b implements cht.a {
		BLACKLIST(
			bvs.iO,
			bvs.cT,
			bvs.jM,
			bvs.jN,
			bvs.jO,
			bvs.jP,
			bvs.jQ,
			bvs.jR,
			bvs.jS,
			bvs.jT,
			bvs.jU,
			bvs.jV,
			bvs.jW,
			bvs.jX,
			bvs.jY,
			bvs.jZ,
			bvs.ka,
			bvs.kb,
			bvs.fo,
			bvs.fp,
			bvs.fq,
			bvs.ef,
			bvs.E,
			bvs.C,
			bvs.D,
			bvs.bZ,
			bvs.ca,
			bvs.cb,
			bvs.cc,
			bvs.cd,
			bvs.ce,
			bvs.cj,
			bvs.ck,
			bvs.cl,
			bvs.cm,
			bvs.cn,
			bvs.co
		) {
			@Override
			public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
				return cfj1;
			}
		},
		DEFAULT {
			@Override
			public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
				return cfj1.a(fz, bqc.d_(fu6), bqc, fu5, fu6);
			}
		},
		CHEST(bvs.bR, bvs.fr) {
			@Override
			public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
				if (cfj3.a(cfj1.b()) && fz.n().d() && cfj1.c(bwh.c) == cgb.SINGLE && cfj3.c(bwh.c) == cgb.SINGLE) {
					fz fz8 = cfj1.c(bwh.b);
					if (fz.n() != fz8.n() && fz8 == cfj3.c(bwh.b)) {
						cgb cgb9 = fz == fz8.g() ? cgb.LEFT : cgb.RIGHT;
						bqc.a(fu6, cfj3.a(bwh.c, cgb9.b()), 18);
						if (fz8 == fz.NORTH || fz8 == fz.EAST) {
							cdl cdl10 = bqc.c(fu5);
							cdl cdl11 = bqc.c(fu6);
							if (cdl10 instanceof cdp && cdl11 instanceof cdp) {
								cdp.a((cdp)cdl10, (cdp)cdl11);
							}
						}

						return cfj1.a(bwh.c, cgb9);
					}
				}

				return cfj1;
			}
		},
		LEAVES(true, bvs.al, bvs.aj, bvs.am, bvs.ak, bvs.ah, bvs.ai) {
			private final ThreadLocal<List<ObjectSet<fu>>> g = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));

			@Override
			public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
				cfj cfj8 = cfj1.a(fz, bqc.d_(fu6), bqc, fu5, fu6);
				if (cfj1 != cfj8) {
					int integer9 = (Integer)cfj8.c(cfz.an);
					List<ObjectSet<fu>> list10 = (List<ObjectSet<fu>>)this.g.get();
					if (list10.isEmpty()) {
						for (int integer11 = 0; integer11 < 7; integer11++) {
							list10.add(new ObjectOpenHashSet());
						}
					}

					((ObjectSet)list10.get(integer9)).add(fu5.h());
				}

				return cfj1;
			}

			@Override
			public void a(bqc bqc) {
				fu.a a3 = new fu.a();
				List<ObjectSet<fu>> list4 = (List<ObjectSet<fu>>)this.g.get();

				for (int integer5 = 2; integer5 < list4.size(); integer5++) {
					int integer6 = integer5 - 1;
					ObjectSet<fu> objectSet7 = (ObjectSet<fu>)list4.get(integer6);
					ObjectSet<fu> objectSet8 = (ObjectSet<fu>)list4.get(integer5);

					for (fu fu10 : objectSet7) {
						cfj cfj11 = bqc.d_(fu10);
						if ((Integer)cfj11.c(cfz.an) >= integer6) {
							bqc.a(fu10, cfj11.a(cfz.an, Integer.valueOf(integer6)), 18);
							if (integer5 != 7) {
								for (fz fz15 : f) {
									a3.a(fu10, fz15);
									cfj cfj16 = bqc.d_(a3);
									if (cfj16.b(cfz.an) && (Integer)cfj11.c(cfz.an) > integer5) {
										objectSet8.add(a3.h());
									}
								}
							}
						}
					}
				}

				list4.clear();
			}
		},
		STEM_BLOCK(bvs.dO, bvs.dN) {
			@Override
			public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
				if ((Integer)cfj1.c(cbp.a) == 7) {
					cbq cbq8 = ((cbp)cfj1.b()).d();
					if (cfj3.a(cbq8)) {
						return cbq8.d().n().a(byp.aq, fz);
					}
				}

				return cfj1;
			}
		};

		public static final fz[] f = fz.values();

		private b(bvr... arr) {
			this(false, arr);
		}

		private b(boolean boolean3, bvr... arr) {
			for (bvr bvr9 : arr) {
				cht.f.put(bvr9, this);
			}

			if (boolean3) {
				cht.g.add(this);
			}
		}
	}
}
