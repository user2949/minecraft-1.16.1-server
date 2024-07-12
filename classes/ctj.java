import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class ctj {
	private static final Map<String, String> a = v.a(Maps.<String, String>newHashMap(), hashMap -> {
		hashMap.put("Village", "Village");
		hashMap.put("Mineshaft", "Mineshaft");
		hashMap.put("Mansion", "Mansion");
		hashMap.put("Igloo", "Temple");
		hashMap.put("Desert_Pyramid", "Temple");
		hashMap.put("Jungle_Pyramid", "Temple");
		hashMap.put("Swamp_Hut", "Temple");
		hashMap.put("Stronghold", "Stronghold");
		hashMap.put("Monument", "Monument");
		hashMap.put("Fortress", "Fortress");
		hashMap.put("EndCity", "EndCity");
	});
	private static final Map<String, String> b = v.a(Maps.<String, String>newHashMap(), hashMap -> {
		hashMap.put("Iglu", "Igloo");
		hashMap.put("TeDP", "Desert_Pyramid");
		hashMap.put("TeJP", "Jungle_Pyramid");
		hashMap.put("TeSH", "Swamp_Hut");
	});
	private final boolean c;
	private final Map<String, Long2ObjectMap<le>> d = Maps.<String, Long2ObjectMap<le>>newHashMap();
	private final Map<String, ctx> e = Maps.<String, ctx>newHashMap();
	private final List<String> f;
	private final List<String> g;

	public ctj(@Nullable daa daa, List<String> list2, List<String> list3) {
		this.f = list2;
		this.g = list3;
		this.a(daa);
		boolean boolean5 = false;

		for (String string7 : this.g) {
			boolean5 |= this.d.get(string7) != null;
		}

		this.c = boolean5;
	}

	public void a(long long1) {
		for (String string5 : this.f) {
			ctx ctx6 = (ctx)this.e.get(string5);
			if (ctx6 != null && ctx6.c(long1)) {
				ctx6.d(long1);
				ctx6.b();
			}
		}
	}

	public le a(le le) {
		le le3 = le.p("Level");
		bph bph4 = new bph(le3.h("xPos"), le3.h("zPos"));
		if (this.a(bph4.b, bph4.c)) {
			le = this.a(le, bph4);
		}

		le le5 = le3.p("Structures");
		le le6 = le5.p("References");

		for (String string8 : this.g) {
			cml<?> cml9 = (cml<?>)cml.a.get(string8.toLowerCase(Locale.ROOT));
			if (!le6.c(string8, 12) && cml9 != null) {
				int integer10 = 8;
				LongList longList11 = new LongArrayList();

				for (int integer12 = bph4.b - 8; integer12 <= bph4.b + 8; integer12++) {
					for (int integer13 = bph4.c - 8; integer13 <= bph4.c + 8; integer13++) {
						if (this.a(integer12, integer13, string8)) {
							longList11.add(bph.a(integer12, integer13));
						}
					}
				}

				le6.c(string8, longList11);
			}
		}

		le5.a("References", le6);
		le3.a("Structures", le5);
		le.a("Level", le3);
		return le;
	}

	private boolean a(int integer1, int integer2, String string) {
		return !this.c ? false : this.d.get(string) != null && ((ctx)this.e.get(a.get(string))).b(bph.a(integer1, integer2));
	}

	private boolean a(int integer1, int integer2) {
		if (!this.c) {
			return false;
		} else {
			for (String string5 : this.g) {
				if (this.d.get(string5) != null && ((ctx)this.e.get(a.get(string5))).c(bph.a(integer1, integer2))) {
					return true;
				}
			}

			return false;
		}
	}

	private le a(le le, bph bph) {
		le le4 = le.p("Level");
		le le5 = le4.p("Structures");
		le le6 = le5.p("Starts");

		for (String string8 : this.g) {
			Long2ObjectMap<le> long2ObjectMap9 = (Long2ObjectMap<le>)this.d.get(string8);
			if (long2ObjectMap9 != null) {
				long long10 = bph.a();
				if (((ctx)this.e.get(a.get(string8))).c(long10)) {
					le le12 = long2ObjectMap9.get(long10);
					if (le12 != null) {
						le6.a(string8, le12);
					}
				}
			}
		}

		le5.a("Starts", le6);
		le4.a("Structures", le5);
		le.a("Level", le4);
		return le;
	}

	private void a(@Nullable daa daa) {
		if (daa != null) {
			for (String string4 : this.f) {
				le le5 = new le();

				try {
					le5 = daa.a(string4, 1493).p("data").p("Features");
					if (le5.isEmpty()) {
						continue;
					}
				} catch (IOException var13) {
				}

				for (String string7 : le5.d()) {
					le le8 = le5.p(string7);
					long long9 = bph.a(le8.h("ChunkX"), le8.h("ChunkZ"));
					lk lk11 = le8.d("Children", 10);
					if (!lk11.isEmpty()) {
						String string12 = lk11.a(0).l("id");
						String string13 = (String)b.get(string12);
						if (string13 != null) {
							le8.a("id", string13);
						}
					}

					String string12 = le8.l("id");
					((Long2ObjectMap)this.d.computeIfAbsent(string12, string -> new Long2ObjectOpenHashMap())).put(long9, le8);
				}

				String string6 = string4 + "_index";
				ctx ctx7 = daa.a(() -> new ctx(string6), string6);
				if (!ctx7.a().isEmpty()) {
					this.e.put(string4, ctx7);
				} else {
					ctx ctx8 = new ctx(string6);
					this.e.put(string4, ctx8);

					for (String string10 : le5.d()) {
						le le11 = le5.p(string10);
						ctx8.a(bph.a(le11.h("ChunkX"), le11.h("ChunkZ")));
					}

					ctx8.b();
				}
			}
		}
	}

	public static ctj a(ug<bqb> ug, @Nullable daa daa) {
		if (ug == bqb.g) {
			return new ctj(
				daa,
				ImmutableList.of("Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"),
				ImmutableList.of("Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument")
			);
		} else if (ug == bqb.h) {
			List<String> list3 = ImmutableList.of("Fortress");
			return new ctj(daa, list3, list3);
		} else if (ug == bqb.i) {
			List<String> list3 = ImmutableList.of("EndCity");
			return new ctj(daa, list3, list3);
		} else {
			throw new RuntimeException(String.format("Unknown dimension type : %s", ug));
		}
	}
}
