import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;

public class agc extends ajj {
	public static final Map<String, String> a = ImmutableMap.<String, String>builder()
		.put("minecraft:salmon_mob", "minecraft:salmon")
		.put("minecraft:cod_mob", "minecraft:cod")
		.build();
	public static final Map<String, String> b = ImmutableMap.<String, String>builder()
		.put("minecraft:salmon_mob_spawn_egg", "minecraft:salmon_spawn_egg")
		.put("minecraft:cod_mob_spawn_egg", "minecraft:cod_spawn_egg")
		.build();

	public agc(Schema schema, boolean boolean2) {
		super("EntityCodSalmonFix", schema, boolean2);
	}

	@Override
	protected String a(String string) {
		return (String)a.getOrDefault(string, string);
	}
}
