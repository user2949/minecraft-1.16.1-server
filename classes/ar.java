import com.google.gson.JsonObject;

public class ar extends ci<ar.a> {
	private static final uh a = new uh("consume_item");

	@Override
	public uh a() {
		return a;
	}

	public ar.a b(JsonObject jsonObject, be.b b, av av) {
		return new ar.a(b, bo.a(jsonObject.get("item")));
	}

	public void a(ze ze, bki bki) {
		this.a(ze, a -> a.a(bki));
	}

	public static class a extends aj {
		private final bo a;

		public a(be.b b, bo bo) {
			super(ar.a, b);
			this.a = bo;
		}

		public static ar.a c() {
			return new ar.a(be.b.a, bo.a);
		}

		public static ar.a a(bqa bqa) {
			return new ar.a(be.b.a, new bo(null, bqa.h(), bx.d.e, bx.d.e, az.b, az.b, null, bz.a));
		}

		public boolean a(bki bki) {
			return this.a.a(bki);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("item", this.a.a());
			return jsonObject3;
		}
	}
}
