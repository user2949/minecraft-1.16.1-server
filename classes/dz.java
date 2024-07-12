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

public class dz implements ArgumentType<String> {
	private static final Collection<String> a = Arrays.asList("foo", "123");
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("team.notFound", object));

	public static dz a() {
		return new dz();
	}

	public static dfk a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		String string3 = commandContext.getArgument(string, String.class);
		dfm dfm4 = commandContext.getSource().j().aF();
		dfk dfk5 = dfm4.f(string3);
		if (dfk5 == null) {
			throw b.create(string3);
		} else {
			return dfk5;
		}
	}

	public String parse(StringReader stringReader) throws CommandSyntaxException {
		return stringReader.readUnquotedString();
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return commandContext.getSource() instanceof db ? db.b(((db)commandContext.getSource()).m(), suggestionsBuilder) : Suggestions.empty();
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
