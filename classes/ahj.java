import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class ahj extends DataFix {
	public ahj(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.t);
		Type<?> type3 = this.getOutputSchema().getType(ajb.t);
		return this.writeFixAndRead("IglooMetadataRemovalFix", type2, type3, ahj::a);
	}

	private static <T> Dynamic<T> a(Dynamic<T> dynamic) {
		boolean boolean2 = (Boolean)dynamic.get("Children").asStreamOpt().map(stream -> stream.allMatch(ahj::c)).result().orElse(false);
		return boolean2 ? dynamic.set("id", dynamic.createString("Igloo")).remove("Children") : dynamic.update("Children", ahj::b);
	}

	private static <T> Dynamic<T> b(Dynamic<T> dynamic) {
		return (Dynamic<T>)dynamic.asStreamOpt().map(stream -> stream.filter(dynamicx -> !c(dynamicx))).map(dynamic::createList).result().orElse(dynamic);
	}

	private static boolean c(Dynamic<?> dynamic) {
		return dynamic.get("id").asString("").equals("Iglu");
	}
}
