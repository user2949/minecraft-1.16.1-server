import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Objects;

public class ajc extends DataFix {
	private final String a;
	private final Map<String, String> b;

	public ajc(Schema schema, boolean boolean2, String string, Map<String, String> map) {
		super(schema, boolean2);
		this.b = map;
		this.a = string;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<Pair<String, String>> type2 = DSL.named(ajb.x.typeName(), aka.a());
		if (!Objects.equals(type2, this.getInputSchema().getType(ajb.x))) {
			throw new IllegalStateException("Biome type is not what was expected.");
		} else {
			return this.fixTypeEverywhere(this.a, type2, dynamicOps -> pair -> pair.mapSecond(string -> (String)this.b.getOrDefault(string, string)));
		}
	}
}
