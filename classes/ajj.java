import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;

public abstract class ajj extends DataFix {
	private final String a;

	public ajj(String string, Schema schema, boolean boolean3) {
		super(schema, boolean3);
		this.a = string;
	}

	@Override
	public TypeRewriteRule makeRule() {
		TaggedChoiceType<String> taggedChoiceType2 = (TaggedChoiceType<String>)this.getInputSchema().findChoiceType(ajb.p);
		TaggedChoiceType<String> taggedChoiceType3 = (TaggedChoiceType<String>)this.getOutputSchema().findChoiceType(ajb.p);
		Type<Pair<String, String>> type4 = DSL.named(ajb.n.typeName(), aka.a());
		if (!Objects.equals(this.getOutputSchema().getType(ajb.n), type4)) {
			throw new IllegalStateException("Entity name type is not what was expected.");
		} else {
			return TypeRewriteRule.seq(this.fixTypeEverywhere(this.a, taggedChoiceType2, taggedChoiceType3, dynamicOps -> pair -> pair.mapFirst(string -> {
						String string5 = this.a(string);
						Type<?> type6 = (Type<?>)taggedChoiceType2.types().get(string);
						Type<?> type7 = (Type<?>)taggedChoiceType3.types().get(string5);
						if (!type7.equals(type6, true, true)) {
							throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", type7, type6));
						} else {
							return string5;
						}
					})), this.fixTypeEverywhere(this.a + " for entity name", type4, dynamicOps -> pair -> pair.mapSecond(this::a)));
		}
	}

	protected abstract String a(String string);
}
