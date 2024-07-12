import javax.annotation.Nullable;

public class yy {
	@Nullable
	protected static fu a(zd zd, int integer2, int integer3, boolean boolean4) {
		fu.a a5 = new fu.a(integer2, 0, integer3);
		bre bre6 = zd.v(a5);
		boolean boolean7 = zd.m().e();
		cfj cfj8 = bre6.A().a();
		if (boolean4 && !cfj8.b().a(acx.U)) {
			return null;
		} else {
			chj chj9 = zd.d(integer2 >> 4, integer3 >> 4);
			int integer10 = boolean7 ? zd.i().g().c() : chj9.a(cio.a.MOTION_BLOCKING, integer2 & 15, integer3 & 15);
			if (integer10 < 0) {
				return null;
			} else {
				int integer11 = chj9.a(cio.a.WORLD_SURFACE, integer2 & 15, integer3 & 15);
				if (integer11 <= integer10 && integer11 > chj9.a(cio.a.OCEAN_FLOOR, integer2 & 15, integer3 & 15)) {
					return null;
				} else {
					for (int integer12 = integer10 + 1; integer12 >= 0; integer12--) {
						a5.d(integer2, integer12, integer3);
						cfj cfj13 = zd.d_(a5);
						if (!cfj13.m().c()) {
							break;
						}

						if (cfj13.equals(cfj8)) {
							return a5.b().h();
						}
					}

					return null;
				}
			}
		}
	}

	@Nullable
	public static fu a(zd zd, bph bph, boolean boolean3) {
		for (int integer4 = bph.d(); integer4 <= bph.f(); integer4++) {
			for (int integer5 = bph.e(); integer5 <= bph.g(); integer5++) {
				fu fu6 = a(zd, integer4, integer5, boolean3);
				if (fu6 != null) {
					return fu6;
				}
			}
		}

		return null;
	}
}
