import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class aic extends DataFix {
	static final Map<String, String> a = v.a(Maps.<String, String>newHashMap(), hashMap -> {
		hashMap.put("0", "minecraft:ocean");
		hashMap.put("1", "minecraft:plains");
		hashMap.put("2", "minecraft:desert");
		hashMap.put("3", "minecraft:mountains");
		hashMap.put("4", "minecraft:forest");
		hashMap.put("5", "minecraft:taiga");
		hashMap.put("6", "minecraft:swamp");
		hashMap.put("7", "minecraft:river");
		hashMap.put("8", "minecraft:nether");
		hashMap.put("9", "minecraft:the_end");
		hashMap.put("10", "minecraft:frozen_ocean");
		hashMap.put("11", "minecraft:frozen_river");
		hashMap.put("12", "minecraft:snowy_tundra");
		hashMap.put("13", "minecraft:snowy_mountains");
		hashMap.put("14", "minecraft:mushroom_fields");
		hashMap.put("15", "minecraft:mushroom_field_shore");
		hashMap.put("16", "minecraft:beach");
		hashMap.put("17", "minecraft:desert_hills");
		hashMap.put("18", "minecraft:wooded_hills");
		hashMap.put("19", "minecraft:taiga_hills");
		hashMap.put("20", "minecraft:mountain_edge");
		hashMap.put("21", "minecraft:jungle");
		hashMap.put("22", "minecraft:jungle_hills");
		hashMap.put("23", "minecraft:jungle_edge");
		hashMap.put("24", "minecraft:deep_ocean");
		hashMap.put("25", "minecraft:stone_shore");
		hashMap.put("26", "minecraft:snowy_beach");
		hashMap.put("27", "minecraft:birch_forest");
		hashMap.put("28", "minecraft:birch_forest_hills");
		hashMap.put("29", "minecraft:dark_forest");
		hashMap.put("30", "minecraft:snowy_taiga");
		hashMap.put("31", "minecraft:snowy_taiga_hills");
		hashMap.put("32", "minecraft:giant_tree_taiga");
		hashMap.put("33", "minecraft:giant_tree_taiga_hills");
		hashMap.put("34", "minecraft:wooded_mountains");
		hashMap.put("35", "minecraft:savanna");
		hashMap.put("36", "minecraft:savanna_plateau");
		hashMap.put("37", "minecraft:badlands");
		hashMap.put("38", "minecraft:wooded_badlands_plateau");
		hashMap.put("39", "minecraft:badlands_plateau");
		hashMap.put("40", "minecraft:small_end_islands");
		hashMap.put("41", "minecraft:end_midlands");
		hashMap.put("42", "minecraft:end_highlands");
		hashMap.put("43", "minecraft:end_barrens");
		hashMap.put("44", "minecraft:warm_ocean");
		hashMap.put("45", "minecraft:lukewarm_ocean");
		hashMap.put("46", "minecraft:cold_ocean");
		hashMap.put("47", "minecraft:deep_warm_ocean");
		hashMap.put("48", "minecraft:deep_lukewarm_ocean");
		hashMap.put("49", "minecraft:deep_cold_ocean");
		hashMap.put("50", "minecraft:deep_frozen_ocean");
		hashMap.put("127", "minecraft:the_void");
		hashMap.put("129", "minecraft:sunflower_plains");
		hashMap.put("130", "minecraft:desert_lakes");
		hashMap.put("131", "minecraft:gravelly_mountains");
		hashMap.put("132", "minecraft:flower_forest");
		hashMap.put("133", "minecraft:taiga_mountains");
		hashMap.put("134", "minecraft:swamp_hills");
		hashMap.put("140", "minecraft:ice_spikes");
		hashMap.put("149", "minecraft:modified_jungle");
		hashMap.put("151", "minecraft:modified_jungle_edge");
		hashMap.put("155", "minecraft:tall_birch_forest");
		hashMap.put("156", "minecraft:tall_birch_hills");
		hashMap.put("157", "minecraft:dark_forest_hills");
		hashMap.put("158", "minecraft:snowy_taiga_mountains");
		hashMap.put("160", "minecraft:giant_spruce_taiga");
		hashMap.put("161", "minecraft:giant_spruce_taiga_hills");
		hashMap.put("162", "minecraft:modified_gravelly_mountains");
		hashMap.put("163", "minecraft:shattered_savanna");
		hashMap.put("164", "minecraft:shattered_savanna_plateau");
		hashMap.put("165", "minecraft:eroded_badlands");
		hashMap.put("166", "minecraft:modified_wooded_badlands_plateau");
		hashMap.put("167", "minecraft:modified_badlands_plateau");
	});

	public aic(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getOutputSchema().getType(ajb.a);
		return this.fixTypeEverywhereTyped(
			"LevelDataGeneratorOptionsFix", this.getInputSchema().getType(ajb.a), type2, typed -> (Typed)typed.write().flatMap(dynamic -> {
					Optional<String> optional3 = dynamic.get("generatorOptions").asString().result();
					Dynamic<?> dynamic4;
					if ("flat".equalsIgnoreCase(dynamic.get("generatorName").asString(""))) {
						String string5 = (String)optional3.orElse("");
						dynamic4 = dynamic.set("generatorOptions", a(string5, dynamic.getOps()));
					} else if ("buffet".equalsIgnoreCase(dynamic.get("generatorName").asString("")) && optional3.isPresent()) {
						Dynamic<JsonElement> dynamic5 = new Dynamic<>(JsonOps.INSTANCE, adt.a((String)optional3.get(), true));
						dynamic4 = dynamic.set("generatorOptions", dynamic5.convert(dynamic.getOps()));
					} else {
						dynamic4 = dynamic;
					}
	
					return type2.readTyped(dynamic4);
				}).map(Pair::getFirst).result().orElseThrow(() -> new IllegalStateException("Could not read new level type."))
		);
	}

	private static <T> Dynamic<T> a(String string, DynamicOps<T> dynamicOps) {
		Iterator<String> iterator3 = Splitter.on(';').split(string).iterator();
		String string5 = "minecraft:plains";
		Map<String, Map<String, String>> map6 = Maps.<String, Map<String, String>>newHashMap();
		List<Pair<Integer, String>> list4;
		if (!string.isEmpty() && iterator3.hasNext()) {
			list4 = b((String)iterator3.next());
			if (!list4.isEmpty()) {
				if (iterator3.hasNext()) {
					string5 = (String)a.getOrDefault(iterator3.next(), "minecraft:plains");
				}

				if (iterator3.hasNext()) {
					String[] arr7 = ((String)iterator3.next()).toLowerCase(Locale.ROOT).split(",");

					for (String string11 : arr7) {
						String[] arr12 = string11.split("\\(", 2);
						if (!arr12[0].isEmpty()) {
							map6.put(arr12[0], Maps.newHashMap());
							if (arr12.length > 1 && arr12[1].endsWith(")") && arr12[1].length() > 1) {
								String[] arr13 = arr12[1].substring(0, arr12[1].length() - 1).split(" ");

								for (String string17 : arr13) {
									String[] arr18 = string17.split("=", 2);
									if (arr18.length == 2) {
										((Map)map6.get(arr12[0])).put(arr18[0], arr18[1]);
									}
								}
							}
						}
					}
				} else {
					map6.put("village", Maps.newHashMap());
				}
			}
		} else {
			list4 = Lists.<Pair<Integer, String>>newArrayList();
			list4.add(Pair.of(1, "minecraft:bedrock"));
			list4.add(Pair.of(2, "minecraft:dirt"));
			list4.add(Pair.of(1, "minecraft:grass_block"));
			map6.put("village", Maps.newHashMap());
		}

		T object7 = dynamicOps.createList(
			list4.stream()
				.map(
					pair -> dynamicOps.createMap(
							ImmutableMap.of(
								dynamicOps.createString("height"),
								dynamicOps.createInt((Integer)pair.getFirst()),
								dynamicOps.createString("block"),
								dynamicOps.createString((String)pair.getSecond())
							)
						)
				)
		);
		T object8 = dynamicOps.createMap(
			(Map<T, T>)map6.entrySet()
				.stream()
				.map(
					entry -> Pair.of(
							dynamicOps.createString(((String)entry.getKey()).toLowerCase(Locale.ROOT)),
							dynamicOps.createMap(
								(Map<T, T>)((Map)entry.getValue())
									.entrySet()
									.stream()
									.map(entryx -> Pair.of(dynamicOps.createString((String)entryx.getKey()), dynamicOps.createString((String)entryx.getValue())))
									.collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
							)
						)
				)
				.collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
		);
		return new Dynamic<>(
			dynamicOps,
			dynamicOps.createMap(
				ImmutableMap.of(
					dynamicOps.createString("layers"),
					object7,
					dynamicOps.createString("biome"),
					dynamicOps.createString(string5),
					dynamicOps.createString("structures"),
					object8
				)
			)
		);
	}

	@Nullable
	private static Pair<Integer, String> a(String string) {
		String[] arr2 = string.split("\\*", 2);
		int integer3;
		if (arr2.length == 2) {
			try {
				integer3 = Integer.parseInt(arr2[0]);
			} catch (NumberFormatException var4) {
				return null;
			}
		} else {
			integer3 = 1;
		}

		String string4 = arr2[arr2.length - 1];
		return Pair.of(integer3, string4);
	}

	private static List<Pair<Integer, String>> b(String string) {
		List<Pair<Integer, String>> list2 = Lists.<Pair<Integer, String>>newArrayList();
		String[] arr3 = string.split(",");

		for (String string7 : arr3) {
			Pair<Integer, String> pair8 = a(string7);
			if (pair8 == null) {
				return Collections.emptyList();
			}

			list2.add(pair8);
		}

		return list2;
	}
}
