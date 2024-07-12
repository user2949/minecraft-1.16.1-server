import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.List;

public class agv extends aij {
	public agv(Schema schema) {
		super(schema, false, "EntityShulkerRotationFix", ajb.p, "minecraft:shulker");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		List<Double> list3 = dynamic.get("Rotation").asList(dynamicx -> dynamicx.asDouble(180.0));
		if (!list3.isEmpty()) {
			list3.set(0, (Double)list3.get(0) - 180.0);
			return dynamic.set("Rotation", dynamic.createList(list3.stream().map(dynamic::createDouble)));
		} else {
			return dynamic;
		}
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}
}
