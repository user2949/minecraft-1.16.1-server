import com.mojang.brigadier.CommandDispatcher;

public class xa {
	public static void a(CommandDispatcher<cz> commandDispatcher, boolean boolean2) {
		commandDispatcher.register(
			da.a("seed")
				.requires(cz -> !boolean2 || cz.c(2))
				.executes(
					commandContext -> {
						long long2 = commandContext.getSource().e().B();
						mr mr4 = ms.a(
							(mr)new nd(String.valueOf(long2))
								.a(nb -> nb.a(i.GREEN).a(new mp(mp.a.COPY_TO_CLIPBOARD, String.valueOf(long2))).a(new mv(mv.a.a, new ne("chat.copy.click"))).a(String.valueOf(long2)))
						);
						commandContext.getSource().a(new ne("commands.seed.success", mr4), false);
						return (int)long2;
					}
				)
		);
	}
}
