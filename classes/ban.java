public class ban extends baf {
	private int b;

	public ban(bac bac) {
		super(bac);
	}

	@Override
	public void b() {
		this.a.l.a(this.a.cC(), this.a.cD(), this.a.cG(), acl.dt, this.a.ct(), 2.5F, 0.8F + this.a.cX().nextFloat() * 0.3F, false);
	}

	@Override
	public void c() {
		if (this.b++ >= 40) {
			this.a.eL().a(bas.f);
		}
	}

	@Override
	public void d() {
		this.b = 0;
	}

	@Override
	public bas<ban> i() {
		return bas.h;
	}
}
