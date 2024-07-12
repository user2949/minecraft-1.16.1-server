import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class ajm extends DataFix {
	public ajm(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.t);
		return this.fixTypeEverywhereTyped("Structure Reference Fix", type2, typed -> typed.update(DSL.remainderFinder(), ajm::a));
	}

	private static <T> Dynamic<T> a(Dynamic<T> dynamic) {
		return dynamic.update(
			"references", dynamicx -> dynamicx.createInt((Integer)dynamicx.asNumber().map(Number::intValue).result().filter(integer -> integer > 0).orElse(1))
		);
	}
}
