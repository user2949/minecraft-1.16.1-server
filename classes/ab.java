import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class ab {
	private final ae a;

	public ab(ae ae) {
		this.a = ae;
	}

	public ab() {
		this.a = null;
	}

	public void a(mg mg) {
	}

	public static ab a(JsonObject jsonObject, av av) {
		uh uh3 = new uh(adt.h(jsonObject, "trigger"));
		ad<?> ad4 = aa.a(uh3);
		if (ad4 == null) {
			throw new JsonSyntaxException("Invalid criterion trigger: " + uh3);
		} else {
			ae ae5 = ad4.a(adt.a(jsonObject, "conditions", new JsonObject()), av);
			return new ab(ae5);
		}
	}

	public static ab b(mg mg) {
		return new ab();
	}

	public static Map<String, ab> b(JsonObject jsonObject, av av) {
		Map<String, ab> map3 = Maps.<String, ab>newHashMap();

		for (Entry<String, JsonElement> entry5 : jsonObject.entrySet()) {
			map3.put(entry5.getKey(), a(adt.m((JsonElement)entry5.getValue(), "criterion"), av));
		}

		return map3;
	}

	public static Map<String, ab> c(mg mg) {
		Map<String, ab> map2 = Maps.<String, ab>newHashMap();
		int integer3 = mg.i();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			map2.put(mg.e(32767), b(mg));
		}

		return map2;
	}

	public static void a(Map<String, ab> map, mg mg) {
		mg.d(map.size());

		for (Entry<String, ab> entry4 : map.entrySet()) {
			mg.a((String)entry4.getKey());
			((ab)entry4.getValue()).a(mg);
		}
	}

	@Nullable
	public ae a() {
		return this.a;
	}

	public JsonElement b() {
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("trigger", this.a.a().toString());
		JsonObject jsonObject3 = this.a.a(cg.a);
		if (jsonObject3.size() != 0) {
			jsonObject2.add("conditions", jsonObject3);
		}

		return jsonObject2;
	}
}
