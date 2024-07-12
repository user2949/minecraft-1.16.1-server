import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class ddg implements ddm {
	private final au a;

	private ddg(au au) {
		this.a = au;
	}

	@Override
	public ddn b() {
		return ddo.l;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.f, dda.c);
	}

	public boolean test(dat dat) {
		anw anw3 = dat.c(dda.c);
		fu fu4 = dat.c(dda.f);
		return fu4 != null && anw3 != null && this.a.a(dat.c(), dem.b(fu4), anw3);
	}

	public static ddm.a a(au.a a) {
		return () -> new ddg(a.b());
	}

	public static class a implements dbc<ddg> {
		public void a(JsonObject jsonObject, ddg ddg, JsonSerializationContext jsonSerializationContext) {
			jsonObject.add("predicate", ddg.a.a());
		}

		public ddg a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			au au4 = au.a(jsonObject.get("predicate"));
			return new ddg(au4);
		}
	}
}
