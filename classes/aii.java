import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.stream.Stream;

public class aii extends DataFix {
	public aii(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	private Dynamic<?> a(Dynamic<?> dynamic) {
		if (!"MobSpawner".equals(dynamic.get("id").asString(""))) {
			return dynamic;
		} else {
			Optional<String> optional3 = dynamic.get("EntityId").asString().result();
			if (optional3.isPresent()) {
				Dynamic<?> dynamic4 = DataFixUtils.orElse(dynamic.get("SpawnData").result(), dynamic.emptyMap());
				dynamic4 = dynamic4.set("id", dynamic4.createString(((String)optional3.get()).isEmpty() ? "Pig" : (String)optional3.get()));
				dynamic = dynamic.set("SpawnData", dynamic4);
				dynamic = dynamic.remove("EntityId");
			}

			Optional<? extends Stream<? extends Dynamic<?>>> optional4 = dynamic.get("SpawnPotentials").asStreamOpt().result();
			if (optional4.isPresent()) {
				dynamic = dynamic.set(
					"SpawnPotentials",
					dynamic.createList(
						((Stream)optional4.get())
							.map(
								dynamicx -> {
									Optional<String> optional2 = dynamicx.get("Type").asString().result();
									if (optional2.isPresent()) {
										Dynamic<?> dynamic3 = DataFixUtils.orElse(dynamicx.get("Properties").result(), dynamicx.emptyMap())
											.set("id", dynamicx.createString((String)optional2.get()));
										return dynamicx.set("Entity", dynamic3).remove("Type").remove("Properties");
									} else {
										return dynamicx;
									}
								}
							)
					)
				);
			}

			return dynamic;
		}
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getOutputSchema().getType(ajb.s);
		return this.fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", this.getInputSchema().getType(ajb.s), type2, typed -> {
			Dynamic<?> dynamic4 = typed.get(DSL.remainderFinder());
			dynamic4 = dynamic4.set("id", dynamic4.createString("MobSpawner"));
			DataResult<? extends Pair<? extends Typed<?>, ?>> dataResult5 = type2.readTyped(this.a(dynamic4));
			return !dataResult5.result().isPresent() ? typed : (Typed)((Pair)dataResult5.result().get()).getFirst();
		});
	}
}
