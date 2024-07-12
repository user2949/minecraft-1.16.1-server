public enum cyb implements cyp, cyu {
	INSTANCE;

	@Override
	public int a(cxn cxn, cxi cxi2, cxi cxi3, int integer4, int integer5) {
		int integer7 = cxi2.a(this.a(integer4), this.b(integer5));
		int integer8 = cxi3.a(this.a(integer4), this.b(integer5));
		if (!cxz.a(integer7)) {
			return integer7;
		} else {
			int integer9 = 8;
			int integer10 = 4;

			for (int integer11 = -8; integer11 <= 8; integer11 += 4) {
				for (int integer12 = -8; integer12 <= 8; integer12 += 4) {
					int integer13 = cxi2.a(this.a(integer4 + integer11), this.b(integer5 + integer12));
					if (!cxz.a(integer13)) {
						if (integer8 == cxz.a) {
							return cxz.b;
						}

						if (integer8 == cxz.e) {
							return cxz.d;
						}
					}
				}
			}

			if (integer7 == cxz.h) {
				if (integer8 == cxz.b) {
					return cxz.g;
				}

				if (integer8 == cxz.c) {
					return cxz.h;
				}

				if (integer8 == cxz.d) {
					return cxz.i;
				}

				if (integer8 == cxz.e) {
					return cxz.j;
				}
			}

			return integer8;
		}
	}
}
