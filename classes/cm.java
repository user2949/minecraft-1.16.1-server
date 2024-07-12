import com.google.gson.JsonObject;

public class cm extends ci<cm.a> {
	private static final uh a = new uh("tame_animal");

	@Override
	public uh a() {
		return a;
	}

	public cm.a b(JsonObject jsonObject, be.b b, av av) {
		be.b b5 = be.b.a(jsonObject, "entity", av);
		return new cm.a(b, b5);
	}

	public void a(ze ze, ayk ayk) {
		dat dat4 = be.b(ze, ayk);
		this.a(ze, a -> a.a(dat4));
	}

	public static class a extends aj {
		private final be.b a;

		public a(be.b b1, be.b b2) {
			super(cm.a, b1);
			this.a = b2;
		}

		public static cm.a c() {
			return new cm.a(be.b.a, be.b.a);
		}

		public static cm.a a(be be) {
			return new cm.a(be.b.a, be.b.a(be));
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
