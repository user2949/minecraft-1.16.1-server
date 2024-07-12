import javax.annotation.Nullable;

public class ced extends cdl implements amx, anj {
	private final amz a = new amz() {
		@Override
		public int ab_() {
			return 1;
		}

		@Override
		public boolean c() {
			return ced.this.c.a();
		}

		@Override
		public bki a(int integer) {
			return integer == 0 ? ced.this.c : bki.b;
		}

		@Override
		public bki a(int integer1, int integer2) {
			if (integer1 == 0) {
				bki bki4 = ced.this.c.a(integer2);
				if (ced.this.c.a()) {
					ced.this.k();
				}

				return bki4;
			} else {
				return bki.b;
			}
		}

		@Override
		public bki b(int integer) {
			if (integer == 0) {
				bki bki3 = ced.this.c;
				ced.this.c = bki.b;
				ced.this.k();
				return bki3;
			} else {
				return bki.b;
			}
		}

		@Override
		public void a(int integer, bki bki) {
		}

		@Override
		public int X_() {
			return 1;
		}

		@Override
		public void Z_() {
			ced.this.Z_();
		}

		@Override
		public boolean a(bec bec) {
			if (ced.this.d.c(ced.this.e) != ced.this) {
				return false;
			} else {
				return bec.g((double)ced.this.e.u() + 0.5, (double)ced.this.e.v() + 0.5, (double)ced.this.e.w() + 0.5) > 64.0 ? false : ced.this.g();
			}
		}

		@Override
		public boolean b(int integer, bki bki) {
			return false;
		}

		@Override
		public void aa_() {
		}
	};
	private final bgr b = new bgr() {
		@Override
		public int a(int integer) {
			return integer == 0 ? ced.this.g : 0;
		}

		@Override
		public void a(int integer1, int integer2) {
			if (integer1 == 0) {
				ced.this.a(integer2);
			}
		}

		@Override
		public int a() {
			return 1;
		}
	};
	private bki c = bki.b;
	private int g;
	private int h;

	public ced() {
		super(cdm.C);
	}

	public bki f() {
		return this.c;
	}

	public boolean g() {
		bke bke2 = this.c.b();
		return bke2 == bkk.oS || bke2 == bkk.oT;
	}

	public void a(bki bki) {
		this.a(bki, null);
	}

	private void k() {
		this.g = 0;
		this.h = 0;
		bzb.a(this.v(), this.o(), this.p(), false);
	}

	public void a(bki bki, @Nullable bec bec) {
		this.c = this.b(bki, bec);
		this.g = 0;
		this.h = bma.g(this.c);
		this.Z_();
	}

	private void a(int integer) {
		int integer3 = aec.a(integer, 0, this.h - 1);
		if (integer3 != this.g) {
			this.g = integer3;
			this.Z_();
			bzb.a(this.v(), this.o(), this.p());
		}
	}

	public int h() {
		return this.g;
	}

	public int j() {
		float float2 = this.h > 1 ? (float)this.h() / ((float)this.h - 1.0F) : 1.0F;
		return aec.d(float2 * 14.0F) + (this.g() ? 1 : 0);
	}

	private bki b(bki bki, @Nullable bec bec) {
		if (this.d instanceof zd && bki.b() == bkk.oT) {
			bma.a(bki, this.a(bec), bec);
		}

		return bki;
	}

	private cz a(@Nullable bec bec) {
		String string3;
		mr mr4;
		if (bec == null) {
			string3 = "Lectern";
			mr4 = new nd("Lectern");
		} else {
			string3 = bec.P().getString();
			mr4 = bec.d();
		}

		dem dem5 = dem.a(this.e);
		return new cz(cy.a_, dem5, del.a, (zd)this.d, 2, string3, mr4, this.d.l(), bec);
	}

	@Override
	public boolean t() {
		return true;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		if (le.c("Book", 10)) {
			this.c = this.b(bki.a(le.p("Book")), null);
		} else {
			this.c = bki.b;
		}

		this.h = bma.g(this.c);
		this.g = aec.a(le.h("Page"), 0, this.h - 1);
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (!this.f().a()) {
			le.a("Book", this.f().b(new le()));
			le.b("Page", this.g);
		}

		return le;
	}

	@Override
	public void aa_() {
		this.a(bki.b);
	}

	@Override
	public bgi createMenu(int integer, beb beb, bec bec) {
		return new bhh(integer, this.a, this.b);
	}

	@Override
	public mr d() {
		return new ne("container.lectern");
	}
}
