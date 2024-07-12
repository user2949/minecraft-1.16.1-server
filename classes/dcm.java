import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Arrays;
import java.util.List;

public class dcm extends dcg {
	private final List<dbo> a;

	private dcm(ddm[] arr, List<dbo> list) {
		super(arr);
		this.a = ImmutableList.copyOf(list);
	}

	@Override
	public dci b() {
		return dcj.n;
	}

	@Override
	public bki a(bki bki, dat dat) {
		if (bki.a()) {
			return bki;
		} else {
			gi<bki> gi4 = gi.a();
			this.a.forEach(dbo -> dbo.expand(dat, dbn -> dbn.a(daw.a(gi4::add), dat)));
			le le5 = new le();
			ana.a(le5, gi4);
			le le6 = bki.p();
			le6.a("BlockEntityTag", le5.a(le6.p("BlockEntityTag")));
			return bki;
		}
	}

	@Override
	public void a(dbe dbe) {
		super.a(dbe);

		for (int integer3 = 0; integer3 < this.a.size(); integer3++) {
			((dbo)this.a.get(integer3)).a(dbe.b(".entry[" + integer3 + "]"));
		}
	}

	public static dcm.a c() {
		return new dcm.a();
	}

	public static class a extends dcg.a<dcm.a> {
		private final List<dbo> a = Lists.<dbo>newArrayList();

		protected dcm.a d() {
			return this;
		}

		public dcm.a a(dbo.a<?> a) {
			this.a.add(a.b());
			return this;
		}

		@Override
		public dch b() {
			return new dcm(this.g(), this.a);
		}
	}

	public static class b extends dcg.c<dcm> {
		public void a(JsonObject jsonObject, dcm dcm, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcm, jsonSerializationContext);
			jsonObject.add("entries", jsonSerializationContext.serialize(dcm.a));
		}

		public dcm b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			dbo[] arr5 = adt.a(jsonObject, "entries", jsonDeserializationContext, dbo[].class);
			return new dcm(arr, Arrays.asList(arr5));
		}
	}
}
