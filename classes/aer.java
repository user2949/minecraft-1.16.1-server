import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class aer extends DataFix {
	protected static final Logger a = LogManager.getLogger();
	protected TypeReference b;

	public aer(Schema schema, TypeReference typeReference) {
		super(schema, false);
		this.b = typeReference;
	}

	protected Typed<?> a(Typed<?> typed, String string, Function<Dynamic<?>, Dynamic<?>> function) {
		Type<?> type5 = this.getInputSchema().getChoiceType(this.b, string);
		Type<?> type6 = this.getOutputSchema().getChoiceType(this.b, string);
		return typed.updateTyped(DSL.namedChoice(string, type5), type6, typedx -> typedx.update(DSL.remainderFinder(), function));
	}

	protected static Optional<Dynamic<?>> a(Dynamic<?> dynamic, String string2, String string3) {
		return a(dynamic, string2).map(dynamic4 -> dynamic.remove(string2).set(string3, dynamic4));
	}

	protected static Optional<Dynamic<?>> b(Dynamic<?> dynamic, String string2, String string3) {
		return dynamic.get(string2).result().flatMap(aer::a).map(dynamic4 -> dynamic.remove(string2).set(string3, dynamic4));
	}

	protected static Optional<Dynamic<?>> c(Dynamic<?> dynamic, String string2, String string3) {
		String string4 = string2 + "Most";
		String string5 = string2 + "Least";
		return d(dynamic, string4, string5).map(dynamic5 -> dynamic.remove(string4).remove(string5).set(string3, dynamic5));
	}

	protected static Optional<Dynamic<?>> a(Dynamic<?> dynamic, String string) {
		return dynamic.get(string).result().flatMap(dynamic2 -> {
			String string3 = dynamic2.asString(null);
			if (string3 != null) {
				try {
					UUID uUID4 = UUID.fromString(string3);
					return a(dynamic, uUID4.getMostSignificantBits(), uUID4.getLeastSignificantBits());
				} catch (IllegalArgumentException var4) {
				}
			}

			return Optional.empty();
		});
	}

	protected static Optional<Dynamic<?>> a(Dynamic<?> dynamic) {
		return d(dynamic, "M", "L");
	}

	protected static Optional<Dynamic<?>> d(Dynamic<?> dynamic, String string2, String string3) {
		long long4 = dynamic.get(string2).asLong(0L);
		long long6 = dynamic.get(string3).asLong(0L);
		return long4 != 0L && long6 != 0L ? a(dynamic, long4, long6) : Optional.empty();
	}

	protected static Optional<Dynamic<?>> a(Dynamic<?> dynamic, long long2, long long3) {
		return Optional.of(dynamic.createIntList(Arrays.stream(new int[]{(int)(long2 >> 32), (int)long2, (int)(long3 >> 32), (int)long3})));
	}
}
