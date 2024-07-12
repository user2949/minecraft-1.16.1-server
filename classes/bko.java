import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import javax.annotation.Nullable;

public class bko extends bix {
	public bko(bke.a a) {
		super(a);
	}

	public static bki a(bqb bqb, int integer2, int integer3, byte byte4, boolean boolean5, boolean boolean6) {
		bki bki7 = new bki(bkk.nf);
		a(bki7, bqb, integer2, integer3, byte4, boolean5, boolean6, bqb.W());
		return bki7;
	}

	@Nullable
	public static czv a(bki bki, bqb bqb) {
		return bqb.a(a(d(bki)));
	}

	@Nullable
	public static czv b(bki bki, bqb bqb) {
		czv czv3 = a(bki, bqb);
		if (czv3 == null && bqb instanceof zd) {
			czv3 = a(bki, bqb, bqb.u_().a(), bqb.u_().c(), 3, false, false, bqb.W());
		}

		return czv3;
	}

	public static int d(bki bki) {
		le le2 = bki.o();
		return le2 != null && le2.c("map", 99) ? le2.h("map") : 0;
	}

	private static czv a(bki bki, bqb bqb, int integer3, int integer4, int integer5, boolean boolean6, boolean boolean7, ug<bqb> ug) {
		int integer9 = bqb.t();
		czv czv10 = new czv(a(integer9));
		czv10.a(integer3, integer4, integer5, boolean6, boolean7, ug);
		bqb.a(czv10);
		bki.p().b("map", integer9);
		return czv10;
	}

	public static String a(int integer) {
		return "map_" + integer;
	}

	public void a(bqb bqb, aom aom, czv czv) {
		if (bqb.W() == czv.c && aom instanceof bec) {
			int integer5 = 1 << czv.f;
			int integer6 = czv.a;
			int integer7 = czv.b;
			int integer8 = aec.c(aom.cC() - (double)integer6) / integer5 + 64;
			int integer9 = aec.c(aom.cG() - (double)integer7) / integer5 + 64;
			int integer10 = 128 / integer5;
			if (bqb.m().e()) {
				integer10 /= 2;
			}

			czv.a a11 = czv.a((bec)aom);
			a11.b++;
			boolean boolean12 = false;

			for (int integer13 = integer8 - integer10 + 1; integer13 < integer8 + integer10; integer13++) {
				if ((integer13 & 15) == (a11.b & 15) || boolean12) {
					boolean12 = false;
					double double14 = 0.0;

					for (int integer16 = integer9 - integer10 - 1; integer16 < integer9 + integer10; integer16++) {
						if (integer13 >= 0 && integer16 >= -1 && integer13 < 128 && integer16 < 128) {
							int integer17 = integer13 - integer8;
							int integer18 = integer16 - integer9;
							boolean boolean19 = integer17 * integer17 + integer18 * integer18 > (integer10 - 2) * (integer10 - 2);
							int integer20 = (integer6 / integer5 + integer13 - 64) * integer5;
							int integer21 = (integer7 / integer5 + integer16 - 64) * integer5;
							Multiset<cxe> multiset22 = LinkedHashMultiset.create();
							chj chj23 = bqb.n(new fu(integer20, 0, integer21));
							if (!chj23.t()) {
								bph bph24 = chj23.g();
								int integer25 = integer20 & 15;
								int integer26 = integer21 & 15;
								int integer27 = 0;
								double double28 = 0.0;
								if (bqb.m().e()) {
									int integer30 = integer20 + integer21 * 231871;
									integer30 = integer30 * integer30 * 31287121 + integer30 * 11;
									if ((integer30 >> 20 & 1) == 0) {
										multiset22.add(bvs.j.n().d(bqb, fu.b), 10);
									} else {
										multiset22.add(bvs.b.n().d(bqb, fu.b), 100);
									}

									double28 = 100.0;
								} else {
									fu.a a30 = new fu.a();
									fu.a a31 = new fu.a();

									for (int integer32 = 0; integer32 < integer5; integer32++) {
										for (int integer33 = 0; integer33 < integer5; integer33++) {
											int integer34 = chj23.a(cio.a.WORLD_SURFACE, integer32 + integer25, integer33 + integer26) + 1;
											cfj cfj35;
											if (integer34 <= 1) {
												cfj35 = bvs.z.n();
											} else {
												do {
													a30.d(bph24.d() + integer32 + integer25, --integer34, bph24.e() + integer33 + integer26);
													cfj35 = chj23.d_(a30);
												} while (cfj35.d(bqb, a30) == cxe.b && integer34 > 0);

												if (integer34 > 0 && !cfj35.m().c()) {
													int integer36 = integer34 - 1;
													a31.g(a30);

													cfj cfj37;
													do {
														a31.p(integer36--);
														cfj37 = chj23.d_(a31);
														integer27++;
													} while (integer36 > 0 && !cfj37.m().c());

													cfj35 = this.a(bqb, cfj35, a30);
												}
											}

											czv.a(bqb, bph24.d() + integer32 + integer25, bph24.e() + integer33 + integer26);
											double28 += (double)integer34 / (double)(integer5 * integer5);
											multiset22.add(cfj35.d(bqb, a30));
										}
									}
								}

								integer27 /= integer5 * integer5;
								double double30 = (double28 - double14) * 4.0 / (double)(integer5 + 4) + ((double)(integer13 + integer16 & 1) - 0.5) * 0.4;
								int integer32 = 1;
								if (double30 > 0.6) {
									integer32 = 2;
								}

								if (double30 < -0.6) {
									integer32 = 0;
								}

								cxe cxe33 = Iterables.getFirst(Multisets.copyHighestCountFirst(multiset22), cxe.b);
								if (cxe33 == cxe.n) {
									double30 = (double)integer27 * 0.1 + (double)(integer13 + integer16 & 1) * 0.2;
									integer32 = 1;
									if (double30 < 0.5) {
										integer32 = 2;
									}

									if (double30 > 0.9) {
										integer32 = 0;
									}
								}

								double14 = double28;
								if (integer16 >= 0 && integer17 * integer17 + integer18 * integer18 < integer10 * integer10 && (!boolean19 || (integer13 + integer16 & 1) != 0)) {
									byte byte34 = czv.g[integer13 + integer16 * 128];
									byte byte35 = (byte)(cxe33.aj * 4 + integer32);
									if (byte34 != byte35) {
										czv.g[integer13 + integer16 * 128] = byte35;
										czv.a(integer13, integer16);
										boolean12 = true;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private cfj a(bqb bqb, cfj cfj, fu fu) {
		cxa cxa5 = cfj.m();
		return !cxa5.c() && !cfj.d(bqb, fu, fz.UP) ? cxa5.g() : cfj;
	}

	private static boolean a(bre[] arr, int integer2, int integer3, int integer4) {
		return arr[integer3 * integer2 + integer4 * integer2 * 128 * integer2].k() >= 0.0F;
	}

	public static void a(zd zd, bki bki) {
		czv czv3 = b(bki, zd);
		if (czv3 != null) {
			if (zd.W() == czv3.c) {
				int integer4 = 1 << czv3.f;
				int integer5 = czv3.a;
				int integer6 = czv3.b;
				bre[] arr7 = new bre[128 * integer4 * 128 * integer4];

				for (int integer8 = 0; integer8 < 128 * integer4; integer8++) {
					for (int integer9 = 0; integer9 < 128 * integer4; integer9++) {
						arr7[integer8 * 128 * integer4 + integer9] = zd.v(
							new fu((integer5 / integer4 - 64) * integer4 + integer9, 0, (integer6 / integer4 - 64) * integer4 + integer8)
						);
					}
				}

				for (int integer8 = 0; integer8 < 128; integer8++) {
					for (int integer9 = 0; integer9 < 128; integer9++) {
						if (integer8 > 0 && integer9 > 0 && integer8 < 127 && integer9 < 127) {
							bre bre10 = arr7[integer8 * integer4 + integer9 * integer4 * 128 * integer4];
							int integer11 = 8;
							if (a(arr7, integer4, integer8 - 1, integer9 - 1)) {
								integer11--;
							}

							if (a(arr7, integer4, integer8 - 1, integer9 + 1)) {
								integer11--;
							}

							if (a(arr7, integer4, integer8 - 1, integer9)) {
								integer11--;
							}

							if (a(arr7, integer4, integer8 + 1, integer9 - 1)) {
								integer11--;
							}

							if (a(arr7, integer4, integer8 + 1, integer9 + 1)) {
								integer11--;
							}

							if (a(arr7, integer4, integer8 + 1, integer9)) {
								integer11--;
							}

							if (a(arr7, integer4, integer8, integer9 - 1)) {
								integer11--;
							}

							if (a(arr7, integer4, integer8, integer9 + 1)) {
								integer11--;
							}

							int integer12 = 3;
							cxe cxe13 = cxe.b;
							if (bre10.k() < 0.0F) {
								cxe13 = cxe.q;
								if (integer11 > 7 && integer9 % 2 == 0) {
									integer12 = (integer8 + (int)(aec.a((float)integer9 + 0.0F) * 7.0F)) / 8 % 5;
									if (integer12 == 3) {
										integer12 = 1;
									} else if (integer12 == 4) {
										integer12 = 0;
									}
								} else if (integer11 > 7) {
									cxe13 = cxe.b;
								} else if (integer11 > 5) {
									integer12 = 1;
								} else if (integer11 > 3) {
									integer12 = 0;
								} else if (integer11 > 1) {
									integer12 = 0;
								}
							} else if (integer11 > 0) {
								cxe13 = cxe.B;
								if (integer11 > 3) {
									integer12 = 1;
								} else {
									integer12 = 3;
								}
							}

							if (cxe13 != cxe.b) {
								czv3.g[integer8 + integer9 * 128] = (byte)(cxe13.aj * 4 + integer12);
								czv3.a(integer8, integer9);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void a(bki bki, bqb bqb, aom aom, int integer, boolean boolean5) {
		if (!bqb.v) {
			czv czv7 = b(bki, bqb);
			if (czv7 != null) {
				if (aom instanceof bec) {
					bec bec8 = (bec)aom;
					czv7.a(bec8, bki);
				}

				if (!czv7.h && (boolean5 || aom instanceof bec && ((bec)aom).dD() == bki)) {
					this.a(bqb, aom, czv7);
				}
			}
		}
	}

	@Nullable
	@Override
	public ni<?> a(bki bki, bqb bqb, bec bec) {
		return b(bki, bqb).a(bki, bqb, bec);
	}

	@Override
	public void b(bki bki, bqb bqb, bec bec) {
		le le5 = bki.o();
		if (le5 != null && le5.c("map_scale_direction", 99)) {
			a(bki, bqb, le5.h("map_scale_direction"));
			le5.r("map_scale_direction");
		}
	}

	protected static void a(bki bki, bqb bqb, int integer) {
		czv czv4 = b(bki, bqb);
		if (czv4 != null) {
			a(bki, bqb, czv4.a, czv4.b, aec.a(czv4.f + integer, 0, 4), czv4.d, czv4.e, czv4.c);
		}
	}

	@Nullable
	public static bki a(bqb bqb, bki bki) {
		czv czv3 = b(bki, bqb);
		if (czv3 != null) {
			bki bki4 = bki.i();
			czv czv5 = a(bki4, bqb, 0, 0, czv3.f, czv3.d, czv3.e, czv3.c);
			czv5.a(czv3);
			return bki4;
		} else {
			return null;
		}
	}

	@Override
	public ang a(blv blv) {
		cfj cfj3 = blv.o().d_(blv.a());
		if (cfj3.a(acx.A)) {
			if (!blv.e.v) {
				czv czv4 = b(blv.l(), blv.o());
				czv4.a(blv.o(), blv.a());
			}

			return ang.a(blv.e.v);
		} else {
			return super.a(blv);
		}
	}
}
