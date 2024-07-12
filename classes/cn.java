import com.google.gson.JsonObject;

public class cn extends ci<cn.a> {
	private static final uh a = new uh("target_hit");

	@Override
	public uh a() {
		return a;
	}

	public cn.a b(JsonObject jsonObject, be.b b, av av) {
		bx.d d5 = bx.d.a(jsonObject.get("signal_strength"));
		be.b b6 = be.b.a(jsonObject, "projectile", av);
		return new cn.a(b, d5, b6);
	}

	public void a(ze ze, aom aom, dem dem, int integer) {
		dat dat6 = be.b(ze, aom);
		this.a(ze, a -> a.a(dat6, dem, integer));
	}

	public static class a extends aj {
		private final bx.d a;
		private final be.b b;

		public a(be.b b1, bx.d d, be.b b3) {
			super(cn.a, b1);
			this.a = d;
			this.b = b3;
		}

		public static cn.a a(bx.d d, be.b b) {
			return new cn.a(be.b.a, d, b);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("signal_strength", this.a.d());
			jsonObject3.add("projectile", this.b.a(cg));
			return jsonObject3;
		}

		public boolean a(dat dat, dem dem, int integer) {
			return !this.a.d(integer) ? false : this.b.a(dat);
		}
	}
}
