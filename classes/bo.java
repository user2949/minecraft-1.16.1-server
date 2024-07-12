import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class bo {
	public static final bo a = new bo();
	@Nullable
	private final adf<bke> b;
	@Nullable
	private final bke c;
	private final bx.d d;
	private final bx.d e;
	private final az[] f;
	private final az[] g;
	@Nullable
	private final bmb h;
	private final bz i;

	public bo() {
		this.b = null;
		this.c = null;
		this.h = null;
		this.d = bx.d.e;
		this.e = bx.d.e;
		this.f = az.b;
		this.g = az.b;
		this.i = bz.a;
	}

	public bo(@Nullable adf<bke> adf, @Nullable bke bke, bx.d d3, bx.d d4, az[] arr5, az[] arr6, @Nullable bmb bmb, bz bz) {
		this.b = adf;
		this.c = bke;
		this.d = d3;
		this.e = d4;
		this.f = arr5;
		this.g = arr6;
		this.h = bmb;
		this.i = bz;
	}

	public boolean a(bki bki) {
		if (this == a) {
			return true;
		} else if (this.b != null && !this.b.a(bki.b())) {
			return false;
		} else if (this.c != null && bki.b() != this.c) {
			return false;
		} else if (!this.d.d(bki.E())) {
			return false;
		} else if (!this.e.c() && !bki.e()) {
			return false;
		} else if (!this.e.d(bki.h() - bki.g())) {
			return false;
		} else if (!this.i.a(bki)) {
			return false;
		} else {
			if (this.f.length > 0) {
				Map<bnw, Integer> map3 = bny.a(bki.q());

				for (az az7 : this.f) {
					if (!az7.a(map3)) {
						return false;
					}
				}
			}

			if (this.g.length > 0) {
				Map<bnw, Integer> map3 = bny.a(bjm.d(bki));

				for (az az7x : this.g) {
					if (!az7x.a(map3)) {
						return false;
					}
				}
			}

			bmb bmb3 = bmd.d(bki);
			return this.h == null || this.h == bmb3;
		}
	}

	public static bo a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "item");
			bx.d d3 = bx.d.a(jsonObject2.get("count"));
			bx.d d4 = bx.d.a(jsonObject2.get("durability"));
			if (jsonObject2.has("data")) {
				throw new JsonParseException("Disallowed data tag found");
			} else {
				bz bz5 = bz.a(jsonObject2.get("nbt"));
				bke bke6 = null;
				if (jsonObject2.has("item")) {
					uh uh7 = new uh(adt.h(jsonObject2, "item"));
					bke6 = (bke)gl.am.b(uh7).orElseThrow(() -> new JsonSyntaxException("Unknown item id '" + uh7 + "'"));
				}

				adf<bke> adf7 = null;
				if (jsonObject2.has("tag")) {
					uh uh8 = new uh(adt.h(jsonObject2, "tag"));
					adf7 = adb.e().b().a(uh8);
					if (adf7 == null) {
						throw new JsonSyntaxException("Unknown item tag '" + uh8 + "'");
					}
				}

				bmb bmb8 = null;
				if (jsonObject2.has("potion")) {
					uh uh9 = new uh(adt.h(jsonObject2, "potion"));
					bmb8 = (bmb)gl.an.b(uh9).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + uh9 + "'"));
				}

				az[] arr9 = az.b(jsonObject2.get("enchantments"));
				az[] arr10 = az.b(jsonObject2.get("stored_enchantments"));
				return new bo(adf7, bke6, d3, d4, arr9, arr10, bmb8, bz5);
			}
		} else {
			return a;
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			if (this.c != null) {
				jsonObject2.addProperty("item", gl.am.b(this.c).toString());
			}

			if (this.b != null) {
				jsonObject2.addProperty("tag", adb.e().b().b(this.b).toString());
			}

			jsonObject2.add("count", this.d.d());
			jsonObject2.add("durability", this.e.d());
			jsonObject2.add("nbt", this.i.a());
			if (this.f.length > 0) {
				JsonArray jsonArray3 = new JsonArray();

				for (az az7 : this.f) {
					jsonArray3.add(az7.a());
				}

				jsonObject2.add("enchantments", jsonArray3);
			}

			if (this.g.length > 0) {
				JsonArray jsonArray3 = new JsonArray();

				for (az az7 : this.g) {
					jsonArray3.add(az7.a());
				}

				jsonObject2.add("stored_enchantments", jsonArray3);
			}

			if (this.h != null) {
				jsonObject2.addProperty("potion", gl.an.b(this.h).toString());
			}

			return jsonObject2;
		}
	}

	public static bo[] b(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonArray jsonArray2 = adt.n(jsonElement, "items");
			bo[] arr3 = new bo[jsonArray2.size()];

			for (int integer4 = 0; integer4 < arr3.length; integer4++) {
				arr3[integer4] = a(jsonArray2.get(integer4));
			}

			return arr3;
		} else {
			return new bo[0];
		}
	}

	public static class a {
		private final List<az> a = Lists.<az>newArrayList();
		private final List<az> b = Lists.<az>newArrayList();
		@Nullable
		private bke c;
		@Nullable
		private adf<bke> d;
		private bx.d e;
		private bx.d f;
		@Nullable
		private bmb g;
		private bz h;

		private a() {
			this.e = bx.d.e;
			this.f = bx.d.e;
			this.h = bz.a;
		}

		public static bo.a a() {
			return new bo.a();
		}

		public bo.a a(bqa bqa) {
			this.c = bqa.h();
			return this;
		}

		public bo.a a(adf<bke> adf) {
			this.d = adf;
			return this;
		}

		public bo.a a(le le) {
			this.h = new bz(le);
			return this;
		}

		public bo.a a(az az) {
			this.a.add(az);
			return this;
		}

		public bo b() {
			return new bo(this.d, this.c, this.e, this.f, (az[])this.a.toArray(az.b), (az[])this.b.toArray(az.b), this.g, this.h);
		}
	}
}
