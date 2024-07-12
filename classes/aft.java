import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class aft extends DataFix {
	public aft(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.c);
		Type<?> type3 = type2.findFieldType("Level");
		OpticFinder<?> opticFinder4 = DSL.fieldFinder("Level", type3);
		return this.fixTypeEverywhereTyped("ChunkStatusFix", type2, this.getOutputSchema().getType(ajb.c), typed -> typed.updateTyped(opticFinder4, typedx -> {
				Dynamic<?> dynamic2 = typedx.get(DSL.remainderFinder());
				String string3 = dynamic2.get("Status").asString("empty");
				if (Objects.equals(string3, "postprocessed")) {
					dynamic2 = dynamic2.set("Status", dynamic2.createString("fullchunk"));
				}

				return typedx.set(DSL.remainderFinder(), dynamic2);
			}));
	}
}
