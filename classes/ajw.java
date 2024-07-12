import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;

public class ajw extends DataFix {
	private final String a;
	private final TypeReference b;

	public ajw(Schema schema, String string, TypeReference typeReference) {
		super(schema, true);
		this.a = string;
		this.b = typeReference;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.writeAndRead(this.a, this.getInputSchema().getType(this.b), this.getOutputSchema().getType(this.b));
	}
}
