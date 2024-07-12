import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

public class ahe extends ajj {
	public static final Map<String, String> a = ImmutableMap.<String, String>builder()
		.put("minecraft:zombie_pigman_spawn_egg", "minecraft:zombified_piglin_spawn_egg")
		.build();

	public ahe(Schema schema) {
		super("EntityZombifiedPiglinRenameFix", schema, true);
	}

	@Override
	protected String a(String string) {
		return Objects.equals("minecraft:zombie_pigman", string) ? "minecraft:zombified_piglin" : string;
	}
}
