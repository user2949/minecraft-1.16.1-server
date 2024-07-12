import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class dbv extends dcg {
	private static final Map<uh, dbv.c> a = Maps.<uh, dbv.c>newHashMap();
	private final bnw b;
	private final dbv.b d;

	private dbv(ddm[] arr, bnw bnw, dbv.b b) {
		super(arr);
		this.b = bnw;
		this.d = b;
	}

	@Override
	public dci b() {
		return dcj.p;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.j);
	}

	@Override
	public bki a(bki bki, dat dat) {
		bki bki4 = dat.c(dda.j);
		if (bki4 != null) {
			int integer5 = bny.a(this.b, bki4);
			int integer6 = this.d.a(dat.a(), bki.E(), integer5);
			bki.e(integer6);
		}

		return bki;
	}

	public static dcg.a<?> a(bnw bnw, float float2, int integer) {
		return a(arr -> new dbv(arr, bnw, new dbv.a(integer, float2)));
	}

	public static dcg.a<?> a(bnw bnw) {
		return a(arr -> new dbv(arr, bnw, new dbv.d()));
	}

	public static dcg.a<?> b(bnw bnw) {
		return a(arr -> new dbv(arr, bnw, new dbv.f(1)));
	}

	public static dcg.a<?> a(bnw bnw, int integer) {
		return a(arr -> new dbv(arr, bnw, new dbv.f(integer)));
	}

	static {
		a.put(dbv.a.a, dbv.a::a);
		a.put(dbv.d.a, dbv.d::a);
		a.put(dbv.f.a, dbv.f::a);
	}

	static final class a implements dbv.b {
		public static final uh a = new uh("binomial_with_bonus_count");
		private final int b;
		private final float c;

		public a(int integer, float float2) {
			this.b = integer;
			this.c = float2;
		}

		@Override
		public int a(Random random, int integer2, int integer3) {
			for (int integer5 = 0; integer5 < integer3 + this.b; integer5++) {
				if (random.nextFloat() < this.c) {
					integer2++;
				}
			}

			return integer2;
		}

		@Override
		public void a(JsonObject jsonObject, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("extra", this.b);
			jsonObject.addProperty("probability", this.c);
		}

		public static dbv.b a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			int integer3 = adt.n(jsonObject, "extra");
			float float4 = adt.l(jsonObject, "probability");
			return new dbv.a(integer3, float4);
		}

		@Override
		public uh a() {
			return a;
		}
	}

	interface b {
		int a(Random random, int integer2, int integer3);

		void a(JsonObject jsonObject, JsonSerializationContext jsonSerializationContext);

		uh a();
	}

	interface c {
		dbv.b deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext);
	}

	static final class d implements dbv.b {
		public static final uh a = new uh("ore_drops");

		private d() {
		}

		@Override
		public int a(Random random, int integer2, int integer3) {
			if (integer3 > 0) {
				int integer5 = random.nextInt(integer3 + 2) - 1;
				if (integer5 < 0) {
					integer5 = 0;
				}

				return integer2 * (integer5 + 1);
			} else {
				return integer2;
			}
		}

		@Override
		public void a(JsonObject jsonObject, JsonSerializationContext jsonSerializationContext) {
		}

		public static dbv.b a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			return new dbv.d();
		}

		@Override
		public uh a() {
			return a;
		}
	}

	public static class e extends dcg.c<dbv> {
		public void a(JsonObject jsonObject, dbv dbv, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dbv, jsonSerializationContext);
			jsonObject.addProperty("enchantment", gl.ak.b(dbv.b).toString());
			jsonObject.addProperty("formula", dbv.d.a().toString());
			JsonObject jsonObject5 = new JsonObject();
			dbv.d.a(jsonObject5, jsonSerializationContext);
			if (jsonObject5.size() > 0) {
				jsonObject.add("parameters", jsonObject5);
			}
		}

		public dbv b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			uh uh5 = new uh(adt.h(jsonObject, "enchantment"));
			bnw bnw6 = (bnw)gl.ak.b(uh5).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + uh5));
			uh uh7 = new uh(adt.h(jsonObject, "formula"));
			dbv.c c8 = (dbv.c)dbv.a.get(uh7);
			if (c8 == null) {
				throw new JsonParseException("Invalid formula id: " + uh7);
			} else {
				dbv.b b9;
				if (jsonObject.has("parameters")) {
					b9 = c8.deserialize(adt.t(jsonObject, "parameters"), jsonDeserializationContext);
				} else {
					b9 = c8.deserialize(new JsonObject(), jsonDeserializationContext);
				}

				return new dbv(arr, bnw6, b9);
			}
		}
	}

	static final class f implements dbv.b {
		public static final uh a = new uh("uniform_bonus_count");
		private final int b;

		public f(int integer) {
			this.b = integer;
		}

		@Override
		public int a(Random random, int integer2, int integer3) {
			return integer2 + random.nextInt(this.b * integer3 + 1);
		}

		@Override
		public void a(JsonObject jsonObject, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("bonusMultiplier", this.b);
		}

		public static dbv.b a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			int integer3 = adt.n(jsonObject, "bonusMultiplier");
			return new dbv.f(integer3);
		}

		@Override
		public uh a() {
			return a;
		}
	}
}
