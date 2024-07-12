import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;

public abstract class dcg implements dch {
	protected final ddm[] c;
	private final Predicate<dat> a;

	protected dcg(ddm[] arr) {
		this.c = arr;
		this.a = ddo.a(arr);
	}

	public final bki apply(bki bki, dat dat) {
		return this.a.test(dat) ? this.a(bki, dat) : bki;
	}

	protected abstract bki a(bki bki, dat dat);

	@Override
	public void a(dbe dbe) {
		dch.super.a(dbe);

		for (int integer3 = 0; integer3 < this.c.length; integer3++) {
			this.c[integer3].a(dbe.b(".conditions[" + integer3 + "]"));
		}
	}

	protected static dcg.a<?> a(Function<ddm[], dch> function) {
		return new dcg.b(function);
	}

	public abstract static class a<T extends dcg.a<T>> implements dch.a, ddf<T> {
		private final List<ddm> a = Lists.<ddm>newArrayList();

		public T b(ddm.a a) {
			this.a.add(a.build());
			return this.d();
		}

		public final T c() {
			return this.d();
		}

		protected abstract T d();

		protected ddm[] g() {
			return (ddm[])this.a.toArray(new ddm[0]);
		}
	}

	static final class b extends dcg.a<dcg.b> {
		private final Function<ddm[], dch> a;

		public b(Function<ddm[], dch> function) {
			this.a = function;
		}

		protected dcg.b d() {
			return this;
		}

		@Override
		public dch b() {
			return (dch)this.a.apply(this.g());
		}
	}

	public abstract static class c<T extends dcg> implements dbc<T> {
		public void a(JsonObject jsonObject, T dcg, JsonSerializationContext jsonSerializationContext) {
			if (!ArrayUtils.isEmpty((Object[])dcg.c)) {
				jsonObject.add("conditions", jsonSerializationContext.serialize(dcg.c));
			}
		}

		public final T a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			ddm[] arr4 = adt.a(jsonObject, "conditions", new ddm[0], jsonDeserializationContext, ddm[].class);
			return this.b(jsonObject, jsonDeserializationContext, arr4);
		}

		public abstract T b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr);
	}
}
