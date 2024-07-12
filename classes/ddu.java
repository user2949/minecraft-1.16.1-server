import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;

public class ddu implements ddm {
	@Nullable
	private final Long a;
	private final dbb b;

	private ddu(@Nullable Long long1, dbb dbb) {
		this.a = long1;
		this.b = dbb;
	}

	@Override
	public ddn b() {
		return ddo.p;
	}

	public boolean test(dat dat) {
		zd zd3 = dat.c();
		long long4 = zd3.R();
		if (this.a != null) {
			long4 %= this.a;
		}

		return this.b.a((int)long4);
	}

	public static class b implements dbc<ddu> {
		public void a(JsonObject jsonObject, ddu ddu, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("period", ddu.a);
			jsonObject.add("value", jsonSerializationContext.serialize(ddu.b));
		}

		public ddu a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			Long long4 = jsonObject.has("period") ? adt.m(jsonObject, "period") : null;
			dbb dbb5 = adt.a(jsonObject, "value", jsonDeserializationContext, dbb.class);
			return new ddu(long4, dbb5);
		}
	}
}
