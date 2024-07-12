import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class cb extends ci<cb.a> {
	private static final uh a = new uh("placed_block");

	@Override
	public uh a() {
		return a;
	}

	public cb.a b(JsonObject jsonObject, be.b b, av av) {
		bvr bvr5 = a(jsonObject);
		ck ck6 = ck.a(jsonObject.get("state"));
		if (bvr5 != null) {
			ck6.a(bvr5.m(), string -> {
				throw new JsonSyntaxException("Block " + bvr5 + " has no property " + string + ":");
			});
		}

		bu bu7 = bu.a(jsonObject.get("location"));
		bo bo8 = bo.a(jsonObject.get("item"));
		return new cb.a(b, bvr5, ck6, bu7, bo8);
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

	public void a(ze ze, fu fu, bki bki) {
		cfj cfj5 = ze.u().d_(fu);
		this.a(ze, a -> a.a(cfj5, fu, ze.u(), bki));
	}

	public static class a extends aj {
		private final bvr a;
		private final ck b;
		private final bu c;
		private final bo d;

		public a(be.b b, @Nullable bvr bvr, ck ck, bu bu, bo bo) {
			super(cb.a, b);
			this.a = bvr;
			this.b = ck;
			this.c = bu;
			this.d = bo;
		}

		public static cb.a a(bvr bvr) {
			return new cb.a(be.b.a, bvr, ck.a, bu.a, bo.a);
		}

		public boolean a(cfj cfj, fu fu, zd zd, bki bki) {
			if (this.a != null && !cfj.a(this.a)) {
				return false;
			} else if (!this.b.a(cfj)) {
				return false;
			} else {
				return !this.c.a(zd, (float)fu.u(), (float)fu.v(), (float)fu.w()) ? false : this.d.a(bki);
			}
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			if (this.a != null) {
				jsonObject3.addProperty("block", gl.aj.b(this.a).toString());
			}

			jsonObject3.add("state", this.b.a());
			jsonObject3.add("location", this.c.a());
			jsonObject3.add("item", this.d.a());
			return jsonObject3;
		}
	}
}
