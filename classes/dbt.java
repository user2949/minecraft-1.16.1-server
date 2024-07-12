import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public class dbt extends dbq {
	private final adf<bke> g;
	private final boolean h;

	private dbt(adf<bke> adf, boolean boolean2, int integer3, int integer4, ddm[] arr, dch[] arr) {
		super(integer3, integer4, arr, arr);
		this.g = adf;
		this.h = boolean2;
	}

	@Override
	public dbp a() {
		return dbm.e;
	}

	@Override
	public void a(Consumer<bki> consumer, dat dat) {
		this.g.b().forEach(bke -> consumer.accept(new bki(bke)));
	}

	private boolean a(dat dat, Consumer<dbn> consumer) {
		if (!this.a(dat)) {
			return false;
		} else {
			for (final bke bke5 : this.g.b()) {
				consumer.accept(new dbq.c() {
					@Override
					public void a(Consumer<bki> consumer, dat dat) {
						consumer.accept(new bki(bke5));
					}
				});
			}

			return true;
		}
	}

	@Override
	public boolean expand(dat dat, Consumer<dbn> consumer) {
		return this.h ? this.a(dat, consumer) : super.expand(dat, consumer);
	}

	public static dbq.a<?> b(adf<bke> adf) {
		return a((integer2, integer3, arr, arrx) -> new dbt(adf, true, integer2, integer3, arr, arrx));
	}

	public static class a extends dbq.e<dbt> {
		public void a(JsonObject jsonObject, dbt dbt, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dbt, jsonSerializationContext);
			jsonObject.addProperty("name", adb.e().b().b(dbt.g).toString());
			jsonObject.addProperty("expand", dbt.h);
		}

		protected dbt b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int integer3, int integer4, ddm[] arr, dch[] arr) {
			uh uh8 = new uh(adt.h(jsonObject, "name"));
			adf<bke> adf9 = adb.e().b().a(uh8);
			if (adf9 == null) {
				throw new JsonParseException("Can't find tag: " + uh8);
			} else {
				boolean boolean10 = adt.j(jsonObject, "expand");
				return new dbt(adf9, boolean10, integer3, integer4, arr, arr);
			}
		}
	}
}
