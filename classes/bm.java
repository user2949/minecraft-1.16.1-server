import com.google.gson.JsonObject;

public class bm extends ci<bm.a> {
	private static final uh a = new uh("item_durability_changed");

	@Override
	public uh a() {
		return a;
	}

	public bm.a b(JsonObject jsonObject, be.b b, av av) {
		bo bo5 = bo.a(jsonObject.get("item"));
		bx.d d6 = bx.d.a(jsonObject.get("durability"));
		bx.d d7 = bx.d.a(jsonObject.get("delta"));
		return new bm.a(b, bo5, d6, d7);
	}

	public void a(ze ze, bki bki, int integer) {
		this.a(ze, a -> a.a(bki, integer));
	}

	public static class a extends aj {
		private final bo a;
		private final bx.d b;
		private final bx.d c;

		public a(be.b b, bo bo, bx.d d3, bx.d d4) {
			super(bm.a, b);
			this.a = bo;
			this.b = d3;
			this.c = d4;
		}

		public static bm.a a(be.b b, bo bo, bx.d d) {
			return new bm.a(b, bo, d, bx.d.e);
		}

		public boolean a(bki bki, int integer) {
			if (!this.a.a(bki)) {
				return false;
			} else {
				return !this.b.d(bki.h() - integer) ? false : this.c.d(bki.g() - integer);
			}
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("item", this.a.a());
			jsonObject3.add("durability", this.b.d());
			jsonObject3.add("delta", this.c.d());
			return jsonObject3;
		}
	}
}
