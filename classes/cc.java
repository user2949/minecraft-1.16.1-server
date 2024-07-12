import com.google.gson.JsonObject;

public class cc extends ci<cc.a> {
	private static final uh a = new uh("player_hurt_entity");

	@Override
	public uh a() {
		return a;
	}

	public cc.a b(JsonObject jsonObject, be.b b, av av) {
		at at5 = at.a(jsonObject.get("damage"));
		be.b b6 = be.b.a(jsonObject, "entity", av);
		return new cc.a(b, at5, b6);
	}

	public void a(ze ze, aom aom, anw anw, float float4, float float5, boolean boolean6) {
		dat dat8 = be.b(ze, aom);
		this.a(ze, a -> a.a(ze, dat8, anw, float4, float5, boolean6));
	}

	public static class a extends aj {
		private final at a;
		private final be.b b;

		public a(be.b b1, at at, be.b b3) {
			super(cc.a, b1);
			this.a = at;
			this.b = b3;
		}

		public static cc.a a(at.a a) {
			return new cc.a(be.b.a, a.b(), be.b.a);
		}

		public boolean a(ze ze, dat dat, anw anw, float float4, float float5, boolean boolean6) {
			return !this.a.a(ze, anw, float4, float5, boolean6) ? false : this.b.a(dat);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("damage", this.a.a());
			jsonObject3.add("entity", this.b.a(cg));
			return jsonObject3;
		}
	}
}
