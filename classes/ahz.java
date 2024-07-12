import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ahz extends aij {
	public ahz(Schema schema, boolean boolean2) {
		super(schema, boolean2, "JigsawPropertiesFix", ajb.k, "minecraft:jigsaw");
	}

	private static Dynamic<?> a(Dynamic<?> dynamic) {
		String string2 = dynamic.get("attachement_type").asString("minecraft:empty");
		String string3 = dynamic.get("target_pool").asString("minecraft:empty");
		return dynamic.set("name", dynamic.createString(string2))
			.set("target", dynamic.createString(string2))
			.remove("attachement_type")
			.set("pool", dynamic.createString(string3))
			.remove("target_pool");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), ahz::a);
	}
}
