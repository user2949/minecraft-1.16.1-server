import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

public class wz {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.scoreboard.objectives.add.duplicate"));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.scoreboard.objectives.display.alreadyEmpty"));
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("commands.scoreboard.objectives.display.alreadySet"));
	private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("commands.scoreboard.players.enable.failed"));
	private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ne("commands.scoreboard.players.enable.invalid"));
	private static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("commands.scoreboard.players.get.null", object1, object2)
	);

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("scoreboard")
				.requires(cz -> cz.c(2))
				.then(
					da.a("objectives")
						.then(da.a("list").executes(commandContext -> b(commandContext.getSource())))
						.then(
							da.a("add")
								.then(
									da.a("objective", StringArgumentType.word())
										.then(
											da.a("criteria", dr.a())
												.executes(
													commandContext -> a(
															commandContext.getSource(),
															StringArgumentType.getString(commandContext, "objective"),
															dr.a(commandContext, "criteria"),
															new nd(StringArgumentType.getString(commandContext, "objective"))
														)
												)
												.then(
													da.a("displayName", dd.a())
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	StringArgumentType.getString(commandContext, "objective"),
																	dr.a(commandContext, "criteria"),
																	dd.a(commandContext, "displayName")
																)
														)
												)
										)
								)
						)
						.then(
							da.a("modify")
								.then(
									da.a("objective", dq.a())
										.then(
											da.a("displayname")
												.then(
													da.a("displayName", dd.a())
														.executes(commandContext -> a(commandContext.getSource(), dq.a(commandContext, "objective"), dd.a(commandContext, "displayName")))
												)
										)
										.then(a())
								)
						)
						.then(da.a("remove").then(da.a("objective", dq.a()).executes(commandContext -> a(commandContext.getSource(), dq.a(commandContext, "objective")))))
						.then(
							da.a("setdisplay")
								.then(
									da.a("slot", dx.a())
										.executes(commandContext -> a(commandContext.getSource(), dx.a(commandContext, "slot")))
										.then(
											da.a("objective", dq.a()).executes(commandContext -> a(commandContext.getSource(), dx.a(commandContext, "slot"), dq.a(commandContext, "objective")))
										)
								)
						)
				)
				.then(
					da.a("players")
						.then(
							da.a("list")
								.executes(commandContext -> a(commandContext.getSource()))
								.then(da.a("target", dw.a()).suggests(dw.a).executes(commandContext -> a(commandContext.getSource(), dw.a(commandContext, "target"))))
						)
						.then(
							da.a("set")
								.then(
									da.a("targets", dw.b())
										.suggests(dw.a)
										.then(
											da.a("objective", dq.a())
												.then(
													da.a("score", IntegerArgumentType.integer())
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	dw.c(commandContext, "targets"),
																	dq.b(commandContext, "objective"),
																	IntegerArgumentType.getInteger(commandContext, "score")
																)
														)
												)
										)
								)
						)
						.then(
							da.a("get")
								.then(
									da.a("target", dw.a())
										.suggests(dw.a)
										.then(
											da.a("objective", dq.a())
												.executes(commandContext -> a(commandContext.getSource(), dw.a(commandContext, "target"), dq.a(commandContext, "objective")))
										)
								)
						)
						.then(
							da.a("add")
								.then(
									da.a("targets", dw.b())
										.suggests(dw.a)
										.then(
											da.a("objective", dq.a())
												.then(
													da.a("score", IntegerArgumentType.integer(0))
														.executes(
															commandContext -> b(
																	commandContext.getSource(),
																	dw.c(commandContext, "targets"),
																	dq.b(commandContext, "objective"),
																	IntegerArgumentType.getInteger(commandContext, "score")
																)
														)
												)
										)
								)
						)
						.then(
							da.a("remove")
								.then(
									da.a("targets", dw.b())
										.suggests(dw.a)
										.then(
											da.a("objective", dq.a())
												.then(
													da.a("score", IntegerArgumentType.integer(0))
														.executes(
															commandContext -> c(
																	commandContext.getSource(),
																	dw.c(commandContext, "targets"),
																	dq.b(commandContext, "objective"),
																	IntegerArgumentType.getInteger(commandContext, "score")
																)
														)
												)
										)
								)
						)
						.then(
							da.a("reset")
								.then(
									da.a("targets", dw.b())
										.suggests(dw.a)
										.executes(commandContext -> a(commandContext.getSource(), dw.c(commandContext, "targets")))
										.then(
											da.a("objective", dq.a())
												.executes(commandContext -> b(commandContext.getSource(), dw.c(commandContext, "targets"), dq.a(commandContext, "objective")))
										)
								)
						)
						.then(
							da.a("enable")
								.then(
									da.a("targets", dw.b())
										.suggests(dw.a)
										.then(
											da.a("objective", dq.a())
												.suggests((commandContext, suggestionsBuilder) -> a(commandContext.getSource(), dw.c(commandContext, "targets"), suggestionsBuilder))
												.executes(commandContext -> a(commandContext.getSource(), dw.c(commandContext, "targets"), dq.a(commandContext, "objective")))
										)
								)
						)
						.then(
							da.a("operation")
								.then(
									da.a("targets", dw.b())
										.suggests(dw.a)
										.then(
											da.a("targetObjective", dq.a())
												.then(
													da.a("operation", ds.a())
														.then(
															da.a("source", dw.b())
																.suggests(dw.a)
																.then(
																	da.a("sourceObjective", dq.a())
																		.executes(
																			commandContext -> a(
																					commandContext.getSource(),
																					dw.c(commandContext, "targets"),
																					dq.b(commandContext, "targetObjective"),
																					ds.a(commandContext, "operation"),
																					dw.c(commandContext, "source"),
																					dq.a(commandContext, "sourceObjective")
																				)
																		)
																)
														)
												)
										)
								)
						)
				)
		);
	}

	private static LiteralArgumentBuilder<cz> a() {
		LiteralArgumentBuilder<cz> literalArgumentBuilder1 = da.a("rendertype");

		for (dfp.a a5 : dfp.a.values()) {
			literalArgumentBuilder1.then(da.a(a5.a()).executes(commandContext -> a(commandContext.getSource(), dq.a(commandContext, "objective"), a5)));
		}

		return literalArgumentBuilder1;
	}

	private static CompletableFuture<Suggestions> a(cz cz, Collection<String> collection, SuggestionsBuilder suggestionsBuilder) {
		List<String> list4 = Lists.<String>newArrayList();
		dfm dfm5 = cz.j().aF();

		for (dfj dfj7 : dfm5.c()) {
			if (dfj7.c() == dfp.c) {
				boolean boolean8 = false;

				for (String string10 : collection) {
					if (!dfm5.b(string10, dfj7) || dfm5.c(string10, dfj7).g()) {
						boolean8 = true;
						break;
					}
				}

				if (boolean8) {
					list4.add(dfj7.b());
				}
			}
		}

		return db.b(list4, suggestionsBuilder);
	}

	private static int a(cz cz, String string, dfj dfj) throws CommandSyntaxException {
		dfm dfm4 = cz.j().aF();
		if (!dfm4.b(string, dfj)) {
			throw f.create(dfj.b(), string);
		} else {
			dfl dfl5 = dfm4.c(string, dfj);
			cz.a(new ne("commands.scoreboard.players.get.success", string, dfl5.b(), dfj.e()), false);
			return dfl5.b();
		}
	}

	private static int a(cz cz, Collection<String> collection2, dfj dfj3, ds.a a, Collection<String> collection5, dfj dfj6) throws CommandSyntaxException {
		dfm dfm7 = cz.j().aF();
		int integer8 = 0;

		for (String string10 : collection2) {
			dfl dfl11 = dfm7.c(string10, dfj3);

			for (String string13 : collection5) {
				dfl dfl14 = dfm7.c(string13, dfj6);
				a.apply(dfl11, dfl14);
			}

			integer8 += dfl11.b();
		}

		if (collection2.size() == 1) {
			cz.a(new ne("commands.scoreboard.players.operation.success.single", dfj3.e(), collection2.iterator().next(), integer8), true);
		} else {
			cz.a(new ne("commands.scoreboard.players.operation.success.multiple", dfj3.e(), collection2.size()), true);
		}

		return integer8;
	}

	private static int a(cz cz, Collection<String> collection, dfj dfj) throws CommandSyntaxException {
		if (dfj.c() != dfp.c) {
			throw e.create();
		} else {
			dfm dfm4 = cz.j().aF();
			int integer5 = 0;

			for (String string7 : collection) {
				dfl dfl8 = dfm4.c(string7, dfj);
				if (dfl8.g()) {
					dfl8.a(false);
					integer5++;
				}
			}

			if (integer5 == 0) {
				throw d.create();
			} else {
				if (collection.size() == 1) {
					cz.a(new ne("commands.scoreboard.players.enable.success.single", dfj.e(), collection.iterator().next()), true);
				} else {
					cz.a(new ne("commands.scoreboard.players.enable.success.multiple", dfj.e(), collection.size()), true);
				}

				return integer5;
			}
		}
	}

	private static int a(cz cz, Collection<String> collection) {
		dfm dfm3 = cz.j().aF();

		for (String string5 : collection) {
			dfm3.d(string5, null);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.scoreboard.players.reset.all.single", collection.iterator().next()), true);
		} else {
			cz.a(new ne("commands.scoreboard.players.reset.all.multiple", collection.size()), true);
		}

		return collection.size();
	}

	private static int b(cz cz, Collection<String> collection, dfj dfj) {
		dfm dfm4 = cz.j().aF();

		for (String string6 : collection) {
			dfm4.d(string6, dfj);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.scoreboard.players.reset.specific.single", dfj.e(), collection.iterator().next()), true);
		} else {
			cz.a(new ne("commands.scoreboard.players.reset.specific.multiple", dfj.e(), collection.size()), true);
		}

		return collection.size();
	}

	private static int a(cz cz, Collection<String> collection, dfj dfj, int integer) {
		dfm dfm5 = cz.j().aF();

		for (String string7 : collection) {
			dfl dfl8 = dfm5.c(string7, dfj);
			dfl8.c(integer);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.scoreboard.players.set.success.single", dfj.e(), collection.iterator().next(), integer), true);
		} else {
			cz.a(new ne("commands.scoreboard.players.set.success.multiple", dfj.e(), collection.size(), integer), true);
		}

		return integer * collection.size();
	}

	private static int b(cz cz, Collection<String> collection, dfj dfj, int integer) {
		dfm dfm5 = cz.j().aF();
		int integer6 = 0;

		for (String string8 : collection) {
			dfl dfl9 = dfm5.c(string8, dfj);
			dfl9.c(dfl9.b() + integer);
			integer6 += dfl9.b();
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.scoreboard.players.add.success.single", integer, dfj.e(), collection.iterator().next(), integer6), true);
		} else {
			cz.a(new ne("commands.scoreboard.players.add.success.multiple", integer, dfj.e(), collection.size()), true);
		}

		return integer6;
	}

	private static int c(cz cz, Collection<String> collection, dfj dfj, int integer) {
		dfm dfm5 = cz.j().aF();
		int integer6 = 0;

		for (String string8 : collection) {
			dfl dfl9 = dfm5.c(string8, dfj);
			dfl9.c(dfl9.b() - integer);
			integer6 += dfl9.b();
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.scoreboard.players.remove.success.single", integer, dfj.e(), collection.iterator().next(), integer6), true);
		} else {
			cz.a(new ne("commands.scoreboard.players.remove.success.multiple", integer, dfj.e(), collection.size()), true);
		}

		return integer6;
	}

	private static int a(cz cz) {
		Collection<String> collection2 = cz.j().aF().e();
		if (collection2.isEmpty()) {
			cz.a(new ne("commands.scoreboard.players.list.empty"), false);
		} else {
			cz.a(new ne("commands.scoreboard.players.list.success", collection2.size(), ms.a(collection2)), false);
		}

		return collection2.size();
	}

	private static int a(cz cz, String string) {
		Map<dfj, dfl> map3 = cz.j().aF().e(string);
		if (map3.isEmpty()) {
			cz.a(new ne("commands.scoreboard.players.list.entity.empty", string), false);
		} else {
			cz.a(new ne("commands.scoreboard.players.list.entity.success", string, map3.size()), false);

			for (Entry<dfj, dfl> entry5 : map3.entrySet()) {
				cz.a(new ne("commands.scoreboard.players.list.entity.entry", ((dfj)entry5.getKey()).e(), ((dfl)entry5.getValue()).b()), false);
			}
		}

		return map3.size();
	}

	private static int a(cz cz, int integer) throws CommandSyntaxException {
		dfm dfm3 = cz.j().aF();
		if (dfm3.a(integer) == null) {
			throw b.create();
		} else {
			dfm3.a(integer, null);
			cz.a(new ne("commands.scoreboard.objectives.display.cleared", dfm.h()[integer]), true);
			return 0;
		}
	}

	private static int a(cz cz, int integer, dfj dfj) throws CommandSyntaxException {
		dfm dfm4 = cz.j().aF();
		if (dfm4.a(integer) == dfj) {
			throw c.create();
		} else {
			dfm4.a(integer, dfj);
			cz.a(new ne("commands.scoreboard.objectives.display.set", dfm.h()[integer], dfj.d()), true);
			return 0;
		}
	}

	private static int a(cz cz, dfj dfj, mr mr) {
		if (!dfj.d().equals(mr)) {
			dfj.a(mr);
			cz.a(new ne("commands.scoreboard.objectives.modify.displayname", dfj.b(), dfj.e()), true);
		}

		return 0;
	}

	private static int a(cz cz, dfj dfj, dfp.a a) {
		if (dfj.f() != a) {
			dfj.a(a);
			cz.a(new ne("commands.scoreboard.objectives.modify.rendertype", dfj.e()), true);
		}

		return 0;
	}

	private static int a(cz cz, dfj dfj) {
		dfm dfm3 = cz.j().aF();
		dfm3.j(dfj);
		cz.a(new ne("commands.scoreboard.objectives.remove.success", dfj.e()), true);
		return dfm3.c().size();
	}

	private static int a(cz cz, String string, dfp dfp, mr mr) throws CommandSyntaxException {
		dfm dfm5 = cz.j().aF();
		if (dfm5.d(string) != null) {
			throw a.create();
		} else if (string.length() > 16) {
			throw dq.a.create(16);
		} else {
			dfm5.a(string, dfp, mr, dfp.e());
			dfj dfj6 = dfm5.d(string);
			cz.a(new ne("commands.scoreboard.objectives.add.success", dfj6.e()), true);
			return dfm5.c().size();
		}
	}

	private static int b(cz cz) {
		Collection<dfj> collection2 = cz.j().aF().c();
		if (collection2.isEmpty()) {
			cz.a(new ne("commands.scoreboard.objectives.list.empty"), false);
		} else {
			cz.a(new ne("commands.scoreboard.objectives.list.success", collection2.size(), ms.b(collection2, dfj::e)), false);
		}

		return collection2.size();
	}
}
