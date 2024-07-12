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

public class dt implements ArgumentType<hf> {
	private static final Collection<String> b = Arrays.asList("foo", "foo:bar", "particle with options");
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("particle.notFound", object));

	public static dt a() {
		return new dt();
	}

	public static hf a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, hf.class);
	}

	public hf parse(StringReader stringReader) throws CommandSyntaxException {
		return b(stringReader);
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}

	public static hf b(StringReader stringReader) throws CommandSyntaxException {
		uh uh2 = uh.a(stringReader);
		hg<?> hg3 = (hg<?>)gl.az.b(uh2).orElseThrow(() -> a.create(uh2));
		return a(stringReader, (hg<hf>)hg3);
	}

	private static <T extends hf> T a(StringReader stringReader, hg<T> hg) throws CommandSyntaxException {
		return hg.d().b(hg, stringReader);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		return db.a(gl.az.b(), suggestionsBuilder);
	}
}
