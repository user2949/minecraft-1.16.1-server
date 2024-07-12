import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dck extends dcg {
	private final dbb a;
	private final int b;

	private dck(ddm[] arr, dbb dbb, int integer) {
		super(arr);
		this.a = dbb;
		this.b = integer;
	}

	@Override
	public dci b() {
		return dcj.g;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.d);
	}

	private boolean c() {
		return this.b > 0;
	}

	@Override
	public bki a(bki bki, dat dat) {
		aom aom4 = dat.c(dda.d);
		if (aom4 instanceof aoy) {
			int integer5 = bny.g((aoy)aom4);
			if (integer5 == 0) {
				return bki;
			}

			float float6 = (float)integer5 * this.a.b(dat.a());
			bki.f(Math.round(float6));
			if (this.c() && bki.E() > this.b) {
				bki.e(this.b);
			}
		}

		return bki;
	}

	public static dck.a a(dbb dbb) {
		return new dck.a(dbb);
	}

	public static class a extends dcg.a<dck.a> {
		private final dbb a;
		private int b = 0;

		public a(dbb dbb) {
			this.a = dbb;
		}

		protected dck.a d() {
			return this;
		}

		public dck.a a(int integer) {
			this.b = integer;
			return this;
		}

		@Override
		public dch b() {
			return new dck(this.g(), this.a, this.b);
		}
	}

	public static class b extends dcg.c<dck> {
		public void a(JsonObject jsonObject, dck dck, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dck, jsonSerializationContext);
			jsonObject.add("count", jsonSerializationContext.serialize(dck.a));
			if (dck.c()) {
				jsonObject.add("limit", jsonSerializationContext.serialize(dck.b));
			}
		}

		public dck b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			int integer5 = adt.a(jsonObject, "limit", 0);
			return new dck(arr, adt.a(jsonObject, "count", jsonDeserializationContext, dbb.class), integer5);
		}
	}
}
