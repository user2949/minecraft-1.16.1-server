import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;

public final class dan implements daz {
	private final int d;
	private final float e;

	public dan(int integer, float float2) {
		this.d = integer;
		this.e = float2;
	}

	@Override
	public int a(Random random) {
		int integer3 = 0;

		for (int integer4 = 0; integer4 < this.d; integer4++) {
			if (random.nextFloat() < this.e) {
				integer3++;
			}
		}

		return integer3;
	}

	public static dan a(int integer, float float2) {
		return new dan(integer, float2);
	}

	@Override
	public uh a() {
		return c;
	}

	public static class a implements JsonDeserializer<dan>, JsonSerializer<dan> {
		public dan deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			JsonObject jsonObject5 = adt.m(jsonElement, "value");
			int integer6 = adt.n(jsonObject5, "n");
			float float7 = adt.l(jsonObject5, "p");
			return new dan(integer6, float7);
		}

		public JsonElement serialize(dan dan, Type type, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject5 = new JsonObject();
			jsonObject5.addProperty("n", dan.d);
			jsonObject5.addProperty("p", dan.e);
			return jsonObject5;
		}
	}
}
