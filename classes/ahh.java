import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ahh extends aij {
	public ahh(Schema schema, String string) {
		super(schema, false, "Gossip for for " + string, ajb.p, string);
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(
			DSL.remainderFinder(),
			dynamic -> dynamic.update(
					"Gossips",
					dynamicx -> DataFixUtils.orElse(
							dynamicx.asStreamOpt()
								.result()
								.map(stream -> stream.map(dynamicxx -> (Dynamic)aer.c(dynamicxx, "Target", "Target").orElse(dynamicxx)))
								.map(dynamicx::createList),
							dynamicx
						)
				)
		);
	}
}
