import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;

public class ddc implements ddm {
	private final ddm[] a;
	private final Predicate<dat> b;

	private ddc(ddm[] arr) {
		this.a = arr;
		this.b = ddo.b(arr);
	}

	@Override
	public ddn b() {
		return ddo.b;
	}

	public final boolean test(dat dat) {
		return this.b.test(dat);
	}

	@Override
	public void a(dbe dbe) {
		ddm.super.a(dbe);

		for (int integer3 = 0; integer3 < this.a.length; integer3++) {
			this.a[integer3].a(dbe.b(".term[" + integer3 + "]"));
		}
	}

	public static ddc.a a(ddm.a... arr) {
		return new ddc.a(arr);
	}

	public static class a implements ddm.a {
		private final List<ddm> a = Lists.<ddm>newArrayList();

		public a(ddm.a... arr) {
			for (ddm.a a6 : arr) {
				this.a.add(a6.build());
			}
		}

		@Override
		public ddc.a a(ddm.a a) {
			this.a.add(a.build());
			return this;
		}

		@Override
		public ddm build() {
			return new ddc((ddm[])this.a.toArray(new ddm[0]));
		}
	}

	public static class b implements dbc<ddc> {
		public void a(JsonObject jsonObject, ddc ddc, JsonSerializationContext jsonSerializationContext) {
			jsonObject.add("terms", jsonSerializationContext.serialize(ddc.a));
		}

		public ddc a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			ddm[] arr4 = adt.a(jsonObject, "terms", jsonDeserializationContext, ddm[].class);
			return new ddc(arr4);
		}
	}
}
