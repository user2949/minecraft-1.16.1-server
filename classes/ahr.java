import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class ahr extends DataFix {
	private static final String[] a = DataFixUtils.make(new String[256], arr -> {
		arr[1] = "Item";
		arr[2] = "XPOrb";
		arr[7] = "ThrownEgg";
		arr[8] = "LeashKnot";
		arr[9] = "Painting";
		arr[10] = "Arrow";
		arr[11] = "Snowball";
		arr[12] = "Fireball";
		arr[13] = "SmallFireball";
		arr[14] = "ThrownEnderpearl";
		arr[15] = "EyeOfEnderSignal";
		arr[16] = "ThrownPotion";
		arr[17] = "ThrownExpBottle";
		arr[18] = "ItemFrame";
		arr[19] = "WitherSkull";
		arr[20] = "PrimedTnt";
		arr[21] = "FallingSand";
		arr[22] = "FireworksRocketEntity";
		arr[23] = "TippedArrow";
		arr[24] = "SpectralArrow";
		arr[25] = "ShulkerBullet";
		arr[26] = "DragonFireball";
		arr[30] = "ArmorStand";
		arr[41] = "Boat";
		arr[42] = "MinecartRideable";
		arr[43] = "MinecartChest";
		arr[44] = "MinecartFurnace";
		arr[45] = "MinecartTNT";
		arr[46] = "MinecartHopper";
		arr[47] = "MinecartSpawner";
		arr[40] = "MinecartCommandBlock";
		arr[48] = "Mob";
		arr[49] = "Monster";
		arr[50] = "Creeper";
		arr[51] = "Skeleton";
		arr[52] = "Spider";
		arr[53] = "Giant";
		arr[54] = "Zombie";
		arr[55] = "Slime";
		arr[56] = "Ghast";
		arr[57] = "PigZombie";
		arr[58] = "Enderman";
		arr[59] = "CaveSpider";
		arr[60] = "Silverfish";
		arr[61] = "Blaze";
		arr[62] = "LavaSlime";
		arr[63] = "EnderDragon";
		arr[64] = "WitherBoss";
		arr[65] = "Bat";
		arr[66] = "Witch";
		arr[67] = "Endermite";
		arr[68] = "Guardian";
		arr[69] = "Shulker";
		arr[90] = "Pig";
		arr[91] = "Sheep";
		arr[92] = "Cow";
		arr[93] = "Chicken";
		arr[94] = "Squid";
		arr[95] = "Wolf";
		arr[96] = "MushroomCow";
		arr[97] = "SnowMan";
		arr[98] = "Ozelot";
		arr[99] = "VillagerGolem";
		arr[100] = "EntityHorse";
		arr[101] = "Rabbit";
		arr[120] = "Villager";
		arr[200] = "EnderCrystal";
	});

	public ahr(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Schema schema2 = this.getInputSchema();
		Type<?> type3 = schema2.getType(ajb.l);
		OpticFinder<Pair<String, String>> opticFinder4 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		OpticFinder<String> opticFinder5 = DSL.fieldFinder("id", DSL.string());
		OpticFinder<?> opticFinder6 = type3.findField("tag");
		OpticFinder<?> opticFinder7 = opticFinder6.type().findField("EntityTag");
		OpticFinder<?> opticFinder8 = DSL.typeFinder(schema2.getTypeRaw(ajb.p));
		Type<?> type9 = this.getOutputSchema().getTypeRaw(ajb.p);
		return this.fixTypeEverywhereTyped(
			"ItemSpawnEggFix",
			type3,
			typed -> {
				Optional<Pair<String, String>> optional8 = typed.getOptional(opticFinder4);
				if (optional8.isPresent() && Objects.equals(((Pair)optional8.get()).getSecond(), "minecraft:spawn_egg")) {
					Dynamic<?> dynamic9 = typed.get(DSL.remainderFinder());
					short short10 = dynamic9.get("Damage").asShort((short)0);
					Optional<? extends Typed<?>> optional11 = typed.getOptionalTyped(opticFinder6);
					Optional<? extends Typed<?>> optional12 = optional11.flatMap(typedx -> typedx.getOptionalTyped(opticFinder7));
					Optional<? extends Typed<?>> optional13 = optional12.flatMap(typedx -> typedx.getOptionalTyped(opticFinder8));
					Optional<String> optional14 = optional13.flatMap(typedx -> typedx.getOptional(opticFinder5));
					Typed<?> typed15 = typed;
					String string16 = a[short10 & 255];
					if (string16 != null && (!optional14.isPresent() || !Objects.equals(optional14.get(), string16))) {
						Typed<?> typed17 = typed.getOrCreateTyped(opticFinder6);
						Typed<?> typed18 = typed17.getOrCreateTyped(opticFinder7);
						Typed<?> typed19 = typed18.getOrCreateTyped(opticFinder8);
						Typed<?> typed21 = (Typed<?>)((Pair)typed19.write()
								.flatMap(dynamic4 -> type9.readTyped(dynamic4.set("id", dynamic9.createString(string16))))
								.result()
								.orElseThrow(() -> new IllegalStateException("Could not parse new entity")))
							.getFirst();
						typed15 = typed.set(opticFinder6, typed17.set(opticFinder7, typed18.set(opticFinder8, typed21)));
					}
	
					if (short10 != 0) {
						dynamic9 = dynamic9.set("Damage", dynamic9.createShort((short)0));
						typed15 = typed15.set(DSL.remainderFinder(), dynamic9);
					}
	
					return typed15;
				} else {
					return typed;
				}
			}
		);
	}
}
