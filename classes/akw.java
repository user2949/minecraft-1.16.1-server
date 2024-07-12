import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class akw extends aka {
	public akw(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerEntities(schema);
		map3.put("minecraft:cod", map3.remove("minecraft:cod_mob"));
		map3.put("minecraft:salmon", map3.remove("minecraft:salmon_mob"));
		return map3;
	}
}
