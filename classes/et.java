import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class et implements ArgumentType<eu> {
	private static final Collection<String> a = Arrays.asList("stick", "minecraft:stick", "stick{foo=bar}");

	public static et a() {
		return new et();
	}

	public eu parse(StringReader stringReader) throws CommandSyntaxException {
		ev ev3 = new ev(stringReader, false).h();
		return new eu(ev3.b(), ev3.c());
	}

	public static <S> eu a(CommandContext<S> commandContext, String string) {
		return commandContext.getArgument(string, eu.class);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		StringReader stringReader4 = new StringReader(suggestionsBuilder.getInput());
		stringReader4.setCursor(suggestionsBuilder.getStart());
		ev ev5 = new ev(stringReader4, false);

		try {
			ev5.h();
		} catch (CommandSyntaxException var6) {
		}

		return ev5.a(suggestionsBuilder, ada.b());
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
