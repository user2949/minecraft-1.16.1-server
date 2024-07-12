import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class ako extends aka {
	public ako(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerBlockEntities(schema);
		map3.remove("minecraft:flower_pot");
		map3.remove("minecraft:noteblock");
		return map3;
	}
}
