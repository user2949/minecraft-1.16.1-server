import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class bgo extends bgi {
	private final bgs d;
	private boolean e;
	private long f;
	public final amz c = new anm(2) {
		@Override
		public void Z_() {
			bgo.this.a(this);
			super.Z_();
		}
	};
	private final bhr g = new bhr() {
		@Override
		public void Z_() {
			bgo.this.a(this);
			super.Z_();
		}
	};

	public bgo(int integer, beb beb) {
		this(integer, beb, bgs.a);
	}

	public bgo(int integer, beb beb, bgs bgs) {
		super(bhk.w, integer);
		this.d = bgs;
		this.a(new bhw(this.c, 0, 15, 15) {
			@Override
			public boolean a(bki bki) {
				return bki.b() == bkk.nf;
			}
		});
		this.a(new bhw(this.c, 1, 15, 52) {
			@Override
			public boolean a(bki bki) {
				bke bke3 = bki.b();
				return bke3 == bkk.mb || bke3 == bkk.pb || bke3 == bkk.dP;
			}
		});
		this.a(new bhw(this.g, 2, 145, 39) {
			@Override
			public boolean a(bki bki) {
				return false;
			}

			@Override
			public bki a(int integer) {
				bki bki3 = super.a(integer);
				bki bki4 = (bki)bgs.a((BiFunction)((bqb, fu) -> {
					if (!bgo.this.e && bgo.this.c.a(1).b() == bkk.dP) {
						bki bki5 = bko.a(bqb, bgo.this.c.a(0));
						if (bki5 != null) {
							bki5.e(1);
							return bki5;
						}
					}

					return bki3;
				})).orElse(bki3);
				bgo.this.c.a(0, 1);
				bgo.this.c.a(1, 1);
				return bki4;
			}

			@Override
			protected void a(bki bki, int integer) {
				this.a(integer);
				super.a(bki, integer);
			}

			@Override
			public bki a(bec bec, bki bki) {
				bki.b().b(bki, bec.l, bec);
				bgs.a((BiConsumer<bqb, fu>)((bqb, fu) -> {
					long long4 = bqb.Q();
					if (bgo.this.f != long4) {
						bqb.a(null, fu, acl.pB, acm.BLOCKS, 1.0F, 1.0F);
						bgo.this.f = long4;
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
	}

	@Override
	public boolean a(bec bec) {
		return a(this.d, bec, bvs.lV);
	}

	@Override
	public void a(amz amz) {
		bki bki3 = this.c.a(0);
		bki bki4 = this.c.a(1);
		bki bki5 = this.g.a(2);
		if (bki5.a() || !bki3.a() && !bki4.a()) {
			if (!bki3.a() && !bki4.a()) {
				this.a(bki3, bki4, bki5);
			}
		} else {
			this.g.b(2);
		}
	}

	private void a(bki bki1, bki bki2, bki bki3) {
		this.d.a((BiConsumer<bqb, fu>)((bqb, fu) -> {
			bke bke7 = bki2.b();
			czv czv8 = bko.a(bki1, bqb);
			if (czv8 != null) {
				bki bki9;
				if (bke7 == bkk.mb && !czv8.h && czv8.f < 4) {
					bki9 = bki1.i();
					bki9.e(1);
					bki9.p().b("map_scale_direction", 1);
					this.c();
				} else if (bke7 == bkk.dP && !czv8.h) {
					bki9 = bki1.i();
					bki9.e(1);
					this.c();
				} else {
					if (bke7 != bkk.pb) {
						this.g.b(2);
						this.c();
						return;
					}

					bki9 = bki1.i();
					bki9.e(2);
					this.c();
				}

				if (!bki.b(bki9, bki3)) {
					this.g.a(2, bki9);
					this.c();
				}
			}
		}));
	}

	@Override
	public boolean a(bki bki, bhw bhw) {
		return bhw.c != this.g && super.a(bki, bhw);
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki bki7 = bki6;
			bke bke8 = bki6.b();
			bki4 = bki6.i();
			if (integer == 2) {
				if (this.c.a(1).b() == bkk.dP) {
					bki7 = (bki)this.d.a((BiFunction)((bqb, fu) -> {
						bki bki5 = bko.a(bqb, this.c.a(0));
						if (bki5 != null) {
							bki5.e(1);
							return bki5;
						} else {
							return bki6;
						}
					})).orElse(bki6);
				}

				bke8.b(bki7, bec.l, bec);
				if (!this.a(bki7, 3, 39, true)) {
					return bki.b;
				}

				bhw5.a(bki7, bki4);
			} else if (integer != 1 && integer != 0) {
				if (bke8 == bkk.nf) {
					if (!this.a(bki6, 0, 1, false)) {
						return bki.b;
					}
				} else if (bke8 != bkk.mb && bke8 != bkk.pb && bke8 != bkk.dP) {
					if (integer >= 3 && integer < 30) {
						if (!this.a(bki6, 30, 39, false)) {
							return bki.b;
						}
					} else if (integer >= 30 && integer < 39 && !this.a(bki6, 3, 30, false)) {
						return bki.b;
					}
				} else if (!this.a(bki6, 1, 2, false)) {
					return bki.b;
				}
			} else if (!this.a(bki6, 3, 39, false)) {
				return bki.b;
			}

			if (bki7.a()) {
				bhw5.d(bki.b);
			}

			bhw5.d();
			if (bki7.E() == bki4.E()) {
				return bki.b;
			}

			this.e = true;
			bhw5.a(bec, bki7);
			this.e = false;
			this.c();
		}

		return bki4;
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.g.b(2);
		this.d.a((BiConsumer<bqb, fu>)((bqb, fu) -> this.a(bec, bec.l, this.c)));
	}
}
