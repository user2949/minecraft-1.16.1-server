import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ajr extends aij {
	public ajr(Schema schema) {
		super(schema, false, "Villager Follow Range Fix", ajb.p, "minecraft:villager");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), ajr::a);
	}

	private static Dynamic<?> a(Dynamic<?> dynamic) {
		return dynamic.update(
			"Attributes",
			dynamic2 -> dynamic.createList(
					dynamic2.asStream()
						.map(
							dynamicx -> dynamicx.get("Name").asString("").equals("generic.follow_range") && dynamicx.get("Base").asDouble(0.0) == 16.0
									? dynamicx.set("Base", dynamicx.createDouble(48.0))
									: dynamicx
						)
				)
		);
	}
}
