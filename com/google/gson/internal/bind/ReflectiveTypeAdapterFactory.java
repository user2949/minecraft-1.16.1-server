package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal..Gson.Types;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
	private final ConstructorConstructor constructorConstructor;
	private final FieldNamingStrategy fieldNamingPolicy;
	private final Excluder excluder;
	private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;

	public ReflectiveTypeAdapterFactory(
		ConstructorConstructor constructorConstructor,
		FieldNamingStrategy fieldNamingPolicy,
		Excluder excluder,
		JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory
	) {
		this.constructorConstructor = constructorConstructor;
		this.fieldNamingPolicy = fieldNamingPolicy;
		this.excluder = excluder;
		this.jsonAdapterFactory = jsonAdapterFactory;
	}

	public boolean excludeField(Field f, boolean serialize) {
		return excludeField(f, serialize, this.excluder);
	}

	static boolean excludeField(Field f, boolean serialize, Excluder excluder) {
		return !excluder.excludeClass(f.getType(), serialize) && !excluder.excludeField(f, serialize);
	}

	private List<String> getFieldNames(Field f) {
		SerializedName annotation = (SerializedName)f.getAnnotation(SerializedName.class);
		if (annotation == null) {
			String name = this.fieldNamingPolicy.translateName(f);
			return Collections.singletonList(name);
		} else {
			String serializedName = annotation.value();
			String[] alternates = annotation.alternate();
			if (alternates.length == 0) {
				return Collections.singletonList(serializedName);
			} else {
				List<String> fieldNames = new ArrayList(alternates.length + 1);
				fieldNames.add(serializedName);

				for (String alternate : alternates) {
					fieldNames.add(alternate);
				}

				return fieldNames;
			}
		}
	}

	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Class<? super T> raw = type.getRawType();
		if (!Object.class.isAssignableFrom(raw)) {
			return null;
		} else {
			ObjectConstructor<T> constructor = this.constructorConstructor.get(type);
			return new ReflectiveTypeAdapterFactory.Adapter<>(constructor, this.getBoundFields(gson, type, raw));
		}
	}

	private ReflectiveTypeAdapterFactory.BoundField createBoundField(
		Gson context, Field field, String name, TypeToken<?> fieldType, boolean serialize, boolean deserialize
	) {
		final boolean isPrimitive = Primitives.isPrimitive(fieldType.getRawType());
		JsonAdapter annotation = (JsonAdapter)field.getAnnotation(JsonAdapter.class);
		TypeAdapter<?> mapped = null;
		if (annotation != null) {
			mapped = this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, context, fieldType, annotation);
		}

		final boolean jsonAdapterPresent = mapped != null;
		if (mapped == null) {
			mapped = context.getAdapter(fieldType);
		}

		final TypeAdapter<?> typeAdapter = mapped;
		return new ReflectiveTypeAdapterFactory.BoundField(name, serialize, deserialize) {
			@Override
			void write(JsonWriter writer, Object value) throws IOException, IllegalAccessException {
				Object fieldValue = field.get(value);
				TypeAdapter t = (TypeAdapter)(jsonAdapterPresent ? typeAdapter : new TypeAdapterRuntimeTypeWrapper<>(context, typeAdapter, fieldType.getType()));
				t.write(writer, fieldValue);
			}

			@Override
			void read(JsonReader reader, Object value) throws IOException, IllegalAccessException {
				Object fieldValue = typeAdapter.read(reader);
				if (fieldValue != null || !isPrimitive) {
					field.set(value, fieldValue);
				}
			}

			@Override
			public boolean writeField(Object value) throws IOException, IllegalAccessException {
				if (!this.serialized) {
					return false;
				} else {
					Object fieldValue = field.get(value);
					return fieldValue != value;
				}
			}
		};
	}

	private Map<String, ReflectiveTypeAdapterFactory.BoundField> getBoundFields(Gson context, TypeToken<?> type, Class<?> raw) {
		Map<String, ReflectiveTypeAdapterFactory.BoundField> result = new LinkedHashMap();
		if (raw.isInterface()) {
			return result;
		} else {
			for (Type declaredType = type.getType(); raw != Object.class; raw = type.getRawType()) {
				Field[] fields = raw.getDeclaredFields();

				for (Field field : fields) {
					boolean serialize = this.excludeField(field, true);
					boolean deserialize = this.excludeField(field, false);
					if (serialize || deserialize) {
						field.setAccessible(true);
						Type fieldType = Types.resolve(type.getType(), raw, field.getGenericType());
						List<String> fieldNames = this.getFieldNames(field);
						ReflectiveTypeAdapterFactory.BoundField previous = null;

						for (int i = 0; i < fieldNames.size(); i++) {
							String name = (String)fieldNames.get(i);
							if (i != 0) {
								serialize = false;
							}

							ReflectiveTypeAdapterFactory.BoundField boundField = this.createBoundField(context, field, name, TypeToken.get(fieldType), serialize, deserialize);
							ReflectiveTypeAdapterFactory.BoundField replaced = (ReflectiveTypeAdapterFactory.BoundField)result.put(name, boundField);
							if (previous == null) {
								previous = replaced;
							}
						}

						if (previous != null) {
							throw new IllegalArgumentException(declaredType + " declares multiple JSON fields named " + previous.name);
						}
					}
				}

				type = TypeToken.get(Types.resolve(type.getType(), raw, raw.getGenericSuperclass()));
			}

			return result;
		}
	}

	public static final class Adapter<T> extends TypeAdapter<T> {
		private final ObjectConstructor<T> constructor;
		private final Map<String, ReflectiveTypeAdapterFactory.BoundField> boundFields;

		Adapter(ObjectConstructor<T> constructor, Map<String, ReflectiveTypeAdapterFactory.BoundField> boundFields) {
			this.constructor = constructor;
			this.boundFields = boundFields;
		}

		@Override
		public T read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			} else {
				T instance = this.constructor.construct();

				try {
					in.beginObject();

					while (in.hasNext()) {
						String name = in.nextName();
						ReflectiveTypeAdapterFactory.BoundField field = (ReflectiveTypeAdapterFactory.BoundField)this.boundFields.get(name);
						if (field != null && field.deserialized) {
							field.read(in, instance);
						} else {
							in.skipValue();
						}
					}
				} catch (IllegalStateException var5) {
					throw new JsonSyntaxException(var5);
				} catch (IllegalAccessException var6) {
					throw new AssertionError(var6);
				}

				in.endObject();
				return instance;
			}
		}

		@Override
		public void write(JsonWriter out, T value) throws IOException {
			if (value == null) {
				out.nullValue();
			} else {
				out.beginObject();

				try {
					for (ReflectiveTypeAdapterFactory.BoundField boundField : this.boundFields.values()) {
						if (boundField.writeField(value)) {
							out.name(boundField.name);
							boundField.write(out, value);
						}
					}
				} catch (IllegalAccessException var5) {
					throw new AssertionError(var5);
				}

				out.endObject();
			}
		}
	}

	abstract static class BoundField {
		final String name;
		final boolean serialized;
		final boolean deserialized;

		protected BoundField(String name, boolean serialized, boolean deserialized) {
			this.name = name;
			this.serialized = serialized;
			this.deserialized = deserialized;
		}

		abstract boolean writeField(Object object) throws IOException, IllegalAccessException;

		abstract void write(JsonWriter jsonWriter, Object object) throws IOException, IllegalAccessException;

		abstract void read(JsonReader jsonReader, Object object) throws IOException, IllegalAccessException;
	}
}
