import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class ahi extends DataFix {
	public ahi(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.c);
		OpticFinder<?> opticFinder3 = type2.findField("Level");
		return this.fixTypeEverywhereTyped(
			"HeightmapRenamingFix", type2, typed -> typed.updateTyped(opticFinder3, typedx -> typedx.update(DSL.remainderFinder(), this::a))
		);
	}

	private Dynamic<?> a(Dynamic<?> dynamic) {
		Optional<? extends Dynamic<?>> optional3 = dynamic.get("Heightmaps").result();
		if (!optional3.isPresent()) {
			return dynamic;
		} else {
			Dynamic<?> dynamic4 = (Dynamic<?>)optional3.get();
			Optional<? extends Dynamic<?>> optional5 = dynamic4.get("LIQUID").result();
			if (optional5.isPresent()) {
				dynamic4 = dynamic4.remove("LIQUID");
				dynamic4 = dynamic4.set("WORLD_SURFACE_WG", (Dynamic<?>)optional5.get());
			}

			Optional<? extends Dynamic<?>> optional6 = dynamic4.get("SOLID").result();
			if (optional6.isPresent()) {
				dynamic4 = dynamic4.remove("SOLID");
				dynamic4 = dynamic4.set("OCEAN_FLOOR_WG", (Dynamic<?>)optional6.get());
				dynamic4 = dynamic4.set("OCEAN_FLOOR", (Dynamic<?>)optional6.get());
			}

			Optional<? extends Dynamic<?>> optional7 = dynamic4.get("LIGHT").result();
			if (optional7.isPresent()) {
				dynamic4 = dynamic4.remove("LIGHT");
				dynamic4 = dynamic4.set("LIGHT_BLOCKING", (Dynamic<?>)optional7.get());
			}

			Optional<? extends Dynamic<?>> optional8 = dynamic4.get("RAIN").result();
			if (optional8.isPresent()) {
				dynamic4 = dynamic4.remove("RAIN");
				dynamic4 = dynamic4.set("MOTION_BLOCKING", (Dynamic<?>)optional8.get());
				dynamic4 = dynamic4.set("MOTION_BLOCKING_NO_LEAVES", (Dynamic<?>)optional8.get());
			}

			return dynamic.set("Heightmaps", dynamic4);
		}
	}
}
