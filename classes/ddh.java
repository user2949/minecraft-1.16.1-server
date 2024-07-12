import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ddh implements ddm {
	private final Map<String, dbb> a;
	private final dat.c b;

	private ddh(Map<String, dbb> map, dat.c c) {
		this.a = ImmutableMap.copyOf(map);
		this.b = c;
	}

	@Override
	public ddn b() {
		return ddo.g;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(this.b.a());
	}

	public boolean test(dat dat) {
		aom aom3 = dat.c(this.b.a());
		if (aom3 == null) {
			return false;
		} else {
			dfm dfm4 = aom3.l.D();

			for (Entry<String, dbb> entry6 : this.a.entrySet()) {
				if (!this.a(aom3, dfm4, (String)entry6.getKey(), (dbb)entry6.getValue())) {
					return false;
				}
			}

			return true;
		}
	}

	protected boolean a(aom aom, dfm dfm, String string, dbb dbb) {
		dfj dfj6 = dfm.d(string);
		if (dfj6 == null) {
			return false;
		} else {
			String string7 = aom.bT();
			return !dfm.b(string7, dfj6) ? false : dbb.a(dfm.c(string7, dfj6).b());
		}
	}

	public static class b implements dbc<ddh> {
		public void a(JsonObject jsonObject, ddh ddh, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject5 = new JsonObject();

			for (Entry<String, dbb> entry7 : ddh.a.entrySet()) {
				jsonObject5.add((String)entry7.getKey(), jsonSerializationContext.serialize(entry7.getValue()));
			}

			jsonObject.add("scores", jsonObject5);
			jsonObject.add("entity", jsonSerializationContext.serialize(ddh.b));
		}

		public ddh a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			Set<Entry<String, JsonElement>> set4 = adt.t(jsonObject, "scores").entrySet();
			Map<String, dbb> map5 = Maps.<String, dbb>newLinkedHashMap();

			for (Entry<String, JsonElement> entry7 : set4) {
				map5.put(entry7.getKey(), adt.a((JsonElement)entry7.getValue(), "score", jsonDeserializationContext, dbb.class));
			}

			return new ddh(map5, adt.a(jsonObject, "entity", jsonDeserializationContext, dat.c.class));
		}
	}
}
