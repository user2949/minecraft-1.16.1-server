import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public abstract class bx<T extends Number> {
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.range.empty"));
	public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("argument.range.swapped"));
	protected final T c;
	protected final T d;

	protected bx(@Nullable T number1, @Nullable T number2) {
		this.c = number1;
		this.d = number2;
	}

	@Nullable
	public T a() {
		return this.c;
	}

	@Nullable
	public T b() {
		return this.d;
	}

	public boolean c() {
		return this.c == null && this.d == null;
	}

	public JsonElement d() {
		if (this.c()) {
			return JsonNull.INSTANCE;
		} else if (this.c != null && this.c.equals(this.d)) {
			return new JsonPrimitive(this.c);
		} else {
			JsonObject jsonObject2 = new JsonObject();
			if (this.c != null) {
				jsonObject2.addProperty("min", this.c);
			}

			if (this.d != null) {
				jsonObject2.addProperty("max", this.d);
			}

			return jsonObject2;
		}
	}

	protected static <T extends Number, R extends bx<T>> R a(@Nullable JsonElement jsonElement, R bx, BiFunction<JsonElement, String, T> biFunction, bx.a<T, R> a) {
		if (jsonElement == null || jsonElement.isJsonNull()) {
			return bx;
		} else if (adt.b(jsonElement)) {
			T number5 = (T)biFunction.apply(jsonElement, "value");
			return a.create(number5, number5);
		} else {
			JsonObject jsonObject5 = adt.m(jsonElement, "value");
			T number6 = (T)(jsonObject5.has("min") ? biFunction.apply(jsonObject5.get("min"), "min") : null);
			T number7 = (T)(jsonObject5.has("max") ? biFunction.apply(jsonObject5.get("max"), "max") : null);
			return a.create(number6, number7);
		}
	}

	protected static <T extends Number, R extends bx<T>> R a(
		StringReader stringReader, bx.b<T, R> b, Function<String, T> function3, Supplier<DynamicCommandExceptionType> supplier, Function<T, T> function5
	) throws CommandSyntaxException {
		if (!stringReader.canRead()) {
			throw a.createWithContext(stringReader);
		} else {
			int integer6 = stringReader.getCursor();

			try {
				T number7 = (T)a(a(stringReader, function3, supplier), function5);
				T number8;
				if (stringReader.canRead(2) && stringReader.peek() == '.' && stringReader.peek(1) == '.') {
					stringReader.skip();
					stringReader.skip();
					number8 = (T)a(a(stringReader, function3, supplier), function5);
					if (number7 == null && number8 == null) {
						throw a.createWithContext(stringReader);
					}
				} else {
					number8 = number7;
				}

				if (number7 == null && number8 == null) {
					throw a.createWithContext(stringReader);
				} else {
					return b.create(stringReader, number7, number8);
				}
			} catch (CommandSyntaxException var8) {
				stringReader.setCursor(integer6);
				throw new CommandSyntaxException(var8.getType(), var8.getRawMessage(), var8.getInput(), integer6);
			}
		}
	}

	@Nullable
	private static <T extends Number> T a(StringReader stringReader, Function<String, T> function, Supplier<DynamicCommandExceptionType> supplier) throws CommandSyntaxException {
		int integer4 = stringReader.getCursor();

		while (stringReader.canRead() && a(stringReader)) {
			stringReader.skip();
		}

		String string5 = stringReader.getString().substring(integer4, stringReader.getCursor());
		if (string5.isEmpty()) {
			return null;
		} else {
			try {
				return (T)function.apply(string5);
			} catch (NumberFormatException var6) {
				throw ((DynamicCommandExceptionType)supplier.get()).createWithContext(stringReader, string5);
			}
		}
	}

	private static boolean a(StringReader stringReader) {
		char character2 = stringReader.peek();
		if ((character2 < '0' || character2 > '9') && character2 != '-') {
			return character2 != '.' ? false : !stringReader.canRead(2) || stringReader.peek(1) != '.';
		} else {
			return true;
		}
	}

	@Nullable
	private static <T> T a(@Nullable T object, Function<T, T> function) {
		return (T)(object == null ? null : function.apply(object));
	}

	@FunctionalInterface
	public interface a<T extends Number, R extends bx<T>> {
		R create(@Nullable T number1, @Nullable T number2);
	}

	@FunctionalInterface
	public interface b<T extends Number, R extends bx<T>> {
		R create(StringReader stringReader, @Nullable T number2, @Nullable T number3) throws CommandSyntaxException;
	}

	public static class c extends bx<Float> {
		public static final bx.c e = new bx.c(null, null);
		private final Double f;
		private final Double g;

		private static bx.c a(StringReader stringReader, @Nullable Float float2, @Nullable Float float3) throws CommandSyntaxException {
			if (float2 != null && float3 != null && float2 > float3) {
				throw b.createWithContext(stringReader);
			} else {
				return new bx.c(float2, float3);
			}
		}

		@Nullable
		private static Double a(@Nullable Float float1) {
			return float1 == null ? null : float1.doubleValue() * float1.doubleValue();
		}

		private c(@Nullable Float float1, @Nullable Float float2) {
			super(float1, float2);
			this.f = a(float1);
			this.g = a(float2);
		}

		public static bx.c b(float float1) {
			return new bx.c(float1, null);
		}

		public boolean d(float float1) {
			return this.c != null && this.c > float1 ? false : this.d == null || !(this.d < float1);
		}

		public boolean a(double double1) {
			return this.f != null && this.f > double1 ? false : this.g == null || !(this.g < double1);
		}

		public static bx.c a(@Nullable JsonElement jsonElement) {
			return a(jsonElement, e, adt::e, bx.c::new);
		}

		public static bx.c a(StringReader stringReader) throws CommandSyntaxException {
			return a(stringReader, float1 -> float1);
		}

		public static bx.c a(StringReader stringReader, Function<Float, Float> function) throws CommandSyntaxException {
			return a(stringReader, bx.c::a, Float::parseFloat, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidFloat, function);
		}
	}

	public static class d extends bx<Integer> {
		public static final bx.d e = new bx.d(null, null);
		private final Long f;
		private final Long g;

		private static bx.d a(StringReader stringReader, @Nullable Integer integer2, @Nullable Integer integer3) throws CommandSyntaxException {
			if (integer2 != null && integer3 != null && integer2 > integer3) {
				throw b.createWithContext(stringReader);
			} else {
				return new bx.d(integer2, integer3);
			}
		}

		@Nullable
		private static Long a(@Nullable Integer integer) {
			return integer == null ? null : integer.longValue() * integer.longValue();
		}

		private d(@Nullable Integer integer1, @Nullable Integer integer2) {
			super(integer1, integer2);
			this.f = a(integer1);
			this.g = a(integer2);
		}

		public static bx.d a(int integer) {
			return new bx.d(integer, integer);
		}

		public static bx.d b(int integer) {
			return new bx.d(integer, null);
		}

		public boolean d(int integer) {
			return this.c != null && this.c > integer ? false : this.d == null || this.d >= integer;
		}

		public static bx.d a(@Nullable JsonElement jsonElement) {
			return a(jsonElement, e, adt::g, bx.d::new);
		}

		public static bx.d a(StringReader stringReader) throws CommandSyntaxException {
			return a(stringReader, integer -> integer);
		}

		public static bx.d a(StringReader stringReader, Function<Integer, Integer> function) throws CommandSyntaxException {
			return a(stringReader, bx.d::a, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt, function);
		}
	}
}
