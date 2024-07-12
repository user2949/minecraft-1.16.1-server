public enum cxs implements cyq {
	INSTANCE;

	@Override
	public int a(cxn cxn, int integer2, int integer3, int integer4, int integer5, int integer6) {
		if (!cxz.b(integer6) || cxz.b(integer5) && cxz.b(integer4) && cxz.b(integer2) && cxz.b(integer3)) {
			if (!cxz.b(integer6) && (cxz.b(integer5) || cxz.b(integer2) || cxz.b(integer4) || cxz.b(integer3)) && cxn.a(5) == 0) {
				if (cxz.b(integer5)) {
					return integer6 == 4 ? 4 : integer5;
				}

				if (cxz.b(integer2)) {
					return integer6 == 4 ? 4 : integer2;
				}

				if (cxz.b(integer4)) {
					return integer6 == 4 ? 4 : integer4;
				}

				if (cxz.b(integer3)) {
					return integer6 == 4 ? 4 : integer3;
				}
			}

			return integer6;
		} else {
			int integer8 = 1;
			int integer9 = 1;
			if (!cxz.b(integer5) && cxn.a(integer8++) == 0) {
				integer9 = integer5;
			}

			if (!cxz.b(integer4) && cxn.a(integer8++) == 0) {
				integer9 = integer4;
			}

			if (!cxz.b(integer2) && cxn.a(integer8++) == 0) {
				integer9 = integer2;
			}

			if (!cxz.b(integer3) && cxn.a(integer8++) == 0) {
				integer9 = integer3;
			}

			if (cxn.a(3) == 0) {
				return integer9;
			} else {
				return integer9 == 4 ? 4 : integer6;
			}
		}
	}
}
