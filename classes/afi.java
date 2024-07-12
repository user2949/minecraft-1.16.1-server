import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.lang.reflect.Type;
import org.apache.commons.lang3.StringUtils;

public class afi extends aij {
	public static final Gson a = new GsonBuilder().registerTypeAdapter(mr.class, new JsonDeserializer<mr>() {
		public mx deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			if (jsonElement.isJsonPrimitive()) {
				return new nd(jsonElement.getAsString());
			} else if (jsonElement.isJsonArray()) {
				JsonArray jsonArray5 = jsonElement.getAsJsonArray();
				mx mx6 = null;

				for (JsonElement jsonElement8 : jsonArray5) {
					mx mx9 = this.a(jsonElement8, jsonElement8.getClass(), jsonDeserializationContext);
					if (mx6 == null) {
						mx6 = mx9;
					} else {
						mx6.a(mx9);
					}
				}

				return mx6;
			} else {
				throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
			}
		}
	}).create();

	public afi(Schema schema, boolean boolean2) {
		super(schema, boolean2, "BlockEntitySignTextStrictJsonFix", ajb.k, "Sign");
	}

	private Dynamic<?> a(Dynamic<?> dynamic, String string) {
		String string4 = dynamic.get(string).asString("");
		mr mr5 = null;
		if (!"null".equals(string4) && !StringUtils.isEmpty(string4)) {
			if (string4.charAt(0) == '"' && string4.charAt(string4.length() - 1) == '"' || string4.charAt(0) == '{' && string4.charAt(string4.length() - 1) == '}') {
				try {
					mr5 = adt.a(a, string4, mr.class, true);
					if (mr5 == null) {
						mr5 = nd.d;
					}
				} catch (JsonParseException var8) {
				}

				if (mr5 == null) {
					try {
						mr5 = mr.a.a(string4);
					} catch (JsonParseException var7) {
					}
				}

				if (mr5 == null) {
					try {
						mr5 = mr.a.b(string4);
					} catch (JsonParseException var6) {
					}
				}

				if (mr5 == null) {
					mr5 = new nd(string4);
				}
			} else {
				mr5 = new nd(string4);
			}
		} else {
			mr5 = nd.d;
		}

		return dynamic.set(string, dynamic.createString(mr.a.a(mr5)));
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), dynamic -> {
			dynamic = this.a(dynamic, "Text1");
			dynamic = this.a(dynamic, "Text2");
			dynamic = this.a(dynamic, "Text3");
			return this.a(dynamic, "Text4");
		});
	}
}
