import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class alu extends Schema {
	protected static final Map<String, String> a = DataFixUtils.make(Maps.<String, String>newHashMap(), hashMap -> {
		hashMap.put("minecraft:furnace", "minecraft:furnace");
		hashMap.put("minecraft:lit_furnace", "minecraft:furnace");
		hashMap.put("minecraft:chest", "minecraft:chest");
		hashMap.put("minecraft:trapped_chest", "minecraft:chest");
		hashMap.put("minecraft:ender_chest", "minecraft:ender_chest");
		hashMap.put("minecraft:jukebox", "minecraft:jukebox");
		hashMap.put("minecraft:dispenser", "minecraft:dispenser");
		hashMap.put("minecraft:dropper", "minecraft:dropper");
		hashMap.put("minecraft:sign", "minecraft:sign");
		hashMap.put("minecraft:mob_spawner", "minecraft:mob_spawner");
		hashMap.put("minecraft:noteblock", "minecraft:noteblock");
		hashMap.put("minecraft:brewing_stand", "minecraft:brewing_stand");
		hashMap.put("minecraft:enhanting_table", "minecraft:enchanting_table");
		hashMap.put("minecraft:command_block", "minecraft:command_block");
		hashMap.put("minecraft:beacon", "minecraft:beacon");
		hashMap.put("minecraft:skull", "minecraft:skull");
		hashMap.put("minecraft:daylight_detector", "minecraft:daylight_detector");
		hashMap.put("minecraft:hopper", "minecraft:hopper");
		hashMap.put("minecraft:banner", "minecraft:banner");
		hashMap.put("minecraft:flower_pot", "minecraft:flower_pot");
		hashMap.put("minecraft:repeating_command_block", "minecraft:command_block");
		hashMap.put("minecraft:chain_command_block", "minecraft:command_block");
		hashMap.put("minecraft:shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:white_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:green_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:red_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:black_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:bed", "minecraft:bed");
		hashMap.put("minecraft:light_gray_shulker_box", "minecraft:shulker_box");
		hashMap.put("minecraft:banner", "minecraft:banner");
		hashMap.put("minecraft:white_banner", "minecraft:banner");
		hashMap.put("minecraft:orange_banner", "minecraft:banner");
		hashMap.put("minecraft:magenta_banner", "minecraft:banner");
		hashMap.put("minecraft:light_blue_banner", "minecraft:banner");
		hashMap.put("minecraft:yellow_banner", "minecraft:banner");
		hashMap.put("minecraft:lime_banner", "minecraft:banner");
		hashMap.put("minecraft:pink_banner", "minecraft:banner");
		hashMap.put("minecraft:gray_banner", "minecraft:banner");
		hashMap.put("minecraft:silver_banner", "minecraft:banner");
		hashMap.put("minecraft:cyan_banner", "minecraft:banner");
		hashMap.put("minecraft:purple_banner", "minecraft:banner");
		hashMap.put("minecraft:blue_banner", "minecraft:banner");
		hashMap.put("minecraft:brown_banner", "minecraft:banner");
		hashMap.put("minecraft:green_banner", "minecraft:banner");
		hashMap.put("minecraft:red_banner", "minecraft:banner");
		hashMap.put("minecraft:black_banner", "minecraft:banner");
		hashMap.put("minecraft:standing_sign", "minecraft:sign");
		hashMap.put("minecraft:wall_sign", "minecraft:sign");
		hashMap.put("minecraft:piston_head", "minecraft:piston");
		hashMap.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
		hashMap.put("minecraft:unpowered_comparator", "minecraft:comparator");
		hashMap.put("minecraft:powered_comparator", "minecraft:comparator");
		hashMap.put("minecraft:wall_banner", "minecraft:banner");
		hashMap.put("minecraft:standing_banner", "minecraft:banner");
		hashMap.put("minecraft:structure_block", "minecraft:structure_block");
		hashMap.put("minecraft:end_portal", "minecraft:end_portal");
		hashMap.put("minecraft:end_gateway", "minecraft:end_gateway");
		hashMap.put("minecraft:sign", "minecraft:sign");
		hashMap.put("minecraft:shield", "minecraft:banner");
	});
	protected static final HookFunction b = new HookFunction() {
		@Override
		public <T> T apply(DynamicOps<T> dynamicOps, T object) {
			return alx.a(new Dynamic<>(dynamicOps, object), alu.a, "ArmorStand");
		}
	};

	public alu(int integer, Schema schema) {
		super(integer, schema);
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)))));
	}

	@Override
	public Type<?> getChoiceType(TypeReference typeReference, String string) {
		return Objects.equals(typeReference.typeName(), ajb.k.typeName())
			? super.getChoiceType(typeReference, aka.a(string))
			: super.getChoiceType(typeReference, string);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = Maps.<String, Supplier<TypeTemplate>>newHashMap();
		a(schema, map3, "minecraft:furnace");
		a(schema, map3, "minecraft:chest");
		schema.registerSimple(map3, "minecraft:ender_chest");
		schema.register(map3, "minecraft:jukebox", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("RecordItem", ajb.l.in(schema))));
		a(schema, map3, "minecraft:dispenser");
		a(schema, map3, "minecraft:dropper");
		schema.registerSimple(map3, "minecraft:sign");
		schema.register(map3, "minecraft:mob_spawner", (Function<String, TypeTemplate>)(string -> ajb.s.in(schema)));
		schema.registerSimple(map3, "minecraft:noteblock");
		schema.registerSimple(map3, "minecraft:piston");
		a(schema, map3, "minecraft:brewing_stand");
		schema.registerSimple(map3, "minecraft:enchanting_table");
		schema.registerSimple(map3, "minecraft:end_portal");
		schema.registerSimple(map3, "minecraft:beacon");
		schema.registerSimple(map3, "minecraft:skull");
		schema.registerSimple(map3, "minecraft:daylight_detector");
		a(schema, map3, "minecraft:hopper");
		schema.registerSimple(map3, "minecraft:comparator");
		schema.register(
			map3, "minecraft:flower_pot", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), ajb.r.in(schema))))
		);
		schema.registerSimple(map3, "minecraft:banner");
		schema.registerSimple(map3, "minecraft:structure_block");
		schema.registerSimple(map3, "minecraft:end_gateway");
		schema.registerSimple(map3, "minecraft:command_block");
		return map3;
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		schema.registerType(false, ajb.k, () -> DSL.taggedChoiceLazy("id", aka.a(), map3));
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
