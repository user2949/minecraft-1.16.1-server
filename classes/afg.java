import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class afg extends aij {
	public afg(Schema schema, boolean boolean2) {
		super(schema, boolean2, "BlockEntityKeepPacked", ajb.k, "DUMMY");
	}

	private static Dynamic<?> a(Dynamic<?> dynamic) {
		return dynamic.set("keepPacked", dynamic.createBoolean(true));
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), afg::a);
	}
}
