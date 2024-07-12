import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class mv {
	private static final Logger a = LogManager.getLogger();
	private final mv.a<?> b;
	private final Object c;

	public <T> mv(mv.a<T> a, T object) {
		this.b = a;
		this.c = object;
	}

	public mv.a<?> a() {
		return this.b;
	}

	@Nullable
	public <T> T a(mv.a<T> a) {
		return this.b == a ? a.b(this.c) : null;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			mv mv3 = (mv)object;
			return this.b == mv3.b && Objects.equals(this.c, mv3.c);
		} else {
			return false;
		}
	}

	public String toString() {
		return "HoverEvent{action=" + this.b + ", value='" + this.c + '\'' + '}';
	}

	public int hashCode() {
		int integer2 = this.b.hashCode();
		return 31 * integer2 + (this.c != null ? this.c.hashCode() : 0);
	}

	@Nullable
	public static mv a(JsonObject jsonObject) {
		String string2 = adt.a(jsonObject, "action", null);
		if (string2 == null) {
			return null;
		} else {
			mv.a<?> a3 = mv.a.a(string2);
			if (a3 == null) {
				return null;
			} else {
				JsonElement jsonElement4 = jsonObject.get("contents");
				if (jsonElement4 != null) {
					return a3.a(jsonElement4);
				} else {
					mr mr5 = mr.a.a(jsonObject.get("value"));
					return mr5 != null ? a3.a(mr5) : null;
				}
			}
		}
	}

	public JsonObject b() {
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("action", this.b.b());
		jsonObject2.add("contents", this.b.a(this.c));
		return jsonObject2;
	}

	public static class a<T> {
		public static final mv.a<mr> a = new mv.a<>("show_text", true, mr.a::a, mr.a::b, Function.identity());
		public static final mv.a<mv.c> b = new mv.a<>("show_item", true, jsonElement -> mv.c.b(jsonElement), object -> ((mv.c)object).b(), mr -> mv.c.b(mr));
		public static final mv.a<mv.b> c = new mv.a<>("show_entity", true, mv.b::a, mv.b::a, mv.b::a);
		private static final Map<String, mv.a> d = (Map<String, mv.a>)Stream.of(a, b, c).collect(ImmutableMap.toImmutableMap(mv.a::b, a -> a));
		private final String e;
		private final boolean f;
		private final Function<JsonElement, T> g;
		private final Function<T, JsonElement> h;
		private final Function<mr, T> i;

		public a(String string, boolean boolean2, Function<JsonElement, T> function3, Function<T, JsonElement> function4, Function<mr, T> function5) {
			this.e = string;
			this.f = boolean2;
			this.g = function3;
			this.h = function4;
			this.i = function5;
		}

		public boolean a() {
			return this.f;
		}

		public String b() {
			return this.e;
		}

		@Nullable
		public static mv.a a(String string) {
			return (mv.a)d.get(string);
		}

		private T b(Object object) {
			return (T)object;
		}

		@Nullable
		public mv a(JsonElement jsonElement) {
			T object3 = (T)this.g.apply(jsonElement);
			return object3 == null ? null : new mv(this, object3);
		}

		@Nullable
		public mv a(mr mr) {
			T object3 = (T)this.i.apply(mr);
			return object3 == null ? null : new mv(this, object3);
		}

		public JsonElement a(Object object) {
			return (JsonElement)this.h.apply(this.b(object));
		}

		public String toString() {
			return "<action " + this.e + ">";
		}
	}

	public static class b {
		public final aoq<?> a;
		public final UUID b;
		@Nullable
		public final mr c;

		public b(aoq<?> aoq, UUID uUID, @Nullable mr mr) {
			this.a = aoq;
			this.b = uUID;
			this.c = mr;
		}

		@Nullable
		public static mv.b a(JsonElement jsonElement) {
			if (!jsonElement.isJsonObject()) {
				return null;
			} else {
				JsonObject jsonObject2 = jsonElement.getAsJsonObject();
				aoq<?> aoq3 = gl.al.a(new uh(adt.h(jsonObject2, "type")));
				UUID uUID4 = UUID.fromString(adt.h(jsonObject2, "id"));
				mr mr5 = mr.a.a(jsonObject2.get("name"));
				return new mv.b(aoq3, uUID4, mr5);
			}
		}

		@Nullable
		public static mv.b a(mr mr) {
			try {
				le le2 = lv.a(mr.getString());
				mr mr3 = mr.a.a(le2.l("name"));
				aoq<?> aoq4 = gl.al.a(new uh(le2.l("type")));
				UUID uUID5 = UUID.fromString(le2.l("id"));
				return new mv.b(aoq4, uUID5, mr3);
			} catch (CommandSyntaxException | JsonSyntaxException var5) {
				return null;
			}
		}

		public JsonElement a() {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("type", gl.al.b(this.a).toString());
			jsonObject2.addProperty("id", this.b.toString());
			if (this.c != null) {
				jsonObject2.add("name", mr.a.b(this.c));
			}

			return jsonObject2;
		}

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			} else if (object != null && this.getClass() == object.getClass()) {
				mv.b b3 = (mv.b)object;
				return this.a.equals(b3.a) && this.b.equals(b3.b) && Objects.equals(this.c, b3.c);
			} else {
				return false;
			}
		}

		public int hashCode() {
			int integer2 = this.a.hashCode();
			integer2 = 31 * integer2 + this.b.hashCode();
			return 31 * integer2 + (this.c != null ? this.c.hashCode() : 0);
		}
	}

	public static class c {
		private final bke a;
		private final int b;
		@Nullable
		private final le c;

		c(bke bke, int integer, @Nullable le le) {
			this.a = bke;
			this.b = integer;
			this.c = le;
		}

		public c(bki bki) {
			this(bki.b(), bki.E(), bki.o() != null ? bki.o().g() : null);
		}

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			} else if (object != null && this.getClass() == object.getClass()) {
				mv.c c3 = (mv.c)object;
				return this.b == c3.b && this.a.equals(c3.a) && Objects.equals(this.c, c3.c);
			} else {
				return false;
			}
		}

		public int hashCode() {
			int integer2 = this.a.hashCode();
			integer2 = 31 * integer2 + this.b;
			return 31 * integer2 + (this.c != null ? this.c.hashCode() : 0);
		}

		private static mv.c b(JsonElement jsonElement) {
			if (jsonElement.isJsonPrimitive()) {
				return new mv.c(gl.am.a(new uh(jsonElement.getAsString())), 1, null);
			} else {
				JsonObject jsonObject2 = adt.m(jsonElement, "item");
				bke bke3 = gl.am.a(new uh(adt.h(jsonObject2, "id")));
				int integer4 = adt.a(jsonObject2, "count", 1);
				if (jsonObject2.has("tag")) {
					String string5 = adt.h(jsonObject2, "tag");

					try {
						le le6 = lv.a(string5);
						return new mv.c(bke3, integer4, le6);
					} catch (CommandSyntaxException var6) {
						mv.a.warn("Failed to parse tag: {}", string5, var6);
					}
				}

				return new mv.c(bke3, integer4, null);
			}
		}

		@Nullable
		private static mv.c b(mr mr) {
			try {
				le le2 = lv.a(mr.getString());
				return new mv.c(bki.a(le2));
			} catch (CommandSyntaxException var2) {
				mv.a.warn("Failed to parse item tag: {}", mr, var2);
				return null;
			}
		}

		private JsonElement b() {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("id", gl.am.b(this.a).toString());
			if (this.b != 1) {
				jsonObject2.addProperty("count", this.b);
			}

			if (this.c != null) {
				jsonObject2.addProperty("tag", this.c.toString());
			}

			return jsonObject2;
		}
	}
}
