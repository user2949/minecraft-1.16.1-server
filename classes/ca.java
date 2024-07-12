import com.google.gson.JsonObject;

public class ca extends ci<ca.a> {
	private static final uh a = new uh("nether_travel");

	@Override
	public uh a() {
		return a;
	}

	public ca.a b(JsonObject jsonObject, be.b b, av av) {
		bu bu5 = bu.a(jsonObject.get("entered"));
		bu bu6 = bu.a(jsonObject.get("exited"));
		aw aw7 = aw.a(jsonObject.get("distance"));
		return new ca.a(b, bu5, bu6, aw7);
	}

	public void a(ze ze, dem dem) {
		this.a(ze, a -> a.a(ze.u(), dem, ze.cC(), ze.cD(), ze.cG()));
	}

	public static class a extends aj {
		private final bu a;
		private final bu b;
		private final aw c;

		public a(be.b b, bu bu2, bu bu3, aw aw) {
			super(ca.a, b);
			this.a = bu2;
			this.b = bu3;
			this.c = aw;
		}

		public static ca.a a(aw aw) {
			return new ca.a(be.b.a, bu.a, bu.a, aw);
		}

		public boolean a(zd zd, dem dem, double double3, double double4, double double5) {
			if (!this.a.a(zd, dem.b, dem.c, dem.d)) {
				return false;
			} else {
				return !this.b.a(zd, double3, double4, double5) ? false : this.c.a(dem.b, dem.c, dem.d, double3, double4, double5);
			}
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("entered", this.a.a());
			jsonObject3.add("exited", this.b.a());
			jsonObject3.add("distance", this.c.a());
			return jsonObject3;
		}
	}
}
