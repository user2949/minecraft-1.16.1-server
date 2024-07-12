import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;

public abstract class dbo implements dbg {
	protected final ddm[] d;
	private final Predicate<dat> c;

	protected dbo(ddm[] arr) {
		this.d = arr;
		this.c = ddo.a(arr);
	}

	public void a(dbe dbe) {
		for (int integer3 = 0; integer3 < this.d.length; integer3++) {
			this.d[integer3].a(dbe.b(".condition[" + integer3 + "]"));
		}
	}

	protected final boolean a(dat dat) {
		return this.c.test(dat);
	}

	public abstract dbp a();

	public abstract static class a<T extends dbo.a<T>> implements ddf<T> {
		private final List<ddm> a = Lists.<ddm>newArrayList();

		protected abstract T d();

		public T b(ddm.a a) {
			this.a.add(a.build());
			return this.d();
		}

		public final T c() {
			return this.d();
		}

		protected ddm[] f() {
			return (ddm[])this.a.toArray(new ddm[0]);
		}

		public dbf.a a(dbo.a<?> a) {
			return new dbf.a(this, a);
		}

		public abstract dbo b();
	}

	public abstract static class b<T extends dbo> implements dbc<T> {
		public final void a(JsonObject jsonObject, T dbo, JsonSerializationContext jsonSerializationContext) {
			if (!ArrayUtils.isEmpty((Object[])dbo.d)) {
				jsonObject.add("conditions", jsonSerializationContext.serialize(dbo.d));
			}

			this.a(jsonObject, dbo, jsonSerializationContext);
		}

		public final T a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			ddm[] arr4 = adt.a(jsonObject, "conditions", new ddm[0], jsonDeserializationContext, ddm[].class);
			return this.b(jsonObject, jsonDeserializationContext, arr4);
		}

		public abstract void a(JsonObject jsonObject, T dbo, JsonSerializationContext jsonSerializationContext);

		public abstract T b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr);
	}
}
