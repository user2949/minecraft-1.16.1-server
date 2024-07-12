import com.mojang.brigadier.CommandDispatcher;
import java.util.Collection;
import java.util.Collections;

public class xd {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("spawnpoint")
				.requires(cz -> cz.c(2))
				.executes(commandContext -> a(commandContext.getSource(), Collections.singleton(commandContext.getSource().h()), new fu(commandContext.getSource().d())))
				.then(
					da.a("targets", dh.d())
						.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), new fu(commandContext.getSource().d())))
						.then(da.a("pos", eh.a()).executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), eh.b(commandContext, "pos"))))
				)
		);
	}

	private static int a(cz cz, Collection<ze> collection, fu fu) {
		ug<bqb> ug4 = cz.e().W();

		for (ze ze6 : collection) {
			ze6.a(ug4, fu, true, false);
		}

		String string5 = ug4.a().toString();
		if (collection.size() == 1) {
			cz.a(new ne("commands.spawnpoint.success.single", fu.u(), fu.v(), fu.w(), string5, ((ze)collection.iterator().next()).d()), true);
		} else {
			cz.a(new ne("commands.spawnpoint.success.multiple", fu.u(), fu.v(), fu.w(), string5, collection.size()), true);
		}

		return collection.size();
	}
}
