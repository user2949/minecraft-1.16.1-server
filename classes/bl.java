import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;

public class bl extends ci<bl.a> {
	private static final uh a = new uh("inventory_changed");

	@Override
	public uh a() {
		return a;
	}

	public bl.a b(JsonObject jsonObject, be.b b, av av) {
		JsonObject jsonObject5 = adt.a(jsonObject, "slots", new JsonObject());
		bx.d d6 = bx.d.a(jsonObject5.get("occupied"));
		bx.d d7 = bx.d.a(jsonObject5.get("full"));
		bx.d d8 = bx.d.a(jsonObject5.get("empty"));
		bo[] arr9 = bo.b(jsonObject.get("items"));
		return new bl.a(b, d6, d7, d8, arr9);
	}

	public void a(ze ze, beb beb, bki bki) {
		int integer5 = 0;
		int integer6 = 0;
		int integer7 = 0;

		for (int integer8 = 0; integer8 < beb.ab_(); integer8++) {
			bki bki9 = beb.a(integer8);
			if (bki9.a()) {
				integer6++;
			} else {
				integer7++;
				if (bki9.E() >= bki9.c()) {
					integer5++;
				}
			}
		}

		this.a(ze, beb, bki, integer5, integer6, integer7);
	}

	private void a(ze ze, beb beb, bki bki, int integer4, int integer5, int integer6) {
		this.a(ze, a -> a.a(beb, bki, integer4, integer5, integer6));
	}

	public static class a extends aj {
		private final bx.d a;
		private final bx.d b;
		private final bx.d c;
		private final bo[] d;

		public a(be.b b, bx.d d2, bx.d d3, bx.d d4, bo[] arr) {
			super(bl.a, b);
			this.a = d2;
			this.b = d3;
			this.c = d4;
			this.d = arr;
		}

		public static bl.a a(bo... arr) {
			return new bl.a(be.b.a, bx.d.e, bx.d.e, bx.d.e, arr);
		}

		public static bl.a a(bqa... arr) {
			bo[] arr2 = new bo[arr.length];

			for (int integer3 = 0; integer3 < arr.length; integer3++) {
				arr2[integer3] = new bo(null, arr[integer3].h(), bx.d.e, bx.d.e, az.b, az.b, null, bz.a);
			}

			return a(arr2);
		}

		@Override
		public JsonObject a(cg cg) {
			JsonObject jsonObject3 = super.a(cg);
			if (!this.a.c() || !this.b.c() || !this.c.c()) {
				JsonObject jsonObject4 = new JsonObject();
				jsonObject4.add("occupied", this.a.d());
				jsonObject4.add("full", this.b.d());
				jsonObject4.add("empty", this.c.d());
				jsonObject3.add("slots", jsonObject4);
			}

			if (this.d.length > 0) {
				JsonArray jsonArray4 = new JsonArray();

				for (bo bo8 : this.d) {
					jsonArray4.add(bo8.a());
				}

				jsonObject3.add("items", jsonArray4);
			}

			return jsonObject3;
		}

		public boolean a(beb beb, bki bki, int integer3, int integer4, int integer5) {
			if (!this.b.d(integer3)) {
				return false;
			} else if (!this.c.d(integer4)) {
				return false;
			} else if (!this.a.d(integer5)) {
				return false;
			} else {
				int integer7 = this.d.length;
				if (integer7 == 0) {
					return true;
				} else if (integer7 != 1) {
					List<bo> list8 = new ObjectArrayList<>(this.d);
					int integer9 = beb.ab_();

					for (int integer10 = 0; integer10 < integer9; integer10++) {
						if (list8.isEmpty()) {
							return true;
						}

						bki bki11 = beb.a(integer10);
						if (!bki11.a()) {
							list8.removeIf(bo -> bo.a(bki11));
						}
					}

					return list8.isEmpty();
				} else {
					return !bki.a() && this.d[0].a(bki);
				}
			}
		}
	}
}
