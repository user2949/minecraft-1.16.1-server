import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class aiu extends DataFix {
	private final String a;
	private final String b;
	private final String c;

	public aiu(Schema schema, boolean boolean2, String string3, String string4, String string5) {
		super(schema, boolean2);
		this.a = string3;
		this.b = string4;
		this.c = string5;
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			this.a,
			this.getInputSchema().getType(ajb.e),
			typed -> typed.update(
					DSL.remainderFinder(), dynamic -> DataFixUtils.orElse(dynamic.get(this.b).result().map(dynamic2 -> dynamic.set(this.c, dynamic2).remove(this.b)), dynamic)
				)
		);
	}
}
