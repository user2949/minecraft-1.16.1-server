import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class adt {
	private static final Gson a = new GsonBuilder().create();

	public static boolean a(JsonObject jsonObject, String string) {
		return !f(jsonObject, string) ? false : jsonObject.getAsJsonPrimitive(string).isString();
	}

	public static boolean b(JsonElement jsonElement) {
		return !jsonElement.isJsonPrimitive() ? false : jsonElement.getAsJsonPrimitive().isNumber();
	}

	public static boolean d(JsonObject jsonObject, String string) {
		return !g(jsonObject, string) ? false : jsonObject.get(string).isJsonArray();
	}

	public static boolean f(JsonObject jsonObject, String string) {
		return !g(jsonObject, string) ? false : jsonObject.get(string).isJsonPrimitive();
	}

	public static boolean g(JsonObject jsonObject, String string) {
		return jsonObject == null ? false : jsonObject.get(string) != null;
	}

	public static String a(JsonElement jsonElement, String string) {
		if (jsonElement.isJsonPrimitive()) {
			return jsonElement.getAsString();
		} else {
			throw new JsonSyntaxException("Expected " + string + " to be a string, was " + d(jsonElement));
		}
	}

	public static String h(JsonObject jsonObject, String string) {
		if (jsonObject.has(string)) {
			return a(jsonObject.get(string), string);
		} else {
			throw new JsonSyntaxException("Missing " + string + ", expected to find a string");
		}
	}

	public static String a(JsonObject jsonObject, String string2, String string3) {
		return jsonObject.has(string2) ? a(jsonObject.get(string2), string2) : string3;
	}

	public static bke b(JsonElement jsonElement, String string) {
		if (jsonElement.isJsonPrimitive()) {
			String string3 = jsonElement.getAsString();
			return (bke)gl.am
				.b(new uh(string3))
				.orElseThrow(() -> new JsonSyntaxException("Expected " + string + " to be an item, was unknown string '" + string3 + "'"));
		} else {
			throw new JsonSyntaxException("Expected " + string + " to be an item, was " + d(jsonElement));
		}
	}

	public static bke i(JsonObject jsonObject, String string) {
		if (jsonObject.has(string)) {
			return b(jsonObject.get(string), string);
		} else {
			throw new JsonSyntaxException("Missing " + string + ", expected to find an item");
		}
	}

	public static boolean c(JsonElement jsonElement, String string) {
		if (jsonElement.isJsonPrimitive()) {
			return jsonElement.getAsBoolean();
		} else {
			throw new JsonSyntaxException("Expected " + string + " to be a Boolean, was " + d(jsonElement));
		}
	}

	public static boolean j(JsonObject jsonObject, String string) {
		if (jsonObject.has(string)) {
			return c(jsonObject.get(string), string);
		} else {
			throw new JsonSyntaxException("Missing " + string + ", expected to find a Boolean");
		}
	}

	public static boolean a(JsonObject jsonObject, String string, boolean boolean3) {
		return jsonObject.has(string) ? c(jsonObject.get(string), string) : boolean3;
	}

	public static float e(JsonElement jsonElement, String string) {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return jsonElement.getAsFloat();
		} else {
			throw new JsonSyntaxException("Expected " + string + " to be a Float, was " + d(jsonElement));
		}
	}

	public static float l(JsonObject jsonObject, String string) {
		if (jsonObject.has(string)) {
			return e(jsonObject.get(string), string);
		} else {
			throw new JsonSyntaxException("Missing " + string + ", expected to find a Float");
		}
	}

	public static float a(JsonObject jsonObject, String string, float float3) {
		return jsonObject.has(string) ? e(jsonObject.get(string), string) : float3;
	}

	public static long f(JsonElement jsonElement, String string) {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return jsonElement.getAsLong();
		} else {
			throw new JsonSyntaxException("Expected " + string + " to be a Long, was " + d(jsonElement));
		}
	}

	public static long m(JsonObject jsonObject, String string) {
		if (jsonObject.has(string)) {
			return f(jsonObject.get(string), string);
		} else {
			throw new JsonSyntaxException("Missing " + string + ", expected to find a Long");
		}
	}

	public static long a(JsonObject jsonObject, String string, long long3) {
		return jsonObject.has(string) ? f(jsonObject.get(string), string) : long3;
	}

	public static int g(JsonElement jsonElement, String string) {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return jsonElement.getAsInt();
		} else {
			throw new JsonSyntaxException("Expected " + string + " to be a Int, was " + d(jsonElement));
		}
	}

	public static int n(JsonObject jsonObject, String string) {
		if (jsonObject.has(string)) {
			return g(jsonObject.get(string), string);
		} else {
			throw new JsonSyntaxException("Missing " + string + ", expected to find a Int");
		}
	}

	public static int a(JsonObject jsonObject, String string, int integer) {
		return jsonObject.has(string) ? g(jsonObject.get(string), string) : integer;
	}

	public static byte h(JsonElement jsonElement, String string) {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return jsonElement.getAsByte();
		} else {
			throw new JsonSyntaxException("Expected " + string + " to be a Byte, was " + d(jsonElement));
		}
	}

	public static byte a(JsonObject jsonObject, String string, byte byte3) {
		return jsonObject.has(string) ? h(jsonObject.get(string), string) : byte3;
	}

	public static JsonObject m(JsonElement jsonElement, String string) {
		if (jsonElement.isJsonObject()) {
			return jsonElement.getAsJsonObject();
		} else {
			throw new JsonSyntaxException("Expected " + string + " to be a JsonObject, was " + d(jsonElement));
		}
	}

	public static JsonObject t(JsonObject jsonObject, String string) {
		if (jsonObject.has(string)) {
			return m(jsonObject.get(string), string);
		} else {
			throw new JsonSyntaxException("Missing " + string + ", expected to find a JsonObject");
		}
	}

	public static JsonObject a(JsonObject jsonObject1, String string, JsonObject jsonObject3) {
		return jsonObject1.has(string) ? m(jsonObject1.get(string), string) : jsonObject3;
	}

	public static JsonArray n(JsonElement jsonElement, String string) {
		if (jsonElement.isJsonArray()) {
			return jsonElement.getAsJsonArray();
		} else {
			throw new JsonSyntaxException("Expected " + string + " to be a JsonArray, was " + d(jsonElement));
		}
	}

	public static JsonArray u(JsonObject jsonObject, String string) {
		if (jsonObject.has(string)) {
			return n(jsonObject.get(string), string);
		} else {
			throw new JsonSyntaxException("Missing " + string + ", expected to find a JsonArray");
		}
	}

	@Nullable
	public static JsonArray a(JsonObject jsonObject, String string, @Nullable JsonArray jsonArray) {
		return jsonObject.has(string) ? n(jsonObject.get(string), string) : jsonArray;
	}

	public static <T> T a(@Nullable JsonElement jsonElement, String string, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> class4) {
		if (jsonElement != null) {
			return jsonDeserializationContext.deserialize(jsonElement, class4);
		} else {
			throw new JsonSyntaxException("Missing " + string);
		}
	}

	public static <T> T a(JsonObject jsonObject, String string, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> class4) {
		if (jsonObject.has(string)) {
			return a(jsonObject.get(string), string, jsonDeserializationContext, class4);
		} else {
			throw new JsonSyntaxException("Missing " + string);
		}
	}

	public static <T> T a(JsonObject jsonObject, String string, T object, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> class5) {
		return jsonObject.has(string) ? a(jsonObject.get(string), string, jsonDeserializationContext, class5) : object;
	}

	public static String d(JsonElement jsonElement) {
		String string2 = StringUtils.abbreviateMiddle(String.valueOf(jsonElement), "...", 10);
		if (jsonElement == null) {
			return "null (missing)";
		} else if (jsonElement.isJsonNull()) {
			return "null (json)";
		} else if (jsonElement.isJsonArray()) {
			return "an array (" + string2 + ")";
		} else if (jsonElement.isJsonObject()) {
			return "an object (" + string2 + ")";
		} else {
			if (jsonElement.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive3 = jsonElement.getAsJsonPrimitive();
				if (jsonPrimitive3.isNumber()) {
					return "a number (" + string2 + ")";
				}

				if (jsonPrimitive3.isBoolean()) {
					return "a boolean (" + string2 + ")";
				}
			}

			return string2;
		}
	}

	@Nullable
	public static <T> T a(Gson gson, Reader reader, Class<T> class3, boolean boolean4) {
		try {
			JsonReader jsonReader5 = new JsonReader(reader);
			jsonReader5.setLenient(boolean4);
			return gson.<T>getAdapter(class3).read(jsonReader5);
		} catch (IOException var5) {
			throw new JsonParseException(var5);
		}
	}

	@Nullable
	public static <T> T a(Gson gson, Reader reader, TypeToken<T> typeToken, boolean boolean4) {
		try {
			JsonReader jsonReader5 = new JsonReader(reader);
			jsonReader5.setLenient(boolean4);
			return gson.getAdapter(typeToken).read(jsonReader5);
		} catch (IOException var5) {
			throw new JsonParseException(var5);
		}
	}

	@Nullable
	public static <T> T a(Gson gson, String string, Class<T> class3, boolean boolean4) {
		return a(gson, new StringReader(string), class3, boolean4);
	}

	@Nullable
	public static <T> T a(Gson gson, Reader reader, TypeToken<T> typeToken) {
		return a(gson, reader, typeToken, false);
	}

	@Nullable
	public static <T> T a(Gson gson, Reader reader, Class<T> class3) {
		return a(gson, reader, class3, false);
	}

	@Nullable
	public static <T> T a(Gson gson, String string, Class<T> class3) {
		return a(gson, string, class3, false);
	}

	public static JsonObject a(String string, boolean boolean2) {
		return a(new StringReader(string), boolean2);
	}

	public static JsonObject a(Reader reader, boolean boolean2) {
		return a(a, reader, JsonObject.class, boolean2);
	}

	public static JsonObject a(String string) {
		return a(string, false);
	}

	public static JsonObject a(Reader reader) {
		return a(reader, false);
	}
}
