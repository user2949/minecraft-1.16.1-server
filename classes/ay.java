import com.google.gson.JsonObject;

public class ay extends ci<ay.a> {
	private static final uh a = new uh("enchanted_item");

	@Override
	public uh a() {
		return a;
	}

	public ay.a b(JsonObject jsonObject, be.b b, av av) {
		bo bo5 = bo.a(jsonObject.get("item"));
		bx.d d6 = bx.d.a(jsonObject.get("levels"));
		return new ay.a(b, bo5, d6);
	}

	public void a(ze ze, bki bki, int integer) {
		this.a(ze, a -> a.a(bki, integer));
	}

	public static class a extends aj {
		private final bo a;
		private final bx.d b;

		public a(be.b b, bo bo, bx.d d) {
			super(ay.a, b);
			this.a = bo;
			this.b = d;
		}

		public static ay.a c() {
			return new ay.a(be.b.a, bo.a, bx.d.e);
		}

		public boolean a(bki bki, int integer) {
			return !this.a.a(bki) ? false : this.b.d(integer);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("item", this.a.a());
			jsonObject3.add("levels", this.b.d());
			return jsonObject3;
		}
	}
}
