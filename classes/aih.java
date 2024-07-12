import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FieldFinder;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;

public class aih extends DataFix {
	public aih(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	private static <A> Type<Pair<A, Dynamic<?>>> a(String string, Type<A> type) {
		return DSL.and(DSL.field(string, type), DSL.remainderType());
	}

	private static <A> Type<Pair<Either<A, Unit>, Dynamic<?>>> b(String string, Type<A> type) {
		return DSL.and(DSL.optional(DSL.field(string, type)), DSL.remainderType());
	}

	private static <A1, A2> Type<Pair<Either<A1, Unit>, Pair<Either<A2, Unit>, Dynamic<?>>>> a(String string1, Type<A1> type2, String string3, Type<A2> type4) {
		return DSL.and(DSL.optional(DSL.field(string1, type2)), DSL.optional(DSL.field(string3, type4)), DSL.remainderType());
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Schema schema2 = this.getInputSchema();
		TaggedChoiceType<String> taggedChoiceType3 = new TaggedChoiceType<>(
			"type",
			DSL.string(),
			ImmutableMap.of(
				"minecraft:debug",
				DSL.remainderType(),
				"minecraft:flat",
				b("settings", a("biome", schema2.getType(ajb.x), "layers", DSL.list(b("block", schema2.getType(ajb.q))))),
				"minecraft:noise",
				a(
					"biome_source",
					DSL.taggedChoiceType(
						"type",
						DSL.string(),
						ImmutableMap.of(
							"minecraft:fixed",
							a("biome", schema2.getType(ajb.x)),
							"minecraft:multi_noise",
							DSL.list(a("biome", schema2.getType(ajb.x))),
							"minecraft:checkerboard",
							a("biomes", DSL.list(schema2.getType(ajb.x))),
							"minecraft:vanilla_layered",
							DSL.remainderType(),
							"minecraft:the_end",
							DSL.remainderType()
						)
					),
					"settings",
					DSL.or(DSL.string(), a("default_block", schema2.getType(ajb.q), "default_fluid", schema2.getType(ajb.q)))
				)
			)
		);
		CompoundListType<String, ?> compoundListType4 = DSL.compoundList(aka.a(), a("generator", taggedChoiceType3));
		Type<?> type5 = DSL.and(compoundListType4, DSL.remainderType());
		Type<?> type6 = schema2.getType(ajb.y);
		FieldFinder<?> fieldFinder7 = new FieldFinder<>("dimensions", type5);
		if (!type6.findFieldType("dimensions").equals(type5)) {
			throw new IllegalStateException();
		} else {
			OpticFinder<? extends List<? extends Pair<String, ?>>> opticFinder8 = compoundListType4.finder();
			return this.fixTypeEverywhereTyped(
				"MissingDimensionFix", type6, typed -> typed.updateTyped(fieldFinder7, typed4 -> typed4.updateTyped(opticFinder8, typed3 -> {
							if (!(typed3.getValue() instanceof List)) {
								throw new IllegalStateException("List exptected");
							} else if (((List)typed3.getValue()).isEmpty()) {
								Dynamic<?> dynamic5 = typed.get(DSL.remainderFinder());
								Dynamic<?> dynamic6 = this.a(dynamic5);
								return DataFixUtils.orElse(compoundListType4.readTyped(dynamic6).result().map(Pair::getFirst), typed3);
							} else {
								return typed3;
							}
						}))
			);
		}
	}

	private <T> Dynamic<T> a(Dynamic<T> dynamic) {
		long long3 = dynamic.get("seed").asLong(0L);
		return new Dynamic<>(dynamic.getOps(), ajv.a(dynamic, long3, ajv.a(dynamic, long3), false));
	}
}
