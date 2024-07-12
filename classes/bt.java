import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class bt {
	public static final bt a = new bt(bx.d.e);
	private final bx.d b;

	private bt(bx.d d) {
		this.b = d;
	}

	public boolean a(zd zd, fu fu) {
		if (this == a) {
			return true;
		} else {
			return !zd.p(fu) ? false : this.b.d(zd.B(fu));
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.add("light", this.b.d());
			return jsonObject2;
		}
	}

	public static bt a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "light");
			bx.d d3 = bx.d.a(jsonObject2.get("light"));
			return new bt(d3);
		} else {
			return a;
		}
	}
}
