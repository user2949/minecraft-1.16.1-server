import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class agu extends aij {
	public agu(Schema schema, boolean boolean2) {
		super(schema, boolean2, "EntityShulkerColorFix", ajb.p, "minecraft:shulker");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		return !dynamic.get("Color").map(Dynamic::asNumber).result().isPresent() ? dynamic.set("Color", dynamic.createByte((byte)10)) : dynamic;
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}
}
