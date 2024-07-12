import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class dcf extends dcg {
	private final das a;

	private dcf(ddm[] arr, das das) {
		super(arr);
		this.a = das;
	}

	@Override
	public dci b() {
		return dcj.o;
	}

	@Override
	public bki a(bki bki, dat dat) {
		int integer4 = this.a.applyAsInt(bki.E());
		bki.e(integer4);
		return bki;
	}

	public static dcg.a<?> a(das das) {
		return a(arr -> new dcf(arr, das));
	}

	public static class a extends dcg.c<dcf> {
		public void a(JsonObject jsonObject, dcf dcf, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcf, jsonSerializationContext);
			jsonObject.add("limit", jsonSerializationContext.serialize(dcf.a));
		}

		public dcf b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			das das5 = adt.a(jsonObject, "limit", jsonDeserializationContext, das.class);
			return new dcf(arr, das5);
		}
	}
}
