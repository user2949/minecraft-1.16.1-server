import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class ak extends ci<ak.a> {
	private static final uh a = new uh("bee_nest_destroyed");

	@Override
	public uh a() {
		return a;
	}

	public ak.a b(JsonObject jsonObject, be.b b, av av) {
		bvr bvr5 = a(jsonObject);
		bo bo6 = bo.a(jsonObject.get("item"));
		bx.d d7 = bx.d.a(jsonObject.get("num_bees_inside"));
		return new ak.a(b, bvr5, bo6, d7);
	}

	@Nullable
	private static bvr a(JsonObject jsonObject) {
		if (jsonObject.has("block")) {
			uh uh2 = new uh(adt.h(jsonObject, "block"));
			return (bvr)gl.aj.b(uh2).orElseThrow(() -> new JsonSyntaxException("Unknown block type '" + uh2 + "'"));
		} else {
			return null;
		}
	}

	public void a(ze ze, bvr bvr, bki bki, int integer) {
		this.a(ze, a -> a.a(bvr, bki, integer));
	}

	public static class a extends aj {
		@Nullable
		private final bvr a;
		private final bo b;
		private final bx.d c;

		public a(be.b b, @Nullable bvr bvr, bo bo, bx.d d) {
			super(ak.a, b);
			this.a = bvr;
			this.b = bo;
			this.c = d;
		}

		public static ak.a a(bvr bvr, bo.a a, bx.d d) {
			return new ak.a(be.b.a, bvr, a.b(), d);
		}

		public boolean a(bvr bvr, bki bki, int integer) {
			if (this.a != null && bvr != this.a) {
				return false;
			} else {
				return !this.b.a(bki) ? false : this.c.d(integer);
			}
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			if (this.a != null) {
				jsonObject3.addProperty("block", gl.aj.b(this.a).toString());
			}

			jsonObject3.add("item", this.b.a());
			jsonObject3.add("num_bees_inside", this.c.d());
			return jsonObject3;
		}
	}
}
