import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class aim extends DataFix {
	public aim(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	private static dfp.a a(String string) {
		return string.equals("health") ? dfp.a.HEARTS : dfp.a.INTEGER;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<Pair<String, Dynamic<?>>> type2 = DSL.named(ajb.u.typeName(), DSL.remainderType());
		if (!Objects.equals(type2, this.getInputSchema().getType(ajb.u))) {
			throw new IllegalStateException("Objective type is not what was expected.");
		} else {
			return this.fixTypeEverywhere("ObjectiveRenderTypeFix", type2, dynamicOps -> pair -> pair.mapSecond(dynamic -> {
						Optional<String> optional2 = dynamic.get("RenderType").asString().result();
						if (!optional2.isPresent()) {
							String string3 = dynamic.get("CriteriaName").asString("");
							dfp.a a4 = a(string3);
							return dynamic.set("RenderType", dynamic.createString(a4.a()));
						} else {
							return dynamic;
						}
					}));
		}
	}
}
