import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class akk extends aka {
	public akk(int integer, Schema schema) {
		super(integer, schema);
	}

	@Override
	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map2, Map<String, Supplier<TypeTemplate>> map3) {
		super.registerTypes(schema, map2, map3);
		schema.registerType(
			false,
			ajb.c,
			() -> DSL.fields(
					"Level",
					DSL.optionalFields(
						"Entities",
						DSL.list(ajb.o.in(schema)),
						"TileEntities",
						DSL.list(ajb.k.in(schema)),
						"TileTicks",
						DSL.list(DSL.fields("i", ajb.q.in(schema))),
						"Sections",
						DSL.list(DSL.optionalFields("Palette", DSL.list(ajb.m.in(schema))))
					)
				)
		);
	}
}
