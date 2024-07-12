import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class akt extends aka {
	public akt(int integer, Schema schema) {
		super(integer, schema);
	}

	protected static void a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
		schema.register(map, string, (Supplier<TypeTemplate>)(() -> akb.a(schema)));
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerEntities(schema);
		a(schema, map3, "minecraft:turtle");
		a(schema, map3, "minecraft:cod_mob");
		a(schema, map3, "minecraft:tropical_fish");
		a(schema, map3, "minecraft:salmon_mob");
		a(schema, map3, "minecraft:puffer_fish");
		a(schema, map3, "minecraft:phantom");
		a(schema, map3, "minecraft:dolphin");
		a(schema, map3, "minecraft:drowned");
		schema.register(map3, "minecraft:trident", (Function<String, TypeTemplate>)(string -> DSL.optionalFields("inBlockState", ajb.m.in(schema))));
		return map3;
	}
}
