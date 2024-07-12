import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class dco extends dcg {
	private final daz a;

	private dco(ddm[] arr, daz daz) {
		super(arr);
		this.a = daz;
	}

	@Override
	public dci b() {
		return dcj.b;
	}

	@Override
	public bki a(bki bki, dat dat) {
		bki.e(this.a.a(dat.a()));
		return bki;
	}

	public static dcg.a<?> a(daz daz) {
		return a(arr -> new dco(arr, daz));
	}

	public static class a extends dcg.c<dco> {
		public void a(JsonObject jsonObject, dco dco, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dco, jsonSerializationContext);
			jsonObject.add("count", dba.a(dco.a, jsonSerializationContext));
		}

		public dco b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			daz daz5 = dba.a(jsonObject.get("count"), jsonDeserializationContext);
			return new dco(arr, daz5);
		}
	}
}
