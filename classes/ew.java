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
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ew implements ArgumentType<ew.b> {
	private static final Collection<String> a = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("arguments.item.tag.unknown", object));

	public static ew a() {
		return new ew();
	}

	public ew.b parse(StringReader stringReader) throws CommandSyntaxException {
		ev ev3 = new ev(stringReader, true).h();
		if (ev3.b() != null) {
			ew.a a4 = new ew.a(ev3.b(), ev3.c());
			return commandContext -> a4;
		} else {
			uh uh4 = ev3.d();
			return commandContext -> {
				adf<bke> adf4 = commandContext.getSource().j().aE().b().a(uh4);
				if (adf4 == null) {
					throw b.create(uh4.toString());
				} else {
					return new ew.c(adf4, ev3.c());
				}
			};
		}
	}

	public static Predicate<bki> a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<ew.b>getArgument(string, ew.b.class).create(commandContext);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
		StringReader stringReader4 = new StringReader(suggestionsBuilder.getInput());
		stringReader4.setCursor(suggestionsBuilder.getStart());
		ev ev5 = new ev(stringReader4, true);

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

	static class a implements Predicate<bki> {
		private final bke a;
		@Nullable
		private final le b;

		public a(bke bke, @Nullable le le) {
			this.a = bke;
			this.b = le;
		}

		public boolean test(bki bki) {
			return bki.b() == this.a && lq.a(this.b, bki.o(), true);
		}
	}

	public interface b {
		Predicate<bki> create(CommandContext<cz> commandContext) throws CommandSyntaxException;
	}

	static class c implements Predicate<bki> {
		private final adf<bke> a;
		@Nullable
		private final le b;

		public c(adf<bke> adf, @Nullable le le) {
			this.a = adf;
			this.b = le;
		}

		public boolean test(bki bki) {
			return this.a.a(bki.b()) && lq.a(this.b, bki.o(), true);
		}
	}
}
