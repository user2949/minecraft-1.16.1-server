import com.google.gson.JsonObject;

public class aq extends ci<aq.a> {
	private static final uh a = new uh("construct_beacon");

	@Override
	public uh a() {
		return a;
	}

	public aq.a b(JsonObject jsonObject, be.b b, av av) {
		bx.d d5 = bx.d.a(jsonObject.get("level"));
		return new aq.a(b, d5);
	}

	public void a(ze ze, cdg cdg) {
		this.a(ze, a -> a.a(cdg));
	}

	public static class a extends aj {
		private final bx.d a;

		public a(be.b b, bx.d d) {
			super(aq.a, b);
			this.a = d;
		}

		public static aq.a a(bx.d d) {
			return new aq.a(be.b.a, d);
		}

		public boolean a(cdg cdg) {
			return this.a.d(cdg.h());
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("level", this.a.d());
			return jsonObject3;
		}
	}
}
