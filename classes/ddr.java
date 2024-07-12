import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class ddr implements ddm {
	private final float a;

	private ddr(float float1) {
		this.a = float1;
	}

	@Override
	public ddn b() {
		return ddo.c;
	}

	public boolean test(dat dat) {
		return dat.a().nextFloat() < this.a;
	}

	public static ddm.a a(float float1) {
		return () -> new ddr(float1);
	}

	public static class a implements dbc<ddr> {
		public void a(JsonObject jsonObject, ddr ddr, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("chance", ddr.a);
		}

		public ddr a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			return new ddr(adt.l(jsonObject, "chance"));
		}
	}
}
