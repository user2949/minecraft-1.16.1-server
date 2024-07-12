import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class ddk implements ddm {
	private final bu a;
	private final fu b;

	private ddk(bu bu, fu fu) {
		this.a = bu;
		this.b = fu;
	}

	@Override
	public ddn b() {
		return ddo.m;
	}

	public boolean test(dat dat) {
		fu fu3 = dat.c(dda.f);
		return fu3 != null && this.a.a(dat.c(), (float)(fu3.u() + this.b.u()), (float)(fu3.v() + this.b.v()), (float)(fu3.w() + this.b.w()));
	}

	public static ddm.a a(bu.a a) {
		return () -> new ddk(a.b(), fu.b);
	}

	public static ddm.a a(bu.a a, fu fu) {
		return () -> new ddk(a.b(), fu);
	}

	public static class a implements dbc<ddk> {
		public void a(JsonObject jsonObject, ddk ddk, JsonSerializationContext jsonSerializationContext) {
			jsonObject.add("predicate", ddk.a.a());
			if (ddk.b.u() != 0) {
				jsonObject.addProperty("offsetX", ddk.b.u());
			}

			if (ddk.b.v() != 0) {
				jsonObject.addProperty("offsetY", ddk.b.v());
			}

			if (ddk.b.w() != 0) {
				jsonObject.addProperty("offsetZ", ddk.b.w());
			}
		}

		public ddk a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			bu bu4 = bu.a(jsonObject.get("predicate"));
			int integer5 = adt.a(jsonObject, "offsetX", 0);
			int integer6 = adt.a(jsonObject, "offsetY", 0);
			int integer7 = adt.a(jsonObject, "offsetZ", 0);
			return new ddk(bu4, new fu(integer5, integer6, integer7));
		}
	}
}
