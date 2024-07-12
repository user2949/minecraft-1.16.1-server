import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class bgy extends bgi {
	private final amz f = new anm(2) {
		@Override
		public void Z_() {
			super.Z_();
			bgy.this.a(this);
		}
	};
	private final bgs g;
	private final Random h = new Random();
	private final bgw i = bgw.a();
	public final int[] c = new int[3];
	public final int[] d = new int[]{-1, -1, -1};
	public final int[] e = new int[]{-1, -1, -1};

	public bgy(int integer, beb beb) {
		this(integer, beb, bgs.a);
	}

	public bgy(int integer, beb beb, bgs bgs) {
		super(bhk.m, integer);
		this.g = bgs;
		this.a(new bhw(this.f, 0, 15, 47) {
			@Override
			public boolean a(bki bki) {
				return true;
			}

			@Override
			public int a() {
				return 1;
			}
		});
		this.a(new bhw(this.f, 1, 35, 47) {
			@Override
			public boolean a(bki bki) {
				return bki.b() == bkk.mv;
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

		this.a(bgw.a(this.c, 0));
		this.a(bgw.a(this.c, 1));
		this.a(bgw.a(this.c, 2));
		this.a(this.i).a(beb.e.eF());
		this.a(bgw.a(this.d, 0));
		this.a(bgw.a(this.d, 1));
		this.a(bgw.a(this.d, 2));
		this.a(bgw.a(this.e, 0));
		this.a(bgw.a(this.e, 1));
		this.a(bgw.a(this.e, 2));
	}

	@Override
	public void a(amz amz) {
		if (amz == this.f) {
			bki bki3 = amz.a(0);
			if (!bki3.a() && bki3.w()) {
				this.g.a((BiConsumer<bqb, fu>)((bqb, fu) -> {
					int integer5 = 0;

					for (int integer6 = -1; integer6 <= 1; integer6++) {
						for (int integer7 = -1; integer7 <= 1; integer7++) {
							if ((integer6 != 0 || integer7 != 0) && bqb.w(fu.b(integer7, 0, integer6)) && bqb.w(fu.b(integer7, 1, integer6))) {
								if (bqb.d_(fu.b(integer7 * 2, 0, integer6 * 2)).a(bvs.bI)) {
									integer5++;
								}

								if (bqb.d_(fu.b(integer7 * 2, 1, integer6 * 2)).a(bvs.bI)) {
									integer5++;
								}

								if (integer7 != 0 && integer6 != 0) {
									if (bqb.d_(fu.b(integer7 * 2, 0, integer6)).a(bvs.bI)) {
										integer5++;
									}

									if (bqb.d_(fu.b(integer7 * 2, 1, integer6)).a(bvs.bI)) {
										integer5++;
									}

									if (bqb.d_(fu.b(integer7, 0, integer6 * 2)).a(bvs.bI)) {
										integer5++;
									}

									if (bqb.d_(fu.b(integer7, 1, integer6 * 2)).a(bvs.bI)) {
										integer5++;
									}
								}
							}
						}
					}

					this.h.setSeed((long)this.i.b());

					for (int integer6 = 0; integer6 < 3; integer6++) {
						this.c[integer6] = bny.a(this.h, integer6, integer5, bki3);
						this.d[integer6] = -1;
						this.e[integer6] = -1;
						if (this.c[integer6] < integer6 + 1) {
							this.c[integer6] = 0;
						}
					}

					for (int integer6x = 0; integer6x < 3; integer6x++) {
						if (this.c[integer6x] > 0) {
							List<bnz> list7 = this.a(bki3, integer6x, this.c[integer6x]);
							if (list7 != null && !list7.isEmpty()) {
								bnz bnz8 = (bnz)list7.get(this.h.nextInt(list7.size()));
								this.d[integer6x] = gl.ak.a(bnz8.b);
								this.e[integer6x] = bnz8.c;
							}
						}
					}

					this.c();
				}));
			} else {
				for (int integer4 = 0; integer4 < 3; integer4++) {
					this.c[integer4] = 0;
					this.d[integer4] = -1;
					this.e[integer4] = -1;
				}
			}
		}
	}

	@Override
	public boolean a(bec bec, int integer) {
		bki bki4 = this.f.a(0);
		bki bki5 = this.f.a(1);
		int integer6 = integer + 1;
		if ((bki5.a() || bki5.E() < integer6) && !bec.bJ.d) {
			return false;
		} else if (this.c[integer] <= 0 || bki4.a() || (bec.bK < integer6 || bec.bK < this.c[integer]) && !bec.bJ.d) {
			return false;
		} else {
			this.g.a((BiConsumer<bqb, fu>)((bqb, fu) -> {
				bki bki9 = bki4;
				List<bnz> list10 = this.a(bki4, integer, this.c[integer]);
				if (!list10.isEmpty()) {
					bec.a(bki4, integer6);
					boolean boolean11 = bki4.b() == bkk.mc;
					if (boolean11) {
						bki9 = new bki(bkk.pp);
						le le12 = bki4.o();
						if (le12 != null) {
							bki9.c(le12.g());
						}

						this.f.a(0, bki9);
					}

					for (int integer12 = 0; integer12 < list10.size(); integer12++) {
						bnz bnz13 = (bnz)list10.get(integer12);
						if (boolean11) {
							bjm.a(bki9, bnz13);
						} else {
							bki9.a(bnz13.b, bnz13.c);
						}
					}

					if (!bec.bJ.d) {
						bki5.g(integer6);
						if (bki5.a()) {
							this.f.a(1, bki.b);
						}
					}

					bec.a(acu.aj);
					if (bec instanceof ze) {
						aa.i.a((ze)bec, bki9, integer6);
					}

					this.f.Z_();
					this.i.a(bec.eF());
					this.a(this.f);
					bqb.a(null, fu, acl.dm, acm.BLOCKS, 1.0F, bqb.t.nextFloat() * 0.1F + 0.9F);
				}
			}));
			return true;
		}
	}

	private List<bnz> a(bki bki, int integer2, int integer3) {
		this.h.setSeed((long)(this.i.b() + integer2));
		List<bnz> list5 = bny.b(this.h, bki, integer3, false);
		if (bki.b() == bkk.mc && list5.size() > 1) {
			list5.remove(this.h.nextInt(list5.size()));
		}

		return list5;
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.g.a((BiConsumer<bqb, fu>)((bqb, fu) -> this.a(bec, bec.l, this.f)));
	}

	@Override
	public boolean a(bec bec) {
		return a(this.g, bec, bvs.dZ);
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			if (integer == 0) {
				if (!this.a(bki6, 2, 38, true)) {
					return bki.b;
				}
			} else if (integer == 1) {
				if (!this.a(bki6, 2, 38, true)) {
					return bki.b;
				}
			} else if (bki6.b() == bkk.mv) {
				if (!this.a(bki6, 1, 2, true)) {
					return bki.b;
				}
			} else {
				if (((bhw)this.a.get(0)).f() || !((bhw)this.a.get(0)).a(bki6)) {
					return bki.b;
				}

				bki bki7 = bki6.i();
				bki7.e(1);
				bki6.g(1);
				((bhw)this.a.get(0)).d(bki7);
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
