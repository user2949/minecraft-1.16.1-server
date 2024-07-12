import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Random;

public class ahd extends aij {
	private static final Random a = new Random();

	public ahd(Schema schema, boolean boolean2) {
		super(schema, boolean2, "EntityZombieVillagerTypeFix", ajb.p, "Zombie");
	}

	public Dynamic<?> a(Dynamic<?> dynamic) {
		if (dynamic.get("IsVillager").asBoolean(false)) {
			if (!dynamic.get("ZombieType").result().isPresent()) {
				int integer3 = this.a(dynamic.get("VillagerProfession").asInt(-1));
				if (integer3 == -1) {
					integer3 = this.a(a.nextInt(6));
				}

				dynamic = dynamic.set("ZombieType", dynamic.createInt(integer3));
			}

			dynamic = dynamic.remove("IsVillager");
		}

		return dynamic;
	}

	private int a(int integer) {
		return integer >= 0 && integer < 6 ? integer : -1;
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::a);
	}
}
