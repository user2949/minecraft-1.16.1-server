import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class biz extends bkv implements blw {
	private boolean c = false;
	private boolean d = false;

	public biz(bke.a a) {
		super(a);
	}

	@Override
	public Predicate<bki> e() {
		return b;
	}

	@Override
	public Predicate<bki> b() {
		return a;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		if (d(bki5)) {
			a(bqb, bec, anf, bki5, m(bki5), 1.0F);
			a(bki5, false);
			return anh.b(bki5);
		} else if (!bec.f(bki5).a()) {
			if (!d(bki5)) {
				this.c = false;
				this.d = false;
				bec.c(anf);
			}

			return anh.b(bki5);
		} else {
			return anh.d(bki5);
		}
	}

	@Override
	public void a(bki bki, bqb bqb, aoy aoy, int integer) {
		int integer6 = this.e_(bki) - integer;
		float float7 = a(integer6, bki);
		if (float7 >= 1.0F && !d(bki) && a(aoy, bki)) {
			a(bki, true);
			acm acm8 = aoy instanceof bec ? acm.PLAYERS : acm.HOSTILE;
			bqb.a(null, aoy.cC(), aoy.cD(), aoy.cG(), acl.ct, acm8, 1.0F, 1.0F / (h.nextFloat() * 0.5F + 1.0F) + 0.2F);
		}
	}

	private static boolean a(aoy aoy, bki bki) {
		int integer3 = bny.a(boa.H, bki);
		int integer4 = integer3 == 0 ? 1 : 3;
		boolean boolean5 = aoy instanceof bec && ((bec)aoy).bJ.d;
		bki bki6 = aoy.f(bki);
		bki bki7 = bki6.i();

		for (int integer8 = 0; integer8 < integer4; integer8++) {
			if (integer8 > 0) {
				bki6 = bki7.i();
			}

			if (bki6.a() && boolean5) {
				bki6 = new bki(bkk.kg);
				bki7 = bki6.i();
			}

			if (!a(aoy, bki, bki6, integer8 > 0, boolean5)) {
				return false;
			}
		}

		return true;
	}

	private static boolean a(aoy aoy, bki bki2, bki bki3, boolean boolean4, boolean boolean5) {
		if (bki3.a()) {
			return false;
		} else {
			boolean boolean6 = boolean5 && bki3.b() instanceof bih;
			bki bki7;
			if (!boolean6 && !boolean5 && !boolean4) {
				bki7 = bki3.a(1);
				if (bki3.a() && aoy instanceof bec) {
					((bec)aoy).bt.f(bki3);
				}
			} else {
				bki7 = bki3.i();
			}

			b(bki2, bki7);
			return true;
		}
	}

	public static boolean d(bki bki) {
		le le2 = bki.o();
		return le2 != null && le2.q("Charged");
	}

	public static void a(bki bki, boolean boolean2) {
		le le3 = bki.p();
		le3.a("Charged", boolean2);
	}

	private static void b(bki bki1, bki bki2) {
		le le3 = bki1.p();
		lk lk4;
		if (le3.c("ChargedProjectiles", 9)) {
			lk4 = le3.d("ChargedProjectiles", 10);
		} else {
			lk4 = new lk();
		}

		le le5 = new le();
		bki2.b(le5);
		lk4.add(le5);
		le3.a("ChargedProjectiles", lk4);
	}

	private static List<bki> k(bki bki) {
		List<bki> list2 = Lists.<bki>newArrayList();
		le le3 = bki.o();
		if (le3 != null && le3.c("ChargedProjectiles", 9)) {
			lk lk4 = le3.d("ChargedProjectiles", 10);
			if (lk4 != null) {
				for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
					le le6 = lk4.a(integer5);
					list2.add(bki.a(le6));
				}
			}
		}

		return list2;
	}

	private static void l(bki bki) {
		le le2 = bki.o();
		if (le2 != null) {
			lk lk3 = le2.d("ChargedProjectiles", 9);
			lk3.clear();
			le2.a("ChargedProjectiles", lk3);
		}
	}

	public static boolean a(bki bki, bke bke) {
		return k(bki).stream().anyMatch(bkix -> bkix.b() == bke);
	}

	private static void a(bqb bqb, aoy aoy, anf anf, bki bki4, bki bki5, float float6, boolean boolean7, float float8, float float9, float float10) {
		if (!bqb.v) {
			boolean boolean11 = bki5.b() == bkk.pn;
			bes bes12;
			if (boolean11) {
				bes12 = new ben(bqb, bki5, aoy, aoy.cC(), aoy.cF() - 0.15F, aoy.cG(), true);
			} else {
				bes12 = a(bqb, aoy, bki4, bki5);
				if (boolean7 || float10 != 0.0F) {
					((beg)bes12).d = beg.a.CREATIVE_ONLY;
				}
			}

			if (aoy instanceof bbo) {
				bbo bbo13 = (bbo)aoy;
				bbo13.a(bbo13.A(), bki4, bes12, float10);
			} else {
				dem dem13 = aoy.i(1.0F);
				d d14 = new d(new g(dem13), float10, true);
				dem dem15 = aoy.f(1.0F);
				g g16 = new g(dem15);
				g16.a(d14);
				bes12.c((double)g16.a(), (double)g16.b(), (double)g16.c(), float8, float9);
			}

			bki4.a(boolean11 ? 3 : 1, aoy, aoyx -> aoyx.d(anf));
			bqb.c(bes12);
			bqb.a(null, aoy.cC(), aoy.cD(), aoy.cG(), acl.cz, acm.PLAYERS, 1.0F, float6);
		}
	}

	private static beg a(bqb bqb, aoy aoy, bki bki3, bki bki4) {
		bih bih5 = (bih)(bki4.b() instanceof bih ? bki4.b() : bkk.kg);
		beg beg6 = bih5.a(bqb, bki4, aoy);
		if (aoy instanceof bec) {
			beg6.a(true);
		}

		beg6.a(acl.cs);
		beg6.p(true);
		int integer7 = bny.a(boa.J, bki3);
		if (integer7 > 0) {
			beg6.b((byte)integer7);
		}

		return beg6;
	}

	public static void a(bqb bqb, aoy aoy, anf anf, bki bki, float float5, float float6) {
		List<bki> list7 = k(bki);
		float[] arr8 = a(aoy.cX());

		for (int integer9 = 0; integer9 < list7.size(); integer9++) {
			bki bki10 = (bki)list7.get(integer9);
			boolean boolean11 = aoy instanceof bec && ((bec)aoy).bJ.d;
			if (!bki10.a()) {
				if (integer9 == 0) {
					a(bqb, aoy, anf, bki, bki10, arr8[integer9], boolean11, float5, float6, 0.0F);
				} else if (integer9 == 1) {
					a(bqb, aoy, anf, bki, bki10, arr8[integer9], boolean11, float5, float6, -10.0F);
				} else if (integer9 == 2) {
					a(bqb, aoy, anf, bki, bki10, arr8[integer9], boolean11, float5, float6, 10.0F);
				}
			}
		}

		a(bqb, aoy, bki);
	}

	private static float[] a(Random random) {
		boolean boolean2 = random.nextBoolean();
		return new float[]{1.0F, a(boolean2), a(!boolean2)};
	}

	private static float a(boolean boolean1) {
		float float2 = boolean1 ? 0.63F : 0.43F;
		return 1.0F / (h.nextFloat() * 0.5F + 1.8F) + float2;
	}

	private static void a(bqb bqb, aoy aoy, bki bki) {
		if (aoy instanceof ze) {
			ze ze4 = (ze)aoy;
			if (!bqb.v) {
				aa.F.a(ze4, bki);
			}

			ze4.b(acu.c.b(bki.b()));
		}

		l(bki);
	}

	@Override
	public void a(bqb bqb, aoy aoy, bki bki, int integer) {
		if (!bqb.v) {
			int integer6 = bny.a(boa.I, bki);
			ack ack7 = this.a(integer6);
			ack ack8 = integer6 == 0 ? acl.cu : null;
			float float9 = (float)(bki.k() - integer) / (float)g(bki);
			if (float9 < 0.2F) {
				this.c = false;
				this.d = false;
			}

			if (float9 >= 0.2F && !this.c) {
				this.c = true;
				bqb.a(null, aoy.cC(), aoy.cD(), aoy.cG(), ack7, acm.PLAYERS, 0.5F, 1.0F);
			}

			if (float9 >= 0.5F && ack8 != null && !this.d) {
				this.d = true;
				bqb.a(null, aoy.cC(), aoy.cD(), aoy.cG(), ack8, acm.PLAYERS, 0.5F, 1.0F);
			}
		}
	}

	@Override
	public int e_(bki bki) {
		return g(bki) + 3;
	}

	public static int g(bki bki) {
		int integer2 = bny.a(boa.I, bki);
		return integer2 == 0 ? 25 : 25 - 5 * integer2;
	}

	@Override
	public blu d_(bki bki) {
		return blu.CROSSBOW;
	}

	private ack a(int integer) {
		switch (integer) {
			case 1:
				return acl.cw;
			case 2:
				return acl.cx;
			case 3:
				return acl.cy;
			default:
				return acl.cv;
		}
	}

	private static float a(int integer, bki bki) {
		float float3 = (float)integer / (float)g(bki);
		if (float3 > 1.0F) {
			float3 = 1.0F;
		}

		return float3;
	}

	private static float m(bki bki) {
		return bki.b() == bkk.qP && a(bki, bkk.pn) ? 1.6F : 3.15F;
	}

	@Override
	public int d() {
		return 8;
	}
}
