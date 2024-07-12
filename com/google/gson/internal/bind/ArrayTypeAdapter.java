package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal..Gson.Types;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class ArrayTypeAdapter<E> extends TypeAdapter<Object> {
	public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
		@Override
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			Type type = typeToken.getType();
			if (type instanceof GenericArrayType || type instanceof Class && ((Class)type).isArray()) {
				Type componentType = Types.getArrayComponentType(type);
				TypeAdapter<?> componentTypeAdapter = gson.getAdapter(TypeToken.get(componentType));
				return new ArrayTypeAdapter(gson, (TypeAdapter<E>)componentTypeAdapter, (Class<E>)Types.getRawType(componentType));
			} else {
				return null;
			}
		}
	};
	private final Class<E> componentType;
	private final TypeAdapter<E> componentTypeAdapter;

	public ArrayTypeAdapter(Gson context, TypeAdapter<E> componentTypeAdapter, Class<E> componentType) {
		this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(context, componentTypeAdapter, componentType);
		this.componentType = componentType;
	}

	@Override
	public Object read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		} else {
			List<E> list = new ArrayList();
			in.beginArray();

			while (in.hasNext()) {
				E instance = this.componentTypeAdapter.read(in);
				list.add(instance);
			}

			in.endArray();
			Object array = Array.newInstance(this.componentType, list.size());

			for (int i = 0; i < list.size(); i++) {
				Array.set(array, i, list.get(i));
			}

			return array;
		}
	}

	@Override
	public void write(JsonWriter out, Object array) throws IOException {
		if (array == null) {
			out.nullValue();
		} else {
			out.beginArray();
			int i = 0;

			for (int length = Array.getLength(array); i < length; i++) {
				E value = (E)Array.get(array, i);
				this.componentTypeAdapter.write(out, value);
			}

			out.endArray();
		}
	}
}
