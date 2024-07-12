import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alc extends aka {
	public alc(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerBlockEntities(schema);
		schema.registerSimple(map3, "minecraft:jigsaw");
		return map3;
	}
}
