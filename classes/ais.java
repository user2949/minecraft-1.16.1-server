import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.stream.Collectors;

public class ais extends DataFix {
	public ais(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"OptionsKeyTranslationFix",
			this.getInputSchema().getType(ajb.e),
			typed -> typed.update(
					DSL.remainderFinder(),
					dynamic -> (Dynamic)dynamic.getMapValues()
							.map(map -> dynamic.createMap((Map<? extends Dynamic<?>, ? extends Dynamic<?>>)map.entrySet().stream().map(entry -> {
									if (((Dynamic)entry.getKey()).asString("").startsWith("key_")) {
										String string3 = ((Dynamic)entry.getValue()).asString("");
										if (!string3.startsWith("key.mouse") && !string3.startsWith("scancode.")) {
											return Pair.of(entry.getKey(), dynamic.createString("key.keyboard." + string3.substring("key.".length())));
										}
									}
			
									return Pair.of(entry.getKey(), entry.getValue());
								}).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))))
							.result()
							.orElse(dynamic)
				)
		);
	}
}
