import com.google.gson.JsonObject;

public class bd extends ci<bd.a> {
	private static final uh a = new uh("entity_hurt_player");

	@Override
	public uh a() {
		return a;
	}

	public bd.a b(JsonObject jsonObject, be.b b, av av) {
		at at5 = at.a(jsonObject.get("damage"));
		return new bd.a(b, at5);
	}

	public void a(ze ze, anw anw, float float3, float float4, boolean boolean5) {
		this.a(ze, a -> a.a(ze, anw, float3, float4, boolean5));
	}

	public static class a extends aj {
		private final at a;

		public a(be.b b, at at) {
			super(bd.a, b);
			this.a = at;
		}

		public static bd.a a(at.a a) {
			return new bd.a(be.b.a, a.b());
		}

		public boolean a(ze ze, anw anw, float float3, float float4, boolean boolean5) {
			return this.a.a(ze, anw, float3, float4, boolean5);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("damage", this.a.a());
			return jsonObject3;
		}
	}
}
