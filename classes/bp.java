import com.google.gson.JsonObject;

public class bp extends ci<bp.a> {
	private static final uh a = new uh("item_used_on_block");

	@Override
	public uh a() {
		return a;
	}

	public bp.a b(JsonObject jsonObject, be.b b, av av) {
		bu bu5 = bu.a(jsonObject.get("location"));
		bo bo6 = bo.a(jsonObject.get("item"));
		return new bp.a(b, bu5, bo6);
	}

	public void a(ze ze, fu fu, bki bki) {
		cfj cfj5 = ze.u().d_(fu);
		this.a(ze, a -> a.a(cfj5, ze.u(), fu, bki));
	}

	public static class a extends aj {
		private final bu a;
		private final bo b;

		public a(be.b b, bu bu, bo bo) {
			super(bp.a, b);
			this.a = bu;
			this.b = bo;
		}

		public static bp.a a(bu.a a, bo.a a) {
			return new bp.a(be.b.a, a.b(), a.b());
		}

		public boolean a(cfj cfj, zd zd, fu fu, bki bki) {
			return !this.a.a(zd, (double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5) ? false : this.b.a(bki);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("location", this.a.a());
			jsonObject3.add("item", this.b.a());
			return jsonObject3;
		}
	}
}
