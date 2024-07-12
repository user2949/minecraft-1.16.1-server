import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class z {
	public static final z a = new z(0, new uh[0], new uh[0], cw.a.a);
	private final int b;
	private final uh[] c;
	private final uh[] d;
	private final cw.a e;

	public z(int integer, uh[] arr2, uh[] arr3, cw.a a) {
		this.b = integer;
		this.c = arr2;
		this.d = arr3;
		this.e = a;
	}

	public void a(ze ze) {
		ze.d(this.b);
		dat dat3 = new dat.a(ze.u()).a(dda.a, ze).a(dda.f, ze.cA()).a(ze.cX()).a(dcz.i);
		boolean boolean4 = false;

		for (uh uh8 : this.c) {
			for (bki bki10 : ze.c.aH().a(uh8).a(dat3)) {
				if (ze.g(bki10)) {
					ze.l.a(null, ze.cC(), ze.cD(), ze.cG(), acl.gL, acm.PLAYERS, 0.2F, ((ze.cX().nextFloat() - ze.cX().nextFloat()) * 0.7F + 1.0F) * 2.0F);
					boolean4 = true;
				} else {
					bbg bbg11 = ze.a(bki10, false);
					if (bbg11 != null) {
						bbg11.n();
						bbg11.b(ze.bR());
					}
				}
			}
		}

		if (boolean4) {
			ze.bv.c();
		}

		if (this.d.length > 0) {
			ze.a(this.d);
		}

		MinecraftServer minecraftServer5 = ze.c;
		this.e.a(minecraftServer5.az()).ifPresent(cw -> minecraftServer5.az().a(cw, ze.cv().a().a(2)));
	}

	public String toString() {
		return "AdvancementRewards{experience="
			+ this.b
			+ ", loot="
			+ Arrays.toString(this.c)
			+ ", recipes="
			+ Arrays.toString(this.d)
			+ ", function="
			+ this.e
			+ '}';
	}

	public JsonElement b() {
		if (this == a) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonObject2 = new JsonObject();
			if (this.b != 0) {
				jsonObject2.addProperty("experience", this.b);
			}

			if (this.c.length > 0) {
				JsonArray jsonArray3 = new JsonArray();

				for (uh uh7 : this.c) {
					jsonArray3.add(uh7.toString());
				}

				jsonObject2.add("loot", jsonArray3);
			}

			if (this.d.length > 0) {
				JsonArray jsonArray3 = new JsonArray();

				for (uh uh7 : this.d) {
					jsonArray3.add(uh7.toString());
				}

				jsonObject2.add("recipes", jsonArray3);
			}

			if (this.e.a() != null) {
				jsonObject2.addProperty("function", this.e.a().toString());
			}

			return jsonObject2;
		}
	}

	public static z a(JsonObject jsonObject) throws JsonParseException {
		int integer2 = adt.a(jsonObject, "experience", 0);
		JsonArray jsonArray3 = adt.a(jsonObject, "loot", new JsonArray());
		uh[] arr4 = new uh[jsonArray3.size()];

		for (int integer5 = 0; integer5 < arr4.length; integer5++) {
			arr4[integer5] = new uh(adt.a(jsonArray3.get(integer5), "loot[" + integer5 + "]"));
		}

		JsonArray jsonArray5 = adt.a(jsonObject, "recipes", new JsonArray());
		uh[] arr6 = new uh[jsonArray5.size()];

		for (int integer7 = 0; integer7 < arr6.length; integer7++) {
			arr6[integer7] = new uh(adt.a(jsonArray5.get(integer7), "recipes[" + integer7 + "]"));
		}

		cw.a a7;
		if (jsonObject.has("function")) {
			a7 = new cw.a(new uh(adt.h(jsonObject, "function")));
		} else {
			a7 = cw.a.a;
		}

		return new z(integer2, arr4, arr6, a7);
	}

	public static class a {
		private int a;
		private final List<uh> b = Lists.<uh>newArrayList();
		private final List<uh> c = Lists.<uh>newArrayList();
		@Nullable
		private uh d;

		public static z.a a(int integer) {
			return new z.a().b(integer);
		}

		public z.a b(int integer) {
			this.a += integer;
			return this;
		}

		public static z.a c(uh uh) {
			return new z.a().d(uh);
		}

		public z.a d(uh uh) {
			this.c.add(uh);
			return this;
		}

		public z a() {
			return new z(this.a, (uh[])this.b.toArray(new uh[0]), (uh[])this.c.toArray(new uh[0]), this.d == null ? cw.a.a : new cw.a(this.d));
		}
	}
}
