public class bgp extends bgi {
	private final amz c;
	private final int d;

	private bgp(bhk<?> bhk, int integer2, beb beb, int integer4) {
		this(bhk, integer2, beb, new anm(9 * integer4), integer4);
	}

	public static bgp a(int integer, beb beb) {
		return new bgp(bhk.a, integer, beb, 1);
	}

	public static bgp b(int integer, beb beb) {
		return new bgp(bhk.b, integer, beb, 2);
	}

	public static bgp c(int integer, beb beb) {
		return new bgp(bhk.c, integer, beb, 3);
	}

	public static bgp d(int integer, beb beb) {
		return new bgp(bhk.d, integer, beb, 4);
	}

	public static bgp e(int integer, beb beb) {
		return new bgp(bhk.e, integer, beb, 5);
	}

	public static bgp f(int integer, beb beb) {
		return new bgp(bhk.f, integer, beb, 6);
	}

	public static bgp a(int integer, beb beb, amz amz) {
		return new bgp(bhk.c, integer, beb, amz, 3);
	}

	public static bgp b(int integer, beb beb, amz amz) {
		return new bgp(bhk.f, integer, beb, amz, 6);
	}

	public bgp(bhk<?> bhk, int integer2, beb beb, amz amz, int integer5) {
		super(bhk, integer2);
		a(amz, integer5 * 9);
		this.c = amz;
		this.d = integer5;
		amz.c_(beb.e);
		int integer7 = (this.d - 4) * 18;

		for (int integer8 = 0; integer8 < this.d; integer8++) {
			for (int integer9 = 0; integer9 < 9; integer9++) {
				this.a(new bhw(amz, integer9 + integer8 * 9, 8 + integer9 * 18, 18 + integer8 * 18));
			}
		}

		for (int integer8 = 0; integer8 < 3; integer8++) {
			for (int integer9 = 0; integer9 < 9; integer9++) {
				this.a(new bhw(beb, integer9 + integer8 * 9 + 9, 8 + integer9 * 18, 103 + integer8 * 18 + integer7));
			}
		}

		for (int integer8 = 0; integer8 < 9; integer8++) {
			this.a(new bhw(beb, integer8, 8 + integer8 * 18, 161 + integer7));
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
			if (integer < this.d * 9) {
				if (!this.a(bki6, this.d * 9, this.a.size(), true)) {
					return bki.b;
				}
			} else if (!this.a(bki6, 0, this.d * 9, false)) {
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

	public amz e() {
		return this.c;
	}
}
