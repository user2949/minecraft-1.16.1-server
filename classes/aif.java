import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;

public class aif extends DataFix {
	public aif(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.h);
		OpticFinder<?> opticFinder3 = type2.findField("data");
		return this.fixTypeEverywhereTyped(
			"Map id fix",
			type2,
			typed -> {
				Optional<? extends Typed<?>> optional3 = typed.getOptionalTyped(opticFinder3);
				return optional3.isPresent()
					? typed
					: typed.update(DSL.remainderFinder(), dynamic -> dynamic.createMap(ImmutableMap.of(dynamic.createString("data"), dynamic)));
			}
		);
	}
}
