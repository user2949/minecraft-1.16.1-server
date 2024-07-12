import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dcp extends dcg {
	private static final Logger a = LogManager.getLogger();
	private final dbb b;

	private dcp(ddm[] arr, dbb dbb) {
		super(arr);
		this.b = dbb;
	}

	@Override
	public dci b() {
		return dcj.h;
	}

	@Override
	public bki a(bki bki, dat dat) {
		if (bki.e()) {
			float float4 = 1.0F - this.b.b(dat.a());
			bki.b(aec.d(float4 * (float)bki.h()));
		} else {
			a.warn("Couldn't set damage of loot item {}", bki);
		}

		return bki;
	}

	public static dcg.a<?> a(dbb dbb) {
		return a(arr -> new dcp(arr, dbb));
	}

	public static class a extends dcg.c<dcp> {
		public void a(JsonObject jsonObject, dcp dcp, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcp, jsonSerializationContext);
			jsonObject.add("damage", jsonSerializationContext.serialize(dcp.b));
		}

		public dcp b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			return new dcp(arr, adt.a(jsonObject, "damage", jsonDeserializationContext, dbb.class));
		}
	}
}
