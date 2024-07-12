import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;

public class tl {
	private mr a;
	private tl.a b;
	private tl.c c;
	private String d;

	public mr a() {
		return this.a;
	}

	public void a(mr mr) {
		this.a = mr;
	}

	public tl.a b() {
		return this.b;
	}

	public void a(tl.a a) {
		this.b = a;
	}

	public tl.c c() {
		return this.c;
	}

	public void a(tl.c c) {
		this.c = c;
	}

	public void a(String string) {
		this.d = string;
	}

	public String d() {
		return this.d;
	}

	public static class a {
		private final int a;
		private final int b;
		private GameProfile[] c;

		public a(int integer1, int integer2) {
			this.a = integer1;
			this.b = integer2;
		}

		public int a() {
			return this.a;
		}

		public int b() {
			return this.b;
		}

		public GameProfile[] c() {
			return this.c;
		}

		public void a(GameProfile[] arr) {
			this.c = arr;
		}

		public static class a implements JsonDeserializer<tl.a>, JsonSerializer<tl.a> {
			public tl.a deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				JsonObject jsonObject5 = adt.m(jsonElement, "players");
				tl.a a6 = new tl.a(adt.n(jsonObject5, "max"), adt.n(jsonObject5, "online"));
				if (adt.d(jsonObject5, "sample")) {
					JsonArray jsonArray7 = adt.u(jsonObject5, "sample");
					if (jsonArray7.size() > 0) {
						GameProfile[] arr8 = new GameProfile[jsonArray7.size()];

						for (int integer9 = 0; integer9 < arr8.length; integer9++) {
							JsonObject jsonObject10 = adt.m(jsonArray7.get(integer9), "player[" + integer9 + "]");
							String string11 = adt.h(jsonObject10, "id");
							arr8[integer9] = new GameProfile(UUID.fromString(string11), adt.h(jsonObject10, "name"));
						}

						a6.a(arr8);
					}
				}

				return a6;
			}

			public JsonElement serialize(tl.a a, Type type, JsonSerializationContext jsonSerializationContext) {
				JsonObject jsonObject5 = new JsonObject();
				jsonObject5.addProperty("max", a.a());
				jsonObject5.addProperty("online", a.b());
				if (a.c() != null && a.c().length > 0) {
					JsonArray jsonArray6 = new JsonArray();

					for (int integer7 = 0; integer7 < a.c().length; integer7++) {
						JsonObject jsonObject8 = new JsonObject();
						UUID uUID9 = a.c()[integer7].getId();
						jsonObject8.addProperty("id", uUID9 == null ? "" : uUID9.toString());
						jsonObject8.addProperty("name", a.c()[integer7].getName());
						jsonArray6.add(jsonObject8);
					}

					jsonObject5.add("sample", jsonArray6);
				}

				return jsonObject5;
			}
		}
	}

	public static class b implements JsonDeserializer<tl>, JsonSerializer<tl> {
		public tl deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			JsonObject jsonObject5 = adt.m(jsonElement, "status");
			tl tl6 = new tl();
			if (jsonObject5.has("description")) {
				tl6.a(jsonDeserializationContext.deserialize(jsonObject5.get("description"), mr.class));
			}

			if (jsonObject5.has("players")) {
				tl6.a(jsonDeserializationContext.deserialize(jsonObject5.get("players"), tl.a.class));
			}

			if (jsonObject5.has("version")) {
				tl6.a(jsonDeserializationContext.deserialize(jsonObject5.get("version"), tl.c.class));
			}

			if (jsonObject5.has("favicon")) {
				tl6.a(adt.h(jsonObject5, "favicon"));
			}

			return tl6;
		}

		public JsonElement serialize(tl tl, Type type, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject5 = new JsonObject();
			if (tl.a() != null) {
				jsonObject5.add("description", jsonSerializationContext.serialize(tl.a()));
			}

			if (tl.b() != null) {
				jsonObject5.add("players", jsonSerializationContext.serialize(tl.b()));
			}

			if (tl.c() != null) {
				jsonObject5.add("version", jsonSerializationContext.serialize(tl.c()));
			}

			if (tl.d() != null) {
				jsonObject5.addProperty("favicon", tl.d());
			}

			return jsonObject5;
		}
	}

	public static class c {
		private final String a;
		private final int b;

		public c(String string, int integer) {
			this.a = string;
			this.b = integer;
		}

		public String a() {
			return this.a;
		}

		public int b() {
			return this.b;
		}

		public static class a implements JsonDeserializer<tl.c>, JsonSerializer<tl.c> {
			public tl.c deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				JsonObject jsonObject5 = adt.m(jsonElement, "version");
				return new tl.c(adt.h(jsonObject5, "name"), adt.n(jsonObject5, "protocol"));
			}

			public JsonElement serialize(tl.c c, Type type, JsonSerializationContext jsonSerializationContext) {
				JsonObject jsonObject5 = new JsonObject();
				jsonObject5.addProperty("name", c.a());
				jsonObject5.addProperty("protocol", c.b());
				return jsonObject5;
			}
		}
	}
}
