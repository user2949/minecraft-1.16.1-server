import com.google.gson.JsonObject;

public class cd extends ci<cd.a> {
	private static final uh a = new uh("player_interacted_with_entity");

	@Override
	public uh a() {
		return a;
	}

	protected cd.a b(JsonObject jsonObject, be.b b, av av) {
		bo bo5 = bo.a(jsonObject.get("item"));
		be.b b6 = be.b.a(jsonObject, "entity", av);
		return new cd.a(b, bo5, b6);
	}

	public void a(ze ze, bki bki, aom aom) {
		dat dat5 = be.b(ze, aom);
		this.a(ze, a -> a.a(bki, dat5));
	}

	public static class a extends aj {
		private final bo a;
		private final be.b b;

		public a(be.b b1, bo bo, be.b b3) {
			super(cd.a, b1);
			this.a = bo;
			this.b = b3;
		}

		public static cd.a a(be.b b1, bo.a a, be.b b3) {
			return new cd.a(b1, a.b(), b3);
		}

		public boolean a(bki bki, dat dat) {
			return !this.a.a(bki) ? false : this.b.a(dat);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("item", this.a.a());
			jsonObject3.add("entity", this.b.a(cg));
			return jsonObject3;
		}
	}
}
