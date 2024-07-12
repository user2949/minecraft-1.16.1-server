import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class agt extends DataFix {
	public agt(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Schema schema2 = this.getInputSchema();
		Schema schema3 = this.getOutputSchema();
		Type<?> type4 = schema2.getTypeRaw(ajb.o);
		Type<?> type5 = schema3.getTypeRaw(ajb.o);
		Type<?> type6 = schema2.getTypeRaw(ajb.p);
		return this.a(schema2, schema3, type4, type5, type6);
	}

	private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule a(
		Schema schema1, Schema schema2, Type<OldEntityTree> type3, Type<NewEntityTree> type4, Type<Entity> type5
	) {
		Type<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> type7 = DSL.named(ajb.o.typeName(), DSL.and(DSL.optional(DSL.field("Riding", type3)), type5));
		Type<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> type8 = DSL.named(
			ajb.o.typeName(), DSL.and(DSL.optional(DSL.field("Passengers", DSL.list(type4))), type5)
		);
		Type<?> type9 = schema1.getType(ajb.o);
		Type<?> type10 = schema2.getType(ajb.o);
		if (!Objects.equals(type9, type7)) {
			throw new IllegalStateException("Old entity type is not what was expected.");
		} else if (!type10.equals(type8, true, true)) {
			throw new IllegalStateException("New entity type is not what was expected.");
		} else {
			OpticFinder<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> opticFinder11 = DSL.typeFinder(type7);
			OpticFinder<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> opticFinder12 = DSL.typeFinder(type8);
			OpticFinder<NewEntityTree> opticFinder13 = DSL.typeFinder(type4);
			Type<?> type14 = schema1.getType(ajb.b);
			Type<?> type15 = schema2.getType(ajb.b);
			return TypeRewriteRule.seq(
				this.fixTypeEverywhere(
					"EntityRidingToPassengerFix",
					type7,
					type8,
					dynamicOps -> pair -> {
							Optional<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> optional8 = Optional.empty();
							Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>> pair9 = pair;
		
							while (true) {
								Either<List<NewEntityTree>, Unit> either10 = DataFixUtils.orElse(
									optional8.map(
										pairx -> {
											Typed<NewEntityTree> typed6 = (Typed<NewEntityTree>)type4.pointTyped(dynamicOps)
												.orElseThrow(() -> new IllegalStateException("Could not create new entity tree"));
											NewEntityTree object7 = (NewEntityTree)typed6.set(opticFinder12, pairx)
												.getOptional(opticFinder13)
												.orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
											return Either.left(ImmutableList.of(object7));
										}
									),
									Either.right(DSL.unit())
								);
								optional8 = Optional.of(Pair.of(ajb.o.typeName(), Pair.of(either10, pair9.getSecond().getSecond())));
								Optional<OldEntityTree> optional11 = pair9.getSecond().getFirst().left();
								if (!optional11.isPresent()) {
									return (Pair)optional8.orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
								}
		
								pair9 = (Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>)new Typed<>(type3, dynamicOps, (OldEntityTree)optional11.get())
									.getOptional(opticFinder11)
									.orElseThrow(() -> new IllegalStateException("Should always have an entity here"));
							}
						}
				),
				this.writeAndRead("player RootVehicle injecter", type14, type15)
			);
		}
	}
}
