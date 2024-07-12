import com.google.gson.JsonObject;

public class cl extends ci<cl.a> {
	private static final uh a = new uh("summoned_entity");

	@Override
	public uh a() {
		return a;
	}

	public cl.a b(JsonObject jsonObject, be.b b, av av) {
		be.b b5 = be.b.a(jsonObject, "entity", av);
		return new cl.a(b, b5);
	}

	public void a(ze ze, aom aom) {
		dat dat4 = be.b(ze, aom);
		this.a(ze, a -> a.a(dat4));
	}

	public static class a extends aj {
		private final be.b a;

		public a(be.b b1, be.b b2) {
			super(cl.a, b1);
			this.a = b2;
		}

		public static cl.a a(be.a a) {
			return new cl.a(be.b.a, be.b.a(a.b()));
		}

		public boolean a(dat dat) {
			return this.a.a(dat);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("entity", this.a.a(cg));
			return jsonObject3;
		}
	}
}
