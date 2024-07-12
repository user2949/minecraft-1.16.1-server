import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;

public class vy {
	public static final SuggestionProvider<cz> a = (commandContext, suggestionsBuilder) -> {
		uu uu3 = commandContext.getSource().j().az();
		db.a(uu3.g(), suggestionsBuilder, "#");
		return db.a(uu3.f(), suggestionsBuilder);
	};

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("function")
				.requires(cz -> cz.c(2))
				.then(da.a("name", es.a()).suggests(a).executes(commandContext -> a(commandContext.getSource(), es.a(commandContext, "name"))))
		);
	}

	private static int a(cz cz, Collection<cw> collection) {
		int integer3 = 0;

		for (cw cw5 : collection) {
			integer3 += cz.j().az().a(cw5, cz.a().b(2));
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.function.success.single", integer3, ((cw)collection.iterator().next()).a()), true);
		} else {
			cz.a(new ne("commands.function.success.multiple", integer3, collection.size()), true);
		}

		return integer3;
	}
}
