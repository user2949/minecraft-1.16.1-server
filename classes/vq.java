import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.server.MinecraftServer;

public class vq {
	private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("commands.difficulty.failure", object));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralArgumentBuilder<cz> literalArgumentBuilder2 = da.a("difficulty");

		for (and and6 : and.values()) {
			literalArgumentBuilder2.then(da.a(and6.c()).executes(commandContext -> a(commandContext.getSource(), and6)));
		}

		commandDispatcher.register(literalArgumentBuilder2.requires(cz -> cz.c(2)).executes(commandContext -> {
			and and2 = commandContext.getSource().e().ac();
			commandContext.getSource().a(new ne("commands.difficulty.query", and2.b()), false);
			return and2.a();
		}));
	}

	public static int a(cz cz, and and) throws CommandSyntaxException {
		MinecraftServer minecraftServer3 = cz.j();
		if (minecraftServer3.aV().r() == and) {
			throw a.create(and.c());
		} else {
			minecraftServer3.a(and, true);
			cz.a(new ne("commands.difficulty.success", and.b()), true);
			return 0;
		}
	}
}
