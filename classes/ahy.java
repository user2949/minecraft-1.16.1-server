import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import org.apache.commons.lang3.StringUtils;

public class ahy extends DataFix {
	public ahy(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		return dynamic.update("pages", dynamic2 -> DataFixUtils.orElse(dynamic2.asStreamOpt().map(stream -> stream.map(dynamicx -> {
					if (!dynamicx.asString().result().isPresent()) {
						return dynamicx;
					} else {
						String string2 = dynamicx.asString("");
						mr mr3 = null;
						if (!"null".equals(string2) && !StringUtils.isEmpty(string2)) {
							if (string2.charAt(0) == '"' && string2.charAt(string2.length() - 1) == '"' || string2.charAt(0) == '{' && string2.charAt(string2.length() - 1) == '}') {
								try {
									mr3 = adt.a(afi.a, string2, mr.class, true);
									if (mr3 == null) {
										mr3 = nd.d;
									}
								} catch (JsonParseException var6) {
								}

								if (mr3 == null) {
									try {
										mr3 = mr.a.a(string2);
									} catch (JsonParseException var5) {
									}
								}

								if (mr3 == null) {
									try {
										mr3 = mr.a.b(string2);
									} catch (JsonParseException var4) {
									}
								}

								if (mr3 == null) {
									mr3 = new nd(string2);
								}
							} else {
								mr3 = new nd(string2);
							}
						} else {
							mr3 = nd.d;
						}

						return dynamicx.createString(mr.a.a(mr3));
					}
				})).map(dynamic::createList).result(), dynamic.emptyList()));
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<?> opticFinder3 = type2.findField("tag");
		return this.fixTypeEverywhereTyped(
			"ItemWrittenBookPagesStrictJsonFix", type2, typed -> typed.updateTyped(opticFinder3, typedx -> typedx.update(DSL.remainderFinder(), this::a))
		);
	}
}
