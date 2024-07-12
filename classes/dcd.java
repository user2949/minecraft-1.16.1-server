import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.authlib.GameProfile;
import java.util.Set;

public class dcd extends dcg {
	private final dat.c a;

	public dcd(ddm[] arr, dat.c c) {
		super(arr);
		this.a = c;
	}

	@Override
	public dci b() {
		return dcj.t;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(this.a.a());
	}

	@Override
	public bki a(bki bki, dat dat) {
		if (bki.b() == bkk.pf) {
			aom aom4 = dat.c(this.a.a());
			if (aom4 instanceof bec) {
				GameProfile gameProfile5 = ((bec)aom4).ez();
				bki.p().a("SkullOwner", lq.a(new le(), gameProfile5));
			}
		}

		return bki;
	}

	public static class a extends dcg.c<dcd> {
		public void a(JsonObject jsonObject, dcd dcd, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcd, jsonSerializationContext);
			jsonObject.add("entity", jsonSerializationContext.serialize(dcd.a));
		}

		public dcd b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			dat.c c5 = adt.a(jsonObject, "entity", jsonDeserializationContext, dat.c.class);
			return new dcd(arr, c5);
		}
	}
}
