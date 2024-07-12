import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;

public class agg extends DataFix {
	private static final Set<String> a = Sets.<String>newHashSet(
		"ArmorStand",
		"Bat",
		"Blaze",
		"CaveSpider",
		"Chicken",
		"Cow",
		"Creeper",
		"EnderDragon",
		"Enderman",
		"Endermite",
		"EntityHorse",
		"Ghast",
		"Giant",
		"Guardian",
		"LavaSlime",
		"MushroomCow",
		"Ozelot",
		"Pig",
		"PigZombie",
		"Rabbit",
		"Sheep",
		"Shulker",
		"Silverfish",
		"Skeleton",
		"Slime",
		"SnowMan",
		"Spider",
		"Squid",
		"Villager",
		"VillagerGolem",
		"Witch",
		"WitherBoss",
		"Wolf",
		"Zombie"
	);

	public agg(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		Optional<Number> optional4 = dynamic.get("HealF").asNumber().result();
		Optional<Number> optional5 = dynamic.get("Health").asNumber().result();
		float float3;
		if (optional4.isPresent()) {
			float3 = ((Number)optional4.get()).floatValue();
			dynamic = dynamic.remove("HealF");
		} else {
			if (!optional5.isPresent()) {
				return dynamic;
			}

			float3 = ((Number)optional5.get()).floatValue();
		}

		return dynamic.set("Health", dynamic.createFloat(float3));
	}

	@Override
	public TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped("EntityHealthFix", this.getInputSchema().getType(ajb.p), typed -> typed.update(DSL.remainderFinder(), this::a));
	}
}
