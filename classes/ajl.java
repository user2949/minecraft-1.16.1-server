import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ajl extends aij {
	public ajl(Schema schema, boolean boolean2) {
		super(schema, boolean2, "StriderGravityFix", ajb.p, "minecraft:strider");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		return dynamic.get("NoGravity").asBoolean(false) ? dynamic.set("NoGravity", dynamic.createBoolean(false)) : dynamic;
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}
}
