import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;

public abstract class ags extends DataFix {
	protected final String a;

	public ags(String string, Schema schema, boolean boolean3) {
		super(schema, boolean3);
		this.a = string;
	}

	@Override
	public TypeRewriteRule makeRule() {
		TaggedChoiceType<String> taggedChoiceType2 = (TaggedChoiceType<String>)this.getInputSchema().findChoiceType(ajb.p);
		TaggedChoiceType<String> taggedChoiceType3 = (TaggedChoiceType<String>)this.getOutputSchema().findChoiceType(ajb.p);
		return this.fixTypeEverywhere(this.a, taggedChoiceType2, taggedChoiceType3, dynamicOps -> pair -> {
				String string6 = (String)pair.getFirst();
				Type<?> type7 = (Type<?>)taggedChoiceType2.types().get(string6);
				Pair<String, Typed<?>> pair8 = this.a(string6, this.a(pair.getSecond(), dynamicOps, type7));
				Type<?> type9 = (Type<?>)taggedChoiceType3.types().get(pair8.getFirst());
				if (!type9.equals(pair8.getSecond().getType(), true, true)) {
					throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", type9, pair8.getSecond().getType()));
				} else {
					return Pair.of(pair8.getFirst(), pair8.getSecond().getValue());
				}
			});
	}

	private <A> Typed<A> a(Object object, DynamicOps<?> dynamicOps, Type<A> type) {
		return new Typed<>(type, dynamicOps, (A)object);
	}

	protected abstract Pair<String, Typed<?>> a(String string, Typed<?> typed);
}
