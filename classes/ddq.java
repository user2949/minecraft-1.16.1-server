import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class ddq implements ddm {
	private static final ddq a = new ddq();

	private ddq() {
	}

	@Override
	public ddn b() {
		return ddo.f;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.b);
	}

	public boolean test(dat dat) {
		return dat.a(dda.b);
	}

	public static ddm.a c() {
		return () -> a;
	}

	public static class a implements dbc<ddq> {
		public void a(JsonObject jsonObject, ddq ddq, JsonSerializationContext jsonSerializationContext) {
		}

		public ddq a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			return ddq.a;
		}
	}
}
