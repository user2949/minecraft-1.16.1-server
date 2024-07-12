import com.mojang.brigadier.CommandDispatcher;
import java.util.List;
import java.util.function.Function;

public class wf {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("list").executes(commandContext -> a(commandContext.getSource())).then(da.a("uuids").executes(commandContext -> b(commandContext.getSource())))
		);
	}

	private static int a(cz cz) {
		return a(cz, bec::d);
	}

	private static int b(cz cz) {
		return a(cz, bec::eN);
	}

	private static int a(cz cz, Function<ze, mr> function) {
		abp abp3 = cz.j().ac();
		List<ze> list4 = abp3.s();
		mr mr5 = ms.b(list4, function);
		cz.a(new ne("commands.list.players", list4.size(), abp3.n(), mr5), false);
		return list4.size();
	}
}
