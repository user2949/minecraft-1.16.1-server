import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class io {
	private final Map<ip, List<iq>> a = Maps.<ip, List<iq>>newHashMap();

	protected void a(ip ip, List<iq> list) {
		List<iq> list4 = (List<iq>)this.a.put(ip, list);
		if (list4 != null) {
			throw new IllegalStateException("Value " + ip + " is already defined");
		}
	}

	Map<ip, List<iq>> a() {
		this.c();
		return ImmutableMap.copyOf(this.a);
	}

	private void c() {
		List<cgl<?>> list2 = this.b();
		Stream<ip> stream3 = Stream.of(ip.a());

		for (cgl<?> cgl5 : list2) {
			stream3 = stream3.flatMap(ip -> cgl5.c().map(ip::a));
		}

		List<ip> list4 = (List<ip>)stream3.filter(ip -> !this.a.containsKey(ip)).collect(Collectors.toList());
		if (!list4.isEmpty()) {
			throw new IllegalStateException("Missing definition for properties: " + list4);
		}
	}

	abstract List<cgl<?>> b();

	public static <T1 extends Comparable<T1>> io.a<T1> a(cgl<T1> cgl) {
		return new io.a<>(cgl);
	}

	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> io.b<T1, T2> a(cgl<T1> cgl1, cgl<T2> cgl2) {
		return new io.b<>(cgl1, cgl2);
	}

	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> io.c<T1, T2, T3> a(cgl<T1> cgl1, cgl<T2> cgl2, cgl<T3> cgl3) {
		return new io.c<>(cgl1, cgl2, cgl3);
	}

	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> io.d<T1, T2, T3, T4> a(
		cgl<T1> cgl1, cgl<T2> cgl2, cgl<T3> cgl3, cgl<T4> cgl4
	) {
		return new io.d<>(cgl1, cgl2, cgl3, cgl4);
	}

	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> io.e<T1, T2, T3, T4, T5> a(
		cgl<T1> cgl1, cgl<T2> cgl2, cgl<T3> cgl3, cgl<T4> cgl4, cgl<T5> cgl5
	) {
		return new io.e<>(cgl1, cgl2, cgl3, cgl4, cgl5);
	}

	public static class a<T1 extends Comparable<T1>> extends io {
		private final cgl<T1> a;

		private a(cgl<T1> cgl) {
			this.a = cgl;
		}

		@Override
		public List<cgl<?>> b() {
			return ImmutableList.of(this.a);
		}

		public io.a<T1> a(T1 comparable, List<iq> list) {
			ip ip4 = ip.a(this.a.b(comparable));
			this.a(ip4, list);
			return this;
		}

		public io.a<T1> a(T1 comparable, iq iq) {
			return this.a(comparable, Collections.singletonList(iq));
		}

		public io a(Function<T1, iq> function) {
			this.a.a().forEach(comparable -> this.a((T1)comparable, (iq)function.apply(comparable)));
			return this;
		}
	}

	public static class b<T1 extends Comparable<T1>, T2 extends Comparable<T2>> extends io {
		private final cgl<T1> a;
		private final cgl<T2> b;

		private b(cgl<T1> cgl1, cgl<T2> cgl2) {
			this.a = cgl1;
			this.b = cgl2;
		}

		@Override
		public List<cgl<?>> b() {
			return ImmutableList.of(this.a, this.b);
		}

		public io.b<T1, T2> a(T1 comparable1, T2 comparable2, List<iq> list) {
			ip ip5 = ip.a(this.a.b(comparable1), this.b.b(comparable2));
			this.a(ip5, list);
			return this;
		}

		public io.b<T1, T2> a(T1 comparable1, T2 comparable2, iq iq) {
			return this.a(comparable1, comparable2, Collections.singletonList(iq));
		}

		public io a(BiFunction<T1, T2, iq> biFunction) {
			this.a.a().forEach(comparable -> this.b.a().forEach(comparable3 -> this.a((T1)comparable, (T2)comparable3, (iq)biFunction.apply(comparable, comparable3))));
			return this;
		}

		public io b(BiFunction<T1, T2, List<iq>> biFunction) {
			this.a
				.a()
				.forEach(comparable -> this.b.a().forEach(comparable3 -> this.a((T1)comparable, (T2)comparable3, (List<iq>)biFunction.apply(comparable, comparable3))));
			return this;
		}
	}

	public static class c<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> extends io {
		private final cgl<T1> a;
		private final cgl<T2> b;
		private final cgl<T3> c;

		private c(cgl<T1> cgl1, cgl<T2> cgl2, cgl<T3> cgl3) {
			this.a = cgl1;
			this.b = cgl2;
			this.c = cgl3;
		}

		@Override
		public List<cgl<?>> b() {
			return ImmutableList.of(this.a, this.b, this.c);
		}

		public io.c<T1, T2, T3> a(T1 comparable1, T2 comparable2, T3 comparable3, List<iq> list) {
			ip ip6 = ip.a(this.a.b(comparable1), this.b.b(comparable2), this.c.b(comparable3));
			this.a(ip6, list);
			return this;
		}

		public io.c<T1, T2, T3> a(T1 comparable1, T2 comparable2, T3 comparable3, iq iq) {
			return this.a(comparable1, comparable2, comparable3, Collections.singletonList(iq));
		}

		public io a(io.h<T1, T2, T3, iq> h) {
			this.a
				.a()
				.forEach(
					comparable -> this.b
							.a()
							.forEach(
								comparable3 -> this.c
										.a()
										.forEach(comparable4 -> this.a((T1)comparable, (T2)comparable3, (T3)comparable4, h.apply((T1)comparable, (T2)comparable3, (T3)comparable4)))
							)
				);
			return this;
		}
	}

	public static class d<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> extends io {
		private final cgl<T1> a;
		private final cgl<T2> b;
		private final cgl<T3> c;
		private final cgl<T4> d;

		private d(cgl<T1> cgl1, cgl<T2> cgl2, cgl<T3> cgl3, cgl<T4> cgl4) {
			this.a = cgl1;
			this.b = cgl2;
			this.c = cgl3;
			this.d = cgl4;
		}

		@Override
		public List<cgl<?>> b() {
			return ImmutableList.of(this.a, this.b, this.c, this.d);
		}

		public io.d<T1, T2, T3, T4> a(T1 comparable1, T2 comparable2, T3 comparable3, T4 comparable4, List<iq> list) {
			ip ip7 = ip.a(this.a.b(comparable1), this.b.b(comparable2), this.c.b(comparable3), this.d.b(comparable4));
			this.a(ip7, list);
			return this;
		}

		public io.d<T1, T2, T3, T4> a(T1 comparable1, T2 comparable2, T3 comparable3, T4 comparable4, iq iq) {
			return this.a(comparable1, comparable2, comparable3, comparable4, Collections.singletonList(iq));
		}
	}

	public static class e<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>>
		extends io {
		private final cgl<T1> a;
		private final cgl<T2> b;
		private final cgl<T3> c;
		private final cgl<T4> d;
		private final cgl<T5> e;

		private e(cgl<T1> cgl1, cgl<T2> cgl2, cgl<T3> cgl3, cgl<T4> cgl4, cgl<T5> cgl5) {
			this.a = cgl1;
			this.b = cgl2;
			this.c = cgl3;
			this.d = cgl4;
			this.e = cgl5;
		}

		@Override
		public List<cgl<?>> b() {
			return ImmutableList.of(this.a, this.b, this.c, this.d, this.e);
		}

		public io.e<T1, T2, T3, T4, T5> a(T1 comparable1, T2 comparable2, T3 comparable3, T4 comparable4, T5 comparable5, List<iq> list) {
			ip ip8 = ip.a(this.a.b(comparable1), this.b.b(comparable2), this.c.b(comparable3), this.d.b(comparable4), this.e.b(comparable5));
			this.a(ip8, list);
			return this;
		}

		public io.e<T1, T2, T3, T4, T5> a(T1 comparable1, T2 comparable2, T3 comparable3, T4 comparable4, T5 comparable5, iq iq) {
			return this.a(comparable1, comparable2, comparable3, comparable4, comparable5, Collections.singletonList(iq));
		}
	}

	@FunctionalInterface
	public interface h<P1, P2, P3, R> {
		R apply(P1 object1, P2 object2, P3 object3);
	}
}
