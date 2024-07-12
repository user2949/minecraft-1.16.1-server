import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;

public class az {
	public static final az a = new az();
	public static final az[] b = new az[0];
	private final bnw c;
	private final bx.d d;

	public az() {
		this.c = null;
		this.d = bx.d.e;
	}

	public az(@Nullable bnw bnw, bx.d d) {
		this.c = bnw;
		this.d = d;
	}

	public boolean a(Map<bnw, Integer> map) {
		if (this.c != null) {
			if (!map.containsKey(this.c)) {
				return false;
			}

			int integer3 = (Integer)map.get(this.c);
			if (this.d != null && !this.d.d(integer3)) {
				return false;
			}
		} else if (this.d != null) {
			for (Integer integer4 : map.values()) {
				if (this.d.d(integer4)) {
					return true;
				}
			}

			return false;
		}

		return true;
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			if (this.c != null) {
				jsonObject2.addProperty("enchantment", gl.ak.b(this.c).toString());
			}

			jsonObject2.add("levels", this.d.d());
			return jsonObject2;
		}
	}

	public static az a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "enchantment");
			bnw bnw3 = null;
			if (jsonObject2.has("enchantment")) {
				uh uh4 = new uh(adt.h(jsonObject2, "enchantment"));
				bnw3 = (bnw)gl.ak.b(uh4).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + uh4 + "'"));
			}

			bx.d d4 = bx.d.a(jsonObject2.get("levels"));
			return new az(bnw3, d4);
		} else {
			return a;
		}
	}

	public static az[] b(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonArray jsonArray2 = adt.n(jsonElement, "enchantments");
			az[] arr3 = new az[jsonArray2.size()];

			for (int integer4 = 0; integer4 < arr3.length; integer4++) {
				arr3[integer4] = a(jsonArray2.get(integer4));
			}

			return arr3;
		} else {
			return b;
		}
	}
}
