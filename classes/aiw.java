import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public abstract class aiw extends DataFix {
	public aiw(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<Pair<String, Dynamic<?>>> type2 = DSL.named(ajb.j.typeName(), DSL.remainderType());
		if (!Objects.equals(type2, this.getInputSchema().getType(ajb.j))) {
			throw new IllegalStateException("Poi type is not what was expected.");
		} else {
			return this.fixTypeEverywhere("POI rename", type2, dynamicOps -> pair -> pair.mapSecond(this::a));
		}
	}

	private <T> Dynamic<T> a(Dynamic<T> dynamic) {
		return dynamic.update(
			"Sections",
			dynamicx -> dynamicx.updateMapValues(
					pair -> pair.mapSecond(dynamicxx -> dynamicxx.update("Records", dynamicxxx -> DataFixUtils.orElse(this.b(dynamicxxx), dynamicxxx)))
				)
		);
	}

	private <T> Optional<Dynamic<T>> b(Dynamic<T> dynamic) {
		return dynamic.asStreamOpt()
			.<Dynamic<T>>map(
				stream -> dynamic.createList(
						stream.map(
							dynamicxx -> dynamicxx.update(
									"type", dynamicxxx -> DataFixUtils.orElse(dynamicxxx.asString().map(this::a).map(dynamicxxx::createString).result(), dynamicxxx)
								)
						)
					)
			)
			.result();
	}

	protected abstract String a(String string);
}
