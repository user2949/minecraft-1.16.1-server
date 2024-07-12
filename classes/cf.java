import com.google.gson.JsonObject;

public class cf extends ci<cf.a> {
	private static final uh a = new uh("recipe_unlocked");

	@Override
	public uh a() {
		return a;
	}

	public cf.a b(JsonObject jsonObject, be.b b, av av) {
		uh uh5 = new uh(adt.h(jsonObject, "recipe"));
		return new cf.a(b, uh5);
	}

	public void a(ze ze, bmu<?> bmu) {
		this.a(ze, a -> a.a(bmu));
	}

	public static cf.a a(uh uh) {
		return new cf.a(be.b.a, uh);
	}

	public static class a extends aj {
		private final uh a;

		public a(be.b b, uh uh) {
			super(cf.a, b);
			this.a = uh;
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.addProperty("recipe", this.a.toString());
			return jsonObject3;
		}

		public boolean a(bmu<?> bmu) {
			return this.a.equals(bmu.f());
		}
	}
}
