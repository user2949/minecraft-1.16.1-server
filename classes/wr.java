import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;

public class wr {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.recipe.give.failed"));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.recipe.take.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("recipe")
				.requires(cz -> cz.c(2))
				.then(
					da.a("give")
						.then(
							da.a("targets", dh.d())
								.then(
									da.a("recipe", dv.a())
										.suggests(fj.b)
										.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), Collections.singleton(dv.b(commandContext, "recipe"))))
								)
								.then(da.a("*").executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), commandContext.getSource().j().aD().b())))
						)
				)
				.then(
					da.a("take")
						.then(
							da.a("targets", dh.d())
								.then(
									da.a("recipe", dv.a())
										.suggests(fj.b)
										.executes(commandContext -> b(commandContext.getSource(), dh.f(commandContext, "targets"), Collections.singleton(dv.b(commandContext, "recipe"))))
								)
								.then(da.a("*").executes(commandContext -> b(commandContext.getSource(), dh.f(commandContext, "targets"), commandContext.getSource().j().aD().b())))
						)
				)
		);
	}

	private static int a(cz cz, Collection<ze> collection2, Collection<bmu<?>> collection3) throws CommandSyntaxException {
		int integer4 = 0;

		for (ze ze6 : collection2) {
			integer4 += ze6.a(collection3);
		}

		if (integer4 == 0) {
			throw a.create();
		} else {
			if (collection2.size() == 1) {
				cz.a(new ne("commands.recipe.give.success.single", collection3.size(), ((ze)collection2.iterator().next()).d()), true);
			} else {
				cz.a(new ne("commands.recipe.give.success.multiple", collection3.size(), collection2.size()), true);
			}

			return integer4;
		}
	}

	private static int b(cz cz, Collection<ze> collection2, Collection<bmu<?>> collection3) throws CommandSyntaxException {
		int integer4 = 0;

		for (ze ze6 : collection2) {
			integer4 += ze6.b(collection3);
		}

		if (integer4 == 0) {
			throw b.create();
		} else {
			if (collection2.size() == 1) {
				cz.a(new ne("commands.recipe.take.success.single", collection3.size(), ((ze)collection2.iterator().next()).d()), true);
			} else {
				cz.a(new ne("commands.recipe.take.success.multiple", collection3.size(), collection2.size()), true);
			}

			return integer4;
		}
	}
}
