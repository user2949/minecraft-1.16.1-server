import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class afw extends DataFix {
	public afw(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.c);
		Type<?> type3 = this.getOutputSchema().getType(ajb.c);
		Type<?> type4 = type2.findFieldType("Level");
		Type<?> type5 = type3.findFieldType("Level");
		Type<?> type6 = type4.findFieldType("TileTicks");
		OpticFinder<?> opticFinder7 = DSL.fieldFinder("Level", type4);
		OpticFinder<?> opticFinder8 = DSL.fieldFinder("TileTicks", type6);
		return TypeRewriteRule.seq(
			this.fixTypeEverywhereTyped(
				"ChunkToProtoChunkFix",
				type2,
				this.getOutputSchema().getType(ajb.c),
				typed -> typed.updateTyped(
						opticFinder7,
						type5,
						typedx -> {
							Optional<? extends Stream<? extends Dynamic<?>>> optional4 = typedx.getOptionalTyped(opticFinder8)
								.flatMap(typedxx -> typedxx.write().result())
								.flatMap(dynamic -> dynamic.asStreamOpt().result());
							Dynamic<?> dynamic5 = typedx.get(DSL.remainderFinder());
							boolean boolean6 = dynamic5.get("TerrainPopulated").asBoolean(false)
								&& (!dynamic5.get("LightPopulated").asNumber().result().isPresent() || dynamic5.get("LightPopulated").asBoolean(false));
							dynamic5 = dynamic5.set("Status", dynamic5.createString(boolean6 ? "mobs_spawned" : "empty"));
							dynamic5 = dynamic5.set("hasLegacyStructureData", dynamic5.createBoolean(true));
							Dynamic<?> dynamic7;
							if (boolean6) {
								Optional<ByteBuffer> optional8 = dynamic5.get("Biomes").asByteBufferOpt().result();
								if (optional8.isPresent()) {
									ByteBuffer byteBuffer9 = (ByteBuffer)optional8.get();
									int[] arr10 = new int[256];
			
									for (int integer11 = 0; integer11 < arr10.length; integer11++) {
										if (integer11 < byteBuffer9.capacity()) {
											arr10[integer11] = byteBuffer9.get(integer11) & 255;
										}
									}
			
									dynamic5 = dynamic5.set("Biomes", dynamic5.createIntList(Arrays.stream(arr10)));
								}
			
								Dynamic<?> dynamic9 = dynamic5;
								List<ShortList> list10 = (List<ShortList>)IntStream.range(0, 16).mapToObj(integer -> new ShortArrayList()).collect(Collectors.toList());
								if (optional4.isPresent()) {
									((Stream)optional4.get()).forEach(dynamic -> {
										int integer3 = dynamic.get("x").asInt(0);
										int integer4 = dynamic.get("y").asInt(0);
										int integer5 = dynamic.get("z").asInt(0);
										short short6 = a(integer3, integer4, integer5);
										((ShortList)list10.get(integer4 >> 4)).add(short6);
									});
									dynamic5 = dynamic5.set(
										"ToBeTicked", dynamic5.createList(list10.stream().map(shortList -> dynamic9.createList(shortList.stream().map(dynamic9::createShort))))
									);
								}
			
								dynamic7 = DataFixUtils.orElse(typedx.set(DSL.remainderFinder(), dynamic5).write().result(), dynamic5);
							} else {
								dynamic7 = dynamic5;
							}
			
							return (Typed)((Pair)type5.readTyped(dynamic7).result().orElseThrow(() -> new IllegalStateException("Could not read the new chunk"))).getFirst();
						}
					)
			),
			this.writeAndRead("Structure biome inject", this.getInputSchema().getType(ajb.t), this.getOutputSchema().getType(ajb.t))
		);
	}

	private static short a(int integer1, int integer2, int integer3) {
		return (short)(integer1 & 15 | (integer2 & 15) << 4 | (integer3 & 15) << 8);
	}
}
