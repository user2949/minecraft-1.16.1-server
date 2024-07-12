import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public class dbr extends dbq {
	private final uh g;

	private dbr(uh uh, int integer2, int integer3, ddm[] arr, dch[] arr) {
		super(integer2, integer3, arr, arr);
		this.g = uh;
	}

	@Override
	public dbp a() {
		return dbm.c;
	}

	@Override
	public void a(Consumer<bki> consumer, dat dat) {
		daw daw4 = dat.a(this.g);
		daw4.a(dat, consumer);
	}

	@Override
	public void a(dbe dbe) {
		if (dbe.a(this.g)) {
			dbe.a("Table " + this.g + " is recursively called");
		} else {
			super.a(dbe);
			daw daw3 = dbe.c(this.g);
			if (daw3 == null) {
				dbe.a("Unknown loot table called " + this.g);
			} else {
				daw3.a(dbe.a("->{" + this.g + "}", this.g));
			}
		}
	}

	public static dbq.a<?> a(uh uh) {
		return a((integer2, integer3, arr, arrx) -> new dbr(uh, integer2, integer3, arr, arrx));
	}

	public static class a extends dbq.e<dbr> {
		public void a(JsonObject jsonObject, dbr dbr, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dbr, jsonSerializationContext);
			jsonObject.addProperty("name", dbr.g.toString());
		}

		protected dbr b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int integer3, int integer4, ddm[] arr, dch[] arr) {
			uh uh8 = new uh(adt.h(jsonObject, "name"));
			return new dbr(uh8, integer3, integer4, arr, arr);
		}
	}
}
