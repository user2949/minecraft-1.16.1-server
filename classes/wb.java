import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;

public class wb {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("give")
				.requires(cz -> cz.c(2))
				.then(
					da.a("targets", dh.d())
						.then(
							da.a("item", et.a())
								.executes(commandContext -> a(commandContext.getSource(), et.a(commandContext, "item"), dh.f(commandContext, "targets"), 1))
								.then(
									da.a("count", IntegerArgumentType.integer(1))
										.executes(
											commandContext -> a(
													commandContext.getSource(), et.a(commandContext, "item"), dh.f(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "count")
												)
										)
								)
						)
				)
		);
	}

	private static int a(cz cz, eu eu, Collection<ze> collection, int integer) throws CommandSyntaxException {
		for (ze ze6 : collection) {
			int integer7 = integer;

			while (integer7 > 0) {
				int integer8 = Math.min(eu.a().i(), integer7);
				integer7 -= integer8;
				bki bki9 = eu.a(integer8, false);
				boolean boolean10 = ze6.bt.e(bki9);
				if (boolean10 && bki9.a()) {
					bki9.e(1);
					bbg bbg11 = ze6.a(bki9, false);
					if (bbg11 != null) {
						bbg11.s();
					}

					ze6.l.a(null, ze6.cC(), ze6.cD(), ze6.cG(), acl.gL, acm.PLAYERS, 0.2F, ((ze6.cX().nextFloat() - ze6.cX().nextFloat()) * 0.7F + 1.0F) * 2.0F);
					ze6.bv.c();
				} else {
					bbg bbg11 = ze6.a(bki9, false);
					if (bbg11 != null) {
						bbg11.n();
						bbg11.b(ze6.bR());
					}
				}
			}
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.give.success.single", integer, eu.a(integer, false).C(), ((ze)collection.iterator().next()).d()), true);
		} else {
			cz.a(new ne("commands.give.success.single", integer, eu.a(integer, false).C(), collection.size()), true);
		}

		return collection.size();
	}
}
