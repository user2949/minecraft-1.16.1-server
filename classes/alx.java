import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class alx extends Schema {
	private static final Logger b = LogManager.getLogger();
	private static final Map<String, String> c = DataFixUtils.make(Maps.<String, String>newHashMap(), hashMap -> {
		hashMap.put("minecraft:furnace", "Furnace");
		hashMap.put("minecraft:lit_furnace", "Furnace");
		hashMap.put("minecraft:chest", "Chest");
		hashMap.put("minecraft:trapped_chest", "Chest");
		hashMap.put("minecraft:ender_chest", "EnderChest");
		hashMap.put("minecraft:jukebox", "RecordPlayer");
		hashMap.put("minecraft:dispenser", "Trap");
		hashMap.put("minecraft:dropper", "Dropper");
		hashMap.put("minecraft:sign", "Sign");
		hashMap.put("minecraft:mob_spawner", "MobSpawner");
		hashMap.put("minecraft:noteblock", "Music");
		hashMap.put("minecraft:brewing_stand", "Cauldron");
		hashMap.put("minecraft:enhanting_table", "EnchantTable");
		hashMap.put("minecraft:command_block", "CommandBlock");
		hashMap.put("minecraft:beacon", "Beacon");
		hashMap.put("minecraft:skull", "Skull");
		hashMap.put("minecraft:daylight_detector", "DLDetector");
		hashMap.put("minecraft:hopper", "Hopper");
		hashMap.put("minecraft:banner", "Banner");
		hashMap.put("minecraft:flower_pot", "FlowerPot");
		hashMap.put("minecraft:repeating_command_block", "CommandBlock");
		hashMap.put("minecraft:chain_command_block", "CommandBlock");
		hashMap.put("minecraft:standing_sign", "Sign");
		hashMap.put("minecraft:wall_sign", "Sign");
		hashMap.put("minecraft:piston_head", "Piston");
		hashMap.put("minecraft:daylight_detector_inverted", "DLDetector");
		hashMap.put("minecraft:unpowered_comparator", "Comparator");
		hashMap.put("minecraft:powered_comparator", "Comparator");
		hashMap.put("minecraft:wall_banner", "Banner");
		hashMap.put("minecraft:standing_banner", "Banner");
		hashMap.put("minecraft:structure_block", "Structure");
		hashMap.put("minecraft:end_portal", "Airportal");
		hashMap.put("minecraft:end_gateway", "EndGateway");
		hashMap.put("minecraft:shield", "Banner");
	});
	protected static final HookFunction a = new HookFunction() {
		@Override
		public <T> T apply(DynamicOps<T> dynamicOps, T object) {
			return alx.a(new Dynamic<>(dynamicOps, object), alx.c, "ArmorStand");
		}
	};

	public alx(int integer, Schema schema) {
		super(integer, schema);
	}

	protected static TypeTemplate a(Schema schema) {
		return DSL.optionalFields("Equipment", DSL.list(ajb.l.in(schema)));
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> a(schema)));
	}

	protected static void b(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inTile", ajb.q.in(schema))));
	}

	protected static void c(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayTile", ajb.q.in(schema))));
	}

	protected static void d(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)))));
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = Maps.<String, Supplier<TypeTemplate>>newHashMap();
		schema.register(map3, "Item", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Item", ajb.l.in(schema))));
		schema.registerSimple(map3, "XPOrb");
		b(schema, map3, "ThrownEgg");
		schema.registerSimple(map3, "LeashKnot");
		schema.registerSimple(map3, "Painting");
		schema.register(map3, "Arrow", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("inTile", ajb.q.in(schema))));
		schema.register(map3, "TippedArrow", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("inTile", ajb.q.in(schema))));
		schema.register(map3, "SpectralArrow", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("inTile", ajb.q.in(schema))));
		b(schema, map3, "Snowball");
		b(schema, map3, "Fireball");
		b(schema, map3, "SmallFireball");
		b(schema, map3, "ThrownEnderpearl");
		schema.registerSimple(map3, "EyeOfEnderSignal");
		schema.register(map3, "ThrownPotion", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("inTile", ajb.q.in(schema), "Potion", ajb.l.in(schema))));
		b(schema, map3, "ThrownExpBottle");
		schema.register(map3, "ItemFrame", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Item", ajb.l.in(schema))));
		b(schema, map3, "WitherSkull");
		schema.registerSimple(map3, "PrimedTnt");
		schema.register(
			map3, "FallingSand", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Block", ajb.q.in(schema), "TileEntityData", ajb.k.in(schema)))
		);
		schema.register(map3, "FireworksRocketEntity", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("FireworksItem", ajb.l.in(schema))));
		schema.registerSimple(map3, "Boat");
		schema.register(map3, "Minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayTile", ajb.q.in(schema), "Items", DSL.list(ajb.l.in(schema)))));
		c(schema, map3, "MinecartRideable");
		schema.register(
			map3, "MinecartChest", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayTile", ajb.q.in(schema), "Items", DSL.list(ajb.l.in(schema))))
		);
		c(schema, map3, "MinecartFurnace");
		c(schema, map3, "MinecartTNT");
		schema.register(map3, "MinecartSpawner", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayTile", ajb.q.in(schema), ajb.s.in(schema))));
		schema.register(
			map3, "MinecartHopper", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("DisplayTile", ajb.q.in(schema), "Items", DSL.list(ajb.l.in(schema))))
		);
		c(schema, map3, "MinecartCommandBlock");
		a(schema, map3, "ArmorStand");
		a(schema, map3, "Creeper");
		a(schema, map3, "Skeleton");
		a(schema, map3, "Spider");
		a(schema, map3, "Giant");
		a(schema, map3, "Zombie");
		a(schema, map3, "Slime");
		a(schema, map3, "Ghast");
		a(schema, map3, "PigZombie");
		schema.register(map3, "Enderman", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("carried", ajb.q.in(schema), a(schema))));
		a(schema, map3, "CaveSpider");
		a(schema, map3, "Silverfish");
		a(schema, map3, "Blaze");
		a(schema, map3, "LavaSlime");
		a(schema, map3, "EnderDragon");
		a(schema, map3, "WitherBoss");
		a(schema, map3, "Bat");
		a(schema, map3, "Witch");
		a(schema, map3, "Endermite");
		a(schema, map3, "Guardian");
		a(schema, map3, "Pig");
		a(schema, map3, "Sheep");
		a(schema, map3, "Cow");
		a(schema, map3, "Chicken");
		a(schema, map3, "Squid");
		a(schema, map3, "Wolf");
		a(schema, map3, "MushroomCow");
		a(schema, map3, "SnowMan");
		a(schema, map3, "Ozelot");
		a(schema, map3, "VillagerGolem");
		schema.register(
			map3,
			"EntityHorse",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields(
					"Items", DSL.list(ajb.l.in(schema)), "ArmorItem", ajb.l.in(schema), "SaddleItem", ajb.l.in(schema), a(schema)
				))
		);
		a(schema, map3, "Rabbit");
		schema.register(
			map3,
			"Villager",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields(
					"Inventory",
					DSL.list(ajb.l.in(schema)),
					"Offers",
					DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", ajb.l.in(schema), "buyB", ajb.l.in(schema), "sell", ajb.l.in(schema)))),
					a(schema)
				))
		);
		schema.registerSimple(map3, "EnderCrystal");
		schema.registerSimple(map3, "AreaEffectCloud");
		schema.registerSimple(map3, "ShulkerBullet");
		a(schema, map3, "Shulker");
		return map3;
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = Maps.<String, Supplier<TypeTemplate>>newHashMap();
		d(schema, map3, "Furnace");
		d(schema, map3, "Chest");
		schema.registerSimple(map3, "EnderChest");
		schema.register(map3, "RecordPlayer", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("RecordItem", ajb.l.in(schema))));
		d(schema, map3, "Trap");
		d(schema, map3, "Dropper");
		schema.registerSimple(map3, "Sign");
		schema.register(map3, "MobSpawner", (Function<String, TypeTemplate>)(string -> ajb.s.in(schema)));
		schema.registerSimple(map3, "Music");
		schema.registerSimple(map3, "Piston");
		d(schema, map3, "Cauldron");
		schema.registerSimple(map3, "EnchantTable");
		schema.registerSimple(map3, "Airportal");
		schema.registerSimple(map3, "Control");
		schema.registerSimple(map3, "Beacon");
		schema.registerSimple(map3, "Skull");
		schema.registerSimple(map3, "DLDetector");
		d(schema, map3, "Hopper");
		schema.registerSimple(map3, "Comparator");
		schema.register(
			map3, "FlowerPot", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), ajb.r.in(schema))))
		);
		schema.registerSimple(map3, "Banner");
		schema.registerSimple(map3, "Structure");
		schema.registerSimple(map3, "EndGateway");
		return map3;
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		schema.registerType(false, ajb.a, DSL::remainder);
		schema.registerType(false, ajb.b, () -> DSL.optionalFields("Inventory", DSL.list(ajb.l.in(schema)), "EnderItems", DSL.list(ajb.l.in(schema))));
		schema.registerType(
			false,
			ajb.c,
			() -> DSL.fields(
					"Level",
					DSL.optionalFields(
						"Entities", DSL.list(ajb.o.in(schema)), "TileEntities", DSL.list(ajb.k.in(schema)), "TileTicks", DSL.list(DSL.fields("i", ajb.q.in(schema)))
					)
				)
		);
		schema.registerType(true, ajb.k, () -> DSL.taggedChoiceLazy("id", DSL.string(), map3));
		schema.registerType(true, ajb.o, () -> DSL.optionalFields("Riding", ajb.o.in(schema), ajb.p.in(schema)));
		schema.registerType(false, ajb.n, () -> DSL.constType(aka.a()));
		schema.registerType(true, ajb.p, () -> DSL.taggedChoiceLazy("id", DSL.string(), map2));
		schema.registerType(
			true,
			ajb.l,
			() -> DSL.hook(
					DSL.optionalFields(
						"id",
						DSL.or(DSL.constType(DSL.intType()), ajb.r.in(schema)),
						"tag",
						DSL.optionalFields(
							"EntityTag", ajb.o.in(schema), "BlockEntityTag", ajb.k.in(schema), "CanDestroy", DSL.list(ajb.q.in(schema)), "CanPlaceOn", DSL.list(ajb.q.in(schema))
						)
					),
					a,
					HookFunction.IDENTITY
				)
		);
		schema.registerType(false, ajb.e, DSL::remainder);
		schema.registerType(false, ajb.q, () -> DSL.or(DSL.constType(DSL.intType()), DSL.constType(aka.a())));
		schema.registerType(false, ajb.r, () -> DSL.constType(aka.a()));
		schema.registerType(false, ajb.g, DSL::remainder);
		schema.registerType(
			false,
			ajb.h,
			() -> DSL.optionalFields(
					"data", DSL.optionalFields("Features", DSL.compoundList(ajb.t.in(schema)), "Objectives", DSL.list(ajb.u.in(schema)), "Teams", DSL.list(ajb.v.in(schema)))
				)
		);
		schema.registerType(false, ajb.t, DSL::remainder);
		schema.registerType(false, ajb.u, DSL::remainder);
		schema.registerType(false, ajb.v, DSL::remainder);
		schema.registerType(true, ajb.s, DSL::remainder);
		schema.registerType(false, ajb.j, DSL::remainder);
		schema.registerType(true, ajb.y, DSL::remainder);
	}

	protected static <T> T a(Dynamic<T> dynamic, Map<String, String> map, String string) {
		return dynamic.update("tag", dynamic4 -> dynamic4.update("BlockEntityTag", dynamic3 -> {
				String string4 = dynamic.get("id").asString("");
				String string5 = (String)map.get(aka.a(string4));
				if (string5 == null) {
					b.warn("Unable to resolve BlockEntity for ItemStack: {}", string4);
					return dynamic3;
				} else {
					return dynamic3.set("id", dynamic.createString(string5));
				}
			}).update("EntityTag", dynamic3 -> {
				String string4 = dynamic.get("id").asString("");
				return Objects.equals(aka.a(string4), "minecraft:armor_stand") ? dynamic3.set("id", dynamic.createString(string)) : dynamic3;
			})).getValue();
	}
}
