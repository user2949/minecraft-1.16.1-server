import java.util.EnumSet;
import java.util.List;

public class awa extends awj {
	private final ayt a;
	private aoy b;
	private final axs c = new axs().a(64.0);

	public awa(ayt ayt) {
		super(ayt, false, true);
		this.a = ayt;
		this.a(EnumSet.of(aug.a.TARGET));
	}

	@Override
	public boolean a() {
		deg deg2 = this.a.cb().c(10.0, 8.0, 10.0);
		List<aoy> list3 = this.a.l.a(bdp.class, this.c, this.a, deg2);
		List<bec> list4 = this.a.l.a(this.c, this.a, deg2);

		for (aoy aoy6 : list3) {
			bdp bdp7 = (bdp)aoy6;

			for (bec bec9 : list4) {
				int integer10 = bdp7.g(bec9);
				if (integer10 <= -100) {
					this.b = bec9;
				}
			}
		}

		return this.b == null ? false : !(this.b instanceof bec) || !this.b.a_() && !((bec)this.b).b_();
	}

	@Override
	public void c() {
		this.a.i(this.b);
		super.c();
	}
}
