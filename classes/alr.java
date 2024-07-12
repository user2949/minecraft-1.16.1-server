import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alr extends Schema {
	public alr(int integer, Schema schema) {
		super(integer, schema);
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> akb.a(schema)));
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerEntities(schema);
		a(schema, map3, "WitherSkeleton");
		a(schema, map3, "Stray");
		return map3;
	}
}
