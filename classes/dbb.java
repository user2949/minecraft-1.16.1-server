import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;

public class dbb implements daz {
	private final float d;
	private final float e;

	public dbb(float float1, float float2) {
		this.d = float1;
		this.e = float2;
	}

	public dbb(float float1) {
		this.d = float1;
		this.e = float1;
	}

	public static dbb a(float float1, float float2) {
		return new dbb(float1, float2);
	}

	public float b() {
		return this.d;
	}

	public float c() {
		return this.e;
	}

	@Override
	public int a(Random random) {
		return aec.a(random, aec.d(this.d), aec.d(this.e));
	}

	public float b(Random random) {
		return aec.a(random, this.d, this.e);
	}

	public boolean a(int integer) {
		return (float)integer <= this.e && (float)integer >= this.d;
	}

	@Override
	public uh a() {
		return b;
	}

	public static class a implements JsonDeserializer<dbb>, JsonSerializer<dbb> {
		public dbb deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			if (adt.b(jsonElement)) {
				return new dbb(adt.e(jsonElement, "value"));
			} else {
				JsonObject jsonObject5 = adt.m(jsonElement, "value");
				float float6 = adt.l(jsonObject5, "min");
				float float7 = adt.l(jsonObject5, "max");
				return new dbb(float6, float7);
			}
		}

		public JsonElement serialize(dbb dbb, Type type, JsonSerializationContext jsonSerializationContext) {
			if (dbb.d == dbb.e) {
				return new JsonPrimitive(dbb.d);
			} else {
				JsonObject jsonObject5 = new JsonObject();
				jsonObject5.addProperty("min", dbb.d);
				jsonObject5.addProperty("max", dbb.e);
				return jsonObject5;
			}
		}
	}
}
