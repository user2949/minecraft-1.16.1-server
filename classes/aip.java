import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class aip extends DataFix {
	public aip(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"OptionsAddTextBackgroundFix",
			this.getInputSchema().getType(ajb.e),
			typed -> typed.update(
					DSL.remainderFinder(),
					dynamic -> DataFixUtils.orElse(
							dynamic.get("chatOpacity").asString().map(string -> dynamic.set("textBackgroundOpacity", dynamic.createDouble(this.a(string)))).result(), dynamic
						)
				)
		);
	}

	private double a(String string) {
		try {
			double double3 = 0.9 * Double.parseDouble(string) + 0.1;
			return double3 / 2.0;
		} catch (NumberFormatException var4) {
			return 0.5;
		}
	}
}
