import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class agb extends aji {
	public agb(Schema schema, boolean boolean2) {
		super("EntityCatSplitFix", schema, boolean2);
	}

	@Override
	protected Pair<String, Dynamic<?>> a(String string, Dynamic<?> dynamic) {
		if (Objects.equals("minecraft:ocelot", string)) {
			int integer4 = dynamic.get("CatType").asInt(0);
			if (integer4 == 0) {
				String string5 = dynamic.get("Owner").asString("");
				String string6 = dynamic.get("OwnerUUID").asString("");
				if (string5.length() > 0 || string6.length() > 0) {
					dynamic.set("Trusting", dynamic.createBoolean(true));
				}
			} else if (integer4 > 0 && integer4 < 4) {
				dynamic = dynamic.set("CatType", dynamic.createInt(integer4));
				dynamic = dynamic.set("OwnerUUID", dynamic.createString(dynamic.get("OwnerUUID").asString("")));
				return Pair.of("minecraft:cat", dynamic);
			}
		}

		return Pair.of(string, dynamic);
	}
}
