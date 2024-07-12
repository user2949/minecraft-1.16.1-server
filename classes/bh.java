import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;

public class bh {
	public static final bh a = new bh(false);
	private boolean b;

	private bh(boolean boolean1) {
		this.b = boolean1;
	}

	public static bh a(boolean boolean1) {
		return new bh(boolean1);
	}

	public static bh a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "fishing_hook");
			JsonElement jsonElement3 = jsonObject2.get("in_open_water");
			return jsonElement3 != null ? new bh(adt.c(jsonElement3, "in_open_water")) : a;
		} else {
			return a;
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.add("in_open_water", new JsonPrimitive(this.b));
			return jsonObject2;
		}
	}

	public boolean a(aom aom) {
		if (this == a) {
			return true;
		} else if (!(aom instanceof beo)) {
			return false;
		} else {
			beo beo3 = (beo)aom;
			return this.b == beo3.g();
		}
	}
}
