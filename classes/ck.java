import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class ck {
	public static final ck a = new ck(ImmutableList.of());
	private final List<ck.c> b;

	private static ck.c a(String string, JsonElement jsonElement) {
		if (jsonElement.isJsonPrimitive()) {
			String string3 = jsonElement.getAsString();
			return new ck.b(string, string3);
		} else {
			JsonObject jsonObject3 = adt.m(jsonElement, "value");
			String string4 = jsonObject3.has("min") ? b(jsonObject3.get("min")) : null;
			String string5 = jsonObject3.has("max") ? b(jsonObject3.get("max")) : null;
			return (ck.c)(string4 != null && string4.equals(string5) ? new ck.b(string, string4) : new ck.d(string, string4, string5));
		}
	}

	@Nullable
	private static String b(JsonElement jsonElement) {
		return jsonElement.isJsonNull() ? null : jsonElement.getAsString();
	}

	private ck(List<ck.c> list) {
		this.b = ImmutableList.copyOf(list);
	}

	public <S extends cfl<?, S>> boolean a(cfk<?, S> cfk, S cfl) {
		for (ck.c c5 : this.b) {
			if (!c5.a(cfk, cfl)) {
				return false;
			}
		}

		return true;
	}

	public boolean a(cfj cfj) {
		return this.a(cfj.b().m(), cfj);
	}

	public boolean a(cxa cxa) {
		return this.a(cxa.a().g(), cxa);
	}

	public void a(cfk<?, ?> cfk, Consumer<String> consumer) {
		this.b.forEach(c -> c.a(cfk, consumer));
	}

	public static ck a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "properties");
			List<ck.c> list3 = Lists.<ck.c>newArrayList();

			for (Entry<String, JsonElement> entry5 : jsonObject2.entrySet()) {
				list3.add(a((String)entry5.getKey(), (JsonElement)entry5.getValue()));
			}

			return new ck(list3);
		} else {
			return a;
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			if (!this.b.isEmpty()) {
				this.b.forEach(c -> jsonObject2.add(c.b(), c.a()));
			}

			return jsonObject2;
		}
	}

	public static class a {
		private final List<ck.c> a = Lists.<ck.c>newArrayList();

		private a() {
		}

		public static ck.a a() {
			return new ck.a();
		}

		public ck.a a(cgl<?> cgl, String string) {
			this.a.add(new ck.b(cgl.f(), string));
			return this;
		}

		public ck.a a(cgl<Integer> cgl, int integer) {
			return this.a(cgl, Integer.toString(integer));
		}

		public ck.a a(cgl<Boolean> cgl, boolean boolean2) {
			return this.a(cgl, Boolean.toString(boolean2));
		}

		public <T extends Comparable<T> & aeh> ck.a a(cgl<T> cgl, T comparable) {
			return this.a(cgl, comparable.a());
		}

		public ck b() {
			return new ck(this.a);
		}
	}

	static class b extends ck.c {
		private final String a;

		public b(String string1, String string2) {
			super(string1);
			this.a = string2;
		}

		@Override
		protected <T extends Comparable<T>> boolean a(cfl<?, ?> cfl, cgl<T> cgl) {
			T comparable4 = cfl.c(cgl);
			Optional<T> optional5 = cgl.b(this.a);
			return optional5.isPresent() && comparable4.compareTo(optional5.get()) == 0;
		}

		@Override
		public JsonElement a() {
			return new JsonPrimitive(this.a);
		}
	}

	abstract static class c {
		private final String a;

		public c(String string) {
			this.a = string;
		}

		public <S extends cfl<?, S>> boolean a(cfk<?, S> cfk, S cfl) {
			cgl<?> cgl4 = cfk.a(this.a);
			return cgl4 == null ? false : this.a(cfl, cgl4);
		}

		protected abstract <T extends Comparable<T>> boolean a(cfl<?, ?> cfl, cgl<T> cgl);

		public abstract JsonElement a();

		public String b() {
			return this.a;
		}

		public void a(cfk<?, ?> cfk, Consumer<String> consumer) {
			cgl<?> cgl4 = cfk.a(this.a);
			if (cgl4 == null) {
				consumer.accept(this.a);
			}
		}
	}

	static class d extends ck.c {
		@Nullable
		private final String a;
		@Nullable
		private final String b;

		public d(String string1, @Nullable String string2, @Nullable String string3) {
			super(string1);
			this.a = string2;
			this.b = string3;
		}

		@Override
		protected <T extends Comparable<T>> boolean a(cfl<?, ?> cfl, cgl<T> cgl) {
			T comparable4 = cfl.c(cgl);
			if (this.a != null) {
				Optional<T> optional5 = cgl.b(this.a);
				if (!optional5.isPresent() || comparable4.compareTo(optional5.get()) < 0) {
					return false;
				}
			}

			if (this.b != null) {
				Optional<T> optional5 = cgl.b(this.b);
				if (!optional5.isPresent() || comparable4.compareTo(optional5.get()) > 0) {
					return false;
				}
			}

			return true;
		}

		@Override
		public JsonElement a() {
			JsonObject jsonObject2 = new JsonObject();
			if (this.a != null) {
				jsonObject2.addProperty("min", this.a);
			}

			if (this.b != null) {
				jsonObject2.addProperty("max", this.b);
			}

			return jsonObject2;
		}
	}
}
