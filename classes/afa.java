import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.stream.LongStream;

public class afa extends DataFix {
	public afa(Schema schema) {
		super(schema, false);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.c);
		Type<?> type3 = type2.findFieldType("Level");
		OpticFinder<?> opticFinder4 = DSL.fieldFinder("Level", type3);
		OpticFinder<?> opticFinder5 = opticFinder4.type().findField("Sections");
		Type<?> type6 = ((ListType)opticFinder5.type()).getElement();
		OpticFinder<?> opticFinder7 = DSL.typeFinder(type6);
		Type<Pair<String, Dynamic<?>>> type8 = DSL.named(ajb.m.typeName(), DSL.remainderType());
		OpticFinder<List<Pair<String, Dynamic<?>>>> opticFinder9 = DSL.fieldFinder("Palette", DSL.list(type8));
		return this.fixTypeEverywhereTyped(
			"BitStorageAlignFix",
			type2,
			this.getOutputSchema().getType(ajb.c),
			typed -> typed.updateTyped(opticFinder4, typedx -> this.a(a(opticFinder5, opticFinder7, opticFinder9, typedx)))
		);
	}

	private Typed<?> a(Typed<?> typed) {
		return typed.update(
			DSL.remainderFinder(),
			dynamic -> dynamic.update("Heightmaps", dynamic2 -> dynamic2.updateMapValues(pair -> pair.mapSecond(dynamic2x -> a(dynamic, dynamic2x, 256, 9))))
		);
	}

	private static Typed<?> a(OpticFinder<?> opticFinder1, OpticFinder<?> opticFinder2, OpticFinder<List<Pair<String, Dynamic<?>>>> opticFinder3, Typed<?> typed) {
		return typed.updateTyped(
			opticFinder1,
			typedx -> typedx.updateTyped(
					opticFinder2,
					typedxx -> {
						int integer3 = (Integer)typedxx.getOptional(opticFinder3).map(list -> Math.max(4, DataFixUtils.ceillog2(list.size()))).orElse(0);
						return integer3 != 0 && !aec.d(integer3)
							? typedxx.update(DSL.remainderFinder(), dynamic -> dynamic.update("BlockStates", dynamic3 -> a(dynamic, dynamic3, 4096, integer3)))
							: typedxx;
					}
				)
		);
	}

	private static Dynamic<?> a(Dynamic<?> dynamic1, Dynamic<?> dynamic2, int integer3, int integer4) {
		long[] arr5 = dynamic2.asLongStream().toArray();
		long[] arr6 = a(integer3, integer4, arr5);
		return dynamic1.createLongList(LongStream.of(arr6));
	}

	public static long[] a(int integer1, int integer2, long[] arr) {
		int integer4 = arr.length;
		if (integer4 == 0) {
			return arr;
		} else {
			long long5 = (1L << integer2) - 1L;
			int integer7 = 64 / integer2;
			int integer8 = (integer1 + integer7 - 1) / integer7;
			long[] arr9 = new long[integer8];
			int integer10 = 0;
			int integer11 = 0;
			long long12 = 0L;
			int integer14 = 0;
			long long15 = arr[0];
			long long17 = integer4 > 1 ? arr[1] : 0L;

			for (int integer19 = 0; integer19 < integer1; integer19++) {
				int integer20 = integer19 * integer2;
				int integer21 = integer20 >> 6;
				int integer22 = (integer19 + 1) * integer2 - 1 >> 6;
				int integer23 = integer20 ^ integer21 << 6;
				if (integer21 != integer14) {
					long15 = long17;
					long17 = integer21 + 1 < integer4 ? arr[integer21 + 1] : 0L;
					integer14 = integer21;
				}

				long long24;
				if (integer21 == integer22) {
					long24 = long15 >>> integer23 & long5;
				} else {
					int integer26 = 64 - integer23;
					long24 = (long15 >>> integer23 | long17 << integer26) & long5;
				}

				int integer26 = integer11 + integer2;
				if (integer26 >= 64) {
					arr9[integer10++] = long12;
					long12 = long24;
					integer11 = integer2;
				} else {
					long12 |= long24 << integer11;
					integer11 = integer26;
				}
			}

			if (long12 != 0L) {
				arr9[integer10] = long12;
			}

			return arr9;
		}
	}
}
