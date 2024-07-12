import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class ww {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.save.alreadyOn"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(da.a("save-on").requires(cz -> cz.c(4)).executes(commandContext -> {
			cz cz2 = commandContext.getSource();
			boolean boolean3 = false;

			for (zd zd5 : cz2.j().F()) {
				if (zd5 != null && zd5.c) {
					zd5.c = false;
					boolean3 = true;
				}
			}

			if (!boolean3) {
				throw a.create();
			} else {
				cz2.a(new ne("commands.save.enabled"), true);
				return 1;
			}
		}));
	}
}
