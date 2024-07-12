public abstract class yz extends cwm {
	protected yz(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	@Override
	protected boolean a(long long1) {
		return long1 == Long.MAX_VALUE;
	}

	@Override
	protected void a(long long1, int integer, boolean boolean3) {
		for (int integer6 = -1; integer6 <= 1; integer6++) {
			for (int integer7 = -1; integer7 <= 1; integer7++) {
				for (int integer8 = -1; integer8 <= 1; integer8++) {
					long long9 = go.a(long1, integer6, integer7, integer8);
					if (long9 != long1) {
						this.b(long1, long9, integer, boolean3);
					}
				}
			}
		}
	}

	@Override
	protected int a(long long1, long long2, int integer) {
		int integer7 = integer;

		for (int integer8 = -1; integer8 <= 1; integer8++) {
			for (int integer9 = -1; integer9 <= 1; integer9++) {
				for (int integer10 = -1; integer10 <= 1; integer10++) {
					long long11 = go.a(long1, integer8, integer9, integer10);
					if (long11 == long1) {
						long11 = Long.MAX_VALUE;
					}

					if (long11 != long2) {
						int integer13 = this.b(long11, long1, this.c(long11));
						if (integer7 > integer13) {
							integer7 = integer13;
						}

						if (integer7 == 0) {
							return integer7;
						}
					}
				}
			}
		}

		return integer7;
	}

	@Override
	protected int b(long long1, long long2, int integer) {
		return long1 == Long.MAX_VALUE ? this.b(long2) : integer + 1;
	}

	protected abstract int b(long long1);

	public void b(long long1, int integer, boolean boolean3) {
		this.a(Long.MAX_VALUE, long1, integer, boolean3);
	}
}
