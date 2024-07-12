import java.util.function.Predicate;

public class bis extends bkv implements blw {
	public bis(bke.a a) {
		super(a);
	}

	@Override
	public void a(bki bki, bqb bqb, aoy aoy, int integer) {
		if (aoy instanceof bec) {
			bec bec6 = (bec)aoy;
			boolean boolean7 = bec6.bJ.d || bny.a(boa.A, bki) > 0;
			bki bki8 = bec6.f(bki);
			if (!bki8.a() || boolean7) {
				if (bki8.a()) {
					bki8 = new bki(bkk.kg);
				}

				int integer9 = this.e_(bki) - integer;
				float float10 = a(integer9);
				if (!((double)float10 < 0.1)) {
					boolean boolean11 = boolean7 && bki8.b() == bkk.kg;
					if (!bqb.v) {
						bih bih12 = (bih)(bki8.b() instanceof bih ? bki8.b() : bkk.kg);
						beg beg13 = bih12.a(bqb, bki8, bec6);
						beg13.a(bec6, bec6.q, bec6.p, 0.0F, float10 * 3.0F, 1.0F);
						if (float10 == 1.0F) {
							beg13.a(true);
						}

						int integer14 = bny.a(boa.x, bki);
						if (integer14 > 0) {
							beg13.h(beg13.n() + (double)integer14 * 0.5 + 0.5);
						}

						int integer15 = bny.a(boa.y, bki);
						if (integer15 > 0) {
							beg13.a(integer15);
						}

						if (bny.a(boa.z, bki) > 0) {
							beg13.f(100);
						}

						bki.a(1, bec6, bec2 -> bec2.d(bec6.dW()));
						if (boolean11 || bec6.bJ.d && (bki8.b() == bkk.qj || bki8.b() == bkk.qk)) {
							beg13.d = beg.a.CREATIVE_ONLY;
						}

						bqb.c(beg13);
					}

					bqb.a(null, bec6.cC(), bec6.cD(), bec6.cG(), acl.Y, acm.PLAYERS, 1.0F, 1.0F / (h.nextFloat() * 0.4F + 1.2F) + float10 * 0.5F);
					if (!boolean11 && !bec6.bJ.d) {
						bki8.g(1);
						if (bki8.a()) {
							bec6.bt.f(bki8);
						}
					}

					bec6.b(acu.c.b(this));
				}
			}
		}
	}

	public static float a(int integer) {
		float float2 = (float)integer / 20.0F;
		float2 = (float2 * float2 + float2 * 2.0F) / 3.0F;
		if (float2 > 1.0F) {
			float2 = 1.0F;
		}

		return float2;
	}

	@Override
	public int e_(bki bki) {
		return 72000;
	}

	@Override
	public blu d_(bki bki) {
		return blu.BOW;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		boolean boolean6 = !bec.f(bki5).a();
		if (!bec.bJ.d && !boolean6) {
			return anh.d(bki5);
		} else {
			bec.c(anf);
			return anh.b(bki5);
		}
	}

	@Override
	public Predicate<bki> b() {
		return a;
	}

	@Override
	public int d() {
		return 15;
	}
}
