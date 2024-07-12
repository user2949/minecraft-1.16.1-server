import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.function.Function;
import javax.annotation.Nullable;

public class dar {
	public static <E, T extends dbd<E>> dar.a<E, T> a(gl<T> gl, String string2, String string3, Function<E, T> function) {
		return new dar.a<>(gl, string2, string3, function);
	}

	public static class a<E, T extends dbd<E>> {
		private final gl<T> a;
		private final String b;
		private final String c;
		private final Function<E, T> d;
		@Nullable
		private Pair<T, dar.b<? extends E>> e;

		private a(gl<T> gl, String string2, String string3, Function<E, T> function) {
			this.a = gl;
			this.b = string2;
			this.c = string3;
			this.d = function;
		}

		public Object a() {
			return new dar.c(this.a, this.b, this.c, this.d, this.e);
		}
	}

	public interface b<T> {
		JsonElement a(T object, JsonSerializationContext jsonSerializationContext);

		T a(JsonElement jsonElement, JsonDeserializationContext jsonDeserializationContext);
	}

	static class c<E, T extends dbd<E>> implements JsonDeserializer<E>, JsonSerializer<E> {
		private final gl<T> a;
		private final String b;
		private final String c;
		private final Function<E, T> d;
		@Nullable
		private final Pair<T, dar.b<? extends E>> e;

		private c(gl<T> gl, String string2, String string3, Function<E, T> function, @Nullable Pair<T, dar.b<? extends E>> pair) {
			this.a = gl;
			this.b = string2;
			this.c = string3;
			this.d = function;
			this.e = pair;
		}

		@Override
		public E deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			if (jsonElement.isJsonObject()) {
				JsonObject jsonObject5 = adt.m(jsonElement, this.b);
				uh uh6 = new uh(adt.h(jsonObject5, this.c));
				T dbd7 = this.a.a(uh6);
				if (dbd7 == null) {
					throw new JsonSyntaxException("Unknown type '" + uh6 + "'");
				} else {
					return (E)dbd7.a().a(jsonObject5, jsonDeserializationContext);
				}
			} else if (this.e == null) {
				throw new UnsupportedOperationException("Object " + jsonElement + " can't be deserialized");
			} else {
				return (E)this.e.getSecond().a(jsonElement, jsonDeserializationContext);
			}
		}

		@Override
		public JsonElement serialize(E object, Type type, JsonSerializationContext jsonSerializationContext) {
			T dbd5 = (T)this.d.apply(object);
			if (this.e != null && this.e.getFirst() == dbd5) {
				return this.e.getSecond().a(object, jsonSerializationContext);
			} else if (dbd5 == null) {
				throw new JsonSyntaxException("Unknown type: " + object);
			} else {
				JsonObject jsonObject6 = new JsonObject();
				jsonObject6.addProperty(this.c, this.a.b(dbd5).toString());
				dbd5.a().a(jsonObject6, object, jsonSerializationContext);
				return jsonObject6;
			}
		}
	}
}
