import com.mojang.brigadier.CommandDispatcher;
import java.util.Collection;

public class wd {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("kick")
				.requires(cz -> cz.c(3))
				.then(
					da.a("targets", dh.d())
						.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), new ne("multiplayer.disconnect.kicked")))
						.then(da.a("reason", dl.a()).executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), dl.a(commandContext, "reason"))))
				)
		);
	}

	private static int a(cz cz, Collection<ze> collection, mr mr) {
		for (ze ze5 : collection) {
			ze5.b.b(mr);
			cz.a(new ne("commands.kick.success", ze5.d(), mr), true);
		}

		return collection.size();
	}
}
