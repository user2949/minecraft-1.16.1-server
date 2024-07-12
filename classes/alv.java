import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class alv extends aka {
	protected static final HookFunction b = new HookFunction() {
		@Override
		public <T> T apply(DynamicOps<T> dynamicOps, T object) {
			return alx.a(new Dynamic<>(dynamicOps, object), alu.a, "minecraft:armor_stand");
		}
	};

	public alv(int integer, Schema schema) {
		super(integer, schema);
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> akb.a(schema)));
	}

	protected static void b(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inTile", ajb.q.in(schema))));
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = Maps.<String, Supplier<TypeTemplate>>newHashMap();
		schema.registerSimple(map3, "minecraft:area_effect_cloud");
		a(schema, map3, "minecraft:armor_stand");
		schema.register(map3, "minecraft:arrow", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("inTile", ajb.q.in(schema))));
		a(schema, map3, "minecraft:bat");
		a(schema, map3, "minecraft:blaze");
		schema.registerSimple(map3, "minecraft:boat");
		a(schema, map3, "minecraft:cave_spider");
		schema.register(
			map3,
			"minecraft:chest_minecart",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayTile", ajb.q.in(schema), "Items", DSL.list(ajb.l.in(schema))))
		);
		a(schema, map3, "minecraft:chicken");
		schema.register(map3, "minecraft:commandblock_minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayTile", ajb.q.in(schema))));
		a(schema, map3, "minecraft:cow");
		a(schema, map3, "minecraft:creeper");
		schema.register(
			map3,
			"minecraft:donkey",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)), "SaddleItem", ajb.l.in(schema), akb.a(schema)))
		);
		schema.registerSimple(map3, "minecraft:dragon_fireball");
		b(schema, map3, "minecraft:egg");
		a(schema, map3, "minecraft:elder_guardian");
		schema.registerSimple(map3, "minecraft:ender_crystal");
		a(schema, map3, "minecraft:ender_dragon");
		schema.register(map3, "minecraft:enderman", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("carried", ajb.q.in(schema), akb.a(schema))));
		a(schema, map3, "minecraft:endermite");
		b(schema, map3, "minecraft:ender_pearl");
		schema.registerSimple(map3, "minecraft:eye_of_ender_signal");
		schema.register(
			map3,
			"minecraft:falling_block",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("Block", ajb.q.in(schema), "TileEntityData", ajb.k.in(schema)))
		);
		b(schema, map3, "minecraft:fireball");
		schema.register(map3, "minecraft:fireworks_rocket", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("FireworksItem", ajb.l.in(schema))));
		schema.register(map3, "minecraft:furnace_minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayTile", ajb.q.in(schema))));
		a(schema, map3, "minecraft:ghast");
		a(schema, map3, "minecraft:giant");
		a(schema, map3, "minecraft:guardian");
		schema.register(
			map3,
			"minecraft:hopper_minecart",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayTile", ajb.q.in(schema), "Items", DSL.list(ajb.l.in(schema))))
		);
		schema.register(
			map3,
			"minecraft:horse",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields("ArmorItem", ajb.l.in(schema), "SaddleItem", ajb.l.in(schema), akb.a(schema)))
		);
		a(schema, map3, "minecraft:husk");
		schema.register(map3, "minecraft:item", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Item", ajb.l.in(schema))));
		schema.register(map3, "minecraft:item_frame", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Item", ajb.l.in(schema))));
		schema.registerSimple(map3, "minecraft:leash_knot");
		a(schema, map3, "minecraft:magma_cube");
		schema.register(map3, "minecraft:minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayTile", ajb.q.in(schema))));
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
		schema.register(
			map3, "minecraft:potion", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Potion", ajb.l.in(schema), "inTile", ajb.q.in(schema)))
		);
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
		b(schema, map3, "minecraft:small_fireball");
		b(schema, map3, "minecraft:snowball");
		a(schema, map3, "minecraft:snowman");
		schema.register(
			map3, "minecraft:spawner_minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayTile", ajb.q.in(schema), ajb.s.in(schema)))
		);
		schema.register(map3, "minecraft:spectral_arrow", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("inTile", ajb.q.in(schema))));
		a(schema, map3, "minecraft:spider");
		a(schema, map3, "minecraft:squid");
		a(schema, map3, "minecraft:stray");
		schema.registerSimple(map3, "minecraft:tnt");
		schema.register(map3, "minecraft:tnt_minecart", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayTile", ajb.q.in(schema))));
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
		a(schema, map3, "minecraft:witch");
		a(schema, map3, "minecraft:wither");
		a(schema, map3, "minecraft:wither_skeleton");
		b(schema, map3, "minecraft:wither_skull");
		a(schema, map3, "minecraft:wolf");
		b(schema, map3, "minecraft:xp_bottle");
		schema.registerSimple(map3, "minecraft:xp_orb");
		a(schema, map3, "minecraft:zombie");
		schema.register(map3, "minecraft:zombie_horse", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("SaddleItem", ajb.l.in(schema), akb.a(schema))));
		a(schema, map3, "minecraft:zombie_pigman");
		a(schema, map3, "minecraft:zombie_villager");
		schema.registerSimple(map3, "minecraft:evocation_fangs");
		a(schema, map3, "minecraft:evocation_illager");
		schema.registerSimple(map3, "minecraft:illusion_illager");
		schema.register(
			map3,
			"minecraft:llama",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields(
					"Items", DSL.list(ajb.l.in(schema)), "SaddleItem", ajb.l.in(schema), "DecorItem", ajb.l.in(schema), akb.a(schema)
				))
		);
		schema.registerSimple(map3, "minecraft:llama_spit");
		a(schema, map3, "minecraft:vex");
		a(schema, map3, "minecraft:vindication_illager");
		return map3;
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
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
					b,
					HookFunction.IDENTITY
				)
		);
	}
}
