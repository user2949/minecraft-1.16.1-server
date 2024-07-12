import java.util.EnumSet;

public class avn extends aug {
	private final apq a;

	public avn(apq apq) {
		this.a = apq;
		this.a(EnumSet.of(aug.a.JUMP, aug.a.MOVE));
	}

	@Override
	public boolean b() {
		return this.a.eP();
	}

	@Override
	public boolean a() {
		if (!this.a.eL()) {
			return false;
		} else if (this.a.aD()) {
			return false;
		} else if (!this.a.aj()) {
			return false;
		} else {
			aoy aoy2 = this.a.eO();
			if (aoy2 == null) {
				return true;
			} else {
				return this.a.h((aom)aoy2) < 144.0 && aoy2.cY() != null ? false : this.a.eP();
			}
		}
	}

	@Override
	public void c() {
		this.a.x().o();
		this.a.v(true);
	}

	@Override
	public void d() {
		this.a.v(false);
	}
}
