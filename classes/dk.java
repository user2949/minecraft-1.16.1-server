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

public class dk implements ArgumentType<bnw> {
	private static final Collection<String> b = Arrays.asList("unbreaking", "silk_touch");
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("enchantment.unknown", object));

	public static dk a() {
		return new dk();
	}

	public static bnw a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, bnw.class);
	}

	public bnw parse(StringReader stringReader) throws CommandSyntaxException {
		uh uh3 = uh.a(stringReader);
		return (bnw)gl.ak.b(uh3).orElseThrow(() -> a.create(uh3));
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return db.a(gl.ak.b(), suggestionsBuilder);
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
