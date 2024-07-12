public class bhm extends bgi {
	private final boy c;
	private final bhl d;

	public bhm(int integer, beb beb) {
		this(integer, beb, new bdm(beb.e));
	}

	public bhm(int integer, beb beb, boy boy) {
		super(bhk.s, integer);
		this.c = boy;
		this.d = new bhl(boy);
		this.a(new bhw(this.d, 0, 136, 37));
		this.a(new bhw(this.d, 1, 162, 37));
		this.a(new bhn(beb.e, boy, this.d, 2, 220, 37));

		for (int integer5 = 0; integer5 < 3; integer5++) {
			for (int integer6 = 0; integer6 < 9; integer6++) {
				this.a(new bhw(beb, integer6 + integer5 * 9 + 9, 108 + integer6 * 18, 84 + integer5 * 18));
			}
		}

		for (int integer5 = 0; integer5 < 9; integer5++) {
			this.a(new bhw(beb, integer5, 108 + integer5 * 18, 142));
		}
	}

	@Override
	public void a(amz amz) {
		this.d.f();
		super.a(amz);
	}

	public void d(int integer) {
		this.d.c(integer);
	}

	@Override
	public boolean a(bec bec) {
		return this.c.eN() == bec;
	}

	@Override
	public boolean a(bki bki, bhw bhw) {
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
				this.k();
			} else if (integer != 0 && integer != 1) {
				if (integer >= 3 && integer < 30) {
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

	private void k() {
		if (!this.c.eV().v) {
			aom aom2 = (aom)this.c;
			this.c.eV().a(aom2.cC(), aom2.cD(), aom2.cG(), this.c.eR(), acm.NEUTRAL, 1.0F, 1.0F, false);
		}
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.c.f(null);
		if (!this.c.eV().v) {
			if (!bec.aU() || bec instanceof ze && ((ze)bec).q()) {
				bki bki3 = this.d.b(0);
				if (!bki3.a()) {
					bec.a(bki3, false);
				}

				bki3 = this.d.b(1);
				if (!bki3.a()) {
					bec.a(bki3, false);
				}
			} else {
				bec.bt.a(bec.l, this.d.b(0));
				bec.bt.a(bec.l, this.d.b(1));
			}
		}
	}

	public void g(int integer) {
		if (this.i().size() > integer) {
			bki bki3 = this.d.a(0);
			if (!bki3.a()) {
				if (!this.a(bki3, 3, 39, true)) {
					return;
				}

				this.d.a(0, bki3);
			}

			bki bki4 = this.d.a(1);
			if (!bki4.a()) {
				if (!this.a(bki4, 3, 39, true)) {
					return;
				}

				this.d.a(1, bki4);
			}

			if (this.d.a(0).a() && this.d.a(1).a()) {
				bki bki5 = ((boz)this.i().get(integer)).b();
				this.c(0, bki5);
				bki bki6 = ((boz)this.i().get(integer)).c();
				this.c(1, bki6);
			}
		}
	}

	private void c(int integer, bki bki) {
		if (!bki.a()) {
			for (int integer4 = 3; integer4 < 39; integer4++) {
				bki bki5 = ((bhw)this.a.get(integer4)).e();
				if (!bki5.a() && this.b(bki, bki5)) {
					bki bki6 = this.d.a(integer);
					int integer7 = bki6.a() ? 0 : bki6.E();
					int integer8 = Math.min(bki.c() - integer7, bki5.E());
					bki bki9 = bki5.i();
					int integer10 = integer7 + integer8;
					bki5.g(integer8);
					bki9.e(integer10);
					this.d.a(integer, bki9);
					if (integer10 >= bki.c()) {
						break;
					}
				}
			}
		}
	}

	private boolean b(bki bki1, bki bki2) {
		return bki1.b() == bki2.b() && bki.a(bki1, bki2);
	}

	public bpa i() {
		return this.c.eP();
	}
}
