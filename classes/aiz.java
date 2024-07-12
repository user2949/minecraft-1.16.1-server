import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;

public class aiz extends aiy {
	private static final Map<String, String> a = ImmutableMap.<String, String>builder()
		.put("minecraft:acacia_bark", "minecraft:acacia_wood")
		.put("minecraft:birch_bark", "minecraft:birch_wood")
		.put("minecraft:dark_oak_bark", "minecraft:dark_oak_wood")
		.put("minecraft:jungle_bark", "minecraft:jungle_wood")
		.put("minecraft:oak_bark", "minecraft:oak_wood")
		.put("minecraft:spruce_bark", "minecraft:spruce_wood")
		.build();

	public aiz(Schema schema, boolean boolean2) {
		super(schema, boolean2, "Recipes renamening fix", string -> (String)a.getOrDefault(string, string));
	}
}
