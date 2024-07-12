import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class aeb implements TypeAdapterFactory {
	@Nullable
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
		Class<T> class4 = (Class<T>)typeToken.getRawType();
		if (!class4.isEnum()) {
			return null;
		} else {
			final Map<String, T> map5 = Maps.<String, T>newHashMap();

			for (T object9 : class4.getEnumConstants()) {
				map5.put(this.a(object9), object9);
			}

			return new TypeAdapter<T>() {
				@Override
				public void write(JsonWriter jsonWriter, T object) throws IOException {
					if (object == null) {
						jsonWriter.nullValue();
					} else {
						jsonWriter.value(aeb.this.a(object));
					}
				}

				@Nullable
				@Override
				public T read(JsonReader jsonReader) throws IOException {
					if (jsonReader.peek() == JsonToken.NULL) {
						jsonReader.nextNull();
						return null;
					} else {
						return (T)map5.get(jsonReader.nextString());
					}
				}
			};
		}
	}

	private String a(Object object) {
		return object instanceof Enum ? ((Enum)object).name().toLowerCase(Locale.ROOT) : object.toString().toLowerCase(Locale.ROOT);
	}
}
