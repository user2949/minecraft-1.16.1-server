import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

public class agp extends ajj {
	public static final Map<String, String> a = ImmutableMap.<String, String>builder()
		.put("minecraft:puffer_fish_spawn_egg", "minecraft:pufferfish_spawn_egg")
		.build();

	public agp(Schema schema, boolean boolean2) {
		super("EntityPufferfishRenameFix", schema, boolean2);
	}

	@Override
	protected String a(String string) {
		return Objects.equals("minecraft:puffer_fish", string) ? "minecraft:pufferfish" : string;
	}
}
