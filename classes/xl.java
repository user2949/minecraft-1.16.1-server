import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;

public class xl {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.team.add.duplicate"));
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.team.add.longName", object));
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("commands.team.empty.unchanged"));
	private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("commands.team.option.name.unchanged"));
	private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ne("commands.team.option.color.unchanged"));
	private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new ne("commands.team.option.friendlyfire.alreadyEnabled"));
	private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new ne("commands.team.option.friendlyfire.alreadyDisabled"));
	private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(new ne("commands.team.option.seeFriendlyInvisibles.alreadyEnabled"));
	private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(new ne("commands.team.option.seeFriendlyInvisibles.alreadyDisabled"));
	private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(new ne("commands.team.option.nametagVisibility.unchanged"));
	private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(new ne("commands.team.option.deathMessageVisibility.unchanged"));
	private static final SimpleCommandExceptionType l = new SimpleCommandExceptionType(new ne("commands.team.option.collisionRule.unchanged"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("team")
				.requires(cz -> cz.c(2))
				.then(
					da.a("list")
						.executes(commandContext -> a(commandContext.getSource()))
						.then(da.a("team", dz.a()).executes(commandContext -> c(commandContext.getSource(), dz.a(commandContext, "team"))))
				)
				.then(
					da.a("add")
						.then(
							da.a("team", StringArgumentType.word())
								.executes(commandContext -> a(commandContext.getSource(), StringArgumentType.getString(commandContext, "team")))
								.then(
									da.a("displayName", dd.a())
										.executes(commandContext -> a(commandContext.getSource(), StringArgumentType.getString(commandContext, "team"), dd.a(commandContext, "displayName")))
								)
						)
				)
				.then(da.a("remove").then(da.a("team", dz.a()).executes(commandContext -> b(commandContext.getSource(), dz.a(commandContext, "team")))))
				.then(da.a("empty").then(da.a("team", dz.a()).executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team")))))
				.then(
					da.a("join")
						.then(
							da.a("team", dz.a())
								.executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), Collections.singleton(commandContext.getSource().g().bT())))
								.then(
									da.a("members", dw.b())
										.suggests(dw.a)
										.executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dw.c(commandContext, "members")))
								)
						)
				)
				.then(da.a("leave").then(da.a("members", dw.b()).suggests(dw.a).executes(commandContext -> a(commandContext.getSource(), dw.c(commandContext, "members")))))
				.then(
					da.a("modify")
						.then(
							da.a("team", dz.a())
								.then(
									da.a("displayName")
										.then(
											da.a("displayName", dd.a())
												.executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dd.a(commandContext, "displayName")))
										)
								)
								.then(
									da.a("color")
										.then(da.a("value", dc.a()).executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dc.a(commandContext, "value"))))
								)
								.then(
									da.a("friendlyFire")
										.then(
											da.a("allowed", BoolArgumentType.bool())
												.executes(commandContext -> b(commandContext.getSource(), dz.a(commandContext, "team"), BoolArgumentType.getBool(commandContext, "allowed")))
										)
								)
								.then(
									da.a("seeFriendlyInvisibles")
										.then(
											da.a("allowed", BoolArgumentType.bool())
												.executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), BoolArgumentType.getBool(commandContext, "allowed")))
										)
								)
								.then(
									da.a("nametagVisibility")
										.then(da.a("never").executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dfo.b.NEVER)))
										.then(da.a("hideForOtherTeams").executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dfo.b.HIDE_FOR_OTHER_TEAMS)))
										.then(da.a("hideForOwnTeam").executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dfo.b.HIDE_FOR_OWN_TEAM)))
										.then(da.a("always").executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dfo.b.ALWAYS)))
								)
								.then(
									da.a("deathMessageVisibility")
										.then(da.a("never").executes(commandContext -> b(commandContext.getSource(), dz.a(commandContext, "team"), dfo.b.NEVER)))
										.then(da.a("hideForOtherTeams").executes(commandContext -> b(commandContext.getSource(), dz.a(commandContext, "team"), dfo.b.HIDE_FOR_OTHER_TEAMS)))
										.then(da.a("hideForOwnTeam").executes(commandContext -> b(commandContext.getSource(), dz.a(commandContext, "team"), dfo.b.HIDE_FOR_OWN_TEAM)))
										.then(da.a("always").executes(commandContext -> b(commandContext.getSource(), dz.a(commandContext, "team"), dfo.b.ALWAYS)))
								)
								.then(
									da.a("collisionRule")
										.then(da.a("never").executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dfo.a.NEVER)))
										.then(da.a("pushOwnTeam").executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dfo.a.PUSH_OWN_TEAM)))
										.then(da.a("pushOtherTeams").executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dfo.a.PUSH_OTHER_TEAMS)))
										.then(da.a("always").executes(commandContext -> a(commandContext.getSource(), dz.a(commandContext, "team"), dfo.a.ALWAYS)))
								)
								.then(
									da.a("prefix")
										.then(da.a("prefix", dd.a()).executes(commandContext -> b(commandContext.getSource(), dz.a(commandContext, "team"), dd.a(commandContext, "prefix"))))
								)
								.then(
									da.a("suffix")
										.then(da.a("suffix", dd.a()).executes(commandContext -> c(commandContext.getSource(), dz.a(commandContext, "team"), dd.a(commandContext, "suffix"))))
								)
						)
				)
		);
	}

	private static int a(cz cz, Collection<String> collection) {
		dfm dfm3 = cz.j().aF();

		for (String string5 : collection) {
			dfm3.h(string5);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.team.leave.success.single", collection.iterator().next()), true);
		} else {
			cz.a(new ne("commands.team.leave.success.multiple", collection.size()), true);
		}

		return collection.size();
	}

	private static int a(cz cz, dfk dfk, Collection<String> collection) {
		dfm dfm4 = cz.j().aF();

		for (String string6 : collection) {
			dfm4.a(string6, dfk);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.team.join.success.single", collection.iterator().next(), dfk.d()), true);
		} else {
			cz.a(new ne("commands.team.join.success.multiple", collection.size(), dfk.d()), true);
		}

		return collection.size();
	}

	private static int a(cz cz, dfk dfk, dfo.b b) throws CommandSyntaxException {
		if (dfk.j() == b) {
			throw j.create();
		} else {
			dfk.a(b);
			cz.a(new ne("commands.team.option.nametagVisibility.success", dfk.d(), b.b()), true);
			return 0;
		}
	}

	private static int b(cz cz, dfk dfk, dfo.b b) throws CommandSyntaxException {
		if (dfk.k() == b) {
			throw k.create();
		} else {
			dfk.b(b);
			cz.a(new ne("commands.team.option.deathMessageVisibility.success", dfk.d(), b.b()), true);
			return 0;
		}
	}

	private static int a(cz cz, dfk dfk, dfo.a a) throws CommandSyntaxException {
		if (dfk.l() == a) {
			throw l.create();
		} else {
			dfk.a(a);
			cz.a(new ne("commands.team.option.collisionRule.success", dfk.d(), a.b()), true);
			return 0;
		}
	}

	private static int a(cz cz, dfk dfk, boolean boolean3) throws CommandSyntaxException {
		if (dfk.i() == boolean3) {
			if (boolean3) {
				throw h.create();
			} else {
				throw i.create();
			}
		} else {
			dfk.b(boolean3);
			cz.a(new ne("commands.team.option.seeFriendlyInvisibles." + (boolean3 ? "enabled" : "disabled"), dfk.d()), true);
			return 0;
		}
	}

	private static int b(cz cz, dfk dfk, boolean boolean3) throws CommandSyntaxException {
		if (dfk.h() == boolean3) {
			if (boolean3) {
				throw f.create();
			} else {
				throw g.create();
			}
		} else {
			dfk.a(boolean3);
			cz.a(new ne("commands.team.option.friendlyfire." + (boolean3 ? "enabled" : "disabled"), dfk.d()), true);
			return 0;
		}
	}

	private static int a(cz cz, dfk dfk, mr mr) throws CommandSyntaxException {
		if (dfk.c().equals(mr)) {
			throw d.create();
		} else {
			dfk.a(mr);
			cz.a(new ne("commands.team.option.name.success", dfk.d()), true);
			return 0;
		}
	}

	private static int a(cz cz, dfk dfk, i i) throws CommandSyntaxException {
		if (dfk.n() == i) {
			throw e.create();
		} else {
			dfk.a(i);
			cz.a(new ne("commands.team.option.color.success", dfk.d(), i.f()), true);
			return 0;
		}
	}

	private static int a(cz cz, dfk dfk) throws CommandSyntaxException {
		dfm dfm3 = cz.j().aF();
		Collection<String> collection4 = Lists.<String>newArrayList(dfk.g());
		if (collection4.isEmpty()) {
			throw c.create();
		} else {
			for (String string6 : collection4) {
				dfm3.b(string6, dfk);
			}

			cz.a(new ne("commands.team.empty.success", collection4.size(), dfk.d()), true);
			return collection4.size();
		}
	}

	private static int b(cz cz, dfk dfk) {
		dfm dfm3 = cz.j().aF();
		dfm3.d(dfk);
		cz.a(new ne("commands.team.remove.success", dfk.d()), true);
		return dfm3.g().size();
	}

	private static int a(cz cz, String string) throws CommandSyntaxException {
		return a(cz, string, new nd(string));
	}

	private static int a(cz cz, String string, mr mr) throws CommandSyntaxException {
		dfm dfm4 = cz.j().aF();
		if (dfm4.f(string) != null) {
			throw a.create();
		} else if (string.length() > 16) {
			throw b.create(16);
		} else {
			dfk dfk5 = dfm4.g(string);
			dfk5.a(mr);
			cz.a(new ne("commands.team.add.success", dfk5.d()), true);
			return dfm4.g().size();
		}
	}

	private static int c(cz cz, dfk dfk) {
		Collection<String> collection3 = dfk.g();
		if (collection3.isEmpty()) {
			cz.a(new ne("commands.team.list.members.empty", dfk.d()), false);
		} else {
			cz.a(new ne("commands.team.list.members.success", dfk.d(), collection3.size(), ms.a(collection3)), false);
		}

		return collection3.size();
	}

	private static int a(cz cz) {
		Collection<dfk> collection2 = cz.j().aF().g();
		if (collection2.isEmpty()) {
			cz.a(new ne("commands.team.list.teams.empty"), false);
		} else {
			cz.a(new ne("commands.team.list.teams.success", collection2.size(), ms.b(collection2, dfk::d)), false);
		}

		return collection2.size();
	}

	private static int b(cz cz, dfk dfk, mr mr) {
		dfk.b(mr);
		cz.a(new ne("commands.team.option.prefix.success", mr), false);
		return 1;
	}

	private static int c(cz cz, dfk dfk, mr mr) {
		dfk.c(mr);
		cz.a(new ne("commands.team.option.suffix.success", mr), false);
		return 1;
	}
}
