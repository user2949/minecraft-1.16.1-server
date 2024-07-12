import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class add<T> {
	private final adg<T> a = new adg<>(uh -> Optional.empty(), "", "");
	private adg<T> b = this.a;
	private final List<add.a<T>> c = Lists.<add.a<T>>newArrayList();

	public adf.e<T> a(String string) {
		add.a<T> a3 = new add.a<>(new uh(string));
		this.c.add(a3);
		return a3;
	}

	public void a(adg<T> adg) {
		this.b = adg;
		this.c.forEach(a -> a.a(adg::a));
	}

	public adg<T> b() {
		return this.b;
	}

	public List<add.a<T>> c() {
		return this.c;
	}

	public Set<uh> b(adg<T> adg) {
		Set<uh> set3 = (Set<uh>)this.c.stream().map(add.a::a).collect(Collectors.toSet());
		ImmutableSet<uh> immutableSet4 = ImmutableSet.copyOf(adg.a());
		return Sets.<uh>difference(set3, immutableSet4);
	}

	public static class a<T> implements adf.e<T> {
		@Nullable
		private adf<T> b;
		protected final uh a;

		private a(uh uh) {
			this.a = uh;
		}

		@Override
		public uh a() {
			return this.a;
		}

		private adf<T> c() {
			if (this.b == null) {
				throw new IllegalStateException("Tag " + this.a + " used before it was bound");
			} else {
				return this.b;
			}
		}

		void a(Function<uh, adf<T>> function) {
			this.b = (adf<T>)function.apply(this.a);
		}

		@Override
		public boolean a(T object) {
			return this.c().a(object);
		}

		@Override
		public List<T> b() {
			return this.c().b();
		}
	}
}
