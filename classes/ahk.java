import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ahk extends DataFix {
	public ahk(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<Pair<String, String>> opticFinder3 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		OpticFinder<?> opticFinder4 = type2.findField("tag");
		OpticFinder<?> opticFinder5 = opticFinder4.type().findField("BlockEntityTag");
		return this.fixTypeEverywhereTyped(
			"ItemBannerColorFix",
			type2,
			typed -> {
				Optional<Pair<String, String>> optional5 = typed.getOptional(opticFinder3);
				if (optional5.isPresent() && Objects.equals(((Pair)optional5.get()).getSecond(), "minecraft:banner")) {
					Dynamic<?> dynamic6 = typed.get(DSL.remainderFinder());
					Optional<? extends Typed<?>> optional7 = typed.getOptionalTyped(opticFinder4);
					if (optional7.isPresent()) {
						Typed<?> typed8 = (Typed<?>)optional7.get();
						Optional<? extends Typed<?>> optional9 = typed8.getOptionalTyped(opticFinder5);
						if (optional9.isPresent()) {
							Typed<?> typed10 = (Typed<?>)optional9.get();
							Dynamic<?> dynamic11 = typed8.get(DSL.remainderFinder());
							Dynamic<?> dynamic12 = typed10.getOrCreate(DSL.remainderFinder());
							if (dynamic12.get("Base").asNumber().result().isPresent()) {
								dynamic6 = dynamic6.set("Damage", dynamic6.createShort((short)(dynamic12.get("Base").asInt(0) & 15)));
								Optional<? extends Dynamic<?>> optional13 = dynamic11.get("display").result();
								if (optional13.isPresent()) {
									Dynamic<?> dynamic14 = (Dynamic<?>)optional13.get();
									Dynamic<?> dynamic15 = dynamic14.createMap(
										ImmutableMap.of(dynamic14.createString("Lore"), dynamic14.createList(Stream.of(dynamic14.createString("(+NBT"))))
									);
									if (Objects.equals(dynamic14, dynamic15)) {
										return typed.set(DSL.remainderFinder(), dynamic6);
									}
								}
	
								dynamic12.remove("Base");
								return typed.set(DSL.remainderFinder(), dynamic6).set(opticFinder4, typed8.set(opticFinder5, typed10.set(DSL.remainderFinder(), dynamic12)));
							}
						}
					}
	
					return typed.set(DSL.remainderFinder(), dynamic6);
				} else {
					return typed;
				}
			}
		);
	}
}
