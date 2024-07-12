import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class by {
	public static final by a = new by(Collections.emptyMap());
	private final Map<aoe, by.a> b;

	public by(Map<aoe, by.a> map) {
		this.b = map;
	}

	public static by a() {
		return new by(Maps.<aoe, by.a>newLinkedHashMap());
	}

	public by a(aoe aoe) {
		this.b.put(aoe, new by.a());
		return this;
	}

	public boolean a(aom aom) {
		if (this == a) {
			return true;
		} else {
			return aom instanceof aoy ? this.a(((aoy)aom).dh()) : false;
		}
	}

	public boolean a(aoy aoy) {
		return this == a ? true : this.a(aoy.dh());
	}

	public boolean a(Map<aoe, aog> map) {
		if (this == a) {
			return true;
		} else {
			for (Entry<aoe, by.a> entry4 : this.b.entrySet()) {
				aog aog5 = (aog)map.get(entry4.getKey());
				if (!((by.a)entry4.getValue()).a(aog5)) {
					return false;
				}
			}

			return true;
		}
	}

	public static by a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "effects");
			Map<aoe, by.a> map3 = Maps.<aoe, by.a>newLinkedHashMap();

			for (Entry<String, JsonElement> entry5 : jsonObject2.entrySet()) {
				uh uh6 = new uh((String)entry5.getKey());
				aoe aoe7 = (aoe)gl.ai.b(uh6).orElseThrow(() -> new JsonSyntaxException("Unknown effect '" + uh6 + "'"));
				by.a a8 = by.a.a(adt.m((JsonElement)entry5.getValue(), (String)entry5.getKey()));
				map3.put(aoe7, a8);
			}

			return new by(map3);
		} else {
			return a;
		}
	}

	public JsonElement b() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();

			for (Entry<aoe, by.a> entry4 : this.b.entrySet()) {
				jsonObject2.add(gl.ai.b((aoe)entry4.getKey()).toString(), ((by.a)entry4.getValue()).a());
			}

			return jsonObject2;
		}
	}

	public static class a {
		private final bx.d a;
		private final bx.d b;
		@Nullable
		private final Boolean c;
		@Nullable
		private final Boolean d;

		public a(bx.d d1, bx.d d2, @Nullable Boolean boolean3, @Nullable Boolean boolean4) {
			this.a = d1;
			this.b = d2;
			this.c = boolean3;
			this.d = boolean4;
		}

		public a() {
			this(bx.d.e, bx.d.e, null, null);
		}

		public boolean a(@Nullable aog aog) {
			if (aog == null) {
				return false;
			} else if (!this.a.d(aog.c())) {
				return false;
			} else if (!this.b.d(aog.b())) {
				return false;
			} else {
				return this.c != null && this.c != aog.d() ? false : this.d == null || this.d == aog.e();
			}
		}

		public JsonElement a() {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.add("amplifier", this.a.d());
			jsonObject2.add("duration", this.b.d());
			jsonObject2.addProperty("ambient", this.c);
			jsonObject2.addProperty("visible", this.d);
			return jsonObject2;
		}

		public static by.a a(JsonObject jsonObject) {
			bx.d d2 = bx.d.a(jsonObject.get("amplifier"));
			bx.d d3 = bx.d.a(jsonObject.get("duration"));
			Boolean boolean4 = jsonObject.has("ambient") ? adt.j(jsonObject, "ambient") : null;
			Boolean boolean5 = jsonObject.has("visible") ? adt.j(jsonObject, "visible") : null;
			return new by.a(d2, d3, boolean4, boolean5);
		}
	}
}
