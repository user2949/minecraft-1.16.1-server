import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class afh extends aij {
	public afh(Schema schema, boolean boolean2) {
		super(schema, boolean2, "BlockEntityShulkerBoxColorFix", ajb.k, "minecraft:shulker_box");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), dynamic -> dynamic.remove("Color"));
	}
}
