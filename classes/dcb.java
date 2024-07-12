import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;

public class dcb extends dcg {
	private final daz a;
	private final boolean b;

	private dcb(ddm[] arr, daz daz, boolean boolean3) {
		super(arr);
		this.a = daz;
		this.b = boolean3;
	}

	@Override
	public dci b() {
		return dcj.c;
	}

	@Override
	public bki a(bki bki, dat dat) {
		Random random4 = dat.a();
		return bny.a(random4, bki, this.a.a(random4), this.b);
	}

	public static dcb.a a(daz daz) {
		return new dcb.a(daz);
	}

	public static class a extends dcg.a<dcb.a> {
		private final daz a;
		private boolean b;

		public a(daz daz) {
			this.a = daz;
		}

		protected dcb.a d() {
			return this;
		}

		public dcb.a e() {
			this.b = true;
			return this;
		}

		@Override
		public dch b() {
			return new dcb(this.g(), this.a, this.b);
		}
	}

	public static class b extends dcg.c<dcb> {
		public void a(JsonObject jsonObject, dcb dcb, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcb, jsonSerializationContext);
			jsonObject.add("levels", dba.a(dcb.a, jsonSerializationContext));
			jsonObject.addProperty("treasure", dcb.b);
		}

		public dcb b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			daz daz5 = dba.a(jsonObject.get("levels"), jsonDeserializationContext);
			boolean boolean6 = adt.a(jsonObject, "treasure", false);
			return new dcb(arr, daz5, boolean6);
		}
	}
}
