import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class be {
	public static final be a = new be(bf.a, aw.a, bu.a, by.a, bz.a, bc.a, bb.a, ce.a, bh.a, null, null);
	private final bf b;
	private final aw c;
	private final bu d;
	private final by e;
	private final bz f;
	private final bc g;
	private final bb h;
	private final ce i;
	private final bh j;
	private final be k;
	private final be l;
	@Nullable
	private final String m;
	@Nullable
	private final uh n;

	private be(bf bf, aw aw, bu bu, by by, bz bz, bc bc, bb bb, ce ce, bh bh, @Nullable String string, @Nullable uh uh) {
		this.b = bf;
		this.c = aw;
		this.d = bu;
		this.e = by;
		this.f = bz;
		this.g = bc;
		this.h = bb;
		this.i = ce;
		this.j = bh;
		this.k = this;
		this.l = this;
		this.m = string;
		this.n = uh;
	}

	private be(bf bf, aw aw, bu bu, by by, bz bz, bc bc, bb bb, ce ce, bh bh, be be10, be be11, @Nullable String string, @Nullable uh uh) {
		this.b = bf;
		this.c = aw;
		this.d = bu;
		this.e = by;
		this.f = bz;
		this.g = bc;
		this.h = bb;
		this.i = ce;
		this.j = bh;
		this.k = be10;
		this.l = be11;
		this.m = string;
		this.n = uh;
	}

	public boolean a(ze ze, @Nullable aom aom) {
		return this.a(ze.u(), ze.cz(), aom);
	}

	public boolean a(zd zd, @Nullable dem dem, @Nullable aom aom) {
		if (this == a) {
			return true;
		} else if (aom == null) {
			return false;
		} else if (!this.b.a(aom.U())) {
			return false;
		} else {
			if (dem == null) {
				if (this.c != aw.a) {
					return false;
				}
			} else if (!this.c.a(dem.b, dem.c, dem.d, aom.cC(), aom.cD(), aom.cG())) {
				return false;
			}

			if (!this.d.a(zd, aom.cC(), aom.cD(), aom.cG())) {
				return false;
			} else if (!this.e.a(aom)) {
				return false;
			} else if (!this.f.a(aom)) {
				return false;
			} else if (!this.g.a(aom)) {
				return false;
			} else if (!this.h.a(aom)) {
				return false;
			} else if (!this.i.a(aom)) {
				return false;
			} else if (!this.j.a(aom)) {
				return false;
			} else if (!this.k.a(zd, dem, aom.cs())) {
				return false;
			} else if (!this.l.a(zd, dem, aom instanceof aoz ? ((aoz)aom).A() : null)) {
				return false;
			} else {
				if (this.m != null) {
					dfo dfo5 = aom.bC();
					if (dfo5 == null || !this.m.equals(dfo5.b())) {
						return false;
					}
				}

				return this.n == null || aom instanceof aym && ((aym)aom).eV().equals(this.n);
			}
		}
	}

	public static be a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "entity");
			bf bf3 = bf.a(jsonObject2.get("type"));
			aw aw4 = aw.a(jsonObject2.get("distance"));
			bu bu5 = bu.a(jsonObject2.get("location"));
			by by6 = by.a(jsonObject2.get("effects"));
			bz bz7 = bz.a(jsonObject2.get("nbt"));
			bc bc8 = bc.a(jsonObject2.get("flags"));
			bb bb9 = bb.a(jsonObject2.get("equipment"));
			ce ce10 = ce.a(jsonObject2.get("player"));
			bh bh11 = bh.a(jsonObject2.get("fishing_hook"));
			be be12 = a(jsonObject2.get("vehicle"));
			be be13 = a(jsonObject2.get("targeted_entity"));
			String string14 = adt.a(jsonObject2, "team", null);
			uh uh15 = jsonObject2.has("catType") ? new uh(adt.h(jsonObject2, "catType")) : null;
			return new be.a().a(bf3).a(aw4).a(bu5).a(by6).a(bz7).a(bc8).a(bb9).a(ce10).a(bh11).a(string14).a(be12).b(be13).b(uh15).b();
		} else {
			return a;
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.add("type", this.b.a());
			jsonObject2.add("distance", this.c.a());
			jsonObject2.add("location", this.d.a());
			jsonObject2.add("effects", this.e.b());
			jsonObject2.add("nbt", this.f.a());
			jsonObject2.add("flags", this.g.a());
			jsonObject2.add("equipment", this.h.a());
			jsonObject2.add("player", this.i.a());
			jsonObject2.add("fishing_hook", this.j.a());
			jsonObject2.add("vehicle", this.k.a());
			jsonObject2.add("targeted_entity", this.l.a());
			jsonObject2.addProperty("team", this.m);
			if (this.n != null) {
				jsonObject2.addProperty("catType", this.n.toString());
			}

			return jsonObject2;
		}
	}

	public static dat b(ze ze, aom aom) {
		return new dat.a(ze.u()).a(dda.a, aom).a(dda.f, aom.cA()).a(dda.g, ze.cz()).a(ze.cX()).a(dcz.j);
	}

	public static class a {
		private bf a;
		private aw b;
		private bu c;
		private by d;
		private bz e;
		private bc f;
		private bb g;
		private ce h;
		private bh i;
		private be j;
		private be k;
		private String l;
		private uh m;

		public a() {
			this.a = bf.a;
			this.b = aw.a;
			this.c = bu.a;
			this.d = by.a;
			this.e = bz.a;
			this.f = bc.a;
			this.g = bb.a;
			this.h = ce.a;
			this.i = bh.a;
			this.j = be.a;
			this.k = be.a;
		}

		public static be.a a() {
			return new be.a();
		}

		public be.a a(aoq<?> aoq) {
			this.a = bf.b(aoq);
			return this;
		}

		public be.a a(adf<aoq<?>> adf) {
			this.a = bf.a(adf);
			return this;
		}

		public be.a a(uh uh) {
			this.m = uh;
			return this;
		}

		public be.a a(bf bf) {
			this.a = bf;
			return this;
		}

		public be.a a(aw aw) {
			this.b = aw;
			return this;
		}

		public be.a a(bu bu) {
			this.c = bu;
			return this;
		}

		public be.a a(by by) {
			this.d = by;
			return this;
		}

		public be.a a(bz bz) {
			this.e = bz;
			return this;
		}

		public be.a a(bc bc) {
			this.f = bc;
			return this;
		}

		public be.a a(bb bb) {
			this.g = bb;
			return this;
		}

		public be.a a(ce ce) {
			this.h = ce;
			return this;
		}

		public be.a a(bh bh) {
			this.i = bh;
			return this;
		}

		public be.a a(be be) {
			this.j = be;
			return this;
		}

		public be.a b(be be) {
			this.k = be;
			return this;
		}

		public be.a a(@Nullable String string) {
			this.l = string;
			return this;
		}

		public be.a b(@Nullable uh uh) {
			this.m = uh;
			return this;
		}

		public be b() {
			return new be(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m);
		}
	}

	public static class b {
		public static final be.b a = new be.b(new ddm[0]);
		private final ddm[] b;
		private final Predicate<dat> c;

		private b(ddm[] arr) {
			this.b = arr;
			this.c = ddo.a(arr);
		}

		public static be.b a(ddm... arr) {
			return new be.b(arr);
		}

		public static be.b a(JsonObject jsonObject, String string, av av) {
			JsonElement jsonElement4 = jsonObject.get(string);
			return a(string, av, jsonElement4);
		}

		public static be.b[] b(JsonObject jsonObject, String string, av av) {
			JsonElement jsonElement4 = jsonObject.get(string);
			if (jsonElement4 != null && !jsonElement4.isJsonNull()) {
				JsonArray jsonArray5 = adt.n(jsonElement4, string);
				be.b[] arr6 = new be.b[jsonArray5.size()];

				for (int integer7 = 0; integer7 < jsonArray5.size(); integer7++) {
					arr6[integer7] = a(string + "[" + integer7 + "]", av, jsonArray5.get(integer7));
				}

				return arr6;
			} else {
				return new be.b[0];
			}
		}

		private static be.b a(String string, av av, @Nullable JsonElement jsonElement) {
			if (jsonElement != null && jsonElement.isJsonArray()) {
				ddm[] arr4 = av.a(jsonElement.getAsJsonArray(), av.a().toString() + "/" + string, dcz.j);
				return new be.b(arr4);
			} else {
				be be4 = be.a(jsonElement);
				return a(be4);
			}
		}

		public static be.b a(be be) {
			if (be == be.a) {
				return a;
			} else {
				ddm ddm2 = ddp.a(dat.c.THIS, be).build();
				return new be.b(new ddm[]{ddm2});
			}
		}

		public boolean a(dat dat) {
			return this.c.test(dat);
		}

		public JsonElement a(cg cg) {
			return (JsonElement)(this.b.length == 0 ? JsonNull.INSTANCE : cg.a(this.b));
		}

		public static JsonElement a(be.b[] arr, cg cg) {
			if (arr.length == 0) {
				return JsonNull.INSTANCE;
			} else {
				JsonArray jsonArray3 = new JsonArray();

				for (be.b b7 : arr) {
					jsonArray3.add(b7.a(cg));
				}

				return jsonArray3;
			}
		}
	}
}
