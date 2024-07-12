import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class wi {
	public static final SuggestionProvider<cz> a = (commandContext, suggestionsBuilder) -> {
		dax dax3 = commandContext.getSource().j().aH();
		return db.a(dax3.a(), suggestionsBuilder);
	};
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.drop.no_held_items", object));
	private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> new ne("commands.drop.no_loot_table", object));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			a(
				da.a("loot").requires(cz -> cz.c(2)),
				(argumentBuilder, b) -> argumentBuilder.then(
							da.a("fish")
								.then(
									da.a("loot_table", dv.a())
										.suggests(a)
										.then(
											da.a("pos", eh.a())
												.executes(commandContext -> a(commandContext, dv.e(commandContext, "loot_table"), eh.a(commandContext, "pos"), bki.b, b))
												.then(
													da.a("tool", et.a())
														.executes(
															commandContext -> a(commandContext, dv.e(commandContext, "loot_table"), eh.a(commandContext, "pos"), et.a(commandContext, "tool").a(1, false), b)
														)
												)
												.then(
													da.a("mainhand")
														.executes(
															commandContext -> a(
																	commandContext, dv.e(commandContext, "loot_table"), eh.a(commandContext, "pos"), a(commandContext.getSource(), aor.MAINHAND), b
																)
														)
												)
												.then(
													da.a("offhand")
														.executes(
															commandContext -> a(
																	commandContext, dv.e(commandContext, "loot_table"), eh.a(commandContext, "pos"), a(commandContext.getSource(), aor.OFFHAND), b
																)
														)
												)
										)
								)
						)
						.then(da.a("loot").then(da.a("loot_table", dv.a()).suggests(a).executes(commandContext -> a(commandContext, dv.e(commandContext, "loot_table"), b))))
						.then(da.a("kill").then(da.a("target", dh.a()).executes(commandContext -> a(commandContext, dh.a(commandContext, "target"), b))))
						.then(
							da.a("mine")
								.then(
									da.a("pos", eh.a())
										.executes(commandContext -> a(commandContext, eh.a(commandContext, "pos"), bki.b, b))
										.then(da.a("tool", et.a()).executes(commandContext -> a(commandContext, eh.a(commandContext, "pos"), et.a(commandContext, "tool").a(1, false), b)))
										.then(da.a("mainhand").executes(commandContext -> a(commandContext, eh.a(commandContext, "pos"), a(commandContext.getSource(), aor.MAINHAND), b)))
										.then(da.a("offhand").executes(commandContext -> a(commandContext, eh.a(commandContext, "pos"), a(commandContext.getSource(), aor.OFFHAND), b)))
								)
						)
			)
		);
	}

	private static <T extends ArgumentBuilder<cz, T>> T a(T argumentBuilder, wi.c c) {
		return argumentBuilder.then(
				da.a("replace")
					.then(
						da.a("entity")
							.then(
								da.a("entities", dh.b())
									.then(
										c.construct(
												da.a("slot", dy.a()), (commandContext, list, a) -> a(dh.b(commandContext, "entities"), dy.a(commandContext, "slot"), list.size(), list, a)
											)
											.then(
												c.construct(
													da.a("count", IntegerArgumentType.integer(0)),
													(commandContext, list, a) -> a(
															dh.b(commandContext, "entities"), dy.a(commandContext, "slot"), IntegerArgumentType.getInteger(commandContext, "count"), list, a
														)
												)
											)
									)
							)
					)
					.then(
						da.a("block")
							.then(
								da.a("targetPos", eh.a())
									.then(
										c.construct(
												da.a("slot", dy.a()),
												(commandContext, list, a) -> a(commandContext.getSource(), eh.a(commandContext, "targetPos"), dy.a(commandContext, "slot"), list.size(), list, a)
											)
											.then(
												c.construct(
													da.a("count", IntegerArgumentType.integer(0)),
													(commandContext, list, a) -> a(
															commandContext.getSource(),
															eh.a(commandContext, "targetPos"),
															IntegerArgumentType.getInteger(commandContext, "slot"),
															IntegerArgumentType.getInteger(commandContext, "count"),
															list,
															a
														)
												)
											)
									)
							)
					)
			)
			.then(
				da.a("insert")
					.then(c.construct(da.a("targetPos", eh.a()), (commandContext, list, a) -> a(commandContext.getSource(), eh.a(commandContext, "targetPos"), list, a)))
			)
			.then(da.a("give").then(c.construct(da.a("players", dh.d()), (commandContext, list, a) -> a(dh.f(commandContext, "players"), list, a))))
			.then(
				da.a("spawn")
					.then(c.construct(da.a("targetPos", eo.a()), (commandContext, list, a) -> a(commandContext.getSource(), eo.a(commandContext, "targetPos"), list, a)))
			);
	}

	private static amz a(cz cz, fu fu) throws CommandSyntaxException {
		cdl cdl3 = cz.e().c(fu);
		if (!(cdl3 instanceof amz)) {
			throw wt.a.create();
		} else {
			return (amz)cdl3;
		}
	}

	private static int a(cz cz, fu fu, List<bki> list, wi.a a) throws CommandSyntaxException {
		amz amz5 = a(cz, fu);
		List<bki> list6 = Lists.<bki>newArrayListWithCapacity(list.size());

		for (bki bki8 : list) {
			if (a(amz5, bki8.i())) {
				amz5.Z_();
				list6.add(bki8);
			}
		}

		a.accept(list6);
		return list6.size();
	}

	private static boolean a(amz amz, bki bki) {
		boolean boolean3 = false;

		for (int integer4 = 0; integer4 < amz.ab_() && !bki.a(); integer4++) {
			bki bki5 = amz.a(integer4);
			if (amz.b(integer4, bki)) {
				if (bki5.a()) {
					amz.a(integer4, bki);
					boolean3 = true;
					break;
				}

				if (a(bki5, bki)) {
					int integer6 = bki.c() - bki5.E();
					int integer7 = Math.min(bki.E(), integer6);
					bki.g(integer7);
					bki5.f(integer7);
					boolean3 = true;
				}
			}
		}

		return boolean3;
	}

	private static int a(cz cz, fu fu, int integer3, int integer4, List<bki> list, wi.a a) throws CommandSyntaxException {
		amz amz7 = a(cz, fu);
		int integer8 = amz7.ab_();
		if (integer3 >= 0 && integer3 < integer8) {
			List<bki> list9 = Lists.<bki>newArrayListWithCapacity(list.size());

			for (int integer10 = 0; integer10 < integer4; integer10++) {
				int integer11 = integer3 + integer10;
				bki bki12 = integer10 < list.size() ? (bki)list.get(integer10) : bki.b;
				if (amz7.b(integer11, bki12)) {
					amz7.a(integer11, bki12);
					list9.add(bki12);
				}
			}

			a.accept(list9);
			return list9.size();
		} else {
			throw wt.b.create(integer3);
		}
	}

	private static boolean a(bki bki1, bki bki2) {
		return bki1.b() == bki2.b() && bki1.g() == bki2.g() && bki1.E() <= bki1.c() && Objects.equals(bki1.o(), bki2.o());
	}

	private static int a(Collection<ze> collection, List<bki> list, wi.a a) throws CommandSyntaxException {
		List<bki> list4 = Lists.<bki>newArrayListWithCapacity(list.size());

		for (bki bki6 : list) {
			for (ze ze8 : collection) {
				if (ze8.bt.e(bki6.i())) {
					list4.add(bki6);
				}
			}
		}

		a.accept(list4);
		return list4.size();
	}

	private static void a(aom aom, List<bki> list2, int integer3, int integer4, List<bki> list5) {
		for (int integer6 = 0; integer6 < integer4; integer6++) {
			bki bki7 = integer6 < list2.size() ? (bki)list2.get(integer6) : bki.b;
			if (aom.a_(integer3 + integer6, bki7.i())) {
				list5.add(bki7);
			}
		}
	}

	private static int a(Collection<? extends aom> collection, int integer2, int integer3, List<bki> list, wi.a a) throws CommandSyntaxException {
		List<bki> list6 = Lists.<bki>newArrayListWithCapacity(list.size());

		for (aom aom8 : collection) {
			if (aom8 instanceof ze) {
				ze ze9 = (ze)aom8;
				ze9.bv.c();
				a(aom8, list, integer2, integer3, list6);
				ze9.bv.c();
			} else {
				a(aom8, list, integer2, integer3, list6);
			}
		}

		a.accept(list6);
		return list6.size();
	}

	private static int a(cz cz, dem dem, List<bki> list, wi.a a) throws CommandSyntaxException {
		zd zd5 = cz.e();
		list.forEach(bki -> {
			bbg bbg4 = new bbg(zd5, dem.b, dem.c, dem.d, bki.i());
			bbg4.m();
			zd5.c(bbg4);
		});
		a.accept(list);
		return list.size();
	}

	private static void a(cz cz, List<bki> list) {
		if (list.size() == 1) {
			bki bki3 = (bki)list.get(0);
			cz.a(new ne("commands.drop.success.single", bki3.E(), bki3.C()), false);
		} else {
			cz.a(new ne("commands.drop.success.multiple", list.size()), false);
		}
	}

	private static void a(cz cz, List<bki> list, uh uh) {
		if (list.size() == 1) {
			bki bki4 = (bki)list.get(0);
			cz.a(new ne("commands.drop.success.single_with_table", bki4.E(), bki4.C(), uh), false);
		} else {
			cz.a(new ne("commands.drop.success.multiple_with_table", list.size(), uh), false);
		}
	}

	private static bki a(cz cz, aor aor) throws CommandSyntaxException {
		aom aom3 = cz.g();
		if (aom3 instanceof aoy) {
			return ((aoy)aom3).b(aor);
		} else {
			throw b.create(aom3.d());
		}
	}

	private static int a(CommandContext<cz> commandContext, fu fu, bki bki, wi.b b) throws CommandSyntaxException {
		cz cz5 = commandContext.getSource();
		zd zd6 = cz5.e();
		cfj cfj7 = zd6.d_(fu);
		cdl cdl8 = zd6.c(fu);
		dat.a a9 = new dat.a(zd6).a(dda.f, fu).a(dda.h, cfj7).b(dda.i, cdl8).b(dda.a, cz5.f()).a(dda.j, bki);
		List<bki> list10 = cfj7.a(a9);
		return b.accept(commandContext, list10, list -> a(cz5, list, cfj7.b().r()));
	}

	private static int a(CommandContext<cz> commandContext, aom aom, wi.b b) throws CommandSyntaxException {
		if (!(aom instanceof aoy)) {
			throw c.create(aom.d());
		} else {
			uh uh4 = ((aoy)aom).do();
			cz cz5 = commandContext.getSource();
			dat.a a6 = new dat.a(cz5.e());
			aom aom7 = cz5.f();
			if (aom7 instanceof bec) {
				a6.a(dda.b, (bec)aom7);
			}

			a6.a(dda.c, anw.o);
			a6.b(dda.e, aom7);
			a6.b(dda.d, aom7);
			a6.a(dda.a, aom);
			a6.a(dda.f, new fu(cz5.d()));
			daw daw8 = cz5.j().aH().a(uh4);
			List<bki> list9 = daw8.a(a6.a(dcz.f));
			return b.accept(commandContext, list9, list -> a(cz5, list, uh4));
		}
	}

	private static int a(CommandContext<cz> commandContext, uh uh, wi.b b) throws CommandSyntaxException {
		cz cz4 = commandContext.getSource();
		dat.a a5 = new dat.a(cz4.e()).b(dda.a, cz4.f()).a(dda.f, new fu(cz4.d()));
		return a(commandContext, uh, a5.a(dcz.b), b);
	}

	private static int a(CommandContext<cz> commandContext, uh uh, fu fu, bki bki, wi.b b) throws CommandSyntaxException {
		cz cz6 = commandContext.getSource();
		dat dat7 = new dat.a(cz6.e()).a(dda.f, fu).a(dda.j, bki).b(dda.a, cz6.f()).a(dcz.e);
		return a(commandContext, uh, dat7, b);
	}

	private static int a(CommandContext<cz> commandContext, uh uh, dat dat, wi.b b) throws CommandSyntaxException {
		cz cz5 = commandContext.getSource();
		daw daw6 = cz5.j().aH().a(uh);
		List<bki> list7 = daw6.a(dat);
		return b.accept(commandContext, list7, list -> a(cz5, list));
	}

	@FunctionalInterface
	interface a {
		void accept(List<bki> list) throws CommandSyntaxException;
	}

	@FunctionalInterface
	interface b {
		int accept(CommandContext<cz> commandContext, List<bki> list, wi.a a) throws CommandSyntaxException;
	}

	@FunctionalInterface
	interface c {
		ArgumentBuilder<cz, ?> construct(ArgumentBuilder<cz, ?> argumentBuilder, wi.b b);
	}
}
