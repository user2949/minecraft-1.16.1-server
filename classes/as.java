import com.google.gson.JsonObject;

public class as extends ci<as.a> {
	private static final uh a = new uh("cured_zombie_villager");

	@Override
	public uh a() {
		return a;
	}

	public as.a b(JsonObject jsonObject, be.b b, av av) {
		be.b b5 = be.b.a(jsonObject, "zombie", av);
		be.b b6 = be.b.a(jsonObject, "villager", av);
		return new as.a(b, b5, b6);
	}

	public void a(ze ze, bcu bcu, bdp bdp) {
		dat dat5 = be.b(ze, bcu);
		dat dat6 = be.b(ze, bdp);
		this.a(ze, a -> a.a(dat5, dat6));
	}

	public static class a extends aj {
		private final be.b a;
		private final be.b b;

		public a(be.b b1, be.b b2, be.b b3) {
			super(as.a, b1);
			this.a = b2;
			this.b = b3;
		}

		public static as.a c() {
			return new as.a(be.b.a, be.b.a, be.b.a);
		}

		public boolean a(dat dat1, dat dat2) {
			return !this.a.a(dat1) ? false : this.b.a(dat2);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("zombie", this.a.a(cg));
			jsonObject3.add("villager", this.b.a(cg));
			return jsonObject3;
		}
	}
}
