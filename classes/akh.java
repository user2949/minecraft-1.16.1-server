import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class akh extends Schema {
	public akh(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		schema.registerType(
			false,
			ajb.b,
			() -> DSL.optionalFields(
					"RootVehicle", DSL.optionalFields("Entity", ajb.o.in(schema)), "Inventory", DSL.list(ajb.l.in(schema)), "EnderItems", DSL.list(ajb.l.in(schema))
				)
		);
		schema.registerType(true, ajb.o, () -> DSL.optionalFields("Passengers", DSL.list(ajb.o.in(schema)), ajb.p.in(schema)));
	}
}
