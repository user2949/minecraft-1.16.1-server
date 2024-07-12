import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class dcn extends dcg {
	private final uh a;
	private final long b;

	private dcn(ddm[] arr, uh uh, long long3) {
		super(arr);
		this.a = uh;
		this.b = long3;
	}

	@Override
	public dci b() {
		return dcj.q;
	}

	@Override
	public bki a(bki bki, dat dat) {
		if (bki.a()) {
			return bki;
		} else {
			le le4 = new le();
			le4.a("LootTable", this.a.toString());
			if (this.b != 0L) {
				le4.a("LootTableSeed", this.b);
			}

			bki.p().a("BlockEntityTag", le4);
			return bki;
		}
	}

	@Override
	public void a(dbe dbe) {
		if (dbe.a(this.a)) {
			dbe.a("Table " + this.a + " is recursively called");
		} else {
			super.a(dbe);
			daw daw3 = dbe.c(this.a);
			if (daw3 == null) {
				dbe.a("Unknown loot table called " + this.a);
			} else {
				daw3.a(dbe.a("->{" + this.a + "}", this.a));
			}
		}
	}

	public static class a extends dcg.c<dcn> {
		public void a(JsonObject jsonObject, dcn dcn, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcn, jsonSerializationContext);
			jsonObject.addProperty("name", dcn.a.toString());
			if (dcn.b != 0L) {
				jsonObject.addProperty("seed", dcn.b);
			}
		}

		public dcn b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			uh uh5 = new uh(adt.h(jsonObject, "name"));
			long long6 = adt.a(jsonObject, "seed", 0L);
			return new dcn(arr, uh5, long6);
		}
	}
}
