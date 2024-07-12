import com.google.gson.JsonObject;

public class bv extends ci<bv.a> {
	private final uh a;

	public bv(uh uh) {
		this.a = uh;
	}

	@Override
	public uh a() {
		return this.a;
	}

	public bv.a b(JsonObject jsonObject, be.b b, av av) {
		JsonObject jsonObject5 = adt.a(jsonObject, "location", jsonObject);
		bu bu6 = bu.a(jsonObject5);
		return new bv.a(this.a, b, bu6);
	}

	public void a(ze ze) {
		this.a(ze, a -> a.a(ze.u(), ze.cC(), ze.cD(), ze.cG()));
	}

	public static class a extends aj {
		private final bu a;

		public a(uh uh, be.b b, bu bu) {
			super(uh, b);
			this.a = bu;
		}

		public static bv.a a(bu bu) {
			return new bv.a(aa.p.a, be.b.a, bu);
		}

		public static bv.a c() {
			return new bv.a(aa.q.a, be.b.a, bu.a);
		}

		public static bv.a d() {
			return new bv.a(aa.H.a, be.b.a, bu.a);
		}

		public boolean a(zd zd, double double2, double double3, double double4) {
			return this.a.a(zd, double2, double3, double4);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("location", this.a.a());
			return jsonObject3;
		}
	}
}
