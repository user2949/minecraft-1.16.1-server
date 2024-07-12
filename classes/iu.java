import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.Supplier;

public class iu implements Supplier<JsonElement> {
	private final uh a;

	public iu(uh uh) {
		this.a = uh;
	}

	public JsonElement get() {
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("parent", this.a.toString());
		return jsonObject2;
	}
}
