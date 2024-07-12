import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class afx extends aij {
	public afx(Schema schema, boolean boolean2) {
		super(schema, boolean2, "Colorless shulker entity fix", ajb.p, "minecraft:shulker");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), dynamic -> dynamic.get("Color").asInt(0) == 10 ? dynamic.set("Color", dynamic.createByte((byte)16)) : dynamic);
	}
}
