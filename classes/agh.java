import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class agh extends aij {
	public agh(Schema schema, boolean boolean2) {
		super(schema, boolean2, "EntityHorseSaddleFix", ajb.p, "EntityHorse");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		OpticFinder<Pair<String, String>> opticFinder3 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		Type<?> type4 = this.getInputSchema().getTypeRaw(ajb.l);
		OpticFinder<?> opticFinder5 = DSL.fieldFinder("SaddleItem", type4);
		Optional<? extends Typed<?>> optional6 = typed.getOptionalTyped(opticFinder5);
		Dynamic<?> dynamic7 = typed.get(DSL.remainderFinder());
		if (!optional6.isPresent() && dynamic7.get("Saddle").asBoolean(false)) {
			Typed<?> typed8 = (Typed<?>)type4.pointTyped(typed.getOps()).orElseThrow(IllegalStateException::new);
			typed8 = typed8.set(opticFinder3, Pair.of(ajb.r.typeName(), "minecraft:saddle"));
			Dynamic<?> dynamic9 = dynamic7.emptyMap();
			dynamic9 = dynamic9.set("Count", dynamic9.createByte((byte)1));
			dynamic9 = dynamic9.set("Damage", dynamic9.createShort((short)0));
			typed8 = typed8.set(DSL.remainderFinder(), dynamic9);
			dynamic7.remove("Saddle");
			return typed.set(opticFinder5, typed8).set(DSL.remainderFinder(), dynamic7);
		} else {
			return typed;
		}
	}
}
