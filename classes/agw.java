import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class agw extends aji {
	public agw(Schema schema, boolean boolean2) {
		super("EntitySkeletonSplitFix", schema, boolean2);
	}

	@Override
	protected Pair<String, Dynamic<?>> a(String string, Dynamic<?> dynamic) {
		if (Objects.equals(string, "Skeleton")) {
			int integer4 = dynamic.get("SkeletonType").asInt(0);
			if (integer4 == 1) {
				string = "WitherSkeleton";
			} else if (integer4 == 2) {
				string = "Stray";
			}
		}

		return Pair.of(string, dynamic);
	}
}
