import com.google.gson.JsonObject;

public class bs extends ci<bs.a> {
	private static final uh a = new uh("levitation");

	@Override
	public uh a() {
		return a;
	}

	public bs.a b(JsonObject jsonObject, be.b b, av av) {
		aw aw5 = aw.a(jsonObject.get("distance"));
		bx.d d6 = bx.d.a(jsonObject.get("duration"));
		return new bs.a(b, aw5, d6);
	}

	public void a(ze ze, dem dem, int integer) {
		this.a(ze, a -> a.a(ze, dem, integer));
	}

	public static class a extends aj {
		private final aw a;
		private final bx.d b;

		public a(be.b b, aw aw, bx.d d) {
			super(bs.a, b);
			this.a = aw;
			this.b = d;
		}

		public static bs.a a(aw aw) {
			return new bs.a(be.b.a, aw, bx.d.e);
		}

		public boolean a(ze ze, dem dem, int integer) {
			return !this.a.a(dem.b, dem.c, dem.d, ze.cC(), ze.cD(), ze.cG()) ? false : this.b.d(integer);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("distance", this.a.a());
			jsonObject3.add("duration", this.b.d());
			return jsonObject3;
		}
	}
}
