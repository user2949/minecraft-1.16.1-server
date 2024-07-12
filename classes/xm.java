import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;

public class xm {
	private static final nb a = nb.b.a(new mv(mv.a.a, new ne("chat.type.team.hover"))).a(new mp(mp.a.SUGGEST_COMMAND, "/teammsg "));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.teammsg.failed.noteam"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralCommandNode<cz> literalCommandNode2 = commandDispatcher.register(
			da.a("teammsg").then(da.a("message", dl.a()).executes(commandContext -> a(commandContext.getSource(), dl.a(commandContext, "message"))))
		);
		commandDispatcher.register(da.a("tm").redirect(literalCommandNode2));
	}

	private static int a(cz cz, mr mr) throws CommandSyntaxException {
		aom aom3 = cz.g();
		dfk dfk4 = (dfk)aom3.bC();
		if (dfk4 == null) {
			throw b.create();
		} else {
			mr mr5 = dfk4.d().c(a);
			List<ze> list6 = cz.j().ac().s();

			for (ze ze8 : list6) {
				if (ze8 == aom3) {
					ze8.a(new ne("chat.type.team.sent", mr5, cz.b(), mr), aom3.bR());
				} else if (ze8.bC() == dfk4) {
					ze8.a(new ne("chat.type.team.text", mr5, cz.b(), mr), aom3.bR());
				}
			}

			return list6.size();
		}
	}
}
