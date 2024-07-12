import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public interface mr extends Message, mu {
	nb c();

	String a();

	@Override
	default String getString() {
		return mu.super.getString();
	}

	default String a(int integer) {
		StringBuilder stringBuilder3 = new StringBuilder();
		this.a(string -> {
			int integer4 = integer - stringBuilder3.length();
			if (integer4 <= 0) {
				return b;
			} else {
				stringBuilder3.append(string.length() <= integer4 ? string : string.substring(0, integer4));
				return Optional.empty();
			}
		});
		return stringBuilder3.toString();
	}

	List<mr> b();

	mx f();

	mx e();

	@Override
	default <T> Optional<T> a(mu.a<T> a) {
		Optional<T> optional3 = this.b(a);
		if (optional3.isPresent()) {
			return optional3;
		} else {
			for (mr mr5 : this.b()) {
				Optional<T> optional6 = mr5.a(a);
				if (optional6.isPresent()) {
					return optional6;
				}
			}

			return Optional.empty();
		}
	}

	default <T> Optional<T> b(mu.a<T> a) {
		return a.accept(this.a());
	}

	public static class a implements JsonDeserializer<mx>, JsonSerializer<mr> {
		private static final Gson a = v.a((Supplier<Gson>)(() -> {
			GsonBuilder gsonBuilder1 = new GsonBuilder();
			gsonBuilder1.disableHtmlEscaping();
			gsonBuilder1.registerTypeHierarchyAdapter(mr.class, new mr.a());
			gsonBuilder1.registerTypeHierarchyAdapter(nb.class, new nb.a());
			gsonBuilder1.registerTypeAdapterFactory(new aeb());
			return gsonBuilder1.create();
		}));
		private static final Field b = v.a((Supplier<Field>)(() -> {
			try {
				new JsonReader(new StringReader(""));
				Field field1 = JsonReader.class.getDeclaredField("pos");
				field1.setAccessible(true);
				return field1;
			} catch (NoSuchFieldException var1) {
				throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", var1);
			}
		}));
		private static final Field c = v.a((Supplier<Field>)(() -> {
			try {
				new JsonReader(new StringReader(""));
				Field field1 = JsonReader.class.getDeclaredField("lineStart");
				field1.setAccessible(true);
				return field1;
			} catch (NoSuchFieldException var1) {
				throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", var1);
			}
		}));

		public mx deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			if (jsonElement.isJsonPrimitive()) {
				return new nd(jsonElement.getAsString());
			} else if (!jsonElement.isJsonObject()) {
				if (jsonElement.isJsonArray()) {
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
			} else {
				JsonObject jsonObject5 = jsonElement.getAsJsonObject();
				mx mx6;
				if (jsonObject5.has("text")) {
					mx6 = new nd(adt.h(jsonObject5, "text"));
				} else if (jsonObject5.has("translate")) {
					String string7 = adt.h(jsonObject5, "translate");
					if (jsonObject5.has("with")) {
						JsonArray jsonArray8 = adt.u(jsonObject5, "with");
						Object[] arr9 = new Object[jsonArray8.size()];

						for (int integer10 = 0; integer10 < arr9.length; integer10++) {
							arr9[integer10] = this.a(jsonArray8.get(integer10), type, jsonDeserializationContext);
							if (arr9[integer10] instanceof nd) {
								nd nd11 = (nd)arr9[integer10];
								if (nd11.c().g() && nd11.b().isEmpty()) {
									arr9[integer10] = nd11.g();
								}
							}
						}

						mx6 = new ne(string7, arr9);
					} else {
						mx6 = new ne(string7);
					}
				} else if (jsonObject5.has("score")) {
					JsonObject jsonObject7 = adt.t(jsonObject5, "score");
					if (!jsonObject7.has("name") || !jsonObject7.has("objective")) {
						throw new JsonParseException("A score component needs a least a name and an objective");
					}

					mx6 = new mz(adt.h(jsonObject7, "name"), adt.h(jsonObject7, "objective"));
				} else if (jsonObject5.has("selector")) {
					mx6 = new na(adt.h(jsonObject5, "selector"));
				} else if (jsonObject5.has("keybind")) {
					mx6 = new mw(adt.h(jsonObject5, "keybind"));
				} else {
					if (!jsonObject5.has("nbt")) {
						throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
					}

					String string7 = adt.h(jsonObject5, "nbt");
					boolean boolean8 = adt.a(jsonObject5, "interpret", false);
					if (jsonObject5.has("block")) {
						mx6 = new my.a(string7, boolean8, adt.h(jsonObject5, "block"));
					} else if (jsonObject5.has("entity")) {
						mx6 = new my.b(string7, boolean8, adt.h(jsonObject5, "entity"));
					} else {
						if (!jsonObject5.has("storage")) {
							throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
						}

						mx6 = new my.c(string7, boolean8, new uh(adt.h(jsonObject5, "storage")));
					}
				}

				if (jsonObject5.has("extra")) {
					JsonArray jsonArray7 = adt.u(jsonObject5, "extra");
					if (jsonArray7.size() <= 0) {
						throw new JsonParseException("Unexpected empty array of components");
					}

					for (int integer8 = 0; integer8 < jsonArray7.size(); integer8++) {
						mx6.a(this.a(jsonArray7.get(integer8), type, jsonDeserializationContext));
					}
				}

				mx6.a(jsonDeserializationContext.deserialize(jsonElement, nb.class));
				return mx6;
			}
		}

		private void a(nb nb, JsonObject jsonObject, JsonSerializationContext jsonSerializationContext) {
			JsonElement jsonElement5 = jsonSerializationContext.serialize(nb);
			if (jsonElement5.isJsonObject()) {
				JsonObject jsonObject6 = (JsonObject)jsonElement5;

				for (Entry<String, JsonElement> entry8 : jsonObject6.entrySet()) {
					jsonObject.add((String)entry8.getKey(), (JsonElement)entry8.getValue());
				}
			}
		}

		public JsonElement serialize(mr mr, Type type, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject5 = new JsonObject();
			if (!mr.c().g()) {
				this.a(mr.c(), jsonObject5, jsonSerializationContext);
			}

			if (!mr.b().isEmpty()) {
				JsonArray jsonArray6 = new JsonArray();

				for (mr mr8 : mr.b()) {
					jsonArray6.add(this.a(mr8, mr8.getClass(), jsonSerializationContext));
				}

				jsonObject5.add("extra", jsonArray6);
			}

			if (mr instanceof nd) {
				jsonObject5.addProperty("text", ((nd)mr).g());
			} else if (mr instanceof ne) {
				ne ne6 = (ne)mr;
				jsonObject5.addProperty("translate", ne6.h());
				if (ne6.i() != null && ne6.i().length > 0) {
					JsonArray jsonArray7 = new JsonArray();

					for (Object object11 : ne6.i()) {
						if (object11 instanceof mr) {
							jsonArray7.add(this.a((mr)object11, object11.getClass(), jsonSerializationContext));
						} else {
							jsonArray7.add(new JsonPrimitive(String.valueOf(object11)));
						}
					}

					jsonObject5.add("with", jsonArray7);
				}
			} else if (mr instanceof mz) {
				mz mz6 = (mz)mr;
				JsonObject jsonObject7 = new JsonObject();
				jsonObject7.addProperty("name", mz6.g());
				jsonObject7.addProperty("objective", mz6.i());
				jsonObject5.add("score", jsonObject7);
			} else if (mr instanceof na) {
				na na6 = (na)mr;
				jsonObject5.addProperty("selector", na6.g());
			} else if (mr instanceof mw) {
				mw mw6 = (mw)mr;
				jsonObject5.addProperty("keybind", mw6.h());
			} else {
				if (!(mr instanceof my)) {
					throw new IllegalArgumentException("Don't know how to serialize " + mr + " as a Component");
				}

				my my6 = (my)mr;
				jsonObject5.addProperty("nbt", my6.g());
				jsonObject5.addProperty("interpret", my6.h());
				if (mr instanceof my.a) {
					my.a a7 = (my.a)mr;
					jsonObject5.addProperty("block", a7.i());
				} else if (mr instanceof my.b) {
					my.b b7 = (my.b)mr;
					jsonObject5.addProperty("entity", b7.i());
				} else {
					if (!(mr instanceof my.c)) {
						throw new IllegalArgumentException("Don't know how to serialize " + mr + " as a Component");
					}

					my.c c7 = (my.c)mr;
					jsonObject5.addProperty("storage", c7.i().toString());
				}
			}

			return jsonObject5;
		}

		public static String a(mr mr) {
			return a.toJson(mr);
		}

		public static JsonElement b(mr mr) {
			return a.toJsonTree(mr);
		}

		@Nullable
		public static mx a(String string) {
			return adt.a(a, string, mx.class, false);
		}

		@Nullable
		public static mx a(JsonElement jsonElement) {
			return a.fromJson(jsonElement, mx.class);
		}

		@Nullable
		public static mx b(String string) {
			return adt.a(a, string, mx.class, true);
		}

		public static mx a(com.mojang.brigadier.StringReader stringReader) {
			try {
				JsonReader jsonReader2 = new JsonReader(new StringReader(stringReader.getRemaining()));
				jsonReader2.setLenient(false);
				mx mx3 = a.<mx>getAdapter(mx.class).read(jsonReader2);
				stringReader.setCursor(stringReader.getCursor() + a(jsonReader2));
				return mx3;
			} catch (StackOverflowError | IOException var3) {
				throw new JsonParseException(var3);
			}
		}

		private static int a(JsonReader jsonReader) {
			try {
				return b.getInt(jsonReader) - c.getInt(jsonReader) + 1;
			} catch (IllegalAccessException var2) {
				throw new IllegalStateException("Couldn't read position of JsonReader", var2);
			}
		}
	}
}
