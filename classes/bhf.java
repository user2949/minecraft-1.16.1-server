public class bhf extends bhp<bgu> {
	public static final uh c = new uh("textures/atlas/blocks.png");
	public static final uh d = new uh("item/empty_armor_slot_helmet");
	public static final uh e = new uh("item/empty_armor_slot_chestplate");
	public static final uh f = new uh("item/empty_armor_slot_leggings");
	public static final uh g = new uh("item/empty_armor_slot_boots");
	public static final uh h = new uh("item/empty_armor_slot_shield");
	private static final uh[] j = new uh[]{g, f, e, d};
	private static final aor[] k = new aor[]{aor.HEAD, aor.CHEST, aor.LEGS, aor.FEET};
	private final bgu l = new bgu(this, 2, 2);
	private final bhr m = new bhr();
	public final boolean i;
	private final bec n;

	public bhf(beb beb, boolean boolean2, bec bec) {
		super(null, 0);
		this.i = boolean2;
		this.n = bec;
		this.a(new bhs(beb.e, this.l, this.m, 0, 154, 28));

		for (int integer5 = 0; integer5 < 2; integer5++) {
			for (int integer6 = 0; integer6 < 2; integer6++) {
				this.a(new bhw(this.l, integer6 + integer5 * 2, 98 + integer6 * 18, 18 + integer5 * 18));
			}
		}

		for (int integer5 = 0; integer5 < 4; integer5++) {
			final aor aor6 = k[integer5];
			this.a(new bhw(beb, 39 - integer5, 8, 8 + integer5 * 18) {
				@Override
				public int a() {
					return 1;
				}

				@Override
				public boolean a(bki bki) {
					return aor6 == aoz.j(bki);
				}

				@Override
				public boolean a(bec bec) {
					bki bki3 = this.e();
					return !bki3.a() && !bec.b_() && bny.d(bki3) ? false : super.a(bec);
				}
			});
		}

		for (int integer5 = 0; integer5 < 3; integer5++) {
			for (int integer6 = 0; integer6 < 9; integer6++) {
				this.a(new bhw(beb, integer6 + (integer5 + 1) * 9, 8 + integer6 * 18, 84 + integer5 * 18));
			}
		}

		for (int integer5 = 0; integer5 < 9; integer5++) {
			this.a(new bhw(beb, integer5, 8 + integer5 * 18, 142));
		}

		this.a(new bhw(beb, 40, 77, 62) {
		});
	}

	@Override
	public void a(bee bee) {
		this.l.a(bee);
	}

	@Override
	public void e() {
		this.m.aa_();
		this.l.aa_();
	}

	@Override
	public boolean a(bmu<? super bgu> bmu) {
		return bmu.a(this.l, this.n.l);
	}

	@Override
	public void a(amz amz) {
		bgv.a(this.b, this.n.l, this.n, this.l, this.m);
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.m.aa_();
		if (!bec.l.v) {
			this.a(bec, bec.l, this.l);
		}
	}

	@Override
	public boolean a(bec bec) {
		return true;
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			aor aor7 = aoz.j(bki4);
			if (integer == 0) {
				if (!this.a(bki6, 9, 45, true)) {
					return bki.b;
				}

				bhw5.a(bki6, bki4);
			} else if (integer >= 1 && integer < 5) {
				if (!this.a(bki6, 9, 45, false)) {
					return bki.b;
				}
			} else if (integer >= 5 && integer < 9) {
				if (!this.a(bki6, 9, 45, false)) {
					return bki.b;
				}
			} else if (aor7.a() == aor.a.ARMOR && !((bhw)this.a.get(8 - aor7.b())).f()) {
				int integer8 = 8 - aor7.b();
				if (!this.a(bki6, integer8, integer8 + 1, false)) {
					return bki.b;
				}
			} else if (aor7 == aor.OFFHAND && !((bhw)this.a.get(45)).f()) {
				if (!this.a(bki6, 45, 46, false)) {
					return bki.b;
				}
			} else if (integer >= 9 && integer < 36) {
				if (!this.a(bki6, 36, 45, false)) {
					return bki.b;
				}
			} else if (integer >= 36 && integer < 45) {
				if (!this.a(bki6, 9, 36, false)) {
					return bki.b;
				}
			} else if (!this.a(bki6, 9, 45, false)) {
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

			bki bki8 = bhw5.a(bec, bki6);
			if (integer == 0) {
				bec.a(bki8, false);
			}
		}

		return bki4;
	}

	@Override
	public boolean a(bki bki, bhw bhw) {
		return bhw.c != this.m && super.a(bki, bhw);
	}

	@Override
	public int f() {
		return 0;
	}

	@Override
	public int g() {
		return this.l.g();
	}

	@Override
	public int h() {
		return this.l.f();
	}

	public bgu j() {
		return this.l;
	}
}
