import javax.annotation.Nullable;

public class bma extends bke {
	public bma(bke.a a) {
		super(a);
	}

	public static boolean a(@Nullable le le) {
		if (!blz.a(le)) {
			return false;
		} else if (!le.c("title", 8)) {
			return false;
		} else {
			String string2 = le.l("title");
			return string2.length() > 32 ? false : le.c("author", 8);
		}
	}

	public static int d(bki bki) {
		return bki.o().h("generation");
	}

	public static int g(bki bki) {
		le le2 = bki.o();
		return le2 != null ? le2.d("pages", 8).size() : 0;
	}

	@Override
	public mr h(bki bki) {
		if (bki.n()) {
			le le3 = bki.o();
			String string4 = le3.l("title");
			if (!aei.b(string4)) {
				return new nd(string4);
			}
		}

		return super.h(bki);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		cfj cfj5 = bqb3.d_(fu4);
		if (cfj5.a(bvs.lY)) {
			return bzb.a(bqb3, fu4, cfj5, blv.l()) ? ang.a(bqb3.v) : ang.PASS;
		} else {
			return ang.PASS;
		}
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		bec.a(bki5, anf);
		bec.b(acu.c.b(this));
		return anh.a(bki5, bqb.s_());
	}

	public static boolean a(bki bki, @Nullable cz cz, @Nullable bec bec) {
		le le4 = bki.o();
		if (le4 != null && !le4.q("resolved")) {
			le4.a("resolved", true);
			if (!a(le4)) {
				return false;
			} else {
				lk lk5 = le4.d("pages", 8);

				for (int integer6 = 0; integer6 < lk5.size(); integer6++) {
					String string7 = lk5.j(integer6);

					mr mr8;
					try {
						mr8 = mr.a.b(string7);
						mr8 = ms.a(cz, mr8, bec, 0);
					} catch (Exception var9) {
						mr8 = new nd(string7);
					}

					lk5.d(integer6, lt.a(mr.a.a(mr8)));
				}

				le4.a("pages", lk5);
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean e(bki bki) {
		return true;
	}
}
