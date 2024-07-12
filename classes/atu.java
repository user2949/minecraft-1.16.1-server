import java.util.EnumSet;

public class atu extends auu {
	private final aym g;

	public atu(aym aym, double double2, int integer) {
		super(aym, double2, integer, 6);
		this.g = aym;
		this.f = -2;
		this.a(EnumSet.of(aug.a.JUMP, aug.a.MOVE));
	}

	@Override
	public boolean a() {
		return this.g.eL() && !this.g.eP() && !this.g.eX() && super.a();
	}

	@Override
	public void c() {
		super.c();
		this.g.v(false);
	}

	@Override
	protected int a(apg apg) {
		return 40;
	}

	@Override
	public void d() {
		super.d();
		this.g.x(false);
	}

	@Override
	public void e() {
		super.e();
		this.g.v(false);
		if (!this.k()) {
			this.g.x(false);
		} else if (!this.g.eX()) {
			this.g.x(true);
		}
	}

	@Override
	protected boolean a(bqd bqd, fu fu) {
		return bqd.w(fu.b()) && bqd.d_(fu).b().a(acx.K);
	}
}
