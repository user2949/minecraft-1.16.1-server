import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class wn {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.particle.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("particle")
				.requires(cz -> cz.c(2))
				.then(
					da.a("name", dt.a())
						.executes(
							commandContext -> a(
									commandContext.getSource(),
									dt.a(commandContext, "name"),
									commandContext.getSource().d(),
									dem.a,
									0.0F,
									0,
									false,
									commandContext.getSource().j().ac().s()
								)
						)
						.then(
							da.a("pos", eo.a())
								.executes(
									commandContext -> a(
											commandContext.getSource(),
											dt.a(commandContext, "name"),
											eo.a(commandContext, "pos"),
											dem.a,
											0.0F,
											0,
											false,
											commandContext.getSource().j().ac().s()
										)
								)
								.then(
									da.a("delta", eo.a(false))
										.then(
											da.a("speed", FloatArgumentType.floatArg(0.0F))
												.then(
													da.a("count", IntegerArgumentType.integer(0))
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	dt.a(commandContext, "name"),
																	eo.a(commandContext, "pos"),
																	eo.a(commandContext, "delta"),
																	FloatArgumentType.getFloat(commandContext, "speed"),
																	IntegerArgumentType.getInteger(commandContext, "count"),
																	false,
																	commandContext.getSource().j().ac().s()
																)
														)
														.then(
															da.a("force")
																.executes(
																	commandContext -> a(
																			commandContext.getSource(),
																			dt.a(commandContext, "name"),
																			eo.a(commandContext, "pos"),
																			eo.a(commandContext, "delta"),
																			FloatArgumentType.getFloat(commandContext, "speed"),
																			IntegerArgumentType.getInteger(commandContext, "count"),
																			true,
																			commandContext.getSource().j().ac().s()
																		)
																)
																.then(
																	da.a("viewers", dh.d())
																		.executes(
																			commandContext -> a(
																					commandContext.getSource(),
																					dt.a(commandContext, "name"),
																					eo.a(commandContext, "pos"),
																					eo.a(commandContext, "delta"),
																					FloatArgumentType.getFloat(commandContext, "speed"),
																					IntegerArgumentType.getInteger(commandContext, "count"),
																					true,
																					dh.f(commandContext, "viewers")
																				)
																		)
																)
														)
														.then(
															da.a("normal")
																.executes(
																	commandContext -> a(
																			commandContext.getSource(),
																			dt.a(commandContext, "name"),
																			eo.a(commandContext, "pos"),
																			eo.a(commandContext, "delta"),
																			FloatArgumentType.getFloat(commandContext, "speed"),
																			IntegerArgumentType.getInteger(commandContext, "count"),
																			false,
																			commandContext.getSource().j().ac().s()
																		)
																)
																.then(
																	da.a("viewers", dh.d())
																		.executes(
																			commandContext -> a(
																					commandContext.getSource(),
																					dt.a(commandContext, "name"),
																					eo.a(commandContext, "pos"),
																					eo.a(commandContext, "delta"),
																					FloatArgumentType.getFloat(commandContext, "speed"),
																					IntegerArgumentType.getInteger(commandContext, "count"),
																					false,
																					dh.f(commandContext, "viewers")
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

	private static int a(cz cz, hf hf, dem dem3, dem dem4, float float5, int integer, boolean boolean7, Collection<ze> collection) throws CommandSyntaxException {
		int integer9 = 0;

		for (ze ze11 : collection) {
			if (cz.e().a(ze11, hf, boolean7, dem3.b, dem3.c, dem3.d, integer, dem4.b, dem4.c, dem4.d, (double)float5)) {
				integer9++;
			}
		}

		if (integer9 == 0) {
			throw a.create();
		} else {
			cz.a(new ne("commands.particle.success", gl.az.b(hf.b()).toString()), true);
			return integer9;
		}
	}
}
