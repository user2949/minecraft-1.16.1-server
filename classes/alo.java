import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alo extends aka {
	public alo(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		schema.registerType(
			false,
			ajb.y,
			() -> DSL.fields(
					"dimensions",
					DSL.compoundList(
						DSL.constType(a()),
						DSL.fields(
							"generator",
							DSL.taggedChoiceLazy(
								"type",
								DSL.string(),
								ImmutableMap.of(
									"minecraft:debug",
									DSL::remainder,
									"minecraft:flat",
									() -> DSL.optionalFields("settings", DSL.optionalFields("biome", ajb.x.in(schema), "layers", DSL.list(DSL.optionalFields("block", ajb.q.in(schema))))),
									"minecraft:noise",
									() -> DSL.optionalFields(
											"biome_source",
											DSL.taggedChoiceLazy(
												"type",
												DSL.string(),
												ImmutableMap.of(
													"minecraft:fixed",
													() -> DSL.fields("biome", ajb.x.in(schema)),
													"minecraft:multi_noise",
													() -> DSL.list(DSL.fields("biome", ajb.x.in(schema))),
													"minecraft:checkerboard",
													() -> DSL.fields("biomes", DSL.list(ajb.x.in(schema))),
													"minecraft:vanilla_layered",
													DSL::remainder,
													"minecraft:the_end",
													DSL::remainder
												)
											),
											"settings",
											DSL.or(DSL.constType(DSL.string()), DSL.optionalFields("default_block", ajb.q.in(schema), "default_fluid", ajb.q.in(schema)))
										)
								)
							)
						)
					)
				)
		);
	}
}
