import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cfr implements Predicate<cfj> {
	private static final cfr a = new cfr(cxd.a) {
		@Override
		public boolean test(@Nullable cfj cfj) {
			return cfj != null && cfj.g();
		}
	};
	private final cxd b;

	private cfr(cxd cxd) {
		this.b = cxd;
	}

	public static cfr a(cxd cxd) {
		return cxd == cxd.a ? a : new cfr(cxd);
	}

	public boolean test(@Nullable cfj cfj) {
		return cfj != null && cfj.c() == this.b;
	}
}
