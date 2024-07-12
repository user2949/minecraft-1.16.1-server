import com.mojang.brigadier.CommandDispatcher;

public class xh {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(da.a("stop").requires(cz -> cz.c(4)).executes(commandContext -> {
			commandContext.getSource().a(new ne("commands.stop.stopping"), true);
			commandContext.getSource().j().a(false);
			return 1;
		}));
	}
}
