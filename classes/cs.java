import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;

public class cs {
	public static final cs a = new cs(null, null);
	public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("argument.range.ints"));
	private final Float c;
	private final Float d;

	public cs(@Nullable Float float1, @Nullable Float float2) {
		this.c = float1;
		this.d = float2;
	}

	@Nullable
	public Float a() {
		return this.c;
	}

	@Nullable
	public Float b() {
		return this.d;
	}

	public static cs a(StringReader stringReader, boolean boolean2, Function<Float, Float> function) throws CommandSyntaxException {
		if (!stringReader.canRead()) {
			throw bx.a.createWithContext(stringReader);
		} else {
			int integer4 = stringReader.getCursor();
			Float float5 = a(b(stringReader, boolean2), function);
			Float float6;
			if (stringReader.canRead(2) && stringReader.peek() == '.' && stringReader.peek(1) == '.') {
				stringReader.skip();
				stringReader.skip();
				float6 = a(b(stringReader, boolean2), function);
				if (float5 == null && float6 == null) {
					stringReader.setCursor(integer4);
					throw bx.a.createWithContext(stringReader);
				}
			} else {
				if (!boolean2 && stringReader.canRead() && stringReader.peek() == '.') {
					stringReader.setCursor(integer4);
					throw b.createWithContext(stringReader);
				}

				float6 = float5;
			}

			if (float5 == null && float6 == null) {
				stringReader.setCursor(integer4);
				throw bx.a.createWithContext(stringReader);
			} else {
				return new cs(float5, float6);
			}
		}
	}

	@Nullable
	private static Float b(StringReader stringReader, boolean boolean2) throws CommandSyntaxException {
		int integer3 = stringReader.getCursor();

		while (stringReader.canRead() && c(stringReader, boolean2)) {
			stringReader.skip();
		}

		String string4 = stringReader.getString().substring(integer3, stringReader.getCursor());
		if (string4.isEmpty()) {
			return null;
		} else {
			try {
				return Float.parseFloat(string4);
			} catch (NumberFormatException var5) {
				if (boolean2) {
					throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(stringReader, string4);
				} else {
					throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(stringReader, string4);
				}
			}
		}
	}

	private static boolean c(StringReader stringReader, boolean boolean2) {
		char character3 = stringReader.peek();
		if ((character3 < '0' || character3 > '9') && character3 != '-') {
			return boolean2 && character3 == '.' ? !stringReader.canRead(2) || stringReader.peek(1) != '.' : false;
		} else {
			return true;
		}
	}

	@Nullable
	private static Float a(@Nullable Float float1, Function<Float, Float> function) {
		return float1 == null ? null : (Float)function.apply(float1);
	}
}
