import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alt extends Schema {
	public alt(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map3 = super.registerEntities(schema);
		map3.remove("EntityHorse");
		schema.register(
			map3, "Horse", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("ArmorItem", ajb.l.in(schema), "SaddleItem", ajb.l.in(schema), akb.a(schema)))
		);
		schema.register(
			map3, "Donkey", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)), "SaddleItem", ajb.l.in(schema), akb.a(schema)))
		);
		schema.register(
			map3, "Mule", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(ajb.l.in(schema)), "SaddleItem", ajb.l.in(schema), akb.a(schema)))
		);
		schema.register(map3, "ZombieHorse", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("SaddleItem", ajb.l.in(schema), akb.a(schema))));
		schema.register(map3, "SkeletonHorse", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("SaddleItem", ajb.l.in(schema), akb.a(schema))));
		return map3;
	}
}
