import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class cj extends ci<cj.a> {
	private static final uh a = new uh("slide_down_block");

	@Override
	public uh a() {
		return a;
	}

	public cj.a b(JsonObject jsonObject, be.b b, av av) {
		bvr bvr5 = a(jsonObject);
		ck ck6 = ck.a(jsonObject.get("state"));
		if (bvr5 != null) {
			ck6.a(bvr5.m(), string -> {
				throw new JsonSyntaxException("Block " + bvr5 + " has no property " + string);
			});
		}

		return new cj.a(b, bvr5, ck6);
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

	public void a(ze ze, cfj cfj) {
		this.a(ze, a -> a.a(cfj));
	}

	public static class a extends aj {
		private final bvr a;
		private final ck b;

		public a(be.b b, @Nullable bvr bvr, ck ck) {
			super(cj.a, b);
			this.a = bvr;
			this.b = ck;
		}

		public static cj.a a(bvr bvr) {
			return new cj.a(be.b.a, bvr, ck.a);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			if (this.a != null) {
				jsonObject3.addProperty("block", gl.aj.b(this.a).toString());
			}

			jsonObject3.add("state", this.b.a());
			return jsonObject3;
		}

		public boolean a(cfj cfj) {
			return this.a != null && !cfj.a(this.a) ? false : this.b.a(cfj);
		}
	}
}
