import com.google.gson.JsonObject;

public class bw extends ci<bw.a> {
	private static final uh a = new uh("player_generates_container_loot");

	@Override
	public uh a() {
		return a;
	}

	protected bw.a b(JsonObject jsonObject, be.b b, av av) {
		uh uh5 = new uh(adt.h(jsonObject, "loot_table"));
		return new bw.a(b, uh5);
	}

	public void a(ze ze, uh uh) {
		this.a(ze, a -> a.b(uh));
	}

	public static class a extends aj {
		private final uh a;

		public a(be.b b, uh uh) {
			super(bw.a, b);
			this.a = uh;
		}

		public static bw.a a(uh uh) {
			return new bw.a(be.b.a, uh);
		}

		public boolean b(uh uh) {
			return this.a.equals(uh);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.addProperty("loot_table", this.a.toString());
			return jsonObject3;
		}
	}
}
