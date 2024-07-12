import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;

public class dba {
	private static final Map<uh, Class<? extends daz>> a = Maps.<uh, Class<? extends daz>>newHashMap();

	public static daz a(JsonElement jsonElement, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		if (jsonElement.isJsonPrimitive()) {
			return jsonDeserializationContext.deserialize(jsonElement, dap.class);
		} else {
			JsonObject jsonObject3 = jsonElement.getAsJsonObject();
			String string4 = adt.a(jsonObject3, "type", daz.b.toString());
			Class<? extends daz> class5 = (Class<? extends daz>)a.get(new uh(string4));
			if (class5 == null) {
				throw new JsonParseException("Unknown generator: " + string4);
			} else {
				return jsonDeserializationContext.deserialize(jsonObject3, class5);
			}
		}
	}

	public static JsonElement a(daz daz, JsonSerializationContext jsonSerializationContext) {
		JsonElement jsonElement3 = jsonSerializationContext.serialize(daz);
		if (jsonElement3.isJsonObject()) {
			jsonElement3.getAsJsonObject().addProperty("type", daz.a().toString());
		}

		return jsonElement3;
	}

	static {
		a.put(daz.b, dbb.class);
		a.put(daz.c, dan.class);
		a.put(daz.a, dap.class);
	}
}
