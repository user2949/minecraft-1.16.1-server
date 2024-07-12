import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

public class vi {
	private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("clear.failed.single", object));
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("clear.failed.multiple", object));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("clear")
				.requires(cz -> cz.c(2))
				.executes(commandContext -> a(commandContext.getSource(), Collections.singleton(commandContext.getSource().h()), bki -> true, -1))
				.then(
					da.a("targets", dh.d())
						.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), bki -> true, -1))
						.then(
							da.a("item", ew.a())
								.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), ew.a(commandContext, "item"), -1))
								.then(
									da.a("maxCount", IntegerArgumentType.integer(0))
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													dh.f(commandContext, "targets"),
													ew.a(commandContext, "item"),
													IntegerArgumentType.getInteger(commandContext, "maxCount")
												)
										)
								)
						)
				)
		);
	}

	private static int a(cz cz, Collection<ze> collection, Predicate<bki> predicate, int integer) throws CommandSyntaxException {
		int integer5 = 0;

		for (ze ze7 : collection) {
			integer5 += ze7.bt.a(predicate, integer, ze7.bv.j());
			ze7.bw.c();
			ze7.bv.a(ze7.bt);
			ze7.n();
		}

		if (integer5 == 0) {
			if (collection.size() == 1) {
				throw a.create(((ze)collection.iterator().next()).P());
			} else {
				throw b.create(collection.size());
			}
		} else {
			if (integer == 0) {
				if (collection.size() == 1) {
					cz.a(new ne("commands.clear.test.single", integer5, ((ze)collection.iterator().next()).d()), true);
				} else {
					cz.a(new ne("commands.clear.test.multiple", integer5, collection.size()), true);
				}
			} else if (collection.size() == 1) {
				cz.a(new ne("commands.clear.success.single", integer5, ((ze)collection.iterator().next()).d()), true);
			} else {
				cz.a(new ne("commands.clear.success.multiple", integer5, collection.size()), true);
			}

			return integer5;
		}
	}
}
