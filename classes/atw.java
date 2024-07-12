public class atw extends auk {
	private static final int[] a = new int[]{0, 1, 4, 5, 6, 7};
	private final ayq b;
	private final int c;
	private boolean d;

	public atw(ayq ayq, int integer) {
		this.b = ayq;
		this.c = integer;
	}

	@Override
	public boolean a() {
		if (this.b.cX().nextInt(this.c) != 0) {
			return false;
		} else {
			fz fz2 = this.b.bZ();
			int integer3 = fz2.i();
			int integer4 = fz2.k();
			fu fu5 = this.b.cA();

			for (int integer9 : a) {
				if (!this.a(fu5, integer3, integer4, integer9) || !this.b(fu5, integer3, integer4, integer9)) {
					return false;
				}
			}

			return true;
		}
	}

	private boolean a(fu fu, int integer2, int integer3, int integer4) {
		fu fu6 = fu.b(integer2 * integer4, 0, integer3 * integer4);
		return this.b.l.b(fu6).a(acz.a) && !this.b.l.d_(fu6).c().c();
	}

	private boolean b(fu fu, int integer2, int integer3, int integer4) {
		return this.b.l.d_(fu.b(integer2 * integer4, 1, integer3 * integer4)).g() && this.b.l.d_(fu.b(integer2 * integer4, 2, integer3 * integer4)).g();
	}

	@Override
	public boolean b() {
		double double2 = this.b.cB().c;
		return (!(double2 * double2 < 0.03F) || this.b.q == 0.0F || !(Math.abs(this.b.q) < 10.0F) || !this.b.aA()) && !this.b.aj();
	}

	@Override
	public boolean D_() {
		return false;
	}

	@Override
	public void c() {
		fz fz2 = this.b.bZ();
		this.b.e(this.b.cB().b((double)fz2.i() * 0.6, 0.7, (double)fz2.k() * 0.6));
		this.b.x().o();
	}

	@Override
	public void d() {
		this.b.q = 0.0F;
	}

	@Override
	public void e() {
		boolean boolean2 = this.d;
		if (!boolean2) {
			cxa cxa3 = this.b.l.b(this.b.cA());
			this.d = cxa3.a(acz.a);
		}

		if (this.d && !boolean2) {
			this.b.a(acl.cJ, 1.0F, 1.0F);
		}

		dem dem3 = this.b.cB();
		if (dem3.c * dem3.c < 0.03F && this.b.q != 0.0F) {
			this.b.q = aec.j(this.b.q, 0.0F, 0.2F);
		} else {
			double double4 = Math.sqrt(aom.b(dem3));
			double double6 = Math.signum(-dem3.c) * Math.acos(double4 / dem3.f()) * 180.0F / (float)Math.PI;
			this.b.q = (float)double6;
		}
	}
}
