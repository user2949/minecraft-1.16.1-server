import java.util.EnumSet;

public class avs extends aug {
	private final bdk a;

	public avs(bdk bdk) {
		this.a = bdk;
		this.a(EnumSet.of(aug.a.JUMP, aug.a.MOVE));
	}

	@Override
	public boolean a() {
		if (!this.a.aU()) {
			return false;
		} else if (this.a.aA()) {
			return false;
		} else if (!this.a.aj()) {
			return false;
		} else if (this.a.w) {
			return false;
		} else {
			bec bec2 = this.a.eN();
			if (bec2 == null) {
				return false;
			} else {
				return this.a.h(bec2) > 16.0 ? false : bec2.bw != null;
			}
		}
	}

	@Override
	public void c() {
		this.a.x().o();
	}

	@Override
	public void d() {
		this.a.f(null);
	}
}
