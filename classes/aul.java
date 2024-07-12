public class aul extends aug {
	private final aze a;
	private ze b;
	private boolean c;

	public aul(aze aze) {
		this.a = aze;
	}

	@Override
	public boolean a() {
		ze ze2 = (ze)this.a.eO();
		boolean boolean3 = ze2 != null && !ze2.a_() && !ze2.bJ.b && !ze2.aA();
		return !this.a.eP() && boolean3 && this.a.eZ();
	}

	@Override
	public boolean D_() {
		return !this.c;
	}

	@Override
	public void c() {
		this.b = (ze)this.a.eO();
		this.c = false;
	}

	@Override
	public void e() {
		if (!this.c && !this.a.eN() && !this.a.eC()) {
			if (this.a.cb().c(this.b.cb())) {
				this.c = this.a.d(this.b);
			}
		}
	}
}
