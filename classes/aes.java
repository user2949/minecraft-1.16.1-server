import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;

public class aes extends DataFix {
	private final String a;
	private final TypeReference b;

	public aes(Schema schema, String string, TypeReference typeReference) {
		super(schema, true);
		this.a = string;
		this.b = typeReference;
	}

	@Override
	public TypeRewriteRule makeRule() {
		TaggedChoiceType<?> taggedChoiceType2 = this.getInputSchema().findChoiceType(this.b);
		TaggedChoiceType<?> taggedChoiceType3 = this.getOutputSchema().findChoiceType(this.b);
		return this.a(this.a, taggedChoiceType2, taggedChoiceType3);
	}

	protected final <K> TypeRewriteRule a(String string, TaggedChoiceType<K> taggedChoiceType2, TaggedChoiceType<?> taggedChoiceType3) {
		if (taggedChoiceType2.getKeyType() != taggedChoiceType3.getKeyType()) {
			throw new IllegalStateException("Could not inject: key type is not the same");
		} else {
			return this.fixTypeEverywhere(string, taggedChoiceType2, (Type<Pair<K, ?>>)taggedChoiceType3, dynamicOps -> pair -> {
					if (!((TaggedChoiceType<Object>)taggedChoiceType3).hasType(pair.getFirst())) {
						throw new IllegalArgumentException(String.format("Unknown type %s in %s ", pair.getFirst(), this.b));
					} else {
						return pair;
					}
				});
		}
	}
}
