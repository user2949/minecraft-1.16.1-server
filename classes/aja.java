import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class aja extends DataFix {
	public aja(Schema schema) {
		super(schema, false);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Schema schema2 = this.getInputSchema();
		return this.fixTypeEverywhereTyped("RedstoneConnectionsFix", schema2.getType(ajb.m), typed -> typed.update(DSL.remainderFinder(), this::a));
	}

	private <T> Dynamic<T> a(Dynamic<T> dynamic) {
		boolean boolean3 = dynamic.get("Name").asString().result().filter("minecraft:redstone_wire"::equals).isPresent();
		return !boolean3
			? dynamic
			: dynamic.update(
				"Properties",
				dynamicx -> {
					String string2 = dynamicx.get("east").asString("none");
					String string3 = dynamicx.get("west").asString("none");
					String string4 = dynamicx.get("north").asString("none");
					String string5 = dynamicx.get("south").asString("none");
					boolean boolean6 = a(string2) || a(string3);
					boolean boolean7 = a(string4) || a(string5);
					String string8 = !a(string2) && !boolean7 ? "side" : string2;
					String string9 = !a(string3) && !boolean7 ? "side" : string3;
					String string10 = !a(string4) && !boolean6 ? "side" : string4;
					String string11 = !a(string5) && !boolean6 ? "side" : string5;
					return dynamicx.update("east", dynamicxx -> dynamicxx.createString(string8))
						.update("west", dynamicxx -> dynamicxx.createString(string9))
						.update("north", dynamicxx -> dynamicxx.createString(string10))
						.update("south", dynamicxx -> dynamicxx.createString(string11));
				}
			);
	}

	private static boolean a(String string) {
		return !"none".equals(string);
	}
}
