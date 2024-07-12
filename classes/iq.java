import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class iq implements Supplier<JsonElement> {
	private final Map<is<?>, is<?>.a> a = Maps.<is<?>, is<?>.a>newLinkedHashMap();

	public <T> iq a(is<T> is, T object) {
		is<?>.a a4 = (is.a)this.a.put(is, is.a(object));
		if (a4 != null) {
			throw new IllegalStateException("Replacing value of " + a4 + " with " + object);
		} else {
			return this;
		}
	}

	public static iq a() {
		return new iq();
	}

	public static iq a(iq iq1, iq iq2) {
		iq iq3 = new iq();
		iq3.a.putAll(iq1.a);
		iq3.a.putAll(iq2.a);
		return iq3;
	}

	public JsonElement get() {
		JsonObject jsonObject2 = new JsonObject();
		this.a.values().forEach(a -> a.a(jsonObject2));
		return jsonObject2;
	}

	public static JsonElement a(List<iq> list) {
		if (list.size() == 1) {
			return ((iq)list.get(0)).b();
		} else {
			JsonArray jsonArray2 = new JsonArray();
			list.forEach(iq -> jsonArray2.add(iq.b()));
			return jsonArray2;
		}
	}
}
