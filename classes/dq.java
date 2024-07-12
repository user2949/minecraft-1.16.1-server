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

public class dq implements ArgumentType<String> {
	private static final Collection<String> b = Arrays.asList("foo", "*", "012");
	private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> new ne("arguments.objective.notFound", object));
	private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(object -> new ne("arguments.objective.readonly", object));
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("commands.scoreboard.objectives.add.longName", object));

	public static dq a() {
		return new dq();
	}

	public static dfj a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		String string3 = commandContext.getArgument(string, String.class);
		dfm dfm4 = commandContext.getSource().j().aF();
		dfj dfj5 = dfm4.d(string3);
		if (dfj5 == null) {
			throw c.create(string3);
		} else {
			return dfj5;
		}
	}

	public static dfj b(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		dfj dfj3 = a(commandContext, string);
		if (dfj3.c().d()) {
			throw d.create(dfj3.b());
		} else {
			return dfj3;
		}
	}

	public String parse(StringReader stringReader) throws CommandSyntaxException {
		String string3 = stringReader.readUnquotedString();
		if (string3.length() > 16) {
			throw a.create(16);
		} else {
			return string3;
		}
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		if (commandContext.getSource() instanceof cz) {
			return db.b(((cz)commandContext.getSource()).j().aF().d(), suggestionsBuilder);
		} else if (commandContext.getSource() instanceof db) {
			db db4 = (db)commandContext.getSource();
			return db4.a(commandContext, suggestionsBuilder);
		} else {
			return Suggestions.empty();
		}
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
