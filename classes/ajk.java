import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class ajk extends DataFix {
	private static final Set<String> a = ImmutableSet.<String>builder()
		.add("stat.craftItem.minecraft.spawn_egg")
		.add("stat.useItem.minecraft.spawn_egg")
		.add("stat.breakItem.minecraft.spawn_egg")
		.add("stat.pickup.minecraft.spawn_egg")
		.add("stat.drop.minecraft.spawn_egg")
		.build();
	private static final Map<String, String> b = ImmutableMap.<String, String>builder()
		.put("stat.leaveGame", "minecraft:leave_game")
		.put("stat.playOneMinute", "minecraft:play_one_minute")
		.put("stat.timeSinceDeath", "minecraft:time_since_death")
		.put("stat.sneakTime", "minecraft:sneak_time")
		.put("stat.walkOneCm", "minecraft:walk_one_cm")
		.put("stat.crouchOneCm", "minecraft:crouch_one_cm")
		.put("stat.sprintOneCm", "minecraft:sprint_one_cm")
		.put("stat.swimOneCm", "minecraft:swim_one_cm")
		.put("stat.fallOneCm", "minecraft:fall_one_cm")
		.put("stat.climbOneCm", "minecraft:climb_one_cm")
		.put("stat.flyOneCm", "minecraft:fly_one_cm")
		.put("stat.diveOneCm", "minecraft:dive_one_cm")
		.put("stat.minecartOneCm", "minecraft:minecart_one_cm")
		.put("stat.boatOneCm", "minecraft:boat_one_cm")
		.put("stat.pigOneCm", "minecraft:pig_one_cm")
		.put("stat.horseOneCm", "minecraft:horse_one_cm")
		.put("stat.aviateOneCm", "minecraft:aviate_one_cm")
		.put("stat.jump", "minecraft:jump")
		.put("stat.drop", "minecraft:drop")
		.put("stat.damageDealt", "minecraft:damage_dealt")
		.put("stat.damageTaken", "minecraft:damage_taken")
		.put("stat.deaths", "minecraft:deaths")
		.put("stat.mobKills", "minecraft:mob_kills")
		.put("stat.animalsBred", "minecraft:animals_bred")
		.put("stat.playerKills", "minecraft:player_kills")
		.put("stat.fishCaught", "minecraft:fish_caught")
		.put("stat.talkedToVillager", "minecraft:talked_to_villager")
		.put("stat.tradedWithVillager", "minecraft:traded_with_villager")
		.put("stat.cakeSlicesEaten", "minecraft:eat_cake_slice")
		.put("stat.cauldronFilled", "minecraft:fill_cauldron")
		.put("stat.cauldronUsed", "minecraft:use_cauldron")
		.put("stat.armorCleaned", "minecraft:clean_armor")
		.put("stat.bannerCleaned", "minecraft:clean_banner")
		.put("stat.brewingstandInteraction", "minecraft:interact_with_brewingstand")
		.put("stat.beaconInteraction", "minecraft:interact_with_beacon")
		.put("stat.dropperInspected", "minecraft:inspect_dropper")
		.put("stat.hopperInspected", "minecraft:inspect_hopper")
		.put("stat.dispenserInspected", "minecraft:inspect_dispenser")
		.put("stat.noteblockPlayed", "minecraft:play_noteblock")
		.put("stat.noteblockTuned", "minecraft:tune_noteblock")
		.put("stat.flowerPotted", "minecraft:pot_flower")
		.put("stat.trappedChestTriggered", "minecraft:trigger_trapped_chest")
		.put("stat.enderchestOpened", "minecraft:open_enderchest")
		.put("stat.itemEnchanted", "minecraft:enchant_item")
		.put("stat.recordPlayed", "minecraft:play_record")
		.put("stat.furnaceInteraction", "minecraft:interact_with_furnace")
		.put("stat.craftingTableInteraction", "minecraft:interact_with_crafting_table")
		.put("stat.chestOpened", "minecraft:open_chest")
		.put("stat.sleepInBed", "minecraft:sleep_in_bed")
		.put("stat.shulkerBoxOpened", "minecraft:open_shulker_box")
		.build();
	private static final Map<String, String> c = ImmutableMap.<String, String>builder()
		.put("stat.craftItem", "minecraft:crafted")
		.put("stat.useItem", "minecraft:used")
		.put("stat.breakItem", "minecraft:broken")
		.put("stat.pickup", "minecraft:picked_up")
		.put("stat.drop", "minecraft:dropped")
		.build();
	private static final Map<String, String> d = ImmutableMap.<String, String>builder()
		.put("stat.entityKilledBy", "minecraft:killed_by")
		.put("stat.killEntity", "minecraft:killed")
		.build();
	private static final Map<String, String> e = ImmutableMap.<String, String>builder()
		.put("Bat", "minecraft:bat")
		.put("Blaze", "minecraft:blaze")
		.put("CaveSpider", "minecraft:cave_spider")
		.put("Chicken", "minecraft:chicken")
		.put("Cow", "minecraft:cow")
		.put("Creeper", "minecraft:creeper")
		.put("Donkey", "minecraft:donkey")
		.put("ElderGuardian", "minecraft:elder_guardian")
		.put("Enderman", "minecraft:enderman")
		.put("Endermite", "minecraft:endermite")
		.put("EvocationIllager", "minecraft:evocation_illager")
		.put("Ghast", "minecraft:ghast")
		.put("Guardian", "minecraft:guardian")
		.put("Horse", "minecraft:horse")
		.put("Husk", "minecraft:husk")
		.put("Llama", "minecraft:llama")
		.put("LavaSlime", "minecraft:magma_cube")
		.put("MushroomCow", "minecraft:mooshroom")
		.put("Mule", "minecraft:mule")
		.put("Ozelot", "minecraft:ocelot")
		.put("Parrot", "minecraft:parrot")
		.put("Pig", "minecraft:pig")
		.put("PolarBear", "minecraft:polar_bear")
		.put("Rabbit", "minecraft:rabbit")
		.put("Sheep", "minecraft:sheep")
		.put("Shulker", "minecraft:shulker")
		.put("Silverfish", "minecraft:silverfish")
		.put("SkeletonHorse", "minecraft:skeleton_horse")
		.put("Skeleton", "minecraft:skeleton")
		.put("Slime", "minecraft:slime")
		.put("Spider", "minecraft:spider")
		.put("Squid", "minecraft:squid")
		.put("Stray", "minecraft:stray")
		.put("Vex", "minecraft:vex")
		.put("Villager", "minecraft:villager")
		.put("VindicationIllager", "minecraft:vindication_illager")
		.put("Witch", "minecraft:witch")
		.put("WitherSkeleton", "minecraft:wither_skeleton")
		.put("Wolf", "minecraft:wolf")
		.put("ZombieHorse", "minecraft:zombie_horse")
		.put("PigZombie", "minecraft:zombie_pigman")
		.put("ZombieVillager", "minecraft:zombie_villager")
		.put("Zombie", "minecraft:zombie")
		.build();

	public ajk(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getOutputSchema().getType(ajb.g);
		return this.fixTypeEverywhereTyped(
			"StatsCounterFix",
			this.getInputSchema().getType(ajb.g),
			type2,
			typed -> {
				Dynamic<?> dynamic4 = typed.get(DSL.remainderFinder());
				Map<Dynamic<?>, Dynamic<?>> map5 = Maps.<Dynamic<?>, Dynamic<?>>newHashMap();
				Optional<? extends Map<? extends Dynamic<?>, ? extends Dynamic<?>>> optional6 = dynamic4.getMapValues().result();
				if (optional6.isPresent()) {
					for (Entry<? extends Dynamic<?>, ? extends Dynamic<?>> entry8 : ((Map)optional6.get()).entrySet()) {
						if (((Dynamic)entry8.getValue()).asNumber().result().isPresent()) {
							String string9 = ((Dynamic)entry8.getKey()).asString("");
							if (!a.contains(string9)) {
								String string10;
								String string11;
								if (b.containsKey(string9)) {
									string10 = "minecraft:custom";
									string11 = (String)b.get(string9);
								} else {
									int integer12 = StringUtils.ordinalIndexOf(string9, ".", 2);
									if (integer12 < 0) {
										continue;
									}
	
									String string13 = string9.substring(0, integer12);
									if ("stat.mineBlock".equals(string13)) {
										string10 = "minecraft:mined";
										string11 = this.b(string9.substring(integer12 + 1).replace('.', ':'));
									} else if (c.containsKey(string13)) {
										string10 = (String)c.get(string13);
										String string14 = string9.substring(integer12 + 1).replace('.', ':');
										String string15 = this.a(string14);
										string11 = string15 == null ? string14 : string15;
									} else {
										if (!d.containsKey(string13)) {
											continue;
										}
	
										string10 = (String)d.get(string13);
										String string14 = string9.substring(integer12 + 1).replace('.', ':');
										string11 = (String)e.getOrDefault(string14, string14);
									}
								}
	
								Dynamic<?> dynamic12 = dynamic4.createString(string10);
								Dynamic<?> dynamic13 = (Dynamic<?>)map5.computeIfAbsent(dynamic12, dynamic2 -> dynamic4.emptyMap());
								map5.put(dynamic12, dynamic13.set(string11, (Dynamic<?>)entry8.getValue()));
							}
						}
					}
				}
	
				return (Typed)((Pair)type2.readTyped(dynamic4.emptyMap().set("stats", dynamic4.createMap(map5)))
						.result()
						.orElseThrow(() -> new IllegalStateException("Could not parse new stats object.")))
					.getFirst();
			}
		);
	}

	@Nullable
	protected String a(String string) {
		return ahv.a(string, 0);
	}

	protected String b(String string) {
		return afn.a(string);
	}
}
