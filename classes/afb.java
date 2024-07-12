import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class afb extends aij {
	public afb(Schema schema, boolean boolean2) {
		super(schema, boolean2, "BlockEntityBannerColorFix", ajb.k, "minecraft:banner");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		dynamic = dynamic.update("Base", dynamicx -> dynamicx.createInt(15 - dynamicx.asInt(0)));
		return dynamic.update(
			"Patterns",
			dynamicx -> DataFixUtils.orElse(
					dynamicx.asStreamOpt()
						.map(stream -> stream.map(dynamicxx -> dynamicxx.update("Color", dynamicxxx -> dynamicxxx.createInt(15 - dynamicxxx.asInt(0)))))
						.map(dynamicx::createList)
						.result(),
					dynamicx
				)
		);
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}
}
