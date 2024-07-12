import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;

public class afu extends DataFix {
	private static final Map<String, String> a = ImmutableMap.<String, String>builder()
		.put("structure_references", "empty")
		.put("biomes", "empty")
		.put("base", "surface")
		.put("carved", "carvers")
		.put("liquid_carved", "liquid_carvers")
		.put("decorated", "features")
		.put("lighted", "light")
		.put("mobs_spawned", "spawn")
		.put("finalized", "heightmaps")
		.put("fullchunk", "full")
		.build();

	public afu(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.c);
		Type<?> type3 = type2.findFieldType("Level");
		OpticFinder<?> opticFinder4 = DSL.fieldFinder("Level", type3);
		return this.fixTypeEverywhereTyped("ChunkStatusFix2", type2, this.getOutputSchema().getType(ajb.c), typed -> typed.updateTyped(opticFinder4, typedx -> {
				Dynamic<?> dynamic2 = typedx.get(DSL.remainderFinder());
				String string3 = dynamic2.get("Status").asString("empty");
				String string4 = (String)a.getOrDefault(string3, "empty");
				return Objects.equals(string3, string4) ? typedx : typedx.set(DSL.remainderFinder(), dynamic2.set("Status", dynamic2.createString(string4)));
			}));
	}
}
