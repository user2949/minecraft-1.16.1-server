import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import java.util.Collection;

public class we {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("kill")
				.requires(cz -> cz.c(2))
				.executes(commandContext -> a(commandContext.getSource(), ImmutableList.of(commandContext.getSource().g())))
				.then(da.a("targets", dh.b()).executes(commandContext -> a(commandContext.getSource(), dh.b(commandContext, "targets"))))
		);
	}

	private static int a(cz cz, Collection<? extends aom> collection) {
		for (aom aom4 : collection) {
			aom4.X();
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.kill.success.single", ((aom)collection.iterator().next()).d()), true);
		} else {
			cz.a(new ne("commands.kill.success.multiple", collection.size()), true);
		}

		return collection.size();
	}
}
