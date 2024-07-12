import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class df implements ArgumentType<uh> {
	private static final Collection<String> a = (Collection<String>)Stream.of(bqb.g, bqb.h).map(ug -> ug.a().toString()).collect(Collectors.toList());
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("argument.dimension.invalid", object));

	public uh parse(StringReader stringReader) throws CommandSyntaxException {
		return uh.a(stringReader);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return commandContext.getSource() instanceof db ? db.a(((db)commandContext.getSource()).p().stream().map(ug::a), suggestionsBuilder) : Suggestions.empty();
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}

	public static df a() {
		return new df();
	}

	public static zd a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		uh uh3 = commandContext.getArgument(string, uh.class);
		ug<bqb> ug4 = ug.a(gl.ae, uh3);
		zd zd5 = commandContext.getSource().j().a(ug4);
		if (zd5 == null) {
			throw b.create(uh3);
		} else {
			return zd5;
		}
	}
}
