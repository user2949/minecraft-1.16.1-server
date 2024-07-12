import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class afz extends aij {
	public afz(Schema schema, boolean boolean2) {
		super(schema, boolean2, "EntityArmorStandSilentFix", ajb.p, "ArmorStand");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		return dynamic.get("Silent").asBoolean(false) && !dynamic.get("Marker").asBoolean(false) ? dynamic.remove("Silent") : dynamic;
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}
}
