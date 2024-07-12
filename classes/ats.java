import java.util.EnumSet;

public class ats extends aug {
	private final apg a;

	public ats(apg apg) {
		this.a = apg;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
	}

	@Override
	public boolean a() {
		return this.a.bE() < 140;
	}

	@Override
	public boolean b() {
		return this.a();
	}

	@Override
	public boolean D_() {
		return false;
	}

	@Override
	public void c() {
		this.g();
	}

	private void g() {
		Iterable<fu> iterable2 = fu.b(
			aec.c(this.a.cC() - 1.0), aec.c(this.a.cD()), aec.c(this.a.cG() - 1.0), aec.c(this.a.cC() + 1.0), aec.c(this.a.cD() + 8.0), aec.c(this.a.cG() + 1.0)
		);
		fu fu3 = null;

		for (fu fu5 : iterable2) {
			if (this.a(this.a.l, fu5)) {
				fu3 = fu5;
				break;
			}
		}

		if (fu3 == null) {
			fu3 = new fu(this.a.cC(), this.a.cD() + 8.0, this.a.cG());
		}

		this.a.x().a((double)fu3.u(), (double)(fu3.v() + 1), (double)fu3.w(), 1.0);
	}

	@Override
	public void e() {
		this.g();
		this.a.a(0.02F, new dem((double)this.a.aY, (double)this.a.aZ, (double)this.a.ba));
		this.a.a(apd.SELF, this.a.cB());
	}

	private boolean a(bqd bqd, fu fu) {
		cfj cfj4 = bqd.d_(fu);
		return (bqd.b(fu).c() || cfj4.a(bvs.lc)) && cfj4.a(bqd, fu, czg.LAND);
	}
}
