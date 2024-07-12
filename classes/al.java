import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class al {
	public static final al a = new al(null, null, ck.a, bz.a);
	@Nullable
	private final adf<bvr> b;
	@Nullable
	private final bvr c;
	private final ck d;
	private final bz e;

	public al(@Nullable adf<bvr> adf, @Nullable bvr bvr, ck ck, bz bz) {
		this.b = adf;
		this.c = bvr;
		this.d = ck;
		this.e = bz;
	}

	public boolean a(zd zd, fu fu) {
		if (this == a) {
			return true;
		} else if (!zd.p(fu)) {
			return false;
		} else {
			cfj cfj4 = zd.d_(fu);
			bvr bvr5 = cfj4.b();
			if (this.b != null && !this.b.a(bvr5)) {
				return false;
			} else if (this.c != null && bvr5 != this.c) {
				return false;
			} else if (!this.d.a(cfj4)) {
				return false;
			} else {
				if (this.e != bz.a) {
					cdl cdl6 = zd.c(fu);
					if (cdl6 == null || !this.e.a(cdl6.a(new le()))) {
						return false;
					}
				}

				return true;
			}
		}
	}

	public static al a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "block");
			bz bz3 = bz.a(jsonObject2.get("nbt"));
			bvr bvr4 = null;
			if (jsonObject2.has("block")) {
				uh uh5 = new uh(adt.h(jsonObject2, "block"));
				bvr4 = gl.aj.a(uh5);
			}

			adf<bvr> adf5 = null;
			if (jsonObject2.has("tag")) {
				uh uh6 = new uh(adt.h(jsonObject2, "tag"));
				adf5 = adb.e().a().a(uh6);
				if (adf5 == null) {
					throw new JsonSyntaxException("Unknown block tag '" + uh6 + "'");
				}
			}

			ck ck6 = ck.a(jsonObject2.get("state"));
			return new al(adf5, bvr4, ck6, bz3);
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
				jsonObject2.addProperty("block", gl.aj.b(this.c).toString());
			}

			if (this.b != null) {
				jsonObject2.addProperty("tag", adb.e().a().b(this.b).toString());
			}

			jsonObject2.add("nbt", this.e.a());
			jsonObject2.add("state", this.d.a());
			return jsonObject2;
		}
	}

	public static class a {
		@Nullable
		private bvr a;
		@Nullable
		private adf<bvr> b;
		private ck c = ck.a;
		private bz d = bz.a;

		private a() {
		}

		public static al.a a() {
			return new al.a();
		}

		public al.a a(bvr bvr) {
			this.a = bvr;
			return this;
		}

		public al.a a(adf<bvr> adf) {
			this.b = adf;
			return this;
		}

		public al.a a(ck ck) {
			this.c = ck;
			return this;
		}

		public al b() {
			return new al(this.b, this.a, this.c, this.d);
		}
	}
}
