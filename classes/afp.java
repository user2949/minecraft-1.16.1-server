import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class afp extends aij {
	public afp(Schema schema, boolean boolean2) {
		super(schema, boolean2, "CatTypeFix", ajb.p, "minecraft:cat");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		return dynamic.get("CatType").asInt(0) == 9 ? dynamic.set("CatType", dynamic.createInt(10)) : dynamic;
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}
}
