public abstract class gx extends gv {
	private boolean b = true;

	public boolean a() {
		return this.b;
	}

	public void a(boolean boolean1) {
		this.b = boolean1;
	}

	@Override
	protected void a(fv fv) {
		fv.h().c(this.a() ? 1000 : 1001, fv.d(), 0);
	}
}
