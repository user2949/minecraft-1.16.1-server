import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class ahf extends DataFix {
	public ahf(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<Pair<String, Dynamic<?>>> type2 = DSL.named(ajb.j.typeName(), DSL.remainderType());
		if (!Objects.equals(type2, this.getInputSchema().getType(ajb.j))) {
			throw new IllegalStateException("Poi type is not what was expected.");
		} else {
			return this.fixTypeEverywhere("POI rebuild", type2, dynamicOps -> pair -> pair.mapSecond(ahf::a));
		}
	}

	private static <T> Dynamic<T> a(Dynamic<T> dynamic) {
		return dynamic.update("Sections", dynamicx -> dynamicx.updateMapValues(pair -> pair.mapSecond(dynamicxx -> dynamicxx.remove("Valid"))));
	}
}
