import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import java.util.Map;

public class afe extends DataFix {
	private static final Map<String, String> a = DataFixUtils.make(Maps.<String, String>newHashMap(), hashMap -> {
		hashMap.put("Airportal", "minecraft:end_portal");
		hashMap.put("Banner", "minecraft:banner");
		hashMap.put("Beacon", "minecraft:beacon");
		hashMap.put("Cauldron", "minecraft:brewing_stand");
		hashMap.put("Chest", "minecraft:chest");
		hashMap.put("Comparator", "minecraft:comparator");
		hashMap.put("Control", "minecraft:command_block");
		hashMap.put("DLDetector", "minecraft:daylight_detector");
		hashMap.put("Dropper", "minecraft:dropper");
		hashMap.put("EnchantTable", "minecraft:enchanting_table");
		hashMap.put("EndGateway", "minecraft:end_gateway");
		hashMap.put("EnderChest", "minecraft:ender_chest");
		hashMap.put("FlowerPot", "minecraft:flower_pot");
		hashMap.put("Furnace", "minecraft:furnace");
		hashMap.put("Hopper", "minecraft:hopper");
		hashMap.put("MobSpawner", "minecraft:mob_spawner");
		hashMap.put("Music", "minecraft:noteblock");
		hashMap.put("Piston", "minecraft:piston");
		hashMap.put("RecordPlayer", "minecraft:jukebox");
		hashMap.put("Sign", "minecraft:sign");
		hashMap.put("Skull", "minecraft:skull");
		hashMap.put("Structure", "minecraft:structure_block");
		hashMap.put("Trap", "minecraft:dispenser");
	});

	public afe(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		Type<?> type3 = this.getOutputSchema().getType(ajb.l);
		TaggedChoiceType<String> taggedChoiceType4 = (TaggedChoiceType<String>)this.getInputSchema().findChoiceType(ajb.k);
		TaggedChoiceType<String> taggedChoiceType5 = (TaggedChoiceType<String>)this.getOutputSchema().findChoiceType(ajb.k);
		return TypeRewriteRule.seq(
			this.convertUnchecked("item stack block entity name hook converter", type2, type3),
			this.fixTypeEverywhere(
				"BlockEntityIdFix", taggedChoiceType4, taggedChoiceType5, dynamicOps -> pair -> pair.mapFirst(string -> (String)a.getOrDefault(string, string))
			)
		);
	}
}
