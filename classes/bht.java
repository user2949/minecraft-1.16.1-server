public class bht extends bgi {
	private final amz c;

	public bht(int integer, beb beb) {
		this(integer, beb, new anm(27));
	}

	public bht(int integer, beb beb, amz amz) {
		super(bhk.t, integer);
		a(amz, 27);
		this.c = amz;
		amz.c_(beb.e);
		int integer5 = 3;
		int integer6 = 9;

		for (int integer7 = 0; integer7 < 3; integer7++) {
			for (int integer8 = 0; integer8 < 9; integer8++) {
				this.a(new bhu(amz, integer8 + integer7 * 9, 8 + integer8 * 18, 18 + integer7 * 18));
			}
		}

		for (int integer7 = 0; integer7 < 3; integer7++) {
			for (int integer8 = 0; integer8 < 9; integer8++) {
				this.a(new bhw(beb, integer8 + integer7 * 9 + 9, 8 + integer8 * 18, 84 + integer7 * 18));
			}
		}

		for (int integer7 = 0; integer7 < 9; integer7++) {
			this.a(new bhw(beb, integer7, 8 + integer7 * 18, 142));
		}
	}

	@Override
	public boolean a(bec bec) {
		return this.c.a(bec);
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			if (integer < this.c.ab_()) {
				if (!this.a(bki6, this.c.ab_(), this.a.size(), true)) {
					return bki.b;
				}
			} else if (!this.a(bki6, 0, this.c.ab_(), false)) {
				return bki.b;
			}

			if (bki6.a()) {
				bhw5.d(bki.b);
			} else {
				bhw5.d();
			}
		}

		return bki4;
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.c.b_(bec);
	}
}
