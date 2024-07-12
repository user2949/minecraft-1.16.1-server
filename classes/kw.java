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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class kw implements ArgumentType<kv> {
	private static final Collection<String> a = Arrays.asList("techtests.piston", "techtests");

	public kv parse(StringReader stringReader) throws CommandSyntaxException {
		String string3 = stringReader.readUnquotedString();
		Optional<kv> optional4 = ki.d(string3);
		if (optional4.isPresent()) {
			return (kv)optional4.get();
		} else {
			Message message5 = new nd("No such test: " + string3);
			throw new CommandSyntaxException(new SimpleCommandExceptionType(message5), message5);
		}
	}

	public static kw a() {
		return new kw();
	}

	public static kv a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, kv.class);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		Stream<String> stream4 = ki.a().stream().map(kv::a);
		return db.b(stream4, suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
