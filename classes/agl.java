import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;

public class agl extends DataFix {
	private static final List<String> a = Lists.<String>newArrayList("MinecartRideable", "MinecartChest", "MinecartFurnace");

	public agl(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		TaggedChoiceType<String> taggedChoiceType2 = (TaggedChoiceType<String>)this.getInputSchema().findChoiceType(ajb.p);
		TaggedChoiceType<String> taggedChoiceType3 = (TaggedChoiceType<String>)this.getOutputSchema().findChoiceType(ajb.p);
		return this.fixTypeEverywhere(
			"EntityMinecartIdentifiersFix",
			taggedChoiceType2,
			taggedChoiceType3,
			dynamicOps -> pair -> {
					if (!Objects.equals(pair.getFirst(), "Minecart")) {
						return pair;
					} else {
						Typed<? extends Pair<String, ?>> typed5 = (Typed<? extends Pair<String, ?>>)taggedChoiceType2.point(dynamicOps, "Minecart", pair.getSecond())
							.orElseThrow(IllegalStateException::new);
						Dynamic<?> dynamic6 = typed5.getOrCreate(DSL.remainderFinder());
						int integer8 = dynamic6.get("Type").asInt(0);
						String string7;
						if (integer8 > 0 && integer8 < a.size()) {
							string7 = (String)a.get(integer8);
						} else {
							string7 = "MinecartRideable";
						}
	
						return Pair.of(
							string7,
							typed5.write()
								.map(dynamic -> ((Type)taggedChoiceType3.types().get(string7)).read(dynamic))
								.result()
								.orElseThrow(() -> new IllegalStateException("Could not read the new minecart."))
						);
					}
				}
		);
	}
}
