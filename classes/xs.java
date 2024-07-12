import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

public class xs {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("weather")
				.requires(cz -> cz.c(2))
				.then(
					da.a("clear")
						.executes(commandContext -> a(commandContext.getSource(), 6000))
						.then(
							da.a("duration", IntegerArgumentType.integer(0, 1000000))
								.executes(commandContext -> a(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "duration") * 20))
						)
				)
				.then(
					da.a("rain")
						.executes(commandContext -> b(commandContext.getSource(), 6000))
						.then(
							da.a("duration", IntegerArgumentType.integer(0, 1000000))
								.executes(commandContext -> b(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "duration") * 20))
						)
				)
				.then(
					da.a("thunder")
						.executes(commandContext -> c(commandContext.getSource(), 6000))
						.then(
							da.a("duration", IntegerArgumentType.integer(0, 1000000))
								.executes(commandContext -> c(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "duration") * 20))
						)
				)
		);
	}

	private static int a(cz cz, int integer) {
		cz.e().a(integer, 0, false, false);
		cz.a(new ne("commands.weather.set.clear"), true);
		return integer;
	}

	private static int b(cz cz, int integer) {
		cz.e().a(0, integer, true, false);
		cz.a(new ne("commands.weather.set.rain"), true);
		return integer;
	}

	private static int c(cz cz, int integer) {
		cz.e().a(0, integer, true, true);
		cz.a(new ne("commands.weather.set.thunder"), true);
		return integer;
	}
}
