import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class bb {
	public static final bb a = new bb(bo.a, bo.a, bo.a, bo.a, bo.a, bo.a);
	public static final bb b = new bb(bo.a.a().a(bkk.pL).a(bfh.s().o()).b(), bo.a, bo.a, bo.a, bo.a, bo.a);
	private final bo c;
	private final bo d;
	private final bo e;
	private final bo f;
	private final bo g;
	private final bo h;

	public bb(bo bo1, bo bo2, bo bo3, bo bo4, bo bo5, bo bo6) {
		this.c = bo1;
		this.d = bo2;
		this.e = bo3;
		this.f = bo4;
		this.g = bo5;
		this.h = bo6;
	}

	public boolean a(@Nullable aom aom) {
		if (this == a) {
			return true;
		} else if (!(aom instanceof aoy)) {
			return false;
		} else {
			aoy aoy3 = (aoy)aom;
			if (!this.c.a(aoy3.b(aor.HEAD))) {
				return false;
			} else if (!this.d.a(aoy3.b(aor.CHEST))) {
				return false;
			} else if (!this.e.a(aoy3.b(aor.LEGS))) {
				return false;
			} else if (!this.f.a(aoy3.b(aor.FEET))) {
				return false;
			} else {
				return !this.g.a(aoy3.b(aor.MAINHAND)) ? false : this.h.a(aoy3.b(aor.OFFHAND));
			}
		}
	}

	public static bb a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "equipment");
			bo bo3 = bo.a(jsonObject2.get("head"));
			bo bo4 = bo.a(jsonObject2.get("chest"));
			bo bo5 = bo.a(jsonObject2.get("legs"));
			bo bo6 = bo.a(jsonObject2.get("feet"));
			bo bo7 = bo.a(jsonObject2.get("mainhand"));
			bo bo8 = bo.a(jsonObject2.get("offhand"));
			return new bb(bo3, bo4, bo5, bo6, bo7, bo8);
		} else {
			return a;
		}
	}

	public JsonElement a() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.add("head", this.c.a());
			jsonObject2.add("chest", this.d.a());
			jsonObject2.add("legs", this.e.a());
			jsonObject2.add("feet", this.f.a());
			jsonObject2.add("mainhand", this.g.a());
			jsonObject2.add("offhand", this.h.a());
			return jsonObject2;
		}
	}

	public static class a {
		private bo a;
		private bo b;
		private bo c;
		private bo d;
		private bo e;
		private bo f;

		public a() {
			this.a = bo.a;
			this.b = bo.a;
			this.c = bo.a;
			this.d = bo.a;
			this.e = bo.a;
			this.f = bo.a;
		}

		public static bb.a a() {
			return new bb.a();
		}

		public bb.a a(bo bo) {
			this.a = bo;
			return this;
		}

		public bb.a b(bo bo) {
			this.b = bo;
			return this;
		}

		public bb.a c(bo bo) {
			this.c = bo;
			return this;
		}

		public bb.a d(bo bo) {
			this.d = bo;
			return this;
		}

		public bb b() {
			return new bb(this.a, this.b, this.c, this.d, this.e, this.f);
		}
	}
}
