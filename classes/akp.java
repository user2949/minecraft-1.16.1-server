import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class akp extends aka {
	public akp(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		Supplier<TypeTemplate> supplier5 = () -> DSL.compoundList(ajb.r.in(schema), DSL.constType(DSL.intType()));
		schema.registerType(
			false,
			ajb.g,
			() -> DSL.optionalFields(
					"stats",
					DSL.optionalFields(
						"minecraft:mined",
						DSL.compoundList(ajb.q.in(schema), DSL.constType(DSL.intType())),
						"minecraft:crafted",
						(TypeTemplate)supplier5.get(),
						"minecraft:used",
						(TypeTemplate)supplier5.get(),
						"minecraft:broken",
						(TypeTemplate)supplier5.get(),
						"minecraft:picked_up",
						(TypeTemplate)supplier5.get(),
						DSL.optionalFields(
							"minecraft:dropped",
							(TypeTemplate)supplier5.get(),
							"minecraft:killed",
							DSL.compoundList(ajb.n.in(schema), DSL.constType(DSL.intType())),
							"minecraft:killed_by",
							DSL.compoundList(ajb.n.in(schema), DSL.constType(DSL.intType())),
							"minecraft:custom",
							DSL.compoundList(DSL.constType(a()), DSL.constType(DSL.intType()))
						)
					)
				)
		);
	}
}
