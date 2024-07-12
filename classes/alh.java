import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alh extends aka {
	public alh(int integer, Schema schema) {
		super(integer, schema);
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> akb.a(schema)));
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerEntities(schema);
		a(schema, map3, "minecraft:bee");
		a(schema, map3, "minecraft:bee_stinger");
		return map3;
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerBlockEntities(schema);
		schema.register(
			map3,
			"minecraft:beehive",
			(Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)), "Bees", DSL.list(DSL.optionalFields("EntityData", ajb.o.in(schema)))))
		);
		return map3;
	}
}
