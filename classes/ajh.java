import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;

public class ajh extends DataFix {
	public ajh(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.writeFixAndRead("SavedDataVillageCropFix", this.getInputSchema().getType(ajb.t), this.getOutputSchema().getType(ajb.t), this::a);
	}

	private <T> Dynamic<T> a(Dynamic<T> dynamic) {
		return dynamic.update("Children", ajh::b);
	}

	private static <T> Dynamic<T> b(Dynamic<T> dynamic) {
		return (Dynamic<T>)dynamic.asStreamOpt().map(ajh::a).map(dynamic::createList).result().orElse(dynamic);
	}

	private static Stream<? extends Dynamic<?>> a(Stream<? extends Dynamic<?>> stream) {
		return stream.map(dynamic -> {
			String string2 = dynamic.get("id").asString("");
			if ("ViF".equals(string2)) {
				return c(dynamic);
			} else {
				return "ViDF".equals(string2) ? d(dynamic) : dynamic;
			}
		});
	}

	private static <T> Dynamic<T> c(Dynamic<T> dynamic) {
		dynamic = a(dynamic, "CA");
		return a(dynamic, "CB");
	}

	private static <T> Dynamic<T> d(Dynamic<T> dynamic) {
		dynamic = a(dynamic, "CA");
		dynamic = a(dynamic, "CB");
		dynamic = a(dynamic, "CC");
		return a(dynamic, "CD");
	}

	private static <T> Dynamic<T> a(Dynamic<T> dynamic, String string) {
		return dynamic.get(string).asNumber().result().isPresent() ? dynamic.set(string, afn.b(dynamic.get(string).asInt(0) << 4)) : dynamic;
	}
}
