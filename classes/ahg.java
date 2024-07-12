import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;

public class ahg extends DataFix {
	public ahg(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.a(this.getOutputSchema().getTypeRaw(ajb.w));
	}

	private <R> TypeRewriteRule a(Type<R> type) {
		Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> type3 = DSL.and(
			DSL.optional(DSL.field("RecipesUsed", DSL.and(DSL.compoundList(type, DSL.intType()), DSL.remainderType()))), DSL.remainderType()
		);
		OpticFinder<?> opticFinder4 = DSL.namedChoice("minecraft:furnace", this.getInputSchema().getChoiceType(ajb.k, "minecraft:furnace"));
		OpticFinder<?> opticFinder5 = DSL.namedChoice("minecraft:blast_furnace", this.getInputSchema().getChoiceType(ajb.k, "minecraft:blast_furnace"));
		OpticFinder<?> opticFinder6 = DSL.namedChoice("minecraft:smoker", this.getInputSchema().getChoiceType(ajb.k, "minecraft:smoker"));
		Type<?> type7 = this.getOutputSchema().getChoiceType(ajb.k, "minecraft:furnace");
		Type<?> type8 = this.getOutputSchema().getChoiceType(ajb.k, "minecraft:blast_furnace");
		Type<?> type9 = this.getOutputSchema().getChoiceType(ajb.k, "minecraft:smoker");
		Type<?> type10 = this.getInputSchema().getType(ajb.k);
		Type<?> type11 = this.getOutputSchema().getType(ajb.k);
		return this.fixTypeEverywhereTyped(
			"FurnaceRecipesFix",
			type10,
			type11,
			typed -> typed.updateTyped(opticFinder4, type7, typedx -> this.a(type, type3, typedx))
					.updateTyped(opticFinder5, type8, typedx -> this.a(type, type3, typedx))
					.updateTyped(opticFinder6, type9, typedx -> this.a(type, type3, typedx))
		);
	}

	private <R> Typed<?> a(Type<R> type1, Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> type2, Typed<?> typed) {
		Dynamic<?> dynamic5 = typed.getOrCreate(DSL.remainderFinder());
		int integer6 = dynamic5.get("RecipesUsedSize").asInt(0);
		dynamic5 = dynamic5.remove("RecipesUsedSize");
		List<Pair<R, Integer>> list7 = Lists.<Pair<R, Integer>>newArrayList();

		for (int integer8 = 0; integer8 < integer6; integer8++) {
			String string9 = "RecipeLocation" + integer8;
			String string10 = "RecipeAmount" + integer8;
			Optional<? extends Dynamic<?>> optional11 = dynamic5.get(string9).result();
			int integer12 = dynamic5.get(string10).asInt(0);
			if (integer12 > 0) {
				optional11.ifPresent(dynamic -> {
					Optional<? extends Pair<R, ? extends Dynamic<?>>> optional5 = type1.read(dynamic).result();
					optional5.ifPresent(pair -> list7.add(Pair.of(pair.getFirst(), integer12)));
				});
			}

			dynamic5 = dynamic5.remove(string9).remove(string10);
		}

		return typed.set(DSL.remainderFinder(), type2, Pair.of(Either.left(Pair.of(list7, dynamic5.emptyMap())), dynamic5));
	}
}
