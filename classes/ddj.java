import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class ddj implements ddm {
	private final ddm a;

	private ddj(ddm ddm) {
		this.a = ddm;
	}

	@Override
	public ddn b() {
		return ddo.a;
	}

	public final boolean test(dat dat) {
		return !this.a.test(dat);
	}

	@Override
	public Set<dcx<?>> a() {
		return this.a.a();
	}

	@Override
	public void a(dbe dbe) {
		ddm.super.a(dbe);
		this.a.a(dbe);
	}

	public static ddm.a a(ddm.a a) {
		ddj ddj2 = new ddj(a.build());
		return () -> ddj2;
	}

	public static class a implements dbc<ddj> {
		public void a(JsonObject jsonObject, ddj ddj, JsonSerializationContext jsonSerializationContext) {
			jsonObject.add("term", jsonSerializationContext.serialize(ddj.a));
		}

		public ddj a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			ddm ddm4 = adt.a(jsonObject, "term", jsonDeserializationContext, ddm.class);
			return new ddj(ddm4);
		}
	}
}
