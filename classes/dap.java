import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;

public final class dap implements daz {
	private final int d;

	public dap(int integer) {
		this.d = integer;
	}

	@Override
	public int a(Random random) {
		return this.d;
	}

	@Override
	public uh a() {
		return a;
	}

	public static dap a(int integer) {
		return new dap(integer);
	}

	public static class a implements JsonDeserializer<dap>, JsonSerializer<dap> {
		public dap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			return new dap(adt.g(jsonElement, "value"));
		}

		public JsonElement serialize(dap dap, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(dap.d);
		}
	}
}
