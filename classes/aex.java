import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class aex extends DataFix {
	public aex(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		OpticFinder<Pair<String, String>> opticFinder2 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		return this.fixTypeEverywhereTyped("BedItemColorFix", this.getInputSchema().getType(ajb.l), typed -> {
			Optional<Pair<String, String>> optional3 = typed.getOptional(opticFinder2);
			if (optional3.isPresent() && Objects.equals(((Pair)optional3.get()).getSecond(), "minecraft:bed")) {
				Dynamic<?> dynamic4 = typed.get(DSL.remainderFinder());
				if (dynamic4.get("Damage").asInt(0) == 0) {
					return typed.set(DSL.remainderFinder(), dynamic4.set("Damage", dynamic4.createShort((short)14)));
				}
			}

			return typed;
		});
	}
}
