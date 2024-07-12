import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;

public class afk extends DataFix {
	public afk(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.q);
		Type<?> type3 = this.getOutputSchema().getType(ajb.q);
		Type<Pair<String, Either<Integer, String>>> type4 = DSL.named(ajb.q.typeName(), DSL.or(DSL.intType(), aka.a()));
		Type<Pair<String, String>> type5 = DSL.named(ajb.q.typeName(), aka.a());
		if (Objects.equals(type2, type4) && Objects.equals(type3, type5)) {
			return this.fixTypeEverywhere(
				"BlockNameFlatteningFix", type4, type5, dynamicOps -> pair -> pair.mapSecond(either -> either.map(afn::a, string -> afn.a(aka.a(string))))
			);
		} else {
			throw new IllegalStateException("Expected and actual types don't match.");
		}
	}
}
