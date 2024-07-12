import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;

public class aiy extends DataFix {
	private final String a;
	private final Function<String, String> b;

	public aiy(Schema schema, boolean boolean2, String string, Function<String, String> function) {
		super(schema, boolean2);
		this.a = string;
		this.b = function;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<Pair<String, String>> type2 = DSL.named(ajb.w.typeName(), aka.a());
		if (!Objects.equals(type2, this.getInputSchema().getType(ajb.w))) {
			throw new IllegalStateException("Recipe type is not what was expected.");
		} else {
			return this.fixTypeEverywhere(this.a, type2, dynamicOps -> pair -> pair.mapSecond(this.b));
		}
	}
}
