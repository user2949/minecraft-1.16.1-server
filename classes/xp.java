import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

public class xp {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("time")
				.requires(cz -> cz.c(2))
				.then(
					da.a("set")
						.then(da.a("day").executes(commandContext -> a(commandContext.getSource(), 1000)))
						.then(da.a("noon").executes(commandContext -> a(commandContext.getSource(), 6000)))
						.then(da.a("night").executes(commandContext -> a(commandContext.getSource(), 13000)))
						.then(da.a("midnight").executes(commandContext -> a(commandContext.getSource(), 18000)))
						.then(da.a("time", ea.a()).executes(commandContext -> a(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "time"))))
				)
				.then(
					da.a("add").then(da.a("time", ea.a()).executes(commandContext -> b(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "time"))))
				)
				.then(
					da.a("query")
						.then(da.a("daytime").executes(commandContext -> c(commandContext.getSource(), a(commandContext.getSource().e()))))
						.then(da.a("gametime").executes(commandContext -> c(commandContext.getSource(), (int)(commandContext.getSource().e().Q() % 2147483647L))))
						.then(da.a("day").executes(commandContext -> c(commandContext.getSource(), (int)(commandContext.getSource().e().R() / 24000L % 2147483647L))))
				)
		);
	}

	private static int a(zd zd) {
		return (int)(zd.R() % 24000L);
	}

	private static int c(cz cz, int integer) {
		cz.a(new ne("commands.time.query", integer), false);
		return integer;
	}

	public static int a(cz cz, int integer) {
		for (zd zd4 : cz.j().F()) {
			zd4.a((long)integer);
		}

		cz.a(new ne("commands.time.set", integer), true);
		return a(cz.e());
	}

	public static int b(cz cz, int integer) {
		for (zd zd4 : cz.j().F()) {
			zd4.a(zd4.R() + (long)integer);
		}

		int integer3 = a(cz.e());
		cz.a(new ne("commands.time.set", integer3), true);
		return integer3;
	}
}
