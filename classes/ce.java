import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ce {
	public static final ce a = new ce.d().b();
	private final bx.d b;
	private final bpy c;
	private final Map<acr<?>, bx.d> d;
	private final Object2BooleanMap<uh> e;
	private final Map<uh, ce.c> f;

	private static ce.c b(JsonElement jsonElement) {
		if (jsonElement.isJsonPrimitive()) {
			boolean boolean2 = jsonElement.getAsBoolean();
			return new ce.b(boolean2);
		} else {
			Object2BooleanMap<String> object2BooleanMap2 = new Object2BooleanOpenHashMap<>();
			JsonObject jsonObject3 = adt.m(jsonElement, "criterion data");
			jsonObject3.entrySet().forEach(entry -> {
				boolean boolean3 = adt.c((JsonElement)entry.getValue(), "criterion test");
				object2BooleanMap2.put((String)entry.getKey(), boolean3);
			});
			return new ce.a(object2BooleanMap2);
		}
	}

	private ce(bx.d d, bpy bpy, Map<acr<?>, bx.d> map3, Object2BooleanMap<uh> object2BooleanMap, Map<uh, ce.c> map5) {
		this.b = d;
		this.c = bpy;
		this.d = map3;
		this.e = object2BooleanMap;
		this.f = map5;
	}

	public boolean a(aom aom) {
		if (this == a) {
			return true;
		} else if (!(aom instanceof ze)) {
			return false;
		} else {
			ze ze3 = (ze)aom;
			if (!this.b.d(ze3.bK)) {
				return false;
			} else if (this.c != bpy.NOT_SET && this.c != ze3.d.b()) {
				return false;
			} else {
				acv acv4 = ze3.A();

				for (Entry<acr<?>, bx.d> entry6 : this.d.entrySet()) {
					int integer7 = acv4.a((acr<?>)entry6.getKey());
					if (!((bx.d)entry6.getValue()).d(integer7)) {
						return false;
					}
				}

				aco aco5 = ze3.B();

				for (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<uh> entry7 : this.e.object2BooleanEntrySet()) {
					if (aco5.b((uh)entry7.getKey()) != entry7.getBooleanValue()) {
						return false;
					}
				}

				if (!this.f.isEmpty()) {
					uq uq6 = ze3.J();
					us us7 = ze3.cg().ay();

					for (Entry<uh, ce.c> entry9 : this.f.entrySet()) {
						w w10 = us7.a((uh)entry9.getKey());
						if (w10 == null || !((ce.c)entry9.getValue()).test(uq6.b(w10))) {
							return false;
						}
					}
				}

				return true;
			}
		}
	}

	public static ce a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "player");
			bx.d d3 = bx.d.a(jsonObject2.get("level"));
			String string4 = adt.a(jsonObject2, "gamemode", "");
			bpy bpy5 = bpy.a(string4, bpy.NOT_SET);
			Map<acr<?>, bx.d> map6 = Maps.<acr<?>, bx.d>newHashMap();
			JsonArray jsonArray7 = adt.a(jsonObject2, "stats", null);
			if (jsonArray7 != null) {
				for (JsonElement jsonElement9 : jsonArray7) {
					JsonObject jsonObject10 = adt.m(jsonElement9, "stats entry");
					uh uh11 = new uh(adt.h(jsonObject10, "type"));
					act<?> act12 = gl.aQ.a(uh11);
					if (act12 == null) {
						throw new JsonParseException("Invalid stat type: " + uh11);
					}

					uh uh13 = new uh(adt.h(jsonObject10, "stat"));
					acr<?> acr14 = a(act12, uh13);
					bx.d d15 = bx.d.a(jsonObject10.get("value"));
					map6.put(acr14, d15);
				}
			}

			Object2BooleanMap<uh> object2BooleanMap8 = new Object2BooleanOpenHashMap<>();
			JsonObject jsonObject9 = adt.a(jsonObject2, "recipes", new JsonObject());

			for (Entry<String, JsonElement> entry11 : jsonObject9.entrySet()) {
				uh uh12 = new uh((String)entry11.getKey());
				boolean boolean13 = adt.c((JsonElement)entry11.getValue(), "recipe present");
				object2BooleanMap8.put(uh12, boolean13);
			}

			Map<uh, ce.c> map10 = Maps.<uh, ce.c>newHashMap();
			JsonObject jsonObject11 = adt.a(jsonObject2, "advancements", new JsonObject());

			for (Entry<String, JsonElement> entry13 : jsonObject11.entrySet()) {
				uh uh14 = new uh((String)entry13.getKey());
				ce.c c15 = b((JsonElement)entry13.getValue());
				map10.put(uh14, c15);
			}

			return new ce(d3, bpy5, map6, object2BooleanMap8, map10);
		} else {
			return a;
		}
	}

	private static <T> acr<T> a(act<T> act, uh uh) {
		gl<T> gl3 = act.a();
		T object4 = gl3.a(uh);
		if (object4 == null) {
			throw new JsonParseException("Unknown object " + uh + " for stat type " + gl.aQ.b(act));
		} else {
			return act.b(object4);
		}
	}

	private static <T> uh a(acr<T> acr) {
		return acr.a().a().b(acr.b());
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.add("level", this.b.d());
			if (this.c != bpy.NOT_SET) {
				jsonObject2.addProperty("gamemode", this.c.b());
			}

			if (!this.d.isEmpty()) {
				JsonArray jsonArray3 = new JsonArray();
				this.d.forEach((acr, d) -> {
					JsonObject jsonObject4 = new JsonObject();
					jsonObject4.addProperty("type", gl.aQ.b(acr.a()).toString());
					jsonObject4.addProperty("stat", a(acr).toString());
					jsonObject4.add("value", d.d());
					jsonArray3.add(jsonObject4);
				});
				jsonObject2.add("stats", jsonArray3);
			}

			if (!this.e.isEmpty()) {
				JsonObject jsonObject3 = new JsonObject();
				this.e.forEach((uh, boolean3) -> jsonObject3.addProperty(uh.toString(), boolean3));
				jsonObject2.add("recipes", jsonObject3);
			}

			if (!this.f.isEmpty()) {
				JsonObject jsonObject3 = new JsonObject();
				this.f.forEach((uh, c) -> jsonObject3.add(uh.toString(), c.a()));
				jsonObject2.add("advancements", jsonObject3);
			}

			return jsonObject2;
		}
	}

	static class a implements ce.c {
		private final Object2BooleanMap<String> a;

		public a(Object2BooleanMap<String> object2BooleanMap) {
			this.a = object2BooleanMap;
		}

		@Override
		public JsonElement a() {
			JsonObject jsonObject2 = new JsonObject();
			this.a.forEach(jsonObject2::addProperty);
			return jsonObject2;
		}

		public boolean test(y y) {
			for (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String> entry4 : this.a.object2BooleanEntrySet()) {
				ac ac5 = y.c((String)entry4.getKey());
				if (ac5 == null || ac5.a() != entry4.getBooleanValue()) {
					return false;
				}
			}

			return true;
		}
	}

	static class b implements ce.c {
		private final boolean a;

		public b(boolean boolean1) {
			this.a = boolean1;
		}

		@Override
		public JsonElement a() {
			return new JsonPrimitive(this.a);
		}

		public boolean test(y y) {
			return y.a() == this.a;
		}
	}

	interface c extends Predicate<y> {
		JsonElement a();
	}

	public static class d {
		private bx.d a = bx.d.e;
		private bpy b = bpy.NOT_SET;
		private final Map<acr<?>, bx.d> c = Maps.<acr<?>, bx.d>newHashMap();
		private final Object2BooleanMap<uh> d = new Object2BooleanOpenHashMap<>();
		private final Map<uh, ce.c> e = Maps.<uh, ce.c>newHashMap();

		public ce b() {
			return new ce(this.a, this.b, this.c, this.d, this.e);
		}
	}
}
