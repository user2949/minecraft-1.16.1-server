import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class wl {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.pardon.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("pardon")
				.requires(cz -> cz.c(3))
				.then(
					da.a("targets", dj.a())
						.suggests((commandContext, suggestionsBuilder) -> db.a(commandContext.getSource().j().ac().f().a(), suggestionsBuilder))
						.executes(commandContext -> a(commandContext.getSource(), dj.a(commandContext, "targets")))
				)
		);
	}

	private static int a(cz cz, Collection<GameProfile> collection) throws CommandSyntaxException {
		abu abu3 = cz.j().ac().f();
		int integer4 = 0;

		for (GameProfile gameProfile6 : collection) {
			if (abu3.a(gameProfile6)) {
				abu3.c(gameProfile6);
				integer4++;
				cz.a(new ne("commands.pardon.success", ms.a(gameProfile6)), true);
			}
		}

		if (integer4 == 0) {
			throw a.create();
		} else {
			return integer4;
		}
	}
}
