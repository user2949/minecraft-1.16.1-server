import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;

public class ahn extends DataFix {
	public ahn(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<?> opticFinder3 = type2.findField("tag");
		return this.fixTypeEverywhereTyped(
			"Item Lore componentize",
			type2,
			typed -> typed.updateTyped(
					opticFinder3,
					typedx -> typedx.update(
							DSL.remainderFinder(),
							dynamic -> dynamic.update(
									"display",
									dynamicx -> dynamicx.update(
											"Lore", dynamicxx -> DataFixUtils.orElse(dynamicxx.asStreamOpt().map(ahn::a).map(dynamicxx::createList).result(), dynamicxx)
										)
								)
						)
				)
		);
	}

	private static <T> Stream<Dynamic<T>> a(Stream<Dynamic<T>> stream) {
		return stream.map(dynamic -> DataFixUtils.orElse(dynamic.asString().map(ahn::a).map(dynamic::createString).result(), dynamic));
	}

	private static String a(String string) {
		return mr.a.a(new nd(string));
	}
}
