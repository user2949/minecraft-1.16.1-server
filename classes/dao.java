import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Set;

public class dao {
	private static final Set<uh> az = Sets.<uh>newHashSet();
	private static final Set<uh> aA = Collections.unmodifiableSet(az);
	public static final uh a = new uh("empty");
	public static final uh b = a("chests/spawn_bonus_chest");
	public static final uh c = a("chests/end_city_treasure");
	public static final uh d = a("chests/simple_dungeon");
	public static final uh e = a("chests/village/village_weaponsmith");
	public static final uh f = a("chests/village/village_toolsmith");
	public static final uh g = a("chests/village/village_armorer");
	public static final uh h = a("chests/village/village_cartographer");
	public static final uh i = a("chests/village/village_mason");
	public static final uh j = a("chests/village/village_shepherd");
	public static final uh k = a("chests/village/village_butcher");
	public static final uh l = a("chests/village/village_fletcher");
	public static final uh m = a("chests/village/village_fisher");
	public static final uh n = a("chests/village/village_tannery");
	public static final uh o = a("chests/village/village_temple");
	public static final uh p = a("chests/village/village_desert_house");
	public static final uh q = a("chests/village/village_plains_house");
	public static final uh r = a("chests/village/village_taiga_house");
	public static final uh s = a("chests/village/village_snowy_house");
	public static final uh t = a("chests/village/village_savanna_house");
	public static final uh u = a("chests/abandoned_mineshaft");
	public static final uh v = a("chests/nether_bridge");
	public static final uh w = a("chests/stronghold_library");
	public static final uh x = a("chests/stronghold_crossing");
	public static final uh y = a("chests/stronghold_corridor");
	public static final uh z = a("chests/desert_pyramid");
	public static final uh A = a("chests/jungle_temple");
	public static final uh B = a("chests/jungle_temple_dispenser");
	public static final uh C = a("chests/igloo_chest");
	public static final uh D = a("chests/woodland_mansion");
	public static final uh E = a("chests/underwater_ruin_small");
	public static final uh F = a("chests/underwater_ruin_big");
	public static final uh G = a("chests/buried_treasure");
	public static final uh H = a("chests/shipwreck_map");
	public static final uh I = a("chests/shipwreck_supply");
	public static final uh J = a("chests/shipwreck_treasure");
	public static final uh K = a("chests/pillager_outpost");
	public static final uh L = a("chests/bastion_treasure");
	public static final uh M = a("chests/bastion_other");
	public static final uh N = a("chests/bastion_bridge");
	public static final uh O = a("chests/bastion_hoglin_stable");
	public static final uh P = a("chests/ruined_portal");
	public static final uh Q = a("entities/sheep/white");
	public static final uh R = a("entities/sheep/orange");
	public static final uh S = a("entities/sheep/magenta");
	public static final uh T = a("entities/sheep/light_blue");
	public static final uh U = a("entities/sheep/yellow");
	public static final uh V = a("entities/sheep/lime");
	public static final uh W = a("entities/sheep/pink");
	public static final uh X = a("entities/sheep/gray");
	public static final uh Y = a("entities/sheep/light_gray");
	public static final uh Z = a("entities/sheep/cyan");
	public static final uh aa = a("entities/sheep/purple");
	public static final uh ab = a("entities/sheep/blue");
	public static final uh ac = a("entities/sheep/brown");
	public static final uh ad = a("entities/sheep/green");
	public static final uh ae = a("entities/sheep/red");
	public static final uh af = a("entities/sheep/black");
	public static final uh ag = a("gameplay/fishing");
	public static final uh ah = a("gameplay/fishing/junk");
	public static final uh ai = a("gameplay/fishing/treasure");
	public static final uh aj = a("gameplay/fishing/fish");
	public static final uh ak = a("gameplay/cat_morning_gift");
	public static final uh al = a("gameplay/hero_of_the_village/armorer_gift");
	public static final uh am = a("gameplay/hero_of_the_village/butcher_gift");
	public static final uh an = a("gameplay/hero_of_the_village/cartographer_gift");
	public static final uh ao = a("gameplay/hero_of_the_village/cleric_gift");
	public static final uh ap = a("gameplay/hero_of_the_village/farmer_gift");
	public static final uh aq = a("gameplay/hero_of_the_village/fisherman_gift");
	public static final uh ar = a("gameplay/hero_of_the_village/fletcher_gift");
	public static final uh as = a("gameplay/hero_of_the_village/leatherworker_gift");
	public static final uh at = a("gameplay/hero_of_the_village/librarian_gift");
	public static final uh au = a("gameplay/hero_of_the_village/mason_gift");
	public static final uh av = a("gameplay/hero_of_the_village/shepherd_gift");
	public static final uh aw = a("gameplay/hero_of_the_village/toolsmith_gift");
	public static final uh ax = a("gameplay/hero_of_the_village/weaponsmith_gift");
	public static final uh ay = a("gameplay/piglin_bartering");

	private static uh a(String string) {
		return a(new uh(string));
	}

	private static uh a(uh uh) {
		if (az.add(uh)) {
			return uh;
		} else {
			throw new IllegalArgumentException(uh + " is already a registered built-in loot table");
		}
	}

	public static Set<uh> a() {
		return aA;
	}
}
