public abstract class ys extends cwm {
	protected ys(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	@Override
	protected boolean a(long long1) {
		return long1 == bph.a;
	}

	@Override
	protected void a(long long1, int integer, boolean boolean3) {
		bph bph6 = new bph(long1);
		int integer7 = bph6.b;
		int integer8 = bph6.c;

		for (int integer9 = -1; integer9 <= 1; integer9++) {
			for (int integer10 = -1; integer10 <= 1; integer10++) {
				long long11 = bph.a(integer7 + integer9, integer8 + integer10);
				if (long11 != long1) {
					this.b(long1, long11, integer, boolean3);
				}
			}
		}
	}

	@Override
	protected int a(long long1, long long2, int integer) {
		int integer7 = integer;
		bph bph8 = new bph(long1);
		int integer9 = bph8.b;
		int integer10 = bph8.c;

		for (int integer11 = -1; integer11 <= 1; integer11++) {
			for (int integer12 = -1; integer12 <= 1; integer12++) {
				long long13 = bph.a(integer9 + integer11, integer10 + integer12);
				if (long13 == long1) {
					long13 = bph.a;
				}

				if (long13 != long2) {
					int integer15 = this.b(long13, long1, this.c(long13));
					if (integer7 > integer15) {
						integer7 = integer15;
					}

					if (integer7 == 0) {
						return integer7;
					}
				}
			}
		}

		return integer7;
	}

	@Override
	protected int b(long long1, long long2, int integer) {
		return long1 == bph.a ? this.b(long2) : integer + 1;
	}

	protected abstract int b(long long1);

	public void b(long long1, int integer, boolean boolean3) {
		this.a(bph.a, long1, integer, boolean3);
	}
}
