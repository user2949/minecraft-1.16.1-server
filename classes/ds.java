import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ds implements ArgumentType<ds.a> {
	private static final Collection<String> a = Arrays.asList("=", ">", "<");
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("arguments.operation.invalid"));
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("arguments.operation.div0"));

	public static ds a() {
		return new ds();
	}

	public static ds.a a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.getArgument(string, ds.a.class);
	}

	public ds.a parse(StringReader stringReader) throws CommandSyntaxException {
		if (!stringReader.canRead()) {
			throw b.create();
		} else {
			int integer3 = stringReader.getCursor();

			while (stringReader.canRead() && stringReader.peek() != ' ') {
				stringReader.skip();
			}

			return a(stringReader.getString().substring(integer3, stringReader.getCursor()));
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return db.a(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}

	private static ds.a a(String string) throws CommandSyntaxException {
		return (ds.a)(string.equals("><") ? (dfl1, dfl2) -> {
			int integer3 = dfl1.b();
			dfl1.c(dfl2.b());
			dfl2.c(integer3);
		} : b(string));
	}

	private static ds.b b(String string) throws CommandSyntaxException {
		switch (string) {
			case "=":
				return (integer1, integer2) -> integer2;
			case "+=":
				return (integer1, integer2) -> integer1 + integer2;
			case "-=":
				return (integer1, integer2) -> integer1 - integer2;
			case "*=":
				return (integer1, integer2) -> integer1 * integer2;
			case "/=":
				return (integer1, integer2) -> {
					if (integer2 == 0) {
						throw c.create();
					} else {
						return aec.a(integer1, integer2);
					}
				};
			case "%=":
				return (integer1, integer2) -> {
					if (integer2 == 0) {
						throw c.create();
					} else {
						return aec.b(integer1, integer2);
					}
				};
			case "<":
				return Math::min;
			case ">":
				return Math::max;
			default:
				throw b.create();
		}
	}

	@FunctionalInterface
	public interface a {
		void apply(dfl dfl1, dfl dfl2) throws CommandSyntaxException;
	}

	@FunctionalInterface
	interface b extends ds.a {
		int apply(int integer1, int integer2) throws CommandSyntaxException;

		@Override
		default void apply(dfl dfl1, dfl dfl2) throws CommandSyntaxException {
			dfl1.c(this.apply(dfl1.b(), dfl2.b()));
		}
	}
}
