import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class agf extends DataFix {
	public agf(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.a(this.getInputSchema().getTypeRaw(ajb.l));
	}

	private <IS> TypeRewriteRule a(Type<IS> type) {
		Type<Pair<Either<List<IS>, Unit>, Dynamic<?>>> type3 = DSL.and(DSL.optional(DSL.field("Equipment", DSL.list(type))), DSL.remainderType());
		Type<Pair<Either<List<IS>, Unit>, Pair<Either<List<IS>, Unit>, Dynamic<?>>>> type4 = DSL.and(
			DSL.optional(DSL.field("ArmorItems", DSL.list(type))), DSL.optional(DSL.field("HandItems", DSL.list(type))), DSL.remainderType()
		);
		OpticFinder<Pair<Either<List<IS>, Unit>, Dynamic<?>>> opticFinder5 = DSL.typeFinder(type3);
		OpticFinder<List<IS>> opticFinder6 = DSL.fieldFinder("Equipment", DSL.list(type));
		return this.fixTypeEverywhereTyped(
			"EntityEquipmentToArmorAndHandFix",
			this.getInputSchema().getType(ajb.p),
			this.getOutputSchema().getType(ajb.p),
			typed -> {
				Either<List<IS>, Unit> either6 = Either.right(DSL.unit());
				Either<List<IS>, Unit> either7 = Either.right(DSL.unit());
				Dynamic<?> dynamic8 = typed.getOrCreate(DSL.remainderFinder());
				Optional<List<IS>> optional9 = typed.getOptional(opticFinder6);
				if (optional9.isPresent()) {
					List<IS> list10 = (List<IS>)optional9.get();
					IS object11 = (IS)((Pair)type.read(dynamic8.emptyMap())
							.result()
							.orElseThrow(() -> new IllegalStateException("Could not parse newly created empty itemstack.")))
						.getFirst();
					if (!list10.isEmpty()) {
						either6 = Either.left(Lists.<IS>newArrayList((IS[])(new Object[]{list10.get(0), object11})));
					}
	
					if (list10.size() > 1) {
						List<IS> list12 = Lists.<IS>newArrayList(object11, object11, object11, object11);
	
						for (int integer13 = 1; integer13 < Math.min(list10.size(), 5); integer13++) {
							list12.set(integer13 - 1, list10.get(integer13));
						}
	
						either7 = Either.left(list12);
					}
				}
	
				Dynamic<?> dynamic10 = dynamic8;
				Optional<? extends Stream<? extends Dynamic<?>>> optional11 = dynamic8.get("DropChances").asStreamOpt().result();
				if (optional11.isPresent()) {
					Iterator<? extends Dynamic<?>> iterator12 = Stream.concat((Stream)optional11.get(), Stream.generate(() -> dynamic10.createInt(0))).iterator();
					float float13 = ((Dynamic)iterator12.next()).asFloat(0.0F);
					if (!dynamic8.get("HandDropChances").result().isPresent()) {
						Dynamic<?> dynamic14 = dynamic8.createList(Stream.of(float13, 0.0F).map(dynamic8::createFloat));
						dynamic8 = dynamic8.set("HandDropChances", dynamic14);
					}
	
					if (!dynamic8.get("ArmorDropChances").result().isPresent()) {
						Dynamic<?> dynamic14 = dynamic8.createList(
							Stream.of(
									((Dynamic)iterator12.next()).asFloat(0.0F),
									((Dynamic)iterator12.next()).asFloat(0.0F),
									((Dynamic)iterator12.next()).asFloat(0.0F),
									((Dynamic)iterator12.next()).asFloat(0.0F)
								)
								.map(dynamic8::createFloat)
						);
						dynamic8 = dynamic8.set("ArmorDropChances", dynamic14);
					}
	
					dynamic8 = dynamic8.remove("DropChances");
				}
	
				return typed.set(opticFinder5, type4, Pair.of(either6, Pair.of(either7, dynamic8)));
			}
		);
	}
}
