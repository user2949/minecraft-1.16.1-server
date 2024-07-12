import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class ajo extends DataFix {
	public ajo(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<Pair<String, Dynamic<?>>> type2 = DSL.named(ajb.v.typeName(), DSL.remainderType());
		if (!Objects.equals(type2, this.getInputSchema().getType(ajb.v))) {
			throw new IllegalStateException("Team type is not what was expected.");
		} else {
			return this.fixTypeEverywhere(
				"TeamDisplayNameFix",
				type2,
				dynamicOps -> pair -> pair.mapSecond(
							dynamic -> dynamic.update(
									"DisplayName",
									dynamic2 -> DataFixUtils.orElse(dynamic2.asString().map(string -> mr.a.a(new nd(string))).map(dynamic::createString).result(), dynamic2)
								)
						)
			);
		}
	}
}
