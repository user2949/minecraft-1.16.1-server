import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;

public class ajn extends DataFix {
	public ajn(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getOutputSchema().getType(ajb.g);
		Type<?> type3 = this.getInputSchema().getType(ajb.g);
		OpticFinder<?> opticFinder4 = type3.findField("stats");
		OpticFinder<?> opticFinder5 = opticFinder4.type().findField("minecraft:custom");
		OpticFinder<String> opticFinder6 = aka.a().finder();
		return this.fixTypeEverywhereTyped(
			"SwimStatsRenameFix",
			type3,
			type2,
			typed -> typed.updateTyped(opticFinder4, typedx -> typedx.updateTyped(opticFinder5, typedxx -> typedxx.update(opticFinder6, string -> {
							if (string.equals("minecraft:swim_one_cm")) {
								return "minecraft:walk_on_water_one_cm";
							} else {
								return string.equals("minecraft:dive_one_cm") ? "minecraft:walk_under_water_one_cm" : string;
							}
						})))
		);
	}
}
