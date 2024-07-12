import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;

public class dcy {
	private final Set<dcx<?>> a;
	private final Set<dcx<?>> b;

	private dcy(Set<dcx<?>> set1, Set<dcx<?>> set2) {
		this.a = ImmutableSet.copyOf(set1);
		this.b = ImmutableSet.copyOf(Sets.union(set1, set2));
	}

	public Set<dcx<?>> a() {
		return this.a;
	}

	public Set<dcx<?>> b() {
		return this.b;
	}

	public String toString() {
		return "[" + Joiner.on(", ").join(this.b.stream().map(dcx -> (this.a.contains(dcx) ? "!" : "") + dcx.a()).iterator()) + "]";
	}

	public void a(dbe dbe, dau dau) {
		Set<dcx<?>> set4 = dau.a();
		Set<dcx<?>> set5 = Sets.<dcx<?>>difference(set4, this.b);
		if (!set5.isEmpty()) {
			dbe.a("Parameters " + set5 + " are not provided in this context");
		}
	}

	public static class a {
		private final Set<dcx<?>> a = Sets.newIdentityHashSet();
		private final Set<dcx<?>> b = Sets.newIdentityHashSet();

		public dcy.a a(dcx<?> dcx) {
			if (this.b.contains(dcx)) {
				throw new IllegalArgumentException("Parameter " + dcx.a() + " is already optional");
			} else {
				this.a.add(dcx);
				return this;
			}
		}

		public dcy.a b(dcx<?> dcx) {
			if (this.a.contains(dcx)) {
				throw new IllegalArgumentException("Parameter " + dcx.a() + " is already required");
			} else {
				this.b.add(dcx);
				return this;
			}
		}

		public dcy a() {
			return new dcy(this.a, this.b);
		}
	}
}
