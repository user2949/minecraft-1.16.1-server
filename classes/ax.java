import com.google.gson.JsonObject;

public class ax extends ci<ax.a> {
	private static final uh a = new uh("effects_changed");

	@Override
	public uh a() {
		return a;
	}

	public ax.a b(JsonObject jsonObject, be.b b, av av) {
		by by5 = by.a(jsonObject.get("effects"));
		return new ax.a(b, by5);
	}

	public void a(ze ze) {
		this.a(ze, a -> a.a(ze));
	}

	public static class a extends aj {
		private final by a;

		public a(be.b b, by by) {
			super(ax.a, b);
			this.a = by;
		}

		public static ax.a a(by by) {
			return new ax.a(be.b.a, by);
		}

		public boolean a(ze ze) {
			return this.a.a((aoy)ze);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("effects", this.a.b());
			return jsonObject3;
		}
	}
}
