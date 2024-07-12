import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ajq extends aij {
	public ajq(Schema schema, String string) {
		super(schema, false, "Villager profession data fix (" + string + ")", ajb.p, string);
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		Dynamic<?> dynamic3 = typed.get(DSL.remainderFinder());
		return typed.set(
			DSL.remainderFinder(),
			dynamic3.remove("Profession")
				.remove("Career")
				.remove("CareerLevel")
				.set(
					"VillagerData",
					dynamic3.createMap(
						ImmutableMap.of(
							dynamic3.createString("type"),
							dynamic3.createString("minecraft:plains"),
							dynamic3.createString("profession"),
							dynamic3.createString(a(dynamic3.get("Profession").asInt(0), dynamic3.get("Career").asInt(0))),
							dynamic3.createString("level"),
							DataFixUtils.orElse(dynamic3.get("CareerLevel").result(), dynamic3.createInt(1))
						)
					)
				)
		);
	}

	private static String a(int integer1, int integer2) {
		if (integer1 == 0) {
			if (integer2 == 2) {
				return "minecraft:fisherman";
			} else if (integer2 == 3) {
				return "minecraft:shepherd";
			} else {
				return integer2 == 4 ? "minecraft:fletcher" : "minecraft:farmer";
			}
		} else if (integer1 == 1) {
			return integer2 == 2 ? "minecraft:cartographer" : "minecraft:librarian";
		} else if (integer1 == 2) {
			return "minecraft:cleric";
		} else if (integer1 == 3) {
			if (integer2 == 2) {
				return "minecraft:weaponsmith";
			} else {
				return integer2 == 3 ? "minecraft:toolsmith" : "minecraft:armorer";
			}
		} else if (integer1 == 4) {
			return integer2 == 2 ? "minecraft:leatherworker" : "minecraft:butcher";
		} else {
			return integer1 == 5 ? "minecraft:nitwit" : "minecraft:none";
		}
	}
}
