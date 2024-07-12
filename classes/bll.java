public class bll extends bke {
	public bll(bke.a a) {
		super(a);
	}

	public static void a(bki bki, aoe aoe, int integer) {
		le le4 = bki.p();
		lk lk5 = le4.d("Effects", 9);
		le le6 = new le();
		le6.a("EffectId", (byte)aoe.a(aoe));
		le6.b("EffectDuration", integer);
		lk5.add(le6);
		le4.a("Effects", lk5);
	}

	@Override
	public bki a(bki bki, bqb bqb, aoy aoy) {
		bki bki5 = super.a(bki, bqb, aoy);
		le le6 = bki.o();
		if (le6 != null && le6.c("Effects", 9)) {
			lk lk7 = le6.d("Effects", 10);

			for (int integer8 = 0; integer8 < lk7.size(); integer8++) {
				int integer9 = 160;
				le le10 = lk7.a(integer8);
				if (le10.c("EffectDuration", 3)) {
					integer9 = le10.h("EffectDuration");
				}

				aoe aoe11 = aoe.a(le10.f("EffectId"));
				if (aoe11 != null) {
					aoy.c(new aog(aoe11, integer9));
				}
			}
		}

		return aoy instanceof bec && ((bec)aoy).bJ.d ? bki5 : new bki(bkk.kC);
	}
}
