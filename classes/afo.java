import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class afo extends DataFix {
	public afo(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"BlockStateStructureTemplateFix", this.getInputSchema().getType(ajb.m), typed -> typed.update(DSL.remainderFinder(), afn::a)
		);
	}
}
