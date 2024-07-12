import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class am extends ci<am.a> {
	private static final uh a = new uh("bred_animals");

	@Override
	public uh a() {
		return a;
	}

	public am.a b(JsonObject jsonObject, be.b b, av av) {
		be.b b5 = be.b.a(jsonObject, "parent", av);
		be.b b6 = be.b.a(jsonObject, "partner", av);
		be.b b7 = be.b.a(jsonObject, "child", av);
		return new am.a(b, b5, b6, b7);
	}

	public void a(ze ze, ayk ayk2, ayk ayk3, @Nullable aok aok) {
		dat dat6 = be.b(ze, ayk2);
		dat dat7 = be.b(ze, ayk3);
		dat dat8 = aok != null ? be.b(ze, aok) : null;
		this.a(ze, a -> a.a(dat6, dat7, dat8));
	}

	public static class a extends aj {
		private final be.b a;
		private final be.b b;
		private final be.b c;

		public a(be.b b1, be.b b2, be.b b3, be.b b4) {
			super(am.a, b1);
			this.a = b2;
			this.b = b3;
			this.c = b4;
		}

		public static am.a c() {
			return new am.a(be.b.a, be.b.a, be.b.a, be.b.a);
		}

		public static am.a a(be.a a) {
			return new am.a(be.b.a, be.b.a, be.b.a, be.b.a(a.b()));
		}

		public static am.a a(be be1, be be2, be be3) {
			return new am.a(be.b.a, be.b.a(be1), be.b.a(be2), be.b.a(be3));
		}

		public boolean a(dat dat1, dat dat2, @Nullable dat dat3) {
			return this.c == be.b.a || dat3 != null && this.c.a(dat3) ? this.a.a(dat1) && this.b.a(dat2) || this.a.a(dat2) && this.b.a(dat1) : false;
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("parent", this.a.a(cg));
			jsonObject3.add("partner", this.b.a(cg));
			jsonObject3.add("child", this.c.a(cg));
			return jsonObject3;
		}
	}
}
