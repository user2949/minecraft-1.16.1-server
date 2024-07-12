import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.MinecraftServer;

public class wy {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.schedule.same_tick"));
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.schedule.cleared.failure", object));
	private static final SuggestionProvider<cz> c = (commandContext, suggestionsBuilder) -> db.b(
			commandContext.getSource().j().aV().G().t().a(), suggestionsBuilder
		);

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("schedule")
				.requires(cz -> cz.c(2))
				.then(
					da.a("function")
						.then(
							da.a("function", es.a())
								.suggests(vy.a)
								.then(
									da.a("time", ea.a())
										.executes(
											commandContext -> a(commandContext.getSource(), es.b(commandContext, "function"), IntegerArgumentType.getInteger(commandContext, "time"), true)
										)
										.then(
											da.a("append")
												.executes(
													commandContext -> a(commandContext.getSource(), es.b(commandContext, "function"), IntegerArgumentType.getInteger(commandContext, "time"), false)
												)
										)
										.then(
											da.a("replace")
												.executes(
													commandContext -> a(commandContext.getSource(), es.b(commandContext, "function"), IntegerArgumentType.getInteger(commandContext, "time"), true)
												)
										)
								)
						)
				)
				.then(
					da.a("clear")
						.then(
							da.a("function", StringArgumentType.greedyString())
								.suggests(c)
								.executes(commandContext -> a(commandContext.getSource(), StringArgumentType.getString(commandContext, "function")))
						)
				)
		);
	}

	private static int a(cz cz, Pair<uh, Either<cw, adf<cw>>> pair, int integer, boolean boolean4) throws CommandSyntaxException {
		if (integer == 0) {
			throw a.create();
		} else {
			long long5 = cz.e().Q() + (long)integer;
			uh uh7 = pair.getFirst();
			ded<MinecraftServer> ded8 = cz.j().aV().G().t();
			pair.getSecond().ifLeft(cw -> {
				String string9 = uh7.toString();
				if (boolean4) {
					ded8.a(string9);
				}

				ded8.a(string9, long5, new ddz(uh7));
				cz.a(new ne("commands.schedule.created.function", uh7, integer, long5), true);
			}).ifRight(adf -> {
				String string9 = "#" + uh7.toString();
				if (boolean4) {
					ded8.a(string9);
				}

				ded8.a(string9, long5, new dea(uh7));
				cz.a(new ne("commands.schedule.created.tag", uh7, integer, long5), true);
			});
			return (int)Math.floorMod(long5, 2147483647L);
		}
	}

	private static int a(cz cz, String string) throws CommandSyntaxException {
		int integer3 = cz.j().aV().G().t().a(string);
		if (integer3 == 0) {
			throw b.create(string);
		} else {
			cz.a(new ne("commands.schedule.cleared.success", integer3, string), true);
			return integer3;
		}
	}
}
