import com.google.common.collect.Maps;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class fj {
	private static final Map<uh, SuggestionProvider<db>> f = Maps.<uh, SuggestionProvider<db>>newHashMap();
	private static final uh g = new uh("ask_server");
	public static final SuggestionProvider<db> a = a(g, (commandContext, suggestionsBuilder) -> commandContext.getSource().a(commandContext, suggestionsBuilder));
	public static final SuggestionProvider<cz> b = a(
		new uh("all_recipes"), (commandContext, suggestionsBuilder) -> db.a(commandContext.getSource().o(), suggestionsBuilder)
	);
	public static final SuggestionProvider<cz> c = a(
		new uh("available_sounds"), (commandContext, suggestionsBuilder) -> db.a(commandContext.getSource().n(), suggestionsBuilder)
	);
	public static final SuggestionProvider<cz> d = a(new uh("available_biomes"), (commandContext, suggestionsBuilder) -> db.a(gl.as.b(), suggestionsBuilder));
	public static final SuggestionProvider<cz> e = a(
		new uh("summonable_entities"),
		(commandContext, suggestionsBuilder) -> db.a(gl.al.e().filter(aoq::b), suggestionsBuilder, aoq::a, aoq -> new ne(v.a("entity", aoq.a(aoq))))
	);

	public static <S extends db> SuggestionProvider<S> a(uh uh, SuggestionProvider<db> suggestionProvider) {
		if (f.containsKey(uh)) {
			throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + uh);
		} else {
			f.put(uh, suggestionProvider);
			return new fj.a(uh, suggestionProvider);
		}
	}

	public static SuggestionProvider<db> a(uh uh) {
		return (SuggestionProvider<db>)f.getOrDefault(uh, a);
	}

	public static uh a(SuggestionProvider<db> suggestionProvider) {
		return suggestionProvider instanceof fj.a ? ((fj.a)suggestionProvider).b : g;
	}

	public static SuggestionProvider<db> b(SuggestionProvider<db> suggestionProvider) {
		return suggestionProvider instanceof fj.a ? suggestionProvider : a;
	}

	public static class a implements SuggestionProvider<db> {
		private final SuggestionProvider<db> a;
		private final uh b;

		public a(uh uh, SuggestionProvider<db> suggestionProvider) {
			this.a = suggestionProvider;
			this.b = uh;
		}

		@Override
		public CompletableFuture<Suggestions> getSuggestions(CommandContext<db> commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
			return this.a.getSuggestions(commandContext, suggestionsBuilder);
		}
	}
}
