import com.mojang.brigadier.Message;
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

public class kt implements ArgumentType<String> {
	private static final Collection<String> a = Arrays.asList("techtests", "mobtests");

	public String parse(StringReader stringReader) throws CommandSyntaxException {
		String string3 = stringReader.readUnquotedString();
		if (ki.b(string3)) {
			return string3;
		} else {
			Message message4 = new nd("No such test class: " + string3);
			throw new CommandSyntaxException(new SimpleCommandExceptionType(message4), message4);
		}
	}

	public static kt a() {
		return new kt();
	}

	public static String a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, String.class);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return db.b(ki.b().stream(), suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
