import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public class dbi extends dbq {
	private final uh g;

	private dbi(uh uh, int integer2, int integer3, ddm[] arr, dch[] arr) {
		super(integer2, integer3, arr, arr);
		this.g = uh;
	}

	@Override
	public dbp a() {
		return dbm.d;
	}

	@Override
	public void a(Consumer<bki> consumer, dat dat) {
		dat.a(this.g, consumer);
	}

	public static dbq.a<?> a(uh uh) {
		return a((integer2, integer3, arr, arrx) -> new dbi(uh, integer2, integer3, arr, arrx));
	}

	public static class a extends dbq.e<dbi> {
		public void a(JsonObject jsonObject, dbi dbi, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dbi, jsonSerializationContext);
			jsonObject.addProperty("name", dbi.g.toString());
		}

		protected dbi b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int integer3, int integer4, ddm[] arr, dch[] arr) {
			uh uh8 = new uh(adt.h(jsonObject, "name"));
			return new dbi(uh8, integer3, integer4, arr, arr);
		}
	}
}
