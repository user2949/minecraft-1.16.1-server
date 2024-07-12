public abstract class bgj extends bhp<amz> {
	private final amz d;
	private final bgr e;
	protected final bqb c;
	private final bmx<? extends bmg> f;

	protected bgj(bhk<?> bhk, bmx<? extends bmg> bmx, int integer, beb beb) {
		this(bhk, bmx, integer, beb, new anm(3), new bhv(4));
	}

	protected bgj(bhk<?> bhk, bmx<? extends bmg> bmx, int integer, beb beb, amz amz, bgr bgr) {
		super(bhk, integer);
		this.f = bmx;
		a(amz, 3);
		a(bgr, 4);
		this.d = amz;
		this.e = bgr;
		this.c = beb.e.l;
		this.a(new bhw(amz, 0, 56, 17));
		this.a(new bgz(this, amz, 1, 56, 53));
		this.a(new bhb(beb.e, amz, 2, 116, 35));

		for (int integer8 = 0; integer8 < 3; integer8++) {
			for (int integer9 = 0; integer9 < 9; integer9++) {
				this.a(new bhw(beb, integer9 + integer8 * 9 + 9, 8 + integer9 * 18, 84 + integer8 * 18));
			}
		}

		for (int integer8 = 0; integer8 < 9; integer8++) {
			this.a(new bhw(beb, integer8, 8 + integer8 * 18, 142));
		}

		this.a(bgr);
	}

	@Override
	public void a(bee bee) {
		if (this.d instanceof bhz) {
			((bhz)this.d).a(bee);
		}
	}

	@Override
	public void e() {
		this.d.aa_();
	}

	@Override
	public void a(boolean boolean1, bmu<?> bmu, ze ze) {
		new tz<>(this).a(ze, (bmu<amz>)bmu, boolean1);
	}

	@Override
	public boolean a(bmu<? super amz> bmu) {
		return bmu.a(this.d, this.c);
	}

	@Override
	public int f() {
		return 2;
	}

	@Override
	public int g() {
		return 1;
	}

	@Override
	public int h() {
		return 1;
	}

	@Override
	public boolean a(bec bec) {
		return this.d.a(bec);
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
			} else if (integer != 1 && integer != 0) {
				if (this.a(bki6)) {
					if (!this.a(bki6, 0, 1, false)) {
						return bki.b;
					}
				} else if (this.b(bki6)) {
					if (!this.a(bki6, 1, 2, false)) {
						return bki.b;
					}
				} else if (integer >= 3 && integer < 30) {
					if (!this.a(bki6, 30, 39, false)) {
						return bki.b;
					}
				} else if (integer >= 30 && integer < 39 && !this.a(bki6, 3, 30, false)) {
					return bki.b;
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

	protected boolean a(bki bki) {
		return this.c.o().a(this.f, new anm(bki), this.c).isPresent();
	}

	protected boolean b(bki bki) {
		return cdb.b(bki);
	}
}
