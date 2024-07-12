import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;

public abstract class aij extends DataFix {
	private final String a;
	private final String b;
	private final TypeReference c;

	public aij(Schema schema, boolean boolean2, String string3, TypeReference typeReference, String string5) {
		super(schema, boolean2);
		this.a = string3;
		this.c = typeReference;
		this.b = string5;
	}

	@Override
	public TypeRewriteRule makeRule() {
		OpticFinder<?> opticFinder2 = DSL.namedChoice(this.b, this.getInputSchema().getChoiceType(this.c, this.b));
		return this.fixTypeEverywhereTyped(
			this.a,
			this.getInputSchema().getType(this.c),
			this.getOutputSchema().getType(this.c),
			typed -> typed.updateTyped(opticFinder2, this.getOutputSchema().getChoiceType(this.c, this.b), this::a)
		);
	}

	protected abstract Typed<?> a(Typed<?> typed);
}
