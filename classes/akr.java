import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class akr extends aka {
	public akr(int integer, Schema schema) {
		super(integer, schema);
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> akb.a(schema)));
	}

	protected static void b(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)))));
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = Maps.<String, Supplier<TypeTemplate>>newHashMap();
		schema.registerSimple(map3, "minecraft:area_effect_cloud");
		a(schema, map3, "minecraft:armor_stand");
		schema.register(map3, "minecraft:arrow", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("inBlockState", ajb.m.in(schema))));
		a(schema, map3, "minecraft:bat");
		a(schema, map3, "minecraft:blaze");
		schema.registerSimple(map3, "minecraft:boat");
		a(schema, map3, "minecraft:cave_spider");
		schema.register(
			map3,
			"minecraft:chest_minecart",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayState", ajb.m.in(schema), "Items", DSL.list(ajb.l.in(schema))))
		);
		a(schema, map3, "minecraft:chicken");
		schema.register(map3, "minecraft:commandblock_minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayState", ajb.m.in(schema))));
		a(schema, map3, "minecraft:cow");
		a(schema, map3, "minecraft:creeper");
		schema.register(
			map3,
			"minecraft:donkey",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)), "SaddleItem", ajb.l.in(schema), akb.a(schema)))
		);
		schema.registerSimple(map3, "minecraft:dragon_fireball");
		schema.registerSimple(map3, "minecraft:egg");
		a(schema, map3, "minecraft:elder_guardian");
		schema.registerSimple(map3, "minecraft:ender_crystal");
		a(schema, map3, "minecraft:ender_dragon");
		schema.register(
			map3, "minecraft:enderman", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("carriedBlockState", ajb.m.in(schema), akb.a(schema)))
		);
		a(schema, map3, "minecraft:endermite");
		schema.registerSimple(map3, "minecraft:ender_pearl");
		schema.registerSimple(map3, "minecraft:evocation_fangs");
		a(schema, map3, "minecraft:evocation_illager");
		schema.registerSimple(map3, "minecraft:eye_of_ender_signal");
		schema.register(
			map3,
			"minecraft:falling_block",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("BlockState", ajb.m.in(schema), "TileEntityData", ajb.k.in(schema)))
		);
		schema.registerSimple(map3, "minecraft:fireball");
		schema.register(map3, "minecraft:fireworks_rocket", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("FireworksItem", ajb.l.in(schema))));
		schema.register(map3, "minecraft:furnace_minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayState", ajb.m.in(schema))));
		a(schema, map3, "minecraft:ghast");
		a(schema, map3, "minecraft:giant");
		a(schema, map3, "minecraft:guardian");
		schema.register(
			map3,
			"minecraft:hopper_minecart",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayState", ajb.m.in(schema), "Items", DSL.list(ajb.l.in(schema))))
		);
		schema.register(
			map3,
			"minecraft:horse",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("ArmorItem", ajb.l.in(schema), "SaddleItem", ajb.l.in(schema), akb.a(schema)))
		);
		a(schema, map3, "minecraft:husk");
		schema.registerSimple(map3, "minecraft:illusion_illager");
		schema.register(map3, "minecraft:item", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Item", ajb.l.in(schema))));
		schema.register(map3, "minecraft:item_frame", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Item", ajb.l.in(schema))));
		schema.registerSimple(map3, "minecraft:leash_knot");
		schema.register(
			map3,
			"minecraft:llama",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields(
					"Items", DSL.list(ajb.l.in(schema)), "SaddleItem", ajb.l.in(schema), "DecorItem", ajb.l.in(schema), akb.a(schema)
				))
		);
		schema.registerSimple(map3, "minecraft:llama_spit");
		a(schema, map3, "minecraft:magma_cube");
		schema.register(map3, "minecraft:minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayState", ajb.m.in(schema))));
		a(schema, map3, "minecraft:mooshroom");
		schema.register(
			map3,
			"minecraft:mule",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)), "SaddleItem", ajb.l.in(schema), akb.a(schema)))
		);
		a(schema, map3, "minecraft:ocelot");
		schema.registerSimple(map3, "minecraft:painting");
		schema.registerSimple(map3, "minecraft:parrot");
		a(schema, map3, "minecraft:pig");
		a(schema, map3, "minecraft:polar_bear");
		schema.register(map3, "minecraft:potion", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Potion", ajb.l.in(schema))));
		a(schema, map3, "minecraft:rabbit");
		a(schema, map3, "minecraft:sheep");
		a(schema, map3, "minecraft:shulker");
		schema.registerSimple(map3, "minecraft:shulker_bullet");
		a(schema, map3, "minecraft:silverfish");
		a(schema, map3, "minecraft:skeleton");
		schema.register(
			map3, "minecraft:skeleton_horse", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("SaddleItem", ajb.l.in(schema), akb.a(schema)))
		);
		a(schema, map3, "minecraft:slime");
		schema.registerSimple(map3, "minecraft:small_fireball");
		schema.registerSimple(map3, "minecraft:snowball");
		a(schema, map3, "minecraft:snowman");
		schema.register(
			map3, "minecraft:spawner_minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayState", ajb.m.in(schema), ajb.s.in(schema)))
		);
		schema.register(map3, "minecraft:spectral_arrow", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("inBlockState", ajb.m.in(schema))));
		a(schema, map3, "minecraft:spider");
		a(schema, map3, "minecraft:squid");
		a(schema, map3, "minecraft:stray");
		schema.registerSimple(map3, "minecraft:tnt");
		schema.register(map3, "minecraft:tnt_minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayState", ajb.m.in(schema))));
		a(schema, map3, "minecraft:vex");
		schema.register(
			map3,
			"minecraft:villager",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields(
					"Inventory",
					DSL.list(ajb.l.in(schema)),
					"Offers",
					DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", ajb.l.in(schema), "buyB", ajb.l.in(schema), "sell", ajb.l.in(schema)))),
					akb.a(schema)
				))
		);
		a(schema, map3, "minecraft:villager_golem");
		a(schema, map3, "minecraft:vindication_illager");
		a(schema, map3, "minecraft:witch");
		a(schema, map3, "minecraft:wither");
		a(schema, map3, "minecraft:wither_skeleton");
		schema.registerSimple(map3, "minecraft:wither_skull");
		a(schema, map3, "minecraft:wolf");
		schema.registerSimple(map3, "minecraft:xp_bottle");
		schema.registerSimple(map3, "minecraft:xp_orb");
		a(schema, map3, "minecraft:zombie");
		schema.register(map3, "minecraft:zombie_horse", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("SaddleItem", ajb.l.in(schema), akb.a(schema))));
		a(schema, map3, "minecraft:zombie_pigman");
		a(schema, map3, "minecraft:zombie_villager");
		return map3;
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = Maps.<String, Supplier<TypeTemplate>>newHashMap();
		b(schema, map3, "minecraft:furnace");
		b(schema, map3, "minecraft:chest");
		b(schema, map3, "minecraft:trapped_chest");
		schema.registerSimple(map3, "minecraft:ender_chest");
		schema.register(map3, "minecraft:jukebox", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("RecordItem", ajb.l.in(schema))));
		b(schema, map3, "minecraft:dispenser");
		b(schema, map3, "minecraft:dropper");
		schema.registerSimple(map3, "minecraft:sign");
		schema.register(map3, "minecraft:mob_spawner", (Function<String, TypeTemplate>)(string -> ajb.s.in(schema)));
		schema.register(map3, "minecraft:piston", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("blockState", ajb.m.in(schema))));
		b(schema, map3, "minecraft:brewing_stand");
		schema.registerSimple(map3, "minecraft:enchanting_table");
		schema.registerSimple(map3, "minecraft:end_portal");
		schema.registerSimple(map3, "minecraft:beacon");
		schema.registerSimple(map3, "minecraft:skull");
		schema.registerSimple(map3, "minecraft:daylight_detector");
		b(schema, map3, "minecraft:hopper");
		schema.registerSimple(map3, "minecraft:comparator");
		schema.registerSimple(map3, "minecraft:banner");
		schema.registerSimple(map3, "minecraft:structure_block");
		schema.registerSimple(map3, "minecraft:end_gateway");
		schema.registerSimple(map3, "minecraft:command_block");
		b(schema, map3, "minecraft:shulker_box");
		schema.registerSimple(map3, "minecraft:bed");
		return map3;
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		schema.registerType(false, ajb.a, DSL::remainder);
		schema.registerType(false, ajb.w, () -> DSL.constType(a()));
		schema.registerType(
			false,
			ajb.b,
			() -> DSL.optionalFields(
					"RootVehicle",
					DSL.optionalFields("Entity", ajb.o.in(schema)),
					"Inventory",
					DSL.list(ajb.l.in(schema)),
					"EnderItems",
					DSL.list(ajb.l.in(schema)),
					DSL.optionalFields(
						"ShoulderEntityLeft",
						ajb.o.in(schema),
						"ShoulderEntityRight",
						ajb.o.in(schema),
						"recipeBook",
						DSL.optionalFields("recipes", DSL.list(ajb.w.in(schema)), "toBeDisplayed", DSL.list(ajb.w.in(schema)))
					)
				)
		);
		schema.registerType(
			false,
			ajb.c,
			() -> DSL.fields(
					"Level",
					DSL.optionalFields(
						"Entities",
						DSL.list(ajb.o.in(schema)),
						"TileEntities",
						DSL.list(ajb.k.in(schema)),
						"TileTicks",
						DSL.list(DSL.fields("i", ajb.q.in(schema))),
						"Sections",
						DSL.list(DSL.optionalFields("Palette", DSL.list(ajb.m.in(schema))))
					)
				)
		);
		schema.registerType(true, ajb.k, () -> DSL.taggedChoiceLazy("id", a(), map3));
		schema.registerType(true, ajb.o, () -> DSL.optionalFields("Passengers", DSL.list(ajb.o.in(schema)), ajb.p.in(schema)));
		schema.registerType(true, ajb.p, () -> DSL.taggedChoiceLazy("id", a(), map2));
		schema.registerType(
			true,
			ajb.l,
			() -> DSL.hook(
					DSL.optionalFields(
						"id",
						ajb.r.in(schema),
						"tag",
						DSL.optionalFields(
							"EntityTag", ajb.o.in(schema), "BlockEntityTag", ajb.k.in(schema), "CanDestroy", DSL.list(ajb.q.in(schema)), "CanPlaceOn", DSL.list(ajb.q.in(schema))
						)
					),
					alv.b,
					HookFunction.IDENTITY
				)
		);
		schema.registerType(false, ajb.d, () -> DSL.compoundList(DSL.list(ajb.l.in(schema))));
		schema.registerType(false, ajb.e, DSL::remainder);
		schema.registerType(
			false,
			ajb.f,
			() -> DSL.optionalFields(
					"entities",
					DSL.list(DSL.optionalFields("nbt", ajb.o.in(schema))),
					"blocks",
					DSL.list(DSL.optionalFields("nbt", ajb.k.in(schema))),
					"palette",
					DSL.list(ajb.m.in(schema))
				)
		);
		schema.registerType(false, ajb.q, () -> DSL.constType(a()));
		schema.registerType(false, ajb.r, () -> DSL.constType(a()));
		schema.registerType(false, ajb.m, DSL::remainder);
		Supplier<TypeTemplate> supplier5 = () -> DSL.compoundList(ajb.r.in(schema), DSL.constType(DSL.intType()));
		schema.registerType(
			false,
			ajb.g,
			() -> DSL.optionalFields(
					"stats",
					DSL.optionalFields(
						"minecraft:mined",
						DSL.compoundList(ajb.q.in(schema), DSL.constType(DSL.intType())),
						"minecraft:crafted",
						(TypeTemplate)supplier5.get(),
						"minecraft:used",
						(TypeTemplate)supplier5.get(),
						"minecraft:broken",
						(TypeTemplate)supplier5.get(),
						"minecraft:picked_up",
						(TypeTemplate)supplier5.get(),
						DSL.optionalFields(
							"minecraft:dropped",
							(TypeTemplate)supplier5.get(),
							"minecraft:killed",
							DSL.compoundList(ajb.n.in(schema), DSL.constType(DSL.intType())),
							"minecraft:killed_by",
							DSL.compoundList(ajb.n.in(schema), DSL.constType(DSL.intType())),
							"minecraft:custom",
							DSL.compoundList(DSL.constType(a()), DSL.constType(DSL.intType()))
						)
					)
				)
		);
		schema.registerType(
			false,
			ajb.h,
			() -> DSL.optionalFields(
					"data", DSL.optionalFields("Features", DSL.compoundList(ajb.t.in(schema)), "Objectives", DSL.list(ajb.u.in(schema)), "Teams", DSL.list(ajb.v.in(schema)))
				)
		);
		schema.registerType(
			false,
			ajb.t,
			() -> DSL.optionalFields(
					"Children", DSL.list(DSL.optionalFields("CA", ajb.m.in(schema), "CB", ajb.m.in(schema), "CC", ajb.m.in(schema), "CD", ajb.m.in(schema)))
				)
		);
		schema.registerType(false, ajb.u, DSL::remainder);
		schema.registerType(false, ajb.v, DSL::remainder);
		schema.registerType(true, ajb.s, () -> DSL.optionalFields("SpawnPotentials", DSL.list(DSL.fields("Entity", ajb.o.in(schema))), "SpawnData", ajb.o.in(schema)));
		schema.registerType(
			false,
			ajb.i,
			() -> DSL.optionalFields(
					"minecraft:adventure/adventuring_time",
					DSL.optionalFields("criteria", DSL.compoundList(ajb.x.in(schema), DSL.constType(DSL.string()))),
					"minecraft:adventure/kill_a_mob",
					DSL.optionalFields("criteria", DSL.compoundList(ajb.n.in(schema), DSL.constType(DSL.string()))),
					"minecraft:adventure/kill_all_mobs",
					DSL.optionalFields("criteria", DSL.compoundList(ajb.n.in(schema), DSL.constType(DSL.string()))),
					"minecraft:husbandry/bred_all_animals",
					DSL.optionalFields("criteria", DSL.compoundList(ajb.n.in(schema), DSL.constType(DSL.string())))
				)
		);
		schema.registerType(false, ajb.x, () -> DSL.constType(a()));
		schema.registerType(false, ajb.n, () -> DSL.constType(a()));
		schema.registerType(false, ajb.j, DSL::remainder);
		schema.registerType(true, ajb.y, DSL::remainder);
	}
}
