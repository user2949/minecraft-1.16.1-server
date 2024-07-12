import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class ddp implements ddm {
	private final be a;
	private final dat.c b;

	private ddp(be be, dat.c c) {
		this.a = be;
		this.b = c;
	}

	@Override
	public ddn b() {
		return ddo.e;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.f, this.b.a());
	}

	public boolean test(dat dat) {
		aom aom3 = dat.c(this.b.a());
		dem dem4 = dat.c(dda.g);
		return this.a.a(dat.c(), dem4, aom3);
	}

	public static ddm.a a(dat.c c) {
		return a(c, be.a.a());
	}

	public static ddm.a a(dat.c c, be.a a) {
		return () -> new ddp(a.b(), c);
	}

	public static ddm.a a(dat.c c, be be) {
		return () -> new ddp(be, c);
	}

	public static class a implements dbc<ddp> {
		public void a(JsonObject jsonObject, ddp ddp, JsonSerializationContext jsonSerializationContext) {
			jsonObject.add("predicate", ddp.a.a());
			jsonObject.add("entity", jsonSerializationContext.serialize(ddp.b));
		}

		public ddp a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			be be4 = be.a(jsonObject.get("predicate"));
			return new ddp(be4, adt.a(jsonObject, "entity", jsonDeserializationContext, dat.c.class));
		}
	}
}
