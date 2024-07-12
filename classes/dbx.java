import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dbx extends dcg {
	private final bvr a;
	private final Set<cgl<?>> b;

	private dbx(ddm[] arr, bvr bvr, Set<cgl<?>> set) {
		super(arr);
		this.a = bvr;
		this.b = set;
	}

	@Override
	public dci b() {
		return dcj.v;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.h);
	}

	@Override
	protected bki a(bki bki, dat dat) {
		cfj cfj4 = dat.c(dda.h);
		if (cfj4 != null) {
			le le5 = bki.p();
			le le6;
			if (le5.c("BlockStateTag", 10)) {
				le6 = le5.p("BlockStateTag");
			} else {
				le6 = new le();
				le5.a("BlockStateTag", le6);
			}

			this.b.stream().filter(cfj4::b).forEach(cgl -> le6.a(cgl.f(), a(cfj4, cgl)));
		}

		return bki;
	}

	public static dbx.a a(bvr bvr) {
		return new dbx.a(bvr);
	}

	private static <T extends Comparable<T>> String a(cfj cfj, cgl<T> cgl) {
		T comparable3 = cfj.c(cgl);
		return cgl.a(comparable3);
	}

	public static class a extends dcg.a<dbx.a> {
		private final bvr a;
		private final Set<cgl<?>> b = Sets.<cgl<?>>newHashSet();

		private a(bvr bvr) {
			this.a = bvr;
		}

		public dbx.a a(cgl<?> cgl) {
			if (!this.a.m().d().contains(cgl)) {
				throw new IllegalStateException("Property " + cgl + " is not present on block " + this.a);
			} else {
				this.b.add(cgl);
				return this;
			}
		}

		protected dbx.a d() {
			return this;
		}

		@Override
		public dch b() {
			return new dbx(this.g(), this.a, this.b);
		}
	}

	public static class b extends dcg.c<dbx> {
		public void a(JsonObject jsonObject, dbx dbx, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dbx, jsonSerializationContext);
			jsonObject.addProperty("block", gl.aj.b(dbx.a).toString());
			JsonArray jsonArray5 = new JsonArray();
			dbx.b.forEach(cgl -> jsonArray5.add(cgl.f()));
			jsonObject.add("properties", jsonArray5);
		}

		public dbx b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			uh uh5 = new uh(adt.h(jsonObject, "block"));
			bvr bvr6 = (bvr)gl.aj.b(uh5).orElseThrow(() -> new IllegalArgumentException("Can't find block " + uh5));
			cfk<bvr, cfj> cfk7 = bvr6.m();
			Set<cgl<?>> set8 = Sets.<cgl<?>>newHashSet();
			JsonArray jsonArray9 = adt.a(jsonObject, "properties", null);
			if (jsonArray9 != null) {
				jsonArray9.forEach(jsonElement -> set8.add(cfk7.a(adt.a(jsonElement, "property"))));
			}

			return new dbx(arr, bvr6, set8);
		}
	}
}
