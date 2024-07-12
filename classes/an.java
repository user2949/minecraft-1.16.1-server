import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class an extends ci<an.a> {
	private static final uh a = new uh("brewed_potion");

	@Override
	public uh a() {
		return a;
	}

	public an.a b(JsonObject jsonObject, be.b b, av av) {
		bmb bmb5 = null;
		if (jsonObject.has("potion")) {
			uh uh6 = new uh(adt.h(jsonObject, "potion"));
			bmb5 = (bmb)gl.an.b(uh6).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + uh6 + "'"));
		}

		return new an.a(b, bmb5);
	}

	public void a(ze ze, bmb bmb) {
		this.a(ze, a -> a.a(bmb));
	}

	public static class a extends aj {
		private final bmb a;

		public a(be.b b, @Nullable bmb bmb) {
			super(an.a, b);
			this.a = bmb;
		}

		public static an.a c() {
			return new an.a(be.b.a, null);
		}

		public boolean a(bmb bmb) {
			return this.a == null || this.a == bmb;
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			if (this.a != null) {
				jsonObject3.addProperty("potion", gl.an.b(this.a).toString());
			}

			return jsonObject3;
		}
	}
}
