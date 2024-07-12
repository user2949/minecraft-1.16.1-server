import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.Collections;

public class vh {
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.bossbar.create.failed", object));
	private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> new ne("commands.bossbar.unknown", object));
	private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("commands.bossbar.set.players.unchanged"));
	private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ne("commands.bossbar.set.name.unchanged"));
	private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new ne("commands.bossbar.set.color.unchanged"));
	private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new ne("commands.bossbar.set.style.unchanged"));
	private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(new ne("commands.bossbar.set.value.unchanged"));
	private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(new ne("commands.bossbar.set.max.unchanged"));
	private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(new ne("commands.bossbar.set.visibility.unchanged.hidden"));
	private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(new ne("commands.bossbar.set.visibility.unchanged.visible"));
	public static final SuggestionProvider<cz> a = (commandContext, suggestionsBuilder) -> db.a(commandContext.getSource().j().aK().a(), suggestionsBuilder);

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("bossbar")
				.requires(cz -> cz.c(2))
				.then(
					da.a("add")
						.then(
							da.a("id", dv.a())
								.then(da.a("name", dd.a()).executes(commandContext -> a(commandContext.getSource(), dv.e(commandContext, "id"), dd.a(commandContext, "name"))))
						)
				)
				.then(da.a("remove").then(da.a("id", dv.a()).suggests(a).executes(commandContext -> e(commandContext.getSource(), a(commandContext)))))
				.then(da.a("list").executes(commandContext -> a(commandContext.getSource())))
				.then(
					da.a("set")
						.then(
							da.a("id", dv.a())
								.suggests(a)
								.then(
									da.a("name").then(da.a("name", dd.a()).executes(commandContext -> a(commandContext.getSource(), a(commandContext), dd.a(commandContext, "name"))))
								)
								.then(
									da.a("color")
										.then(da.a("pink").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.a.PINK)))
										.then(da.a("blue").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.a.BLUE)))
										.then(da.a("red").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.a.RED)))
										.then(da.a("green").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.a.GREEN)))
										.then(da.a("yellow").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.a.YELLOW)))
										.then(da.a("purple").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.a.PURPLE)))
										.then(da.a("white").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.a.WHITE)))
								)
								.then(
									da.a("style")
										.then(da.a("progress").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.b.PROGRESS)))
										.then(da.a("notched_6").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.b.NOTCHED_6)))
										.then(da.a("notched_10").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.b.NOTCHED_10)))
										.then(da.a("notched_12").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.b.NOTCHED_12)))
										.then(da.a("notched_20").executes(commandContext -> a(commandContext.getSource(), a(commandContext), amw.b.NOTCHED_20)))
								)
								.then(
									da.a("value")
										.then(
											da.a("value", IntegerArgumentType.integer(0))
												.executes(commandContext -> a(commandContext.getSource(), a(commandContext), IntegerArgumentType.getInteger(commandContext, "value")))
										)
								)
								.then(
									da.a("max")
										.then(
											da.a("max", IntegerArgumentType.integer(1))
												.executes(commandContext -> b(commandContext.getSource(), a(commandContext), IntegerArgumentType.getInteger(commandContext, "max")))
										)
								)
								.then(
									da.a("visible")
										.then(
											da.a("visible", BoolArgumentType.bool())
												.executes(commandContext -> a(commandContext.getSource(), a(commandContext), BoolArgumentType.getBool(commandContext, "visible")))
										)
								)
								.then(
									da.a("players")
										.executes(commandContext -> a(commandContext.getSource(), a(commandContext), Collections.emptyList()))
										.then(da.a("targets", dh.d()).executes(commandContext -> a(commandContext.getSource(), a(commandContext), dh.d(commandContext, "targets"))))
								)
						)
				)
				.then(
					da.a("get")
						.then(
							da.a("id", dv.a())
								.suggests(a)
								.then(da.a("value").executes(commandContext -> a(commandContext.getSource(), a(commandContext))))
								.then(da.a("max").executes(commandContext -> b(commandContext.getSource(), a(commandContext))))
								.then(da.a("visible").executes(commandContext -> c(commandContext.getSource(), a(commandContext))))
								.then(da.a("players").executes(commandContext -> d(commandContext.getSource(), a(commandContext))))
						)
				)
		);
	}

	private static int a(cz cz, uz uz) {
		cz.a(new ne("commands.bossbar.get.value", uz.e(), uz.c()), true);
		return uz.c();
	}

	private static int b(cz cz, uz uz) {
		cz.a(new ne("commands.bossbar.get.max", uz.e(), uz.d()), true);
		return uz.d();
	}

	private static int c(cz cz, uz uz) {
		if (uz.g()) {
			cz.a(new ne("commands.bossbar.get.visible.visible", uz.e()), true);
			return 1;
		} else {
			cz.a(new ne("commands.bossbar.get.visible.hidden", uz.e()), true);
			return 0;
		}
	}

	private static int d(cz cz, uz uz) {
		if (uz.h().isEmpty()) {
			cz.a(new ne("commands.bossbar.get.players.none", uz.e()), true);
		} else {
			cz.a(new ne("commands.bossbar.get.players.some", uz.e(), uz.h().size(), ms.b(uz.h(), bec::d)), true);
		}

		return uz.h().size();
	}

	private static int a(cz cz, uz uz, boolean boolean3) throws CommandSyntaxException {
		if (uz.g() == boolean3) {
			if (boolean3) {
				throw k.create();
			} else {
				throw j.create();
			}
		} else {
			uz.d(boolean3);
			if (boolean3) {
				cz.a(new ne("commands.bossbar.set.visible.success.visible", uz.e()), true);
			} else {
				cz.a(new ne("commands.bossbar.set.visible.success.hidden", uz.e()), true);
			}

			return 0;
		}
	}

	private static int a(cz cz, uz uz, int integer) throws CommandSyntaxException {
		if (uz.c() == integer) {
			throw h.create();
		} else {
			uz.a(integer);
			cz.a(new ne("commands.bossbar.set.value.success", uz.e(), integer), true);
			return integer;
		}
	}

	private static int b(cz cz, uz uz, int integer) throws CommandSyntaxException {
		if (uz.d() == integer) {
			throw i.create();
		} else {
			uz.b(integer);
			cz.a(new ne("commands.bossbar.set.max.success", uz.e(), integer), true);
			return integer;
		}
	}

	private static int a(cz cz, uz uz, amw.a a) throws CommandSyntaxException {
		if (uz.l().equals(a)) {
			throw f.create();
		} else {
			uz.a(a);
			cz.a(new ne("commands.bossbar.set.color.success", uz.e()), true);
			return 0;
		}
	}

	private static int a(cz cz, uz uz, amw.b b) throws CommandSyntaxException {
		if (uz.m().equals(b)) {
			throw g.create();
		} else {
			uz.a(b);
			cz.a(new ne("commands.bossbar.set.style.success", uz.e()), true);
			return 0;
		}
	}

	private static int a(cz cz, uz uz, mr mr) throws CommandSyntaxException {
		mr mr4 = ms.a(cz, mr, null, 0);
		if (uz.j().equals(mr4)) {
			throw e.create();
		} else {
			uz.a(mr4);
			cz.a(new ne("commands.bossbar.set.name.success", uz.e()), true);
			return 0;
		}
	}

	private static int a(cz cz, uz uz, Collection<ze> collection) throws CommandSyntaxException {
		boolean boolean4 = uz.a(collection);
		if (!boolean4) {
			throw d.create();
		} else {
			if (uz.h().isEmpty()) {
				cz.a(new ne("commands.bossbar.set.players.success.none", uz.e()), true);
			} else {
				cz.a(new ne("commands.bossbar.set.players.success.some", uz.e(), collection.size(), ms.b(collection, bec::d)), true);
			}

			return uz.h().size();
		}
	}

	private static int a(cz cz) {
		Collection<uz> collection2 = cz.j().aK().b();
		if (collection2.isEmpty()) {
			cz.a(new ne("commands.bossbar.list.bars.none"), false);
		} else {
			cz.a(new ne("commands.bossbar.list.bars.some", collection2.size(), ms.b(collection2, uz::e)), false);
		}

		return collection2.size();
	}

	private static int a(cz cz, uh uh, mr mr) throws CommandSyntaxException {
		va va4 = cz.j().aK();
		if (va4.a(uh) != null) {
			throw b.create(uh.toString());
		} else {
			uz uz5 = va4.a(uh, ms.a(cz, mr, null, 0));
			cz.a(new ne("commands.bossbar.create.success", uz5.e()), true);
			return va4.b().size();
		}
	}

	private static int e(cz cz, uz uz) {
		va va3 = cz.j().aK();
		uz.b();
		va3.a(uz);
		cz.a(new ne("commands.bossbar.remove.success", uz.e()), true);
		return va3.b().size();
	}

	public static uz a(CommandContext<cz> commandContext) throws CommandSyntaxException {
		uh uh2 = dv.e(commandContext, "id");
		uz uz3 = commandContext.getSource().j().aK().a(uh2);
		if (uz3 == null) {
			throw c.create(uh2.toString());
		} else {
			return uz3;
		}
	}
}
