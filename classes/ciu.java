import java.util.Random;

public class ciu implements bpm {
	private int a;

	@Override
	public int a(zd zd, boolean boolean2, boolean boolean3) {
		if (!boolean2) {
			return 0;
		} else if (!zd.S().b(bpx.D)) {
			return 0;
		} else {
			Random random5 = zd.t;
			this.a--;
			if (this.a > 0) {
				return 0;
			} else {
				this.a = this.a + 12000 + random5.nextInt(1200);
				long long6 = zd.R() / 24000L;
				if (long6 < 5L || !zd.J()) {
					return 0;
				} else if (random5.nextInt(5) != 0) {
					return 0;
				} else {
					int integer8 = zd.w().size();
					if (integer8 < 1) {
						return 0;
					} else {
						bec bec9 = (bec)zd.w().get(random5.nextInt(integer8));
						if (bec9.a_()) {
							return 0;
						} else if (zd.a(bec9.cA(), 2)) {
							return 0;
						} else {
							int integer10 = (24 + random5.nextInt(24)) * (random5.nextBoolean() ? -1 : 1);
							int integer11 = (24 + random5.nextInt(24)) * (random5.nextBoolean() ? -1 : 1);
							fu.a a12 = bec9.cA().i().e(integer10, 0, integer11);
							if (!zd.a(a12.u() - 10, a12.v() - 10, a12.w() - 10, a12.u() + 10, a12.v() + 10, a12.w() + 10)) {
								return 0;
							} else {
								bre bre13 = zd.v(a12);
								bre.b b14 = bre13.y();
								if (b14 == bre.b.MUSHROOM) {
									return 0;
								} else {
									int integer15 = 0;
									int integer16 = (int)Math.ceil((double)zd.d(a12).b()) + 1;

									for (int integer17 = 0; integer17 < integer16; integer17++) {
										integer15++;
										a12.p(zd.a(cio.a.MOTION_BLOCKING_NO_LEAVES, a12).v());
										if (integer17 == 0) {
											if (!this.a(zd, a12, random5, true)) {
												break;
											}
										} else {
											this.a(zd, a12, random5, false);
										}

										a12.o(a12.u() + random5.nextInt(5) - random5.nextInt(5));
										a12.q(a12.w() + random5.nextInt(5) - random5.nextInt(5));
									}

									return integer15;
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean a(bqb bqb, fu fu, Random random, boolean boolean4) {
		cfj cfj6 = bqb.d_(fu);
		if (!bqj.a(bqb, fu, cfj6, cfj6.m(), aoq.aj)) {
			return false;
		} else if (!bcc.b(aoq.aj, bqb, apb.PATROL, fu, random)) {
			return false;
		} else {
			bcc bcc7 = aoq.aj.a(bqb);
			if (bcc7 != null) {
				if (boolean4) {
					bcc7.t(true);
					bcc7.eV();
				}

				bcc7.d((double)fu.u(), (double)fu.v(), (double)fu.w());
				bcc7.a(bqb, bqb.d(fu), apb.PATROL, null, null);
				bqb.c(bcc7);
				return true;
			} else {
				return false;
			}
		}
	}
}
