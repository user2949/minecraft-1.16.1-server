import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dn.h;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;

public class vu {
	private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("commands.execute.blocks.toobig", object1, object2)
	);
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.execute.conditional.fail"));
	private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> new ne("commands.execute.conditional.fail_count", object));
	private static final BinaryOperator<ResultConsumer<cz>> d = (resultConsumer1, resultConsumer2) -> (commandContext, boolean4, integer) -> {
			resultConsumer1.onCommandComplete(commandContext, boolean4, integer);
			resultConsumer2.onCommandComplete(commandContext, boolean4, integer);
		};
	private static final SuggestionProvider<cz> e = (commandContext, suggestionsBuilder) -> {
		day day3 = commandContext.getSource().j().aI();
		return db.a(day3.a(), suggestionsBuilder);
	};

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralCommandNode<cz> literalCommandNode2 = commandDispatcher.register(da.a("execute").requires(cz -> cz.c(2)));
		commandDispatcher.register(
			da.a("execute")
				.requires(cz -> cz.c(2))
				.then(da.a("run").redirect(commandDispatcher.getRoot()))
				.then(a(literalCommandNode2, da.a("if"), true))
				.then(a(literalCommandNode2, da.a("unless"), false))
				.then(da.a("as").then(da.a("targets", dh.b()).fork(literalCommandNode2, commandContext -> {
					List<cz> list2 = Lists.<cz>newArrayList();
		
					for (aom aom4 : dh.c(commandContext, "targets")) {
						list2.add(commandContext.getSource().a(aom4));
					}
		
					return list2;
				})))
				.then(da.a("at").then(da.a("targets", dh.b()).fork(literalCommandNode2, commandContext -> {
					List<cz> list2 = Lists.<cz>newArrayList();
		
					for (aom aom4 : dh.c(commandContext, "targets")) {
						list2.add(commandContext.getSource().a((zd)aom4.l).a(aom4.cz()).a(aom4.be()));
					}
		
					return list2;
				})))
				.then(da.a("store").then(a(literalCommandNode2, da.a("result"), true)).then(a(literalCommandNode2, da.a("success"), false)))
				.then(
					da.a("positioned")
						.then(da.a("pos", eo.a()).redirect(literalCommandNode2, commandContext -> commandContext.getSource().a(eo.a(commandContext, "pos")).a(dg.a.FEET)))
						.then(da.a("as").then(da.a("targets", dh.b()).fork(literalCommandNode2, commandContext -> {
							List<cz> list2 = Lists.<cz>newArrayList();
				
							for (aom aom4 : dh.c(commandContext, "targets")) {
								list2.add(commandContext.getSource().a(aom4.cz()));
							}
				
							return list2;
						})))
				)
				.then(
					da.a("rotated")
						.then(
							da.a("rot", el.a())
								.redirect(literalCommandNode2, commandContext -> commandContext.getSource().a(el.a(commandContext, "rot").b(commandContext.getSource())))
						)
						.then(da.a("as").then(da.a("targets", dh.b()).fork(literalCommandNode2, commandContext -> {
							List<cz> list2 = Lists.<cz>newArrayList();
				
							for (aom aom4 : dh.c(commandContext, "targets")) {
								list2.add(commandContext.getSource().a(aom4.be()));
							}
				
							return list2;
						})))
				)
				.then(da.a("facing").then(da.a("entity").then(da.a("targets", dh.b()).then(da.a("anchor", dg.a()).fork(literalCommandNode2, commandContext -> {
					List<cz> list2 = Lists.<cz>newArrayList();
					dg.a a3 = dg.a(commandContext, "anchor");
		
					for (aom aom5 : dh.c(commandContext, "targets")) {
						list2.add(commandContext.getSource().a(aom5, a3));
					}
		
					return list2;
				})))).then(da.a("pos", eo.a()).redirect(literalCommandNode2, commandContext -> commandContext.getSource().b(eo.a(commandContext, "pos")))))
				.then(
					da.a("align")
						.then(
							da.a("axes", em.a())
								.redirect(literalCommandNode2, commandContext -> commandContext.getSource().a(commandContext.getSource().d().a(em.a(commandContext, "axes"))))
						)
				)
				.then(
					da.a("anchored")
						.then(da.a("anchor", dg.a()).redirect(literalCommandNode2, commandContext -> commandContext.getSource().a(dg.a(commandContext, "anchor"))))
				)
				.then(
					da.a("in")
						.then(da.a("dimension", df.a()).redirect(literalCommandNode2, commandContext -> commandContext.getSource().a(df.a(commandContext, "dimension"))))
				)
		);
	}

	private static ArgumentBuilder<cz, ?> a(LiteralCommandNode<cz> literalCommandNode, LiteralArgumentBuilder<cz> literalArgumentBuilder, boolean boolean3) {
		literalArgumentBuilder.then(
			da.a("score")
				.then(
					da.a("targets", dw.b())
						.suggests(dw.a)
						.then(
							da.a("objective", dq.a())
								.redirect(
									literalCommandNode, commandContext -> a(commandContext.getSource(), dw.c(commandContext, "targets"), dq.a(commandContext, "objective"), boolean3)
								)
						)
				)
		);
		literalArgumentBuilder.then(
			da.a("bossbar")
				.then(
					da.a("id", dv.a())
						.suggests(vh.a)
						.then(da.a("value").redirect(literalCommandNode, commandContext -> a(commandContext.getSource(), vh.a(commandContext), true, boolean3)))
						.then(da.a("max").redirect(literalCommandNode, commandContext -> a(commandContext.getSource(), vh.a(commandContext), false, boolean3)))
				)
		);

		for (xx.c c5 : xx.b) {
			c5.a(
				literalArgumentBuilder,
				argumentBuilder -> argumentBuilder.then(
						da.a("path", dn.a())
							.then(
								da.a("int")
									.then(
										da.a("scale", DoubleArgumentType.doubleArg())
											.redirect(
												literalCommandNode,
												commandContext -> a(
														commandContext.getSource(),
														c5.a(commandContext),
														dn.a(commandContext, "path"),
														integer -> lj.a((int)((double)integer * DoubleArgumentType.getDouble(commandContext, "scale"))),
														boolean3
													)
											)
									)
							)
							.then(
								da.a("float")
									.then(
										da.a("scale", DoubleArgumentType.doubleArg())
											.redirect(
												literalCommandNode,
												commandContext -> a(
														commandContext.getSource(),
														c5.a(commandContext),
														dn.a(commandContext, "path"),
														integer -> lh.a((float)((double)integer * DoubleArgumentType.getDouble(commandContext, "scale"))),
														boolean3
													)
											)
									)
							)
							.then(
								da.a("short")
									.then(
										da.a("scale", DoubleArgumentType.doubleArg())
											.redirect(
												literalCommandNode,
												commandContext -> a(
														commandContext.getSource(),
														c5.a(commandContext),
														dn.a(commandContext, "path"),
														integer -> ls.a((short)((int)((double)integer * DoubleArgumentType.getDouble(commandContext, "scale")))),
														boolean3
													)
											)
									)
							)
							.then(
								da.a("long")
									.then(
										da.a("scale", DoubleArgumentType.doubleArg())
											.redirect(
												literalCommandNode,
												commandContext -> a(
														commandContext.getSource(),
														c5.a(commandContext),
														dn.a(commandContext, "path"),
														integer -> lm.a((long)((double)integer * DoubleArgumentType.getDouble(commandContext, "scale"))),
														boolean3
													)
											)
									)
							)
							.then(
								da.a("double")
									.then(
										da.a("scale", DoubleArgumentType.doubleArg())
											.redirect(
												literalCommandNode,
												commandContext -> a(
														commandContext.getSource(),
														c5.a(commandContext),
														dn.a(commandContext, "path"),
														integer -> lf.a((double)integer * DoubleArgumentType.getDouble(commandContext, "scale")),
														boolean3
													)
											)
									)
							)
							.then(
								da.a("byte")
									.then(
										da.a("scale", DoubleArgumentType.doubleArg())
											.redirect(
												literalCommandNode,
												commandContext -> a(
														commandContext.getSource(),
														c5.a(commandContext),
														dn.a(commandContext, "path"),
														integer -> lc.a((byte)((int)((double)integer * DoubleArgumentType.getDouble(commandContext, "scale")))),
														boolean3
													)
											)
									)
							)
					)
			);
		}

		return literalArgumentBuilder;
	}

	private static cz a(cz cz, Collection<String> collection, dfj dfj, boolean boolean4) {
		dfm dfm5 = cz.j().aF();
		return cz.a((commandContext, boolean6, integer) -> {
			for (String string9 : collection) {
				dfl dfl10 = dfm5.c(string9, dfj);
				int integer11 = boolean4 ? integer : (boolean6 ? 1 : 0);
				dfl10.c(integer11);
			}
		}, d);
	}

	private static cz a(cz cz, uz uz, boolean boolean3, boolean boolean4) {
		return cz.a((commandContext, boolean5, integer) -> {
			int integer7 = boolean4 ? integer : (boolean5 ? 1 : 0);
			if (boolean3) {
				uz.a(integer7);
			} else {
				uz.b(integer7);
			}
		}, d);
	}

	private static cz a(cz cz, xw xw, h h, IntFunction<lu> intFunction, boolean boolean5) {
		return cz.a((commandContext, boolean6, integer) -> {
			try {
				le le8 = xw.a();
				int integer9 = boolean5 ? integer : (boolean6 ? 1 : 0);
				h.b(le8, () -> (lu)intFunction.apply(integer9));
				xw.a(le8);
			} catch (CommandSyntaxException var9) {
			}
		}, d);
	}

	private static ArgumentBuilder<cz, ?> a(CommandNode<cz> commandNode, LiteralArgumentBuilder<cz> literalArgumentBuilder, boolean boolean3) {
		literalArgumentBuilder.then(
				da.a("block")
					.then(
						da.a("pos", eh.a())
							.then(
								a(
									commandNode,
									da.a("block", ed.a()),
									boolean3,
									commandContext -> ed.a(commandContext, "block").test(new cfn(commandContext.getSource().e(), eh.a(commandContext, "pos"), true))
								)
							)
					)
			)
			.then(
				da.a("score")
					.then(
						da.a("target", dw.a())
							.suggests(dw.a)
							.then(
								da.a("targetObjective", dq.a())
									.then(
										da.a("=")
											.then(
												da.a("source", dw.a())
													.suggests(dw.a)
													.then(a(commandNode, da.a("sourceObjective", dq.a()), boolean3, commandContext -> a(commandContext, Integer::equals)))
											)
									)
									.then(
										da.a("<")
											.then(
												da.a("source", dw.a())
													.suggests(dw.a)
													.then(a(commandNode, da.a("sourceObjective", dq.a()), boolean3, commandContext -> a(commandContext, (integer1, integer2) -> integer1 < integer2)))
											)
									)
									.then(
										da.a("<=")
											.then(
												da.a("source", dw.a())
													.suggests(dw.a)
													.then(a(commandNode, da.a("sourceObjective", dq.a()), boolean3, commandContext -> a(commandContext, (integer1, integer2) -> integer1 <= integer2)))
											)
									)
									.then(
										da.a(">")
											.then(
												da.a("source", dw.a())
													.suggests(dw.a)
													.then(a(commandNode, da.a("sourceObjective", dq.a()), boolean3, commandContext -> a(commandContext, (integer1, integer2) -> integer1 > integer2)))
											)
									)
									.then(
										da.a(">=")
											.then(
												da.a("source", dw.a())
													.suggests(dw.a)
													.then(a(commandNode, da.a("sourceObjective", dq.a()), boolean3, commandContext -> a(commandContext, (integer1, integer2) -> integer1 >= integer2)))
											)
									)
									.then(da.a("matches").then(a(commandNode, da.a("range", du.a()), boolean3, commandContext -> a(commandContext, du.b.a(commandContext, "range")))))
							)
					)
			)
			.then(
				da.a("blocks")
					.then(
						da.a("start", eh.a())
							.then(
								da.a("end", eh.a())
									.then(da.a("destination", eh.a()).then(a(commandNode, da.a("all"), boolean3, false)).then(a(commandNode, da.a("masked"), boolean3, true)))
							)
					)
			)
			.then(
				da.a("entity")
					.then(
						da.a("entities", dh.b())
							.fork(commandNode, commandContext -> a(commandContext, boolean3, !dh.c(commandContext, "entities").isEmpty()))
							.executes(a(boolean3, commandContext -> dh.c(commandContext, "entities").size()))
					)
			)
			.then(
				da.a("predicate")
					.then(a(commandNode, da.a("predicate", dv.a()).suggests(e), boolean3, commandContext -> a(commandContext.getSource(), dv.c(commandContext, "predicate"))))
			);

		for (xx.c c5 : xx.c) {
			literalArgumentBuilder.then(
				c5.a(
					da.a("data"),
					argumentBuilder -> argumentBuilder.then(
							da.a("path", dn.a())
								.fork(commandNode, commandContext -> a(commandContext, boolean3, a(c5.a(commandContext), dn.a(commandContext, "path")) > 0))
								.executes(a(boolean3, commandContext -> a(c5.a(commandContext), dn.a(commandContext, "path"))))
						)
				)
			);
		}

		return literalArgumentBuilder;
	}

	private static Command<cz> a(boolean boolean1, vu.a a) {
		return boolean1 ? commandContext -> {
			int integer3 = a.test(commandContext);
			if (integer3 > 0) {
				commandContext.getSource().a(new ne("commands.execute.conditional.pass_count", integer3), false);
				return integer3;
			} else {
				throw b.create();
			}
		} : commandContext -> {
			int integer3 = a.test(commandContext);
			if (integer3 == 0) {
				commandContext.getSource().a(new ne("commands.execute.conditional.pass"), false);
				return 1;
			} else {
				throw c.create(integer3);
			}
		};
	}

	private static int a(xw xw, h h) throws CommandSyntaxException {
		return h.b(xw.a());
	}

	private static boolean a(CommandContext<cz> commandContext, BiPredicate<Integer, Integer> biPredicate) throws CommandSyntaxException {
		String string3 = dw.a(commandContext, "target");
		dfj dfj4 = dq.a(commandContext, "targetObjective");
		String string5 = dw.a(commandContext, "source");
		dfj dfj6 = dq.a(commandContext, "sourceObjective");
		dfm dfm7 = commandContext.getSource().j().aF();
		if (dfm7.b(string3, dfj4) && dfm7.b(string5, dfj6)) {
			dfl dfl8 = dfm7.c(string3, dfj4);
			dfl dfl9 = dfm7.c(string5, dfj6);
			return biPredicate.test(dfl8.b(), dfl9.b());
		} else {
			return false;
		}
	}

	private static boolean a(CommandContext<cz> commandContext, bx.d d) throws CommandSyntaxException {
		String string3 = dw.a(commandContext, "target");
		dfj dfj4 = dq.a(commandContext, "targetObjective");
		dfm dfm5 = commandContext.getSource().j().aF();
		return !dfm5.b(string3, dfj4) ? false : d.d(dfm5.c(string3, dfj4).b());
	}

	private static boolean a(cz cz, ddm ddm) {
		zd zd3 = cz.e();
		dat.a a4 = new dat.a(zd3).a(dda.f, new fu(cz.d())).b(dda.a, cz.f());
		return ddm.test(a4.a(dcz.c));
	}

	private static Collection<cz> a(CommandContext<cz> commandContext, boolean boolean2, boolean boolean3) {
		return (Collection<cz>)(boolean3 == boolean2 ? Collections.singleton(commandContext.getSource()) : Collections.emptyList());
	}

	private static ArgumentBuilder<cz, ?> a(CommandNode<cz> commandNode, ArgumentBuilder<cz, ?> argumentBuilder, boolean boolean3, vu.b b) {
		return argumentBuilder.fork(commandNode, commandContext -> a(commandContext, boolean3, b.test(commandContext))).executes(commandContext -> {
			if (boolean3 == b.test(commandContext)) {
				commandContext.getSource().a(new ne("commands.execute.conditional.pass"), false);
				return 1;
			} else {
				throw vu.b.create();
			}
		});
	}

	private static ArgumentBuilder<cz, ?> a(CommandNode<cz> commandNode, ArgumentBuilder<cz, ?> argumentBuilder, boolean boolean3, boolean boolean4) {
		return argumentBuilder.fork(commandNode, commandContext -> a(commandContext, boolean3, c(commandContext, boolean4).isPresent()))
			.executes(boolean3 ? commandContext -> a(commandContext, boolean4) : commandContext -> b(commandContext, boolean4));
	}

	private static int a(CommandContext<cz> commandContext, boolean boolean2) throws CommandSyntaxException {
		OptionalInt optionalInt3 = c(commandContext, boolean2);
		if (optionalInt3.isPresent()) {
			commandContext.getSource().a(new ne("commands.execute.conditional.pass_count", optionalInt3.getAsInt()), false);
			return optionalInt3.getAsInt();
		} else {
			throw b.create();
		}
	}

	private static int b(CommandContext<cz> commandContext, boolean boolean2) throws CommandSyntaxException {
		OptionalInt optionalInt3 = c(commandContext, boolean2);
		if (optionalInt3.isPresent()) {
			throw c.create(optionalInt3.getAsInt());
		} else {
			commandContext.getSource().a(new ne("commands.execute.conditional.pass"), false);
			return 1;
		}
	}

	private static OptionalInt c(CommandContext<cz> commandContext, boolean boolean2) throws CommandSyntaxException {
		return a(commandContext.getSource().e(), eh.a(commandContext, "start"), eh.a(commandContext, "end"), eh.a(commandContext, "destination"), boolean2);
	}

	private static OptionalInt a(zd zd, fu fu2, fu fu3, fu fu4, boolean boolean5) throws CommandSyntaxException {
		ctd ctd6 = new ctd(fu2, fu3);
		ctd ctd7 = new ctd(fu4, fu4.a(ctd6.c()));
		fu fu8 = new fu(ctd7.a - ctd6.a, ctd7.b - ctd6.b, ctd7.c - ctd6.c);
		int integer9 = ctd6.d() * ctd6.e() * ctd6.f();
		if (integer9 > 32768) {
			throw a.create(32768, integer9);
		} else {
			int integer10 = 0;

			for (int integer11 = ctd6.c; integer11 <= ctd6.f; integer11++) {
				for (int integer12 = ctd6.b; integer12 <= ctd6.e; integer12++) {
					for (int integer13 = ctd6.a; integer13 <= ctd6.d; integer13++) {
						fu fu14 = new fu(integer13, integer12, integer11);
						fu fu15 = fu14.a(fu8);
						cfj cfj16 = zd.d_(fu14);
						if (!boolean5 || !cfj16.a(bvs.a)) {
							if (cfj16 != zd.d_(fu15)) {
								return OptionalInt.empty();
							}

							cdl cdl17 = zd.c(fu14);
							cdl cdl18 = zd.c(fu15);
							if (cdl17 != null) {
								if (cdl18 == null) {
									return OptionalInt.empty();
								}

								le le19 = cdl17.a(new le());
								le19.r("x");
								le19.r("y");
								le19.r("z");
								le le20 = cdl18.a(new le());
								le20.r("x");
								le20.r("y");
								le20.r("z");
								if (!le19.equals(le20)) {
									return OptionalInt.empty();
								}
							}

							integer10++;
						}
					}
				}
			}

			return OptionalInt.of(integer10);
		}
	}

	@FunctionalInterface
	interface a {
		int test(CommandContext<cz> commandContext) throws CommandSyntaxException;
	}

	@FunctionalInterface
	interface b {
		boolean test(CommandContext<cz> commandContext) throws CommandSyntaxException;
	}
}
