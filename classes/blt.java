import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

public class blt extends bke implements blw {
	private final Multimap<aps, apv> a;

	public blt(bke.a a) {
		super(a);
		Builder<aps, apv> builder3 = ImmutableMultimap.builder();
		builder3.put(apx.f, new apv(f, "Tool modifier", 8.0, apv.a.ADDITION));
		builder3.put(apx.h, new apv(g, "Tool modifier", -2.9F, apv.a.ADDITION));
		this.a = builder3.build();
	}

	@Override
	public boolean a(cfj cfj, bqb bqb, fu fu, bec bec) {
		return !bec.b_();
	}

	@Override
	public blu d_(bki bki) {
		return blu.SPEAR;
	}

	@Override
	public int e_(bki bki) {
		return 72000;
	}

	@Override
	public void a(bki bki, bqb bqb, aoy aoy, int integer) {
		if (aoy instanceof bec) {
			bec bec6 = (bec)aoy;
			int integer7 = this.e_(bki) - integer;
			if (integer7 >= 10) {
				int integer8 = bny.g(bki);
				if (integer8 <= 0 || bec6.aB()) {
					if (!bqb.v) {
						bki.a(1, bec6, bec -> bec.d(aoy.dW()));
						if (integer8 == 0) {
							bfe bfe9 = new bfe(bqb, bec6, bki);
							bfe9.a(bec6, bec6.q, bec6.p, 0.0F, 2.5F + (float)integer8 * 0.5F, 1.0F);
							if (bec6.bJ.d) {
								bfe9.d = beg.a.CREATIVE_ONLY;
							}

							bqb.c(bfe9);
							bqb.a(null, bfe9, acl.pc, acm.PLAYERS, 1.0F, 1.0F);
							if (!bec6.bJ.d) {
								bec6.bt.f(bki);
							}
						}
					}

					bec6.b(acu.c.b(this));
					if (integer8 > 0) {
						float float9 = bec6.p;
						float float10 = bec6.q;
						float float11 = -aec.a(float9 * (float) (Math.PI / 180.0)) * aec.b(float10 * (float) (Math.PI / 180.0));
						float float12 = -aec.a(float10 * (float) (Math.PI / 180.0));
						float float13 = aec.b(float9 * (float) (Math.PI / 180.0)) * aec.b(float10 * (float) (Math.PI / 180.0));
						float float14 = aec.c(float11 * float11 + float12 * float12 + float13 * float13);
						float float15 = 3.0F * ((1.0F + (float)integer8) / 4.0F);
						float11 *= float15 / float14;
						float12 *= float15 / float14;
						float13 *= float15 / float14;
						bec6.h((double)float11, (double)float12, (double)float13);
						bec6.r(20);
						if (bec6.aj()) {
							float float16 = 1.1999999F;
							bec6.a(apd.SELF, new dem(0.0, 1.1999999F, 0.0));
						}

						ack ack16;
						if (integer8 >= 3) {
							ack16 = acl.pb;
						} else if (integer8 == 2) {
							ack16 = acl.pa;
						} else {
							ack16 = acl.oZ;
						}

						bqb.a(null, bec6, ack16, acm.PLAYERS, 1.0F, 1.0F);
					}
				}
			}
		}
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		if (bki5.g() >= bki5.h() - 1) {
			return anh.d(bki5);
		} else if (bny.g(bki5) > 0 && !bec.aB()) {
			return anh.d(bki5);
		} else {
			bec.c(anf);
			return anh.b(bki5);
		}
	}

	@Override
	public boolean a(bki bki, aoy aoy2, aoy aoy3) {
		bki.a(1, aoy3, aoy -> aoy.c(aor.MAINHAND));
		return true;
	}

	@Override
	public boolean a(bki bki, bqb bqb, cfj cfj, fu fu, aoy aoy) {
		if ((double)cfj.h(bqb, fu) != 0.0) {
			bki.a(2, aoy, aoyx -> aoyx.c(aor.MAINHAND));
		}

		return true;
	}

	@Override
	public Multimap<aps, apv> a(aor aor) {
		return aor == aor.MAINHAND ? this.a : super.a(aor);
	}

	@Override
	public int c() {
		return 1;
	}
}
