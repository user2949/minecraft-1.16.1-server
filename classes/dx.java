import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class dx implements ArgumentType<Integer> {
	private static final Collection<String> b = Arrays.asList("sidebar", "foo.bar");
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("argument.scoreboardDisplaySlot.invalid", object));

	private dx() {
	}

	public static dx a() {
		return new dx();
	}

	public static int a(CommandContext<cz> commandContext, String string) {
		return commandContext.<Integer>getArgument(string, Integer.class);
	}

	public Integer parse(StringReader stringReader) throws CommandSyntaxException {
		String string3 = stringReader.readUnquotedString();
		int integer4 = dfm.j(string3);
		if (integer4 == -1) {
			throw a.create(string3);
		} else {
			return integer4;
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return db.a(dfm.h(), suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
