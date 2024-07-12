import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.MinecraftServer;

public class wu {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.save.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("save-all")
				.requires(cz -> cz.c(4))
				.executes(commandContext -> a(commandContext.getSource(), false))
				.then(da.a("flush").executes(commandContext -> a(commandContext.getSource(), true)))
		);
	}

	private static int a(cz cz, boolean boolean2) throws CommandSyntaxException {
		cz.a(new ne("commands.save.saving"), false);
		MinecraftServer minecraftServer3 = cz.j();
		minecraftServer3.ac().h();
		boolean boolean4 = minecraftServer3.a(true, boolean2, true);
		if (!boolean4) {
			throw a.create();
		} else {
			cz.a(new ne("commands.save.success"), true);
			return 1;
		}
	}
}
