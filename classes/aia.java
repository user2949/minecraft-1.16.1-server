import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;

public class aia extends DataFix {
	private static final Map<String, String> a = ImmutableMap.<String, String>builder()
		.put("down", "down_south")
		.put("up", "up_north")
		.put("north", "north_up")
		.put("south", "south_up")
		.put("west", "west_up")
		.put("east", "east_up")
		.build();

	public aia(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	private static Dynamic<?> a(Dynamic<?> dynamic) {
		Optional<String> optional2 = dynamic.get("Name").asString().result();
		return optional2.equals(Optional.of("minecraft:jigsaw")) ? dynamic.update("Properties", dynamicx -> {
			String string2 = dynamicx.get("facing").asString("north");
			return dynamicx.remove("facing").set("orientation", dynamicx.createString((String)a.getOrDefault(string2, string2)));
		}) : dynamic;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped("jigsaw_rotation_fix", this.getInputSchema().getType(ajb.m), typed -> typed.update(DSL.remainderFinder(), aia::a));
	}
}
