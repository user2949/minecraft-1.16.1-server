import com.google.gson.JsonObject;

public class bg extends ci<bg.a> {
	private static final uh a = new uh("filled_bucket");

	@Override
	public uh a() {
		return a;
	}

	public bg.a b(JsonObject jsonObject, be.b b, av av) {
		bo bo5 = bo.a(jsonObject.get("item"));
		return new bg.a(b, bo5);
	}

	public void a(ze ze, bki bki) {
		this.a(ze, a -> a.a(bki));
	}

	public static class a extends aj {
		private final bo a;

		public a(be.b b, bo bo) {
			super(bg.a, b);
			this.a = bo;
		}

		public static bg.a a(bo bo) {
			return new bg.a(be.b.a, bo);
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
