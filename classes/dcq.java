import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;

public class dcq extends dcg {
	private final boolean a;
	private final List<mr> b;
	@Nullable
	private final dat.c d;

	public dcq(ddm[] arr, boolean boolean2, List<mr> list, @Nullable dat.c c) {
		super(arr);
		this.a = boolean2;
		this.b = ImmutableList.copyOf(list);
		this.d = c;
	}

	@Override
	public dci b() {
		return dcj.s;
	}

	@Override
	public Set<dcx<?>> a() {
		return this.d != null ? ImmutableSet.of(this.d.a()) : ImmutableSet.of();
	}

	@Override
	public bki a(bki bki, dat dat) {
		lk lk4 = this.a(bki, !this.b.isEmpty());
		if (lk4 != null) {
			if (this.a) {
				lk4.clear();
			}

			UnaryOperator<mr> unaryOperator5 = dcr.a(dat, this.d);
			this.b.stream().map(unaryOperator5).map(mr.a::a).map(lt::a).forEach(lk4::add);
		}

		return bki;
	}

	@Nullable
	private lk a(bki bki, boolean boolean2) {
		le le4;
		if (bki.n()) {
			le4 = bki.o();
		} else {
			if (!boolean2) {
				return null;
			}

			le4 = new le();
			bki.c(le4);
		}

		le le5;
		if (le4.c("display", 10)) {
			le5 = le4.p("display");
		} else {
			if (!boolean2) {
				return null;
			}

			le5 = new le();
			le4.a("display", le5);
		}

		if (le5.c("Lore", 9)) {
			return le5.d("Lore", 8);
		} else if (boolean2) {
			lk lk6 = new lk();
			le5.a("Lore", lk6);
			return lk6;
		} else {
			return null;
		}
	}

	public static class b extends dcg.c<dcq> {
		public void a(JsonObject jsonObject, dcq dcq, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcq, jsonSerializationContext);
			jsonObject.addProperty("replace", dcq.a);
			JsonArray jsonArray5 = new JsonArray();

			for (mr mr7 : dcq.b) {
				jsonArray5.add(mr.a.b(mr7));
			}

			jsonObject.add("lore", jsonArray5);
			if (dcq.d != null) {
				jsonObject.add("entity", jsonSerializationContext.serialize(dcq.d));
			}
		}

		public dcq b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			boolean boolean5 = adt.a(jsonObject, "replace", false);
			List<mr> list6 = (List<mr>)Streams.stream(adt.u(jsonObject, "lore")).map(mr.a::a).collect(ImmutableList.toImmutableList());
			dat.c c7 = adt.a(jsonObject, "entity", null, jsonDeserializationContext, dat.c.class);
			return new dcq(arr, boolean5, list6, c7);
		}
	}
}
