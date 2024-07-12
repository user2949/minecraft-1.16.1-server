import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface adf<T> {
	static <T> Codec<adf<T>> a(Supplier<adg<T>> supplier) {
		return uh.a
			.flatXmap(
				uh -> (DataResult)Optional.ofNullable(((adg)supplier.get()).a(uh)).map(DataResult::success).orElseGet(() -> DataResult.error("Unknown tag: " + uh)),
				adf -> (DataResult)Optional.ofNullable(((adg)supplier.get()).a(adf)).map(DataResult::success).orElseGet(() -> DataResult.error("Unknown tag: " + adf))
			);
	}

	boolean a(T object);

	List<T> b();

	default T a(Random random) {
		List<T> list3 = this.b();
		return (T)list3.get(random.nextInt(list3.size()));
	}

	static <T> adf<T> b(Set<T> set) {
		return adc.a(set);
	}

	public static class a {
		private final List<adf.b> a = Lists.<adf.b>newArrayList();

		public static adf.a a() {
			return new adf.a();
		}

		public adf.a a(adf.b b) {
			this.a.add(b);
			return this;
		}

		public adf.a a(adf.d d, String string) {
			return this.a(new adf.b(d, string));
		}

		public adf.a a(uh uh, String string) {
			return this.a(new adf.c(uh), string);
		}

		public adf.a b(uh uh, String string) {
			return this.a(new adf.f(uh), string);
		}

		public <T> Optional<adf<T>> a(Function<uh, adf<T>> function1, Function<uh, T> function2) {
			Builder<T> builder4 = ImmutableSet.builder();

			for (adf.b b6 : this.a) {
				if (!b6.a().a(function1, function2, builder4::add)) {
					return Optional.empty();
				}
			}

			return Optional.of(adf.b(builder4.build()));
		}

		public Stream<adf.b> b() {
			return this.a.stream();
		}

		public <T> Stream<adf.b> b(Function<uh, adf<T>> function1, Function<uh, T> function2) {
			return this.b().filter(b -> !b.a().a(function1, function2, object -> {
				}));
		}

		public adf.a a(JsonObject jsonObject, String string) {
			JsonArray jsonArray4 = adt.u(jsonObject, "values");
			List<adf.d> list5 = Lists.<adf.d>newArrayList();

			for (JsonElement jsonElement7 : jsonArray4) {
				String string8 = adt.a(jsonElement7, "value");
				if (string8.startsWith("#")) {
					list5.add(new adf.f(new uh(string8.substring(1))));
				} else {
					list5.add(new adf.c(new uh(string8)));
				}
			}

			if (adt.a(jsonObject, "replace", false)) {
				this.a.clear();
			}

			list5.forEach(d -> this.a.add(new adf.b(d, string)));
			return this;
		}

		public JsonObject c() {
			JsonObject jsonObject2 = new JsonObject();
			JsonArray jsonArray3 = new JsonArray();

			for (adf.b b5 : this.a) {
				b5.a().a(jsonArray3);
			}

			jsonObject2.addProperty("replace", false);
			jsonObject2.add("values", jsonArray3);
			return jsonObject2;
		}
	}

	public static class b {
		private final adf.d a;
		private final String b;

		private b(adf.d d, String string) {
			this.a = d;
			this.b = string;
		}

		public adf.d a() {
			return this.a;
		}

		public String toString() {
			return this.a.toString() + " (from " + this.b + ")";
		}
	}

	public static class c implements adf.d {
		private final uh a;

		public c(uh uh) {
			this.a = uh;
		}

		@Override
		public <T> boolean a(Function<uh, adf<T>> function1, Function<uh, T> function2, Consumer<T> consumer) {
			T object5 = (T)function2.apply(this.a);
			if (object5 == null) {
				return false;
			} else {
				consumer.accept(object5);
				return true;
			}
		}

		@Override
		public void a(JsonArray jsonArray) {
			jsonArray.add(this.a.toString());
		}

		public String toString() {
			return this.a.toString();
		}
	}

	public interface d {
		<T> boolean a(Function<uh, adf<T>> function1, Function<uh, T> function2, Consumer<T> consumer);

		void a(JsonArray jsonArray);
	}

	public interface e<T> extends adf<T> {
		uh a();
	}

	public static class f implements adf.d {
		private final uh a;

		public f(uh uh) {
			this.a = uh;
		}

		@Override
		public <T> boolean a(Function<uh, adf<T>> function1, Function<uh, T> function2, Consumer<T> consumer) {
			adf<T> adf5 = (adf<T>)function1.apply(this.a);
			if (adf5 == null) {
				return false;
			} else {
				adf5.b().forEach(consumer);
				return true;
			}
		}

		@Override
		public void a(JsonArray jsonArray) {
			jsonArray.add("#" + this.a);
		}

		public String toString() {
			return "#" + this.a;
		}
	}
}
