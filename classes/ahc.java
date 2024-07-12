import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class ahc extends aji {
	public ahc(Schema schema, boolean boolean2) {
		super("EntityZombieSplitFix", schema, boolean2);
	}

	@Override
	protected Pair<String, Dynamic<?>> a(String string, Dynamic<?> dynamic) {
		if (Objects.equals("Zombie", string)) {
			String string4 = "Zombie";
			int integer5 = dynamic.get("ZombieType").asInt(0);
			switch (integer5) {
				case 0:
				default:
					break;
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
					string4 = "ZombieVillager";
					dynamic = dynamic.set("Profession", dynamic.createInt(integer5 - 1));
					break;
				case 6:
					string4 = "Husk";
			}

			dynamic = dynamic.remove("ZombieType");
			return Pair.of(string4, dynamic);
		} else {
			return Pair.of(string, dynamic);
		}
	}
}
