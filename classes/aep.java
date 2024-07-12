import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class aep {
	private static final BiFunction<Integer, Schema, Schema> a = Schema::new;
	private static final BiFunction<Integer, Schema, Schema> b = aka::new;
	private static final DataFixer c = b();

	private static DataFixer b() {
		DataFixerBuilder dataFixerBuilder1 = new DataFixerBuilder(u.a().getWorldVersion());
		a(dataFixerBuilder1);
		return dataFixerBuilder1.build(v.e());
	}

	public static DataFixer a() {
		return c;
	}

	private static void a(DataFixerBuilder dataFixerBuilder) {
		Schema schema2 = dataFixerBuilder.addSchema(99, alx::new);
		Schema schema3 = dataFixerBuilder.addSchema(100, akb::new);
		dataFixerBuilder.addFixer(new agf(schema3, true));
		Schema schema4 = dataFixerBuilder.addSchema(101, a);
		dataFixerBuilder.addFixer(new afi(schema4, false));
		Schema schema5 = dataFixerBuilder.addSchema(102, akc::new);
		dataFixerBuilder.addFixer(new ahm(schema5, true));
		dataFixerBuilder.addFixer(new aho(schema5, false));
		Schema schema6 = dataFixerBuilder.addSchema(105, a);
		dataFixerBuilder.addFixer(new ahr(schema6, true));
		Schema schema7 = dataFixerBuilder.addSchema(106, ake::new);
		dataFixerBuilder.addFixer(new aii(schema7, true));
		Schema schema8 = dataFixerBuilder.addSchema(107, akf::new);
		dataFixerBuilder.addFixer(new agl(schema8, true));
		Schema schema9 = dataFixerBuilder.addSchema(108, a);
		dataFixerBuilder.addFixer(new agx(schema9, true));
		Schema schema10 = dataFixerBuilder.addSchema(109, a);
		dataFixerBuilder.addFixer(new agg(schema10, true));
		Schema schema11 = dataFixerBuilder.addSchema(110, a);
		dataFixerBuilder.addFixer(new agh(schema11, true));
		Schema schema12 = dataFixerBuilder.addSchema(111, a);
		dataFixerBuilder.addFixer(new agm(schema12, true));
		Schema schema13 = dataFixerBuilder.addSchema(113, a);
		dataFixerBuilder.addFixer(new agr(schema13, true));
		Schema schema14 = dataFixerBuilder.addSchema(135, akh::new);
		dataFixerBuilder.addFixer(new agt(schema14, true));
		Schema schema15 = dataFixerBuilder.addSchema(143, aki::new);
		dataFixerBuilder.addFixer(new agz(schema15, true));
		Schema schema16 = dataFixerBuilder.addSchema(147, a);
		dataFixerBuilder.addFixer(new afz(schema16, true));
		Schema schema17 = dataFixerBuilder.addSchema(165, a);
		dataFixerBuilder.addFixer(new ahy(schema17, true));
		Schema schema18 = dataFixerBuilder.addSchema(501, alp::new);
		dataFixerBuilder.addFixer(new aes(schema18, "Add 1.10 entities fix", ajb.p));
		Schema schema19 = dataFixerBuilder.addSchema(502, a);
		dataFixerBuilder.addFixer(
			ahp.a(schema19, "cooked_fished item renamer", string -> Objects.equals(aka.a(string), "minecraft:cooked_fished") ? "minecraft:cooked_fish" : string)
		);
		dataFixerBuilder.addFixer(new ahd(schema19, false));
		Schema schema20 = dataFixerBuilder.addSchema(505, a);
		dataFixerBuilder.addFixer(new aiq(schema20, false));
		Schema schema21 = dataFixerBuilder.addSchema(700, alq::new);
		dataFixerBuilder.addFixer(new age(schema21, true));
		Schema schema22 = dataFixerBuilder.addSchema(701, alr::new);
		dataFixerBuilder.addFixer(new agw(schema22, true));
		Schema schema23 = dataFixerBuilder.addSchema(702, als::new);
		dataFixerBuilder.addFixer(new ahc(schema23, true));
		Schema schema24 = dataFixerBuilder.addSchema(703, alt::new);
		dataFixerBuilder.addFixer(new agi(schema24, true));
		Schema schema25 = dataFixerBuilder.addSchema(704, alu::new);
		dataFixerBuilder.addFixer(new afe(schema25, true));
		Schema schema26 = dataFixerBuilder.addSchema(705, alv::new);
		dataFixerBuilder.addFixer(new agj(schema26, true));
		Schema schema27 = dataFixerBuilder.addSchema(804, b);
		dataFixerBuilder.addFixer(new ahk(schema27, true));
		Schema schema28 = dataFixerBuilder.addSchema(806, b);
		dataFixerBuilder.addFixer(new ahx(schema28, false));
		Schema schema29 = dataFixerBuilder.addSchema(808, alw::new);
		dataFixerBuilder.addFixer(new aes(schema29, "added shulker box", ajb.k));
		Schema schema30 = dataFixerBuilder.addSchema(808, 1, b);
		dataFixerBuilder.addFixer(new agu(schema30, false));
		Schema schema31 = dataFixerBuilder.addSchema(813, b);
		dataFixerBuilder.addFixer(new ahq(schema31, false));
		dataFixerBuilder.addFixer(new afh(schema31, false));
		Schema schema32 = dataFixerBuilder.addSchema(816, b);
		dataFixerBuilder.addFixer(new ait(schema32, false));
		Schema schema33 = dataFixerBuilder.addSchema(820, b);
		dataFixerBuilder.addFixer(ahp.a(schema33, "totem item renamer", a("minecraft:totem", "minecraft:totem_of_undying")));
		Schema schema34 = dataFixerBuilder.addSchema(1022, akd::new);
		dataFixerBuilder.addFixer(new ajw(schema34, "added shoulder entities to players", ajb.b));
		Schema schema35 = dataFixerBuilder.addSchema(1125, akg::new);
		dataFixerBuilder.addFixer(new aew(schema35, true));
		dataFixerBuilder.addFixer(new aex(schema35, false));
		Schema schema36 = dataFixerBuilder.addSchema(1344, b);
		dataFixerBuilder.addFixer(new air(schema36, false));
		Schema schema37 = dataFixerBuilder.addSchema(1446, b);
		dataFixerBuilder.addFixer(new ais(schema37, false));
		Schema schema38 = dataFixerBuilder.addSchema(1450, b);
		dataFixerBuilder.addFixer(new afo(schema38, false));
		Schema schema39 = dataFixerBuilder.addSchema(1451, akj::new);
		dataFixerBuilder.addFixer(new aes(schema39, "AddTrappedChestFix", ajb.k));
		Schema schema40 = dataFixerBuilder.addSchema(1451, 1, akk::new);
		dataFixerBuilder.addFixer(new afs(schema40, true));
		Schema schema41 = dataFixerBuilder.addSchema(1451, 2, akl::new);
		dataFixerBuilder.addFixer(new afc(schema41, true));
		Schema schema42 = dataFixerBuilder.addSchema(1451, 3, akm::new);
		dataFixerBuilder.addFixer(new aga(schema42, true));
		dataFixerBuilder.addFixer(new aht(schema42, false));
		Schema schema43 = dataFixerBuilder.addSchema(1451, 4, akn::new);
		dataFixerBuilder.addFixer(new afk(schema43, true));
		dataFixerBuilder.addFixer(new ahv(schema43, false));
		Schema schema44 = dataFixerBuilder.addSchema(1451, 5, ako::new);
		dataFixerBuilder.addFixer(new aes(schema44, "RemoveNoteBlockFlowerPotFix", ajb.k));
		dataFixerBuilder.addFixer(new ahu(schema44, false));
		dataFixerBuilder.addFixer(new ahb(schema44, false));
		dataFixerBuilder.addFixer(new afb(schema44, false));
		dataFixerBuilder.addFixer(new aid(schema44, false));
		Schema schema45 = dataFixerBuilder.addSchema(1451, 6, akp::new);
		dataFixerBuilder.addFixer(new ajk(schema45, true));
		dataFixerBuilder.addFixer(new aff(schema45, false));
		Schema schema46 = dataFixerBuilder.addSchema(1451, 7, akq::new);
		dataFixerBuilder.addFixer(new ajh(schema46, true));
		Schema schema47 = dataFixerBuilder.addSchema(1451, 7, b);
		dataFixerBuilder.addFixer(new ajt(schema47, false));
		Schema schema48 = dataFixerBuilder.addSchema(1456, b);
		dataFixerBuilder.addFixer(new agk(schema48, false));
		Schema schema49 = dataFixerBuilder.addSchema(1458, b);
		dataFixerBuilder.addFixer(new agd(schema49, false));
		dataFixerBuilder.addFixer(new ahl(schema49, false));
		dataFixerBuilder.addFixer(new afd(schema49, false));
		Schema schema50 = dataFixerBuilder.addSchema(1460, akr::new);
		dataFixerBuilder.addFixer(new agn(schema50, false));
		Schema schema51 = dataFixerBuilder.addSchema(1466, aks::new);
		dataFixerBuilder.addFixer(new afw(schema51, true));
		Schema schema52 = dataFixerBuilder.addSchema(1470, akt::new);
		dataFixerBuilder.addFixer(new aes(schema52, "Add 1.13 entities fix", ajb.p));
		Schema schema53 = dataFixerBuilder.addSchema(1474, b);
		dataFixerBuilder.addFixer(new afx(schema53, false));
		dataFixerBuilder.addFixer(
			afl.a(schema53, "Colorless shulker block fixer", string -> Objects.equals(aka.a(string), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : string)
		);
		dataFixerBuilder.addFixer(
			ahp.a(schema53, "Colorless shulker item fixer", string -> Objects.equals(aka.a(string), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : string)
		);
		Schema schema54 = dataFixerBuilder.addSchema(1475, b);
		dataFixerBuilder.addFixer(
			afl.a(schema54, "Flowing fixer", a(ImmutableMap.of("minecraft:flowing_water", "minecraft:water", "minecraft:flowing_lava", "minecraft:lava")))
		);
		Schema schema55 = dataFixerBuilder.addSchema(1480, b);
		dataFixerBuilder.addFixer(afl.a(schema55, "Rename coral blocks", a(aje.a)));
		dataFixerBuilder.addFixer(ahp.a(schema55, "Rename coral items", a(aje.a)));
		Schema schema56 = dataFixerBuilder.addSchema(1481, aku::new);
		dataFixerBuilder.addFixer(new aes(schema56, "Add conduit", ajb.k));
		Schema schema57 = dataFixerBuilder.addSchema(1483, akv::new);
		dataFixerBuilder.addFixer(new agp(schema57, true));
		dataFixerBuilder.addFixer(ahp.a(schema57, "Rename pufferfish egg item", a(agp.a)));
		Schema schema58 = dataFixerBuilder.addSchema(1484, b);
		dataFixerBuilder.addFixer(
			ahp.a(
				schema58, "Rename seagrass items", a(ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))
			)
		);
		dataFixerBuilder.addFixer(
			afl.a(
				schema58, "Rename seagrass blocks", a(ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))
			)
		);
		dataFixerBuilder.addFixer(new ahi(schema58, false));
		Schema schema59 = dataFixerBuilder.addSchema(1486, akw::new);
		dataFixerBuilder.addFixer(new agc(schema59, true));
		dataFixerBuilder.addFixer(ahp.a(schema59, "Rename cod/salmon egg items", a(agc.b)));
		Schema schema60 = dataFixerBuilder.addSchema(1487, b);
		dataFixerBuilder.addFixer(
			ahp.a(
				schema60,
				"Rename prismarine_brick(s)_* blocks",
				a(
					ImmutableMap.of(
						"minecraft:prismarine_bricks_slab", "minecraft:prismarine_brick_slab", "minecraft:prismarine_bricks_stairs", "minecraft:prismarine_brick_stairs"
					)
				)
			)
		);
		dataFixerBuilder.addFixer(
			afl.a(
				schema60,
				"Rename prismarine_brick(s)_* items",
				a(
					ImmutableMap.of(
						"minecraft:prismarine_bricks_slab", "minecraft:prismarine_brick_slab", "minecraft:prismarine_bricks_stairs", "minecraft:prismarine_brick_stairs"
					)
				)
			)
		);
		Schema schema61 = dataFixerBuilder.addSchema(1488, b);
		dataFixerBuilder.addFixer(
			afl.a(schema61, "Rename kelp/kelptop", a(ImmutableMap.of("minecraft:kelp_top", "minecraft:kelp", "minecraft:kelp", "minecraft:kelp_plant")))
		);
		dataFixerBuilder.addFixer(ahp.a(schema61, "Rename kelptop", a("minecraft:kelp_top", "minecraft:kelp")));
		dataFixerBuilder.addFixer(new aij(schema61, false, "Command block block entity custom name fix", ajb.k, "minecraft:command_block") {
			@Override
			protected Typed<?> a(Typed<?> typed) {
				return typed.update(DSL.remainderFinder(), agd::a);
			}
		});
		dataFixerBuilder.addFixer(new aij(schema61, false, "Command block minecart custom name fix", ajb.p, "minecraft:commandblock_minecart") {
			@Override
			protected Typed<?> a(Typed<?> typed) {
				return typed.update(DSL.remainderFinder(), agd::a);
			}
		});
		dataFixerBuilder.addFixer(new ahj(schema61, false));
		Schema schema62 = dataFixerBuilder.addSchema(1490, b);
		dataFixerBuilder.addFixer(afl.a(schema62, "Rename melon_block", a("minecraft:melon_block", "minecraft:melon")));
		dataFixerBuilder.addFixer(
			ahp.a(
				schema62,
				"Rename melon_block/melon/speckled_melon",
				a(
					ImmutableMap.of(
						"minecraft:melon_block", "minecraft:melon", "minecraft:melon", "minecraft:melon_slice", "minecraft:speckled_melon", "minecraft:glistering_melon_slice"
					)
				)
			)
		);
		Schema schema63 = dataFixerBuilder.addSchema(1492, b);
		dataFixerBuilder.addFixer(new afv(schema63, false));
		Schema schema64 = dataFixerBuilder.addSchema(1494, b);
		dataFixerBuilder.addFixer(new ahs(schema64, false));
		Schema schema65 = dataFixerBuilder.addSchema(1496, b);
		dataFixerBuilder.addFixer(new aib(schema65, false));
		Schema schema66 = dataFixerBuilder.addSchema(1500, b);
		dataFixerBuilder.addFixer(new afg(schema66, false));
		Schema schema67 = dataFixerBuilder.addSchema(1501, b);
		dataFixerBuilder.addFixer(new aet(schema67, false));
		Schema schema68 = dataFixerBuilder.addSchema(1502, b);
		dataFixerBuilder.addFixer(new aix(schema68, false));
		Schema schema69 = dataFixerBuilder.addSchema(1506, b);
		dataFixerBuilder.addFixer(new aic(schema69, false));
		Schema schema70 = dataFixerBuilder.addSchema(1510, akx::new);
		dataFixerBuilder.addFixer(afl.a(schema70, "Block renamening fix", a(agy.b)));
		dataFixerBuilder.addFixer(ahp.a(schema70, "Item renamening fix", a(agy.c)));
		dataFixerBuilder.addFixer(new aiz(schema70, false));
		dataFixerBuilder.addFixer(new agy(schema70, true));
		dataFixerBuilder.addFixer(new ajn(schema70, false));
		Schema schema71 = dataFixerBuilder.addSchema(1514, b);
		dataFixerBuilder.addFixer(new ail(schema71, false));
		dataFixerBuilder.addFixer(new ajo(schema71, false));
		dataFixerBuilder.addFixer(new aim(schema71, false));
		Schema schema72 = dataFixerBuilder.addSchema(1515, b);
		dataFixerBuilder.addFixer(afl.a(schema72, "Rename coral fan blocks", a(ajd.a)));
		Schema schema73 = dataFixerBuilder.addSchema(1624, b);
		dataFixerBuilder.addFixer(new ajp(schema73, false));
		Schema schema74 = dataFixerBuilder.addSchema(1800, aky::new);
		dataFixerBuilder.addFixer(new aes(schema74, "Added 1.14 mobs fix", ajb.p));
		dataFixerBuilder.addFixer(ahp.a(schema74, "Rename dye items", a(afy.a)));
		Schema schema75 = dataFixerBuilder.addSchema(1801, akz::new);
		dataFixerBuilder.addFixer(new aes(schema75, "Added Illager Beast", ajb.p));
		Schema schema76 = dataFixerBuilder.addSchema(1802, b);
		dataFixerBuilder.addFixer(
			afl.a(
				schema76,
				"Rename sign blocks & stone slabs",
				a(
					ImmutableMap.of(
						"minecraft:stone_slab", "minecraft:smooth_stone_slab", "minecraft:sign", "minecraft:oak_sign", "minecraft:wall_sign", "minecraft:oak_wall_sign"
					)
				)
			)
		);
		dataFixerBuilder.addFixer(
			ahp.a(
				schema76,
				"Rename sign item & stone slabs",
				a(ImmutableMap.of("minecraft:stone_slab", "minecraft:smooth_stone_slab", "minecraft:sign", "minecraft:oak_sign"))
			)
		);
		Schema schema77 = dataFixerBuilder.addSchema(1803, b);
		dataFixerBuilder.addFixer(new ahn(schema77, false));
		Schema schema78 = dataFixerBuilder.addSchema(1904, ala::new);
		dataFixerBuilder.addFixer(new aes(schema78, "Added Cats", ajb.p));
		dataFixerBuilder.addFixer(new agb(schema78, false));
		Schema schema79 = dataFixerBuilder.addSchema(1905, b);
		dataFixerBuilder.addFixer(new aft(schema79, false));
		Schema schema80 = dataFixerBuilder.addSchema(1906, alb::new);
		dataFixerBuilder.addFixer(new aes(schema80, "Add POI Blocks", ajb.k));
		Schema schema81 = dataFixerBuilder.addSchema(1909, alc::new);
		dataFixerBuilder.addFixer(new aes(schema81, "Add jigsaw", ajb.k));
		Schema schema82 = dataFixerBuilder.addSchema(1911, b);
		dataFixerBuilder.addFixer(new afu(schema82, false));
		Schema schema83 = dataFixerBuilder.addSchema(1917, b);
		dataFixerBuilder.addFixer(new afp(schema83, false));
		Schema schema84 = dataFixerBuilder.addSchema(1918, b);
		dataFixerBuilder.addFixer(new ajq(schema84, "minecraft:villager"));
		dataFixerBuilder.addFixer(new ajq(schema84, "minecraft:zombie_villager"));
		Schema schema85 = dataFixerBuilder.addSchema(1920, ald::new);
		dataFixerBuilder.addFixer(new aik(schema85, false));
		dataFixerBuilder.addFixer(new aes(schema85, "Add campfire", ajb.k));
		Schema schema86 = dataFixerBuilder.addSchema(1925, b);
		dataFixerBuilder.addFixer(new aif(schema86, false));
		Schema schema87 = dataFixerBuilder.addSchema(1928, ale::new);
		dataFixerBuilder.addFixer(new agq(schema87, true));
		dataFixerBuilder.addFixer(ahp.a(schema87, "Rename ravager egg item", a(agq.a)));
		Schema schema88 = dataFixerBuilder.addSchema(1929, alf::new);
		dataFixerBuilder.addFixer(new aes(schema88, "Add Wandering Trader and Trader Llama", ajb.p));
		Schema schema89 = dataFixerBuilder.addSchema(1931, alg::new);
		dataFixerBuilder.addFixer(new aes(schema89, "Added Fox", ajb.p));
		Schema schema90 = dataFixerBuilder.addSchema(1936, b);
		dataFixerBuilder.addFixer(new aip(schema90, false));
		Schema schema91 = dataFixerBuilder.addSchema(1946, b);
		dataFixerBuilder.addFixer(new ajf(schema91, false));
		Schema schema92 = dataFixerBuilder.addSchema(1948, b);
		dataFixerBuilder.addFixer(new aio(schema92, false));
		Schema schema93 = dataFixerBuilder.addSchema(1953, b);
		dataFixerBuilder.addFixer(new ain(schema93, false));
		Schema schema94 = dataFixerBuilder.addSchema(1955, b);
		dataFixerBuilder.addFixer(new ajs(schema94, false));
		dataFixerBuilder.addFixer(new ajx(schema94, false));
		Schema schema95 = dataFixerBuilder.addSchema(1961, b);
		dataFixerBuilder.addFixer(new afr(schema95, false));
		Schema schema96 = dataFixerBuilder.addSchema(2100, alh::new);
		dataFixerBuilder.addFixer(new aes(schema96, "Added Bee and Bee Stinger", ajb.p));
		dataFixerBuilder.addFixer(new aes(schema96, "Add beehive", ajb.k));
		dataFixerBuilder.addFixer(new aiy(schema96, false, "Rename sugar recipe", a("minecraft:sugar", "sugar_from_sugar_cane")));
		dataFixerBuilder.addFixer(
			new aeu(schema96, false, "Rename sugar recipe advancement", a("minecraft:recipes/misc/sugar", "minecraft:recipes/misc/sugar_from_sugar_cane"))
		);
		Schema schema97 = dataFixerBuilder.addSchema(2202, b);
		dataFixerBuilder.addFixer(new afq(schema97, false));
		Schema schema98 = dataFixerBuilder.addSchema(2209, b);
		dataFixerBuilder.addFixer(ahp.a(schema98, "Rename bee_hive item to beehive", a("minecraft:bee_hive", "minecraft:beehive")));
		dataFixerBuilder.addFixer(new aey(schema98));
		dataFixerBuilder.addFixer(afl.a(schema98, "Rename bee_hive block to beehive", a("minecraft:bee_hive", "minecraft:beehive")));
		Schema schema99 = dataFixerBuilder.addSchema(2211, b);
		dataFixerBuilder.addFixer(new ajm(schema99, false));
		Schema schema100 = dataFixerBuilder.addSchema(2218, b);
		dataFixerBuilder.addFixer(new ahf(schema100, false));
		Schema schema101 = dataFixerBuilder.addSchema(2501, ali::new);
		dataFixerBuilder.addFixer(new ahg(schema101, true));
		Schema schema102 = dataFixerBuilder.addSchema(2502, alj::new);
		dataFixerBuilder.addFixer(new aes(schema102, "Added Hoglin", ajb.p));
		Schema schema103 = dataFixerBuilder.addSchema(2503, b);
		dataFixerBuilder.addFixer(new aju(schema103, false));
		dataFixerBuilder.addFixer(
			new aeu(schema103, false, "Composter category change", a("minecraft:recipes/misc/composter", "minecraft:recipes/decorations/composter"))
		);
		Schema schema104 = dataFixerBuilder.addSchema(2505, alk::new);
		dataFixerBuilder.addFixer(new aes(schema104, "Added Piglin", ajb.p));
		dataFixerBuilder.addFixer(new aig(schema104, "minecraft:villager"));
		Schema schema105 = dataFixerBuilder.addSchema(2508, b);
		dataFixerBuilder.addFixer(
			ahp.a(
				schema105,
				"Renamed fungi items to fungus",
				a(ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))
			)
		);
		dataFixerBuilder.addFixer(
			afl.a(
				schema105,
				"Renamed fungi blocks to fungus",
				a(ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))
			)
		);
		Schema schema106 = dataFixerBuilder.addSchema(2509, all::new);
		dataFixerBuilder.addFixer(new ahe(schema106));
		dataFixerBuilder.addFixer(ahp.a(schema106, "Rename zombie pigman egg item", a(ahe.a)));
		Schema schema107 = dataFixerBuilder.addSchema(2511, b);
		dataFixerBuilder.addFixer(new ago(schema107));
		Schema schema108 = dataFixerBuilder.addSchema(2514, b);
		dataFixerBuilder.addFixer(new aha(schema108));
		dataFixerBuilder.addFixer(new afj(schema108));
		dataFixerBuilder.addFixer(new aiv(schema108));
		dataFixerBuilder.addFixer(new aie(schema108));
		dataFixerBuilder.addFixer(new ajg(schema108));
		dataFixerBuilder.addFixer(new ahw(schema108));
		Schema schema109 = dataFixerBuilder.addSchema(2516, b);
		dataFixerBuilder.addFixer(new ahh(schema109, "minecraft:villager"));
		dataFixerBuilder.addFixer(new ahh(schema109, "minecraft:zombie_villager"));
		Schema schema110 = dataFixerBuilder.addSchema(2518, b);
		dataFixerBuilder.addFixer(new ahz(schema110, false));
		dataFixerBuilder.addFixer(new aia(schema110, false));
		Schema schema111 = dataFixerBuilder.addSchema(2519, alm::new);
		dataFixerBuilder.addFixer(new aes(schema111, "Added Strider", ajb.p));
		Schema schema112 = dataFixerBuilder.addSchema(2522, aln::new);
		dataFixerBuilder.addFixer(new aes(schema112, "Added Zoglin", ajb.p));
		Schema schema113 = dataFixerBuilder.addSchema(2523, b);
		dataFixerBuilder.addFixer(new aev(schema113));
		Schema schema114 = dataFixerBuilder.addSchema(2527, b);
		dataFixerBuilder.addFixer(new afa(schema114));
		Schema schema115 = dataFixerBuilder.addSchema(2528, b);
		dataFixerBuilder.addFixer(
			ahp.a(
				schema115,
				"Rename soul fire torch and soul fire lantern",
				a(ImmutableMap.of("minecraft:soul_fire_torch", "minecraft:soul_torch", "minecraft:soul_fire_lantern", "minecraft:soul_lantern"))
			)
		);
		dataFixerBuilder.addFixer(
			afl.a(
				schema115,
				"Rename soul fire torch and soul fire lantern",
				a(
					ImmutableMap.of(
						"minecraft:soul_fire_torch",
						"minecraft:soul_torch",
						"minecraft:soul_fire_wall_torch",
						"minecraft:soul_wall_torch",
						"minecraft:soul_fire_lantern",
						"minecraft:soul_lantern"
					)
				)
			)
		);
		Schema schema116 = dataFixerBuilder.addSchema(2529, b);
		dataFixerBuilder.addFixer(new ajl(schema116, false));
		Schema schema117 = dataFixerBuilder.addSchema(2531, b);
		dataFixerBuilder.addFixer(new aja(schema117));
		Schema schema118 = dataFixerBuilder.addSchema(2533, b);
		dataFixerBuilder.addFixer(new ajr(schema118));
		Schema schema119 = dataFixerBuilder.addSchema(2535, b);
		dataFixerBuilder.addFixer(new agv(schema119));
		Schema schema120 = dataFixerBuilder.addSchema(2550, b);
		dataFixerBuilder.addFixer(new ajv(schema120));
		Schema schema121 = dataFixerBuilder.addSchema(2551, alo::new);
		dataFixerBuilder.addFixer(new ajw(schema121, "add types to WorldGenData", ajb.y));
		Schema schema122 = dataFixerBuilder.addSchema(2552, b);
		dataFixerBuilder.addFixer(new ajc(schema122, false, "Nether biome rename", ImmutableMap.of("minecraft:nether", "minecraft:nether_wastes")));
		Schema schema123 = dataFixerBuilder.addSchema(2553, b);
		dataFixerBuilder.addFixer(new aez(schema123, false));
		Schema schema124 = dataFixerBuilder.addSchema(2558, b);
		dataFixerBuilder.addFixer(new aih(schema124, false));
		dataFixerBuilder.addFixer(new aiu(schema124, false, "Rename swapHands setting", "key_key.swapHands", "key_key.swapOffhand"));
	}

	private static UnaryOperator<String> a(Map<String, String> map) {
		return string -> (String)map.getOrDefault(string, string);
	}

	private static UnaryOperator<String> a(String string1, String string2) {
		return string3 -> Objects.equals(string3, string1) ? string2 : string3;
	}
}
