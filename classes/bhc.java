import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class bhc extends bgi {
	private final amz c = new bhr();
	private final amz d = new anm(2) {
		@Override
		public void Z_() {
			super.Z_();
			bhc.this.a(this);
		}
	};
	private final bgs e;

	public bhc(int integer, beb beb) {
		this(integer, beb, bgs.a);
	}

	public bhc(int integer, beb beb, bgs bgs) {
		super(bhk.o, integer);
		this.e = bgs;
		this.a(new bhw(this.d, 0, 49, 19) {
			@Override
			public boolean a(bki bki) {
				return bki.e() || bki.b() == bkk.pp || bki.x();
			}
		});
		this.a(new bhw(this.d, 1, 49, 40) {
			@Override
			public boolean a(bki bki) {
				return bki.e() || bki.b() == bkk.pp || bki.x();
			}
		});
		this.a(new bhw(this.c, 2, 129, 34) {
			@Override
			public boolean a(bki bki) {
				return false;
			}

			@Override
			public bki a(bec bec, bki bki) {
				bgs.a((BiConsumer<bqb, fu>)((bqb, fu) -> {
					int integer4 = this.a(bqb);

					while (integer4 > 0) {
						int integer5 = aos.a(integer4);
						integer4 -= integer5;
						bqb.c(new aos(bqb, (double)fu.u(), (double)fu.v() + 0.5, (double)fu.w() + 0.5, integer5));
					}

					bqb.c(1042, fu, 0);
				}));
				bhc.this.d.a(0, bki.b);
				bhc.this.d.a(1, bki.b);
				return bki;
			}

			private int a(bqb bqb) {
				int integer3 = 0;
				integer3 += this.e(bhc.this.d.a(0));
				integer3 += this.e(bhc.this.d.a(1));
				if (integer3 > 0) {
					int integer4 = (int)Math.ceil((double)integer3 / 2.0);
					return integer4 + bqb.t.nextInt(integer4);
				} else {
					return 0;
				}
			}

			private int e(bki bki) {
				int integer3 = 0;
				Map<bnw, Integer> map4 = bny.a(bki);

				for (Entry<bnw, Integer> entry6 : map4.entrySet()) {
					bnw bnw7 = (bnw)entry6.getKey();
					Integer integer8 = (Integer)entry6.getValue();
					if (!bnw7.c()) {
						integer3 += bnw7.a(integer8);
					}
				}

				return integer3;
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
	public void a(amz amz) {
		super.a(amz);
		if (amz == this.d) {
			this.e();
		}
	}

	private void e() {
		bki bki2 = this.d.a(0);
		bki bki3 = this.d.a(1);
		boolean boolean4 = !bki2.a() || !bki3.a();
		boolean boolean5 = !bki2.a() && !bki3.a();
		if (!boolean4) {
			this.c.a(0, bki.b);
		} else {
			boolean boolean6 = !bki2.a() && bki2.b() != bkk.pp && !bki2.x() || !bki3.a() && bki3.b() != bkk.pp && !bki3.x();
			if (bki2.E() > 1 || bki3.E() > 1 || !boolean5 && boolean6) {
				this.c.a(0, bki.b);
				this.c();
				return;
			}

			int integer8 = 1;
			int integer7;
			bki bki9;
			if (boolean5) {
				if (bki2.b() != bki3.b()) {
					this.c.a(0, bki.b);
					this.c();
					return;
				}

				bke bke10 = bki2.b();
				int integer11 = bke10.j() - bki2.g();
				int integer12 = bke10.j() - bki3.g();
				int integer13 = integer11 + integer12 + bke10.j() * 5 / 100;
				integer7 = Math.max(bke10.j() - integer13, 0);
				bki9 = this.b(bki2, bki3);
				if (!bki9.e()) {
					if (!bki.b(bki2, bki3)) {
						this.c.a(0, bki.b);
						this.c();
						return;
					}

					integer8 = 2;
				}
			} else {
				boolean boolean10 = !bki2.a();
				integer7 = boolean10 ? bki2.g() : bki3.g();
				bki9 = boolean10 ? bki2 : bki3;
			}

			this.c.a(0, this.a(bki9, integer7, integer8));
		}

		this.c();
	}

	private bki b(bki bki1, bki bki2) {
		bki bki4 = bki1.i();
		Map<bnw, Integer> map5 = bny.a(bki2);

		for (Entry<bnw, Integer> entry7 : map5.entrySet()) {
			bnw bnw8 = (bnw)entry7.getKey();
			if (!bnw8.c() || bny.a(bnw8, bki4) == 0) {
				bki4.a(bnw8, (Integer)entry7.getValue());
			}
		}

		return bki4;
	}

	private bki a(bki bki, int integer2, int integer3) {
		bki bki5 = bki.i();
		bki5.c("Enchantments");
		bki5.c("StoredEnchantments");
		if (integer2 > 0) {
			bki5.b(integer2);
		} else {
			bki5.c("Damage");
		}

		bki5.e(integer3);
		Map<bnw, Integer> map6 = (Map<bnw, Integer>)bny.a(bki)
			.entrySet()
			.stream()
			.filter(entry -> ((bnw)entry.getKey()).c())
			.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		bny.a(map6, bki5);
		bki5.c(0);
		if (bki5.b() == bkk.pp && map6.size() == 0) {
			bki5 = new bki(bkk.mc);
			if (bki.t()) {
				bki5.a(bki.r());
			}
		}

		for (int integer7 = 0; integer7 < map6.size(); integer7++) {
			bki5.c(bgk.d(bki5.B()));
		}

		return bki5;
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.e.a((BiConsumer<bqb, fu>)((bqb, fu) -> this.a(bec, bqb, this.d)));
	}

	@Override
	public boolean a(bec bec) {
		return a(this.e, bec, bvs.lX);
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			bki bki7 = this.d.a(0);
			bki bki8 = this.d.a(1);
			if (integer == 2) {
				if (!this.a(bki6, 3, 39, true)) {
					return bki.b;
				}

				bhw5.a(bki6, bki4);
			} else if (integer != 0 && integer != 1) {
				if (!bki7.a() && !bki8.a()) {
					if (integer >= 3 && integer < 30) {
						if (!this.a(bki6, 30, 39, false)) {
							return bki.b;
						}
					} else if (integer >= 30 && integer < 39 && !this.a(bki6, 3, 30, false)) {
						return bki.b;
					}
				} else if (!this.a(bki6, 0, 2, false)) {
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
}
