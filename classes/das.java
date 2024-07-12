import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.function.IntUnaryOperator;
import javax.annotation.Nullable;

public class das implements IntUnaryOperator {
	private final Integer a;
	private final Integer b;
	private final IntUnaryOperator c;

	private das(@Nullable Integer integer1, @Nullable Integer integer2) {
		this.a = integer1;
		this.b = integer2;
		if (integer1 == null) {
			if (integer2 == null) {
				this.c = integer -> integer;
			} else {
				int integer4 = integer2;
				this.c = integer2x -> Math.min(integer4, integer2x);
			}
		} else {
			int integer4 = integer1;
			if (integer2 == null) {
				this.c = integer2x -> Math.max(integer4, integer2x);
			} else {
				int integer5 = integer2;
				this.c = integer3 -> aec.a(integer3, integer4, integer5);
			}
		}
	}

	public static das a(int integer1, int integer2) {
		return new das(integer1, integer2);
	}

	public static das a(int integer) {
		return new das(integer, null);
	}

	public static das b(int integer) {
		return new das(null, integer);
	}

	public int applyAsInt(int integer) {
		return this.c.applyAsInt(integer);
	}

	public static class a implements JsonDeserializer<das>, JsonSerializer<das> {
		public das deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			JsonObject jsonObject5 = adt.m(jsonElement, "value");
			Integer integer6 = jsonObject5.has("min") ? adt.n(jsonObject5, "min") : null;
			Integer integer7 = jsonObject5.has("max") ? adt.n(jsonObject5, "max") : null;
			return new das(integer6, integer7);
		}

		public JsonElement serialize(das das, Type type, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject5 = new JsonObject();
			if (das.b != null) {
				jsonObject5.addProperty("max", das.b);
			}

			if (das.a != null) {
				jsonObject5.addProperty("min", das.a);
			}

			return jsonObject5;
		}
	}
}
