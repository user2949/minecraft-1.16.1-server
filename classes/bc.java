import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class bc {
	public static final bc a = new bc.a().b();
	@Nullable
	private final Boolean b;
	@Nullable
	private final Boolean c;
	@Nullable
	private final Boolean d;
	@Nullable
	private final Boolean e;
	@Nullable
	private final Boolean f;

	public bc(@Nullable Boolean boolean1, @Nullable Boolean boolean2, @Nullable Boolean boolean3, @Nullable Boolean boolean4, @Nullable Boolean boolean5) {
		this.b = boolean1;
		this.c = boolean2;
		this.d = boolean3;
		this.e = boolean4;
		this.f = boolean5;
	}

	public boolean a(aom aom) {
		if (this.b != null && aom.bm() != this.b) {
			return false;
		} else if (this.c != null && aom.bv() != this.c) {
			return false;
		} else if (this.d != null && aom.bw() != this.d) {
			return false;
		} else {
			return this.e != null && aom.bx() != this.e ? false : this.f == null || !(aom instanceof aoy) || ((aoy)aom).x_() == this.f;
		}
	}

	@Nullable
	private static Boolean a(JsonObject jsonObject, String string) {
		return jsonObject.has(string) ? adt.j(jsonObject, string) : null;
	}

	public static bc a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "entity flags");
			Boolean boolean3 = a(jsonObject2, "is_on_fire");
			Boolean boolean4 = a(jsonObject2, "is_sneaking");
			Boolean boolean5 = a(jsonObject2, "is_sprinting");
			Boolean boolean6 = a(jsonObject2, "is_swimming");
			Boolean boolean7 = a(jsonObject2, "is_baby");
			return new bc(boolean3, boolean4, boolean5, boolean6, boolean7);
		} else {
			return a;
		}
	}

	private void a(JsonObject jsonObject, String string, @Nullable Boolean boolean3) {
		if (boolean3 != null) {
			jsonObject.addProperty(string, boolean3);
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			this.a(jsonObject2, "is_on_fire", this.b);
			this.a(jsonObject2, "is_sneaking", this.c);
			this.a(jsonObject2, "is_sprinting", this.d);
			this.a(jsonObject2, "is_swimming", this.e);
			this.a(jsonObject2, "is_baby", this.f);
			return jsonObject2;
		}
	}

	public static class a {
		@Nullable
		private Boolean a;
		@Nullable
		private Boolean b;
		@Nullable
		private Boolean c;
		@Nullable
		private Boolean d;
		@Nullable
		private Boolean e;

		public static bc.a a() {
			return new bc.a();
		}

		public bc.a a(@Nullable Boolean boolean1) {
			this.a = boolean1;
			return this;
		}

		public bc.a e(@Nullable Boolean boolean1) {
			this.e = boolean1;
			return this;
		}

		public bc b() {
			return new bc(this.a, this.b, this.c, this.d, this.e);
		}
	}
}
