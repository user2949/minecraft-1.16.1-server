import javax.annotation.Nullable;

public class avg extends avf {
	public avg(apg apg, double double2, int integer) {
		super(apg, double2, integer);
	}

	@Nullable
	@Override
	protected dem g() {
		dem dem2 = axu.a(this.a, 10, 7);
		int integer3 = 0;

		while (dem2 != null && !this.a.l.d_(new fu(dem2)).a(this.a.l, new fu(dem2), czg.WATER) && integer3++ < 10) {
			dem2 = axu.a(this.a, 10, 7);
		}

		return dem2;
	}
}
