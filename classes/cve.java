import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class cve {
	private final List<cve.a> a = Lists.<cve.a>newArrayList();
	private final List<cve.d> b = Lists.<cve.d>newArrayList();
	private fu c = fu.b;
	private String d = "?";

	public fu a() {
		return this.c;
	}

	public void a(String string) {
		this.d = string;
	}

	public String b() {
		return this.d;
	}

	public void a(bqb bqb, fu fu2, fu fu3, boolean boolean4, @Nullable bvr bvr) {
		if (fu3.u() >= 1 && fu3.v() >= 1 && fu3.w() >= 1) {
			fu fu7 = fu2.a(fu3).b(-1, -1, -1);
			List<cve.c> list8 = Lists.<cve.c>newArrayList();
			List<cve.c> list9 = Lists.<cve.c>newArrayList();
			List<cve.c> list10 = Lists.<cve.c>newArrayList();
			fu fu11 = new fu(Math.min(fu2.u(), fu7.u()), Math.min(fu2.v(), fu7.v()), Math.min(fu2.w(), fu7.w()));
			fu fu12 = new fu(Math.max(fu2.u(), fu7.u()), Math.max(fu2.v(), fu7.v()), Math.max(fu2.w(), fu7.w()));
			this.c = fu3;

			for (fu fu14 : fu.a(fu11, fu12)) {
				fu fu15 = fu14.b(fu11);
				cfj cfj16 = bqb.d_(fu14);
				if (bvr == null || bvr != cfj16.b()) {
					cdl cdl17 = bqb.c(fu14);
					cve.c c18;
					if (cdl17 != null) {
						le le19 = cdl17.a(new le());
						le19.r("x");
						le19.r("y");
						le19.r("z");
						c18 = new cve.c(fu15, cfj16, le19.g());
					} else {
						c18 = new cve.c(fu15, cfj16, null);
					}

					a(c18, list8, list9, list10);
				}
			}

			List<cve.c> list13 = a(list8, list9, list10);
			this.a.clear();
			this.a.add(new cve.a(list13));
			if (boolean4) {
				this.a(bqb, fu11, fu12.b(1, 1, 1));
			} else {
				this.b.clear();
			}
		}
	}

	private static void a(cve.c c, List<cve.c> list2, List<cve.c> list3, List<cve.c> list4) {
		if (c.c != null) {
			list3.add(c);
		} else if (!c.b.b().o() && c.b.r(bpp.INSTANCE, fu.b)) {
			list2.add(c);
		} else {
			list4.add(c);
		}
	}

	private static List<cve.c> a(List<cve.c> list1, List<cve.c> list2, List<cve.c> list3) {
		Comparator<cve.c> comparator4 = Comparator.comparingInt(c -> c.a.v()).thenComparingInt(c -> c.a.u()).thenComparingInt(c -> c.a.w());
		list1.sort(comparator4);
		list3.sort(comparator4);
		list2.sort(comparator4);
		List<cve.c> list5 = Lists.<cve.c>newArrayList();
		list5.addAll(list1);
		list5.addAll(list3);
		list5.addAll(list2);
		return list5;
	}

	private void a(bqb bqb, fu fu2, fu fu3) {
		List<aom> list5 = bqb.a(aom.class, new deg(fu2, fu3), aom -> !(aom instanceof bec));
		this.b.clear();

		for (aom aom7 : list5) {
			dem dem8 = new dem(aom7.cC() - (double)fu2.u(), aom7.cD() - (double)fu2.v(), aom7.cG() - (double)fu2.w());
			le le9 = new le();
			aom7.d(le9);
			fu fu10;
			if (aom7 instanceof bbd) {
				fu10 = ((bbd)aom7).n().b(fu2);
			} else {
				fu10 = new fu(dem8);
			}

			this.b.add(new cve.d(dem8, fu10, le9.g()));
		}
	}

	public List<cve.c> a(fu fu, cvb cvb, bvr bvr) {
		return this.a(fu, cvb, bvr, true);
	}

	public List<cve.c> a(fu fu, cvb cvb, bvr bvr, boolean boolean4) {
		List<cve.c> list6 = Lists.<cve.c>newArrayList();
		ctd ctd7 = cvb.h();
		if (this.a.isEmpty()) {
			return Collections.emptyList();
		} else {
			for (cve.c c9 : cvb.a(this.a, fu).a(bvr)) {
				fu fu10 = boolean4 ? a(cvb, c9.a).a(fu) : c9.a;
				if (ctd7 == null || ctd7.b(fu10)) {
					list6.add(new cve.c(fu10, c9.b.a(cvb.d()), c9.c));
				}
			}

			return list6;
		}
	}

	public fu a(cvb cvb1, fu fu2, cvb cvb3, fu fu4) {
		fu fu6 = a(cvb1, fu2);
		fu fu7 = a(cvb3, fu4);
		return fu6.b(fu7);
	}

	public static fu a(cvb cvb, fu fu) {
		return a(fu, cvb.c(), cvb.d(), cvb.e());
	}

	public void a(bqc bqc, fu fu, cvb cvb, Random random) {
		cvb.k();
		this.b(bqc, fu, cvb, random);
	}

	public void b(bqc bqc, fu fu, cvb cvb, Random random) {
		this.a(bqc, fu, fu, cvb, random, 2);
	}

	public boolean a(bqc bqc, fu fu2, fu fu3, cvb cvb, Random random, int integer) {
		if (this.a.isEmpty()) {
			return false;
		} else {
			List<cve.c> list8 = cvb.a(this.a, fu2).a();
			if ((!list8.isEmpty() || !cvb.g() && !this.b.isEmpty()) && this.c.u() >= 1 && this.c.v() >= 1 && this.c.w() >= 1) {
				ctd ctd9 = cvb.h();
				List<fu> list10 = Lists.<fu>newArrayListWithCapacity(cvb.l() ? list8.size() : 0);
				List<Pair<fu, le>> list11 = Lists.<Pair<fu, le>>newArrayListWithCapacity(list8.size());
				int integer12 = Integer.MAX_VALUE;
				int integer13 = Integer.MAX_VALUE;
				int integer14 = Integer.MAX_VALUE;
				int integer15 = Integer.MIN_VALUE;
				int integer16 = Integer.MIN_VALUE;
				int integer17 = Integer.MIN_VALUE;

				for (cve.c c20 : a(bqc, fu2, fu3, cvb, list8)) {
					fu fu21 = c20.a;
					if (ctd9 == null || ctd9.b(fu21)) {
						cxa cxa22 = cvb.l() ? bqc.b(fu21) : null;
						cfj cfj23 = c20.b.a(cvb.c()).a(cvb.d());
						if (c20.c != null) {
							cdl cdl24 = bqc.c(fu21);
							amx.a(cdl24);
							bqc.a(fu21, bvs.go.n(), 20);
						}

						if (bqc.a(fu21, cfj23, integer)) {
							integer12 = Math.min(integer12, fu21.u());
							integer13 = Math.min(integer13, fu21.v());
							integer14 = Math.min(integer14, fu21.w());
							integer15 = Math.max(integer15, fu21.u());
							integer16 = Math.max(integer16, fu21.v());
							integer17 = Math.max(integer17, fu21.w());
							list11.add(Pair.of(fu21, c20.c));
							if (c20.c != null) {
								cdl cdl24 = bqc.c(fu21);
								if (cdl24 != null) {
									c20.c.b("x", fu21.u());
									c20.c.b("y", fu21.v());
									c20.c.b("z", fu21.w());
									if (cdl24 instanceof cef) {
										c20.c.a("LootTableSeed", random.nextLong());
									}

									cdl24.a(c20.b, c20.c);
									cdl24.a(cvb.c());
									cdl24.a(cvb.d());
								}
							}

							if (cxa22 != null && cfj23.b() instanceof bzf) {
								((bzf)cfj23.b()).a(bqc, fu21, cfj23, cxa22);
								if (!cxa22.b()) {
									list10.add(fu21);
								}
							}
						}
					}
				}

				boolean boolean19 = true;
				fz[] arr20 = new fz[]{fz.UP, fz.NORTH, fz.EAST, fz.SOUTH, fz.WEST};

				while (boolean19 && !list10.isEmpty()) {
					boolean19 = false;
					Iterator<fu> iterator21 = list10.iterator();

					while (iterator21.hasNext()) {
						fu fu22 = (fu)iterator21.next();
						fu fu23 = fu22;
						cxa cxa24 = bqc.b(fu22);

						for (int integer25 = 0; integer25 < arr20.length && !cxa24.b(); integer25++) {
							fu fu26 = fu23.a(arr20[integer25]);
							cxa cxa27 = bqc.b(fu26);
							if (cxa27.a(bqc, fu26) > cxa24.a(bqc, fu23) || cxa27.b() && !cxa24.b()) {
								cxa24 = cxa27;
								fu23 = fu26;
							}
						}

						if (cxa24.b()) {
							cfj cfj25 = bqc.d_(fu22);
							bvr bvr26 = cfj25.b();
							if (bvr26 instanceof bzf) {
								((bzf)bvr26).a(bqc, fu22, cfj25, cxa24);
								boolean19 = true;
								iterator21.remove();
							}
						}
					}
				}

				if (integer12 <= integer15) {
					if (!cvb.i()) {
						dev dev21 = new dep(integer15 - integer12 + 1, integer16 - integer13 + 1, integer17 - integer14 + 1);
						int integer22 = integer12;
						int integer23 = integer13;
						int integer24 = integer14;

						for (Pair<fu, le> pair26 : list11) {
							fu fu27 = pair26.getFirst();
							dev21.a(fu27.u() - integer22, fu27.v() - integer23, fu27.w() - integer24, true, true);
						}

						a(bqc, integer, dev21, integer22, integer23, integer24);
					}

					for (Pair<fu, le> pair22 : list11) {
						fu fu23 = pair22.getFirst();
						if (!cvb.i()) {
							cfj cfj24 = bqc.d_(fu23);
							cfj cfj25 = bvr.b(cfj24, bqc, fu23);
							if (cfj24 != cfj25) {
								bqc.a(fu23, cfj25, integer & -2 | 16);
							}

							bqc.a(fu23, cfj25.b());
						}

						if (pair22.getSecond() != null) {
							cdl cdl24 = bqc.c(fu23);
							if (cdl24 != null) {
								cdl24.Z_();
							}
						}
					}
				}

				if (!cvb.g()) {
					this.a(bqc, fu2, cvb.c(), cvb.d(), cvb.e(), ctd9, cvb.m());
				}

				return true;
			} else {
				return false;
			}
		}
	}

	public static void a(bqc bqc, int integer2, dev dev, int integer4, int integer5, int integer6) {
		dev.a((fz, integer7, integer8, integer9) -> {
			fu fu10 = new fu(integer4 + integer7, integer5 + integer8, integer6 + integer9);
			fu fu11 = fu10.a(fz);
			cfj cfj12 = bqc.d_(fu10);
			cfj cfj13 = bqc.d_(fu11);
			cfj cfj14 = cfj12.a(fz, cfj13, bqc, fu10, fu11);
			if (cfj12 != cfj14) {
				bqc.a(fu10, cfj14, integer2 & -2);
			}

			cfj cfj15 = cfj13.a(fz.f(), cfj14, bqc, fu11, fu10);
			if (cfj13 != cfj15) {
				bqc.a(fu11, cfj15, integer2 & -2);
			}
		});
	}

	public static List<cve.c> a(bqc bqc, fu fu2, fu fu3, cvb cvb, List<cve.c> list) {
		List<cve.c> list6 = Lists.<cve.c>newArrayList();

		for (cve.c c8 : list) {
			fu fu9 = a(cvb, c8.a).a(fu2);
			cve.c c10 = new cve.c(fu9, c8.b, c8.c != null ? c8.c.g() : null);
			Iterator<cvc> iterator11 = cvb.j().iterator();

			while (c10 != null && iterator11.hasNext()) {
				c10 = ((cvc)iterator11.next()).a(bqc, fu2, fu3, c8, c10, cvb);
			}

			if (c10 != null) {
				list6.add(c10);
			}
		}

		return list6;
	}

	private void a(bqc bqc, fu fu2, bzj bzj, cap cap, fu fu5, @Nullable ctd ctd, boolean boolean7) {
		for (cve.d d10 : this.b) {
			fu fu11 = a(d10.b, bzj, cap, fu5).a(fu2);
			if (ctd == null || ctd.b(fu11)) {
				le le12 = d10.c.g();
				dem dem13 = a(d10.a, bzj, cap, fu5);
				dem dem14 = dem13.b((double)fu2.u(), (double)fu2.v(), (double)fu2.w());
				lk lk15 = new lk();
				lk15.add(lf.a(dem14.b));
				lk15.add(lf.a(dem14.c));
				lk15.add(lf.a(dem14.d));
				le12.a("Pos", lk15);
				le12.r("UUID");
				a(bqc, le12).ifPresent(aom -> {
					float float8 = aom.a(bzj);
					float8 += aom.p - aom.a(cap);
					aom.b(dem14.b, dem14.c, dem14.d, float8, aom.q);
					if (boolean7 && aom instanceof aoz) {
						((aoz)aom).a(bqc, bqc.d(new fu(dem14)), apb.STRUCTURE, null, le12);
					}

					bqc.c(aom);
				});
			}
		}
	}

	private static Optional<aom> a(bqc bqc, le le) {
		try {
			return aoq.a(le, bqc.n());
		} catch (Exception var3) {
			return Optional.empty();
		}
	}

	public fu a(cap cap) {
		switch (cap) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				return new fu(this.c.w(), this.c.v(), this.c.u());
			default:
				return this.c;
		}
	}

	public static fu a(fu fu1, bzj bzj, cap cap, fu fu4) {
		int integer5 = fu1.u();
		int integer6 = fu1.v();
		int integer7 = fu1.w();
		boolean boolean8 = true;
		switch (bzj) {
			case LEFT_RIGHT:
				integer7 = -integer7;
				break;
			case FRONT_BACK:
				integer5 = -integer5;
				break;
			default:
				boolean8 = false;
		}

		int integer9 = fu4.u();
		int integer10 = fu4.w();
		switch (cap) {
			case COUNTERCLOCKWISE_90:
				return new fu(integer9 - integer10 + integer7, integer6, integer9 + integer10 - integer5);
			case CLOCKWISE_90:
				return new fu(integer9 + integer10 - integer7, integer6, integer10 - integer9 + integer5);
			case CLOCKWISE_180:
				return new fu(integer9 + integer9 - integer5, integer6, integer10 + integer10 - integer7);
			default:
				return boolean8 ? new fu(integer5, integer6, integer7) : fu1;
		}
	}

	public static dem a(dem dem, bzj bzj, cap cap, fu fu) {
		double double5 = dem.b;
		double double7 = dem.c;
		double double9 = dem.d;
		boolean boolean11 = true;
		switch (bzj) {
			case LEFT_RIGHT:
				double9 = 1.0 - double9;
				break;
			case FRONT_BACK:
				double5 = 1.0 - double5;
				break;
			default:
				boolean11 = false;
		}

		int integer12 = fu.u();
		int integer13 = fu.w();
		switch (cap) {
			case COUNTERCLOCKWISE_90:
				return new dem((double)(integer12 - integer13) + double9, double7, (double)(integer12 + integer13 + 1) - double5);
			case CLOCKWISE_90:
				return new dem((double)(integer12 + integer13 + 1) - double9, double7, (double)(integer13 - integer12) + double5);
			case CLOCKWISE_180:
				return new dem((double)(integer12 + integer12 + 1) - double5, double7, (double)(integer13 + integer13 + 1) - double9);
			default:
				return boolean11 ? new dem(double5, double7, double9) : dem;
		}
	}

	public fu a(fu fu, bzj bzj, cap cap) {
		return a(fu, bzj, cap, this.a().u(), this.a().w());
	}

	public static fu a(fu fu, bzj bzj, cap cap, int integer4, int integer5) {
		integer4--;
		integer5--;
		int integer6 = bzj == bzj.FRONT_BACK ? integer4 : 0;
		int integer7 = bzj == bzj.LEFT_RIGHT ? integer5 : 0;
		fu fu8 = fu;
		switch (cap) {
			case COUNTERCLOCKWISE_90:
				fu8 = fu.b(integer7, 0, integer4 - integer6);
				break;
			case CLOCKWISE_90:
				fu8 = fu.b(integer5 - integer7, 0, integer6);
				break;
			case CLOCKWISE_180:
				fu8 = fu.b(integer4 - integer6, 0, integer5 - integer7);
				break;
			case NONE:
				fu8 = fu.b(integer6, 0, integer7);
		}

		return fu8;
	}

	public ctd b(cvb cvb, fu fu) {
		return this.a(fu, cvb.d(), cvb.e(), cvb.c());
	}

	public ctd a(fu fu1, cap cap, fu fu3, bzj bzj) {
		fu fu6 = this.a(cap);
		int integer7 = fu3.u();
		int integer8 = fu3.w();
		int integer9 = fu6.u() - 1;
		int integer10 = fu6.v() - 1;
		int integer11 = fu6.w() - 1;
		ctd ctd12 = new ctd(0, 0, 0, 0, 0, 0);
		switch (cap) {
			case COUNTERCLOCKWISE_90:
				ctd12 = new ctd(integer7 - integer8, 0, integer7 + integer8 - integer11, integer7 - integer8 + integer9, integer10, integer7 + integer8);
				break;
			case CLOCKWISE_90:
				ctd12 = new ctd(integer7 + integer8 - integer9, 0, integer8 - integer7, integer7 + integer8, integer10, integer8 - integer7 + integer11);
				break;
			case CLOCKWISE_180:
				ctd12 = new ctd(integer7 + integer7 - integer9, 0, integer8 + integer8 - integer11, integer7 + integer7, integer10, integer8 + integer8);
				break;
			case NONE:
				ctd12 = new ctd(0, 0, 0, integer9, integer10, integer11);
		}

		switch (bzj) {
			case LEFT_RIGHT:
				this.a(cap, integer11, integer9, ctd12, fz.NORTH, fz.SOUTH);
				break;
			case FRONT_BACK:
				this.a(cap, integer9, integer11, ctd12, fz.WEST, fz.EAST);
			case NONE:
		}

		ctd12.a(fu1.u(), fu1.v(), fu1.w());
		return ctd12;
	}

	private void a(cap cap, int integer2, int integer3, ctd ctd, fz fz5, fz fz6) {
		fu fu8 = fu.b;
		if (cap == cap.CLOCKWISE_90 || cap == cap.COUNTERCLOCKWISE_90) {
			fu8 = fu8.a(cap.a(fz5), integer3);
		} else if (cap == cap.CLOCKWISE_180) {
			fu8 = fu8.a(fz6, integer2);
		} else {
			fu8 = fu8.a(fz5, integer2);
		}

		ctd.a(fu8.u(), 0, fu8.w());
	}

	public le a(le le) {
		if (this.a.isEmpty()) {
			le.a("blocks", new lk());
			le.a("palette", new lk());
		} else {
			List<cve.b> list3 = Lists.<cve.b>newArrayList();
			cve.b b4 = new cve.b();
			list3.add(b4);

			for (int integer5 = 1; integer5 < this.a.size(); integer5++) {
				list3.add(new cve.b());
			}

			lk lk5 = new lk();
			List<cve.c> list6 = ((cve.a)this.a.get(0)).a();

			for (int integer7 = 0; integer7 < list6.size(); integer7++) {
				cve.c c8 = (cve.c)list6.get(integer7);
				le le9 = new le();
				le9.a("pos", this.a(c8.a.u(), c8.a.v(), c8.a.w()));
				int integer10 = b4.a(c8.b);
				le9.b("state", integer10);
				if (c8.c != null) {
					le9.a("nbt", c8.c);
				}

				lk5.add(le9);

				for (int integer11 = 1; integer11 < this.a.size(); integer11++) {
					cve.b b12 = (cve.b)list3.get(integer11);
					b12.a(((cve.c)((cve.a)this.a.get(integer11)).a().get(integer7)).b, integer10);
				}
			}

			le.a("blocks", lk5);
			if (list3.size() == 1) {
				lk lk7 = new lk();

				for (cfj cfj9 : b4) {
					lk7.add(lq.a(cfj9));
				}

				le.a("palette", lk7);
			} else {
				lk lk7 = new lk();

				for (cve.b b9 : list3) {
					lk lk10 = new lk();

					for (cfj cfj12 : b9) {
						lk10.add(lq.a(cfj12));
					}

					lk7.add(lk10);
				}

				le.a("palettes", lk7);
			}
		}

		lk lk3 = new lk();

		for (cve.d d5 : this.b) {
			le le6 = new le();
			le6.a("pos", this.a(d5.a.b, d5.a.c, d5.a.d));
			le6.a("blockPos", this.a(d5.b.u(), d5.b.v(), d5.b.w()));
			if (d5.c != null) {
				le6.a("nbt", d5.c);
			}

			lk3.add(le6);
		}

		le.a("entities", lk3);
		le.a("size", this.a(this.c.u(), this.c.v(), this.c.w()));
		le.b("DataVersion", u.a().getWorldVersion());
		return le;
	}

	public void b(le le) {
		this.a.clear();
		this.b.clear();
		lk lk3 = le.d("size", 3);
		this.c = new fu(lk3.e(0), lk3.e(1), lk3.e(2));
		lk lk4 = le.d("blocks", 10);
		if (le.c("palettes", 9)) {
			lk lk5 = le.d("palettes", 9);

			for (int integer6 = 0; integer6 < lk5.size(); integer6++) {
				this.a(lk5.b(integer6), lk4);
			}
		} else {
			this.a(le.d("palette", 10), lk4);
		}

		lk lk5 = le.d("entities", 10);

		for (int integer6 = 0; integer6 < lk5.size(); integer6++) {
			le le7 = lk5.a(integer6);
			lk lk8 = le7.d("pos", 6);
			dem dem9 = new dem(lk8.h(0), lk8.h(1), lk8.h(2));
			lk lk10 = le7.d("blockPos", 3);
			fu fu11 = new fu(lk10.e(0), lk10.e(1), lk10.e(2));
			if (le7.e("nbt")) {
				le le12 = le7.p("nbt");
				this.b.add(new cve.d(dem9, fu11, le12));
			}
		}
	}

	private void a(lk lk1, lk lk2) {
		cve.b b4 = new cve.b();

		for (int integer5 = 0; integer5 < lk1.size(); integer5++) {
			b4.a(lq.c(lk1.a(integer5)), integer5);
		}

		List<cve.c> list5 = Lists.<cve.c>newArrayList();
		List<cve.c> list6 = Lists.<cve.c>newArrayList();
		List<cve.c> list7 = Lists.<cve.c>newArrayList();

		for (int integer8 = 0; integer8 < lk2.size(); integer8++) {
			le le9 = lk2.a(integer8);
			lk lk10 = le9.d("pos", 3);
			fu fu11 = new fu(lk10.e(0), lk10.e(1), lk10.e(2));
			cfj cfj12 = b4.a(le9.h("state"));
			le le13;
			if (le9.e("nbt")) {
				le13 = le9.p("nbt");
			} else {
				le13 = null;
			}

			cve.c c14 = new cve.c(fu11, cfj12, le13);
			a(c14, list5, list6, list7);
		}

		List<cve.c> list8 = a(list5, list6, list7);
		this.a.add(new cve.a(list8));
	}

	private lk a(int... arr) {
		lk lk3 = new lk();

		for (int integer7 : arr) {
			lk3.add(lj.a(integer7));
		}

		return lk3;
	}

	private lk a(double... arr) {
		lk lk3 = new lk();

		for (double double7 : arr) {
			lk3.add(lf.a(double7));
		}

		return lk3;
	}

	public static final class a {
		private final List<cve.c> a;
		private final Map<bvr, List<cve.c>> b = Maps.<bvr, List<cve.c>>newHashMap();

		private a(List<cve.c> list) {
			this.a = list;
		}

		public List<cve.c> a() {
			return this.a;
		}

		public List<cve.c> a(bvr bvr) {
			return (List<cve.c>)this.b.computeIfAbsent(bvr, bvrx -> (List)this.a.stream().filter(c -> c.b.a(bvrx)).collect(Collectors.toList()));
		}
	}

	static class b implements Iterable<cfj> {
		public static final cfj a = bvs.a.n();
		private final ge<cfj> b = new ge<>(16);
		private int c;

		private b() {
		}

		public int a(cfj cfj) {
			int integer3 = this.b.a(cfj);
			if (integer3 == -1) {
				integer3 = this.c++;
				this.b.a(cfj, integer3);
			}

			return integer3;
		}

		@Nullable
		public cfj a(int integer) {
			cfj cfj3 = this.b.a(integer);
			return cfj3 == null ? a : cfj3;
		}

		public Iterator<cfj> iterator() {
			return this.b.iterator();
		}

		public void a(cfj cfj, int integer) {
			this.b.a(cfj, integer);
		}
	}

	public static class c {
		public final fu a;
		public final cfj b;
		public final le c;

		public c(fu fu, cfj cfj, @Nullable le le) {
			this.a = fu;
			this.b = cfj;
			this.c = le;
		}

		public String toString() {
			return String.format("<StructureBlockInfo | %s | %s | %s>", this.a, this.b, this.c);
		}
	}

	public static class d {
		public final dem a;
		public final fu b;
		public final le c;

		public d(dem dem, fu fu, le le) {
			this.a = dem;
			this.b = fu;
			this.c = le;
		}
	}
}
