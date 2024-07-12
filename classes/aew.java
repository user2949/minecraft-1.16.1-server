import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class aew extends DataFix {
	public aew(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getOutputSchema().getType(ajb.c);
		Type<?> type3 = type2.findFieldType("Level");
		Type<?> type4 = type3.findFieldType("TileEntities");
		if (!(type4 instanceof ListType)) {
			throw new IllegalStateException("Tile entity type is not a list type.");
		} else {
			ListType<?> listType5 = (ListType<?>)type4;
			return this.a(type3, listType5);
		}
	}

	private <TE> TypeRewriteRule a(Type<?> type, ListType<TE> listType) {
		Type<TE> type4 = listType.getElement();
		OpticFinder<?> opticFinder5 = DSL.fieldFinder("Level", type);
		OpticFinder<List<TE>> opticFinder6 = DSL.fieldFinder("TileEntities", listType);
		int integer7 = 416;
		return TypeRewriteRule.seq(
			this.fixTypeEverywhere(
				"InjectBedBlockEntityType", this.getInputSchema().findChoiceType(ajb.k), this.getOutputSchema().findChoiceType(ajb.k), dynamicOps -> pair -> pair
			),
			this.fixTypeEverywhereTyped(
				"BedBlockEntityInjecter",
				this.getOutputSchema().getType(ajb.c),
				typed -> {
					Typed<?> typed5 = typed.getTyped(opticFinder5);
					Dynamic<?> dynamic6 = typed5.get(DSL.remainderFinder());
					int integer7x = dynamic6.get("xPos").asInt(0);
					int integer8 = dynamic6.get("zPos").asInt(0);
					List<TE> list9 = Lists.<TE>newArrayList(typed5.getOrCreate(opticFinder6));
					List<? extends Dynamic<?>> list10 = dynamic6.get("Sections").asList(Function.identity());
		
					for (int integer11 = 0; integer11 < list10.size(); integer11++) {
						Dynamic<?> dynamic12 = (Dynamic<?>)list10.get(integer11);
						int integer13 = dynamic12.get("Y").asInt(0);
						Stream<Integer> stream14 = dynamic12.get("Blocks").asStream().map(dynamic -> dynamic.asInt(0));
						int integer15 = 0;
		
						for (int integer17 : stream14::iterator) {
							if (416 == (integer17 & 0xFF) << 4) {
								int integer18 = integer15 & 15;
								int integer19 = integer15 >> 8 & 15;
								int integer20 = integer15 >> 4 & 15;
								Map<Dynamic<?>, Dynamic<?>> map21 = Maps.<Dynamic<?>, Dynamic<?>>newHashMap();
								map21.put(dynamic12.createString("id"), dynamic12.createString("minecraft:bed"));
								map21.put(dynamic12.createString("x"), dynamic12.createInt(integer18 + (integer7x << 4)));
								map21.put(dynamic12.createString("y"), dynamic12.createInt(integer19 + (integer13 << 4)));
								map21.put(dynamic12.createString("z"), dynamic12.createInt(integer20 + (integer8 << 4)));
								map21.put(dynamic12.createString("color"), dynamic12.createShort((short)14));
								list9.add(
									((Pair)type4.read(dynamic12.createMap(map21)).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created bed block entity.")))
										.getFirst()
								);
							}
		
							integer15++;
						}
					}
		
					return !list9.isEmpty() ? typed.set(opticFinder5, typed5.set(opticFinder6, list9)) : typed;
				}
			)
		);
	}
}
