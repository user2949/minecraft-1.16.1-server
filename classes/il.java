import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface il extends Supplier<JsonElement> {
	void a(cfk<?, ?> cfk);

	static il.c a() {
		return new il.c();
	}

	static il b(il... arr) {
		return new il.a(il.b.OR, Arrays.asList(arr));
	}

	public static class a implements il {
		private final il.b a;
		private final List<il> b;

		private a(il.b b, List<il> list) {
			this.a = b;
			this.b = list;
		}

		@Override
		public void a(cfk<?, ?> cfk) {
			this.b.forEach(il -> il.a(cfk));
		}

		public JsonElement get() {
			JsonArray jsonArray2 = new JsonArray();
			this.b.stream().map(Supplier::get).forEach(jsonArray2::add);
			JsonObject jsonObject3 = new JsonObject();
			jsonObject3.add(this.a.c, jsonArray2);
			return jsonObject3;
		}
	}

	public static enum b {
		AND("AND"),
		OR("OR");

		private final String c;

		private b(String string3) {
			this.c = string3;
		}
	}

	public static class c implements il {
		private final Map<cgl<?>, String> a = Maps.<cgl<?>, String>newHashMap();

		private static <T extends Comparable<T>> String a(cgl<T> cgl, Stream<T> stream) {
			return (String)stream.map(cgl::a).collect(Collectors.joining("|"));
		}

		private static <T extends Comparable<T>> String c(cgl<T> cgl, T comparable, T[] arr) {
			return a(cgl, Stream.concat(Stream.of(comparable), Stream.of(arr)));
		}

		private <T extends Comparable<T>> void a(cgl<T> cgl, String string) {
			String string4 = (String)this.a.put(cgl, string);
			if (string4 != null) {
				throw new IllegalStateException("Tried to replace " + cgl + " value from " + string4 + " to " + string);
			}
		}

		public final <T extends Comparable<T>> il.c a(cgl<T> cgl, T comparable) {
			this.a(cgl, cgl.a(comparable));
			return this;
		}

		@SafeVarargs
		public final <T extends Comparable<T>> il.c a(cgl<T> cgl, T comparable, T... arr) {
			this.a(cgl, c(cgl, comparable, arr));
			return this;
		}

		public JsonElement get() {
			JsonObject jsonObject2 = new JsonObject();
			this.a.forEach((cgl, string) -> jsonObject2.addProperty(cgl.f(), string));
			return jsonObject2;
		}

		@Override
		public void a(cfk<?, ?> cfk) {
			List<cgl<?>> list3 = (List<cgl<?>>)this.a.keySet().stream().filter(cgl -> cfk.a(cgl.f()) != cgl).collect(Collectors.toList());
			if (!list3.isEmpty()) {
				throw new IllegalStateException("Properties " + list3 + " are missing from " + cfk);
			}
		}
	}
}
