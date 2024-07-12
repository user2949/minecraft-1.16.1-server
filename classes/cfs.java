import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cfs implements Predicate<cfj> {
	private final bvr a;

	public cfs(bvr bvr) {
		this.a = bvr;
	}

	public static cfs a(bvr bvr) {
		return new cfs(bvr);
	}

	public boolean test(@Nullable cfj cfj) {
		return cfj != null && cfj.a(this.a);
	}
}
