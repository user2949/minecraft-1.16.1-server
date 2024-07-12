import com.google.gson.JsonObject;

public class bn extends ci<bn.a> {
	private static final uh a = new uh("thrown_item_picked_up_by_entity");

	@Override
	public uh a() {
		return a;
	}

	protected bn.a b(JsonObject jsonObject, be.b b, av av) {
		bo bo5 = bo.a(jsonObject.get("item"));
		be.b b6 = be.b.a(jsonObject, "entity", av);
		return new bn.a(b, bo5, b6);
	}

	public void a(ze ze, bki bki, aom aom) {
		dat dat5 = be.b(ze, aom);
		this.a(ze, a -> a.a(ze, bki, dat5));
	}

	public static class a extends aj {
		private final bo a;
		private final be.b b;

		public a(be.b b1, bo bo, be.b b3) {
			super(bn.a, b1);
			this.a = bo;
			this.b = b3;
		}

		public static bn.a a(be.b b1, bo.a a, be.b b3) {
			return new bn.a(b1, a.b(), b3);
		}

		public boolean a(ze ze, bki bki, dat dat) {
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
