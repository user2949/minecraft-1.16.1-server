import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;

public class ajx extends aij {
	public ajx(Schema schema, boolean boolean2) {
		super(schema, boolean2, "Zombie Villager XP rebuild", ajb.p, "minecraft:zombie_villager");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), dynamic -> {
			Optional<Number> optional2 = dynamic.get("Xp").asNumber().result();
			if (!optional2.isPresent()) {
				int integer3 = dynamic.get("VillagerData").get("level").asInt(1);
				return dynamic.set("Xp", dynamic.createInt(ajs.a(integer3)));
			} else {
				return dynamic;
			}
		});
	}
}
