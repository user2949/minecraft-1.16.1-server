import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class ve {
	public static final Pattern a = Pattern.compile(
		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
	);
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.banip.invalid"));
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("commands.banip.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("ban-ip")
				.requires(cz -> cz.c(3))
				.then(
					da.a("target", StringArgumentType.word())
						.executes(commandContext -> a(commandContext.getSource(), StringArgumentType.getString(commandContext, "target"), null))
						.then(
							da.a("reason", dl.a())
								.executes(commandContext -> a(commandContext.getSource(), StringArgumentType.getString(commandContext, "target"), dl.a(commandContext, "reason")))
						)
				)
		);
	}

	private static int a(cz cz, String string, @Nullable mr mr) throws CommandSyntaxException {
		Matcher matcher4 = a.matcher(string);
		if (matcher4.matches()) {
			return b(cz, string, mr);
		} else {
			ze ze5 = cz.j().ac().a(string);
			if (ze5 != null) {
				return b(cz, ze5.v(), mr);
			} else {
				throw b.create();
			}
		}
	}

	private static int b(cz cz, String string, @Nullable mr mr) throws CommandSyntaxException {
		abm abm4 = cz.j().ac().g();
		if (abm4.a(string)) {
			throw c.create();
		} else {
			List<ze> list5 = cz.j().ac().b(string);
			abn abn6 = new abn(string, null, cz.c(), null, mr == null ? null : mr.getString());
			abm4.a(abn6);
			cz.a(new ne("commands.banip.success", string, abn6.d()), true);
			if (!list5.isEmpty()) {
				cz.a(new ne("commands.banip.info", list5.size(), ez.a(list5)), true);
			}

			for (ze ze8 : list5) {
				ze8.b.b(new ne("multiplayer.disconnect.ip_banned"));
			}

			return list5.size();
		}
	}
}
