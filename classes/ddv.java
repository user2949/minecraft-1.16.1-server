import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;

public class ddv implements ddm {
	@Nullable
	private final Boolean a;
	@Nullable
	private final Boolean b;

	private ddv(@Nullable Boolean boolean1, @Nullable Boolean boolean2) {
		this.a = boolean1;
		this.b = boolean2;
	}

	@Override
	public ddn b() {
		return ddo.n;
	}

	public boolean test(dat dat) {
		zd zd3 = dat.c();
		return this.a != null && this.a != zd3.U() ? false : this.b == null || this.b == zd3.T();
	}

	public static class b implements dbc<ddv> {
		public void a(JsonObject jsonObject, ddv ddv, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("raining", ddv.a);
			jsonObject.addProperty("thundering", ddv.b);
		}

		public ddv a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			Boolean boolean4 = jsonObject.has("raining") ? adt.j(jsonObject, "raining") : null;
			Boolean boolean5 = jsonObject.has("thundering") ? adt.j(jsonObject, "thundering") : null;
			return new ddv(boolean4, boolean5);
		}
	}
}
