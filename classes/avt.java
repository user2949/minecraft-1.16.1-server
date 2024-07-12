public class avt extends aug {
	private final apg a;

	public avt(apg apg) {
		this.a = apg;
	}

	@Override
	public boolean a() {
		return this.a.aj() && !this.a.l.b(this.a.cA()).a(acz.a);
	}

	@Override
	public void c() {
		fu fu2 = null;

		for (fu fu5 : fu.b(
			aec.c(this.a.cC() - 2.0), aec.c(this.a.cD() - 2.0), aec.c(this.a.cG() - 2.0), aec.c(this.a.cC() + 2.0), aec.c(this.a.cD()), aec.c(this.a.cG() + 2.0)
		)) {
			if (this.a.l.b(fu5).a(acz.a)) {
				fu2 = fu5;
				break;
			}
		}

		if (fu2 != null) {
			this.a.u().a((double)fu2.u(), (double)fu2.v(), (double)fu2.w(), 1.0);
		}
	}
}
