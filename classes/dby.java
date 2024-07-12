import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class dby extends dcg {
	private final dby.a a;

	private dby(ddm[] arr, dby.a a) {
		super(arr);
		this.a = a;
	}

	@Override
	public dci b() {
		return dcj.m;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(this.a.f);
	}

	@Override
	public bki a(bki bki, dat dat) {
		Object object4 = dat.c(this.a.f);
		if (object4 instanceof ank) {
			ank ank5 = (ank)object4;
			if (ank5.Q()) {
				bki.a(ank5.d());
			}
		}

		return bki;
	}

	public static dcg.a<?> a(dby.a a) {
		return a(arr -> new dby(arr, a));
	}

	public static enum a {
		THIS("this", dda.a),
		KILLER("killer", dda.d),
		KILLER_PLAYER("killer_player", dda.b),
		BLOCK_ENTITY("block_entity", dda.i);

		public final String e;
		public final dcx<?> f;

		private a(String string3, dcx<?> dcx) {
			this.e = string3;
			this.f = dcx;
		}

		public static dby.a a(String string) {
			for (dby.a a5 : values()) {
				if (a5.e.equals(string)) {
					return a5;
				}
			}

			throw new IllegalArgumentException("Invalid name source " + string);
		}
	}

	public static class b extends dcg.c<dby> {
		public void a(JsonObject jsonObject, dby dby, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dby, jsonSerializationContext);
			jsonObject.addProperty("source", dby.a.e);
		}

		public dby b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			dby.a a5 = dby.a.a(adt.h(jsonObject, "source"));
			return new dby(arr, a5);
		}
	}
}
