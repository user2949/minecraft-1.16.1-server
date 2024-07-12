import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;

public class agq extends ajj {
	public static final Map<String, String> a = ImmutableMap.<String, String>builder()
		.put("minecraft:illager_beast_spawn_egg", "minecraft:ravager_spawn_egg")
		.build();

	public agq(Schema schema, boolean boolean2) {
		super("EntityRavagerRenameFix", schema, boolean2);
	}

	@Override
	protected String a(String string) {
		return Objects.equals("minecraft:illager_beast", string) ? "minecraft:ravager" : string;
	}
}
