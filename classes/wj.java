import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.UUID;

public class wj {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralCommandNode<cz> literalCommandNode2 = commandDispatcher.register(
			da.a("msg")
				.then(
					da.a("targets", dh.d())
						.then(da.a("message", dl.a()).executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), dl.a(commandContext, "message"))))
				)
		);
		commandDispatcher.register(da.a("tell").redirect(literalCommandNode2));
		commandDispatcher.register(da.a("w").redirect(literalCommandNode2));
	}

	private static int a(cz cz, Collection<ze> collection, mr mr) {
		UUID uUID4 = cz.f() == null ? v.b : cz.f().bR();

		for (ze ze6 : collection) {
			ze6.a(new ne("commands.message.display.incoming", cz.b(), mr).a(new i[]{i.GRAY, i.ITALIC}), uUID4);
			cz.a(new ne("commands.message.display.outgoing", ze6.d(), mr).a(new i[]{i.GRAY, i.ITALIC}), false);
		}

		return collection.size();
	}
}
