import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public final class cip extends cha {
	public static final Codec<cip> d = RecordCodecBuilder.create(
		instance -> instance.group(
					brh.a.fieldOf("biome_source").forGetter(cip -> cip.b),
					Codec.LONG.fieldOf("seed").stable().forGetter(cip -> cip.w),
					ciq.b.fieldOf("settings").forGetter(cip -> cip.h)
				)
				.apply(instance, instance.stable(cip::new))
	);
	private static final float[] i = v.a(new float[13824], arr -> {
		for (int integer2 = 0; integer2 < 24; integer2++) {
			for (int integer3 = 0; integer3 < 24; integer3++) {
				for (int integer4 = 0; integer4 < 24; integer4++) {
					arr[integer2 * 24 * 24 + integer3 * 24 + integer4] = (float)b(integer3 - 12, integer4 - 12, integer2 - 12);
				}
			}
		}
	});
	private static final float[] j = v.a(new float[25], arr -> {
		for (int integer2 = -2; integer2 <= 2; integer2++) {
			for (int integer3 = -2; integer3 <= 2; integer3++) {
				float float4 = 10.0F / aec.c((float)(integer2 * integer2 + integer3 * integer3) + 0.2F);
				arr[integer2 + 2 + (integer3 + 2) * 5] = float4;
			}
		}
	});
	private static final cfj k = bvs.a.n();
	private final int l;
	private final int m;
	private final int n;
	private final int o;
	private final int p;
	protected final ciy e;
	private final cwe q;
	private final cwe r;
	private final cwe s;
	private final cwh t;
	private final cwe u;
	@Nullable
	private final cwg v;
	protected final cfj f;
	protected final cfj g;
	private final long w;
	protected final ciq h;
	private final int x;

	public cip(brh brh, long long2, ciq ciq) {
		this(brh, brh, long2, ciq);
	}

	private cip(brh brh1, brh brh2, long long3, ciq ciq) {
		super(brh1, brh2, ciq.a(), long3);
		this.w = long3;
		this.h = ciq;
		cis cis7 = ciq.b();
		this.x = cis7.a();
		this.l = cis7.f() * 4;
		this.m = cis7.e() * 4;
		this.f = ciq.c();
		this.g = ciq.d();
		this.n = 16 / this.m;
		this.o = cis7.a() / this.l;
		this.p = 16 / this.m;
		this.e = new ciy(long3);
		this.q = new cwe(this.e, IntStream.rangeClosed(-15, 0));
		this.r = new cwe(this.e, IntStream.rangeClosed(-15, 0));
		this.s = new cwe(this.e, IntStream.rangeClosed(-7, 0));
		this.t = (cwh)(cis7.i() ? new cwf(this.e, IntStream.rangeClosed(-3, 0)) : new cwe(this.e, IntStream.rangeClosed(-3, 0)));
		this.e.a(2620);
		this.u = new cwe(this.e, IntStream.rangeClosed(-15, 0));
		if (cis7.k()) {
			ciy ciy8 = new ciy(long3);
			ciy8.a(17292);
			this.v = new cwg(ciy8);
		} else {
			this.v = null;
		}
	}

	@Override
	protected Codec<? extends cha> a() {
		return d;
	}

	public boolean a(long long1, ciq.a a) {
		return this.w == long1 && this.h.a(a);
	}

	private double a(int integer1, int integer2, int integer3, double double4, double double5, double double6, double double7) {
		double double13 = 0.0;
		double double15 = 0.0;
		double double17 = 0.0;
		boolean boolean19 = true;
		double double20 = 1.0;

		for (int integer22 = 0; integer22 < 16; integer22++) {
			double double23 = cwe.a((double)integer1 * double4 * double20);
			double double25 = cwe.a((double)integer2 * double5 * double20);
			double double27 = cwe.a((double)integer3 * double4 * double20);
			double double29 = double5 * double20;
			cwc cwc31 = this.q.a(integer22);
			if (cwc31 != null) {
				double13 += cwc31.a(double23, double25, double27, double29, (double)integer2 * double29) / double20;
			}

			cwc cwc32 = this.r.a(integer22);
			if (cwc32 != null) {
				double15 += cwc32.a(double23, double25, double27, double29, (double)integer2 * double29) / double20;
			}

			if (integer22 < 8) {
				cwc cwc33 = this.s.a(integer22);
				if (cwc33 != null) {
					double17 += cwc33.a(
							cwe.a((double)integer1 * double6 * double20),
							cwe.a((double)integer2 * double7 * double20),
							cwe.a((double)integer3 * double6 * double20),
							double7 * double20,
							(double)integer2 * double7 * double20
						)
						/ double20;
				}
			}

			double20 /= 2.0;
		}

		return aec.b(double13 / 512.0, double15 / 512.0, (double17 / 10.0 + 1.0) / 2.0);
	}

	private double[] b(int integer1, int integer2) {
		double[] arr4 = new double[this.o + 1];
		this.a(arr4, integer1, integer2);
		return arr4;
	}

	private void a(double[] arr, int integer2, int integer3) {
		cis cis9 = this.h.b();
		double double5;
		double double7;
		if (this.v != null) {
			double5 = (double)(buh.a(this.v, integer2, integer3) - 8.0F);
			if (double5 > 0.0) {
				double7 = 0.25;
			} else {
				double7 = 1.0;
			}
		} else {
			float float10 = 0.0F;
			float float11 = 0.0F;
			float float12 = 0.0F;
			int integer13 = 2;
			int integer14 = this.f();
			float float15 = this.b.b(integer2, integer14, integer3).k();

			for (int integer16 = -2; integer16 <= 2; integer16++) {
				for (int integer17 = -2; integer17 <= 2; integer17++) {
					bre bre18 = this.b.b(integer2 + integer16, integer14, integer3 + integer17);
					float float19 = bre18.k();
					float float20 = bre18.o();
					float float21;
					float float22;
					if (cis9.l() && float19 > 0.0F) {
						float21 = 1.0F + float19 * 2.0F;
						float22 = 1.0F + float20 * 4.0F;
					} else {
						float21 = float19;
						float22 = float20;
					}

					float float23 = float19 > float15 ? 0.5F : 1.0F;
					float float24 = float23 * j[integer16 + 2 + (integer17 + 2) * 5] / (float21 + 2.0F);
					float10 += float22 * float24;
					float11 += float21 * float24;
					float12 += float24;
				}
			}

			float float16 = float11 / float12;
			float float17 = float10 / float12;
			double double18 = (double)(float16 * 0.5F - 0.125F);
			double double20 = (double)(float17 * 0.9F + 0.1F);
			double5 = double18 * 0.265625;
			double7 = 96.0 / double20;
		}

		double double10 = 684.412 * cis9.b().a();
		double double12 = 684.412 * cis9.b().b();
		double double14 = double10 / cis9.b().c();
		double double16 = double12 / cis9.b().d();
		double double18 = (double)cis9.c().a();
		double double20 = (double)cis9.c().b();
		double double22 = (double)cis9.c().c();
		double double24 = (double)cis9.d().a();
		double double26 = (double)cis9.d().b();
		double double28 = (double)cis9.d().c();
		double double30 = cis9.j() ? this.c(integer2, integer3) : 0.0;
		double double32 = cis9.g();
		double double34 = cis9.h();

		for (int integer36 = 0; integer36 <= this.o; integer36++) {
			double double37 = this.a(integer2, integer36, integer3, double10, double12, double14, double16);
			double double39 = 1.0 - (double)integer36 * 2.0 / (double)this.o + double30;
			double double41 = double39 * double32 + double34;
			double double43 = (double41 + double5) * double7;
			if (double43 > 0.0) {
				double37 += double43 * 4.0;
			} else {
				double37 += double43;
			}

			if (double20 > 0.0) {
				double double45 = ((double)(this.o - integer36) - double22) / double20;
				double37 = aec.b(double18, double37, double45);
			}

			if (double26 > 0.0) {
				double double45 = ((double)integer36 - double28) / double26;
				double37 = aec.b(double24, double37, double45);
			}

			arr[integer36] = double37;
		}
	}

	private double c(int integer1, int integer2) {
		double double4 = this.u.a((double)(integer1 * 200), 10.0, (double)(integer2 * 200), 1.0, 0.0, true);
		double double6;
		if (double4 < 0.0) {
			double6 = -double4 * 0.3;
		} else {
			double6 = double4;
		}

		double double8 = double6 * 24.575625 - 2.0;
		return double8 < 0.0 ? double8 * 0.009486607142857142 : Math.min(double8, 1.0) * 0.006640625;
	}

	@Override
	public int a(int integer1, int integer2, cio.a a) {
		return this.a(integer1, integer2, null, a.e());
	}

	@Override
	public bpg a(int integer1, int integer2) {
		cfj[] arr4 = new cfj[this.o * this.l];
		this.a(integer1, integer2, arr4, null);
		return new bqk(arr4);
	}

	private int a(int integer1, int integer2, @Nullable cfj[] arr, @Nullable Predicate<cfj> predicate) {
		int integer6 = Math.floorDiv(integer1, this.m);
		int integer7 = Math.floorDiv(integer2, this.m);
		int integer8 = Math.floorMod(integer1, this.m);
		int integer9 = Math.floorMod(integer2, this.m);
		double double10 = (double)integer8 / (double)this.m;
		double double12 = (double)integer9 / (double)this.m;
		double[][] arr14 = new double[][]{
			this.b(integer6, integer7), this.b(integer6, integer7 + 1), this.b(integer6 + 1, integer7), this.b(integer6 + 1, integer7 + 1)
		};

		for (int integer15 = this.o - 1; integer15 >= 0; integer15--) {
			double double16 = arr14[0][integer15];
			double double18 = arr14[1][integer15];
			double double20 = arr14[2][integer15];
			double double22 = arr14[3][integer15];
			double double24 = arr14[0][integer15 + 1];
			double double26 = arr14[1][integer15 + 1];
			double double28 = arr14[2][integer15 + 1];
			double double30 = arr14[3][integer15 + 1];

			for (int integer32 = this.l - 1; integer32 >= 0; integer32--) {
				double double33 = (double)integer32 / (double)this.l;
				double double35 = aec.a(double33, double10, double12, double16, double24, double20, double28, double18, double26, double22, double30);
				int integer37 = integer15 * this.l + integer32;
				cfj cfj38 = this.a(double35, integer37);
				if (arr != null) {
					arr[integer37] = cfj38;
				}

				if (predicate != null && predicate.test(cfj38)) {
					return integer37 + 1;
				}
			}
		}

		return 0;
	}

	protected cfj a(double double1, int integer) {
		cfj cfj5;
		if (double1 > 0.0) {
			cfj5 = this.f;
		} else if (integer < this.f()) {
			cfj5 = this.g;
		} else {
			cfj5 = k;
		}

		return cfj5;
	}

	@Override
	public void a(zj zj, cgy cgy) {
		bph bph4 = cgy.g();
		int integer5 = bph4.b;
		int integer6 = bph4.c;
		ciy ciy7 = new ciy();
		ciy7.a(integer5, integer6);
		bph bph8 = cgy.g();
		int integer9 = bph8.d();
		int integer10 = bph8.e();
		double double11 = 0.0625;
		fu.a a13 = new fu.a();

		for (int integer14 = 0; integer14 < 16; integer14++) {
			for (int integer15 = 0; integer15 < 16; integer15++) {
				int integer16 = integer9 + integer14;
				int integer17 = integer10 + integer15;
				int integer18 = cgy.a(cio.a.WORLD_SURFACE_WG, integer14, integer15) + 1;
				double double19 = this.t.a((double)integer16 * 0.0625, (double)integer17 * 0.0625, 0.0625, (double)integer14 * 0.0625) * 15.0;
				zj.v(a13.d(integer9 + integer14, integer18, integer10 + integer15))
					.a(ciy7, cgy, integer16, integer17, integer18, double19, this.f, this.g, this.f(), zj.B());
			}
		}

		this.a(cgy, ciy7);
	}

	private void a(cgy cgy, Random random) {
		fu.a a4 = new fu.a();
		int integer5 = cgy.g().d();
		int integer6 = cgy.g().e();
		int integer7 = this.h.f();
		int integer8 = this.x - 1 - this.h.e();
		int integer9 = 5;
		boolean boolean10 = integer8 + 4 >= 0 && integer8 < this.x;
		boolean boolean11 = integer7 + 4 >= 0 && integer7 < this.x;
		if (boolean10 || boolean11) {
			for (fu fu13 : fu.b(integer5, 0, integer6, integer5 + 15, 0, integer6 + 15)) {
				if (boolean10) {
					for (int integer14 = 0; integer14 < 5; integer14++) {
						if (integer14 <= random.nextInt(5)) {
							cgy.a(a4.d(fu13.u(), integer8 - integer14, fu13.w()), bvs.z.n(), false);
						}
					}
				}

				if (boolean11) {
					for (int integer14x = 4; integer14x >= 0; integer14x--) {
						if (integer14x <= random.nextInt(5)) {
							cgy.a(a4.d(fu13.u(), integer7 + integer14x, fu13.w()), bvs.z.n(), false);
						}
					}
				}
			}
		}
	}

	@Override
	public void b(bqc bqc, bqq bqq, cgy cgy) {
		ObjectList<cty> objectList5 = new ObjectArrayList<>(10);
		ObjectList<cpy> objectList6 = new ObjectArrayList<>(32);
		bph bph7 = cgy.g();
		int integer8 = bph7.b;
		int integer9 = bph7.c;
		int integer10 = integer8 << 4;
		int integer11 = integer9 << 4;

		for (cml<?> cml13 : cml.t) {
			bqq.a(go.a(bph7, 0), cml13).forEach(ctz -> {
				for (cty cty8 : ctz.d()) {
					if (cty8.a(bph7, 12)) {
						if (cty8 instanceof cts) {
							cts cts9 = (cts)cty8;
							cqf.a a10 = cts9.b().e();
							if (a10 == cqf.a.RIGID) {
								objectList5.add(cts9);
							}

							for (cpy cpy12 : cts9.e()) {
								int integer13 = cpy12.a();
								int integer14 = cpy12.c();
								if (integer13 > integer10 - 12 && integer14 > integer11 - 12 && integer13 < integer10 + 15 + 12 && integer14 < integer11 + 15 + 12) {
									objectList6.add(cpy12);
								}
							}
						} else {
							objectList5.add(cty8);
						}
					}
				}
			});
		}

		double[][][] arr12 = new double[2][this.p + 1][this.o + 1];

		for (int integer13 = 0; integer13 < this.p + 1; integer13++) {
			arr12[0][integer13] = new double[this.o + 1];
			this.a(arr12[0][integer13], integer8 * this.n, integer9 * this.p + integer13);
			arr12[1][integer13] = new double[this.o + 1];
		}

		chr chr13 = (chr)cgy;
		cio cio14 = chr13.a(cio.a.OCEAN_FLOOR_WG);
		cio cio15 = chr13.a(cio.a.WORLD_SURFACE_WG);
		fu.a a16 = new fu.a();
		ObjectListIterator<cty> objectListIterator17 = objectList5.iterator();
		ObjectListIterator<cpy> objectListIterator18 = objectList6.iterator();

		for (int integer19 = 0; integer19 < this.n; integer19++) {
			for (int integer20 = 0; integer20 < this.p + 1; integer20++) {
				this.a(arr12[1][integer20], integer8 * this.n + integer19 + 1, integer9 * this.p + integer20);
			}

			for (int integer20 = 0; integer20 < this.p; integer20++) {
				chk chk21 = chr13.a(15);
				chk21.a();

				for (int integer22 = this.o - 1; integer22 >= 0; integer22--) {
					double double23 = arr12[0][integer20][integer22];
					double double25 = arr12[0][integer20 + 1][integer22];
					double double27 = arr12[1][integer20][integer22];
					double double29 = arr12[1][integer20 + 1][integer22];
					double double31 = arr12[0][integer20][integer22 + 1];
					double double33 = arr12[0][integer20 + 1][integer22 + 1];
					double double35 = arr12[1][integer20][integer22 + 1];
					double double37 = arr12[1][integer20 + 1][integer22 + 1];

					for (int integer39 = this.l - 1; integer39 >= 0; integer39--) {
						int integer40 = integer22 * this.l + integer39;
						int integer41 = integer40 & 15;
						int integer42 = integer40 >> 4;
						if (chk21.g() >> 4 != integer42) {
							chk21.b();
							chk21 = chr13.a(integer42);
							chk21.a();
						}

						double double43 = (double)integer39 / (double)this.l;
						double double45 = aec.d(double43, double23, double31);
						double double47 = aec.d(double43, double27, double35);
						double double49 = aec.d(double43, double25, double33);
						double double51 = aec.d(double43, double29, double37);

						for (int integer53 = 0; integer53 < this.m; integer53++) {
							int integer54 = integer10 + integer19 * this.m + integer53;
							int integer55 = integer54 & 15;
							double double56 = (double)integer53 / (double)this.m;
							double double58 = aec.d(double56, double45, double47);
							double double60 = aec.d(double56, double49, double51);

							for (int integer62 = 0; integer62 < this.m; integer62++) {
								int integer63 = integer11 + integer20 * this.m + integer62;
								int integer64 = integer63 & 15;
								double double65 = (double)integer62 / (double)this.m;
								double double67 = aec.d(double65, double58, double60);
								double double69 = aec.a(double67 / 200.0, -1.0, 1.0);
								double69 = double69 / 2.0 - double69 * double69 * double69 / 24.0;

								while (objectListIterator17.hasNext()) {
									cty cty71 = (cty)objectListIterator17.next();
									ctd ctd72 = cty71.g();
									int integer73 = Math.max(0, Math.max(ctd72.a - integer54, integer54 - ctd72.d));
									int integer74 = integer40 - (ctd72.b + (cty71 instanceof cts ? ((cts)cty71).d() : 0));
									int integer75 = Math.max(0, Math.max(ctd72.c - integer63, integer63 - ctd72.f));
									double69 += a(integer73, integer74, integer75) * 0.8;
								}

								objectListIterator17.back(objectList5.size());

								while (objectListIterator18.hasNext()) {
									cpy cpy71 = (cpy)objectListIterator18.next();
									int integer72 = integer54 - cpy71.a();
									int integer73 = integer40 - cpy71.b();
									int integer74 = integer63 - cpy71.c();
									double69 += a(integer72, integer73, integer74) * 0.4;
								}

								objectListIterator18.back(objectList6.size());
								cfj cfj71 = this.a(double69, integer40);
								if (cfj71 != k) {
									if (cfj71.f() != 0) {
										a16.d(integer54, integer40, integer63);
										chr13.j(a16);
									}

									chk21.a(integer55, integer41, integer64, cfj71, false);
									cio14.a(integer55, integer40, integer64, cfj71);
									cio15.a(integer55, integer40, integer64, cfj71);
								}
							}
						}
					}
				}

				chk21.b();
			}

			double[][] arr20 = arr12[0];
			arr12[0] = arr12[1];
			arr12[1] = arr20;
		}
	}

	private static double a(int integer1, int integer2, int integer3) {
		int integer4 = integer1 + 12;
		int integer5 = integer2 + 12;
		int integer6 = integer3 + 12;
		if (integer4 < 0 || integer4 >= 24) {
			return 0.0;
		} else if (integer5 < 0 || integer5 >= 24) {
			return 0.0;
		} else {
			return integer6 >= 0 && integer6 < 24 ? (double)i[integer6 * 24 * 24 + integer4 * 24 + integer5] : 0.0;
		}
	}

	private static double b(int integer1, int integer2, int integer3) {
		double double4 = (double)(integer1 * integer1 + integer3 * integer3);
		double double6 = (double)integer2 + 0.5;
		double double8 = double6 * double6;
		double double10 = Math.pow(Math.E, -(double8 / 16.0 + double4 / 16.0));
		double double12 = -double6 * aec.i(double8 / 2.0 + double4 / 2.0) / 2.0;
		return double12 * double10;
	}

	@Override
	public int e() {
		return this.x;
	}

	@Override
	public int f() {
		return this.h.g();
	}

	@Override
	public List<bre.g> a(bre bre, bqq bqq, apa apa, fu fu) {
		if (bqq.a(fu, true, cml.j).e()) {
			if (apa == apa.MONSTER) {
				return cml.j.c();
			}

			if (apa == apa.CREATURE) {
				return cml.j.j();
			}
		}

		if (apa == apa.MONSTER) {
			if (bqq.a(fu, false, cml.b).e()) {
				return cml.b.c();
			}

			if (bqq.a(fu, false, cml.l).e()) {
				return cml.l.c();
			}

			if (bqq.a(fu, true, cml.n).e()) {
				return cml.n.c();
			}
		}

		return super.a(bre, bqq, apa, fu);
	}

	@Override
	public void a(zj zj) {
		if (!this.h.h()) {
			int integer3 = zj.a();
			int integer4 = zj.b();
			bre bre5 = zj.v(new bph(integer3, integer4).l());
			ciy ciy6 = new ciy();
			ciy6.a(zj.B(), integer3 << 4, integer4 << 4);
			bqj.a(zj, bre5, integer3, integer4, ciy6);
		}
	}
}
