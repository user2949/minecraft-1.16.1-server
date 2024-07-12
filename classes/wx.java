import com.mojang.brigadier.CommandDispatcher;

public class wx {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(da.a("say").requires(cz -> cz.c(2)).then(da.a("message", dl.a()).executes(commandContext -> {
			mr mr2 = dl.a(commandContext, "message");
			ne ne3 = new ne("chat.type.announcement", commandContext.getSource().b(), mr2);
			aom aom4 = commandContext.getSource().f();
			if (aom4 != null) {
				commandContext.getSource().j().ac().a(ne3, mo.CHAT, aom4.bR());
			} else {
				commandContext.getSource().j().ac().a(ne3, mo.SYSTEM, v.b);
			}

			return 1;
		})));
	}
}
