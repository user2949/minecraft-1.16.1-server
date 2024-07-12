import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class ao extends ci<ao.a> {
	private static final uh a = new uh("changed_dimension");

	@Override
	public uh a() {
		return a;
	}

	public ao.a b(JsonObject jsonObject, be.b b, av av) {
		ug<bqb> ug5 = jsonObject.has("from") ? ug.a(gl.ae, new uh(adt.h(jsonObject, "from"))) : null;
		ug<bqb> ug6 = jsonObject.has("to") ? ug.a(gl.ae, new uh(adt.h(jsonObject, "to"))) : null;
		return new ao.a(b, ug5, ug6);
	}

	public void a(ze ze, ug<bqb> ug2, ug<bqb> ug3) {
		this.a(ze, a -> a.b(ug2, ug3));
	}

	public static class a extends aj {
		@Nullable
		private final ug<bqb> a;
		@Nullable
		private final ug<bqb> b;

		public a(be.b b, @Nullable ug<bqb> ug2, @Nullable ug<bqb> ug3) {
			super(ao.a, b);
			this.a = ug2;
			this.b = ug3;
		}

		public static ao.a a(ug<bqb> ug) {
			return new ao.a(be.b.a, null, ug);
		}

		public boolean b(ug<bqb> ug1, ug<bqb> ug2) {
			return this.a != null && this.a != ug1 ? false : this.b == null || this.b == ug2;
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			if (this.a != null) {
				jsonObject3.addProperty("from", this.a.a().toString());
			}

			if (this.b != null) {
				jsonObject3.addProperty("to", this.b.a().toString());
			}

			return jsonObject3;
		}
	}
}
