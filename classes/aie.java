import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class aie extends aer {
	public aie(Schema schema) {
		super(schema, ajb.a);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"LevelUUIDFix",
			this.getInputSchema().getType(this.b),
			typed -> typed.updateTyped(DSL.remainderFinder(), typedx -> typedx.update(DSL.remainderFinder(), dynamic -> {
						dynamic = this.d(dynamic);
						dynamic = this.c(dynamic);
						return this.b(dynamic);
					}))
		);
	}

	private Dynamic<?> b(Dynamic<?> dynamic) {
		return (Dynamic<?>)a(dynamic, "WanderingTraderId", "WanderingTraderId").orElse(dynamic);
	}

	private Dynamic<?> c(Dynamic<?> dynamic) {
		return dynamic.update(
			"DimensionData",
			dynamicx -> dynamicx.updateMapValues(
					pair -> pair.mapSecond(dynamicxx -> dynamicxx.update("DragonFight", dynamicxxx -> (Dynamic)c(dynamicxxx, "DragonUUID", "Dragon").orElse(dynamicxxx)))
				)
		);
	}

	private Dynamic<?> d(Dynamic<?> dynamic) {
		return dynamic.update(
			"CustomBossEvents",
			dynamicx -> dynamicx.updateMapValues(
					pair -> pair.mapSecond(
							dynamicxx -> dynamicxx.update("Players", dynamic2 -> dynamicxx.createList(dynamic2.asStream().map(dynamicxxx -> (Dynamic)a(dynamicxxx).orElseGet(() -> {
											a.warn("CustomBossEvents contains invalid UUIDs.");
											return dynamicxxx;
										}))))
						)
				)
		);
	}
}
