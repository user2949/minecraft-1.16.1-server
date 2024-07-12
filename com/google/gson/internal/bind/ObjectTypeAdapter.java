package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ObjectTypeAdapter extends TypeAdapter<Object> {
	public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
		@Override
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			return type.getRawType() == Object.class ? new ObjectTypeAdapter(gson) : null;
		}
	};
	private final Gson gson;

	ObjectTypeAdapter(Gson gson) {
		this.gson = gson;
	}

	@Override
	public Object read(JsonReader in) throws IOException {
		JsonToken token = in.peek();
		switch (token) {
			case BEGIN_ARRAY:
				List<Object> list = new ArrayList();
				in.beginArray();

				while (in.hasNext()) {
					list.add(this.read(in));
				}

				in.endArray();
				return list;
			case BEGIN_OBJECT:
				Map<String, Object> map = new LinkedTreeMap<String, Object>();
				in.beginObject();

				while (in.hasNext()) {
					map.put(in.nextName(), this.read(in));
				}

				in.endObject();
				return map;
			case STRING:
				return in.nextString();
			case NUMBER:
				return in.nextDouble();
			case BOOLEAN:
				return in.nextBoolean();
			case NULL:
				in.nextNull();
				return null;
			default:
				throw new IllegalStateException();
		}
	}

	@Override
	public void write(JsonWriter out, Object value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else {
			TypeAdapter<Object> typeAdapter = this.gson.getAdapter(value.getClass());
			if (typeAdapter instanceof ObjectTypeAdapter) {
				out.beginObject();
				out.endObject();
			} else {
				typeAdapter.write(out, value);
			}
		}
	}
}
