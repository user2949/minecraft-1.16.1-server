import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

public class wg {
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("commands.locatebiome.invalid", object));
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.locatebiome.notFound", object));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("locatebiome")
				.requires(cz -> cz.c(2))
				.then(da.a("biome", dv.a()).suggests(fj.d).executes(commandContext -> a(commandContext.getSource(), commandContext.getArgument("biome", uh.class))))
		);
	}

	private static int a(cz cz, uh uh) throws CommandSyntaxException {
		bre bre3 = (bre)gl.as.b(uh).orElseThrow(() -> a.create(uh));
		fu fu4 = new fu(cz.d());
		fu fu5 = cz.e().a(bre3, fu4, 6400, 8);
		String string6 = uh.toString();
		if (fu5 == null) {
			throw b.create(string6);
		} else {
			return wh.a(cz, string6, fu4, fu5, "commands.locatebiome.success");
		}
	}
}
