public class bho extends anm {
	private cdx a;

	public bho() {
		super(27);
	}

	public void a(cdx cdx) {
		this.a = cdx;
	}

	@Override
	public void a(lk lk) {
		for (int integer3 = 0; integer3 < this.ab_(); integer3++) {
			this.a(integer3, bki.b);
		}

		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			le le4 = lk.a(integer3);
			int integer5 = le4.f("Slot") & 255;
			if (integer5 >= 0 && integer5 < this.ab_()) {
				this.a(integer5, bki.a(le4));
			}
		}
	}

	@Override
	public lk g() {
		lk lk2 = new lk();

		for (int integer3 = 0; integer3 < this.ab_(); integer3++) {
			bki bki4 = this.a(integer3);
			if (!bki4.a()) {
				le le5 = new le();
				le5.a("Slot", (byte)integer3);
				bki4.b(le5);
				lk2.add(le5);
			}
		}

		return lk2;
	}

	@Override
	public boolean a(bec bec) {
		return this.a != null && !this.a.a(bec) ? false : super.a(bec);
	}

	@Override
	public void c_(bec bec) {
		if (this.a != null) {
			this.a.d();
		}

		super.c_(bec);
	}

	@Override
	public void b_(bec bec) {
		if (this.a != null) {
			this.a.f();
		}

		super.b_(bec);
		this.a = null;
	}
}
