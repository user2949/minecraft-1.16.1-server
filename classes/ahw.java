import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class ahw extends aer {
	public ahw(Schema schema) {
		super(schema, ajb.l);
	}

	@Override
	public TypeRewriteRule makeRule() {
		OpticFinder<Pair<String, String>> opticFinder2 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		return this.fixTypeEverywhereTyped("ItemStackUUIDFix", this.getInputSchema().getType(this.b), typed -> {
			OpticFinder<?> opticFinder4 = typed.getType().findField("tag");
			return typed.updateTyped(opticFinder4, typed3 -> typed3.update(DSL.remainderFinder(), dynamic -> {
					dynamic = this.b(dynamic);
					if ((Boolean)typed.getOptional(opticFinder2).map(pair -> "minecraft:player_head".equals(pair.getSecond())).orElse(false)) {
						dynamic = this.c(dynamic);
					}

					return dynamic;
				}));
		});
	}

	private Dynamic<?> b(Dynamic<?> dynamic) {
		return dynamic.update(
			"AttributeModifiers", dynamic2 -> dynamic.createList(dynamic2.asStream().map(dynamicx -> (Dynamic)c(dynamicx, "UUID", "UUID").orElse(dynamicx)))
		);
	}

	private Dynamic<?> c(Dynamic<?> dynamic) {
		return dynamic.update("SkullOwner", dynamicx -> (Dynamic)a(dynamicx, "Id", "Id").orElse(dynamicx));
	}
}
