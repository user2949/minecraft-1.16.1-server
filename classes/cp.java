import com.google.gson.JsonObject;

public class cp extends ci<cp.a> {
	private static final uh a = new uh("villager_trade");

	@Override
	public uh a() {
		return a;
	}

	public cp.a b(JsonObject jsonObject, be.b b, av av) {
		be.b b5 = be.b.a(jsonObject, "villager", av);
		bo bo6 = bo.a(jsonObject.get("item"));
		return new cp.a(b, b5, bo6);
	}

	public void a(ze ze, bdk bdk, bki bki) {
		dat dat5 = be.b(ze, bdk);
		this.a(ze, a -> a.a(dat5, bki));
	}

	public static class a extends aj {
		private final be.b a;
		private final bo b;

		public a(be.b b1, be.b b2, bo bo) {
			super(cp.a, b1);
			this.a = b2;
			this.b = bo;
		}

		public static cp.a c() {
			return new cp.a(be.b.a, be.b.a, bo.a);
		}

		public boolean a(dat dat, bki bki) {
			return !this.a.a(dat) ? false : this.b.a(bki);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("item", this.b.a());
			jsonObject3.add("villager", this.a.a(cg));
			return jsonObject3;
		}
	}
}
