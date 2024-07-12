import javax.annotation.Nullable;

public class blz extends bke {
	public blz(bke.a a) {
		super(a);
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

	public static boolean a(@Nullable le le) {
		if (le == null) {
			return false;
		} else if (!le.c("pages", 9)) {
			return false;
		} else {
			lk lk2 = le.d("pages", 8);

			for (int integer3 = 0; integer3 < lk2.size(); integer3++) {
				String string4 = lk2.j(integer3);
				if (string4.length() > 32767) {
					return false;
				}
			}

			return true;
		}
	}
}
