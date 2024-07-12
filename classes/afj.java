import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class afj extends aer {
	public afj(Schema schema) {
		super(schema, ajb.k);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.b), typed -> {
			typed = this.a(typed, "minecraft:conduit", this::c);
			return this.a(typed, "minecraft:skull", this::b);
		});
	}

	private Dynamic<?> b(Dynamic<?> dynamic) {
		return (Dynamic<?>)dynamic.get("Owner")
			.get()
			.map(dynamicx -> (Dynamic)a(dynamicx, "Id", "Id").orElse(dynamicx))
			.map(dynamic2 -> dynamic.remove("Owner").set("SkullOwner", dynamic2))
			.result()
			.orElse(dynamic);
	}

	private Dynamic<?> c(Dynamic<?> dynamic) {
		return (Dynamic<?>)b(dynamic, "target_uuid", "Target").orElse(dynamic);
	}
}
