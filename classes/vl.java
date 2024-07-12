import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class vl {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.deop.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("deop")
				.requires(cz -> cz.c(3))
				.then(
					da.a("targets", dj.a())
						.suggests((commandContext, suggestionsBuilder) -> db.a(commandContext.getSource().j().ac().l(), suggestionsBuilder))
						.executes(commandContext -> a(commandContext.getSource(), dj.a(commandContext, "targets")))
				)
		);
	}

	private static int a(cz cz, Collection<GameProfile> collection) throws CommandSyntaxException {
		abp abp3 = cz.j().ac();
		int integer4 = 0;

		for (GameProfile gameProfile6 : collection) {
			if (abp3.h(gameProfile6)) {
				abp3.b(gameProfile6);
				integer4++;
				cz.a(new ne("commands.deop.success", ((GameProfile)collection.iterator().next()).getName()), true);
			}
		}

		if (integer4 == 0) {
			throw a.create();
		} else {
			cz.j().a(cz);
			return integer4;
		}
	}
}
