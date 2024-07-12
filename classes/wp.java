import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class wp {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.publish.failed"));
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.publish.alreadyPublished", object));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("publish")
				.requires(cz -> cz.c(4))
				.executes(commandContext -> a(commandContext.getSource(), adv.a()))
				.then(
					da.a("port", IntegerArgumentType.integer(0, 65535))
						.executes(commandContext -> a(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "port")))
				)
		);
	}

	private static int a(cz cz, int integer) throws CommandSyntaxException {
		if (cz.j().m()) {
			throw b.create(cz.j().L());
		} else if (!cz.j().a(cz.j().r(), false, integer)) {
			throw a.create();
		} else {
			cz.a(new ne("commands.publish.success", integer), true);
			return integer;
		}
	}
}
