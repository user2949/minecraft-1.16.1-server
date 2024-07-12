import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;

public class vg {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.ban.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("ban")
				.requires(cz -> cz.c(3))
				.then(
					da.a("targets", dj.a())
						.executes(commandContext -> a(commandContext.getSource(), dj.a(commandContext, "targets"), null))
						.then(da.a("reason", dl.a()).executes(commandContext -> a(commandContext.getSource(), dj.a(commandContext, "targets"), dl.a(commandContext, "reason"))))
				)
		);
	}

	private static int a(cz cz, Collection<GameProfile> collection, @Nullable mr mr) throws CommandSyntaxException {
		abu abu4 = cz.j().ac().f();
		int integer5 = 0;

		for (GameProfile gameProfile7 : collection) {
			if (!abu4.a(gameProfile7)) {
				abv abv8 = new abv(gameProfile7, null, cz.c(), null, mr == null ? null : mr.getString());
				abu4.a(abv8);
				integer5++;
				cz.a(new ne("commands.ban.success", ms.a(gameProfile7), abv8.d()), true);
				ze ze9 = cz.j().ac().a(gameProfile7.getId());
				if (ze9 != null) {
					ze9.b.b(new ne("multiplayer.disconnect.banned"));
				}
			}
		}

		if (integer5 == 0) {
			throw a.create();
		} else {
			return integer5;
		}
	}
}
