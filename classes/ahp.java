import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;

public abstract class ahp extends DataFix {
	private final String a;

	public ahp(Schema schema, String string) {
		super(schema, false);
		this.a = string;
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<Pair<String, String>> type2 = DSL.named(ajb.r.typeName(), aka.a());
		if (!Objects.equals(this.getInputSchema().getType(ajb.r), type2)) {
			throw new IllegalStateException("item name type is not what was expected.");
		} else {
			return this.fixTypeEverywhere(this.a, type2, dynamicOps -> pair -> pair.mapSecond(this::a));
		}
	}

	protected abstract String a(String string);

	public static DataFix a(Schema schema, String string, Function<String, String> function) {
		return new ahp(schema, string) {
			@Override
			protected String a(String string) {
				return (String)function.apply(string);
			}
		};
	}
}
