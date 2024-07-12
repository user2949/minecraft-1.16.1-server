import com.mojang.brigadier.CommandDispatcher;

public class xo {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(da.a("tellraw").requires(cz -> cz.c(2)).then(da.a("targets", dh.d()).then(da.a("message", dd.a()).executes(commandContext -> {
			int integer2 = 0;

			for (ze ze4 : dh.f(commandContext, "targets")) {
				ze4.a(ms.a(commandContext.getSource(), dd.a(commandContext, "message"), ze4, 0), v.b);
				integer2++;
			}

			return integer2;
		}))));
	}
}
