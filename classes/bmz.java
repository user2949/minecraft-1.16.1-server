import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class bmz implements bmm {
	private final int a;
	private final int b;
	private final gi<bmr> c;
	private final bki d;
	private final uh e;
	private final String f;

	public bmz(uh uh, String string, int integer3, int integer4, gi<bmr> gi, bki bki) {
		this.e = uh;
		this.f = string;
		this.a = integer3;
		this.b = integer4;
		this.c = gi;
		this.d = bki;
	}

	@Override
	public uh f() {
		return this.e;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.a;
	}

	@Override
	public bki c() {
		return this.d;
	}

	@Override
	public gi<bmr> a() {
		return this.c;
	}

	public boolean a(bgu bgu, bqb bqb) {
		for (int integer4 = 0; integer4 <= bgu.g() - this.a; integer4++) {
			for (int integer5 = 0; integer5 <= bgu.f() - this.b; integer5++) {
				if (this.a(bgu, integer4, integer5, true)) {
					return true;
				}

				if (this.a(bgu, integer4, integer5, false)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean a(bgu bgu, int integer2, int integer3, boolean boolean4) {
		for (int integer6 = 0; integer6 < bgu.g(); integer6++) {
			for (int integer7 = 0; integer7 < bgu.f(); integer7++) {
				int integer8 = integer6 - integer2;
				int integer9 = integer7 - integer3;
				bmr bmr10 = bmr.a;
				if (integer8 >= 0 && integer9 >= 0 && integer8 < this.a && integer9 < this.b) {
					if (boolean4) {
						bmr10 = this.c.get(this.a - integer8 - 1 + integer9 * this.a);
					} else {
						bmr10 = this.c.get(integer8 + integer9 * this.a);
					}
				}

				if (!bmr10.a(bgu.a(integer6 + integer7 * bgu.g()))) {
					return false;
				}
			}
		}

		return true;
	}

	public bki a(bgu bgu) {
		return this.c().i();
	}

	public int i() {
		return this.a;
	}

	public int j() {
		return this.b;
	}

	private static gi<bmr> b(String[] arr, Map<String, bmr> map, int integer3, int integer4) {
		gi<bmr> gi5 = gi.a(integer3 * integer4, bmr.a);
		Set<String> set6 = Sets.<String>newHashSet(map.keySet());
		set6.remove(" ");

		for (int integer7 = 0; integer7 < arr.length; integer7++) {
			for (int integer8 = 0; integer8 < arr[integer7].length(); integer8++) {
				String string9 = arr[integer7].substring(integer8, integer8 + 1);
				bmr bmr10 = (bmr)map.get(string9);
				if (bmr10 == null) {
					throw new JsonSyntaxException("Pattern references symbol '" + string9 + "' but it's not defined in the key");
				}

				set6.remove(string9);
				gi5.set(integer8 + integer3 * integer7, bmr10);
			}
		}

		if (!set6.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set6);
		} else {
			return gi5;
		}
	}

	@VisibleForTesting
	static String[] a(String... arr) {
		int integer2 = Integer.MAX_VALUE;
		int integer3 = 0;
		int integer4 = 0;
		int integer5 = 0;

		for (int integer6 = 0; integer6 < arr.length; integer6++) {
			String string7 = arr[integer6];
			integer2 = Math.min(integer2, a(string7));
			int integer8 = b(string7);
			integer3 = Math.max(integer3, integer8);
			if (integer8 < 0) {
				if (integer4 == integer6) {
					integer4++;
				}

				integer5++;
			} else {
				integer5 = 0;
			}
		}

		if (arr.length == integer5) {
			return new String[0];
		} else {
			String[] arr6 = new String[arr.length - integer5 - integer4];

			for (int integer7 = 0; integer7 < arr6.length; integer7++) {
				arr6[integer7] = arr[integer7 + integer4].substring(integer2, integer3 + 1);
			}

			return arr6;
		}
	}

	private static int a(String string) {
		int integer2 = 0;

		while (integer2 < string.length() && string.charAt(integer2) == ' ') {
			integer2++;
		}

		return integer2;
	}

	private static int b(String string) {
		int integer2 = string.length() - 1;

		while (integer2 >= 0 && string.charAt(integer2) == ' ') {
			integer2--;
		}

		return integer2;
	}

	private static String[] b(JsonArray jsonArray) {
		String[] arr2 = new String[jsonArray.size()];
		if (arr2.length > 3) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
		} else if (arr2.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for (int integer3 = 0; integer3 < arr2.length; integer3++) {
				String string4 = adt.a(jsonArray.get(integer3), "pattern[" + integer3 + "]");
				if (string4.length() > 3) {
					throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
				}

				if (integer3 > 0 && arr2[0].length() != string4.length()) {
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}

				arr2[integer3] = string4;
			}

			return arr2;
		}
	}

	private static Map<String, bmr> c(JsonObject jsonObject) {
		Map<String, bmr> map2 = Maps.<String, bmr>newHashMap();

		for (Entry<String, JsonElement> entry4 : jsonObject.entrySet()) {
			if (((String)entry4.getKey()).length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + (String)entry4.getKey() + "' is an invalid symbol (must be 1 character only).");
			}

			if (" ".equals(entry4.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}

			map2.put(entry4.getKey(), bmr.a((JsonElement)entry4.getValue()));
		}

		map2.put(" ", bmr.a);
		return map2;
	}

	public static bki a(JsonObject jsonObject) {
		String string2 = adt.h(jsonObject, "item");
		bke bke3 = (bke)gl.am.b(new uh(string2)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + string2 + "'"));
		if (jsonObject.has("data")) {
			throw new JsonParseException("Disallowed data tag found");
		} else {
			int integer4 = adt.a(jsonObject, "count", 1);
			return new bki(bke3, integer4);
		}
	}

	public static class a implements bmw<bmz> {
		public bmz a(uh uh, JsonObject jsonObject) {
			String string4 = adt.a(jsonObject, "group", "");
			Map<String, bmr> map5 = bmz.c(adt.t(jsonObject, "key"));
			String[] arr6 = bmz.a(bmz.b(adt.u(jsonObject, "pattern")));
			int integer7 = arr6[0].length();
			int integer8 = arr6.length;
			gi<bmr> gi9 = bmz.b(arr6, map5, integer7, integer8);
			bki bki10 = bmz.a(adt.t(jsonObject, "result"));
			return new bmz(uh, string4, integer7, integer8, gi9, bki10);
		}

		public bmz a(uh uh, mg mg) {
			int integer4 = mg.i();
			int integer5 = mg.i();
			String string6 = mg.e(32767);
			gi<bmr> gi7 = gi.a(integer4 * integer5, bmr.a);

			for (int integer8 = 0; integer8 < gi7.size(); integer8++) {
				gi7.set(integer8, bmr.b(mg));
			}

			bki bki8 = mg.m();
			return new bmz(uh, string6, integer4, integer5, gi7, bki8);
		}

		public void a(mg mg, bmz bmz) {
			mg.d(bmz.a);
			mg.d(bmz.b);
			mg.a(bmz.f);

			for (bmr bmr5 : bmz.c) {
				bmr5.a(mg);
			}

			mg.a(bmz.d);
		}
	}
}
