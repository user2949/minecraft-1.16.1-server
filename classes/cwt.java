import org.apache.commons.lang3.mutable.MutableInt;

public final class cwt extends cwo<cwu.a, cwu> {
	private static final fz[] e = fz.values();
	private static final fz[] f = new fz[]{fz.NORTH, fz.SOUTH, fz.WEST, fz.EAST};

	public cwt(chl chl) {
		super(chl, bqi.SKY, new cwu(chl));
	}

	@Override
	protected int b(long long1, long long2, int integer) {
		if (long2 == Long.MAX_VALUE) {
			return 15;
		} else {
			if (long1 == Long.MAX_VALUE) {
				if (!this.c.m(long2)) {
					return 15;
				}

				integer = 0;
			}

			if (integer >= 15) {
				return integer;
			} else {
				MutableInt mutableInt7 = new MutableInt();
				cfj cfj8 = this.a(long2, mutableInt7);
				if (mutableInt7.getValue() >= 15) {
					return 15;
				} else {
					int integer9 = fu.b(long1);
					int integer10 = fu.c(long1);
					int integer11 = fu.d(long1);
					int integer12 = fu.b(long2);
					int integer13 = fu.c(long2);
					int integer14 = fu.d(long2);
					boolean boolean15 = integer9 == integer12 && integer11 == integer14;
					int integer16 = Integer.signum(integer12 - integer9);
					int integer17 = Integer.signum(integer13 - integer10);
					int integer18 = Integer.signum(integer14 - integer11);
					fz fz19;
					if (long1 == Long.MAX_VALUE) {
						fz19 = fz.DOWN;
					} else {
						fz19 = fz.a(integer16, integer17, integer18);
					}

					cfj cfj20 = this.a(long1, null);
					if (fz19 != null) {
						dfg dfg21 = this.a(cfj20, long1, fz19);
						dfg dfg22 = this.a(cfj8, long2, fz19.f());
						if (dfd.b(dfg21, dfg22)) {
							return 15;
						}
					} else {
						dfg dfg21 = this.a(cfj20, long1, fz.DOWN);
						if (dfd.b(dfg21, dfd.a())) {
							return 15;
						}

						int integer22 = boolean15 ? -1 : 0;
						fz fz23 = fz.a(integer16, integer22, integer18);
						if (fz23 == null) {
							return 15;
						}

						dfg dfg24 = this.a(cfj8, long2, fz23.f());
						if (dfd.b(dfd.a(), dfg24)) {
							return 15;
						}
					}

					boolean boolean21 = long1 == Long.MAX_VALUE || boolean15 && integer10 > integer13;
					return boolean21 && integer == 0 && mutableInt7.getValue() == 0 ? 0 : integer + Math.max(1, mutableInt7.getValue());
				}
			}
		}
	}

	@Override
	protected void a(long long1, int integer, boolean boolean3) {
		long long6 = go.e(long1);
		int integer8 = fu.c(long1);
		int integer9 = go.b(integer8);
		int integer10 = go.a(integer8);
		int integer11;
		if (integer9 != 0) {
			integer11 = 0;
		} else {
			int integer12 = 0;

			while (!this.c.g(go.a(long6, 0, -integer12 - 1, 0)) && this.c.a(integer10 - integer12 - 1)) {
				integer12++;
			}

			integer11 = integer12;
		}

		long long12 = fu.a(long1, 0, -1 - integer11 * 16, 0);
		long long14 = go.e(long12);
		if (long6 == long14 || this.c.g(long14)) {
			this.b(long1, long12, integer, boolean3);
		}

		long long16 = fu.a(long1, fz.UP);
		long long18 = go.e(long16);
		if (long6 == long18 || this.c.g(long18)) {
			this.b(long1, long16, integer, boolean3);
		}

		for (fz fz23 : f) {
			int integer24 = 0;

			do {
				long long25 = fu.a(long1, fz23.i(), -integer24, fz23.k());
				long long27 = go.e(long25);
				if (long6 == long27) {
					this.b(long1, long25, integer, boolean3);
					break;
				}

				if (this.c.g(long27)) {
					this.b(long1, long25, integer, boolean3);
				}
			} while (++integer24 > integer11 * 16);
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
			long long17 = go.e(long15);
			chd chd19;
			if (long8 == long17) {
				chd19 = chd10;
			} else {
				chd19 = this.c.a(long17, true);
			}

			if (chd19 != null) {
				if (long15 != long2) {
					int integer20 = this.b(long15, long1, this.a(chd19, long15));
					if (integer7 > integer20) {
						integer7 = integer20;
					}

					if (integer7 == 0) {
						return integer7;
					}
				}
			} else if (fz14 != fz.DOWN) {
				for (long15 = fu.f(long15); !this.c.g(long17) && !this.c.n(long17); long15 = fu.a(long15, 0, 16, 0)) {
					long17 = go.a(long17, fz.UP);
				}

				chd chd20 = this.c.a(long17, true);
				if (long15 != long2) {
					int integer21;
					if (chd20 != null) {
						integer21 = this.b(long15, long1, this.a(chd20, long15));
					} else {
						integer21 = this.c.o(long17) ? 0 : 15;
					}

					if (integer7 > integer21) {
						integer7 = integer21;
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
	protected void f(long long1) {
		this.c.d();
		long long4 = go.e(long1);
		if (this.c.g(long4)) {
			super.f(long1);
		} else {
			for (long1 = fu.f(long1); !this.c.g(long4) && !this.c.n(long4); long1 = fu.a(long1, 0, 16, 0)) {
				long4 = go.a(long4, fz.UP);
			}

			if (this.c.g(long4)) {
				super.f(long1);
			}
		}
	}
}
