import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;

public class vr {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.effect.give.failed"));
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.effect.clear.everything.failed"));
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("commands.effect.clear.specific.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("effect")
				.requires(cz -> cz.c(2))
				.then(
					da.a("clear")
						.executes(commandContext -> a(commandContext.getSource(), ImmutableList.of(commandContext.getSource().g())))
						.then(
							da.a("targets", dh.b())
								.executes(commandContext -> a(commandContext.getSource(), dh.b(commandContext, "targets")))
								.then(da.a("effect", dm.a()).executes(commandContext -> a(commandContext.getSource(), dh.b(commandContext, "targets"), dm.a(commandContext, "effect"))))
						)
				)
				.then(
					da.a("give")
						.then(
							da.a("targets", dh.b())
								.then(
									da.a("effect", dm.a())
										.executes(commandContext -> a(commandContext.getSource(), dh.b(commandContext, "targets"), dm.a(commandContext, "effect"), null, 0, true))
										.then(
											da.a("seconds", IntegerArgumentType.integer(1, 1000000))
												.executes(
													commandContext -> a(
															commandContext.getSource(),
															dh.b(commandContext, "targets"),
															dm.a(commandContext, "effect"),
															IntegerArgumentType.getInteger(commandContext, "seconds"),
															0,
															true
														)
												)
												.then(
													da.a("amplifier", IntegerArgumentType.integer(0, 255))
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	dh.b(commandContext, "targets"),
																	dm.a(commandContext, "effect"),
																	IntegerArgumentType.getInteger(commandContext, "seconds"),
																	IntegerArgumentType.getInteger(commandContext, "amplifier"),
																	true
																)
														)
														.then(
															da.a("hideParticles", BoolArgumentType.bool())
																.executes(
																	commandContext -> a(
																			commandContext.getSource(),
																			dh.b(commandContext, "targets"),
																			dm.a(commandContext, "effect"),
																			IntegerArgumentType.getInteger(commandContext, "seconds"),
																			IntegerArgumentType.getInteger(commandContext, "amplifier"),
																			!BoolArgumentType.getBool(commandContext, "hideParticles")
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

	private static int a(cz cz, Collection<? extends aom> collection, aoe aoe, @Nullable Integer integer, int integer, boolean boolean6) throws CommandSyntaxException {
		int integer7 = 0;
		int integer8;
		if (integer != null) {
			if (aoe.a()) {
				integer8 = integer;
			} else {
				integer8 = integer * 20;
			}
		} else if (aoe.a()) {
			integer8 = 1;
		} else {
			integer8 = 600;
		}

		for (aom aom10 : collection) {
			if (aom10 instanceof aoy) {
				aog aog11 = new aog(aoe, integer8, integer, false, boolean6);
				if (((aoy)aom10).c(aog11)) {
					integer7++;
				}
			}
		}

		if (integer7 == 0) {
			throw a.create();
		} else {
			if (collection.size() == 1) {
				cz.a(new ne("commands.effect.give.success.single", aoe.d(), ((aom)collection.iterator().next()).d(), integer8 / 20), true);
			} else {
				cz.a(new ne("commands.effect.give.success.multiple", aoe.d(), collection.size(), integer8 / 20), true);
			}

			return integer7;
		}
	}

	private static int a(cz cz, Collection<? extends aom> collection) throws CommandSyntaxException {
		int integer3 = 0;

		for (aom aom5 : collection) {
			if (aom5 instanceof aoy && ((aoy)aom5).df()) {
				integer3++;
			}
		}

		if (integer3 == 0) {
			throw b.create();
		} else {
			if (collection.size() == 1) {
				cz.a(new ne("commands.effect.clear.everything.success.single", ((aom)collection.iterator().next()).d()), true);
			} else {
				cz.a(new ne("commands.effect.clear.everything.success.multiple", collection.size()), true);
			}

			return integer3;
		}
	}

	private static int a(cz cz, Collection<? extends aom> collection, aoe aoe) throws CommandSyntaxException {
		int integer4 = 0;

		for (aom aom6 : collection) {
			if (aom6 instanceof aoy && ((aoy)aom6).d(aoe)) {
				integer4++;
			}
		}

		if (integer4 == 0) {
			throw c.create();
		} else {
			if (collection.size() == 1) {
				cz.a(new ne("commands.effect.clear.specific.success.single", aoe.d(), ((aom)collection.iterator().next()).d()), true);
			} else {
				cz.a(new ne("commands.effect.clear.specific.success.multiple", aoe.d(), collection.size()), true);
			}

			return integer4;
		}
	}
}
