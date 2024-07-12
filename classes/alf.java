import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class alf extends aka {
	public alf(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerEntities(schema);
		schema.register(
			map3,
			"minecraft:wandering_trader",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields(
					"Inventory",
					DSL.list(ajb.l.in(schema)),
					"Offers",
					DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", ajb.l.in(schema), "buyB", ajb.l.in(schema), "sell", ajb.l.in(schema)))),
					akb.a(schema)
				))
		);
		schema.register(
			map3,
			"minecraft:trader_llama",
			(Function<String, TypeTemplate>)(string -> DSL.optionalFields(
					"Items", DSL.list(ajb.l.in(schema)), "SaddleItem", ajb.l.in(schema), "DecorItem", ajb.l.in(schema), akb.a(schema)
				))
		);
		return map3;
	}
}
