import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class dcl extends dcg {
	private final List<dcl.b> a;

	private dcl(ddm[] arr, List<dcl.b> list) {
		super(arr);
		this.a = ImmutableList.copyOf(list);
	}

	@Override
	public dci b() {
		return dcj.i;
	}

	@Override
	public bki a(bki bki, dat dat) {
		Random random4 = dat.a();

		for (dcl.b b6 : this.a) {
			UUID uUID7 = b6.e;
			if (uUID7 == null) {
				uUID7 = UUID.randomUUID();
			}

			aor aor8 = v.a(b6.f, random4);
			bki.a(b6.b, new apv(uUID7, b6.a, (double)b6.d.b(random4), b6.c), aor8);
		}

		return bki;
	}

	static class b {
		private final String a;
		private final aps b;
		private final apv.a c;
		private final dbb d;
		@Nullable
		private final UUID e;
		private final aor[] f;

		private b(String string, aps aps, apv.a a, dbb dbb, aor[] arr, @Nullable UUID uUID) {
			this.a = string;
			this.b = aps;
			this.c = a;
			this.d = dbb;
			this.e = uUID;
			this.f = arr;
		}

		public JsonObject a(JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject3 = new JsonObject();
			jsonObject3.addProperty("name", this.a);
			jsonObject3.addProperty("attribute", gl.aP.b(this.b).toString());
			jsonObject3.addProperty("operation", a(this.c));
			jsonObject3.add("amount", jsonSerializationContext.serialize(this.d));
			if (this.e != null) {
				jsonObject3.addProperty("id", this.e.toString());
			}

			if (this.f.length == 1) {
				jsonObject3.addProperty("slot", this.f[0].d());
			} else {
				JsonArray jsonArray4 = new JsonArray();

				for (aor aor8 : this.f) {
					jsonArray4.add(new JsonPrimitive(aor8.d()));
				}

				jsonObject3.add("slot", jsonArray4);
			}

			return jsonObject3;
		}

		public static dcl.b a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			String string3 = adt.h(jsonObject, "name");
			uh uh4 = new uh(adt.h(jsonObject, "attribute"));
			aps aps5 = gl.aP.a(uh4);
			if (aps5 == null) {
				throw new JsonSyntaxException("Unknown attribute: " + uh4);
			} else {
				apv.a a6 = a(adt.h(jsonObject, "operation"));
				dbb dbb7 = adt.a(jsonObject, "amount", jsonDeserializationContext, dbb.class);
				UUID uUID9 = null;
				aor[] arr8;
				if (adt.a(jsonObject, "slot")) {
					arr8 = new aor[]{aor.a(adt.h(jsonObject, "slot"))};
				} else {
					if (!adt.d(jsonObject, "slot")) {
						throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
					}

					JsonArray jsonArray10 = adt.u(jsonObject, "slot");
					arr8 = new aor[jsonArray10.size()];
					int integer11 = 0;

					for (JsonElement jsonElement13 : jsonArray10) {
						arr8[integer11++] = aor.a(adt.a(jsonElement13, "slot"));
					}

					if (arr8.length == 0) {
						throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
					}
				}

				if (jsonObject.has("id")) {
					String string10 = adt.h(jsonObject, "id");

					try {
						uUID9 = UUID.fromString(string10);
					} catch (IllegalArgumentException var13) {
						throw new JsonSyntaxException("Invalid attribute modifier id '" + string10 + "' (must be UUID format, with dashes)");
					}
				}

				return new dcl.b(string3, aps5, a6, dbb7, arr8, uUID9);
			}
		}

		private static String a(apv.a a) {
			switch (a) {
				case ADDITION:
					return "addition";
				case MULTIPLY_BASE:
					return "multiply_base";
				case MULTIPLY_TOTAL:
					return "multiply_total";
				default:
					throw new IllegalArgumentException("Unknown operation " + a);
			}
		}

		private static apv.a a(String string) {
			switch (string) {
				case "addition":
					return apv.a.ADDITION;
				case "multiply_base":
					return apv.a.MULTIPLY_BASE;
				case "multiply_total":
					return apv.a.MULTIPLY_TOTAL;
				default:
					throw new JsonSyntaxException("Unknown attribute modifier operation " + string);
			}
		}
	}

	public static class d extends dcg.c<dcl> {
		public void a(JsonObject jsonObject, dcl dcl, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dcl, jsonSerializationContext);
			JsonArray jsonArray5 = new JsonArray();

			for (dcl.b b7 : dcl.a) {
				jsonArray5.add(b7.a(jsonSerializationContext));
			}

			jsonObject.add("modifiers", jsonArray5);
		}

		public dcl b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			JsonArray jsonArray5 = adt.u(jsonObject, "modifiers");
			List<dcl.b> list6 = Lists.<dcl.b>newArrayListWithExpectedSize(jsonArray5.size());

			for (JsonElement jsonElement8 : jsonArray5) {
				list6.add(dcl.b.a(adt.m(jsonElement8, "modifier"), jsonDeserializationContext));
			}

			if (list6.isEmpty()) {
				throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
			} else {
				return new dcl(arr, list6);
			}
		}
	}
}
