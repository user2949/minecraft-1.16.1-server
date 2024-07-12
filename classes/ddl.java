import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Set;

public class ddl implements ddm {
	private final bvr a;
	private final ck b;

	private ddl(bvr bvr, ck ck) {
		this.a = bvr;
		this.b = ck;
	}

	@Override
	public ddn b() {
		return ddo.h;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.h);
	}

	public boolean test(dat dat) {
		cfj cfj3 = dat.c(dda.h);
		return cfj3 != null && this.a == cfj3.b() && this.b.a(cfj3);
	}

	public static ddl.a a(bvr bvr) {
		return new ddl.a(bvr);
	}

	public static class a implements ddm.a {
		private final bvr a;
		private ck b = ck.a;

		public a(bvr bvr) {
			this.a = bvr;
		}

		public ddl.a a(ck.a a) {
			this.b = a.b();
			return this;
		}

		@Override
		public ddm build() {
			return new ddl(this.a, this.b);
		}
	}

	public static class b implements dbc<ddl> {
		public void a(JsonObject jsonObject, ddl ddl, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("block", gl.aj.b(ddl.a).toString());
			jsonObject.add("properties", ddl.b.a());
		}

		public ddl a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			uh uh4 = new uh(adt.h(jsonObject, "block"));
			bvr bvr5 = (bvr)gl.aj.b(uh4).orElseThrow(() -> new IllegalArgumentException("Can't find block " + uh4));
			ck ck6 = ck.a(jsonObject.get("properties"));
			ck6.a(bvr5.m(), string -> {
				throw new JsonSyntaxException("Block " + bvr5 + " has no property " + string);
			});
			return new ddl(bvr5, ck6);
		}
	}
}
