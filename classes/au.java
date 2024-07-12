import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class au {
	public static final au a = au.a.a().b();
	private final Boolean b;
	private final Boolean c;
	private final Boolean d;
	private final Boolean e;
	private final Boolean f;
	private final Boolean g;
	private final Boolean h;
	private final Boolean i;
	private final be j;
	private final be k;

	public au(
		@Nullable Boolean boolean1,
		@Nullable Boolean boolean2,
		@Nullable Boolean boolean3,
		@Nullable Boolean boolean4,
		@Nullable Boolean boolean5,
		@Nullable Boolean boolean6,
		@Nullable Boolean boolean7,
		@Nullable Boolean boolean8,
		be be9,
		be be10
	) {
		this.b = boolean1;
		this.c = boolean2;
		this.d = boolean3;
		this.e = boolean4;
		this.f = boolean5;
		this.g = boolean6;
		this.h = boolean7;
		this.i = boolean8;
		this.j = be9;
		this.k = be10;
	}

	public boolean a(ze ze, anw anw) {
		return this.a(ze.u(), ze.cz(), anw);
	}

	public boolean a(zd zd, dem dem, anw anw) {
		if (this == a) {
			return true;
		} else if (this.b != null && this.b != anw.b()) {
			return false;
		} else if (this.c != null && this.c != anw.d()) {
			return false;
		} else if (this.d != null && this.d != anw.f()) {
			return false;
		} else if (this.e != null && this.e != anw.h()) {
			return false;
		} else if (this.f != null && this.f != anw.i()) {
			return false;
		} else if (this.g != null && this.g != anw.p()) {
			return false;
		} else if (this.h != null && this.h != anw.t()) {
			return false;
		} else if (this.i != null && this.i != (anw == anw.b)) {
			return false;
		} else {
			return !this.j.a(zd, dem, anw.j()) ? false : this.k.a(zd, dem, anw.k());
		}
	}

	public static au a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "damage type");
			Boolean boolean3 = a(jsonObject2, "is_projectile");
			Boolean boolean4 = a(jsonObject2, "is_explosion");
			Boolean boolean5 = a(jsonObject2, "bypasses_armor");
			Boolean boolean6 = a(jsonObject2, "bypasses_invulnerability");
			Boolean boolean7 = a(jsonObject2, "bypasses_magic");
			Boolean boolean8 = a(jsonObject2, "is_fire");
			Boolean boolean9 = a(jsonObject2, "is_magic");
			Boolean boolean10 = a(jsonObject2, "is_lightning");
			be be11 = be.a(jsonObject2.get("direct_entity"));
			be be12 = be.a(jsonObject2.get("source_entity"));
			return new au(boolean3, boolean4, boolean5, boolean6, boolean7, boolean8, boolean9, boolean10, be11, be12);
		} else {
			return a;
		}
	}

	@Nullable
	private static Boolean a(JsonObject jsonObject, String string) {
		return jsonObject.has(string) ? adt.j(jsonObject, string) : null;
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			this.a(jsonObject2, "is_projectile", this.b);
			this.a(jsonObject2, "is_explosion", this.c);
			this.a(jsonObject2, "bypasses_armor", this.d);
			this.a(jsonObject2, "bypasses_invulnerability", this.e);
			this.a(jsonObject2, "bypasses_magic", this.f);
			this.a(jsonObject2, "is_fire", this.g);
			this.a(jsonObject2, "is_magic", this.h);
			this.a(jsonObject2, "is_lightning", this.i);
			jsonObject2.add("direct_entity", this.j.a());
			jsonObject2.add("source_entity", this.k.a());
			return jsonObject2;
		}
	}

	private void a(JsonObject jsonObject, String string, @Nullable Boolean boolean3) {
		if (boolean3 != null) {
			jsonObject.addProperty(string, boolean3);
		}
	}

	public static class a {
		private Boolean a;
		private Boolean b;
		private Boolean c;
		private Boolean d;
		private Boolean e;
		private Boolean f;
		private Boolean g;
		private Boolean h;
		private be i = be.a;
		private be j = be.a;

		public static au.a a() {
			return new au.a();
		}

		public au.a a(Boolean boolean1) {
			this.a = boolean1;
			return this;
		}

		public au.a h(Boolean boolean1) {
			this.h = boolean1;
			return this;
		}

		public au.a a(be.a a) {
			this.i = a.b();
			return this;
		}

		public au b() {
			return new au(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
		}
	}
}
