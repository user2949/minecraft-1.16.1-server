public class cxr {
	public static enum a implements cyt {
		INSTANCE;

		@Override
		public int a(cxn cxn, int integer2, int integer3, int integer4, int integer5, int integer6) {
			return integer6 != 1 || integer2 != 3 && integer3 != 3 && integer5 != 3 && integer4 != 3 && integer2 != 4 && integer3 != 4 && integer5 != 4 && integer4 != 4
				? integer6
				: 2;
		}
	}

	public static enum b implements cyt {
		INSTANCE;

		@Override
		public int a(cxn cxn, int integer2, int integer3, int integer4, int integer5, int integer6) {
			return integer6 != 4 || integer2 != 1 && integer3 != 1 && integer5 != 1 && integer4 != 1 && integer2 != 2 && integer3 != 2 && integer5 != 2 && integer4 != 2
				? integer6
				: 3;
		}
	}

	public static enum c implements cyr {
		INSTANCE;

		@Override
		public int a(cxn cxn, int integer) {
			if (!cxz.b(integer) && cxn.a(13) == 0) {
				integer |= 1 + cxn.a(15) << 8 & 3840;
			}

			return integer;
		}
	}
}
