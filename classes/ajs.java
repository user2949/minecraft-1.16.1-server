import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class ajs extends DataFix {
	private static final int[] a = new int[]{0, 10, 50, 100, 150};

	public static int a(int integer) {
		return a[aec.a(integer - 1, 0, a.length - 1)];
	}

	public ajs(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getChoiceType(ajb.p, "minecraft:villager");
		OpticFinder<?> opticFinder3 = DSL.namedChoice("minecraft:villager", type2);
		OpticFinder<?> opticFinder4 = type2.findField("Offers");
		Type<?> type5 = opticFinder4.type();
		OpticFinder<?> opticFinder6 = type5.findField("Recipes");
		ListType<?> listType7 = (ListType<?>)opticFinder6.type();
		OpticFinder<?> opticFinder8 = listType7.getElement().finder();
		return this.fixTypeEverywhereTyped(
			"Villager level and xp rebuild",
			this.getInputSchema().getType(ajb.p),
			typed -> typed.updateTyped(
					opticFinder3,
					type2,
					typedx -> {
						Dynamic<?> dynamic5 = typedx.get(DSL.remainderFinder());
						int integer6 = dynamic5.get("VillagerData").get("level").asInt(0);
						Typed<?> typed7 = typedx;
						if (integer6 == 0 || integer6 == 1) {
							int integer8 = (Integer)typedx.getOptionalTyped(opticFinder4)
								.flatMap(typedxx -> typedxx.getOptionalTyped(opticFinder6))
								.map(typedxx -> typedxx.getAllTyped(opticFinder8).size())
								.orElse(0);
							integer6 = aec.a(integer8 / 2, 1, 5);
							if (integer6 > 1) {
								typed7 = a(typedx, integer6);
							}
						}
		
						Optional<Number> optional8 = dynamic5.get("Xp").asNumber().result();
						if (!optional8.isPresent()) {
							typed7 = b(typed7, integer6);
						}
		
						return typed7;
					}
				)
		);
	}

	private static Typed<?> a(Typed<?> typed, int integer) {
		return typed.update(DSL.remainderFinder(), dynamic -> dynamic.update("VillagerData", dynamicx -> dynamicx.set("level", dynamicx.createInt(integer))));
	}

	private static Typed<?> b(Typed<?> typed, int integer) {
		int integer3 = a(integer);
		return typed.update(DSL.remainderFinder(), dynamic -> dynamic.set("Xp", dynamic.createInt(integer3)));
	}
}
