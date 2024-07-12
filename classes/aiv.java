import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class aiv extends aer {
	public aiv(Schema schema) {
		super(schema, ajb.b);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"PlayerUUIDFix",
			this.getInputSchema().getType(this.b),
			typed -> {
				OpticFinder<?> opticFinder2 = typed.getType().findField("RootVehicle");
				return typed.updateTyped(
						opticFinder2, opticFinder2.type(), typedx -> typedx.update(DSL.remainderFinder(), dynamic -> (Dynamic)c(dynamic, "Attach", "Attach").orElse(dynamic))
					)
					.update(DSL.remainderFinder(), dynamic -> aha.c(aha.b(dynamic)));
			}
		);
	}
}
