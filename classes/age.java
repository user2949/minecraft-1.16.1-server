import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class age extends aji {
	public age(Schema schema, boolean boolean2) {
		super("EntityElderGuardianSplitFix", schema, boolean2);
	}

	@Override
	protected Pair<String, Dynamic<?>> a(String string, Dynamic<?> dynamic) {
		return Pair.of(Objects.equals(string, "Guardian") && dynamic.get("Elder").asBoolean(false) ? "ElderGuardian" : string, dynamic);
	}
}
