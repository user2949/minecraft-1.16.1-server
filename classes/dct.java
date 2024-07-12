import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class dct extends dcg {
	private final Map<aoe, dbb> a;

	private dct(ddm[] arr, Map<aoe, dbb> map) {
		super(arr);
		this.a = ImmutableMap.copyOf(map);
	}

	@Override
	public dci b() {
		return dcj.l;
	}

	@Override
	public bki a(bki bki, dat dat) {
		if (bki.b() == bkk.qQ && !this.a.isEmpty()) {
			Random random4 = dat.a();
			int integer5 = random4.nextInt(this.a.size());
			Entry<aoe, dbb> entry6 = Iterables.get(this.a.entrySet(), integer5);
			aoe aoe7 = (aoe)entry6.getKey();
			int integer8 = ((dbb)entry6.getValue()).a(random4);
			if (!aoe7.a()) {
				integer8 *= 20;
			}

			bll.a(bki, aoe7, integer8);
			return bki;
		} else {
			return bki;
		}
	}

	public static dct.a c() {
		return new dct.a();
	}

	public static class a extends dcg.a<dct.a> {
		private final Map<aoe, dbb> a = Maps.<aoe, dbb>newHashMap();

		protected dct.a d() {
			return this;
		}

		public dct.a a(aoe aoe, dbb dbb) {
			this.a.put(aoe, dbb);
			return this;
		}

		@Override
		public dch b() {
			return new dct(this.g(), this.a);
		}
	}

	public static class b extends dcg.c<dct> {
		public void a(JsonObject jsonObject, dct dct, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dct, jsonSerializationContext);
			if (!dct.a.isEmpty()) {
				JsonArray jsonArray5 = new JsonArray();

				for (aoe aoe7 : dct.a.keySet()) {
					JsonObject jsonObject8 = new JsonObject();
					uh uh9 = gl.ai.b(aoe7);
					if (uh9 == null) {
						throw new IllegalArgumentException("Don't know how to serialize mob effect " + aoe7);
					}

					jsonObject8.add("type", new JsonPrimitive(uh9.toString()));
					jsonObject8.add("duration", jsonSerializationContext.serialize(dct.a.get(aoe7)));
					jsonArray5.add(jsonObject8);
				}

				jsonObject.add("effects", jsonArray5);
			}
		}

		public dct b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			Map<aoe, dbb> map5 = Maps.<aoe, dbb>newHashMap();
			if (jsonObject.has("effects")) {
				for (JsonElement jsonElement8 : adt.u(jsonObject, "effects")) {
					String string9 = adt.h(jsonElement8.getAsJsonObject(), "type");
					aoe aoe10 = (aoe)gl.ai.b(new uh(string9)).orElseThrow(() -> new JsonSyntaxException("Unknown mob effect '" + string9 + "'"));
					dbb dbb11 = adt.a(jsonElement8.getAsJsonObject(), "duration", jsonDeserializationContext, dbb.class);
					map5.put(aoe10, dbb11);
				}
			}

			return new dct(arr, map5);
		}
	}
}
