public class bhd extends bgi {
	private final amz c;

	public bhd(int integer, beb beb) {
		this(integer, beb, new anm(5));
	}

	public bhd(int integer, beb beb, amz amz) {
		super(bhk.p, integer);
		this.c = amz;
		a(amz, 5);
		amz.c_(beb.e);
		int integer5 = 51;

		for (int integer6 = 0; integer6 < 5; integer6++) {
			this.a(new bhw(amz, integer6, 44 + integer6 * 18, 20));
		}

		for (int integer6 = 0; integer6 < 3; integer6++) {
			for (int integer7 = 0; integer7 < 9; integer7++) {
				this.a(new bhw(beb, integer7 + integer6 * 9 + 9, 8 + integer7 * 18, integer6 * 18 + 51));
			}
		}

		for (int integer6 = 0; integer6 < 9; integer6++) {
			this.a(new bhw(beb, integer6, 8 + integer6 * 18, 109));
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
