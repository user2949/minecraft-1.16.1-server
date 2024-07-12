import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class agk extends aij {
	public agk(Schema schema, boolean boolean2) {
		super(schema, boolean2, "EntityItemFrameDirectionFix", ajb.p, "minecraft:item_frame");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		return dynamic.set("Facing", dynamic.createByte(a(dynamic.get("Facing").asByte((byte)0))));
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}

	private static byte a(byte byte1) {
		switch (byte1) {
			case 0:
				return 3;
			case 1:
				return 4;
			case 2:
			default:
				return 2;
			case 3:
				return 5;
		}
	}
}
