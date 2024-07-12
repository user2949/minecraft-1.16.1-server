import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class at {
	public static final at a = at.a.a().b();
	private final bx.c b;
	private final bx.c c;
	private final be d;
	private final Boolean e;
	private final au f;

	public at() {
		this.b = bx.c.e;
		this.c = bx.c.e;
		this.d = be.a;
		this.e = null;
		this.f = au.a;
	}

	public at(bx.c c1, bx.c c2, be be, @Nullable Boolean boolean4, au au) {
		this.b = c1;
		this.c = c2;
		this.d = be;
		this.e = boolean4;
		this.f = au;
	}

	public boolean a(ze ze, anw anw, float float3, float float4, boolean boolean5) {
		if (this == a) {
			return true;
		} else if (!this.b.d(float3)) {
			return false;
		} else if (!this.c.d(float4)) {
			return false;
		} else if (!this.d.a(ze, anw.k())) {
			return false;
		} else {
			return this.e != null && this.e != boolean5 ? false : this.f.a(ze, anw);
		}
	}

	public static at a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "damage");
			bx.c c3 = bx.c.a(jsonObject2.get("dealt"));
			bx.c c4 = bx.c.a(jsonObject2.get("taken"));
			Boolean boolean5 = jsonObject2.has("blocked") ? adt.j(jsonObject2, "blocked") : null;
			be be6 = be.a(jsonObject2.get("source_entity"));
			au au7 = au.a(jsonObject2.get("type"));
			return new at(c3, c4, be6, boolean5, au7);
		} else {
			return a;
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.add("dealt", this.b.d());
			jsonObject2.add("taken", this.c.d());
			jsonObject2.add("source_entity", this.d.a());
			jsonObject2.add("type", this.f.a());
			if (this.e != null) {
				jsonObject2.addProperty("blocked", this.e);
			}

			return jsonObject2;
		}
	}

	public static class a {
		private bx.c a = bx.c.e;
		private bx.c b = bx.c.e;
		private be c = be.a;
		private Boolean d;
		private au e = au.a;

		public static at.a a() {
			return new at.a();
		}

		public at.a a(Boolean boolean1) {
			this.d = boolean1;
			return this;
		}

		public at.a a(au.a a) {
			this.e = a.b();
			return this;
		}

		public at b() {
			return new at(this.a, this.b, this.c, this.d, this.e);
		}
	}
}
