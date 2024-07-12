import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;

public class afr extends DataFix {
	public afr(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.c);
		Type<?> type3 = type2.findFieldType("Level");
		OpticFinder<?> opticFinder4 = DSL.fieldFinder("Level", type3);
		return this.fixTypeEverywhereTyped(
			"ChunkLightRemoveFix",
			type2,
			this.getOutputSchema().getType(ajb.c),
			typed -> typed.updateTyped(opticFinder4, typedx -> typedx.update(DSL.remainderFinder(), dynamic -> dynamic.remove("isLightOn")))
		);
	}
}
