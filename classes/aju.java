import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Set;

public class aju extends DataFix {
	private static final Set<String> a = ImmutableSet.of(
		"minecraft:andesite_wall",
		"minecraft:brick_wall",
		"minecraft:cobblestone_wall",
		"minecraft:diorite_wall",
		"minecraft:end_stone_brick_wall",
		"minecraft:granite_wall",
		"minecraft:mossy_cobblestone_wall",
		"minecraft:mossy_stone_brick_wall",
		"minecraft:nether_brick_wall",
		"minecraft:prismarine_wall",
		"minecraft:red_nether_brick_wall",
		"minecraft:red_sandstone_wall",
		"minecraft:sandstone_wall",
		"minecraft:stone_brick_wall"
	);

	public aju(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped("WallPropertyFix", this.getInputSchema().getType(ajb.m), typed -> typed.update(DSL.remainderFinder(), aju::a));
	}

	private static String a(String string) {
		return "true".equals(string) ? "low" : "none";
	}

	private static <T> Dynamic<T> a(Dynamic<T> dynamic, String string) {
		return dynamic.update(string, dynamicx -> DataFixUtils.orElse(dynamicx.asString().result().map(aju::a).map(dynamicx::createString), dynamicx));
	}

	private static <T> Dynamic<T> a(Dynamic<T> dynamic) {
		boolean boolean2 = dynamic.get("Name").asString().result().filter(a::contains).isPresent();
		return !boolean2 ? dynamic : dynamic.update("Properties", dynamicx -> {
			Dynamic<?> dynamic2 = a(dynamicx, "east");
			dynamic2 = a(dynamic2, "west");
			dynamic2 = a(dynamic2, "north");
			return a(dynamic2, "south");
		});
	}
}
