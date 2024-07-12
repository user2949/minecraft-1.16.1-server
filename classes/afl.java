import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class afl extends DataFix {
	private final String a;

	public afl(Schema schema, String string) {
		super(schema, false);
		this.a = string;
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.q);
		Type<Pair<String, String>> type3 = DSL.named(ajb.q.typeName(), aka.a());
		if (!Objects.equals(type2, type3)) {
			throw new IllegalStateException("block type is not what was expected.");
		} else {
			TypeRewriteRule typeRewriteRule4 = this.fixTypeEverywhere(this.a + " for block", type3, dynamicOps -> pair -> pair.mapSecond(this::a));
			TypeRewriteRule typeRewriteRule5 = this.fixTypeEverywhereTyped(
				this.a + " for block_state", this.getInputSchema().getType(ajb.m), typed -> typed.update(DSL.remainderFinder(), dynamic -> {
						Optional<String> optional3 = dynamic.get("Name").asString().result();
						return optional3.isPresent() ? dynamic.set("Name", dynamic.createString(this.a((String)optional3.get()))) : dynamic;
					})
			);
			return TypeRewriteRule.seq(typeRewriteRule4, typeRewriteRule5);
		}
	}

	protected abstract String a(String string);

	public static DataFix a(Schema schema, String string, Function<String, String> function) {
		return new afl(schema, string) {
			@Override
			protected String a(String string) {
				return (String)function.apply(string);
			}
		};
	}
}
