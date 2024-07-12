import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class agd extends DataFix {
	public agd(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		OpticFinder<String> opticFinder2 = DSL.fieldFinder("id", aka.a());
		return this.fixTypeEverywhereTyped(
			"EntityCustomNameToComponentFix", this.getInputSchema().getType(ajb.p), typed -> typed.update(DSL.remainderFinder(), dynamic -> {
					Optional<String> optional4 = typed.getOptional(opticFinder2);
					return optional4.isPresent() && Objects.equals(optional4.get(), "minecraft:commandblock_minecart") ? dynamic : a(dynamic);
				})
		);
	}

	public static Dynamic<?> a(Dynamic<?> dynamic) {
		String string2 = dynamic.get("CustomName").asString("");
		return string2.isEmpty() ? dynamic.remove("CustomName") : dynamic.set("CustomName", dynamic.createString(mr.a.a(new nd(string2))));
	}
}
