import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class aik extends DataFix {
	public aik(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		CompoundListType<String, ?> compoundListType2 = DSL.compoundList(DSL.string(), this.getInputSchema().getType(ajb.t));
		OpticFinder<? extends List<? extends Pair<String, ?>>> opticFinder3 = compoundListType2.finder();
		return this.a(compoundListType2);
	}

	private <SF> TypeRewriteRule a(CompoundListType<String, SF> compoundListType) {
		Type<?> type3 = this.getInputSchema().getType(ajb.c);
		Type<?> type4 = this.getInputSchema().getType(ajb.t);
		OpticFinder<?> opticFinder5 = type3.findField("Level");
		OpticFinder<?> opticFinder6 = opticFinder5.type().findField("Structures");
		OpticFinder<?> opticFinder7 = opticFinder6.type().findField("Starts");
		OpticFinder<List<Pair<String, SF>>> opticFinder8 = compoundListType.finder();
		return TypeRewriteRule.seq(
			this.fixTypeEverywhereTyped(
				"NewVillageFix",
				type3,
				typed -> typed.updateTyped(
						opticFinder5,
						typedx -> typedx.updateTyped(
								opticFinder6,
								typedxx -> typedxx.updateTyped(
											opticFinder7,
											typedxxx -> typedxxx.update(
													opticFinder8,
													list -> (List)list.stream()
															.filter(pair -> !Objects.equals(pair.getFirst(), "Village"))
															.map(pair -> pair.mapFirst(string -> string.equals("New_Village") ? "Village" : string))
															.collect(Collectors.toList())
												)
										)
										.update(DSL.remainderFinder(), dynamic -> dynamic.update("References", dynamicx -> {
												Optional<? extends Dynamic<?>> optional2 = dynamicx.get("New_Village").result();
												return DataFixUtils.orElse(optional2.map(dynamic2 -> dynamicx.remove("New_Village").set("Village", dynamic2)), dynamicx).remove("Village");
											}))
							)
					)
			),
			this.fixTypeEverywhereTyped(
				"NewVillageStartFix",
				type4,
				typed -> typed.update(
						DSL.remainderFinder(),
						dynamic -> dynamic.update(
								"id", dynamicx -> Objects.equals(aka.a(dynamicx.asString("")), "minecraft:new_village") ? dynamicx.createString("minecraft:village") : dynamicx
							)
					)
			)
		);
	}
}
