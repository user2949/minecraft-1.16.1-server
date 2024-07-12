import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import java.util.Optional;

public class afd extends DataFix {
	public afd(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		OpticFinder<String> opticFinder2 = DSL.fieldFinder("id", aka.a());
		return this.fixTypeEverywhereTyped(
			"BlockEntityCustomNameToComponentFix", this.getInputSchema().getType(ajb.k), typed -> typed.update(DSL.remainderFinder(), dynamic -> {
					Optional<String> optional4 = typed.getOptional(opticFinder2);
					return optional4.isPresent() && Objects.equals(optional4.get(), "minecraft:command_block") ? dynamic : agd.a(dynamic);
				})
		);
	}
}
