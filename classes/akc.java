import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;

public class akc extends Schema {
	public akc(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		schema.registerType(
			true,
			ajb.l,
			() -> DSL.hook(
					DSL.optionalFields(
						"id",
						ajb.r.in(schema),
						"tag",
						DSL.optionalFields(
							"EntityTag", ajb.o.in(schema), "BlockEntityTag", ajb.k.in(schema), "CanDestroy", DSL.list(ajb.q.in(schema)), "CanPlaceOn", DSL.list(ajb.q.in(schema))
						)
					),
					alx.a,
					HookFunction.IDENTITY
				)
		);
	}
}
