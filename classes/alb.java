import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class alb extends aka {
	public alb(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerBlockEntities(schema);
		a(schema, map3, "minecraft:barrel");
		a(schema, map3, "minecraft:smoker");
		a(schema, map3, "minecraft:blast_furnace");
		schema.register(map3, "minecraft:lectern", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("Book", ajb.l.in(schema))));
		schema.registerSimple(map3, "minecraft:bell");
		return map3;
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)))));
	}
}
