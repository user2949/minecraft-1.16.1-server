import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class wk {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.op.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(da.a("op").requires(cz -> cz.c(3)).then(da.a("targets", dj.a()).suggests((commandContext, suggestionsBuilder) -> {
			abp abp3 = commandContext.getSource().j().ac();
			return db.b(abp3.s().stream().filter(ze -> !abp3.h(ze.ez())).map(ze -> ze.ez().getName()), suggestionsBuilder);
		}).executes(commandContext -> a(commandContext.getSource(), dj.a(commandContext, "targets")))));
	}

	private static int a(cz cz, Collection<GameProfile> collection) throws CommandSyntaxException {
		abp abp3 = cz.j().ac();
		int integer4 = 0;

		for (GameProfile gameProfile6 : collection) {
			if (!abp3.h(gameProfile6)) {
				abp3.a(gameProfile6);
				integer4++;
				cz.a(new ne("commands.op.success", ((GameProfile)collection.iterator().next()).getName()), true);
			}
		}

		if (integer4 == 0) {
			throw a.create();
		} else {
			return integer4;
		}
	}
}
