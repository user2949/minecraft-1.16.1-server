import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.Function;

public class is<T> {
	private final String a;
	private final Function<T, JsonElement> b;

	public is(String string, Function<T, JsonElement> function) {
		this.a = string;
		this.b = function;
	}

	public is<T>.a a(T object) {
		return new is.a(object);
	}

	public String toString() {
		return this.a;
	}

	public class a {
		private final T b;

		public a(T object) {
			this.b = object;
		}

		public void a(JsonObject jsonObject) {
			jsonObject.add(is.this.a, (JsonElement)is.this.b.apply(this.b));
		}

		public String toString() {
			return is.this.a + "=" + this.b;
		}
	}
}
