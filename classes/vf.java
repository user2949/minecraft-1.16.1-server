import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import java.util.Collection;

public class vf {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("banlist")
				.requires(cz -> cz.c(3))
				.executes(commandContext -> {
					abp abp2 = commandContext.getSource().j().ac();
					return a(commandContext.getSource(), Lists.newArrayList(Iterables.concat(abp2.f().d(), abp2.g().d())));
				})
				.then(da.a("ips").executes(commandContext -> a(commandContext.getSource(), commandContext.getSource().j().ac().g().d())))
				.then(da.a("players").executes(commandContext -> a(commandContext.getSource(), commandContext.getSource().j().ac().f().d())))
		);
	}

	private static int a(cz cz, Collection<? extends abk<?>> collection) {
		if (collection.isEmpty()) {
			cz.a(new ne("commands.banlist.none"), false);
		} else {
			cz.a(new ne("commands.banlist.list", collection.size()), false);

			for (abk<?> abk4 : collection) {
				cz.a(new ne("commands.banlist.entry", abk4.e(), abk4.b(), abk4.d()), false);
			}
		}

		return collection.size();
	}
}
