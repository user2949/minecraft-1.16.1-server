import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class akd extends Schema {
	public akd(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		schema.registerType(false, ajb.w, () -> DSL.constType(aka.a()));
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
		schema.registerType(false, ajb.d, () -> DSL.compoundList(DSL.list(ajb.l.in(schema))));
	}
}
