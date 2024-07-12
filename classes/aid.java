import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.math.NumberUtils;

public class aid extends DataFix {
	private static final Splitter a = Splitter.on(';').limit(5);
	private static final Splitter b = Splitter.on(',');
	private static final Splitter c = Splitter.on('x').limit(2);
	private static final Splitter d = Splitter.on('*').limit(2);
	private static final Splitter e = Splitter.on(':').limit(3);

	public aid(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped("LevelFlatGeneratorInfoFix", this.getInputSchema().getType(ajb.a), typed -> typed.update(DSL.remainderFinder(), this::a));
	}

	private Dynamic<?> a(Dynamic<?> dynamic) {
		return dynamic.get("generatorName").asString("").equalsIgnoreCase("flat")
			? dynamic.update("generatorOptions", dynamicx -> DataFixUtils.orElse(dynamicx.asString().map(this::a).map(dynamicx::createString).result(), dynamicx))
			: dynamic;
	}

	@VisibleForTesting
	String a(String string) {
		if (string.isEmpty()) {
			return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
		} else {
			Iterator<String> iterator3 = a.split(string).iterator();
			String string4 = (String)iterator3.next();
			int integer5;
			String string6;
			if (iterator3.hasNext()) {
				integer5 = NumberUtils.toInt(string4, 0);
				string6 = (String)iterator3.next();
			} else {
				integer5 = 0;
				string6 = string4;
			}

			if (integer5 >= 0 && integer5 <= 3) {
				StringBuilder stringBuilder7 = new StringBuilder();
				Splitter splitter8 = integer5 < 3 ? c : d;
				stringBuilder7.append((String)StreamSupport.stream(b.split(string6).spliterator(), false).map(stringx -> {
					List<String> list6 = splitter8.splitToList(stringx);
					int integer4;
					String string5;
					if (list6.size() == 2) {
						integer4 = NumberUtils.toInt((String)list6.get(0));
						string5 = (String)list6.get(1);
					} else {
						integer4 = 1;
						string5 = (String)list6.get(0);
					}

					List<String> list7 = e.splitToList(string5);
					int integer8 = ((String)list7.get(0)).equals("minecraft") ? 1 : 0;
					String string9 = (String)list7.get(integer8);
					int integer10 = integer5 == 3 ? aga.a("minecraft:" + string9) : NumberUtils.toInt(string9, 0);
					int integer11 = integer8 + 1;
					int integer12 = list7.size() > integer11 ? NumberUtils.toInt((String)list7.get(integer11), 0) : 0;
					return (integer4 == 1 ? "" : integer4 + "*") + afn.b(integer10 << 4 | integer12).get("Name").asString("");
				}).collect(Collectors.joining(",")));

				while (iterator3.hasNext()) {
					stringBuilder7.append(';').append((String)iterator3.next());
				}

				return stringBuilder7.toString();
			} else {
				return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
			}
		}
	}
}
