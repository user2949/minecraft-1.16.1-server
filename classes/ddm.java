import java.util.function.Predicate;

public interface ddm extends dau, Predicate<dat> {
	ddn b();

	@FunctionalInterface
	public interface a {
		ddm build();

		default ddm.a a() {
			return ddj.a(this);
		}

		default ddc.a a(ddm.a a) {
			return ddc.a(this, a);
		}
	}
}
