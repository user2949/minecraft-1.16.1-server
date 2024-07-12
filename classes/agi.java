import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class agi extends ags {
	public agi(Schema schema, boolean boolean2) {
		super("EntityHorseSplitFix", schema, boolean2);
	}

	@Override
	protected Pair<String, Typed<?>> a(String string, Typed<?> typed) {
		Dynamic<?> dynamic4 = typed.get(DSL.remainderFinder());
		if (Objects.equals("EntityHorse", string)) {
			int integer6 = dynamic4.get("Type").asInt(0);
			String string5;
			switch (integer6) {
				case 0:
				default:
					string5 = "Horse";
					break;
				case 1:
					string5 = "Donkey";
					break;
				case 2:
					string5 = "Mule";
					break;
				case 3:
					string5 = "ZombieHorse";
					break;
				case 4:
					string5 = "SkeletonHorse";
			}

			dynamic4.remove("Type");
			Type<?> type7 = (Type<?>)this.getOutputSchema().findChoiceType(ajb.p).types().get(string5);
			return Pair.of(
				string5,
				(Typed<?>)((Pair)typed.write().flatMap(type7::readTyped).result().orElseThrow(() -> new IllegalStateException("Could not parse the new horse"))).getFirst()
			);
		} else {
			return Pair.of(string, typed);
		}
	}
}
