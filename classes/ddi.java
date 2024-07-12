import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;

public class ddi implements ddm {
	private static final ddi a = new ddi();

	private ddi() {
	}

	@Override
	public ddn b() {
		return ddo.k;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.k);
	}

	public boolean test(dat dat) {
		Float float3 = dat.c(dda.k);
		if (float3 != null) {
			Random random4 = dat.a();
			float float5 = 1.0F / float3;
			return random4.nextFloat() <= float5;
		} else {
			return true;
		}
	}

	public static ddm.a c() {
		return () -> a;
	}

	public static class a implements dbc<ddi> {
		public void a(JsonObject jsonObject, ddi ddi, JsonSerializationContext jsonSerializationContext) {
		}

		public ddi a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			return ddi.a;
		}
	}
}
