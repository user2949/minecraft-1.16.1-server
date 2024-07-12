public enum cxq implements cyt {
	INSTANCE;

	@Override
	public int a(cxn cxn, int integer2, int integer3, int integer4, int integer5, int integer6) {
		if (cxz.b(integer6)) {
			int integer8 = 0;
			if (cxz.b(integer2)) {
				integer8++;
			}

			if (cxz.b(integer3)) {
				integer8++;
			}

			if (cxz.b(integer5)) {
				integer8++;
			}

			if (cxz.b(integer4)) {
				integer8++;
			}

			if (integer8 > 3) {
				if (integer6 == cxz.a) {
					return cxz.f;
				}

				if (integer6 == cxz.b) {
					return cxz.g;
				}

				if (integer6 == cxz.c) {
					return cxz.h;
				}

				if (integer6 == cxz.d) {
					return cxz.i;
				}

				if (integer6 == cxz.e) {
					return cxz.j;
				}

				return cxz.h;
			}
		}

		return integer6;
	}
}
