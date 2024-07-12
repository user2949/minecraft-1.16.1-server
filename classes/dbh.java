import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public abstract class dbh extends dbo {
	protected final dbo[] c;
	private final dbg e;

	protected dbh(dbo[] arr, ddm[] arr) {
		super(arr);
		this.c = arr;
		this.e = this.a(arr);
	}

	@Override
	public void a(dbe dbe) {
		super.a(dbe);
		if (this.c.length == 0) {
			dbe.a("Empty children list");
		}

		for (int integer3 = 0; integer3 < this.c.length; integer3++) {
			this.c[integer3].a(dbe.b(".entry[" + integer3 + "]"));
		}
	}

	protected abstract dbg a(dbg[] arr);

	@Override
	public final boolean expand(dat dat, Consumer<dbn> consumer) {
		return !this.a(dat) ? false : this.e.expand(dat, consumer);
	}

	public static <T extends dbh> dbo.b<T> a(dbh.a<T> a) {
		return new dbo.b<T>() {
			public void a(JsonObject jsonObject, T dbh, JsonSerializationContext jsonSerializationContext) {
				jsonObject.add("children", jsonSerializationContext.serialize(dbh.c));
			}

			public final T b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
				dbo[] arr5 = adt.a(jsonObject, "children", jsonDeserializationContext, dbo[].class);
				return a.create(arr5, arr);
			}
		};
	}

	@FunctionalInterface
	public interface a<T extends dbh> {
		T create(dbo[] arr, ddm[] arr);
	}
}
