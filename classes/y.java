import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class y implements Comparable<y> {
	private final Map<String, ac> a = Maps.<String, ac>newHashMap();
	private String[][] b = new String[0][];

	public void a(Map<String, ab> map, String[][] arr) {
		Set<String> set4 = map.keySet();
		this.a.entrySet().removeIf(entry -> !set4.contains(entry.getKey()));

		for (String string6 : set4) {
			if (!this.a.containsKey(string6)) {
				this.a.put(string6, new ac());
			}
		}

		this.b = arr;
	}

	public boolean a() {
		if (this.b.length == 0) {
			return false;
		} else {
			for (String[] arr5 : this.b) {
				boolean boolean6 = false;

				for (String string10 : arr5) {
					ac ac11 = this.c(string10);
					if (ac11 != null && ac11.a()) {
						boolean6 = true;
						break;
					}
				}

				if (!boolean6) {
					return false;
				}
			}

			return true;
		}
	}

	public boolean b() {
		for (ac ac3 : this.a.values()) {
			if (ac3.a()) {
				return true;
			}
		}

		return false;
	}

	public boolean a(String string) {
		ac ac3 = (ac)this.a.get(string);
		if (ac3 != null && !ac3.a()) {
			ac3.b();
			return true;
		} else {
			return false;
		}
	}

	public boolean b(String string) {
		ac ac3 = (ac)this.a.get(string);
		if (ac3 != null && ac3.a()) {
			ac3.c();
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return "AdvancementProgress{criteria=" + this.a + ", requirements=" + Arrays.deepToString(this.b) + '}';
	}

	public void a(mg mg) {
		mg.d(this.a.size());

		for (Entry<String, ac> entry4 : this.a.entrySet()) {
			mg.a((String)entry4.getKey());
			((ac)entry4.getValue()).a(mg);
		}
	}

	public static y b(mg mg) {
		y y2 = new y();
		int integer3 = mg.i();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			y2.a.put(mg.e(32767), ac.b(mg));
		}

		return y2;
	}

	@Nullable
	public ac c(String string) {
		return (ac)this.a.get(string);
	}

	public Iterable<String> e() {
		List<String> list2 = Lists.<String>newArrayList();

		for (Entry<String, ac> entry4 : this.a.entrySet()) {
			if (!((ac)entry4.getValue()).a()) {
				list2.add(entry4.getKey());
			}
		}

		return list2;
	}

	public Iterable<String> f() {
		List<String> list2 = Lists.<String>newArrayList();

		for (Entry<String, ac> entry4 : this.a.entrySet()) {
			if (((ac)entry4.getValue()).a()) {
				list2.add(entry4.getKey());
			}
		}

		return list2;
	}

	@Nullable
	public Date g() {
		Date date2 = null;

		for (ac ac4 : this.a.values()) {
			if (ac4.a() && (date2 == null || ac4.d().before(date2))) {
				date2 = ac4.d();
			}
		}

		return date2;
	}

	public int compareTo(y y) {
		Date date3 = this.g();
		Date date4 = y.g();
		if (date3 == null && date4 != null) {
			return 1;
		} else if (date3 != null && date4 == null) {
			return -1;
		} else {
			return date3 == null && date4 == null ? 0 : date3.compareTo(date4);
		}
	}

	public static class a implements JsonDeserializer<y>, JsonSerializer<y> {
		public JsonElement serialize(y y, Type type, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject5 = new JsonObject();
			JsonObject jsonObject6 = new JsonObject();

			for (Entry<String, ac> entry8 : y.a.entrySet()) {
				ac ac9 = (ac)entry8.getValue();
				if (ac9.a()) {
					jsonObject6.add((String)entry8.getKey(), ac9.e());
				}
			}

			if (!jsonObject6.entrySet().isEmpty()) {
				jsonObject5.add("criteria", jsonObject6);
			}

			jsonObject5.addProperty("done", y.a());
			return jsonObject5;
		}

		public y deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			JsonObject jsonObject5 = adt.m(jsonElement, "advancement");
			JsonObject jsonObject6 = adt.a(jsonObject5, "criteria", new JsonObject());
			y y7 = new y();

			for (Entry<String, JsonElement> entry9 : jsonObject6.entrySet()) {
				String string10 = (String)entry9.getKey();
				y7.a.put(string10, ac.a(adt.a((JsonElement)entry9.getValue(), string10)));
			}

			return y7;
		}
	}
}
