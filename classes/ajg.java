import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ajg extends aer {
	public ajg(Schema schema) {
		super(schema, ajb.h);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"SavedDataUUIDFix",
			this.getInputSchema().getType(this.b),
			typed -> typed.updateTyped(
					typed.getType().findField("data"),
					typedx -> typedx.update(
							DSL.remainderFinder(),
							dynamic -> dynamic.update(
									"Raids",
									dynamicx -> dynamicx.createList(
											dynamicx.asStream()
												.map(
													dynamicxx -> dynamicxx.update(
															"HeroesOfTheVillage",
															dynamicxxx -> dynamicxxx.createList(dynamicxxx.asStream().map(dynamicxxxx -> (Dynamic)d(dynamicxxxx, "UUIDMost", "UUIDLeast").orElseGet(() -> {
																		a.warn("HeroesOfTheVillage contained invalid UUIDs.");
																		return dynamicxxxx;
																	})))
														)
												)
										)
								)
						)
				)
		);
	}
}
