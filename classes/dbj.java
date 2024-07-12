import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.function.Consumer;

public class dbj extends dbq {
	private dbj(int integer1, int integer2, ddm[] arr, dch[] arr) {
		super(integer1, integer2, arr, arr);
	}

	@Override
	public dbp a() {
		return dbm.a;
	}

	@Override
	public void a(Consumer<bki> consumer, dat dat) {
	}

	public static dbq.a<?> b() {
		return a(dbj::new);
	}

	public static class a extends dbq.e<dbj> {
		public dbj b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int integer3, int integer4, ddm[] arr, dch[] arr) {
			return new dbj(integer3, integer4, arr, arr);
		}
	}
}
