import java.util.function.Predicate;

public class dew implements der {
	protected static final der a = new dew(false, -Double.MAX_VALUE, bkk.a, cwz -> false) {
		@Override
		public boolean a(dfg dfg, fu fu, boolean boolean3) {
			return boolean3;
		}
	};
	private final boolean b;
	private final double c;
	private final bke d;
	private final Predicate<cwz> e;

	protected dew(boolean boolean1, double double2, bke bke, Predicate<cwz> predicate) {
		this.b = boolean1;
		this.c = double2;
		this.d = bke;
		this.e = predicate;
	}

	@Deprecated
	protected dew(aom aom) {
		this(aom.bu(), aom.cD(), aom instanceof aoy ? ((aoy)aom).dC().b() : bkk.a, aom instanceof aoy ? ((aoy)aom)::a : cwz -> false);
	}

	@Override
	public boolean a(bke bke) {
		return this.d == bke;
	}

	@Override
	public boolean a(cxa cxa, cwy cwy) {
		return this.e.test(cwy) && !cxa.a().a(cwy);
	}

	@Override
	public boolean b() {
		return this.b;
	}

	@Override
	public boolean a(dfg dfg, fu fu, boolean boolean3) {
		return this.c > (double)fu.v() + dfg.c(fz.a.Y) - 1.0E-5F;
	}
}
