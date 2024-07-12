import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class ddt implements ddm {
	private final bo a;

	public ddt(bo bo) {
		this.a = bo;
	}

	@Override
	public ddn b() {
		return ddo.i;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.j);
	}

	public boolean test(dat dat) {
		bki bki3 = dat.c(dda.j);
		return bki3 != null && this.a.a(bki3);
	}

	public static ddm.a a(bo.a a) {
		return () -> new ddt(a.b());
	}

	public static class a implements dbc<ddt> {
		public void a(JsonObject jsonObject, ddt ddt, JsonSerializationContext jsonSerializationContext) {
			jsonObject.add("predicate", ddt.a.a());
		}

		public ddt a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			bo bo4 = bo.a(jsonObject.get("predicate"));
			return new ddt(bo4);
		}
	}
}
