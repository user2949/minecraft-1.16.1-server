import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Arrays;
import java.util.function.Function;

public class ago extends DataFix {
	public ago(Schema schema) {
		super(schema, false);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Schema schema2 = this.getInputSchema();
		return this.fixTypeEverywhereTyped("EntityProjectileOwner", schema2.getType(ajb.p), this::a);
	}

	private Typed<?> a(Typed<?> typed) {
		typed = this.a(typed, "minecraft:egg", this::d);
		typed = this.a(typed, "minecraft:ender_pearl", this::d);
		typed = this.a(typed, "minecraft:experience_bottle", this::d);
		typed = this.a(typed, "minecraft:snowball", this::d);
		typed = this.a(typed, "minecraft:potion", this::d);
		typed = this.a(typed, "minecraft:potion", this::c);
		typed = this.a(typed, "minecraft:llama_spit", this::b);
		typed = this.a(typed, "minecraft:arrow", this::a);
		typed = this.a(typed, "minecraft:spectral_arrow", this::a);
		return this.a(typed, "minecraft:trident", this::a);
	}

	private Dynamic<?> a(Dynamic<?> dynamic) {
		long long3 = dynamic.get("OwnerUUIDMost").asLong(0L);
		long long5 = dynamic.get("OwnerUUIDLeast").asLong(0L);
		return this.a(dynamic, long3, long5).remove("OwnerUUIDMost").remove("OwnerUUIDLeast");
	}

	private Dynamic<?> b(Dynamic<?> dynamic) {
		OptionalDynamic<?> optionalDynamic3 = dynamic.get("Owner");
		long long4 = optionalDynamic3.get("OwnerUUIDMost").asLong(0L);
		long long6 = optionalDynamic3.get("OwnerUUIDLeast").asLong(0L);
		return this.a(dynamic, long4, long6).remove("Owner");
	}

	private Dynamic<?> c(Dynamic<?> dynamic) {
		OptionalDynamic<?> optionalDynamic3 = dynamic.get("Potion");
		return dynamic.set("Item", optionalDynamic3.orElseEmptyMap()).remove("Potion");
	}

	private Dynamic<?> d(Dynamic<?> dynamic) {
		String string3 = "owner";
		OptionalDynamic<?> optionalDynamic4 = dynamic.get("owner");
		long long5 = optionalDynamic4.get("M").asLong(0L);
		long long7 = optionalDynamic4.get("L").asLong(0L);
		return this.a(dynamic, long5, long7).remove("owner");
	}

	private Dynamic<?> a(Dynamic<?> dynamic, long long2, long long3) {
		String string7 = "OwnerUUID";
		return long2 != 0L && long3 != 0L ? dynamic.set("OwnerUUID", dynamic.createIntList(Arrays.stream(a(long2, long3)))) : dynamic;
	}

	private static int[] a(long long1, long long2) {
		return new int[]{(int)(long1 >> 32), (int)long1, (int)(long2 >> 32), (int)long2};
	}

	private Typed<?> a(Typed<?> typed, String string, Function<Dynamic<?>, Dynamic<?>> function) {
		Type<?> type5 = this.getInputSchema().getChoiceType(ajb.p, string);
		Type<?> type6 = this.getOutputSchema().getChoiceType(ajb.p, string);
		return typed.updateTyped(DSL.namedChoice(string, type5), type6, typedx -> typedx.update(DSL.remainderFinder(), function));
	}
}
