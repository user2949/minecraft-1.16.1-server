import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;

public class ajt extends aij {
	public ajt(Schema schema, boolean boolean2) {
		super(schema, boolean2, "Villager trade fix", ajb.p, "minecraft:villager");
	}

	@Override
	protected Typed<?> a(Typed<?> typed) {
		OpticFinder<?> opticFinder3 = typed.getType().findField("Offers");
		OpticFinder<?> opticFinder4 = opticFinder3.type().findField("Recipes");
		Type<?> type5 = opticFinder4.type();
		if (!(type5 instanceof ListType)) {
			throw new IllegalStateException("Recipes are expected to be a list.");
		} else {
			ListType<?> listType6 = (ListType<?>)type5;
			Type<?> type7 = listType6.getElement();
			OpticFinder<?> opticFinder8 = DSL.typeFinder(type7);
			OpticFinder<?> opticFinder9 = type7.findField("buy");
			OpticFinder<?> opticFinder10 = type7.findField("buyB");
			OpticFinder<?> opticFinder11 = type7.findField("sell");
			OpticFinder<Pair<String, String>> opticFinder12 = DSL.fieldFinder("id", DSL.named(ajb.r.typeName(), aka.a()));
			Function<Typed<?>, Typed<?>> function13 = typedx -> this.a(opticFinder12, typedx);
			return typed.updateTyped(
				opticFinder3,
				typedx -> typedx.updateTyped(
						opticFinder4,
						typedxx -> typedxx.updateTyped(
								opticFinder8, typedxxx -> typedxxx.updateTyped(opticFinder9, function13).updateTyped(opticFinder10, function13).updateTyped(opticFinder11, function13)
							)
					)
			);
		}
	}

	private Typed<?> a(OpticFinder<Pair<String, String>> opticFinder, Typed<?> typed) {
		return typed.update(opticFinder, pair -> pair.mapSecond(string -> Objects.equals(string, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : string));
	}
}
