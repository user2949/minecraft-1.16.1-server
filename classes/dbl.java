import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public class dbl extends dbq {
	private final bke g;

	private dbl(bke bke, int integer2, int integer3, ddm[] arr, dch[] arr) {
		super(integer2, integer3, arr, arr);
		this.g = bke;
	}

	@Override
	public dbp a() {
		return dbm.b;
	}

	@Override
	public void a(Consumer<bki> consumer, dat dat) {
		consumer.accept(new bki(this.g));
	}

	public static dbq.a<?> a(bqa bqa) {
		return a((integer2, integer3, arr, arrx) -> new dbl(bqa.h(), integer2, integer3, arr, arrx));
	}

	public static class a extends dbq.e<dbl> {
		public void a(JsonObject jsonObject, dbl dbl, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dbl, jsonSerializationContext);
			uh uh5 = gl.am.b(dbl.g);
			if (uh5 == null) {
				throw new IllegalArgumentException("Can't serialize unknown item " + dbl.g);
			} else {
				jsonObject.addProperty("name", uh5.toString());
			}
		}

		protected dbl b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int integer3, int integer4, ddm[] arr, dch[] arr) {
			bke bke8 = adt.i(jsonObject, "name");
			return new dbl(bke8, integer3, integer4, arr, arr);
		}
	}
}
