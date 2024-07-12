import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class aht extends DataFix {
	public aht(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<Pair<String, String>> opticFinder3 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		OpticFinder<?> opticFinder4 = type2.findField("tag");
		return this.fixTypeEverywhereTyped("ItemInstanceMapIdFix", type2, typed -> {
			Optional<Pair<String, String>> optional4 = typed.getOptional(opticFinder3);
			if (optional4.isPresent() && Objects.equals(((Pair)optional4.get()).getSecond(), "minecraft:filled_map")) {
				Dynamic<?> dynamic5 = typed.get(DSL.remainderFinder());
				Typed<?> typed6 = typed.getOrCreateTyped(opticFinder4);
				Dynamic<?> dynamic7 = typed6.get(DSL.remainderFinder());
				dynamic7 = dynamic7.set("map", dynamic7.createInt(dynamic5.get("Damage").asInt(0)));
				return typed.set(opticFinder4, typed6.set(DSL.remainderFinder(), dynamic7));
			} else {
				return typed;
			}
		});
	}
}
