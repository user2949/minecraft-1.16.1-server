import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Locale;

public class xq {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("title")
				.requires(cz -> cz.c(2))
				.then(
					da.a("targets", dh.d())
						.then(da.a("clear").executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"))))
						.then(da.a("reset").executes(commandContext -> b(commandContext.getSource(), dh.f(commandContext, "targets"))))
						.then(
							da.a("title")
								.then(
									da.a("title", dd.a())
										.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), dd.a(commandContext, "title"), qk.a.TITLE))
								)
						)
						.then(
							da.a("subtitle")
								.then(
									da.a("title", dd.a())
										.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), dd.a(commandContext, "title"), qk.a.SUBTITLE))
								)
						)
						.then(
							da.a("actionbar")
								.then(
									da.a("title", dd.a())
										.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), dd.a(commandContext, "title"), qk.a.ACTIONBAR))
								)
						)
						.then(
							da.a("times")
								.then(
									da.a("fadeIn", IntegerArgumentType.integer(0))
										.then(
											da.a("stay", IntegerArgumentType.integer(0))
												.then(
													da.a("fadeOut", IntegerArgumentType.integer(0))
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	dh.f(commandContext, "targets"),
																	IntegerArgumentType.getInteger(commandContext, "fadeIn"),
																	IntegerArgumentType.getInteger(commandContext, "stay"),
																	IntegerArgumentType.getInteger(commandContext, "fadeOut")
																)
														)
												)
										)
								)
						)
				)
		);
	}

	private static int a(cz cz, Collection<ze> collection) {
		qk qk3 = new qk(qk.a.CLEAR, null);

		for (ze ze5 : collection) {
			ze5.b.a(qk3);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.title.cleared.single", ((ze)collection.iterator().next()).d()), true);
		} else {
			cz.a(new ne("commands.title.cleared.multiple", collection.size()), true);
		}

		return collection.size();
	}

	private static int b(cz cz, Collection<ze> collection) {
		qk qk3 = new qk(qk.a.RESET, null);

		for (ze ze5 : collection) {
			ze5.b.a(qk3);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.title.reset.single", ((ze)collection.iterator().next()).d()), true);
		} else {
			cz.a(new ne("commands.title.reset.multiple", collection.size()), true);
		}

		return collection.size();
	}

	private static int a(cz cz, Collection<ze> collection, mr mr, qk.a a) throws CommandSyntaxException {
		for (ze ze6 : collection) {
			ze6.b.a(new qk(a, ms.a(cz, mr, ze6, 0)));
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.title.show." + a.name().toLowerCase(Locale.ROOT) + ".single", ((ze)collection.iterator().next()).d()), true);
		} else {
			cz.a(new ne("commands.title.show." + a.name().toLowerCase(Locale.ROOT) + ".multiple", collection.size()), true);
		}

		return collection.size();
	}

	private static int a(cz cz, Collection<ze> collection, int integer3, int integer4, int integer5) {
		qk qk6 = new qk(integer3, integer4, integer5);

		for (ze ze8 : collection) {
			ze8.b.a(qk6);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.title.times.single", ((ze)collection.iterator().next()).d()), true);
		} else {
			cz.a(new ne("commands.title.times.multiple", collection.size()), true);
		}

		return collection.size();
	}
}
