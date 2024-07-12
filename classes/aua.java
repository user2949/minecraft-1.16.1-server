import java.util.EnumSet;

public class aua extends aug {
	private final aoz a;

	public aua(aoz aoz) {
		this.a = aoz;
		this.a(EnumSet.of(aug.a.JUMP));
		aoz.x().d(true);
	}

	@Override
	public boolean a() {
		return this.a.aA() && this.a.b(acz.a) > this.a.cw() || this.a.aN();
	}

	@Override
	public void e() {
		if (this.a.cX().nextFloat() < 0.8F) {
			this.a.v().a();
		}
	}
}
