import org.apache.commons.lang3.mutable.MutableInt;

public final class cwj extends cwo<cwk.a, cwk> {
	private static final fz[] e = fz.values();
	private final fu.a f = new fu.a();

	public cwj(chl chl) {
		super(chl, bqi.BLOCK, new cwk(chl));
	}

	private int d(long long1) {
		int integer4 = fu.b(long1);
		int integer5 = fu.c(long1);
		int integer6 = fu.d(long1);
		bpg bpg7 = this.a.c(integer4 >> 4, integer6 >> 4);
		return bpg7 != null ? bpg7.h(this.f.d(integer4, integer5, integer6)) : 0;
	}

	@Override
	protected int b(long long1, long long2, int integer) {
		if (long2 == Long.MAX_VALUE) {
			return 15;
		} else if (long1 == Long.MAX_VALUE) {
			return integer + 15 - this.d(long2);
		} else if (integer >= 15) {
			return integer;
		} else {
			int integer7 = Integer.signum(fu.b(long2) - fu.b(long1));
			int integer8 = Integer.signum(fu.c(long2) - fu.c(long1));
			int integer9 = Integer.signum(fu.d(long2) - fu.d(long1));
			fz fz10 = fz.a(integer7, integer8, integer9);
			if (fz10 == null) {
				return 15;
			} else {
				MutableInt mutableInt11 = new MutableInt();
				cfj cfj12 = this.a(long2, mutableInt11);
				if (mutableInt11.getValue() >= 15) {
					return 15;
				} else {
					cfj cfj13 = this.a(long1, null);
					dfg dfg14 = this.a(cfj13, long1, fz10);
					dfg dfg15 = this.a(cfj12, long2, fz10.f());
					return dfd.b(dfg14, dfg15) ? 15 : integer + Math.max(1, mutableInt11.getValue());
				}
			}
		}
	}

	@Override
	protected void a(long long1, int integer, boolean boolean3) {
		long long6 = go.e(long1);

		for (fz fz11 : e) {
			long long12 = fu.a(long1, fz11);
			long long14 = go.e(long12);
			if (long6 == long14 || this.c.g(long14)) {
				this.b(long1, long12, integer, boolean3);
			}
		}
	}

	@Override
	protected int a(long long1, long long2, int integer) {
		int integer7 = integer;
		if (Long.MAX_VALUE != long2) {
			int integer8 = this.b(Long.MAX_VALUE, long1, 0);
			if (integer > integer8) {
				integer7 = integer8;
			}

			if (integer7 == 0) {
				return integer7;
			}
		}

		long long8 = go.e(long1);
		chd chd10 = this.c.a(long8, true);

		for (fz fz14 : e) {
			long long15 = fu.a(long1, fz14);
			if (long15 != long2) {
				long long17 = go.e(long15);
				chd chd19;
				if (long8 == long17) {
					chd19 = chd10;
				} else {
					chd19 = this.c.a(long17, true);
				}

				if (chd19 != null) {
					int integer20 = this.b(long15, long1, this.a(chd19, long15));
					if (integer7 > integer20) {
						integer7 = integer20;
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
	public void a(fu fu, int integer) {
		this.c.d();
		this.a(Long.MAX_VALUE, fu.a(), 15 - integer, true);
	}
}
