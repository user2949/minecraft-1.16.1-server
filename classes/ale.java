import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class ale extends aka {
	public ale(int integer, Schema schema) {
		super(integer, schema);
	}

	protected static TypeTemplate a(Schema schema) {
		return DSL.optionalFields("ArmorItems", DSL.list(ajb.l.in(schema)), "HandItems", DSL.list(ajb.l.in(schema)));
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> a(schema)));
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerEntities(schema);
		map3.remove("minecraft:illager_beast");
		a(schema, map3, "minecraft:ravager");
		return map3;
	}
}
