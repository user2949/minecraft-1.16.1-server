import com.mojang.brigadier.CommandDispatcher;

public class xe {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("setworldspawn")
				.requires(cz -> cz.c(2))
				.executes(commandContext -> a(commandContext.getSource(), new fu(commandContext.getSource().d())))
				.then(da.a("pos", eh.a()).executes(commandContext -> a(commandContext.getSource(), eh.b(commandContext, "pos"))))
		);
	}

	private static int a(cz cz, fu fu) {
		cz.e().a_(fu);
		cz.a(new ne("commands.setworldspawn.success", fu.u(), fu.v(), fu.w()), true);
		return 1;
	}
}
