import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Function;

public class aeu extends DataFix {
	private final String a;
	private final Function<String, String> b;

	public aeu(Schema schema, boolean boolean2, String string, Function<String, String> function) {
		super(schema, boolean2);
		this.a = string;
		this.b = function;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			this.a, this.getInputSchema().getType(ajb.i), typed -> typed.update(DSL.remainderFinder(), dynamic -> dynamic.updateMapValues(pair -> {
						String string4 = ((Dynamic)pair.getFirst()).asString("");
						return pair.mapFirst(dynamic3 -> dynamic.createString((String)this.b.apply(string4)));
					}))
		);
	}
}
