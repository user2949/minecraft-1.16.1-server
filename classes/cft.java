import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cft implements Predicate<cfj> {
	public static final Predicate<cfj> a = cfj -> true;
	private final cfk<bvr, cfj> b;
	private final Map<cgl<?>, Predicate<Object>> c = Maps.<cgl<?>, Predicate<Object>>newHashMap();

	private cft(cfk<bvr, cfj> cfk) {
		this.b = cfk;
	}

	public static cft a(bvr bvr) {
		return new cft(bvr.m());
	}

	public boolean test(@Nullable cfj cfj) {
		if (cfj != null && cfj.b().equals(this.b.c())) {
			if (this.c.isEmpty()) {
				return true;
			} else {
				for (Entry<cgl<?>, Predicate<Object>> entry4 : this.c.entrySet()) {
					if (!this.a(cfj, (cgl)entry4.getKey(), (Predicate<Object>)entry4.getValue())) {
						return false;
					}
				}

				return true;
			}
		} else {
			return false;
		}
	}

	protected <T extends Comparable<T>> boolean a(cfj cfj, cgl<T> cgl, Predicate<Object> predicate) {
		T comparable5 = cfj.c(cgl);
		return predicate.test(comparable5);
	}

	public <V extends Comparable<V>> cft a(cgl<V> cgl, Predicate<Object> predicate) {
		if (!this.b.d().contains(cgl)) {
			throw new IllegalArgumentException(this.b + " cannot support property " + cgl);
		} else {
			this.c.put(cgl, predicate);
			return this;
		}
	}
}
