import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class ahx extends DataFix {
	public ahx(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<Pair<String, String>> opticFinder3 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		OpticFinder<?> opticFinder4 = type2.findField("tag");
		return this.fixTypeEverywhereTyped(
			"ItemWaterPotionFix",
			type2,
			typed -> {
				Optional<Pair<String, String>> optional4 = typed.getOptional(opticFinder3);
				if (optional4.isPresent()) {
					String string5 = (String)((Pair)optional4.get()).getSecond();
					if ("minecraft:potion".equals(string5)
						|| "minecraft:splash_potion".equals(string5)
						|| "minecraft:lingering_potion".equals(string5)
						|| "minecraft:tipped_arrow".equals(string5)) {
						Typed<?> typed6 = typed.getOrCreateTyped(opticFinder4);
						Dynamic<?> dynamic7 = typed6.get(DSL.remainderFinder());
						if (!dynamic7.get("Potion").asString().result().isPresent()) {
							dynamic7 = dynamic7.set("Potion", dynamic7.createString("minecraft:water"));
						}
	
						return typed.set(opticFinder4, typed6.set(DSL.remainderFinder(), dynamic7));
					}
				}
	
				return typed;
			}
		);
	}
}
