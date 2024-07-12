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

public class dm implements ArgumentType<aoe> {
	private static final Collection<String> b = Arrays.asList("spooky", "effect");
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("effect.effectNotFound", object));

	public static dm a() {
		return new dm();
	}

	public static aoe a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.getArgument(string, aoe.class);
	}

	public aoe parse(StringReader stringReader) throws CommandSyntaxException {
		uh uh3 = uh.a(stringReader);
		return (aoe)gl.ai.b(uh3).orElseThrow(() -> a.create(uh3));
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return db.a(gl.ai.b(), suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
