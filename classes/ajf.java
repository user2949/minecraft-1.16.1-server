import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ajf extends DataFix {
	public ajf(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<Pair<String, Dynamic<?>>> type2 = DSL.named(ajb.j.typeName(), DSL.remainderType());
		if (!Objects.equals(type2, this.getInputSchema().getType(ajb.j))) {
			throw new IllegalStateException("Poi type is not what was expected.");
		} else {
			return this.fixTypeEverywhere("POI reorganization", type2, dynamicOps -> pair -> pair.mapSecond(ajf::a));
		}
	}

	private static <T> Dynamic<T> a(Dynamic<T> dynamic) {
		Map<Dynamic<T>, Dynamic<T>> map2 = Maps.<Dynamic<T>, Dynamic<T>>newHashMap();

		for (int integer3 = 0; integer3 < 16; integer3++) {
			String string4 = String.valueOf(integer3);
			Optional<Dynamic<T>> optional5 = dynamic.get(string4).result();
			if (optional5.isPresent()) {
				Dynamic<T> dynamic6 = (Dynamic<T>)optional5.get();
				Dynamic<T> dynamic7 = dynamic.createMap(ImmutableMap.of(dynamic.createString("Records"), dynamic6));
				map2.put(dynamic.createInt(integer3), dynamic7);
				dynamic = dynamic.remove(string4);
			}
		}

		return dynamic.set("Sections", dynamic.createMap(map2));
	}
}
