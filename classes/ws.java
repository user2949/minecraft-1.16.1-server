import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import java.util.Collection;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ws {
	private static final Logger a = LogManager.getLogger();

	public static void a(Collection<String> collection, cz cz) {
		cz.j().a(collection).exceptionally(throwable -> {
			a.warn("Failed to execute reload", throwable);
			cz.a(new ne("commands.reload.failure"));
			return null;
		});
	}

	private static Collection<String> a(aar<?> aar, dal dal, Collection<String> collection) {
		aar.a();
		Collection<String> collection4 = Lists.<String>newArrayList(collection);
		Collection<String> collection5 = dal.C().b();

		for (String string7 : aar.b()) {
			if (!collection5.contains(string7) && !collection4.contains(string7)) {
				collection4.add(string7);
			}
		}

		return collection4;
	}

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(da.a("reload").requires(cz -> cz.c(2)).executes(commandContext -> {
			cz cz2 = commandContext.getSource();
			MinecraftServer minecraftServer3 = cz2.j();
			aar<?> aar4 = minecraftServer3.aA();
			dal dal5 = minecraftServer3.aV();
			Collection<String> collection6 = aar4.d();
			Collection<String> collection7 = a(aar4, dal5, collection6);
			cz2.a(new ne("commands.reload.success"), true);
			a(collection7, cz2);
			return 0;
		}));
	}
}
