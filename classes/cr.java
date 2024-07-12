import com.google.gson.JsonObject;

public class cr extends ci<cr.a> {
	private static final uh a = new uh("used_totem");

	@Override
	public uh a() {
		return a;
	}

	public cr.a b(JsonObject jsonObject, be.b b, av av) {
		bo bo5 = bo.a(jsonObject.get("item"));
		return new cr.a(b, bo5);
	}

	public void a(ze ze, bki bki) {
		this.a(ze, a -> a.a(bki));
	}

	public static class a extends aj {
		private final bo a;

		public a(be.b b, bo bo) {
			super(cr.a, b);
			this.a = bo;
		}

		public static cr.a a(bqa bqa) {
			return new cr.a(be.b.a, bo.a.a().a(bqa).b());
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
