import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Map.Entry;

public class wh {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.locate.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralArgumentBuilder<cz> literalArgumentBuilder2 = da.a("locate").requires(cz -> cz.c(2));

		for (Entry<String, cml<?>> entry4 : cml.a.entrySet()) {
			literalArgumentBuilder2 = literalArgumentBuilder2.then(
				da.a((String)entry4.getKey()).executes(commandContext -> a(commandContext.getSource(), (cml<?>)entry4.getValue()))
			);
		}

		commandDispatcher.register(literalArgumentBuilder2);
	}

	private static int a(cz cz, cml<?> cml) throws CommandSyntaxException {
		fu fu3 = new fu(cz.d());
		fu fu4 = cz.e().a(cml, fu3, 100, false);
		if (fu4 == null) {
			throw a.create();
		} else {
			return a(cz, cml.i(), fu3, fu4, "commands.locate.success");
		}
	}

	public static int a(cz cz, String string2, fu fu3, fu fu4, String string5) {
		int integer6 = aec.d(a(fu3.u(), fu3.w(), fu4.u(), fu4.w()));
		mr mr7 = ms.a((mr)(new ne("chat.coordinates", fu4.u(), "~", fu4.w())))
			.a(nb -> nb.a(i.GREEN).a(new mp(mp.a.SUGGEST_COMMAND, "/tp @s " + fu4.u() + " ~ " + fu4.w())).a(new mv(mv.a.a, new ne("chat.coordinates.tooltip"))));
		cz.a(new ne(string5, string2, mr7, integer6), false);
		return integer6;
	}

	private static float a(int integer1, int integer2, int integer3, int integer4) {
		int integer5 = integer3 - integer1;
		int integer6 = integer4 - integer2;
		return aec.c((float)(integer5 * integer5 + integer6 * integer6));
	}
}
