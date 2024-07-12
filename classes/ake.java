import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class ake extends Schema {
	public ake(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		schema.registerType(true, ajb.s, () -> DSL.optionalFields("SpawnPotentials", DSL.list(DSL.fields("Entity", ajb.o.in(schema))), "SpawnData", ajb.o.in(schema)));
	}
}
