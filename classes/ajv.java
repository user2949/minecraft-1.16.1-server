import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class ajv extends DataFix {
	private static final ImmutableMap<String, ajv.a> a = ImmutableMap.<String, ajv.a>builder()
		.put("minecraft:village", new ajv.a(32, 8, 10387312))
		.put("minecraft:desert_pyramid", new ajv.a(32, 8, 14357617))
		.put("minecraft:igloo", new ajv.a(32, 8, 14357618))
		.put("minecraft:jungle_pyramid", new ajv.a(32, 8, 14357619))
		.put("minecraft:swamp_hut", new ajv.a(32, 8, 14357620))
		.put("minecraft:pillager_outpost", new ajv.a(32, 8, 165745296))
		.put("minecraft:monument", new ajv.a(32, 5, 10387313))
		.put("minecraft:endcity", new ajv.a(20, 11, 10387313))
		.put("minecraft:mansion", new ajv.a(80, 20, 10387319))
		.build();

	public ajv(Schema schema) {
		super(schema, true);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped("WorldGenSettings building", this.getInputSchema().getType(ajb.y), typed -> typed.update(DSL.remainderFinder(), ajv::a));
	}

	private static <T> Dynamic<T> a(long long1, DynamicLike<T> dynamicLike, Dynamic<T> dynamic3, Dynamic<T> dynamic4) {
		return dynamicLike.createMap(
			ImmutableMap.of(
				dynamicLike.createString("type"),
				dynamicLike.createString("minecraft:noise"),
				dynamicLike.createString("biome_source"),
				dynamic4,
				dynamicLike.createString("seed"),
				dynamicLike.createLong(long1),
				dynamicLike.createString("settings"),
				dynamic3
			)
		);
	}

	private static <T> Dynamic<T> a(Dynamic<T> dynamic, long long2, boolean boolean3, boolean boolean4) {
		Builder<Dynamic<T>, Dynamic<T>> builder6 = ImmutableMap.<Dynamic<T>, Dynamic<T>>builder()
			.put(dynamic.createString("type"), dynamic.createString("minecraft:vanilla_layered"))
			.put(dynamic.createString("seed"), dynamic.createLong(long2))
			.put(dynamic.createString("large_biomes"), dynamic.createBoolean(boolean4));
		if (boolean3) {
			builder6.put(dynamic.createString("legacy_biome_init_layer"), dynamic.createBoolean(boolean3));
		}

		return dynamic.createMap(builder6.build());
	}

	private static <T> Dynamic<T> a(Dynamic<T> dynamic) {
		DynamicOps<T> dynamicOps2 = dynamic.getOps();
		long long3 = dynamic.get("RandomSeed").asLong(0L);
		Optional<String> optional6 = dynamic.get("generatorName").asString().<String>map(string -> string.toLowerCase(Locale.ROOT)).result();
		Optional<String> optional7 = (Optional<String>)dynamic.get("legacy_custom_options")
			.asString()
			.result()
			.map(Optional::of)
			.orElseGet(() -> optional6.equals(Optional.of("customized")) ? dynamic.get("generatorOptions").asString().result() : Optional.empty());
		boolean boolean8 = false;
		Dynamic<T> dynamic5;
		if (optional6.equals(Optional.of("customized"))) {
			dynamic5 = a(dynamic, long3);
		} else if (!optional6.isPresent()) {
			dynamic5 = a(dynamic, long3);
		} else {
			String boolean9 = (String)optional6.get();
			switch (boolean9) {
				case "flat":
					OptionalDynamic<T> optionalDynamic11 = dynamic.get("generatorOptions");
					Map<Dynamic<T>, Dynamic<T>> map12 = a(dynamicOps2, optionalDynamic11);
					dynamic5 = dynamic.createMap(
						ImmutableMap.of(
							dynamic.createString("type"),
							dynamic.createString("minecraft:flat"),
							dynamic.createString("settings"),
							dynamic.createMap(
								ImmutableMap.of(
									dynamic.createString("structures"),
									dynamic.createMap(map12),
									dynamic.createString("layers"),
									(Dynamic<?>)optionalDynamic11.get("layers")
										.result()
										.orElseGet(
											() -> dynamic.createList(
													Stream.of(
														dynamic.createMap(
															ImmutableMap.of(dynamic.createString("height"), dynamic.createInt(1), dynamic.createString("block"), dynamic.createString("minecraft:bedrock"))
														),
														dynamic.createMap(
															ImmutableMap.of(dynamic.createString("height"), dynamic.createInt(2), dynamic.createString("block"), dynamic.createString("minecraft:dirt"))
														),
														dynamic.createMap(
															ImmutableMap.of(
																dynamic.createString("height"), dynamic.createInt(1), dynamic.createString("block"), dynamic.createString("minecraft:grass_block")
															)
														)
													)
												)
										),
									dynamic.createString("biome"),
									dynamic.createString(optionalDynamic11.get("biome").asString("minecraft:plains"))
								)
							)
						)
					);
					break;
				case "debug_all_block_states":
					dynamic5 = dynamic.createMap(ImmutableMap.of(dynamic.createString("type"), dynamic.createString("minecraft:debug")));
					break;
				case "buffet":
					OptionalDynamic<T> optionalDynamic13 = dynamic.get("generatorOptions");
					OptionalDynamic<?> optionalDynamic14 = optionalDynamic13.get("chunk_generator");
					Optional<String> optional15 = optionalDynamic14.get("type").asString().result();
					Dynamic<T> dynamic16;
					if (Objects.equals(optional15, Optional.of("minecraft:caves"))) {
						dynamic16 = dynamic.createString("minecraft:caves");
						boolean8 = true;
					} else if (Objects.equals(optional15, Optional.of("minecraft:floating_islands"))) {
						dynamic16 = dynamic.createString("minecraft:floating_islands");
					} else {
						dynamic16 = dynamic.createString("minecraft:overworld");
					}

					Dynamic<T> dynamic17 = (Dynamic<T>)optionalDynamic13.get("biome_source")
						.result()
						.orElseGet(() -> dynamic.createMap(ImmutableMap.of(dynamic.createString("type"), dynamic.createString("minecraft:fixed"))));
					Dynamic<T> dynamic18;
					if (dynamic17.get("type").asString().result().equals(Optional.of("minecraft:fixed"))) {
						String string19 = (String)dynamic17.get("options")
							.get("biomes")
							.asStream()
							.findFirst()
							.flatMap(dynamicx -> dynamicx.asString().result())
							.orElse("minecraft:ocean");
						dynamic18 = dynamic17.remove("options").set("biome", dynamic.createString(string19));
					} else {
						dynamic18 = dynamic17;
					}

					dynamic5 = a(long3, dynamic, dynamic16, dynamic18);
					break;
				default:
					boolean boolean19 = ((String)optional6.get()).equals("default");
					boolean boolean20 = ((String)optional6.get()).equals("default_1_1") || boolean19 && dynamic.get("generatorVersion").asInt(0) == 0;
					boolean boolean21 = ((String)optional6.get()).equals("amplified");
					boolean boolean22 = ((String)optional6.get()).equals("largebiomes");
					dynamic5 = a(long3, dynamic, dynamic.createString(boolean21 ? "minecraft:amplified" : "minecraft:overworld"), a(dynamic, long3, boolean20, boolean22));
			}
		}

		boolean boolean9 = dynamic.get("MapFeatures").asBoolean(true);
		boolean boolean10 = dynamic.get("BonusChest").asBoolean(false);
		Builder<T, T> builder11 = ImmutableMap.builder();
		builder11.put(dynamicOps2.createString("seed"), dynamicOps2.createLong(long3));
		builder11.put(dynamicOps2.createString("generate_features"), dynamicOps2.createBoolean(boolean9));
		builder11.put(dynamicOps2.createString("bonus_chest"), dynamicOps2.createBoolean(boolean10));
		builder11.put(dynamicOps2.createString("dimensions"), a(dynamic, long3, dynamic5, boolean8));
		optional7.ifPresent(string -> builder11.put(dynamicOps2.createString("legacy_custom_options"), dynamicOps2.createString(string)));
		return new Dynamic<>(dynamicOps2, dynamicOps2.createMap(builder11.build()));
	}

	protected static <T> Dynamic<T> a(Dynamic<T> dynamic, long long2) {
		return a(long2, dynamic, dynamic.createString("minecraft:overworld"), a(dynamic, long2, false, false));
	}

	protected static <T> T a(Dynamic<T> dynamic1, long long2, Dynamic<T> dynamic3, boolean boolean4) {
		DynamicOps<T> dynamicOps6 = dynamic1.getOps();
		return dynamicOps6.createMap(
			ImmutableMap.of(
				dynamicOps6.createString("minecraft:overworld"),
				dynamicOps6.createMap(
					ImmutableMap.of(
						dynamicOps6.createString("type"),
						dynamicOps6.createString("minecraft:overworld" + (boolean4 ? "_caves" : "")),
						dynamicOps6.createString("generator"),
						dynamic3.getValue()
					)
				),
				dynamicOps6.createString("minecraft:the_nether"),
				dynamicOps6.createMap(
					ImmutableMap.of(
						dynamicOps6.createString("type"),
						dynamicOps6.createString("minecraft:the_nether"),
						dynamicOps6.createString("generator"),
						a(
								long2,
								dynamic1,
								dynamic1.createString("minecraft:nether"),
								dynamic1.createMap(
									ImmutableMap.of(
										dynamic1.createString("type"),
										dynamic1.createString("minecraft:multi_noise"),
										dynamic1.createString("seed"),
										dynamic1.createLong(long2),
										dynamic1.createString("preset"),
										dynamic1.createString("minecraft:nether")
									)
								)
							)
							.getValue()
					)
				),
				dynamicOps6.createString("minecraft:the_end"),
				dynamicOps6.createMap(
					ImmutableMap.of(
						dynamicOps6.createString("type"),
						dynamicOps6.createString("minecraft:the_end"),
						dynamicOps6.createString("generator"),
						a(
								long2,
								dynamic1,
								dynamic1.createString("minecraft:end"),
								dynamic1.createMap(
									ImmutableMap.of(dynamic1.createString("type"), dynamic1.createString("minecraft:the_end"), dynamic1.createString("seed"), dynamic1.createLong(long2))
								)
							)
							.getValue()
					)
				)
			)
		);
	}

	private static <T> Map<Dynamic<T>, Dynamic<T>> a(DynamicOps<T> dynamicOps, OptionalDynamic<T> optionalDynamic) {
		MutableInt mutableInt3 = new MutableInt(32);
		MutableInt mutableInt4 = new MutableInt(3);
		MutableInt mutableInt5 = new MutableInt(128);
		MutableBoolean mutableBoolean6 = new MutableBoolean(false);
		Map<String, ajv.a> map7 = Maps.<String, ajv.a>newHashMap();
		if (!optionalDynamic.result().isPresent()) {
			mutableBoolean6.setTrue();
			map7.put("minecraft:village", a.get("minecraft:village"));
		}

		optionalDynamic.get("structures")
			.flatMap(Dynamic::getMapValues)
			.result()
			.ifPresent(map6 -> map6.forEach((dynamic6, dynamic7) -> dynamic7.getMapValues().result().ifPresent(map7x -> map7x.forEach((dynamic7x, dynamic8) -> {
							String string9 = dynamic6.asString("");
							String string10 = dynamic7x.asString("");
							String string11 = dynamic8.asString("");
							if ("stronghold".equals(string9)) {
								mutableBoolean6.setTrue();
								switch (string10) {
									case "distance":
										mutableInt3.setValue(a(string11, mutableInt3.getValue(), 1));
										return;
									case "spread":
										mutableInt4.setValue(a(string11, mutableInt4.getValue(), 1));
										return;
									case "count":
										mutableInt5.setValue(a(string11, mutableInt5.getValue(), 1));
										return;
								}
							} else {
								switch (string10) {
									case "distance":
										switch (string9) {
											case "village":
												a(map7, "minecraft:village", string11, 9);
												return;
											case "biome_1":
												a(map7, "minecraft:desert_pyramid", string11, 9);
												a(map7, "minecraft:igloo", string11, 9);
												a(map7, "minecraft:jungle_pyramid", string11, 9);
												a(map7, "minecraft:swamp_hut", string11, 9);
												a(map7, "minecraft:pillager_outpost", string11, 9);
												return;
											case "endcity":
												a(map7, "minecraft:endcity", string11, 1);
												return;
											case "mansion":
												a(map7, "minecraft:mansion", string11, 1);
												return;
											default:
												return;
										}
									case "separation":
										if ("oceanmonument".equals(string9)) {
											ajv.a a14 = (ajv.a)map7.getOrDefault("minecraft:monument", a.get("minecraft:monument"));
											int integer15 = a(string11, a14.c, 1);
											map7.put("minecraft:monument", new ajv.a(integer15, a14.c, a14.d));
										}
	
										return;
									case "spacing":
										if ("oceanmonument".equals(string9)) {
											a(map7, "minecraft:monument", string11, 1);
										}
	
										return;
								}
							}
						}))));
		Builder<Dynamic<T>, Dynamic<T>> builder8 = ImmutableMap.builder();
		builder8.put(
			optionalDynamic.createString("structures"),
			optionalDynamic.createMap(
				(Map<? extends Dynamic<?>, ? extends Dynamic<?>>)map7.entrySet()
					.stream()
					.collect(Collectors.toMap(entry -> optionalDynamic.createString((String)entry.getKey()), entry -> ((ajv.a)entry.getValue()).a(dynamicOps)))
			)
		);
		if (mutableBoolean6.isTrue()) {
			builder8.put(
				optionalDynamic.createString("stronghold"),
				optionalDynamic.createMap(
					ImmutableMap.of(
						optionalDynamic.createString("distance"),
						optionalDynamic.createInt(mutableInt3.getValue()),
						optionalDynamic.createString("spread"),
						optionalDynamic.createInt(mutableInt4.getValue()),
						optionalDynamic.createString("count"),
						optionalDynamic.createInt(mutableInt5.getValue())
					)
				)
			);
		}

		return builder8.build();
	}

	private static int a(String string, int integer) {
		return NumberUtils.toInt(string, integer);
	}

	private static int a(String string, int integer2, int integer3) {
		return Math.max(integer3, a(string, integer2));
	}

	private static void a(Map<String, ajv.a> map, String string2, String string3, int integer) {
		ajv.a a5 = (ajv.a)map.getOrDefault(string2, a.get(string2));
		int integer6 = a(string3, a5.b, integer);
		map.put(string2, new ajv.a(integer6, a5.c, a5.d));
	}

	static final class a {
		public static final Codec<ajv.a> a = RecordCodecBuilder.create(
			instance -> instance.group(
						Codec.INT.fieldOf("spacing").forGetter(a -> a.b), Codec.INT.fieldOf("separation").forGetter(a -> a.c), Codec.INT.fieldOf("salt").forGetter(a -> a.d)
					)
					.apply(instance, ajv.a::new)
		);
		private final int b;
		private final int c;
		private final int d;

		public a(int integer1, int integer2, int integer3) {
			this.b = integer1;
			this.c = integer2;
			this.d = integer3;
		}

		public <T> Dynamic<T> a(DynamicOps<T> dynamicOps) {
			return new Dynamic<>(dynamicOps, (T)a.encodeStart(dynamicOps, this).result().orElse(dynamicOps.emptyMap()));
		}
	}
}
