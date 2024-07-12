import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class akq extends aka {
	public akq(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		schema.registerType(
			false,
			ajb.t,
			() -> DSL.optionalFields(
					"Children", DSL.list(DSL.optionalFields("CA", ajb.m.in(schema), "CB", ajb.m.in(schema), "CC", ajb.m.in(schema), "CD", ajb.m.in(schema)))
				)
		);
	}
}
