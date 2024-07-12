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

public class dc implements ArgumentType<i> {
	private static final Collection<String> b = Arrays.asList("red", "green");
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("argument.color.invalid", object));

	private dc() {
	}

	public static dc a() {
		return new dc();
	}

	public static i a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, i.class);
	}

	public i parse(StringReader stringReader) throws CommandSyntaxException {
		String string3 = stringReader.readUnquotedString();
		i i4 = i.b(string3);
		if (i4 != null && !i4.c()) {
			return i4;
		} else {
			throw a.create(string3);
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return db.b(i.a(true, false), suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
