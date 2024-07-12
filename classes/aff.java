import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class aff extends aij {
	public aff(Schema schema, boolean boolean2) {
		super(schema, boolean2, "BlockEntityJukeboxFix", ajb.k, "minecraft:jukebox");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		Type<?> type3 = this.getInputSchema().getChoiceType(ajb.k, "minecraft:jukebox");
		Type<?> type4 = type3.findFieldType("RecordItem");
		OpticFinder<?> opticFinder5 = DSL.fieldFinder("RecordItem", type4);
		Dynamic<?> dynamic6 = typed.get(DSL.remainderFinder());
		int integer7 = dynamic6.get("Record").asInt(0);
		if (integer7 > 0) {
			dynamic6.remove("Record");
			String string8 = ahv.a(ahm.a(integer7), 0);
			if (string8 != null) {
				Dynamic<?> dynamic9 = dynamic6.emptyMap();
				dynamic9 = dynamic9.set("id", dynamic9.createString(string8));
				dynamic9 = dynamic9.set("Count", dynamic9.createByte((byte)1));
				return typed.set(
						opticFinder5,
						(Typed)((Pair)type4.readTyped(dynamic9).result().orElseThrow(() -> new IllegalStateException("Could not create record item stack."))).getFirst()
					)
					.set(DSL.remainderFinder(), dynamic6);
			}
		}

		return typed;
	}
}
