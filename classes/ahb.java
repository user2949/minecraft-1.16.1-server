import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ahb extends aij {
	public ahb(Schema schema, boolean boolean2) {
		super(schema, boolean2, "EntityWolfColorFix", ajb.p, "minecraft:wolf");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		return dynamic.update("CollarColor", dynamicx -> dynamicx.createByte((byte)(15 - dynamicx.asInt(0))));
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}
}
