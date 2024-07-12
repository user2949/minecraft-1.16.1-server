import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dds implements ddm {
	private final float a;
	private final float b;

	private dds(float float1, float float2) {
		this.a = float1;
		this.b = float2;
	}

	@Override
	public ddn b() {
		return ddo.d;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.d);
	}

	public boolean test(dat dat) {
		aom aom3 = dat.c(dda.d);
		int integer4 = 0;
		if (aom3 instanceof aoy) {
			integer4 = bny.g((aoy)aom3);
		}

		return dat.a().nextFloat() < this.a + (float)integer4 * this.b;
	}

	public static ddm.a a(float float1, float float2) {
		return () -> new dds(float1, float2);
	}

	public static class a implements dbc<dds> {
		public void a(JsonObject jsonObject, dds dds, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("chance", dds.a);
			jsonObject.addProperty("looting_multiplier", dds.b);
		}

		public dds a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			return new dds(adt.l(jsonObject, "chance"), adt.l(jsonObject, "looting_multiplier"));
		}
	}
}
