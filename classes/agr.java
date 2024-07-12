import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;

public class agr extends DataFix {
	private static final Codec<List<Float>> a = Codec.FLOAT.listOf();

	public agr(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"EntityRedundantChanceTagsFix", this.getInputSchema().getType(ajb.p), typed -> typed.update(DSL.remainderFinder(), dynamic -> {
					if (a(dynamic.get("HandDropChances"), 2)) {
						dynamic = dynamic.remove("HandDropChances");
					}
	
					if (a(dynamic.get("ArmorDropChances"), 4)) {
						dynamic = dynamic.remove("ArmorDropChances");
					}
	
					return dynamic;
				})
		);
	}

	private static boolean a(OptionalDynamic<?> optionalDynamic, int integer) {
		return (Boolean)optionalDynamic.flatMap(a::parse)
			.map(list -> list.size() == integer && list.stream().allMatch(float1 -> float1 == 0.0F))
			.result()
			.orElse(false);
	}
}
