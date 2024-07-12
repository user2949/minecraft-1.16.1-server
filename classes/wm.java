import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.regex.Matcher;

public class wm {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.pardonip.invalid"));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.pardonip.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("pardon-ip")
				.requires(cz -> cz.c(3))
				.then(
					da.a("target", StringArgumentType.word())
						.suggests((commandContext, suggestionsBuilder) -> db.a(commandContext.getSource().j().ac().g().a(), suggestionsBuilder))
						.executes(commandContext -> a(commandContext.getSource(), StringArgumentType.getString(commandContext, "target")))
				)
		);
	}

	private static int a(cz cz, String string) throws CommandSyntaxException {
		Matcher matcher3 = ve.a.matcher(string);
		if (!matcher3.matches()) {
			throw a.create();
		} else {
			abm abm4 = cz.j().ac().g();
			if (!abm4.a(string)) {
				throw b.create();
			} else {
				abm4.c(string);
				cz.a(new ne("commands.pardonip.success", string), true);
				return 1;
			}
		}
	}
}
