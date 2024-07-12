import com.google.gson.JsonObject;
import java.util.Collection;

public class bi extends ci<bi.a> {
	private static final uh a = new uh("fishing_rod_hooked");

	@Override
	public uh a() {
		return a;
	}

	public bi.a b(JsonObject jsonObject, be.b b, av av) {
		bo bo5 = bo.a(jsonObject.get("rod"));
		be.b b6 = be.b.a(jsonObject, "entity", av);
		bo bo7 = bo.a(jsonObject.get("item"));
		return new bi.a(b, bo5, b6, bo7);
	}

	public void a(ze ze, bki bki, beo beo, Collection<bki> collection) {
		dat dat6 = be.b(ze, (aom)(beo.k() != null ? beo.k() : beo));
		this.a(ze, a -> a.a(bki, dat6, collection));
	}

	public static class a extends aj {
		private final bo a;
		private final be.b b;
		private final bo c;

		public a(be.b b1, bo bo2, be.b b3, bo bo4) {
			super(bi.a, b1);
			this.a = bo2;
			this.b = b3;
			this.c = bo4;
		}

		public static bi.a a(bo bo1, be be, bo bo3) {
			return new bi.a(be.b.a, bo1, be.b.a(be), bo3);
		}

		public boolean a(bki bki, dat dat, Collection<bki> collection) {
			if (!this.a.a(bki)) {
				return false;
			} else if (!this.b.a(dat)) {
				return false;
			} else {
				if (this.c != bo.a) {
					boolean boolean5 = false;
					aom aom6 = dat.c(dda.a);
					if (aom6 instanceof bbg) {
						bbg bbg7 = (bbg)aom6;
						if (this.c.a(bbg7.g())) {
							boolean5 = true;
						}
					}

					for (bki bki8 : collection) {
						if (this.c.a(bki8)) {
							boolean5 = true;
							break;
						}
					}

					if (!boolean5) {
						return false;
					}
				}

				return true;
			}
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			jsonObject3.add("rod", this.a.a());
			jsonObject3.add("entity", this.b.a(cg));
			jsonObject3.add("item", this.c.a());
			return jsonObject3;
		}
	}
}
