import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class aio extends DataFix {
	public aio(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	private Dynamic<?> a(Dynamic<?> dynamic) {
		Optional<? extends Dynamic<?>> optional3 = dynamic.get("display").result();
		if (optional3.isPresent()) {
			Dynamic<?> dynamic4 = (Dynamic<?>)optional3.get();
			Optional<String> optional5 = dynamic4.get("Name").asString().result();
			if (optional5.isPresent()) {
				String string6 = (String)optional5.get();
				string6 = string6.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
				dynamic4 = dynamic4.set("Name", dynamic4.createString(string6));
			}

			return dynamic.set("display", dynamic4);
		} else {
			return dynamic;
		}
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.l);
		OpticFinder<Pair<String, String>> opticFinder3 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
		OpticFinder<?> opticFinder4 = type2.findField("tag");
		return this.fixTypeEverywhereTyped("OminousBannerRenameFix", type2, typed -> {
			Optional<Pair<String, String>> optional5 = typed.getOptional(opticFinder3);
			if (optional5.isPresent() && Objects.equals(((Pair)optional5.get()).getSecond(), "minecraft:white_banner")) {
				Optional<? extends Typed<?>> optional6 = typed.getOptionalTyped(opticFinder4);
				if (optional6.isPresent()) {
					Typed<?> typed7 = (Typed<?>)optional6.get();
					Dynamic<?> dynamic8 = typed7.get(DSL.remainderFinder());
					return typed.set(opticFinder4, typed7.set(DSL.remainderFinder(), this.a(dynamic8)));
				}
			}

			return typed;
		});
	}
}
