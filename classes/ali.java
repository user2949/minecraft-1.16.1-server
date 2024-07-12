import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class ali extends aka {
	public ali(int integer, Schema schema) {
		super(integer, schema);
	}

	private static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(
			map,
			string,
			(Supplier<TypeTemplate>)(() -> DSL.optionalFields(
					"Items", DSL.list(ajb.l.in(schema)), "RecipesUsed", DSL.compoundList(ajb.w.in(schema), DSL.constType(DSL.intType()))
				))
		);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerBlockEntities(schema);
		a(schema, map3, "minecraft:furnace");
		a(schema, map3, "minecraft:smoker");
		a(schema, map3, "minecraft:blast_furnace");
		return map3;
	}
}
