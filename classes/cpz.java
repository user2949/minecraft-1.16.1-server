import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cpz {
	private static final Logger b = LogManager.getLogger();
	public static final cqg a = new cqg();

	public static void a() {
		cjs.a();
		cms.a();
		ctr.a();
	}

	public static void a(uh uh, int integer, cpz.a a, cha cha, cva cva, fu fu, List<? super cts> list, Random random, boolean boolean9, boolean boolean10) {
		cml.g();
		cap cap11 = cap.a(random);
		cqf cqf12 = cpz.a.a(uh);
		cqd cqd13 = cqf12.a(random);
		cts cts14 = a.create(cva, cqd13, fu, cqd13.f(), cap11, cqd13.a(cva, fu, cap11));
		ctd ctd15 = cts14.g();
		int integer16 = (ctd15.d + ctd15.a) / 2;
		int integer17 = (ctd15.f + ctd15.c) / 2;
		int integer18;
		if (boolean10) {
			integer18 = fu.v() + cha.b(integer16, integer17, cio.a.WORLD_SURFACE_WG);
		} else {
			integer18 = fu.v();
		}

		int integer19 = ctd15.b + cts14.d();
		cts14.a(0, integer18 - integer19, 0);
		list.add(cts14);
		if (integer > 0) {
			int integer20 = 80;
			deg deg21 = new deg(
				(double)(integer16 - 80),
				(double)(integer18 - 80),
				(double)(integer17 - 80),
				(double)(integer16 + 80 + 1),
				(double)(integer18 + 80 + 1),
				(double)(integer17 + 80 + 1)
			);
			cpz.c c22 = new cpz.c(integer, a, cha, cva, list, random);
			c22.g.addLast(new cpz.b(cts14, new MutableObject<>(dfd.a(dfd.a(deg21), dfd.a(deg.a(ctd15)), deq.e)), integer18 + 80, 0));

			while (!c22.g.isEmpty()) {
				cpz.b b23 = (cpz.b)c22.g.removeFirst();
				c22.a(b23.a, b23.b, b23.c, b23.d, boolean9);
			}
		}
	}

	public static void a(cts cts, int integer, cpz.a a, cha cha, cva cva, List<? super cts> list, Random random) {
		a();
		cpz.c c8 = new cpz.c(integer, a, cha, cva, list, random);
		c8.g.addLast(new cpz.b(cts, new MutableObject<>(dfd.a), 0, 0));

		while (!c8.g.isEmpty()) {
			cpz.b b9 = (cpz.b)c8.g.removeFirst();
			c8.a(b9.a, b9.b, b9.c, b9.d, false);
		}
	}

	static {
		a.a(cqf.b);
	}

	public interface a {
		cts create(cva cva, cqd cqd, fu fu, int integer, cap cap, ctd ctd);
	}

	static final class b {
		private final cts a;
		private final MutableObject<dfg> b;
		private final int c;
		private final int d;

		private b(cts cts, MutableObject<dfg> mutableObject, int integer3, int integer4) {
			this.a = cts;
			this.b = mutableObject;
			this.c = integer3;
			this.d = integer4;
		}
	}

	static final class c {
		private final int a;
		private final cpz.a b;
		private final cha c;
		private final cva d;
		private final List<? super cts> e;
		private final Random f;
		private final Deque<cpz.b> g = Queues.<cpz.b>newArrayDeque();

		private c(int integer, cpz.a a, cha cha, cva cva, List<? super cts> list, Random random) {
			this.a = integer;
			this.b = a;
			this.c = cha;
			this.d = cva;
			this.e = list;
			this.f = random;
		}

		private void a(cts cts, MutableObject<dfg> mutableObject, int integer3, int integer4, boolean boolean5) {
			cqd cqd7 = cts.b();
			fu fu8 = cts.c();
			cap cap9 = cts.ap_();
			cqf.a a10 = cqd7.e();
			boolean boolean11 = a10 == cqf.a.RIGID;
			MutableObject<dfg> mutableObject12 = new MutableObject<>();
			ctd ctd13 = cts.g();
			int integer14 = ctd13.b;

			label125:
			for (cve.c c16 : cqd7.a(this.d, fu8, cap9, this.f)) {
				fz fz17 = byu.h(c16.b);
				fu fu18 = c16.a;
				fu fu19 = fu18.a(fz17);
				int integer20 = fu18.v() - integer14;
				int integer21 = -1;
				cqf cqf22 = cpz.a.a(new uh(c16.c.l("pool")));
				cqf cqf23 = cpz.a.a(cqf22.a());
				if (cqf22 != cqf.c && (cqf22.c() != 0 || cqf22 == cqf.b)) {
					boolean boolean26 = ctd13.b(fu19);
					MutableObject<dfg> mutableObject24;
					int integer25;
					if (boolean26) {
						mutableObject24 = mutableObject12;
						integer25 = integer14;
						if (mutableObject12.getValue() == null) {
							mutableObject12.setValue(dfd.a(deg.a(ctd13)));
						}
					} else {
						mutableObject24 = mutableObject;
						integer25 = integer3;
					}

					List<cqd> list27 = Lists.<cqd>newArrayList();
					if (integer4 != this.a) {
						list27.addAll(cqf22.b(this.f));
					}

					list27.addAll(cqf23.b(this.f));

					for (cqd cqd29 : list27) {
						if (cqd29 == cpw.b) {
							break;
						}

						for (cap cap31 : cap.b(this.f)) {
							List<cve.c> list32 = cqd29.a(this.d, fu.b, cap31, this.f);
							ctd ctd33 = cqd29.a(this.d, fu.b, cap31);
							int integer34;
							if (boolean5 && ctd33.e() <= 16) {
								integer34 = list32.stream().mapToInt(c -> {
									if (!ctd33.b(c.a.a(byu.h(c.b)))) {
										return 0;
									} else {
										uh uh4 = new uh(c.c.l("pool"));
										cqf cqf5 = cpz.a.a(uh4);
										cqf cqf6 = cpz.a.a(cqf5.a());
										return Math.max(cqf5.a(this.d), cqf6.a(this.d));
									}
								}).max().orElse(0);
							} else {
								integer34 = 0;
							}

							for (cve.c c36 : list32) {
								if (byu.a(c16, c36)) {
									fu fu37 = c36.a;
									fu fu38 = new fu(fu19.u() - fu37.u(), fu19.v() - fu37.v(), fu19.w() - fu37.w());
									ctd ctd39 = cqd29.a(this.d, fu38, cap31);
									int integer40 = ctd39.b;
									cqf.a a41 = cqd29.e();
									boolean boolean42 = a41 == cqf.a.RIGID;
									int integer43 = fu37.v();
									int integer44 = integer20 - integer43 + byu.h(c16.b).j();
									int integer45;
									if (boolean11 && boolean42) {
										integer45 = integer14 + integer44;
									} else {
										if (integer21 == -1) {
											integer21 = this.c.b(fu18.u(), fu18.w(), cio.a.WORLD_SURFACE_WG);
										}

										integer45 = integer21 - integer43;
									}

									int integer46 = integer45 - integer40;
									ctd ctd47 = ctd39.b(0, integer46, 0);
									fu fu48 = fu38.b(0, integer46, 0);
									if (integer34 > 0) {
										int integer49 = Math.max(integer34 + 1, ctd47.e - ctd47.b);
										ctd47.e = ctd47.b + integer49;
									}

									if (!dfd.c(mutableObject24.getValue(), dfd.a(deg.a(ctd47).h(0.25)), deq.c)) {
										mutableObject24.setValue(dfd.b(mutableObject24.getValue(), dfd.a(deg.a(ctd47)), deq.e));
										int integer49 = cts.d();
										int integer50;
										if (boolean42) {
											integer50 = integer49 - integer44;
										} else {
											integer50 = cqd29.f();
										}

										cts cts51 = this.b.create(this.d, cqd29, fu48, integer50, cap31, ctd47);
										int integer52;
										if (boolean11) {
											integer52 = integer14 + integer20;
										} else if (boolean42) {
											integer52 = integer45 + integer43;
										} else {
											if (integer21 == -1) {
												integer21 = this.c.b(fu18.u(), fu18.w(), cio.a.WORLD_SURFACE_WG);
											}

											integer52 = integer21 + integer44 / 2;
										}

										cts.a(new cpy(fu19.u(), integer52 - integer20 + integer49, fu19.w(), integer44, a41));
										cts51.a(new cpy(fu18.u(), integer52 - integer43 + integer50, fu18.w(), -integer44, a10));
										this.e.add(cts51);
										if (integer4 + 1 <= this.a) {
											this.g.addLast(new cpz.b(cts51, mutableObject24, integer25, integer4 + 1));
										}
										continue label125;
									}
								}
							}
						}
					}
				} else {
					cpz.b.warn("Empty or none existent pool: {}", c16.c.l("pool"));
				}
			}
		}
	}
}
