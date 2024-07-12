import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class afc extends aij {
	public afc(Schema schema, boolean boolean2) {
		super(schema, boolean2, "BlockEntityBlockStateFix", ajb.k, "minecraft:piston");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		Type<?> type3 = this.getOutputSchema().getChoiceType(ajb.k, "minecraft:piston");
		Type<?> type4 = type3.findFieldType("blockState");
		OpticFinder<?> opticFinder5 = DSL.fieldFinder("blockState", type4);
		Dynamic<?> dynamic6 = typed.get(DSL.remainderFinder());
		int integer7 = dynamic6.get("blockId").asInt(0);
		dynamic6 = dynamic6.remove("blockId");
		int integer8 = dynamic6.get("blockData").asInt(0) & 15;
		dynamic6 = dynamic6.remove("blockData");
		Dynamic<?> dynamic9 = afn.b(integer7 << 4 | integer8);
		Typed<?> typed10 = (Typed<?>)type3.pointTyped(typed.getOps()).orElseThrow(() -> new IllegalStateException("Could not create new piston block entity."));
		return typed10.set(DSL.remainderFinder(), dynamic6)
			.set(
				opticFinder5,
				(Typed)((Pair)type4.readTyped(dynamic9).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created block state tag."))).getFirst()
			);
	}
}
