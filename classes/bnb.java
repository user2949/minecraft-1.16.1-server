public class bnb extends bmn {
	public bnb(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		bki bki4 = bki.b;
		bki bki5 = bki.b;

		for (int integer6 = 0; integer6 < bgu.ab_(); integer6++) {
			bki bki7 = bgu.a(integer6);
			if (!bki7.a()) {
				if (bki7.b() instanceof bij) {
					if (!bki5.a()) {
						return false;
					}

					bki5 = bki7;
				} else {
					if (bki7.b() != bkk.qm) {
						return false;
					}

					if (!bki4.a()) {
						return false;
					}

					if (bki7.b("BlockEntityTag") != null) {
						return false;
					}

					bki4 = bki7;
				}
			}
		}

		return !bki4.a() && !bki5.a();
	}

	public bki a(bgu bgu) {
		bki bki3 = bki.b;
		bki bki4 = bki.b;

		for (int integer5 = 0; integer5 < bgu.ab_(); integer5++) {
			bki bki6 = bgu.a(integer5);
			if (!bki6.a()) {
				if (bki6.b() instanceof bij) {
					bki3 = bki6;
				} else if (bki6.b() == bkk.qm) {
					bki4 = bki6.i();
				}
			}
		}

		if (bki4.a()) {
			return bki4;
		} else {
			le le5 = bki3.b("BlockEntityTag");
			le le6 = le5 == null ? new le() : le5.g();
			le6.b("Base", ((bij)bki3.b()).b().b());
			bki4.a("BlockEntityTag", le6);
			return bki4;
		}
	}

	@Override
	public bmw<?> ai_() {
		return bmw.l;
	}
}
