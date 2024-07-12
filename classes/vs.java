import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

public class vs {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(da.a("me").then(da.a("action", StringArgumentType.greedyString()).executes(commandContext -> {
			ne ne2 = new ne("chat.type.emote", commandContext.getSource().b(), StringArgumentType.getString(commandContext, "action"));
			aom aom3 = commandContext.getSource().f();
			if (aom3 != null) {
				commandContext.getSource().j().ac().a(ne2, mo.CHAT, aom3.bR());
			} else {
				commandContext.getSource().j().ac().a(ne2, mo.SYSTEM, v.b);
			}

			return 1;
		})));
	}
}
