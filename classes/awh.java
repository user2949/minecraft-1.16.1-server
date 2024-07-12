import java.util.EnumSet;

public class awh extends awj {
	private final apq a;
	private aoy b;
	private int c;

	public awh(apq apq) {
		super(apq, false);
		this.a = apq;
		this.a(EnumSet.of(aug.a.TARGET));
	}

	@Override
	public boolean a() {
		if (this.a.eL() && !this.a.eP()) {
			aoy aoy2 = this.a.eO();
			if (aoy2 == null) {
				return false;
			} else {
				this.b = aoy2.da();
				int integer3 = aoy2.db();
				return integer3 != this.c && this.a(this.b, axs.a) && this.a.a(this.b, aoy2);
			}
		} else {
			return false;
		}
	}

	@Override
	public void c() {
		this.e.i(this.b);
		aoy aoy2 = this.a.eO();
		if (aoy2 != null) {
			this.c = aoy2.db();
		}

		super.c();
	}
}
