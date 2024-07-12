import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class dr implements ArgumentType<dfp> {
	private static final Collection<String> b = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("argument.criteria.invalid", object));

	private dr() {
	}

	public static dr a() {
		return new dr();
	}

	public static dfp a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, dfp.class);
	}

	public dfp parse(StringReader stringReader) throws CommandSyntaxException {
		int integer3 = stringReader.getCursor();

		while (stringReader.canRead() && stringReader.peek() != ' ') {
			stringReader.skip();
		}

		String string4 = stringReader.getString().substring(integer3, stringReader.getCursor());
		return (dfp)dfp.a(string4).orElseThrow(() -> {
			stringReader.setCursor(integer3);
			return a.create(string4);
		});
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		List<String> list4 = Lists.<String>newArrayList(dfp.a.keySet());

		for (act<?> act6 : gl.aQ) {
			for (Object object8 : act6.a()) {
				String string9 = this.a(act6, object8);
				list4.add(string9);
			}
		}

		return db.b(list4, suggestionsBuilder);
	}

	public <T> String a(act<T> act, Object object) {
		return acr.a(act, (T)object);
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
