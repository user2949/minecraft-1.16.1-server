import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class cv implements BuiltInExceptionProvider {
	private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType((object1, object2) -> new ne("argument.double.low", object2, object1));
	private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType((object1, object2) -> new ne("argument.double.big", object2, object1));
	private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((object1, object2) -> new ne("argument.float.low", object2, object1));
	private static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType((object1, object2) -> new ne("argument.float.big", object2, object1));
	private static final Dynamic2CommandExceptionType e = new Dynamic2CommandExceptionType((object1, object2) -> new ne("argument.integer.low", object2, object1));
	private static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType((object1, object2) -> new ne("argument.integer.big", object2, object1));
	private static final Dynamic2CommandExceptionType g = new Dynamic2CommandExceptionType((object1, object2) -> new ne("argument.long.low", object2, object1));
	private static final Dynamic2CommandExceptionType h = new Dynamic2CommandExceptionType((object1, object2) -> new ne("argument.long.big", object2, object1));
	private static final DynamicCommandExceptionType i = new DynamicCommandExceptionType(object -> new ne("argument.literal.incorrect", object));
	private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(new ne("parsing.quote.expected.start"));
	private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(new ne("parsing.quote.expected.end"));
	private static final DynamicCommandExceptionType l = new DynamicCommandExceptionType(object -> new ne("parsing.quote.escape", object));
	private static final DynamicCommandExceptionType m = new DynamicCommandExceptionType(object -> new ne("parsing.bool.invalid", object));
	private static final DynamicCommandExceptionType n = new DynamicCommandExceptionType(object -> new ne("parsing.int.invalid", object));
	private static final SimpleCommandExceptionType o = new SimpleCommandExceptionType(new ne("parsing.int.expected"));
	private static final DynamicCommandExceptionType p = new DynamicCommandExceptionType(object -> new ne("parsing.long.invalid", object));
	private static final SimpleCommandExceptionType q = new SimpleCommandExceptionType(new ne("parsing.long.expected"));
	private static final DynamicCommandExceptionType r = new DynamicCommandExceptionType(object -> new ne("parsing.double.invalid", object));
	private static final SimpleCommandExceptionType s = new SimpleCommandExceptionType(new ne("parsing.double.expected"));
	private static final DynamicCommandExceptionType t = new DynamicCommandExceptionType(object -> new ne("parsing.float.invalid", object));
	private static final SimpleCommandExceptionType u = new SimpleCommandExceptionType(new ne("parsing.float.expected"));
	private static final SimpleCommandExceptionType v = new SimpleCommandExceptionType(new ne("parsing.bool.expected"));
	private static final DynamicCommandExceptionType w = new DynamicCommandExceptionType(object -> new ne("parsing.expected", object));
	private static final SimpleCommandExceptionType x = new SimpleCommandExceptionType(new ne("command.unknown.command"));
	private static final SimpleCommandExceptionType y = new SimpleCommandExceptionType(new ne("command.unknown.argument"));
	private static final SimpleCommandExceptionType z = new SimpleCommandExceptionType(new ne("command.expected.separator"));
	private static final DynamicCommandExceptionType A = new DynamicCommandExceptionType(object -> new ne("command.exception", object));

	@Override
	public Dynamic2CommandExceptionType doubleTooLow() {
		return a;
	}

	@Override
	public Dynamic2CommandExceptionType doubleTooHigh() {
		return b;
	}

	@Override
	public Dynamic2CommandExceptionType floatTooLow() {
		return c;
	}

	@Override
	public Dynamic2CommandExceptionType floatTooHigh() {
		return d;
	}

	@Override
	public Dynamic2CommandExceptionType integerTooLow() {
		return e;
	}

	@Override
	public Dynamic2CommandExceptionType integerTooHigh() {
		return f;
	}

	@Override
	public Dynamic2CommandExceptionType longTooLow() {
		return g;
	}

	@Override
	public Dynamic2CommandExceptionType longTooHigh() {
		return h;
	}

	@Override
	public DynamicCommandExceptionType literalIncorrect() {
		return i;
	}

	@Override
	public SimpleCommandExceptionType readerExpectedStartOfQuote() {
		return j;
	}

	@Override
	public SimpleCommandExceptionType readerExpectedEndOfQuote() {
		return k;
	}

	@Override
	public DynamicCommandExceptionType readerInvalidEscape() {
		return l;
	}

	@Override
	public DynamicCommandExceptionType readerInvalidBool() {
		return m;
	}

	@Override
	public DynamicCommandExceptionType readerInvalidInt() {
		return n;
	}

	@Override
	public SimpleCommandExceptionType readerExpectedInt() {
		return o;
	}

	@Override
	public DynamicCommandExceptionType readerInvalidLong() {
		return p;
	}

	@Override
	public SimpleCommandExceptionType readerExpectedLong() {
		return q;
	}

	@Override
	public DynamicCommandExceptionType readerInvalidDouble() {
		return r;
	}

	@Override
	public SimpleCommandExceptionType readerExpectedDouble() {
		return s;
	}

	@Override
	public DynamicCommandExceptionType readerInvalidFloat() {
		return t;
	}

	@Override
	public SimpleCommandExceptionType readerExpectedFloat() {
		return u;
	}

	@Override
	public SimpleCommandExceptionType readerExpectedBool() {
		return v;
	}

	@Override
	public DynamicCommandExceptionType readerExpectedSymbol() {
		return w;
	}

	@Override
	public SimpleCommandExceptionType dispatcherUnknownCommand() {
		return x;
	}

	@Override
	public SimpleCommandExceptionType dispatcherUnknownArgument() {
		return y;
	}

	@Override
	public SimpleCommandExceptionType dispatcherExpectedArgumentSeparator() {
		return z;
	}

	@Override
	public DynamicCommandExceptionType dispatcherParseException() {
		return A;
	}
}
