import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class xt {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.whitelist.alreadyOn"));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.whitelist.alreadyOff"));
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("commands.whitelist.add.failed"));
	private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("commands.whitelist.remove.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("whitelist")
				.requires(cz -> cz.c(3))
				.then(da.a("on").executes(commandContext -> b(commandContext.getSource())))
				.then(da.a("off").executes(commandContext -> c(commandContext.getSource())))
				.then(da.a("list").executes(commandContext -> d(commandContext.getSource())))
				.then(da.a("add").then(da.a("targets", dj.a()).suggests((commandContext, suggestionsBuilder) -> {
					abp abp3 = commandContext.getSource().j().ac();
					return db.b(abp3.s().stream().filter(ze -> !abp3.i().a(ze.ez())).map(ze -> ze.ez().getName()), suggestionsBuilder);
				}).executes(commandContext -> a(commandContext.getSource(), dj.a(commandContext, "targets")))))
				.then(
					da.a("remove")
						.then(
							da.a("targets", dj.a())
								.suggests((commandContext, suggestionsBuilder) -> db.a(commandContext.getSource().j().ac().j(), suggestionsBuilder))
								.executes(commandContext -> b(commandContext.getSource(), dj.a(commandContext, "targets")))
						)
				)
				.then(da.a("reload").executes(commandContext -> a(commandContext.getSource())))
		);
	}

	private static int a(cz cz) {
		cz.j().ac().a();
		cz.a(new ne("commands.whitelist.reloaded"), true);
		cz.j().a(cz);
		return 1;
	}

	private static int a(cz cz, Collection<GameProfile> collection) throws CommandSyntaxException {
		abw abw3 = cz.j().ac().i();
		int integer4 = 0;

		for (GameProfile gameProfile6 : collection) {
			if (!abw3.a(gameProfile6)) {
				abx abx7 = new abx(gameProfile6);
				abw3.a(abx7);
				cz.a(new ne("commands.whitelist.add.success", ms.a(gameProfile6)), true);
				integer4++;
			}
		}

		if (integer4 == 0) {
			throw c.create();
		} else {
			return integer4;
		}
	}

	private static int b(cz cz, Collection<GameProfile> collection) throws CommandSyntaxException {
		abw abw3 = cz.j().ac().i();
		int integer4 = 0;

		for (GameProfile gameProfile6 : collection) {
			if (abw3.a(gameProfile6)) {
				abx abx7 = new abx(gameProfile6);
				abw3.b(abx7);
				cz.a(new ne("commands.whitelist.remove.success", ms.a(gameProfile6)), true);
				integer4++;
			}
		}

		if (integer4 == 0) {
			throw d.create();
		} else {
			cz.j().a(cz);
			return integer4;
		}
	}

	private static int b(cz cz) throws CommandSyntaxException {
		abp abp2 = cz.j().ac();
		if (abp2.o()) {
			throw a.create();
		} else {
			abp2.a(true);
			cz.a(new ne("commands.whitelist.enabled"), true);
			cz.j().a(cz);
			return 1;
		}
	}

	private static int c(cz cz) throws CommandSyntaxException {
		abp abp2 = cz.j().ac();
		if (!abp2.o()) {
			throw b.create();
		} else {
			abp2.a(false);
			cz.a(new ne("commands.whitelist.disabled"), true);
			return 1;
		}
	}

	private static int d(cz cz) {
		String[] arr2 = cz.j().ac().j();
		if (arr2.length == 0) {
			cz.a(new ne("commands.whitelist.none"), false);
		} else {
			cz.a(new ne("commands.whitelist.list", arr2.length, String.join(", ", arr2)), false);
		}

		return arr2.length;
	}
}
