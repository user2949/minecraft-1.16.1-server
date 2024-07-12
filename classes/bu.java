import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bu {
	private static final Logger b = LogManager.getLogger();
	public static final bu a = new bu(bx.c.e, bx.c.e, bx.c.e, null, null, null, null, bt.a, al.a, bj.a);
	private final bx.c c;
	private final bx.c d;
	private final bx.c e;
	@Nullable
	private final bre f;
	@Nullable
	private final cml<?> g;
	@Nullable
	private final ug<bqb> h;
	@Nullable
	private final Boolean i;
	private final bt j;
	private final al k;
	private final bj l;

	public bu(bx.c c1, bx.c c2, bx.c c3, @Nullable bre bre, @Nullable cml<?> cml, @Nullable ug<bqb> ug, @Nullable Boolean boolean7, bt bt, al al, bj bj) {
		this.c = c1;
		this.d = c2;
		this.e = c3;
		this.f = bre;
		this.g = cml;
		this.h = ug;
		this.i = boolean7;
		this.j = bt;
		this.k = al;
		this.l = bj;
	}

	public static bu a(bre bre) {
		return new bu(bx.c.e, bx.c.e, bx.c.e, bre, null, null, null, bt.a, al.a, bj.a);
	}

	public static bu a(ug<bqb> ug) {
		return new bu(bx.c.e, bx.c.e, bx.c.e, null, null, ug, null, bt.a, al.a, bj.a);
	}

	public static bu a(cml<?> cml) {
		return new bu(bx.c.e, bx.c.e, bx.c.e, null, cml, null, null, bt.a, al.a, bj.a);
	}

	public boolean a(zd zd, double double2, double double3, double double4) {
		return this.a(zd, (float)double2, (float)double3, (float)double4);
	}

	public boolean a(zd zd, float float2, float float3, float float4) {
		if (!this.c.d(float2)) {
			return false;
		} else if (!this.d.d(float3)) {
			return false;
		} else if (!this.e.d(float4)) {
			return false;
		} else if (this.h != null && this.h != zd.W()) {
			return false;
		} else {
			fu fu6 = new fu((double)float2, (double)float3, (double)float4);
			boolean boolean7 = zd.p(fu6);
			if (this.f == null || boolean7 && this.f == zd.v(fu6)) {
				if (this.g == null || boolean7 && zd.a().a(fu6, true, this.g).e()) {
					if (this.i == null || boolean7 && this.i == bwb.a(zd, fu6)) {
						if (!this.j.a(zd, fu6)) {
							return false;
						} else {
							return !this.k.a(zd, fu6) ? false : this.l.a(zd, fu6);
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			if (!this.c.c() || !this.d.c() || !this.e.c()) {
				JsonObject jsonObject3 = new JsonObject();
				jsonObject3.add("x", this.c.d());
				jsonObject3.add("y", this.d.d());
				jsonObject3.add("z", this.e.d());
				jsonObject2.add("position", jsonObject3);
			}

			if (this.h != null) {
				bqb.f.encodeStart(JsonOps.INSTANCE, this.h).resultOrPartial(b::error).ifPresent(jsonElement -> jsonObject2.add("dimension", jsonElement));
			}

			if (this.g != null) {
				jsonObject2.addProperty("feature", this.g.i());
			}

			if (this.f != null) {
				jsonObject2.addProperty("biome", gl.as.b(this.f).toString());
			}

			if (this.i != null) {
				jsonObject2.addProperty("smokey", this.i);
			}

			jsonObject2.add("light", this.j.a());
			jsonObject2.add("block", this.k.a());
			jsonObject2.add("fluid", this.l.a());
			return jsonObject2;
		}
	}

	public static bu a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "location");
			JsonObject jsonObject3 = adt.a(jsonObject2, "position", new JsonObject());
			bx.c c4 = bx.c.a(jsonObject3.get("x"));
			bx.c c5 = bx.c.a(jsonObject3.get("y"));
			bx.c c6 = bx.c.a(jsonObject3.get("z"));
			ug<bqb> ug7 = jsonObject2.has("dimension")
				? (ug)uh.a.parse(JsonOps.INSTANCE, jsonObject2.get("dimension")).resultOrPartial(b::error).map(uh -> ug.a(gl.ae, uh)).orElse(null)
				: null;
			cml<?> cml8 = jsonObject2.has("feature") ? (cml)cml.a.get(adt.h(jsonObject2, "feature")) : null;
			bre bre9 = null;
			if (jsonObject2.has("biome")) {
				uh uh10 = new uh(adt.h(jsonObject2, "biome"));
				bre9 = (bre)gl.as.b(uh10).orElseThrow(() -> new JsonSyntaxException("Unknown biome '" + uh10 + "'"));
			}

			Boolean boolean10 = jsonObject2.has("smokey") ? jsonObject2.get("smokey").getAsBoolean() : null;
			bt bt11 = bt.a(jsonObject2.get("light"));
			al al12 = al.a(jsonObject2.get("block"));
			bj bj13 = bj.a(jsonObject2.get("fluid"));
			return new bu(c4, c5, c6, bre9, cml8, ug7, boolean10, bt11, al12, bj13);
		} else {
			return a;
		}
	}

	public static class a {
		private bx.c a = bx.c.e;
		private bx.c b = bx.c.e;
		private bx.c c = bx.c.e;
		@Nullable
		private bre d;
		@Nullable
		private cml<?> e;
		@Nullable
		private ug<bqb> f;
		@Nullable
		private Boolean g;
		private bt h = bt.a;
		private al i = al.a;
		private bj j = bj.a;

		public static bu.a a() {
			return new bu.a();
		}

		public bu.a a(@Nullable bre bre) {
			this.d = bre;
			return this;
		}

		public bu.a a(al al) {
			this.i = al;
			return this;
		}

		public bu.a a(Boolean boolean1) {
			this.g = boolean1;
			return this;
		}

		public bu b() {
			return new bu(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
		}
	}
}
