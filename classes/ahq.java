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

public class ahq extends DataFix {
	public static final String[] a = new String[]{
		"minecraft:white_shulker_box",
		"minecraft:orange_shulker_box",
		"minecraft:magenta_shulker_box",
		"minecraft:light_blue_shulker_box",
		"minecraft:yellow_shulker_box",
		"minecraft:lime_shulker_box",
		"minecraft:pink_shulker_box",
		"minecraft:gray_shulker_box",
		"minecraft:silver_shulker_box",
		"minecraft:cyan_shulker_box",
		"minecraft:purple_shulker_box",
		"minecraft:blue_shulker_box",
		"minecraft:brown_shulker_box",
		"minecraft:green_shulker_box",
		"minecraft:red_shulker_box",
		"minecraft:black_shulker_box"
	};

	public ahq(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<Pair<String, String>> opticFinder3 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		OpticFinder<?> opticFinder4 = type2.findField("tag");
		OpticFinder<?> opticFinder5 = opticFinder4.type().findField("BlockEntityTag");
		return this.fixTypeEverywhereTyped(
			"ItemShulkerBoxColorFix",
			type2,
			typed -> {
				Optional<Pair<String, String>> optional5 = typed.getOptional(opticFinder3);
				if (optional5.isPresent() && Objects.equals(((Pair)optional5.get()).getSecond(), "minecraft:shulker_box")) {
					Optional<? extends Typed<?>> optional6 = typed.getOptionalTyped(opticFinder4);
					if (optional6.isPresent()) {
						Typed<?> typed7 = (Typed<?>)optional6.get();
						Optional<? extends Typed<?>> optional8 = typed7.getOptionalTyped(opticFinder5);
						if (optional8.isPresent()) {
							Typed<?> typed9 = (Typed<?>)optional8.get();
							Dynamic<?> dynamic10 = typed9.get(DSL.remainderFinder());
							int integer11 = dynamic10.get("Color").asInt(0);
							dynamic10.remove("Color");
							return typed.set(opticFinder4, typed7.set(opticFinder5, typed9.set(DSL.remainderFinder(), dynamic10)))
								.set(opticFinder3, Pair.of(ajb.r.typeName(), a[integer11 % 16]));
						}
					}
				}
	
				return typed;
			}
		);
	}
}
