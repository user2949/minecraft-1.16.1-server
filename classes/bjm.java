public class bjm extends bke {
	public bjm(bke.a a) {
		super(a);
	}

	@Override
	public boolean e(bki bki) {
		return true;
	}

	@Override
	public boolean f_(bki bki) {
		return false;
	}

	public static lk d(bki bki) {
		le le2 = bki.o();
		return le2 != null ? le2.d("StoredEnchantments", 10) : new lk();
	}

	public static void a(bki bki, bnz bnz) {
		lk lk3 = d(bki);
		boolean boolean4 = true;
		uh uh5 = gl.ak.b(bnz.b);

		for (int integer6 = 0; integer6 < lk3.size(); integer6++) {
			le le7 = lk3.a(integer6);
			uh uh8 = uh.a(le7.l("id"));
			if (uh8 != null && uh8.equals(uh5)) {
				if (le7.h("lvl") < bnz.c) {
					le7.a("lvl", (short)bnz.c);
				}

				boolean4 = false;
				break;
			}
		}

		if (boolean4) {
			le le6 = new le();
			le6.a("id", String.valueOf(uh5));
			le6.a("lvl", (short)bnz.c);
			lk3.add(le6);
		}

		bki.p().a("StoredEnchantments", lk3);
	}

	public static bki a(bnz bnz) {
		bki bki2 = new bki(bkk.pp);
		a(bki2, bnz);
		return bki2;
	}

	@Override
	public void a(biy biy, gi<bki> gi) {
		if (biy == biy.g) {
			for (bnw bnw5 : gl.ak) {
				if (bnw5.b != null) {
					for (int integer6 = bnw5.e(); integer6 <= bnw5.a(); integer6++) {
						gi.add(a(new bnz(bnw5, integer6)));
					}
				}
			}
		} else if (biy.o().length != 0) {
			for (bnw bnw5x : gl.ak) {
				if (biy.a(bnw5x.b)) {
					gi.add(a(new bnz(bnw5x, bnw5x.a())));
				}
			}
		}
	}
}
