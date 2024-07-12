import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import javax.annotation.Nullable;

public class xf {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.spectate.self"));
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.spectate.not_spectator", object));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("spectate")
				.requires(cz -> cz.c(2))
				.executes(commandContext -> a(commandContext.getSource(), null, commandContext.getSource().h()))
				.then(
					da.a("target", dh.a())
						.executes(commandContext -> a(commandContext.getSource(), dh.a(commandContext, "target"), commandContext.getSource().h()))
						.then(da.a("player", dh.c()).executes(commandContext -> a(commandContext.getSource(), dh.a(commandContext, "target"), dh.e(commandContext, "player"))))
				)
		);
	}

	private static int a(cz cz, @Nullable aom aom, ze ze) throws CommandSyntaxException {
		if (ze == aom) {
			throw a.create();
		} else if (ze.d.b() != bpy.SPECTATOR) {
			throw b.create(ze.d());
		} else {
			ze.e(aom);
			if (aom != null) {
				cz.a(new ne("commands.spectate.success.started", aom.d()), false);
			} else {
				cz.a(new ne("commands.spectate.success.stopped"), false);
			}

			return 1;
		}
	}
}
