import java.util.EnumSet;

public class avp extends aug {
	private final bbn a;
	private aoy b;

	public avp(bbn bbn) {
		this.a = bbn;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		aoy aoy2 = this.a.A();
		return this.a.eL() > 0 || aoy2 != null && this.a.h((aom)aoy2) < 9.0;
	}

	@Override
	public void c() {
		this.a.x().o();
		this.b = this.a.A();
	}

	@Override
	public void d() {
		this.b = null;
	}

	@Override
	public void e() {
		if (this.b == null) {
			this.a.a(-1);
		} else if (this.a.h((aom)this.b) > 49.0) {
			this.a.a(-1);
		} else if (!this.a.z().a(this.b)) {
			this.a.a(-1);
		} else {
			this.a.a(1);
		}
	}
}
