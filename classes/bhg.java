import java.util.function.BiConsumer;
import javax.annotation.Nullable;

public abstract class bhg extends bgi {
	protected final amz c = new bhr();
	protected final amz d = new anm(2) {
		@Override
		public void Z_() {
			super.Z_();
			bhg.this.a(this);
		}
	};
	protected final bgs e;
	protected final bec f;

	protected abstract boolean b(bec bec, boolean boolean2);

	protected abstract bki a(bec bec, bki bki);

	protected abstract boolean a(cfj cfj);

	public bhg(@Nullable bhk<?> bhk, int integer, beb beb, bgs bgs) {
		super(bhk, integer);
		this.e = bgs;
		this.f = beb.e;
		this.a(new bhw(this.d, 0, 27, 47));
		this.a(new bhw(this.d, 1, 76, 47));
		this.a(new bhw(this.c, 2, 134, 47) {
			@Override
			public boolean a(bki bki) {
				return false;
			}

			@Override
			public boolean a(bec bec) {
				return bhg.this.b(bec, this.f());
			}

			@Override
			public bki a(bec bec, bki bki) {
				return bhg.this.a(bec, bki);
			}
		});

		for (int integer6 = 0; integer6 < 3; integer6++) {
			for (int integer7 = 0; integer7 < 9; integer7++) {
				this.a(new bhw(beb, integer7 + integer6 * 9 + 9, 8 + integer7 * 18, 84 + integer6 * 18));
			}
		}

		for (int integer6 = 0; integer6 < 9; integer6++) {
			this.a(new bhw(beb, integer6, 8 + integer6 * 18, 142));
		}
	}

	public abstract void e();

	@Override
	public void a(amz amz) {
		super.a(amz);
		if (amz == this.d) {
			this.e();
		}
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.e.a((BiConsumer<bqb, fu>)((bqb, fu) -> this.a(bec, bqb, this.d)));
	}

	@Override
	public boolean a(bec bec) {
		return this.e.a((bqb, fu) -> !this.a(bqb.d_(fu)) ? false : bec.g((double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5) <= 64.0, true);
	}

	protected boolean a(bki bki) {
		return false;
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			if (integer == 2) {
				if (!this.a(bki6, 3, 39, true)) {
					return bki.b;
				}

				bhw5.a(bki6, bki4);
			} else if (integer != 0 && integer != 1) {
				if (integer >= 3 && integer < 39) {
					int integer7 = this.a(bki4) ? 1 : 0;
					if (!this.a(bki6, integer7, 2, false)) {
						return bki.b;
					}
				}
			} else if (!this.a(bki6, 3, 39, false)) {
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
}
