import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class bj {
	public static final bj a = new bj(null, null, ck.a);
	@Nullable
	private final adf<cwz> b;
	@Nullable
	private final cwz c;
	private final ck d;

	public bj(@Nullable adf<cwz> adf, @Nullable cwz cwz, ck ck) {
		this.b = adf;
		this.c = cwz;
		this.d = ck;
	}

	public boolean a(zd zd, fu fu) {
		if (this == a) {
			return true;
		} else if (!zd.p(fu)) {
			return false;
		} else {
			cxa cxa4 = zd.b(fu);
			cwz cwz5 = cxa4.a();
			if (this.b != null && !this.b.a(cwz5)) {
				return false;
			} else {
				return this.c != null && cwz5 != this.c ? false : this.d.a(cxa4);
			}
		}
	}

	public static bj a(@Nullable JsonElement jsonElement) {
		if (jsonElement != null && !jsonElement.isJsonNull()) {
			JsonObject jsonObject2 = adt.m(jsonElement, "fluid");
			cwz cwz3 = null;
			if (jsonObject2.has("fluid")) {
				uh uh4 = new uh(adt.h(jsonObject2, "fluid"));
				cwz3 = gl.ah.a(uh4);
			}

			adf<cwz> adf4 = null;
			if (jsonObject2.has("tag")) {
				uh uh5 = new uh(adt.h(jsonObject2, "tag"));
				adf4 = adb.e().c().a(uh5);
				if (adf4 == null) {
					throw new JsonSyntaxException("Unknown fluid tag '" + uh5 + "'");
				}
			}

			ck ck5 = ck.a(jsonObject2.get("state"));
			return new bj(adf4, cwz3, ck5);
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
				jsonObject2.addProperty("fluid", gl.ah.b(this.c).toString());
			}

			if (this.b != null) {
				jsonObject2.addProperty("tag", adb.e().c().b(this.b).toString());
			}

			jsonObject2.add("state", this.d.a());
			return jsonObject2;
		}
	}
}
