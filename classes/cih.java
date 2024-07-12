import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;

public enum cih {
	START {
		@Override
		public void a(zd zd, cii cii, List<bab> list, int integer, fu fu) {
			fu fu7 = new fu(0, 128, 0);

			for (bab bab9 : list) {
				bab9.a(fu7);
			}

			cii.a(PREPARING_TO_SUMMON_PILLARS);
		}
	},
	PREPARING_TO_SUMMON_PILLARS {
		@Override
		public void a(zd zd, cii cii, List<bab> list, int integer, fu fu) {
			if (integer < 100) {
				if (integer == 0 || integer == 50 || integer == 51 || integer == 52 || integer >= 95) {
					zd.c(3001, new fu(0, 128, 0), 0);
				}
			} else {
				cii.a(SUMMONING_PILLARS);
			}
		}
	},
	SUMMONING_PILLARS {
		@Override
		public void a(zd zd, cii cii, List<bab> list, int integer, fu fu) {
			int integer7 = 40;
			boolean boolean8 = integer % 40 == 0;
			boolean boolean9 = integer % 40 == 39;
			if (boolean8 || boolean9) {
				List<cmi.a> list10 = cmi.a(zd);
				int integer11 = integer / 40;
				if (integer11 < list10.size()) {
					cmi.a a12 = (cmi.a)list10.get(integer11);
					if (boolean8) {
						for (bab bab14 : list) {
							bab14.a(new fu(a12.a(), a12.d() + 1, a12.b()));
						}
					} else {
						int integer13 = 10;

						for (fu fu15 : fu.a(new fu(a12.a() - 10, a12.d() - 10, a12.b() - 10), new fu(a12.a() + 10, a12.d() + 10, a12.b() + 10))) {
							zd.a(fu15, false);
						}

						zd.a(null, (double)((float)a12.a() + 0.5F), (double)a12.d(), (double)((float)a12.b() + 0.5F), 5.0F, bpt.a.DESTROY);
						coq coq14 = new coq(true, ImmutableList.of(a12), new fu(0, 128, 0));
						ckt.A.b(coq14).a(zd, zd.a(), zd.i().g(), new Random(), new fu(a12.a(), 45, a12.b()));
					}
				} else if (boolean8) {
					cii.a(SUMMONING_DRAGON);
				}
			}
		}
	},
	SUMMONING_DRAGON {
		@Override
		public void a(zd zd, cii cii, List<bab> list, int integer, fu fu) {
			if (integer >= 100) {
				cii.a(END);
				cii.f();

				for (bab bab8 : list) {
					bab8.a(null);
					zd.a(bab8, bab8.cC(), bab8.cD(), bab8.cG(), 6.0F, bpt.a.NONE);
					bab8.aa();
				}
			} else if (integer >= 80) {
				zd.c(3001, new fu(0, 128, 0), 0);
			} else if (integer == 0) {
				for (bab bab8 : list) {
					bab8.a(new fu(0, 128, 0));
				}
			} else if (integer < 5) {
				zd.c(3001, new fu(0, 128, 0), 0);
			}
		}
	},
	END {
		@Override
		public void a(zd zd, cii cii, List<bab> list, int integer, fu fu) {
		}
	};

	private cih() {
	}

	public abstract void a(zd zd, cii cii, List<bab> list, int integer, fu fu);
}
