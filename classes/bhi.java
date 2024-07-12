import java.util.function.BiConsumer;

public class bhi extends bgi {
	private final bgs c;
	private final bgw d = bgw.a();
	private Runnable e = () -> {
	};
	private final bhw f;
	private final bhw g;
	private final bhw h;
	private final bhw i;
	private long j;
	private final amz k = new anm(3) {
		@Override
		public void Z_() {
			super.Z_();
			bhi.this.a(this);
			bhi.this.e.run();
		}
	};
	private final amz l = new anm(1) {
		@Override
		public void Z_() {
			super.Z_();
			bhi.this.e.run();
		}
	};

	public bhi(int integer, beb beb) {
		this(integer, beb, bgs.a);
	}

	public bhi(int integer, beb beb, bgs bgs) {
		super(bhk.r, integer);
		this.c = bgs;
		this.f = this.a(new bhw(this.k, 0, 13, 26) {
			@Override
			public boolean a(bki bki) {
				return bki.b() instanceof bij;
			}
		});
		this.g = this.a(new bhw(this.k, 1, 33, 26) {
			@Override
			public boolean a(bki bki) {
				return bki.b() instanceof bjf;
			}
		});
		this.h = this.a(new bhw(this.k, 2, 23, 45) {
			@Override
			public boolean a(bki bki) {
				return bki.b() instanceof bik;
			}
		});
		this.i = this.a(new bhw(this.l, 0, 143, 58) {
			@Override
			public boolean a(bki bki) {
				return false;
			}

			@Override
			public bki a(bec bec, bki bki) {
				bhi.this.f.a(1);
				bhi.this.g.a(1);
				if (!bhi.this.f.f() || !bhi.this.g.f()) {
					bhi.this.d.a(0);
				}

				bgs.a((BiConsumer<bqb, fu>)((bqb, fu) -> {
					long long4 = bqb.Q();
					if (bhi.this.j != long4) {
						bqb.a(null, fu, acl.pA, acm.BLOCKS, 1.0F, 1.0F);
						bhi.this.j = long4;
					}
				}));
				return super.a(bec, bki);
			}
		});

		for (int integer5 = 0; integer5 < 3; integer5++) {
			for (int integer6 = 0; integer6 < 9; integer6++) {
				this.a(new bhw(beb, integer6 + integer5 * 9 + 9, 8 + integer6 * 18, 84 + integer5 * 18));
			}
		}

		for (int integer5 = 0; integer5 < 9; integer5++) {
			this.a(new bhw(beb, integer5, 8 + integer5 * 18, 142));
		}

		this.a(this.d);
	}

	@Override
	public boolean a(bec bec) {
		return a(this.c, bec, bvs.lR);
	}

	@Override
	public boolean a(bec bec, int integer) {
		if (integer > 0 && integer <= cdd.R) {
			this.d.a(integer);
			this.j();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void a(amz amz) {
		bki bki3 = this.f.e();
		bki bki4 = this.g.e();
		bki bki5 = this.h.e();
		bki bki6 = this.i.e();
		if (bki6.a() || !bki3.a() && !bki4.a() && this.d.b() > 0 && (this.d.b() < cdd.P - cdd.Q || !bki5.a())) {
			if (!bki5.a() && bki5.b() instanceof bik) {
				le le7 = bki3.a("BlockEntityTag");
				boolean boolean8 = le7.c("Patterns", 9) && !bki3.a() && le7.d("Patterns", 10).size() >= 6;
				if (boolean8) {
					this.d.a(0);
				} else {
					this.d.a(((bik)bki5.b()).b().ordinal());
				}
			}
		} else {
			this.i.d(bki.b);
			this.d.a(0);
		}

		this.j();
		this.c();
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			if (integer == this.i.d) {
				if (!this.a(bki6, 4, 40, true)) {
					return bki.b;
				}

				bhw5.a(bki6, bki4);
			} else if (integer != this.g.d && integer != this.f.d && integer != this.h.d) {
				if (bki6.b() instanceof bij) {
					if (!this.a(bki6, this.f.d, this.f.d + 1, false)) {
						return bki.b;
					}
				} else if (bki6.b() instanceof bjf) {
					if (!this.a(bki6, this.g.d, this.g.d + 1, false)) {
						return bki.b;
					}
				} else if (bki6.b() instanceof bik) {
					if (!this.a(bki6, this.h.d, this.h.d + 1, false)) {
						return bki.b;
					}
				} else if (integer >= 4 && integer < 31) {
					if (!this.a(bki6, 31, 40, false)) {
						return bki.b;
					}
				} else if (integer >= 31 && integer < 40 && !this.a(bki6, 4, 31, false)) {
					return bki.b;
				}
			} else if (!this.a(bki6, 4, 40, false)) {
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
		this.c.a((BiConsumer<bqb, fu>)((bqb, fu) -> this.a(bec, bec.l, this.k)));
	}

	private void j() {
		if (this.d.b() > 0) {
			bki bki2 = this.f.e();
			bki bki3 = this.g.e();
			bki bki4 = bki.b;
			if (!bki2.a() && !bki3.a()) {
				bki4 = bki2.i();
				bki4.e(1);
				cdd cdd5 = cdd.values()[this.d.b()];
				bje bje6 = ((bjf)bki3.b()).d();
				le le7 = bki4.a("BlockEntityTag");
				lk lk8;
				if (le7.c("Patterns", 9)) {
					lk8 = le7.d("Patterns", 10);
				} else {
					lk8 = new lk();
					le7.a("Patterns", lk8);
				}

				le le9 = new le();
				le9.a("Pattern", cdd5.b());
				le9.b("Color", bje6.b());
				lk8.add(le9);
			}

			if (!bki.b(bki4, this.i.e())) {
				this.i.d(bki4);
			}
		}
	}
}
