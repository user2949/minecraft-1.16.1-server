import com.google.gson.JsonObject;

public class br extends ci<br.a> {
	private final uh a;

	public br(uh uh) {
		this.a = uh;
	}

	@Override
	public uh a() {
		return this.a;
	}

	public br.a b(JsonObject jsonObject, be.b b, av av) {
		return new br.a(this.a, b, be.b.a(jsonObject, "entity", av), au.a(jsonObject.get("killing_blow")));
	}

	public void a(ze ze, aom aom, anw anw) {
		dat dat5 = be.b(ze, aom);
		this.a(ze, a -> a.a(ze, dat5, anw));
	}

	public static class a extends aj {
		private final be.b a;
		private final au b;

		public a(uh uh, be.b b2, be.b b3, au au) {
			super(uh, b2);
			this.a = b3;
			this.b = au;
		}

		public static br.a a(be.a a) {
			return new br.a(aa.b.a, be.b.a, be.b.a(a.b()), au.a);
		}

		public static br.a c() {
			return new br.a(aa.b.a, be.b.a, be.b.a, au.a);
		}

		public static br.a a(be.a a, au.a a) {
			return new br.a(aa.b.a, be.b.a, be.b.a(a.b()), a.b());
		}

		public static br.a d() {
			return new br.a(aa.c.a, be.b.a, be.b.a, au.a);
		}

		public boolean a(ze ze, dat dat, anw anw) {
			return !this.b.a(ze, anw) ? false : this.a.a(dat);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("entity", this.a.a(cg));
			jsonObject3.add("killing_blow", this.b.a());
			return jsonObject3;
		}
	}
}
