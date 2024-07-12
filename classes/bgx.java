public class bgx extends bgi {
	private final amz c;

	public bgx(int integer, beb beb) {
		this(integer, beb, new anm(9));
	}

	public bgx(int integer, beb beb, amz amz) {
		super(bhk.g, integer);
		a(amz, 9);
		this.c = amz;
		amz.c_(beb.e);

		for (int integer5 = 0; integer5 < 3; integer5++) {
			for (int integer6 = 0; integer6 < 3; integer6++) {
				this.a(new bhw(amz, integer6 + integer5 * 3, 62 + integer6 * 18, 17 + integer5 * 18));
			}
		}

		for (int integer5 = 0; integer5 < 3; integer5++) {
			for (int integer6 = 0; integer6 < 9; integer6++) {
				this.a(new bhw(beb, integer6 + integer5 * 9 + 9, 8 + integer6 * 18, 84 + integer5 * 18));
			}
		}

		for (int integer5 = 0; integer5 < 9; integer5++) {
			this.a(new bhw(beb, integer5, 8 + integer5 * 18, 142));
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
			if (integer < 9) {
				if (!this.a(bki6, 9, 45, true)) {
					return bki.b;
				}
			} else if (!this.a(bki6, 0, 9, false)) {
				return bki.b;
			}

			if (bki6.a()) {
				bhw5.d(bki.b);
			} else {
				bhw5.d();
			}

			if (bki6.E() == bki4.E()) {
				return bki.b;
			}

			bhw5.a(bec, bki6);
		}

		return bki4;
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.c.b_(bec);
	}
}
