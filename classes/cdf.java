import javax.annotation.Nullable;

public abstract class cdf extends cdl implements amz, anj, ank {
	private ani a;
	private mr b;

	protected cdf(cdm<?> cdm) {
		super(cdm);
		this.a = ani.a;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a = ani.b(le);
		if (le.c("CustomName", 8)) {
			this.b = mr.a.a(le.l("CustomName"));
		}
	}

	@Override
	public le a(le le) {
		super.a(le);
		this.a.a(le);
		if (this.b != null) {
			le.a("CustomName", mr.a.a(this.b));
		}

		return le;
	}

	public void a(mr mr) {
		this.b = mr;
	}

	@Override
	public mr P() {
		return this.b != null ? this.b : this.g();
	}

	@Override
	public mr d() {
		return this.P();
	}

	@Nullable
	@Override
	public mr R() {
		return this.b;
	}

	protected abstract mr g();

	public boolean e(bec bec) {
		return a(bec, this.a, this.d());
	}

	public static boolean a(bec bec, ani ani, mr mr) {
		if (!bec.a_() && !ani.a(bec.dC())) {
			bec.a(new ne("container.isLocked", mr), true);
			bec.a(acl.bF, acm.BLOCKS, 1.0F, 1.0F);
			return false;
		} else {
			return true;
		}
	}

	@Nullable
	@Override
	public bgi createMenu(int integer, beb beb, bec bec) {
		return this.e(bec) ? this.a(integer, beb) : null;
	}

	protected abstract bgi a(int integer, beb beb);
}
