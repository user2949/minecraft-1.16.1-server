import com.google.gson.JsonObject;

public class ch extends ci<ch.a> {
	private static final uh a = new uh("shot_crossbow");

	@Override
	public uh a() {
		return a;
	}

	public ch.a b(JsonObject jsonObject, be.b b, av av) {
		bo bo5 = bo.a(jsonObject.get("item"));
		return new ch.a(b, bo5);
	}

	public void a(ze ze, bki bki) {
		this.a(ze, a -> a.a(bki));
	}

	public static class a extends aj {
		private final bo a;

		public a(be.b b, bo bo) {
			super(ch.a, b);
			this.a = bo;
		}

		public static ch.a a(bqa bqa) {
			return new ch.a(be.b.a, bo.a.a().a(bqa).b());
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
