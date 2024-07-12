import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public interface dbc<T> {
	void a(JsonObject jsonObject, T object, JsonSerializationContext jsonSerializationContext);

	T a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext);
}
