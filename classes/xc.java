import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

public class xc {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("setidletimeout")
				.requires(cz -> cz.c(3))
				.then(
					da.a("minutes", IntegerArgumentType.integer(0))
						.executes(commandContext -> a(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "minutes")))
				)
		);
	}

	private static int a(cz cz, int integer) {
		cz.j().d(integer);
		cz.a(new ne("commands.setidletimeout.success", integer), true);
		return integer;
	}
}
