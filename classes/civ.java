import java.util.Random;

public class civ implements bpm {
	private int a;

	@Override
	public int a(zd zd, boolean boolean2, boolean boolean3) {
		if (!boolean2) {
			return 0;
		} else if (!zd.S().b(bpx.y)) {
			return 0;
		} else {
			Random random5 = zd.t;
			this.a--;
			if (this.a > 0) {
				return 0;
			} else {
				this.a = this.a + (60 + random5.nextInt(60)) * 20;
				if (zd.c() < 5 && zd.m().d()) {
					return 0;
				} else {
					int integer6 = 0;

					for (bec bec8 : zd.w()) {
						if (!bec8.a_()) {
							fu fu9 = bec8.cA();
							if (!zd.m().d() || fu9.v() >= zd.t_() && zd.f(fu9)) {
								ane ane10 = zd.d(fu9);
								if (ane10.a(random5.nextFloat() * 3.0F)) {
									acq acq11 = ((ze)bec8).A();
									int integer12 = aec.a(acq11.a(acu.i.b(acu.m)), 1, Integer.MAX_VALUE);
									int integer13 = 24000;
									if (random5.nextInt(integer12) >= 72000) {
										fu fu14 = fu9.b(20 + random5.nextInt(15)).g(-10 + random5.nextInt(21)).e(-10 + random5.nextInt(21));
										cfj cfj15 = zd.d_(fu14);
										cxa cxa16 = zd.b(fu14);
										if (bqj.a(zd, fu14, cfj15, cxa16, aoq.ag)) {
											apo apo17 = null;
											int integer18 = 1 + random5.nextInt(ane10.a().a() + 1);

											for (int integer19 = 0; integer19 < integer18; integer19++) {
												bcd bcd20 = aoq.ag.a(zd);
												bcd20.a(fu14, 0.0F, 0.0F);
												apo17 = bcd20.a(zd, ane10, apb.NATURAL, apo17, null);
												zd.c(bcd20);
											}

											integer6 += integer18;
										}
									}
								}
							}
						}
					}

					return integer6;
				}
			}
		}
	}
}
