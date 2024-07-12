import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class akm extends aka {
	public akm(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerEntities(schema);
		schema.registerSimple(map3, "minecraft:egg");
		schema.registerSimple(map3, "minecraft:ender_pearl");
		schema.registerSimple(map3, "minecraft:fireball");
		schema.register(map3, "minecraft:potion", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Potion", ajb.l.in(schema))));
		schema.registerSimple(map3, "minecraft:small_fireball");
		schema.registerSimple(map3, "minecraft:snowball");
		schema.registerSimple(map3, "minecraft:wither_skull");
		schema.registerSimple(map3, "minecraft:xp_bottle");
		schema.register(map3, "minecraft:arrow", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inBlockState", ajb.m.in(schema))));
		schema.register(map3, "minecraft:enderman", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("carriedBlockState", ajb.m.in(schema), akb.a(schema))));
		schema.register(
			map3, "minecraft:falling_block", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("BlockState", ajb.m.in(schema), "TileEntityData", ajb.k.in(schema)))
		);
		schema.register(map3, "minecraft:spectral_arrow", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("inBlockState", ajb.m.in(schema))));
		schema.register(
			map3, "minecraft:chest_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", ajb.m.in(schema), "Items", DSL.list(ajb.l.in(schema))))
		);
		schema.register(map3, "minecraft:commandblock_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", ajb.m.in(schema))));
		schema.register(map3, "minecraft:furnace_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", ajb.m.in(schema))));
		schema.register(
			map3, "minecraft:hopper_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", ajb.m.in(schema), "Items", DSL.list(ajb.l.in(schema))))
		);
		schema.register(map3, "minecraft:minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", ajb.m.in(schema))));
		schema.register(map3, "minecraft:spawner_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", ajb.m.in(schema), ajb.s.in(schema))));
		schema.register(map3, "minecraft:tnt_minecart", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("DisplayState", ajb.m.in(schema))));
		return map3;
	}
}
