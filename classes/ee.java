import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ee implements ArgumentType<ec> {
	private static final Collection<String> a = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}");

	public static ee a() {
		return new ee();
	}

	public ec parse(StringReader stringReader) throws CommandSyntaxException {
		ef ef3 = new ef(stringReader, false).a(true);
		return new ec(ef3.b(), ef3.a().keySet(), ef3.c());
	}

	public static ec a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, ec.class);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		StringReader stringReader4 = new StringReader(suggestionsBuilder.getInput());
		stringReader4.setCursor(suggestionsBuilder.getStart());
		ef ef5 = new ef(stringReader4, false);

		try {
			ef5.a(true);
		} catch (CommandSyntaxException var6) {
		}

		return ef5.a(suggestionsBuilder, acx.b());
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
