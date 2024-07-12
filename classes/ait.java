import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Locale;
import java.util.Optional;

public class ait extends DataFix {
	public ait(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"OptionsLowerCaseLanguageFix", this.getInputSchema().getType(ajb.e), typed -> typed.update(DSL.remainderFinder(), dynamic -> {
					Optional<String> optional2 = dynamic.get("lang").asString().result();
					return optional2.isPresent() ? dynamic.set("lang", dynamic.createString(((String)optional2.get()).toLowerCase(Locale.ROOT))) : dynamic;
				})
		);
	}
}
